<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.alipay.api.response.AlipayTradeRefundResponse"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>退款</title>
</head>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.alipay.config.*"%>
<%@page import="com.alipay.tps.Dao"%>
<%@ page import="com.alipay.api.*"%>
<%@ page import="com.alipay.api.request.*"%>
<%

	//商户订单号，商户网站订单系统中唯一订单号
	String out_trade_no = new String(request.getParameter("WIDTRout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
	//支付宝交易号
	String trade_no = new String(request.getParameter("WIDTRtrade_no").getBytes("ISO-8859-1"),"UTF-8");
	//请二选一设置
	//需要退款的金额，该金额不能大于订单金额，必填
	String refund_amount = new String(request.getParameter("WIDTRrefund_amount").getBytes("ISO-8859-1"),"UTF-8");
	//退款的原因说明
	String refund_reason = new String(request.getParameter("WIDTRrefund_reason").getBytes("ISO-8859-1"),"UTF-8");
	//标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
	String out_request_no = new String(request.getParameter("WIDTRout_request_no").getBytes("ISO-8859-1"),"UTF-8");
	String app_auth_token = new String(request.getParameter("app_auth_token").getBytes("ISO-8859-1"),"UTF-8");
	
	//请求
	 AlipayTradeRefundResponse refundResponse= Implements.refund(out_trade_no, trade_no, refund_amount, refund_reason, out_request_no,app_auth_token);
	 String result = refundResponse.getBody();
	if(refundResponse.getCode().equals("10000") && refundResponse.getFundChange().equals("Y")){
	Dao.add_refund(refundResponse,out_request_no,refund_reason,app_auth_token);
	}
	//输出
	out.println(result);
%>
<body>
</body>
</html>