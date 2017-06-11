<%@ page
	import="java.util.*, java.text.*,beans.identite.*,dao.imp.identite.*,beans.parametres.accueil.*"%>
	
	
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>suivi formation</title>
<%


SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
String jour =sdf.format(new Date());

%>

<link rel="stylesheet" href="/valence/css/suivi/suiviformation.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />


<script type='text/javascript' src='/valence/dwr/engine.js'></script>
<script type='text/javascript' src='/valence/dwr/util.js'></script>
<script type="text/javascript"
	src="/valence/dwr/interface/AnimatriceDAO.js"></script>

<script type="text/javascript"
	src="/valence/javascript/scripts/attention.js"></script>


<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier

		var animatriceformation = document.getElementById("animatriceformation").value;
		var formation = document.getElementById("formation").value;
		var type = document.getElementById("type").value;
		
		

		if (animatriceformation == "Aucun" ||animatriceformation==0) {
			document.form1.animatriceformation.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le référent doit être renseigné !!!!");
			return false;

		}
				
		if (formation == ""|| formation==0) {
			document.form1.formation.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le nom de la formation doit être renseigné !!!!");
			return false;

		}
		
		if ( type==0) {
			document.form1.type.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le type de formation doit être renseigné !!!!");
			return false;

		}
		
		
		formulaire.submit();
		return true;
	}
	
	function vider(formulaire) {
		formulaire.reset();
	
	}
</script>

<%
String numero = request.getParameter("personne");
IdentiteDAO idao=new IdentiteDAO();
Identite ident=idao.findByID(Integer.parseInt(numero));
TypeFormationsDAO tdao=new TypeFormationsDAO();
List<String> listetypes=tdao.afficherListePropositionsFormations();


%>
</head>
<body>

	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div id="creation">NOUVEAU SUIVI FORMATION pour <%=ident.getPrenom_IDE() %> <%=ident.getNom_IDE() %></div>
		<br>
		<br>
		<h3>SUIVI FORMATION</h3>
		<br>
		<form method="post" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="suiviformation" /> <input
				type="hidden" name="personne" value="<%=numero%>" />

			<table id="table">
				<tr>
					<td class="un">Date de rédaction :</td>
					<td class="deux"><input class="taille" type="text" id="dateredaction"
						name="dateredaction" value="<%=jour %>" /></td>
					<td class="un">Nom du référent :</td>
					<td class="deux"><select class="taille" name="animatriceformation"
						id="animatriceformation" onfocus="recupererAnimatriceformation();">
							<option>Aucun</option>
					</select></td>
				</tr>
				
				<tr>
					<td>Formation :</td>
					<td class="deux" colspan="3"><input type="text" name="formation" id="formation" /></td>
				</tr>
				
				<tr>
					<td class="un">Date début formation :</td>
					<td class="deux"><input type="text"  id="datedebut" name="datedebut"></td>
					<td class="un">Date fin formation :</td>
					<td class="deux"><input type="text" id="datefin" name="datefin"></td>
				</tr>
				<tr>
					<td class="un">Type de formation :</td>
					<td class="deux"><select name="type" id="type">
					<option><% for(int i=0;i<listetypes.size();i++) 
						out.println("<option>"+listetypes.get(i)+"</option>");%></select></td>
					<td class="un">Organisme de formation :</td>
					<td class="deux"><input type="text" id="of" name="of"></td>
				</tr>
				
				<tr>
					<td></td>
				</tr>
			</table>
			<br>
			<p class="col1">COMMENTAIRES</p>
			<textarea class="col3" rows="5" name="commentaires">
</textarea>
			<br> <br> <p><input type="button" id="gauche"
				value="Vider" onclick="vider(form1);" /> <input type="button" id="droite"
				value="Enregistrer" onclick="valider(form1);" /></p>

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
		src="/valence/javascript/scripts/instructions.js"></script>

	<script type="text/javascript"
		src="/valence/javascript/scripts/journouveaux.js"></script>

</body>
</html>