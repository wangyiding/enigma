function  dicShow(dic){
	var gpid= $(dic).attr('gpid');
	var type=$(dic).attr('type');
	var specTag=$(dic).attr('specTag')==null?"":$(dic).attr('specTag');
	 var obj=ajx("dicBean.getDicInfo",{gpid:gpid,type:type,specTag:specTag});
	 var dicInfo;
	 try{
		 dicInfo=obj.obj.dicInfo;
	 }catch (e) {
		 dicInfo={error:"字典读取失败"};
	}
	 if(type=="list"){
		 var innerHtmlInfo="";
		 for(var k=0;k<dicInfo.length;k++){
			 innerHtmlInfo+="<option value="+dicInfo[k].dicValue+">"+dicInfo[k].dicName+"<option>";
		 }
		 $(dic).html("<select>"+innerHtmlInfo+"</select>");
	 }else if(type=="checkBox"){
		 
	 }
	 
		}
$(document).ready(
		function(){
			var dicList=$($("dic"));
			for(var k=0;k<dicList.length;k++){
				dicShow(dicList[k]);
			}
		}
);