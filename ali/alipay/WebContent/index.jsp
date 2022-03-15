<%@page import="com.alipay.tps.Dao"%>
<%@page import="com.alipay.config.AlipayConfig"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@page import="com.alipay.api.response.AlipayOpenAuthTokenAppQueryResponse"%>
<%@page import="com.alipay.api.response.AlipayOpenAuthTokenAppResponse"%>
<%@ page import="java.util.*"%>
<%@ page import="com.alipay.config.*"%>
<%@ page import="com.alipay.api.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>支付宝接口调用</title>
<style>
* {
	margin: 0;
	padding: 0;
}

ul, ol {
	list-style: none;
}

body {
	font-family: "Helvetica Neue", Helvetica, Arial, "Lucida Grande",
		sans-serif;
}

.tab-head {
	margin-left: 120px;
	margin-bottom: 10px;
}

.tab-content {
	clear: left;
	display: none;
}

h2 {
	border-bottom: solid #02aaf1 2px;
	width: 200px;
	height: 25px;
	margin: 0;
	float: left;
	text-align: center;
	font-size: 16px;
}

.selected {
	color: #FFFFFF;
	background-color: #02aaf1;
}

.show {
	clear: left;
	display: block;
}

.hidden {
	display: none;
}

.new-btn-login-sp {
	padding: 1px;
	display: inline-block;
	width: 75%;
}

.new-btn-login {
	background-color: #02aaf1;
	color: #FFFFFF;
	font-weight: bold;
	border: none;
	width: 100%;
	height: 30px;
	border-radius: 5px;
	font-size: 16px;
}

#main {
	width: 100%;
	margin: 0 auto;
	font-size: 14px;
}

.red-star {
	color: #f00;
	width: 10px;
	display: inline-block;
}

.null-star {
	color: #fff;
}

.content {
	margin-top: 5px;
}

.content dt {
	width: 100px;
	display: inline-block;
	float: left;
	margin-left: 20px;
	color: #666;
	font-size: 13px;
	margin-top: 8px;
}

.content dd {
	margin-left: 120px;
	margin-bottom: 5px;
}

.content dd input {
	width: 85%;
	height: 40px;
	border: 0;
	-webkit-border-radius: 0;
	-webkit-appearance: none;
}

#foot {
	margin-top: 10px;
	position: absolute;
	bottom: 15px;
	width: 100%;
}

.foot-ul {
	width: 100%;
}

.foot-ul li {
	width: 100%;
	text-align: center;
	color: #666;
}

.note-help {
	color: #999999;
	font-size: 12px;
	line-height: 130%;
	margin-top: 5px;
	width: 100%;
	display: block;
}

#btn-dd {
	margin: 20px;
	text-align: center;
}

.foot-ul {
	width: 100%;
}

.one_line {
	display: block;
	height: 1px;
	border: 0;
	border-top: 1px solid #eeeeee;
	width: 100%;
	margin-left: 20px;
}

.am-header {
	display: -webkit-box;
	display: -ms-flexbox;
	display: box;
	width: 100%;
	position: relative;
	padding: 7px 0;
	-webkit-box-sizing: border-box;
	-ms-box-sizing: border-box;
	box-sizing: border-box;
	background: #1D222D;
	height: 50px;
	text-align: center;
	-webkit-box-pack: center;
	-ms-flex-pack: center;
	box-pack: center;
	-webkit-box-align: center;
	-ms-flex-align: center;
	box-align: center;
}

