package com.shiroDemo.shiroProject.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 
* @ClassName: Module 
* @Description: TODO权限表
* @author A18ccms a18ccms_gmail_com 
* @date 2018年12月4日 下午9:01:52 
*
 */
public class Module implements Serializable{

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 4664467358692208348L;
	private Integer mid;
	private String mname;
	private Set<Role> roles = new HashSet<Role>();

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
