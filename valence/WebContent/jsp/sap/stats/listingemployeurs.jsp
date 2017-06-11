<%@ page
	import="dao.imp.identite.*, beans.identite.*,dao.imp.sap.*,beans.sap.*,
	java.util.*,java.text.*,dao.imp.employeur.*,beans.employeurs.*"%>
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


<title>employeurs sap</title>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	PrestationCDIDAO predao=new PrestationCDIDAO();
	AvenantPrestationCDIDAO avdao=new AvenantPrestationCDIDAO();
	List<PrestationCDI> listeprestations=null;
	listeprestations=predao.listePrestationsParEmployeurEnCours();
	EmployeurDAO empdao=new EmployeurDAO();
	IdentiteDAO idao=new IdentiteDAO();
	TachesSAPDAO tacdao=new TachesSAPDAO();
	List<AvenantPrestationCDI> listeavenants=null;
	
%>


</head>
<body>
	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div id="creation">LISTE EMPLOYEURS SAP EN COURS</div>
		<br>
		<%
			if(listeprestations.size()>0){
		%>

		<table id="sapsalaries">
			<thead>
				<tr>
					<th>N°</th>
					<th>Employeur</th>
					<th>Date Signature</th>
					<th>Début</th>
					<th>Fin</th>
					<th>Heures Hebdo</th>
					<th>Salarié</th>
					<th>Taches</th>
				</tr>
			</thead>
			<tbody>

				<%
					for(int k=0;k<listeprestations.size();k++ ){
									PrestationCDI prestation=predao.findByID(listeprestations.get(k).getId_prestationcontrat());
									Employeur emp=empdao.findByID(prestation.getEmployeur().getId_employeur());
									Identite une=idao.findByID(prestation.getIdentite().getId_IDE());
									TachesSAP tache=tacdao.findByID(prestation.getTache().getId_tache());
				%>

				<tr>
					<td><a
						href="/valence/jsp/employeurs/prestations/affichageprestation.jsp?prestation=<%=prestation.getId_prestationcontrat()%>">
							P<%=prestation.getId_prestationcontrat()%>
					</a></td>
					<td><a
						href="/valence/jsp/employeurs/affichage.jsp?numero=<%=emp.getId_employeur()%>"><%=emp.getRs_employeur()%>
					</a></td>
					<td>
						<%
							if(prestation.getRedaction_pr()!=null) out.println(sdf.format(prestation.getRedaction_pr()));
						%>
					</td>
					<td>
						<%
							if(prestation.getDatedebut_pr()!=null) out.println(sdf.format(prestation.getRedaction_pr()));
						%>
					</td>
					<td>
						<%
							if(prestation.getDatefin_pr()!=null) out.println(sdf.format(prestation.getDatefin_pr()));
						%>
					</td>
					<td class="centre"><%=prestation.getHeuresminimois_pr()%></td>
					<td><a
						href="/valence/jsp/accueil/afficheInscrit.jsp?numero=<%=une.getId_IDE()%>">
							<%=une.getNom_IDE()%> <%=une.getPrenom_IDE()%>
					</a></td>
					<td><%=tache.getLibelle()%></td>
				</tr>
				<%
					}
					//pour chaque prestation on recupere les avenants
							listeavenants=avdao.listeAvenantsPrestationsParEmployeurEnCours();
							if(listeavenants!=null){	
								for(int j=0;j<listeavenants.size();j++){
									Identite identite=idao.findByID(listeavenants.get(j).getPrestation().getIdentite().getId_IDE());
									Employeur employeur=empdao.findByID(listeavenants.get(j).getPrestation().getEmployeur().getId_employeur());
				%>
				<tr>
					<td><a
						href="/valence/jsp/employeurs/avenantprestation/affichageprestation.jsp?prestation=<%=listeavenants.get(j).getId_prestationavenant()%>">
						P<%=listeavenants.get(j).getId_prestationavenant() %>-A<%=listeavenants.get(j).getRangavenant() %>
					</a></td>
					<td><a
						href="/valence/jsp/employeurs/affichage.jsp?numero=<%=employeur.getId_employeur()%>"><%=employeur.getRs_employeur()%>
					</a></td>
					<td>
						<%
							if(listeavenants.get(j).getRedaction_pr()!=null) out.println(sdf.format(listeavenants.get(j).getRedaction_pr()));
						%>
					</td>
					<td>
						<%
							if(listeavenants.get(j).getDatedebut_pr()!=null) out.println(sdf.format(listeavenants.get(j).getDatedebut_pr()));
						%>
					</td>
					<td>
						<%
							if(listeavenants.get(j).getDatefin_pr()!=null) out.println(sdf.format(listeavenants.get(j).getDatefin_pr()));
						%>
					</td>
					<td class="centre"><%=listeavenants.get(j).getHeuresminimois_pres()%></td>
					<td><a
						href="/valence/jsp/accueil/afficheInscrit.jsp?numero=<% if(listeavenants.get(j)!=null)
							out.println(identite.getId_IDE()); %>" >
							<% if(listeavenants.get(j)!=null)
								out.println(identite.getNom_IDE()+" "+
										identite.getPrenom_IDE()); %>
					</a></td>
					<td><%=listeavenants.get(j).getTache().getLibelle() %></td>
				</tr>

				<%
					}}
				%>
			</tbody>

		</table>
		
		<br>
		<div id="pager" class="pager5">
			<img src="/valence/javascript/images/icons/first.png" class="first" />
			<img src="/valence/javascript/images/icons/prev.png" class="prev" />
			<span class="pagedisplay"></span>
			<!-- this can be any element, including an input -->
			<img src="/valence/javascript/images/icons/next.png" class="next" />
			<img src="/valence/javascript/images/icons/last.png" class="last" />
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
			src="/valence/javascript/jquery.tablesorter.min.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/jquery.tablesorter.pager.min.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/scripts/tableautablesorter.js"></script>

	</div>
</body>
</html>