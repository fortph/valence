<%@page import="beans.parametres.employeur.*,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
String stat=request.getParameter("activite");
ActiviteDAO stadao=new ActiviteDAO();
List <Activite> liste=stadao.findAll();
Activite statut=null;
%>

<link rel="stylesheet" href="/valence/css/admin/statut.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>employeur activite</title>
<script type="text/javascript"
	src="/valence/javascript/scripts/attention.js"></script>

<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		
		
		var activite = document.getElementById("activite").value;
		
		if (activite == "") {
			document.form1.activite.focus();
			// Afficher un message d'erreur
			attentionComplet(" L'activité  doit être renseignée !!!!");
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
		<div  id="creation">AJOUTER UNE ACTIVITE</div>
		<br>
		<br>
		<table class="table2">
		<caption class="labelgras">LISTE EXISTANTE</caption>
				<%
		for(int i=0;i<liste.size();i++){ %>
		
		
		<tr><td><%=liste.get(i).getActivite() %></td></tr>
		<%
		}
		%>
		</table>
		
		<br>
		<hr>
		<br>
		<form method="get" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="adminactiviteemployeur" />
			
			<table class="table1">
			<tr>
			<td class="labelgras">NOM NOUVELLE ACTIVITE</td><td><input type="text" name="activite" id="activite" /></td>
			</tr>
			</table>
			<br>
		<input type="button" id="droite" value="Enregistrer"
				onclick="valider(form1);" />
			
		</form>
		
		<%
		if (stat!=null ){
			if( !stat.equals("0")){
		
			statut=stadao.findByID(Integer.parseInt(stat));
			out.println("<p>L'activité professionnelle <span id='gras'>"+statut.getActivite()+"</span> a été créé.</p>");
			
		}
		}
		
		%>
		
</div>
</body>
</html>