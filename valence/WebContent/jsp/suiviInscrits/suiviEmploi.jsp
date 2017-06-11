<%@ page
	import="java.util.*, java.text.*,beans.identite.*,dao.imp.identite.*"%>
	
	
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>suivi emploi</title>
<%


SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
String jour =sdf.format(new Date());

%>

<link rel="stylesheet" href="/valence/css/suivi/suiviemploi.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />


<script type='text/javascript' src='/valence/dwr/engine.js'></script>
<script type='text/javascript' src='/valence/dwr/util.js'></script>
<script type="text/javascript"
	src="/valence/dwr/interface/Utilisateur.js"></script>
<script type="text/javascript" src="/valence/dwr/interface/Employeur.js"></script>
<script type="text/javascript" src="/valence/dwr/interface/Rome.js"></script>

<script type="text/javascript"
	src="/valence/javascript/scripts/attention.js"></script>


<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier

		var utilisateurs = document.getElementById("utilisateurs").value;
		var employeurs = document.getElementById("employeurs").value;
		var emploipropose = document.getElementById("emploipropose").value;
		
		

		if (utilisateurs == "Aucun" ||utilisateurs==0) {
			document.form1.utilisateurs.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le référent doit être renseigné !!!!");
			return false;

		}
		
		
		if (employeurs == "Aucun" ||employeurs==0) {
			document.form1.employeurs.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le nom de l'employeur doit être renseigné !!!!");
			return false;

		}
		
		if (emploipropose == ""|| emploipropose==0) {
			document.form1.emploipropose.focus();
			// Afficher un message d'erreur
			attentionComplet(" L'emploi doit être renseigné !!!!");
			return false;

		}

		
		formulaire.submit();
		return true;
	}
</script>

<%
	String numero = request.getParameter("personne");
IdentiteDAO idao=new IdentiteDAO();
Identite ident=idao.findByID(Integer.parseInt(numero));
%>
</head>
<body>

	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div id="creation">NOUVEAU SUIVI EMPLOI pour <%=ident.getPrenom_IDE() %> <%=ident.getNom_IDE() %></div>
		<br>
		<hr>
		<h3>SUIVI EMPLOI</h3>
		<br>
		<form method="post" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="suiviemploisalarie" /> <input
				type="hidden" name="personne" value="<%=numero%>" />

			<table id="table">
				<tr>
					<td class="un">Date de rédaction :</td>
					<td class="deux"><input class="taille" type="text" id="dateredaction"
						name="dateredaction" value="<%=jour %>" /></td>
					<td class="un">Nom du référent :</td>
					<td class="deux"><select class="taille" name="utilisateurs"
						id="utilisateurs" onfocus="afficheCapUtilisateurs();">
							<option>Aucun</option>
					</select></td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td>Employeur :</td>
					<td class="deux"><select name="employeurs" id="employeurs"
						onfocus="recupererEmployeur();"><option>Aucun</option></select></td>
				</tr>
				<!-- 
				<tr>
					<td>Type contrat :</td>
					<td class="deux"><input type="text" name="type" id="type" /></td>
				</tr>
				-->
				<tr>
					<td>Métier :</td>
					<td class="deux"><select name="emploipropose" id="emploipropose"
						onfocus="recupererEmploiPropose();"></select></td>
				</tr>
				<tr>
					<td class="un">Date début suivi :</td>
					<td class="deux"><input type="text"  id="datedebut" name="datedebut"></td>
					<td class="un">Date fin suivi :</td>
					<td class="deux"><input type="text" id="datefin" name="datefin"></td>
				</tr>
				
				<tr>
					<td></td>
				</tr>
			</table>
			<br>
			<p class="col1">COMMENTAIRES</p>
			<textarea class="col3" rows="5" name="commentaires">
</textarea>
			<br> <br> <input type="button" id="droite"
				value="Enregistrer" onclick="valider(form1);" />

		</form>

		<br>
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
		src="/valence/javascript/scripts/dwrfonctionsemployeurs.js"></script>


	<script type="text/javascript"
		src="/valence/javascript/scripts/journouveaux.js"></script>

</body>
</html>