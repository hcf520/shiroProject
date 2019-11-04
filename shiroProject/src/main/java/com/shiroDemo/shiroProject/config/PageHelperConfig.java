package com.shiroDemo.shiroProject.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.pagehelper.PageHelper;

@Configuration // 将该类加到spring容器里
public class PageHelperConfig {

	@Bean // 加上该注解spring容器自动配置
	public PageHelper pageHelper() {
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("offsetAsPageNum", "true"); //1.offsetAsPageNum:设置为true时，会将RowBounds第一个参数offset当成pageNum页码使用
		properties.setProperty("rowBoundsWithCount", "true"); //2.rowBoundsWithCount:设置为true时，使用RowBounds分页会进行count查询
		properties.setProperty("reasonable", "true"); //3.reasonable：启用合理化时，如果pageNum<1会查询第一页，如果pageNum>pages会查询最后一页
		properties.setProperty("helperDialect", "mysql");
		pageHelper.setProperties(properties);
		return pageHelper;
	}
}
