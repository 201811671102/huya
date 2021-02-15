package pre.cg.ws.websocket;

import org.springframework.util.StringUtils;
import pre.cg.ws.base.PlayInfo;

import java.util.concurrent.ConcurrentHashMap;


public class PlayInfoManager {
    private static final PlayInfoManager playInfoManager = new PlayInfoManager();
    public static PlayInfoManager getInstance() {
        return playInfoManager;
    }
    private PlayInfoManager(){}

    // uid 到 playinfo 的映射
    private ConcurrentHashMap<String, PlayInfo> uidPlayInfo = new ConcurrentHashMap<>();

    public void addPlayInfo(PlayInfo playInfo){
        this.uidPlayInfo.put(playInfo.getUid(),playInfo);
    }
    public PlayInfo getPlayInfo(String uid){
        return this.uidPlayInfo.getOrDefault(uid,new PlayInfo());
    }

    public void removePlayInfo(String uid) {
        this.uidPlayInfo.remove(uid);
    }
}
