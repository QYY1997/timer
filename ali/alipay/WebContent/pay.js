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

$(function(){
	
});