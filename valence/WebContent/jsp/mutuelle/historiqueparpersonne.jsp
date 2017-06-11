<%@ page
	import="dao.imp.identite.*, beans.identite.*,dao.imp.ai.*,beans.ai.*,
	java.util.*,beans.smic.*,beans.employeurs.*,dao.imp.employeur.*,
	beans.parametres.accueil.*,java.util.*,java.text.*,beans.mutuelle.*,dao.mutuelle.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	String idpersonne = request.getParameter("numero");
	IdentiteDAO idao = new IdentiteDAO();
	Identite identite = idao.findByID(Integer.parseInt(idpersonne));
	MutuelleDAO mudao = new MutuelleDAO();
	List<Mutuelle> liste = mudao.findAllParPersonne(identite);
%>
<link rel="stylesheet" href="/valence/css/ai/contrat/affichage.css">
<link rel="stylesheet" href="/valence/javascript/css/theme.default.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.tablesorter.pager.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />



<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>historique mutuelle</title>

</head>
<body>




	<div id="body">
		<p><%@ include file='/menus/mutuelle/menusimple2.jsp'%></p>
		<br>
		<div id="creation">
			HISTORIQUE MUTUELLE de
			<%=identite.getNom_IDE()%>
			<%=identite.getPrenom_IDE()%></div>
		<br>
	<%
			if(liste.size()>0){
		%>

		<br>
		<h3><%=liste.size()%>
			réponse(s) trouvée(s)
		</h3>
		<br/>
		<table id="mutuelle" >

			<thead>
				<tr>
					<th>Date proposition</th>
					<th>Date échéance</th>
					<th>Acceptation</th>
					<th>Cause Refus</th>
					<th>Date Fin couverture</th>
					<th>Modifier</th>
				</tr>
			</thead>
			<tbody>
				<%
					for (int i = 0; i < liste.size(); i++) {
						Mutuelle mut = liste.get(i);
						boolean accept = mut.isAcceptation();
				%>
				<tr>
					<td><%=sdf.format(mut.getDateProposition())%></td>
					<td>
						<%
							if (mut.getDateecheance() != null)
									out.println(sdf.format(mut.getDateecheance()));
						%>
					</td>
					<td>
						<%
							if (accept)
									out.println("OUI");
								else
									out.println("NON");
						%>
					</td>
					<td><%=mut.getCauseRefus()%></td>
					<td>
						<%
							if (mut.getDateEcheanceMultiEmp() != null)
									out.println(sdf.format(mut.getDateEcheanceMultiEmp()));
						%> <!-- lien modifier -->
					<td><center>
							<a
								href="/valence/jsp/mutuelle/modification.jsp?id_mutuelle=<%=mut.getId_mutuelle()%>&numero=<%=identite.getId_IDE()%>"><img
								src="/valence/images/bleu/mod.png" /></a>
						</center></td>
				</tr>
				<%
				}
			%>
			</tbody>
			
		</table>
		<br>
		<div id="pager" class="pager7">
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
		<br/>
		

<%
		
		}else {
			
			%>

			<br/>
			<h3>Aucune proposition d'adhésion à la mutuelle n'a été proposée</h3>
			<br/>
			<input type="button" value="Créer" id="droite"
				onclick="self.location.href='/valence/jsp/mutuelle/nouveau.jsp?numero=<%=identite.getId_IDE() %>'" />
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
			src="/valence/javascript/jquery.tablesorter.min.js" ></script>
			<script type="text/javascript"
			src="/valence/javascript/jquery.tablesorter.pager.min.js" ></script>
		<script type="text/javascript"
			src="/valence/javascript/scripts/tableautablesorter.js"></script>
</body>
</html>