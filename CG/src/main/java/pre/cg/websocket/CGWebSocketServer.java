package pre.cg.websocket;

import lombok.extern.log4j.Log4j2;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pre.cg.base.websocket.ServerEncoder;
import pre.cg.base.websocket.WebsocketBase;
import pre.cg.redis.RedisUtil;
import pre.cg.service.UserService;


import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


@ServerEndpoint(value = "/WebsocketServer/{userId}",encoders = {ServerEncoder.class})
@Component
@Log4j2
public class CGWebSocketServer {

    /**concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。*/
    private static ConcurrentHashMap<Integer,CGWebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;
    /**接收userId*/
    private Integer userId;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserService userService;

    public static CGWebSocketServer webSocketServer;

    @PostConstruct
    public void init(){
        webSocketServer = this;
        webSocketServer.redisUtil = this.redisUtil;
        webSocketServer.userService = this.userService;
    }

    @OnOpen
    public void onOpen(Session session,@PathParam("userId") Integer userId) {
        this.session = session;
        this.userId=userId;
        webSocketMap.put(userId,this);
        //接受离线时间收到的信息
        if (webSocketServer.redisUtil.hasKey(userId.toString())){
            List<Object> messageList = webSocketServer.redisUtil.lGet(userId.toString(),0,-1);
            for (Object object : messageList){
                try {
                    WebsocketBase websocketBase = (WebsocketBase) object;
                    this.sendMessage(websocketBase.getMessage().toString(),websocketBase.getFromid(),this.userId);
                    webSocketServer.redisUtil.del(userId.toString());
                }catch (Exception e){
                    e.printStackTrace();
                    log.info(this.userId+"<-->:信息接受失败");
                }

            }
        }

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("userid")Integer userid) {
        webSocketMap.remove(userid);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message) {
        try {
            sendInfo(message);
        }catch (Exception e){
            log.info(e.toString());
        }
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:"+this.userId+",原因:"+error.getMessage());
    }

    public void sendInfo(Object object) throws IOException, EncodeException {
        this.session.getBasicRemote().sendObject(object);
    }
    public void sendInfo(String message) throws IOException, EncodeException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 发送自定义消息
     * */
    public static void sendMessage(String message, @PathParam("userId")Integer fromid,Integer toid) throws IOException, EncodeException {
        WebsocketBase websocketBase = new WebsocketBase();
        websocketBase.setFromid(fromid);
        websocketBase.setMessage(message);
        websocketBase.setUphoto(webSocketServer.userService.SelectOne("uid",fromid).getPhoto());
        if ( !webSocketMap.containsKey(toid)){
            webSocketServer.redisUtil.lSet(toid.toString(),websocketBase);
        }else{
            webSocketMap.get(toid).sendInfo(websocketBase);
        }
    }


    /**
     * 发送自定义群发消息
     * */
    public void sendInfoToAll(String message,@PathParam("userId")Integer fromid) throws IOException, EncodeException {
        if (StringUtils.isNotBlank(message)) {
            for (Integer key : webSocketMap.keySet()) {
                if (key != fromid || webSocketMap.get(key) != null){
                    webSocketMap.get(key).sendMessage(message,fromid,webSocketMap.get(key).userId);
                }
            }
        }
    }

    /**
     * 发送自定义群发消息
     * */
    public void sendStringInfoToAll(String message,@PathParam("userId")Integer fromid) throws IOException, EncodeException {
        if (StringUtils.isNotBlank(message)) {
            for (Integer key : webSocketMap.keySet()) {
                if (key != fromid || webSocketMap.get(key) != null){
                    webSocketMap.get(key).sendInfo(message);
                }
            }
        }
    }
}
