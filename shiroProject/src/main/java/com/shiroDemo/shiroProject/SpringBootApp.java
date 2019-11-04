package com.shiroDemo.shiroProject;

import javax.servlet.MultipartConfigElement;
import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;

@SpringBootApplication
@EnableScheduling
public class SpringBootApp {
	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(SpringBootApp.class, args);

		DataSource dataSource = context.getBean(DataSource.class);

		System.out.println(dataSource.getClass());

	}
	
	/**  
     * 文件上传配置  
     * @return  
     */  
    @Bean  
    public MultipartConfigElement multipartConfigElement() {  
        MultipartConfigFactory factory = new MultipartConfigFactory();  
        //单个文件最大  
        factory.setMaxFileSize("20480KB"); //KB,MB  
        /// 设置总上传数据总大小  
        factory.setMaxRequestSize("20480KB");  
        return factory.createMultipartConfig();  
    }
}