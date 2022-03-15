<%@page import="com.alipay.tps.Dao"%>
<%@page import="com.alipay.api.response.AlipayOpenAuthTokenAppQueryResponse"%>
<%@page import="com.alipay.api.response.AlipayOpenAuthTokenAppResponse"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.alipay.config.*"%>
<%@ page import="com.alipay.api.*"%>
<%
String app_id = new String(request.getParameter("app_id").getBytes("ISO-8859-1"),"UTF-8");
String app_auth_code = new String(request.getParameter("app_auth_code").getBytes("ISO-8859-1"),"UTF-8");

	if(app_id.equals(AlipayConfig.app_id)) {//验证成功
	
		AlipayOpenAuthTokenAppResponse AOATAResponse= Implements.openauth(app_auth_code, true);
		if(Dao.add_token(AOATAResponse.getAppAuthToken())){
		out.println("授权令牌保存成功，请返回商家令牌表刷新查看！");
		}
		else{
			out.println("授权令牌保存失败！");
		}
		out.println(AOATAResponse.getBody());	
	}else {//验证失败
		if(app_id.equals("1")){
			AlipayOpenAuthTokenAppResponse AOATAResponse= Implements.openauth(app_auth_code, false);
			out.println(AOATAResponse.getBody());
		}
	else if(app_id.equals("2")){
		AlipayOpenAuthTokenAppQueryResponse AOATAQResponse= Implements.openauth_query(app_auth_code);
		out.println(AOATAQResponse.getBody());
	}
	else{
		out.println("fail");
	}
	}	
%>