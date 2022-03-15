<%@page import="com.alipay.model.Pay"%>
<%@page import="com.alipay.model.PayModel"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.alipay.tps.Dao"%>
<%@ page import="com.alipay.config.*"%>
<%@page import="com.alipay.model.Agent"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	PayModel model = (PayModel) request.getAttribute("model");
    List<Agent> sell_uid=Dao.qurey_agent("and level=4 ");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>交易记录表</title>
<style type="text/css">
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
table{width:100%;border:1px solid #999;}
table td{word-break: keep-all;white-space:nowrap;}
table th{word-break: keep-all;white-space:nowrap;}</style>
<!-- Bootstrap -->
<!-- link 引入样式表 -->
<link href="frameworks/bootstrap-3.3.5-dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="shortcut icon" href="favicon.ico" />
</head>
<body style="overflow-x: scroll;">
	<div class="col-sm-10 col-sm-offset-1" >
	<button class="button" type="button"  
	    onclick="window.location.href='index.jsp'">调用接口页</button>
		<button class="button " type="button"  
		onclick="window.location.href='tp.svlt?key=Merchant.search'">商户信息表</button>
	    <button class="button " type="button"  
		onclick="window.location.href='tp.svlt?key=Tokensql.search'">商户令牌表</button>
			<button class="button " type="button"  
		onclick="window.location.href='tp.svlt?key=Chart.search'">汇总统计图</button>
		<br>
		<h2>
			<span style="color: white">交易记录表</span>
		</h2>
		<form id="form1" class="form-inline" role="form"
			action="tp.svlt?key=Paysql.search" method="post">
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
					type="text" class="form-control" placeholder="请输入查找的商户支付宝ID或订单标题"
					value=<%=model.getKeyword()%>>
				<button class="btn btn-primary" type="button" onclick="search(0)">查询</button>
			</div>
		<table class="table">
			<caption>满足条件的查询结果</caption>
			<thead>
				<tr>
					<th style="color: white">数据库ID</th>
					<th style="color: white">商家订单号</th>
					<th style="color: white">支付宝交易号</th>
					<th style="color: white">订单标题</th>
					<th style="color: white">订单金额</th>
					<th style="color: white">买家实付金额</th>
					<th style="color: white">商家实收金额</th>
					<th style="color: white">买家支付宝ID</th>
					<th style="color: white">商家支付宝ID</th>
					<th style="color: white">交易时间</th>
					<th style="color: white">交易状态</th>
					<th style="color: white">退款订单号</th>
					<th style="color: white">退款原因</th>
					<th style="color: white">退款金额</th>
					<th style="color: white">退款时间</th>
					<th style="color: white">商家有效流水</th>
					<th style="color: white">一级上级支付宝ID</th>
					<th style="color: white">一级上级所得佣金</th>
					<th style="color: white">二级上级支付宝ID</th>
					<th style="color: white">二级上级所得佣金</th>
					<th style="color: white">服务商支付宝ID</th>
					<th style="color: white">服务商所得佣金</th>
					
				</tr>
			</thead>
			<tbody>
				<%
					for (Pay de : model.getPay_list()) {
				%>
				<tr>
				    <td style="color: white"><%=de.getId() %></td>
					<td style="color: white"><%=de.getOut_trade_no() %></td>
					<td style="color: white"><%=de.getTrade_no() %></td>
					<td style="color: white"><%=de.getSubject()%></td>
					<td style="color: white"><%=de.getTotal_amount()%></td>
					<td style="color: white"><%=de.getBuyer_pay_amount() %></td>
					<td style="color: white"><%=de.getReceipt_amount() %></td>
					<td style="color: white"><%=de.getBuyer_user_id() %></td>
					<td style="color: white"><%=de.getSeller_uid()%></td>
					<td style="color: white"><%=de.getSend_pay_date()%></td>
					<td style="color: white"><%=de.getTrade_status() %></td>
					<td style="color: white"><%=de.getOut_request_no() %></td>
					<td style="color: white"><%=de.getRefund_reason()%></td>
					<td style="color: white"><%=de.getRefund_amount() %></td>
					<td style="color: white"><%=de.getGmt_refund_pay()==null?"":de.getGmt_refund_pay()%></td>
					<td style="color: white"><%=de.getEffective_sum() %></td>
					<td style="color: white"><%=de.getSuperior1()%></td>
					<td style="color: white"><%=de.getCommission1()%></td>
					<td style="color: white"><%=de.getSuperior2()%></td>
					<td style="color: white"><%=de.getCommission2()%></td>
					<td style="color: white"><%=de.getSuperior3()%></td>
					<td style="color: white"><%=de.getCommission3()%></td>
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
				<option value="100"<%=model.getPagesize() == 100 ? "selected" : ""%>>每页100条</option>
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
		<script>jqutils.loadJs('pay.js')</script>

</body>
</html>