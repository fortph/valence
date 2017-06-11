
<%@ page
	import="beans.parametres.accueil.*,java.util.concurrent.TimeUnit,java.util.*,beans.identite.*,dao.imp.identite.*,
beans.suivi.*,dao.imp.suivi.*,dao.imp.employeur.*,dao.imp.ai.*,divers.*,beans.ai.*,java.text.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	SimpleDateFormat sdform = new SimpleDateFormat("dd-MM-yyyy");
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/valence/css/ai/contrat/extranet.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<link rel="stylesheet" href="/valence/javascript/css/TableTools.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables_themeroller.css">
<title>Liste Extranet</title>

</head>

<body>
	<%
		Date jour = new Date();

		String affiche = "LISTE DES PERSONNES RENTREES DANS L'EXTRANET";
		ExtranetDAO exdao = new ExtranetDAO();
		List<Extranet> liste = exdao.listeExtranetSansDateFin();
		IdentiteDAO idao = new IdentiteDAO();
	%>

	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div id="creation"><%=affiche%>
		</div>
		<br>
		<!-- Affichage des résultats -->
		<br> <br> 
		<table id="listeextranet" class="display">
			<thead>
				<tr>
					<th>Nom-prénom</th>
					<th>CP</th>
					<th>Ville</th>
					<th>Téléphone</th>
					<th>Début extranet</th>
					<th>Dépas Mois</th>
					<th>Dépas Jours</th>
				</tr>
				
			</thead>
			<tbody>
				<%
					for (int i = 0; i < liste.size(); i++) {
						Identite identite = liste.get(i).getIdentite();
						Extranet extra = exdao.findByID(liste.get(i).getId_extranet());
						Date une = extra.getDebut();
						long diff = Difference2Dates.getDateDiffEnJours(une, jour, TimeUnit.DAYS);
				%>

				<tr>
					<td><a
						href="/valence/jsp/accueil/afficheInscrit.jsp?numero=<%=identite.getId_IDE()%>"><%=identite.getNom_IDE()%>
							<%=identite.getPrenom_IDE()%></a></td>
					<td><%=identite.getCp_IDE()%></td>
					<td><%=identite.getVille_IDE()%></td>
					<td><%=identite.getMobile_IDE()%></td>
					<td><%=sdform.format(extra.getDebut())%></td>
					<td><%=diff / 30 %></td>
					<td><%=diff%></td>
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
		<script type="text/javascript" src="/valence/javascript/TableTools.js"></script>

		<script type="text/javascript"
			src="/valence/javascript/scripts/tableaux.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/scripts/datatabletripardates.js"></script>

		<br>

	</div>

</body>
</html>