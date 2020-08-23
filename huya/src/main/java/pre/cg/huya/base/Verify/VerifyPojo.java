package pre.cg.huya.base.Verify;

import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.impl.WaterRipple;
import com.google.code.kaptcha.util.Configurable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName VerifyConfig
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/10 9:42
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyPojo {
    /*是否使用边框 true*/
    private String  kaptcha_border ="yes";
    /*边框颜色 r,g,b(and optional alpha) 或者 white,black,blue*/
    private String kaptcha_border_color="black";
    /*边框厚度*/
    private String kaptcha_border_thickness="1";
    /*图片宽度*/
    private String kaptcha_image_width="200";
    /*图片高度*/
    private String kaptcha_image_height="50";

    /*验证码字体大小*/
    private String kaptcha_textproducer_font_size="40";
    /*验证码字体颜色 r,g,b 或者 white,black,blue*/
    private String kaptcha_textproducer_font_color="black";
    /*验证码字体格式*/
    private String kaptcha_textproducer_font_names="Arial, Courier";
    /*字体间隔*/
    private String kaptcha_textproducer_char_space="2";

    /*验证码保存在session的key*/
    private String kaptcha_session_key = "code";

    /*验证码字符长度*/
    private String kaptcha_textproducer_char_length="5";
    /*验证码取值范围*/
    private String kaptcha_textproducer_char_string="1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";

    /*干扰线颜色 r,g,b 或者 white,black,blue*/
    private String kaptcha_noise_color="black";

    /*图片样式 com.google.code.kaptcha.impl.ShadowGimpy
    * 水纹 WaterRipple
    * 鱼眼 FishEyeGimpy
    * 阴影 ShadowGimpy
    * */
    private String kaptcha_obscurificator_impl="com.google.code.kaptcha.impl.WaterRipple";

    /*背景颜色渐变 开始*/
    private String kaptcha_background_clear_from="light grey";
    /*背景颜色渐变 结束*/
    private String kaptcha_background_clear_to="white";

}
