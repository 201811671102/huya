package pre.cg.base.service.ServiceImpl;


import org.apache.ibatis.session.RowBounds;

import org.springframework.beans.factory.annotation.Autowired;
import pre.cg.base.service.BaseService;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * @auto CG
 * @date 2019/12/7 15:26
 */
public abstract class BaseServiceImpl<M extends Mapper<T>,T> implements BaseService<T> {

    @Autowired
    protected M mapper;


    public void GetBaseService(M mapper){
        this.mapper = mapper;
    }

    @Override
    public List<T> SelectAll() {
        return mapper.selectAll();
    }

    @Override
    public T SelectById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<T> SelectList(String key ,Object value) {
        Class<T> clazzT = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazzT);
        Example.Criteria criteria1 = example.createCriteria();
        criteria1.andEqualTo(key,value);
        return mapper.selectByExample(example);
    }

    @Override
    public T SelectOne(String key, Object value) {
        Class<T> clazzT = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazzT);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(key,value);
        return mapper.selectOneByExample(example);
    }

    @Override
    public List<T> SelectPage(T t,Integer page, Integer rows) {
        RowBounds rowBounds = new RowBounds((page-1)*page,rows);
        return mapper.selectByRowBounds(t,rowBounds);
    }

    @Override
    public int InsertOne(T t) {
        return mapper.insert(t);
    }

    @Override
    public int DelectById(Integer id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int Update(String key,Object value, T t) {
        Class<T> clazzT = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazzT);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(key,value);
        return mapper.updateByExample(t,example);
    }

    @Override
    public int Update(T t) {
        return mapper.updateByPrimaryKey(t);
    }
}
