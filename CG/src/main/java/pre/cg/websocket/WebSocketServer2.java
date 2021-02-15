package pre.cg.websocket;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/WebSocket22/{uid}")
@Component
@Slf4j
public class WebSocketServer2 {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static Map<String,Session> webSocketServer2Map = new ConcurrentHashMap<>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //接收sid
    private String uid="";

    @OnOpen
    public void onOpen(Session session,@PathParam("sid")String uid){
        onlineCount++;
        this.session  = session;
        this.uid = uid;
        webSocketServer2Map.put(uid,session);
    }
    @OnMessage
    public void onMessage(String message,Session session){
        sendMessageTOALl(message);
    }
    @OnError
    public void onError(Throwable throwable){
        log.info("服务端发生了错误"+throwable.getMessage());
    }
    @OnClose
    public void onClose(){
        onlineCount--;
        webSocketServer2Map.remove(uid);
        sendMessageTOALl(uid+"已经下线");
    }
    
    public void sendMessageTOALl(String message){
        Set<String> keySet = webSocketServer2Map.keySet();
        keySet.remove(uid);
        for (String key : keySet){
           webSocketServer2Map.get(key).getAsyncRemote().sendText(message);
        }
    }
    public void sendMessageTOOne(String message,String touid){
        try {
            webSocketServer2Map.get(touid).getAsyncRemote().sendText(message);
        }catch (Exception e){
            session.getAsyncRemote().sendText("发送信息失败");
        }
    }

    public static synchronized int getOnlineCount(){
        return onlineCount;
    }


}
