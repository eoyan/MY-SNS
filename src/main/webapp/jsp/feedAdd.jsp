<%@ page import="java.util.*"%>
<%@ page contentType = "text/html" pageEncoding = "utf-8" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="java.io.IOException" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="java.io.File" %>
<%@ page import="dao.*" %>
<%@ page import="util.*" %>

<%
    String jsonstr = null;
    String ufname = null; // 선언 추가
    byte[] ufile = null;
    request.setCharacterEncoding("utf-8");

    ServletFileUpload sfu = new ServletFileUpload(new DiskFileItemFactory());
    List items = sfu.parseRequest(request);
    Iterator iter = items.iterator();

    while (iter.hasNext()) {
        FileItem item = (FileItem) iter.next();
        String name = item.getFieldName();
        if (item.isFormField()) {
            String value = item.getString("utf-8");
            if (name.equals("jsonstr")) jsonstr = value;
        } else {
        	if (name.equals("image")) {
        	    ufname = item.getName(); // 파일 이름 가져오기
        	    ufile = item.get();
        	    String root = application.getRealPath(java.io.File.separator);
        	    System.out.println(root);
        	    FileUtil.saveImage(root, ufname, ufile);
        	}
        } 
    }

    FeedDAO dao = new FeedDAO();
    if (dao.insert(jsonstr) == true) { // ufname이 null일 경우 빈 문자열 저장
        out.print("OK");
    } else {
        out.print("ER");
    }
    