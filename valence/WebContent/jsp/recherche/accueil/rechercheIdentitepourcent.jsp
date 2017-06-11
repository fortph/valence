<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/valence/css/recherche/rechercheidentite.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<link rel="stylesheet" href="/valence/javascript/css/TableTools.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables_themeroller.css">
	<script type="text/javascript"
	src="/valence/javascript/scripts/attention.js"></script>	
<script type="text/javascript">

	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		var debut = document.getElementById("datedebutrech").value;
		var fin = document.getElementById("datefinrech").value;
		if (debut == "") {
			document.form1.datedebutrech.focus();
			// Afficher un message d'erreur
			attentionComplet(" La date de début doit être renseignée  !!!!");
			return false;
		}
		if (fin == "") {
			document.form1.datefinrech.focus();
			// Afficher un message d'erreur
			attentionComplet(" La date de fin doit être renseignée  !!!!");
			return false;
		}
		formulaire.submit();
		return true;
	}
</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Statistiques</title>
</head>
<body>
	<div id="body">
	<p><%@ include file='/menus/menugeneral/menu.html' %></p>
		<div id="creation">STATISTIQUES ACCUEIL</div>
		<br />
		<h4>Sélection des critères à intégrer à votre recherche</h4>
		<form method="post" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="stataccueilpourcent" />
			<table>
				<tr>
					<td><label for="datedebutrech">Date accueil de Début
							de recherche :</label></td>
					<td><input type="text" id="datedebutrech" class="largeur centre"
						name="datedebutrech"  /></td>
				</tr>
				<tr>
					<td><label for="datefinrech">Date accueil de Fin de
							recherche :</label></td>
					<td><input type="text" id="datefinrech" class="largeur centre"
						name="datefinrech" /></td>
				</tr>
				
			</table>
			<br>
			<hr>
			<br>
			<div id="droite">
				<input type="button" value="Rechercher" class="boutonvert" onclick="valider(form1);" /><input
					type="reset" class="boutonrouge" value="Effacer" />
			</div>
		</form>

	



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
		src="/valence/javascript/scripts/jour.js"></script>
	<script type="text/javascript"
		src="/valence/javascript/scripts/instructions.js"></script>
		
		</div>
</body>
</html>