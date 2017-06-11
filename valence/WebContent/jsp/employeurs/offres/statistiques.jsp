<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet" href="/valence/css/offres/statistiques.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />


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
			attentionComplet(" La date de début de recherche  doit être renseignée !!!!");
			return false;

		}
		
		if (datefinrech == "") {
			document.form1.datefinrech.focus();
			// Afficher un message d'erreur
			attentionComplet("La date de fin de recherche  doit être renseignée !!! !!!!");
			return false;

		}

		formulaire.submit();
		return true;
	}
</script>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>statistiques offres</title>
</head>


<body>
<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div id="creation"  > STATISTIQUES OFFRES ENREGISTREES </div>
		<br>
		
		<form method="post" name="form1" action="/valence/controleur">
		<span><input type="hidden" name="action" value="statistiquesoffresemployeurs" />
		<label class="col1">du :</label><input type="text" id="datedebutrech" name="datedebutrech" />
		<label class="col1"> au :</label><input type="text" id="datefinrech" name="datefinrech" />
		<input type="button" id="droite" class="boutonvert" value="Envoyer"
				onclick="valider(form1);" />
		</span>
		</form>
		<br>
					
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
		<script type="text/javascript" src="/valence/javascript/scripts/journouveaux.js"></script>
		
		
		
		
		</div>
		
	<!-- on rajoute la page affichestatistiques à la fin de celle ci-->	
	 <%@ include file='/jsp/employeurs/offres/affichestatistiques.jsp'%>
	 
	 
	
		
</body>
</html>