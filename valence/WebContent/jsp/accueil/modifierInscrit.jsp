<%@ page
	import="java.util.*, java.sql.*,dao.imp.identite.*, beans.identite.*,java.text.*,divers.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/valence/css/inscription.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Modification fiche</title>

<script type="text/javascript"
	src="/valence/javascript/scripts/attention.js"></script>
	
	
<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		var vtelephone1 = document.getElementById("mobile").value;
		var vtelephone2 = document.getElementById("fixe").value;
		var vtelephone3 = document.getElementById("autrephone").value;
		var vcourriel = document.getElementById("mail").value;
		var codep = document.getElementById("cp").value;
		var sexe = document.getElementById("sexe").value;
		var ss = document.getElementById("securitesociale").value;
		var naiss = document.getElementById("naissance").value;
		var nom = document.getElementById("nom").value;
		var prenom = document.getElementById("prenom").value;
		var adresse = document.getElementById("adresse1").value;
		var datechapeau= document.getElementById("datechapeau").value;
		var datesocle= document.getElementById("datesocle").value;
		var expirerth= document.getElementById("expirerth").value;
		if (nom == "") {
			document.form1.nom.focus();
			// Afficher un message d'erreur
			attentionComplet("Le nom doit être renseigné  !!!!");
			return false;
		}
		if (prenom == "") {
			document.form1.prenom.focus();
			// Afficher un message d'erreur
			attentionComplet("Le prénom doit être renseigné  !!!!");
			return false;
		}
