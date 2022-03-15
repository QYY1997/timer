<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.alipay.model.Token"%>
<%@page import="com.alipay.model.TokenModel"%>
<%@page import="com.alipay.tps.Dao"%>
<%@ page import="com.alipay.config.*"%>
<%@page import="com.alipay.model.Agent"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	TokenModel model = (TokenModel) request.getAttribute("model");
    List<Agent> sell_uid=Dao.qurey_agent("and level=4 ");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商户令牌表</title>
	<style>
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
<!-- Bootstrap -->
<!-- link 引入样式表 -->
<link href="frameworks/bootstrap-3.3.5-dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="icon" href="favicon.ico" type="images/x-ico" />
</head>
<body>
	<div class="col-sm-10 col-sm-offset-1 " >
	<button class="button" type="button"  
	    onclick="window.location.href='index.jsp'">调用接口页</button>
		<button class="button" type="button"  
		onclick="window.location.href='tp.svlt?key=Merchant.search'">商户信息表</button>
	    <button class="button" type="button"  
		onclick="window.location.href='tp.svlt?key=Paysql.search'">交易记录表</button>
		<button class="button" type="button"  
		onclick="window.location.href='tp.svlt?key=Chart.search'">汇总统计图</button>
		
		<br>
		<h2>
			<span style="color: white">商户令牌表</span>
		</h2>
		<form id="form1" class="form-inline" role="form"
			action="tp.svlt?key=Tokensql.search" method="post">
			<div>
			<span style="color: white">商户支付宝ID：</span>
			 <select class="form-control" name="uid" id="uid">
					<option value="" selected>----</option>
					<%
						for (Agent ct : sell_uid) {
					%>
					<option value="<%=ct.getAgent_uid()%>" 
						<%=model.getUid().equals
						(ct.getAgent_uid())? "selected" : ""%>><%=ct.getAgent_uid()%></option>
					<%}%>
				</select> 
					<span style="color: white">关键字：</span> <input name="keyword"
					type="text" class="form-control" placeholder="请输入查找的APPID或AUTH_APP_TOKEN"
					value=<%=model.getKeyword()%>>
				<button class="btn btn-primary" type="button" onclick="search(0)">
					查询</button>
				<button class="btn btn-primary" type="button" onclick="window.location.href='<%=AlipayConfig.Url%>'">去授权</button>			
			</div>
		<table class="table">
			<caption>满足条件的查询结果</caption>
			<thead>
				<tr>
					<th style="color: white">数据库ID</th>
					<th style="color: white">授权令牌</th>
					<th style="color: white">商户应用ID</th>
					<th style="color: white">失效期</th>
					<th style="color: white">商户支付宝ID</th>
					<th style="color: white">授权状态</th>
				</tr>
			</thead>
			<tbody>
				<%
					for (Token de : model.getToken_list()) {
				%>
				<tr>
				    <td style="color: white"><%=de.getId() %></td>
					<td style="color: white"><%=de.getApp_auth_token() %></td>
					<td style="color: white"><%=de.getAuth_app_id() %></td>
					<td style="color: white"><%=de.getAuth_end()%></td>
					<td style="color: white"><%=de.getUser_id()%></td>
					<td style="color: white"><%=(de.getStatus().equals("true"))?"有效":"失效"%></td>
					<td>	<button class="btn btn-xs btn-default" type="button"
							onclick="window.location.href='index.jsp?app_auth_token=<%=de.getApp_auth_token() %>'">调用接口</button>
								<button class="btn btn-xs btn-default" type="button"
							onclick="window.location.href='tp.svlt?key=Paysql.search&UID=<%=de.getUser_id() %>'">交易记录</button></td>
				</tr>
				<%
					}
				%>

			</tbody>
		</table>

			<div id="footer">
			<button class="btn btn-default" type="button" onclick="search(0)">
				首页</button>
			<button class="btn btn-default" type="button" onclick="prepage()">
				上一页</button>
			<select class="form-control"
				onchange="search(parseInt(this.value)-1)">
				<%
					for (int i = 1; i <= model.getTotalpage(); i++) {
				%>
				<option value="<%=i%>"
					<%=i == model.getPage() + 1 ? "selected" : ""%>><%=i%></option>
				<%
					}
				%>
			</select> <span style="color: white">/<%=model.getTotalpage()%></span>
			<button class="btn btn-default" type="button" onclick="nextpage()">
				下一页</button>
			<button class="btn btn-default" type="button"
				onclick="search(<%=model.getTotalpage() - 1%>)">末页</button>
			<select class="form-control" name="pagesize" onchange="search(0)">
				<option value="10" <%=model.getPagesize() == 10 ? "selected" : ""%>>每页10条</option>
				<option value="20" <%=model.getPagesize() == 20 ? "selected" : ""%>>每页20条</option>
				<option value="30" <%=model.getPagesize() == 30 ? "selected" : ""%>>每页30条</option>
				<option value="50" <%=model.getPagesize() == 50 ? "selected" : ""%>>每页50条</option>
				<option value="100"
					<%=model.getPagesize() == 100 ? "selected" : ""%>>每页100条</option>
			</select> <input type="hidden" id="page" name="page"
				value=<%=model.getPage()%>> <input type="hidden"
				id="totalpage" name="totalpage" value=<%=model.getTotalpage()%>>
		</div>
		</form>
	</div>
	<!-- Javascript -->

		<script src="assets/js/jquery-1.11.1.min.js"></script>
		<script src="assets/bootstrap/js/bootstrap.min.js"></script>
		<script src="assets/js/jquery.backstretch.min.js"></script>
		<script src="assets/js/scripts.js"></script>
		<script src="frameworks/iceking-utils-jquery.js"></script>
		<script>jqutils.loadJs('token.js')</script>

</body>
</html>