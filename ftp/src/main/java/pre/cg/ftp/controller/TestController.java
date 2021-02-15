package pre.cg.ftp.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pre.cg.ftp.base.DTO.ResultDTO;
import pre.cg.ftp.base.ResultUtils;
import pre.cg.ftp.base.code.Code;

/**
 * @ClassName TestController
 * @Description TODO
 * @Author QQ163
 * @Date 2020/9/6 13:42
 **/
@RequestMapping("/test")
@RestController
@Api(value = "测试")
public class TestController {

    @RequestMapping(value = "/user/{uid}",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查")
    public ResultDTO<String> selectUser(
            @PathVariable
            @ApiParam(value = "用户id",required = true) String uid
    ){
        return ResultUtils.success("select");
    }

    @ResponseBody
    @RequestMapping(value = "/user/{uid}",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "增")
    public ResultDTO<String> addUser(
            @PathVariable
            @ApiParam(value = "用户id",required = true) String uid
    ){
        return ResultUtils.success("add");
    }

    @ResponseBody
    @RequestMapping(value = "/user/{uid}",method = RequestMethod.PUT)
    @ApiOperation(value = "改")
    public ResultDTO<String> updateUser(
            @PathVariable
            @ApiParam(value = "用户id",required = true) String uid
    ){
        return ResultUtils.success("update");
    }
    @ResponseBody
    @RequestMapping(value = "/user/{uid}",method = RequestMethod.DELETE)
    @ApiOperation(value = "删")
    public ResultDTO<String> deleteUser(
            @PathVariable
            @ApiParam(value = "用户id",required = true)String uid
    ){
        return ResultUtils.success("delete");
    }



    @RequestMapping(value = "/user",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查")
    public ResultDTO<String> selectUserTwo(@ApiParam(value = "用户id",required = true) Integer uid){
        return ResultUtils.success("select");
    }

    @ResponseBody
    @RequestMapping(value = "/user",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "增")
    public ResultDTO<String> addUserTwo(@ApiParam(value = "用户id",required = true) Integer uid){
        return ResultUtils.success("add");
    }


    @RequestMapping(value = "/file",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "上传文件",notes = "400 上传失败")
    public ResultDTO<String> upfile(
            @ApiParam(value = "照片",required = true)@RequestParam(value = "photo",required = true) MultipartFile photo) {
        if (photo!=null && !photo.isEmpty()){
            return ResultUtils.success("success");
        }
        return ResultUtils.error(Code.ERROR.code,"fail");
    }

    @RequestMapping(value = "/fileTwo",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "上传文件",notes = "400 上传失败")
    public ResultDTO<String> fileTwo(
            @ApiParam(value = "类型",required = true)@RequestParam(value = "type",required = true) String type,
            @ApiParam(value = "照片",required = true)@RequestParam(value = "photo",required = true) MultipartFile photo) {
        if (photo!=null && !photo.isEmpty()){
            return ResultUtils.success("success");
        }
        return ResultUtils.error(Code.ERROR.code,"fail");
    }


}
