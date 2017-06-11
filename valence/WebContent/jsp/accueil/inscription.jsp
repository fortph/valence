<%@ page import="java.util.*, java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/valence/css/inscription.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inscription</title>
<script type="text/javascript"
	src="/valence/javascript/scripts/attention.js"></script>	
<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		
		//var codep = document.getElementById("cp").value;
		//var sexe = document.getElementById("sexe").value;
		
		var vcourriel = document.getElementById("mail").value;
		var naiss = document.getElementById("naissance").value;
		var nom = document.getElementById("nom").value;
		var prenom = document.getElementById("prenom").value;
		//var adresse = document.getElementById("adresse1").value;
		//var nationalite=document.getElementById("nationalite").value;
		//var paysnaissance=document.getElementById("paysnaissance").value;
		var datechapeau= document.getElementById("datechapeau").value;
		var datesocle= document.getElementById("datesocle").value;
		var expirerth= document.getElementById("expirerth").value;
		
		
		if (nom == "") {
			document.form1.nom.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le nom doit être renseigné  !!!!");
			return false;
		}
		if (prenom == "") {
			document.form1.prenom.focus();
			// Afficher un message d'erreur
			attentionComplet("Le prénom doit être renseigné  !!!!");
			return false;
		}
		/*
		if(nationalite==""){
			document.form1.nationalite.focus();
			// Afficher un message d'erreur
			attentionComplet("La nationalité doit être renseignée  !!!!");
			return false;
		}
		
		if(paysnaissance==""){
			document.form1.paysnaissance.focus();
			// Afficher un message d'erreur
			attentionComplet("Le pays de naissance doit être renseigné  !!!!");
			return false;
		}
		if (adresse == "") {
			document.form1.adresse1.focus();
			// Afficher un message d'erreur
			attentionComplet("L'adresse doit être renseignée  !!!!");
			return false;

		}
		
		//test du code postal
		var ex1 = new RegExp(
				"^((0[1-9])|([1-8][0-9])|(9[0-8])|(2A)|(2B))[0-9]{3}$");
		if(codep.length >2){
		if (!ex1.test(codep)) {
			document.form1.cp.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le code postal est incorrect  !!!!");
			return false;
		}
	}
*/
		if (naiss == "") {
			document.form1.naissance.focus();
			attentionComplet(" La date de naissance doit être renseignée  !!!!");
			return false;

		}

		
		//test adresse mail 
		var expr = new RegExp("^[a-zA-Z0-9._-]+@[a-z0-9.-]{2,}[.][a-z]{2,4}$");
		if (vcourriel != "" & !expr.test(vcourriel)) { // Placer le focus dans la zone courriel
			document.form1.mail.focus();
			// Afficher un message d'erreur
			attentionComplet("  Le courriel doit avoir un format correct : xxxxx@xxxxx.xx  !!!!");
			// Fin de la fonction -> Pas de submit
			return false;

		}
		//test dates
		var datetest=new RegExp("^[0-9]{2}([/-])[0-9]{2}([/-])[0-9]{4}$");
		if (expirerth != "" & !datetest.test(expirerth)) { // Placer le focus dans la zone courriel
			document.form1.expirerth.focus();
			// Afficher un message d'erreur
			attentionComplet("  La date RTH doit avoir un format correct : xx-xx-xxxx ");
			// Fin de la fonction -> Pas de submit
			return false;

		}
		if (datesocle != "" & !datetest.test(datesocle)) { // Placer le focus dans la zone courriel
			document.form1.datesocle.focus();
			// Afficher un message d'erreur
			attentionComplet("  La date RSA SOCLE doit avoir un format correct : xx-xx-xxxx ");
			// Fin de la fonction -> Pas de submit
			return false;

		}if (datechapeau != "" & !datetest.test(datechapeau)) { // Placer le focus dans la zone courriel
			document.form1.datechapeau.focus();
			// Afficher un message d'erreur
			attentionComplet("  La date RSA CHAPEAU doit avoir un format correct : xx-xx-xxxx ");
			// Fin de la fonction -> Pas de submit
			return false;

		}
		
		
		
		formulaire.submit();
		return true;
	}
</script>

