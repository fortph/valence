<%@ page
	import="dao.imp.identite.*, beans.identite.*,java.util.*,java.lang.*,
dao.imp.formation.*,beans.formation.*,dao.imp.suivi.*,beans.suivi.*,java.text.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet" href="/valence/css/suivi/accompagneemploi.css">
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
		jeunefille = "N�e " + jf;

	SuiviEmploiDAO suidao = new SuiviEmploiDAO();
	List<SuiviEmploi> liste = suidao.afficheTousLesSuivis(une);
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>suivi emploi</title>
</head>
<body>


	<div id="body">
		<p><%@ include file='/menus/menugeneral/menusuivicourt.jsp'%></p>
		<br>
		<div id="creation">
			SUIVI EMPLOI de 
			<%=monsieur%>				
			<%=nom%>
			<%=jeunefille%></div>
		<br>

		<div id="boutons">
			<a
				href="/valence/jsp/suiviInscrits/suiviEmploi.jsp?personne=<%=une.getId_IDE()%>"><input
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
					<td id="bleu">le : <%
						if (liste.get(i).getDateSuivi() != null)
									out.println(sdf.format(liste.get(i).getDateSuivi()));
					%></td>
					<td class="gras">par : <%=liste.get(i).getReferent().getNom()%>
						<%=liste.get(i).getReferent().getPrenom()%></td>
					<td id="grand" class="gras" colspan="3">Employeur : <%=liste.get(i).getEmployeur().getRs_employeur()%></td>
					<td id="puce" rowspan="3"><a
						href="/valence/jsp/suiviInscrits/modificationsuiviemploi.jsp?suivi=<%=liste.get(i).getIdsuiviemploi()%>"><img
							src="/valence/images/bleu/mod.png" /></a></td>
				</tr>
				<tr>
					<td class="gras">du : <%
						if (liste.get(i).getDateDebutSuivi() != null)
									out.println(sdf
											.format(liste.get(i).getDateDebutSuivi()));
					%></td>
					<td class="gras">au : <%
						if (liste.get(i).getDateFinSuivi() != null)
									out.println(sdf.format(liste.get(i).getDateFinSuivi()));
					%></td>
					<td class="gras" colspan="3">Poste: <%=liste.get(i).getRome().getIntitule()%></td>
					
				</tr>

				<tr>
					<td id="comm" colspan="5" ><textarea><%=liste.get(i).getCommentaires()%></textarea>
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
		<h4>Aucun accompagnement n'a encore �t� fait....</h4>
		<%
			}
		%>

		<hr>

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