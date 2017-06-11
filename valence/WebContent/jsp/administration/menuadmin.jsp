<%@ page
	import="dao.imp.identite.*,beans.parametres.accueil.*, beans.identite.*,
	java.text.*,dao.imp.employeur.*,beans.employeurs.*,java.util.*,beans.smic.*,dao.imp.sap.*, beans.sap.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>
<%
	String login = request.getParameter("login");
	String passe = request.getParameter("passe");
	String affiche = "";
	
	for (int i = 0; i < passe.length(); i++)
		affiche += "*";
	
	String modifpasse=request.getParameter("modifpasse");
	//String nouveau1=request.getParameter("nouveau1");
	//String nouveau2=request.getParameter("nouveau2");

	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	//String id = request.getParameter("personne");
%>
<link rel="stylesheet" href="/valence/css/admin/passe.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />


<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier

		var nouveau1 = document.getElementById("nouveau1").value;
		var nouveau2 = document.getElementById("nouveau2").value;

		if (nouveau1 == "") {
			document.form1.nouveau1.focus();
			// Afficher un message d'erreur
			attentionComplet(" Veuillez saisir votre nouveau mot de passe SVP!!!!");
			return false;
		}

		if (nouveau2 == "") {
			document.form1.nouveau2.focus();
			// Afficher un message d'erreur
			attentionComplet(" Veuillez confirmer votre mot de passe !!!!");
			return false;
		}

		formulaire.submit();
		return true;
	}
</script>




<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>administration base de données</title>



<script type="text/javascript">
	function cacher() {
		var coche = $("input[name='modifpasse']:checked").val();

		if (coche == "modifpasse") {
			$(".non").show();

		} else {
			$(".non").hide();
		}
	}
</script>


</head>
<body onload="cacher();">

	<div id="body">
		<p><%@ include file='/menus/admin/menu.html'%></p>
		<br>
		<div id="creation">MISE A JOUR BASE DE DONNEES</div>

		<%
			if(modifpasse!=null && modifpasse.equals("non")){
		%>

		<script type="text/javascript">
		alert("La saisie du mot de passe et sa confirmation sont différents\nVeuillez recommencer la saisie  SVP...");
		</script>
		<%
			}
			else if(modifpasse!=null && modifpasse.equals("ok"))
			{
		%>
		<script type="text/javascript">
		alert("Le mot de passe a été modifié...");
		</script>
		<%
			}
			
		%>

		<br> <input type="checkbox" name="modifpasse" id="modifpasse"
			value="modifpasse" onclick="cacher();" <% if(modifpasse!=null && modifpasse.equals("non")) out.println("checked='checked'");%> "> Modification du mot
		de passe <br> <br>
		<form name="form1" method="get" action="/valence/controleur">
			<input type="hidden" name="action" value="administrationmodifpasse" />
			<input type="hidden" name="nom" value="<%=login%>" /> <input
				type="hidden" name="passe" value="<%=passe%>" />


			<table class="table1">
				<tr>
					<td class="non">LOGIN :</td>
					<td class="non de "><%=login%></td>
				</tr>
				<tr>
					<td class="non">MOT DE PASSE</td>
					<td class="non de"><%=affiche%></td>
				</tr>

				<tr>
					<td class="non">Nouveau mot de passe</td>
					<td><input type="password" class="non de" name="nouveau1"
						id="nouveau1" autocomplete="off" /></td>
				</tr>
				<tr>
					<td class="non">Confirmez le mot de passe</td>
					<td><input type="password" class="non de " name="nouveau2"
						id="nouveau2" autocomplete="off" /></td>
				</tr>

			</table>

			<br> <br> <input type="button" id="droite" class="non"
				value="Modifier" onclick="valider(form1);" />
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