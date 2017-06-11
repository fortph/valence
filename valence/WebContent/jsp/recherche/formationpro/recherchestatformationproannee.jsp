<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Recherche par critere</title>
<link rel="stylesheet" href="/valence/css/formationpro/affichagestat.css">

<script type="text/javascript" src="/valence/dwr/engine.js"></script>
<script type="text/javascript" src="/valence/dwr/util.js"></script>
<script type="text/javascript"
	src="/valence/dwr/interface/NiveauFormation.js"></script>
<script type="text/javascript"
	src="/valence/dwr/interface/ListeFormationsDAO.js"></script>
	
	
	<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>
			
			
<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		var debut = document.getElementById("datedebutrech").value;
		
		if (debut == "" || debut.length != 4 || isNaN(debut)) {
			document.form1.datedebutrech.focus();
			// Afficher un message d'erreur
			attentionComplet(" L'année doit être correctement renseignée  !!!!");
			return false;
		}
		
		formulaire.submit();
		return true;
	}
</script>
</head>
<body>


	<div id="body">
	<p><%@ include file='/menus/menugeneral/menu.html' %></p><br>
		<div id="creation">STATITIQUES FORMATION PROFESSIONNELLE </div>
		<h3>Sélection des critères à intégrer à votre recherche</h3>
		<hr>
		<br />
		<form method="post" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="statformationproanneee" />
			<table>
				
				<tr>
					<td><label for="datedebutrech" class="nom">ANNEE DE RECHERCHE :</label></td>
					<td><input type="text" id="datedebutrech" class="largeur"
						value="2014" name="datedebutrech" /></td>
				</tr>
				<tr>
					<td><input type="radio" class="choix" name="annee"
						value="civil" /><label>Année Civile</label></td>
						
					
				</tr>
				<tr>
					<td><input type="radio" checked="checked" class="choix"
						name="annee" value="exercice" /><label>Exercice
							"PYRAMIDE"</label></td>
				
			</table>
			<br>
<hr><br>
			
			<div id="fin">
				<input type="button" value="Rechercher" class="boutonvert" onclick="valider(form1);" /><input
					type="reset" class="boutonrouge" value="Effacer" />
			</div>
		</form>

	</div>


	<script type="text/javascript"
		src="/valence/javascript/scripts/instructions.js"></script>
</body>
</html>