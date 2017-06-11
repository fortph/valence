<%@ page
	import="dao.imp.identite.*, beans.identite.*,dao.imp.ai.*,beans.ai.*,
	java.util.*,beans.smic.*,beans.employeurs.*,dao.imp.employeur.*,
	beans.parametres.accueil.*,java.text.*"%>
	
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
String avena=request.getParameter("avenant");
AvenantDAO avedao=new AvenantDAO();
Avenant avenant=avedao.findByID(Integer.parseInt(avena));

Date deb=avenant.getDatedeb();
Date fin=avenant.getDatefin();
//calcul de la difference entre les 2 dates
long deb1=deb.getTime();
long fin1=fin.getTime();
long diff=fin1-deb1;
long jour=diff/(1000*3600*24);
jour=jour+1;

ContratDAO contdao=new ContratDAO();
Contrat contrat=contdao.findByID(avenant.getContrat().getIdaicontrat());

IdentiteDAO iddao=new IdentiteDAO();
Identite identite=iddao.findByID(contrat.getIdentite().getId_IDE());


%>
<link rel="stylesheet" href="/valence/css/ai/avenant/affichage.css">

	

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> contrat ai</title>

</head>
<body>




	<div id="body">
		<p><%@ include file='/menus/menusai/menuafficheavenant.jsp'%></p>
		<br>
		<div id="creation"> AVENANT AI</div>
		<br>
		<h3>ETAT CIVIL</h3>
		
		
		<table class="table1">
		<tr>
		<td  class="un">NOM, Prénom</td><td  class="de"><%=identite.getNom_IDE() %> <%=identite.getPrenom_IDE() %></td></tr>
		<tr><td  class="un">Adresse</td><td class="de"><%=identite.getAdr1_IDE() %></td></tr>
		<tr><td></td><td class="de"><%=identite.getAdr2_IDE() %><br></td></tr>
		<tr><td>Code Postal et Ville</td><td class="de"><%=identite.getCp_IDE() %> <%=identite.getVille_IDE() %></td></tr>
		<tr><td>Né(e) le </td><td class="de"><% if(identite.getDatenais_IDE()!=null) out.println(sdf.format(identite.getDatenais_IDE())); %></td></tr>
		<tr><td>N° de Sécurité Sociale</td><td class="de"><%=identite.getNss_IDE() %></td></tr>
		
		<tr>
		</table>
		<br><hr>
		<br>
		
		<h3>AVENANT CDD</h3>
		<table class="table2">
		<tr>
		<th>Avenant N°</th>
		<th>DEBUT</th>
		<th>FIN</th>
		<th>DUREE(J)</th>
		<th>HEBDO</th>
		</tr>
		<tr>
		<td><%=avenant.getN_aiavenant()  %></td>
		<td><% if(deb!=null) out.println(sdf.format(deb)); %></td>
		<td><% if(fin!=null) out.println(sdf.format(fin)); %></td>
		<td><%=jour %></td>
		<td><%=avenant.getDureehebdomadaire() %></td></tr>
		</table>
		<br>
		<p>Tâches:</p>
		<p class="deux"> <%=avenant.getTache()%><br></p>
		<br>
		<p>Lieu d'exécution de la tâche:</p>
		<p class="deux"> <%=avenant.getLieu() %><br></p>
		<br>
		<hr><br>		
		
		<h3>FACTURATION</h3>
		<table class="table3" >
		<tr>
		<th  ></th>
		<th  >PAIEMENT</th>
		<th >FACTURATION</th>
		</tr>
		<tr>
		<td>SALAIRE HOR BRUT:</td>
		<td class="quatre"><%=avenant.getSalairehoraire() %></td>
		
		<td class="quatre"><%=avenant.getFacturation() %></td>
		</tr>
		<tr>
		<td>PANIERS:</td>
		<td class="quatre"><%=avenant.getPanier() %></td>
	
		<td class="quatre"><%=avenant.getFacturepanier() %></td>
		</tr>
		<tr>
		<td >DEPLACEMENTS:</td>
		<td class="quatre"><%=avenant.getDeplacement() %></td>
		
		<td class="quatre"><%=avenant.getFacturedeplace() %></td>
		</tr>
		<tr>
		<td >DIVERS:</td>
		<td class="quatre"><%=avenant.getDivers() %></td>
		
		<td class="quatre"><%=avenant.getFacturedivers() %></td>
		</tr>
		<tr>
		<td >COMMENTAIRES:</td>
		<td class="quatre"><%=avenant.getCommentaire() %></td>
		
		<td class="quatre"><%=avenant.getFacturecommentaire() %></td>
		</tr>
		</table>
		<br>
		
		<p id="droite">DATE DE REDACTION : <span id="datedroite" ><% if(avenant.getRedaction()!=null) out.println(sdf.format(avenant.getRedaction())); %></span></p>
		<br>
		
		<br>
				
		
		</div>
		
</body>
</html>