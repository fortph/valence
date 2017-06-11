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
	
	
	PrestationCDIDAO prestdao=new PrestationCDIDAO();
	PrestationCDI prestation=prestdao.findByID(Integer.parseInt(contrat));
	
	
	
	IdentiteDAO agdao = new IdentiteDAO();
	Identite une = agdao.findByID(prestation.getIdentite().getId_IDE());

	OrganismeDAO ordao = new OrganismeDAO();
	Organisme capemploi = ordao.findByID(1);

	

	EmployeurDAO empdao = new EmployeurDAO();
	Employeur employeur = empdao.findByID(prestation.getEmployeur()
			.getId_employeur());

	TachesSAPDAO tacdao = new TachesSAPDAO();
	TachesSAP tache = tacdao.findByID(prestation.getTache()
			.getId_tache());
%>
<link rel="stylesheet" href="/valence/css/sap/afficheprestation.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />




<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>affichage prestation sap</title>


</head>
<body>




	<div id="body">
		<p><%@ include file='/menus/employeur/menuafficheprestationsap.jsp'%></p>
		<br>
		<div id="creation">AFFICHAGE PRESTATION SAP N° P<%=contrat %></div>
		
		
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
				<td class="une">DATE REDACTION PRESTATION:</td>
				<td class="blanc">
					<%
						if (prestation.getRedaction_pr() != null)
							out.println(sdf.format(prestation.getRedaction_pr()));
					%>
				</td>

			</tr>
			</table>
			<table class="table4">
			
			<tr>
				<td class="un">DEBUT PRESTATION :</td>
				<td class="blanche">
					<%
						if (prestation.getDatedebut_pr() != null)
							out.println(sdf.format(prestation.getDatedebut_pr()));
					%>
				</td>
				<td class="un centre2">FIN PRESTATION :</td>
				<td class="blanche">
					<%
						if (prestation.getDatefin_pr() != null)
							out.println(sdf.format(prestation.getDatefin_pr()));
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
				<td class="blanc"><%=prestation.getHeuresminimois_pr()%>
			</tr>



		</table>
		
		<table class="table2">
			<tr  >
				<td class="un">COMMENTAIRES : </td><td class="blanc"><%=prestation.getCommentaire_pr()%></td>
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
				<td class="tiers blanc"><%=prestation.getSalairehor_pr()%></td>
				<td class="tiers blanc"><%=prestation.getFacsalairehor_pr()%></td>
			</tr>
			<tr>
				<td>PANIERS:</td>
				<td class="tiers blanc"><%=prestation.getPanier_pr()%></td>
				<td class="tiers blanc"></td>
			</tr>
			<tr>
				<td>DEPLACEMENTS:</td>
				<td class="tiers blanc"><%=prestation.getDeplacement_pr()  %></td>
				<td class="tiers blanc"></td>
			</tr>
		</table>
		
		<br> 
		
		<hr>
		<br>
		<h3>DATE RETOUR CONTRAT PRESTATION</h3>
		<br>
			<div id= "bloc"><% if(prestation.getDaterenouvel()!=null) out.println(sdf.format(prestation.getDaterenouvel())); else out.println(" "); %></div>	
		<br>
		
	</div>

</body>
</html>