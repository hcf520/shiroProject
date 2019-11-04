package com.shiroDemo.shiroProject.service.interf;

/**
 * 
* @ClassName: MailServiceI 
* @Description: TODO 邮件业务类
* @author A18ccms a18ccms_gmail_com 
* @date 2019年2月14日 下午2:07:36 
*
 */
public interface MailServiceI {

	/**
	 * 
	* @Title: mailRegisterNotify 
	* @Description: TODO 邮件注册通知
	* @param @param addressee
	* @param @param user_uuid
	* @return void
	* @throws
	 */
	public void mailRegisterNotify(String addressee,String user_uuid);
}
