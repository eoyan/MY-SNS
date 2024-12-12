<%@ page contentType = "text/html" pageEncoding = "utf-8" %>
<%@ page import = "dao.*" %>

<%
	String uid = request.getParameter("id");
	UserDAO dao = new UserDAO();
	
	if (!dao.delete(uid)) out.print("NE");
	else {
		out.print("OK");
	}
	
%>
