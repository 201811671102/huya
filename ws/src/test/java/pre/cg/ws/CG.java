package pre.cg.ws;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pre.cg.ws.base.Packet;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName CG
 * @Description TODO
 * @Author QQ163
 * @Date 2020/7/31 11:22
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WsApplication.class)
public class CG {

    public  static void  main(String[] args) throws UnsupportedEncodingException {
//        Packet packet = new Packet(1000,"陈干");
//        System.out.println(JSONObject.toJSONString(packet));
        //System.out.println(new String("unDs7a/GJvZkYBouYtkDQgIPO4bMmNuxtx".getBytes("gbk"), "utf-8"));
  //      String name = "灏忕尓闄堝嚡";
        String name = "hy_78219730";
        try {
            name = new String(name.getBytes("utf-8"), "gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("-------------"+name);
    }
}
