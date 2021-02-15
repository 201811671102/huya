package pre.cg.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import pre.cg.pojo.Prod;
import pre.cg.pojo.ProdExample;
@Component
public interface ProdMapper {
    int deleteByExample(ProdExample example);

    int deleteByPrimaryKey(Integer prodId);

    int insert(Prod record);

    int insertSelective(Prod record);

    List<Prod> selectByExample(ProdExample example);

    Prod selectByPrimaryKey(Integer prodId);

    int updateByExampleSelective(@Param("record") Prod record, @Param("example") ProdExample example);

    int updateByExample(@Param("record") Prod record, @Param("example") ProdExample example);

    int updateByPrimaryKeySelective(Prod record);

    int updateByPrimaryKey(Prod record);
}