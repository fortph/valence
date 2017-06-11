<%@page
	import="beans.formation.*,dao.imp.formation.*, java.util.*,java.sql.*,dao.exception.*,java.text.*"%>
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
java.sql.Date debut=(java.sql.Date)request.getAttribute("debut");
java.sql.Date fin=(java.sql.Date)request.getAttribute("fin");
List <ListeFormations> liste=(List <ListeFormations>)request.getAttribute("liste");


%>

<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<link rel="stylesheet" href="/valence/javascript/css/TableTools.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables_themeroller.css">
	<link rel="stylesheet" href="/valence/css/formation/affichage.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>listing formations</title>
</head>
<body>
<div id="body">
	<p><%@ include file='/menus/menugeneral/menu.html' %></p><br>
<h3>
	Liste des formations enregistrées en base entre le
	<% if(debut!=null) out.println(sdf.format(debut)); %>
	et le
	<% if(fin!=null) out.println(sdf.format(fin));%></h3>
	<hr><br>
	<%
	if(liste.size()>0){
	%>
<table id="tableformationlistedates" class="display">
	<thead>
		<tr>
			<th>INTITULE DE FORMATION</th>
			<th>ORGANISME DE FORMATION</th>
			<th>NOMBRE DE STAGIAIRES</th>
		</tr>
	</thead>
	<tbody>
	
	<%
	int total=0;
	for(int i=0;i<liste.size();i++){
		OrganismeFormationDAO orgdao=new OrganismeFormationDAO();
		OrganismeFormation org=null;
		PreInscriptionDAO predao=new PreInscriptionDAO();
		PreInscription inscrits=new PreInscription() ;
		String nomformation=liste.get(i).getFormation();
		int idformation=liste.get(i).getId_pformation();
		org=orgdao.findByID(liste.get(i).getOrganis().getId_org());
		String nomorganisme=org.getOrg();
		inscrits.setListe(liste.get(i));
		inscrits.setInscrit(true);
		List<PreInscription> preins=predao.findByCriteria(inscrits);
		int n=preins.size();
		
		total+=n;
		// if(n>0){
	
	%>
	<tr>
	<td><%=nomformation %></td>
	<td><%=nomorganisme %></td>
	<td class="centre"><%=n %>
	</tr>
	<%
	}
		// }
	
	
	%>
	
	</tbody>
	

</table>
<br>
<br>
<div id="total">TOTAL DES STAGIAIRES : <%=total %> </div>
<br>

<%
}
else {
%>
<h3> Aucun resultat trouve</h3>
<%
}
%>




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
		src="/valence/javascript/jquery.dataTables.min.js"></script>

	<script type="text/javascript"
		src="/valence/javascript/ZeroClipboard.js"></script>
	<script type="text/javascript" src="/valence/javascript/TableTools.min.js"></script>

	<script type="text/javascript"
		src="/valence/javascript/scripts/tableaux.js"></script>
		<br>
		
		</div>
</body>
</html>