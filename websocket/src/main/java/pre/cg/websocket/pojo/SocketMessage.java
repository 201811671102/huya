package pre.cg.websocket.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName message
 * @Description TODO
 * @Author QQ163
 * @Date 2020/4/1 17:43
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocketMessage {
    public String message;
    public String date;
}
