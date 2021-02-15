package pre.cg.ws.base;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName PlayInfo
 * @Description TODO
 * @Author QQ163
 * @Date 2020/7/20 14:15
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayInfo {
    private String uid;
    private String name;
    private Object score;
}
