<%@page import="beans.smic.*,java.text.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
SmicDAO smicdao=new SmicDAO();
Smic ancien=smicdao.findByID(1);
%>

<link rel="stylesheet" href="/valence/css/admin/statut.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>grille smic</title>

<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		
		
		var smic = document.getElementById("smic").value;
		var vigueur = document.getElementById("vigueur").value;
		
		if (smic == "" || isNaN(smic)) {
			document.form1.smic.focus();
			// Afficher un message d'erreur
			alert(" ERREUR !\n\nLe nouveau montant du smic  doit être correctement renseigné !!!!");
			return false;
		}
		
		if (vigueur == "") {
			document.form1.vigueur.focus();
			// Afficher un message d'erreur
			alert(" ERREUR !\n\nLa date d'entrée en vigueur du nouveau smic doit être renseignée !!!!");
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
		<div  id="creation">MODIFIER SMIC</div>
		<br>
		<hr>
		<form method="get" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="adminsmic" />
			
			<table class="table1">
			<tr>
			<td class="un">ANCIEN SMIC</td><td class="blanc"><%=ancien.getAnciensmic() %></td></tr>
			<tr><td class="un">SMIC EN VIGUEUR</td><td class="blanc"><%=ancien.getTsxmic() %></td></tr>
			<tr><td class="un">NOUVEAU SMIC</td><td><input type="text" name="smic" id="smic"/></td>
			</tr>
			<tr><td class="un">Date d'entrée en vigueur</td><td><input type="text" name="vigueur" id="vigueur" value="<%=sdf.format(ancien.getDate_smic()) %>" /></td></tr>
			</table>
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
			src="/valence/javascript/scripts/journouveaux.js"></script>
		
</div>
</body>
</html>