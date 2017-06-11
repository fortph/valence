<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/valence/css/formationpro/preinscription.css">


<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>
			
<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		var stat = document.getElementById("nomstatut").value;
		
		if (stat == "") {
			document.form1.nomstatut.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le statut du stagiaire doit être renseigné  !!!!");
			return false;
		}
		formulaire.submit();
		return true;
	}
	</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>statut</title>
<%
String id=request.getParameter("ident");

%>
</head>
<body>
<div id="body">
	<p><%@ include file='/menus/menugeneral/menu.html' %></p><br>
<h2> Création d'un nouveau Statut Stagiaire</h2>
<br>
<hr>
<form method="post" name="form1" action="/valence/controleur">
		<input type="hidden" name="action" value="formpronouveaustatut" /> 
		<input
				type="hidden" name="ident" value="<%=id%>" />
		<table>
		<tr>
		<td> Nom du Statut: </td>
<td><input type="text" name="nomstatut" id="nomstatut" /></td>
</tr>

</table>
<br>
<input type="button" value="Enregistrer" onclick="valider(form1);" />
<input type="reset" value="Effacer" />

</form>
</div>

</body>
</html>