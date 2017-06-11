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


<title>emplois sap</title>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	String nume=request.getParameter("numero");
	IdentiteDAO idao=new IdentiteDAO();
	Identite pers=idao.findByID(Integer.parseInt(nume));
	PrestationCDIDAO predao=new PrestationCDIDAO();
	AvenantPrestationCDIDAO avdao=new AvenantPrestationCDIDAO();
	List<PrestationCDI> listeemplois=predao.listePrestationsParPersonne(pers);
	EmployeurDAO empdao=new EmployeurDAO();
	Employeur employeur=null;
	TachesSAPDAO tacdao=new TachesSAPDAO();
	TachesSAP tache=null;
%>


</head>
<body>
	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div id="creation"><%=pers.getNom_IDE()%>
			<%=pers.getPrenom_IDE()%>
			: LISTE EMPLOIS SAP OCCUPES
		</div>
		<br>
		<%
			if(listeemplois.size()>0){
		%>

		<table id="sapsalaries">
			<thead>
				<tr>
					<th>Employeur</th>
					<th>Tache</th>
					<th>Début</th>
					<th>Fin</th>
					<th>heures</th>

				</tr>
			</thead>
			<tbody>

				<%
					//pour chaque personne on recupere tous ces contrats
						for(int k=0;k<listeemplois.size();k++ ){
								employeur=empdao.findByID(listeemplois.get(k).getEmployeur().getId_employeur())	;
								tache=tacdao.findByID(listeemplois.get(k).getTache().getId_tache());
				%>

				<tr>
					<td><a
						href="/valence/jsp/employeurs/affichage.jsp?numero=<%=employeur.getId_employeur()%>"><%=employeur.getRs_employeur()%></a></td>
					<td><%=tache.getLibelle()%></td>
					<td>
						<%
							if(listeemplois.get(k).getDatedebut_pr()!=null) out.println(sdf.format(listeemplois.get(k).getDatedebut_pr()));
						%>
					</td>
					<td>
						<%
							if(listeemplois.get(k).getDatefin_pr() !=null) out.println(sdf.format(listeemplois.get(k).getDatefin_pr()));
						%>
					</td>
					<td><%=listeemplois.get(k).getHeuresminimois_pr()%></td>


					<%
						}
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
				else {
		%>
		<p>Aucun emploi occupé pour le moment...</p>
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