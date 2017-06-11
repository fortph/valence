<%@ page
	import="dao.imp.identite.*, beans.identite.*,java.util.Date,java.text.*,dao.imp.rmi.*,beans.rmi.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String id = request.getParameter("numero");
	String fiche = request.getParameter("fiche");
	IdentiteDAO idao = new IdentiteDAO();
	Identite identite = idao.findByID(Integer.parseInt(id));

	String mr = idao.afficheCivilite(identite);

	FicheRMIDAO fichermidao = new FicheRMIDAO();
	FicheRMI fichermi = fichermidao.findByID(Integer.parseInt(fiche));

	String cochecg = "", cochepe = "";
	if (fichermi.getPrescripteur().equals("Conseil Général 82"))
		cochecg = "checked=checked";
	else if (fichermi.getPrescripteur().equals("Pole Emploi"))
		cochepe = "checked=checked";

	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
%>

<link rel="stylesheet" href="/valence/css/rmi/fiche.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />


<script type="text/javascript" src="/valence/dwr/engine.js"></script>
<script type="text/javascript" src="/valence/dwr/util.js"></script>


<script type="text/javascript"
	src="/valence/dwr/interface/Utilisateur.js"></script>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>modification fiche RMI</title>


<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>
			

<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier

		var mailcg82 = document.getElementById("mail").value;

		/*
		 if (getRadioValue2() == null) {
		 document.form1.instructeur[0].focus();
		 // Afficher un message d'erreur
		 alert(" ERREUR !\n\nUne option 'service instructeur' doit être cochée !!!!");
		 return false;
		 }
		 */
		//test adresse mail 
		var expr = new RegExp("^[a-z0-9._-]+@[a-z0-9.-]{2,}[.][a-z]{2,4}$");
		if (mailcg82 != "" & !expr.test(mailcg82)) { // Placer le focus dans la zone courriel
			document.form1.mail.focus();
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
		<div id="creation">MODIFICATION FICHE RMI</div>
		<br>

		<p><%=mr%>
			<%=identite.getPrenom_IDE()%>
			<%=identite.getNom_IDE()%></p>
		<br>
		<form method="post" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="modificationfichermi" /> <input
				type="hidden" name="fiche" value="<%=fiche%>" />


			<table class="table2">
				<tr>
					<td>Date d'accueil : <%
						if (identite.getDateAccueil_IDE() != null)
							out.println(sdf.format(identite.getDateAccueil_IDE()));
						else
							out.println("Non saisie");
					%>
					</td>
					<td></td>
					<td>Date création fiche :</td>
					<td><input type="text" name="datecreation" id="datecreation"
						value="<%if (fichermi.getCreation_rmi() != null)
				out.println(sdf.format(fichermi.getCreation_rmi()));%>" /></td>
				</tr>
				<tr>
					<td colspan="4"></td>
				</tr>

				<tr>
					<td>&nbsp;</td>
				</tr>
				</table>
				<table id="table6">
				<tr>
					<td>PRESCRIPTEUR</td><td>
					 <input type="radio" name="instructeur"
						value="Conseil Général 82" <%=cochecg%> id="cg82"/> Conseil Général <input
						type="radio" name="instructeur" value="Pole Emploi" <%=cochepe%> id="pe" />
						Pole Emploi
						
						</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>NOM DU CONSEILLER :</td>
					<td><input type="text" name="responsable" id="responsable" class="centre"
						value="<%=fichermi.getResponsable()%>" /></td>


					<td>FONCTION :</td>
					<td><input type="text" name="fonction" id="fonction" class="centre"
						value="<%=fichermi.getFonction()%>" /></td>
				</tr>
				<tr>
					
					<td class="agencepe" >Agence :</td>
					<td><input type="text" name="agence"
						id="agence"  class="agencepe centre" 
						value="<%if (fichermi.getAgence() != null)
					out.println(fichermi.getAgence());%>"></td>
					</tr><tr>


					<td class="agencecg82">Mail Conseiller :</td>
					<td ><input type="text" name="mail"
						id="mail" class="agencecg82 centre"
						value="<%if (fichermi.getMail() != null)
					out.println(fichermi.getMail());%>"></td>
					
				</tr>
			</table>
			<br>
			<hr>
			<br>
			<h3>REFERENT</h3>
			<table id="table1">
				<tr>
					<td>NOM DU REFERENT :</td>
					<td><select name="utilisateurs" id="utilisateurs"
						onfocus="afficheCapUtilisateurs();"><option><%=fichermi.getReferent().getNom()%>
								<%=fichermi.getReferent().getPrenom()%></option></select></td>

				</tr>
			</table>
			<br>
			<hr>



			<br> <input type="button" id="droite" value="Enregistrer"
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
						$("input:radio[name=instructeur]").on("load change",
								myfunction);
						function myfunction() {
							var valeur=$("input:radio[name=instructeur]:checked").val();
							
									if( valeur=="Conseil Général 82"){
								$(".agencepe").hide();
								$(".agencecg82").show();

							} else {
								$(".agencecg82").hide();
								$(".agencepe").show();
								
							}

						};

						myfunction();

					});
	</script>



	</div>


</body>
</html>