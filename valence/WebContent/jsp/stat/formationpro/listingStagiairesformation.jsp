<%@page
	import="beans.formationpro.*,dao.imp.identite.*,beans.identite.*,beans.parametres.formationpro.*,dao.imp.formationpro.*, java.util.*,java.sql.*,dao.exception.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	java.sql.Date debut=(java.sql.Date)request.getAttribute("debut");
java.sql.Date fin=(java.sql.Date)request.getAttribute("fin");
List <FormationProInscription> liste=(List <FormationProInscription>)request.getAttribute("liste");
Formprotheme formation=(Formprotheme)request.getAttribute("theme");
%>

<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<link rel="stylesheet" href="/valence/javascript/css/TableTools.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables_themeroller.css">
<link rel="stylesheet" href="/valence/css/formation/affichage.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>listing stagiaires</title>
</head>
<body>
	<div id="body">
	<p><%@ include file='/menus/menugeneral/menu.html' %></p><br>
		<h3>
			Liste des stagiaires enregistrés à
			<%=formation.getTheme()%>
			entre le
			<%=debut%>
			et le
			<%=fin%></h3>
		<hr>
		<br>
		<%
			if(liste.size()>0){
		%>
		<table id="tableformationlistedates" class="display">
			<thead>
				<tr>
					<th>NOM</th>
					<th>PRENOM</th>
					<th>CODE POSTAL</th>
					<th>VILLE</th>
					<th>NIVEAU FORMATION</th>
					<th>HEURES FORMATION</th>
					<th>STATUT</th>
					<th>EMPLOYEUR</th>
				</tr>
			</thead>
			<tbody>

				<%
					
					for(int i=0;i<liste.size();i++){
						IdentiteDAO iddao=new IdentiteDAO();
						Identite identite=iddao.findByID(liste.get(i).getIdentite().getId_IDE());
											
				%>
				<tr>
					<td><%=identite.getNom_IDE()%></td>
					<td><%=identite.getPrenom_IDE()%></td>
					<td><%=identite.getCp_IDE() %> </td>
					<td><%=identite.getVille_IDE() %></td>
					<td><%=liste.get(i).getNiveau().getNiveau()  %></td>
					<td><%=liste.get(i).getNbheures() %></td>
					<td><%=liste.get(i).getStatut().getStatut() %></td>
					<td><%=liste.get(i).getEmployeur().getEmployeur() %></td>
					
				</tr>
				<%
					}
						 
				%>

			</tbody>


		</table>
		<br> <br>
		<div id="total">
			TOTAL DES STAGIAIRES :
			<%=liste.size()  %>
		</div>
		<br>

		<%
			}
		else {
		%>
		<h3>Aucun resultat trouve</h3>
		<%
			}
		%>



<script type="text/javascript"
			src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
		<script>
			window.jQuery
					|| document
							.write('<script src="/valence/javascript/jquery-2.0.3.min.js"><\/script>')
		</script>
		<script type="text/javascript"
			src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
		<script>
			window.jQuery
					|| document
							.write('<script src="/valence/javascript/jquery-ui-1.10.3.custom.min.js"><\/script>')
		</script>
		<script type="text/javascript"
			src="/valence/javascript/jquery.dataTables.min.js"></script>

		<script type="text/javascript"
			src="/valence/javascript/ZeroClipboard.js"></script>
		<script type="text/javascript" src="/valence/javascript/TableTools.min.js"></script>

		<script type="text/javascript"
			src="/valence/javascript/scripts/tableaux.js"></script>
		<br>

	</div>
</body>
</html>