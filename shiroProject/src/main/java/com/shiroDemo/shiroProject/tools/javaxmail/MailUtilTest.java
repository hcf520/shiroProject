package com.shiroDemo.shiroProject.tools.javaxmail;


public class MailUtilTest {

	public static void main(String args[]) {
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.qq.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("846656938@qq.com");

		// 邮箱密码
		mailInfo.setPassword("dgbqplhhlvsebdaj");
		mailInfo.setFromAddress("846656938@qq.com");
		mailInfo.setToAddress("he1cf@zhsin.cn");
		mailInfo.setCcAddress("347477212@qq.com,HCF_staff@163.com");
//		mailInfo.setBccAddress("******@qq.com,*****@qq.com");邮件密送地址
		mailInfo.setSubject("邮件TEST");
		mailInfo.setContent("邮件TEST");
//		mailInfo.setAttachFileNames(new String[] {
//				"C:" + File.separator + "Users" + File.separator + "Desktop" + File.separator + "新建文本文档 (1).txt",
//				"C:" + File.separator + "Users" + File.separator + "Desktop" + File.separator + "新建文本文档 (2).txt" });
//		SimpleMailSender.sendHtmlMail4AttachFile(mailInfo);
//		SimpleMailSender.sendTextMail(mailInfo);
		
	}
}