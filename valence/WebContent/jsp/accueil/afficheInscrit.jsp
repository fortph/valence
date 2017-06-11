<%@ page
	import="dao.imp.identite.*, beans.identite.*,java.util.*,java.lang.*,java.text.*,
dao.imp.formation.*,beans.formation.*,dao.imp.suivi.*,beans.suivi.*,divers.*,
dao.imp.sap.*,beans.sap.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/valence/css/afficheinscription.css">
<link rel="stylesheet" href="/valence/javascript/css/theme.default.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.tablesorter.pager.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<%
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	String id = request.getParameter("numero");
	int num = Integer.parseInt(id);
	Identite un = new IdentiteDAO().findByID(num);

	String saisi = request.getParameter("saisi");

	FormaterDate datefr = new FormaterDate();

	/**********************recuperation des prestations et avenants sap*********/
	PrestationCDIDAO pcdidao = new PrestationCDIDAO();
	List<PrestationCDI> lcdi = null;
	try {
		lcdi = pcdidao.listePrestationsParPersonne(un);

	} catch (Exception e) {
		e.printStackTrace();
	}
	AvenantPrestationCDIDAO avcdidao = new AvenantPrestationCDIDAO();
	List<AvenantPrestationCDI> lavcdi = null;
	try {
		lavcdi = avcdidao.listeAvenantPrestationCDIParPersonne(un);

	} catch (Exception e) {
		e.printStackTrace();
	}

	/***************************************************************************/
	/* on recupere les formations pyramides suivies par cette personne*/
	//personnes accueillies
	PreInscriptionDAO ldao = new PreInscriptionDAO();
	List<PreInscription> listingaccueil = null;
	try {
		listingaccueil = ldao.pyramideAccueil(un);

	} catch (Exception e) {
		e.printStackTrace();
	}

	//formations enregistrées
	ListeFormationsDAO lfdao = new ListeFormationsDAO();
	List<ListeFormations> listingenregistre = null;
	try {
		listingenregistre = lfdao.pyramideEnregistre(un);
	} catch (Exception e) {
		e.printStackTrace();
	}

	//pformations inscrites
	List<ListeFormations> listinginscrit = null;
	try {
		listinginscrit = lfdao.pyramideInscrit(un);
	} catch (Exception e) {
		e.printStackTrace();
	}

	//formations abandonnées

	List<PreInscription> listingabandon = null;
	try {
		listingabandon = ldao.formationabandon(un);
	} catch (Exception e) {
		e.printStackTrace();
	}

	List<ListeFormations> listingabandon2 = null;

	//liste des suivis
	SuiviPersonneDAO suivdao = new SuiviPersonneDAO();
	List<SuiviPersonne> liste3suivis = null;
	try {
		liste3suivis = suivdao.afficherSuiviPersonne(un);
	} catch (Exception e) {
		e.printStackTrace();
	}

	//liste des suivis
	SuiviEmploiDAO suivempdao = new SuiviEmploiDAO();
	List<SuiviEmploi> liste3suivisemploi = null;
	try {
		liste3suivisemploi = suivempdao.afficherTroisSuiviEmploi(un);
	} catch (Exception e) {
		e.printStackTrace();
	}

	//liste des accompagnements formation
	AccompagnementFormationDAO acdao = new AccompagnementFormationDAO();
	List<AccompagnementFormation> liste3accomp = null;
	try {
		liste3accomp = acdao.afficherAccompagenementFormation(un);
	} catch (Exception e) {
		e.printStackTrace();
	}

	//liste des suivis formation
	SuiviFormationDAO suidao = new SuiviFormationDAO();
	List<SuiviFormation> liste3suivifor = null;
	try {
		liste3suivifor = suidao.afficherTroisSuiviForm(un);
	} catch (Exception e) {
		e.printStackTrace();
	}

	/***************************************************************************/
	//recuperation du permis
	//System.out.println("permis b ="+un.isPermib_IDE());
	boolean permis = un.isPermib_IDE();
	boolean actif = un.isStatutfiche_IDE();
	String urlimgac = "/valence/images/bleu/coche_1.jpg";

	//recuperation des moyens de locomotion
	String auto = "", velo = "", moto = "";
	ProfilLocomotionDAO pldao = new ProfilLocomotionDAO();
	ProfilLocomotion pl = new ProfilLocomotion();
	pl.setId_identite(un.getId_IDE());
	List<ProfilLocomotion> listeLoco = pldao.findByCriteria(pl);
	if (listeLoco.size() > 0) {
		for (int i = 0; i < listeLoco.size(); i++) {
			if (listeLoco.get(i).getLocomotion().equals("VOITURE"))
				auto = "checked";
			else if (listeLoco.get(i).getLocomotion().equals("VELO"))
				velo = "checked";
			else if (listeLoco.get(i).getLocomotion().equals("MOTO"))
				moto = "checked";
		}
	}

	//récuperation des diplomes
	ProfilDiplomeDAO pdao = new ProfilDiplomeDAO();
	ProfilDiplome pd = new ProfilDiplome();
	pd.setId_identite(un.getId_IDE());
	List<ProfilDiplome> listeDip = pdao.findByCriteria(pd);
	//System.out.println("encodage ="+request.getCharacterEncoding());

	//récuperation des permis professionnels
	ProfilPermisProDAO pppdao = new ProfilPermisProDAO();
	List<ProfilPermisPro> ppro = pppdao.listingPermis(un);
	Date perc = null, perd = null, perec = null, percariste = null, percaces = null, perfimo = null,
			perfcos = null, perapth = null;
	for (int n = 0; n < ppro.size(); n++) {
		if (ppro.get(n).getLibelle().equals("C"))
			perc = ppro.get(n).getExpiration();
		else if (ppro.get(n).getLibelle().equals("D"))
			perd = ppro.get(n).getExpiration();
		else if (ppro.get(n).getLibelle().equals("E(C)"))
			perec = ppro.get(n).getExpiration();
		else if (ppro.get(n).getLibelle().equals("CARISTE"))
			percariste = ppro.get(n).getExpiration();
		else if (ppro.get(n).getLibelle().equals("CACES"))
			percaces = ppro.get(n).getExpiration();
		else if (ppro.get(n).getLibelle().equals("FIMO"))
			perfimo = ppro.get(n).getExpiration();
		else if (ppro.get(n).getLibelle().equals("FCOS"))
			perfcos = ppro.get(n).getExpiration();
		else if (ppro.get(n).getLibelle().equals("APTH"))
			perapth = ppro.get(n).getExpiration();
	}

	//récuperation des emplois recherchés
	ProfilRechercheDAO prdao = new ProfilRechercheDAO();
	//ProfilRecherche pr = new ProfilRecherche();
	//pr.setIdentite(un);
	List<ProfilRecherche> listeRech = prdao.listeEmploiParPersonne(un);

	//récuperation des priorités
	String aah = "", are = "", ass = "", exdet = "", aide = "", sal = "", interim = "", indep = "", etud = "",
			ret = "", civ = "", civ2 = "", rth = "", rsasoc = "", rsachap = "";//a=null,b=null,c=null;
	java.util.Date a = null, b = null, c = null;
	ProfilPrioriteDAO ppdao = new ProfilPrioriteDAO();
	ProfilPriorite pri = new ProfilPriorite();
	pri.setId_identite(un.getId_IDE());
	List<ProfilPriorite> listePrio = null;

	listePrio = ppdao.findByCriteria(pri);

	if (listePrio.size() > 0) {
		for (int i = 0; i < listePrio.size(); i++) {
			if (listePrio.get(i).getLibelle().equals("AAH"))
				aah = "checked";
			else if (listePrio.get(i).getLibelle().equals("ARE"))
				are = "checked";
			else if (listePrio.get(i).getLibelle().equals("ASS"))
				ass = "checked";
			else if (listePrio.get(i).getLibelle().equals("EXDET"))
				exdet = "checked";
			else if (listePrio.get(i).getLibelle().equals("AIDE-SOCIALE"))
				aide = "checked";
			else if (listePrio.get(i).getLibelle().equals("SALARIE"))
				sal = "checked";
			else if (listePrio.get(i).getLibelle().equals("INTERIMAIRE"))
				interim = "checked";
			else if (listePrio.get(i).getLibelle().equals("INDEPENDANT"))
				indep = "checked";
			else if (listePrio.get(i).getLibelle().equals("ETUDIANT"))
				etud = "checked";
			else if (listePrio.get(i).getLibelle().equals("RETRAITE"))
				ret = "checked";
			else if (listePrio.get(i).getLibelle().equals("CIVIS-CLASSIQUE"))
				civ = "checked";
			else if (listePrio.get(i).getLibelle().equals("CIVIS-RENFORCE"))
				civ2 = "checked";
			else if (listePrio.get(i).getLibelle().equals("RTH")) {
				rth = "checked";
				a = listePrio.get(i).getExpire();
			} else if (listePrio.get(i).getLibelle().equals("RSA-SOCLE")) {
				rsasoc = "checked";
				b = listePrio.get(i).getExpire();
			} else if (listePrio.get(i).getLibelle().equals("RSA-CHAPEAU")) {
				rsachap = "checked";
				c = listePrio.get(i).getExpire();
			}

		}
	}
