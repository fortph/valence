<%@page import="dao.imp.identite.*,beans.identite.*,beans.formation.*,dao.imp.formation.*,java.util.*"%>
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
	
	function cacher(){
		var affiche=$("#nomformation").val();
		var exp=new RegExp("^AUCUNE.*");
		//alert("affiche ="+affiche);
		if(exp.test(affiche) || affiche=="")
			$(".fond3").hide();
		else
			$(".fond3").show();
	}
</script>


<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>

<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		
		
		var nomformation = document.getElementById("nomformation").value;
		
		
		if (nomformation == "") {
			document.form1.nomformation.focus();
			// Afficher un message d'erreur
			attentionComplet(" Une formation doit être renseignée !!!!");
			return false;
		}
		
		formulaire.submit();
		return true;
	}
</script>

<link rel="stylesheet"
	href="/valence/css/formation/preinscription.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />

<%
	String id = request.getParameter("numero");
	int num =0;
	try{
		num=Integer.parseInt(id);
	}
	catch (NumberFormatException ne){
	ne.printStackTrace();	
    }
	IdentiteDAO identitedao = new IdentiteDAO();
	Identite identite = identitedao.findByID(num);
	String nom =   identite.getPrenom_IDE()+" "+identite.getNom_IDE();
	
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
	onload="recupererAnimatriceformation(),recupererPrescripteurformation(),recupererlisteFormations(),cacher();">
	<div id="body">
	<p><%@ include file='/menus/menupyramide/menupyramide.jsp' %></p><br>
	
	<div id="creation"> PREINSCRIPTION FORMATION PYRAMIDE </div>
	<br>
	<form name="form1" method="post" action="/valence/controleur">
		<input type="hidden" name="action" value="enregistrerpreinscriptionformation" /> 
		<input	type="hidden" name="numero" value="<%=num%>" />
		<table id="table1">
			<tr>
				<td>Identification :</td><td> <label id="nom"><%=nom%></label></td>
			</tr>
			<tr>
				<td >Adresse : </td><td><%=identite.getCp_IDE()%> <%=identite.getVille_IDE() %></td>
			</tr>
			<tr><td> &nbsp;</td></tr>
						<tr>
				<td><label for="tempsaccueilformation">Temps d'Accueil
						:</label></td>
				<td><input type="text" id="tempsaccueilformation"
					value="01:00:00" name="tempsaccueilformation" /></td>
			</tr>
			<tr>
				<td><label for="animatriceformation">Animatrice :</label></td>
				<td><select name="animatriceformation" id="animatriceformation"></select></td>
			</tr>
			<tr>
				<td><label for="prescripteurformation">Prescripteur :</label></td>
				<td><select name="prescripteurformation" id="prescripteurformation"></select></td>
			</tr>
			<tr>
				<td><label for="nomformation">Formation :</label></td>
				<td><select name="nomformation" id="nomformation"
					onchange="recupererlisteOrganimeParFormation(),vider(),cacher(),recupererdatedebutFormation();">
					<option></option>
					</select></td><td id="nb">  Sélectionner 'AUCUNE' si aucune formation n'est trouvée</td>
			</tr>
			
			<tr  class="fond2">
				
				<td><label for="nomorganismeformation">Organisme de
						formation :</label></td>
				<td><input type="text" disabled="disabled"
					id="nomorganismeformationconcerne" 
					 /></td>
			</tr>
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
			
			<div id="fin">
			<br>
			<input type="button" value="Enregistrer" class="boutonvert"
				onclick="valider(form1);" />
			
			<input type="reset" class="boutonrouge" value="Effacer" />
			</div>
		
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
	<script>
	$(document).ready(function() {
		$("tr td#nb").fadeOut(800).fadeIn(800).fadeOut(400).fadeIn(400);
	});
	
	</script>
	<script type="text/javascript"
		src="/valence/javascript/scripts/journouveaux.js"></script>
	<script type="text/javascript"
		src="/valence/javascript/scripts/instructions.js"></script>
</body>
</html>