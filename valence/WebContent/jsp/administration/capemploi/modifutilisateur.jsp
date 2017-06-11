<%@page import="beans.parametres.employeur.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
String id=request.getParameter("id");
String nom=request.getParameter("nom");
String prenom=request.getParameter("prenom");
String login=request.getParameter("login");
String privilege=request.getParameter("privilege");
String mail=request.getParameter("mail");
String modif=request.getParameter("modif");
%>

<link rel="stylesheet" href="/valence/css/admin/statut.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />


<script type='text/javascript' src='/valence/dwr/engine.js'></script>
<script type='text/javascript'
	src='/valence/dwr/interface/Utilisateur.js'></script>
<script type='text/javascript' src='/valence/dwr/util.js'></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>modifier utilisateur</title>
<script type="text/javascript"
	src="/valence/javascript/scripts/attention.js"></script>

<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		
		
		var nom = document.getElementById("utilisateurs").value;
		
		var login = document.getElementById("login").value;
		var privilege = document.getElementById("privilege").value;
		var mail = document.getElementById("mail").value;
		
		if (nom == "") {
			document.form1.nom.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le nom de l'utilisateur doit être renseigné !!!!");
			return false;
		}
				
		if (login == "") {
			document.form1.login.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le login de l'utilisateur doit être renseigné et doit être unique !!!!");
			return false;
		}
		
		if (privilege == "") {
			document.form1.privilege.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le privilège de l'utilisateur doit être renseigné !!!!");
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
<body onload="afficheCapUtilisateurs();">
<div id="body">
		<p><%@ include file='/menus/admin/menu.html'%></p>
		<br>
		<div  id="creation">MODIFIER UN UTILISATEUR</div>
		<br>
		<br>
		<form method="get" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="admincapmodifutilisateur" />
			<input type="hidden" name="ancienlogin"id="ancienlogin" >
			<table class="table1">
			<tr>
			<td class="label">NOM </td><td><select name="utilisateurs" id="utilisateurs" onchange="recupererinfoscaputilisateur();"></select></td>
			</tr>
			<tr>
			<td class="label">LOGIN</td><td><input type="text"  name="login" id="login" /></td>
			</tr>
			<tr>
			<td class="label">PRIVILEGE</td><td><select name="privilege" id="privilege" >
			<option></option>
			<option>admin</option>
			<option>admingen</option>
			</select></td>
			</tr>
			<tr><td>MAIL</td><td><input type="text" name="mail" id="mail" /></td>
			</tr>
			</table>
			<div id="act">
			<label id="plus">ACTIF :</label>
						<input type="radio" value="oui" id="oui" class="horstab"name="actif" > OUI
			<input type="radio" name="actif" class="horstab" id="non" value="non"> NON
			</div>
			<br>
		<input type="button" id="droite" value="Modifier"
				onclick="valider(form1);" />
			
		</form>
		
		<%
		if (id!=null ){
			if( !id.equals("0")&& modif==null){		
						out.println("<p class='ok'>L'utilisateur "+nom+" "+prenom+"   a été créé.</p>");
			
		}
			else if(!id.equals("0")&& modif.equals("1")){
				out.println("<p class='ok'>L'utilisateur "+nom+" "+prenom+"   a été modifié.</p>");
				
			}
			else
				out.println("<p class='ok'>Le login "+login+" existe déjà .<br> Veuillez modifier SVP...</p>");
		}
		
		%>
		
		
		
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
		src="/valence/javascript/scripts/dwrfonctionsemployeurs.js"></script>
		
</div>
</body>
</html>