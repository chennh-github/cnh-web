package com.cnh.mvc.system.menuInfo.controller;

import com.cnh.frame.crud.base.constant.CONSTANT;
import com.cnh.frame.crud.base.dao.ISortableDao;
import com.cnh.frame.crud.base.service.BaseService;
import com.cnh.frame.crud.exception.BaseException;
import com.cnh.frame.crud.utils.Assist;
import com.cnh.mvc.system.base.controller.SystemApiController;
import com.cnh.mvc.system.menuInfo.dao.IMenuInfoDao;
import com.cnh.mvc.system.menuInfo.entity.MenuInfo;
import com.cnh.mvc.system.menuInfo.service.MenuInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@Api("MenuInfo Api")
@RestController
@RequestMapping(CONSTANT.API_SYSTEM_PATH + "/menuInfo")
public class MenuInfoApiController extends SystemApiController<MenuInfo> {

    @Autowired
    private MenuInfoService menuInfoService;

    @Autowired
    private IMenuInfoDao menuInfoDao;

    @Override
    protected BaseService<MenuInfo> getService() {
        return menuInfoService;
    }

    @Override
    protected ISortableDao<MenuInfo> getSortableDao() {
        return menuInfoDao;
    }


    @ApiOperation("更新菜单依赖")
    @RequestMapping(value = "update/dependency", method = RequestMethod.POST)
    public void updateDependency() {

    }


    @ApiOperation("zTree拖拽")
    @RequestMapping(value = "drop", method = RequestMethod.PUT)
    @Transactional
    public void drop(@RequestParam(value = "id") Long id,
                     @RequestParam(value = "targetId") Long targetId,
                     @RequestParam(value = "moveType") String moveType) throws BaseException {
        MenuInfo menuInfo = menuInfoDao.get(id);
        Assist.notNull(menuInfo, "不存在ID为" + id + "的数据");

        MenuInfo target = menuInfoDao.get(targetId);
        Assist.notNull(target, "不存在ID为" + targetId + "的数据");

        long orderNo = menuInfo.getOrderNo();
        long targetOrderNo = target.getOrderNo();

        Assist.threw(StringUtils.equalsIgnoreCase("inner", moveType) && target.getParentId() != null, "菜单不允许超过两级");

        if (StringUtils.equalsIgnoreCase("inner", moveType)) {
            menuInfo.setParentId(target.getId());
            menuInfoDao.update(menuInfo);
        } else {
            // MENU_INFO序号是升序
            if (StringUtils.equalsIgnoreCase("prev", moveType)) {
                menuInfoDao.increaseOrderNo(orderNo, targetOrderNo, target.getMenuType(), target.getParentId());
            } else if (StringUtils.equalsIgnoreCase("next", moveType)) {
                menuInfoDao.reduceOrderNo(orderNo, targetOrderNo, target.getMenuType(), target.getParentId());
            }

            menuInfo.setParentId(target.getParentId());
            menuInfo.setOrderNo(targetOrderNo);
            menuInfoDao.update(menuInfo);
        }
    }

    @Override
    protected Long getZTopOrderNo(Object orderType, MenuInfo sourceEntity) throws Exception {
        MenuInfo targetEntity = menuInfoDao.getSmallestOne(orderType, sourceEntity.getParentId());
        Assist.notNull(targetEntity, "置顶错误，找不到置顶对象");
        return targetEntity.getOrderNo();
    }

    @Override
    protected MenuInfo getMoveUDownTarget(Long sourceOrderNo, Object orderType, MenuInfo sourceEntity) throws Exception {
        return menuInfoDao.getCloserBiggerOne(sourceOrderNo, orderType, sourceEntity.getParentId());
    }

    @Override
    protected MenuInfo getMoveUpTarget(Long sourceOrderNo, Object orderType, MenuInfo sourceEntity) throws Exception {
        return menuInfoDao.getCloserSmallerOne(sourceOrderNo, orderType, sourceEntity.getParentId());
    }
}
