package pre.cg.ftp.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import pre.cg.ftp.pojo.UserFile;
import pre.cg.ftp.pojo.UserFileExample;

public interface UserFileMapper {
    long countByExample(UserFileExample example);

    int deleteByExample(UserFileExample example);

    int deleteByPrimaryKey(Integer fid);

    int insert(UserFile record);

    int insertSelective(UserFile record);

    List<UserFile> selectByExampleWithRowbounds(UserFileExample example, RowBounds rowBounds);

    List<UserFile> selectByExample(UserFileExample example);

    UserFile selectByPrimaryKey(Integer fid);

    int updateByExampleSelective(@Param("record") UserFile record, @Param("example") UserFileExample example);

    int updateByExample(@Param("record") UserFile record, @Param("example") UserFileExample example);

    int updateByPrimaryKeySelective(UserFile record);

    int updateByPrimaryKey(UserFile record);
}