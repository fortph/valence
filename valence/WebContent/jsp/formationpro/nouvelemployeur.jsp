<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="/valence/css/formationpro/preinscription.css">
	<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />

<script type="text/javascript" src="/valence/dwr/engine.js"></script>
<script type="text/javascript" src="/valence/dwr/util.js"></script>

<script type="text/javascript"
	src="/valence/dwr/interface/FormprostatutemployeurDAO.js"></script>
<script type="text/javascript"
	src="/valence/dwr/interface/CodesPostaux.js"></script>
	
	
	
	<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>
			

<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		var emp = document.getElementById("nomemployeur").value;		

		if (emp == "") {
			document.form1.nomemployeur.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le nom de l'employeur doit être renseignée  !!!!");
			return false;
		}
		formulaire.submit();
		return true;
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>nouvel employeur</title>
</head>
<body>
	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<h2>Création d'un nouvel employeur</h2>
		<br>
		<hr>
		<form method="post" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="formpronouveauemployeur" />
			<table>
				<tr>
					<td>Nom :</td>
					<td><input type="text" name="nomemployeur" id="nomemployeur" onfocus="recupererStatutFormationspro();"/></td>
				</tr>
				<tr>
					<td>Adresse :</td>
					<td><input type="text" name="adresse" id="adresse" /></td>
				</tr>
				<tr>
					<td>Code Postal :</td>
					<td><input type="text" id="cp" name="cp"
						onblur="recupererSaisie();" /></td>
				</tr>
				<tr>
					<td>VILLE :</td>
					<td>
						<!-- affichage des villes correspondant au code postal --> <select
						id="ville" name="ville">
					</select>
					</td>
				</tr>
				<tr>
					<td>Téléphone :</td>
					<td><input type="text" name="fixe" id="fixe" /></td>
				</tr>
				<tr>
					<td>Mobile :</td>
					<td><input type="text" name="mobile" id="mobile" /></td>
				</tr>
				<tr>
					<td>Statut :</td>
					<td><select name="nomstatut" id="nomstatut"></select></td>
				</tr>
			</table>
			<br> <input type="button" value="Enregistrer"
				onclick="valider(form1);" /> <input type="reset" value="Effacer" />

		</form>
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
		src="/valence/javascript/jquery.mask.min.js"></script>
	<script type="text/javascript"
		src="/valence/javascript/scripts/formatageMask.js"></script>
	<script type="text/javascript"
		src="/valence/javascript/scripts/instructions.js"></script>
</body>
</html>