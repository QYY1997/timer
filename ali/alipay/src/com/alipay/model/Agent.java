package com.alipay.model;

public class Agent {	
	private int id;
	//数据库序号
	
	private String agent_name;
	//姓名
	
	private String agent_uid;
	//支付宝id
	
	private int level;
	//等级：1为服务商；2为一级代理商；3二级为代理商；4为商户
	
	private String superior;
	//上级id ：采用支付宝id
	
	private Double amount;
	//总金额
	
	private Double rate;
	//费率 服务商费率+一级代理商费率+二级代理商费率=商户费率<=0.6%
	
    private Double commission=Double.valueOf(0);
	//服务商和代理商是总佣金；商户是总费用
    
	public String getAgent_name() {
		return agent_name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}
	public String getAgent_uid() {
		return agent_uid;
	}
	public void setAgent_uid(String agent_uid) {
		this.agent_uid = agent_uid;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getSuperior() {
		return superior;
	}
	public void setSuperior(String superior) {
		this.superior = superior;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public Double getCommission() {
		return commission;
	}
	public void setCommission(Double commission) {
		this.commission = commission;
	}
	
}
