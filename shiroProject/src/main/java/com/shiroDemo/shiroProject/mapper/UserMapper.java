package com.shiroDemo.shiroProject.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.cache.annotation.CacheConfig;

import com.shiroDemo.shiroProject.entity.User;

@Mapper
@CacheConfig(cacheNames = "users")
public interface UserMapper {

	public void insertUser(User user);

	/**
	 * 
	* @Title: queryUserByLoginVali 
	* @Description: TODO多字段需要@param注解
	* @param @param username
	* @param @param userpwd
	* @param @return
	* @return User
	* @throws
	 */
	@Select("select count(username) from user where username = #{username} and userpwd=#{userpwd}")
	public Integer queryUserByLoginVali(@Param("username")String username,@Param("userpwd")String userpwd);

	@Select("select uid,username,userpwd,permissionlevel,name,state from user")
	@Results({ @Result(property = "uid", column = "uid"), 
		       @Result(property = "username", column = "username"),
		       @Result(property = "userpwd", column = "userpwd"),
		       @Result(property = "permissionlevel", column = "permissionlevel"),
		       @Result(property = "name", column = "name"),
		       @Result(property = "state", column = "state")})
	public List<User> queryAllUser();

	public User queryUserByUsername(String username);
	
	@Update("update user set state=#{state} where username=#{username}")
	public void updateUser(User user);
	
	@Select("select * from user where username=#{username}")
	public User queryUserByValiUsername(String username);

}
