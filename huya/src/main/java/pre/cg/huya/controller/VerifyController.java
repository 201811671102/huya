package pre.cg.huya.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.impl.FishEyeGimpy;
import com.google.code.kaptcha.impl.ShadowGimpy;
import com.google.code.kaptcha.impl.WaterRipple;
import com.mysql.jdbc.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pre.cg.huya.base.ResultUtil;
import pre.cg.huya.base.Verify.VerifyConfig;
import pre.cg.huya.base.Verify.VerifyPojo;
import pre.cg.huya.base.dto.ResultDTO;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @ClassName VerifyController
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/10 10:15
 **/
@Controller
@Api(value = "验证码")
@RequestMapping("verify")
public class VerifyController {
    /*配置验证码*/
    @PostMapping("verifyConfig")
    @ResponseBody
    @ApiOperation(value = "配置验证码 不需要带单位 rbg以 110,100,200  形式字符串")
    public ResultDTO verifyConfig(
        @ApiParam(value = "是否使用边框 yes no",required = false)@RequestParam(value = "kaptcha_border",required = false)String kaptcha_border,
        @ApiParam(value = "边框颜色 r,g,b(and optional alpha) 或者 white,black,blue",required = false)@RequestParam(value = "kaptcha_border_color",required = false)String kaptcha_border_color,
        @ApiParam(value = "边框厚度 px",required = false)@RequestParam(value = "kaptcha_border_thickness",required = false)String kaptcha_border_thickness,
        @ApiParam(value = "图片宽度 px",required = false)@RequestParam(value = "kaptcha_image_width",required = false)String kaptcha_image_width,
        @ApiParam(value = "图片高度 px",required = false)@RequestParam(value = "kaptcha_image_height",required = false)String kaptcha_image_height,
        @ApiParam(value = "背景颜色渐变 开始",required = false)@RequestParam(value = "kaptcha_background_clear_from",required = false)String kaptcha_background_clear_from,
        @ApiParam(value = "背景颜色渐变 结束",required = false)@RequestParam(value = "kaptcha_background_clear_to",required = false)String kaptcha_background_clear_to,
        @ApiParam(value = "验证码字体大小 px",required = false)@RequestParam(value = "kaptcha_textproducer_font_size",required = false)String kaptcha_textproducer_font_size,
        @ApiParam(value = "验证码字体颜色 r,g,b 或者 white,black,blue",required = false)@RequestParam(value = "kaptcha_textproducer_font_color",required = false)String kaptcha_textproducer_font_color,
        @ApiParam(value = "验证码字体格式",required = false)@RequestParam(value = "kaptcha_textproducer_font_names",required = false)String kaptcha_textproducer_font_names,
        @ApiParam(value = "字体间隔",required = false)@RequestParam(value = "kaptcha_textproducer_char_space",required = false)String kaptcha_textproducer_char_space,
        @ApiParam(value = "验证码字符长度 默认5",required = false)@RequestParam(value = "kaptcha_textproducer_char_length",required = false)String kaptcha_textproducer_char_length,
        @ApiParam(value = "验证码取值范围",required = false)@RequestParam(value = "kaptcha_textproducer_char_string",required = false)String kaptcha_textproducer_char_string,
        @ApiParam(value = "干扰线颜色 r,g,b 或者 white,black,blue",required = false)@RequestParam(value = "kaptcha_noise_color",required = false)String kaptcha_noise_color,
        @ApiParam(value = "图片样式 水纹：WaterRipple 鱼眼 FishEyeGimpy 阴影 ShadowGimpy",required = false)@RequestParam(value = "kaptcha_obscurificator_impl",required = false)String kaptcha_obscurificator_impl
    ){
        try {
            VerifyPojo verifyPojo = new VerifyPojo();
            switch (StringUtils.isNullOrEmpty(kaptcha_obscurificator_impl)?"WaterRipple":kaptcha_obscurificator_impl){
                case "WaterRipple": verifyPojo.setKaptcha_obscurificator_impl("com.google.code.kaptcha.impl.WaterRipple");break;
                case "FishEyeGimpy": verifyPojo.setKaptcha_obscurificator_impl("com.google.code.kaptcha.impl.FishEyeGimpy");break;
                case "ShadowGimpy": verifyPojo.setKaptcha_obscurificator_impl("com.google.code.kaptcha.impl.ShadowGimpy");break;
                default:verifyPojo.setKaptcha_obscurificator_impl("com.google.code.kaptcha.impl.WaterRipple");
            }
            if (!StringUtils.isNullOrEmpty(kaptcha_border))
                verifyPojo.setKaptcha_border(kaptcha_border);
            if (!StringUtils.isNullOrEmpty(kaptcha_border_color))
                verifyPojo.setKaptcha_border_color(kaptcha_border_color);
            if (!StringUtils.isNullOrEmpty(kaptcha_border_thickness))
                verifyPojo.setKaptcha_border_thickness(kaptcha_border_thickness);
            if (!StringUtils.isNullOrEmpty(kaptcha_image_width))
                verifyPojo.setKaptcha_image_width(kaptcha_image_width);
            if (!StringUtils.isNullOrEmpty(kaptcha_image_height))
                verifyPojo.setKaptcha_image_height( kaptcha_image_height);
            if (!StringUtils.isNullOrEmpty(kaptcha_image_height))
                verifyPojo.setKaptcha_background_clear_from(kaptcha_background_clear_from);
            if (!StringUtils.isNullOrEmpty(kaptcha_image_height))
                verifyPojo.setKaptcha_background_clear_to(kaptcha_background_clear_to);
            if (!StringUtils.isNullOrEmpty(kaptcha_textproducer_font_size))
                verifyPojo.setKaptcha_textproducer_font_size(kaptcha_textproducer_font_size);
            if (!StringUtils.isNullOrEmpty(kaptcha_textproducer_font_color))
                verifyPojo.setKaptcha_textproducer_font_color(kaptcha_textproducer_font_color);
            if (!StringUtils.isNullOrEmpty(kaptcha_textproducer_font_names))
                verifyPojo.setKaptcha_textproducer_font_names(kaptcha_textproducer_font_names);
            if (!StringUtils.isNullOrEmpty(kaptcha_textproducer_char_space))
                verifyPojo.setKaptcha_textproducer_char_space(kaptcha_textproducer_char_space);
            if (!StringUtils.isNullOrEmpty(kaptcha_textproducer_char_length))
                verifyPojo.setKaptcha_textproducer_char_length(kaptcha_textproducer_char_length);
            if (!StringUtils.isNullOrEmpty(kaptcha_textproducer_char_string))
                verifyPojo.setKaptcha_textproducer_char_string(kaptcha_textproducer_char_string);
            if (!StringUtils.isNullOrEmpty(kaptcha_noise_color))
                verifyPojo.setKaptcha_noise_color(kaptcha_noise_color);
            return ResultUtil.Success(verifyPojo);
        }catch (Exception e){
            return ResultUtil.Error("500",e.getMessage());
        }
    }

