package com.shiroDemo.shiroProject.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
/**
 * 
 * @ClassName: User
 * @Description: TODO用户表
 * @author A18ccms a18ccms_gmail_com
 * @date 2018年12月4日 下午9:02:42
 *
 */
public class User implements Serializable {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 7601672659380320987L;
	private int uid;
	private String username;
	private String userpwd;
	private int permissionlevel;
	private String name;
	private char state;
	private Set<Role> roles = new HashSet<Role>();

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpwd() {
		return userpwd;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

	public int getPermissionlevel() {
		return permissionlevel;
	}

	public void setPermissionlevel(int permissionlevel) {
		this.permissionlevel = permissionlevel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char getState() {
		return state;
	}

	public void setState(char state) {
		this.state = state;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	

}
