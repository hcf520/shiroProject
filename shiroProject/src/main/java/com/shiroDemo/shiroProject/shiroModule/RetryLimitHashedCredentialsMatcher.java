package com.shiroDemo.shiroProject.shiroModule;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

import com.shiroDemo.shiroProject.entity.User;
import com.shiroDemo.shiroProject.redisModule.RedisManager;
import com.shiroDemo.shiroProject.service.interf.UserServiceI;

/**
 * 
 * @ClassName: RetryLimitHashedCredentialsMatcher
 * @Description: TODO 登录次数限制
 * @author A18ccms a18ccms_gmail_com
 * @date 2018年12月15日 上午2:39:08
 *
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

	private static final Logger logger = LogManager.getLogger(RetryLimitHashedCredentialsMatcher.class);

	public static final String DEFAULT_RETRYLIMIT_CACHE_KEY_PREFIX = "shiro:cache:retrylimit:";
	
	private String keyPrefix = DEFAULT_RETRYLIMIT_CACHE_KEY_PREFIX;

	@Autowired
	private UserServiceI userServiceI;

	@Autowired
	private RedisManager redisManager;

	@Autowired
	private AtomicInteger atomicInteger;

//	用户登录次计数
	private int count = 0;
	public void setRedisManager(RedisManager redisManager) {
		this.redisManager = redisManager;
	}

	private String getRedisKickoutKey(String username) {
		return this.keyPrefix + username;
	}

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

		// 获取用户名
		String username = (String) token.getPrincipal();
		
		// 获取用户登录次数,首次登录初始化次数
		if (null == redisManager.get(getRedisKickoutKey(username))) {
			redisManager.set(getRedisKickoutKey(username), 1,120);
		} else {
			count = (int) redisManager.get(getRedisKickoutKey(username));
			System.err.println("getRedisKickoutKey(username):" + getRedisKickoutKey(username));
			System.err.println(
					"redisManager.get(getRedisKickoutKey(username)):" + redisManager.get(getRedisKickoutKey(username)));
//			System.err.println("用户登录次数:" + atomicInteger.get());
		}

		if (count+1 > 5) {
			// 如果用户登陆失败次数大于5次 抛出锁定用户异常 并修改数据库字段
			User user = userServiceI.queryCascUser(username);
			if (user != null && '1' == user.getState()) {
				//正确的帐号用户登录次数大于五次且密码错误且帐号状态正常的，30分钟后再进行正常登录
				redisManager.set(getRedisKickoutKey(username), count+1, 60*30);
				// 数据库字段 默认为 1就是正常状态, 所以 要改为0
				// 修改数据库的状态字段为锁定
				user.setState('0');
				userServiceI.updateUser(user);
				logger.info("锁定用户" + user.getUsername());
				// 抛出用户锁定异常
				throw new LockedAccountException();
			}else{
				redisManager.set(getRedisKickoutKey(username), count, 60*30);	
			}
			
		}else{
			redisManager.set(getRedisKickoutKey(username), count+1,120);
		}
		// 判断用户账号和密码是否正确
		boolean matches = super.doCredentialsMatch(token, info);
		System.err.println("matches(判断用户密码是否正确):" + matches);
		if (matches) {
			// 如果正确,从缓存中将用户登录计数 清除
			// redisManager.del(getRedisKickoutKey(username));
			System.err.println("登录成功清空redis缓存计数的帐号:" + username);
			System.err.println("if (matches)登录成功计数:" + atomicInteger.get());
			// 解锁用户帐号恢复为正常状态——>1
			unlockAccount(username);
		} else if(count<6){
				redisManager.set(getRedisKickoutKey(username), count+1,120);
		}
		return matches;
	}

	/**
	 * 根据用户名 解锁用户
	 * 
	 * @param username
	 * @return
	 */
	public void unlockAccount(String username) {
		User user = userServiceI.queryCascUser(username);
		if (user != null) {
			// 修改数据库的状态字段为正常
			user.setState('1');
			userServiceI.updateUser(user);
			;
			redisManager.del(getRedisKickoutKey(username));
		}
	}

}