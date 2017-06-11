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
String idcontrat=request.getParameter("contrat");
ContratDAO contdao=new ContratDAO();
Contrat contrat=contdao.findByID(Integer.parseInt(idcontrat));
AvenantDAO avendao=new AvenantDAO();
List<Avenant> listeavenants=avendao.listeAvenantContrat(contrat);

IdentiteDAO iddao=new IdentiteDAO();
Identite identite=iddao.findByID(contrat.getIdentite().getId_IDE());
EmployeurDAO empdao=new EmployeurDAO();
Employeur employeur=empdao.findByID(contrat.getEmployeur().getId_employeur());

Date deb=contrat.getDebutcontrat();
Date fin=contrat.getFincontrat();
//calcul de la difference entre les 2 dates
long deb1=deb.getTime();
long fin1=fin.getTime();
long diff=fin1-deb1;
long jour=diff/(1000*3600*24);
jour=jour+1;




%>
<link rel="stylesheet" href="/valence/css/ai/contrat/affichage.css">

	

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> contrat ai</title>

</head>
<body>




	<div id="body">
		<p><%@ include file='/menus/menusai/menuaffichecontratai.jsp'%></p>
		<br>
		<div id="creation"> CONTRAT ASSOCIATION INTERMEDIAIRE</div>
		<br>
		<h3>CONTRAT AI (CDD)</h3>
		<table class="table1">
		<tr>
		<td>Contrat Numéro:</td><td class="affiche" >C<%=contrat.getIdaicontrat()%></td><td> rédigé le :</td>
		<td class="affiche" ><% if(contrat.getRedaction()!=null) out.println(sdf.format(contrat.getRedaction()));  %> </td><td>URSSAF N°:</td>
		<td class="affiche" ><%=contrat.getUrssaf() %></td>
		</tr>
		</table>
		<br>
		
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
		<table class="table1">
		<tr>
		<td class="un">UTILISATEUR:</td><td class="de"><%=employeur.getRs_employeur() %></td></tr>
		<tr><td class="un">SERVICE:</td><td class="de"><%=contrat.getService().getService() %></td></tr>
		<tr><td></td><td></td></tr>
		<tr><td>Adresse</td><td class="de"><%=employeur.getAdr1() %></td></tr>
		<tr><td></td><td class="de"><%=employeur.getAdr2() %><br></td></tr>
		<tr><td>Code Postal et Ville</td><td class="de"><%=employeur.getCp() %> <%=employeur.getVille()%></td></tr>
		</table>
		<br>
		<hr>
		<br>
		<table class="table2">
		<tr>
		<th>DEBUT</th>
		<th>FIN</th>
		<th>POSTE</th>
		<th>HEBDO</th>
		<th>DUREE(J)</th>
		</tr>
		<tr>
		<td><% if(deb!=null) out.println(sdf.format(deb)); %></td>
		<td><% if(fin!=null) out.println(sdf.format(fin));  %></td>
		<td><%=contrat.getRome().getIntitule() %></td>
		<td><% if(!contrat.getHeuresminihebdo().equals(""))out.println( contrat.getHeuresminihebdo());%> h</td>
		<td><%=jour %></td></tr>
		</table>
		<br>
		<p>Tâches:</p>
		<p class="deux"> <%=contrat.getTache() %><br></p>
		<br>
		<p>Lieu d'exécution de la tâche:</p>
		<p class="deux"> <%=contrat.getLieu() %><br></p>
		<br>
		<hr>
		<br>
		
		
		<h3>FACTURATION</h3>
		<table class="table3" >
		<tr>
		<th  ></th>
		<th  >PAIEMENT</th>
		<th >FACTURATION</th>
		</tr>
		<tr>
		<td>SALAIRE HOR BRUT:</td>
		<td class="quatre"><%=contrat.getSalairehoraire() %></td>
		
		<td class="quatre"><%=contrat.getFacturation() %></td>
		</tr>
		<tr>
		<td>PANIERS:</td>
		<td class="quatre"><%=contrat.getPanier() %></td>
	
		<td class="quatre"><%=contrat.getFacturepanier() %></td>
		</tr>
		<tr>
		<td >DEPLACEMENTS:</td>
		<td class="quatre"><%=contrat.getDeplacement() %></td>
		
		<td class="quatre"><%=contrat.getFacturedeplace() %></td>
		</tr>
		<tr>
		<td >DIVERS:</td>
		<td class="quatre"><%=contrat.getDivers() %></td>
		
		<td class="quatre"><%=contrat.getFacturedivers() %></td>
		</tr>
		<tr>
		<td >COMMENTAIRES:</td>
		<td class="quatre"><%=contrat.getCommentaire() %></td>
		
		<td class="quatre"><%=contrat.getFacturecommentaire() %></td>
		</tr>
		</table>
		<br>
		
		
		<%
		if (listeavenants.size()> 0){		
			
		%>
		<hr>
		<br>
		<h3>AVENANTS</h3>
		<table class="table3" >
		<tr>
		<th >N°</th>
		<th>Début</th>
		<th>Fin</th>
		<th>Durée</th>
		<th>Facturation</th>
		<th>Date rédaction</th>
		<th>Av</th>
		<th>Dispo</th>
		
		</tr>
		<%
		for(int i=0;i<listeavenants.size();i++){
			Date debav=listeavenants.get(i).getDatedeb();
			Date finav=listeavenants.get(i).getDatefin();
			//calcul de la difference entre les 2 dates
			long debav1=debav.getTime();
			long finav1=finav.getTime();
			long diffav1=finav1-debav1;
			long jourav1=diffav1/(1000*3600*24);
		%>
		<tr>
		<td><a href="/valence/jsp/ai/avenant/affichage.jsp?avenant=<%=listeavenants.get(i).getIdavenant() %>" ><%=listeavenants.get(i).getN_aiavenant() %></a></td>
		<td><%if(debav!=null) out.println(sdf.format(debav)); %></td>
		<td><% if (finav!=null) out.println(sdf.format(finav));  %></td>
		<td><%=jourav1 %></td>
		<td><%=listeavenants.get(i).getFacturation() %></td>
		<td><%if(listeavenants.get(i).getRedaction()!=null) out.println(sdf.format(listeavenants.get(i).getRedaction()));  %></td>
		<td class="imprime"><a href="/valence/jsp/pdf/ai/avenant/avenant.jsp?numeroavenant=<%=listeavenants.get(i).getIdavenant() %>" ><img src="/valence/images/bleu/imprimer.png" /></a></td>
		<td class="imprime"><a href="/valence/jsp/pdf/ai/avenant/dispo.jsp?numeroavenant=<%=listeavenants.get(i).getIdavenant() %>" ><img src="/valence/images/bleu/imprimer.png" /></a></td>
		</tr>
		<%
		}
		%>
		
		</table>
		<%
		}
		%>
		
		
		</div>
		
</body>
</html>