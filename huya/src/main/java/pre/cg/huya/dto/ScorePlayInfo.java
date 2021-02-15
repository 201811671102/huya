package pre.cg.huya.dto;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pre.cg.huya.pojo.PlayInfo;

/**
 * @ClassName ScorePlayInfo
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/25 21:55
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScorePlayInfo{
    public ScorePlayInfo(PlayInfo playInfo) {
        this.uid = playInfo.getUid();
        this.name = playInfo.getName();
        this.phone = playInfo.getPhone();
    }
    private String uid;
    private String name;
    private String phone;
    private Integer score;
}
