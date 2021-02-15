package pre.cg.ftp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pre.cg.ftp.mapper.UserMapper;
import pre.cg.ftp.pojo.User;
import pre.cg.ftp.pojo.UserExample;
import pre.cg.ftp.service.UserService;

import java.util.List;

/**
 * @ClassName FileServiceImpl
 * @Description TODO
 * @Author QQ163
 * @Date 2020/8/19 0:26
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public int addUser(User user) throws Exception {
        return userMapper.insertSelective(user);
    }

    @Override
    public User getUser(String account) throws Exception {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andAccountEqualTo(account);
        List<User> users = userMapper.selectByExample(userExample);
        if (users!=null && users.size()>0){
            return users.get(0);
        }
        return null;
    }

    @Override
    public User getUser(Integer id) throws Exception {
        return userMapper.selectByPrimaryKey(id);
    }
}
