package com.shiroDemo.shiroProject.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shiroDemo.shiroProject.entity.TableData;
import com.shiroDemo.shiroProject.entity.UserData;
import com.shiroDemo.shiroProject.service.interf.TableServiceI;
import com.shiroDemo.shiroProject.tools.pagehelper.PageInfo;

@Controller
@RequestMapping(value = "/table")
public class TableController {

	private static Logger logger = LogManager.getLogger(TableController.class);

	@Autowired
	private TableServiceI tableServiceI;

	@RequestMapping(value = "/getTable", method = RequestMethod.GET)
	public @ResponseBody TableData getTable() {
		System.out.println("已进入getTable请求方法！！！+++++++++++++++++++++");
		String json = "";
		try {
			System.out.println(JSONObject.toJSONString(tableServiceI.getAllUserData()));
			File file = org.springframework.util.ResourceUtils.getFile("classpath:templates/files/json.json");
			String encoding = "UTF-8";
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					System.out.println("文件*" + file.getName() + "*的内容为：" + lineTxt);
					json = lineTxt;
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}

		TableData tableData = JSONObject.parseObject(json, TableData.class);

		return tableData;
	}

	@RequestMapping(value = "/saveTable", method = RequestMethod.GET)
	public @ResponseBody int saveTable() {
		System.out.println("已进入saveTable请求方法！！！+++++++++++++++++++++");
		String json = "";
		int reIndex = 0;
		try {
			File file = org.springframework.util.ResourceUtils.getFile("classpath:templates/files/json.json");
			String encoding = "UTF-8";
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					System.out.println("文件*" + file.getName() + "*的内容为：" + lineTxt);
					json = lineTxt;
					//
					TableData tableData = JSONObject.parseObject(json, TableData.class);
					reIndex = tableServiceI.saveListUserData(tableData.getData());
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}

		return reIndex;
	}

	@RequestMapping(value = "/tableUserData", method = RequestMethod.GET)
	public @ResponseBody PageInfo<UserData> tableUserData(@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "size", defaultValue = "5") int size) {
		// 1. 在参数里接受当前是第几页 start ，以及每页显示多少条数据 size。 默认值分别是0和5。
		// 2. 根据start,size进行分页，并且设置id 倒排序
		System.out.println("start==》"+start+"》》》》》》》》》》》size==》"+size);
		PageInfo<UserData> pageInfo = null;
//		Page<UserData> page = null;
		try {
			PageHelper.startPage(start, size);
			// 3. 因为PageHelper的作用，这里就会返回当前分页的集合了
			List<UserData> userDatas = tableServiceI.getAllUserData();
			pageInfo = new PageInfo<UserData>(userDatas);
		} catch (Exception ex) {
			System.out.println(ex.getCause());
		}
//		pageInfo.setList(null);
		return pageInfo;
	}
	
	@RequestMapping(value = "/updateUserDataRow", method = RequestMethod.GET)
	public @ResponseBody boolean updateUserDataRow(UserData userData){
		System.out.println(JSONObject.toJSONString(userData));
		return tableServiceI.updateUserData(userData);
	}
	
	@RequestMapping(value = "/delUserDataRow", method = RequestMethod.GET)
	public @ResponseBody boolean delUserDataRow(Integer id){
		System.out.println(JSONObject.toJSONString(id));
		return tableServiceI.delUserData(id);
	}
}