/*
		if (adresse == "") {
			document.form1.adresse1.focus();
			// Afficher un message d'erreur
			attentionComplet("L'adresse doit être renseignée  !!!!");
			return false;

		}
		//test du code postal
		var ex1 = new RegExp(
				"^((0[1-9])|([1-8][0-9])|(9[0-8])|(2A)|(2B))[0-9]{3}$");
		if (!ex1.test(codep)) {
			document.form1.cp.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le code postal est incorrect  !!!!");
			return false;
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
			attentionComplet("Le courriel doit avoir un format correct : xxxxx@xxxxx.xx  !!!!");
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
<SCRIPT type="text/javascript">
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
		if (source.value == '') {
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
	
	function ajoutrecherche(cib){
		var cible = document.getElementById(cib);
		if(cible.value=""){
			cible.style.display = "none";
		cible.style.visibility="hidden";
	}
		else{
			cible.style.display = "block";		
			cible.style.visibility="visible";
		}
	
		
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

<%
	SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
	String id = request.getParameter("numero");
	int num = Integer.parseInt(id);
	Identite un = new IdentiteDAO().findByID(num);
	String ss=un.getNss_IDE();
	
	/*la fiche est elle active*/
	boolean actif=un.isStatutfiche_IDE();
	
	//recuperation du permis
		//System.out.println("permis b ="+un.isPermib_IDE());
		boolean permis=un.isPermib_IDE();

	//recuperation des moyens de locomotion
	ProfilLocomotionDAO pldao = new ProfilLocomotionDAO();
	ProfilLocomotion pl = new ProfilLocomotion();
	pl.setId_identite(un.getId_IDE());
	List<ProfilLocomotion> listeLoco = pldao.findByCriteria(pl);
	String auto="",velo="",moto="";
		if(listeLoco.size()>0){
		for(int i=0;i<listeLoco.size();i++){
	if(listeLoco.get(i).getLocomotion().equals("VOITURE"))
		auto="checked";
	else if(listeLoco.get(i).getLocomotion().equals("VELO"))
		velo="checked";
	else if(listeLoco.get(i).getLocomotion().equals("MOTO"))
		moto="checked";
		}
	}
	

	//récuperation des diplomes
	ProfilDiplomeDAO pdao = new ProfilDiplomeDAO();
	ProfilDiplome pd = new ProfilDiplome();
	pd.setId_identite(un.getId_IDE());
	List<ProfilDiplome> listeDip = pdao.findByCriteria(pd);
	

	//récuperation des permis professionnels
	ProfilPermisProDAO pppdao = new ProfilPermisProDAO();	
	List<ProfilPermisPro> ppro = pppdao.listingPermis(un);
	java.util.Date perc=null,perd=null,perec=null,percariste=null,percaces=null,perfimo=null,perfcos=null,perapth=null;
	for(int n=0;n<ppro.size();n++){
	if(ppro.get(n).getLibelle().equals("C"))
		perc=ppro.get(n).getExpiration();
	else if (ppro.get(n).getLibelle().equals("D"))
		perd=ppro.get(n).getExpiration();
	else if (ppro.get(n).getLibelle().equals("E(C)"))
		perec=ppro.get(n).getExpiration();
	else if (ppro.get(n).getLibelle().equals("CARISTE"))
		percariste=ppro.get(n).getExpiration();
	else if (ppro.get(n).getLibelle().equals("CACES"))
		percaces=ppro.get(n).getExpiration();
	else if (ppro.get(n).getLibelle().equals("FIMO"))
		perfimo=ppro.get(n).getExpiration();
	else if (ppro.get(n).getLibelle().equals("FCOS"))
		perfcos=ppro.get(n).getExpiration();
	else if (ppro.get(n).getLibelle().equals("APTH"))
		perapth=ppro.get(n).getExpiration();
}

	

	//récuperation des emplois recherchés
	ProfilRechercheDAO prdao = new ProfilRechercheDAO();
	//ProfilRecherche pr = new ProfilRecherche();
	//pr.setIdentite(un);
	List<ProfilRecherche> listeRech = prdao.listeEmploiParPersonne(un);
	

	//récuperation des priorités
	ProfilPrioriteDAO ppdao = new ProfilPrioriteDAO();
	ProfilPriorite pri = new ProfilPriorite();
	pri.setId_identite(un.getId_IDE());
	List<ProfilPriorite> listePrio = ppdao.findByCriteria(pri);
	String aah="",are="",ass="",exdet="",aide="",sal="",interim="",
	indep="",etud="",ret="",civ="",civ2="",rth="",rsasoc="",rsachap="";//a=null,b=null,c=null;
	java.util.Date a=null,b=null,c=null;
	
	if(listePrio.size()>0){
		for(int i=0;i<listePrio.size();i++){
	if(listePrio.get(i).getLibelle().equals("AAH"))
		aah="checked";
	else if(listePrio.get(i).getLibelle().equals("ARE"))
		are="checked";
	else if(listePrio.get(i).getLibelle().equals("ASS"))
		ass="checked";
	else if(listePrio.get(i).getLibelle().equals("EXDET"))
		exdet="checked";
	else if(listePrio.get(i).getLibelle().equals("AIDE-SOCIALE"))
		aide="checked";
	else if(listePrio.get(i).getLibelle().equals("SALARIE"))
		sal="checked";
	/*
	else if(listePrio.get(i).getLibelle().equals("INTERIMAIRE"))
		interim="checked";
	else if(listePrio.get(i).getLibelle().equals("INDEPENDANT"))
		indep="checked";
	*/
	else if(listePrio.get(i).getLibelle().equals("ETUDIANT"))
		etud="checked";
	else if(listePrio.get(i).getLibelle().equals("RETRAITE"))
		ret="checked";
	else if(listePrio.get(i).getLibelle().equals("CIVIS-CLASSIQUE"))
		civ="checked";
	else if(listePrio.get(i).getLibelle().equals("CIVIS-RENFORCE"))
		civ2="checked";
	else if(listePrio.get(i).getLibelle().equals("RTH")){
		rth="checked";
		a=listePrio.get(i).getExpire();
		}
	else if(listePrio.get(i).getLibelle().equals("RSA-SOCLE")){
		rsasoc="checked";
		b=listePrio.get(i).getExpire();
		}
	else if(listePrio.get(i).getLibelle().equals("RSA-CHAPEAU")){
		rsachap="checked";
		c=listePrio.get(i).getExpire();
	}
	
		}
	}
