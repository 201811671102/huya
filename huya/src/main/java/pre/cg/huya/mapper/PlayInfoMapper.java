package pre.cg.huya.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;
import pre.cg.huya.pojo.PlayInfo;
import pre.cg.huya.pojo.PlayInfoExample;
@Component
public interface PlayInfoMapper {
    long countByExample(PlayInfoExample example);

    int deleteByExample(PlayInfoExample example);

    int deleteByPrimaryKey(String uid);

    int insert(PlayInfo record);

    int insertSelective(PlayInfo record);

    List<PlayInfo> selectByExampleWithRowbounds(PlayInfoExample example, RowBounds rowBounds);

    List<PlayInfo> selectByExample(PlayInfoExample example);

    PlayInfo selectByPrimaryKey(String uid);

    int updateByExampleSelective(@Param("record") PlayInfo record, @Param("example") PlayInfoExample example);

    int updateByExample(@Param("record") PlayInfo record, @Param("example") PlayInfoExample example);

    int updateByPrimaryKeySelective(PlayInfo record);

    int updateByPrimaryKey(PlayInfo record);
}