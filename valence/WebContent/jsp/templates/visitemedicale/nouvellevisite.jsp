<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
String id=request.getParameter("numero");
%>
<link rel="stylesheet" href="/valence/css/ai/fiches/fichesuite.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Visite médicale</title>


<script type="text/javascript"
	src="/valence/javascript/scripts/attention.js"></script>

<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		
		var dateconvoc = document.getElementById("dateconvoc").value;
		var datevisite = document.getElementById("datevisite").value;
		
		if (dateconvoc == "" && datevisite=="" ) {
			document.form1.dateconvoc.focus();
			// Afficher un message d'erreur
			attentionComplet("nUne date doit être renseignée !!!!");
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
		<div id="creation">VISITE MEDICALE</div>
		<br>
		
		<form name="form1" method="post" action="/valence/controleur" >		
			<input type="hidden" name="action" value="<%=destination %>" />
			<input type="hidden" name="personne" value="<%=id %>" />
		<table class="table1" >
			<tr>
			
			<td>DATE CONVOCATION:</td><td><input  type="text"  id="dateconvoc" class="centre"name="dateconvoc" /></td>
			<td>DATE VISITE:</td><td><input  type="text"  id="datevisite" class="centre"name="datevisite" /></td>
			<!-- <td>DATE ECHEANCE:</td><td><input  type="text"  id="dateecheance" name="dateecheance" /></td>
			-->
			</tr>
			
			</table>
			<br>
			<input type="button" id="droite" value="Enregistrer"
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