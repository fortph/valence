<%@ page
	import="dao.imp.identite.*,beans.parametres.accueil.*, beans.identite.*,java.text.*,dao.imp.sap.*,
	beans.sap.*,java.util.*,beans.smic.*,dao.imp.employeur.*,beans.employeurs.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	String presta = request.getParameter("numcontrat");

	PrestationCDIDAO prescdi = new PrestationCDIDAO();
	PrestationCDI prestation = prescdi.findByID(Integer
			.parseInt(presta));
	EmployeurDAO empdao = new EmployeurDAO();
	Employeur employeur = empdao.findByID(prestation.getEmployeur()
			.getId_employeur());

	IdentiteDAO agdao = new IdentiteDAO();
	Identite une = agdao.findByID(prestation.getIdentite().getId_IDE());
%>
<link rel="stylesheet" href="/valence/css/sap/prestation.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />

<script type="text/javascript" src="/valence/dwr/engine.js"></script>
<script type="text/javascript" src="/valence/dwr/util.js"></script>
<script type="text/javascript" src="/valence/dwr/interface/TachesSAP.js"></script>
<script type="text/javascript"
	src="/valence/dwr/interface/AvenantCDI.js"></script>
<script type="text/javascript"
	src="/valence/dwr/interface/ContratCDI.js"></script>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>modifier prestation sap</title>


<script type="text/javascript"
	src="/valence/javascript/scripts/attention.js"></script>


<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier

		var datedebut = document.getElementById("datedebut").value;
		var datefin = document.getElementById("datefin").value;
		var tachepropose = document.getElementById("tachepropose").value;
		var dureehebdo = document.getElementById("dureehebdo").value;
		//var urssaf = document.getElementById("urssaf").value;		
		var salhor = document.getElementById("salhor").value;
		var panier = document.getElementById("panier").value;
		var deplacement = document.getElementById("deplacement").value;
		var facsalhor = document.getElementById("facsalhor").value;

		if (datedebut == "") {
			document.form1.datedebut.focus();
			// Afficher un message d'erreur
			attentionComplet(" La date un début de prestation doit être renseignée !!!!");
			return false;
		}

		if (datefin == "") {
			document.form1.datefin.focus();
			// Afficher un message d'erreur
			attentionComplet(" La date de fin de prestation doit être renseignée !!!!");
			return false;
		}
		if (tachepropose == "") {
			document.form1.tachepropose.focus();
			// Afficher un message d'erreur
			attentionComplet(" L'emploi doit être indiqué !!!!");
			return false;

		}

		if (dureehebdo == "" || isNaN(dureehebdo)) {
			document.form1.dureehebdo.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le nombre d'heures doit être correctement renseigné !!!!");
			return false;
		}

		/*
		
		if (urssaf == "") {
			document.form1.urssaf.focus();
			// Afficher un message d'erreur
			alert(" ERREUR !\n\nLe numéro d'URSSAF doit être renseigné !!!!");
			return false;
		}
		
		 */
		if (salhor == "" || isNaN(salhor)) {
			document.form1.salhor.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le salaire horaire doit être correctement renseigné !!!!");
			return false;
		}

		if (facsalhor == "" || isNaN(facsalhor)) {
			document.form1.facsalhor.focus();
			// Afficher un message d'erreur
			attentionComplet("La refacturation horaire doit être correctement renseigné !!!!");
			return false;
		}
		
		 if(panier!="" && isNaN(panier)){
			 document.form1.panier.focus();
			 attentionComplet(" Le montant du panier doit être correctement renseigné !!!!");
				return false;
		 }
		 
		 if(deplacement!="" && isNaN(deplacement)){
			 document.form1.deplacement.focus();
			 attentionComplet(" Le montant du déplacement doit être correctement renseigné !!!!");
				return false;
		 }
		formulaire.submit();
		return true;
	}
</script>

<script>

function verifnom(){
	//test saisie personne
	var personne = document.getElementById("personne").value;
	//alert(personne);
	var ex1 = new RegExp(
			/^.*\(\d*\)$/i);
	//alert(ex1.test(personne));
	if (personne != "" && !ex1.test(personne)) {			
		// Afficher un message d'erreur
		attentionComplet(" Une personne doit être sélectionnée dans la liste des salariés !!!!");
		document.form1.personne.focus();	
		return false;
	}
}



</script>


