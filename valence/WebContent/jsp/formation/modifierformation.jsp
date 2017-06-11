<%@page import="dao.imp.formation.*, beans.formation.*,java.sql.*,java.text.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/valence/css/formation/affichage.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<script type="text/javascript" src="/valence/dwr/engine.js"></script>
<script type="text/javascript" src="/valence/dwr/util.js"></script>

<script type="text/javascript"
	src="/valence/dwr/interface/FormprostatutemployeurDAO.js"></script>
	<script type="text/javascript"
	src="/valence/dwr/interface/OrganismeFormationDAO.js">
	</script>
	<script type="text/javascript"
	src="/valence/dwr/interface/NiveauQualificationFormationDAO.js">
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
		
		var nom = document.getElementById("nom").value;
		
		if (nom == "" || !isNaN(parseInt(nom.substring(0,2)))) {
			document.form1.nom.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le nom de formation indiqué  ne doit pas contenir de date. Le système la rajoute automatiquement !!!!");
			return false;
		}
		
		
		formulaire.submit();
		return true;
	}
</script>


<%
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
	String num = request.getParameter("numero");
	int numero = Integer.parseInt(num);
	ListeFormationsDAO formationsdao = new ListeFormationsDAO();
	ListeFormations formation = formationsdao.findByID(numero);
	String oui = "", non = "";
	if (formation.isActif()) {
		oui = "checked";
		non = "";
	} else {
		non = "checked";
		oui = "";
	}
	String etat=formation.getEtat_form();
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>modification formation</title>
</head>
<body onload="recupererOrganisme();" >
<div id="body">
	<p><%@ include file='/menus/menugeneral/menu.html' %></p><br>
	<form method="post" name="form1"  action="/valence/controleur" >
		<input type="hidden" name="action" value="modifierformation" />
		 <input	type="hidden" name="numero" value="<%=numero%>" /> 
		 <input	type="hidden" name="etat" value="<%=etat%>" /> 
		<div id="creation"> MODIFIER FORMATION</div>
		<hr>
		<br>
		<table>
			<tr>
				<td>Libellé :</td>
				<%
				String nomFormation=formation.getFormation().substring(5);
				%>
				<td ><input type="text" name="nom" id="nom" class="valeur" 
					value="<%=nomFormation%>" /></td>
					<td></td>
			</tr>
			<tr>
				<td>Organisme de formation :</td>
				<td ><select name="nomorganismeformation" class="valeur"
				id="nomorganismeformation" onfocus="remplirListeOrganisme();" >
						<option selected="selected"><%=formation.getOrganis().getOrg()%>(<%=formation.getOrganis().getId_org() %>)</option>
				</select></td>
				<td><input	type="hidden" name="idorg" value="<%=formation.getOrganis().getId_org()  %>" /></td> 
			</tr>
			<tr>
				<td>Niveau :</td>
				<td ><select name="niveauqualification" class="valeur"
				id="niveauqualification" onfocus="recupererNiveauQualif();" >
				<option selected="selected"><%=formation.getNiveau()%></option>
					</select></td>
					<td></td>
			</tr>
			<tr>
				<td>Date de début :</td>
				<td ><input type="text" id="debutformation" name="debut"
					class="valeur" value="<% if(formation.getDatedeb_form()!=null) out.println(sdf.format(formation.getDatedeb_form())); %>" /></td>
					<td></td>
			</tr>
			<tr>
				<td>Date de fin :</td>
				<td><input type="text" id="finformation" name="fin"
					 class="valeur" value="<% if(formation.getDatefin_form()!=null) out.println(sdf.format(formation.getDatefin_form())); %>" /></td>
					 <td></td>
			</tr>
			<tr>
				<td>Nombre d'heures :</td>
				<td ><input type="text" name="heure" class="valeur"
					value="<%=formation.getHeure_form()%>" /></td>
					<td></td>
			</tr>
			<tr>
				<td>Thème :</td>
				<td ><select name="nomtheme" class="valeur"
				id="nomtheme" onfocus=" recupererthemeFormations();" >
				<option selected="selected"><%=formation.getTheme().getLibelle()   %></option>
				</select></td>
				<td></td>
			</tr>
			</table>
			<table>
			<tr>
				<td>Actif :</td>
				<td ><input type="radio" name="actif" value="OUI" <%=oui%> />
					OUI </td><td><input type="radio" name="actif" value="NON" <%=non%> /> NON</td>
			</tr>
			
		</table>
<br>
<hr><br>
		<span id="fin"><input type="button" id="droite" value="Enregistrer"
				onclick="valider(form1);" />
			 <input type="reset"
			value="Restaurer" /></span>
			
	</form>
	
	
	<br>
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