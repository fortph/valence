<%@page import="dao.imp.identite.*,beans.identite.*,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/valence/css/formationpro/preinscription.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<%
String id =request.getParameter("ident");

%>


<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>
			
<script type="text/javascript" >
function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		var nom = document.getElementById("nom").value;
		
		if (nom=="") {
			document.form1.nom.focus();
			// Afficher un message d'erreur
			attentionComplet(" Veuillez entrer le nom de la formation!!!!");
			return false;

		}

		formulaire.submit();
		return true;
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>nouvelle formation</title>
</head>
<body>
<div id="body">
	<p><%@ include file='/menus/menugeneral/menu.html' %></p><br>
<div id="creation">CREER NOUVELLE FORMATION PROFESSIONNELLE</div>
<br>

<form method="post" name="form1" action="/valence/controleur">
			<input type="hidden" name="action"
				value="enregistrenouvelleformationpro" /> <input
				type="hidden" name="ident" value="<%=id%>" />
	<table id="table1"><tr>
	<td>Nom de la formation : </td>
	<td><input type="text" name="nom" id="nom" /></td>
	</tr>
	<tr></tr>
	</table>
	<br>
	<div id="fin"><input type="button"  class="boutonvert" value="Enregistrer" onclick="valider(form1);"/>
	<input type="reset" class="boutonrouge"  value="Effacer" />
	</div>
	
	
	
	</form>			
</div>				
</body>
</html>