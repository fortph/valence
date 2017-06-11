<%@ page
	import="beans.suivi.*,dao.imp.suivi.*,java.util.*,divers.*,beans.employeurs.*,dao.imp.employeur.*,
	beans.parametres.capemploi.*,beans.parametres.accueil.*,java.util.Date,
	java.text.*,beans.identite.*,dao.imp.identite.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="/valence/css/employeur/affichageoffreemp.css">
<link rel="stylesheet" href="/valence/javascript/css/theme.default.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.tablesorter.pager.css">

<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>affiche offre</title>
<%
	SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
	String numoffre = request.getParameter("numoffre");
	int numero=Integer.parseInt(numoffre);
	
	OffreDAO ofdao = new OffreDAO();
	Offre offre = ofdao.findByID(numero);
	int coderome=0;
	if(offre!=null)
		coderome=offre.getRome().getIdrome();
	
	Date dfin = offre.getDatefin_offre();
	String fin = "";
	
	if (dfin != null)
		fin = sdf.format(dfin);
	
	EmployeurDAO emdao = new EmployeurDAO();
	Employeur emp = emdao.findByID(offre.getEmployeur()
	.getId_employeur());
	ContactDAO contdao = new ContactDAO();
	Contact contact = contdao.findByID(offre.getContact()
	.getId_contact());
	UtilisateurDAO utidao = new UtilisateurDAO();
	Utilisateur utilisateur = utidao.findByID(offre.getSalarie()
	.getId_salarie());
	RomeDAO romdao = new RomeDAO();
	Rome rome = romdao.findByID(coderome);
	
	PositionnerPersonneDAO posdao=new PositionnerPersonneDAO();
	List<PositionnerPersonne> position=posdao.personnesPositionneesParOffre(numero);
	
	SuiviPersonneDAO suidao=new SuiviPersonneDAO();
	IdentiteDAO identdao=new IdentiteDAO();
	List<Identite> listecorrespond=identdao.rechercheParEmploiRome(coderome);
	
	ProfilPrioriteDAO profidao=new ProfilPrioriteDAO();
	AfficherPriorites afficheprio=new AfficherPriorites();
	AfficherTypeContrat affichecontrat=new AfficherTypeContrat();
