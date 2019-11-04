package com.shiroDemo.shiroProject.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @ClassName: DemoExcel
 * @Description: TODO easyexcel 测试
 * @author A18ccms a18ccms_gmail_com
 * @date 2019年1月28日 上午9:24:56
 *
 */
public class DemoExcel implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 2318334635041119267L;

	private int id;

	private String name;

	private Date importTime;

	public DemoExcel() {
	}

	public DemoExcel(int id, String name, Date importTime) {
		this.id = id;
		this.name = name;
		this.importTime = importTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getImportTime() {
		return importTime;
	}

	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}
}
