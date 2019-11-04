package com.shiroDemo.shiroProject.shiroModule;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.shiroDemo.shiroProject.entity.Module;
import com.shiroDemo.shiroProject.entity.Role;
import com.shiroDemo.shiroProject.entity.User;
import com.shiroDemo.shiroProject.service.interf.UserServiceI;

//实现AuthorizingRealm接口用户用户认证
public class MyShiroRealm extends AuthorizingRealm {

	// 用于用户查询
	@Autowired
	private UserServiceI userServiceI;

	// 角色权限和对应权限添加
	/**
	 * doGetAuthorizationInfo()是权限控制，当访问到页面的时候，
	 * 使用了相应的注解或者shiro标签才会执行此方法否则不会执行，
	 * 所以如果只是简单的身份认证没有权限的控制的话，那么这个方法可以不进行实现，
	 * 直接返回null即可。
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		// 获取登录用户名
		String username = (String) principalCollection.getPrimaryPrincipal();
		// 查询用户名称
		User user = userServiceI.queryCascUser(username);
		// 添加角色和权限
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		for(Role role : user.getRoles()){
			// 添加角色
			simpleAuthorizationInfo.addRole(role.getRname());
			for (Module permission : role.getModules()) {
				// 添加权限
				simpleAuthorizationInfo.addStringPermission(permission.getMname());
				System.out.println("用户"+user.getName()+"拥有权限："+permission.getMname());
			}
		}
		
		return simpleAuthorizationInfo;
	}

	// 用户认证
	/**
	 * doGetAuthenticationInfo主要是用来进行身份认证的，
	 * 也就是说验证用户输入的账号和密码是否正确
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		// 加这一步的目的是在Post请求的时候会先进认证，然后在到请求
		if (authenticationToken.getPrincipal() == null) {
			return null;
		}
		// 获取用户信息
		String username = authenticationToken.getPrincipal().toString();
		System.err.println("username:"+username);
		User user = userServiceI.queryCascUser(username);
		String jsonStrUser = JSONObject.toJSONString(user);
		System.err.println("doGetAuthenticationInfo_User:"+jsonStrUser);
		if (user == null) {
			// 这里返回后会报出对应异常
			System.err.println("doGetAuthenticationInfo_User=null:"+jsonStrUser);
			return null;
		} else {
			// 这里验证authenticationToken和simpleAuthenticationInfo的信息
			// 获取盐值，即用户名
			
            ByteSource salt = ByteSource.Util.bytes(username);
            String realmName = this.getName();
            
//			SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user,
//					user.getUserpwd().toString(), this.getClass().getName());
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user,
					user.getUserpwd().toString(), salt,realmName);
			return simpleAuthenticationInfo;
		}
	}
}
