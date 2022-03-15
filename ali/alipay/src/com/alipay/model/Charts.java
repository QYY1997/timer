package com.alipay.model;

import java.math.BigDecimal;
import java.util.List;

public class Charts {	
	private String agent_uid;
	//支付宝id

	private List<BigDecimal> amount;
	//总金额

	public String getAgent_uid() {
		return agent_uid;
	}

	public void setAgent_uid(String agent_uid) {
		this.agent_uid = agent_uid;
	}

	public List<BigDecimal> getAmount() {
		return amount;
	}

	public void setAmount(List<BigDecimal> amount) {
		this.amount = amount;
	}
	
}
