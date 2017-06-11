<%@page
	import="dao.imp.identite.*,dao.imp.formation.*,beans.identite.*,beans.formation.*,java.util.*,java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>modifier enregistrement</title>
<script type="text/javascript">
	function vider() {
		$("#datedebutform").empty();
		$("#datefinform").empty();
		$("#heuresform").empty();
		$("#etatform").empty();
		$("#niveauform").empty();
		$("#sessionform").empty();

	}

	function cacher() {
		var affiche = $("#nomformation").val();
		var exp = new RegExp("^AUCUNE.*");
		//alert("affiche ="+affiche);
		if (exp.test(affiche))
			$(".fond1").hide();
		else
			$(".fond1").show();
	}
</script>
<link rel="stylesheet" href="/valence/css/formation/preinscription.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />

<%
	String id = request.getParameter("numero");
	PreInscriptionDAO predao = new PreInscriptionDAO();
	PreInscription pre = predao.findByID(Integer.parseInt(id));
	IdentiteDAO iddao = new IdentiteDAO();
	Identite ident = iddao.findByID(pre.getIdentite().getId_IDE());
	String nom = ident.getNom_IDE() + " " + ident.getPrenom_IDE();
	int id_identite=ident.getId_IDE();
%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inscription formation</title>
<script type="text/javascript" src="/valence/dwr/engine.js"></script>
<script type="text/javascript" src="/valence/dwr/util.js"></script>

<script type="text/javascript"
	src="/valence/dwr/interface/AnimatriceDAO.js"></script>
<script type="text/javascript"
	src="/valence/dwr/interface/OrganismeFormationDAO.js"></script>
<script type="text/javascript"
	src="/valence/dwr/interface/ListeFormationsDAO.js"></script>
<script type="text/javascript"
	src="/valence/dwr/interface/PrescripteurDAO.js"></script>
</head>
<body
	onload="recupererlisteFormations(),recupererlisteOrganimeParFormation(),vider(),cacher(),recupererdatedebutFormation();">
	<div id="body">
	<p><%@ include file='/menus/menugeneral/menu.html' %></p><br>
		<form method="post" action="/valence/controleur">
			<input type="hidden" name="action"
				value="modifierenregistrementpersonne" />
				 <input type="hidden"
				name="numero" value="<%=id%>" />
				 <input type="hidden"
				name="identite" value="<%=id_identite%>" />
				
			<table>
				<tr>
					<td>Identification : <label id="nom"><%=nom%></label></td>
				</tr>

				<tr>
					<td><label for="tempsaccueilformation">Temps d'Accueil
							:</label></td>
					<td><input type="text" id="tempsaccueilformation"
						value=<%=pre.getTempsacc_pyramide()%> name="tempsaccueilformation" /></td>
				</tr>
				<tr>
					<td><label for="animatriceformation">Animatrice :</label></td>
					<td><select name="animatriceformation"
						onfocus="recupererAnimatriceformation();"
						id="animatriceformation">
							<option ><%=pre.getAnimatrice().getNom()%>
							</option>
					</select></td>
				</tr>
				<tr>
					<td><label for="prescripteurformation">Prescripteur :</label></td>
					<td><select name="prescripteurformation"
						onfocus="recupererPrescripteurformation();"
						id="prescripteurformation">
							<option ><%=pre.getPrescripteur()%></option>
					</select></td>
				</tr>
				<tr>
					<td><label for="nomformation">Formation :</label></td>
					<td><select name="nomformation" id="nomformation"
						onfocus="recupererlisteOrganimeParFormation(),vider(),cacher(),recupererdatedebutFormation();"
						onchange="recupererlisteOrganimeParFormation(),vider(),cacher(),recupererdatedebutFormation();">
							<option selected="selected"><%=pre.getListe().getFormation()%>(<%=pre.getListe().getId_pformation()%>)
							</option>
					
					</select></td>
				</tr>

				<tr class="fond1">

					<td><label for="nomorganismeformation">Organisme de
							formation :</label></td>
					<td><input type="text" disabled="disabled"
						id="nomorganismeformationconcerne" /></td>
				</tr>
			</table>
			<table>

				<tr class="fond1">
					<td>du :</td>
					<td><div id="datedebutform" class="fond"></div></td>
					<td>au :</td>
					<td><div id="datefinform" class="fond"></div></td>
				</tr>
				<tr class="fond1">
					<td>Heures de formation:</td>
					<td><div id="heuresform" class="fond"></div></td>
					<td>Etat de la formation :</td>
					<td><div id="etatform" class="fond"></div></td>
				</tr>
				<tr class="fond1">
					<td>Niveau:</td>
					<td><div id="niveauform" class="fond"></div></td>
					<td>Session :</td>
					<td><div id="sessionform" class="fond"></div></td>
				</tr>
				<tr>
					<td>Commentaires</td>
					<td><textarea name="explication" id="explication"
					><%=pre.getCommentaires() %>
					</textarea></td>
				</tr>
				<tr>
					<td><input type="submit" value="Enregistrer" /></td>
					
				</tr>
			</table>
		</form>
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
		src="/valence/javascript/scripts/journouveaux.js"></script>
	<script type="text/javascript"
		src="/valence/javascript/scripts/instructions.js"></script>
</body>
</html>
