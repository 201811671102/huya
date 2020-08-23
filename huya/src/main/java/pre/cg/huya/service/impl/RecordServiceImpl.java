package pre.cg.huya.service.impl;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pre.cg.huya.mapper.RecordMapper;
import pre.cg.huya.pojo.Record;
import pre.cg.huya.pojo.RecordExample;
import pre.cg.huya.redis.RedisUtil;
import pre.cg.huya.service.RecordService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName RecordServiceImpl
 * @Description TODO
 * @Author QQ163
 * @Date 2020/6/15 14:35
 **/
@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    RedisUtil redisUtil;


    @Override
    public int insertNew(Record record) throws Exception {
        RecordExample recordExample = new RecordExample();
        recordExample.createCriteria().andNameEqualTo(record.getName());
        Record record1 = null;
        redisUtil.set("huyaRecird"+record.getRid(),record);
       try {
           record1 = recordMapper.selectByExample(recordExample).get(0);
           record.setRid(record1.getRid());
           return recordMapper.updateByPrimaryKeySelective(record);
       }catch (IndexOutOfBoundsException e){}
        return recordMapper.insertSelective(record);
    }

    @Override
    public List<Record> selectAll() throws Exception {
        RecordExample recordExample = new RecordExample();
        recordExample.createCriteria().andRidIsNotNull();
        recordExample.setOrderByClause("score DESC");
        return recordMapper.selectByExample(recordExample);
    }

    @Override
    public List<Record> selectPage(Integer page, Integer size) throws Exception {
        RecordExample recordExample = new RecordExample();
        recordExample.createCriteria()
                .andRidIsNotNull();
        recordExample.setOrderByClause("score DESC");
        RowBounds rowBounds = new RowBounds(page*size,size);
        return recordMapper.selectByExampleWithRowbounds(recordExample,rowBounds);
    }

    @Override
    public Record selectHuyaId(Integer huyaId) throws Exception {
        RecordExample recordExample = new RecordExample();
        recordExample.createCriteria().andHuyaidEqualTo(huyaId);
        try {
            if (redisUtil.hasKey("huyaRecird"+huyaId)){
                return (Record) redisUtil.get("huyaRecird"+huyaId);
            }else{
                Record record = recordMapper.selectByExample(recordExample).get(0);
                redisUtil.set("huyaRecird"+huyaId,record);
                return record;
            }
        }catch (IndexOutOfBoundsException e){
            return null;
        }
    }
}
