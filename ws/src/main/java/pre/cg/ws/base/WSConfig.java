package pre.cg.ws.base;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


/**
 * @ClassName config
 * @Description TODO
 * @Author QQ163
 * @Date 2020/7/21 18:36
 **/
@Data
public class WSConfig {

    public static final Integer port = 9091;
    public static final String secretKey = "65f3285847dfeac7252f73d980010105";
}
