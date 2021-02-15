package pre.cg.ws.websocket.handler;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import pre.cg.ws.base.MessageUtil;
import pre.cg.ws.base.Packet;
import pre.cg.ws.base.PlayInfo;
import pre.cg.ws.base.Protocol;
import pre.cg.ws.websocket.ChannelUtil;
import pre.cg.ws.websocket.ConnManager;
import pre.cg.ws.websocket.PlayInfoManager;
import pre.cg.ws.websocket.RoomManager;


import java.io.IOException;
import java.util.List;
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
@ChannelHandler.Sharable
public class WebsocketHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

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
                        // 根据不同协议，进入部分逻辑分支处理
                        switch (protocol) {
                            // 客户端心跳请求
                            case HeartbeatReq:
                                MessageUtil.unicast(uid,Protocol.HeartbeatReq.getValue(),"CG心跳包"+body,roomId);
                                break;
                             //开房
                            case Room_Open:
                                RoomManager.getInstance().openRoom(roomId,profileId);
                                MessageUtil.broadcast(Protocol.Room_Open.getValue(),"",roomId);
                                break;
                            //申请同戏
                            case Play_Info:
                                PlayInfoManager.getInstance().addPlayInfo(new PlayInfo(uid,(String) body,"0"));
                                RoomManager.getInstance().joinRoom(roomId,uid);
                                log.info("接收到观众传来的名字--"+(String) body+"--id--"+uid);
                                MessageUtil.unicast(profileId,Protocol.Play_Info.getValue(), body,roomId);
                                break;
                            //游戏开始
                            case Game_On:
                                MessageUtil.broadcast(Protocol.Game_On.getValue(),"",roomId);
                                break;
                            //游戏结束
                            case Game_Over:
                                PlayInfo playInfo = PlayInfoManager.getInstance().getPlayInfo(uid);
                                playInfo.setScore(body);
                                log.info("发送给主播观众信息,名字--"+playInfo.getName()+"--id--"+playInfo.getUid());
                                MessageUtil.unicast(profileId,Protocol.Game_Over.getValue(),playInfo,roomId);
                                break;
                             //关闭房间
                            case Room_Close:
                                RoomManager.getInstance().closeRoom(roomId);
                                List<String> uidList = RoomManager.getInstance().getUidList(roomId);
                                for (String uId : uidList){
                                    PlayInfoManager.getInstance().removePlayInfo(uId);
                                }
                                MessageUtil.broadcast(Protocol.Room_Close.getValue(),"",uid);
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
