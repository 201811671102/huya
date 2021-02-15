package pre.cg.shiro.shiro;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import pre.cg.shiro.data.enpty.User;

import java.sql.Statement;

/**
 * @ClassName PasswordEcord
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/14 21:21
 **/
public class PasswordEcord {
    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    public static final String ALGORITHM_NAME = "md5";//基础散列算法
    public static final int HASH_ITERATIONS = 2;//自定义散列次数

    public void encryptPassword(User user) {
        // 随机字符串作为salt因子，实际参与运算的salt我们还引入其它干扰因子
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        String newPassword = new SimpleHash(ALGORITHM_NAME, user.getPassword(),
                ByteSource.Util.bytes(user.getAccount()+user.getSalt()), HASH_ITERATIONS).toHex();
        user.setPassword(newPassword);
    }
}
