<%@ page contentType = "text/html" pageEncoding = "utf-8" %>
<%@ page import = "dao.UserDAO"%>

<%
	request.setCharacterEncoding("utf-8");

	String uid = request.getParameter("id");
	//String upass = request.getParameter("ps");
	//String upass2 = request.getParameter("ps2");
	String jsonstr = request.getParameter("jsonstr");
	
	UserDAO dao = new UserDAO();
	if (dao.exists(uid)) {
		out.print("EX"); // 이미 가입된 회
		return;
	}
	
	if (dao.insert(uid, jsonstr) == true) {
		session.setAttribute("id", uid);
		out.print("OK");
	}

	
	else {
		out.print("ER");
	}
%>