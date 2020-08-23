package pre.cg.huya.service;

import pre.cg.huya.pojo.Record;

import java.util.List;



public interface RecordService {
    /*添加*/
    int insertNew(Record record)throws Exception;
    /*查询*/
    List<Record> selectAll()throws Exception;
    /*分页查询*/
    List<Record> selectPage(Integer page,Integer size)throws Exception;
    /*虎牙id查询*/
    Record selectHuyaId(Integer huyaId)throws  Exception;
}
