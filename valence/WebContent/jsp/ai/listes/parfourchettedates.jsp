
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
	
	String datedebut = request.getParameter("datedebut");
	String datefin = request.getParameter("datefin");
	
	 String affiche="RECHERCHE LISTE DES CONTRATS AI PAR DATES";
	

	List<Contrat>contrats=(List<Contrat>)request.getAttribute("contrats");
	List<Avenant>avenants=(List<Avenant>)request.getAttribute("avenants");
	
	
	ContratDAO codao=new ContratDAO();
	AvenantDAO avdao=new AvenantDAO();
	IdentiteDAO idao=new IdentiteDAO();
	EmployeurDAO empdao=new EmployeurDAO();
	RomeDAO rodao=new RomeDAO();
	
	
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
	

	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div id="creation"><%=affiche%>
		</div>
		<br>
		<form name="form1" method="post" action="/valence/controleur">
			<input type="hidden" name="action" value="ailistecontratdansfourchette" />
			<table class="table4">
				<tr>
					<td>Du :</td>
					<td><input type="text" class="centre" id="datedebut" name="datedebut" value="<% if (datedebut!=null) out.println(datedebut); %>" /></td>
					<td>au :</td>
					<td><input type="text" class="centre" id="datefin" name="datefin" value="<% if(datefin!=null) out.println(datefin);%>"/>
					<td><input type="button" value="Rechercher" id="droite" class="boutonvert"
				onclick="valider(form1);" /></td>
				</tr>
			</table>

		</form>
		<br>
		<br>
		<!-- Affichage des résultats -->
		<%
		if(contrats!=null){
		%>
		<table id="listecontratspardatesdebut" class="display">
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
	<%
	}
	%>

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
		<script type="text/javascript"
			src="/valence/javascript/TableTools.min.js"></script>

		<script type="text/javascript"
			src="/valence/javascript/scripts/tableaux.js"></script>
			<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>
			<script type="text/javascript"
			src="/valence/javascript/scripts/datatabletripardates.js"></script>
		<br>

	</div>

</body>
</html>