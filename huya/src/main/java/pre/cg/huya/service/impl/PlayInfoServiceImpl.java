package pre.cg.huya.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pre.cg.huya.mapper.PlayInfoMapper;
import pre.cg.huya.pojo.PlayInfo;
import pre.cg.huya.pojo.PlayInfoExample;
import pre.cg.huya.service.PlayInfoService;

import java.util.List;

/**
 * @ClassName PlayInfoServiceImpl
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/25 21:33
 **/
@Service
public class PlayInfoServiceImpl implements PlayInfoService {
    @Autowired
    private PlayInfoMapper playInfoMapper;

    @CachePut(value = "playinfo" ,key="#playInfo.uid")
    @Override
    public void insertInfo(PlayInfo playInfo) throws Exception {
        try {
            playInfoMapper.insertSelective(playInfo);
        }catch (Exception e){
            throw e;
        }
    }
    @Cacheable(value = "playinfo" ,key="#playInfo.uid")
    @Override
    public PlayInfo selectInfo(String uid) throws Exception {
        try {
            PlayInfo playInfo = playInfoMapper.selectByPrimaryKey(uid);
            return playInfo == null? null:playInfo;
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<PlayInfo> selectAll() throws Exception {
        try {
            PlayInfoExample playInfoExample = new PlayInfoExample();
            playInfoExample.createCriteria().andUidIsNotNull();
            return playInfoMapper.selectByExample(playInfoExample);
        }catch (Exception e){
            throw e;
        }
    }
}
