
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
String num=request.getParameter("employeur");


%>
<link rel="stylesheet" href="/valence/css/employeur/affichage.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>nouveau service</title>
</head>
<body>
<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div  id="creation">AJOUTER UN SERVICE</div>
		<br>
		<hr>
		<form method="post" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="creerservice" />
			<input type="hidden" name="idemployeur" value="<%=num %>" />
			<table class="table1">
			<tr>
			<td class="label">Service</td><td><input type="text" name="service" id="service" /></td>
			</tr>
			</table>
			<br>
		<input type="submit" value="Enregistrer" />
			
		</form>
		
</div>
</body>
</html>