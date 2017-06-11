<%@page
	import="dao.imp.identite.*, beans.identite.*,java.sql.*, java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Recherche</title>
<%
	List<Identite> liste = (List<Identite>) request.getAttribute("resultset");
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/valence/css/listing.css">

<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />

<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables_themeroller.css">
</head>
<body>
	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div id="creation"> ACCUEIL: RECHERCHE INSCRIPTION</div>
	<br>

		<%
			if(liste.size()>0){
		%>

		<br>
		<h3><%=liste.size()%>
			réponses ont été trouvées
		</h3>
		<input type="button" value="Créer"
			onclick="self.location.href='jsp/accueil/inscription.jsp'" />


		<!-- si des resultats ont ete trouves-->
		<table id="accueilpourcent" class="display">
			<thead>
				<tr>
					<th>NOM-PRENOM</th>
					<th>VILLE</th>
					<th>PORTABLE</th>
					<th>FIXE</th>
					<th>Modifier</th>

				</tr>
			</thead>
			<tbody>
				<%
					for (int i = 0; i < liste.size(); i++) {Identite tamp = liste.get(i);
				%>

				<tr>
					<td><a
						href="jsp/accueil/afficheInscrit.jsp?numero=<%=tamp.getId_IDE()%>"><%=tamp.getNom_IDE()%>
							<%=tamp.getPrenom_IDE()%></a></td>
					<td><%=tamp.getVille_IDE()%>
					<td><%=tamp.getMobile_IDE()%></td>
					<td><%=tamp.getFixe_IDE()%></td>
					<!-- lien modifier -->
					<td><center>
							<a
								href="jsp/accueil/modifierInscrit.jsp?numero=<%=tamp.getId_IDE()%>"><img
								src="images/bleu/mod.png" /></a>
						</center></td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>

		<%
			}
				
			
			else {
		%>


		<p>Aucune réponse ne correspond à la recherche</p>
		<input type="button" value="Créer"
			onclick="self.location.href='/valence/jsp/accueil/inscription.jsp'" />
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
			src="/valence/javascript/scripts/tableaux.js"></script>
		<BR>
	</DIV>

</body>
</html>