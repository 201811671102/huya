package pre.cg.shiro.data.enpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName Role
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/14 20:42
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private String roleName;
    private List<String> permission;
}
