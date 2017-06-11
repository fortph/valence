<%@page
	import="beans.parametres.formationpro.*,dao.imp.formationpro.*,beans.formationpro.*,
	dao.imp.identite.*, beans.identite.*,divers.*,java.util.*,java.sql.*,dao.exception.*,java.text.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/valence/css/stat/listingAccueil.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<link rel="stylesheet" href="/valence/javascript/css/TableTools.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables_themeroller.css">
<%
	//on recupere toutes les inscriptions sur la periode definie
	List<FormationProInscription> liste = (List<FormationProInscription>)request.getAttribute("liste");
	java.util.Date debut=(java.util.Date)request.getAttribute("debut");
	java.util.Date fin=(java.util.Date)request.getAttribute("fin");
	SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
	
	//on clone le tableau pour l'envoyer en parametre de la fonction nombreHeuresFormateur(liste);
	List<FormationProInscription> clone=new ArrayList<FormationProInscription>();
	for(int i=0;i<liste.size();i++){
		clone.add(liste.get(i));
	}
	
	FormationProInscriptionDAO fpdao=new FormationProInscriptionDAO();
	FormationProInscription forpro=null;
	FormprothemeDAO themdao=new FormprothemeDAO();
	Formprotheme theme=null;
	
	int nbformations=0;
	int nbheuresformationtotal=0;
	int nbheuresformationcumulTousStagiaires=0;
	float budgettotal=0;
	int groupes=0;
	
	
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Statistiques</title>
</head>




<body>
	<div id="body">
	<p><%@ include file='/menus/menugeneral/menu.html' %></p><br>
		<h3>
			Personnes enregistrées en formation entre le
			<%=sdf.format(debut)%>
			et le
			<%=sdf.format(fin)%></h3>
			
			<%
			if(liste.size()>0){
		//tableau qui recupere tous les themes de formation proposés sur la periode
		List<Formprotheme>listetheme=new ArrayList<Formprotheme>();
		//on recupere le nombre de groupes differenrs sur la période
		groupes=fpdao.nombreHeuresFormateur(clone);
		System.out.println("nombre de groupes ="+groupes);
	
	//pour chaque formation trouvee sur la periode selectionnee
	for(int i=0;i<liste.size();i++)		
		{
		nbheuresformationcumulTousStagiaires+=liste.get(i).getNbheures();
		budgettotal+=liste.get(i).getMontant();
		
		theme=themdao.findByID(liste.get(i).getTheme().getId_theme());
		listetheme.add(theme);
		
				
		}
				
		
		
	nbformations=themdao.nombreTheme(listetheme);
	%>
		<table id="accueilpourcent" class="display">
			<thead>
				<tr>
					<th>Nombre de formations</th>
					<th>Nombre de stagiaires</th>
					<th>Nombre heures formateurs</th>
					<th>Nombre heures stagiaires</th>
					<th>Budget formation</th>
				</tr>
			</thead>
			<tbody>

				<tr>
					<td><%=nbformations%></td>
					<td><%=liste.size()%></td>
					<td><%=groupes %></td>
					<td><%=nbheuresformationcumulTousStagiaires%></td>
					<td><%=budgettotal%></td>

				</tr>

			</tbody>

		</table>
		<%
			}
			else
				out.println("Aucune personne enregistrée sur cette date");
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
			src="/valence/javascript/jquery.dataTables.min.js"></script>

		<script type="text/javascript"
			src="/valence/javascript/ZeroClipboard.js"></script>
		<script type="text/javascript" src="/valence/javascript/TableTools.min.js"></script>

		<script type="text/javascript"
			src="/valence/javascript/scripts/tableaux.js"></script>
		<br>
	</div>
</body>
</html>