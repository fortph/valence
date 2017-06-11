<%@page import="beans.parametres.employeur.*,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
String stat=request.getParameter("statut");
StatutDAO stadao=new StatutDAO();
List<Statut> liste=stadao.findAll();
Statut statut=null;
%>

<link rel="stylesheet" href="/valence/css/admin/statut.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>employeur statut</title>
<script type="text/javascript"
	src="/valence/javascript/scripts/attention.js"></script>
<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		
		
		var statut = document.getElementById("statut").value;
		
		if (statut == "") {
			document.form1.statut.focus();
			// Afficher un message d'erreur
			attentionComplet("Le statut doit être renseigné !!!!");
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
		<div  id="creation">AJOUTER UN STATUT</div>
		
		<br>
		<table class="table2">
		<caption class="labelgras">LISTE EXISTANTE</caption>
				<%
		for(int i=0;i<liste.size();i++){ %>
		
		
		<tr><td><%=liste.get(i).getStaturrs() %></td></tr>
		<%
		}
		%>
		</table>
		
		<br>
		<hr>
		<br>
		<form method="get" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="adminstatutemployeur" />
			
			<table class="table1">
			<tr>
			<td class="labelgras">NOM NOUVEAU STATUT</td><td><input type="text" name="statut" id="statut" /></td>
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
			out.println("<p>Le statut <span id='gras'>"+statut.getStaturrs()+"</span> a été créé.</p>");
			
		}
		}
		
		%>
		
</div>
</body>
</html>