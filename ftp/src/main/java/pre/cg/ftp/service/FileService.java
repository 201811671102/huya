package pre.cg.ftp.service;

import pre.cg.ftp.pojo.UserFile;

import java.util.List;

public interface FileService {
    /*新增文件*/
    int addFile(UserFile file)throws Exception;
    /*查找文件*/
    UserFile getFile(Integer fid)throws Exception;
    /*删除文件*/
    int removeFile(Integer fid)throws Exception;
    /*查找全部*/
    List<UserFile> getALL()throws Exception;
}
