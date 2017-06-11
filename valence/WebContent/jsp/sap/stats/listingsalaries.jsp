<%@ page
	import="dao.imp.identite.*, beans.identite.*,dao.imp.sap.*,beans.sap.*,
	java.util.*,java.text.*,dao.imp.ai.*,beans.ai.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript"
	src="/valence/javascript/scripts/attention.js"></script>
<link rel="stylesheet" href="/valence/css/sap/fichesuite.css">
<link rel="stylesheet" href="/valence/javascript/css/theme.default.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.tablesorter.pager.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<title>salaries sap</title>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	
	ContratCDIDAO condao = new ContratCDIDAO();
	AvenantCDIDAO avedao=new AvenantCDIDAO();
	IdentiteDAO idao=new IdentiteDAO();
	//recupere le contrat en cours de chaque personne
	ContratCDI listecontrats=null;
	//on recupere toutes les personnes en contrat SAP
	List<Integer> listesalaries = condao.sapContrats();
	
	//pour chaque contrat on recupere les avenants
	List<AvenantCDI> listeAvenants=null;
%>


</head>
<body>
	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div id="creation">LISTE SALARIES CONTRAT SAP (CDI)</div>
		<br>
		<%
		if(listesalaries.size()>0){
		%>

		<table id="salariesap" >
			<thead>
				<tr>
					<th>Identite</th>
					<th>N° Contrat</th>
				<!-- 	<th>Date Signature</th> -->
					<th>Début</th>
					<th>Fin</th>
					<th>Heures Hebdo</th>
					<th>Emplois occupés</th>
				</tr>
			</thead>
			<tbody>

				<%	//pour chaque personne on recupere tous ces contrats
				for(int k=0;k<listesalaries.size();k++ ){
					Identite une=idao.findByID(listesalaries.get(k));
					listecontrats=condao.dernierContrat(une.getId_IDE());
					if(listecontrats!=null){
					
				%>
				
				<tr>
				<td><a href="/valence/jsp/accueil/afficheInscrit.jsp?numero=<%=une.getId_IDE()%>"><%=une.getNom_IDE()%> <%=une.getPrenom_IDE()%></a></td>
				<td><a href="/valence/jsp/sap/contrat/affichagecontrat.jsp?contrat=<%=listecontrats.getId_contratcdi()%>&personne=<%=une.getId_IDE() %>">C<%=listecontrats.getId_contratcdi()%></a></td>
				<!-- <td>
					<%
						if(listecontrats.getRedaction()!=null) out.println(sdf.format(listecontrats.getRedaction()));
					%>
				</td>
				-->
				<td>
					<%
						if(listecontrats.getEmbauche()!=null) out.println(sdf.format(listecontrats.getEmbauche()));
					%>
				</td>
				<td>
					<%
						if(listecontrats.getTermecontrat()!=null) out.println(sdf.format(listecontrats.getTermecontrat()));
					%>
				</td>
				<td><%=listecontrats.getHeuresminimois()%></td>
				
				<td><a href="/valence/jsp/sap/stats/listeemploisoccupes.jsp?numero=<%=une.getId_IDE()%>">afficher</a></td>
				</tr>
				<%
				}}
				for(int k=0;k<listesalaries.size();k++ ){
					Identite une=idao.findByID(listesalaries.get(k));
					listecontrats=condao.dernierContrat(une.getId_IDE());
					if(listecontrats!=null){
					//pour chaque contrat on recupere les avenants
					listeAvenants=avedao.listeAvenantContratCDI(listecontrats);
					if(listeAvenants.size()>0){	
						for(int j=0;j<listeAvenants.size();j++){
				%>
		
				<tr>
					<td><a href="/valence/jsp/accueil/afficheInscrit.jsp?numero=<%=une.getId_IDE()%>"><%=une.getNom_IDE()%> <%=une.getPrenom_IDE()%></a></td>
					<td><a href="/valence/jsp/sap/avenantcontrat/affichageavenant.jsp?contrat=<%=listeAvenants.get(j).getId_avenant() %>&personne=<%=une.getId_IDE() %>">A<%=listeAvenants.get(j).getRangavenent()%> C<%=listecontrats.getId_contratcdi()%></a></td>
				<!--
					<td>
					 	<%
							if(listeAvenants.get(j).getRedaction()!=null) out.println(sdf.format(listeAvenants.get(j).getRedaction()));
						%>
					</td>
					-->
					<td>
						<%
							if(listeAvenants.get(j).getDateeffet()!=null) out.println(sdf.format(listeAvenants.get(j).getDateeffet()));
						%>
					</td>
					<td>
					 	<%
					 	if(listecontrats.getTermecontrat()!=null) out.println(sdf.format(listecontrats.getTermecontrat()));
						%>
					</td>
					
					<td><%=listeAvenants.get(j).getHeuresminimois()%></td>
					<td><a href="/valence/jsp/sap/stats/listeemploisoccupes.jsp?numero=<%=une.getId_IDE()%>">afficher</a></td>
				</tr>
				
				<%
				}}}}
				%>
			</tbody>
			
			
		</table>
		
		<br>
		<div id="pager" class="pager5">
  <img src="/valence/javascript/images/icons/first.png" class="first"/>
    <img src="/valence/javascript/images/icons/prev.png" class="prev"/>
    <span class="pagedisplay"></span> <!-- this can be any element, including an input -->
    <img src="/valence/javascript/images/icons/next.png" class="next"/>
    <img src="/valence/javascript/images/icons/last.png" class="last"/>
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
			src="/valence/javascript/jquery.tablesorter.min.js" ></script>
			<script type="text/javascript"
			src="/valence/javascript/jquery.tablesorter.pager.min.js" ></script>
		<script type="text/javascript"
			src="/valence/javascript/scripts/tableautablesorter.js"></script>
	
	</div>
	</body>
	</html>