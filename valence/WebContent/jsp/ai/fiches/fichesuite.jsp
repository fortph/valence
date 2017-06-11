<%@ page
	import="dao.imp.identite.*, beans.identite.*,dao.imp.ai.*,beans.ai.*,java.util.*,java.text.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/valence/javascript/css/theme.default.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.tablesorter.pager.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<link rel="stylesheet" href="/valence/css/ai/fiches/fichesuite3.css">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>fiche ai</title>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	boolean permanent = false;
	String personne = request.getParameter("personne");
	IdentiteDAO iddao = new IdentiteDAO();
	Identite une = iddao.findByID(Integer.parseInt(personne));

	CreationDAO fiche = new CreationDAO();
	int numerofiche = fiche.recupererID(une);

	if (request.getAttribute("permanent") != null)
		permanent = (Boolean) request.getAttribute("permanent");
	else {
		permanent = fiche.verifierPermanentFiche(une);
	}

	//String agrement=(String)request.getAttribute("agrement");
	//java.sql.Date deb=(java.sql.Date)request.getAttribute("debut");
	//java.sql.Date fin=(java.sql.Date)request.getAttribute("fin");
	String affiche = "PERMANENT";

	if (!permanent)
		affiche = "NON PERMANENT";

	ContratDAO condao = new ContratDAO();
	List<Contrat> liste = condao.contratAiParPersonne(une);

	ExtranetDAO exdao = new ExtranetDAO();
	List<Extranet> listeextranet = exdao.listeExtranetParPersonne(une);

	AgrementDAO agdao = new AgrementDAO();
	List<Agrement> listeagrement = agdao.listeAgrementParPersonne(une);

	AvenantDAO avenantdao = new AvenantDAO();

	VisiteMedicaleDAO vidao = new VisiteMedicaleDAO();
	List<VisiteMedicale> listevisitesmed = vidao
			.listeVisitesParPersonne(une);
