package pre.cg.ftp.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import pre.cg.ftp.base.DTO.ResultDTO;
import pre.cg.ftp.base.ResultUtils;
import pre.cg.ftp.dto.ItemUserDTO;
import pre.cg.ftp.pojo.Item;
import pre.cg.ftp.pojo.User;
import pre.cg.ftp.service.UserService;
import pre.cg.ftp.service.impl.ItemServiceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName ItemController
 * @Description TODO
 * @Author QQ163
 * @Date 2020/11/26 16:48
 **/
@RequestMapping("/item")
@RestController
@Api(value = "item")
public class ItemController {

    @Autowired
    ItemServiceImpl itemService;
    @Autowired
    UserService userService;

    @RequestMapping(value = "/itemId/{id}",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "get itme")
    public ResultDTO<Item> itemOne(
            @ApiParam(value = "id",required = true)@PathVariable Integer id
    ) {
        return ResultUtils.success(itemService.selectOne(id));
    }

    @RequestMapping(value = "/itemClassifier/{classifier}",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "get itme")
    public ResultDTO<PageInfo<ItemUserDTO>> itemClassifier(
            @ApiParam(value = "classifier",required = true)@PathVariable Integer classifier,
            @ApiParam(value = "页码",required = true)@RequestParam(value = "offset",required = true,defaultValue = "1")Integer offset,
            @ApiParam(value = "每页条数",required = true)@RequestParam(value = "limit",required = true,defaultValue = "10")Integer limit
    ) {
        List<ItemUserDTO> itemUserDTOList = new ArrayList<>();
        PageHelper.startPage(offset, limit);
        List<Item> itemList = itemService.selectByClassifier(classifier);
        itemList.stream().forEach(item -> {
            User user;
            try {
                user = userService.getUser(item.getUid());
            } catch (Exception e) {
                itemList.remove(item);
                return;
            }
            ItemUserDTO itemUserDTO = new ItemUserDTO(item,user);
            itemUserDTOList.add(itemUserDTO);
        });
        PageInfo<ItemUserDTO> pageInfo = new PageInfo<ItemUserDTO>(itemUserDTOList);
        return ResultUtils.success(pageInfo);
    }

    @RequestMapping(value = "/item/{uid}",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "get itme")
    public ResultDTO<PageInfo<Item>> itemUid(
            @ApiParam(value = "用户id",required = true)@PathVariable(value = "uid",required = true)Integer uid,
            @ApiParam(value = "页码",required = true)@RequestParam(value = "offset",required = true,defaultValue = "1")Integer offset,
            @ApiParam(value = "每页条数",required = true)@RequestParam(value = "limit",required = true,defaultValue = "10")Integer limit
    ) {
        PageHelper.startPage(offset, limit);
        List<Item> itemList = itemService.selectByUid(uid);
        PageInfo<Item> pageInfo = new PageInfo<Item>(itemList);
        return ResultUtils.success(pageInfo);
    }

    @RequestMapping(value = "/item",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "get itme")
    public ResultDTO<Item> addItem(
            @ApiParam(value = "title",required = true)@RequestParam(value = "title",required = true)String title,
            @ApiParam(value = "uid",required = true)@RequestParam(value = "uid",required = true)Integer uid,
            @ApiParam(value = "classifier",required = true)@RequestParam(value = "classifier",required = true)Integer classifier,
            @ApiParam(value = "imagepath",required = true)@RequestParam(value = "imagepath",required = true)String imagepath,
            @ApiParam(value = "videopath",required = true)@RequestParam(value = "videopath",required = true)String videopath
    ) {
        Item item = new Item();
        item.setTitle(title);
        item.setClassifier(classifier);
        item.setImagepath(imagepath);
        item.setVideopath(videopath);
        item.setUid(uid);
        item.setUptime(new Date());
        itemService.insertItem(item);
        return ResultUtils.success(item);
    }

    @RequestMapping(value = "/item/{id}",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "get itme")
    public ResultDTO<Integer> deleteItem(
            @ApiParam(value = "id",required = true)@PathVariable Integer id
    ) {
        return ResultUtils.success(itemService.deleteItem(id));
    }

    @RequestMapping(value = "/item",method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "get itme")
    public ResultDTO<Integer> updateItem(
            @ApiParam(value = "item",required = true)Item item
    ) {
        return ResultUtils.success(itemService.updateItem(item));
    }
}
