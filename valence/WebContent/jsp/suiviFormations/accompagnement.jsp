<%@ page
	import="dao.imp.identite.*, beans.identite.*,java.util.*,java.lang.*,java.text.*,
dao.imp.formation.*,beans.formation.*,dao.imp.suivi.*,beans.suivi.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet" href="/valence/css/suivi/accompagne.css">
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

	AccompagnementFormationDAO suidao = new AccompagnementFormationDAO();
	List<AccompagnementFormation> liste =suidao.afficheTousLesSuivis(une);
	
	PropositionsFormationDAO propdao=new PropositionsFormationDAO();
	List<PropositionsFormation> listeprop=null;
	
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>accompagnement</title>
</head>
<body>


	<div id="body">
		<p><%@ include file='/menus/menugeneral/menusuivicourt.jsp'%></p>
		<br>
		<div id="creation">
			ACCOMPAGNEMENT FORMATION de 
			<%=monsieur%>			
			<%=nom%>
			<%=jeunefille%></div>
		<br>

		<div id="boutons">
			<a
				href="/valence/jsp/suiviFormations/suivi.jsp?personne=<%=une.getId_IDE()%>"><input
				type="button" class="boutoncentre" id="modifierbouton"
				value="Nouvel Accompagnement"> </a>
		</div>
		<br>
		<% if(!liste.equals(null))
		
			if (  liste.size() > 0) {
				for (int i = 0; i < liste.size(); i++) {
					int id_ac=liste.get(i).getId_accomp();
					//propdao=new PropositionsFormationDAO();
					listeprop=propdao.findByIDAccompagnement(liste.get(i));
		%>
		<div id="bloc">
			<table>
				<tr>
					<td id="bleu">le : <% if(liste.get(i).getDateredaction()!=null) out.println(sdf.format(liste.get(i).getDateredaction())); %></td>
					<td id="gras">par : <%=liste.get(i).getReferent().getNom()%> </td>
					
				</tr>
				<tr ><td class="bl" colspan="2" >Formation demandée :</td></tr>
				<tr>
					<td colspan="2"><textarea ><%=liste.get(i).getDemande() %></textarea>
					<td>
					<td id="puce" ><a title="modifier"
						href="/valence/jsp/suiviFormations/modificationAccomp.jsp?suivi=<%=liste.get(i).getId_accomp() %>"><img
							src="/valence/images/bleu/mod.png" /></a></td>
				</tr>
				<tr ><td class="bl" colspan="2" >Propositions :</td></tr>
				<%
				if(listeprop.size()>0 ){
				for(int j=0;j<listeprop.size();j++ ){
				
				%>
				
				<tr>
				<td ><%=listeprop.get(j).getNomProposition() %></td>
				<td class="blanc"><%=listeprop.get(j).getCommentaire() %></td>
				</tr>
				<% 
				}}
				%>
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
		<h4>Aucun accompagnement n'a encore été fait....</h4>
		<%
			}
		%>

		

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