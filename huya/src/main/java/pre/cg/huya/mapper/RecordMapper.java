package pre.cg.huya.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;
import pre.cg.huya.pojo.Record;
import pre.cg.huya.pojo.RecordExample;
@Component
public interface RecordMapper {
    long countByExample(RecordExample example);

    int deleteByExample(RecordExample example);

    int deleteByPrimaryKey(Integer rid);

    int insert(Record record);

    int insertSelective(Record record);

    List<Record> selectByExampleWithRowbounds(RecordExample example, RowBounds rowBounds);

    List<Record> selectByExample(RecordExample example);

    Record selectByPrimaryKey(Integer rid);

    int updateByExampleSelective(@Param("record") Record record, @Param("example") RecordExample example);

    int updateByExample(@Param("record") Record record, @Param("example") RecordExample example);

    int updateByPrimaryKeySelective(Record record);

    int updateByPrimaryKey(Record record);
}