<%@ page
	import="dao.imp.identite.*, beans.identite.*,dao.imp.ai.*,beans.ai.*,java.util.*,
	java.text.*,dao.imp.rmi.*,beans.rmi.*,dao.imp.suivi.*,beans.suivi.*,beans.parametres.accueil.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/valence/css/rmi/fichecli.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<title>fiche liaison</title>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	String idpersonne = request.getParameter("personne");
	String contrat = request.getParameter("contrat");
	
	

	//on recupere les coordonnees du conseil general 82
	OrganismeDAO orgdao = new OrganismeDAO();
	Organisme organisme = orgdao.findByID(3);

	IdentiteDAO idao = new IdentiteDAO();
	Identite identite = idao.findByID(Integer.parseInt(idpersonne));
	String mr = idao.afficheCivilite(identite);

	FicheRMIDAO ficdao = new FicheRMIDAO();
	FicheRMI fiche = ficdao.recupereDerniereFicheRMI(identite);

	ContratRMIDAO contdao = new ContratRMIDAO();
	ContratRMI contratencours = contdao.findByID(Integer
			.parseInt(contrat));

	FicheLiaisonRMIDAO fldao = new FicheLiaisonRMIDAO();
	FicheLiaisonRMI ficheliaison = fldao
			.recupereFicheCorrespondContrat(contratencours);

	String prescripteur = fiche.getPrescripteur();
	int numpres=1;
	if(!prescripteur.equals("Pole Emploi"))
		numpres=2;
	//System.out.println("numero="+numpres);
	
	ReferentPoleEmploiDAO pedao=new ReferentPoleEmploiDAO();
	ReferentPoleEmploi refepe=pedao.findByID(numpres);
	

	List<SuiviPersonne> listesuivi = contdao.recupereDateSUivi(
			identite, contratencours);
%>
</head>
<body>

<div id="body">
	<%@ include file='/menus/menurmi/menuficheliaison.jsp'%>


	
		<br>
		<div id="creation">AFFICHAGE FICHE LIAISON PDI/RSA</div>
		<br>
		<p><%=mr%>
			<%=identite.getPrenom_IDE()%>
			<%=identite.getNom_IDE()%></p>
		<br>





		<table class="table2">
			<tr>
				<th>Date Bilan</th>
				<th>Nom du référent</th>
				<th>N° contrat</th>
				<th>Début de contrat</th>
				<th>Fin de contrat</th>
			</tr>
			<tr>

				<td class="blanccentre">
					<%
						if (ficheliaison.getDatecreation() != null)
							out.println(sdf.format(ficheliaison.getDatecreation()));
					%>
				</td>

				<td class="blanccentre"><%=fiche.getReferent().getPrenom()%> <%=fiche.getReferent().getNom()%></td>
				<td class="blanccentre"><%=contrat%></td>
				<td class="blanccentre">
					<%
						if (contratencours.getDatedeb_rmicontrat() != null)
							out.println(sdf.format(contratencours.getDatedeb_rmicontrat()));
					%>
				</td>
				<td class="blanccentre">
					<%
						if (contratencours.getDatefin_rmicontrat() != null)
							out.println(sdf.format(contratencours.getDatefin_rmicontrat()));
					%>
				
			</tr>

		</table>
		<br>
		<hr>
		<br>

		<h3>INSERTION SOCIO-PROFESSIONNELLE BILAN INDIVIDUEL</h3>
		<br> <span class="gauchenormal">Prescripteur : <label
			class="grasgauche"><%=prescripteur%></label></span> <br> <br>
		<p class="grasgauche">Coordonnées du Prescripteur</p>
		<table class="table2">

			<tr>

				<td class="un">Nom :</td>
				<td class="grasgauche blanc"><%=fiche.getResponsable()%></td>

			</tr>
			<tr>
				<td>Fonction :</td>
				<td class="grasgauche blanc"><% if(fiche.getFonction()!=null) out.println(fiche.getFonction());%></td>

			</tr>
			<%
				if (refepe != null) {
					//if (prescripteur.equals("Pole Emploi")) {
			%>
			<tr>
				<td>Agence :</td>
				<td class="grasgauche blanc"><%=fiche.getAgence()%></td>
			</tr>
			<tr>
				<td>Nom du référent :</td>
				<td class="grasgauche blanc"><%=refepe.getNom()%>
					<%=refepe.getPrenom()%></td>
			</tr>
			<tr>
				<td>Mail du référent : </td>
				<td class="grasgauche blanc">
				<%=refepe.getMail()%>
			<tr>
				<%
			}
		%>

		</table>
		
		<br>
			<hr>
			<br>
			<h3>OBSERVATIONS DU PRESCRIPTEUR</h3>
			<br>
			<table class="table4">

				<tr>
					<td><textarea rows="5" name="obsprescripteur" id="obsprescripteur">
					<% if(ficheliaison.getObsprescripteur()!=null) out.println(ficheliaison.getObsprescripteur());   %></textarea></td>
				</tr>
			</table>


		<br>
		<hr>
		<br>
		<h3>PRESENCE ET OBJECTIFS FIXES AU PREMIER MOIS PRISE EN CHARGE</h3>
		<br>

		<table class="table4">

			<tr>
				<td><textarea rows="5" name="presence" id="presence">
				<% if(ficheliaison.getPresence()!=null) out.println(ficheliaison.getPresence()); %></textarea></td>
			</tr>
		</table>



		<br>
		<hr>
		<br>
		<h3>DATES DE RENCONTRES</h3>
		<br>
		<table class="table4">

			<tr>
				<td class="centre blanc">
					<%
						if (listesuivi != null)
							for (int i = 0; i < listesuivi.size(); i++)
								out.println(sdf.format(listesuivi.get(i).getDatesuivi())
										+ ", ");
					%>
				</td>
			</tr>
		</table>
		<br>
		<hr>
		<br>
		<h3>RESULTATS OBTENUS</h3>
		<br>
		<table class="table4">

			<tr>
				<th>Freins identifiés en début de parcours</th>
				<th>Freins levés au cours de l'acompagnement</th>
				<th>Freins restant à lever</th>
			</tr>
			<tr>
				<td><textarea rows="5" name="acquis" id="acquis">
				<% if(ficheliaison.getAcquis()!=null) out.println(ficheliaison.getAcquis());%></textarea></td>
				<td><textarea rows="5" name="nonacquis" id="nonacquis">
				<% if(ficheliaison.getNomacquis()!=null) out.println(ficheliaison.getNomacquis());%></textarea>
				</td>
				<td><textarea rows="5" name="encours" id="encours">
				<% if(ficheliaison.getEncours()!=null) out.println(ficheliaison.getEncours()); %></textarea>
				</td>
			</tr>
		</table>


		<br>
		<hr>
		<br>
		<h3>CONCLUSION ET PERSPECTIVES</h3>
		<br>
		<table class="table4">

			<tr>
				<td><textarea rows="5" name="conclusion" id="conclusion">
				<% if(ficheliaison.getConclusion()!=null) out.println(ficheliaison.getConclusion());%></textarea></td>
			</tr>
		</table>


	</div>


</body>
</html>