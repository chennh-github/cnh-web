package com.cnh.frame.crud.base.dao;

import org.apache.ibatis.annotations.Param;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/10/8
 */
public interface ISortableDao<T> {

    /**
     * 取最大的orderNo
     * @return
     */
    public Long getMaxOrderNo();

    /**
     * 取最近的序号较大的对象，没有则返回null
     * @param orderNo
     * @return
     */
    public T getCloserBiggerOne(@Param("orderNo") Long orderNo);

    /**
     * 取最近的序号较小的对象，没有则返回null
     * @param orderNo
     * @return
     */
    public T getCloserSmallerOne(@Param("orderNo") Long orderNo);

    /**
     * 置顶
     * @param id
     */
    public void zTop(@Param("id") Long id);

}
