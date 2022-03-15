package com.alipay.model;

public class Token {
private int id;
//数据库序号
private String app_auth_token;
//授权令牌
private String auth_app_id;
//商户的应用id
private String auth_end;
//授权失效期
private String user_id;
//商户的支付宝id
private String status;
//授权状态
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getApp_auth_token() {
	return app_auth_token;
}

public void setApp_auth_token(String app_auth_token) {
	this.app_auth_token = app_auth_token;
}
public String getAuth_app_id() {
	return auth_app_id;
}
public void setAuth_app_id(String auth_app_id) {
	this.auth_app_id = auth_app_id;
}
public String getAuth_end() {
	return auth_end;
}
public void setAuth_end(String auth_end) {
	this.auth_end = auth_end;
}
public String getUser_id() {
	return user_id;
}
public void setUser_id(String user_id) {
	this.user_id = user_id;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}



}
