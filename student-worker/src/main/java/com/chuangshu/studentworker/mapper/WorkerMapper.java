package com.chuangshu.studentworker.mapper;


import com.chuangshu.studentworker.pojo.Worker;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Jiaqi Guo
 * @date 2020/6/8  - 13:33
 */
@Repository
public interface WorkerMapper extends Mapper<Worker> {
    //自定义一个登录使用的mapper
    @Select("select * from t_worker where worker_number = #{worker_number} and password = #{password}")
    Worker selectByTwo(Integer worker_number, String password);

    @Select("select * from t_worker where worker_number = #{worker_number} and password = #{password}")
    Worker ifNull(Integer worker_number, String password);
}
