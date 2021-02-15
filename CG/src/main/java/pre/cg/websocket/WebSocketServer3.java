package pre.cg.websocket;

import org.springframework.stereotype.Component;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/websocket/{uid}")
@Component
public class WebSocketServer3 {

    private static  int onlineCount = 0;
    private static CopyOnWriteArraySet<WebSocketServer3> webSocketServer3s = new CopyOnWriteArraySet<>();
    private Session session;
    @OnOpen
    public void onOpen(Session session, @PathParam("uid")String uid){
        this.session = session;
        webSocketServer3s.add(this);
        addOnlineCount();
    }
    @OnClose
    public void onClose(@PathParam("uid")String uid){
        subOnlineCount();
        webSocketServer3s.remove(this);
    }
    @OnMessage
    public void onMessage(String messsage,@PathParam("uid")String uid){
        sendInfo(messsage,uid);
    }
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
    public static void sendInfo(String message, @PathParam("uid") String sid) {
        for (WebSocketServer3 item : webSocketServer3s) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                item.sendMessage(message);
            } catch (IOException e) {
                return;
            }
        }
    }

    /**
     * 原子性的++操作
     */
    public static synchronized void addOnlineCount() {
        WebSocketServer3.onlineCount++;
    }

    /**
     * 原子性的--操作
     */
    public static synchronized void subOnlineCount() {
        WebSocketServer3.onlineCount--;
    }
}
