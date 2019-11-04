package com.shiroDemo.shiroProject.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shiroDemo.shiroProject.redisModule.RedisManager;
import com.shiroDemo.shiroProject.service.interf.MailServiceI;
import com.shiroDemo.shiroProject.tools.javaxmail.ExecutorMail;
import com.shiroDemo.shiroProject.tools.javaxmail.MailSenderInfo;
import com.shiroDemo.shiroProject.util.SystemInfo;

@Service
@Transactional
public class MailServiceImpl implements MailServiceI {

	@Autowired
	private RedisManager redisManager;
	
	public static final String DEFAULT_RETRYLIMIT_CACHE_KEY_PREFIX = "user:cache:mailRegister:";
	private String keyPrefix = DEFAULT_RETRYLIMIT_CACHE_KEY_PREFIX;

	public String getRedisKickoutKey(String username) {
		return this.keyPrefix + username;
	}

	public void setRedisManager(RedisManager redisManager) {
		this.redisManager = redisManager;
	}

	@Override
	public void mailRegisterNotify(String addressee, String user_uuid) {
		System.out.println("addressee:"+addressee+"^^^^^user_uuid:"+user_uuid);
		String redisUser = (String) redisManager.get(getRedisKickoutKey(user_uuid));
		System.out.println("redisUser:"+redisUser);
		// 邮箱用户激活动态链接地址
		String host = null;
		try {
			host = SystemInfo.localIpAddress().get("ipAddress");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String activationHref = host + ":8081/index/registerUser/?aCode=" + user_uuid;
		System.out.println(activationHref);

		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setToAddress(addressee);
		mailInfo.setSubject("平台账号注册通知");
		StringBuffer theMessage = new StringBuffer();
		theMessage.append("<h2><font color=red>平台账号:" + addressee + "注册成功！</font></h2>");
		theMessage.append("<hr>");
		theMessage.append("<i>请点击以下链接进行帐号激活！</i>");
		theMessage.append("<a href='http://"+activationHref+"'>" + activationHref + "</a>");
		mailInfo.setContent(theMessage.toString());
		// 异步发送
		ExecutorMail executorMail = new ExecutorMail();
		executorMail.setMailSenderInfo(mailInfo);
		// String excception = null;
		try {
			executorMail.asynTask();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			// excception = e.getMessage();
		}

	}

}
