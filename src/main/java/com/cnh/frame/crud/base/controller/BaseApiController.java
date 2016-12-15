package com.cnh.frame.crud.base.controller;

import com.cnh.frame.crud.base.constant.CONSTANT;
import com.cnh.frame.crud.base.dao.ISortableDao;
import com.cnh.frame.crud.base.entity.BaseEntity;
import com.cnh.frame.crud.base.service.BaseService;
import com.cnh.frame.crud.utils.Assist;
import com.cnh.frame.wraps.RequestWrap;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

public abstract class BaseApiController<T extends BaseEntity> {


    private static final Logger LOGGER = LoggerFactory.getLogger(BaseApiController.class);


    protected abstract BaseService<T> getService();

    /**
     * override该方法以支持排序
     *
     * @return
     */
    protected ISortableDao<T> getSortableDao() {
        return null;
    }


    /**
     * 读取数据列表
     * 如果传递了分页则执行分页读取，否则返回全部数据
     *
     * @param pageNo
     * @param pageSize
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Object list(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                       HttpServletRequest request) throws Exception {
        if (pageNo != null) {
            PageHelper.startPage(pageNo, pageSize);
            return new PageInfo<T>(getService().select(RequestWrap.getMap(request)));
        } else {
            return getService().select(RequestWrap.getMap(request));
        }
    }

    /**
     * 根据ID读取对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public T get(@PathVariable(value = "id") Long id) throws Exception {
        return getService().get(id);
    }

    /**
     * 更新对象
     *
     * @param entity
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public void update(@RequestBody T entity) throws Exception {
        getService().update(entity);
    }

    /**
     * "新增对象
     *
     * @param entity
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody T entity) throws Exception {
        getService().save(entity);
    }

    /**
     * 根据IDS删除对象
     *
     * @param ids
     * @throws Exception
     */
    @RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable(value = "ids") Long[] ids) throws Exception {
        getService().delete(ids);
    }


    /**
     * 上移
     *
     * @param id
     * @param orderType
     * @throws Exception
     */
    @RequestMapping(value = "/moveUp", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public void moveUp(@RequestParam(value = "id") Long id,
                       @RequestParam(value = "orderType", required = false) Object orderType) throws Exception {
        BaseService<T> service = getService();
        T sourceEntity = service.get(id);
        Assist.notNull(sourceEntity, "不存在ID=" + id + "的数据");
        Long sourceOrderNo = sourceEntity.getOrderNo();
        Assist.notNull(sourceOrderNo, "序号不能为null");

        T targetEntity = getMoveUpTarget(sourceOrderNo, orderType, sourceEntity);
        Assist.notNull(targetEntity, "已经是第一个了");

        sourceEntity.setOrderNo(targetEntity.getOrderNo());
        service.update(sourceEntity);

        targetEntity.setOrderNo(sourceOrderNo);
        service.update(targetEntity);
    }

    /**
     * 根据排序策略取上移的目标对象
     *
     * @param sourceOrderNo
     * @param orderType
     * @param sourceEntity
     * @return
     */
    protected T getMoveUpTarget(Long sourceOrderNo, Object orderType, T sourceEntity) throws Exception {
        return StringUtils.equalsIgnoreCase(sourceEntity.orderBy(), CONSTANT.DESC) ?
                getSortableDao().getCloserBiggerOne(sourceOrderNo, orderType) :
                getSortableDao().getCloserSmallerOne(sourceOrderNo, orderType);
    }


    /**
     * 下移
     *
     * @param id
     * @param orderType
     * @throws Exception
     */
    @RequestMapping(value = "/moveDown", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public void moveDown(@RequestParam(value = "id") Long id,
                         @RequestParam(value = "orderType", required = false) Object orderType) throws Exception {
        BaseService<T> service = getService();
        T sourceEntity = service.get(id);
        Assist.notNull(sourceEntity, "不存在ID=" + id + "的数据");
        Long sourceOrderNo = sourceEntity.getOrderNo();
        Assist.notNull(sourceOrderNo, "序号不能为null");

        T targetEntity = getMoveUDownTarget(sourceOrderNo, orderType, sourceEntity);
        Assist.notNull(targetEntity, "已经是最后一个了");

        sourceEntity.setOrderNo(targetEntity.getOrderNo());
        service.update(sourceEntity);

        targetEntity.setOrderNo(sourceOrderNo);
        service.update(targetEntity);
    }

    /**
     * 根据排序策略取下移的目标对象
     *
     * @param sourceOrderNo
     * @param orderType
     * @param sourceEntity
     * @return
     */
    protected T getMoveUDownTarget(Long sourceOrderNo, Object orderType, T sourceEntity) throws Exception {
        return StringUtils.equalsIgnoreCase(sourceEntity.orderBy(), CONSTANT.DESC) ?
                getSortableDao().getCloserSmallerOne(sourceOrderNo, orderType) :
                getSortableDao().getCloserBiggerOne(sourceOrderNo, orderType);
    }


    /**
     * 置顶
     *
     * @param id
     * @param orderType
     * @throws Exception
     */
    @RequestMapping(value = "/zTop", method = RequestMethod.PUT)
    @ResponseBody
    public void zTop(@RequestParam(value = "id") Long id,
                     @RequestParam(value = "orderType", required = false) Object orderType) throws Exception {
        BaseService<T> service = getService();
        T sourceEntity = service.get(id);
        Assist.notNull(sourceEntity, "不存在ID=" + id + "的数据");

        sourceEntity.setOrderNo(getZTopOrderNo(orderType, sourceEntity));
        service.update(sourceEntity);
    }

    /**
     * 根据排序策略取置顶的目标对象
     *
     * @param orderType
     * @param sourceEntity
     * @return
     */
    protected Long getZTopOrderNo(Object orderType, T sourceEntity) throws Exception{
        boolean isDesc = StringUtils.equalsIgnoreCase(sourceEntity.orderBy(), CONSTANT.DESC);
        T targetEntity = isDesc ?
                getSortableDao().getBiggestOne(orderType) :
                getSortableDao().getSmallestOne(orderType);
        Assist.notNull(targetEntity, "置顶错误，找不到置顶对象");
        return targetEntity.getOrderNo() + (isDesc ? 1 : -1);
    }
}
