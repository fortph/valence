<%@ page
	import="dao.imp.identite.*, beans.identite.*,dao.imp.ai.*,beans.ai.*,java.util.*,
	java.text.*,dao.imp.rmi.*,beans.rmi.*,beans.parametres.accueil.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/valence/css/rmi/fichesuite.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>fiche rmi</title>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	String idpersonne = request.getParameter("personne");

	Date jour = new Date();

	IdentiteDAO idao = new IdentiteDAO();
	Identite identite = idao.findByID(Integer.parseInt(idpersonne));

	String mr = idao.afficheCivilite(identite);

	FicheRMIDAO ficdao = new FicheRMIDAO();
	FicheRMI fiche = ficdao.recupereDerniereFicheRMI(identite);
	
	
	
	String pres=fiche.getPrescripteur();
	//System.out.println("pres ="+pres);
	int numpres=1;
	if(!pres.equals("Pole Emploi"))
		numpres=2;
	//System.out.println("numero="+numpres);
	
	ReferentPoleEmploiDAO pedao=new ReferentPoleEmploiDAO();
	ReferentPoleEmploi refepe=pedao.findByID(numpres);
	
	

	ContratRMIDAO contdao = new ContratRMIDAO();
	List<ContratRMI> listecontratsrmi = contdao.contratRMIParPersonne(identite);
	ContratRMI derniercontrat = contdao
			.recupereDernierContratRMI(identite);
	
	

	ConvocRMIDAO convocdao = new ConvocRMIDAO();
	ConvocRMI convoc = null;
	int idconvoc = 0;
	if(fiche!=null){
	idconvoc = convocdao.recupereDerniereDateConvocation(fiche);	
	if (idconvoc != 0)
		convoc = convocdao.findByID(idconvoc);
	}

	//affiche une image differente en fonction de la date de fin du dernier contrat
	String urlencours = "/valence/images/bleu/encours_0.png";
	if(derniercontrat!=null){
	if (derniercontrat.getDatefin_rmicontrat() != null
			&& derniercontrat.getDatefin_rmicontrat().compareTo(jour) >= 0)
		urlencours = "/valence/images/bleu/encours.png";
	}
	//affiche une image differente si la fiche de liaison du contrat est crée ou pas
	String imgficheliaisonoui="/valence/images/bleu/voir.png";
	String imgficheliaisonnon="/valence/images/bleu/creer.png";
	String afficheimageliaison="";
	FicheLiaisonRMIDAO fldao=new FicheLiaisonRMIDAO();
	FicheLiaisonRMI ficheliaison=null;
	int idficheliaison=0;
	
	//aiguillage different si la fiche est créé ou a modifier
	String aiguillage="";
	String urlimpressionon="/valence/images/bleu/imprimer.png";
	String urlimpressionoff="/valence/images/bleu/imprimeroff.png";
	String aiguilleimpression="";
	String legende="";
	String pdfficheliaison="";
%>
</head>
<body>
<div id="body">

	<%@ include file='/menus/menurmi/menufichermi.jsp'%>


	
		<br>
		<div id="creation">FICHE RSA</div>
		<br>
		<p><%=mr%>
			<%=identite.getPrenom_IDE()%>
			<%=identite.getNom_IDE()%></p>
		<br>
		<table class="table1">
			<tr>
				<td align="right" class="un"><img src="<%=urlencours%>"
					id="enveloppe"></td>
				<td class="gras">
					<%
					if(derniercontrat!=null){
						if (derniercontrat.getDatefin_rmicontrat() != null)
							out.println(" jusqu'au : "
									+ sdf.format(derniercontrat.getDatefin_rmicontrat()));
					}
					%>
				</td>
				<td></td>
				<td></td>
				<td class="centre"><button
						onclick="self.location.href='/valence/jsp/rmi_rsa/fiches/convocation.jsp?personne=<%=idpersonne%>' ">CONVOQUER</button></td>
			</tr>
			<tr>
				<td class="un">Date d'accueil :</td>
				<td class="gras">
					<%
						if (identite.getDateAccueil_IDE() != null)
							out.println(sdf.format(identite.getDateAccueil_IDE()));
					%>
				</td>
				<td>Date de création fiche : <span class="gras" > <%
					if (fiche.getCreation_rmi() != null)
						out.println(sdf.format(fiche.getCreation_rmi()));
				%></span></td>

				<td>
			</tr>
			<tr>
				<td class="un">Prescripteur :</td>
				<td class="gras" ><%=fiche.getPrescripteur()%></td>
				<td>Référent Cap 2000 : <span class="gras" ><%=fiche.getReferent().getNom()%> <%=fiche.getReferent().getPrenom()%></span></td>
			</tr>
