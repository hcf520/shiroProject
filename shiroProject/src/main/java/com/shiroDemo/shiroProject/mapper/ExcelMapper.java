package com.shiroDemo.shiroProject.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.cache.annotation.CacheConfig;

import com.shiroDemo.shiroProject.entity.DemoExcel;
import com.shiroDemo.shiroProject.entity.UserData;

@Mapper
@CacheConfig(cacheNames = "excels")
public interface ExcelMapper {

	@Select("SELECT id,name,importTime FROM demoexcel")
	@Results({ @Result(property = "id", column = "id"), @Result(property = "name", column = "name"),
			@Result(property = "importTime", column = "importTime") })
	public List<DemoExcel> queryDemoExcel();

	@Insert({ "<script>",
			"insert into userdata(username,sex,city,sign,experience,logins,wealth,classify,score) values ",
			"<foreach collection='userDataList' item='item' index='index' separator=','>",
			"(#{item.username}, #{item.sex}, #{item.city}, #{item.sign}, #{item.experience}, #{item.logins}, #{item.wealth}, #{item.classify}, #{item.score})",
			"</foreach>", "</script>" })
	@SelectKey(statement = "select last_insert_id()", before = false, keyProperty = "id", resultType = int.class)
//	public int insertExcelUserData(@Param("userDataList") List<UserData> userDataList);
	public int insertExcelUserData( Map userDataList);
}
