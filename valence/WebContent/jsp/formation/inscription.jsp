<%@page
	import="dao.imp.identite.*,dao.imp.formation.*,beans.formation.*,beans.identite.*,java.util.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" %>
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
	String id = request.getParameter("numero");
	int num = Integer.parseInt(id);
	IdentiteDAO identitedao = new IdentiteDAO();
	Identite identite = identitedao.findByID(num);
	String nom = identite.getNom_IDE() + " " + identite.getPrenom_IDE();
	PreInscriptionDAO predao=new PreInscriptionDAO();
	PreInscription pre=new PreInscription();
	pre.setIdentite(identite);
	pre.setEnregistre(true);
	pre.setInscrit(false);
	List<PreInscription>liste =predao.findByCriteria(pre);
	
%>
<link rel="stylesheet" href="/valence/css/formation/preinscription.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>valider inscription</title>
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


<body onload="recupererlisteOrganimeParFormation(),recupererdatedebutFormation();">
	<div id="body">
	<p><%@ include file='/menus/menupyramide/menupyramidemini.jsp' %></p><br>
	
	<div id="creation"> INSCRIPTION FORMATION PYRAMIDE </div>
	<br>
	<%
	if(liste.size()>0){
	%>
		<h4>
			Inscription formation de
			<span id="nom"><%=nom%></span></h4>
			<br>
		<form method="post" action="/valence/controleur">
			<input type="hidden" name="action"
				value="validerinscriptionformation" /> <input type="hidden"
				name="numero" value="<%=num%>" />
			<table>
				<tr>
					<td><label for="nomformation">Nom de la formation :</label></td>

					<td><select name="nomformation" id="nomformation"
						onchange="recupererlisteOrganimeParFormation(),vider(),cacher(),recupererdatedebutFormation();"
						onfocus="recupererlisteOrganimeParFormation(),vider(),cacher(),recupererdatedebutFormation();">
							<%
								for( int i=0;i<liste.size();i++){
							%>
							<option><%=liste.get(i).getListe().getFormation()%>(<%=liste.get(i).getListe().getId_pformation()%>)
							</option>
							<%
							}
							%>
					</select></td>
				</tr>

				<tr class="fond1">

					<td><label for="nomorganismeformation">Organisme de
							formation :</label></td>
					<td><input type="text" disabled="disabled" name="nomorganismeformationconcerne"
						id="nomorganismeformationconcerne" /></td>
				</tr>
				<tr>
				<td>Interlocuteur formation:</td>
				<td><input type="text" name="interlocuteur" id="interlocuteur" /></td>
				</tr>
				<tr>
			</table>
			<br>
			<!-- affiche des indications sur la formation -->
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
			
			<label for="explication" >Commentaires : </label>
			<textarea name="explication" id="explication" ></textarea>
			<br>
			<div id="fin">			
			<input type="submit" value="Enregistrer" />
			<input type="reset" value="Effacer" />
			</div>
			
		</form>
		
		
		<%
		}else
			out.println(nom+" n'est enregistré sur aucune formation");
		%>
	




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
		<br>
		</div>
		
</body>
</html>