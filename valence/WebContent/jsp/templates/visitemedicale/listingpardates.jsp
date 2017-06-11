<%@ page
	import="beans.parametres.accueil.*,java.util.*,beans.identite.*,dao.imp.identite.*,
beans.suivi.*,dao.imp.suivi.*,dao.imp.employeur.*,java.text.*,dao.imp.ai.*,divers.*,beans.ai.*"%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		
		var debut = document.getElementById("datedebut").value;
		var fin = document.getElementById("datefin").value;
		
		if (debut == "") {
			document.form1.datedebut.focus();
			// Afficher un message d'erreur
			attentionComplet(" La date de début doit être renseignée  !!!!");
			return false;
		}
		if (fin == "") {
			document.form1.datefin.focus();
			// Afficher un message d'erreur
			attentionComplet("La date de fin doit être renseignée  !!!!");
			return false;
		}
			
		formulaire.submit();
		return true;
	}
</script>

<%

	SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
	String tous=request.getParameter("tous");
	String affiche=null;

	String datedebut = request.getParameter("datedebut");
	String datefin = request.getParameter("datefin");
	
	VisiteMedicaleDAO avdao=new VisiteMedicaleDAO();
	List<VisiteMedicale> listevisites=null;
	IdentiteDAO idao=new IdentiteDAO();
	
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/valence/css/ai/contrat/pardates.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<link rel="stylesheet" href="/valence/javascript/css/TableTools.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables_themeroller.css">
<title>Liste Visites médicales</title>
</head>

<body>
<%

if(datedebut==null || datefin==null){
	
	java.sql.Date ddeb=ManipulationDates.premierJourMoisCourant();
	String affichedatedeb=sdf.format(ddeb);
	java.sql.Date dfin=ManipulationDates.finDumois(ddeb);
	 affiche="LISTE DES ECHEANCES VISITES MEDICALES SE TERMINANT PENDANT LA PERIODE DU "+affichedatedeb+" AU "+sdf.format(dfin);
	 listevisites=avdao.listeVisitesParDates(ddeb, dfin);
	
	
}
else{	
 affiche="LISTE DES ECHEANCES VISITES MEDICALES SE TERMINANT PENDANT LA PERIODE DU "+sdf.format(java.sql.Date.valueOf(datedebut))+" AU "+sdf.format(java.sql.Date.valueOf(datefin));

 listevisites=(List<VisiteMedicale>)request.getAttribute("listevisites");

}

%>

	<div id="body">
		<p><%@ include file='/menus/accueil/menusimple.jsp'%></p>
		<br>
		<div id="creation"><%=affiche %>
			</div>
		<br>
		<form method="post" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="<%=destination %>" />
			<table class="table4">
				<tr>
					<td>Du :</td>
					<td><input type="text" id="datedebut" name="datedebut" /></td>
					<td>au :</td>
					<td><input type="text" id="datefin" name="datefin" />
					<td><input type="button" class="boutonvert" value="Envoyer" onclick="valider(form1);" /></td>
				</tr>
			</table>

		</form>

		<!-- Affichage des résultats -->
		<br>
		<table class="table1">
			<tr>
				
				<td class="petit"><a
					href="/valence/controleur?action=ailistevisitespardatefin&a=0">M</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=ailistevisitespardatefin&a=1">M+1</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=ailistevisitespardatefin&a=2">M+2</a></td>
			</tr>
		</table>
		<br> <br>
		<table  id="listecontratspardates" class="display">
		<thead>
			<tr>
			
				<th>Nom-prénom</th>
				<th>Echéance</th>
			</tr>
			</thead>
				<tbody>
			<%
			if(listevisites!=null){
					for(int j=0;j<listevisites.size();j++){
			%>
		
			<tr>
				<td><a
					href="/valence/jsp/accueil/afficheInscrit.jsp?numero=<%=listevisites.get(j).getIdentite().getId_IDE()%>" ><%=listevisites.get(j).getIdentite().getNom_IDE()%>
						<%=listevisites.get(j).getIdentite().getPrenom_IDE()%></a></td>
				<td><%=sdf.format(listevisites.get(j).getEcheance()) %></td>

			</tr>


			<%
				} }
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
			src="/valence/javascript/scripts/journouveaux.js"></script>
	<script type="text/javascript"
		src="/valence/javascript/jquery.dataTables.min.js"></script>

	<script type="text/javascript"
		src="/valence/javascript/ZeroClipboard.js"></script>
	<script type="text/javascript" src="/valence/javascript/TableTools.min.js"></script>

	<script type="text/javascript"
		src="/valence/javascript/scripts/tableaux.js"></script>
		
	<script type="text/javascript"
		src="/valence/javascript/scripts/attention.js"></script>
		<br>
		
		</div>

</body>
</html>