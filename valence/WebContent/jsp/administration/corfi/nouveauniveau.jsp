<%@page import="beans.parametres.employeur.*,java.util.*,dao.imp.formation.*,beans.formation.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%
String id=request.getParameter("id");
String nom=request.getParameter("nom");
NiveauQualificationFormationDAO nivdao=new NiveauQualificationFormationDAO();
List<NiveauQualificationFormation> liste=nivdao.findAll();

%>

<link rel="stylesheet" href="/valence/css/admin/statut.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>nouveau niveau</title>
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
			attentionComplet(" Un niveau de formation doit être renseigné et doit être unique !!!!");
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
		<div  id="creation">AJOUTER UN NIVEAU DE QUALIFICATION</div>
		<br>
		<table class="table2">
		<caption class="labelgras">LISTE EXISTANTE</caption>
				<%
		for(int i=0;i<liste.size();i++){ %>
		
		
		<tr><td><%=liste.get(i).getLibelle_niv() %></td></tr>
		<%
		}
		%>
		</table>
		
		<br>
		<hr>
		<br>
		<form method="get" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="admincorfiajoutniveau" />
			
			<table class="table1">
			<tr>
			<td class="labelgras">NIVEAU A AJOUTER</td><td><input type="text" name="nom" id="nom" /></td>
			</tr>
			
			</table>
			<br>
		<input type="button" id="droite" value="Enregistrer"
				onclick="valider(form1);" />
			
		</form>
		
		<%
		if (id!=null ){
			if( !id.equals("0")){		
						out.println("<p class='ok'>Le niveau "+nom+"  a été créé.</p>");
			
		}
			else
				out.println("<p class='ok'>Le niveau "+nom+" existe déjà .<br> Veuillez modifier SVP...</p>");
		}
		
		%>
		
</div>
</body>
</html>