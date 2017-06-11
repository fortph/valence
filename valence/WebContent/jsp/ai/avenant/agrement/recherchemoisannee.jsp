<%@ page
	import="dao.imp.identite.*, beans.identite.*,dao.imp.ai.*,beans.ai.*,
	java.util.*,beans.smic.*,beans.employeurs.*,dao.imp.employeur.*,
	beans.parametres.accueil.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<link rel="stylesheet" href="/valence/css/ai/contrat/agrement.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Choix mois et annee</title>


<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>
			

<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		
		var datedebutrech = document.getElementById("datedebutrech").value;
		var datefinrech = document.getElementById("datefinrech").value;
		
		
		
		
		if (datedebutrech == "") {
			document.form1.datedebutrech.focus();
			// Afficher un message d'erreur
			attentionComplet(" La date de début doit être renseignée !!!!");
			return false;
		}
		
		if (datefinrech == "") {
			document.form1.datefinrech.focus();
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




	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div id="creation"> CHOIX DU MOIS ET DE L'ANNEE</div>
		<br>
		<br>
		<p class="centre3">Veuillez sélectionner les dates de début et de fin de recherche </p>
		<br>
		<form name="form1" method="post" action="/valence/controleur" >
		<input type="hidden" name="action" value="listeavenantpardates" />
		
		
			<table class="table5">
				
				<tr>
					<td> Du : <input type="text" name="datedebutrech" id="datedebutrech" />
					</td>
					
					<td> au : <input type="text" name="datefinrech" id="datefinrech" />
					
					</td>
					 </tr>
					
			</table>
			<br>
			 <input type="button" value="Envoyer"   id="droite" onclick="valider(form1);" />
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

	</div>

</body>
</html>