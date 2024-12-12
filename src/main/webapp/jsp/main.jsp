<%@ page contentType = "text/html" pageEncoding="UTF-8" %>
<%@ page import = "dao.*" %>
<%@ page import = "java.util.*" %>


<%
   	// 피드 리스트 가져오기
   	String str = (new FeedDAO()).getList(); 
	out.print(str);
%>