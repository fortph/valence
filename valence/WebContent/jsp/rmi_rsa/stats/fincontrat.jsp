
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
	String a=request.getParameter("a");
	String referent=request.getParameter("referent");
	
	String affiche=null;
	

	String datedebut = request.getParameter("datedebut");
	String datefin = request.getParameter("datefin");
	
	List<List<String>> listefiche=(List<List<String>>)request.getAttribute("contrats");
	Integer totalpersonnes=(Integer)request.getAttribute("totalpersonnes");
	
	FicheRMIDAO ficdao=new FicheRMIDAO();
	IdentiteDAO idao=new IdentiteDAO();
	Identite identite=null;
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
<title>Liste fin contrats RSA</title>
</head>

<body>
	<%
		UtilisateurDAO utidao = new UtilisateurDAO();
		Utilisateur ref = null;
		
		
			//action par defaut à l'ouverture de la page :mois en cours et tous referents
			if(datedebut==null &&  datefin==null && referent ==null ){
				java.sql.Date ddeb=ManipulationDates.premierJourMoisCourant();
				String affichedatedeb=sdform.format(ddeb);	
				java.sql.Date dfin=ManipulationDates.finDumois(ddeb);
				String affichedatefin=sdform.format(dfin);
		
		 affiche="LISTE DES FINS DE CONTRATS DU "+affichedatedeb+" AU "+affichedatefin;
		 
		listefiche=ficdao.contratRMIParDates(ddeb, dfin,null);
		 	totalpersonnes=ficdao.totalPersonnesParDates(ddeb,dfin,ref);
		 
			}
		// idem mois en cours avec un nom de referent
			else if(datedebut.equals("null") && datefin.equals("null") && !referent.equals("null")){
				java.sql.Date ddeb=ManipulationDates.premierJourMoisCourant();
				String affichedatedeb=sdform.format(ddeb);		
				java.sql.Date dfin=ManipulationDates.finDumois(ddeb);
				String affichedatefin=sdform.format(dfin);
			
			 if (referent.equals("lac"))
			try {
				ref = utidao.findByName("Lacourt monique");
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		else if (referent.equals("phil"))
			try {
				ref = utidao.findByName("Jacquet philippe");
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		else if (referent.equals("nat"))
			try {
				ref = utidao.findByName("Marty Nathalie");
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		else 
			ref=null;
			
			
			 listefiche=ficdao.contratRMIParDates(ddeb, dfin,ref);
			 totalpersonnes=ficdao.totalPersonnesParDates(ddeb,dfin,ref); 
			 
			 
		 
			}
		//si des dates ont ete saisies
			else{	
		java.sql.Date datededeb=java.sql.Date.valueOf(datedebut);
		java.sql.Date datedefin=java.sql.Date.valueOf(datefin);
		String affichedatedebut=sdform.format(datededeb);
		String affichedatefin=sdform.format(datedefin);
			 affiche="LISTE DES FINS DE CONTRATS DU "+affichedatedebut+" AU "+affichedatefin;
			 if(referent!=null){
			 if (referent.equals("lac"))
					try {
						ref = utidao.findByName("Lacourt monique");
					} catch (DAOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				else if (referent.equals("phil"))
					try {
						ref = utidao.findByName("Jacquet philippe");
					} catch (DAOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				else if (referent.equals("nat"))
					try {
						ref = utidao.findByName("Marty Nathalie");
					} catch (DAOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				else 
					ref=null;
			 }
			 
			 listefiche=ficdao.contratRMIParDates(datededeb, datedefin,ref);
			 totalpersonnes=ficdao.totalPersonnesParDates(datededeb,datedefin,ref); 
		}
		
			//on calcule le nombre de pole emploi et de conseil general trouvés dans la liste	
			if(listefiche!=null){
		 for(int i=0;i<listefiche.size();i++){
			 if(listefiche.get(i).get(1).equals("Conseil Général 82"))
		 totalcg82+=1;
			 else
		 totalpe+=1; 
		 }
			 }
	%>

	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div id="creation"><%=affiche%>
		</div>
		<br>


		<form method="post" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="rmilistecontratpardatefin" />
			<!-- <input type="hidden" name="dtdebut" value="<%=datedebut %>" />
			<input type="hidden" name="dtfin" value="<%=datefin %>"/>-->
			<table class="table4">
				<tr>
					<td>Du</td>
					<td><input type="text" id="datedebut" name="datedebut" /></td>
					<td>au</td>
					<td><input type="text" id="datefin" name="datefin" />
					<td><input type="button" value="Envoyer" class="boutonvert" onclick="valider(form1);"/></td>
				</tr>
			</table>

		</form>




		<!-- Affichage des résultats -->
		<br>
		<table class="table1">
			<tr>
				<td class="quart"><a
					href="/valence/controleur?action=rmilistecontratpardatefin&a=<%=a %>&referent=tous&datedebut=<%=datedebut%>&datefin=<%=datefin%>">Tous</a></td>
				<td class="quart"><a
					href="/valence/controleur?action=rmilistecontratpardatefin&a=<%=a %>&referent=lac&datedebut=<%=datedebut%>&datefin=<%=datefin%>">Monique
						Lacourt</a></td>
				<td class="quart"><a
					href="/valence/controleur?action=rmilistecontratpardatefin&a=<%=a %>&referent=nat&datedebut=<%=datedebut%>&datefin=<%=datefin%>">Nathalie</a></td>
				<td class="quart"><a
					href="/valence/controleur?action=rmilistecontratpardatefin&a=<%=a %>&referent=phil&datedebut=<%=datedebut%>&datefin=<%=datefin%>">Philippe</a></td>
			</tr>
		</table>

		<table class="table2">
			<tr>
				<td class="petit"><a
					href="/valence/controleur?action=rmilistecontratpardatefin&a=-12&referent=<%=referent%>">M-12</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=rmilistecontratpardatefin&a=-11&referent=<%=referent%>">M-11</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=rmilistecontratpardatefin&a=-10&referent=<%=referent%>">M-10</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=rmilistecontratpardatefin&a=-9&referent=<%=referent%>">M-9</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=rmilistecontratpardatefin&a=-8&referent=<%=referent%>">M-8</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=rmilistecontratpardatefin&a=-7&referent=<%=referent%>">M-7</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=rmilistecontratpardatefin&a=-6&referent=<%=referent%>">M-6</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=rmilistecontratpardatefin&a=-5&referent=<%=referent%>">M-5</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=rmilistecontratpardatefin&a=-4&referent=<%=referent%>">M-4</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=rmilistecontratpardatefin&a=-3&referent=<%=referent%>">M-3</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=rmilistecontratpardatefin&a=-2&referent=<%=referent%>">M-2</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=rmilistecontratpardatefin&a=-1&referent=<%=referent%>">M-1</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=rmilistecontratpardatefin&a=0&referent=<%=referent%>">M</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=rmilistecontratpardatefin&a=1&referent=<%=referent%>">M+1</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=rmilistecontratpardatefin&a=2&referent=<%=referent%>">M+2</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=rmilistecontratpardatefin&a=3&referent=<%=referent%>">M+3</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=rmilistecontratpardatefin&a=4&referent=<%=referent%>">M+4</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=rmilistecontratpardatefin&a=5&referent=<%=referent%>">M+5</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=rmilistecontratpardatefin&a=6&referent=<%=referent%>">M+6</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=rmilistecontratpardatefin&a=7&referent=<%=referent%>">M+7</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=rmilistecontratpardatefin&a=8&referent=<%=referent%>">M+8</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=rmilistecontratpardatefin&a=9&referent=<%=referent%>">M+9</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=rmilistecontratpardatefin&a=10&referent=<%=referent%>">M+10</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=rmilistecontratpardatefin&a=11&referent=<%=referent%>">M+11</a></td>
				<td class="petit"><a
					href="/valence/controleur?action=rmilistecontratpardatefin&a=12&referent=<%=referent%>">M+12</a></td>

			</tr>
		</table>

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
					if(listefiche!=null){
																			
										for(int i=0;i<listefiche.size();i++){
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


				<%
					}
				%>


			</tbody>
		</table>
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