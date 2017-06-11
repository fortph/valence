<%@page import="dao.imp.formation.*, beans.formation.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>

<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		var org=document.getElementById("nom").value;
		//var phone = document.getElementById("phone").value;
		var codep = document.getElementById("cp").value;
		var tel = document.getElementById("fixe").value;
		
	if(org=="" || org.length==0){
		document.form4.nom.focus();
		// Afficher un message d'erreur
		attentionComplet("Le nom de l'organisme est requis  !!!!");
		return false;
		
	}
		
		//test du code postal
		var ex1 = new RegExp(
				"^((0[1-9])|([1-8][0-9])|(9[0-8])|(2A)|(2B))[0-9]{3}$");
		if (codep !="" && !ex1.test(codep)) {
			document.form4.cp.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le code postal est incorrect  !!!!");
			return false;
		}

		if(tel!="" && tel.length!=14){
			document.form4.fixe.focus();
			// Afficher un message d'erreur
			attentionComplet("Le numéro de téléphone est incorrect  !!!!");
			return false;
			
		}
		
		formulaire.submit();
		return true;
	}
</script>


<%
	String numero = request.getParameter("numero");
	int num = Integer.parseInt(numero);
	OrganismeFormationDAO ofdao = new OrganismeFormationDAO();
	OrganismeFormation of = ofdao.findByID(num);
	String oui = "", non = "";
	if (of.isActif()) {
		oui = "checked";
		non = "";
	} else {
		non = "checked";
		oui = "";
	}
%>

<link rel="stylesheet" href="/valence/css/formation/affichageorg.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>modification organisme</title>
</head>
<body>
	<div id="body">
	<p><%@ include file='/menus/menugeneral/menu.html' %></p><br>
	
	<div id="creation">MODIFIER ORGANISME FORMATION</div><br>
		<form method="post" name="form4"  action="/valence/controleur">
			<input type="hidden" name="action" value="modifierorganismeformation" />
			<input type="hidden" name="numero" value="<%=numero%>" />
			
			
			<br>
			<table>
				<tr>
					<td>Nom :</td>
					<td><input type="text" class="valeur" id="nom" name="nom" value="<%=of.getOrg()%>" /></td>
				</tr>
				<tr>
					<td>Adresse :</td>
					<td><input type="text" name="adresse"
						class="valeur" value="<%=of.getAdr_org()%>" /></td>
				</tr>
				<tr>
					<td>Code postal :</td>
					<td><input type="text" name="cp" id="cp" class="valeur" value="<%=of.getCp_org()%>" /></td>
				</tr>
				<tr>
					<td>Ville :</td>
					<td><input type="text" name="ville" class="valeur"
						value="<%=of.getVille_org()%>" /></td>
				</tr>
				<tr>
					<td>Téléphone :</td>
					<td><input type="text" id="fixe" name="phone" class="valeur"
						value="<%=of.getTel_org()%>" /></td>
				</tr>
				<tr>
					<td>Actif :</td>
					
					<td>OUI<input type="radio" name="actif"  value="OUI" <%=oui%> />
					</td>
					</tr>
					<tr><td></td><td>NON<input type="radio" name="actif" value="NON" <%=non%> />
					</td>
				</tr>
			</table>
			<br> <div id="fin">
				<input type="button" value="modifier" onclick="valider(form4);" /><input
					type="reset" value="Effacer" />
</div>

		</form>
		<br>
	
	<script type="text/javascript" src="/valence/javascript/jquery.mask.min.js"></script>
	<script type="text/javascript" src="/valence/javascript/scripts/formatageMask.js"></script>
</div>	
</body>
</html>