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
<script type='text/javascript'
	src='/valence/dwr/interface/Utilisateur.js'></script>
<script type='text/javascript' src='/valence/dwr/util.js'></script>


<script type="text/javascript"
	src="/valence/javascript/scripts/attention.js"></script>

<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier

		var utilisateurs = document.getElementById("utilisateurs").value;

		if (utilisateurs == "Aucun" ||utilisateurs==0) {
			document.form1.utilisateurs.focus();
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
<title>suivi</title>
</head>
<body>

	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div id="creation">NOUVEAU SUIVI pour <%=ident.getPrenom_IDE() %> <%=ident.getNom_IDE() %></div>
		<br>
		<hr>
		
		
		<h3>SUIVI</h3>
		<br>
		<form method="post" name="form1" action="/valence/controleur">
		<input type="hidden" name="action" value="suivisimplesalarie" />
		<input type="hidden" name="personne" value="<%=numero %>" />
			
			<div>
					<label class="col1">Date de rédaction :</label>
					<input class="taille" type="text" id="dateredaction" name="dateredaction" />
					<label>Nom du référent :</label>
					<select class="taille" name="utilisateurs" id="utilisateurs" onfocus="afficheCapUtilisateurs();">
					<option>Aucun</option>
					</select>
			</div>
			<br>
			<p class="col1">COMMENTAIRES</p>
<textarea class="col3" rows="5" name="commentaires" >
</textarea>
<br>
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

</body>
</html>