package pre.cg.huya.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pre.cg.huya.base.ResultUtil;
import pre.cg.huya.base.dto.ResultDTO;
import pre.cg.huya.pojo.Record;
import pre.cg.huya.service.RecordService;
import pre.cg.huya.websocket.manage.RoomManager;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName RecordController
 * @Description TODO
 * @Author QQ163
 * @Date 2020/6/15 15:09
 **/
@Controller
@Api(value = "分数记录controler")
@RequestMapping("/record")
@Log4j2
public class RecordController {

    @Autowired
    private RecordService recordService;

    @PostMapping("/addRecord")
    @ResponseBody
    @ApiOperation(value = "添加记录")
    public ResultDTO addRecord(
            @ApiParam(value = "虎牙id",required = true)@RequestParam(value = "huyaId",required = true)Integer huyaId,
            @ApiParam(value = "头像",required = true)@RequestParam(value = "phone",required = true)String phone,
            @ApiParam(value = "名字",required = true)@RequestParam(value = "name",required = true)String name,
            @ApiParam(value = "分数",required = true)@RequestParam(value = "score",required = true)Integer score){
        try {
            Record record = new Record();
            record.setName(name);
            record.setScore(score);
            record.setHuyaid(huyaId);
            record.setPhone(phone);
            Integer n = recordService.insertNew(record);
            if (n == -1){
                return ResultUtil.Error("500","增加错误");
            }
            return ResultUtil.Success(record);
        }catch (Exception e){
            return ResultUtil.Error("500",e.toString());
        }
    }
    @GetMapping("/getAll")
    @ResponseBody
    @ApiOperation(value = "查询全部记录，根据分数高到低排序")
    public ResultDTO getAll(){
        try {
            return ResultUtil.Success(recordService.selectAll());
        }catch (Exception e){
            return ResultUtil.Error("500",e.toString());
        }
    }

    @GetMapping("/getPage")
    @ResponseBody
    @ApiOperation(value = "分页查找")
    public ResultDTO getPage( @ApiParam(value = "页码",required = true)@RequestParam(value = "page",required = true)Integer page,
                              @ApiParam(value = "条数",required = true)@RequestParam(value = "size",required = true)Integer size){
        try {
            return ResultUtil.Success(recordService.selectPage(page,size));
        }catch (Exception e){
            return ResultUtil.Error("500",e.toString());
        }
    }

    @GetMapping("/getOne")
    @ResponseBody
    @ApiOperation(value = "虎牙id查找,rid为排名",notes = "400 没有该用户名")
    public ResultDTO getOne( @ApiParam(value = "虎牙id",required = true)@RequestParam(value = "huyaId",required = true)Integer huyaId){
        try {
            Record record = recordService.selectHuyaId(huyaId);
            if (record != null){
                List<Record> recordList = recordService.selectAll();
                record.setRid(1);
                recordList.stream()
                        .collect(Collectors.groupingBy(Record::getScore))
                        .entrySet().stream()
                        .sorted((e1,e2) -> Integer.compare(e2.getKey(),e1.getKey()))
                        .anyMatch((e) ->{
                            if (e.getKey().equals(record.getScore())){
                                    return true;
                            }else{
                                record.setRid(record.getRid()+e.getValue().size());
                            }
                            return false;
                        });
                return ResultUtil.Success(record);
            }else{
                return ResultUtil.Error("400","没有该用户");
            }
        }catch (Exception e){
            return ResultUtil.Error("500",e.toString());
        }
    }

    @PostMapping("/getOnePost")
    @ResponseBody
    @ApiOperation(value = "虎牙id查找,rid为排名",notes = "400 没有该用户名")
    public ResultDTO getOnePost(
            @ApiParam(value = "虎牙id",required = true)@RequestParam(value = "huyaId",required = true)Integer huyaId){
        try {
            Record record = recordService.selectHuyaId(huyaId);
            if (record != null){
                List<Record> recordList = recordService.selectAll();
                record.setRid(1);
                recordList.stream()
                        .collect(Collectors.groupingBy(Record::getScore))
                        .entrySet().stream()
                        .sorted((e1,e2) -> Integer.compare(e2.getKey(),e1.getKey()))
                        .anyMatch((e) ->{
                            if (e.getKey().equals(record.getScore())){
                                return true;
                            }else{
                                record.setRid(record.getRid()+e.getValue().size());
                            }
                            return false;
                        });
                return ResultUtil.Success(record);
            }else{
                return ResultUtil.Error("400","没有该用户");
            }
        }catch (Exception e){
            return ResultUtil.Error("500",e.toString());
        }
    }
    @GetMapping("/room")
    @ResponseBody
    @ApiOperation(value = "获取房间")
    public ResultDTO getRoom(){
        Map<String,Object> room = new Hashtable<>();
        room.put("房间号和主播id",RoomManager.getInstance().getRoomIdProfileId());
        room.put("房间号和观众id",RoomManager.getInstance().getRoomIdUid());
        room.put("solo 房间号到主播id",RoomManager.getInstance().getSolrRoom());
        room.put("等待房间 房间号和观众id",RoomManager.getInstance().getRoomIdUidWait());
        return ResultUtil.Success(room);
    }

}
