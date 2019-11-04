package com.shiroDemo.shiroProject.service.interf;

import java.util.List;
import java.util.Map;

import com.shiroDemo.shiroProject.entity.User;

public interface UserServiceI {

	/*
	 * 插入用户帐号信息
	 */
	public List<String> insertUser(User user,String aCode);
	
	/*
	 * 查询是否存在帐号
	 */
	public Integer loginUserVali(String username,String userpwd);
	
	/*
	 * 修改帐号信息
	 */
	public void updateUser(User user);
	
	/**
	 * 
	* @Title: queryAllUser 
	* @Description: TODO查询所有帐号信息
	* @param @return
	* @return List<User>
	* @throws
	 */
	public List<User> queryAllUser();
	
	/**
	 * 
	* @Title: queryCascUser 
	* @Description: TODO级联查询所有用户相关信息
	* @param @return
	* @return User
	* @throws
	 */
	public User queryCascUser(String username);
	
	/**
	 * 
	* @Title: mailRegister 
	* @Description: TODO 邮箱注册形式的帐号激活动作
	* @param @param user
	* @param @return
	* @return List<String>
	* @throws
	 */
	public Map<String,String> mailRegister(User user);
	
	public User getRedisMailUser(String userkey);
}
