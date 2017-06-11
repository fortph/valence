<%@page
	import="dao.imp.identite.*, beans.identite.*, java.util.*"%>
	
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet" href="/valence/css/suivi/suivi.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />


<script type='text/javascript' src='/valence/dwr/engine.js'></script>
<script type="text/javascript"
	src="/valence/dwr/interface/AnimatriceDAO.js"></script>
<script type='text/javascript' src='/valence/dwr/util.js'></script>


<script type="text/javascript"
	src="/valence/javascript/scripts/attention.js"></script>

<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier

		var utilisateurs = document.getElementById("animatriceformation").value;

		if (utilisateurs == "Aucun" ||utilisateurs==0) {
			document.form1.animatriceformation.focus();
			// Afficher un message d'erreur
			attentionComplet("Le référent doit être renseigné !!!!");
			return false;

		}

		
		formulaire.submit();
		return true;
	}
</script>

<%
String numero=request.getParameter("personne");
IdentiteDAO idao=new IdentiteDAO();
Identite ident=idao.findByID(Integer.parseInt(numero));

%>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>accompagnement</title>
</head>
<body>

	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div id="creation">NOUVEL ACCOMPAGNEMENT FORMATION pour <%=ident.getPrenom_IDE() %> <%=ident.getNom_IDE() %></div>
		<br>
		<br/>
		<form method="post" name="form1" action="/valence/controleur">
		<!-- <input type="hidden" name="action" value="suivisimplesalarie" />-->
		<input type="hidden" name="action" value="suiviaccompform" />
		<input type="hidden" name="personne" value="<%=numero %>" />
			
			<div>
					<label class="col1">Date de rédaction :</label>
					<input class="taille" type="text" id="dateredaction" name="dateredaction" />
					<label>Nom du référent :</label>
					<select class="taille" name="animatriceformation" id="animatriceformation" onfocus="recupererAnimatriceformation();">
					<option>Aucun</option>
					</select>
			</div>
			<br/>
			<br/>
			<hr>
			<br/>
			<h3>FORMATION DEMANDEE</h3>
			<br/>
			
			
<textarea class="col3" rows="5" name="demande" >
</textarea>
<br/>
<br/>
<hr>
<br/>
<h3>PROPOSITIONS</h3>
<br/>
<table class="table1">
			<tr><td class="neuf"><input type="checkbox" name="choixform" value="Pyramide"></td><td class="dix">Pyramide</td>
			<td class="onze" ><input type="text" name="choixform1" /></td></tr>
			<tr><td class="neuf"><input  type="checkbox" name="choixform" value="Formation Pro"></td><td class="dix"> Formation Pro</td>
			<td><input  type="text" name="choixform2" /></td></tr>
			<tr><td class="neuf"><input  type="checkbox" name="choixform" value="Cyber-Base"></td><td class="dix"> Cyber-Base</td>
			<td><input class="onze"  type="text" name="choixform3" /></td></tr>
			<tr><td class="neuf"><input  type="checkbox" name="choixform" value="Vodéclic"></td><td class="dix"> Vodélic</td>
			<td><input class="onze"  type="text" name="choixform4" /></td></tr>
			<tr><td class="neuf"><input   type="checkbox" name="choixform" value="Autres"></td><td class="dix"> Autres</td>
			<td><input class="onze"  type="text" name="choixform5" /></td></tr>
</table>
<br>
<h4>Commentaires</h4>
<textarea class="col3" rows="5" name="commentaires1" ></textarea>
<br/>
			<br> <input type="button" id="droite" value="Enregistrer"
				onclick="valider(form1);" />
				
		</form>

		<br>
	</div>

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
		
	
		<script type="text/javascript" src="/valence/javascript/scripts/journouveaux.js"></script>
		<script type="text/javascript"
		src="/valence/javascript/scripts/instructions.js"></script>
</body>
</html>