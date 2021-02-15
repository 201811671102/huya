package pre.cg.ftp.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;
import pre.cg.ftp.pojo.Item;
import pre.cg.ftp.pojo.ItemExample;
@Component
public interface ItemMapper {
    long countByExample(ItemExample example);

    int deleteByExample(ItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Item record);

    int insertSelective(Item record);

    List<Item> selectByExampleWithRowbounds(ItemExample example, RowBounds rowBounds);

    List<Item> selectByExample(ItemExample example);

    Item selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Item record, @Param("example") ItemExample example);

    int updateByExample(@Param("record") Item record, @Param("example") ItemExample example);

    int updateByPrimaryKeySelective(Item record);

    int updateByPrimaryKey(Item record);
}