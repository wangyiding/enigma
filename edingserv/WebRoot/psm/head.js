/**
 * 
 */
function showDevCharm(id){
    var  charm = $(id).data("charm");
    if (charm.element.data("opened") === true) {
        charm.close();
    } else {
        charm.open();
    }
}


function doLogin(){
	var obj={};
	obj.name=$("#name").val();
	obj.passwd=$("#passwd").val();
	
	var rets=ajx("userBean.doLogin",obj)
	if("000"==rets.ret_code){
		location.href="";
	}else{
		$.Notify({
		    caption: '错误：',
		    content: '登陆失败，请检查用户名和密码',
		    shadow:'true',
		    type: 'alert'
		});
	}
}

