$(function() {
	$("#login_btn").click(function() {
		var loginload;
		var uname = $("#username").val();
		var upwd = $("#userpwd").val();
		if(null==uname||uname==""||uname.replace(/(^s*)|(s*$)/g, "").length ==0){
			layer.alert('请输入帐号！', {icon: 2});
		}else if(null==upwd||upwd==""||upwd.replace(/(^s*)|(s*$)/g, "").length ==0){	
			layer.alert('请输入密码！', {icon: 2});
		}else{
			login(uname,upwd);	
		}
		
	});
});
//登录ajax
var login = function ajaxLogin(uname,upwd){
	$.ajax({
		type : "post",
		url : "/index/loginUser",
		data : {
			"username" : uname,	
			"userpwd" : upwd
		},
		dataType : "json",
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		async : true, 
		beforeSend: function() {
			loginload = layer.load(1, {
				  shade: [0.5,'#fff'] //0.1透明度的白色背景
				});
		    },
		error : function(data) {
			layer.close(loginload);
			layer.alert('出错了', {icon: 2});
		},
		success : function(data) {
			layer.close(loginload);
		if(data.target =="index"){
			layer.msg(data.msg, {icon: 1});
		  window.location.href = "/index/directionalIndex";
		}else if(data.target =="login"){
			
			layer.alert(data.msg, {icon: 2});
		}
	}
	});
}

$(function() {
	$("#signup_btn").click(function() {
		var loginload;
		/*邮箱验证正则表达式*/
		var email_reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		var uname = $("#signup_username").val();
		var upwd = $("#signup_userpwd").val();
		var name = $("#signup_name").val();
		if(null==uname||uname==""||uname.replace(/(^s*)|(s*$)/g, "").length ==0){
			layer.alert('请输入帐号！', {icon: 2});
		}else if(null==upwd||upwd==""||upwd.replace(/(^s*)|(s*$)/g, "").length ==0){	
			layer.alert('请输入密码！', {icon: 2});
		}else if(null==name||name==""||name.replace(/(^s*)|(s*$)/g, "").length ==0){	
			layer.alert('请输入真实姓名！', {icon: 2});
		}else if(!email_reg.test(uname)){	
			layer.alert('请输入有效的用户邮箱！', {icon: 2});
		}else{
			signup(uname,upwd,name);	
		}
		
	});
});
//邮箱注册ajax
var signup = function ajaxSignup(uname,upwd,name){
	$.ajax({
		type : "post",
		url : "/index/mailRegister",
		data : {
			"username" : uname,	
			"userpwd" : upwd,
			"name" : name
		},
		dataType : "json",
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		async : true, 
		beforeSend: function() {
			loginload = layer.load(1, {
				  shade: [0.5,'#fff'] //0.1透明度的白色背景
				});
		    },
		error : function(data) {
			layer.close(loginload);
			layer.alert('出错了', {icon: 2});
		},
		success : function(data) {
			layer.close(loginload);
		if(data[0] =="100"){
			layer.msg(data[1], {icon: 1});
			doReset();
		}else if(data[0] =="101"){
			layer.alert(data[1], {icon: 2});
			doReset();
		}else {
			layer.alert(data[1], {icon: 2});
			doReset();
		}
	}
	});
}
//清空所有input输入框
var doReset = function doReset(){
	$(".signup input[type='text']").each(function(){
	    $(this).val("");
	  });
	$(".signup input[type='password']").each(function(){
	    $(this).val("");
	  });
}