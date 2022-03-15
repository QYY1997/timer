package com.alipay.model;

import java.util.List;


public class PayModel {
	/**
	 * 关键字
	 */
	private String keyword="";
	
	/**
	 * 控制翻页
	 */
	private int page=0;
	private int pagesize=10;
	
	private int total;
	private int totalpage;
	
	public int getOffset() {
		return page*pagesize;
	}
	private String uid="";
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	private List<Pay> pay_list;


	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTotalpage() {
		return totalpage;
	}

	public void setTotalpage(int totalpage) {
		this.totalpage = totalpage;
	}

	public List<Pay> getPay_list() {
		return pay_list;
	}

	public void setPay_list(List<Pay> pay_list) {
		this.pay_list = pay_list;
	}

	
}
