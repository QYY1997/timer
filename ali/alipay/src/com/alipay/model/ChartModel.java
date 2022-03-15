package com.alipay.model;

import java.math.BigDecimal;
import java.util.List;


public class ChartModel {
	private int year=0;
	//年份
	
	private List<Integer> years;
	//年份数组
	
	private int month=0;
	//月份
	
	private int level=0;
	//商户等级
	
	private int type=1;
	//返佣还是流水

	private List<BigDecimal> agent1;	

	private List<BigDecimal> agent2;	

	private List<BigDecimal> agent3;	

	private List<BigDecimal> agent4;	

	private List<BigDecimal> agent_all;

	private List<Charts> list;

	public List<BigDecimal> getAgent1() {
		return agent1;
	}

	public void setAgent1(List<BigDecimal> agent1) {
		this.agent1 = agent1;
	}

	public List<BigDecimal> getAgent2() {
		return agent2;
	}

	public void setAgent2(List<BigDecimal> agent2) {
		this.agent2 = agent2;
	}

	public List<BigDecimal> getAgent3() {
		return agent3;
	}

	public void setAgent3(List<BigDecimal> agent3) {
		this.agent3 = agent3;
	}

	public List<BigDecimal> getAgent4() {
		return agent4;
	}

	public void setAgent4(List<BigDecimal> agent4) {
		this.agent4 = agent4;
	}

	public List<BigDecimal> getAgent_all() {
		return agent_all;
	}

	public void setAgent_all(List<BigDecimal> agent_all) {
		this.agent_all = agent_all;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public List<Integer> getYears() {
		return years;
	}

	public void setYears(List<Integer> years) {
		this.years = years;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<Charts> getList() {
		return list;
	}

	public void setList(List<Charts> list) {
		this.list = list;
	}
	
}
