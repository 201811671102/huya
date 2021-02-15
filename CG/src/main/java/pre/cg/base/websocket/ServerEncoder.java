package pre.cg.base.websocket;

import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @ClassName ServerEncoder
 * @Description TODO
 * @Author QQ163
 * @Date 2020/3/25 17:29
 **/
@Log4j2
public class ServerEncoder implements Encoder.Text<WebsocketBase> {
    @Override
    public String encode(WebsocketBase websocketBase) throws EncodeException {
        try {
            return JSON.toJSONString(websocketBase);
        }catch (Exception e){
            log.info(e.toString());
            return null;
        }
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
            EndpointConfig ec;
    }

    @Override
    public void destroy() {

    }
}
