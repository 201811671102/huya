package pre.cg.ftp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pre.cg.ftp.mapper.UserFileMapper;
import pre.cg.ftp.pojo.UserFile;
import pre.cg.ftp.pojo.UserFileExample;
import pre.cg.ftp.service.FileService;

import java.util.List;

/**
 * @ClassName FileServiceImpl
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/19 0:26
 **/
@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private UserFileMapper userFileMapper;


    @Override
    public int addFile(UserFile file) throws Exception {
        return userFileMapper.insertSelective(file);
    }

    @Override
    public UserFile getFile(Integer fid) throws Exception {
        return userFileMapper.selectByPrimaryKey(fid);
    }

    @Override
    public int removeFile(Integer fid) throws Exception {
        return userFileMapper.deleteByPrimaryKey(fid);
    }

    @Override
    public List<UserFile> getALL() throws Exception {
        UserFileExample userFileExample = new UserFileExample();
        userFileExample.createCriteria().andFidIsNotNull();
        return userFileMapper.selectByExample(userFileExample);
    }
}
