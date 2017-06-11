<%@page import="beans.parametres.employeur.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
String id=request.getParameter("id");
String nom=request.getParameter("nom");
String mail=request.getParameter("mail");
String modif=request.getParameter("modif");
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
		<div  id="creation">AJOUTER UN UTILISATEUR</div>
		<br>
		
		<form method="get" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="admincorfiajoututilisateur" />
			
			<table class="table1">
			<tr>
			<td class="label">NOM </td><td><input type="text" name="nom" value="<% if(nom!=null) out.println(nom);%>"id="nom" /></td>
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
			if( !id.equals("0") && modif==null){		
						out.println("<p class='ok'>L'utilisateur "+nom+"  a été créé.</p>");
			
		}
			else if( !id.equals("0") && modif.equals("1")){		
				out.println("<p class='ok'>L'utilisateur "+nom+"  a été modifié.</p>");
				
}
			else
				out.println("<p class='ok'>Le nom d'utilisateur "+nom+" existe déjà .<br> Veuillez modifier SVP...</p>");
		}
		
		%>
		
</div>
</body>
</html>