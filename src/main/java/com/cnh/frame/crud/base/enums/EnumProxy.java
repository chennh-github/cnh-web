package com.cnh.frame.crud.base.enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/10/26
 */
public class EnumProxy {

    private List<EnumEntity> list = new ArrayList<EnumEntity>();

    public EnumProxy(EnumEntity... enumEntities) {
        add(enumEntities);
    }

    public void add(EnumEntity... enumEntities) {
        Collections.addAll(list, enumEntities);
    }

    public EnumEntity remove(int value) {
        EnumEntity enumEntity = null;
        for (EnumEntity entity : list) {
            if (entity.getValue() == value) {
                list.remove(enumEntity = entity);
                break;
            }
        }
        return enumEntity;
    }

    /**
     * 根据name取value
     *
     * @param name
     * @return
     */
    public Integer getValue(String name) {
        for (EnumEntity entity : list) {
            if (entity.getName().equals(name)) {
                return entity.getValue();
            }
        }
        return null;
    }

    /**
     * 根据value取name
     *
     * @param value
     * @return
     */
    public String getName(int value) {
        for (EnumEntity entity : list) {
            if (entity.getValue() == value) {
                return entity.getName();
            }
        }
        return null;
    }

    /**
     * 是否包含value
     *
     * @param value
     * @return
     */
    public boolean containsValue(int value) {
        return valueList().contains(value);
    }

    /**
     * 是否包含name
     *
     * @param name
     * @return
     */
    public boolean containsName(String name) {
        return nameList().contains(name);
    }

    /**
     * 取全部name
     *
     * @return
     */
    public List<String> nameList() {
        List<String> nameList = new ArrayList<String>(list.size());
        for (EnumEntity entity : list) {
            nameList.add(entity.getName());
        }
        return nameList;
    }

    /**
     * 取全部value
     *
     * @return
     */
    public List<Integer> valueList() {
        List<Integer> valueList = new ArrayList<Integer>(list.size());
        for (EnumEntity entity : list) {
            valueList.add(entity.getValue());
        }
        return valueList;
    }


}
