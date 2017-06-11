<%@ page
	import="dao.imp.identite.*, beans.identite.*,dao.imp.sap.*,beans.sap.*,
	java.util.*,java.text.*,dao.imp.ai.*,beans.ai.*,beans.employeurs.*,dao.imp.employeur.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript"
	src="/valence/javascript/scripts/attention.js"></script>
<link rel="stylesheet" href="/valence/css/sap/fichesuite.css">
<link rel="stylesheet" href="/valence/javascript/css/theme.default.css">
<link rel="stylesheet" href="/valence/javascript/css/TableTools.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables_themeroller.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<title>fiche sap</title>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	String personne = request.getParameter("personne");
	IdentiteDAO iddao = new IdentiteDAO();
	Identite une = iddao.findByID(Integer.parseInt(personne));

	

	ContratCDIDAO condao = new ContratCDIDAO();
	List<ContratCDI> listec = condao.contratSapParPersonne(une);
	
	
	AvenantCDIDAO avenantdao = new AvenantCDIDAO();
	List<AvenantCDI> listea=null;
	
	PrestationCDIDAO prestdao=new PrestationCDIDAO();
	List<PrestationCDI> listeprest=prestdao.listePrestationsPersonne(une);
	
	AvenantPrestationCDIDAO avepdao=new AvenantPrestationCDIDAO();
	//List<AvenantPrestationCDI> listeprestav=avepdao.listeAvenantPrestationCDIParPersonne(une);

	VisiteMedicaleDAO vidao = new VisiteMedicaleDAO();
	List<VisiteMedicale> listevisitesmed = vidao
	.listeVisitesParPersonne(une);
%>


