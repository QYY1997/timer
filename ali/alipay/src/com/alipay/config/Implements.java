package com.alipay.config;


import com.alipay.Utils.QRCodeUtils;
import com.alipay.api.*;
import com.alipay.api.request.*;
import com.alipay.api.response.AlipayOpenAuthTokenAppQueryResponse;
import com.alipay.api.response.AlipayOpenAuthTokenAppResponse;
import com.alipay.api.response.AlipayTradeCancelResponse;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradePayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.alipay.config.AlipayConfig;

public class Implements {
	public static AlipayTradePayResponse pay(String out_trade_no,String total_amount,String subject,String auth_code,String token) throws AlipayApiException{
			//获得初始化的AlipayClient
			AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
			//设置请求参数
			AlipayTradePayRequest alipayRequest = new AlipayTradePayRequest();
			alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
			if(!token.equals("")) {
	        alipayRequest.putOtherTextParam("app_auth_code",token);
			}
			alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
					+ "\"total_amount\":\""+ total_amount +"\"," 
					+ "\"subject\":\""+ subject +"\"," 
					+ "\"auth_code\":\""+ auth_code +"\"," 
					+ "\"timeout_express\":\"60m\"," 
					+ "\"extend_params\":{\"sys_service_provider_id\":\""+ AlipayConfig.sys_service_provider_id +"\"}," 
					+ "\"scene\":\"bar_code\"}");		
			AlipayTradePayResponse payResponse= alipayClient.execute(alipayRequest);
			//请求
			String result = payResponse.getBody();
			
			//输出
		   AlipayConfig.logResult(result+alipayRequest.getBizContent().toString(),"pay",out_trade_no);
			
