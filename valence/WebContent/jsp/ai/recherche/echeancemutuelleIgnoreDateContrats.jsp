
<%@ page
	import="beans.parametres.accueil.*,java.util.*,beans.identite.*,dao.imp.identite.*,dao.mutuelle.*,
beans.suivi.*,dao.imp.suivi.*,dao.imp.employeur.*,dao.imp.ai.*,divers.*,beans.ai.*,java.text.*,beans.mutuelle.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	SimpleDateFormat sdform = new SimpleDateFormat("dd-MM-yyyy");
	String tous = request.getParameter("tous");
	String affiche = null;

	String datedebut = request.getParameter("datedebut");
	String datefin = request.getParameter("datefin");

	MutuelleDAO mudao = new MutuelleDAO();
	List<Mutuelle> liste = null;
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>echeance mutuelle</title>



<link rel="stylesheet" href="/valence/css/ai/contrat/pardates.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.tablesorter.pager.css">
<link rel="stylesheet" href="/valence/javascript/css/theme.default.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<script type="text/javascript"
	src="/valence/javascript/scripts/attention.js"></script>
<script>
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier

		var datedebutrech = document.getElementById("datedebut").value;
		var datefinrech = document.getElementById("datefin").value;

		if (datedebutrech == "") {
			document.form1.datedebut.focus();
			// Afficher un message d'erreur
			attentionComplet(" La date de début doit être renseignée !!!!");
			return false;
		}

		if (datefinrech == "") {
			document.form1.datefin.focus();
			// Afficher un message d'erreur
			attentionComplet(" La date de fin doit être renseignée !!!!");
			return false;
		}

		formulaire.submit();
		return true;
	}
	function validertout(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier

		document.getElementById("datedebut").value="";
		var datefinrech = document.getElementById("datefin1").value;

		
		if (datefinrech == "") {
			document.form1.datefin1.focus();
			// Afficher un message d'erreur
			attentionComplet(" La date de fin doit être renseignée !!!!");
			return false;
		}

		formulaire.submit();
		return true;
	}
	
</script>
</head>

<body>
	<%
		if (datedebut == null || datefin == null) {
			java.sql.Date ddeb = ManipulationDates.premierJourMoisCourant();
			String affichedatedeb = sdform.format(ddeb);
			java.sql.Date dfin = ManipulationDates.finDumois(ddeb);
			String affichedatefin = sdform.format(dfin);
			affiche = "MUTUELLES SE TERMINANT PENDANT LA PERIODE DU " + affichedatedeb + " AU " + affichedatefin;
			liste = mudao.termineesDansFourchette(ddeb, dfin);

		} else {
			java.sql.Date datededeb = java.sql.Date.valueOf(datedebut);
			java.sql.Date datedefin = java.sql.Date.valueOf(datefin);
			String affichedatedebut = sdform.format(datededeb);
			String affichedatefin = sdform.format(datedefin);
			affiche = "MUTUELLES SE TERMINANT PENDANT LA PERIODE DU " + affichedatedebut + " AU " + affichedatefin;
			liste = (List<Mutuelle>) request.getAttribute("liste");
			IdentiteDAO idao = new IdentiteDAO();

		}
	%>

	<div id="body">
		<%@ include file='/menus/menugeneral/menu.html'%>
		<br>
		<div id="creation"><%=affiche%></div>

		<br>
		<form name="form1" method="post" action="/valence/controleur">
			<input type="hidden" name="action"
				value="echeancemutuelleignoredatescontrats" />

		<table class="table4">
				<tr>
					<td colspan='3'>Affichage du 01-01-2017 au :</td>
					<td><input type="text" class="centre" id="datefin1"
						name="datefin1" />
					<td><input type="button" value="Rechercher" id="droite"
						class="boutonvert" onclick="validertout(form1);" /></td>
				</tr>
			<tr><td> </td></tr>
			
				<tr>
					<td>Du :</td>
					<td><input type="text" class="centre" id="datedebut"
						name="datedebut" /></td>
					<td>au :</td>
					<td><input type="text" class="centre" id="datefin"
						name="datefin" />
					<td><input type="button" value="Rechercher" id="droite"
						class="boutonvert" onclick="valider(form1);" /></td>
				</tr>
			</table>

		</form>

		<!-- Affichage des résultats -->
		<br>
		<table class="table1">

			<tr>
				<td class="petit"><a
					href="/valence/controleur?action=echeancemutuelleignoredatescontrats&a=-2">M-2</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=echeancemutuelleignoredatescontrats&a=-1">M-1</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=echeancemutuelleignoredatescontrats&a=0">M</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=echeancemutuelleignoredatescontrats&a=1">M+1</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=echeancemutuelleignoredatescontrats&a=2">M+2</a></td>
			</tr>

		</table>
		<br> <br>
		<%
			if (liste != null) {
		%>
		<table id="listeecheancemutuelle1">
			<thead>
				<tr>

					<th width=40% >Nom-prénom</th>

					<th width=25% >Début</th>
					<th width=25% >Echéance</th>
					<th width=10% >Proposition</th>

				</tr>
			</thead>
			<tbody>
				<%
					for (int j = 0; j < liste.size(); j++) {
							Identite une = liste.get(j).getIdentite();
				%>

				<tr>
					<td><a
						href="/valence/jsp/accueil/afficheInscrit.jsp?numero=<%=une.getId_IDE()%>"><%=une.getNom_IDE()%>
							<%=une.getPrenom_IDE()%></a></td>

					<td>
						<%
							if (liste.get(j).getDateProposition() != null)
										out.println(sdform.format(liste.get(j).getDateProposition()));
						%>
					</td>
					<td>
						<%
							if (liste.get(j).getDateecheance() != null)
										out.println(sdform.format(liste.get(j).getDateecheance()));
						%>
					</td>

					<td align=center ><a
						href="/valence/jsp/mutuelle/affichage.jsp?idmutuelle=<%=liste.get(j).getId_mutuelle()%>&numero=<%=liste.get(j).getIdentite().getId_IDE()%>"><img
							src="/valence/images/bleu/voir.png" /></a></td>

				</tr>


				<%
					}
				%>

			</tbody>
		</table>


		<!--************************Rajout des pager a tablesorter*********************** -->
		<div id="pager" class="pager9">
			<form>
				<img src="/valence/javascript/images/icons/first.png" class="first" />
				<img src="/valence/javascript/images/icons/prev.png" class="prev" />
				<span class="pagedisplay"></span>
				<!-- this can be any element, including an input -->
				<img src="/valence/javascript/images/icons/next.png" class="next" />
				<img src="/valence/javascript/images/icons/last.png" class="last" />
				<select class="pagesize">
					<option selected="selected" value="25">25</option>
					<option value="50">50</option>
					<option value="100">100</option>
					<option value="99999">Tout</option>
				</select>
			</form>
		</div>

		<%
			} else
				out.println("Aucun résultat trouvé sur cette période");
		%>

	</div>
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
		src="/valence/javascript/scripts/journouveaux.js"></script>

	<script type="text/javascript"
		src="/valence/javascript/jquery.tablesorter.min.js"></script>

	<script type="text/javascript"
		src="/valence/javascript/jquery.tablesorter.pager.min.js"></script>

	<script type="text/javascript"
		src="/valence/javascript/scripts/tableautablesorter.js"></script>

	<br>
</body>
</html>