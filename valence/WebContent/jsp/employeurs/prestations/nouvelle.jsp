<%@ page
	import="dao.imp.identite.*,beans.employeurs.*,beans.parametres.accueil.*,java.util.Date,
	dao.imp.employeur.*, beans.identite.*,java.text.*,dao.imp.sap.*,beans.sap.*,java.util.*,beans.smic.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String numero = request.getParameter("numero");
	EmployeurDAO empdao = new EmployeurDAO();
	Employeur une = empdao.findByID(Integer.parseInt(numero));
	String personne = request.getParameter("personne");
	IdentiteDAO idao = new IdentiteDAO();
	Identite identite = null;
	String nom = "";
	ContratCDIDAO contdao = new ContratCDIDAO();
	AvenantCDIDAO avdao=new AvenantCDIDAO();
	float salaire = 0.00f;
	if (personne != null) {
		int n = personne.lastIndexOf("(");
		personne = personne.substring(n, personne.length() - 1);
		//System.out.println("personne=" + personne);
		identite = idao.findByID(Integer.parseInt(personne));		
		ContratCDI contrat = contdao.dernierContrat(identite.getId_IDE());		
		AvenantCDI avenant=avdao.dernierAvenantContratCDI(contrat);
		/*if(avenant!=null)
			salaire = avenant.getSalairehoraire();
		else
			salaire=contrat.getSalairehoraire();*/
	}
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
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
<script type="text/javascript"
	src="/valence/dwr/interface/Identite.js"></script>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>nouvelle prestation sap</title>


<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>
			
			
<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		
		
		
		var personne = document.getElementById("personne").value;
		var datedebut = document.getElementById("datedebut").value;
		var datefin = document.getElementById("datefin").value;
		var tachepropose = document.getElementById("tachepropose").value;
		var dureehebdo = document.getElementById("dureehebdo").value;
		//var urssaf = document.getElementById("urssaf").value;
		
		
		var salhor = document.getElementById("salhor").value;
		var panier = document.getElementById("panier").value;
		var deplacement = document.getElementById("deplacement").value;
		
		var facsalhor = document.getElementById("facsalhor").value;

		if (personne == "") {
			document.form1.personne.focus();
			// Afficher un message d'erreur
			attentionComplet(" Un salarié doit être sélectionné!!!!");
			return false;
		}
		
		/*if (personne != "" && !ex1.test(personne)) {
			document.form1.personne.focus();
			// Afficher un message d'erreur
			attentionComplet(" Une personne doit être sélectionnée dans la liste ci-dessous  !!!!");
			return false;
		}*/

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
		if (tachepropose == "" || tachepropose ==0) {
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
			attentionComplet(" La refacturation horaire doit être correctement renseignée !!!!");
			return false;
		}

		formulaire.submit();
		return true;
	}
</script>

<script>
function change1(){
	document.form1.datedebut.focus();
	return false;
}
function change2(){
	document.form1.datefin.focus();
	return false;
}
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
<body>




	<div id="body">
		<p><%@ include file='/menus/employeur/menuprestation.jsp'%></p>
		<br>
		<div id="creation">CREATION NOUVELLE PRESTATION</div>
		
		<form name="form1" method="post" action="/valence/controleur">
			<input type="hidden" name="action" value="saprestationnouveau" /> <input
				type="hidden" name="emp" value="<%=une.getId_employeur()%>" />
				<input	type="hidden" id="heuresmini" name="heuresmini" />
				 <br>
				
				<center><label for="dateinscription">DATE ENREGISTREMENT:</label> <input
					type="text" id="dateinscription" name="dateinscription" value="<%=aff%>"/></center>
					<br>
					<hr>
					<br>
			<h3>UTILISATEUR</h3>

			<table class="table1">
				<tr>
					<td class="un">NOM :</td>
					<td><%=une.getRs_employeur()%></td>
				</tr>
				<tr>
					<td class="un">Adresse</td>
					<td><%=une.getAdr1()%></td>
				</tr>
				<tr>
					<td class="un">CP VILLE</td>
					<td><%=une.getCp()%> <%=une.getVille()%></td>
				</tr>
			</table>


			<br>
			<hr>
			<br>

			<h3>SALARIE</h3>

			<p>Salarié proposé (seuls les salariés disposant d'un contrat SAP
				sont affichés...):</p>
			<div id="saisienom">
				Tapez les 3 premières lettre du nom :<input type="text"
					autocomplete="off" name="personne" id="personne" 
					onblur="verifnom();"
					value="<% if (identite != null)
				out.println(identite.getNom_IDE() + " "
						+ identite.getPrenom_IDE());%>" />
			</div>

			<br>
			<hr>
			<br>
			<h3>DESCRIPTIF</h3>
			<table class="table2">
				<tr>
					<td class="un">DEBUT :</td>
					<td><input type="text" class="centre" id="datedebut" autocomplete="off"
						name="datedebut" onfocus="recupererSalaire();" onchange="change1();"></td>
					<td class="un centre">FIN :</td>
					<td><input type="text" class="centre" id="datefin" autocomplete="off"
						name="datefin" onchange="change2();" ></td>
				</tr>
			</table>


			<table class="table4">
				<tr>
					<td class="un">TACHES :</td>
					<td class="trois"><select name="tachepropose"
						id="tachepropose" onfocus="recupererTacheSAP();"></select></td>
					<td class="un">HRS MINI HEBDO</td>
					<td class="trois"><input type="text" id="dureehebdo"
						name="dureehebdo" autocomplete="off" ></td>
				</tr>
			</table>

			<br>
			<table class="table3">
				<tr>
					<th></th>
					<th>PAIEMENT</th>
					<th>FACTURATION</th>
				</tr>
				<tr>
					<td class="un">SALAIRE HOR BRUT</td>
					<td class="centre blanc"><input type="text" class="centre gras"id="salhor"
					name="salhor" value="<%=salaire%>" autocomplete="off"/></td>

					<td><input type="text" class="centre" id="facsalhor"
						name="facsalhor"  autocomplete="off"></td>
				</tr>
				<tr>
					<td class="un">PANIERS</td>
					<td class="centre blanc"><input type="text" class="centre gras"id="panier"
					name="panier" autocomplete="off"/></td>

					<td></td>
				</tr>
				<tr>
					<td class="un">DEPLACEMENTS</td>
					<td class="centre blanc"><input type="text" class="centre gras"id="deplacement"
					name="deplacement" autocomplete="off"/></td>

					<td></td>
				</tr>

			</table>
			<br>
			<p>
				DIVERS: <input type="text" id="comm" name="comm">
			</p>

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


	</div>

</body>
</html>