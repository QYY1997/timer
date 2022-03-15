<%@page import="java.math.BigDecimal"%>
<%@page import="com.alipay.tps.Dao"%>
<%@page import="com.alipay.model.AgentModel"%>
<%@page import="com.alipay.model.Agent"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	AgentModel model = (AgentModel) request.getAttribute("model");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商户信息表</title>
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
<link rel="shortcut icon" href="favicon.ico" />
</head>
<body>
	<div class="col-sm-10 col-sm-offset-1 ">
	<button class="button" type="button"  
	    onclick="window.location.href='index.jsp'">调用接口页</button>
		<button class="button" type="button"  
		onclick="window.location.href='tp.svlt?key=Tokensql.search'">商户令牌表</button>
		<button class="button" type="button"  
		onclick="window.location.href='tp.svlt?key=Paysql.search'">交易记录表</button>
			<button class="button" type="button"  
		onclick="window.location.href='tp.svlt?key=Chart.search'">汇总统计图</button>
		<br>
		<h2>
			<span style="color: white">商户信息表</span>
		</h2>
		<form class="form-horizontal" role="form" id="form3"
						onsubmit="return false">
					
						<span style="color: white">服务商费率：</span>
						<input name="rate1" id="rate1" type="number"  width=7px maxlength="2"
						placeholder="请输入0到60的整数" value=<%=(Double)Dao.qurey("role", "rate","level=1") %>
						 onchange="sum()">
						<span style="color: white">一级代理商费率：</span>
						<input name="rate2" id="rate2" type="number" width=7px maxlength="2"
						placeholder="请输入0到60的整数" value=<%=(Double)Dao.qurey("role", "rate","level=2") %>
						 onchange="sum()">
						<span style="color: white">二级代理商费率：</span>
						<input name="rate3"  id="rate3" type="number"  width=7px maxlength="2"
						placeholder="请输入0到60的整数" value=<%=(Double)Dao.qurey("role", "rate","level=3") %>
						 onchange="sum()">
							<span style="color: white">商户支付费率：</span>
						<input name="rate4"  id="rate4" type="number"    width=7px maxlength="2"
						readonly="readonly" value="<%=(Double)Dao.qurey("role", "rate","level=4") %>">
						<span style="color: white">单位(万分之一)</span>
							 <input type="hidden" name="key" value="Merchant.rate">
						<button class="btn btn-primary" type="button" onclick="update()">修改费率</button>
					</form>
		<form id="form1" class="form-inline" role="form"
			action="tp.svlt?key=Merchant.search" method="post">
			<div>
				<span style="color: white">角色：</span>
				 <select class="form-control" name="level" >
					<option value="0" selected>----</option>
					<option value="1" <%=model.getLevel()==1?"selected":"" %>>服务商</option>
					<option value="2" <%=model.getLevel()==2?"selected":"" %>>一级代理商</option>
					<option value="3" <%=model.getLevel()==3?"selected":"" %>>二级代理商</option>
					<option value="4" <%=model.getLevel()==4?"selected":"" %>>商户</option>
					
				</select> 
				
				<span style="color: white">关键字：</span> <input name="keyword"
					type="text" class="form-control" placeholder="请输入商户名称或支付宝ID"
					value=<%=model.getKeyword()%>>
				<button class="btn btn-primary" type="button" onclick="search(0)">
					查询</button>
				<button class="btn btn-default" type="button" onclick="presave(0)">
					新增</button>
							
			</div>


		<table class="table">
			<caption>满足条件的查询结果</caption>
			<thead>
				<tr>
					<th style="color: white">数据库ID</th>
					<th style="color: white">商户名称</th>
					<th style="color: white">支付宝ID</th>
					<th style="color: white">商户类型</th>
					<th style="color: white">上级代理</th>
					<th style="color: white">总流水(元)</th>
					<th style="color: white">费率(万一)</th>
					<th style="color: white">可得佣金/支付费用(元)</th>
				</tr>
			</thead>
			<tbody>
				<%
					for (Agent de : model.getAgent_list()) {
				%>
				<tr>
				    <td style="color: white"><%=de.getId() %></td>
					<td style="color: white"><%=de.getAgent_name() %></td>
					<td style="color: white"><%=de.getAgent_uid()%></td>
					<td style="color: white"><%=(String)Dao.qurey("Role", "name","level="+de.getLevel())%></td>
					<td style="color: white"><%=de.getSuperior()%></td>
					<td style="color: white"><%=new BigDecimal(de.getAmount().toString()).setScale(4,BigDecimal.ROUND_HALF_UP)%></td>
					<td style="color: white"><%=de.getRate()%></td>
					<td style="color: white"><%=new BigDecimal(de.getCommission().toString()).setScale(4,BigDecimal.ROUND_HALF_UP)%></td>
					<td><button class="btn btn-xs btn-default" type="button"
							onclick="del(<%=de.getId()%>)">删除</button>
						<button class="btn btn-xs btn-default" type="button"
							onclick="presave(<%=de.getId()%>)">修改</button>
								<%if((int)Dao.qurey("Agent", "level","agent_uid='"+de.getAgent_uid()+"'")==4){ %><button class="btn btn-xs btn-default" type="button"
							onclick="window.location.href='tp.svlt?key=Tokensql.search&UID=<%=de.getAgent_uid()%>'">Token</button>
							<%} %>
							</td>
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
	<!-- 模态框（Modal） -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">修改/新增</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="form2"
						onsubmit="return false">

						<div class="form-group">
						
							<div style="float:left;margin-left:20px"><label style="color: red">*	</label></div>
						<div style="float:left"><label>商户名称</label></div>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="agent_name" id="agent_name"
									placeholder="请输入商户姓名或代理商名称">
							</div>
						</div>

						<div class="form-group">
						<div style="float:left;margin-left:20px"><label style="color: red">*	</label></div>
						<div style="float:left"><label>支付宝ID</label></div>
							
							<div class="col-sm-10">
								<input type="number" class="form-control" name="agent_uid" id="agent_uid"
								maxlength="16"	placeholder="请输入支付宝ID(2088开头的16位数字)">
							</div>
						</div>
						<div class="form-group">
						<div style="float:left;margin-left:20px"><label style="color: red">*	</label></div>
						<div style="float:left;margin-left:13px"><label>上级ID</label></div>
							
							<div class="col-sm-10">
								<input type="number" class="form-control" name="superior" id="superior"
								maxlength="16"	placeholder="请输入上级的支付宝ID(2088开头的16位数字)">
							</div>
						</div>
						<div class="form-group">
						<div style="float:left;margin-left:20px"><label style="color: red">*	</label></div>
						<div style="float:left"><label>商户类型</label></div>
						<div class="col-sm-10">
								 <select class="form-control" name="level" id="level">
					<option value="0" selected>----</option>
					<option value="1">服务商</option>
					<option value="2">一级代理商</option>
					<option value="3">二级代理商</option>
					<option value="4">商户</option></select> 
							</div>
						</div>

						<div class="form-group">
						<div style="float:left;margin-left:40px"><label>总流水</label></div>
							<div class="col-sm-10">
								<input type="number" class="form-control" name="amount" id="amount"
									placeholder="请输入总流水金额">
							</div>
						</div>

						<input type="hidden" name="id" value="0"><br>
						 <input type="hidden" name="key" value="Merchant.save"><br>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="save()">提交更改</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->


		<!-- Javascript -->

		<script src="assets/js/jquery-1.11.1.min.js"></script>
		<script src="assets/bootstrap/js/bootstrap.min.js"></script>
		<script src="assets/js/jquery.backstretch.min.js"></script>
		<script src="assets/js/scripts.js"></script>
		<script src="frameworks/iceking-utils-jquery.js"></script>
		<script>
		jqutils.loadJs('agent.js')
	</script>
	</div>
</body>
</html>