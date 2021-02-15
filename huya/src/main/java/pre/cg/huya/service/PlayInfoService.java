package pre.cg.huya.service;

import pre.cg.huya.pojo.PlayInfo;

import java.util.List;

public interface PlayInfoService {
    /*添加*/
    void insertInfo(PlayInfo playInfo)throws Exception;
    /*获取*/
    PlayInfo selectInfo(String uid)throws Exception;
    /*全部*/
    List<PlayInfo> selectAll()throws Exception;
}
