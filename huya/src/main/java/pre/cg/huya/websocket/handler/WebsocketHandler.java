package pre.cg.huya.websocket.handler;


import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.StringUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pre.cg.huya.base.websocket.*;
import pre.cg.huya.dto.ScorePlayInfo;
import pre.cg.huya.pojo.PlayInfo;
import pre.cg.huya.service.PlayInfoService;
import pre.cg.huya.websocket.manage.ConnManager;
import pre.cg.huya.websocket.manage.RoomManager;


import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @date 2020/5/21
 * @author wangpeng1@huya.com
 * @description websocket协议处理器
 *
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class WebsocketHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    @Autowired
    private PlayInfoService playInfoService;

    //解决@Autowired为null
    private static WebsocketHandler websocketHandler;
    @PostConstruct
    public void init(){
        websocketHandler = this;
    }

    // 业务线程池
    private static final ExecutorService bizThreadPool =
            new ThreadPoolExecutor(10, 512, 60L, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(20000));

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        if (msg instanceof BinaryWebSocketFrame) {
            handleWSClose(ctx);
        } else if (msg instanceof CloseWebSocketFrame) {
            handleWSClose(ctx);
        } else if(msg instanceof TextWebSocketFrame) {
            TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame)msg;
            handleWSTextFrame(ctx,textWebSocketFrame);
        }

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent && ((IdleStateEvent)evt).state() == IdleState.READER_IDLE) {
            handleWSClose(ctx);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    protected void handleWSTextFrame(ChannelHandlerContext ctx, TextWebSocketFrame data) throws IOException, ClassNotFoundException {
        Packet packet = Packet.forPacket(data.text());
        if(packet != null){
            bizThreadPool.submit(new Runnable() {
                @Override
                public void run() {
                    try{
                        // 拿到协议号
                        Protocol protocol = Protocol.forNumber(packet.getUri());
                        String roomId = ConnManager.getInstance().getRoomId(ctx.channel());
                        String uid = ConnManager.getInstance().getUid(ctx.channel());
                        String profileId = RoomManager.getInstance().getProfileId(roomId);
                        Object body = packet.getBody();
                        if(protocol == null){
                            handleWSClose(ctx.channel());
                            return;
                        }
                        if (RoomManager.getInstance().inwaitRoom(roomId,uid)){
                            Packet packet = new Packet(Protocol.Room_Close.getValue(),"");
                            ChannelUtil.write(ctx.channel(),Packet.forSend(packet));
                            return;
                        }
                        // 根据不同协议，进入部分逻辑分支处理
                        switch (protocol) {
                            // 客户端心跳请求
                            case HeartbeatReq:
                                if(uid.equalsIgnoreCase(profileId)){
                                    MessageUtil.unicast(uid,protocol.HeartbeatReq.getValue(),"",roomId);
                                    break;
                                }
                                if(RoomManager.getInstance().isRoom(roomId)){
                                    MessageUtil.unicast(uid,protocol.Room_Open.getValue(),"",roomId);
                                }else{
                                    MessageUtil.unicast(uid,protocol.Room_Close.getValue(),"",roomId);
                                }
                                break;
                             //开房
                            case Room_Open:
                                RoomManager.getInstance().openRoom(roomId);
                                MessageUtil.broadcastwait(Protocol.Room_Open.getValue(),"",roomId);
                                break;
                            //申请同戏
                            case Play_Info:
                                PlayInfo playInfo = websocketHandler.playInfoService.selectInfo(uid);
                                if (playInfo == null){
                                    playInfo = new PlayInfo();
                                    playInfo.setUid(uid);
                                    playInfo.setName((String)body);
                                    playInfo.setPhone("null");
                                    websocketHandler.playInfoService.insertInfo(playInfo);
                                }
                                RoomManager.getInstance().joinRoom(roomId,uid);
                                MessageUtil.unicast(profileId,Protocol.Play_Info.getValue(), body,roomId);
                                break;
                            //游戏开始
                            case Game_On:
                                MessageUtil.broadcast(Protocol.Game_On.getValue(),"主播开始游戏",roomId);
                                break;
                            //游戏结束
                            case Game_Over:
                                PlayInfo playInfo1 = websocketHandler.playInfoService.selectInfo(uid);
                                if (playInfo1 == null){
                                    break;
                                }
                                ScorePlayInfo scorePlayInfo = new ScorePlayInfo(playInfo1);
                                scorePlayInfo.setScore((Integer)body);
                                MessageUtil.unicast(profileId,Protocol.Game_Over.getValue(),scorePlayInfo,roomId);
                                break;
                             //关闭房间
                            case Room_Close:
                                MessageUtil.broadcastClose(roomId);
                                break;
                              //私聊主播
                            case Profile_Talk:
                                ProfileMessage profileMessage = JSONObject.parseObject(body.toString(),ProfileMessage.class);
                                profileMessage.setFromRoomid(roomId);
                                String toRoomid = profileMessage.getToRoomid();
                                String toToRoomProfileid = RoomManager.getInstance().getProfileId(toRoomid);
                                if(StringUtils.isNullOrEmpty(toToRoomProfileid)){
                                    profileMessage.setMessage("对方未上线");
                                    profileMessage.setFromRoomid("");
                                    profileMessage.setName("");
                                    profileMessage.setToRoomid(uid);
                                    profileMessage.setUri(404);
                                    MessageUtil.unicast(uid,Protocol.Profile_Talk.getValue(),profileMessage,roomId);
                                    break;
                                }
                                MessageUtil.unicast(toToRoomProfileid,Protocol.Profile_Talk.getValue(),profileMessage,toRoomid);
                                break;
                             //solo 开房
                            case Solo_Open:
                                ProfileInfo profileInfoopen = JSONObject.parseObject( body.toString(),ProfileInfo.class);
                                profileInfoopen.setUid(uid);
                                profileInfoopen.setRoomid(roomId);
                                profileInfoopen.setSoloRoomId(roomId);
                                RoomManager.getInstance().openSolo(profileInfoopen);
                                MessageUtil.unicast(uid,protocol.Solo_Open.getValue(),profileInfoopen,roomId);
                                break;
                              //solo 加入房间
                            case Solo_Join:
                                ProfileInfo profileInfojoin = JSONObject.parseObject( body.toString(),ProfileInfo.class);
                                profileInfojoin.setUid(uid);
                                profileInfojoin.setRoomid(roomId);
                                if(!RoomManager.getInstance().isSoloRoom(profileInfojoin.getSoloRoomId())){
                                    ProfileMessage profileMessagewrong = new ProfileMessage();
                                    profileMessagewrong.setToRoomid(roomId);
                                    profileMessagewrong.setFromRoomid("");
                                    profileMessagewrong.setName("");
                                    profileMessagewrong.setMessage("房间不存在");
                                    profileMessagewrong.setUri(400);
                                    MessageUtil.unicast(uid,Protocol.Profile_Talk.getValue(),profileMessagewrong,roomId);
                                    break;
                                }
                                RoomManager.getInstance().joinSolo(profileInfojoin);
                                MessageUtil.broadcastSolo(profileInfojoin,protocol.Solo_Join.getValue());
                                break;
                             //solo 分数
                            case Solo_Score:
                                ProfileInfo profileInfo = JSONObject.parseObject(body.toString(),ProfileInfo.class);
                                MessageUtil.broadcastSolo(profileInfo,Protocol.Solo_Score.getValue());
                                break;
                              //solr 结束
                            case Solo_Over:
                                ProfileInfo profileInfoover = JSONObject.parseObject(body.toString(),ProfileInfo.class);
                                RoomManager.getInstance().leftSole(profileInfoover);
                                MessageUtil.broadcastSolo(profileInfoover,Protocol.Solo_Over.getValue());
                                break;
                            default:
                                // 未知协议
                                handleWSClose(ctx);
                                break;
                        }
                    }catch (Exception e){
                        log.error("handle frame failed.", e);
                    }
                }
            });
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        handleWSClose(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ConnManager.getInstance().onLogout(ctx.channel());
    }

    public void handleWSClose(ChannelHandlerContext ctx) {
        handleWSClose(ctx.channel());
    }

    public void handleWSClose(Channel channel) {
        channel.writeAndFlush(new CloseWebSocketFrame());
        channel.close();
    }


}
