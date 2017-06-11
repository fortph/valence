<%@ page
	import="dao.imp.identite.*, beans.identite.*,dao.imp.ai.*,beans.ai.*,java.util.*,java.text.*"%>
	
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
String agre=request.getParameter("numagrement");
String numpers=request.getParameter("personne");
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
AgrementDAO agdao=new AgrementDAO();
Agrement agrement=agdao.findByID(Integer.parseInt(agre));

%>
<link rel="stylesheet" href="/valence/css/ai/fiches/fichesuite.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>modification agrement</title>

<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>
<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		
		var datedebutagrement = document.getElementById("datedebutagrement").value;
		
		if (datedebutagrement == "") {
			document.form1.datedebutagrement.focus();
			// Afficher un message d'erreur
			attentionComplet(" La date de début d'agrément doit être renseignée !!!!");
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
		<div id="creation">MODIFICATION AGREMENT POLE EMPLOI</div>
		<br>
		
		<form name="form1" method="post" action="/valence/controleur" >		
			<input type="hidden" name="action" value="aimodifieragrement" />
			<input type="hidden" name="numagrement" value="<%=agrement.getId_aifiche() %>" />
			<input type="hidden" name="idpers" value="<%=numpers %>" />
		<table class="table1" >
			<tr>
			<td class="grand" >Numéro :</td>
			<td><input type="text" id="agrement" name="agrement" value="<%=agrement.getNumagrement() %>" /></td>
			<td>DEBUT:</td><td><input  type="text"  id="datedebutagrement" name="datedebutagrement" value=<% if(agrement.getDatedeb()!=null) out.println(sdf.format(agrement.getDatedeb()));  %> /></td>
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