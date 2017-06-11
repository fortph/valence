<%@ page
	import="dao.imp.identite.*,beans.parametres.accueil.*, beans.identite.*,java.text.*,java.util.Date,
	dao.imp.sap.*,beans.sap.*,java.util.*,beans.smic.*,dao.imp.employeur.*,beans.employeurs.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	String idprestation = request.getParameter("numcontrat");

	PrestationCDIDAO contdao = new PrestationCDIDAO();
	PrestationCDI prestation = contdao.findByID(Integer
			.parseInt(idprestation));
	AvenantPrestationCDIDAO avndao=new AvenantPrestationCDIDAO();
	java.sql.Date debutavenant=avndao.dernierAvenantPrestationCDI(prestation);
	//on rajoute 1 jour a la date de fin du dernier avenant
	java.util.Date debutav=new java.util.Date(debutavenant.getTime());
	debutav=new FormaterDate().lendemain(debutav);
	debutavenant= new java.sql.Date(debutav.getTime());
	
	EmployeurDAO empdao = new EmployeurDAO();
	Employeur employeur = empdao.findByID(prestation.getEmployeur()
			.getId_employeur());
	IdentiteDAO idao = new IdentiteDAO();
	Identite une = idao.findByID(prestation.getIdentite().getId_IDE());
	
	Date jour=new Date();
	String aff=sdf.format(jour);
%>
<link rel="stylesheet" href="/valence/css/employeur/prestation.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />

<script type="text/javascript" src="/valence/dwr/engine.js"></script>
<script type="text/javascript" src="/valence/dwr/util.js"></script>
<script type="text/javascript" src="/valence/dwr/interface/TachesSAP.js"></script>
<script type="text/javascript"
	src="/valence/dwr/interface/ContratCDI.js"></script>
 <script type="text/javascript"
	src="/valence/dwr/interface/AvenantCDI.js"></script>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>avenant prestation sap</title>


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
		var facsalhor = document.getElementById("facsalhor").value;
		var panier = document.getElementById("panier").value;
		var deplacement = document.getElementById("deplacement").value;
		

		if (datedebut == "") {
			document.form1.datedebut.focus();
			// Afficher un message d'erreur
			attentionComplet(" La date un début de prestation doit être renseignée !!!!");
			return false;
		}

		if (datefin == "") {
			document.form1.datefin.focus();
			// Afficher un message d'erreur
			attentionComplet("La date de fin de prestation doit être renseignée !!!!");
			return false;
		}
		if (tachepropose == "") {
			document.form1.tachepropose.focus();
			// Afficher un message d'erreur
			attentionComplet("L'emploi doit être indiqué !!!!");
			return false;

		}

		if (dureehebdo == "" || isNaN(dureehebdo)) {
			document.form1.dureehebdo.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le nombre d'heures doit être correctement renseigné !!!!");
			return false;
		}

		 if(salhor=="" || isNaN(salhor)){
			 document.form1.salhor.focus();
			 attentionComplet(" Le salaire horaire doit être correctement renseigné !!!!");
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

		if (facsalhor == "" || isNaN(facsalhor)) {
			document.form1.facsalhor.focus();
			// Afficher un message d'erreur
			attentionComplet(" La refacturation horaire doit être correctement renseigné !!!!");
			return false;
		}

		formulaire.submit();
		return true;
	}
</script>

</head>
<body onload="recupererSalaire1(<%=une.getId_IDE()%>);">




	<div id="body">
		<p><%@ include file='/menus/employeur/menuprestationavenant.jsp'%></p>
		<br>
		<div id="creation">
			CREATION NOUVEL AVENANT A LA PRESTATION P-<%=idprestation%></div>
		<br> 
		
		<form name="form1" method="post" action="/valence/controleur">
			<input type="hidden" name="action" value="sapavenantprestation" /> <input
				type="hidden" name="prestation" value="<%=idprestation%>" />
				<input	type="hidden" id="heuresmini" name="heuresmini" /> <br>
		
		<center><label for="dateinscription">DATE ENREGISTREMENT:</label> <input
					type="text" id="dateinscription" name="dateinscription" value="<%=aff%>"/></center>
					<br>

		<h3>UTILISATEUR</h3>
		<br>

		<table class="table1">
			<tr>
				<td class="un">NOM :</td>
				<td><%=employeur.getRs_employeur()%></td>
			</tr>
			<tr>
				<td class="un">Adresse</td>
				<td><%=employeur.getAdr1()%></td>
			</tr>
			<tr>
				<td class="un">CP VILLE</td>
				<td><%=employeur.getCp()%> <%=employeur.getVille()%></td>
			</tr>
		</table>

		

			<br>
			<hr>
			<br>
			<h3>PRESTATION</h3>

			<br>

			<table class="table2">
				<tr>
					<td class="un">DEBUT :</td>
					<td><input type="text" class="centre" id="datedebut"
						name="datedebut" value="<%=sdf.format(debutavenant) %>"
						></td>
					<td class="un centre">FIN :</td>
					<td><input type="text" class="centre" id="datefin"
						name="datefin"
						></td>
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

			<br>
			<hr>
			<br>
			
			<h3>SALARIE AFFECTE</h3>
			<table class="table1">
				<tr>
					<td>NOM :</td>
					<td><%=une.getNom_IDE() %> <%=une.getPrenom_IDE() %></td>
					</tr>
					<tr>
					<td>Code Postal: </td><td><%=une.getCp_IDE() %></td>
					<td>Ville :</td><td><%=une.getVille_IDE() %></td>
					</tr>
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
					<td class="centre blanc"><input type="text" class="centre gras"id="salhor"
					name="salhor"  autocomplete="off"/></td>

					<td><input type="text" class="centre" id="facsalhor"
						name="facsalhor"  autocomplete="off"></td>
				</tr>
				<tr>
					<td class="un">PANIERS</td>
					<td class="centre blanc"><input type="text" class="centre gras"id="panier"
					name="panier"  autocomplete="off"/></td>

					<td></td>
				</tr>
				<tr>
					<td class="un">DEPLACEMENTS</td>
					<td class="centre blanc"><input type="text" class="centre gras"id="deplacement"
					name="deplacement"  autocomplete="off"/></td>

					<td></td>
				</tr>

			</table>
			<br>
			<p>
				DIVERS: <input type="text" id="comm" name="comm" value="<%=prestation.getCommentaire_pr()  %>">
			</p>

			<br>
			<hr>

			<br> <br> <input type="button" id="droite"
				value="Enregistrer" onclick="valider(form1);" />
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

					
			
		<!-- on efface le salarie si on clique dessus -->
		<script type="text/javascript">
			$(document).ready(function() {
				$("#personne").on("focus", function() {
					$("#personne").val("");
					$("#salhor").val("");

				});

			});
		</script>


	</div>

</body>
</html>