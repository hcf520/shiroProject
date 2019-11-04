package com.shiroDemo.shiroProject.config;

import java.util.LinkedHashMap;
//import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shiroDemo.shiroProject.shiroModule.MyShiroRealm;
//import com.shiroDemo.shiroProject.shiroModule.RetryLimitHashedCredentialsMatcher;
import com.shiroDemo.shiroProject.shiroModule.RetryLimitHashedCredentialsMatcher;
import com.shiroDemo.shiroProject.tools.AtomicIntegerInstance;

/**
 * shiro的配置类
 * 
 * @author Administrator
 *
 */
@Configuration
public class ShiroConfiguration {
	// 将自己的验证方式加入容器
	@Bean(name="myShiroRealm")
	public MyShiroRealm myShiroRealm() {
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		myShiroRealm.setCredentialsMatcher(retryLimitHashedCredentialsMatcher());
		return myShiroRealm;
	}

	// 权限管理，配置主要是Realm的管理认证
	@Bean(name="securityManager")
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(myShiroRealm());
		return securityManager;
	}

	// Filter工厂，设置对应的过滤条件和跳转条件
	@Bean(name="shiroFilter")
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		System.out.println("ShiroConfiguration.shirFilter()");
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		LinkedHashMap<String, String>  linkedmap = new LinkedHashMap<String, String>();
		linkedmap.put("/static/**", "anon");
		// 登出
		linkedmap.put("/index/logOut/**", "logout");
		
		linkedmap.put("/webjars/**", "anon");
		linkedmap.put("/index/registerUser/**", "anon");
		linkedmap.put("/index/queryCascUser/**", "anon");
		linkedmap.put("/index/queryAllUser/**", "anon");
		linkedmap.put("/index/loginUser/**", "anon");
		linkedmap.put("/index/updateUser/**", "anon");
		linkedmap.put("/index/mailRegister/**", "anon");
		linkedmap.put("/excel/**/**", "anon");
		linkedmap.put("/table/**/**", "anon");
		// 对所有用户认证
		linkedmap.put("/**", "authc");
		// 登录
		shiroFilterFactoryBean.setLoginUrl("/guest/login");
		// 首页
		shiroFilterFactoryBean.setSuccessUrl("/index");
		// 错误页面，认证不通过跳转
		shiroFilterFactoryBean.setUnauthorizedUrl("/error");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(linkedmap);
		return shiroFilterFactoryBean;
	}

	// 加入注解的使用，不加入这个注解不生效
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}
	
	/**
	 * 
	* @Title: retryLimitHashedCredentialsMatcher 
	* @Description: TODO登录次数限制及密码加密
	* @param @return
	* @return RetryLimitHashedCredentialsMatcher
	* @throws
	 */
	@Bean("credentialsMatcher")
	public RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher(){
	    RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher = new RetryLimitHashedCredentialsMatcher();

	    //如果密码加密,可以打开下面配置
	    //加密算法的名称
	    retryLimitHashedCredentialsMatcher.setHashAlgorithmName("MD5");
	    //配置加密的次数
	    retryLimitHashedCredentialsMatcher.setHashIterations(1024);
	    //是否存储为16进制
	    retryLimitHashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);

	    return retryLimitHashedCredentialsMatcher;
	}
	/**
	 * 
	* @Title: getAtomicInteger 
	* @Description: TODO自增原子类
	* @param @return
	* @return AtomicInteger
	* @throws
	 */
	@Bean("atomicInteger")
	public AtomicInteger getAtomicInteger(){		
	return AtomicIntegerInstance.getInstance();
		
	}
	
}