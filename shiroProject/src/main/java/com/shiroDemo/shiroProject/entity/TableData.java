package com.shiroDemo.shiroProject.entity;

import java.io.Serializable;
import java.util.List;

public class TableData implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -8212892235541709719L;
	private int code;
	private String msg;
	private int count;
	private List<UserData> data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<UserData> getData() {
		return data;
	}

	public void setData(List<UserData> data) {
		this.data = data;
	}
}
