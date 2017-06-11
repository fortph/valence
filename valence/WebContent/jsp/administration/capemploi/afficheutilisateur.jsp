<%@page import="beans.parametres.capemploi.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
String id=request.getParameter("id");
String login=request.getParameter("login");
String modif=request.getParameter("modif");
UtilisateurDAO utildao=new UtilisateurDAO();



%>


<link rel="stylesheet" href="/valence/css/admin/affichecorfi.css">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>affiche utilisateur</title>
<script type="text/javascript"
	src="/valence/javascript/scripts/attention.js"></script>


</head>

<div id="body">
		<p><%@ include file='/menus/admin/menu.html'%></p>
		<br>
		<div  id="creation">AFFICHAGE UTILISATEUR</div>
		<br>
		<br>
		<%
		
			Utilisateur utilisateur=utildao.findByID(Integer.parseInt(id));
		%>
			<table class="table1">
			<tr>
			<td class="un">NOM </td><td class="blanc"><%=utilisateur.getNom() %></td>
			</tr>
			<tr>
			<td class="un">PRENOM </td><td class="blanc"><%=utilisateur.getPrenom() %></td>
			</tr>
			<tr>
			<td class="un">LOGIN</td><td class="blanc"><%=utilisateur.getLogin() %></td>
			</tr>
			<tr>
			<td class="un">PRIVILEGE</td><td class="blanc"><%=utilisateur.getPrivilege() %>
			</td>
			</tr>
			<tr><td class="un">MAIL</td><td class="blanc"><%=utilisateur.getMail() %></td>
			</tr>
			<tr><td class="un">ACTIF</td>
			<td class="blanc"><% 
			if(utilisateur.isActif()) out.println("OUI"); else out.println("NON"); %>
			</td>
			</tr>
			</table>
			<%
			if (modif!=null){
			%>
			<script>alert(" L'utilisateur de  login <%=login%> à été modifié ...")</script>
			<%
			}
			else {
			%>
			<script>alert(" L'utilisateur de  login <%=login%> à été créé...")</script>
			
			<%
			}
			%>
		
</div>
</body>
</html>