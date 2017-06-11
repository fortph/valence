
<%@ page
	import="beans.parametres.accueil.*,java.util.*,beans.identite.*,dao.imp.identite.*,beans.parametres.capemploi.*,
beans.suivi.*,dao.imp.suivi.*,dao.imp.employeur.*,dao.imp.rmi.*,divers.*,dao.exception.*,beans.rmi.*,java.text.*"%>

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
	SimpleDateFormat sdform=new SimpleDateFormat("dd-MM-yyyy");
	
	String affiche=null;//"RSA - LISTE DES CONTRATS EN COURS";	

	String datedebut = request.getParameter("datedebut");
	String datefin = request.getParameter("datefin");
	
	List<List<String>> listefiche=null;
	Integer totalpersonnes=null;
		
	FicheRMIDAO ficdao=new FicheRMIDAO();
	IdentiteDAO idao=new IdentiteDAO();
	Identite identite=null;
	UtilisateurDAO utidao = new UtilisateurDAO();
	Utilisateur ref = null;
	
	//variables pour résultats cumulés
	int suiviindividuel=0,totalsuivi=0,totalcg82=0,totalpe=0;
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/valence/css/rmi/statfincontrat.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<link rel="stylesheet" href="/valence/javascript/css/TableTools.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables_themeroller.css">
<title>Liste contrats RSA en cours</title>
</head>

<body>
	<%
		
	/*on affiche les contrats en cours sur le mois, par défaut*/
	if(datedebut==null || datefin==null){
		java.sql.Date ddeb=ManipulationDates.premierJourMoisCourant();
		String affichedatedeb=sdform.format(ddeb);	
		java.sql.Date dfin=ManipulationDates.finDumois(ddeb);
		String affichedatefin=sdform.format(dfin);
	
		listefiche = ficdao.contratRMIEnCoursParDates(ddeb, dfin);
		totalpersonnes = ficdao.totalPersonnesParDatesContratsEnCours(ddeb, dfin);
	
		affiche="LISTE DES  CONTRATS RSA EN COURS DU "+affichedatedeb+" AU "+affichedatefin;
	}
	/* sinon on affiche la période demandée*/
	else{	
		java.sql.Date datededeb=java.sql.Date.valueOf(datedebut);
		java.sql.Date datedefin=java.sql.Date.valueOf(datefin);
		String affichedatedebut=sdform.format(datededeb);
		String affichedatefin=sdform.format(datedefin);
		listefiche=(List<List<String>>)request.getAttribute("contrats");
		totalpersonnes=(Integer)request.getAttribute("totalpersonnes");
				
		affiche="LISTE DES  CONTRATS RSA EN COURS DU "+affichedatedebut+" AU "+affichedatefin;
			}
			%>
			
			

	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div id="creation"><%=affiche%>
		</div>
		<br>


		<form method="post" name="form1" action="/valence/controleur">
			<input type="hidden" name="action"
				value="rmilistecontratpardateencours" />
			<table class="table4">
				<tr>
					<td>Du</td>
					<td><input type="text" id="datedebut" name="datedebut" /></td>
					<td>au</td>
					<td><input type="text" id="datefin" name="datefin" />
					<td><input type="button" value="Afficher" class="boutonvert" onclick="valider(form1);" /></td>
				</tr>
			</table>

		</form>




		<!-- Affichage des résultats -->
		<br>
		<%
		if(listefiche!=null && listefiche.size()> 0){
		%>
	
	


		<br> <br>
		<table id="rsalistecontratspardates" class="display">
			<thead>
				<tr>

					<th>Nom-prénom</th>
					<th>Adresse</th>
					<th>datedeb</th>
					<th>datefin</th>
					<th>suivi</th>
					<th>Org</th>
					<th>Référent</th>
				</tr>
			</thead>
			<tbody>
				<%
				
				
					
					 for(int i=0;i<listefiche.size();i++){
						 if(listefiche.get(i).get(1).equals("Conseil Général 82"))
					 totalcg82+=1;
						 else
					 totalpe+=1; 
					
					identite=idao.findByID(Integer.parseInt(listefiche.get(i).get(0)));
					//nombre de suivis pour la personne sur la periode
					suiviindividuel=ficdao.totalSuivisParPersonneDates(new FormaterDate().changeFormatChaineDate(listefiche.get(i).get(3)), new FormaterDate().changeFormatChaineDate(listefiche.get(i).get(4)), identite);
				%>
				<tr>
					<td><a
						href="/valence/jsp/accueil/afficheInscrit.jsp?numero=<%=identite.getId_IDE()%>"><%=identite.getNom_IDE()%>
							<%=identite.getPrenom_IDE()%></a></td>
					<td><%=identite.getAdr1_IDE()%> <%=identite.getCp_IDE()%> <%=identite.getVille_IDE()%></td>

					<td class="centre"><%=listefiche.get(i).get(3)%></td>
					<td class="centre"><%=listefiche.get(i).get(4)%></td>
					<td class="centre"><%=suiviindividuel%></td>
					<td class="centre"><%=listefiche.get(i).get(1)%></td>
					<td class="centre"><%=new UtilisateurDAO().findByID(Integer.parseInt(listefiche.get(i).get(2))).getPrenom()%>
					</td>
					<%
						//calcul du nombre total de suivis
						totalsuivi+=suiviindividuel;
						}
					%>


				</tr>




			</tbody>
		</table>
		<%
			}
		else out.println("<h3>Aucun contrat RSA sur la période demandée</h3>");
		%>
		<br> <br>
		<hr>
		<br>
		<p>RESULTATS CUMULES</p>
		<table class="table3">
			<tr>
				<th>Total nom</th>
				<th>Total contrat</th>
				<th>Total suivi</th>
				<th>Total CG 82</th>
				<th>Total P.E</th>

			</tr>
			<tr>
				<td><%=totalpersonnes%></td>
				<td><%=listefiche.size()%></td>
				<td><%=totalsuivi%></td>
				<td><%=totalcg82%></td>
				<td><%=totalpe%></td>

			</tr>
		</table>

		<%
			
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
			src="/valence/javascript/scripts/datatabletripardates.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/scripts/tableaux.js"></script>
			<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>
		<br>

	</div>

</body>
</html>