%>
</head>
<body>
	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div id="creation">MODIFICATION FICHE ACCUEIL</div>
		<br />
		<hr>
		<form method="post"  name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="modifierpersonnebase" /> <input
				type="hidden" name="numero" value=<%=un.getId_IDE()%> />
			<hr>
			<label>Fiche active:</label><input type="radio" name="actif"
				value="oui" <%=actif ? "checked" : ""%> /> <label>OUI</label> <input
				type="radio" name="actif" value="non" <%=actif ? "" : "checked"%> />
			<label>NON</label>
			<center>
				<label>Date accueil:</label> <input type="text"
					name="dateinscription" id="dateinscription"
					value="<% if(un.getDateAccueil_IDE()!=null) out.println(sdf.format(un.getDateAccueil_IDE()));%>" />
			</center>
			<hr>
			<h3>Etat Civil</h3>
			<table id="table1">

				<tr>
					<td>NOM</td>
					<td>PRENOM</td>
					<td>NOM DE JEUNE FILLE</td>
				</tr>
				<tr>
					<td><input type="text" id="nom" name="nom"
						value="<%=un.getNom_IDE()%>" /></td>
					<td><input type="text" id="prenom" name="prenom"
						value="<%=un.getPrenom_IDE()%>" /></td>
					<td><input type="text" name="nomjf"
						value="<%=un.getNomjf_IDE()%>" /></td>
				</tr>
				<tr>
					<td>ADRESSE</td>
				</tr>
				<tr>
					<td colspan=3><input type="text" id="adresse1" name="adresse1"
						value="<%=un.getAdr1_IDE()%>" /></td>
				</tr>

				<tr>
					<td colspan=3><input type="text" name="adresse2"
						value="<%=un.getAdr2_IDE()%>" /></td>
				</tr>
			</table>
			<table class="table2">
				<tr>
					<td>CODE POSTAL :</td>
					<td><input type="text" id="cp" name="cp"
						value=<%=un.getCp_IDE()%> onblur="recupererSaisie();" /></td>
					<td>VILLE :</td>
					<td colspan=2><select id="ville" name="ville"
						onfocus="recupererSaisie();">
							<option selected="selected"><% if(un.getVille_IDE()!=null) out.println(un.getVille_IDE());%></option>
					</select></td>
				</tr>
			</table>


			<table class="table2">
				<tr>
					<td>DATE de NAISSANCE</td>
					<td>LIEU de NAISSANCE</td>
					<td>PAYS de NAISSANCE</td>
					<td>DEPT de NAISSANCE</td>
				</tr>
				<tr>
					<td><input type="text" id="naissance" name="naissance"
						value="<%if(un.getDatenais_IDE()!=null) out.println(sdf.format(un.getDatenais_IDE()));%>" /></td>
					<td><input type="text" name="lieunaissance"
						value="<%=un.getLieunais_IDE()%>" /></td>
					<td><select id="paysnaissance" onfocus="afficherOrigine();"
						name="paysnaissance">
							<option><% if(un.getPaysnais_IDE()!=null) out.println(un.getPaysnais_IDE());%></option>
					</select></td>
					<td><input type="text" name="departement"
						value="<%=un.getDeptnais_IDE()%>" /></td>
				</tr>
				<tr>
					<td>NATIONALITE</td>
					<td>SEXE</td>
					<td>SITUATION FAMILIALE</td>
					<td>ENFANTS</td>
				</tr>
				<tr>
					<td><select name="nationalite" id="nationalite"
						onfocus="afficherNationalite();">
							<option><% if(un.getNationalite_IDE()!=null) out.println(un.getNationalite_IDE());%></option>
					</select></td>

					<td><select id="sexe" name="sexe" onfocus="afficherSexe();">
							<option><%=un.getSexe_IDE()%></option>
					</select></td>

					<td><select name="situationfamiliale" id="situationfamiliale"
						onfocus="recupererSF();">
							<option selected="selected"><% if(un.getSitfam_IDE()!=null) out.println(un.getSitfam_IDE()); %></option>
					</select></td>
					<td><input type="text" id="enfants" name="enfants"
						value="<%=un.getEnfants_IDE()%>"></td>
				</tr>
			</table>
			<hr>
			<br>
			<h3>SOCIAL</h3>
			<!--  Social -->
			<table class="table2">

				<tr>
					<td>SECURITE SOCIALE</td>
					<td><input type="text" id="securitesociale"
						name="securitesociale" value="<% if(ss!=null) out.println(new SecuriteSociale().formaterNumeroSS(ss));%>" /></td>
					<td>CARTE SEJOUR :</td>
					<td><input type="text" name="cartesejour"
						value="<% if(un.getNcs_IDE()!=null) out.println(un.getNcs_IDE());%>" /></td>
					<td>DATE EXPIRATION :</td>
					<td><input type="text" id="dateexpiration"
						name="dateexpiration"
						value="<%if(un.getCs_expiration_IDE()!=null) out.println(sdf.format(un.getCs_expiration_IDE()));%>" /></td>
				</tr>

			</table>
			<hr>
			<br>
			<h3>CONTACT</h3>
			<!--  Telephone -->
			<table class="table2">

				<tr>
					<td>MOBILE</td>
					<td>TELEPHONE FIXE</td>
					<td>AUTRE TELEPHONE</td>
					<td>LOCALISATION</td>
				</tr>
				<tr>
					<td><input type="text" id="mobile" name="mobile"
						value="<%=un.getMobile_IDE()%>" /></td>
					<td><input type="text" id="fixe" name="fixe"
						value="<%=un.getFixe_IDE()%>" /></td>
					<td><input type="text" id="autrephone" name="autrephone"
						value="<%=un.getPhone3_IDE()%>" /></td>
					<td><%=un.getPhone3Localisation_IDE()%></td>
				</tr>
				<tr>
					<td>ADRESSE MAIL :</td>
					<td colspan="2"><input type="text" id="mail" name="mail"
						value="<%=un.getMail_IDE()%>" /></td>
				</tr>

			</table>
			<hr>
			<br>
			<h3>LOCOMOTION</h3>
			<!-- *******************on liste les moyens de locomotion*************************** -->

			<div class="centre">MOYENS</div>
			<div id="casesacocher">

				VELO <input type="checkbox" name="loco" value="VELO" <%=velo%> />
				MOTO <input type="checkbox" name="loco" value="MOTO" <%=moto%> />
				VOITURE <input type="checkbox" name="loco" value="VOITURE" <%=auto%> />



			</div>

			<br />

			<div class="centre">TITULAIRE DU PERMIS B :</div>
			<div class="centre" id="permisb">
				<input type="radio" class="permisb" name="permisb" value="oui"
					<%=permis ? "checked" : ""%> /><label> OUI</label> <input
					type="radio" class="permisb" name="permisb" value="non"
					<%=permis ? "" : "checked"%> /><label> NON</label>
			</div>
			<br>
			<hr>
			<br>
			<h3>PRIORITE</h3>

			<!-- ***********************on liste les  PRIORITEs********************************* -->

			<div id="casesacocher">
				AAH <input type="checkbox" name="prio" value="AAH" <%=aah%> /> ARE
				<input type="checkbox" name="prio" value="ARE" <%=are%> /> ASS <input
					type="checkbox" name="prio" value="ASS" <%=ass%> /> SUIVI
				JUDICIAIRE <input type="checkbox" name="prio" value="EXDET"
					<%=exdet%> /> AIDE-SOCIALE <input type="checkbox" name="prio"
					value="AIDE-SOCIALE" <%=aide%> /><br> SALARIE <input
					type="checkbox" name="prio" value="SALARIE" <%=sal%> />
				<!-- INTERIMAIRE <input type="checkbox"
					name="prio" value="INTERIMAIRE" <%=interim%> />INDEPENDANT <input
					type="checkbox" name="prio" value="INDEPENDANT" <%=indep%> />
					-->
				ETUDIANT <input type="checkbox" name="prio" value="ETUDIANT"
					<%=etud%> /> RETRAITE <input type="checkbox" name="prio"
					value="RETRAITE" <%=ret%> /> <br> CIVIS Classique <input
					type="checkbox" name="prio" value="CIVIS-CLASSIQUE" <%=civ%> />
				CIVIS Renforcé <input type="checkbox" name="prio"
					value="CIVIS-RENFORCE" <%=civ2%> /><br />

				<table id="tableprio">
					<tr>
						<td>RTH <input type="checkbox" name="prio" id="RTH" <%=rth%>
							value="RTH" />
						</td>
						<td><em>DATE EXPIRATION RTH: </em></td>
						<td><input type="text" id="expirerth" name="expirerth"
							value="<%if(a!=null) out.println(sdf.format(a));%>" /></td>
					</tr>
					<tr>
						<td>RSA SOCLE <input type="checkbox" id="RSA-SOCLE"
							name="prio" value="RSA-SOCLE" <%=rsasoc%> />
						</td>
						<td><em> DEPUIS LE: </em></td>
						<td><input type="text" id="datesocle" name="datesocle"
							value="<%if(b!=null) out.println(sdf.format(b));%>" /></td>
					</tr>
					<tr>
						<td>RSA CHAPEAU <input type="checkbox" name="prio"
							id="RSA-CHAPEAU" value="RSA-CHAPEAU" <%=rsachap%> />
						</td>
						<td><em> DEPUIS LE: </em></td>
						<td><input type="text" id="datechapeau" name="datechapeau"
							value="<%if(c!=null) out.println(sdf.format(c));%>" /></td>
					</tr>
				</table>
			</div>
			<br>
			<hr>
			<br>
			<h3>POLE EMPLOI</h3>

			<!--  POLE EMPLOI-->

			<table class="table3">
				<tr>
					<td>DATE INSCRIPTION :</td>
					<td><input type="text" name="dateinscriptionanpe"
						id="dateinscriptionanpe"
						value="<%if(un.getPoleEmploiInscripription_IDE()!=null) out.println(sdf.format(un.getPoleEmploiInscripription_IDE()));%>" /></td>
					<td>NUMERO D'IDENTIFIANT :</td>
					<td><input type="text" id="numeroidentifiant"
						name="numeroidentifiant" value="<%=un.getPoleEmploi_ID_IDE()%>" /></td>
				</tr>
			</table>
			<br>
			<hr>
			<br>
			<h3>FORMATION et QUALIFICATION</h3>

			<!--  NIVEAU DE FORMATION-->

			<div id="table4">
				<table>
					<tr>
						<td>FORMATION SCOLAIRE :</td>
						<td><select name="niveauformation" id="niveauformation"
							onfocus="recupererNiveau();">
								<option selected="selected"><%=un.getNiveauFormation_IDE()%></option>
						</select></td>
					</tr>
				</table>
				<br />
				<p>
					<!-- *******************On liste les permis professionnels********************* -->

					<u>PERMIS PROFESSIONNEL</u>
				</p>
				<table id="permispro">
					<tr>
						<td align="center"><em>NOM PERMIS</em></td>
						<td><em>DATE EXPIRATION</em></td>
					</tr>
					<tr>
						<td align="center"><label>C</label></td>
						<td><input type="text" name="expirationC" id="expirationC"
							value="<%if(perc!=null) out.println(sdf.format(perc));%>" /></td>
					</tr>
					<tr>
						<td align="center"><label>D</label></td>
						<td><input type="text" name="expirationD" id="expirationD"
							value="<%if(perd!=null) out.println(sdf.format(perd));%>" /></td>
					</tr>
					<tr>
						<td align="center"><label>EC</label></td>
						<td><input type="text" name="expirationEC" id="expirationEC"
							value="<%if(perec!=null) out.println(sdf.format(perec));%>" /></td>
					</tr>
					<tr>
						<td align="center"><label>Cariste</label></td>
						<td><input type="text" name="expirationCariste"
							id="expirationCariste"
							value="<%if(percariste!=null) out.println(sdf.format(percariste));%>" /></td>
					</tr>
					<tr>
						<td align="center"><label>Caces</label></td>
						<td><input type="text" name="expirationCaces"
							id="expirationCaces"
							value="<%if(percaces!=null) out.println(sdf.format(percaces));%>" /></td>
					</tr>
					<tr>
						<td align="center"><label>Fimo</label></td>
						<td><input type="text" name="expirationFimo"
							id="expirationFimo"
							value="<%if(perfimo!=null) out.println(sdf.format(perfimo));%>" /></td>
					</tr>
					<tr>
						<td align="center"><label>Fcos</label></td>
						<td><input type="text" name="expirationFcos"
							id="expirationFcos"
							value="<%if(perfcos!=null) out.println(sdf.format(perfcos));%>" /></td>
					</tr>
					<tr>
						<td align="center"><label>APTH</label></td>
						<td><input type="text" name="expirationApth"
							id="expirationApth"
							value="<%if(perapth!=null) out.println(sdf.format(perapth));%>" /></td>
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
			<h3>DIPLOMES</h3>
			<!--*************************  on liste les DIPLOMES****************************-->

			<table id="table5">
				<tr>
					<td width=70%>Diplome</td>
					<td width=10%>Obtenu</td>
					<td>ANNEE</td>
				</tr>

				<%
					for (int i = 0; i < listeDip.size(); i++) {
				%>
				<tr>
					<td><input type="text" name="nomdiplome<%=i + 1%>"
						value="<% if(listeDip.get(i).getNomDiplome()!=null) out.println(listeDip.get(i).getNomDiplome());%>" /></td>
					<td><select name="diplomeobtenu<%=i + 1%>">
							<option value="non">NON</option>
							<option value="oui">OUI</option>
							<option selected="selected"><%=listeDip.get(i).getObtenu()%></option>
					</select></td>
					<td><input type="text" name="anneeobtention<%=i + 1%>"
						value="<% if(listeDip.get(i).getAnnee()!=null) out.println(listeDip.get(i).getAnnee());%>" /></td>
				</tr>

				<%
					}
																			for (int j = listeDip.size(); j < 5; j++) {
				%>
				<tr>
					<td><input type="text" name="nomdiplome<%=j + 1%>" /></td>
					<td><select name="diplomeobtenu<%=j + 1%>">
							<option value="non">NON</option>
							<option value="oui">OUI</option>
					</select></td>
					<td><input type="text" name="anneeobtention<%=j + 1%>" /></td>
				</tr>
				<%
					}
				%>
			</table>
			<br>
			<hr>
			<br>
			<h3>EMPLOI RECHERCHE</h3>

			<!--************************** On liste les  EMPLOI RECHERCHE**********************-->

			<table id="table10">

				<%
					for (int i = 0; i < listeRech.size(); i++) {
				%>
				<tr>
					<td><select name="emploirecherche<%=i + 1%>"
						id="emploirecherche<%=i + 1%>"
						onfocus="recupererEmploiRecherche<%=i + 1%>();">
							<option selected="selected"><%=listeRech.get(i).getRome().getIntitule()%></option>
					</select></td>
					<td><a
						href="/valence/controleur?action=supprimerechercheemploi&emploi=<%=listeRech.get(i).getId_recherche()%>&id=<%=num%>"
						title="Supprimer"><img src="/valence/images/bleu/sup.jpg" /></a><br>
					</td>
				</tr>
				<%
				}
			%>
			</table>
			

			<div id="inutile">
				<%
					if (listeRech.size()<10){
							for (int j = listeRech.size(); j < 10; j++) {
				%>
				<select name="emploirecherche<%=j + 1%>"
					id="emploirecherche<%=j + 1%>"
					onfocus="recupererEmploiRecherche<%=j + 1%>();">
					<option selected="selected"></option>
				</select><br>

				<%
					}}
				%>
			</div>


			<br> <a href="#table10" onclick="ajoutrecherche('inutile');">Dérouler
				liste des recherches</a> <br> <br>
			<hr>
			<br>
	
	<br>
	
	<div id="fin">
		<input type="button" id="enregistrer" class="boutonvert" value="Enregistrer"   onclick="valider(form1);" />
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

	<script type="text/javascript"
		src="/valence/javascript/jquery.mask.min.js"></script>
	<script type="text/javascript"
		src="/valence/javascript/scripts/formatageMask.js"></script>

	<script type="text/javascript"
		src="/valence/javascript/scripts/jour.js"></script>
	<script type="text/javascript"
		src="/valence/javascript/scripts/journouveaux.js"></script>
	<script type="text/javascript"
		src="/valence/javascript/scripts/instructions.js"></script>
		
	
</body>
</html>