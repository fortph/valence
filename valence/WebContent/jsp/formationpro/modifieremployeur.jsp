<%@page import="dao.imp.formationpro.*, beans.formationpro.*,java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/valence/css/formationpro/preinscription.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<script type="text/javascript" src="/valence/dwr/engine.js"></script>
<script type="text/javascript" src="/valence/dwr/util.js"></script>

<script type="text/javascript"
	src="/valence/dwr/interface/FormprostatutemployeurDAO.js"></script>
<script type="text/javascript"
	src="/valence/dwr/interface/CodesPostaux.js"></script>

<%
	String num = request.getParameter("numero");
	int numero = Integer.parseInt(num);
	FormationProEmployeurDAO empdao = new FormationProEmployeurDAO();
	FormationProEmployeur employeur = empdao.findByID(numero);
	String oui = "", non = "";
	if (employeur.isActif()) {
		oui = "checked";
		non = "";
	} else {
		non = "checked";
		oui = "";
	}
	
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>modification employeur</title>
</head>
<body>
<body>
<div id="body">
	<p><%@ include file='/menus/menugeneral/menu.html' %></p><br>
	<form method="post" action="/valence/controleur" >
		<input type="hidden" name="action" value="modifieremployeurforpro" />
		 <input	type="hidden" name="numero" value="<%=numero%>" /> 
		 
		<div id="creation">MODIFIER EMPLOYEUR</div>
		
		<br>
		<table id="table1">
			<tr>
				<td>NOM :</td>
				<td ><input type="text" name="nom" disabled="disabled"  class="valeur" 
					value="<%=employeur.getEmployeur()   %>" /></td>
			</tr>
			<tr>
					<td>Adresse :</td>
					<td><input type="text" name="adresse" id="adresse" value="<% if(employeur.getAdresse()!=null)out.println(employeur.getAdresse()); %>"/></td>
				</tr>
				<tr>
					<td>Code Postal :</td>
					<td><input type="text" id="cp" name="cp" value="<% if(employeur.getCp()!=null) out.println(employeur.getCp()); %>"
						onblur="recupererSaisie();" /></td>
				</tr>
				<tr>
					<td>VILLE :</td>
					<td>
						<!-- affichage des villes correspondant au code postal --> <select
						id="ville" name="ville">
						<option selected="selected"><% if(employeur.getVille()!=null)out.println(employeur.getVille()); %></option>
					</select>
					</td>
				</tr>
				<tr>
					<td>Téléphone :</td>
					<td><input type="text" name="fixe" id="fixe" value="<% if(employeur.getTel1()!=null)out.println(employeur.getTel1());  %>" /></td>
				</tr>
				<tr>
					<td>Mobile :</td>
					<td><input type="text" name="mobile" id="mobile" value="<% if(employeur.getTel2()!=null)out.println(employeur.getTel2()); %>" /></td>
				</tr>
			<tr>
				<td>STATUT : </td>
		<td><select  name="nomstatut" id="nomstatut" onfocus="recupererStatutFormationspro();">
		<option><%=employeur.getStatut().getStatut() %></option>
		</select></td>
</tr>
				
			<tr>
				<td>Actif :</td>
				<td ><input type="radio" name="actif" value="OUI" <%=oui%> />
					OUI <input type="radio" name="actif" value="NON" <%=non%> /> NON</td>
			</tr>
			
		</table>
		<br>
		<div id="fin">

		<input type="submit" value="Modifier" /> <input type="reset"
			value="Restaurer" />
			</div>
			
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
	<script type="text/javascript"
		src="/valence/javascript/jquery.mask.min.js"></script>
	<script type="text/javascript"
		src="/valence/javascript/scripts/formatageMask.js"></script>
	<script type="text/javascript"
		src="/valence/javascript/scripts/instructions.js"></script>
	
</div>
</body>
</html>