<!-- script qui permet de griser la date civis si civis n'est pas coché -->
<script type="text/javascript">
	function griser(cib, sour) {
		var cible = document.getElementById(cib);
		var source = document.getElementById(sour);
		if (source.value == '') {
			cible.visibility = "hidden";
			document.getElementById("champdexpire").visibility = "hidden";
			cible.style.display = "none";
			document.getElementById("champdexpire").style.display = "none";
			document.getElementById("mobile").focus();
		} else {
			document.getElementById("champdexpire").visibility = "visible";
			document.getElementById("champdexpire").style.display = "block";
			cible.visibility = "visible";
			cible.style.display = "block";

		}

	}
	function griserbis(cib, sour) {

		var cible = document.getElementById(cib);
		var source = document.getElementById(sour);

		if (source.checked == true) {
			cible.style.display = "block";
		} else {
			cible.style.display = "none";
			cible.value = "";
		}
	}

	function griserter(cib, sour) {
		var cible = document.getElementById(cib);
		var source = document.getElementById(sour);
		if (source.value == '') {wr.engine._debug(message="Error: undefined, Error", stacktrace=true)
			cible.visibility = "hidden";
			document.getElementById("phonelocalisation").visibility = "hidden";
			cible.style.display = "none";
			document.getElementById("phonelocalisation").display = "none";
			document.getElementById("mail").focus();
		} else {
			cible.visibility = "visible";
			document.getElementById("phonelocalisation").visibility = "visible";
			cible.style.display = "block";
			document.getElementById("phonelocalisation").display = "block";
			document.getElementById("phonelocalisation").focus();

		}

	}
	function donnerFocus(champ) {
		document.getElementById(champ).focus();

	}
</script>


<script type="text/javascript" src="/valence/dwr/engine.js"></script>
<script type="text/javascript" src="/valence/dwr/util.js"></script>

<script type="text/javascript"
	src="/valence/dwr/interface/NiveauFormation.js"></script>
<script type="text/javascript"
	src="/valence/dwr/interface/SituationFamiliale.js"></script>
<script type="text/javascript"
	src="/valence/dwr/interface/CodesPostaux.js"></script>
<script type="text/javascript" src="/valence/dwr/interface/Origine.js"></script>
<script type="text/javascript" src="/valence/dwr/interface/Sexe.js"></script>
<script type="text/javascript"
	src="/valence/dwr/interface/Nationalite.js"></script>
<script type="text/javascript" src="/valence/dwr/interface/Rome.js"></script>


</head>
<!-- une fois inscrit, on enregistre la personne en base et on affiche la saisie -->
<body>
	<div id="body">
	<p><%@ include file='/menus/menugeneral/menu.html' %></p><br>
		<div id="creation">CREATION FICHE ACCUEIL</div>
		<br>
		
		<form method="post" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="savepersonnebase" />
			<center>
				<label for="dateinscription">Date accueil:</label> <input
					type="text" id="dateinscription" name="dateinscription" />
			</center>
			<hr>

<h3>ETAT CIVIL</h3>
			<table id="table1">

				<!-- Etat Civil -->
				
				<tr>
					<td><label for="nom">NOM</label></td>
					<td><label for="prenom">PRENOM</label></td>
					<td><label for="nomjf">NOM DE JEUNE FILLE</label></td>
				</tr>
				<tr>
					<td><input type="text" id="nom" name="nom"
						onblur="afficherSexe();recupererSF();recupererEmploiRecherche1();" /></td>
					<td><input type="text" id="prenom" name="prenom" /></td>
					<td><input type="text" id="nomjf" name="nomjf" /></td>
				</tr>
				<tr>
					<td><label for="adresse1">ADRESSE</label></td>
				</tr>
				<tr>
					<td colspan=3><input type="text" id="adresse1" name="adresse1" /></td>
				</tr>
				<tr>
					<td colspan=3><input type="text" name="adresse2" /></td>
				</tr>
			</table>
						
			<table class="table2">
				<tr>
					<td><label for="cp">CODE POSTAL :</label></td>
					<td><input type="text" id="cp" name="cp" value="82"
						onblur="recupererSaisie();" /></td>
					<td><label for="ville">VILLE :</label></td>
					<td colspan=2>
						<!-- affichage des villes correspondant au code postal --> <select
						id="ville" name="ville" onfocus="recupererSaisie();">
					</select>
					</td>
				</tr>
			</table>


			<table class="table2">
				<tr>
					<td><label for="naissance">DATE de NAISSANCE</label></td>
					<td><label for="lieunaissance">LIEU de NAISSANCE</label></td>
					<td><label for="paysnaissance">PAYS de NAISSANCE</label></td>
					<td><label for="departement">DEPT de NAISSANCE</label></td>
				</tr>
				<tr>
					<td><input type="text" name="naissance" id="naissance"
						onchange="donnerFocus('lieunaissance');"
						onblur="donnerFocus('lieunaissance');" /></td>
					<td><input type="text" id="lieunaissance" name="lieunaissance" /></td>
					<td><select id="paysnaissance" name="paysnaissance"
						onfocus="afficherOrigine();">
							
					</select></td>
					<td><input type="text" id="departement" name="departement" /></td>
				</tr>
				<tr>
					<td>NATIONALITE</td>
					<td>SEXE</td>
					<td>SITUATION FAMILIALE</td>
					<td><label for="enfants">ENFANTS</label></td>
				</tr>
				<tr>
					<td><select name="nationalite" id="nationalite"
						onfocus="afficherNationalite();">
							
					</select></td>
					<td><select name="sexe" id="sexe" onfocus="afficherSexe();"><option>&nbsp;</option></select></td>
					<td><select name="situationfamiliale" id="situationfamiliale"
					onfocus="recupererSF();" >
					</select></td>
					<td><input type="text" id="enfants" name="enfants" value="0"></td>
				</tr>
			</table>
			<br>
			<hr>
			<br>
