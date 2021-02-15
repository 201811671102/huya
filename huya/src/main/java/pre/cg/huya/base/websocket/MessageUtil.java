package pre.cg.huya.base.websocket;



import lombok.extern.slf4j.Slf4j;
import pre.cg.huya.websocket.manage.ConnManager;
import pre.cg.huya.websocket.manage.RoomManager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;


/**
 * @ClassName MessageUtil
 * @Description TODO
 * @Author QQ163
 * @Date 2020/7/20 13:43
 **/
public class MessageUtil {
    /**
     * 等待房间消息广播
     */
    public static void broadcastwait(int uri, Object data,String roomId) throws IOException {
        Packet packet = new Packet(uri,data);
        List<String> uidlist = RoomManager.getInstance().getWaitUid(roomId);
        if (uidlist!=null){
            for (int i =0;i<uidlist.size();i++){
                ChannelUtil.write(ConnManager.getInstance().getChannel(uidlist.get(i), roomId), Packet.forSend(packet));
                if (uri == Protocol.Room_Open.getValue()){
                    RoomManager.getInstance().waittoroom(roomId,uidlist.get(i));
                }
            }
        }
    }

    /**
     * 房间消息广播
     */
    public static void broadcast(int uri, Object data,String roomId) throws IOException {
        Packet packet = new Packet(uri,data);
        List<String> uidlist = RoomManager.getInstance().getUidList(roomId);
        if (uidlist!=null) {
            for (String uid : uidlist) {
                ChannelUtil.write(ConnManager.getInstance().getChannel(uid, roomId), Packet.forSend(packet));
            }
        }
    }

    /**
     * 按玩家unionId单播
     */
    public static void unicast(String uid, int uri, Object data,String roomId) throws IOException {
        Packet packet = new Packet(uri,data);
        ChannelUtil.write(ConnManager.getInstance().getChannel(uid, roomId), Packet.forSend(packet));
    }

    /**
     * 房间消息广播
     * 关闭房间
     */
    public static void broadcastClose(String roomId) throws IOException {
        Packet packet = new Packet(Protocol.Room_Close.getValue(),"");
        List<String> uidlist = RoomManager.getInstance().getUidList(roomId);
        if (uidlist!=null) {
            for (int i = 0; i < uidlist.size(); i++) {
                ChannelUtil.write(ConnManager.getInstance().getChannel(uidlist.get(i), roomId), Packet.forSend(packet));
                RoomManager.getInstance().leftRoom(uidlist.get(i), roomId);
            }
            RoomManager.getInstance().closeRoom(roomId);
        }
    }

    /**
     * 房间消息广播
     * solo房间
     */
    public static void broadcastSolo(ProfileInfo profileInfo,Integer uri){
        Packet packet = new Packet(uri,profileInfo);
        List<ProfileInfo> profileInfoList = RoomManager.getInstance().getSoloObj(profileInfo.getSoloRoomId());
        if (profileInfoList!=null) {
            profileInfoList.stream()
                    .filter(profileInfo1 -> !profileInfo1.equals(profileInfo))
                    .forEach(profileInfo1 -> {
                        try {
                            ChannelUtil.write(ConnManager.getInstance().getChannel(profileInfo1.getUid(), profileInfo1.getRoomid()), Packet.forSend(packet));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }
}
