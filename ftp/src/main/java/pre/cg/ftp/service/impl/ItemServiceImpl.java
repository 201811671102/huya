package pre.cg.ftp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pre.cg.ftp.mapper.ItemMapper;
import pre.cg.ftp.pojo.Item;
import pre.cg.ftp.pojo.ItemExample;
import pre.cg.ftp.service.ItemService;

import java.util.List;

/**
 * @ClassName ItemServiceImpl
 * @Description TODO
 * @Author QQ163
 * @Date 2020/11/26 16:44
 **/
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    ItemMapper itemMapper;
    @Override
    public int insertItem(Item item) {
        return itemMapper.insertSelective(item);
    }

    @Override
    public int deleteItem(Integer id) {
        return itemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateItem(Item item) {
        return itemMapper.updateByPrimaryKeySelective(item);
    }

    @Override
    public Item selectOne(Integer id) {
        return itemMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Item> selectByClassifier(Integer classifier) {
        ItemExample itemExample = new ItemExample();
        itemExample.createCriteria().andClassifierEqualTo(classifier);
        return itemMapper.selectByExample(itemExample);
    }

    @Override
    public List<Item> selectByUid(Integer uid) {
        ItemExample itemExample = new ItemExample();
        itemExample.createCriteria().andUidEqualTo(uid);
        return itemMapper.selectByExample(itemExample);
    }
}