<h3>SOCIAL</h3>
			<!--  Social -->
			<table class="table2">
				
				<tr>
					<td><label for="securitesociale"> SECURITE SOCIALE</label></td>
					<td><input type="text" id="securitesociale"
						name="securitesociale" /></td>
					<td><label for="cartesejour"> CARTE SEJOUR : </label></td>
					<td><input type="text" id="cartesejour" name="cartesejour"
						onchange="griser('dateexpiration','cartesejour');"
						onblur="griser('dateexpiration','cartesejour');" /></td>
					<td><label id="champdexpire"> DATE EXPIRATION :</label></td>
					<td><input type="text" id="dateexpiration"
						name="dateexpiration" /></td>
				</tr>

			</table>
			<br>
			<hr>
<br>
<h3>CONTACT</h3>
			<!--  Telephone -->
			<table class="table2">
				
				<tr>
					<td><label for="mobile">MOBILE</label></td>
					<td><label for="fixe">TELEPHONE FIXE</label></td>
					<td><label for="autrephone">AUTRE TELEPHONE</label></td>
					<td><label for="phonelocalisation">LOCALISATION</label></td>
				</tr>
				<tr>
					<td><input type="text" id="mobile" name="mobile" /></td>
					<td><input type="text" id="fixe" name="fixe" /></td>
					<td><input type="text" id="autrephone" name="autrephone"
						onchange="griserter('phonelocalisation','autrephone');"
						onblur="griserter('phonelocalisation','autrephone');" /></td>
					<td><input type="text" id="phonelocalisation"
						name="phonelocalisation" /></td>
				</tr>
				<tr>
					<td><label for="mail">ADRESSE MAIL :</label></td>
					<td colspan="2"><input type="text" id="mail" name="mail" /></td>
				</tr>

			</table>
			<br>
			<hr>
<br>
			<!--  LOCOMOTION -->
			<h3>LOCOMOTION</h3>
			<div class="centre">MOYENS</div>
			<div id="casesacocher">
				VELO <input type="checkbox" name="loco" value="VELO" /> MOTO <input
					type="checkbox" name="loco" value="MOTO" /> VOITURE <input
					type="checkbox" name="loco" value="VOITURE" />
			</div>
			<br />
			<div class="centre">TITULAIRE DU PERMIS B :</div>
			<div class="centre" id="permisb">
				<input type="radio" class="permisb" name="permisb" value="oui"
					checked="checked" /><label> OUI</label> <input type="radio"
					class="permisb" name="permisb" value="non" /><label> NON</label>
			</div>
			<br>
			<hr>
