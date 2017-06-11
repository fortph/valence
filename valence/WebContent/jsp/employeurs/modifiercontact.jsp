<%@ page import="beans.employeurs.*,dao.imp.employeur.*,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/valence/css/employeur/affichage.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
	
<script type="text/javascript" src="/valence/dwr/engine.js"></script>
<script type="text/javascript" src="/valence/dwr/util.js"></script>


<script type="text/javascript" src="/valence/dwr/interface/Service.js"></script>
<%
	String cont = request.getParameter("contact");
	String emp = request.getParameter("emp");
	ContactDAO contdao = new ContactDAO();
	Contact contact = contdao.findByID(Integer.valueOf(cont));
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>modifier contact</title>


<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>



<script type="text/javascript" >
	//liste des champs qui doivent etre renseignés ou dont le 
	//contenu est a verifier

	function valider(formulaire) {
	var civilitecontact = document.getElementById("civilitecontact").value;
	var vcourriel = document.getElementById("mail").value;
	var nom = document.getElementById("nom").value;
	var prenom = document.getElementById("prenom").value;

	
	
	if (civilitecontact == "") {
		document.form1.civilitecontact.focus();
		// Afficher un message d'erreur
		attentionComplet(" La civilité du contact doit être renseignée !!!!");
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
		attentionComplet(" Le prénom du contact doit être renseigné !!!!");
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
		<div id="creation">MODIFIER UN CONTACT</div>
		<br>
		<hr>
		<br>

		<form method="post" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="modifiercontact" /> <input
				type="hidden" id="employeurs" name="employeurs" value="<%=emp%>" /> <input
				type="hidden" name="id_contact" value="<%=cont%>" />
			<table class="table3">
				<tr>
					<td class="label"></td>
					<td><select name="civilitecontact" id="civilitecontact"
						class="select1">
							<option><%=contact.getCiv_contact()%></option>
							<option>Mme</option>
							<option>Mlle</option>
							<option>Mr</option>
					</select></td>
					<td>Nom :</td>
					<td><input type="text" name="nom" id="nom"
						value="<%=contact.getNom_contact()%>" /></td>
					<td>&nbsp;Prénom :</td>
					<td><input type="text" name="prenom" id="prenom"
						value="<%=contact.getPrenom_contact()%>" /></td>
				</tr>
			</table>


			<table class="table2">
				<tr>
					<td class="label">Rang :</td>
					<td><input type="text" name="rang" id="rang"
						value="<%=contact.getRang_contact()%>" /></td>
				</tr>
				<tr>
					<td class="label">Service :</td>
					<td><select name="services" id="services"
						onfocus="recupererServices();">
							<option><%=contact.getService().getService()%>
					</select></td>
				</tr>
			</table>

			<br>

			<table class="table3">
				<tr>

					<td class="label">Téléphone 1:</td>
					<td><input type="text" name="fixe" id="fixe"
						value="<%=contact.getTel_contact()%>" />
					<td>&nbsp;Téléphone 2:</td>
					<td><input type="text" name="mobile" id="mobile"
						value="<%=contact.getPortable_contact()%>" /></td>
				</tr>
				<tr>

					<td class="label">Fax:</td>
					<td><input type="text" name="fax" id="fax"
						value="<%=contact.getFax_contact()%>" />
					<td>&nbsp;Mail:</td>
					<td><input type="text" name="mail" id="mail"
						value="<%=contact.getMail_contact()%>" /></td>
				</tr>
			</table>
			<br> <input type="button" value="Modifier"
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
		src="/valence/javascript/scripts/dwrfonctionsemployeurs.js"></script>
</body>
</html>