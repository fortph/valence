<%@page
	import="dao.imp.formation.*,dao.imp.identite.*,beans.identite.*,beans.formation.*,java.util.*,java.sql.*"%>
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
	function vider() {
		$("#datedebutform").empty();
		$("#datefinform").empty();
		$("#heuresform").empty();
		$("#etatform").empty();
		$("#niveauform").empty();
		$("#sessionform").empty();
	}
	</script>
	<%
		String id = request.getParameter("numero");
		int num=Integer.parseInt(id);
		PreInscriptionDAO predao=new PreInscriptionDAO();
		PreInscription pre=predao.findByID(num);
		
		IdentiteDAO identitedao = new IdentiteDAO();
		Identite identite = identitedao.findByID(pre.getIdentite().getId_IDE());
		
		String nom = identite.getNom_IDE() + " " + identite.getPrenom_IDE();
		
		
				
		ListeFormationsDAO listedao=new ListeFormationsDAO();
		ListeFormations formation=listedao.findByID(pre.getListe().getId_pformation());
		String nomformation=formation.getFormation()+"("+formation.getId_pformation()+")";
	%>
	<link rel="stylesheet" href="/valence/css/formation/preinscription.css">
	<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />

	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>valider inscription</title>
	<script type="text/javascript" src="/valence/dwr/engine.js"></script>
	<script type="text/javascript" src="/valence/dwr/util.js"></script>
	<script type="text/javascript"
	src="/valence/dwr/interface/ListeFormationsDAO.js"></script>
	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body onload="recupererlisteOrganimeParFormation(),recupererdatedebutFormation();">
	<div id="body">
	<p><%@ include file='/menus/menugeneral/menu.html' %></p><br>
		<h3>
			Modification de l'inscription  pour
			<%=nom%></h3>
		<form method="post" action="/valence/controleur">
			<input type="hidden" name="action"
				value="modifierinscriptionformation" /> <input type="hidden"
				name="numero" value="<%=num%>" />
			<table>
				<tr>
					<td>Nom de la formation :</td>

					<td><input type="text"	name="nomformation" id="nomformation" onfocus="vider(),cacher(),recupererdatedebutFormation();"
					value="<%=nomformation  %>" disabled="disabled"  >
							</td>
				</tr>

				<tr class="fond1">

					<td><label for="nomorganismeformation">Organisme de
							formation :</label></td>
					<td><input type="text" disabled="disabled" name="nomorganismeformationconcerne"
						id="nomorganismeformationconcerne" /></td>
				</tr>
				<tr>
				<td>Date d'abandon de la formation:</td>
				<td><input type="text" id="dateabandonformation" name="dateabandonformation" /></td>
				</tr>
				<tr>
				<td>Raison de l'abandon:</td>
				<td><textarea name="raison" id="raison" ></textarea>
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