<%@ page import="beans.employeurs.*,dao.imp.employeur.*,java.text.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/valence/css/employeur/inscription.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>employeur</title>

<script type="text/javascript" src="/valence/dwr/engine.js"></script>
<script type="text/javascript" src="/valence/dwr/util.js"></script>


<script type="text/javascript" src="/valence/dwr/interface/Statut.js"></script>
<script type="text/javascript" src="/valence/dwr/interface/Structure.js"></script>
<script type="text/javascript" src="/valence/dwr/interface/Activite.js"></script>
<script type="text/javascript"
	src="/valence/dwr/interface/CodesPostaux.js"></script>
	
	
	<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>


<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier

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

		if (statut == "" && civiliteemp == "") {
			document.form1.statut.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le statut de l'employeur ou la civilité du particulier doit être renseigné !!!!");
			return false;
		}

		if (statut != "" && civiliteemp != "") {
			document.form1.statut.focus();
			// Afficher un message d'erreur
			attentionComplet("Le statut de l'employeur et la civilité du particulier ne peuvent  être renseignés en même temps !!!!");
			return false;
		}

		//test adresse mail 
		var expr = new RegExp("^[a-z0-9._-]+@[a-z0-9.-]{2,}[.][a-z]{2,4}$");
		if (vcourriel != "" & !expr.test(vcourriel)) { // Placer le focus dans la zone courriel
			document.form1.mail.focus();
			// Afficher un message d'erreur
			attentionComplet("Le courriel doit avoir un format correct : xxxxx@xxxxx.xx  !!!!");
			// Fin de la fonction -> Pas de submit
			return false;

		}

		if (rs == "") {
			document.form1.rs.focus();
			// Afficher un message d'erreur
			attentionComplet(" La raison sociale doit être renseignée !!!!");
			return false;
		}
		if (adr1 == "") {
			document.form1.adresse1.focus();
			// Afficher un message d'erreur
			attentionComplet("L'adresse doit être renseignée !!!!");
			return false;
		}

		if (cp == "") {
			document.form1.cp.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le code postal doit être renseigné !!!!");
			return false;
		}
		if (ville == "") {
			document.form1.ville.focus();
			// Afficher un message d'erreur
			attentionComplet(" La ville doit être renseignée !!!!");
			return false;
		}
		if (civiliteresp == "") {
			document.form1.civiliteresp.focus();
			// Afficher un message d'erreur
			attentionComplet("La civilité de l'employeur doit être renseigné !!!!");
			return false;

		}
		if (nomresp == "") {
			document.form1.nomresp.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le nom de l'employeur doit être renseigné !!!!");
			return false;
		}

		if (prenomresp == "") {
			document.form1.prenomresp.focus();
			// Afficher un message d'erreur
			attentionComplet("Le prénom de l'employeur doit être renseigné !!!!");
			return false;
		}
		if (fixe == "") {
			document.form1.fixe.focus();
			// Afficher un message d'erreur
			attentionComplet("Le numéro de téléphone est obligatoire !!!!");
			return false;

		}
		if (structure == "") {
			document.form1.structure.focus();
			// Afficher un message d'erreur
			attentionComplet("La structure de  l'employeur doit être renseignée !!!!");
			return false;

		}

		if (activite == "") {
			document.form1.activite.focus();
			// Afficher un message d'erreur
			attentionComplet(" L'activité de l'employeur doit être renseigné !!!!");
			return false;

		}

		
		formulaire.submit();
		return true;
	}
</script>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	String numero = request.getParameter("numero");
	EmployeurDAO empdao = new EmployeurDAO();
	Employeur emp = empdao.findByID(Integer.valueOf(numero));
	boolean actif = emp.isActif();
%>

