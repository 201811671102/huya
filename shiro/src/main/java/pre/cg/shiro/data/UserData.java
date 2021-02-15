package pre.cg.shiro.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pre.cg.shiro.data.enpty.Role;
import pre.cg.shiro.data.enpty.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName UserData
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/14 20:40
 **/
public class UserData {

    public static  User getUser(){
        List<Role> roleList = new ArrayList<>();
        List<String> permission = new ArrayList<>();
        permission.add("insert");
        permission.add("delete");
        permission.add("update");
        permission.add("selete");
        roleList.add(new Role("user",permission));
        return new User("123","63eed8a1ccd67b88efc8f20d8c10ff19","2298667b83cdc6edbbeb23db1cec9b2e",roleList);
    }
    public static User getManage(){
        List<Role> roleList = new ArrayList<>();
        List<String> permission = new ArrayList<>();
        permission.add("insert");
        permission.add("delete");
        permission.add("update");
        permission.add("selete");
        roleList.add(new Role("manager",permission));
        return new User("456","edbc26e4be2a71e790626a1c3885016d","a5f2b179c31f30dfbdb9ee8017e3a362",roleList);
    }
    public static User getCustem(){
        List<Role> roleList = new ArrayList<>();
        List<String> permission = new ArrayList<>();
        permission.add("insert");
        permission.add("delete");
        permission.add("update");
        permission.add("selete");
        roleList.add(new Role("custem",permission));
        return new User("789","bc14abda185b9058ca0012cecfed267a","a229d3b50313693ea679d2c46a57d5ce",roleList);
    }
}
