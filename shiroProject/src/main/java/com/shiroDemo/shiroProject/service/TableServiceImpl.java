package com.shiroDemo.shiroProject.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shiroDemo.shiroProject.entity.UserData;
import com.shiroDemo.shiroProject.mapper.UserDataMapper;
import com.shiroDemo.shiroProject.redisModule.RedisCache;
import com.shiroDemo.shiroProject.redisModule.RedisManager;
import com.shiroDemo.shiroProject.service.interf.TableServiceI;

@Service
@Transactional
public class TableServiceImpl implements TableServiceI {

	@Autowired
	private UserDataMapper userDataMapper;

	public static final String DEFAULT_RETRYLIMIT_CACHE_KEY_PREFIX = "TableServiceImpl:updateUserData:exception:";

	private String keyPrefix = DEFAULT_RETRYLIMIT_CACHE_KEY_PREFIX;
	@Autowired
	private RedisManager redisManager;

	@Override
	public List<UserData> getAllUserData() {
		// TODO Auto-generated method stub
		return userDataMapper.queryAllUserData();
	}

	@Override
	public List<UserData> getUserData(Integer page, Integer limit) {
		List<UserData> UserDatas = null;
		UserDatas = userDataMapper.queryAllUserData();
		return UserDatas;
	}

	@Override
	public int saveListUserData(List<UserData> userDatas) {
		int reIndex = userDataMapper.insertListUserDate(userDatas);
		return reIndex;
	}

	@Override
	public boolean updateUserData(UserData userData) {
		boolean flag = false;
		try{
			flag = userDataMapper.updateUserData(userData);
		}catch(Exception ex){
			System.out.println(ex.getCause());
			Subject subject = SecurityUtils.getSubject();
			System.out.println(subject.getPrincipals());
			new RedisCache<>(redisManager, getRedisKickoutKey("aaaaaaaaaaaaa"), 5000, "").put("test1", ex.getCause());
		}
		return flag;
	}

	public void setRedisManager(RedisManager redisManager) {
		this.redisManager = redisManager;
	}
	
	private String getRedisKickoutKey(String username) {
		return this.keyPrefix + username;
	}

	@Override
	public boolean delUserData(Integer id) {
		boolean flag = false;
		try{
			flag = userDataMapper.delUserData(id);
		}catch(Exception ex){
			ex.getCause();
		}
		return flag;
	}
}
