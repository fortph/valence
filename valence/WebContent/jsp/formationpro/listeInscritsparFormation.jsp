<%@page
	import="dao.imp.formationpro.*,beans.parametres.formationpro.*,dao.imp.identite.*,beans.identite.*,beans.formationpro.*,java.util.*,java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="/valence/css/formation/listeparformation.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables_themeroller.css">
<link rel="stylesheet" href="/valence/javascript/css/TableTools.css">
<%
	Formprotheme theme = (Formprotheme) request
			.getAttribute("formation");
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>liste inscrits</title>
</head>
<body>

<div id="body">
	<p><%@ include file='/menus/menugeneral/menu.html' %></p><br>
		<h2>Liste des inscrits à <%=theme.getTheme() %></h2>
		
				
		<%	
		
		FormationProInscriptionDAO fordao=new FormationProInscriptionDAO();
		FormationProInscription liste=new FormationProInscription();
		liste.setTheme(theme);
		
		 List<FormationProInscription> listeinscrits=fordao.findByCriteria(liste);
		 
		 
				if(listeinscrits.size()>0){
		%>
		<h3><%=listeinscrits.size()%>
			personnes  inscrites
			</h3>
			<br>
			<hr>
		<table id="tableinscritpouruneformation" class="display">
			<thead>
				<tr>
					<th>NOM</th>
					<th>PRENOM</th>
					<th>CODE POSTAL</th>
					<th>VILLE</th>
					<th>NIVEAU</th>
					<th>DATE DEBUT</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<%
					for(int i=0;i<listeinscrits.size();i++){
								  Identite une=new IdentiteDAO().findByID(listeinscrits.get(i).getIdentite().getId_IDE());
				%>
				<tr>
					<td><%=une.getNom_IDE()%></td>
					<td><%=une.getPrenom_IDE()%></td>
					<td><%=une.getCp_IDE()%></td>
					<td><%=une.getVille_IDE()%></td>
					<td><%=listeinscrits.get(i).getNiveau().getNiveau() %></td>
					<td><%=listeinscrits.get(i).getDebutformation()%></td>
					<td><a href="jsp/formationpro/modifierInscritformation.jsp?inscription=<%=listeinscrits.get(i).getId_inscription()  %>">Modifier</a></td>
				</tr>
				<%
					}
				%>
			</tbody>

		</table>
		<br>
		<%
			}
				else
				{
		%>
		<h3>Personne n'est inscrit à cette formation</h3>
		<%
			}
		%>
		<br />
	

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


	


</div>
</body>
</html>