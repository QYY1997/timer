package com.alipay.model;

public class Pay {
	private int id;
	//数据库序号
	
	private String out_trade_no;
	//商家订单号
	
	private String trade_no;
	//支付宝交易号
	
	private String subject;
	//订单标题
	
	private double total_amount;
	//订单金额
	
	private double buyer_pay_amount;
	//买家实付金额
	
	private double receipt_amount;
	//商家实收金额

	private String buyer_user_id;
	//买家支付宝id
	
	private String seller_uid;
	//商家支付宝id
	
	private String send_pay_date;
	//交易时间
	
	private String trade_status;
	//交易状态
	
	private double refund_amount=0;
	//退款金额
	
	private String out_request_no="";
	//退款订单号
		
	private String gmt_refund_pay="";
	//退款时间
	
	private String refund_reason="";
	//退款原因
	
	private double effective_sum;
    //商户有效流水
	
	private String superior1;
	//一级上级ID
	
	private double commission1;
	//一级上级所得佣金
	
	private String superior2;
	//二级上级ID
	
	private double commission2;
	//二级上级所得佣金
	
	private String superior3;
	//服务商ID
	
	private double commission3;
	//服务商所得佣金
	

	public double getRefund_amount() {
		return refund_amount;
	}
	public void setRefund_amount(double refund_amount) {
		this.refund_amount = refund_amount;
	}
	public String getOut_request_no() {
		return out_request_no;
	}
	public void setOut_request_no(String out_request_no) {
		this.out_request_no = out_request_no;
	}
	public String getRefund_reason() {
		return refund_reason;
	}
	public void setRefund_reason(String refund_reason) {
		this.refund_reason = refund_reason;
	}
	public double getEffective_sum() {
		return effective_sum;
	}
	public void setEffective_sum(double effective_sum) {
		this.effective_sum = effective_sum;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public double getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}
	public double getBuyer_pay_amount() {
		return buyer_pay_amount;
	}
	public void setBuyer_pay_amount(double buyer_pay_amount) {
		this.buyer_pay_amount = buyer_pay_amount;
	}
	public double getReceipt_amount() {
		return receipt_amount;
	}
	public void setReceipt_amount(double receipt_amount) {
		this.receipt_amount = receipt_amount;
	}
	public String getBuyer_user_id() {
		return buyer_user_id;
	}
	public void setBuyer_user_id(String buyer_user_id) {
		this.buyer_user_id = buyer_user_id;
	}
	public String getSend_pay_date() {
		return send_pay_date;
	}
	public void setSend_pay_date(String send_pay_date) {
		this.send_pay_date = send_pay_date;
	}
	public String getTrade_status() {
		return trade_status;
	}
	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}
	public String getGmt_refund_pay() {
		return gmt_refund_pay;
	}
	public void setGmt_refund_pay(String gmt_refund_pay) {
		this.gmt_refund_pay = gmt_refund_pay;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSeller_uid() {
		return seller_uid;
	}
	public void setSeller_uid(String seller_uid) {
		this.seller_uid = seller_uid;
	}
	public String getSuperior1() {
		return superior1;
	}
	public void setSuperior1(String superior1) {
		this.superior1 = superior1;
	}
	public double getCommission1() {
		return commission1;
	}
	public void setCommission1(double commission1) {
		this.commission1 = commission1;
	}
	public String getSuperior2() {
		return superior2;
	}
	public void setSuperior2(String superior2) {
		this.superior2 = superior2;
	}
	public double getCommission2() {
		return commission2;
	}
	public void setCommission2(double commission2) {
		this.commission2 = commission2;
	}
	public String getSuperior3() {
		return superior3;
	}
	public void setSuperior3(String superior3) {
		this.superior3 = superior3;
	}
	public double getCommission3() {
		return commission3;
	}
	public void setCommission3(double commission3) {
		this.commission3 = commission3;
	}
	
	
}
