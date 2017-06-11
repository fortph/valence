<%@page
	import="dao.imp.formation.*,dao.imp.identite.*,beans.identite.*,beans.formation.*,java.util.*,java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <link rel="stylesheet"
	href="/valence/css/formation/listeparformation.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<script type="text/javascript" src="/valence/dwr/engine.js"></script>
<script type="text/javascript" src="/valence/dwr/util.js"></script>

<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables_themeroller.css">
<link rel="stylesheet" href="/valence/javascript/css/TableTools.css">

<script type="text/javascript"
	src="/valence/dwr/interface/OrganismeFormationDAO.js"></script>
<script type="text/javascript"
	src="/valence/dwr/interface/ListeFormationsDAO.js"></script>

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
<%
String num=request.getParameter("numeroformation");
PreInscriptionDAO predao=new PreInscriptionDAO();
ListeFormationsDAO listeda=new ListeFormationsDAO(); 

if(num==null){

%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%=etat1 %> par formation</title>

</head>
<body onload="recupererlisteFormations();">



	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html' %></p><br>
			<div id="creation">LISTE DES
			<%=etat1 %> PAR FORMATION</div>
		<br>
		<form method="post" action="/valence/controleur">
			<input type="hidden" name="action" value="<%=monchoix %>" />
			<table class="une">

				<tr>
					<td><label for="nomformation"> Sélectionner la
							formation :</label></td>
					<td><select name="nomformation" id="nomformation"
						onchange="recupererlisteOrganimeParFormation(),vider(),cacher(),recupererdatedebutFormation();">
							<option></option>
					</select></td>
				</tr>

				<tr>

					<td><label for="nomorganismeformation">Organisme de
							formation :</label></td>
					<td><input type="text" disabled="disabled"
						id="nomorganismeformationconcerne" /></td>
				</tr>
			</table>
			<br>
			<table class="fond3">

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
			</table>

			<div id="fin">
				<input type="submit" class="boutonvert" value="Afficher" />

			</div>
		</form>
	</div>
	<br>
</body>
<%
		}
		else {
	%>
<div id="body">
	