<br>

			<!--  PRIORITE -->
			<h3>PRIORITE</h3>
			<div id="casesacocher">
				AAH <input type="checkbox" name="prio" value="AAH" />
				 ARE <input	type="checkbox" name="prio" value="ARE" />
				  ASS <input type="checkbox" name="prio" value="ASS" />
				   SUIVI JUDICIAIRE <input	type="checkbox" name="prio" value="EXDET" />
				    AIDE-SOCIALE <input	type="checkbox" name="prio" value="AIDE-SOCIALE" /><br>
				SALARIE <input type="checkbox" name="prio" value="SALARIE" /><!-- 				INTERIMAIRE <input type="checkbox" name="prio" value="INTERIMAIRE" />INDEPENDANT
				<input type="checkbox" name="prio" value="INDEPENDANT" /> -->
				ETUDIANT<input type="checkbox" name="prio" value="ETUDIANT" />
				 RETRAITE <input type="checkbox" name="prio" value="RETRAITE" /> <br> CIVIS
				Classique <input type="checkbox" name="prio" value="CIVIS-CLASSIQUE" />
				CIVIS Renforcé <input type="checkbox" name="prio"
					value="CIVIS-RENFORCE" /><br />

				<table id="tableprio">
					<tr>
						<td>RTH <input type="checkbox" name="prio" id="RTH"
							value="RTH" onclick="griserbis('expirerth','RTH');return true;" />
						</td>
						<td><em>DATE EXPIRATION RTH: </em></td>
						<td><input type="text" id="expirerth" name="expirerth"
							class="cache" /></td>
					</tr>
					<tr>
						<td>RSA SOCLE <input type="checkbox" id="RSA-SOCLE"
							name="prio" value="RSA-SOCLE"
							onclick="griserbis('datesocle','RSA-SOCLE');return true;" />
						</td>
						<td><em> DEPUIS LE: </em></td>
						<td><input type="text" id="datesocle" name="datesocle"
							class="cache" /></td>
					</tr>
					<tr>
						<td>RSA CHAPEAU <input type="checkbox" name="prio"
							id="RSA-CHAPEAU" value="RSA-CHAPEAU"
							onclick="griserbis('datechapeau','RSA-CHAPEAU');return true;" />
						</td>
						<td><em> DEPUIS LE: </em></td>
						<td><input type="text" id="datechapeau" name="datechapeau"
							class="cache" /></td>
					</tr>
				</table>
			</div>
			<br>
			<hr>
<br>

			<!--  POLE EMPLOI-->
			<h3>POLE EMPLOI</h3>
			<table class="table3">
				<tr>
					<td><label for="dateinscriptionanpe">DATE INSCRIPTION
							:</label></td>
					<td><input type="text" name="dateinscriptionanpe"
						id="dateinscriptionanpe" /></td>
					<td><label for="numeroidentifiant">NUMERO
							D'IDENTIFIANT :</label></td>
					<td><input type="text" id="numeroidentifiant"
						name="numeroidentifiant" /></td>
				</tr>
			</table>
			<br>
			<hr>
<br>

			<!--  NIVEAU DE FORMATION-->
			<h3>FORMATION et QUALIFICATION</h3>
			<div id="table4">
				<table>
					<tr>
						<td>FORMATION SCOLAIRE :</td>
						<td><select name="niveauformation" id="niveauformation"
							onfocus="recupererNiveau();">
						</select></td>
					</tr>
				</table>
				<br />
				<p>
					<u>PERMIS PROFESSIONNEL</u>
				</p>
				<table id="permispro">
					<tr>
						<td align="center"><em>NOM PERMIS</em></td>
						<td><em>DATE EXPIRATION</em></td>
					</tr>
					<tr>
						<td align="center"><label>C</label></td>
						<td><input type="text" name="expirationC" id="expirationC" /></td>
					</tr>
					<tr>
						<td align="center"><label>D</label></td>
						<td><input type="text" name="expirationD" id="expirationD" /></td>
					</tr>
					<tr>
						<td align="center"><label>EC</label></td>
						<td><input type="text" name="expirationEC" id="expirationEC" /></td>
					</tr>
					<tr>
						<td align="center"><label>Cariste</label></td>
						<td><input type="text" name="expirationCariste"
							id="expirationCariste" /></td>
					</tr>
					<tr>
						<td align="center"><label>Caces</label></td>
						<td><input type="text" name="expirationCaces"
							id="expirationCaces" /></td>
					</tr>
					<tr>
						<td align="center"><label>Fimo</label></td>
						<td><input type="text" name="expirationFimo"
							id="expirationFimo" /></td>
					</tr>
					<tr>
						<td align="center"><label>Fcos</label></td>
						<td><input type="text" name="expirationFcos"
							id="expirationFcos" /></td>
					</tr>
					<tr>
						<td align="center"><label>APTH</label></td>
						<td><input type="text" name="expirationApth"
							id="expirationApth" /></td>
					</tr>


					<!--  <tr><td>
					FORMATION PYRAMIDE :</td><td>
					<!-- liste déroulante pour récuperer les formations pyramide dans BdD
					plusieurs choix possibles -->
					<!-- <select id="formationpyramide" name="formationpyramide" >
					
					</select></td><td><button  onclick=";" >Accès</button></td></tr>-->

				</table>
			</div>
			<br>
			<hr>
