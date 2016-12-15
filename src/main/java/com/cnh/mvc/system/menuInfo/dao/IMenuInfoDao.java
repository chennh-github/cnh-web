package com.cnh.mvc.system.menuInfo.dao;

import com.cnh.frame.crud.base.dao.IBaseDao;
import com.cnh.frame.crud.base.dao.ISortableDao;
import com.cnh.mvc.system.menuInfo.entity.MenuInfo;
import org.apache.ibatis.annotations.Param;

public interface IMenuInfoDao extends IBaseDao<MenuInfo>, ISortableDao<MenuInfo> {


    /**
     * 减小所有符合条件的数据的序号
     *
     * @param orderNo
     * @param targetOrderNo
     * @param orderType
     * @param parentId
     */
    public void reduceOrderNo(@Param("orderNo") Long orderNo,
                              @Param("targetOrderNo") Long targetOrderNo,
                              @Param("orderType") Object orderType,
                              @Param("parentId") Long parentId);

    /**
     * 增大所有符合条件的数据的序号
     *
     * @param orderNo
     * @param targetOrderNo
     * @param orderType
     * @param parentId
     */
    public void increaseOrderNo(@Param("orderNo") Long orderNo,
                                @Param("targetOrderNo") Long targetOrderNo,
                                @Param("orderType") Object orderType,
                                @Param("parentId") Long parentId);


    Long getMaxOrderNo(@Param("orderType") Object orderType,
                       @Param("parentId") Long parentId);

    MenuInfo getCloserBiggerOne(@Param("orderNo") Long orderNo,
                                @Param("orderType") Object orderType,
                                @Param("parentId") Long parentId);

    MenuInfo getCloserSmallerOne(@Param("orderNo") Long orderNo,
                                 @Param("orderType") Object orderType,
                                 @Param("parentId") Long parentId);

    MenuInfo getBiggestOne(@Param("orderType") Object orderType,
                           @Param("parentId") Long parentId);

    MenuInfo getSmallestOne(@Param("orderType") Object orderType,
                            @Param("parentId") Long parentId);
}
