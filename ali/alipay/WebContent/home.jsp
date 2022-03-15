<%@page import="com.alipay.api.response.AlipayOpenAuthTokenAppQueryResponse"%>
<%@page import="com.alipay.api.response.AlipayOpenAuthTokenAppResponse"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.alipay.config.*"%>
<%@ page import="com.alipay.api.*"%>
<%@ page import="com.alipay.tps.*"%>
<%@ page import="com.alipay.api.internal.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>支付宝沙盒模拟对接</title>
	<style>
	body{ text-align:center} 
	.button {
	  display: inline-block;
	  padding: 10px 20px;
	  font-size: 20px;
	  cursor: pointer;
	  magrgin:20px;
	  text-align: center;   
	  text-decoration: none;
	  outline: none;
	  color: #fff;
	  background-color: #4CAF50;
	  border: none;
	  border-radius: 15px;
	  box-shadow: 0 8px #999;
	}

	.button:hover {background-color: #3e8e41}

	.button:active {
	  background-color: #3e8e41;	
	  box-shadow: 0 4px #666;
	  transform: translateY(2px);
	}
	 .div{
	 width: 1600px;
	 height: 450px;
     position: absolute;
     top:50%;
     left:50%;
     margin:-225px 0 0 -800px;
        }
	</style>
</head>

<body>
	<div class="div" >
<div style="font-size:100px;color:white;top:50%" ><font >支付宝沙盒模拟对接系统</font></div> 
<div style="width=50%">
		
		<button class="button" type="button"  
		onclick="window.location.href='tp.svlt?key=Merchant.search'">商户信息表</button>
	    <button class="button" type="button"  
		onclick="window.location.href='tp.svlt?key=Tokensql.search'">商户令牌表</button>
		<button class="button" type="button"  
	    onclick="window.location.href='index.jsp'">调用接口页</button>
		<button class="button" type="button"  
		onclick="window.location.href='tp.svlt?key=Paysql.search'">交易记录表</button>
		<button class="button" type="button"  
		onclick="window.location.href='tp.svlt?key=Chart.search'">汇总统计图</button>
</div>	
</div>
		<script src="assets/js/jquery-1.11.1.min.js"></script>
		<script src="assets/bootstrap/js/bootstrap.min.js"></script>
		<script src="assets/js/jquery.backstretch.min.js"></script>
		<script src="assets/js/scripts.js"></script>
		<script src="frameworks/iceking-utils-jquery.js"></script>
</body>
</html>