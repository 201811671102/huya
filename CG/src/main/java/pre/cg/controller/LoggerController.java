package pre.cg.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pre.cg.base.ResultUtil;
import pre.cg.base.dto.ResultDTO;


/**
 * @auto CG
 * 测试logging
 * @date 2019/12/31 15:35
 */
@Controller
@RequestMapping("/logging")
@Slf4j
@Api(value = "logging|一个用来测试logging的控制器",tags = {"说明","说明"})
public class LoggerController {
    @ResponseBody
    @PostMapping("/fileUploadController")
    @ApiOperation(
            value = "测试logger",     //value : 方法描述
            notes = "测试logger，无实际用途")   //notes : 提示内容)
    public ResultDTO fileUpload(){
        //日志级别低到高
        log.trace("这是trace日志");
        log.debug("这是debug日志");
        //springboot默认使用info级别，没有指定级别的使用springboot的默认级别 root级别
        log.info("这是info日志");
        log.warn("这是warn日志");
        log.error("这是error日志");
       return new ResultUtil().SUccess();
    }
}
