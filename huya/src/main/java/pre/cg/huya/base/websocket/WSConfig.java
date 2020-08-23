package pre.cg.huya.base.websocket;

import lombok.Data;
import org.springframework.stereotype.Component;


/**
 * @ClassName config
 * @Description TODO
 * @Author QQ163
 * @Date 2020/7/21 18:36
 **/
@Data
@Component
public class WSConfig {
/*    @Value("${ws.port}")
    private Integer port;
    @Value("${ws.secretKey}")
    private String secretKey;

    private static final WSConfig wsConfig = new WSConfig();
    public static WSConfig getInstance() {
        return wsConfig;
    }
    private WSConfig(){}*/

    public static final Integer port = 9091;
    public static final String secretKey = "65f3285847dfeac7252f73d980010105";
}
