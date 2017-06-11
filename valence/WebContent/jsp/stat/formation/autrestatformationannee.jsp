<%@page
	import="beans.parametres.accueil.*,dao.imp.formation.*,beans.formation.*,
	dao.imp.identite.*, beans.identite.*,divers.*,java.util.*,java.sql.*,dao.exception.*,java.text.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/valence/css/formation/affichagestat.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<link rel="stylesheet" href="/valence/javascript/css/TableTools.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables_themeroller.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>statistiques</title>
<%
	SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
	java.sql.Date debut = (java.sql.Date) request.getAttribute("debut");
	java.sql.Date fin = (java.sql.Date) request.getAttribute("fin");
	String sexe = (String) request.getAttribute("sexe");
	String age = (String) request.getAttribute("age");
	String niveau = (String) request.getAttribute("niveauformation");
	String pole = (String) request.getAttribute("pole");
	String origine = (String) request.getAttribute("origine");
	
	ListeFormationsDAO listedao = new ListeFormationsDAO();
	ListeFormations nomformation = null;
	ListeFormations formpardate = null;
	List<ListeFormations> correspond = null;
	IdentiteDAO iddao = new IdentiteDAO();
	PreInscriptionDAO predao = new PreInscriptionDAO();
	int totalinscrit = 0;
	int totalenregistre = 0;
	int idformation = 0;
	formpardate = new ListeFormations();
	formpardate.setDatedeb_form(debut);
	formpardate.setDatefin_form(fin);
	correspond = listedao.findByCriteria(formpardate);

	
%>
</head>
<body>
	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>

		<div id="creation">

			AFFICHAGE PERSONNES PAR FORMATION </div>
			<h4>
			correspondants aux critères définis ci-dessous :
		</h4>

		<label >Date des formations entre le <span class="nom"><%=sdf.format(debut)%></span>
			et le <span class="nom"><%=sdf.format(fin)%></span></label> <br>
		<%
			if (!age.equals("vide")) {
		%>
		<label class="bleu">- Age :<span class="nom"><%=age%></span></label> <br>
		<%
			}
				if (!sexe.equals("vide")) {
		%>
		<label class="bleu">- Sexe :<span class="nom"><%=sexe%></span></label> <br>
		<%
			}
				if (!niveau.equals("vide")) {
		%>
		<label class="bleu">- Niveau de formation :<span class="nom"><%=niveau%></span></label>
		<br>
		<%
			}
				if (!pole.equals("vide")) {
		%>
		<label class="bleu">- Inscription à Pole Emploi :<span class="nom"><%=pole%></span></label>
		<br>
		<%
			}

				if (!origine.equals("vide")) {
		%>
		<label class="bleu">- Origine :<span class="nom"><%=origine%></span></label> <br>

		<%
			}
		%>
		<br/>
		<hr>
		<%
			for (int i = 0; i < correspond.size(); i++) {
				totalinscrit=0;
				totalenregistre=0;
		%>

		<label>Nom de la formation : </label><span class="nom"> <%=correspond.get(i).getFormation()%>(<%=correspond.get(i).getId_pformation()%>)
		</span>
		<table id="tablestatformationpourcent<%=i%>" class="display">

			<thead>
				<tr>
					<th>NOM</th>
					<th>PRENOM</th>
					<th>CODE POSTAL</th>
					<th>VILLE</th>
					<th>INSCRIT</th>
					<th>ENREGISTRE</th>


				</tr>
			</thead>
			<tbody>


				<%
					Identite identite = null;
								PreInscription prein = new PreInscription();
								prein.setListe(correspond.get(i));
								
								//on recupere les champs de la table preinscription correspondant a ce numero de formation
								List<PreInscription> liste = predao.findByCriteria2(prein);
								
								for (int j = 0; j < liste.size(); j++) {
									
									//on affecte la recherche a une autre personne
									identite = predao.afficherStatistiquesformation(liste.get(j), sexe, age, nomformation,niveau, pole, origine);
									if(identite!=null){
									
				%>
				<tr>
					<td><%=identite.getNom_IDE()%></td>
					<td><%=identite.getPrenom_IDE()%></td>
					<td><%=identite.getCp_IDE()%></td>
					<td><%=identite.getVille_IDE()%></td>
					<td>
						<%
							if(liste.get(j).isInscrit()){
								totalinscrit++;
											out.println("OUI");
							}
												else
											out.println("NON");
						%>
					</td>
					<td>
						<%
							if(liste.get(j).isEnregistre()){
								totalenregistre++;
							
											out.println("OUI");
							}
												else
											out.println("NON");
						%>
					</td>
				</tr>
				<%
					}}
				%>
			</tbody>

		</table>
		<br> <br>
		<div class="total">
			TOTAL DES INSCRITS :
			<%=totalinscrit%>
		</div>
		<div class="total">
			TOTAL DES ENREGISTRES :
			<%=totalenregistre%>
		</div>
		<br>
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
			src="/valence/javascript/jquery.dataTables.min.js"></script>

		<script type="text/javascript"
			src="/valence/javascript/ZeroClipboard.js"></script>
		<script type="text/javascript" src="/valence/javascript/TableTools.js"></script>

		<script type="text/javascript"
			src="/valence/javascript/scripts/tableaux.js"></script>

	</div>
</body>
</html>