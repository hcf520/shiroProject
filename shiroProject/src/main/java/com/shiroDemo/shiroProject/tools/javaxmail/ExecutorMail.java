package com.shiroDemo.shiroProject.tools.javaxmail;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorMail {
	
	private ExecutorService executor = Executors.newFixedThreadPool(1);

	private MailSenderInfo mailSenderInfo;
	
	public void asynTask() throws InterruptedException {

		executor.submit(new Runnable() {
			@Override
			public void run() {
				SimpleMailSender.sendHtmlMail(mailSenderInfo);
			}
		});
	}

	public MailSenderInfo getMailSenderInfo() {
		return mailSenderInfo;
	}

	public void setMailSenderInfo(MailSenderInfo mailSenderInfo) {
		this.mailSenderInfo = mailSenderInfo;
	}
	
}
