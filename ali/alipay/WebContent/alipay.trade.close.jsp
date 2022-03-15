<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>交易关闭</title>
</head>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.alipay.config.*"%>
<%@ page import="com.alipay.api.*"%>
<%@ page import="com.alipay.api.request.*"%>
<%
	//商户订单号，商户网站订单系统中唯一订单号
	String out_trade_no = new String(request.getParameter("WIDTCout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
	//支付宝交易号
	String trade_no = new String(request.getParameter("WIDTCtrade_no").getBytes("ISO-8859-1"),"UTF-8");
	//请二选一设置
	String app_auth_token = new String(request.getParameter("app_auth_token").getBytes("ISO-8859-1"),"UTF-8");
	
	//请求
	String result = Implements.cancel(out_trade_no, trade_no,app_auth_token).getBody();
	//输出
	out.println(result);
%>
<body>
		
</body>
</html>