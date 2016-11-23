package com.cnh.frame.crud.base.service;

import com.cnh.frame.crud.base.dao.IBaseDao;
import com.cnh.frame.crud.base.entity.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

public abstract class BaseService<T extends BaseEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseService.class);

    protected abstract IBaseDao<T> getDao();

    /**
     * 执行查询前设置查询条件
     * @param paramMap
     */
    protected void beforeSelect(Map<String, Object> paramMap) throws Exception {}

    /**
     * 执行查询后回调函数
     * @param list
     * @param paramMap
     */
    protected void afterSelect(List<T> list, Map<String, Object> paramMap) throws Exception {};

    /**
     * get查询后回调函数
     * @param entity
     * @throws Exception
     */
    protected void afterGet(T entity) throws Exception {};

    /**
     * select或get后回调函数
     * @param entity
     * @throws Exception
     */
    protected void afterSelectOrGet(T entity) throws Exception {};

    /**
     * 保存/更新前数据校验和数据更改
     * @param entity
     * @return
     */
    protected boolean beforeSaveOrUpdate(T entity) throws Exception {return true;}

    /**
     * 保存/更新后回调函数
     * @param entity
     */
    protected void afterSaveOrUpdate(T entity) throws Exception {}

    /**
     * 保存后回调函数，afterSaveOrUpdate之后执行
     * @throws Exception
     */
    protected void afterSave() throws Exception {}

    /**
     * 更新后回调函数，afterSaveOrUpdate之后执行
     * @throws Exception
     */
    protected void afterUpdate() throws Exception {}

    /**
     * 删除前校验
     * @param id
     * @return
     */
    protected boolean beforeDelete(Long id) throws Exception {return true;};

    /**
     * 删除后回调函数
     * @param id
     */
    protected void afterDelete(Long id) throws Exception {};

    /**
     * 删除前校验
     * @param ids
     * @return
     */
    protected boolean beforeDeletes(Long[] ids) throws Exception {return true;};

    /**
     * 删除后回调函数
     * @param ids
     */
    protected void afterDeletes(Long[] ids) throws Exception {};


    /**
     * 执行select
     * @param paramMap
     * @return
     */
    public List<T> select(Map<String, Object> paramMap) throws Exception {
        LOGGER.debug("service select original paramMap: {}", paramMap);
        this.beforeSelect(paramMap);
        LOGGER.debug("service select custom paramMap: {}", paramMap);
        final List<T> list = getDao().select(paramMap);
        if (list.size() > 0) {
            for (T entity : list) {
                this.afterSelectOrGet(entity);
            }
        }
        this.afterSelect(list, paramMap);
        return list;
    }


    /**
     * 根据ID取实体对象
     * @param id
     * @return
     * @throws Exception
     */
    public T get(Long id) throws Exception{
        LOGGER.debug("service get id: {}", id);
        T entity = getDao().get(id);
        Assert.notNull(entity, String.format("不存在ID为%s的数据", id));
        this.afterSelectOrGet(entity);
        this.afterGet(entity);
        return entity;
    }


    /**
     * 执行插入，插入前执行数据校验，插入后执行回调函数
     * @param entity
     */
    public void save(T entity) throws Exception {
        LOGGER.debug("service save original entity: {}", entity);
        if (this.beforeSaveOrUpdate(entity)) {
            LOGGER.debug("service save custom entity: {}", entity);
            this.getDao().insert(entity);
            this.afterSaveOrUpdate(entity);
            this.afterSave();
        } else {
            LOGGER.warn("service save cancel by user, entity: {}", entity);
        }
    }

    /**
     * 执行更新，更新前执行数据校验，更新后执行回调函数
     * @param entity
     */
    public void update(T entity) throws Exception {
        LOGGER.debug("service update original entity: {}", entity);
        if (this.beforeSaveOrUpdate(entity)) {
            LOGGER.debug("service update custom entity: {}", entity);
            this.getDao().update(entity);
            this.afterSaveOrUpdate(entity);
            this.afterUpdate();
        } else {
            LOGGER.warn("service update cancel by user, entity: {}", entity);
        }
    }

    /**
     * 保存/更新
     * @param entity
     */
    public void saveOrUpdate(T entity) throws Exception {
        if (entity.getId() == null || entity.getId() == 0) {
            this.save(entity);
        } else {
            this.update(entity);
        }
    }

    /**
     * 执行删除
     * @param id
     */
    public void delete(Long id) throws Exception {
        LOGGER.debug("service delete id: {}", id);
        if (this.beforeDelete(id)) {
            this.getDao().delete(id);
            this.afterDelete(id);
        } else {
            LOGGER.warn("service delete cancel by user, id: {}", id);
        }
    }

    /**
     * 执行批量删除
     * @param ids
     */
    public void delete(Long[] ids) throws Exception {
        LOGGER.debug("service delete ids: {}", ids);
        if (this.beforeDeletes(ids)) {
            this.getDao().deletes(ids);
            this.afterDeletes(ids);
        } else {
            LOGGER.warn("service delete cancel by user, ids: {}", ids);
        }
    }
}
