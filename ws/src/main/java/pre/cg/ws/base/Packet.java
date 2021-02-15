package pre.cg.ws.base;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName Packet
 * @Description TODO
 * @Author QQ163
 * @Date 2020/7/21 21:46
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Packet {
    private int uri;
    private Object body;

    public static String forSend(Packet packet) throws UnsupportedEncodingException {
      return  new String(JSONObject.toJSONString(packet).getBytes(),"utf-8");
    }

    public static Packet forPacket(String message){
        return JSONObject.parseObject(message,Packet.class);
    }
}