<tr>
				<td class="un">Nom Conseiller :</td>
				<td class="gras"><%=fiche.getResponsable()%></td>
				<td>Fonction : <span class="gras" ><% if(fiche.getFonction()!=null) out.println(fiche.getFonction()); %></span></td>
				</tr>
				<%
				if (fiche.getPrescripteur().equals("Pole Emploi")){
				%>
				<tr>
				<td class="un">Agence :</td>
				<td class="gras"><%=fiche.getAgence()%></td>
				
				<%
				}
				
				%>
				
			</tr>			
			<tr>
				<td class="un">Dernière convocation le :</td>
				<td class="gras" >
					<%
						if (convoc != null && convoc.getDateconvoc_rmiconvoc() != null)
							out.println(sdf.format(convoc.getDateconvoc_rmiconvoc()));
					%>
				</td>
				<td></td>
				<td></td>
				<td class="centre">
					<a href='/valence/jsp/rmi_rsa/pdf/convocation.jsp?convocation=<%=idconvoc%>' target='_blank'>
					<button type=button>
						IMP	CONVOC</button></a>
				</td>
			</tr>		
		<tr>
		<td>&nbsp;</td>
		</tr>		
		<tr class="gras" id="mdfond">
		
				<td colspan="2" >Nom Référent <%=fiche.getPrescripteur() %> :</td>
				<td id="mdfond" class="gras"><% if(refepe!=null) {out.println(refepe.getNom()+" "+refepe.getPrenom()); %></td>
				<td></td>				
				<td class="centre">
					<button
						onclick="self.location.href='/valence/jsp/rmi_rsa/fiches/modifierReferentPoleEmploi.jsp?ref=<%=refepe.getId_referent()%>&pe=<%=pres%>&pers=<%=idpersonne%>'">
						Modifier</button>
				</td>
				<%
				}
				%>
				</tr>
				</table>
		<br>
		
		<hr>
		<br>
		<h3>CONTRAT INSERTION</h3>
		<br>
		<table class="table2">
			<tr>
				<th>RANG</th>
				<th>DEBUT</th>
				<th>FIN</th>
				<th>MODIF</th>
				<th>CLI</th>
				<th>CLI</th>
			</tr>
			<%
				for (int i = 0; i < listecontratsrmi.size(); i++) {
			%>

			<tr>
				<td>Contrat Insertion numéro : <%=listecontratsrmi.get(i).getId_rmicontrat()%></td>
				<td class="centre">
					<%
						if (listecontratsrmi.get(i).getDatedeb_rmicontrat() != null)
								out.println(sdf.format(listecontratsrmi.get(i)
										.getDatedeb_rmicontrat()));
					%>
				</td>
				<td class="centre">
					<%
						if (listecontratsrmi.get(i).getDatefin_rmicontrat() != null)
								out.println(sdf.format(listecontratsrmi.get(i)
										.getDatefin_rmicontrat()));
					%>
				</td>
				<td class="colpetite"><a
					href="/valence/jsp/rmi_rsa/contrat/modification.jsp?contrat=<%=listecontratsrmi.get(i).getId_rmicontrat()%>&personne=<%=idpersonne%>" 
					title="Modification du contrat" ><img
						src="/valence/images/bleu/mod.png"></a></td>
						<%
						if (fldao.ficheLiaisonCree(listecontratsrmi.get(i))){
								afficheimageliaison=imgficheliaisonoui;
								aiguillage="/valence/jsp/rmi_rsa/fiches/afficherfichecli.jsp";
								aiguilleimpression=urlimpressionon;
								legende="Voir la fiche de liaison";
								ficheliaison=fldao.recupereFicheCorrespondContrat(listecontratsrmi.get(i));
								idficheliaison=ficheliaison.getId_ficheliaison();
								
								/*aiguillage different en fonction du prescripteur*/
								//if(fiche.getPrescripteur().equals("Pole Emploi")){
										//pdfficheliaison="/valence/jsp/rmi_rsa/pdf/fichepoleemploi.jsp";
									//	}
							//	else{
								pdfficheliaison="/valence/jsp/rmi_rsa/pdf/fichecg82.jsp";
									
								//}
						}
						else {
							afficheimageliaison=imgficheliaisonnon;
							aiguillage="/valence/jsp/rmi_rsa/fiches/fichecli.jsp";
							aiguilleimpression=urlimpressionoff;
							legende="Création de la fiche de liaison";
						}
						%>
				<td class="colpetite"><a href="<%=aiguillage %>?contrat=<%=listecontratsrmi.get(i).getId_rmicontrat()%>&personne=<%=idpersonne%>"  title="" ><img
						src="<%=afficheimageliaison %>" title="<%=legende %>" ></a></td>
						
						
				<% if(aiguilleimpression.equals(urlimpressionoff)){
				
				%>
				
				<td class="colpetite">
				
				<img src="<%=aiguilleimpression%>" /></td>
				<%
				}
				else {
				%>
						<td class="colpetite">
						<a href="<%=pdfficheliaison%>?fiche=<%=idficheliaison %>" target="_blank"><img
						src="<%=aiguilleimpression%>"></a></td>
						<%
						}
						%>

			</tr>
			<%
				}
			%>
		</table>



	</div>
</body>
</html>