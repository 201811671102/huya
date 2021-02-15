package pre.cg.ftp.service;

import pre.cg.ftp.pojo.Item;

import java.util.List;

public interface ItemService {
    int insertItem(Item item);
    int deleteItem(Integer id);
    int updateItem(Item item);
    Item selectOne(Integer id);
    List<Item> selectByClassifier(Integer classifier);
    List<Item> selectByUid(Integer uid);
}
