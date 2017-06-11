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
	String idmutuelle = request.getParameter("idmutuelle");

	MutuelleDAO mudao = new MutuelleDAO();
	Mutuelle mutuelle = mudao.findByID(Integer.parseInt(idmutuelle));
	Identite une = mutuelle.getIdentite();
	boolean accept = mutuelle.isAcceptation();
%>
<link rel="stylesheet" href="/valence/css/mutuelle/mutuelle2.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>affiche mutuelle</title>

</head>
<body onload="cache3();">




	<div id="body">
		<p><%@ include file='/menus/mutuelle/menusimple2.jsp'%></p>
		<br>
		<div id="creation">AFFICHAGE SAISIE MUTUELLE</div>
		<br>

		<h3>IDENTITE</h3>
		<table class="table1">
			<tr>
				<td><%=une.getNom_IDE()%> <%=une.getPrenom_IDE()%></td>
				<td><%=une.getCp_IDE()%> <%=une.getVille_IDE()%></td>
			</tr>
			<tr>
				<td>Date accueil :</td>
				<td>
					<%
						if (une.getDateAccueil_IDE() != null)
							out.println(sdf.format(une.getDateAccueil_IDE()));
					%>
				</td>
			</tr>

		</table>
		<br>


		<h3>AFFICHAGE PROPOSITION</h3>
		
			<table class="un">
				<tr>
					<td>Date proposition :</td>
					<td class="decal"><%=sdf.format(mutuelle.getDateProposition())%>
					</td>
				</tr>
				<tr>
					<td>Date Echéance :</td>
					<td class="decal">
						<%
							if (mutuelle.getDateecheance() != null) {
								out.println(sdf.format(mutuelle.getDateecheance()));
							}
						%>
					</td>
				</tr>
				<tr>
					<td>Acceptation :</td>
					<td class="decal">
						<%
							if (accept) {
						%> <label id="oui"> <%
 	out.println("OUI");
 %>
					</label> <%
 	} else {
 %> <label id="non"> <%
 	out.println("NON");
 %>
					</label> <%
 	}
 %>

					</td>
				</tr>
				<tr>
					<td class="cacherrefus">Cause du refus:</td>
					<td class="decal"><%=mutuelle.getCauseRefus()%></td>
				</tr>
				<tr>
					<td class="cacherrefus">Date fin couverture :</td>
					<td class="decal">
						<%
							if (mutuelle.getDateEcheanceMultiEmp() != null)
								out.println(sdf.format(mutuelle.getDateEcheanceMultiEmp()));
						%>

					</td>
				</tr>
			</table>
			<br />
			<button id="droite"
				onclick="self.location.href='/valence/jsp/mutuelle/modification.jsp?id_mutuelle=<%=idmutuelle%>&numero=<%=une.getId_IDE()%>'">Modification</button>
			<br />


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
			src="/valence/javascript/scripts/cachePartiePage3.js"></script>
</body>
</html>