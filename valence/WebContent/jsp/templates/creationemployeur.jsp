<%@ page import="beans.employeurs.*,dao.imp.employeur.*,beans.ai.*,dao.imp.ai.*,beans.identite.*,dao.imp.identite.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/valence/css/employeur/inscription.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Nouvel employeur</title>

<%
String personne=null;

if(request.getParameter("numero")!=null){
	personne=request.getParameter("numero");
		
}

%>

<script type="text/javascript" src="/valence/dwr/engine.js"></script>
<script type="text/javascript" src="/valence/dwr/util.js"></script>


<script type="text/javascript" src="/valence/dwr/interface/Statut.js"></script>
<script type="text/javascript" src="/valence/dwr/interface/Structure.js"></script>
<script type="text/javascript" src="/valence/dwr/interface/Activite.js"></script>
<script type="text/javascript" src="/valence/dwr/interface/Civilite.js"></script>
<script type="text/javascript"
	src="/valence/dwr/interface/CodesPostaux.js"></script>


<script type="text/javascript"
	src="/valence/javascript/scripts/attention.js"></script>


<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		
		var mailcontact = document.getElementById("mailcontact").value;
		var statut = document.getElementById("statut").value;
		var civiliteemp = document.getElementById("civiliteemp").value;
		var fixe = document.getElementById("fixe").value;
		var vcourriel = document.getElementById("mail").value;
		var rs = document.getElementById("rs").value;
		var adr1 = document.getElementById("adresse1").value;
		var cp = document.getElementById("cp").value;
		var ville = document.getElementById("ville").value;
		var nomresp = document.getElementById("nomresp").value;
		var prenomresp = document.getElementById("prenomresp").value;
		var civiliteresp = document.getElementById("civiliteresp").value;
		var structure = document.getElementById("structure").value;
		var activite = document.getElementById("activite").value;
		var nomcontact = document.getElementById("nomcontact").value;
		var prenomcontact = document.getElementById("prenomcontact").value;
		//test adresse mail 
		var expr = new RegExp("^[a-z0-9._-]+@[a-z0-9.-]{2,}[.][a-z]{2,4}$");
		if (vcourriel != "" & !expr.test(vcourriel)) { // Placer le focus dans la zone courriel
			document.form1.mail.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le courriel doit avoir un format correct : xxxxx@xxxxx.xx  !!!!");
			// Fin de la fonction -> Pas de submit
			return false;

		}
		if (statut == "" && (civiliteemp ==""  || civiliteemp ==0)) {
			document.form1.statut.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le statut de l'employeur ou la civilité du particulier doit être renseigné !!!!");
			return false;
		}
		
		if (statut != "" && (civiliteemp != "" || civiliteemp !=0)) {
			document.form1.statut.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le statut de l'employeur et la civilité du particulier ne peuvent  être renseignés en même temps !!!!");
			return false;
		}
		if (rs == "") {
			document.form1.rs.focus();
			// Afficher un message d'erreur
			attentionComplet("La raison sociale doit être renseignée !!!!");
			return false;
		}
		if (adr1 == "") {
			document.form1.adresse1.focus();
			// Afficher un message d'erreur
			attentionComplet(" L'adresse doit être renseignée !!!!");
			return false;
		}

		if (cp == "" || isNaN(cp)) {
			document.form1.cp.focus();
			// Afficher un message d'erreur
			attentionComplet("Le code postal doit être renseigné !!!!");
			return false;
		}
		if (ville == "") {
			document.form1.ville.focus();
			// Afficher un message d'erreur
			attentionComplet(" La ville doit être renseignée !!!!");
			return false;
		}
		if (civiliteresp == "" || civiliteresp ==0) {
			document.form1.civiliteresp.focus();
			// Afficher un message d'erreur
			attentionComplet(" La civilité du responsable doit être renseigné !!!!");
			return false;

		}
		if (nomresp == "") {
			document.form1.nomresp.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le nom du responsable  doit être renseigné !!!!");
			return false;
		}

		if (prenomresp == "") {
			document.form1.prenomresp.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le prénom du responsable  doit être renseigné !!!!");
			return false;
		}
		if (fixe == "") {
			document.form1.fixe.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le numéro de téléphone est obligatoire !!!!");
			return false;

		}
		if (structure == 0 || structure=="") {
			document.form1.structure.focus();
			// Afficher un message d'erreur
			attentionComplet(" La structure de  l'employeur doit être renseignée !!!!");
			return false;

		}

		if (activite ==0 || activite=="") {
			document.form1.activite.focus();
			// Afficher un message d'erreur
			attentionComplet(" L'activité de l'employeur doit être renseigné !!!!");
			return false;

		}
		if (nomcontact != "") {
			if (prenomcontact == "") {
				document.form1.prenomcontact.focus();
				// Afficher un message d'erreur
				attentionComplet("Le prénom du contact doit être renseigné !!!!");
				return false;

			}

		}
		
		if (mailcontact != "" && !expr.test(mailcontact)) { // Placer le focus dans la zone courriel
			document.form1.mailcontact.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le courriel doit avoir un format correct : xxxxx@xxxxx.xx  !!!!");
			// Fin de la fonction -> Pas de submit
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
		<h3 id="creation">CREATION FICHE EMPLOYEUR</h3>
		<br>
		<hr>


		<h3>EMPLOYEUR</h3>
		<form method="post" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="saveemployeur" />
			<input type="hidden" name="type" value="<%=type %>" />
			<% if(personne!=null) { %>
			<input type="hidden" name="personne" value="<%=personne %>" />
			<%
			}
			%>
			<center>
				<label for="dateinscription">Date de Création:</label> <input
					type="text" id="dateinscription" name="dateinscription" />
			</center>
			<br>


			<table class="table1">
				<tr>
					<td>L'employeur est un professionnel :</td>
					<td><select name="statut" id="statut" class="select1"
						onfocus="afficherStatuts();"></select></td>

					<td>&nbsp;L'employeur est un particulier :</td>
					<td><select name="civiliteemp" id="civiliteemp"
						class="select1" onfocus="recupererCivilite();" >
							
					</select></td>
				</tr>
			</table>


			<table class="table2">
				<tr>
					<th class="col1"></th>
					<th class="col2"></th>
				</tr>
				<tr>
					<td>Raison Sociale</td>
					<td><input type="text" name="rs" id="rs"></td>
				</tr>
				<tr>
					<td>Adresse</td>
					<td><input type="text" name="adresse1" id="adresse1"></td>
				</tr>
				<tr>
					<td>Adresse (suite)</td>
					<td><input type="text" name="adresse2"></td>
				</tr>
			</table>



			<table class="table3">
				<tr>
					<th width="15%"></th>
					<th width="35%"></th>
					<th width="15%"></th>
					<th width="35%"></th>
				</tr>
				<tr>

					<td>CODE POSTAL :</td>
					<td><input type="text" id="cp" name="cp" value="82"
						onblur="recupererSaisie();" /></td>
					<td>VILLE :</td>
					<td>
						<!-- affichage des villes correspondant au code postal -->
						 <select
						id="ville" name="ville" onfocus="recupererSaisie();">
					</select>
					</td>
				</tr>
			</table>
			<br>


			<table class="resp">
				<tr>
					<th width="15%"></th>
					<th width="15%"></th>
				</tr>
				<tr>
					<td>RESPONSABLE :</td>
					<td><select name="civiliteresp" id="civiliteresp"
						class="select1" onfocus="recupererCiviliteResp();" >
							
					</select></td>
				</tr>
			</table>

			<table class="table4">
				<tr>
					<th width="15%"></th>
					<th width="35%"></th>
					<th width="15%"></th>
					<th width="35%"></th>
				</tr>

				<tr>
					<td>Nom</td>
					<td><input type="text" name="nomresp" id="nomresp" /></td>
					<td>Prénom</td>
					<td><input type="text" name="prenomresp" id="prenomresp" /></td>
				</tr>
				<tr>
					<td>Téléphone 1:</td>
					<td><input type="text" name="fixe" id="fixe" /></td>
					<td>Téléphone 2:</td>
					<td><input type="text" name="mobile" id="mobile" /></td>
				</tr>
				<tr>
					<td>Fax :</td>
					<td><input type="text" name="fax" id="fax" /></td>
					<td>Mail :</td>
					<td><input type="text" name="mail" id="mail" /></td>
				</tr>
				<tr>
					<td>Structure</td>
					<td><select name="structure" id="structure"
						onfocus="afficherStructure();"></select></td>
					<td>Activité</td>
					<td><select name="activite" id="activite"
						onfocus="recupererActivite();"></select></td>
				</tr>

			</table>


			<table class="obs">
				<tr>
					<th width="15%"></th>
					<th width="85%"></th>
				</tr>
				<tr>
					<td>Rang</td>
					<td><input type="text" name="rangresp" id="rangresp" /></td>
				</tr>
				<tr>
					<td>Observations</td>
					<td><input type="text" name="obs" id="obs" /></td>
				</tr>
			</table>


			<table class="table6">
				<tr>
					<th width="15%"></th>
					<th width="35%"></th>
					<th width="5%"></th>
					<th width="10%"></th>
					<th width="5%"></th>
					<th width="15%"></th>
				</tr>
				<tr>
					<td>SIRET :</td>
					<td><input type="text" name="siret" /></td>
					<td>APE :</td>
					<td><input type="text" name="ape" /></td>
					<td>RM :</td>
					<td><input type="text" name="rm" /></td>
				</tr>

			</table>
			<br>
			<hr>
			<br>



			<h3>CONTACT</h3>
			<br>
			<table class="table6">
				<tr>
					<th width="15%"></th>
					<th width="10%"></th>
					<th width="5%"></th>
					<th width="30%"></th>
					<th width="5%"></th>
					<th width="30%"></th>
				</tr>
				<tr>
					<td></td>
					<td><select name="civilitecontact" id="civilitecontact"
						class="select1" onfocus="recupererCiviliteCont();">
							
					</select></td>
					<td>Nom :</td>
					<td><input type="text" name="nomcontact" id="nomcontact"/></td>
					<td>Prénom :</td>
					<td><input type="text" name="prenomcontact" id="prenomcontact"/></td>
				</tr>
			</table>

			<table class="obs">
				<tr>
					<th width="15%"></th>
					<th width="85%"></th>
				</tr>
				<tr>
					<td>Rang</td>
					<td><input type="text" name="rangcontact" id="rangcontact" /></td>
				</tr>
			</table>

			<table class="table4">
				<tr>
					<th width="15%"></th>
					<th width="35%"></th>
					<th width="15%"></th>
					<th width="35%"></th>
				</tr>
				<tr>
					<td>Téléphone fixe :</td>
					<td><input type="text" name="fixecontact" id="fixecontact" /></td>
					<td>Mobile :</td>
					<td><input type="text" name="mobilecontact" id="mobilecontact" /></td>
				</tr>
				<tr>
					<td>Fax :</td>
					<td><input type="text" name="faxcontact" id="faxcontact" /></td>
					<td>Mail :</td>
					<td><input type="text" name="mailcontact" id="mailcontact" /></td>
				</tr>
			</table>
			<br>
			<hr>
			<br>
			<span id="fin">
				<input type="button" value="Ajouter" onclick="valider(form1);" /> <input
					type="reset" value="Effacer" />
			</span>

		</form>


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
			src="/valence/javascript/scripts/journouveaux.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/scripts/dwrfonctionsemployeurs.js"></script>

		<br>
	</div>

</body>
</html>