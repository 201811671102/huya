package pre.cg.huya.base.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ProfileInfo
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/11 10:31
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileInfo {
    private String uid;
    private String roomid;
    private String name;
    private String photo;
    private String score;
    private String soloRoomId;
}
