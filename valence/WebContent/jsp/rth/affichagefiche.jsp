<%@ page
	import="dao.imp.identite.*,beans.rth.*,dao.imp.rth.*, beans.identite.*,java.util.Date,java.text.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String id = request.getParameter("personne");
	IdentiteDAO iddao = new IdentiteDAO();
	Identite un = iddao.findByID(Integer.parseInt(id));
	FicheDAORTH ficdao = new FicheDAORTH();
	FicheRTH ficherth = ficdao.derniereFicheRth(un);
%>

<link rel="stylesheet" href="/valence/css/rth/affiche.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>fiche RTH</title>


</head>
<body>
	<div id="body">
		<p><%@ include file='/menus/menurth/menuficherth.jsp'%></p>
		<br>
		<div id="creation">FICHE RTH</div>
		<br>
		<h3>ETAT CIVIL</h3>
		<table class="table1">
			<tr>
				<td class="petit" >NOM, prénom :</td><td class="affiche"><%=un.getNom_IDE()%>
				<%=un.getPrenom_IDE()%></td>
			</tr>
			<tr>

				<td class="petit">ADRESSE :</td>
				<td  class="affiche"><%=un.getAdr1_IDE()%></td>
			</tr>
			<tr>

				<td class="petit"></td>
				<td  class="affiche"><%=un.getAdr2_IDE()%></td>
			</tr>

			<tr>

				<td class="petit">Code Postal et Ville</td>
				<td  class="affiche"><%=un.getCp_IDE()%> <%=un.getVille_IDE()%></td>
			</tr>

			<tr>

				<td class="petit">N° sécurité sociale</td>
				<td  class="affiche">
					<%
						if (un.getNss_IDE() != null){
							out.println(un.getNss_IDE());
						}
					%>
				</td>
			</tr>

		</table>
		<br>
		<hr>
		<br>
		<table class="table2">
		<tr>
		<td class="petits">Catégorie</td>	<td  class="affiche">
		<%=ficherth.getCategorie_rth() %>
		</td>
		<td class="petits">Taux </td>
		<td  class="affiche"><%=ficherth.getTaux_rth() %></td></tr>
		<tr>
		<td class="petits">Pension</td><td  class="affiche"><% if(ficherth.isPension_rth())
			out.println("OUI");
			else
				out.println("NON"); %></td>
				
				<td>Montant</td><td  class="affiche"><%=ficherth.getMontant() %></td>
				</tr>
				<tr>
				<td>CI</td><td  class="affiche"><%=ficherth.getCi_rth() %></td>
				<td>Réf. ADIAD</td><td  class="affiche"><%=ficherth.getReferent_rth() %></td>
				</tr>
		</table>


		

	</div>


</body>
</html>