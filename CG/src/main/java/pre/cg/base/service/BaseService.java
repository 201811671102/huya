package pre.cg.base.service;



import java.util.List;

/**
 * @auto CG
 * @date 2019/12/7 15:25
 */
public interface BaseService<T> {
    /**
     * 查找全部
     */
    List<T> SelectAll();
    /**
     * 根据id查找
     */
    T SelectById(Integer id);
    /**
     * 根据属性查找多个
     */
    List<T> SelectList(String key, Object value);
    /**
     * 根据属性查找一个
     */
    T SelectOne(String key, Object value);
    /**
     * 分页查找
     */
    List<T> SelectPage(T t, Integer page, Integer rows);
    /**
     * 增加
     */
    int InsertOne(T t);
    /**
     * 根据id删除
     */
    int DelectById(Integer id);
    /**
     * 根据属性修改
     */
    int Update(String key, Object value, T t);
    /**
     * 根据id修改
     */
    int Update(T t);
}
