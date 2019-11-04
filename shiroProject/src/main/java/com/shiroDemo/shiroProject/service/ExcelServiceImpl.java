package com.shiroDemo.shiroProject.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shiroDemo.shiroProject.entity.DemoExcel;
import com.shiroDemo.shiroProject.entity.UserData;
import com.shiroDemo.shiroProject.exceptions.MyException;
import com.shiroDemo.shiroProject.mapper.ExcelMapper;
import com.shiroDemo.shiroProject.service.interf.ExcelServiceI;

@Service
@Transactional
public class ExcelServiceImpl implements ExcelServiceI {

	@Autowired
	private ExcelMapper excelMapper;

	private static Logger logger = LogManager.getLogger(ExcelServiceImpl.class);

	@Override
	public List<DemoExcel> queryDemoExcel() {
		List<DemoExcel> list = excelMapper.queryDemoExcel();
		return list;
	}

	@SuppressWarnings("deprecation")
	public Map batchImport(String fileName, MultipartFile file) {
		Map infoMap = new HashMap();
		List lists = new ArrayList<>();
		if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
			infoMap.put("info", "上传文件格式不正确");
			infoMap.put("status", 1);
			return infoMap;
		}
		boolean isExcel2003 = true;
		if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
			isExcel2003 = false;
		}
		InputStream is = null;
		try {
			is = file.getInputStream();
		} catch (IOException e) {
			infoMap.put("info", e.getCause().toString());
			infoMap.put("status", 1);
			return infoMap;
		}
		Workbook wb = null;
		if (isExcel2003) {
			try {
				wb = new HSSFWorkbook(is);
			} catch (IOException e) {
				infoMap.put("info", e.getCause().toString());
				infoMap.put("status", 1);
				return infoMap;
			}
		} else {
			try {
				wb = new XSSFWorkbook(is);
			} catch (IOException e) {
				infoMap.put("info", e.getCause().toString());
				infoMap.put("status", 1);
				return infoMap;
			}
		}
		Sheet sheet = wb.getSheetAt(0);// 获取第一页
		if (sheet == null) {
			infoMap.put("info", "文件无有效数据");
			infoMap.put("status", 1);
			return infoMap;
		}
		
		//---
		for (int r = 1; r <= sheet.getLastRowNum(); r++) {// r = 1 表示从第二行开始循环
			Map<String, Object> maps = new HashMap<String, Object>();// 如果你的第三行开始是数据
			Row row = sheet.getRow(r);// 通过sheet表单对象得到 行对象
			if (row == null) {
				continue;
			}
			
			if (null == row.getCell(1) || row.getCell(1).toString().isEmpty()) {
				infoMap.put("info", "导入失败(第" + (r + 1) + "行,用户名称为空)");
				infoMap.put("status", 1);
				return infoMap;
			}
			row.getCell(1).setCellType(HSSFCell.CELL_TYPE_STRING);
			String username = row.getCell(1).getStringCellValue();

			if (null == row.getCell(2) || row.getCell(2).toString().isEmpty()) {
				infoMap.put("info", "导入失败(第" + (r + 1) + "行,性别为空)");
				infoMap.put("status", 1);
				return infoMap;
			}
			row.getCell(2).setCellType(HSSFCell.CELL_TYPE_STRING);
			String sex = row.getCell(2).getStringCellValue();

			if (null == row.getCell(3) || row.getCell(3).toString().isEmpty()) {
				infoMap.put("info", "导入失败(第" + (r + 1) + "行,城市为空)");
				infoMap.put("status", 1);
				return infoMap;
			}
			row.getCell(3).setCellType(HSSFCell.CELL_TYPE_STRING);
			String city = row.getCell(3).getStringCellValue();

			if (null == row.getCell(4) || row.getCell(4).toString().isEmpty()) {
				infoMap.put("info", "导入失败(第" + (r + 1) + "签名为空)");
				infoMap.put("status", 1);
				return infoMap;
			}
			row.getCell(4).setCellType(HSSFCell.CELL_TYPE_STRING);
			String sign = row.getCell(4).getStringCellValue();

			if (null == row.getCell(5) || row.getCell(5).toString().isEmpty()) {
				infoMap.put("info", "导入失败(第" + (r + 1) + "行,积分为空)");
				infoMap.put("status", 1);
				return infoMap;
			}
			row.getCell(5).setCellType(HSSFCell.CELL_TYPE_STRING);
			String experience = row.getCell(5).getStringCellValue();

			if (null == row.getCell(6) || row.getCell(6).toString().isEmpty()) {
				infoMap.put("info", "导入失败(第" + (r + 1) + "行,登录次数为空)");
				infoMap.put("status", 1);
				return infoMap;
			}
			row.getCell(6).setCellType(HSSFCell.CELL_TYPE_STRING);
			String logins = row.getCell(6).getStringCellValue();

			if (null == row.getCell(7) || row.getCell(7).toString().isEmpty()) {
				infoMap.put("info", "导入失败(第" + (r + 1) + "行,财富为空)");
				infoMap.put("status", 1);
				return infoMap;
			}
			row.getCell(7).setCellType(HSSFCell.CELL_TYPE_STRING);
			String wealth = row.getCell(7).getStringCellValue();

			if (null == row.getCell(8) || row.getCell(8).toString().isEmpty()) {
				infoMap.put("info", "导入失败(第" + (r + 1) + "行,职业为空)");
				infoMap.put("status", 1);
				return infoMap;
			}
			row.getCell(8).setCellType(HSSFCell.CELL_TYPE_STRING);
			String classify = row.getCell(8).getStringCellValue();

			if (null == row.getCell(9) || row.getCell(9).toString().isEmpty()) {
				infoMap.put("info", "导入失败(第" + (r + 1) + "行,评分为空)");
				infoMap.put("status", 1);
				return infoMap;
			}
			row.getCell(9).setCellType(HSSFCell.CELL_TYPE_STRING);
			String score = row.getCell(9).getStringCellValue();

			maps.put("username", username);
			maps.put("sex", sex);
			maps.put("city", city);
			maps.put("sign", sign);
			maps.put("experience", experience);
			maps.put("logins", logins);
			maps.put("wealth", wealth);
			maps.put("classify", classify);
			maps.put("score", score);
			lists.add(maps);
		}
		try {
			Map<String, List<Map>> tempMap = new HashMap<String, List<Map>>();
			tempMap.put("userDataList", lists);
//			System.out.println(JSON.toJSONString(lists));
			//返回插入行数
			System.out.println(excelMapper.insertExcelUserData(tempMap));
			infoMap.put("info", "success");
			infoMap.put("status", 0);
			return infoMap;
		} catch (Exception e) {
			infoMap.put("info", e.getCause());
			infoMap.put("status", 1);
			return infoMap;
		}
	}

}
