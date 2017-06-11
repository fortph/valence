<%@ page import="beans.employeurs.*,dao.imp.employeur.*,java.util.Date,java.text.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/valence/css/employeur/offre.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<title>offre employeur</title>

<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>
			
<script type="text/javascript">
/*verifie qu'au moins 1 bouton radio est coché*/
		function getRadioValue()		{   
			var val=null;
			for (var i = 0; i < document.form1.stats.length; i++)
		    {   	if (document.form1.stats[i].checked){
		    		val=document.form1.stats[i].value;
		    			}
		    	
		}return val;
		}
		/*******************************************************************/
</script>


<script type="text/javascript"  >
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		
		var utilisateurs = document.getElementById("utilisateurs").value;
		var contacts = document.getElementById("contacts").value;
		var employeurs = document.getElementById("employeurs").value;
		var emploipropose = document.getElementById("emploipropose").value;
		var nbpersonne = document.getElementById("nbpersonne").value;
		var autre = document.getElementById("autre").checked;
		var ai = document.getElementById("ai").checked;
		var cae = document.getElementById("cae").checked;
		var avenir=document.getElementById("avenir").checked;
		var cdd=document.getElementById("cdd").checked;
		var cdi=document.getElementById("cdi").checked;
		var alternance=document.getElementById("alternance").checked;		
		var datedebutemploi = document.getElementById("datedebutemploi").value;
		var duree = document.getElementById("duree").value;
		
		
		if (utilisateurs == "Aucun" || utilisateurs==0) {
			document.form1.utilisateurs.focus();
			// Afficher un message d'erreur
			attentionComplet("Votre  nom doit être renseigné  !!!!");
			return false;
		}
		if (employeurs == "Aucun") {
			document.form1.employeurs.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le nom de l'employeur doit être renseigné  !!!!");
			return false;
		}
		if (contacts == "Aucun") {
			document.form1.contacts.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le nom du contact doit être renseigné  !!!!");
			return false;
		}
		
		if(emploipropose==""){
			document.form1.emploipropose.focus();
			// Afficher un message d'erreur
			attentionComplet("L'emploi proposé doit être renseigné  !!!!");
			return false;
		}
		if (nbpersonne == "") {
			document.form1.nbpersonne.focus();
			// Afficher un message d'erreur
			attentionComplet("Le nombre de personnes doit être renseigné  !!!!");
			return false;
		}
		if(autre==false && ai==false  && cae==false && avenir==false && cdd==false && cdi==false && alternance==false)
		{document.form1.autre.focus();
		attentionComplet(" Une case doit être cochée dans les types de contrats !!!!");
		return false;
			
		}
		if (datedebutemploi == "") {
			document.form1.datedebutemploi.focus();
			attentionComplet(" La date de début doit être renseignée  !!!!");
			return false;
		}
		
		if (getRadioValue()==null) {
			document.form1.stats[0].focus();
			// Afficher un message d'erreur
			attentionComplet(" Une option doit être cochée pour les statistiques  !!!!");
			return false;
		}
		
	

		formulaire.submit();
		return true;
	}
</script>
<script type="text/javascript">
function verifemployeur(){
	var employeurs = document.getElementById("employeurs").value;
	if(employeurs=="Aucun"){
		document.form1.employeurs.focus();
		// Afficher un message d'erreur
		attentionComplet("Le nom de l'employeur doit être renseigné  avant de saisir le contact!!!!");
		return false;
		
	}
	
}
</script>

<script type="text/javascript" src="/valence/dwr/engine.js"></script>
<script type="text/javascript" src="/valence/dwr/util.js"></script>


<script type="text/javascript"
	src="/valence/dwr/interface/Utilisateur.js"></script>
<script type="text/javascript" src="/valence/dwr/interface/Employeur.js"></script>
<script type="text/javascript" src="/valence/dwr/interface/Contact.js"></script>
<script type="text/javascript" src="/valence/dwr/interface/Rome.js"></script>
<%
	OffreDAO ofdao = new OffreDAO();
	Date jour=new Date();
	SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
	String aff=sdf.format(jour);
%>

