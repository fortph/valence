<%@page
	import="dao.imp.formationpro.*,beans.formationpro.*,java.util.*,java.io.*,divers.*" %>
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/valence/css/formationpro/listeformation.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables_themeroller.css">
	<link rel="stylesheet" href="/valence/javascript/css/TableTools.css">
	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>liste employeurs</title>
<%
FormationProEmployeurDAO empdao = new FormationProEmployeurDAO();
FormationProEmployeur emp=new FormationProEmployeur();
emp.setActif(true);
	List<FormationProEmployeur> liste = empdao.findByCriteria(emp);
if(liste.size()!=0){
	//System.out.println("liste des formations="+liste);
%>


</head>
<body>
	<div id="body">
	<p><%@ include file='/menus/menugeneral/menu.html' %></p><br>
		<h1>Liste des Employeurs actifs</h1>
		<h3><%=liste.size()%>
			employeurs référencés
		</h3>
		<br>
		<hr>
		
		<table id="tablelisteemployeurss"align="center"width="800" class="display"  cellpadding="0" cellspacing="0" >
			<thead>
				<tr>
					<th>NOM</th>
					<th>ADRESSE</th>
					<th>CP</th>
					<th>VILLE</th>
					<th>Tél.</th>
					<th>MOBILE</th>
					<th>STATUT</th>
					<th>Actif</th>
					<th></th>
					
				</tr>
			</thead>
			<tbody>
				<%
					for (int i = 0; i < liste.size(); i++) {
				%>
				<tr>
					<td><%=liste.get(i).getEmployeur() %></td>
					<td><%=liste.get(i).getAdresse() %></td>
					<td><%=liste.get(i).getCp() %></td>
					<td><%=liste.get(i).getVille() %></td>
					<td><%=liste.get(i).getTel1() %></td>
					<td><%=liste.get(i).getTel2()  %></td>
					<td><%=liste.get(i).getStatut().getStatut()   %></td>
									
					
						<%
							if (liste.get(i).isActif()) {
						%> <td>oui</td> <%
							} else {
						%><td> non </td><%
							}
						%>
					
						<td align="center"><a
						href="/valence/jsp/formationpro/modifieremployeur.jsp?numero=<%=liste.get(i).getId_employeur()%>"><img
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
		src="/valence/javascript/scripts/tableaux.js"></script>
		<br>
		</div>
</body>
</html>