<br>

			<!--  DIPLOMES-->
			<h3>DIPLOMES</h3>
			<table id="table5">
				<tr>
					<td width=70%>Diplome</td>
					<td width=10%>Obtenu</td>
					<td>ANNEE</td>
				</tr>
				<tr>
					<td><input type="text" name="nomdiplome1" /></td>
					<td><select name="diplomeobtenu1">
							<option value="non">NON</option>
							<option value="oui">OUI</option>
					</select></td>
					<td><input type="text" name="anneeobtention1" /></td>
				</tr>
				<tr>
					<td><input type="text" name="nomdiplome2" /></td>
					<td><select name="diplomeobtenu2">
							<option value="non">NON</option>
							<option value="oui">OUI</option>
					</select></td>
					<td><input type="text" name="anneeobtention2" /></td>
				</tr>
				<tr>
					<td><input type="text" name="nomdiplome3" /></td>
					<td><select name="diplomeobtenu3">
							<option value="non">NON</option>
							<option value="oui">OUI</option>
					</select></td>
					<td><input type="text" name="anneeobtention3" /></td>
				</tr>
				<tr>
					<td><input type="text" name="nomdiplome4" /></td>
					<td><select name="diplomeobtenu4">
							<option value="non">NON</option>
							<option value="oui">OUI</option>
					</select></td>
					<td><input type="text" name="anneeobtention4" /></td>
				</tr>
				<tr>
					<td><input type="text" name="nomdiplome5" /></td>
					<td><select name="diplomeobtenu5">
							<option value="non">NON</option>
							<option value="oui">OUI</option>
					</select></td>
					<td><input type="text" name="anneeobtention5" /></td>
				</tr>

			</table>
			<br>
			<hr>
<br>

			<!--  EMPLOI RECHERCHE-->
			<h3>EMPLOI RECHERCHE</h3>
			<div id="emploirecherche">
				<select name="emploirecherche1" id="emploirecherche1"
					onfocus="recupererEmploiRecherche1();">
					<option value="" selected="selected"></option>
				</select><br> <select name="emploirecherche2" id="emploirecherche2"
					onfocus="recupererEmploiRecherche2();">
					<option value="" selected="selected"></option>
				</select><br> <select name="emploirecherche3" id="emploirecherche3"
					onfocus="recupererEmploiRecherche3();">
					<option value="" selected="selected"></option>
				</select> <br> <select name="emploirecherche4" id="emploirecherche4"
					onfocus="recupererEmploiRecherche4();">
					<option value="" selected="selected"></option>
				</select><br> <select name="emploirecherche5" id="emploirecherche5"
					onfocus="recupererEmploiRecherche5();">
					<option value="" selected="selected"></option>
				</select>
			</div>
			<br>
			<hr>
			<br>
			<div id="fin">
				<input type="button" value="Ajouter" class="boutonvert" onclick="valider(form1);" /><input
					type="reset" class="boutonrouge" value="Effacer" />
			</div>
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
		<script type="text/javascript" src="/valence/javascript/scripts/journouveaux.js"></script>
	
	<script type="text/javascript" src="/valence/javascript/jquery.mask.min.js"></script>
	<script type="text/javascript" src="/valence/javascript/scripts/formatageMask.js"></script>
	
	
	<script type="text/javascript" src="/valence/javascript/scripts/journouveaux.js"></script>
	<script type="text/javascript" src="/valence/javascript/scripts/jour.js"></script>
	<script type="text/javascript"
		src="/valence/javascript/scripts/instructions.js"></script>
</body>
</html>