<%@page
	import="dao.imp.formation.*,beans.formation.*,java.util.*,java.io.*,divers.*,java.text.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet" href="/valence/css/formation/listeformation.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables_themeroller.css">
	<link rel="stylesheet" href="/valence/javascript/css/TableTools.css">
	

<%
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
	ListeFormationsDAO formation = new ListeFormationsDAO();
	List<ListeFormations> liste = formation.findAll();

	//System.out.println("liste des formations="+liste);
%>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Liste des formations</title>
</head>
<body>
	<div id="body">
	<p><%@ include file='/menus/menugeneral/menu.html' %></p><br>
		
		<div id="creation"> LISTE DES FORMATIONS</div>
		<br>
		<h3><%=liste.size()%>
			formations sont actives
		</h3>
		<br>
		<hr>
		<%
		if(liste.size()!=0){
		%>
		<table id="listeformationsactives"align="center" class="display"  cellpadding="0" cellspacing="0" >
			<thead>
				<tr>
					<th>Libellé</th>
					<th>Oganisme de formation</th>
					<th>Niveau</th>
					<th>Début</th>
					<th>Fin</th>
					<th>Heures</th>
					<th>Actif</th>
					<th>Session</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<%
					for (int i = 0; i < liste.size(); i++) {
				%>
				<tr>
					<td><%=liste.get(i).getFormation()%></td>
					<td><%=liste.get(i).getOrganis().getOrg()%></td>
					<td><%=liste.get(i).getNiveau()%></td>
					<td><% if(liste.get(i).getDatedeb_form()!=null)out.println(sdf.format(liste.get(i).getDatedeb_form())); else out.println("00-00-0000"); %></td>
					<td><% if(liste.get(i).getDatefin_form()!=null) out.println(sdf.format(liste.get(i).getDatefin_form())) ; else out.println("00-00-0000");%></td>
					<td><%=liste.get(i).getHeure_form()%></td>
					
						<%
							if (liste.get(i).isActif()) {
						%> <td>oui</td> <%
							} else {
						%><td> non </td><%
							}
						%>
					
					<td><%=liste.get(i).getSession_form()%></td>
					<td align="center"><a
						href="/valence/jsp/formation/modifierformation.jsp?numero=<%=liste.get(i).getId_pformation()%>"><img
							src="/valence/images/bleu/mod.png" /></a></td>
				</tr>
				<%
					}}
				%>
			</tbody>
		</table>
	
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
	<script type="text/javascript"
		src="/valence/javascript/TableTools.min.js"></script>
	<script type="text/javascript"
		src="/valence/javascript/scripts/datatabletripardates.js"></script>
	<script type="text/javascript"
		src="/valence/javascript/scripts/tableaux.js"></script>
		<br>
		</div>
</body>
</html>
