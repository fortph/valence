<%@ page
	import="dao.imp.identite.*, beans.identite.*,java.util.Date,java.text.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String id = request.getParameter("personne");

	Date jour = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	String crea = sdf.format(jour);
%>

<link rel="stylesheet" href="/valence/css/rmi/convoc.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />


<script type="text/javascript" src="/valence/dwr/engine.js"></script>
<script type="text/javascript" src="/valence/dwr/util.js"></script>


<script type="text/javascript"
	src="/valence/dwr/interface/Utilisateur.js"></script>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>creation fiche RMI</title>



<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>

<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier

		var utilisateurs = document.getElementById("utilisateurs").value;
		var datecreation=document.getElementById("datecreation").value;
		var heure=document.getElementById("heure").value;
	
		
		if (datecreation == "") {
			document.form1.datecreation.focus();
			// Afficher un message d'erreur
			attentionComplet(" Une date doit être saisie !!!!");
			return false;
		}
		
		if (heure == "") {
			document.form1.heure.focus();
			// Afficher un message d'erreur
			attentionComplet("Une heure doit être saisie !!!!");
			return false;
		}
		
		if(utilisateurs=="Aucun"){
			document.form1.utilisateurs.focus();
			attentionComplet(" Un utilisateur doit être sélectionné !!!!");
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
		<div id="creation">NOUVELLE CONVOCATION</div>
		<br>
		
		<form method="post" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="convocationrmi" /> <input
				type="hidden" name="personne" value="<%=id%>" />

<br>
			<table class="table1">
				<tr>
				<th>DATE CONVOCATION</th>
				<th>HEURE</th>
				<th>REFERENT</th>
				</tr>
				<tr>
					
					<td ><input type="text" name="datecreation"
						id="datecreation"  value="<%=crea%>" /></td>
				
					
					
					<td><input type="text" name="heure" id="heure" /></td>
					
					<td ><select name="utilisateurs" id="utilisateurs"
						onfocus="afficheCapUtilisateurs();"><option>Aucun</option></select></td>

				</tr>
			</table>
			

			<br> <input type="button" id="droite" value="Enregistrer"
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
			src="/valence/javascript/scripts/dwrfonctionsemployeurs.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/scripts/journouveaux.js"></script>
<script type="text/javascript"
			src="/valence/javascript/jquery.mask.min.js"></script>
			<script type="text/javascript"
			src="/valence/javascript/scripts/formatageMask.js"></script>

	</div>


</body>
</html>