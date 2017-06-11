<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/valence/css/formation/preinscription.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Nouvelle formation</title>

<%
String num=request.getParameter("numero");
%>
<script type="text/javascript" src="/valence/dwr/engine.js"></script>
<script type="text/javascript" src="/valence/dwr/util.js"></script>

<script type="text/javascript"
	src="/valence/dwr/interface/OrganismeFormationDAO.js">
	</script>
	<script type="text/javascript"
	src="/valence/dwr/interface/AnimatriceDAO.js">
	</script>
	<script type="text/javascript"
	src="/valence/dwr/interface/NiveauQualificationFormationDAO.js">
	</script>
	<script type="text/javascript"
	src="/valence/dwr/interface/PrescripteurDAO.js">
	</script>
	<script type="text/javascript"
	src="/valence/dwr/interface/ThemeFormationDAO.js">
	</script>
	
	
	<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>
			
	
	
	<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		var heure = document.getElementById("heureformation").value;
		var debut = document.getElementById("datedebutformation").value;
		var fin = document.getElementById("datefinformation").value;
		var theme=document.getElementById("nomtheme").value;
		if (debut == "") {
			document.form3.datedebutformation.focus();
			// Afficher un message d'erreur
			attentionComplet(" La date doit être renseignée  !!!!");
			return false;
		}
		if (fin == "") {
			document.form3.datefinformation.focus();
			// Afficher un message d'erreur
			attentionComplet("La date doit être renseignée  !!!!");
			return false;
		}
		
		

		if (isNaN(heure)==true || heure=="") {
			document.form3.heureformation.focus();
			attentionComplet("La saisie des heures est incorrecte !!!!");
			return false;

		}
		 if(theme==""){
			 document.form3.nomtheme.focus();
			 attentionComplet(" Le thème de la formation doit être saisi !!!!");
				return false;
		 }

		formulaire.submit();
		return true;
	}
</script>
</head>
<body onload="recupererOrganisme();recupererAnimatriceformation();recupererNiveauQualif();">
<div id="body">
<%
if(num==null){
%>
<p><%@ include file='/menus/menugeneral/menu.html' %></p><br>
<%
}
else 
{
%>	<p><%@ include file='/menus/menupyramide/menupyramidemini.jsp' %></p><br>
	<%
	}
	%>	
		<div id="creation"> CREATION NOUVELLE FORMATION</div>
		<br>
<hr>
<br>
<form method="post" name="form3" action="/valence/controleur" >
<input type="hidden" name="action" value="creerformation" /> 
<input type="hidden" name="numero" value="<%=num %>" /> 
		<table>
		<tr><td><label for="intitule" >Intitulé de la Formation</label></td>
		<td><input type="text" name="intitule" id="intitule" /></td>
		</tr>
		<tr><td><label for=nomorganismeformation>Organisme de formation</label></td>
		<td><select name="nomorganismeformation" id="nomorganismeformation" ></select></td>
		<td><input type="button" class="boutonvert" onclick="self.location.href='creerorganismeformation.jsp'" value="créer nouvel organisme" /></td>
		</tr>
		<tr><td><label for="niveauqualification" >Niveau :</label></td>
		<td><select name="niveauqualification" id="niveauqualification" >
		<option selected="selected" value="NULL">NULL</option>
		</select></td>
		</tr>
		<tr><td><label for="datedebutformation" >Date Début</label></td>
		<td><input type="text" name="datedebutformation" id="datedebutformation" /></td>
		</tr>
		<tr><td><label for="datefinformation" >Date Fin</label></td>
		<td><input type="text" name="datefinformation" id="datefinformation" /></td>
		</tr>
		<tr><td><label for="animatriceformation" >Animatrice :</label></td>
		<td><select name="animatriceformation" id="animatriceformation" ></select></td>
		</tr>
		<tr><td><label for="heureformation" >Nombre d'Heures : </label></td>
		<td><input type="text" id="heureformation"  name="heureformation" /></td>
		</tr>
		<tr><td><label for="nomtheme" >Thème : </label></td>
		<td><select name="nomtheme" id="nomtheme" onfocus="recupererthemeFormations();"></select></td>
		</tr>
		
		</table>
		<br>
		<div id="fin">
				<input type="button" value="Ajouter" class="boutonvert" onclick="valider(form3);" /><input
					type="reset" class="boutonrouge" value="Effacer" />
			</div>


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
	<script type="text/javascript" src="/valence/javascript/scripts/journouveaux.js"></script>
	<script type="text/javascript"
		src="/valence/javascript/scripts/instructions.js"></script>
	</div>
</body>
</html>