</head>
<body onload="attention();">




	<div id="body">
		<p><%@ include file='/menus/employeur/menumodifprestationsap.jsp'%></p>
		<br>
		<div id="creation">
			MODIFIER PRESTATION N° P<%=presta%></div>
		<br>
		
					
		<h3>UTILISATEUR</h3>
		<table class="table5">
			<tr>
				<td>NOM :</td>
				<td><%=employeur.getRs_employeur()%></td>
			</tr>
			<tr>
				<td>ADRESSE :</td>
				<td><%=employeur.getAdr1()%></td>
			</tr>

			<tr>
				<td>CP VILLE</td>
				<td><%=employeur.getCp()%> <%=employeur.getVille()%></td>
			</tr>
		</table>

		<br>
		<hr>
		<br>


		<form name="form1" method="post" action="/valence/controleur">
			<input type="hidden" name="action" value="saprestationmodifier" />
			<input type="hidden" name="prestation" value="<%=presta%>" /> 
			
			<br>
				
				
				
			<h3>PRESTATION</h3>

			<br>
			<div id="dprest">DATE ENREGISTREMENT :<input type="text" id="dateinscription" name="dateinscription"
					value="<% if(prestation.getRedaction_pr()!=null) out.println(sdf.format(prestation.getRedaction_pr())); %>"></div>
					
			<table class="table2">
				<tr>
					<td class="un">DEBUT :</td>
					<td><input type="text" class="centre" id="datedebut"
						name="datedebut"
						value="<%if (prestation.getDatedebut_pr() != null)
				out.println(sdf.format(prestation.getDatedebut_pr()));%>"></td>
					<td class="un centre">FIN :</td>
					<td><input type="text" class="centre" id="datefin"
						name="datefin"
						value="<%if (prestation.getDatefin_pr() != null)
				out.println(sdf.format(prestation.getDatefin_pr()));%>"></td>
				</tr>
			</table>


			<table class="table4">
				<tr>
					<td class="un">TACHES :</td>
					<td class="trois"><select name="tachepropose"
						id="tachepropose" onfocus="recupererTacheSAP();"><option><%=prestation.getTache().getLibelle()%></option>
					</select></td>
					<td class="un">HRS MINI HEBDO</td>
					<td class="trois"><input type="text" id="dureehebdo"
						name="dureehebdo" value="<%=prestation.getHeuresminimois_pr()%>"></td>
				</tr>
			</table>
			<p>
				COMMENTAIRES: <input type="text"  id="comm" name="comm" value="<%=prestation.getCommentaire_pr() %>" >
			</p>	
			<br>
			
			<br>
			<hr>
			<br>
			<h3>SALARIE AFFECTE</h3>
			<table class="table1">
				<tr>
					<td>NOM :</td>
					<td><input type="text"
					autocomplete="off" name="personne" id="personne" 
					onblur="verifnom();"
					value="<% if (une != null)
				out.println(une.getNom_IDE() + " "
						+ une.getPrenom_IDE()+" ("+une.getId_IDE()+")");%>" /></td>
					</tr>
					<!-- 
					<tr>
					<td>Code Postal: </td><td><%=une.getCp_IDE() %></td>
					<td>Ville :</td><td><%=une.getVille_IDE() %></td>
					</tr>
					-->
					</table>
					<br>
					<br>
					<hr>
					<br>
					<h3>PAIEMENT ET FACTURATION</h3>
			<table class="table3">
				<tr>
					<th></th>
					<th>PAIEMENT</th>
					<th>FACTURATION</th>
				</tr>
				<tr>
					<td class="un">SALAIRE HOR BRUT</td>
					<td class="centre blanc"><input type="text" class="centre gras" id="salhor"
					name="salhor" value="<%=prestation.getSalairehor_pr() %>" autocomplete="off"/></td>

					<td><input type="text" class="centre" id="facsalhor"
						name="facsalhor" value="<%=prestation.getFacsalairehor_pr() %>"  autocomplete="off"></td>
				</tr>
				<tr>
					<td class="un">PANIERS</td>
					<td class="centre blanc"><input type="text" class="centre gras"id="panier"
					name="panier" value="<%=prestation.getPanier_pr() %>"  autocomplete="off"/></td>

					<td></td>
				</tr>
				<tr>
					<td class="un">DEPLACEMENTS</td>
					<td class="centre blanc"><input type="text" class="centre gras"id="deplacement"
					name="deplacement" value="<%=prestation.getDeplacement_pr() %>" autocomplete="off"/></td>

					<td></td>
				</tr>

			</table>
			<br>
			

			<br>
			<hr>
			<br>

			<h3>DATE RETOUR CONTRAT PRESTATION</h3>
			<br>
			<input type="text" class="posrelance" name="relance" value="<% if (prestation.getDaterenouvel() != null)out.println(sdf.format(prestation.getDaterenouvel())); %>" id="relance" >
				
			
			<br> <br> <br> <input type="button"  id="droite"
				value="Modifier" onclick="valider(form1);" />
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
			src="/valence/javascript/jquery.mask.min.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/scripts/formatageMask.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/scripts/dwrfonctionsap.js"></script>

		<script type="text/javascript"
			src="/valence/javascript/jquery.ui.autocomplete.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/scripts/autocompletesap.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>

		<!-- on efface le salarie si on clique dessus -->
		<script type="text/javascript">
			$(document).ready(function() {
				$("#personne").on("focus", function() {
					$("#personne").val("");
					/*$("#salhor").val("");*/

				});

			});
			
		</script>

	

</body>
</html>