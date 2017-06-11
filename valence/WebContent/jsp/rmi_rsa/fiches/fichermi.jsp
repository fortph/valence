<%@ page import="dao.imp.identite.*,beans.identite.*,java.util.Date,java.text.*,beans.parametres.accueil.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String id = request.getParameter("personne");
	IdentiteDAO identdao = new IdentiteDAO();
	Identite candidat = identdao.findByID(Integer.parseInt(id));

	String mr = identdao.afficheCivilite(candidat);
	
	
	ReferentPoleEmploiDAO ref=new ReferentPoleEmploiDAO();
	ReferentPoleEmploi referent=ref.afficheReferent();
	
	//on recupere les coordonnees du conseil general 82
	OrganismeDAO orgdao=new OrganismeDAO();
	Organisme organisme=orgdao.findByID(3);

	Date jour = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	String crea = sdf.format(jour);
%>

<link rel="stylesheet" href="/valence/css/rmi/fiche.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />


<script type="text/javascript" src="/valence/dwr/engine.js"></script>
<script type="text/javascript" src="/valence/dwr/util.js"></script>


<script type="text/javascript"
	src="/valence/dwr/interface/Utilisateur.js"></script>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>creation fiche RMI</title>



<script type="text/javascript">
	/*verifie qu'au moins 1 bouton radio est coché*/
	
	function getRadioValue2() {
		var val = null;
		for (var i = 0; i < document.form1.instructeur.length; i++) {
			if (document.form1.instructeur[i].checked) {
				val = document.form1.instructeur[i].value;
			}

		}
		return val;
	}
	/*******************************************************************/
</script>
<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>
			

<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier

		var utilisateurs = document.getElementById("utilisateurs").value;
		var datecreation = document.getElementById("datecreation").value;
		var mailcg82 = document.getElementById("mailcg82").value; 
		var datedebut=document.getElementById("datedebut").value; 
		var datefin=document.getElementById("datefin").value; 
		// var referents = document.getElementById("referents").value;
		if (datecreation == "") {
			document.form1.datecreation.focus();
			attentionComplet(" La date de création doit être complétée !!!!");
			return false;

		}
		if (datedebut == "") {
			document.form1.datedebut.focus();
			attentionComplet(" La date de début doit être complétée !!!!");
			return false;

		}
		if (datefin == "") {
			document.form1.datefin.focus();
			attentionComplet(" La date de fin doit être complétée !!!!");
			return false;

		}

		if (getRadioValue2() == null) {
			document.form1.instructeur[0].focus();
			// Afficher un message d'erreur
			attentionComplet("Une option 'Prescripteur' doit être cochée !!!!");
			return false;
		}
		
		//test adresse mail 
		var expr = new RegExp("^[a-z0-9._-]+@[a-z0-9.-]{2,}[.][a-z]{2,4}$");
		if (mailcg82 != "" & !expr.test(mailcg82)) { // Placer le focus dans la zone courriel
			document.form1.mailcg82.focus();
			// Afficher un message d'erreur
			attentionComplet("Le courriel doit avoir un format correct : xxxxx@xxxxx.xx  !!!!");
			// Fin de la fonction -> Pas de submit
			return false;

		}
/*
		if (referents == "Aucun") {
			document.form1.referents.focus();
			attentionComplet("Un référent doit être sélectionné !!!!");
			return false;

		}
*/
		if (utilisateurs == "Aucun") {
			document.form1.utilisateurs.focus();
			attentionComplet("Un utilisateur doit être sélectionné !!!!");
			return false;

		}

		
		formulaire.submit();
		return true;
	}
</script>



</head>
<body>



	<div id="body">
		<%@ include file='/menus/menugeneral/menu.html'%>
		<br>
		<div id="creation">CREATION FICHE RSA</div>
		<br>
		<p><%=mr%> <%=candidat.getPrenom_IDE()%> <%=candidat.getNom_IDE() %></p>
		<br>
		<h3>FICHE RSA</h3>

		<form method="post" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="creationfichermi" /> <input
				type="hidden" name="personne" value="<%=id%>" />


			<table class="table2">
				<tr>
					<td>DATE CREATION FICHE :</td>
					<td><input type="text" name="datecreation" id="datecreation"
						value="<%=crea%>" /></td>
				</tr>
				<tr>
					<td colspan="4"></td>
				</tr>
				<tr>
					<td class="petit">DATE DEBUT DU CONTRAT :</td>
					<td><input type="text" name="datedebut" id="datedebut" /></td>
					<td class="petit">DATE FIN DU CONTRAT :</td>
					<td><input type="text" name="datefin" id="datefin" /></td>
					<td>
				</tr>
				
				<tr><td>&nbsp;</td></tr>
				</table>
				<table id="table4">
				<tr>
					<td >PRESCRIPTEUR :</td>
					<td><input type="radio" name="instructeur"
						value="Conseil Général 82" />Conseil Général <input type="radio" name="instructeur"
						value="Pole Emploi" /> Pole Emploi
						</td>
						</tr>
						<tr><td>&nbsp;</td></tr>
						</table>
						
						
						<table id="table5">
						<tr>
						
					<td class="petit">NOM DU CONSEILLER :</td>
					<td ><input type="text" name="conseiller" id="conseiller" /></td>
					</tr><tr><td class="petit">Fonction :</td>
					<td><input type="text" name="fonction" id="fonction" /></td>
					</tr>
					<tr>
					<td class="agencepe petit">Agence :</td>
					<td><input type="text" class="agencepe"
						name="agencepresripteur" id="agencepresripteur" /></td>
				</tr>
				<tr>
					<td class="agencecg82">Mail Conseiller :</td>
					<td><input type="text" class="agencecg82" name="mailcg82"
						id="mailcg82"  /></td>
				</tr>

				
			</table>
			<br/>
			
			
			<hr>
			<br>
			<h3>REFERENT CAP 2000</h3>
			<br>
			<table id="table1">
				<tr>
					<td>NOM :</td>
					<td><select name="utilisateurs" id="utilisateurs"
						onfocus="afficheCapUtilisateurs();"><option>Aucun</option></select></td>

				</tr>
			</table>
			<br>
			
			

			<br> <input type="button" id="droite" value="Enregistrer" class="boutonvert"
				onclick="valider(form1);" />
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
			src="/valence/javascript/scripts/dwrfonctionsemployeurs.js"></script>
			<script type="text/javascript"
			src="/valence/javascript/scripts/journouveaux.js"></script>
			
			

	<script type="text/javascript">
		$(document).ready(
				function() {
					$("input:radio[name='instructeur']").on('load change',
							myfunction);
					function myfunction() {
						if ($(this).is(":checked")
								&& $(this).val() == "Conseil Général 82") {

							$(".agencepe").hide();
							$(".agencecg82").show();

						} else {
							$(".agencepe").show();
							$(".agencecg82").hide();
						}

					}
					;

					myfunction();

				});
	</script>


	</div>


</body>
</html>