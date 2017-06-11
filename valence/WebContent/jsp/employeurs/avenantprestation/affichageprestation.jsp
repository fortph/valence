<%@ page
	import="dao.imp.identite.*,beans.parametres.accueil.*, beans.identite.*,
	java.text.*,dao.imp.employeur.*,beans.employeurs.*,java.util.*,beans.smic.*,dao.imp.sap.*, beans.sap.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	//String id = request.getParameter("personne");
	String contrat = request.getParameter("prestation");
	
	
	AvenantPrestationCDIDAO avdao=new AvenantPrestationCDIDAO();
	AvenantPrestationCDI avenant=avdao.findByID(Integer.parseInt(contrat));
	
	int id_prestcdi=avenant.getPrestation().getId_prestationcontrat();
	
	//IdentiteDAO agdao = new IdentiteDAO();
	Identite une = avenant.getPrestation().getIdentite();
	
	

	EmployeurDAO empdao = new EmployeurDAO();
	Employeur employeur = empdao.findByID(avenant.getPrestation().getEmployeur().getId_employeur());

	TachesSAPDAO tacdao = new TachesSAPDAO();
	TachesSAP tache = tacdao.findByID(avenant.getTache().getId_tache());
	
%>
<link rel="stylesheet" href="/valence/css/sap/afficheprestation.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />




<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>affichage avenant prestation</title>


</head>
<body>

<div id="body">
		<p><%@ include file='/menus/employeur/menuafficheprestationavenantsap.jsp'%></p>
		<br>
		<div id="creation">AFFICHAGE AVENANT PRESTATION SAP N° P<%=id_prestcdi %>-A<%=avenant.getRangavenant() %></div>
		<br>
		
		<h3>UTILISATEUR</h3>
		<br>

		<table class="table2">
			<tr  >
				<td class="un">NOM : </td><td class="blanc"><%=employeur.getRs_employeur()%>-<%=employeur.getId_employeur() %></td>
			</tr>
			<tr>
				<td class="un">ADRESSE :</td><td class="blanc"><%=employeur.getAdr1()%></td>
			</tr>
			<tr>
				<td class="un">CP VILLE :</td><td class="blanc"><%=employeur.getCp()%> <%=employeur.getVille()%></td>

			</tr>
		</table>
		<br>
		<hr>
		<br>
		<h3>PRESTATION</h3>
		<br>
		<table class="table2">
			<tr>
				<td class="une">DATE REDACTION AVENANT:</td>
				<td class="blanc">
					<%
						if (avenant.getRedaction_pr() != null)
							out.println(sdf.format(avenant.getRedaction_pr()));
					%>
				</td>

			</tr>
			</table>
			<table class="table4">
			
			<tr>
				<td class="un">DEBUT AVENANT :</td>
				<td class="blanche">
					<%
						if (avenant.getDatedebut_pr() != null)
							out.println(sdf.format(avenant.getDatedebut_pr()));
					%>
				</td>
				<td class="un centre2">FIN AVENANT :</td>
				<td class="blanche">
					<%
						if (avenant.getDatefin_pr() != null)
							out.println(sdf.format(avenant.getDatefin_pr()));
					%>
				</td>

			</tr>
		</table>
		<table class="table2">
			<tr>
				<td class="un">Descriptif Emploi :</td>
				<td class="blanche"><%=tache.getLibelle()%></td>
			</tr>
			<tr>
				<td class="un">Nbre Heures Hebdomadaire:</td>
				<td class="blanc"><%=avenant.getHeuresminimois_pres()%>
			</tr>



		</table>
		<table class="table2">
			<tr  >
				<td class="un">COMMENTAIRES : </td><td class="blanc"><%=avenant.getCommentaire()%></td>
			</tr>
			</table>
		
		<br>
		<hr>
		<br>
		
		
		<h3>SALARIE</h3>
		<table class="table2">
			<tr>
				<td class="un">NOM</td>
				<td class="blanc3"><%=une.getNom_IDE()%> <%=une.getPrenom_IDE()%></td>
			</tr>
			</table>
		<table class="table2">
			<tr>
				<td class="un">Code Postal: </td><td class="blanc1"><%=une.getCp_IDE() %></td>
					<td class="blanc4">Ville :</td><td class="blanc3"><%=une.getVille_IDE() %></td>
					</tr>
					</table>
		<br><hr> <br>
			<h3>PAIEMENT ET FACTURATION</h3>
		<br>
		<table class="table3">
			<tr>
				<th></th>
				<th>PAIEMENT</th>
				<th>REFACTURATION</th>
			</tr>
			<tr>
				<td>SALAIRE HOR BRUT:</td>
				<td class="tiers blanc"><%=avenant.getSalairehor_av()%></td>
				<td class="tiers blanc"><%=avenant.getFacsalairehor_pr()%></td>
			</tr>
			<tr>
				<td>PANIERS:</td>
				<td class="tiers blanc"><%=avenant.getPanier_av()%></td>
				<td class="tiers blanc"></td>
			</tr>
			<tr>
				<td>DEPLACEMENTS:</td>
				<td class="tiers blanc"><%=avenant.getDeplacement_av()  %></td>
				<td class="tiers blanc"></td>
			</tr>
		</table>
		
		<br> 
		
		<hr>
		<br>
		<h3>DATE RETOUR CONTRAT PRESTATION</h3>
		<br>
			<div id= "bloc"><% if(avenant.getDaterenouvel()!=null) out.println(sdf.format(avenant.getDaterenouvel())); else out.println(" "); %></div>	
		<br>
			


	</div>

</body>
</html>