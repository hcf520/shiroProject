package com.shiroDemo.shiroProject.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.shiroDemo.shiroProject.entity.DemoExcel;
import com.shiroDemo.shiroProject.service.interf.ExcelServiceI;

@Controller
@RequestMapping(value = "/excel")
public class ExcelController {

	private static Logger logger = LogManager.getLogger(ExcelController.class);

	@Autowired
	private ExcelServiceI excelServiceI;

	@RequestMapping(value = "/excelExport", method = RequestMethod.GET)
	public void export(HttpServletResponse response) {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		// 将查询到结果遍历
		List<DemoExcel> demoExcels = excelServiceI.queryDemoExcel();
		String jsonStrDemoExcels = JSONObject.toJSONString(demoExcels);
		System.err.println("jsonStrDemoExcels:" + jsonStrDemoExcels);
		HSSFWorkbook wb = new HSSFWorkbook();

		HSSFSheet sheet = wb.createSheet("获取excel测试表格");

		HSSFRow row = null;

		row = sheet.createRow(0);// 创建第一个单元格
		row.setHeight((short) (26.25 * 20));
		row.createCell(0).setCellValue("用户信息列表");// 为第一行单元格设值

		/*
		 * 为标题设计空间 firstRow从第1行开始 lastRow从第0行结束
		 *
		 * 从第1个单元格开始 从第3个单元格结束
		 */
		CellRangeAddress rowRegion = new CellRangeAddress(0, 0, 0, 2);
		sheet.addMergedRegion(rowRegion);

		/*
		 * CellRangeAddress columnRegion = new CellRangeAddress(1,4,0,0);
		 * sheet.addMergedRegion(columnRegion);
		 */

		/*
		 * 动态获取数据库列 sql语句 select COLUMN_NAME from INFORMATION_SCHEMA.Columns
		 * where table_name='user' and table_schema='test' 第一个table_name 表名字
		 * 第二个table_name 数据库名称
		 */
		row = sheet.createRow(1);
		row.setHeight((short) (22.50 * 20));// 设置行高
		row.createCell(0).setCellValue("Id");// 为第一个单元格设值
		row.createCell(1).setCellValue("Name");// 为第二个单元格设值
		row.createCell(2).setCellValue("ImportTime");// 为第三个单元格设值

		DataFormatter dfm = new DataFormatter();

		for (int i = 0; i < demoExcels.size(); i++) {
			row = sheet.createRow(i + 2);
			DemoExcel demoExcel = (DemoExcel) demoExcels.get(i);
			row.createCell(0).setCellValue(demoExcel.getId());
			row.createCell(1).setCellValue(demoExcel.getName());
			row.createCell(2).setCellValue(df.format(demoExcel.getImportTime()));
			;
		}

		sheet.setDefaultRowHeight((short) (16.5 * 20));
		// 列宽自适应
		for (int i = 0; i <= 13; i++) {
			sheet.autoSizeColumn(i);
		}

		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		OutputStream os = null;
		try {
			os = response.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setHeader("Content-disposition", "attachment;filename=user.xls");// 默认Excel名称
		try {
			wb.write(os);
			os.flush();
			os.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	public @ResponseBody Map importExcel(@RequestParam("fileExcel") MultipartFile excelForDemo,
			HttpServletRequest request) throws UnsupportedEncodingException {
//		System.err.println(excelForDemo.getName());
//		System.err.println(excelForDemo.getOriginalFilename());
		String fileName = excelForDemo.getOriginalFilename();

		Map infoMap = excelServiceI.batchImport(fileName, excelForDemo);
		System.out.println("infoMap==========>" + infoMap);
		return infoMap;
	}

	@RequestMapping(value = "/targetExcel", method = RequestMethod.GET)
	public String targetExcel() {

		return "/excelDemo";
	}
}
