
<%@ page
	import="beans.parametres.accueil.*,java.util.*,beans.identite.*,dao.imp.identite.*,
beans.suivi.*,dao.imp.suivi.*,dao.imp.employeur.*,dao.imp.ai.*,divers.*,beans.ai.*,java.text.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	SimpleDateFormat sdform=new SimpleDateFormat("dd-MM-yyyy");
	String tous=request.getParameter("tous");
	String affiche=null;

	String datedebut = request.getParameter("datedebut");
	String datefin = request.getParameter("datefin");
	
	
	ContratDAO codao=new ContratDAO();
	AvenantDAO avdao=new AvenantDAO();
	List<Contrat>contrats=null;
	List<Avenant>avenants=null;
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
<title>Liste Contrats AI</title>

<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier

		var datedebutrech = document.getElementById("datedebut").value;
		var datefinrech = document.getElementById("datefin").value;

		if (datedebutrech == "") {
			document.form1.datedebut.focus();
			// Afficher un message d'erreur
			attentionComplet(" La date de début doit être renseignée !!!!");
			return false;
		}

		if (datefinrech == "") {
			document.form1.datefin.focus();
			// Afficher un message d'erreur
			attentionComplet(" La date de fin doit être renseignée !!!!");
			return false;
		}

		formulaire.submit();
		return true;
	}
</script>


</head>

<body>
	<%
		if(datedebut==null || datefin==null){
			java.sql.Date ddeb=ManipulationDates.premierJourMoisCourant();
			String affichedatedeb=sdform.format(ddeb);	
			java.sql.Date dfin=ManipulationDates.finDumois(ddeb);
			String affichedatefin=sdform.format(dfin);
		
		 affiche="LISTE DES CONTRATS SE TERMINANT PENDANT LA PERIODE DU "+affichedatedeb+" AU "+affichedatefin;
		 			
		avenants=avdao.listeAvenantParDates(ddeb, dfin);
		contrats=codao.contratAvenantParDateFin(ddeb, dfin);
		
	}
	else{	
		java.sql.Date datededeb=java.sql.Date.valueOf(datedebut);
		java.sql.Date datedefin=java.sql.Date.valueOf(datefin);
		String affichedatedebut=sdform.format(datededeb);
		String affichedatefin=sdform.format(datedefin);
	 affiche="LISTE DES CONTRATS SE TERMINANT PENDANT LA PERIODE DU "+affichedatedebut+" AU "+affichedatefin;

	contrats=(List<Contrat>)request.getAttribute("contrats");
	avenants=(List<Avenant>)request.getAttribute("avenants");
	IdentiteDAO idao=new IdentiteDAO();
	EmployeurDAO empdao=new EmployeurDAO();
	RomeDAO rodao=new RomeDAO();
	}
	%>

	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div id="creation"><%=affiche%>
		</div>
		<br>
		<form name="form1" method="post" action="/valence/controleur">
			<input type="hidden" name="action" value="ailistecontratpardatefin" />
			<table class="table4">
				<tr>
					<td>Du :</td>
					<td><input type="text" class="centre" id="datedebut" name="datedebut" /></td>
					<td>au :</td>
					<td><input type="text" class="centre" id="datefin" name="datefin" />
					<td><input type="button" value="Rechercher" id="droite" class="boutonvert"
				onclick="valider(form1);" /></td>
				</tr>
			</table>

		</form>

		<!-- Affichage des résultats -->
		<br>
		<table class="table1">
		
			<tr>
				<td class="petit"><a
					href="/valence/controleur?action=ailistecontratpardatefin&a=-2">M-2</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=ailistecontratpardatefin&a=-1">M-1</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=ailistecontratpardatefin&a=0">M</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=ailistecontratpardatefin&a=1">M+1</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=ailistecontratpardatefin&a=2">M+2</a></td>
			</tr>
			
		</table>
		<br> <br>
		<table id="listecontratspardates" class="display">
			<thead>
				<tr>

					<th>Nom-prénom</th>
					<th>Utilisateur</th>
					<th>Type</th>
					<th>Poste</th>
					<th>Début</th>
					<th>Fin</th>
				</tr>
			</thead>
			<tbody>
				<%
					if(avenants!=null){
							for(int j=0;j<avenants.size();j++){
				%>

				<tr>
					<td><a
						href="/valence/jsp/accueil/afficheInscrit.jsp?numero=<%=avenants.get(j).getContrat().getIdentite().getId_IDE()%>"><%=avenants.get(j).getContrat().getIdentite().getNom_IDE()%>
							<%=avenants.get(j).getContrat().getIdentite().getPrenom_IDE()%></a></td>
					<td><%=avenants.get(j).getContrat().getEmployeur().getRs_employeur()%></td>
					<td class="centre"><a
						href="/valence/jsp/ai/avenant/affichage.jsp?avenant=<%=avenants.get(j).getIdavenant()%>">
							A </a></td>
					<td><%=avenants.get(j).getContrat().getRome().getIntitule()%></td>
					<td class="centre"><% if(avenants.get(j).getDatedeb()!=null) out.println(sdform.format(avenants.get(j).getDatedeb())); %></td>
					<td class="centre"><% if(avenants.get(j).getDatefin()!=null) out.println(sdform.format(avenants.get(j).getDatefin())); %></td>


				</tr>


				<%
					} }
					
						if(contrats!=null){
									for(int i=0;i<contrats.size();i++){
				%>

				<tr>
					<td><a
						href="/valence/jsp/accueil/afficheInscrit.jsp?numero=<%=contrats.get(i).getIdentite().getId_IDE()%>"><%=contrats.get(i).getIdentite().getNom_IDE()%>
							<%=contrats.get(i).getIdentite().getPrenom_IDE()%></a></td>
					<td><%=contrats.get(i).getEmployeur().getRs_employeur()%></td>
					<td class="centre"><a
						href="/valence/jsp/ai/contrat/affichage.jsp?contrat=<%=contrats.get(i).getIdaicontrat()%>">
							C </a></td>
					<td><%=contrats.get(i).getRome().getIntitule()%></td>
					<td class="centre"><% if(contrats.get(i).getDebutcontrat()!=null) out.println(sdform.format(contrats.get(i).getDebutcontrat())); %></td>
					<td class="centre"><% if(contrats.get(i).getFincontrat()!=null) out.println(sdform.format(contrats.get(i).getFincontrat())); %> </td>


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

	</div>

</body>
</html>