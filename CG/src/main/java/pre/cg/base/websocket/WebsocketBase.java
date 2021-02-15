package pre.cg.base.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName WebsocketBase
 * @Description TODO
 * @Author QQ163
 * @Date 2020/3/25 15:40
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebsocketBase {
    private Integer fromid;
    private String uphoto;
    private Object message;
}
