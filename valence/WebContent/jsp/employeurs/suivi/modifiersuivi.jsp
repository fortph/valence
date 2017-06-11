<%@page import="dao.imp.employeur.*, beans.employeurs.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/valence/css/employeur/suivi.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>suivi employeur</title>

<script type="text/javascript" src="/valence/dwr/engine.js"></script>
<script type="text/javascript" src="/valence/dwr/util.js"></script>


<script type="text/javascript"
	src="/valence/dwr/interface/Utilisateur.js"></script>
	
	
	<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>
			
			
	
	
<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier

		var nom = document.getElementById("utilisateurs").value;

		if (nom == "") {
			document.form1.utilisateurs.focus();
			// Afficher un message d'erreur
			attentionComplet("Le nom de l'utilisateur doit être renseigné !!!!");
			return false;
		}

		
		formulaire.submit();
		return true;
	}
</script>


<%
	String id_emp = request.getParameter("employeur");
	
	String id_suiv = request.getParameter("suivi");
	SuiviDAO suidao = new SuiviDAO();
	Suivi suivi = suidao.findByID(Integer.valueOf(id_suiv));
%>
</head>
<body>

	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<h3 id="creation">MODIFICATION SUIVI EMPLOYEUR</h3>
		<br>
		<hr>
		<br>


		<form method="post" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="modifsuiviemployeur" /> 
			 <input	type="hidden" name="id_suivi" value="<%=id_suiv%>" />
			<center>
				<label for="dateinscription">Date de Création:</label> <input
					type="text" id="dateinscription" name="dateinscription"
					value="<%=suivi.getJour()%>" />
			</center>
			<br>
			<table class="table1">

				<tr>
					<td class="label">Identification :</td>
					<td><select name="utilisateurs" id="utilisateurs"
						onfocus="afficheCapUtilisateurs();">
							<option><%=suivi.getUtilisateur().getNom()%>
								<%=suivi.getUtilisateur().getPrenom()%>
							</option>
					</select></td>

				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>

				<tr>
					<td class="label">Commentaires:</td>
					<td><textarea name="commentaires" id="commentaires"><%=suivi.getCommentaires()%></textarea></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
			</table>
			<br>
			<div id="fin">
				<input type="button" value="Modifier" onclick="valider(form1);" />
				<input type="reset" value="Effacer" />
			</div>
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
			src="/valence/javascript/jquery.autosize.min.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/scripts/journouveaux.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/scripts/dwrfonctionsemployeurs.js"></script>
			
		<script type="text/javascript"
			src="/valence/javascript/scripts/hauteurtextarea.js"></script>

		<br>
	</div>
</body>
</html>