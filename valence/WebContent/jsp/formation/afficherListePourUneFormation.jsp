<%@page
	import="dao.imp.formation.*,dao.imp.identite.*,beans.formation.*,beans.identite.*,java.util.*,java.io.*,divers.*;"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables_themeroller.css">
<link rel="stylesheet" href="/valence/javascript/css/TableTools.css">
<link rel="stylesheet" href="/valence/css/formation/affichage.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>liste inscrits</title>
<%
	//String nomformation=request.getParameter("nom");
String numeroformation=request.getParameter("numeroformation");


System.out.println("numero formation="+numeroformation);
ListeFormationsDAO formationdao= new ListeFormationsDAO();
ListeFormations formation=formationdao.findByID(Integer.parseInt(numeroformation));
PreInscriptionDAO predao=new  PreInscriptionDAO();
 PreInscription preincrip=new PreInscription();
 preincrip.setListe(formation);
 preincrip.setEnregistre(true);
 List<PreInscription> listepreins=predao.findByCriteria(preincrip);

 //List<Identite> liste=predao.afficherListeEnregistreFormation(numeroformation);
%>
</head>
<body>
	<div id="body">
	<p><%@ include file='/menus/menugeneral/menu.html' %></p><br>
	<%
	if(listepreins.size()>0){
	%>
		<h3><%=listepreins.size()%>
			personnes enregistrées à
			<%=formation.getFormation()%></h3>
		<table id="tableinscritpouruneformation" class="display">
			<thead>
				<tr>
					<th>NOM</th>
					<th>PRENOM</th>
					<th>CODE POSTAL</th>
					<th>VILLE</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<%
					for(int i=0;i<listepreins.size();i++){
						  Identite une=new IdentiteDAO().findByID(listepreins.get(i).getIdentite().getId_IDE());
				%>
				<tr>
					<td><%=une.getNom_IDE()%></td>
					<td><%=une.getPrenom_IDE()%></td>
					<td><%=une.getCp_IDE()%></td>
					<td><%=une.getVille_IDE()%></td>
					<td><a
						href="jsp/formation/modifierpersonneenregistree.jsp?numeroid=<%=listepreins.get(i).getId_formation()%>">Modifier
							</a></td>
				</tr>
				<%
					}
				
					
				%>
			</tbody>
		</table>
		<%
		}
		else
		{
		%>
		<h3>Peronne n'est inscrit à cette formation</h3>
		<%
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
		src="/valence/javascript/jquery.dataTables.min.js"></script>

	<script type="text/javascript"
		src="/valence/javascript/ZeroClipboard.js"></script>
	<script type="text/javascript"
		src="/valence/javascript/TableTools.min.js"></script>

	<script type="text/javascript"
		src="/valence/javascript/scripts/tableaux.js"></script>
</body>
</html>