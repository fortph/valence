<%@ page
	import="dao.imp.employeur.*, beans.employeurs.*,java.util.*,java.lang.*,
	dao.imp.formation.*,beans.formation.*,beans.sap.*,dao.imp.sap.*,java.text.*,
	beans.ai.*,dao.imp.ai.*,divers.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/valence/css/employeur/affichage.css">
<link rel="stylesheet" href="/valence/javascript/css/theme.default.css">
<link rel="stylesheet" href="/valence/javascript/css/TableTools.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables_themeroller.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />




<title>Fiche employeur</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<%
	SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
	String id=request.getParameter("numero");
	int num = Integer.parseInt(id);
	Employeur un =new EmployeurDAO().findByID(num);
	//on verifie si l'employeur est un particulier ou une entreprise*/
	String struct=un.getRsstatut_employeur().getStaturrs();
	String civilemployeur=un.getCivemp();
	
		
	String civilite=null;
	if(struct.equals("")||struct.equals("AUTRE STATUT"))
		civilite=civilemployeur;
	else
		civilite=struct;
	
	
	boolean actif=un.isActif();
	String urlimgac="/valence/images/bleu/coche_1.jpg";
	/***************************************************************************/
	/* on recupere les services associé à cet employeur*/
	
	ServiceDAO ldao=new ServiceDAO();	
	List<Service> listingservices=null;
	try{
	listingservices=ldao.differentsServices(num);
	}
	catch(Exception e){
		e.printStackTrace();
	}
	
	/***************************************************************************/
	/*                      on recupere les offres							   */
	
	OffreDAO offredao=new OffreDAO();
	List<Offre> listeoffres=null;
	try{
		listeoffres=offredao.offresParEmployeur(un);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	
	
	/***************************************************************************/
	/*                      on recupere les suivis 							   */
	
	SuiviDAO suidao=new SuiviDAO();
	List<Suivi>listesuivi=null;
	try{
		listesuivi=suidao.listeSuiviParEmployeur(num);
	}
	catch(Exception e){
		e.printStackTrace();
	}
	
	/***************************************************************************/
	/*                      on recupere les prestations						   */
	
	PrestationCDIDAO prestdao=new PrestationCDIDAO();
	List<PrestationCDI>listeprestations=null;
	try{
		listeprestations=prestdao.listePrestationsParEmployeur(un);
	
	}
	catch(Exception e){
		e.printStackTrace();
	}
	
	
	/***************************************************************************/
	/*                      on recupere les avenants							   */
	
	AvenantPrestationCDIDAO avendao=new AvenantPrestationCDIDAO();
	List<AvenantPrestationCDI>listeavenants=null;
	
	
	/********       recupération des contrats et avenants ai             *******/
	
	ContratDAO condao = new ContratDAO();
	List<Contrat> liste = condao.contratAiParEmployeur(un);
	
	AvenantDAO avenantaidao = new AvenantDAO();
	
	
	
	
%>

</head>




<body>

	<div id="body">
		<p><%@ include file='/menus/employeur/menuemp.jsp'%></p>
		<br>
		<div id="creation">FICHE EMPLOYEUR</div>
		<br>
		<hr>


		<label>Fiche active: </label><%=actif ? "OUI" : "NON"%><img
			src="<%=urlimgac%>">
		<div class="boutons">
			<input type="button" class="blue" id="modifierbouton"
				value="Modifier fiche"
				onclick="self.location.href='/valence/jsp/employeurs/modificationemployeur.jsp?numero=<%=un.getId_employeur()%>'" />

		</div>
		<br>
		<h3>EMPLOYEUR</h3>
		<table class="table1">


			<!-- Etat Civil -->

			<tr>
				<td class="label">Raison sociale</td>
				<td><input type="text" name="rs"
					value="<%=civilite%> <%=un.getRs_employeur()%>" /></td>
			</tr>
			<tr>
				<td class="label">Adresse</td>
				<td><input type="text" name="adr1" value="<%=un.getAdr1()%>" /></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="text" name="adr2" value="<%=un.getAdr2()%>" /></td>
			</tr>
		</table>


		<table class="table2">
			<tr>
				<td class="label">C.P.</td>
				<td><input type="text" name="cp" value="<%=un.getCp()%>" /></td>
				<td class="label">&nbsp;Ville</td>
				<td><input type="text" name="ville" value="<%=un.getVille()%>" /></td>
			</tr>
		</table>
		<table class="table1">
			<tr>
				<td class="label">Responsable</td>
				<td><input type="text" name="nom"
					value="<%=un.getCivresp()%> <%=un.getNomresponsable()%> <%=un.getPrenomresponsable()%> <%if(un.getRangresponsable()!=null) out.println(un.getRangresponsable());%>" /></td>
			</tr>

		</table>


		<table class="table3">
			<tr>
				<td class="label">Tél.:</td>
				<td><input type="text" name="tel1" id="tel1"
					value="<%=un.getTel1()%>" /></td>
				<td class="label">&nbsp;Tél. :</td>
				<td><input type="text" name="tel2" id="tel2"
					value="<%=un.getTel2()%>" /></td>
				<td class="label">&nbsp;Fax :</td>
				<td><input type="text" name="fax" id="fax"
					value="<%=un.getFax()%>" /></td>
			</tr>
		</table>


		<table class="table1">
			<tr>
				<td class="label">Mail :</td>
				<td><input type="text" name="mail" id="mail"
					value="<%=un.getMail()%>" /></td>
			</tr>
		</table>
		<br>
		<table class="table1">
			<tr>
				<td class="label">Structure :</td>
				<td><input type="text" name="structure" id="structure"
					value=" <%=un.getStructure().getStructure()%>" /></td>
			</tr>
			<tr>
				<td class="label">Activité :</td>
				<td><input type="text" name="activite" id="activite"
					value="<%=un.getActivite().getActivite()%>" /></td>
			</tr>
			<tr>
				<td class="label">Observation :</td>
				<td><input type="text" name="obs" id="obs"
					value="<%=un.getObservation()%>" /></td>
			</tr>
		</table>


		<table class="table3">
			<tr>
				<td class="label">SIRET :</td>
				<td><input type="text" name="siret" id="siret"
					value="<%if(un.getSiret()!=null)out.println(un.getSiret());%>" /></td>
				<td class="label">&nbsp; APE:</td>
				<td><input type="text" name="ape" id="ape"
					value="<%if(un.getApe()!=null)out.println(un.getApe());%>" /></td>
				<td class="label">&nbsp;RM:</td>
				<td><input type="text" name="rm" id="rm"
					value="<%if(un.getRm()!=null)out.println(un.getRm());%>" /></td>
			</tr>
		</table>


		<!--**********************Les  services **************************-->
		<br>
		<hr>
		<br>
		<h3>SERVICES (liste)</h3>
		<div class="boutons">
			<input type="button" class="bouton" id="modifierbouton"
				value="Créer Service"
				onclick="self.location.href='/valence/jsp/employeurs/nouveauservice.jsp?employeur=<%=un.getId_employeur()%>'" />
		</div>
		<%
			if (listingservices.size()>0){			
																	for (int i=0;i<listingservices.size();i++){
		%>
		<table class="table1">

			<tr>
				<td class="label"></td>
				<td class="liste"><%=listingservices.get(i).getService()%></td>
			</tr>
		</table>
		<%
			}}
		%>

		<br>
		<hr>
		<br>


		<!--**********************Les  contacts**************************-->
		<h3>CONTACTS</h3>
		<div class="boutons">
			<input type="button" class="bouton" id="modifierbouton"
				value="Nouveau Contact"
				onclick="self.location.href='/valence/jsp/employeurs/nouveaucontact.jsp?employeur=<%=un.getId_employeur()%>'" />
		</div>
		<br>
		<%
			ContactDAO contdao=new ContactDAO();
				List<Contact> listecontact=contdao.afficherListeContacts(id);
				if(listecontact.size()>0){
					for(int k=0;k<listecontact.size();k++){
					String identite= listecontact.get(k).getCiv_contact()+" "+listecontact.get(k).getPrenom_contact()
					+ " "+listecontact.get(k).getNom_contact().toUpperCase();
		%>

		<input type="button" class="bleu" value="Modifier"
			onclick="self.location.href='/valence/jsp/employeurs/modifiercontact.jsp?contact=<%=listecontact.get(k).getId_contact()%>&emp=<%=id%>'" />
		<input type="button" class="rouge" value="Supprimer"
			onclick="self.location.href='/valence/jsp/employeurs/supprimercontact.jsp?contact=<%=listecontact.get(k).getId_contact()%>&emp=<%=id%>'" />

		<table class="table3">
			<tr>
				<td class="label">Contact</td>
				<td><input type="text" name="civilite" value="<%=identite%>" /></td>
				<td>&nbsp;Rang</td>
				<td><input type="text" name="rang"
					value="<%=listecontact.get(k).getRang_contact()%>" /></td>
				<td>&nbsp;Service</td>
				<td><input type="text" name="service"
					value="<%=listecontact.get(k).getService().getService()%>" /></td>
			</tr>
		</table>


		<table class="table1">
			<tr>
				<td class="label">Téléphone 1</td>
				<td><input type="text" name="tel1"
					value="<%=listecontact.get(k).getTel_contact()%>" /></td>
				<td>&nbsp;Téléphone 2</td>
				<td><input type="text" name="tel2"
					value="<%=listecontact.get(k).getPortable_contact()%>" /></td>
			</tr>
			<tr>
				<td class="label">Fax</td>
				<td><input type="text" name="fax"
					value="<%=listecontact.get(k).getFax_contact()%>" /></td>
				<td>&nbsp;Mail</td>
				<td><input type="text" name="mail"
					value="<%=listecontact.get(k).getMail_contact()%>" /></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
		</table>

		<%
			}}
		%>
		
		
		<!--**********************Les  offres**************************-->
		<br>
		<hr>
		<br>
		<h3>OFFRES</h3>
		<br>
		<%
			if (listeoffres.size() > 0) {
		%>
		<table id="suivioffres" class="display">
		<thead>
			<tr>
				<th>Numéro</th>
				<th>DateDéb</th>
				<th>Poste</th>
				<th>Type</th>
				<th>Pers</th>
				<th>Durée</th>
				<th>Pourvue</th>
			</tr>
			</thead>
			<tbody>
			<%
				for (int i = 0; i < listeoffres.size(); i++) {
					String types=new AfficherTypeContrat().afficher(listeoffres.get(i));
					boolean pourvue=listeoffres.get(i).isPourvue();
					String affichePourvue="NON";
					if(pourvue)affichePourvue="OUI";
			%>
			<tr>
				<td><a
					href="/valence/jsp/employeurs/offres/affichageoffre.jsp?numoffre=<%=listeoffres.get(i).getId_offre()%>">OF-<%=listeoffres.get(i).getId_offre()%></a></td>
				<td><% if(listeoffres.get(i).getDatedeb_offre()!=null) out.println(sdf.format(listeoffres.get(i).getDatedeb_offre())); %></td>
				<td><a href="#monrepere" id="monrepere" title="<%=listeoffres.get(i).getRome().getIntitule() %>"><%=listeoffres.get(i).getRome().getNrome() %></a></td>
				<td><%=types %></td>
				<td><%=listeoffres.get(i).getNbpersonnes()%></td>
				<td><%=listeoffres.get(i).getDuree_offre() %></td>
				<td><%=affichePourvue %></td>
			</tr>
			
			<%
				}
						
					
			%>
			</tbody>
			<%
			}
			%>
		</table>
			<br><br>	

		<!--**********************Les  suivis**************************-->
		<br>
		<hr>
		<br>
		<h3>SUIVI</h3>
		<div class="boutons">
			<input type="button" class="bouton" id="modifierbouton"
				value="Nouveau Suivi"
				onclick="self.location.href='/valence/jsp/employeurs/suivi/suiviemployeur.jsp?employeur=<%=un.getId_employeur()%>'" />
		</div>
		<br>
		<table id="suiviemployeur" class="display">
			<thead>
				<tr>
					<th>Date</th>
					<th>Utilisateur</th>
					<th>Commentaires</th>
					<th>Modifier</th>
				</tr>
			</thead>
			<tbody>
				<%
					if(listesuivi.size() >0){
							for(int i=0;i<listesuivi.size();i++){
				%>
				<tr>
					<td class="ldate"><%=sdf.format(listesuivi.get(i).getJour())%></td>
					<td><%=listesuivi.get(i).getUtilisateur().getNom()%> <%=listesuivi.get(i).getUtilisateur().getPrenom()%></td>
					<td><textarea><%=listesuivi.get(i).getCommentaires()%></textarea></td>
					<td class="petit"><a
						href="/valence/jsp/employeurs/suivi/modifiersuivi.jsp?employeur=<%=un.getId_employeur()%>&suivi=<%=listesuivi.get(i).getId_suivi()%>"><img
							src="/valence/images/bleu/mod.png" /></a></td>

				</tr>
				<%
					}}
				%>
			</tbody>
		</table>
		<br> <br> <br>
		<hr>
		<br>
		<!-- ************************ les contrats	*******************-->
		<h3>CONTRATS ET AVENANTS AI</h3>
		<br>
		<%
			if (liste.size() > 0) {
		%>
		<table id="suiviaiparemployeur" class="display">
		<thead>
			<tr>
				<th>Numéro</th>
				<th>Salarié</th>
				<th>Début</th>
				<th>Fin</th>
				<th>Emploi</th>
			</tr>
			</thead>
			<tbody>
			<%
				for (int i = 0; i < liste.size(); i++) {
			%>
			<tr>
				<td><a
					href="/valence/jsp/ai/contrat/affichage.jsp?contrat=<%=liste.get(i).getIdaicontrat()%>">C<%=liste.get(i).getNumerocontrat()%></a></td>
				<td class="aligngauch"><a
					href="/valence/jsp/accueil/afficheInscrit.jsp?numero=<%=liste.get(i).getIdentite().getId_IDE()%>"><%=liste.get(i).getIdentite().getNom_IDE()%> <%=liste.get(i).getIdentite().getPrenom_IDE()%></a></td>
				<td><% if(liste.get(i).getDebutcontrat()!=null) out.println(sdf.format(liste.get(i).getDebutcontrat()));%></td>
				<td><% if(liste.get(i).getFincontrat()!=null) out.println(sdf.format(liste.get(i).getFincontrat()));%></td>
				<td><%=liste.get(i).getRome().getIntitule()%></td>
			</tr>
			<%
				List<Avenant> listeaiavenants = avenantaidao
								.listeAvenantContrat(liste.get(i));
						if (listeaiavenants.size() > 0) {
							for (int j = 0; j < listeaiavenants.size(); j++) {
			%>
			<tr>
				
				<td class="aligngauch"><a
					href="/valence/jsp/ai/avenant/affichage.jsp?avenant=<%=listeaiavenants.get(j).getIdavenant()%>"><%=listeaiavenants.get(j).getN_aiavenant()%></a></td>
				<td class="aligngauch"><a
					href="/valence/jsp/accueil/afficheInscrit.jsp?numero=<%=liste.get(i).getIdentite().getId_IDE()%>"><%=liste.get(i).getIdentite().getNom_IDE()%> <%=liste.get(i).getIdentite().getPrenom_IDE()%></a></td>
				<td><% if(listeaiavenants.get(j).getDatedeb()!=null)out.println(sdf.format(listeaiavenants.get(j).getDatedeb())); %></td>
				<td><% if(listeaiavenants.get(j).getDatefin()!=null) out.println(sdf.format(listeaiavenants.get(j).getDatefin()));%></td>
				<td><%=liste.get(i).getRome().getIntitule() %></td>
			</tr>
			<%
				}
						}
					}
			%>
			</tbody>
			
		</table>
		<%
			}
		%>
		<br>
		<br>
		<hr>
		<br>
		
		<!-- ************************ les prestations	*******************-->
		<h3>PRESTATIONS SAP</h3>
		<div class="boutons">
			<input type="button" class="bouton" id="modifierbouton"
				value="Nouvelle Prestation"
				onclick="self.location.href='/valence/jsp/employeurs/prestations/nouvelle.jsp?numero=<%=un.getId_employeur()%>'" />
		</div>

		<table id="suiviprestation" class="display">
			<thead>
				<tr>
					<th>N°</th>
					<th>Date</th>
					<th>Salarié</th>
					<th>Tache</th>
					<th>Heures</th>
					<th>Début</th>
					<th>Fin</th>
					<th>Modifier</th>
				</tr>
			</thead>
			<tbody>

				<%
					if(listeprestations.size() >0)
																								{
																									for(int i=0;i<listeprestations.size();i++){
				%>
				<tr>
					<td class="lcontrat"><a
						href="/valence/jsp/employeurs/prestations/affichageprestation.jsp?prestation=<%=listeprestations.get(i).getId_prestationcontrat()%>">P<%=listeprestations.get(i).getId_prestationcontrat()%></a></td>
					<td class="ldate"><%=sdf.format(listeprestations.get(i).getRedaction_pr())%></td>
					<td><a
						href="/valence/jsp/accueil/afficheInscrit.jsp?numero=<%=listeprestations.get(i).getIdentite().getId_IDE()%>"><%=listeprestations.get(i).getIdentite().getNom_IDE()%>
							<%=listeprestations.get(i).getIdentite().getPrenom_IDE()%></a></td>
					<td><%=listeprestations.get(i).getTache().getLibelle()%></td>
					<td class="petit"><%=listeprestations.get(i).getHeuresminimois_pr()%></td>
					<td class="ldate"><%=sdf.format(listeprestations.get(i).getDatedebut_pr())%></td>
					<td class="ldate"><%=sdf.format(listeprestations.get(i).getDatefin_pr())%></td>
					<td class="petit"><a
						href="/valence/jsp/employeurs/prestations/modifierprestation.jsp?numcontrat=<%=listeprestations.get(i).getId_prestationcontrat()%>"><img
							src="/valence/images/bleu/mod.png" /></a></td>
				</tr>

				<%
					listeavenants=avendao.listeAvenantPrestationCDI(listeprestations.get(i));
											if(listeavenants!=null){
												for(int j=0;j<listeavenants.size();j++){
				%>

				<tr>
					<td class="lcontrat"><a
						href="/valence/jsp/employeurs/avenantprestation/affichageprestation.jsp?prestation=<%=listeavenants.get(j).getId_prestationavenant()%>">P<%=listeprestations.get(i).getId_prestationcontrat()%>-A<%=listeavenants.get(j).getRangavenant()%></a></td>
					<td class="ldate"><%=sdf.format(listeavenants.get(j).getRedaction_pr())%></td>
					<td><a
						href="/valence/jsp/accueil/afficheInscrit.jsp?numero=<%=listeprestations.get(i).getIdentite().getId_IDE()%>"><%=listeprestations.get(i).getIdentite().getNom_IDE()%>
							<%=listeprestations.get(i).getIdentite().getPrenom_IDE()%></a></td>
					<td><%=listeavenants.get(j).getTache().getLibelle()%></td>
					<td class="petit"><%=listeavenants.get(j).getHeuresminimois_pres()%></td>
					<td class="ldate"><%=sdf.format(listeavenants.get(j).getDatedebut_pr())%></td>
					<td class="ldate"><%=sdf.format(listeavenants.get(j).getDatefin_pr())%></td>
					<td class="petit"><a
						href="/valence/jsp/employeurs/avenantprestation/modifierprestation.jsp?numcontrat=<%=listeavenants.get(j).getId_prestationavenant()%>"><img
							src="/valence/images/bleu/mod.png" /></a></td>

				</tr>
				<%
					}
											}
											}
				%>
			</tbody>
			<%
			}
		%>
		</table>
		


		


		<!-- **************************************************************************-->

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
			src="/valence/javascript/jquery.autosize.min.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/jquery.dataTables.min.js"></script>

		<script type="text/javascript"
			src="/valence/javascript/scripts/plugindatatables.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/scripts/tableaux.js"></script>
			
		<script type="text/javascript"
			src="/valence/javascript/scripts/hauteurtextarea.js"></script>

		<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>

		<br>
	</div>
</body>
</html>
