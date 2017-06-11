<%@page import="dao.imp.identite.*,beans.identite.*,java.util.*"%>
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
		var debut = document.getElementById("datedebutformation").value;
		var formation = document.getElementById("nomtheme").value;
		var niveau = document.getElementById("nomniveau").value;
		var heures = document.getElementById("nbheures").value;
		var financeur = document.getElementById("nomfinanceur").value;
		var employeur = document.getElementById("listeemppro").value;
		var statut = document.getElementById("nomstatut").value;

		if (formation == "") {
			document.form1.nomtheme.focus();
			// Afficher un message d'erreur
			attentionComplet(" La formation doit être renseignée  !!!!");
			return false;

		}
		if (niveau == "") {
			document.form1.nomniveau.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le niveau de formation doit être renseignée  !!!!");
			return false;

		}
		if (heures == "" || isNaN(heures)) {
			document.form1.nbheures.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le nombre d'heures de formation doit être correctement renseignée  !!!!");
			return false;

		}

		if (statut == "") {
			document.form1.nomstatut.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le statut du stagiaire doit être renseignée  !!!!");
			return false;
		}
		if (debut == "") {
			document.form1.datedebutformation.focus();
			// Afficher un message d'erreur
			attentionComplet("La date doit être renseignée  !!!!");
			return false;
		}
		if (financeur == "") {
			document.form1.nomfinanceur.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le financement de la formation doit être renseignée  !!!!");
			return false;

		}

		if (statut !='Perso' && employeur == "") {
			document.form1.listeemppro.focus();
			// Afficher un message d'erreur
			attentionComplet(" L'employeur doit être renseignée  !!!!");
			return false;

		}

		formulaire.submit();
		return true;
	}

	function cacher(cib, sour) {
		var cible = document.getElementById("listeemppro");
		var source = document.getElementById("nomstatut");
		var libelle=document.getElementById("libelleemp");
		var bouton=document.getElementById("boutonemp");

		if (source.value == "Perso") {
			cible.visibility = "hidden";
			libelle.visibility = "hidden";
			bouton.visibility = "hidden";
			cible.style.display = "none";
			libelle.style.display = "none";
			bouton.style.display = "none";
			
		} else {
			cible.visibility = "visible";
			libelle.visibility = "visible";
			bouton.visibility = "visible";
			cible.style.display = "block";
			libelle.style.display = "block";
			bouton.style.display = "block";

		}

	}
</script>
<link rel="stylesheet" href="/valence/css/formation/preinscription.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery-ui-1.10.3.custom.min.css">

<%
	String id = request.getParameter("numero");
	String id1=(String)request.getAttribute("numero");
	int num=0;
	if(id!=null)
		num = Integer.parseInt(id);
	else
		num = Integer.parseInt(id1);
	IdentiteDAO identitedao = new IdentiteDAO();
	Identite identite = identitedao.findByID(num);
	String nom = identite.getNom_IDE() + " " + identite.getPrenom_IDE();
%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inscription formation</title>
<script type="text/javascript" src="/valence/dwr/engine.js"></script>
<script type="text/javascript" src="/valence/dwr/util.js"></script>

<script type="text/javascript"
	src="/valence/dwr/interface/FormprofinancementDAO.js"></script>
<script type="text/javascript"
	src="/valence/dwr/interface/FormproniveauDAO.js"></script>
<script type="text/javascript"
	src="/valence/dwr/interface/FormprothemeDAO.js"></script>
<script type="text/javascript"
	src="/valence/dwr/interface/FormationProEmployeurDAO.js"></script>
<script type="text/javascript"
	src="/valence/dwr/interface/FormprostatutemployeurDAO.js"></script>
</head>
<body
	onload="recupererNiveauFormationspro(),recupererfinancementFormationspro(),recupererthemeFormationspro(),recupererEmployeursFormPro(),recupererStatutFormationspro();">
	<div id="body">
	<p><%@ include file='/menus/menugeneral/menu.html' %></p><br>
		<form method="post" name="form1" action="/valence/controleur">
			<input type="hidden" name="action"
				value="enregistrerinscriptionformationpro" /> <input
				type="hidden" name="numero" value="<%=num%>" />
			<table>
				<tr>
					<td>Identification : <label id="nom"><%=nom%></label></td>
				</tr>



				<tr>
					<td><label for="nomtheme">Formation :</label></td>
					<td><select name="nomtheme" id="nomtheme">
							<option></option>
					</select></td>
					<td><input type="button" value="Nouvelle Formation" id="boutonformation"
						onclick="self.location.href='nouvelleformation.jsp?ident=<%=num%>'" /></td>
				</tr>
				<tr>
					<td>Niveau:</td>
					<td><select name="nomniveau" id="nomniveau">
							<option></option>
					</select></td>

				</tr>

				<tr>
					<td>Nombre d'heures de formation:</td>
					<td><input type="text" name="nbheures" id="nbheures" /></td>

				</tr>
				<tr>
					<td>Statut du stagiaire :</td>
					<td><select name="nomstatut" id="nomstatut" onchange="cacher('nomstatut','ligneemployeur');">
							<option></option>
					</select></td>

				</tr>

				<tr>
					<td>Date début de formation :</td>
					<td><input type="text" id="datedebutformation"
						name="datedebutformation" /></td>
				</tr>

				<tr>
					<td>Financement :</td>
					<td><select name="nomfinanceur" id="nomfinanceur">
							<option></option>
					</select></td>
				</tr>
				<tr>
					<td><span id="libelleemp" >Employeur :</span></td>
					<td><select name="listeemppro" id="listeemppro">
							<option></option>
					</select></td>
					<td><input type="button" value="Nouvel Employeur" id="boutonemp"
						onclick="self.location.href='nouvelemployeur.jsp?personne=<%=id%>'" /></td>
				</tr>
				<tr>
					<td>Prix :</td>
					<td><input type="text" name="montant" id="montant" value="0.00"/>
							</td>
				</tr>
				<tr>
					<td><input type="button" value="Enregistrer"
						onclick="valider(form1);" /></td>
					<td><input type="reset" value="Effacer" /></td>
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