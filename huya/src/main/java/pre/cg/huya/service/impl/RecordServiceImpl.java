package pre.cg.huya.service.impl;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pre.cg.huya.mapper.RecordMapper;
import pre.cg.huya.pojo.Record;
import pre.cg.huya.pojo.RecordExample;
import pre.cg.huya.redis.RedisUtil;
import pre.cg.huya.service.RecordService;

import java.util.List;

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
    public void insertNew(Record record) throws Exception {
        RecordExample recordExample = new RecordExample();
        recordExample.createCriteria().andUidEqualTo(record.getUid());
        Record record1 = null;
        try {
            if (redisUtil.hasKey("huyaRecird" + record.getUid())) {
                redisUtil.del(record.getUid());
            }
            redisUtil.set("huyaRecird" + record.getUid(), record);
            if (recordMapper.selectByExample(recordExample)!=null){
                recordMapper.updateByExample(record,recordExample);
            }else{
                recordMapper.insertSelective(record);
            }
        }catch (Exception e){
            throw e;
        }
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
    public Record selectUid(String uid) throws Exception {
        RecordExample recordExample = new RecordExample();
        recordExample.createCriteria().andUidEqualTo(uid);
        try {
            if (redisUtil.hasKey("huyaRecird"+uid)){
                return (Record) redisUtil.get("huyaRecird"+uid);
            }else{
                Record record = recordMapper.selectByExample(recordExample).get(0);
                redisUtil.set("huyaRecird"+uid,record);
                return record;
            }
        }catch (IndexOutOfBoundsException e){
            return null;
        }
    }
}
