package pre.cg.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pre.cg.base.service.ServiceImpl.BaseServiceImpl;
import pre.cg.mapper.UserMapper;
import pre.cg.pojo.User;
import pre.cg.service.UserService;



@Service
public class UserserviceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper usersMapper;

}
