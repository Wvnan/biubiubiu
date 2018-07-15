<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${ pageContext.request.contextPath }/css/bootstrap.min.css" type="text/css" />
		<script src="${ pageContext.request.contextPath }/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${ pageContext.request.contextPath }/js/bootstrap.min.js" type="text/javascript"></script>
</head>
<body>
<%@ include file="menu.jsp" %>

	<div class="container">
		<h1>${msg }</h1>
		<h3><a href="${ pageContext.request.contextPath }/index.jsp">首页</a></h3>
		<h3><a href="${ pageContext.request.contextPath }/UserServlet?method=loginUI">登录</a></h3>
		<h3><a href="${ pageContext.request.contextPath }/UserServlet?method=registUI">注册</a></h3>
	</div>


	<!--
            	作者：ci2713@163.com
            	时间：2015-12-30
            	描述：页脚部分
            -->
			  <%@ include file="footer.jsp" %>
</body>
</html>