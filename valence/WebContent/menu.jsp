<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="/valence/css/menu.css" />
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/jquery-ui.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>accueil</title>
</head>
<body>

	<!-- partie gauche du menu -->
	<div id="navigation">
		<div class="bloc">


			<h2 class="ascenceur" id="accueil">Accueil</h2>
			<div>
				<ul>
					<li><a href="jsp/accueil/rechercherPersonne.jsp">Rechercher</a></li>
					<li><a href="jsp/recherche/accueil/rechercheIdentite.jsp">Recherche
							par critères</a></li>
					<li><a
						href="jsp/recherche/accueil/rechercheIdentitepourcent.jsp">Statistiques
							en %</a></li>

				</ul>
			</div>



			<h2 class="ascenceur" id="formation">Formation Pyramide</h2>
			<div>
				<ul>

					<li><a href="jsp/formation/rechercherPersonneFormation.jsp">Enregistrement/Inscription</a></li>

					<li><a href="jsp/formation/listeFormations.jsp">Liste des
							formations</a></li>
					<li><a href="jsp/formation/creernouvelleformation.jsp">
							Nouvelle formation</a></li>
					<li><a href="jsp/formation/listeOrganismeFormation.jsp">Liste
							des organismes de formation</a></li>
					<li><a href="jsp/formation/listeenregistresparformation.jsp">Liste
							Enregistrés par Formation</a></li>
					<li><a href="jsp/formation/listeinscritsparformation.jsp">Liste
							Inscrits par Formation</a></li>
					<li><a
						href="jsp/recherche/formation/recherchelisteformationsanneespyramide.jsp">Liste
						formations par Exercice </a></li>
						<li><a
						href="jsp/recherche/formation/recherchelisteformationsanneecivile.jsp">Liste
							formations par années Civiles</a></li>
							<li><a
						href="jsp/recherche/formation/rechercheautrestatformation.jsp">Filtre
							par formation</a></li>
					
					<li><a
						href="jsp/recherche/formation/rechercheautrestatformationannee.jsp">Filtre
							par année</a></li>
					<li><a
							href="jsp/recherche/formation/recherchelisteformationsanneecivile.jsp">Stagiaires
								par année civile</a></li>

						<li><a
							href="jsp/recherche/formation/recherchelisteformationsanneespyramide.jsp">Stagiaires
								par Exercice</a></li>
							
					<li><a href="jsp/recherche/formation/rechechestatpourcent.jsp">Statistiques
							 par Exercice</a></li>
					
					<li><a
						href="jsp/recherche/formation/rechechestatpourcentcivil.jsp">Statistiques
							par années Civiles</a></li>
				</ul>
			</div>


			<h2 class="ascenceur" id="forpro">Formation professionnelle</h2>
			<div>
				<ul>
					<li><a
						href="jsp/formationpro/rechercherPersonneFormationPro.jsp">Inscription</a></li>
					<li><a href="jsp/formationpro/listingTousEmployeurs.jsp">Listing
							Employeurs</a></li>
					<li><a href="/valence/jsp/formationpro/nouvelleformation.jsp">Nouvelle
							Formation</a></li>

					<li><a
						href="jsp/recherche/formationpro/listeInscritsparFormationDate.jsp">Liste
							Inscrits par formation</a></li>
					<li><a
						href="jsp/recherche/formationpro/listeInscritsParEmployeur.jsp">Liste
							Stagiaires par Employeur</a></li>
					<li><a
						href="jsp/recherche/formationpro/recherchestatformationproannee.jsp">Statistiques
							annuelles</a></li>
				</ul>
			</div>


		</div>
	</div>
	<!-- partie droite du menu -->

	<div id="navigation1">
		<div class="bloc1">
			<h2 class="ascenceur" id="employeur">Employeurs</h2>
			<div>
				<ul>
					<li><a href="/valence/jsp/employeurs/rechercherEmployeur.jsp">Rechercher</a>

					</li>
					<li><a href="jsp/employeurs/rechercherEmployeur.jsp">Création</a></li>
					<li><a href="jsp/employeurs/offres/creation.jsp">Offre</a></li>

				</ul>
			</div>
			<h2 class="ascenceur" id="capemploi">A I</h2>
			<div>
				<ul>
					<li><a href="/valence/jsp/ai/recherche/rechercheavance.jsp">Recherche
							avancée</a></li>
					<li><a href="/valence/jsp/ai/listes/pardates.jsp">Liste
							contrats par date</a></li>
					<li><a href="/valence/jsp/ai/agrement/recherchemoisannee.jsp">Agrément</a></li>

					<li><a href="/valence/jsp/pdf/ai/permanents/listing.jsp">H.
							Permanents</a></li>

					<li><a
						href="/valence/jsp/ai/visitemedicale/listingpardates.jsp">Echéances
							visites médicales</a></li>
					<li><a href="/valence/jsp/ai/dmo/selectionmois.jsp">DMO</a></li>
					<li><a href="/valence/jsp/ai/statistiques/contratseul.jsp">Stats
							Contrats seul</a></li>
					<li><a
						href="/valence/jsp/ai/statistiques/contratparavenant.jsp">Stats
							Contrats/Avenant</a></li>

					<li><a href="/valence/jsp/ai/statistiques/contratmois.jsp">Stats
							Contrats/Mois</a>
				</ul>
			</div>
			<h2 class="ascenceur" id="rsa">RSA</h2>
			<div>
				<ul>
					<li><a href="/valence/jsp/rmi_rsa/stats/fincontrat.jsp">Liste
							contrats par fin de contrat</a></li>
					<li><a href="/valence/jsp/rmi_rsa/stats/debutcontrat.jsp">Début
								Contrat</a></li>
					<li><a href="/valence/jsp/rmi_rsa/stats/contratencours.jsp">Liste
							contrats en cours</a></li>
				</ul>
			</div>

			<!-- 
			<h2 class="ascenceur" id="cae">CAE</h2>
			<div>
				<ul>
					<li><a href="#">aa</a></li>
					<li><a href="#">bb</a></li>
				</ul>
			</div>
		</div>
		-->
		</div>
	</div>


	<script type="text/javascript"
		src="/valence/javascript/jquery-1.10.2.js"></script>
	<script type="text/javascript"
		src="/valence/javascript/jquery-ui-1.10.3.custom.js"></script>
	<script type="text/javascript"
		src="/valence/javascript/scripts/ascenceur.js"></script>
</body>
</html>