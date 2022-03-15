
<%@page import="com.alipay.model.ChartModel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%	ChartModel model = (ChartModel) request.getAttribute("model"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>汇总统计图</title>
</head>
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
.highcharts-figure, .highcharts-data-table table {
    min-width: 360px; 
    max-width: 800px;
    margin: 1em auto;
}

.highcharts-data-table table {
	font-family: Verdana, sans-serif;
	border-collapse: collapse;
	border: 1px solid #EBEBEB;
	margin: 10px auto;
	text-align: center;
	width: 100%;
	max-width: 500px;
}
.highcharts-data-table caption {
    padding: 1em 0;
    font-size: 1.2em;
    color: #555;
}
.highcharts-data-table th {
	font-weight: 600;
    padding: 0.5em;
}
.highcharts-data-table td, .highcharts-data-table th, .highcharts-data-table caption {
    padding: 0.5em;
}
.highcharts-data-table thead tr, .highcharts-data-table tr:nth-child(even) {
    background: #f8f8f8;
}
.highcharts-data-table tr:hover {
    background: #f1f7ff;
}
#main {position:absolute;width:800px;left:50%;top:50%; 
margin-left:-350px;margin-top:-250px} 
</style>
<body>
<script src="code/highcharts.js"></script>
<script src="code/modules/series-label.js"></script>
<script src="code/modules/exporting.js"></script>
<script src="code/modules/export-data.js"></script>
<script src="code/modules/accessibility.js"></script>
<script src="assets/js/jquery-1.11.1.min.js"></script>
<script src="assets/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/js/jquery.backstretch.min.js"></script>
<script src="assets/js/scripts.js"></script>
<script src="frameworks/iceking-utils-jquery.js"></script>
	<button class="button" type="button"  
	    onclick="window.location.href='index.jsp'">调用接口页</button>
		<button class="button" type="button"  
		onclick="window.location.href='tp.svlt?key=Tokensql.search'">商户令牌表</button>
		<button class="button" type="button"  
		onclick="window.location.href='tp.svlt?key=Paysql.search'">交易记录表</button>
		<button class="button" type="button"  
		onclick="window.location.href='tp.svlt?key=Merchant.search'">商户信息表</button>
		<h2  align="center">
			<span style="color: white">汇总统计图</span>
		</h2>
<div id="main">
<div  align="center">
<form id="form1" class="form-inline" role="form"
			action="tp.svlt?key=Chart.search" method="post">
		
	<span style="color: white">年份：</span>
	<select name="year" id="year" class="form-control" onchange="search()">
		<option value="0" selected>----</option>
			<%for(int i:model.getYears()){%>
			<option value="<%=i %>"  <%=model.getYear()==i?"selected":"" %>><%=i %></option>
		<%} %>
		</select>
		
	<span style="color: white">月份：</span>
	<select name="month" id="month" class="form-control" onchange="search()">
		<option value="0" selected>----</option>
		<%if(model.getYear()!=0){ 
		for(int i=1;i<13;i++){%>
			<option value="<%=i %>"  <%=model.getMonth()==i?"selected":"" %>><%=i %></option>
		<%}} %></select>
	
	<span style="color: white">商户等级：</span>
	<select name="level" id="level" class="form-control" onchange="search()">	
	  <option value="0" selected>----</option>
	  <option value="1" <%=model.getLevel()==1?"selected":"" %>>服务商</option>
	  <option value="2" <%=model.getLevel()==2?"selected":"" %>>一级代理商</option>
	  <option value="3" <%=model.getLevel()==3?"selected":"" %>>二级代理商</option>
	  <option value="4" <%=model.getLevel()==4?"selected":"" %>>商户</option>
	</select>
		<span style="color: white">图示内容：</span>
	<select name="type" id="type" class="form-control" onchange="search()">
		<option value="1" <%=(model.getType()==1?"selected":"") %>>佣金</option>
		<%if(model.getLevel()!=0){ %><option value="2" <%=(model.getType()==2?"selected":"")%>>流水</option><%} %>
		</select>
	</form>
</div>
<figure class="highcharts-figure">
    <div id="container"></div>
    
</figure>

</div>
		<script type="text/javascript">
		var search=function(){
			$('#form1').get(0).submit();
		};
		
Highcharts.chart('container', {
    chart: {  
    	 // plotBackgroundImage: 'timg.jpg', //(图表的)背景图片
    	    backgroundColor: "#c0c0c0" ,     //图框的背景颜色
    	  borderRadius: 25,            //图框的圆角大小
    	  borderColor: "red",       //图框的边框颜色
    		  borderWidth: 1,      
    		  plotBorderColor: "white",     //图表的边框颜色
    		    plotBorderWidth: 1         //图表的边框大小
    },  
    title: {
        text: '支付宝服务商返佣统计图'
    },

    subtitle: {
        text: '此图仅做参考，一切数据以支付宝实际数据为准'
    },

    yAxis: {
        title: {
            text: '<%=model.getType()==1?"所得佣金(单位：元)":"有效流水(单位：元)"%>'
        }
    },


    legend: {
        layout: 'vertical',
        align: 'right',
        verticalAlign: 'middle'
    },
    xAxis: { 
        allowDecimals:false 
},
    plotOptions: {
        series: {
            label: {
                connectorAllowed: false
            },
            pointStart: <%if(model.getYear()==0){%><%=model.getYears().get(0)%><%}else{%>1<%}%>,
            pointInterval:1
        }
    },
    series: [
    	<%if(model.getLevel()==0){%>
    {
        name: '服务商',
        data: [<%for(int i=0;i<model.getAgent1().size();i++){%>
        <%if(i!=0){%>,<%}%>
        <%=model.getAgent1().get(i)%><%}%>]
    }, {
        name: '一级代理商',
        data: [<%for(int i=0;i<model.getAgent2().size();i++){%>
        <%if(i!=0){%>,<%}%>
        <%=model.getAgent2().get(i)%><%}%>]
    }, {
        name: '二级代理商',
        data: [<%for(int i=0;i<model.getAgent3().size();i++){%>
        <%if(i!=0){%>,<%}%>
        <%=model.getAgent3().get(i)%><%}%>]
    }, {
        name: '商户',
        data: [<%for(int i=0;i<model.getAgent4().size();i++){%>
        <%if(i!=0){%>,<%}%>
        <%=model.getAgent4().get(i)%><%}%>]
    }
    <%}
    else{%>
    <%for(int i=0;i<model.getList().size();i++){%>
    <%if(i!=0){%>, <%}%>
    {
        name: '<%=model.getList().get(i).getAgent_uid()%>',
        data: [<%for(int j=0;j<model.getList().get(i).getAmount().size();j++){%>
        <%if(j!=0){%>,<%}%>
        <%=model.getList().get(i).getAmount().get(j)%><%}%>]
    }
    
    <%}}%>
    ],
});
		</script>
	</body>
</html>