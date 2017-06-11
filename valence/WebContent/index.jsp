
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="/valence/css/index.css" />
<link rel="stylesheet" type="text/css" href="/valence/css/menu.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>accueil</title>

</head>
<body>

	<div id="bonjour" onclick="document.location.href='/valence/jsp/accueil/rechercherPersonne.jsp';" >
		<center>
			<%
				out.println("<p>Bienvenue</p><br>");
			%>
		
			<noscript>
ATTENTION
<br>
JAVASCRIPT doit etre activé pour que cette application 
<br>fonctionne correctement...
</noscript>
		</center>
		</div>

	<%@include file="menu.jsp" %>
	

</body>
</html>