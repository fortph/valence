<%@page import="dao.imp.formation.*, beans.formation.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%

String id=request.getParameter("id");
AnimatriceDAO dao=new AnimatriceDAO();
Animatrice anim=dao.findByID(Integer.parseInt(id));


%>

<link rel="stylesheet" href="/valence/css/admin/affichecorfi.css">


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>affiche utilisateur</title>

<script type="text/javascript"
	src="/valence/javascript/scripts/attention.js"></script>


</head>
<body>
<div id="body">
		<p><%@ include file='/menus/admin/menu.html'%></p>
		<br>
		<div  id="creation">AFFICHAGE UTILISATEUR</div>
		<br>
		
			
			<table class="table1">
			<tr>
			<td class="un">NOM </td>
			<td class="blanc"><%=anim.getNom() %></td>
			</tr>
			<tr><td class="un">MAIL</td><td class="blanc"><%=anim.getMail() %></td>
			
			</tr>
			<tr><td class="un">ACTIF</td>
			<td class="blanc"><% if (anim.isActif()) out.println("OUI"); else out.println("NON"); %>
			</td>
			</tr>
			</table>
			
			<br>
		
		
		
		
		
		<br>
		
</div>
</body>
</html>