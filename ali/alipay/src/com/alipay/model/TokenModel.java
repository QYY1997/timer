package com.alipay.model;

import java.util.List;


public class TokenModel {
	/**
	 * 控制翻页
	 */
	private int page=0;
	private int pagesize=10;
	
	private int total;
	private int totalpage;
	/**
	 * 关键字
	 */
	private String keyword="";
	
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
	private List<Token> token_list;
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
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

	public List<Token> getToken_list() {
		return token_list;
	}

	public void setToken_list(List<Token> token_list) {
		this.token_list = token_list;
	}



	
	
}
