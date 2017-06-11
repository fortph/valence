<%@ page
	import="dao.imp.identite.*, beans.identite.*,dao.imp.ai.*,beans.ai.*,java.util.*,java.text.*"%>
	
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
String id=request.getParameter("idextranet");
String numpers=request.getParameter("personne");
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
ExtranetDAO agdao=new ExtranetDAO();
Extranet extra=agdao.findByID(Integer.parseInt(id));

%>
<link rel="stylesheet" href="/valence/css/ai/fiches/fichesuite.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>modification extranet</title>

<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>
			
<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		
		var datedebut = document.getElementById("datedebut").value;
		
		if (datedebut == "") {
			document.form1.datedebut.focus();
			// Afficher un message d'erreur
			attentionComplet(" La date de début doit être renseignée !!!!");
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
		<div id="creation">MODIFICATION EXTRANET</div>
		<br>
		
		<form name="form1" method="post" action="/valence/controleur" >		
			<input type="hidden" name="action" value="aimodifierextranet" />
			<input type="hidden" name="idextranet" value="<%=extra.getId_extranet() %>" />
			<input type="hidden" name="idpers" value="<%=numpers %>" />
		<table class="table1" >
			<tr>
			<td>DEBUT:</td><td><input class="centre" type="text"  id="datedebut" name="datedebut" value=<% if(extra.getDebut()!=null) out.println(sdf.format(extra.getDebut())); %> /></td>
			<td>FIN :</td><td><input  type="text" class="centre"  id="datefin" name="datefin" value="<% if(extra.getFin()!=null) out.println(sdf.format(extra.getFin())); else out.println("");%>" /></td>
			</tr>
			<% System.out.println(extra.getFin()); %>
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