</head>
<body>

	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<h3 id="creation">MODIFICATION FICHE EMPLOYEUR</h3>
		<br>
		<hr>


		<h3>EMPLOYEUR</h3>
		<form method="post" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="modifieremployeur" /> <input
				type="hidden" name="emp" value="<%=numero%>" />
			<center>
				<label for="dateinscription">Date de Création:</label> <input
					type="text" id="dateinscription" name="dateinscription"
					value="<% if(emp.getDatecreation()!=null) out.println(sdf.format(emp.getDatecreation()));%>" />
			</center>
			<br> <label>Fiche active: </label><input type="radio"
				class="actif" name="actif" value="oui" <%=actif ? "checked" : ""%> /><label>
				OUI </label> <input type="radio" class="actif" name="actif" value="non"
				<%=actif ? "" : "checked"%> /><label> NON</label>
			<p>&nbsp;</p>
			<table class="table1">
				<tr>
					<td>L'employeur est un professionnel :</td>
					<td><select name="statut" id="statut" class="select1"
						onfocus="afficherStatuts();">
							<option><%=emp.getRsstatut_employeur().getStaturrs()%></option>
					</select></td>

					<td>&nbsp;L'employeur est un particulier :</td>
					<td><select name="civiliteemp" id="civiliteemp"
						class="select1">
							<option><%=emp.getCivemp()%></option>
							<option></option>
							<option>Mme</option>
							<option>Mlle</option>
							<option>Mr</option>
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
					<td><input type="text" name="rs" id="rs"
						value="<%=emp.getRs_employeur()%>" /></td>
				</tr>
				<tr>
					<td>Adresse</td>
					<td><input type="text" name="adresse1" id="adresse1"
						value="<%=emp.getAdr1()%>" /></td>
				</tr>
				<tr>
					<td>Adresse (suite)</td>
					<td><input type="text" name="adresse2"
						value="<%=emp.getAdr2()%>" /></td>
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
					<td><input type="text" id="cp" name="cp"
						value="<%=emp.getCp()%>" onblur="recupererSaisie();" /></td>
					<td>VILLE :</td>
					<td>
						<!-- affichage des villes correspondant au code postal --> <select
						id="ville" name="ville" onfocus="recupererSaisie();">
							<option><%=emp.getVille()%></option>
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
						class="select1">
							<option selected="selected"><%=emp.getCivresp()%></option>
							<option></option>
							<option>Mme</option>
							<option>Mlle</option>
							<option>Mr</option>
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
					<td><input type="text" name="nomresp" id="nomresp"
						value="<%=emp.getNomresponsable()%>" /></td>
					<td>Prénom</td>
					<td><input type="text" name="prenomresp" id="prenomresp"
						value="<%=emp.getPrenomresponsable()%>" /></td>
				</tr>
				<tr>
					<td>Téléphone 1:</td>
					<td><input type="text" name="fixe" id="fixe"
						value="<%=emp.getTel1()%>" /></td>
					<td>Téléphone 2:</td>
					<td><input type="text" name="mobile" id="mobile"
						value="<%=emp.getTel2()%>" /></td>
				</tr>
				<tr>
					<td>Fax :</td>
					<td><input type="text" name="fax" id="fax"
						value="<%=emp.getFax()%>" /></td>
					<td>Mail :</td>
					<td><input type="text" name="mail" id="mail"
						value="<%=emp.getMail()%>" /></td>
				</tr>
				<tr>
					<td>Structure</td>
					<td><select name="structure" id="structure"
						onfocus="afficherStructure();">
							<option><%=emp.getStructure().getStructure()%></option>
					</select></td>
					<td>Activité</td>
					<td><select name="activite" id="activite"
						onfocus="recupererActivite();">
							<option><%=emp.getActivite().getActivite()%>
					</select></td>
				</tr>

			</table>


			<table class="obs">
				<tr>
					<th width="15%"></th>
					<th width="85%"></th>
				</tr>
				<tr>
					<td>Rang</td>
					<td><input type="text" name="rangresp" id="rangresp"
						value="<%if (emp.getRangresponsable() != null)
				out.println(emp.getRangresponsable());%>" /></td>
				</tr>
				<tr>
					<td>Observations</td>
					<td><input type="text" name="obs" id="obs"
						value="<%=emp.getObservation()%>" /></td>
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
					<td><input type="text" name="siret"
						value="<%if (emp.getSiret() != null)
				out.println(emp.getSiret());%>" /></td>
					<td>&nbsp;APE :</td>
					<td><input type="text" name="ape"
						value="<%if (emp.getApe() != null)
				out.println(emp.getApe());%>" /></td>
					<td>&nbsp;RM :</td>
					<td><input type="text" name="rm"
						value="<%if (emp.getRm() != null)
				out.println(emp.getRm());%>" /></td>
				</tr>

			</table>
			<br>
			<hr>
			<br>
			<span id="fin">
				<input type="button" value="Modifier" onclick="valider(form1);" />
				<input type="reset" value="Effacer" />
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