%>
</head>

<body>

	<div id="feuille">
		<!-- onload="recupererSaisie();afficherNationalite();recupererSF();"-->
		<p><%@ include file='/menus/menugeneral/menusuivi.jsp'%></p>


		<%@ include file='/menus/menugaucheidentite/menugauche.jsp'%>
		<div id="body">
			<br>
			<div id="creation">FICHE ACCUEIL</div>
			<br>
			<hr>
			<center>
				<label>Date accueil: <%
					if (un.getDateAccueil_IDE() != null)
						out.println(sdf.format(un.getDateAccueil_IDE()));
					else {
						out.println("Non saisie");
					};
				%></label>
			</center>
			<hr>
			<label>Fiche active: </label><%=actif ? "OUI" : "NON"%><img
				src="<%=urlimgac%>">
			<div id="boutons">
				<input type="button" class="bouton" id="modifierbouton"
					value="Modifier fiche"
					onclick="self.location.href='/valence/jsp/accueil/modifierInscrit.jsp?numero=<%=un.getId_IDE()%>'" />
				<input type="button" class="bouton" id="modifierbouton1"
					value="Pré-Inscription Pyramide"
					onclick="self.location.href='/valence/jsp/formation/preinscription.jsp?numero=<%=un.getId_IDE()%>'" />
			</div>
			<br>
			<h3>ETAT CIVIL</h3>
			<table class="table1">


				<!-- Etat Civil -->

				<tr>
					<td>NOM</td>
					<td>PRENOM</td>
					<td>NOM DE JEUNE FILLE</td>
				</tr>
				<tr>
					<td class="entree"><%=un.getNom_IDE()%></td>
					<td class="entree"><%=un.getPrenom_IDE()%></td>
					<td class="entree"><%=un.getNomjf_IDE()%></td>
				</tr>
				<tr>
					<td>ADRESSE</td>
				</tr>
				<tr>
					<td colspan="3" class="entree"><%=un.getAdr1_IDE()%></td>
				</tr>

				<tr>
					<td colspan="3" class="entree"><%=un.getAdr2_IDE()%></td>
				</tr>
			</table>
			<table class="table2">
				<tr>
					<td>CODE POSTAL :</td>
					<td class="blanc">
						<%
							if (un.getCp_IDE() != null)
								out.println(un.getCp_IDE());
						%>
					</td>
					<td>VILLE :</td>
					<td class="blanc">
						<%
							if (un.getVille_IDE() != null)
								out.println(un.getVille_IDE());
						%>
					</td>

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
					<td class="entree1">
						<%
							if (un.getDatenais_IDE() != null)
								out.println(sdf.format(un.getDatenais_IDE()) + " (" + un.calculerAgeReel() + ")");
						%>
					</td>
					<td class="entree1 "><%=un.getLieunais_IDE()%></td>
					<td class="entree1">
						<%
							if (un.getPaysnais_IDE() != null)
								out.println(un.getPaysnais_IDE());
						%>
					</td>
					<td class="entree1"><%=un.getDeptnais_IDE()%></td>
				</tr>
				<tr>
					<td>NATIONALITE</td>
					<td>SEXE</td>
					<td>SITUATION FAMILIALE</td>
					<td>ENFANTS</td>
				</tr>
				<tr>
					<td class="entree1">
						<%
							if (un.getNationalite_IDE() != null)
								out.println(un.getNationalite_IDE());
						%>
					</td>

					<td class="entree1">
						<%
							if (un.getSexe_IDE() != null)
								out.println(un.getSexe_IDE());
						%>
					</td>

					<td class="entree1">
						<%
							if (un.getSitfam_IDE() != null)
								out.println(un.getSitfam_IDE());
						%>
					</td>
					<td class="entree1"><%=un.getEnfants_IDE()%></td>
				</tr>
			</table>
			<br>
			<hr>
			<br>
			<h3>SOCIAL</h3>
			<!--  Social -->
			<table class="table2">

				<tr>
					<td class="entree3">SECURITE SOCIALE</td>
					<td class="entree3 blanc">
						<%
							if (un.getNss_IDE() != null)
								out.println(new SecuriteSociale().formaterNumeroSS(un.getNss_IDE()));
						%>
					</td>
					<td class="entree4">CARTE SEJOUR</td>
					<td class="entree2">
						<%
							if (un.getNcs_IDE() != null)
								out.println(un.getNcs_IDE());
						%>
					</td>
					<td class="entree4">DATE EXPIRATION</td>
					<td class="entree2">
						<%
							if (un.getCs_expiration_IDE() != null)
								out.println(sdf.format(un.getCs_expiration_IDE()));
						%>
					</td>
				</tr>

			</table>
			<br>
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
					<td class="entree1">&nbsp; <%=un.getMobile_IDE()%></td>
					<td class="entree1"><%=un.getFixe_IDE()%></td>
					<td class="entree1"><%=un.getPhone3_IDE()%></td>
					<td class="entree1">
						<%
							if (un.getPhone3Localisation_IDE() != null)
								out.println(un.getPhone3Localisation_IDE());
						%>
					</td>
				</tr>
				<tr>
					<td>ADRESSE MAIL :</td>
					<td colspan="2" class="entree1"><%=un.getMail_IDE()%></td>
				</tr>

			</table>
			<br>
			<hr>
			<br>
			<!-- *******************on liste les moyens de locomotion*************************** -->
			<h3>LOCOMOTION</h3>
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

			<!-- ***********************on liste les  PRIORITEs********************************* -->
			<h3>PRIORITE</h3>
			<div id="casesacocher">
				AAH <input type="checkbox" name="prio" value="AAH" <%=aah%> /> ARE
				<input type="checkbox" name="prio" value="ARE" <%=are%> /> ASS <input
					type="checkbox" name="prio" value="ASS" <%=ass%> /> SUIVI
				JUDICIAIRE <input type="checkbox" name="prio" value="EXDET"
					<%=exdet%> /> AIDE-SOCIALE <input type="checkbox" name="prio"
					value="AIDE-SOCIALE" <%=aide%> /><br> SALARIE <input
					type="checkbox" name="prio" value="SALARIE" <%=sal%> />
				<!--INTERIMAIRE <input type="checkbox"
				name="prio" value="INTERIMAIRE" <%=interim%> />INDEPENDANT <input
				type="checkbox" name="prio" value="INDEPENDANT" <%=indep%> />-->
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
						<td><em>DATE EXPIRATION: </em></td>
						<td>
							<%
								if (a != null)
									out.println(sdf.format(a));
							%>
						</td>
					</tr>
					<tr>
						<td>RSA SOCLE <input type="checkbox" id="RSA-SOCLE"
							name="prio" value="RSA-SOCLE" <%=rsasoc%> />
						</td>
						<td><em> DEPUIS LE: </em></td>
						<td>
							<%
								if (b != null)
									out.println(sdf.format(b));
							%>
						</td>
					</tr>
					<tr>
						<td>RSA CHAPEAU <input type="checkbox" name="prio"
							id="RSA-CHAPEAU" value="RSA-CHAPEAU" <%=rsachap%> />
						</td>
						<td><em> DEPUIS LE: </em></td>
						<td>
							<%
								if (c != null)
									out.println(sdf.format(c));
							%>
						</td>
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
					<td>DATE INSCRIPTION :</td>
					<td class="entree1">
						<%
							if (un.getPoleEmploiInscripription_IDE() != null)
								out.println(sdf.format(un.getPoleEmploiInscripription_IDE()));
						%>
					</td>
					<td>NUMERO D'IDENTIFIANT :</td>
					<td class="entree1"><%=un.getPoleEmploi_ID_IDE()%></td>
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
						<td>NIVEAU de FORMATION :</td>
						<td class="entree1">
							<%
								if (un.getNiveauFormation_IDE() != null)
									out.println(un.getNiveauFormation_IDE());
							%>
						</td>
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
						<td align="center"><em>DATE EXPIRATION</em></td>
					</tr>
					<tr>
						<td class="prioperm"><label>C</label></td>
						<td class="prioperm">
							<%
								if (perc != null)
									out.println(sdf.format(perc));
							%>
						</td>
					</tr>
					<tr>
						<td class="prioperm"><label>D</label></td>
						<td class="prioperm">
							<%
								if (perd != null)
									out.println(sdf.format(perd));
							%>
						</td>
					</tr>
					<tr>
						<td class="prioperm"><label>EC</label></td>
						<td class="prioperm">
							<%
								if (perec != null)
									out.println(sdf.format(perec));
							%>
						</td>
					</tr>
					<tr>
						<td class="prioperm"><label>Cariste</label></td>
						<td class="prioperm">
							<%
								if (percariste != null)
									out.println(sdf.format(percariste));
							%>
						</td>
					</tr>
					<tr>
						<td class="prioperm"><label>Caces</label></td>
						<td class="prioperm">
							<%
								if (percaces != null)
									out.println(sdf.format(percaces));
							%>
						</td>
					</tr>
					<tr>
						<td class="prioperm"><label>Fimo</label></td>
						<td class="prioperm">
							<%
								if (perfimo != null)
									out.println(sdf.format(perfimo));
							%>
						</td>
					</tr>
					<tr>
						<td class="prioperm"><label>Fcos</label></td>
						<td class="prioperm">
							<%
								if (perfcos != null)
									out.println(sdf.format(perfcos));
							%>
						</td>
					</tr>
					<tr>
						<td class="prioperm"><label>APTH</label></td>
						<td class="prioperm">
							<%
								if (perapth != null)
									out.println(sdf.format(perapth));
							%>
						</td>
					</tr>




				</table>
			</div>
			<br>
			<hr>
			<br>

			<!-- *******************On liste les formations pyramides********************* -->

			<h3>FORMATION PYRAMIDE</h3>

			<%
				if (listingaccueil != null && listingaccueil.size() > 0) {
			%>
			<br>
			<h4>Renseignements</h4>
			<table id="listingaccueil">
				<thead>
					<tr>
						<th>Date Accueil</th>
						<th>Animatrice</th>
						<th>Commentaires</th>
					</tr>
				</thead>

				<tbody>
					<%
						for (int j = 0; j < listingaccueil.size(); j++) {
					%>



					<tr>
						<td>
							<%
								if (listingaccueil.get(j).getDate_pyramide() != null)
											out.println(sdf.format(listingaccueil.get(j).getDate_pyramide()));
							%>
						</td>
						<td><%=listingaccueil.get(j).getAnimatrice().getNom()%></td>
						<%
							if (listingaccueil.get(j).getCommentaires() != null) {
						%>
						<td><%=listingaccueil.get(j).getCommentaires()%></td>
						<%
							} else {
						%>
						<td></td>
					</tr>
					<%
						}
							}
					%>
				</tbody>

			</table>


			<!--************************Rajout des pager a tablesorter*********************** -->
			<div id="pager" class="pager">
				<form>
					<img src="/valence/javascript/images/icons/first.png" class="first" />
					<img src="/valence/javascript/images/icons/prev.png" class="prev" />
					<span class="pagedisplay"></span>
					<!-- this can be any element, including an input -->
					<img src="/valence/javascript/images/icons/next.png" class="next" />
					<img src="/valence/javascript/images/icons/last.png" class="last" />
					<select class="pagesize">
						<option selected="selected" value="5">5</option>
						<option value="10">10</option>
						<option value="20">20</option>
						<option value="30">30</option>
						<option value="100">100</option>
					</select>
				</form>
			</div>

			<%
				}
			%>

			<!-- **********************formations enregistreées pour cette personne*************-->
			<%
				if (listingenregistre.size() > 0) {
			%>

			<h4>Enregistrés</h4>
			<table class="listingaccueil1">
				<thead>
					<tr>
						<th>Formation</th>
						<th>Organisme</th>
						<th>Niveau</th>
						<th>Date de début</th>
						<th>Date de fin</th>
					</tr>
				</thead>

				<tbody>
					<%
						for (int j = 0; j < listingenregistre.size(); j++) {
					%>



					<tr>
						<td><%=listingenregistre.get(j).getFormation()%></td>
						<td><%=listingenregistre.get(j).getOrganis().getOrg()%></td>
						<td><%=listingenregistre.get(j).getNiveau()%></td>
						<td>
							<%
								if (listingenregistre.get(j).getDatedeb_form() != null)
											out.println(sdf.format(listingenregistre.get(j).getDatedeb_form()));
							%>
						</td>
						<td>
							<%
								if (listingenregistre.get(j).getDatefin_form() != null)
											out.println(sdf.format(listingenregistre.get(j).getDatefin_form()));
							%>
						</td>

					</tr>
					<%
						}
					%>
				</tbody>

			</table>


			<!--************************Rajout des pager a tablesorter*********************** -->
			<div id="pager" class="pager1">
				<form>
					<img src="/valence/javascript/images/icons/first.png" class="first" />
					<img src="/valence/javascript/images/icons/prev.png" class="prev" />
					<span class="pagedisplay"></span>
					<!-- this can be any element, including an input -->
					<img src="/valence/javascript/images/icons/next.png" class="next" />
					<img src="/valence/javascript/images/icons/last.png" class="last" />
					<select class="pagesize">
						<option selected="selected" value="5">5</option>
						<option value="10">10</option>
						<option value="20">20</option>
						<option value="30">30</option>
						<option value="100">100</option>
					</select>
				</form>
			</div>

			<%
				}
			%>

			<!-- **********************formations inscrites pour cette personne*************-->
			<%
				if (listinginscrit.size() > 0) {
			%>

			<h4>Inscrits</h4>
			<table class="listingaccueil2">
				<thead>
					<tr>
						<th>Formation</th>
						<th>Organisme</th>
						<th>Niveau</th>
						<th>Date de début</th>
						<th>Date de fin</th>
					</tr>
				</thead>

				<tbody>
					<%
						for (int j = 0; j < listinginscrit.size(); j++) {
					%>



					<tr>
						<td><%=listinginscrit.get(j).getFormation()%></td>
						<td><%=listinginscrit.get(j).getOrganis().getOrg()%></td>
						<td><%=listinginscrit.get(j).getNiveau()%></td>
						<td>
							<%
								if (listinginscrit.get(j).getDatedeb_form() != null)
											out.println(sdf.format(listinginscrit.get(j).getDatedeb_form()));
							%>
						</td>
						<td>
							<%
								if (listinginscrit.get(j).getDatefin_form() != null)
											out.println(sdf.format(listinginscrit.get(j).getDatefin_form()));
							%>
						</td>

					</tr>
					<%
						}
					%>
				</tbody>

			</table>


			<!--************************Rajout des pager a tablesorter*********************** -->
			<div id="pager" class="pager2">
				<form>
					<img src="/valence/javascript/images/icons/first.png" class="first" />
					<img src="/valence/javascript/images/icons/prev.png" class="prev" />
					<span class="pagedisplay"></span>
					<!-- this can be any element, including an input -->
					<img src="/valence/javascript/images/icons/next.png" class="next" />
					<img src="/valence/javascript/images/icons/last.png" class="last" />
					<select class="pagesize">
						<option selected="selected" value="5">5</option>
						<option value="10">10</option>
						<option value="20">20</option>
						<option value="30">30</option>
						<option value="100">100</option>
					</select>
				</form>
			</div>

			<%
				}
			%>


			<!--******************* Formations abandonnées par cette personne*******************  -->
			<%
				if (listingabandon.size() > 0) {
			%>
			<h4>Abandons</h4>
			<table class="listingaccueil3">
				<thead>
					<tr>
						<th>Formation</th>
						<th>Organisme</th>
						<th>Niveau</th>
						<th>Date début</th>
						<th>Date fin</th>
						<th>Date d'abandon</th>
						<th>Motif</th>
					</tr>
				</thead>
				<tbody>
					<%
						for (int j = 0; j < listingabandon.size(); j++) {
								listingabandon2 = lfdao
										.pyramideAbandon(lfdao.findByID(listingabandon.get(j).getListe().getId_pformation()));
					%>
					<tr>
						<td><%=listingabandon2.get(j).getFormation()%></td>
						<td><%=listingabandon2.get(j).getOrganis().getOrg()%></td>
						<td><%=listingabandon2.get(j).getNiveau()%></td>
						<td>
							<%
								if (listingabandon2.get(j).getDatedeb_form() != null)
											out.println(sdf.format(listingabandon2.get(j).getDatedeb_form()));
							%>
						</td>
						<td>
							<%
								if (listingabandon2.get(j).getDatefin_form() != null)
											out.println(sdf.format(listingabandon2.get(j).getDatefin_form()));
							%>
						</td>
						<td>
							<%
								if (listingabandon.get(j).getDateAbandon() != null)
											out.println(sdf.format(listingabandon.get(j).getDateAbandon()));
							%>
						</td>
						<td><%=listingabandon.get(j).getRais_abandon()%></td>

					</tr>
					<%
						}
					%>


				</tbody>
			</table>



			<!--************************Rajout des pager a tablesorter*********************** -->
			<div id="pager" class="pager3">
				<form>
					<img src="/valence/javascript/images/icons/first.png" class="first" />
					<img src="/valence/javascript/images/icons/prev.png" class="prev" />
					<span class="pagedisplay"></span>
					<!-- this can be any element, including an input -->
					<img src="/valence/javascript/images/icons/next.png" class="next" />
					<img src="/valence/javascript/images/icons/last.png" class="last" />
					<select class="pagesize">
						<option selected="selected" value="5">5</option>
						<option value="10">10</option>
						<option value="20">20</option>
						<option value="30">30</option>
						<option value="100">100</option>
					</select>
				</form>
			</div>

			<%
				}
			%>


			<!-- ************************************************************************************ -->
			<br>
			<hr>
			<br>
			<!--*************************  on liste les DIPLOMES****************************-->
			<h3>DIPLOMES</h3>
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
						value="<%if (listeDip.get(i).getNomDiplome() != null)
					out.println(listeDip.get(i).getNomDiplome());%>" /></td>
					<td><select name="diplomeobtenu<%=i + 1%>">
							<option value="non">NON</option>
							<option value="oui">OUI</option>
							<option selected="selected">
								<%
									if (listeDip.get(i).getObtenu() != null)
											out.println(listeDip.get(i).getObtenu());
								%>
							</option>
					</select></td>
					<td><input type="text" name="anneeobtention<%=i + 1%>"
						value="<%if (listeDip.get(i).getAnnee() != null)
					out.println(listeDip.get(i).getAnnee());%>" /></td>
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

			<!--************************** On liste les  EMPLOI RECHERCHE**********************-->
			<h3>EMPLOI RECHERCHE</h3>
			<table id="table10">
				<%
					for (int i = 0; i < listeRech.size(); i++) {
				%>
				<tr>
					<td><select name="emploirecherche<%=i + 1%>"
						id="emploirecherche<%=i + 1%>"
						onclick="recupererEmploiRecherche<%=i + 1%>();">
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
			<br>
			<hr>
			<br>


			<!-- *******************On liste les accompagnements********************* -->

			<h3>ACCOMPAGNEMENT EMPLOI</h3>

			<div id="boutons">
				<input type="button" class="boutondroit" id="modifierbouton"
					value="Accès"
					onclick="self.location.href='/valence/jsp/suiviInscrits/accompagnement.jsp?numero=<%=un.getId_IDE()%>'" />
			</div>

			<p class="gras">Les 3 derniers accompagnements emploi:</p>
			<table class="table1">
				<%
					if (liste3suivis.size() > 0) {
						for (int i = 0; i < liste3suivis.size(); i++) {
				%>


				<tr class="vu">
					<td >Vu(e) le:</td>
					<td>
						<%
							if (liste3suivis.get(i).getDatesuivi() != null)
										out.println(sdf.format(liste3suivis.get(i).getDatesuivi()));
						%>
					</td>
					<td>par :</td>
					<td><%=liste3suivis.get(i).getReferent().getPrenom()%> <%=liste3suivis.get(i).getReferent().getNom()%></td>
				</tr>
				<%
					}
					}
				%>
			</table>
			<br>
			<hr>
			<br>
			<!-- *******************On liste les suivi emploi********************* -->

			<h3>SUIVI EN EMPLOI</h3>

			<div id="boutons">
				<input type="button" class="boutondroit" id="modifierbouton"
					value="Accès"
					onclick="self.location.href='/valence/jsp/suiviInscrits/accompagnementemploi.jsp?numero=<%=un.getId_IDE()%>'" />
			</div>

			<p class="gras">Les 3 derniers suivis en emploi:</p>

			<%
				if (liste3suivisemploi.size() > 0) {
					for (int j = 0; j < liste3suivisemploi.size(); j++) {
			%>
			<table id="table10">
				<tr>
					<th class="petite"></th>
					<th class="majeur"></th>
					<th class="petite-"></th>
					<th class="petitep"></th>
					<th class="petite-"></th>
					<th class="petite"></th>
				</tr>
				<tr class="vu">
					<td >Vu(e) le:</td>
					<td>
						<%
							if (liste3suivisemploi.get(j).getDateSuivi() != null)
										out.println(sdf.format(liste3suivisemploi.get(j).getDateSuivi()));
						%>
					</td>
					<td>par :</td>
					<td><%=liste3suivisemploi.get(j).getReferent().getPrenom()%> <%=liste3suivisemploi.get(j).getReferent().getNom()%></td>
					<td></td>
					<td></td>
				</tr>

				<tr>
					<td>Employeur :</td>
					<td><%=liste3suivisemploi.get(j).getEmployeur().getRs_employeur()%></td>
					<td>du :</td>
					<td>
						<%
							if (liste3suivisemploi.get(j).getDateDebutSuivi() != null)
										out.println(sdf.format(liste3suivisemploi.get(j).getDateDebutSuivi()));
						%>
					</td>
					<td>au :</td>
					<td>
						<%
							if (liste3suivisemploi.get(j).getDateFinSuivi() != null)
										out.println(sdf.format(liste3suivisemploi.get(j).getDateFinSuivi()));
						%>
					</td>

				</tr>



			</table>
			<%
				}
				}
			%>

			<br>
			<hr>
			<br>




			<!-- *******************On liste les accompagnements********************* -->

			<h3>ACCOMPAGNEMENT FORMATION</h3>

			<div id="boutons">
				<input type="button" class="boutondroit" id="modifierbouton"
					value="Accès"
					onclick="self.location.href='/valence/jsp/suiviFormations/accompagnement.jsp?numero=<%=un.getId_IDE()%>'" />
			</div>

			<p class="gras">Les 3 derniers accompagnements formation:</p>
			<table class="table1">
				<%
					if (liste3accomp.size() > 0) {
						for (int i = 0; i < liste3accomp.size(); i++) {
				%>


				<tr class="vu">
					<td >Vu(e) le:</td>
					<td>
						<%
							if (liste3accomp.get(i).getDateredaction() != null)
										out.println(sdf.format(liste3accomp.get(i).getDateredaction()));
						%>
					</td>
					<td>par :</td>
					<td><%=liste3accomp.get(i).getReferent().getNom()%></td>
				</tr>
				<%
					}
					}
				%>
			</table>

			<br>
			<hr>
			<br>
			<!-- *******************On liste les suivi formations********************* -->

			<h3>SUIVI FORMATION</h3>

			<div id="boutons">
				<input type="button" class="boutondroit" id="modifierbouton"
					value="Accès"
					onclick="self.location.href='/valence/jsp/suiviFormations/suiviformation.jsp?numero=<%=un.getId_IDE()%>'" />
			</div>

			<p class="gras">Les 3 derniers suivis formation:</p>

			<%
				if (liste3suivifor.size() > 0) {
					for (int j = 0; j < liste3suivifor.size(); j++) {
			%>
			<table id="table10">
				<tr>
					<th class="petite"></th>
					<th class="majeur"></th>
					<th class="petite-"></th>
					<th class="petite"></th>
					<th class="petite-"></th>
					<th class="petite"></th>
				<tr>
				<tr class="vu">
					<td>Vu(e) le:</td>
					<td>
						<%
							if (liste3suivifor.get(j).getDateSuivi() != null)
										out.println(sdf.format(liste3suivifor.get(j).getDateSuivi()));
						%>
					</td>
					<td>par :</td>
					<td colspan="3"><%=liste3suivifor.get(j).getReferent().getNom()%></td>
					
				</tr>

				<tr>
					<td>Formation :</td>
					<td colspan="5"><%=liste3suivifor.get(j).getFormation()%></td>
					</tr>
					<tr>
					<td >du :</td>
					<td colspan="2">
						<%
							if (liste3suivifor.get(j).getDateDebutFormation() != null)
										out.println(sdf.format(liste3suivifor.get(j).getDateDebutFormation()));
						%>
					</td>
					<td>au :</td>
					<td colspan="2">
						<%
							if (liste3suivifor.get(j).getDateFinFormation() != null)
										out.println(sdf.format(liste3suivifor.get(j).getDateFinFormation()));
						%>
					</td>

				</tr>
				<tr>
					<td></td>
				</tr>


			</table>
			<%
				}
				}
			%>

			<br>
			<hr>
			<br>




			<h3>HISTORIQUE PRESTATIONS SAP</h3>


			<%
				if (lcdi != null && lcdi.size() > 0) {
			%>

			<table class="listingaccueil3">
				<thead>
					<tr>
						<th>N°</th>
						<th>Date</th>
						<th>Employeur</th>
						<th>Tache</th>
						<th>Heures</th>
						<th>Date Déb</th>
						<th>Date Fin</th>

					</tr>
				</thead>
				<tbody>
					<%
						for (int j = 0; j < lcdi.size(); j++) {
					%>
					<tr>
						<td><a
							href="/valence/jsp/employeurs/prestations/affichageprestation.jsp?prestation=<%=lcdi.get(j).getId_prestationcontrat()%>">P<%=lcdi.get(j).getId_prestationcontrat()%></a></td>
						<td><%=sdf.format(lcdi.get(j).getRedaction_pr())%></td>
						<td><%=lcdi.get(j).getEmployeur().getRs_employeur()%></td>
						<td><%=lcdi.get(j).getTache().getLibelle()%></td>
						<td><%=lcdi.get(j).getHeuresminimois_pr()%></td>
						<td>
							<%
								if (lcdi.get(j).getDatedebut_pr() != null)
											out.println(sdf.format(lcdi.get(j).getDatedebut_pr()));
							%>
						</td>
						<td>
							<%
								if (lcdi.get(j).getDatefin_pr() != null)
											out.println(sdf.format(lcdi.get(j).getDatefin_pr()));
							%>
						</td>

					</tr>
					<%
						}
							if (lavcdi.size() > 0) {
								for (int j = 0; j < lavcdi.size(); j++) {
					%>
					<tr>
						<td><a
							href="/valence/jsp/employeurs/avenantprestation/affichageprestation.jsp?prestation=<%=lavcdi.get(j).getId_prestationavenant()%>">P<%=lavcdi.get(j).getPrestation().getId_prestationcontrat()%>-A<%=lavcdi.get(j).getId_prestationavenant()%></a></td>
						<td><%=sdf.format(lavcdi.get(j).getRedaction_pr())%></td>
						<td><%=lavcdi.get(j).getPrestation().getEmployeur().getRs_employeur()%></td>
						<td><%=lavcdi.get(j).getTache().getLibelle()%></td>
						<td><%=lavcdi.get(j).getHeuresminimois_pres()%></td>
						<td>
							<%
								if (lavcdi.get(j).getDatedebut_pr() != null)
												out.println(sdf.format(lavcdi.get(j).getDatedebut_pr()));
							%>
						</td>
						<td>
							<%
								if (lavcdi.get(j).getDatefin_pr() != null)
												out.println(sdf.format(lavcdi.get(j).getDatefin_pr()));
							%>
						</td>

					</tr>
					<%
						}
					%>

				</tbody>
			</table>



			<!--************************Rajout des pager a tablesorter*********************** -->
			<div id="pager" class="pager3">
				<form>
					<img src="/valence/javascript/images/icons/first.png" class="first" />
					<img src="/valence/javascript/images/icons/prev.png" class="prev" />
					<span class="pagedisplay"></span>
					<!-- this can be any element, including an input -->
					<img src="/valence/javascript/images/icons/next.png" class="next" />
					<img src="/valence/javascript/images/icons/last.png" class="last" />
					<select class="pagesize">
						<option selected="selected" value="5">5</option>
						<option value="10">10</option>
						<option value="20">20</option>
						<option value="30">30</option>
						<option value="100">100</option>
					</select>
				</form>
			</div>

			<%
				}
				}
			%>




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
				src="/valence/javascript/jquery.tablesorter.min.js"></script>

			<script type="text/javascript"
				src="/valence/javascript/jquery.tablesorter.pager.min.js"></script>

			<script type="text/javascript"
				src="/valence/javascript/scripts/tableautablesorter.js"></script>



			<br>


		</div>
	</div>
</body>
</html>