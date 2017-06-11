<%@ page
	import="beans.employeurs.*,dao.imp.employeur.*,java.text.*"%>
	
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
String numoffrepropo=request.getParameter("numoffrepropo");
PositionnerPersonneDAO podao=new PositionnerPersonneDAO();
PositionnerPersonne position=podao.findByID(Integer.parseInt(numoffrepropo));

%>
<link rel="stylesheet"
	href="/valence/css/employeur/affichageoffreemp.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>modification offre</title>

</head>
<body>

<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div id="creation">MODIFICATION D'UNE OFFRE</div>
		<br>
		<hr>
		<h3>POSITIONNEMENT</h3>
		<br>
		<form method="post" action="/valence/controleur" >
		<input type="hidden" name="action" value="modifierpositionoffre" />
		<input type="hidden" name="numoffrepropo" value=<%=numoffrepropo %> />
		
		<table id="table5">
		<tr class="col1"><td>NOM PRENOM</td>
		<td>TEL</td>
		<td>CONTACT</td>
		<td>REFERENT</td>
		<td id="fixe">REPONSE</td>
		</tr>
		<tr>
		<td><%=position.getIdentite().getNom_IDE() %> <%=position.getIdentite().getPrenom_IDE() %></td>
		<td><%=position.getIdentite().getFixe_IDE() %></td>
		<td><% if(position.getDatecontact()!=null) out.println(sdf.format(position.getDatecontact()));  %></td>
		<td><%=position.getSalarie().getPrenom() %> <%=position.getSalarie().getNom() %></td>
		<td><input id="fixe1" type="text" name="reponse" /></td>
		
		</table>
		<br>
		<input id="boutondroit" type="submit" value="Enregistrer" />
		</form>
		</div>

</body>
</html>