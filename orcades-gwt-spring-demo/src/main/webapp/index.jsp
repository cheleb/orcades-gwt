
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String serverIP = InetAddress.getByName(request.getServerName())
			.getHostAddress();
%>

<%@page import="java.net.InetAddress"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sample index</title>
</head>
<body>
<h1>Simple index page</h1>
<center><a href="search.html?gwt.codesvr=<%=serverIP%>:9997">search
hosted mode</a></center>
</body>
</html>