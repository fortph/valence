<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>

<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseign�s ou dont le 
		//contenu est a verifier
		var debut = document.getElementById("datedebutrech").value;
		var employeur = document.getElementById("listeemppro").value;
		

		if (debut == "" || debut.length != 4 || isNaN(debut)) {
			document.form1.datedebutrech.focus();
			// Afficher un message d'erreur
			attentionComplet("L'ann�e doit �tre correctement renseign�e  !!!!");
			return false;
		}
		if (employeur == "") {
			document.form1.listeemppro.focus();
			// Afficher un message d'erreur
			attentionComplet(" L'employeur doit �tre renseign�  !!!!");
			return false;

		}

		formulaire.submit();
		return true;
	}
</script>
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<link rel="stylesheet" href="/valence/css/formationpro/affichagestat.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<script type="text/javascript" src="/valence/dwr/engine.js"></script>
<script type="text/javascript" src="/valence/dwr/util.js"></script>


<script type="text/javascript"
	src="/valence/dwr/interface/FormationProEmployeurDAO.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>liste stagiaires</title>
</head>
<body onload="recupererEmployeursFormPro();">
<div id="body">
	<p><%@ include file='/menus/menugeneral/menu.html' %></p>
<div id="creation">LISTE STAGIAIRES PAR EMPLOYEUR</div>
<br>
<form method="post" name="form1" action="/valence/controleur">
		<input type="hidden" name="action"
			value="afficherinscritsformationproEmploy" />
		<table>
		
		<tr>
				<td><label for="datedebutrech" class="nom">ANNEE DE
						RECHERCHE :</label></td>
				<td><input type="text" id="datedebutrech" class="largeur"
					value="2013" name="datedebutrech" /></td>
			</tr>
			<tr>
				<td><input type="radio" class="choix" name="annee"
					value="civil" /><label>Ann�e Civile</label></td>


			</tr>
			<tr>
				<td><input type="radio" checked="checked" class="choix"
					name="annee" value="exercice" /><label>Exercice "PYRAMIDE"</label></td>
					
			<tr>
				<td><label for="listeemppro">Employeur :</label></td>
				<td><select name="listeemppro" id="listeemppro" class="largeur" >
						<option></option>
				</select></td>
		
		
		
		
		
		</table>
		
		
		<br>

		<div id="fin">
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
		src="/valence/javascript/scripts/instructions.js"></script>
</div>
</body>
</html>