package pre.cg.huya.websocket.manage;

import pre.cg.huya.base.websocket.ProfileInfo;

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
    //roomId 到 uid 观众等待
    private ConcurrentHashMap<String, List<String>> RoomIdUidWait = new ConcurrentHashMap<>();
    //solo房间 到主播 信息
    private ConcurrentHashMap<String, List<ProfileInfo>> SolrRoom = new ConcurrentHashMap<>();

    public static RoomManager getRoomManager() {
        return roomManager;
    }

    public ConcurrentHashMap<String, List<String>> getRoomIdUid() {
        return RoomIdUid;
    }

    public ConcurrentHashMap<String, String> getRoomIdProfileId() {
        return RoomIdProfileId;
    }

    public ConcurrentHashMap<String, List<String>> getRoomIdUidWait() {
        return RoomIdUidWait;
    }

    public ConcurrentHashMap<String, List<ProfileInfo>> getSolrRoom() {
        return SolrRoom;
    }

    public void openSolo(ProfileInfo profileInfo){
        if (this.SolrRoom.containsKey(profileInfo.getSoloRoomId())){
            this.SolrRoom.remove(profileInfo.getSoloRoomId());
        }
        List<ProfileInfo> profileInfoList = new ArrayList<>();
        profileInfoList.add(profileInfo);
        this.SolrRoom.put(profileInfo.getSoloRoomId(),profileInfoList);
    }
    public boolean isSoloRoom(String soloRoomid){
        return this.SolrRoom.containsKey(soloRoomid==null?"":soloRoomid);
    }
    public void joinSolo(ProfileInfo profileInfo){
        if (this.SolrRoom.containsKey(profileInfo.getSoloRoomId())){
            List<ProfileInfo> profileInfoList = this.SolrRoom.get(profileInfo.getSoloRoomId());
            profileInfoList.add(profileInfo);
            this.SolrRoom.replace(profileInfo.getSoloRoomId(),profileInfoList);
        }
    }
    public void leftSole(ProfileInfo profileInfo){
        List<ProfileInfo> profileInfoList = this.SolrRoom.getOrDefault(profileInfo.getSoloRoomId(),null);
        if (profileInfoList == null?false:profileInfoList.contains(profileInfo)){
            if (profileInfoList.size() == 1){
                this.SolrRoom.remove(profileInfo.getSoloRoomId());
            }else{
                List<ProfileInfo> profileInfos = this.SolrRoom.get(profileInfo.getSoloRoomId());
                profileInfos.remove(profileInfos.indexOf(profileInfo));
                this.SolrRoom.replace(profileInfo.getSoloRoomId(),profileInfos);
            }
        }
    }
    public List<ProfileInfo> getSoloObj(String soloRoomid){
        List<ProfileInfo> profileInfoList = this.SolrRoom.getOrDefault(soloRoomid,null);
        return  profileInfoList;
    }
    

    public boolean inwaitRoom(String roomid,String uid){
        if (this.RoomIdUidWait.containsKey(roomid) && this.RoomIdUidWait.get(roomid).contains(uid)){
            return true;
        }
        return false;
    }
    public void joinWaitRoom(String roomid,String uid){
        if (this.RoomIdUidWait.containsKey(roomid)){
            this.closeWaitRoom(roomid);
        }
        List<String> uidlist = new ArrayList<>();
        uidlist.add(uid);
        this.RoomIdUidWait.put(roomid,uidlist);
    }
    public void closeWaitRoom(String roomid){
        if (this.RoomIdUidWait.containsKey(roomid)) {
            this.RoomIdUidWait.remove(roomid);
        }
    }
    public void leftWaitRoom(String roomid,String uid){
        List<String> waituidlist = this.RoomIdUidWait.getOrDefault(roomid,null);
        int index = waituidlist == null?-1:waituidlist.indexOf(uid);
        if (index != -1){
            waituidlist.remove(index);
        }
    }
    public List<String> getWaitUid(String roomid){
        return this.RoomIdUidWait.getOrDefault(roomid,null);
    }
    public void waittoroom(String roomid,String uid){
        List<String> uidlist = this.RoomIdUidWait.getOrDefault(roomid,null);
        if (uidlist == null || uidlist.size() == 0){
            this.RoomIdUidWait.remove(roomid);
        }else {
            uidlist.remove(uidlist.indexOf(uid));
            this.RoomIdUidWait.replace(roomid,uidlist);
        }
        joinRoom(roomid,uid);
    }

    public void inProfileRoom(String roomid,String profileId){
        if (this.RoomIdProfileId.containsKey(roomid)){
            this.RoomIdProfileId.replace(roomid,profileId);
        }else {
            this.RoomIdProfileId.put(roomid, profileId);
        }
    }
    public void openRoom(String roomid){
        if (this.RoomIdUid.containsKey(roomid)){
            closeRoom(roomid);
        }
        List<String> list = new ArrayList<>();
        this.RoomIdUid.put(roomid,list);
    }
    public void joinRoom(String roomid,String uid){
        List<String> list = this.RoomIdUid.get(roomid);
        if (!list.contains(uid)){
            list.add(uid);
            this.RoomIdUid.replace(roomid,list);
        }
    }
    public void closeRoom(String roomid){
        if (this.RoomIdUid.containsKey(roomid) && getUidList(roomid) != null) {
            this.RoomIdUid.remove(roomid);
            this.RoomIdUidWait.remove(roomid);
        }
    }
    public void leftRoom(String uid,String roomId){
        List<String> list = this.RoomIdUid.getOrDefault(roomId,null);
        int index = list == null?-1:list.indexOf(uid);
        if (index != -1) {
            list.remove(index);
            this.RoomIdUid.put(roomId,list);
        }
    }
    public boolean isRoom(String roomid){
        return this.RoomIdUid.containsKey(roomid==null?"":roomid);
    }
    public List<String> getUidList(String roomid){
        return this.RoomIdUid.getOrDefault(roomid,null);
    }
    public String getProfileId(String roomid){
        return this.RoomIdProfileId.getOrDefault(roomid,null);
    }
    public boolean inRoom(String uid,String roomid){
        List<String> uidlist = this.RoomIdUid.getOrDefault(roomid,null);
        return uidlist == null?false:(uidlist.contains(uid));
    }
    public void removeProfile(String roomid){
        if (this.RoomIdProfileId.containsKey(roomid)) {
            this.RoomIdProfileId.remove(roomid);
        }
    }
}
