//package com.shiroDemo.shiroProject.junitTest;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.shiroDemo.shiroProject.service.interf.MailService;
//
///**
// * 发送邮件测试类 Created by ASUS on 2018/5/5
// *
// * @Authod Grey Wolf
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class MailTest {
//
////	@Autowired
////	private MailService mailService;
//	@Autowired
//	private MailUtil mailUtil;
//
//	@Value("${mail.fromMail.addr}")
//	private String form;
//
//	@Test
//	public void sendSimpleMail() throws Exception {
////		mailService.sendMail(form, "简单邮件", "springboot实现邮件发送");
//		String deliver = "846656938@qq.com";
//        String[] receiver = {"347477212@qq.com"};
////        String[] carbonCopy = {"抄送者邮件地址"};
//        String subject = "This is a simple email";
//        String content = "This is a simple content";
//        
//        try {
//            mailUtil.sendSimpleEmail(deliver, receiver, subject, content);
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        }
//	}
//}