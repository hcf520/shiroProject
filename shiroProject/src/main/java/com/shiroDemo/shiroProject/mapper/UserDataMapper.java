package com.shiroDemo.shiroProject.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.shiroDemo.shiroProject.entity.UserData;

@Mapper
public interface UserDataMapper {

	/**
	 * 
	* @Title: queryAllUserData 
	* @Description: TODO 分页查询持久层接口
	* @param @return
	* @return List<UserData>
	* @throws
	 */
	@Select("select id,username,sex,city,sign,experience,logins,wealth,classify,score from userdata")
	@Results({ @Result(property = "id", column = "id"), @Result(property = "username", column = "username"),
			@Result(property = "sex", column = "sex"), @Result(property = "city", column = "city"),
			@Result(property = "sign", column = "sign"), @Result(property = "experience", column = "experience"),
			@Result(property = "logins", column = "logins"), @Result(property = "wealth", column = "wealth"),
			@Result(property = "classify", column = "classify"), @Result(property = "score", column = "score") })
	public List<UserData> queryAllUserData();

	/**
	 * 
	* @Title: insertListUserDate 
	* @Description: TODO excel导入持久层接口
	* @param @param userDatas
	* @param @return
	* @return int
	* @throws
	 */
	@Insert({ "<script>",
			"insert into userdata(username,sex,city,sign,experience,logins,wealth,classify,score) values ",
			"<foreach collection='userDatas' item='item' index='index' separator=','>",
			"(#{item.username}, #{item.sex},#{item.city},#{item.sign},#{item.experience},#{item.logins},#{item.wealth},#{item.classify},#{item.score})",
			"</foreach>", "</script>" })
	@SelectKey(statement = "select last_insert_id()", before = false, keyProperty = "id", resultType = int.class)
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "ID") // id自动增长
	public int insertListUserDate(@Param("userDatas") List<UserData> userDatas);
	
	@Update({"<script>",
        "update userdata",
        "<set>",
        "<if test='experience != null'>",
        "experience = #{experience} ,",
        "</if>",
        "<if test='score != null'>",
        "score = #{score} ,",
        "</if>",
        "<if test='wealth != null'>",
        "wealth = #{wealth} ,",
        "</if>",
        "</set>",
        "where id = #{id}",
        "</script>"})
	public boolean updateUserData(UserData userData);
	
	@Delete("delete from userdata where id=#{id}")
	public boolean delUserData(Integer id);
}
