package pre.cg.ws.websocket;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName RoomManager
 * @Description TODO
 * @Author QQ163
 * @Date 2020/7/22 18:18
 **/
public class RoomManager {
    private static final RoomManager roomManager = new RoomManager();
    public static RoomManager getInstance() {
        return roomManager;
    }
    private RoomManager(){}

    //roomId 到 uid
    private ConcurrentHashMap<String, List<String>> RoomIdUid = new ConcurrentHashMap<>();
    //roomid 到 profileId
    private ConcurrentHashMap<String, String> RoomIdProfileId = new ConcurrentHashMap<>();

    public void openRoom(String roomid,String profileId){
        if (this.RoomIdUid.containsKey(roomid)){
            closeRoom(roomid);
        }
        List<String> list = new ArrayList<>();
        list.add(profileId);
        this.RoomIdUid.put(roomid,list);
        this.RoomIdProfileId.put(roomid,profileId);
    }
    public void joinRoom(String roomid,String uid){
        List<String> list = this.RoomIdUid.get(roomid);
        list.add(uid);
        this.RoomIdUid.put(roomid,list);
    }
    public void closeRoom(String roomid){
        this.RoomIdUid.remove(roomid);
        this.RoomIdProfileId.remove(roomid);
    }
    public void leftRoom(String uid,String roomId){
        List<String> list = this.RoomIdUid.get(roomId);
        list.remove(list.indexOf(uid));
        this.RoomIdUid.put(roomId,list);
    }
    public boolean isRoom(String roomid){
        return this.RoomIdUid.containsKey(roomid);
    }
    public List<String> getUidList(String roomid){
        return this.RoomIdUid.getOrDefault(roomid,new ArrayList<>());
    }

    public String getProfileId(String roomid){
        return this.RoomIdProfileId.getOrDefault(roomid,"0");
    }
}
