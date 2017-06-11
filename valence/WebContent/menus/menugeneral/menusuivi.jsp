<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
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
					.write('<script src="/valence/javascript/jquery-ui-1.10.3.custom.js"><\/script>')
</script>


<!--on recupere l'id de la personne dans l'url de la	 page-->
<%
String numerop=request.getParameter("numero");
//System.out.println("dans menu id ="+numerop);
%>

<script type='text/javascript'>
//var id=window.location.href.split("numero=")[1];
var un="/valence/jsp/suiviInscrits/suivi.jsp?personne="+<%=numerop%>;
var de="/valence/jsp/suiviInscrits/suiviEmploi.jsp?personne="+<%=numerop%>;
var trois="/valence/jsp/suiviFormations/suivi.jsp?personne="+<%=numerop%>;
var quatre="/valence/jsp/suiviFormations/nouveausuiviformation.jsp?personne="+<%=numerop%>;

function modifier(){
document.getElementById("suivi").setAttribute("href",un);
}

function modifier1(){
	document.getElementById("suiviemploi").setAttribute("href",de);
	}
function modifier2(){
	document.getElementById("accfor").setAttribute("href",trois);
	}
function modifier3(){
	document.getElementById("suivifor").setAttribute("href",quatre);
	}

</script>



<script type="text/javascript">
	$(function() {
		var nav = $('#main-menu-container').offset().top;

	    $(window).scroll(function () {
	        if ($(this).scrollTop() > nav) {
	        	 $('#main-menu-container').addClass("f-nav");
	        } else {
	        	 $('#main-menu-container').removeClass("f-nav");
	        }
	    });
		$("#menu").menu({
			position : {
				my : "left top",
				at : "left top+25"
			}
		});

	});
</script>

<style>

.f-nav{  /* To fix main menu container */
    z-index: 9999;
    position: fixed;
    tex-align:center;
    top: 0;
   width: 78%;
}

.ui-menu {
	width: 15%;
	font-size: 90%;
	font-weight: bold;
	min-height: 25px;
}

ul#menu {
	width: 100%;
	background: #888;
}

ul#menu>li {
	margin: auto;
	/*largeur divise par le nombre de menus principaux soit 100/4= 25 %  */
	width: 10%;
	padding: 0;
	background: #888;
	float: left;
}

ul#menu>li>ul {
	margin: auto;
	width: 15%;
	padding: 0;
	background: #888;
	float: left;
	list-style-type: none;
}

/*.niv3{
	width: 100%;
	margin: 20%;
	background: #888;
	float: left;
	list-style-type: none;
}*/
ul#menu>li>ul>li>ul { /*position:relative;*/
	position: relative;
	width: 100%;
	padding-left: 25%;
	background: #a5a0a0;
}
</style>

</head>

<body>
<div id="main-menu-container">
	<ul id="menu">

		<li><a href="/valence/jsp/accueil/rechercherPersonne.jsp">Accueil </a>

			</li>
			
			
			<li><a href="#">Suivi </a>

			<ul>

				<li><a  id="suivi" onclick="modifier();" >
						Accompagnement emploi</a></li>
						
				<li><a id="suiviemploi" onclick="modifier1();">
						Suivi emploi</a></li>
						
					<li><a  id="accfor" onclick="modifier2();" >
						Accompagnement formation</a></li>
						
				<li><a id="suivifor" onclick="modifier3();">
						Suivi formation</a></li>

			</ul></li>

		<li><a href="#">Pyramide</a>

			<ul>

				<li><a
					href="/valence/jsp/formation/rechercherPersonneFormation.jsp">Enregistrement</a>

				</li>

				<li><a
					href="/valence/jsp/formation/rechercherPersonneFormation.jsp">Inscription</a>

				</li>
				<li><a href="/valence/jsp/formation/creernouvelleformation.jsp">Nouvelle
						formation</a></li>
				<li><a href="/valence/jsp/formation/listeFormations.jsp">Liste
						formations</a></li>
				<li><a
					href="/valence/jsp/formation/listeenregistresparformation.jsp">Liste

						Enregistrés</a></li>

				<li><a
					href="/valence/jsp/formation/listeinscritsparformation.jsp">Liste

						Inscrits (Abandon)</a></li>

				<li><a
					href="/valence/jsp/recherche/formation/rechercheautrestatformation.jsp">Recherche
						par critères</a></li>

			</ul></li>

		<li><a href="#">Formation </a>

			<ul>
				<li><a
					href="/valence/jsp/formationpro/listingTousEmployeurs.jsp">Liste
						Employeurs</a></li>
				<li><a href="/valence/jsp/formationpro/nouvelleformation.jsp">Nouvelle
						Formation</a></li>
				<li><a
					href="/valence/jsp/formationpro/rechercherPersonneFormationPro.jsp">Inscription</a>

				</li>

				<li><a
					href="/valence/jsp/recherche/formationpro/listeInscritsparFormationDate.jsp">Inscrits/formation</a>

				</li>

				<li><a
					href="/valence/jsp/recherche/formationpro/listeInscritsParEmployeur.jsp">Inscrits/Employeur</a>

				</li>



			</ul></li>

		<li><a href="#">Employeur</a>

			<ul>
				
				<li><a href="/valence/jsp/employeurs/rechercherEmployeur.jsp">Rechercher</a>

				</li>
				<li><a href="/valence/jsp/employeurs/rechercherStructureEmp.jsp">Recherche/Structure</a>

				</li>
				<li><a href="/valence/jsp/employeurs/rechercherDomaineEmp.jsp">Recherche/Domaine</a>

				
			</ul></li>
			
			
			<li><a href="#">Offres</a>

			<ul>
				
				<li><a href="/valence/jsp/employeurs/offres/creation.jsp">Nouvelle Offre</a>

				</li>
				<li><a href="/valence/jsp/employeurs/offres/nonpourvues.jsp">Consulter</a>

				
			</ul></li>


		<li><a href="#">Statistiques</a>

			<ul>

				<li><a href="#">Accueil</a>

					<ul class="niv3">

						<li><a
							href="/valence/jsp/recherche/accueil/rechercheIdentitepourcent.jsp">

								%</a></li>

						<li><a
							href="/valence/jsp/recherche/accueil/rechercheIdentite.jsp">

								critères</a></li>

					</ul></li>

				<li><a href="#">Pyramide</a>

					<ul class="niv3">

						<li><a
							href="/valence/jsp/recherche/formation/recherchelisteformationsanneecivile.jsp">Stagiaires
								par année civile</a></li>

						<li><a
							href="/valence/jsp/recherche/formation/recherchelisteformationsanneespyramide.jsp">Stagiaires
								par exercice</a></li>

						<li><a
							href="/valence/jsp/recherche/formation/rechechestatpourcent.jsp">%
								par exercice</a></li>

						<li><a
							href="/valence/jsp/recherche/formation/rechechestatpourcentcivil.jsp">%
								par année civile</a></li>
						<li><a
							href="/valence/jsp/recherche/formation/rechercheautrestatformationannee.jsp">r...</a></li>

					</ul></li>

				<li><a href="#">Formation PRO</a>
					<ul class="niv3">



						<li><a
							href="/valence/jsp/recherche/formationpro/recherchestatformationproannee.jsp">

								%</a></li>


					</ul></li>

			</ul>
	</ul>
</div>

</body>

</html>