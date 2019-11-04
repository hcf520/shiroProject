package com.shiroDemo.shiroProject.service.interf;

import java.util.List;

import com.github.pagehelper.Page;
import com.shiroDemo.shiroProject.entity.UserData;

public interface TableServiceI {

	 List<UserData> getAllUserData();
	
	 List<UserData> getUserData(Integer page, Integer limit);
	 
	 int saveListUserData(List<UserData> userDatas);
	 
	 boolean updateUserData(UserData userData);
	 
	 boolean delUserData(Integer id);
}
