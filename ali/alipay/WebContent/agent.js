//执行具体表单提交动作
var search=function(page){
	$('#page').val(page);
	$('#form1').get(0).submit();
};

//上一页
var prepage=function(){
	var page=parseInt($('#page').val());
	if(page==0) return;
	page--;
	search(page);
};

//下一页
var nextpage=function(){
	var page=parseInt($('#page').val());
	var totalpage=parseInt($('#totalpage').val());
	if(page==totalpage-1) return;
	page++;
	search(page);
};
var sum=function(){
	var rate1=jqutils.formItem('rate1','form3').val();
	var rate2=jqutils.formItem('rate2','form3').val();
	var rate3=jqutils.formItem('rate3','form3').val();
	
	
	if(isNaN(rate1) || isNaN(rate2) || isNaN(rate3)){
	('#rate4').val(0);
	return;
	}

	if(rate1=="" || rate1<0 || rate1>60 ){
		('#rate4').val(0);
		return;
	}
	if(rate2.value=="" || rate2<0 || rate2>60 ){
		('#rate4').val(0);
		return;
		}
	if(rate3=="" || rate3<0 || rate3>60 ){
		('#rate4').val(0);
		return;
		}
	var sums =  parseInt(rate1)+ parseInt(rate2)+ parseInt(rate3);
	$('#rate4').attr("value",sums);
		return;
};
var update=function(){
	var rate1=jqutils.formItem('rate1','form3').val();
	var rate2=jqutils.formItem('rate2','form3').val();
	var rate3=jqutils.formItem('rate3','form3').val();
	var rate4=jqutils.formItem('rate4','form3').val();
	
	
	if(isNaN(rate1) || isNaN(rate2) || isNaN(rate3)){
	alert("费率必须是数值！");
	return;
	}

	if(rate1=="" || rate1<0 || rate1>60 ){
		alert("请填写正确的费率！");
		return;
	}
	if(rate2=="" || rate2<0 || rate2>60 ){
		alert("请填写正确的费率！");
		return;
		}
	if(rate3=="" || rate3<0 || rate3>60 ){
		alert("请填写正确的费率！");
		return;
		}
	if(rate4=="" || rate4<0 ){
		alert("请填写正确的费率！");
		return;
		}
	if(rate4>60){
		alert("总费率不能超过万分之60！");
		return;
	}
	if(!confirm('确定要修改费率吗？')) return;
	
	var data=jqutils.formData('form3');

	var rst=jqutils.loadJson('tp.svlt',data);
	if(!rst.succ){
		alert(rst.stmt);
		return;
	}
	else{
		alert("修改费率成功！");
		var page=parseInt($('#page').val());
		search(page);
	}
	
};


var del=function(id){
	if(!confirm('确定要删除本条数据吗？')) return;
	//ajax调用
	var rst=jqutils.loadJson('tp.svlt',{
		key:'Merchant.del',
		id:id
	});
	//发生删除错误的情况
	if(!rst.succ){
		alert(rst.stmt);
		return;
	}
	//删除成功后，刷新当前页面
	var page=parseInt($('#page').val());
	search(page);
};

var presave=function(id){
	//ajax调用
	var rst=jqutils.loadJson('tp.svlt',{
		key:'Merchant.presave',
		id:id
	});
	if(!rst.succ){
		alert(rst.stmt);
		return;
	}
	//成功
	var de=rst.de;
	//alert(jqutils.obj2json(mdc));
	//数据装载到表单
	jqutils.formLoad('form2',de);
	//弹出修改框
	$('#myModal').modal('show');
};

var save=function(){
	//对输入数据进行有效性判断
	
	
	if(jqutils.formItem('agent_name','form2').val()==""){
		alert("商户名称必须填写！");
		return;
	}
	var val=jqutils.formVal('agent_uid','form2');
	if(val=="" ){
		alert("支付宝ID必须填写！");
		return;
	}
	if(isNaN(val) || val<0 || val.toString().length != 16){
		alert("请填写正确的支付宝ID！");
		return;
	}
	var val=jqutils.formVal('superior','form2');
	if(val=="" ){
		alert("上级ID必须填写！");
		return;
	}
	if(isNaN(val) || val<0 || val.toString().length != 16){
		alert("请填写正确的上级ID！");
		return;
	}
	if(jqutils.formVal('level','form2')=="0"){
		alert("请选择商户类型！");
		return;
	}
	val=jqutils.formVal('amount','form2');
	if( val==""){
		alert("总流水不能为空！")	;
		return;
	}
	if(isNaN(val)){
		alert("总流水必须是数值！")	;
		return;
	}
	if(val<0){
		alert("总流水不能为负数！")	;
		return;
	}

	if(!confirm('确定要提交本条数据吗？')) {
		return;
	}
	var data=jqutils.formData('form2');
	var rst=jqutils.loadJson('tp.svlt',data);
	if(!rst.succ){
		alert(rst.stmt);
		return;
	}
	var page=parseInt($('#page').val());
	search(page);
	
};

$(function(){
	
});