			return payResponse;
	}
	
	
	public static AlipayTradeRefundResponse refund(String out_trade_no,String trade_no,String refund_amount,String refund_reason,String out_request_no,String token ) throws AlipayApiException{
	//获得初始化的AlipayClient
	AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
	 
	//设置请求参数
	AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
	if(!token.equals("")) {
        alipayRequest.putOtherTextParam("app_auth_code",token);
		}
	alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
			+ "\"trade_no\":\""+ trade_no +"\"," 
			+ "\"refund_amount\":\""+ refund_amount +"\"," 
			+ "\" refund_reason\":\""+  refund_reason +"\"," 
			+ "\"out_request_no\":\""+ out_request_no +"\"}");
	
	//请求
	AlipayTradeRefundResponse response = alipayClient.execute(alipayRequest);
	String result = response.getBody();
	
	//输出
	AlipayConfig.logResult(result+alipayRequest.getBizContent().toString(),"refund",out_trade_no);
	
	return response;
	
	}
	public static AlipayTradeCancelResponse cancel(String out_trade_no,String trade_no,String token) throws AlipayApiException{
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
		
		//设置请求参数
		
		AlipayTradeCancelRequest alipayRequest = new AlipayTradeCancelRequest();
		if(!token.equals("")) {
	        alipayRequest.putOtherTextParam("app_auth_code",token);
			}
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
				+ "\"trade_no\":\""+ trade_no +"\"}");
	

		//请求
		AlipayTradeCancelResponse response = alipayClient.execute(alipayRequest);
		String result = response.getBody();
		
		//输出
		AlipayConfig.logResult(result+alipayRequest.getBizContent().toString(),"cencel",out_trade_no);
		
		return response;
		
		}
	public static AlipayTradeQueryResponse query(String out_trade_no,String trade_no,String token) throws AlipayApiException{
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
		
		//设置请求参数
		AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
		if(!token.equals("")) {
	        alipayRequest.putOtherTextParam("app_auth_code",token);
			}
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
				+ "\"trade_no\":\""+ trade_no +"\"}");
	  
	    AlipayTradeQueryResponse response= alipayClient.execute(alipayRequest);
		//请求
		String result =response.getBody();
		
		//输出
		AlipayConfig.logResult(result+alipayRequest.getBizContent().toString(),"query",out_trade_no);
		
		return response;
		
		}
	public static AlipayTradeFastpayRefundQueryResponse refund_query(String out_trade_no,String trade_no,String out_request_no,String token) throws AlipayApiException{
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
		 if(out_request_no.equals("")) {
			   out_request_no=out_trade_no;
		   }
		//设置请求参数
		AlipayTradeFastpayRefundQueryRequest alipayRequest = new AlipayTradeFastpayRefundQueryRequest();
		if(!token.equals("")) {
	        alipayRequest.putOtherTextParam("app_auth_code",token);
			}
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
				+ "\"trade_no\":\""+ trade_no +"\","
				+"\"out_request_no\":\""+ out_request_no +"\"}");
	   

		//请求
		AlipayTradeFastpayRefundQueryResponse response = alipayClient.execute(alipayRequest);
		String result = response.getBody();
		
		//输出
		AlipayConfig.logResult(result+alipayRequest.getBizContent().toString(),"refund_query",out_trade_no);
		
		return response;
		
		}
	public static 	AlipayOpenAuthTokenAppResponse openauth(String app_auth_code,boolean type) throws AlipayApiException{
			//获得初始化的AlipayClient
			AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
			
			AlipayOpenAuthTokenAppRequest request = new AlipayOpenAuthTokenAppRequest(); 
			if(type) {
			request.setBizContent("{" +"\"grant_type\":\"authorization_code\"," +   
			                    " \"code\":\""+app_auth_code+"\"}");
			}
			else {
				request.setBizContent("{" +"\"grant_type\":\"refresh_token\"," +   
	                    " \"code\":\""+app_auth_code+"\"}");
			}
			
			AlipayOpenAuthTokenAppResponse response =alipayClient.execute(request);
			//请求
			String result = response.getBody();
			
			//输出
			AlipayConfig.logResult(result+request.getBizContent().toString(),"openauth",response.getAppAuthToken());
			
			return response;
			
			}
	
	public static 	AlipayOpenAuthTokenAppQueryResponse openauth_query(String app_auth_token) throws AlipayApiException{
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
		
		AlipayOpenAuthTokenAppQueryRequest request = new AlipayOpenAuthTokenAppQueryRequest(); 
	
		request.setBizContent("{\"app_auth_token\":\""+app_auth_token+"\"}");
		
		AlipayOpenAuthTokenAppQueryResponse response =alipayClient.execute(request);
		//请求
		String result = response.getBody();
		
		//输出
		AlipayConfig.logResult(result+request.getBizContent().toString(),"openauth_query",app_auth_token);
		
		return response;
		
		}
	public static AlipayTradePrecreateResponse precreate(String out_trade_no,String total_amount,String subject,String token) throws AlipayApiException{
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
		
		//设置请求参数
		AlipayTradePrecreateRequest alipayRequest = new AlipayTradePrecreateRequest();
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
				+ "\"total_amount\":\""+ total_amount +"\"," 
				+ "\"qr_code_timeout_express\":\"30m\"," 
				+ "\"extend_params\":{\"sys_service_provider_id\":\""+ AlipayConfig.sys_service_provider_id +"\"}," 
				+ "\"subject\":\""+ subject +"\"}");
		if(!token.equals("")) {
	        alipayRequest.putOtherTextParam("app_auth_code",token);
			}

		//请求
		
		AlipayTradePrecreateResponse response = alipayClient.execute(alipayRequest);
		String result=response.getBody();
		String qrcode = response.getQrCode();
		QRCodeUtils.generate(qrcode,"D:/Q/ali/qrcode.jpg");
		//输出
		AlipayConfig.logResult(result+alipayRequest.getBizContent().toString(),"precreate",out_trade_no);
		
		return response;
		
		}
	public static AlipayTradeCreateResponse create(String out_trade_no,String total_amount,String subject,String token) throws AlipayApiException{
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
		
		//设置请求参数
		AlipayTradeCreateRequest alipayRequest = new AlipayTradeCreateRequest();
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
				+ "\"total_amount\":\""+ total_amount +"\"," 
				+ "\"timeout_express\":\"30m\"," 
				+ "\"extend_params\":{\"sys_service_provider_id\":\""+ AlipayConfig.sys_service_provider_id +"\"}," 
				+ "\"subject\":\""+ subject +"\"}");
		if(!token.equals("")) {
	        alipayRequest.putOtherTextParam("app_auth_code",token);
			}

		//请求
		
		AlipayTradeCreateResponse response = alipayClient.execute(alipayRequest);
		String result=response.getBody();
		//输出
		AlipayConfig.logResult(result+alipayRequest.getBizContent().toString(),"create",out_trade_no);
		
		return response;
		
		}
	
}
