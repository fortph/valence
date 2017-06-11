<%@ page
	import="dao.imp.identite.*, beans.identite.*,dao.imp.ai.*,beans.ai.*,
	java.util.*,beans.smic.*,beans.employeurs.*,dao.imp.employeur.*,
	beans.parametres.accueil.*,java.text.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	String idavenant = request.getParameter("avenant");
	AvenantDAO avendao = new AvenantDAO();
	Avenant avenantCont = avendao.findByID(Integer.parseInt(idavenant));

	ContratDAO contdao = new ContratDAO();
	Contrat contratlie = contdao.findByID(avenantCont.getContrat()
			.getIdaicontrat());

	IdentiteDAO iddao = new IdentiteDAO();
	Identite identite = iddao.findByID(contratlie.getIdentite()
			.getId_IDE());
%>
<link rel="stylesheet" href="/valence/css/ai/contrat/contrat.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />


<script type="text/javascript" src="/valence/dwr/engine.js"></script>
<script type="text/javascript" src="/valence/dwr/util.js"></script>


<script type="text/javascript"
	src="/valence/dwr/interface/Utilisateur.js"></script>
<script type="text/javascript" src="/valence/dwr/interface/Employeur.js"></script>
<script type="text/javascript" src="/valence/dwr/interface/Rome.js"></script>
<script type="text/javascript" src="/valence/dwr/interface/Service.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>modification contrat ai</title>


<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>
			
<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier

		var datedebut = document.getElementById("datedebut").value;
		var datefin = document.getElementById("datefin").value;
		var tache = document.getElementById("tache").value;
		var lieu = document.getElementById("lieu").value;
		var facsalhor = document.getElementById("facsalhor").value;
		var salhor = document.getElementById("salhor").value;
		var panier = document.getElementById("panier").value;
		var facpanier = document.getElementById("facpanier").value;
		var dep = document.getElementById("dep").value;
		var facdep = document.getElementById("facdep").value;
		var divers = document.getElementById("divers").value;
		var facdivers = document.getElementById("facdivers").value;
		var dureehebdo = document.getElementById("dureehebdo").value;

		if (datedebut == "") {
			document.form1.datedebut.focus();
			// Afficher un message d'erreur
			attentionComplet("La date de début doit être renseignée !!!!");
			return false;
		}

		if (datefin == "") {
			document.form1.datefin.focus();
			// Afficher un message d'erreur
			attentionComplet("La date de fin doit être renseignée !!!!");
			return false;
		}

		if (dureehebdo != "" && isNaN(dureehebdo)) {
			document.form1.dureehebdo.focus();
			// Afficher un message d'erreur
			attentionComplet(" La durée hebdomadaire doit être un nombre !!!!");
			return false;
		}

		if (tache == "") {
			document.form1.tache.focus();
			// Afficher un message d'erreur
			attentionComplet(" La description des taches doit être renseignée !!!!");
			return false;
		}

		if (lieu == "") {
			document.form1.lieu.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le lieu de travail doit être précisé !!!!");
			return false;
		}

		if (salhor == "" || isNaN(salhor)) {
			document.form1.salhor.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le salaire horaire doit être renseigné !!!!");
			return false;
		}
		if (facsalhor == "" || isNaN(facsalhor)) {
			document.form1.facsalhor.focus();
			// Afficher un message d'erreur
			attentionComplet(" La refacturation horaire doit être renseigné !!!!");
			return false;
		}

		if (panier != "" && isNaN(panier)) {
			document.form1.panier.focus();
			// Afficher un message d'erreur
			attentionComplet("Le montant du panier doit être correctement renseigné !!!!");
			return false;
		}

		if (facpanier != "" && isNaN(facpanier)) {
			document.form1.facpanier.focus();
			// Afficher un message d'erreur
			attentionComplet("Le montant de la refacturation du panier doit être correctement renseigné !!!!");
			return false;
		}

		if (dep != "" && isNaN(dep)) {
			document.form1.dep.focus();
			// Afficher un message d'erreur
			attentionComplet("Le montant des déplacements doit être correctement renseigné !!!!");
			return false;
		}

		if (facdep != "" && isNaN(facdep)) {
			document.form1.facdep.focus();
			// Afficher un message d'erreur
			attentionComplet("Le montant de la refacturation des déplacements doit être correctement renseigné !!!!");
			return false;
		}

		if (divers != "" && isNaN(divers)) {
			document.form1.divers.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le montant des frais divers être correctement renseigné !!!!");
			return false;
		}

		if (facdivers != "" && isNaN(facdivers)) {
			document.form1.facdivers.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le montant de la refacturation des frais divers doit être correctement renseigné !!!!");
			return false;
		}
		
		formulaire.submit();
		return true;

	}
</script>

