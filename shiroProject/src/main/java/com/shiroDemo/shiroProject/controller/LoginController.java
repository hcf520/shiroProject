package com.shiroDemo.shiroProject.controller; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.shiroDemo.shiroProject.entity.User;
import com.shiroDemo.shiroProject.service.interf.MailServiceI;
import com.shiroDemo.shiroProject.service.interf.UserServiceI;


@Controller
@RequestMapping(value = "/index")
public class LoginController {
	
    private final static Logger logger = LogManager.getLogger(LoginController.class);
	
    @Autowired
    private UserServiceI userServiceI;
	
    @Autowired
    private MailServiceI mailServiceI;
	/**
	 * 
	* @Title: loginUser 
	* @Description: TODO 用户登录方法
	* @param @param username
	* @param @param userpwd
	* @param @param session
	* @param @return
	* @return String
	* @throws
	 */
	@RequestMapping(value = "loginUser", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> loginUser(String username,String userpwd,HttpSession session) {
		System.err.println("username:"+username+"__userpwd:"+userpwd);
		//返回结构对象
		Map<String, String> maps = new HashMap<String, String>();
		//UsernamePasswordToken对象用来存放提交的登录信息
		UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(username,userpwd);

		Subject subject = SecurityUtils.getSubject();
        try {
        	logger.info("进入登录验证......");
            subject.login(usernamePasswordToken);   //完成登录
            User user=(User) subject.getPrincipal();
            String jsonStrUser = JSONObject.toJSONString(user);
    		System.err.println("loginUser_User:"+jsonStrUser);
            session.setAttribute("user", user);
            maps.put("msg","用户校验成功！");
            maps.put("target", "index");
        } catch(Exception e) {
        	//返回登录页面
        	maps.put("msg","用户登录失败！");
            maps.put("target", "login");
        }
        return maps;
	}
	
	 @RequestMapping("/directionalIndex")
	    public String directionalIndex(HttpSession session) {  
		 try{
	        User user = (User) session.getAttribute("user");
	        String jsonStrUser = JSONObject.toJSONString(user);
    		System.err.println("loginUser_User:"+jsonStrUser);
    		 return "index";
		 }catch(Exception ex){
			 System.err.println(ex.getMessage());
			 return "login";
		 }
	        
	    }
	 
	 @RequestMapping("/logOut")
	    public String logOut(HttpSession session) {
		 System.err.println("logOut:~~~~~~~~~~~~~~~~~~~~");
	        Subject subject = SecurityUtils.getSubject();
	        subject.logout();
	        session.removeAttribute("user");
	        return "login";
	    }
	 
	 /**
	  * 
	 * @Title: insertUser 
	 * @Description: TODO注册用户帐号
	 * @param @param user
	 * @param @return
	 * @return List<String>
	 * @throws
	  */
	@RequestMapping(value = "registerUser", method = RequestMethod.GET)
	public String registerUser(String aCode,HttpSession session) {
		System.out.println("aCode:"+aCode);
		//进行帐号激活并删除redis缓存
		List<String> resultlist = userServiceI.insertUser(userServiceI.getRedisMailUser(aCode),aCode);
		session.setAttribute("resultlist", resultlist);
		return "login";
		
	}
	@RequestMapping(value = "loginUserVali", method = RequestMethod.GET)
	public @ResponseBody Integer loginUserVali(String username,String userpwd) {
		System.out.println("Controller_queryUser:"+username);
		return userServiceI.loginUserVali(username, userpwd);
	}
	@RequestMapping(value = "updateUser", method = RequestMethod.GET)
	public @ResponseBody void updateUser(User user) {
		System.out.println("Controller_____:"+user);
		userServiceI.updateUser(user);;
	}
	
	@RequestMapping(value = "queryAllUser", method = RequestMethod.GET)
	public @ResponseBody List<User> queryAllUser() {
		
		return userServiceI.queryAllUser();
	}
	
	@RequestMapping(value = "queryCascUser", method = RequestMethod.GET)
	public @ResponseBody User queryCascUser(String username) {
		
		return userServiceI.queryCascUser(username);
	}
	
	@RequestMapping(value = "mailRegister", method = RequestMethod.POST)
	public @ResponseBody List<String> mailRegister(User user){
		System.out.println(JSONObject.toJSONString(user));
		
		Map<String, String> maps = userServiceI.mailRegister(user);
		List<String> resultlist = new ArrayList<>();
		//发送激活邮件
		if(maps.get("stutas").equals("100")){
			mailServiceI.mailRegisterNotify(maps.get("username"), maps.get("user_uuid"));	
			resultlist.add(maps.get("stutas"));
			resultlist.add("激活邮件已发送，请前往邮箱进行激活操作！");
			return resultlist;
		}else if(maps.get("stutas").equals("101")){
			resultlist.add(maps.get("stutas"));
			resultlist.add("邮箱注册失败，此邮箱已注册！");
			return resultlist;	
		}else {
			resultlist.add(maps.get("stutas"));
			resultlist.add("邮箱注册失败，邮箱格式错误！）");
			return resultlist;	
		}
		
	}
}