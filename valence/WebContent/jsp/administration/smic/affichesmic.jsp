<%@page import="beans.smic.*,java.text.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
SmicDAO smicdao=new SmicDAO();
Smic ancien=smicdao.findByID(1);
%>

<link rel="stylesheet" href="/valence/css/admin/statut.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>affiche smic</title>


</head>
<body>
<div id="body">
		<p><%@ include file='/menus/admin/menu.html'%></p>
		<br>
		<div  id="creation">AFFICHAGE SMIC</div>
		<br>
		<hr>
		
			<table class="table1">
			<tr>
			<td class="un">ANCIEN SMIC</td><td class="blanc"><%=ancien.getAnciensmic() %></td></tr>
			<tr><td class="un">SMIC EN VIGUEUR</td><td class="blanc"><%=ancien.getTsxmic() %></td></tr>
			
			<tr><td class="un">Date d'entrée en vigueur</td><td class="blanc"><%=sdf.format(ancien.getDate_smic())%></td></tr>
			</table>
			<br>
		
		
		
		
</div>
</body>
</html>