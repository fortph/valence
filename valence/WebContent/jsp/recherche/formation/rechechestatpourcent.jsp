<%@page import="divers.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/valence/css/recherche/rechercheidentite.css">

<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>

<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		var debut = document.getElementById("datedebutrech").value;
		//var fin = document.getElementById("datefinrech").value;
		if (debut == "" ||debut.length!=4 || isNaN(debut)) {
			document.form1.datedebutrech.focus();
			// Afficher un message d'erreur
			attentionComplet("L'année doit être correctement renseignée ('2010')  !!!!");
			return false;
		}
	
		formulaire.submit();
		return true;
	}
</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Statistiques formation</title>

<%
String jour=new FormaterDate().getSortie();
String an=jour.substring(0,4);
%>
</head>
<body>
	<div id="body">
	<p><%@ include file='/menus/menugeneral/menu.html' %></p>
		<div id="creation">STATISTIQUES PAR EXERCICE</div>
		
		<br />
		<form  method="post"  name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="statformationpourcent" />
			<table>
				<tr>
					<td><label for="datedebutrech">Année de Début
							de recherche :</label></td>
					<td><input type="text" id="datedebutrech"  value="<%=an%>" class="largeur"
						name="datedebutrech" autocomplete="off"  /></td>
				</tr>
				
				
			</table>
			<br>
			<div id="fin">
				<input type="button" value="Rechercher" class="boutonvert" onclick="valider(form1);" /><input
					type="reset" class="boutonrouge" value="Effacer" />
			</div>
		</form>

	</div>




	
</body>
</html>