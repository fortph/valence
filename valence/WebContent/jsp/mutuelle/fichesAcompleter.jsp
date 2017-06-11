<%@ page
	import="dao.imp.identite.*, beans.identite.*,dao.imp.ai.*,beans.ai.*,
	java.util.*,beans.smic.*,beans.employeurs.*,dao.imp.employeur.*,beans.mutuelle.*,dao.mutuelle.*,
	beans.parametres.accueil.*,java.text.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	MutuelleDAO mudao = new MutuelleDAO();
	List<Mutuelle> liste=mudao.fichesAcompleter();
	
%>

<link rel="stylesheet" href="/valence/css/ai/contrat/pardates.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<link rel="stylesheet" href="/valence/javascript/css/TableTools.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables_themeroller.css">



<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>mutuelle incomplete</title>

</head>
<body>




	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div id="creation">FICHES MUTUELLE A COMPLETER</div>
		<br>
		<br>
		<%
		if(liste.size() >0){
		%>
		<table id="mutuelleincomplete" class="display">
			<thead>
			<tr>
			<th>NOM-Prénom</th>
			<th>CP</th>
			<th>VILLE</th>
			<th>Date Proposition</th>
			<th>Date Echéance</th>
			<th>Date Echéance Refus</th>
			</tr>
			</thead>
			<tbody>
			<%
			
			for(int i=0;i<liste.size();i++){
				Identite une=liste.get(i).getIdentite();
			
			%>
			<tr>
				<td class="un"><a href="/valence/jsp/accueil/afficheInscrit.jsp?numero=<%=une.getId_IDE()%>"><%=une.getNom_IDE() %> <%=une.getPrenom_IDE() %></a></td>
				<td><%=une.getCp_IDE()%></td>
				<td class="un"><%=une.getVille_IDE()%></td>
				<td><a href="/valence/jsp/mutuelle/modification.jsp?id_mutuelle=<%=liste.get(i).getId_mutuelle() %>&numero=<%=une.getId_IDE()%>"><%=sdf.format(liste.get(i).getDateProposition())%></a></td>
				<td><% if(liste.get(i).getDateecheance()!=null) out.println(sdf.format(liste.get(i).getDateecheance())); %></td>
				<td><% if(liste.get(i).getDateEcheanceMultiEmp()!=null)out.println(sdf.format(liste.get(i).getDateEcheanceMultiEmp())); %></td>
			</tr>
			
			<%
			}
			%>
			</tbody>
		</table>
		<%
		} else{
			out.println("Il n'y a aucune fiche mutuelle à compléter...");
		
		}
		%>
	</div>
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
			src="/valence/javascript/scripts/journouveaux.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/jquery.dataTables.min.js"></script>
			<script type="text/javascript"
			src="/valence/javascript/scripts/datatabletripardates.js"></script>
			<script type="text/javascript"
			src="/valence/javascript/scripts/tableaux.js"></script>
			<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>
			
		<script type="text/javascript"
			src="/valence/javascript/ZeroClipboard.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/TableTools.min.js"></script>

		
		<br>

</body>
</html>