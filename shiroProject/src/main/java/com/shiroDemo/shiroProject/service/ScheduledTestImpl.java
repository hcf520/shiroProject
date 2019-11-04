package com.shiroDemo.shiroProject.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.shiroDemo.shiroProject.service.interf.ScheduledTestI;

@Component
public class ScheduledTestImpl implements ScheduledTestI{

	@Override
	@Scheduled(fixedDelay = 1000 * 60 * 30)
	public void testOne() {
		
		System.out.println("testOne执行成功");
		
	}

	@Override
	@Scheduled(fixedDelay = 1000 * 60 * 60 * 596)
	public void testTwo() {

		System.out.println("testTwo执行成功");
		
	}

}
