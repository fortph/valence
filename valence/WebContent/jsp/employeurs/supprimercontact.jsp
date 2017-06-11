<%@ page import="beans.employeurs.*,dao.imp.employeur.*,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/valence/css/employeur/affichage.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>contact</title>
<%
	String cont = request.getParameter("contact");
	ContactDAO contdao = new ContactDAO();
	Contact contact = contdao.findByID(Integer.valueOf(cont));
	String emp = request.getParameter("emp");
	EmployeurDAO empdao = new EmployeurDAO();
	Employeur employeur = empdao.findByID(Integer.valueOf(emp));
%>
</head>
<body>
	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div id="creation">SUPPRESSION D'UN CONTACT EMPLOYEUR</div>
		<br>
		<hr>
		<br>
		<div class="centregras">ATTENTION</div>
		<br>
		<div class="centre">
			Voulez vous réellement supprimer le contact
			<label class="couleur" ><%=contact.getNom_contact()%>
			<%=contact.getPrenom_contact()%></label></div>
		<div class="centre">
			pour l'employeur
			<label class="couleur" ><%=employeur.getRs_employeur()%></label></div>
		<form method="post" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="supprimercontact" /> <input
				type="hidden" name="contact" value="<%=cont%>" />
				<input type="hidden" name="emp" value="<%=emp%>" />
				<br>
			<div class="centre">
				<input type="submit" value="OUI" /> <input type="button"
					value="NON"
					onclick="self.location.href='/valence/jsp/employeurs/affichage.jsp?numero=<%=emp%>'" />
			</div>
		</form>
	</div>
</body>
</html>