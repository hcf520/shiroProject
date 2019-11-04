package com.shiroDemo.shiroProject.tools;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 
 * @ClassName: SendEmail
 * @Description: TODO
 * @author A18ccms a18ccms_gmail_com
 * @date 2019年1月27日 下午10:51:31
 *
 */
public class SendEmail {
	public static final String HOST = "smtp.qq.com";
	public static final String PROTOCOL = "smtp";
	public static final int PORT = 25;
	public static final String FROM = "846656938@qq.com";// 发件人的email
	public static final String PWD = "dgbqplhhlvsebdaj";// 发件人密码

	/**
	 * 获取Session
	 * 
	 * @return
	 */
	private static Session getSession() {
		Properties props = new Properties();
		props.put("mail.smtp.host", HOST);// 设置服务器地址
		props.put("mail.store.protocol", PROTOCOL);// 设置协议
		props.put("mail.smtp.port", PORT);// 设置端口
		props.put("mail.smtp.auth", true);

		Authenticator authenticator = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(FROM, PWD);
			}

		};
		Session session = Session.getDefaultInstance(props, authenticator);

		return session;
	}

	public static void send(String toEmail, String content, String subject) {
		Session session = getSession();
		try {
			// System.out.println("--send--" + content);
			// Instantiate a message
			Message msg = new MimeMessage(session);

			// Set message attributes
			msg.setFrom(new InternetAddress(FROM));
			InternetAddress[] address = { new InternetAddress(toEmail) };
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			msg.setContent(content, "text/html;charset=utf-8");

			// Send the message
			Transport.send(msg);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	public static void sendSSLEmail(String emailAddress, String content, String subject) {
		// emailAddress 收件人邮箱
		boolean isSSL = true;
		String host = "smtp.qq.com";
		int port = 465;
		String from = "846656938@qq.com"; // 发件人的email
		boolean isAuth = true;
		final String password = "dgbqplhhlvsebdaj";

		Properties props = new Properties();
		props.put("mail.smtp.ssl.enable", isSSL);
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", isAuth);

		Session session = Session.getDefaultInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
			message.setSubject(subject);
//			message.setText(content);
			message.setContent(content, "text/html;charset=utf-8");
			Transport.send(message);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		System.out.println("----测试");
		  StringBuffer theMessage = new StringBuffer();
	         theMessage.append("<h2><font color=red>这倒霉孩子</font></h2>");
	         theMessage.append("<hr>");
	         theMessage.append("<i>年年失望年年望</i>");
		SendEmail.send("347477212@qq.com", theMessage.toString(), "HTML_TEST");
		
		SendEmail.sendSSLEmail("347477212@qq.com", theMessage.toString(), "测试邮件标题");
		System.out.println("----完成");
	}
}