package pre.cg.shiro.data.enpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName User
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/14 20:41
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String account;
    private String password;
    private String salt;
    private List<Role> roleList;
}
