<%@ page
	import="dao.imp.identite.*,beans.parametres.capemploi.*,beans.parametres.accueil.*,
	dao.imp.employeur.*, beans.identite.*,java.text.*,dao.imp.sap.*,beans.sap.*,java.util.*,beans.smic.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
String login=request.getParameter("login");
String passe=request.getParameter("passe");
UtilisateurDAO userdao = new UtilisateurDAO();
Utilisateur log=null;
if(login!=null)
log=userdao.rechercheParLogin(login);

%>
	<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>

<link rel="stylesheet" href="/valence/css/admin/passe.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>administration</title>
<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		
		
		var login = document.getElementById("login").value;
		var passe = document.getElementById("passe").value;
		
		if (login == "") {
			document.form1.login.focus();
			// Afficher un message d'erreur
			attentionComplet(" Veuillez saisir votre login SVP!!!!");
			return false;
		}

		/*if (passe == "") {
			document.form1.passe.focus();
			// Afficher un message d'erreur
			attentionComplet(" Veuillez saisir votre mot de passe !!!!");
			return false;
		}
*/

		formulaire.submit();
		return true;
	}
</script>

</head>
<body>
<%
if(login!=null && login.equals("erreur")){
%>
<script type="text/javascript">
alert("Ce login n'existe pas\nVeuillez le ressaisir  SVP...");
</script>
<%
}
%>

<%
if(passe!=null && passe.equals("erreur")){
%>
<script type="text/javascript">
alert("Mot de passe erronné\nVeuillez le ressaisir  SVP...");
</script>
<%
}
%>
	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div id="creation">ADMINISTRATION</div>
		<br>

		

		<form name="form1" method="get" action="/valence/controleur">
			<input type="hidden" name="action" value="administration" /> 

			<table class="table1">
				<tr>
					<td class="un">LOGIN :</td>
					<td><input type="text" name="login" id="login" autocomplete="off"
					value="<% if(log!=null  ) out.println(log.getLogin()); %>" /></td>
				</tr>
				<tr>
					<td class="un">MOT DE PASSE</td>
					<td><input type="password" name="passe" id="passe" autocomplete="off" /></td>
				</tr>
			
			</table>

			<br> <br> <input type="button" id="droite"
				value="Connexion" onclick="valider(form1);" />
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
		
		
		
		
		
	
		
	</div>

</body>
</html>