</head>
<body>
 

	<div id="body">





		<p><%@ include file='/menus/menusap/menufichesap.jsp'%></p>
		<br>
		<div id="creation">FICHE SAP</div>
		<br>

		<h3>ETAT CIVIL</h3>
		<table class="table1">
			<tr>
				<td class="grand">Nom, Prénom</td>
				<td class="affiche"><%=une.getNom_IDE()%> <%=une.getPrenom_IDE()%></td>
			</tr>
			<tr>
				<td>Adresse</td>
				<td class="affiche"><%=une.getAdr1_IDE()%></td>
			</tr>
			<tr>
				<td></td>
				<td class="affiche"><%=une.getAdr2_IDE()%></td>
			</tr>
			<tr>
				<td>Code Postal et Ville</td>
				<td class="affiche"><%=une.getCp_IDE()%> <%=une.getVille_IDE()%></td>
			</tr>
			<tr>
				<td>Numéro de Sécurité Sociale</td>
				<td class="affiche">
					<%
						if (une.getNss_IDE() != null)
																out.println(une.getNss_IDE());
															else
																out.println("");
					%>
				</td>
			</tr>
		</table>
		<br>

		<hr>

		<br>
		<h3>VISITE MEDICALE</h3>
		<div class="boutons">
			<input type="button" class="bouton" id="modifierbouton"
				value="Nouveau"
				onclick="self.location.href='/valence/jsp/sap/visitemedicale/nouvellevisite.jsp?numero=<%=une.getId_IDE()%>'" />
		</div>
		<br>
		<%
			if (listevisitesmed.size() > 0) {
				for (int i = 0; i < listevisitesmed.size(); i++) {
		%>
		<table class="table1">
			<tr>
				<td>CONVOCATION</td>
				<td class="affiche1">
					<%
						if (listevisitesmed.get(i).getConvocation() != null)
																		out.println(sdf.format(listevisitesmed.get(i)
																				.getConvocation()));
					%>
				</td>
				<td>REALISATION</td>
				<td class="affiche1">
					<%
						if (listevisitesmed.get(i).getVisite() != null)
																		out.println(sdf.format(listevisitesmed.get(i)
																				.getVisite()));
					%>
				</td>
				<td>ECHEANCE</td>
				<td class="affiche1">
					<%
						if (listevisitesmed.get(i).getEcheance() != null)
																		out.println(sdf.format(listevisitesmed.get(i)
																				.getEcheance()));
					%>
				</td>
				<td class="petit"><a
					href="/valence/jsp/sap/visitemedicale/modifier.jsp?numvisite=<%=listevisitesmed.get(i).getId_suivi()%>"><img
						src="/valence/images/bleu/mod.png" /></a></td>

			</tr>
			<%
				}
			%>
		</table>
		<%
			}
		%>
		<br>
		<hr>
		<br>



		<h3>Historique des Contrats de Travail</h3>

		<div class="boutons">
			<input type="button" class="bouton" id="modifierbouton"
				value="Nouveau Contrat"
				onclick="self.location.href='/valence/jsp/sap/contrat/nouveau.jsp?personne=<%=une.getId_IDE()%>'" />
		</div>
		<br>
		<%
			if (listec.size() > 0) {
		%>
		<table class="table2">
			<tr>
				<th>Numéro</th>
				<th>Début</th>
				<th>Fin</th>
				<th>Heures Hebdo</th>
				<th>Taches</th>
			</tr>
			<%
				for (int i = 0; i < listec.size(); i++) {
									String couleur = "rouge";
									if (listec.get(i).getTermecontrat() == null)
										couleur = "vert";
			%>
			<tr class="<%=couleur%> ">
				<td class="aligngauch"><a
					href="/valence/jsp/sap/contrat/affichagecontrat.jsp?contrat=<%=listec.get(i).getId_contratcdi()%>&personne=<%=personne%>">C<%=listec.get(i).getId_contratcdi()%></a></td>
				<td>
					<%
						if (listec.get(i).getEmbauche() != null)
																		out.println(sdf.format(listec.get(i).getEmbauche()));
					%>
				</td>
				<td>
					<%
						if (listec.get(i).getTermecontrat() != null)
																		out.println(sdf.format(listec.get(i).getTermecontrat()));
					%>
				</td>
				<td><%=listec.get(i).getHeuresminimois()%></td>
				<td><%=listec.get(i).getTache()%></td>
			</tr>
			<%
				listea = avenantdao.listeAvenantContratCDI(listec.get(i));

									if (listea.size() > 0) {
										for (int j = 0; j < listea.size(); j++) {
			%>
			<tr>

				<td><a
					href="/valence/jsp/sap/avenantcontrat/affichageavenant.jsp?contrat=<%=listea.get(j).getId_avenant()%>&personne=<%=personne%>">A<%=listea.get(j).getRangavenent()%>
						C<%=listec.get(i).getId_contratcdi()%></a></td>
				<td>
					<%
						if (listea.get(j).getDateeffet() != null)
																				out.println(sdf.format(listea.get(j)
																						.getDateeffet()));
					%>
				</td>
				<td class="rien"></td>
				<td><%=listea.get(j).getHeuresminimois()%></td>
				<td><%=listea.get(j).getTache()%></td>
			</tr>
			<%
				}
									}
								}
			%>
		</table>
		<%
			}
		%>
		<br>





		<%
			if (listeprest.size() > 0) {
		%>

		<hr>
		<br>
		<h3>Historique des Prestations</h3>
		<br>
	

		<table id="suiviprestation" class="display">
			<thead>
				<tr>
					<th>Numéro</th>
					<th>Utilisateur</th>
					<th>Début</th>
					<th>Fin</th>
					<th>Heures mois</th>
					<th>Taches</th>
				</tr>
			</thead>
			<tbody>
				<%
					for (int i = 0; i < listeprest.size(); i++) {
				%>
				<tr>
					<td class="aligngauch grande"><a
						href="/valence/jsp/employeurs/prestations/affichageprestation.jsp?prestation=<%=listeprest.get(i).getId_prestationcontrat()%>">P<%=listeprest.get(i).getId_prestationcontrat()%></a></td>
					<td><a href="/valence/jsp/employeurs/affichage.jsp?numero=<%=listeprest.get(i).getEmployeur().getId_employeur()  %>">
					<% if(!listeprest.get(i).getEmployeur().getNomresponsable().equals("")) out.println(listeprest.get(i).getEmployeur().getNomresponsable()
					+" "+listeprest.get(i).getEmployeur().getPrenomresponsable());
					else
					out.println(listeprest.get(i).getEmployeur().getRs_employeur());%></a></td>
					<td class="centre">
						<%
							if (listeprest.get(i).getDatedebut_pr() != null)
								out.println(sdf.format(listeprest.get(i).getDatedebut_pr()));
						%>
					</td>
					<td class="centre">
						<%
							if (listeprest.get(i).getDatefin_pr() != null)
								out.println(sdf.format(listeprest.get(i).getDatefin_pr()));
						%>
					</td>
					<td class="centre"><%=listeprest.get(i).getHeuresminimois_pr()%></td>
					<td><%=listeprest.get(i).getTache().getLibelle()%></td>
				</tr>
				
				<%
				List<AvenantPrestationCDI> listeprestav=avepdao.listeAvenantPrestationCDI(listeprest.get(i));
				if (listeprestav!=null) {
					for (int j = 0; j < listeprestav.size(); j++) {
						int id_ave=listeprestav.get(j).getId_prestationavenant();
						AvenantPrestationCDI avenantencours=avepdao.findByID(id_ave);
				%>
				
				<tr>

					<td class="grande"><a
						href="/valence/jsp/employeurs/avenantprestation/affichageprestation.jsp?prestation=<%=id_ave%>">
							P<%=avenantencours.getPrestation().getId_prestationcontrat()%>-A<%=listeprestav.get(j).getRangavenant()%></a></td>
					<td><a href="/valence/jsp/employeurs/affichage.jsp?numero=<%=avenantencours.getPrestation().getEmployeur().getId_employeur()  %>">
					<% if(!avenantencours.getPrestation().getEmployeur().getNomresponsable().equals(""))
						out.println(avenantencours.getPrestation().getEmployeur().getNomresponsable()+" "
								+avenantencours.getPrestation().getEmployeur().getPrenomresponsable());
					else
					out.println(avenantencours.getPrestation().getEmployeur().getRs_employeur());
					%></a></td>
					<td class="centre">
						<%
							if (listeprestav.get(j).getDatedebut_pr()!= null)
																					out.println(sdf.format(listeprestav.get(j)
																							.getDatedebut_pr()));
						%>
					</td>
					<td class="centre">
						<%
							if (listeprestav.get(j).getDatefin_pr()!= null)
																					out.println(sdf.format(listeprestav.get(j)
																							.getDatefin_pr()));
						%>
					</td>
					<td class="centre"><%=listeprestav.get(j).getHeuresminimois_pres()%></td>
					<td><%=listeprestav.get(j).getTache().getLibelle()%></td>
				</tr>
				<%
				}}}
				%>
				</tbody>
				</table>
				<%
				}
				%>
				<br>
				<br>
				
				
				
				

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
			src="/valence/javascript/jquery.dataTables.min.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/scripts/datatabletripardates.js"></script>

		<script type="text/javascript"
			src="/valence/javascript/scripts/tableaux.js"></script>


		<%
			int existedeja=0 ;
			if(listec!=null)
				for(int i=0;i<listec.size();i++){
			if(listec.get(i).getTermecontrat()==null){
				existedeja++;
			
			}
				}
				if(existedeja>1){
		%>


		<script type="text/javascript">
			attentionComplet("Les contrats en cours sont de couleur verte...");
		</script>
		<%
			}
		%>




		<br>
	</div>
</body>
</html>