    /*生成验证码图像*/
    @GetMapping("verifyImage")
    @ApiOperation(value = "生成验证码图像")
    public void verifyImage(
            HttpServletRequest request, HttpServletResponse response,
            @ApiParam(value = "验证码配置",required = false)@RequestParam(value = "verifyPojo",required = false)VerifyPojo verifyPojo) throws IOException {
        ServletOutputStream servletOutputStream = response.getOutputStream();
        try {
            DefaultKaptcha defaultKaptcha = VerifyConfig.getInstance().getKaptcha(verifyPojo);
            byte[] captchaChallengeAsJpeg;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            /*生成验证码字符串保存到session*/
            String createText = defaultKaptcha.createText();
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("code",createText);
            /*使用生成验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中*/
            BufferedImage bufferedImage = defaultKaptcha.createImage(createText);
            ImageIO.write(bufferedImage,"jpg",byteArrayOutputStream);
            /**
             * 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
             */
            captchaChallengeAsJpeg = byteArrayOutputStream.toByteArray();
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires",0);
            response.setContentType("image/jpeg");
            servletOutputStream.write(captchaChallengeAsJpeg);
            servletOutputStream.flush();
        }catch (Exception e){
            response.setStatus(404);
        }finally {
            servletOutputStream.close();
        }
    }

    /*校验验证码*/
    @PostMapping("verifyCheck")
    @ResponseBody
    @ApiOperation(value = "校验验证码",notes = "400 没有生成验证码 404 验证码错误")
    public ResultDTO verifyCheck(
            HttpServletRequest request,
            @ApiParam(value = "验证码",required = true)@RequestParam(value = "code",required = true)String code
    ){
        try {
            HttpSession session = request.getSession();
            String rightcode = (String) session.getAttribute("code");
            if (StringUtils.isNullOrEmpty(rightcode)){
                return ResultUtil.Error("400","没有验证码");
            }else{
                if (rightcode.equals(code)){
                    return ResultUtil.Success();
                }else{
                    return ResultUtil.Error("404","验证码错误");
                }
            }
        }catch (Exception e){
            return ResultUtil.Error("500",e.getMessage());
        }

    }

}
