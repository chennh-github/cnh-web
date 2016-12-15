package com.cnh.mvc.system.menuRelate.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import com.cnh.frame.crud.base.entity.BaseEntity;
import lombok.Data;

@Data
public class MenuRelate extends BaseEntity {
	

	private static final long serialVersionUID = 1L;


	//columns START

	/**
	 * 菜单ID
	 */
	private Long menuId;

	/**
	 * 关联ID
	 */
	private Long relateId;

	/**
	 * 菜单类型。1-超级后台菜单，2-幼儿园后台菜单
	 */
	private Long type;

	/**
	 * 状态。1-启用，0-禁用，-1-删除
	 */
	private Long status;

	/**
	 * 菜单顺序，越大越靠前
	 */
	private Long orderNo;

	/**
	 * 是否允许被分配给角色或者子级权限。1-是，0-否
	 */
	private Long roleable;

	/**
	 * 菜单编码，唯一识别一种菜单
	 */
	private String code;

	/**
	 * 
	 */
	private String menuTitle;

	/**
	 * 菜单父ID
	 */
	private Long menuParentId;

	//columns END
	


	@Override
	public String toString() {
		return super.toString();
	}

}

