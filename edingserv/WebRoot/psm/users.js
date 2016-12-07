/**
 * 
 */
$("#headDiv").load("head.html");

function checkpasswd(){
	var passwd=$("#regpasswd").val();
	var confirmpasswd=$("#confirmpasswd").val();
	var valid=(passwd==confirmpasswd);
	if(valid){
		$("#confirmpasswd").parent().attr('class','input-control text success full-size');
		return true;
	}else{
		$("#confirmpasswd").parent().attr('class','input-control text error full-size');
		 $.Notify({
			    caption: '错误：',
			    content: '两次密码不相同',
			    shadow:'true',
			    type: 'alert'
			});
		 return false;
	}
	//input-control text error full-size
	//input-control text full-size
}



function checkphone(){
	var phone=$("#regphone").val();
	 if(!/^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/i.test(phone)){ 
		 $.Notify({
			    caption: '错误：',
			    content: '手机号码不符合',
			    shadow:'true',
			    type: 'alert'
			});
		 return false;
	 }
	 return true;
}

function checkUserName(){
	var regname=$("#regname").val();
	if(regname.length<1){
		$.Notify({
		    caption: '提示',
		    content: '用户名不可为空',
		    shadow:'true',
		    type: 'alert'
		});
		return false;
	}
	var obj={};
	obj.name=regname;
	var retMap=ajx("userBean.checkName",obj );
	var valid=(retMap.ret_code=="000")
	if(valid){
		$("#regname").parent().attr('class','input-control text success full-size');
		$.Notify({
		    caption: '提示',
		    content: '该用户可以注册',
		    shadow:'true',
		    type: 'success'
		});
		return true;
	}else{
		$("#regname").parent().attr('class','input-control text error full-size');
		$.Notify({
		    caption: '错误：',
		    content: '用户已被注册',
		    shadow:'true',
		    type: 'alert'
		});
		return false;
	}
	
}



function doRegist(){
	var validflag=checkpasswd()&&checkphone()&&checkUserName();
	if(validflag){
		alert("ok");
	}else{
		$.Notify({
		    caption: '错误：',
		    content: '请检查表单内容',
		    shadow:'true',
		    type: 'alert'
		});
	}
	
}