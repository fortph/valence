<%@ page
	import="divers.*,java.util.*,beans.employeurs.*,dao.imp.employeur.*,
	beans.parametres.capemploi.*,beans.parametres.accueil.*,java.util.Date,java.text.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/valence/css/employeur/nonpourvu.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>offres non pourvues</title>
<%
	SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
	String categorie = request.getParameter("categorie");
	OffreDAO ofdao = new OffreDAO();
	int total = ofdao.totalOffres();
	int pourvues = ofdao.totalOffresPourvues();
	int annule = ofdao.totalOffresAnnulees();
	int autrerecrute = ofdao.totalOffresAutreRecrute();
	int nonpourvues = ofdao.totalOffresNonPourvues();
	List<Offre> liste = null;
	String titre = "";
	if (categorie==null || categorie.equals( "nonpourvues")) {
		liste = ofdao.offresNonPourvues();
		titre = "OFFRES NON POURVUES";
	} else if (categorie.equals("toutes") ){
		liste = ofdao.findAll();
		titre = "TOUTES LES OFFRES";

	} else if (categorie.equals("pourvues")) {
		liste = ofdao.offresPourvues();
		titre = "OFFRES POURVUES";

	} else if (categorie.equals("annulees")) {
		liste = ofdao.offresAnnulees();
		titre = "OFFRES ANNULEES";

	} else if (categorie.equals("autres")) {
		liste = ofdao.offresAutreRecrutement();
		titre = "OFFRES POURVUES PAR UN TIERS";

	}
