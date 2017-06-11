<%@page
	import="dao.imp.formation.OrganismeFormationDAO,beans.formation.*,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/valence/css/formation/listeorganisme.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables_themeroller.css">
	<link rel="stylesheet" href="/valence/javascript/css/TableTools.css">
<%
	OrganismeFormationDAO org = new OrganismeFormationDAO();
	List<OrganismeFormation> liste = org.findAll();
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Organismes de formation</title>
</head>
<body>


	<div id="body">
	<p><%@ include file='/menus/menugeneral/menu.html' %></p><br>

		<div id="creation">LISTE ORGANISMES FORMATION</div>
		<br>
		<h3><%=liste.size()%> organismes référencés</h3>
		
		<br>
		<hr>
		<table id="tablelisteorganisation" class="display" >
			<thead>
			<tr>
				<th>Nom</th>
				<th>Adresse</th>
				<th>CP</th>
				<th>Ville</th>
				<th>Téléphone</th>
				<th>Actif</th>
				<th>Modif</th>
				<th></th>
			</tr>
			</thead>
			<tbody>
			<%
				for (int i = 0; i < liste.size(); i++) {
			%>
			<tr>
				<td><%=liste.get(i).getOrg()%></td>
				<td><%=liste.get(i).getAdr_org()%></td>
				<td><%=liste.get(i).getCp_org()%></td>
				<td><%=liste.get(i).getVille_org()%></td>
				<td><%=liste.get(i).getTel_org()%></td>
				<td>
					<%
						if (liste.get(i).isActif()) {
					%> oui <%
						} else {
					%> non <%
						}
					%>
				</td>
				<td align="center"><a
					href="/valence/jsp/formation/modifierorganismeformation.jsp?numero=<%=liste.get(i).getId_org()%>"><img
						src="/valence/images/bleu/mod.png" /></a></td>
				<td><input type="button" id="archiver" value="archiver" 
				onclick="location.href='/valence/controleur?action=archiverorganismeformation&numero=<%=liste.get(i).getId_org()%>'"></td>
			</tr>
			<%
				}
			%>
		</tbody>
		</table>
	
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
	<script type="text/javascript"
		src="/valence/javascript/TableTools.min.js"></script>
	
	<script type="text/javascript"
		src="/valence/javascript/scripts/tableaux.js"></script>
<br>
</div>
</body>
</html>