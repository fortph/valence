<%@ page
	import="dao.imp.identite.*, beans.identite.*,java.util.*,java.lang.*,
dao.imp.formation.*,beans.formation.*,dao.imp.suivi.*,beans.suivi.*,java.text.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet" href="/valence/css/suivi/accompagneemploi2.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />

<%
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	String personne = request.getParameter("numero");
	IdentiteDAO iddao = new IdentiteDAO();
	Identite une = iddao.findByID(Integer.parseInt(personne));
	String nom = une.getNom_IDE() + " " + une.getPrenom_IDE();
	String jf = une.getNomjf_IDE();
	String civil = une.getSexe_IDE();
	String monsieur = "";
	if (civil.equals("MASCULIN"))
		monsieur = "Monsieur";
	else if (civil.equals("FEMININ"))
		monsieur = "Madame";
	String jeunefille = "";
	if (!jf.equals(""))
		jeunefille = "Née " + jf;

	SuiviFormationDAO suidao = new SuiviFormationDAO();
	List<SuiviFormation> liste = suidao.afficheTousLesSuivisForm(une);
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>suivi formation</title>
</head>
<body>


	<div id="body">
		<p><%@ include file='/menus/menugeneral/menusuivicourt.jsp'%></p>
		<br>
		<div id="creation">
			SUIVI FORMATION de
			<%=monsieur%>
			<%=nom%>
			<%=jeunefille%></div>
		<br>

		<div id="boutons">
			<a
				href="/valence/jsp/suiviFormations/nouveausuiviformation.jsp?personne=<%=une.getId_IDE()%>"><input
				type="button" class="boutoncentre" id="modifierbouton"
				value="Nouveau Suivi"> </a>
		</div>
		<br>
		<%
			if (liste.size() > 0) {
				for (int i = 0; i < liste.size(); i++) {
		%>
		<div id="bloc">
			<table>
				<tr>
					<td id="bleu" colspan="1">le : <%
						if (liste.get(i).getDateSuivi() != null)
									out.println(sdf.format(liste.get(i).getDateSuivi()));
					%></td>
					
					<td class="gras" colspan="5">par : <%=liste.get(i).getReferent().getNom()%></td>
					
					
					<td id="puce" rowspan="4"><a
					href="/valence/jsp/suiviFormations/modificationsuiviformation.jsp?suivi=<%=liste.get(i).getIdsuivi()%>"><img
							src="/valence/images/bleu/mod.png"></a></td>
					
				</tr>
				<tr>
					<td id="grand" colspan="4">Formation : <span class="gras"><%=liste.get(i).getFormation()%>
					</span></td>
					<td>Organisme:<span class="gras"> <%=liste.get(i).getOf()%></span></td>
				</tr>
				
				<tr>
					<td  colspan="2">du :<span class="gras"> <%
						if (liste.get(i).getDateDebutFormation() != null)
									out.println(sdf.format(liste.get(i).getDateDebutFormation()));
					%></span></td>
					<td  colspan="2">au : <span class="gras"><%
						if (liste.get(i).getDateFinFormation() != null)
									out.println(sdf.format(liste.get(i).getDateFinFormation()));
					%></span></td>
					<td >Type:<span class="gras"> <%=liste.get(i).getTypeFormations().getNomProposition()%></span> </td>

				</tr>

				<tr>
					<td id="comm" colspan="6"><textarea><%=liste.get(i).getCommentaires()%></textarea>
					<td>
				</tr>

			</table>


		</div>
		<br>
		<%
			}
		%>
		<br>
		<%
			} else {
		%>
		<h4>Aucun suivi n'a encore été fait....</h4>
		<%
			}
		%>

		<br/>

		<br>
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
		src="/valence/javascript/jquery.autosize.min.js"></script>
	<script type="text/javascript"
		src="/valence/javascript/scripts/hauteurtextarea.js"></script>


</body>
</html>