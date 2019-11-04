package com.shiroDemo.shiroProject.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.shiroDemo.shiroProject.entity.User;
import com.shiroDemo.shiroProject.mapper.UserMapper;
import com.shiroDemo.shiroProject.redisModule.RedisManager;
import com.shiroDemo.shiroProject.service.interf.UserServiceI;
import com.shiroDemo.shiroProject.util.StringUtil;

@Service
@Transactional
public class UserServiceImpl implements UserServiceI {

	@Autowired
	private UserMapper userMapper;

	public static final String DEFAULT_RETRYLIMIT_CACHE_KEY_PREFIX = "user:cache:mailRegister:";
	private String keyPrefix = DEFAULT_RETRYLIMIT_CACHE_KEY_PREFIX;

	@Autowired
	private RedisManager redisManager;

	@Override
	public List<String> insertUser(User user,String aCode) {
		List<String> signup_msg = new ArrayList<>();
		try {
//			// 将用户名作为盐值
//			ByteSource salt = ByteSource.Util.bytes(user.getUsername());
//			/*
//			 * MD5加密： 使用SimpleHash类对原始密码进行加密。 第一个参数代表使用MD5方式加密 第二个参数为原始密码
//			 * 第三个参数为盐值，即用户名 第四个参数为加密次数 最后用toHex()方法将加密后的密码转成String
//			 */
//			String newPwd = new SimpleHash("MD5", user.getUserpwd(), salt, 1024).toHex();
//			user.setUserpwd(newPwd);
//			user.setPermissionlevel(2);
//			user.setState('2');
			userMapper.insertUser(user);
			signup_msg.add("success");
			signup_msg.add("平台用户\"" + user.getUsername() + "\"注册成功");
			redisManager.del(aCode);
			return signup_msg;
		} catch (Exception ex) {
			signup_msg.add("failed");
			signup_msg.add("Service操作插入用户异常信息:" + ex.getMessage());
			return signup_msg;
		}
	}

	@Override
	public Integer loginUserVali(String username, String userpwd) {
		// TODO Auto-generated method stub
		return userMapper.queryUserByLoginVali(username, userpwd);
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		userMapper.updateUser(user);

	}

	@Override
	public List<User> queryAllUser() {
		// TODO Auto-generated method stub
		return userMapper.queryAllUser();
	}

	@Override
	public User queryCascUser(String username) {
		// TODO Auto-generated method stub
		return userMapper.queryUserByUsername(username);
	}

	/**
	 * is邮箱注册模块，另配合激活模块完成配套
	 */
	@Override
	public Map<String, String> mailRegister(User user) {
		// Object jsonObjUser = JSONObject.toJSON(user);
		User ValiUser = userMapper.queryUserByValiUsername(user.getUsername());
		Map<String, String> maps = new HashMap<String, String>();
		if (ValiUser != null) {
			maps.put("stutas", "101");
			return maps;
		} else if (StringUtil.emailFormat(user.getUsername())) {
			// 将用户名作为盐值
			ByteSource salt = ByteSource.Util.bytes(user.getUsername());
			/*
			 * MD5加密： 使用SimpleHash类对原始密码进行加密。 第一个参数代表使用MD5方式加密 第二个参数为原始密码
			 * 第三个参数为盐值，即用户名 第四个参数为加密次数 最后用toHex()方法将加密后的密码转成String
			 */
			String newPwd = new SimpleHash("MD5", user.getUserpwd(), salt, 1024).toHex();
			user.setUserpwd(newPwd);
			user.setPermissionlevel(2);
			user.setState('2');
			
			String jsonStrUser = JSONObject.toJSONString(user);
			// redis模块
			String user_uuid = StringUtil.getUUID(true);
			redisManager.set(getRedisKickoutKey(user_uuid), jsonStrUser, 60*60*24);
			maps.put("stutas", "100");
			maps.put("username", user.getUsername());
			maps.put("user_uuid", user_uuid);
			return maps;
		} else {
			maps.put("stutas", "102");
			return maps;
		}
	}	

	public String getRedisKickoutKey(String username) {
		return this.keyPrefix + username;
	}

	public void setRedisManager(RedisManager redisManager) {
		this.redisManager = redisManager;
	}

	@Override
	public User getRedisMailUser(String userkey) {
		String userStr = (String) redisManager.get(getRedisKickoutKey(userkey));
		System.out.println("userStr:"+userStr);
//		User user = (User)userStr;
		String jsonStrUser = JSONObject.toJSONString(userStr);
		System.out.println("jsonStrUser:"+jsonStrUser);
		User user = JSON.parseObject(userStr,new TypeReference<User>() {});
		return user;
	}

}