%>
</head>
<body>
	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div id="creation">
			CONSULTATION:
			<%=titre%></div>
		<br>
		<hr>
		<table id="table1">
			<tr id="ligne1">
				<td><a
					href="/valence/jsp/employeurs/offres/nonpourvues.jsp?categorie=toutes">Toutes</a></td>
				<td><a
					href="/valence/jsp/employeurs/offres/nonpourvues.jsp?categorie=pourvues">Pourvues</a></td>
				<td><a
					href="/valence/jsp/employeurs/offres/nonpourvues.jsp?categorie=nonpourvues">Non
						pourvues</a></td>
				<td><a
					href="/valence/jsp/employeurs/offres/nonpourvues.jsp?categorie=annulees">Annulées</a></td>
				<td><a
					href="/valence/jsp/employeurs/offres/nonpourvues.jsp?categorie=autres">Autre
						recrutement</a></td>
			</tr>
			<tr id="ligne2">
				<td><%=total%></td>
				<td><%=pourvues%></td>
				<td><%=nonpourvues%></td>
				<td><%=annule%></td>
				<td><%=autrerecrute%></td>
			</tr>
		</table>
		<br>

		<%
			if (categorie=="nonpourvues" || categorie==null ) {
				if (liste.size() > 0) {
		%>
		<table id="table2" border=1>
			<tr>
				<td class="numero">N°</td>
				<td>EMPLOYEUR</td>
				<td>DATE DEBUT</td>
				<td>POSTE</td>
				<td>TYPE</td>
				<td class="taille">P</td>
				<td class="taille">A</td>
				<td class="taille">AR</td>
				<td class="taille">MOD</td>
				
			</tr>
			<tr id="lignes">
				<%
					for (int i = 0; i < liste.size(); i++) {
						String typecontrat=new AfficherTypeContrat().afficher(liste.get(i));
								
				%>
				<td><a
					href="/valence/jsp/employeurs/offres/affichageoffre.jsp?numoffre=<%=liste.get(i).getId_offre()%>"><%=liste.get(i).getId_offre()%></a></td>
				<td class="gauche"><a
					href="/valence/jsp/employeurs/affichage.jsp?numero=<%=liste.get(i).getEmployeur().getId_employeur()%>"><%=liste.get(i).getEmployeur().getRs_employeur()%></a></td>
				<td><% if(liste.get(i).getDatedeb_offre()!=null) out.println(sdf.format(liste.get(i).getDatedeb_offre()));%></td>
				<td><%=liste.get(i).getRome().getIntitule()%></td>
				<td><%=typecontrat%></td>
				<td><a
					href="/valence/controleur?action=modifoffrespourvues&numero=<%=liste.get(i).getId_offre()%>"><input
						type="checkbox" /></a></td>
				<td><a
					href="/valence/controleur?action=modifoffresannulees&numero=<%=liste.get(i).getId_offre()%>"><input
						type="checkbox" /></a></td>
				<td><a
					href="/valence/controleur?action=modifoffresautres&numero=<%=liste.get(i).getId_offre()%>"><input
						type="checkbox" /></a></td>
				<td><a href="/valence/jsp/employeurs/offres/modifcreation.jsp?numoffre=<%=liste.get(i).getId_offre() %>"><img
								src="/valence/images/bleu/mod.png" /></a></td>
			</tr>

			<%
				}
			%>
		</table>
		<%
			}
			} else {
				if (liste.size() > 0) {
		%>
		<table id="table2" border=1>
			<tr>
				<td class="numero">N°</td>
				<td>EMPLOYEUR</td>
				<td>DATE DEBUT</td>
				<td>POSTE</td>
				<td>TYPE</td>
				<td class="taille">P</td>
				<td class="taille">A</td>
				<td class="taille">AR</td>
				<td class="taille">MOD</td>
			</tr>
			<tr id="lignes">
				<%
					for (int i = 0; i < liste.size(); i++) {
								String autre = null, ai = null, cae = null, avenir = null, cdd = null, cdi = null, alternance = null, typecontrat = "";

								boolean autre1 = liste.get(i).isContrat_autre();
								if (autre1)
									autre = "Autre";
								boolean ai1 = liste.get(i).isContrat_ai();
								if (ai1)
									ai = "AI";
								boolean cae1 = liste.get(i).isContrat_cae();
								if (cae1)
									cae = "CAE";
								boolean avenir1 = liste.get(i).isContrat_avenir();
								if (avenir1)
									avenir = "AVENIR";
								boolean cdd1 = liste.get(i).isContrat_cdd();
								if (cdd1)
									cdd = "CDD";
								boolean cdi1 = liste.get(i).isContrat_cdi();
								if (cdi1)
									cdi = "CDI";
								boolean alter1 = liste.get(i).isContrat_alternance();
								if (alter1)
									alternance = "Alternance";

								if (autre != null)
									typecontrat = autre + "-";
								if (ai != null)
									typecontrat += ai + "-";
								if (cae != null)
									typecontrat += cae + "-";
								if (avenir != null)
									typecontrat += avenir + "-";
								if (cdd != null)
									typecontrat += cdd + "-";
								if (cdi != null)
									typecontrat += cdi + "-";
								if (alternance != null)
									typecontrat += alternance;

								if (typecontrat.charAt((typecontrat.length() - 1)) == '-')
									typecontrat = typecontrat.substring(0,(typecontrat.length() - 1));
								
								
								boolean pour=liste.get(i).isPourvue();
								boolean annul=liste.get(i).isAnnule_offre();
								boolean ar=liste.get(i).isAutrerecrute();
								String image1=null,image2=null,image3=null;
								
								
								if (pour)
									image1="/valence/images/bleu/coche_1.jpg";
								else
									image1="/valence/images/bleu/coche_0.jpg";
								if(annul)
									image2="/valence/images/bleu/coche_1.jpg";
								else
									image2="/valence/images/bleu/coche_0.jpg";
								if(ar)
									image3="/valence/images/bleu/coche_1.jpg";
								else
									image3="/valence/images/bleu/coche_0.jpg";
										
				%>
				<td><a
					href="/valence/jsp/employeurs/offres/affichageoffre.jsp?numoffre=<%=liste.get(i).getId_offre()%>"><%=liste.get(i).getId_offre()%></a></td>
				<td class="gauche"><a
					href="/valence/jsp/employeurs/affichage.jsp?numero=<%=liste.get(i).getEmployeur().getId_employeur()%>"><%=liste.get(i).getEmployeur().getRs_employeur()%></a></td>
				<td><% if(liste.get(i).getDatedeb_offre()!=null) out.println(sdf.format(liste.get(i).getDatedeb_offre()));  %></td>
				<td><%=liste.get(i).getRome().getIntitule()%></td>
				<td><%=typecontrat%></td>
				<td><a
					href="/valence/controleur?action=offrepassepourvue&numero=<%=liste.get(i).getId_offre()%>&categorie=<%=categorie%>&colonne=un"><img src="<%=image1 %>"/></a></td>
				<td><a
					href="/valence/controleur?action=offrepassepourvue&numero=<%=liste.get(i).getId_offre()%>&categorie=<%=categorie%>&colonne=deux"><img src="<%=image2 %>"/></a></td>
					
				<td><a
					href="/valence/controleur?action=offrepassepourvue&numero=<%=liste.get(i).getId_offre()%>&categorie=<%=categorie%>&colonne=trois"><img src="<%=image3%>"/></a></td>
				<td><a href="/valence/jsp/employeurs/offres/modifcreation.jsp?numoffre=<%=liste.get(i).getId_offre() %>"><img
								src="/valence/images/bleu/mod.png" /></a></td>	
			</tr>

			<%
				}
			%>
		</table>
		<%
			}
			}
		%>
	</div>
</body>
</html>