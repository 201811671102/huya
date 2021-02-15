package pre.cg.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.params.SolrParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pre.cg.base.ResultUtil;
import pre.cg.base.dto.ResultDTO;

import pre.cg.mapper.UserMapper;
import pre.cg.pojo.Prod;
import tk.mybatis.mapper.util.StringUtil;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * 一个用来测试swagger注解的控制器
 *
 */
@Controller
@RequestMapping("/say")
@Api(value = "SayController|一个用来测试swagger注解的控制器",tags = {"说明","说明"})
public class SayController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SolrClient solrClient;

    @ResponseBody
    @PostMapping("/getUsername")
    @ApiOperation(
            value = "根据用户编号获取用户姓名",     //value : 方法描述
            notes = "test:仅1和2有正确返回",       //notes : 提示内容
            tags = {"根据用户编号获取用户姓名copy"}) //tags  : 重新分组
    @ApiImplicitParam(
            paramType = "query",    //指定参数放在哪个地方
            name = "userNumber",    //重写属性名字
            value = "用户编号",       //字段说明
            required = true,        //是否必填
            dataType = "Integer") //数据类型
    public ResultDTO getUserName(@RequestParam Integer userNumber){
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("* : *");
        List<Prod> prodList = null;
        try {
            prodList = solrClient.query(solrQuery).getBeans(Prod.class);
            return new ResultUtil().Success(prodList);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResultUtil().Success(prodList);
       /* if(userNumber == 1){
            return new ResultUtil().Success("张三丰");
        }
        else if(userNumber == 2){
            return new ResultUtil().Success("慕容复");
        }
        else{
            return new ResultUtil().Error("404","查无此人");
        }*/
    }

    @ResponseBody
    @PostMapping("/updatePassword")
    @ApiOperation(value = "修改用户密码", notes = "根据用户id修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "userId",value = "用户编号",required = true,dataType = "Integer"),
            @ApiImplicitParam(paramType = "query",name = "password",value = "用户密码",required = true,dataType = "Integer"),
            @ApiImplicitParam(paramType = "query",name = "newPassword",value = "用户新密码",required = true,dataType = "Integer")
    })
    public String updatePassword(
            @RequestParam(value = "userId") Integer userId,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "newPassword") String newPassword){
        if(userId <= 0 || userId > 2){
            return "未知的用户";
        }
        if(StringUtil.isEmpty(password) || StringUtil.isEmpty(newPassword)){
            return "密码不能为空";
        }
        if(password.equals(newPassword)){
            return "新旧密码不能相同";
        }
        return "密码修改成功!";

    }
}
