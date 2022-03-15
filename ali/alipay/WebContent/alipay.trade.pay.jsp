<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.alipay.tps.Dao"%>
<%@page import="com.alipay.api.response.AlipayTradeQueryResponse"%>
<%@page import="com.alipay.api.response.AlipayTradePrecreateResponse"%>
<%@page import="net.sf.json.JSONException"%>
<html>
<head>
<!--  <meta http-equiv="refresh" content="2"> -->
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>付款</title>
</head>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.alipay.config.AlipayConfig"%>
<%@ page import="com.alipay.api.*"%>
<%@ page import="com.alipay.api.request.*"%>
<%@ page import="com.alipay.config.*"%>
<%

String out_trade_no="",total_amount="",subject="",auth_code="",app_auth_token="";
boolean success=true;

out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
//商户订单号，商户网站订单系统中唯一订单号，必填
try{
	app_auth_token = new String(request.getParameter("app_auth_token").getBytes("ISO-8859-1"),"UTF-8");
	System.out.print(app_auth_token);
	//订单名称，必填
		 subject = new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"),"UTF-8");
	//付款金额，必填
	 total_amount = new String(request.getParameter("WIDtotal_amount").getBytes("ISO-8859-1"),"UTF-8");
	//支付授权码，必填
	auth_code = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");
	
	//请求
	String result="";
	if(!auth_code.equals("")){
	result =Implements.pay(out_trade_no,total_amount,subject,auth_code,app_auth_token).getBody();
	out.print(Dao.add_pay(out_trade_no, subject, app_auth_token)?"记录保存成功":"记录保存失败");
	out.println("<br>");
	
	
	}
	else{
		AlipayTradePrecreateResponse precreateResponse=Implements.precreate(out_trade_no, total_amount, subject,app_auth_token);
		result=precreateResponse.getBody();
		if(!precreateResponse.isSuccess()){
		out.print("预下单失败！"+"<br>");
		success=false;
		
		}
		else{
			out.print("预下单成功！"+"<br>");
			out.println(Implements.create(out_trade_no, total_amount, subject, app_auth_token).getBody());
			}
	
	} 
	//输出
	out.println(result+"<br>"); 
}
catch(Exception e){
	 if(total_amount.equals("")){
			subject=URLDecoder.decode(request.getParameter("WIDsubject"),"UTF-8");
		}
}
	
	
%>
<body>
		<%=app_auth_token %>
<%if(auth_code.equals("") && success ){
	if(total_amount.equals("")){%>

<img src="D:/Q/ali/qrcode.jpg" alt="二维码" width="400" height="400">
<br>
<%
}
subject=URLEncoder.encode(subject,"UTF-8");
AlipayTradeQueryResponse alipayTradeQueryResponse= Implements.query(out_trade_no,"",app_auth_token);
if(alipayTradeQueryResponse.getMsg().toString().equals("Business Failed")){
out.println("等待支付中……"+"<br>");
out.println(alipayTradeQueryResponse.getBody().toString()+"<br>");
response.setHeader("Refresh", "2;http://localhost:8080/QYY/alipay.trade.pay.jsp?WIDout_trade_no="+out_trade_no+"&app_auth_token="+app_auth_token+"&WIDsubject="+subject);
}
else if(alipayTradeQueryResponse.getMsg().toString().equals("Success")){

	if(alipayTradeQueryResponse.getTradeStatus().toString().equals("TRADE_SUCCESS")){
	out.println("支付成功！"+"<br>");
	subject=URLDecoder.decode(subject,"UTF-8");
	out.print((Dao.add_pay(out_trade_no, subject, app_auth_token)?"记录保存成功":"记录保存失败")+"<br>");
	
	out.println(alipayTradeQueryResponse.getBody().toString()+"<br>");
	}
	else if(alipayTradeQueryResponse.getTradeStatus().toString().equals("WAIT_BUYER_PAY"))
	{
		out.println("买家已扫码！"+"<br>");
		out.println("subject="+URLDecoder.decode(subject,"UTF-8")+"<br>");
		out.println(alipayTradeQueryResponse.getBody().toString()+"");
		response.setHeader("Refresh", "2;http://localhost:8080/QYY/alipay.trade.pay.jsp?WIDout_trade_no="+out_trade_no+"&app_auth_token="+app_auth_token+"&WIDsubject="+subject);
	}
	else if(alipayTradeQueryResponse.getTradeStatus().toString().equals("TRADE_CLOSED")){
		out.println("交易已关闭！"+"<br>");
		out.println(alipayTradeQueryResponse.getBody().toString()+"<br>");
		out.println("<br>");
		}
	else if(alipayTradeQueryResponse.getTradeStatus().toString().equals("TRADE_FINISHED")){
		out.println("交易已完结！"+"<br>");
		out.println(alipayTradeQueryResponse.getBody().toString()+"<br>");
		out.println("<br>");
		}
	else{
		out.println(alipayTradeQueryResponse.getBody().toString()+"<br>");
		out.println("<br>");
	}
}
else{
	out.println(alipayTradeQueryResponse.getBody().toString()+"<br>");
	response.setHeader("Refresh", "2;http://localhost:8080/QYY/alipay.trade.pay.jsp?WIDout_trade_no="+out_trade_no+"&app_auth_token="+app_auth_token+"&WIDsubject="+subject);
}
}%>
</body>
</html>