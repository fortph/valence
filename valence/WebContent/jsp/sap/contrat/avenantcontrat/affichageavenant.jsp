<%@ page
	import="dao.imp.identite.*,beans.parametres.accueil.*, beans.identite.*,
	java.text.*,dao.imp.ai.*,beans.ai.*,java.util.*,beans.smic.*,dao.imp.sap.*, beans.sap.*"%>
	
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
String id=request.getParameter("personne");
String contrat=request.getParameter("contrat");

IdentiteDAO agdao=new IdentiteDAO();
Identite une=agdao.findByID(Integer.parseInt(id));


AvenantCDIDAO csapdao=new AvenantCDIDAO();
AvenantCDI sap=csapdao.findByID(Integer.parseInt(contrat));

OrganismeDAO ordao=new OrganismeDAO();
Organisme capemploi=ordao.findByID(1);


%>
<link rel="stylesheet" href="/valence/css/sap/affichecontrat.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
	
	


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>affichage avenant sap</title>

		
</head>
<body>




	<div id="body">
		<p><%@ include file='/menus/menusap/menuafficheavenantsap.jsp'%></p>
		<br>
		<div id="creation">AFFICHAGE AVENANT SAP N° A<%=sap.getRangavenent() %>-C<%=sap.getContrat().getId_contratcdi() %></div>
		<br>
		<h3>IDENTITE</h3>
		<table class="table1">
		<tr>
		<td><%=une.getNom_IDE() %> <%=une.getPrenom_IDE() %></td><td><%=une.getCp_IDE() %> <%=une.getVille_IDE() %></td>
		</tr>
		<tr><td>Date accueil :</td><td><% if(une.getDateAccueil_IDE()!=null) out.println(sdf.format(une.getDateAccueil_IDE())); %></td></tr>
		
		</table>
		<br>
		<hr>
		<br>
		<h3>EMPLOYEUR</h3>
		<br>
		
		<table class="table2">
		<tr>
		<td><%=capemploi.getRs() %>-<%=capemploi.getStructure() %>
		
		</td>
		<td>N° Agrément simple : <%=sap.getContrat().getAgrement() %></td>
		
		</tr></table>
		<br>
		<table class="table1">
		<tr>
		<td class="un">DATE REDACTION AVENANT:</td><td class="blanc"><% if(sap.getRedaction()!=null) out.println(sdf.format(sap.getRedaction())); %></td>
		
		</tr>
		<tr>
		<td class="un">DATE EFFET:</td><td class="blanc"><% if(sap.getDateeffet()!=null) out.println(sdf.format(sap.getDateeffet())); %></td>
		
		</tr>
		</table>
		<table class="table1">
		<tr>
		<td  class="un">Descriptif Emploi :</td><td class="blanc"><%=sap.getTache() %>
			</td>
		</tr>
		<tr>
		<td class="un">Nbre Heures Hebdomadaire:</td><td class="blanc"><%=sap.getHeuresminimois() %>
				</tr>
		
		<tr>
		<td class="un">URSSAF N°:</td><td class="blanc"><%=sap.getUrssaf() %>
		</td>
		
		</table>
		<br>
		
		
		<br>
		<table class="table3" >
		<tr>
		<th ></th>
		<th >PAIEMENT</th>
		</tr>
		<tr>
		<td>SALAIRE HOR BRUT:</td>
		<td class="blanche"><%=sap.getSalairehoraire() %>
		</td>
		
		</tr>
		<tr>
		<td>PANIERS:</td>
		<td class="blanche"><% if(sap.getPanier()!=0.0) out.println(sap.getPanier()); %>
		</td>
	
		</tr>
		<tr>
		<td >DEPLACEMENTS:</td>
		<td class="blanche"><% if(sap.getDeplacement()!=0.0)out.println(sap.getDeplacement()); %>
	</td>
		
		</tr>
		
		<tr>
		<td >COMMENTAIRES:</td>
		<td class="blanche"><%=sap.getCommentaire() %>
		</td>
		
		</tr>
		</table>
		<br>
		<br>
			
		
		
		</div>
		
</body>
</html>