%>
</head>
<body>

	<div id="body">
		<p><%@ include file='/menus/menusai/menuficheai.jsp'%></p>
		<br>
		<div id="creation">CREATION FICHE AI</div>
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
		<p class="centre2">
			<a
				href="/valence/controleur?action=aimodifierfichepermanent&id=<%=numerofiche%>&personne=<%=une.getId_IDE()%>"><%=affiche%></a>
		</p>
		<br>
		<hr>
		<br>




		<h3>POLE EMPLOI</h3>
		<div class="boutons">
			<input type="button" class="bouton boutonvert" id="modifierbouton"
				value="Nouveau" 
				onclick="self.location.href='/valence/jsp/ai/agrement/nouveauagrement.jsp?numero=<%=une.getId_IDE()%>'" />
		</div>
		<br>
		<%
			if (listeagrement.size() > 0) {
				for (int i = 0; i < listeagrement.size(); i++) {
		%>
		<table class="table1">
			<tr>
				<td>AGREMENT N°:</td>
				<td class="affiche1 centre"><%=listeagrement.get(i).getNumagrement()%></td>
				<td>DATE DEBUT:</td>
				<td class="affiche1 centre"><% if(listeagrement.get(i).getDatedeb()!=null) out.println(sdf.format(listeagrement.get(i).getDatedeb())); %></td>
				<td>DATE FIN:</td>
				<td class="affiche1 centre"><% if(listeagrement.get(i).getDatefin()!=null) out.println(sdf.format(listeagrement.get(i).getDatefin())); %></td>
				<td class="petit"><a
					href="/valence/jsp/ai/agrement/modifier.jsp?numagrement=<%=listeagrement.get(i).getId_aifiche()%>&personne=<% if(une!=null) out.println(une.getId_IDE()); %>"><img
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



		<h3>EXTRANET</h3>
		<div class="boutons">
			<input type="button" class="bouton boutonvert" id="modifierbouton"
				value="Nouveau"
				onclick="self.location.href='/valence/jsp/ai/extranet/nouveauextranet.jsp?numero=<%=une.getId_IDE()%>'" />
		</div>
		<br>
		<%
			if (listeextranet.size() > 0) {
				for (int i = 0; i < listeextranet.size(); i++) {
		%>
		<table class="table3">
			<tr>
				<td class="affiche4">DATE DEBUT:</td>
				<td class="affiche1"><% if(listeextranet.get(i).getDebut()!=null) out.println(sdf.format(listeextranet.get(i).getDebut()));%></td>
				<td class="affiche4">DATE FIN:</td>
				<td class="affiche1"><% if(listeextranet.get(i).getFin()!=null) out.println(sdf.format(listeextranet.get(i).getFin()));%></td>
				<td class="petit"><a title="modifier"
					href="/valence/jsp/ai/extranet/modifier.jsp?idextranet=<%=listeextranet.get(i).getId_extranet()%>&personne=<% if(une!=null) out.println(une.getId_IDE()); %>"><img
						src="/valence/images/bleu/mod.png" /></a></td>
						
						<!--************************** rajouté le 10/1/2015*****************-->
				<td class="petit"><a title="supprimer"
					href="/valence/controleur?action=supextranet&idextranet=<%=listeextranet.get(i).getId_extranet()%>&personne=<% if(une!=null) out.println(une.getId_IDE()); %>"><img
						src="/valence/images/bleu/sup.jpg" /></a></td>

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
		<h3>VISITE MEDICALE</h3>
		<div class="boutons">
			<input type="button" class="bouton boutonvert" id="modifierbouton"
				value="Nouveau"
				onclick="self.location.href='/valence/jsp/ai/visitemedicale/nouvellevisite.jsp?numero=<%=une.getId_IDE()%>'" />
		</div>
		<br>
		<%
			if (listevisitesmed.size() > 0) {
				for (int i = 0; i < listevisitesmed.size(); i++) {
		%>
		<table class="table1">
			<tr>
				<td> DEMANDE CONVOCATION</td>
				<td class="affiche1">
					<%
						if (listevisitesmed.get(i).getConvocation() != null)
									out.println(sdf.format(listevisitesmed.get(i).getConvocation()));
					%>
				</td>
				<td>CONVOCATION SMTI</td>
				<td class="affiche1">
					<%
						if (listevisitesmed.get(i).getVisite() != null)
									out.println(sdf.format(listevisitesmed.get(i).getVisite()));
					%>
				</td>
				<td>ECHEANCE</td>
				<td class="affiche1">
					<%
						if (listevisitesmed.get(i).getEcheance() != null)
									out.println(sdf.format(listevisitesmed.get(i).getEcheance()));
					%>
				</td>
				<td class="petit"><a
					href="/valence/jsp/ai/visitemedicale/modifier.jsp?numvisite=<%=listevisitesmed.get(i).getId_suivi()%>"><img
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



		<h3>HISTORIQUE</h3>

		<div class="boutons">
			<input type="button" class="bouton boutonvert" id="modifierbouton"
				value="Nouveau Contrat"
				onclick="self.location.href='/valence/jsp/ai/contrat/nouveau.jsp?numero=<%=une.getId_IDE()%>'" />
		</div>
		<br>
		
		
		<%
			if (liste.size() > 0) {
		%>
		<table id="listecontratsaiparpersonne" >
		<thead>
			<tr>
				<th>Numéro</th>
				<th>Employeur</th>
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
					href="/valence/jsp/employeurs/affichage.jsp?numero=<%=liste.get(i).getEmployeur().getId_employeur()%>"><%=liste.get(i).getEmployeur().getRs_employeur()%></a></td>
				<td><% if(liste.get(i).getDebutcontrat()!=null) out.println(sdf.format(liste.get(i).getDebutcontrat()));%></td>
				<td><% if(liste.get(i).getFincontrat()!=null) out.println(sdf.format(liste.get(i).getFincontrat()));%></td>
				<td><% String intitule=liste.get(i).getRome().getIntitule();
					out.println(intitule);
					%></td>
				
			</tr>
			<%  
			List<Avenant> listeavenants = avenantdao.listeAvenantContrat(liste.get(i));
						if (listeavenants.size() > 0) {
							for (int j = 0; j < listeavenants.size(); j++) {
			%>
			<tr>
			<td class="aligngauch"><a
					href="/valence/jsp/ai/avenant/affichage.jsp?avenant=<%=listeavenants.get(j).getIdavenant()%>"><%=listeavenants.get(j).getN_aiavenant()%></a></td>
				
			<td class="aligngauch"><a
					href="/valence/jsp/employeurs/affichage.jsp?numero=<%=liste.get(i).getEmployeur().getId_employeur()%>"><%=liste.get(i).getEmployeur().getRs_employeur()%></a></td>
				
				<td><% if(listeavenants.get(j).getDatedeb()!=null)out.println(sdf.format(listeavenants.get(j).getDatedeb())); %></td>
				<td><% if(listeavenants.get(j).getDatefin()!=null) out.println(sdf.format(listeavenants.get(j).getDatefin()));%></td>
				<td><%=intitule %></td>
			</tr>
			<%
				}}}
			%>
			</tbody>
			
		</table>
		
		<div id="pager" class="pager5">
			<img src="/valence/javascript/images/icons/first.png" class="first" />
			<img src="/valence/javascript/images/icons/prev.png" class="prev" />
			<span class="pagedisplay"></span>
			<!-- this can be any element, including an input -->
			<img src="/valence/javascript/images/icons/next.png" class="next" />
			<img src="/valence/javascript/images/icons/last.png" class="last" />
			<select class="pagesize">
				<!--  <option selected="selected" value="5">5</option>-->
				<option selected="selected" value="10">10</option>
				<option value="20">20</option>
				<option value="30">30</option>
				<option value="100">100</option>
				<option value="99999">Tout</option>
			</select>
		</div>
		<%
			}
			else
			{
		%>
		<p>Aucun enregistrement....</p>
		<%
		}
		%>
	
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
			src="/valence/javascript/jquery.tablesorter.min.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/jquery.tablesorter.pager.min.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/scripts/tableautablesorter.js"></script>
			</div>
</body>
</html>