.am-header h1 {
	-webkit-box-flex: 1;
	-ms-flex: 1;
	box-flex: 1;
	line-height: 18px;
	text-align: center;
	font-size: 18px;
	font-weight: 300;
	color: #fff;
}
	.button {
	  display: inline-block;
	  padding: 5px 10px;
	  font-size: 15px;
	  cursor: pointer;
	  text-align: center;   
	  text-decoration: none;
	  outline: none;
	  color: #fff;
	  background-color: #4CAF50;
	  border: none;
	  border-radius: 10px;
	  box-shadow: 0 5px #999;
	}

	.button:hover {background-color: #3e8e41}

	.button:active {
	  background-color: #3e8e41;
	  box-shadow: 0 2px #666;
	  transform: translateY(2px);
	}
</style>
</head>
<body text=#000000 bgColor="#ffffff" leftMargin=0 topMargin=4>
	<header>
		<button class="button" type="button"  
		onclick="window.location.href='tp.svlt?key=Merchant.search'">商户信息表</button>
	    <button class="button" type="button"  
		onclick="window.location.href='tp.svlt?key=Paysql.search'">交易记录表</button>
	    <button class="button" type="button"  
		onclick="window.location.href='tp.svlt?key=Tokensql.search'">商户令牌表</button>
			<button class="button" type="button"  
		onclick="window.location.href='tp.svlt?key=Chart.search'">汇总统计图</button>
	<%
	String token ="";
	try{
	token = new String(request.getParameter("app_auth_token").getBytes("ISO-8859-1"),"UTF-8");
	}
	catch(Exception exception){
		try{	
			String app_id = new String(request.getParameter("app_id").getBytes("ISO-8859-1"),"UTF-8");
			String code = new String(request.getParameter("app_auth_code").getBytes("ISO-8859-1"),"UTF-8");
			if(app_id.equals(AlipayConfig.app_id)){
				AlipayOpenAuthTokenAppResponse AOATAResponse= Implements.openauth(code, true);
			 token = AOATAResponse.getAppAuthToken();
			 Dao.add_token(token);
			 }
			}
			catch(Exception e){
			}
	}%>
	</header>
	<div id="main">
		<div id="tabhead" class="tab-head">
			<h2 id="tab1" class="selected" name="tab">付 款</h2>
			<h2 id="tab2" name="tab">交 易 查 询</h2>
			<h2 id="tab3" name="tab">退 款</h2>
			<h2 id="tab4" name="tab">退 款 查 询</h2>
			<h2 id="tab5" name="tab">交 易 关 闭</h2>
			<h2 id="tab5" name="tab">第 三 方 授 权</h2>
			<h2 id="tab6" name="tab">授权查询/刷新</h2>
		</div>
		<form name=alipayment action=alipay.trade.pay.jsp method=post
			target="_blank">
			<div id="body1" class="show" name="divcontent">
				<dl class="content">
					<dt>商户订单号 ：</dt>
					<dd>
						<input id="WIDout_trade_no" name="WIDout_trade_no" value="<%=AlipayConfig.getCode()%>"/>
					</dd>
					<hr class="one_line">
					<dt>订单名称 ：</dt>
					<dd>
						<input id="WIDsubject" name="WIDsubject" />
					</dd>
					<hr class="one_line">
					<dt>付款金额 ：</dt>
					<dd>
						<input id="WIDtotal_amount" name="WIDtotal_amount" />
					</dd>
					<hr class="one_line">
					<dt>支付授权码：</dt>
					<dd>
						<input id="WIDbody" name="WIDbody" />
					
					</dd>
					<hr class="one_line">
					<dt>授权令牌 ：</dt>
					<dd>
						    <input  id="app_auth_token"  value="<%=token%>" name="app_auth_token"   />
					</dd>
					<hr class="one_line">
					<dt></dt>
					<dd id="btn-dd">
						<span class="new-btn-login-sp">
							<button class="new-btn-login" type="submit"
								style="text-align: center;">付 款</button>
						</span> <span class="note-help">如果您点击“付款”按钮，即表示您同意该次的执行操作。</span>
					</dd>
				</dl>
			</div>
		</form>
		<form name=tradequery action=alipay.trade.query.jsp method=post
			target="_blank">
			<div id="body2" class="tab-content" name="divcontent">
				<dl class="content">
					<dt>商户订单号 ：</dt>
					<dd>
						<input id="WIDTQout_trade_no" name="WIDTQout_trade_no" />
					</dd>
					<hr class="one_line">
					<dt>支付宝交易号 ：</dt>
					<dd>
						<input id="WIDTQtrade_no" name="WIDTQtrade_no" />
				
					</dd>
					<hr class="one_line">
					<dt>授权令牌 ：</dt>
					<dd>
						    <input  id="app_auth_token"  value="<%=token%>" name="app_auth_token"   />
					</dd>
					<hr class="one_line">
					<dt></dt>
					<dd id="btn-dd">
						<span class="new-btn-login-sp">
							<button class="new-btn-login" type="submit"
								style="text-align: center;">交 易 查 询</button>
						</span> <span class="note-help">商户订单号与支付宝交易号二选一，如果您点击“交易查询”按钮，即表示您同意该次的执行操作。</span>
					</dd>
				</dl>
			</div>
		</form>
		<form name=traderefund action=alipay.trade.refund.jsp method=post
			target="_blank">
			<div id="body3" class="tab-content" name="divcontent">
				<dl class="content">
					<dt>商户订单号 ：</dt>
					<dd>
						<input id="WIDTRout_trade_no" name="WIDTRout_trade_no" />
					</dd>
					<hr class="one_line">
					<dt>支付宝交易号 ：</dt>
					<dd>
						<input id="WIDTRtrade_no" name="WIDTRtrade_no" />
					</dd>
					<hr class="one_line">
					<dt>退款金额 ：</dt>
					<dd>
						<input id="WIDTRrefund_amount" name="WIDTRrefund_amount" />
					</dd>
					<hr class="one_line">
					<dt>退款原因 ：</dt>
					<dd>
						<input id="WIDTRrefund_reason" name="WIDTRrefund_reason" />
					</dd>
					<hr class="one_line">
					<dt>退款请求号 ：</dt>
					<dd>
						<input id="WIDTRout_request_no" name="WIDTRout_request_no" />
					
					</dd>
					<hr class="one_line">
					<dt>授权令牌 ：</dt>
					<dd>
						    <input  id="app_auth_token"  value="<%=token%>" name="app_auth_token"   />
					</dd>
					<hr class="one_line">
					<dt></dt>
					<dd id="btn-dd">
						<span class="new-btn-login-sp">
							<button class="new-btn-login" type="submit"
								style="text-align: center;">退 款</button>
						</span> <span class="note-help">商户订单号与支付宝交易号二选一，如果您点击“退款”按钮，即表示您同意该次的执行操作。</span>
					</dd>
				</dl>
			</div>
		</form>
		<form name=traderefundquery
			action=alipay.trade.fastpay.refund.query.jsp method=post
			target="_blank">
			<div id="body4" class="tab-content" name="divcontent">
				<dl class="content">
					<dt>商户订单号 ：</dt>
					<dd>
						<input id="WIDRQout_trade_no" name="WIDRQout_trade_no" />
					</dd>
					<hr class="one_line">
					<dt>支付宝交易号 ：</dt>
					<dd>
						<input id="WIDRQtrade_no" name="WIDRQtrade_no" />
					</dd>
					<hr class="one_line">
					<dt>退款请求号 ：</dt>
					<dd>
						<input id="WIDRQout_request_no" name="WIDRQout_request_no" />
					
					</dd>
					<hr class="one_line">
					<dt>授权令牌 ：</dt>
					<dd>
						    <input  id="app_auth_token"  value="<%=token%>" name="app_auth_token"   />
					</dd>
					<hr class="one_line">
					<dt></dt>
					<dd id="btn-dd">
						<span class="new-btn-login-sp">
							<button class="new-btn-login" type="submit"
								style="text-align: center;">退 款 查 询</button>
						</span> <span class="note-help">商户订单号与支付宝交易号二选一，如果您点击“退款查询”按钮，即表示您同意该次的执行操作。</span>
					</dd>
				</dl>
			</div>
		</form>
		<form name=tradeclose action=alipay.trade.close.jsp method=post
			target="_blank">
			<div id="body5" class="tab-content" name="divcontent">
				<dl class="content">
					<dt>商户订单号 ：</dt>
					<dd>
						<input id="WIDTCout_trade_no" name="WIDTCout_trade_no" />
					</dd>
					<hr class="one_line">
					<dt>支付宝交易号 ：</dt>
					<dd>
						<input id="WIDTCtrade_no" name="WIDTCtrade_no" />
					
					</dd>
					<hr class="one_line">
					<dt>授权令牌 ：</dt>
					<dd>
						    <input  id="app_auth_token"  value="<%=token%>" name="app_auth_token"   />
					</dd>
					<hr class="one_line">
					<dt></dt>
					<dd id="btn-dd">
						<span class="new-btn-login-sp">
							<button class="new-btn-login" type="submit"
								style="text-align: center;">交 易 关 闭</button>
						</span> <span class="note-help">商户订单号与支付宝交易号二选一，如果您点击“交易关闭”按钮，即表示您同意该次的执行操作。</span>
					</dd>
				</dl>
			</div>
		</form>
				<form name=traderefundquery
			action="<%=AlipayConfig.Url %>" method=post
			target="_blank">
			<div id="body7" class="tab-content" name="divcontent">
				<dl class="content">
				
					<dd id="btn-dd">		
						<span class="new-btn-login-sp">
							<button class="new-btn-login" type="submit"
								style="text-align: center;">第三方授权</button>
						</span> 
					</dd>
				</dl>
			</div>
		</form>
		
		<form name=traderefundquery
			action="app_auth_code.jsp" method=post
			target="_blank">
			<div id="body6" class="tab-content" name="divcontent">
				<dl class="content">
				<dt>授权令牌：</dt>
					<dd>
						<input id="app_auth_code" name="app_auth_code" /><br>
			查询授权令牌<input type="radio"  id="app_id" name="app_id"  value ="2" checked/><br>
			刷新授权令牌<input type="radio"  id="app_id" name="app_id"  value ="1"/><br>
	
					</dd>
					
					<dd id="btn-dd">		
						<span class="new-btn-login-sp">
							<button class="new-btn-login" type="submit"
								style="text-align: center;">第三方授权</button>
						</span> <span class="note-help">商户订单号与支付宝交易号二选一，如果您点击“退款查询”按钮，即表示您同意该次的执行操作。</span>
					</dd>
				</dl>
			</div>
		</form>
	</div>
</body>
<script language="javascript">
	var tabs = document.getElementsByName('tab');
	var contents = document.getElementsByName('divcontent');
	
	(function changeTab(tab) {
	    for(var i = 0, len = tabs.length; i < len; i++) {
	        tabs[i].onmouseover = showTab;
	    }
	})();
	
	function showTab() {
	    for(var i = 0, len = tabs.length; i < len; i++) {
	        if(tabs[i] === this) {
	            tabs[i].className = 'selected';
	            contents[i].className = 'show';
	        } else {
	            tabs[i].className = '';
	            contents[i].className = 'tab-content';
	        }
	    }
	}
	
	function GetDateNow() {
		document.getElementById("WIDsubject").value = "测试";
	}
	GetDateNow();
</script>
</html>