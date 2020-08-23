package pre.cg.huya.base.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ProfileMessage
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/11 13:08
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileMessage {
    private Integer uri;
    private String toRoomid;
    private String fromRoomid;
    private Object message;
    private String name;
    private String photo;
}
