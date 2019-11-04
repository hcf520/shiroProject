package com.shiroDemo.shiroProject.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
public class DataSourceConfiguration {
	@Bean(name="dataSource") // 定义Bean
    @Qualifier(value = "dataSource")
	@Primary
	@ConfigurationProperties(prefix="c3p0") // 配置属性
	public DataSource dataSource(){
		return DataSourceBuilder.create() // 创建数据源构建对象
				.type(ComboPooledDataSource.class) // 设置数据源类型
				.build(); // 构建数据源对象
}
}