</head>
<body>
	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div id="creation">NOUVELLE OFFRE EMPLOYEUR</div>

		<br>
		<form method="post" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="nouvelleoffreemployeur" />
			<input type="hidden" name="numoffre" value=<%=ofdao.numeroOffre()%> />
			<h3>EMPLOYEUR</h3>
			<div class="un">

				<span class="col1">Saisie le :</span> <span class="saisie"><input
					type="text" id="dateinscription" name="dateinscription" value="<%=aff %>"/></span> <span
					class="col1">&nbsp;Par :</span> <span class="saisie"><select
					name="utilisateurs" id="utilisateurs"
					onfocus="afficheCapUtilisateurs();">
						<option>Aucun</option>
				</select></span> <span class="col1">&nbsp;Offre N° :</span> <span class="affiche">OF-<%=ofdao.numeroOffre()%></span>

			</div>
			<br>
			<div class="deux">

				<span class="col1">Employeur :</span> <span class="saisie"><select
					name="employeurs" id="employeurs" onfocus="recupererEmployeur();"><option>Aucun</option></select></span>
			</div>
			<br>
			<div class="deux">

				<span class="col1">Contact:</span> <span class="saisie"><select
					name="contacts" id="contacts" onfocus="verifemployeur();recupererContacts();">
						<option selected="selected">Aucun</option>
				</select></span>


			</div>
			<br>
			<hr>
			<br>


			<h3>OFFRE</h3>
			<div class="trois">
				<span class="col1">Poste proposé :</span> <span class="saisie"><select
					name="emploipropose" id="emploipropose"
					onfocus="recupererEmploiPropose();">
				</select></span> <span class="col1">Nombre de personnes :</span> <span
					class="saisie"><input type="text" name="nbpersonne"
					id="nbpersonne" /></span>
			</div>
			<br>
			<table id="table1">
				<tr>
					<td class="col1">Type de Contrat :</td>
					<td><input type="checkbox" class="ch" name="autre" id="autre"
						value="autre">Autre</td>
					<td><input type="checkbox" class="ch" name="ai" id="ai"
						value="ai">AI</td>
					<td><input type="checkbox" class="ch" name="cae" id="cae"
						value="cae">CAE</td>
					<td><input type="checkbox" class="ch" name="avenir"
						id="avenir" value="avenir">AVENIR</td>
					<td><input type="checkbox" class="ch" name="cdd" id="cdd"
						value="cdd">CDD</td>
					<td><input type="checkbox" class="ch" name="cdi" id="cdi"
						value="cdi">CDI</td>
					<td><input type="checkbox" class="ch" name="alternance"
						id="alternance" value="alternance">Alternance</td>
				</tr>

			</table>
			<br>
			<div class="quatre">
				<span class="col1">Date Début :</span> <span class="saisie"><input
					type="text" name="datedebutemploi" id="datedebutemploi"> </span> <span
					class="col1">Durée :</span> <span class="saisie"><input
					type="text" name="duree" id="duree" /></span>
			</div>


			<br>
			<table id="table2">
				<tr>
					<td class="col1">Durée stats :</td>
					<td><input type="radio" class="cl1" name="stats" value="1semaine" > -1
						semaine</td>
					<td><input type="radio" class="cl1" name="stats" value="1mois" > -1
						mois</td>
					<td><input type="radio" class="cl1" name="stats" value="-6mois" > de 1
						à 6 mois</td>
					<td><input type="radio" class="cl1" name="stats" value="+6mois" > de 6
						mois à 1 an</td>
					<td><input type="radio" class="cl1" name="stats" value="1an" > + 1
						an</td>

				</tr>

			</table>
			<br>
			<div class="cinq">

				<span class="col2">JOURS de travail proposés :</span> <span
					class="saisie"> <input type="text" name="jourstravail" /></span>
			</div>
			<br>
			<div class="cinq">

				<span class="col2">HEURES de travail proposées :</span> <span
					class="saisie"> <input type="text" name="heurestravail" /></span>
			</div>
			<br>
			<p class="col1">Détail du poste</p>
<textarea class="col3" rows="5" name="detailposte" >
</textarea>
			<br> <br>
			<p class="col1">OBSERVATIONS</p>
<textarea class="col3" rows="5" name="obs" >
</textarea>
			<br> <br> <input type="button" id="droite" class="boutonvert" value="Ajouter"
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
			src="/valence/javascript/jquery.mask.min.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/scripts/formatageMask.js"></script>

		<script type="text/javascript"
			src="/valence/javascript/scripts/journouveaux.js"></script>
		<script type="text/javascript" 
			src="/valence/javascript/scripts/dwrfonctionsemployeurs.js"></script>

	</div>
</body>
</html>