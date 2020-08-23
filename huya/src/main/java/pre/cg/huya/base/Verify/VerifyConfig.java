package pre.cg.huya.base.Verify;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.mysql.jdbc.StringUtils;


import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Properties;

/**
 * @ClassName VerifyConfig
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/10 10:03
 **/
public class VerifyConfig {

    public static final VerifyConfig verrifyConfig = new VerifyConfig();
    public static VerifyConfig getInstance(){return  verrifyConfig;}
    private VerifyConfig(){}

    public DefaultKaptcha getKaptcha(VerifyPojo verifyPojo){
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        if (verifyPojo!=null){
            /*遍历配置类*/
            Class cs = verifyPojo.getClass();
            Field[] fields = cs.getDeclaredFields();
            Arrays.stream(fields).forEach(field -> {
                Field field1 = field;
                field1.setAccessible(true);
                try {
                    properties.setProperty(field1.getName().replace("_","."), (String) field1.get(verifyPojo));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        }
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
