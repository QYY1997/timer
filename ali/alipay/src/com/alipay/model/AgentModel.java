package com.alipay.model;

import java.util.List;


public class AgentModel {
	/**
	 * 当前查询的类别id
	 */
	private int level;
	
	/**
	 * 关键字
	 */
	private String keyword="";
	
	/**
	 * 控制翻页
	 */
	private int page=0;
	private int pagesize=20;
	
	private int total;
	private int totalpage;
	
	public int getOffset() {
		return page*pagesize;
	}
	private int uid;
	
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}
	
	private List<Agent> agent_list;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

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

	public List<Agent> getAgent_list() {
		return agent_list;
	}

	public void setAgent_list(List<Agent> agent_list) {
		this.agent_list = agent_list;
	}
	
}
