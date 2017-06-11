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
%>

<link rel="stylesheet" href="/valence/css/admin/statut.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>creer utilisateur</title>
<script type="text/javascript"
	src="/valence/javascript/scripts/attention.js"></script>

<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		
		
		var nom = document.getElementById("nom").value;
		var prenom = document.getElementById("prenom").value;
		var login = document.getElementById("login").value;
		var privilege = document.getElementById("privilege").value;
		var mail = document.getElementById("mail").value;
		
		if (nom == "") {
			document.form1.nom.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le nom de l'utilisateur doit être renseigné !!!!");
			return false;
		}
		if (prenom == "") {
			document.form1.prenom.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le prenom de l'utilisateur doit être renseigné !!!!");
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
<body>
<div id="body">
		<p><%@ include file='/menus/admin/menu.html'%></p>
		<br>
		<div  id="creation">AJOUTER UN UTILISATEUR</div>
		<br>
		<br>
		<form method="get" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="admincapajoututilisateur" />
			
			<table class="table1">
			<tr>
			<td class="label">NOM </td><td><input type="text" name="nom" value="<% if(nom!=null) out.println(nom);%>"id="nom" /></td>
			</tr>
			<tr>
			<td class="label">PRENOM </td><td><input type="text" name="prenom" value="<% if(prenom!=null) out.println(prenom);%>"id="prenom" /></td>
			</tr>
			<tr>
			<td class="label">LOGIN</td><td><input type="text" name="login" value="<% if(login!=null) out.println(login);%>"id="login" /></td>
			</tr>
			<tr>
			<td class="label">PRIVILEGE</td><td><select name="privilege" id="privilege" >
			<option><% if(privilege!=null) out.println(privilege); %></option>
			<option>admin</option>
			<option>admingen</option>
			</select></td>
			</tr>
			<tr><td>MAIL</td><td><input type="text" name="mail" value="<% if (mail!=null) out.println(mail);%>"id="mail" /></td>
			</tr>
			</table>
			<br>
		<input type="button" id="droite" value="Enregistrer"
				onclick="valider(form1);" />
			
		</form>
		
		<%
		if (id!=null ){
			if( !id.equals("0")){		
						out.println("<p class='ok'>L'utilisateur "+nom+" "+prenom+"   a été créé.</p>");
			
		}
			else
				out.println("<p class='ok'>Le login "+login+" existe déjà .<br> Veuillez modifier SVP...</p>");
		}
		
		%>
		
</div>
</body>
</html>