package pre.cg.ws.base;




import pre.cg.ws.websocket.ChannelUtil;
import pre.cg.ws.websocket.ConnManager;
import pre.cg.ws.websocket.RoomManager;

import java.io.IOException;
import java.util.*;


/**
 * @ClassName MessageUtil
 * @Description TODO
 * @Author QQ163
 * @Date 2020/7/20 13:43
 **/
public class MessageUtil {

    /**
     * 房间消息广播
     */
    public static void broadcast(int uri, Object data,String roomId) throws IOException {
        Packet packet = new Packet(uri,data);
        List<String> uidlist = RoomManager.getInstance().getUidList(roomId);
        for (String value : uidlist){
            ChannelUtil.write(ConnManager.getInstance().getChannel(value, roomId), Packet.forSend(packet));
        }
    }

    /**
     * 按玩家unionId单播
     */
    public static void unicast(String uid, int uri, Object data,String roomId) throws IOException {
        Packet packet = new Packet(uri,data);
        ChannelUtil.write(ConnManager.getInstance().getChannel(uid, roomId), Packet.forSend(packet));
    }
}
