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
     *
     * @param orderType
     * @return
     */
    public Long getMaxOrderNo(@Param("orderType") Object orderType);

    /**
     * 取最近的序号较大的对象，没有则返回null
     *
     * @param orderNo
     * @param orderType
     * @return
     */
    public T getCloserBiggerOne(@Param("orderNo") Long orderNo, @Param("orderType") Object orderType);

    /**
     * 取最近的序号较小的对象，没有则返回null
     *
     * @param orderNo
     * @param orderType
     * @return
     */
    public T getCloserSmallerOne(@Param("orderNo") Long orderNo, @Param("orderType") Object orderType);

    /**
     * 取序号最大的对象，没有则返回null
     *
     * @param orderType
     * @return
     */
    public T getBiggestOne(@Param("orderType") Object orderType);

    /**
     * 取序号最小的对象，没有则返回null
     *
     * @param orderType
     * @return
     */
    public T getSmallestOne(@Param("orderType") Object orderType);
}
