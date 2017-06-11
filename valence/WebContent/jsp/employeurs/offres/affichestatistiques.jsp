<%@ page
	import="beans.suivi.*,dao.imp.suivi.*,java.util.*,divers.*,beans.employeurs.*,dao.imp.employeur.*,
	beans.parametres.capemploi.*,beans.parametres.accueil.*,java.util.Date"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="/valence/css/offres/affichestatistiques.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<%

OffreDAO ofdao=new OffreDAO();
RomeDAO romdao=new RomeDAO();
List<Rome> lstrome=romdao.findAll();

java.util.Date debut=null;
debut=(java.util.Date)request.getAttribute("debut");
java.util.Date fin=null;
fin=(java.util.Date)request.getAttribute("fin");


PositionnerPersonneDAO posdao=new PositionnerPersonneDAO();


int total=ofdao.totalOffresFourchette(debut, fin);

int autre=ofdao.totalOffresAutre(debut, fin);
int ai=ofdao.totalOffresai(debut, fin);
int cae=ofdao.totalOffresCae(debut, fin);
int avenir=ofdao.totalOffresAvenir(debut, fin);
int cdd=ofdao.totalOffresCdd(debut, fin);
int cdi=ofdao.totalOffresCdi(debut, fin);
int alter=ofdao.totalOffresAlternance(debut, fin);
int aicdi=ofdao.totalOffresAiCdi(debut, fin);
int aicdd=ofdao.totalOffresAiCdd(debut, fin);


int pourvue=ofdao.totalOffresPourvues(debut, fin);
int nonpourvue=ofdao.totalOffresNonPourvues(debut, fin);
int annule=ofdao.totalOffresAnnule(debut, fin);
int autrerecrut=ofdao.totalOffresAutreRecrute(debut, fin);

int semaine=ofdao.totalOffresUneSemaine(debut, fin);
int mois=ofdao.totalOffresUnMois(debut, fin);
int an=ofdao.totalOffresPlusUnAn(debut, fin);
int semestremoins=ofdao.totalOffresUnSemestre(debut, fin);
int semestreplus=ofdao.totalOffresMoinsUnAn(debut, fin);

int position=posdao.totalOffresPositionnees(debut, fin); 
int retenu=posdao.totalOffresPositionneesRetenues(debut, fin);
	
			
%>
</head>
<body>

	<div id="body">
		<br>
		<h3>GENERALITES</h3>
		<br>
		<table id="table1">
			<tr class="centre">
				<td>TOTAL OFFRE</td>
				<td>POSITION</td>
				<td>RETENUS</td>
				<td>POURVUES</td>
				<td>LIBRE</td>
				<td>ANNULEES</td>
				<td>AUTRE RECRUT</td>
			</tr>
			
			<tr id="vert">
				<td id="rouge"><%=total%></td>
				<td><%=position %></td>
				<td><%=retenu %></td>
				<td><%=pourvue %></td>
				<td><%=nonpourvue %></td>
				<td><%=annule %></td>
				<td><%=autrerecrut %></td>
			</tr>
			
		</table>
		<br>
		
		
		<hr>
		<br>
		<h3>DUREE OFFRES</h3>
		<br>
		<%
			if(debut!=null){
			%>
		<table id="table1">
		<tr class="centre">
		<td>DUREE</td>
		<td>Résultat</td>
		</tr>
		<tr class="tab">
		
		<td> moins de 1 semaine </td>
		<td class="centre"><%=semaine %></td>
		</tr>
		<tr class="tab">
		<td> moins de  1 mois </td>
		<td class="centre"><%=mois %></td>
		</tr>
		<tr class="tab">
		<td> de  1 à 6 mois </td>
		<td class="centre"><%=semestremoins %></td>
		</tr>
		<tr class="tab">
		<td> de  6 mois  à 1 an </td>
		<td class="centre"><%=semestreplus %></td>
		</tr>
		<tr class="tab">
		<td> plus de 1 an </td>
		<td class="centre"><%=an %></td>
		</tr>
		<%
		}
		%>
		</table>
		
		
		<br>
		<hr>
		<br>
		<h3>OFFRES PAR CATEGORIES</h3>
		<br>
		<%
			if(debut!=null){
			%>
		<table id="table1" >
		<tr class="centre">
		<td >ROME</td>
		<td>Résultat</td>
		
		</tr>
		<%
						
		for(int x=0;x<lstrome.size();x++){
		%>
		<tr  class="tab">
		<td ><%=lstrome.get(x).getNrome() %> - <%=lstrome.get(x).getIntitule() %></td>
		<td class="centre"><%=ofdao.totalOffresParMetierRome(debut, fin, lstrome.get(x).getIdrome())%></td>
		</tr>
		<%
		}}
		%>
		</table>
		
		
		<br>
		<hr>
		<br>
		<h3>TYPE DE CONTRATS</h3>
		<br>
		<%
			if(debut!=null){
			%>
		<table id="table1" >
		<tr class="centre">
		<td>TYPE DE CONTRAT</td>
		<td>Résultat</td>
		
		</tr>
		
		<tr class="tab">
		<td>AI</td>
		<td class="centre"><%=ai %></td>
		</tr>
		<tr class="tab">
		<td>AI - CDD</td>
		<td class="centre"><%=aicdd %></td>
		</tr>
		<tr class="tab">
		<td>AI -CDI</td>
		<td class="centre"><%=aicdi %></td>
		</tr>
		<tr class="tab">
		<td>Alternance</td>
		<td class="centre"><%=alter %></td>
		</tr>
		<tr class="tab">
		<td>AVENIR</td>
		<td class="centre"><%=avenir %></td>
		</tr>
		<tr class="tab">
		<td>CAE</td>
		<td class="centre"><%=cae %></td>
		</tr>
		<tr class="tab">
		<td>CDD</td>
		<td class="centre"><%=cdd %></td>
		</tr>
		<tr class="tab">
		<td>CDI</td>
		<td class="centre"><%=cdi %></td>
		</tr>
		<tr class="tab">
		<td>AUTRE</td>
		<td class="centre"><%=autre %></td>
		</tr>
		<%
		}
		%>
		</table>
		

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



	<script type="text/javascript">
		var titre = document.getElementById("creation").innerHTML;
		var uri = window.location.href.split("datedebutrech=")[1];
		/*si la date de debut de recherche est renseignee on peut modifier le titre 
		en rajoutant le fourchette de recherche*/
		if (uri != null) {
			/*on extrait les champs debut et fin de l'url*/
			var uri1 = uri.split("&");
			var date1 = uri1[0];
			var date2 = window.location.href.split("&datefinrech=")[1];
			titre += " ENTRE LE " + date1 + " ET LE " + date2;
			document.getElementById("creation").innerHTML = titre;
		}
	</script>

</body>
</html>