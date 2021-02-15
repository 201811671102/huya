package pre.cg.ftp.service;

import pre.cg.ftp.pojo.User;

public interface UserService {

    int addUser(User user)throws Exception;

    User getUser(String account)throws Exception;

    User getUser(Integer id)throws Exception;
}