%>
</head>
<body>
	<div id="body">
		<p><%@ include file='/menus/employeur/menuoffre.jsp'%></p>
		<br>
		<div id="creation">CONSULTATION D'UNE OFFRE</div>
		<br>
		<hr>
		<h3>EMPLOYEUR</h3>
		<br>
		<table id="table1">
			<tr>
				<td class="col1">Saisie le :</td>
				<td><% if(offre.getDateSaisie()!=null) out.println(sdf.format(offre.getDateSaisie())); %></td>
				<td class="col1">Par :</td>
				<td><%=offre.getSalarie().getPrenom()%> <%=offre.getSalarie().getNom()%></td>
				<td class="col1">Offre N°:</td>
				<td>OF-<%=offre.getId_offre()%></td>
			</tr>
		</table>
		<table id="table2">
			<tr>
				<td class="col1">Employeur :</td>
				<td><%=emp.getRs_employeur()%></td>
				<td>Tél: <%=emp.getTel1()%> Mob: <%=emp.getTel2()%></td>
			</tr>
			<tr>
				<td class="col1">Contact :</td>
				<td><%=contact.getNom_contact()%> <%=contact.getPrenom_contact()%></td>
				<td>Tél: <%=contact.getTel_contact()%> Mob:<%=contact.getPortable_contact()%></td>
			</tr>
			<tr>
				<td class="col1">Adresse :</td>
				<td><%=emp.getAdr1()%></td>
				<td></td>
			</tr>
			<tr>
				<td class="col1"></td>
				<td><%=emp.getCp()%> <%=emp.getVille()%></td>
				<td></td>
			</tr>
			<tr>
				<td class="col1">Activité :</td>
				<td><%=emp.getActivite().getActivite() %></td>
				<td></td>
			</tr>
			<tr>
				<td class="col1">Catégorie :</td>
				<td><%=emp.getStructure().getStructure()%></td>
				<td>Fax: <%=emp.getFax()%></td>
			</tr>
		</table>

		<br>
		<h3>POSTE</h3>
		<br>
		<table id="table3">
			<tr class="col1">
				<td>POSTE</td>
				<td class="centre">NOMBRE</td>
				<td class="centre">TYPE</td>
			</tr>
			<tr>
				<td><%=rome.getIntitule()%></td>
				<td class="centre"><%=offre.getNbpersonnes()%></td>
				<td class="centre"><%=affichecontrat.afficher(offre)%></td>
			</tr>
		</table>

		<table id="table1">
			<tr>
				<td class="col1">Date début :</td>
				<td class="reduit"><% if(offre.getDatedeb_offre()!=null) out.println(sdf.format(offre.getDatedeb_offre())); %></td>
				<td class="col1">Date fin :</td>
				<td class="reduit"><% if(fin!=null) out.println(fin); %></td>
				<td class="col1">Durée :</td>
				<td><%=offre.getDuree_offre()%></td>
			</tr>
		</table>

		<table id="table4">
			<tr>
				<td class="col2">Jours de travail proposés:</td>
				<td><%=offre.getJour()%></td>
			</tr>
			<tr>
				<td class="col2">Heures de travail proposées:</td>
				<td><%=offre.getHeures()%></td>
			</tr>
		</table>
		<br>
		<p class="marge">DETAILS DU POSTE :</p>
		<textarea class="zonetexte"><%=offre.getDetail()%></textarea>
		<br>
		<p class="marge">OBSERVATIONS:</p>
		<textarea class="zonetexte"><%=offre.getObservation()%></textarea>
		<br>
		<br>
		<hr>
		<br>
		<h3>PERSONNES POSITIONNEES</h3>
		
		<button id="positionner" onclick="self.location.href='/valence/jsp/positionnementSalarie/nouveauTout.jsp?numoffre=<%=offre.getId_offre() %>'">Positionner</button>
		<br>
		<br>
		<table id="table5">
			<tr class="col1">
				<td>NOM PRENOM</td>
				<td>TEL</td>
				<td>CONTACT</td>
				<td>REFERENT</td>
				<td>REPONSE</td>
				<td>M</td>
				<td>R</td>
				<td>S</td>
			</tr>
			<%
				if (position.size() >0 ){
									for(int i=0;i<position.size();i++){
										boolean url=position.get(i).isRetenu();
										
										String url1="/valence/images/bleu/coche_0.jpg";
										if(url)
											url1="/valence/images/bleu/coche_1.jpg";
			%>
			<tr>
				<td><a
					href="/valence/jsp/accueil/afficheInscrit.jsp?numero=<%=position.get(i).getIdentite().getId_IDE()%>"><%=position.get(i).getIdentite().getNom_IDE()%>
						<%=position.get(i).getIdentite().getPrenom_IDE()%></a></td>
				<td><%=position.get(i).getIdentite().getFixe_IDE()%></td>
				<td><% if(position.get(i).getDatecontact()!=null) out.println(sdf.format(position.get(i).getDatecontact())); %></td>
				<td><%=position.get(i).getSalarie().getNom()%> <%=position.get(i).getSalarie().getPrenom()%></td>
				<td><%=position.get(i).getReponse()%></td>
				<td class="puces"><a
					href="/valence/jsp/employeurs/offres/modification.jsp?numoffrepropo=<%=position.get(i).getId_propo()%>"><img
						src="/valence/images/bleu/mod.png" /></a></td>
				<td class="puces"><a
					href="/valence/controleur?action=offrepropositionretenue&numoffre=<%=numoffre%>&personne=<%=position.get(i).getIdentite().getId_IDE()%>&url=<%=url%>"><img
						src="<%=url1%>" /></a></td>
				<td class="puces"><a
					href="/valence/controleur?action=supprimerpositionnement&numoffre=<%=numoffre%>&personne=<%=position.get(i).getIdentite().getId_IDE()%>"><img
						src="/valence/images/bleu/suppos.png" /></a></td>
			</tr>
			<%
				} }
			%>
		</table>

		<br>
		<hr>
		<br>
		<h3>
			PROPOSITIONS EXACTES
			<%=rome.getIntitule()%></h3>
		<br>
		<table id="listeexactes">
			<thead>
				<tr>
					<th>NOM PRENOM</th>
					<th>COMMUNE</th>
					<th>TEL</th>
					<th>PORTABLE</th>
					<th>PRIO</th>
					<th>DER SUIVI</th>
					<th>PRO</th>
				</tr>
			</thead>
			<tbody>
				<%
					if(listecorrespond.size()>0){
											for(int j=0;j<listecorrespond.size();j++  ){
												Date derniersuivi=suidao.dateDernierSuivi(listecorrespond.get(j));
												//System.out.println("dernier suivi ="+derniersuivi);
												/*if(derniersuivi==null)
													derniersuivi="";*/
				%>
				<tr>
					<td><a href="/valence/jsp/accueil/afficheInscrit.jsp?numero=<%=listecorrespond.get(j).getId_IDE() %>"><%=listecorrespond.get(j).getNom_IDE()%> <%=listecorrespond.get(j).getPrenom_IDE()%></a>
					</td>
					<td><%=listecorrespond.get(j).getVille_IDE()%></td>
					<td><%=listecorrespond.get(j).getFixe_IDE()%></td>
					<td><%=listecorrespond.get(j).getMobile_IDE()%></td>
					<td><%=afficheprio.listePrioritesPersonne(listecorrespond.get(j))%></td>
					<td><% if(derniersuivi!=null)out.println(sdf.format(derniersuivi)); 
					else out.println("01-01-2000");
					%></td>
					<td><a
						href="/valence/jsp/positionnementSalarie/nouveau.jsp?numoffre=<%=numoffre%>&personne=<%=listecorrespond.get(j).getId_IDE()%>"><img
							src="/valence/images/bleu/pos.png" /></a>
				</tr>
				<%
					}
				%>
			</tbody>
			<%
				}
			%>
		</table>

		<br>

		<div id="pager" class="pager4">
			<form>
				<img src="/valence/javascript/images/icons/first.png" class="first" />
				<img src="/valence/javascript/images/icons/prev.png" class="prev" />
				<span class="pagedisplay"></span>
				<!-- this can be any element, including an input -->
				<img src="/valence/javascript/images/icons/next.png" class="next" />
				<img src="/valence/javascript/images/icons/last.png" class="last" />
				<select class="pagesize">
					<option selected="selected" value="10">10</option>
					<option value="20">20</option>
					<option value="50">50</option>
					<option value="100">100</option>
					<option value="99999">Tout</option>
				</select>
			</form>
		</div>
	</div>



	<script type="text/javascript"
		src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
	<script>
		window.jQuery
				|| document
						.write('<script src="/valence/javascript/jquery-2.0.3.js"><\/script>')
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
		src="/valence/javascript/jquery.autosize.min.js"></script>

	<script type="text/javascript"
		src="/valence/javascript/scripts/tableautablesorter.js"></script>
	<script type="text/javascript"
		src="/valence/javascript/scripts/hauteurtextarea.js"></script>


</body>
</html>