</head>
<body>





	<div id="body">
		<p><%@ include file='/menus/menusai/menuafficheavenant.jsp'%></p>
		<br>
		<div id="creation">MODIFICATION AVENANT AI</div>
		<br>
		<h3>ETAT CIVIL</h3>


		<table class="table1">
			<tr>
				<td class="un">NOM, Prénom</td>
				<td class="de"><%=identite.getNom_IDE()%> <%=identite.getPrenom_IDE()%></td>
			</tr>
			<tr>
				<td class="un">Adresse</td>
				<td class="de"><%=identite.getAdr1_IDE()%></td>
			</tr>
			<tr>
				<td></td>
				<td class="de"><%=identite.getAdr2_IDE()%><br></td>
			</tr>
			<tr>
				<td>Code Postal et Ville</td>
				<td class="de"><%=identite.getCp_IDE()%> <%=identite.getVille_IDE()%></td>
			</tr>
			<tr>
				<td>Né(e) le</td>
				<td class="de"><% if(identite.getDatenais_IDE()!=null) out.println(sdf.format(identite.getDatenais_IDE()));%></td>
			</tr>
			<tr>
				<td>N° de Sécurité Sociale</td>
				<td class="de"><%=identite.getNss_IDE()%></td>
			</tr>

			<tr>
		</table>
		<br>
		<hr>
		<br>

		<form name="form1" method="post" action="/valence/controleur">
			<input type="hidden" name="action" value="aimodifieravenantcontrat" />
			<input type="hidden" name="numeroavenant" value="<%=idavenant%>" />
			<input type="hidden" name="idpersonne"
				value="<%=identite.getId_IDE()%>" />
			<h3>AVENANT</h3>

			<table class="table2">
				<tr>
					<th>Avenant N°</th>
					<th>DEBUT</th>
					<th>FIN</th>
					<th>HEBDO</th>
				</tr>
				<tr>
					<td><%=avenantCont.getN_aiavenant()%></td>
					<td><input type="text" id="datedebut" name="datedebut"
						value="<% if(avenantCont.getDatedeb()!=null) out.println(sdf.format(avenantCont.getDatedeb()));%>" /></td>
					<td><input type="text" id="datefin" name="datefin"
						value="<% if(avenantCont.getDatefin()!=null) out.println(sdf.format(avenantCont.getDatefin()));%>" /></td>
					<td><input type="text" id="dureehebdo" name="dureehebdo"
						value="<%=avenantCont.getDureehebdomadaire()%>" /></td>
				</tr>
			</table>
			<br>
			<p>Tâches:</p>
			<p class="de">
				<input type="text" name="tache" id="tache"
					value="<%=avenantCont.getTache()%>" />
			</p>
			<br>
			<p>Lieu d'exécution de la tâche:</p>
			<p class="de">
				<input type="text" name="lieu" id="lieu"
					value="<%=avenantCont.getLieu()%>" />
			</p>
			<br>
			<hr>
			<br>

			<h3>FACTURATION</h3>
			<table class="table3">
				<tr>
					<th></th>
					<th>PAIEMENT</th>
					<th>FACTURATION</th>
				</tr>
				<tr>
					<td>SALAIRE HOR BRUT:</td>
					<td class="quatre"><input type="text" id="salhor"
						name="salhor" value="<%=avenantCont.getSalairehoraire()%>"></td>

					<td class="quatre"><input type="text" id="facsalhor"
						name="facsalhor" value="<%=avenantCont.getFacturation()%>"></td>
				</tr>
				<tr>
					<td>PANIERS:</td>
					<td class="quatre"><input type="text" id="panier"
						name="panier" value="<%=avenantCont.getPanier()%>"></td>

					<td class="quatre"><input type="text" id="facpanier"
						name="facpanier" value="<%=avenantCont.getFacturepanier()%>"></td>
				</tr>
				<tr>
					<td>DEPLACEMENTS:</td>
					<td class="quatre"><input type="text" id="dep" name="dep"
						value="<%=avenantCont.getDeplacement()%>"></td>

					<td class="quatre"><input type="text" id="facdep"
						name="facdep" value="<%=avenantCont.getFacturedeplace()%>"></td>
				</tr>
				<tr>
					<td>DIVERS:</td>
					<td class="quatre"><input type="text" id="divers"
						name="divers" value="<%=avenantCont.getDivers()%>"></td>

					<td class="quatre"><input type="text" id="facdivers"
						name="facdivers" value="<%=avenantCont.getFacturedivers()%>"></td>
				</tr>
				<tr>
					<td>COMMENTAIRES:</td>
					<td class="quatre"><input type="text" id="comm" name="comm"
						value="<%=avenantCont.getCommentaire()%>"></td>

					<td class="quatre"><input type="text" id="faccomm"
						name="faccomm" value="<%=avenantCont.getFacturecommentaire()%>"></td>
				</tr>
			</table>
			<br>
			<table class="table1">
				<tr>
					<td class="trois">DATE DE REDACTION</td>
					<td class="de"><input type="text" name="dateredaction"
						id="dateredaction" value="<% if(avenantCont.getRedaction()!=null) out.println(sdf.format(avenantCont.getRedaction()));%>" /></td>
				</tr>
			</table>
			<br> <input type="button" id="droite" value="Enregistrer" class="boutonvert"
				onclick="valider(form1);" />
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
			src="/valence/javascript/scripts/journouveaux.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/scripts/dwrfonctionsemployeurs.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/jquery.mask.min.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/scripts/formatageMask.js"></script>



	</div>

</body>
</html>