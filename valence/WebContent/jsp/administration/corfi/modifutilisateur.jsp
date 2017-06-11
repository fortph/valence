<%@page import="beans.parametres.employeur.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%

String nom=request.getParameter("nom");
String mail=request.getParameter("mail");
String modif=request.getParameter("modif");

%>

<link rel="stylesheet" href="/valence/css/admin/corfi.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>modifier utilisateur</title>
<script type="text/javascript" src="/valence/dwr/engine.js"></script>
<script type="text/javascript" src="/valence/dwr/util.js"></script>

<script type="text/javascript"
	src="/valence/dwr/interface/AnimatriceDAO.js"></script>
<script type="text/javascript"
	src="/valence/javascript/scripts/attention.js"></script>

<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		
		
		var nom = document.getElementById("animatriceformation").value;
		var mail = document.getElementById("mail").value;
		
		if (nom == "") {
			document.form1.nom.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le nom de l'utilisateur doit être renseigné et doit être unique !!!!");
			return false;
		}
		//test adresse mail 
		var expr = new RegExp("^[a-z0-9._-]+@[a-z0-9.-]{2,}[.][a-z]{2,4}$");
		if (mail != "" && !expr.test(mail)) { // Placer le focus dans la zone courriel
			document.form1.mail.focus();
			// Afficher un message d'erreur
			attentionComplet("  Le courriel doit avoir un format correct : xxxxx@xxxxx.xx  !!!!");
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
		<p><%@ include file='/menus/admin/menu.html'%></p>
		<br>
		<div  id="creation">MODIFIER UN UTILISATEUR</div>
		<br>
		
		<form method="get" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="admincorfimodifutilisateur" />
			
			<table class="table1">
			<tr>
			<td class="label">NOM </td>
			<td><select name="animatriceformation" id="animatriceformation" onfocus="recupererToutesAnimatriceformation();" onchange="recupererinfosutilisateur();" ><option><% if(nom!=null) out.println(nom); %></option></select></td>
			<td>&nbsp;</td></tr>
			<tr><td>MAIL</td><td><input type="text" name="mail" id="mail" /></td>
			<td>&nbsp;</td>
			</tr>
			</table>
			<br>
			<div id="act">
			<label id="plus">ACTIF :</label>
						<input type="radio" id="oui" class="horstab"name="actif" value="oui"> OUI
			<input type="radio" name="actif" class="horstab" id="non" value="non"> NON
			</div>
			<br>
		<input type="button" id="droite" value="Modifier"
				onclick="valider(form1);" />
			
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
		<br>
		
</div>
</body>
</html>