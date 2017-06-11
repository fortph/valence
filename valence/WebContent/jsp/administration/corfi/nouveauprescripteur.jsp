<%@page import="beans.parametres.employeur.*,dao.imp.formation.*,beans.formation.*,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%
String id=request.getParameter("id");
String nom=request.getParameter("nom");
PrescripteurDAO predao=new PrescripteurDAO();
List<Prescripteur> liste=predao.findAll();

%>

<link rel="stylesheet" href="/valence/css/admin/statut.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>nouveau prescripteur</title>
<script type="text/javascript"
	src="/valence/javascript/scripts/attention.js"></script>

<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		
		
		var nom = document.getElementById("nom").value;
		
		
		if (nom == "") {
			document.form1.nom.focus();
			// Afficher un message d'erreur
			attentionComplet(" Un nom de precripteur doit être renseigné et doit être unique !!!!");
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
		<div  id="creation">AJOUTER UN PRESCRIPTEUR</div>
		<br>
		<br>
		<table class="table2">
		<caption class="labelgras">LISTE EXISTANTE</caption>
				<%
		for(int i=0;i<liste.size();i++){ %>
		
		
		<tr><td><%=liste.get(i).getPrescripteur() %></td></tr>
		<%
		}
		%>
		
		
		</table>
		<br>
		<br>
		
		<form method="get" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="admincorfiajoutprecripteur" />
			
			<table class="table1">
			<tr>
			<td class="labelgras">NOM PRESCRIPTEUR </td><td><input type="text" name="nom" id="nom" /></td>
			</tr>
			
			</table>
			<br>
		<input type="button" id="droite" value="Enregistrer"
				onclick="valider(form1);" />
			
		</form>
		
		<%
		if (id!=null ){
			if( !id.equals("0")){		
						out.println("<p class='ok'>Le prescripteur "+nom+"  a été créé.</p>");
			
		}
			else
				out.println("<p class='ok'>Le prescripteur "+nom+" existe déjà .<br> Veuillez modifier SVP...</p>");
		}
		
		%>
		
</div>
</body>
</html>