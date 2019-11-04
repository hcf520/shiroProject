package com.shiroDemo.shiroProject.service.interf;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.shiroDemo.shiroProject.entity.DemoExcel;

public interface ExcelServiceI {

	/**
	 * 
	* @Title: queryDemoExcel 
	* @Description: TODO 测试poi查询导出
	* @param @return
	* @return List<?>
	* @throws
	 */
	public List<DemoExcel> queryDemoExcel();
	
	/**
	 * 
	* @Title: batchImport 
	* @Description: TODO 导入Excel并插入数据
	* @param @param fileName
	* @param @param file
	* @param @return
	* @param @throws Exception
	* @return boolean
	* @throws
	 */
	public Map batchImport(String fileName, MultipartFile file);
	
}
