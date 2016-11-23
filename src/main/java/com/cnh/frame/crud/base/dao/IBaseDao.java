package com.cnh.frame.crud.base.dao;
import com.cnh.frame.crud.base.entity.BaseEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IBaseDao<T extends BaseEntity>  {
    /**
     * 查询列表
     * @param paramMap 参数Map
     * @return
     */
    public List<T> select(Map<String, Object> paramMap);

    /**
     * 根据ID读取对象
     * @param id ID
     * @return
     */
    public T get(Long id);

    /**
     * 插入新对象
     * @param entity 实体对象
     */
    public void insert(T entity);

    /**
     * 批量插入新对象
     * @param list 实体List
     */
    public void inserts(@Param("list") List<T> list);

    /**
     * 更新对象
     * @param entity 实体对象
     */
    public void update(T entity);

    /**
     * 根据ID删除对象
     * @param id ID
     */
    public void delete(Long id);

    /**
     * 删除包含IDS的数据
     * @param ids IDS
     */
    public void deletes(Long[] ids);

}
