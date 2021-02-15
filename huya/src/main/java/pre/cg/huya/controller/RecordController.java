package pre.cg.huya.controller;

import com.mysql.jdbc.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pre.cg.huya.base.Code;
import pre.cg.huya.base.ResultUtil;
import pre.cg.huya.base.dto.ResultDTO;
import pre.cg.huya.pojo.PlayInfo;
import pre.cg.huya.pojo.Record;
import pre.cg.huya.service.PlayInfoService;
import pre.cg.huya.service.RecordService;
import pre.cg.huya.websocket.manage.RoomManager;

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
    @Autowired
    private PlayInfoService playInfoService;

    @PostMapping("/addRecord")
    @ResponseBody
    @ApiOperation(value = "添加记录")
    public ResultDTO addRecord(
            @ApiParam(value = "用户id",required = true)@RequestParam(value = "uid",required = true)String uid,
            @ApiParam(value = "头像",required = true)@RequestParam(value = "phone",required = true)String phone,
            @ApiParam(value = "名字",required = true)@RequestParam(value = "name",required = true)String name,
            @ApiParam(value = "分数",required = true)@RequestParam(value = "score",required = true)Integer score){
        try {
            PlayInfo playInfo = playInfoService.selectInfo(uid);
            if (playInfo == null || StringUtils.isNullOrEmpty(playInfo.getPhone())){
                playInfo = new PlayInfo();
                playInfo.setName(name);
                playInfo.setPhone(phone);
                playInfo.setUid(uid);
                playInfoService.insertInfo(playInfo);
            }else{
                Record record = new Record();
                record.setScore(score);
                record.setUid(uid);
                recordService.insertNew(record);
            }
            return ResultUtil.success();
        }catch (Exception e){
            return ResultUtil.error(Code.ERROR.code,e.toString());
        }
    }
    @GetMapping("/getAll")
    @ResponseBody
    @ApiOperation(value = "查询全部记录，根据分数高到低排序")
    public ResultDTO getAll(){

        try {
            return ResultUtil.success(recordService.selectAll());
        }catch (Exception e){
            return ResultUtil.error(Code.ERROR.code,e.toString());
        }
    }

    @GetMapping("/getPage")
    @ResponseBody
    @ApiOperation(value = "分页查找")
    public ResultDTO getPage( @ApiParam(value = "页码",required = true)@RequestParam(value = "page",required = true)Integer page,
                              @ApiParam(value = "条数",required = true)@RequestParam(value = "size",required = true)Integer size){
        try {
            return ResultUtil.success(recordService.selectPage(page,size));
        }catch (Exception e){
            return ResultUtil.error(Code.ERROR.code,e.toString());
        }
    }

    @GetMapping("/getOne")
    @ResponseBody
    @ApiOperation(value = "用户id查找,rid为排名",notes = "400 没有该用户名")
    public ResultDTO getOne(
            @ApiParam(value = "用户id",required = true)@RequestParam(value = "uid",required = true)String uid){
        try {
            Record record = recordService.selectUid(uid);
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
                return ResultUtil.success(record);
            }else{
                return ResultUtil.error(Code.FAil.code,"没有该用户");
            }
        }catch (Exception e){
            return ResultUtil.error(Code.ERROR.code,e.toString());
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
        try {
            room.put("观众信息",playInfoService.selectAll());
        }catch (Exception e){
            return ResultUtil.error(Code.ERROR.code,e.getMessage());
        }
        return ResultUtil.success(room);
    }

}
