<%@ page
	import="dao.imp.identite.*, beans.identite.*,java.util.Date,java.text.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String id = request.getParameter("personne");
	IdentiteDAO iddao = new IdentiteDAO();
	Identite un = iddao.findByID(Integer.parseInt(id));
%>

<link rel="stylesheet" href="/valence/css/rth/fiche.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>creation fiche RTH</title>

<script type="text/javascript"
	src="/valence/javascript/scripts/attention.js"></script>

<script type="text/javascript">
	/*verifie qu'au moins 1 bouton radio est coché*/
	function getRadioValue() {
		var val = null;
		for (var i = 0; i < document.form1.categorie.length; i++) {
			if (document.form1.categorie[i].checked) {
				val = document.form1.categorie[i].value;
			}

		}
		return val;
	}
	/*******************************************************************/
</script>


<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier

		var pension = document.getElementById("pension").value;

		if (getRadioValue() == null) {
			document.form1.categorie[0].focus();
			// Afficher un message d'erreur
			attentionComplet(" Une catégorie doit être cochée !!!!");
			return false;
		}
		if (pension == "Aucune") {
			document.form1.pension.focus();
			// Afficher un message d'erreur
			attentionComplet(" La personne perçoit elle une pension?...");
			return false;
		}

		formulaire.submit();
		return true;
	}
</script>



</head>
<body>



	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div id="creation">CREATION FICHE RTH</div>
		<br>

		<table class="table3">
			<tr>
				<td>NOM :</td>
				<td class="affiche"><%=un.getNom_IDE()%></td>
				<td>PRENOM:</td>
				<td class="affiche"><%=un.getPrenom_IDE()%></td>
			</tr>
			<tr>
				<td>ADRESSE :</td>
				<td class="affiche"><%=un.getAdr1_IDE()%></td>
				<td>VILLE :</td>
				<td class="affiche"><%=un.getCp_IDE()%> <%=un.getVille_IDE()%></td>
			</tr>
		</table>
		<br>
		<hr>
		<br>
		<h3>CONTRAT</h3>
		<form method="post" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="creationficherth" /> <input
				type="hidden" name="personne" value="<%=id%>" /> <br>

			<table class="table4">
				<tr>
					<td colspan="7" class="th">Catégorie</td>
				</tr>
				<tr>
					<td><input type="radio" name="categorie" value="A"> A</td>
					<td><input type="radio" name="categorie" value="B"> B
					</td>
					<td><input type="radio" name="categorie" value="C"> C
					</td>
					<td><input type="radio" name="categorie" value="D"> D
					</td>
					<td><input type="radio" name="categorie" value="E"> E
					</td>
					<td><input type="radio" name="categorie" value="F"> F
					</td>
					<td><input type="radio" name="categorie" value="G"> G</td>
				</tr>
			</table>
			<br>
			<table class="table2">
				<tr>

					<th>Taux</th>
					<th>Pension</th>
					<th>Montant</th>
				</tr>
				<tr>

					<td><input type="text" id="taux" name="taux" value="0" /></td>
					<td><select name="pension" id="pension">
							<option>Aucune</option>
							<option>OUI</option>
							<option>NON</option>
					</select></td>
					<td><input type="text" name="montant" id="montant" value="0" />
					</td>
				</tr>

			</table>
			<br>
			<table class="table1">
				<tr>
					<th>CI</th>
					<th>Référent ADIAD</th>
				</tr>
				<tr>
					<td><input type="text" name="ci" id="ci" /></td>
					<td><input type="text" name="referent" id="referent" /></td>
				</tr>
			</table>

			<br> <input type="button" id="droite" value="Enregistrer" class="boutonvert"
				onclick="valider(form1);" />
		</form>


		<script type="text/javascript"
			src="/valence/javascript/jquery.mask.min.js"></script>

		<script type="text/javascript"
			src="/valence/javascript/scripts/formatageMask.js"></script>



	</div>


</body>
</html>