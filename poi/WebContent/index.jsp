<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.ron.model.PlayListFilePIC"%>
<%@ page import="com.ron.pereference.SystemGlobals"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<% for(PlayListFilePIC tmp:SystemGlobals.playlistfilepic){
	System.out.println(tmp.toString());
}%>
<br>



</body>
</html>