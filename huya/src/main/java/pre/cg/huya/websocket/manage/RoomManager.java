package pre.cg.huya.websocket.manage;

import pre.cg.huya.base.websocket.PlayInfo;
import pre.cg.huya.base.websocket.ProfileInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
    private ConcurrentHashMap<String, List<PlayInfo>> RoomIdUid = new ConcurrentHashMap<>();
    //roomid 到 profileId
    private ConcurrentHashMap<String, String> RoomIdProfileId = new ConcurrentHashMap<>();
    //roomId 到 uid 观众等待
    private ConcurrentHashMap<String, List<String>> RoomIdUidWait = new ConcurrentHashMap<>();
    //solo房间 到主播 信息
    private ConcurrentHashMap<String, List<ProfileInfo>> SolrRoom = new ConcurrentHashMap<>();

    public static RoomManager getRoomManager() {
        return roomManager;
    }

    public ConcurrentHashMap<String, List<PlayInfo>> getRoomIdUid() {
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
    public void leftSole(String soloRoomid,ProfileInfo profileInfo){
        List<ProfileInfo> profileInfoList = new ArrayList<>();
        if ((profileInfoList = this.SolrRoom.getOrDefault(soloRoomid,null))==null?false:profileInfoList.contains(profileInfo)){
            if (profileInfoList.size() == 1){
                this.SolrRoom.remove(soloRoomid);
            }else{
                List<ProfileInfo> profileInfos = this.SolrRoom.get(soloRoomid);
                profileInfos.remove(profileInfos.indexOf(profileInfo));
                this.SolrRoom.replace(soloRoomid,profileInfos);
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
        this.RoomIdUidWait.remove(roomid);
    }
    public void leftWaitRoom(String roomid,String uid){
        List<String> waituidlist = this.RoomIdUidWait.get(roomid);
        waituidlist.remove(waituidlist.indexOf(uid));
    }
    public List<String> getWaitUid(String roomid){
        return this.RoomIdUidWait.getOrDefault(roomid,null);
    }
    public void waittoroom(String roomid,String uid){
        List<String> uidlist = this.RoomIdUidWait.get(roomid);
        uidlist.remove(uidlist.indexOf(uid));
        this.RoomIdUidWait.replace(roomid,uidlist);
        PlayInfo playInfo = new PlayInfo();
        playInfo.setUid(uid);
        joinRoom(roomid,playInfo);
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
        List<PlayInfo> list = new ArrayList<>();
        this.RoomIdUid.put(roomid,list);
    }
    public void joinRoom(String roomid,PlayInfo playInfo){
        List<PlayInfo> list = this.RoomIdUid.get(roomid);
        list.add(playInfo);
        this.RoomIdUid.replace(roomid,list);
    }
    public void closeRoom(String roomid){
        this.RoomIdUid.remove(roomid);
        this.RoomIdUidWait.remove(roomid);
    }
    public void leftRoom(String uid,String roomId){
        List<PlayInfo> list = this.RoomIdUid.get(roomId);
        list.remove(list.indexOf(uid));
        this.RoomIdUid.put(roomId,list);
    }
    public boolean isRoom(String roomid){
        return this.RoomIdUid.containsKey(roomid==null?"":roomid);
    }
    public List<PlayInfo> getUidList(String roomid){
        return this.RoomIdUid.getOrDefault(roomid,new ArrayList<>());
    }
    public String getProfileId(String roomid){
        return this.RoomIdProfileId.getOrDefault(roomid,null);
    }
    public boolean inRoom(String uid,String roomid){
        List<PlayInfo> uidlist = this.RoomIdUid.getOrDefault(roomid,null);
        return uidlist == null?false:(uidlist.contains(uid));
    }
    public PlayInfo getPlayInfo(String roomid,PlayInfo playInfo){
        List<PlayInfo> playInfoList =  RoomIdUid.getOrDefault(roomid,null);
        if (playInfoList != null)
            return playInfoList.get(playInfoList.indexOf(playInfo));
        return null;
    }
    public List<PlayInfo> updatePlayInfoSort(String roomid,PlayInfo playInfo){
        List<PlayInfo> playInfoList = RoomIdUid.getOrDefault(roomid,null);
        if (playInfoList == null){
            return null;
        }
        Collections.replaceAll(playInfoList,playInfo,playInfo);
        return playInfoList.stream().sorted((a,b)->{
            return b.getScore().compareTo(a.getScore());
        }).collect(Collectors.toList());
    }
    public void  updatePlayInfo(String roomid,PlayInfo playInfo){
        List<PlayInfo> playInfoList = RoomIdUid.getOrDefault(roomid,null);
        if (playInfoList != null)
        Collections.replaceAll(playInfoList,playInfo,playInfo);
    }
}
