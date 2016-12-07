
<%@page import="java.net.URLEncoder"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.support.XmlWebApplicationContext"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>mainform </title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
     <script type="text/javascript" src="js/jquery-1.9.1.js"></script>
     <script src="js/edingcore.js" type="text/javascript"></script>
  </head>
  <body>
 	<%
		String usr="中文";
		Cookie cookie=new Cookie("user",URLEncoder.encode(usr,"UTF-8"));

  	//ApplicationContext context=WebApplicationCont	extUtils.getWebApplicationContext(request.getSession().getServletContext());
   %>
   	<script type="text/javascript">
   		$(document).ready(
   			function(){
   				var par=new Object();
   				par.name="eding";
   				par.passwd=3.22;
   				var v1=ajx("userBean.test",par);
   			}
   		);
   	</script>
   	<%
   		cookie.setValue(" {userName:'name',power:35}");
   	 %>
  </body>
</html>
