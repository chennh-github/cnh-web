package com.cnh.mvc.system.menuInfo.entity;

import com.cnh.frame.crud.base.constant.CONSTANT;
import com.cnh.frame.crud.base.entity.BaseEntity;
import lombok.Data;

@Data
public class MenuInfo extends BaseEntity {


	private static final long serialVersionUID = 1L;


	//columns START

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 跳转地址
	 */
	private String url;

	/**
	 * 菜单类型。1-超级后台菜单，2-幼儿园后台菜单 3教师端 4家长端 5pc教师端
	 */
	private Long menuType;

	/**
	 * 菜单描述
	 */
	private String describtion;

	/**
	 * 父菜单ID。0-一级菜单
	 */
	private Long parentId;

	/**
	 *
	 */
	private String menuIcon;

	/**
	 * 菜单编码，唯一识别一种菜单
	 */
	private String code;

	/**
	 * 状态，1-启用，0-禁用，-1-删除
	 */
	private Long status;

	/**
	 * 排序号，越小排越前
	 */
	private Long orderNo;

	/**
	 *
	 */
	private String target;

	//columns END


	@Override
	public String orderBy() {
		return CONSTANT.ASC;
	}

	@Override
	public String toString() {
		return super.toString();
	}

}

