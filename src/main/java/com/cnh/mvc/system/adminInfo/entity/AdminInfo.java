package com.cnh.mvc.system.adminInfo.entity;

import com.cnh.frame.crud.base.entity.BaseEntity;
import lombok.Data;

@Data
public class AdminInfo extends BaseEntity {
	

	private static final long serialVersionUID = 1L;


	//columns START

	/**
	 * 账号
	 */
	private String account;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 类型，0-超级管理员，1-管理员
	 */
	private Long type;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 状态，0-禁用，1-启用
	 */
	private Long status;
			
	private java.util.Date createTime;
	
	private String createTimeString;
	
	private String createTimeBegin;
	
	private String createTimeEnd;

	//columns END
	


	@Override
	public String toString() {
		return super.toString();
	}

}

