<%@page
	import="dao.imp.employeur.*, beans.employeurs.*,java.sql.*, java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Recherche</title>

<%
	List<Employeur> liste = (List<Employeur>) request.getAttribute("resultset");
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/valence/css/employeur/listing.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />


</head>
<body>
	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<h2>LISTE DES EMPLOYEURS PAR RAISON SOCIALE</h2>

		<%
			if(liste.size()>0){
		%>

		<br>
		<h3><%=liste.size()%>
			réponses ont été trouvées
		</h3>
		<input type="button" value="Créer"
			onclick="self.location.href='jsp/employeurs/creation.jsp'" />


		<!-- si des resultats ont ete trouves-->
		<table id="tableemployeur">
			<thead>
			<tr>
				<th>Raison sociale</th>
				<th>Responsable</th>
				<th>Ville</th>
				<th>Tél.</th>
				<th>Fax</th>
				<th>Structure</th>
				<th></th>
				<!-- lien pour modifier -->
			</tr>
			</thead>
			<tbody>
			<%
				for (int i = 0; i < liste.size(); i++) {
						Employeur emp = liste.get(i);
			%>

			<tr>
				<td class="nom"><a
					href="jsp/employeurs/affichage.jsp?numero=<%=emp.getId_employeur()%>"><%=emp.getRs_employeur()%>
				</a></td>
				<td><%=emp.getNomresponsable()%> <%=emp.getPrenomresponsable()%></td>
				<td><%=emp.getVille()%></td>
				<td><%=emp.getTel1()%></td>
				<td><%=emp.getFax()%></td>
				<td><%=emp.getStructure().getStructure()%></td>
				<!-- lien modifier -->
				<td><center>
						<a
							href="jsp/employeurs/modificationemployeur.jsp?numero=<%=emp.getId_employeur()%>"><img
							src="images/bleu/mod.png" /></a>
					</center></td>
			</tr>
			
			
				<%
				}
		%>
		</tbody>
		</table>
		<%
		
		}else {
			
			%>

			<br>
			<h3>Aucune réponse ne correspond à la recherche</h3>
			<input type="button" value="Créer"
				onclick="self.location.href='/valence/jsp/employeurs/creation.jsp'" />
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

		
			<BR>
		
	</div>
</body>
</html>