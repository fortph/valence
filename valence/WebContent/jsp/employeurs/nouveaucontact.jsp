<%@ page import="beans.employeurs.*,dao.imp.employeur.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/valence/css/employeur/affichage.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>contact</title>
<%
String id_emp=request.getParameter("employeur");
EmployeurDAO empdao=new EmployeurDAO();
Employeur employeur=empdao.findByID(Integer.valueOf(id_emp));


%>
<script type="text/javascript" src="/valence/dwr/engine.js"></script>
<script type="text/javascript" src="/valence/dwr/util.js"></script>
<script type="text/javascript" src="/valence/dwr/interface/Service.js"></script>


<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>
			

<script type="text/javascript" >
var emploi=window.location.href.split("=")[1];
var valeur=new String(emploi);
function valider(formulaire) {

	//liste des champs qui doivent etre renseignés ou dont le 
	//contenu est a verifier

	
	var civilitecontact = document.getElementById("civilitecontact").value;
	var vcourriel = document.getElementById("mail").value;
	var nom = document.getElementById("nom").value;
	var prenom = document.getElementById("prenom").value;
	
	
	if (civilitecontact == "") {
		document.form1.civilitecontact.focus();
		// Afficher un message d'erreur
		attentionComplet("La civilité du contact doit être renseignée !!!!");
		return false;

	}
	
	
	if (nom == "") {
		document.form1.nom.focus();
		// Afficher un message d'erreur
		attentionComplet(" Le nom du contact doit être renseigné !!!!");
		return false;
	}

	if (prenom == "") {
		document.form1.prenom.focus();
		// Afficher un message d'erreur
		attentionComplet("Le prénom du contact doit être renseigné !!!!");
		return false;
	}
	//test adresse mail 
	var expr = new RegExp("^[a-z0-9._-]+@[a-z0-9.-]{2,}[.][a-z]{2,4}$");
	if (vcourriel != "" & !expr.test(vcourriel)) { // Placer le focus dans la zone courriel
		document.form1.mail.focus();
		// Afficher un message d'erreur
		attentionComplet(" Le courriel doit avoir un format correct : xxxxx@xxxxx.xx  !!!!");
		// Fin de la fonction -> Pas de submit
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
		<div id="creation">AJOUTER UN CONTACT</div>
		<br>
		<hr>
		<h3>Création d'un nouveau contact pour <%=employeur.getRs_employeur() %> </h3>
		<br>
		
		<form method="post" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="nouveaucontact" />
			<input type="hidden" name="employeurs" id="employeurs" value="<%=id_emp %>" />
		<table class="table3">
			<tr>
				<td class="label"></td>
				<td><select name="civilitecontact" id="civilitecontact"
						class="select1">
							<option></option>
							<option>Mme</option>
							<option>Mlle</option>
							<option>Mr</option>
					</select></td>
					<td>Nom :</td><td><input type="text" name="nom" id="nom" /></td>
					<td>&nbsp;Prénom :</td>
					<td><input type="text" name="prenom" id="prenom" /></td>
					</tr>
				</table>
				
				
		<table class="table2">
			<tr>
				<td class="label">Rang :</td>
				<td><input type="text" name="rang" id="rang" /></td>
				</tr>
				<tr>
				<td class="label">Service :</td>
				<td><select name="services" id="services" 
				onfocus="recupererServices();" ></select></td>
				</tr>
				</table>
				
				<br>
				
		<table class="table3">
			<tr>
			
				<td class="label">Téléphone 1:</td>
				<td><input type="text" name="fixe" id="fixe" />
				<td>&nbsp;Téléphone 2:</td>
				<td><input type="text" name="mobile" id="mobile" /></td>
				</tr>
				<tr>
				
				<td class="label">Fax:</td>
				<td><input type="text" name="fax" id="fax" />
				<td>&nbsp;Mail:</td>
				<td><input type="text" name="mail" id="mail" /></td>
				</tr>
				</table>
				<br>
				
				
				<input type="button" value="Ajouter" onclick="valider(form1);" />
				<input type="reset" value="Effacer" />
				
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
			src="/valence/javascript/scripts/dwrfonctionsemployeurs.js"></script>		
</body>
</html>