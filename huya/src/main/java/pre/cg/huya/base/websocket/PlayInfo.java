package pre.cg.huya.base.websocket;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

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
    private Integer score;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayInfo playInfo = (PlayInfo) o;
        return Objects.equals(uid, playInfo.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid);
    }
}
