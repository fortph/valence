<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Recherche par critere</title>
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<link rel="stylesheet" href="/valence/css/recherche/rechercheidentite.css">	
	
<script type="text/javascript" src="/valence/dwr/engine.js"></script>
<script type="text/javascript" src="/valence/dwr/util.js"></script>
<script type="text/javascript"
	src="/valence/dwr/interface/NiveauFormation.js"></script>
<script type="text/javascript"
	src="/valence/dwr/interface/Priorites.js"></script>
<script type="text/javascript"
	src="/valence/javascript/scripts/attention.js"></script>	
	<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		var debut = document.getElementById("datedebutrech").value;
		var fin = document.getElementById("datefinrech").value;
		if (debut == "") {
			document.form1.datedebutrech.focus();
			// Afficher un message d'erreur
			attentionComplet("La date  de début doit être renseignée  !!!!");
			return false;
		}
		if (fin == "") {
			document.form1.datefinrech.focus();
			// Afficher un message d'erreur
			attentionComplet("La date de fin doit être renseignée  !!!!");
			return false;
		}
		formulaire.submit();
		return true;
	}
		</script>
</head>
<body>


<div id="body">
	<p><%@ include file='/menus/menugeneral/menu.html' %></p>
<div id="creation">STATISTIQUES ACCUEIL : SELECTION CRITERES</div>
<br/>
<form method="post" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="statIdentite" />
			<table>
				<tr><td>
					<label for="datedebutrech">Date accueil de Début de recherche :</label></td>
				<td>
					<input type="text" id="datedebutrech" class="largeur centre" name="datedebutrech" /></td>
				</tr><tr>
				<td>
					<label for="datefinrech">Date accueil de Fin de recherche :</label></td>
				<td>
					<input type="text" id="datefinrech" class="largeur centre "  name="datefinrech" /></td>
				</tr><tr>
				<td>
					<label for="sexe">Sexe :</label></td>
				<td>
					<select name="sexe" class="largeur centre" id="sexe" >
						<option value="vide" selected="selected" ></option>
						<option value="FEMININ">FEMININ</option>
						<option value="MASCULIN">MASCULIN</option>
				</select></td>
				</tr>
				
				
				<tr>
				<td>
					<label for="age">Age :</label></td>
				<td>
					<select name="age" class="largeur centre"  id="age" >
						<option value="vide" selected="selected" ></option>
						<option value="-25"> jusqu'à 25 ans</option>
						<option value="26 a 49">de 26 à 49 ans</option>
						<option value="+50" >50 ans et plus</option>
				</select>
				</td></tr>
				
				
				<tr><tr>
						<td>FORMATION SCOLAIRE :</td>
						<td>
							<select name="niveauformation" id="niveauformation"
							class="largeur centre"  onfocus="recupererNiveau();">
								<option value="vide" selected="selected"></option>
						</select></td>
					</tr><tr>
					
					
					<td>
						<label for="pole">Pole Emploi :</label></td>
					<td>
						<select name="pole" id="pole" class="largeur centre" >
						<option value="vide" selected="selected" ></option>
						<optgroup label="Inscrit">						
						<option value="-1 an"> moins de 1 an</option>
						<option value="-2 ans">entre 1 et 2 ans</option>
						<option value="-3 ans"> entre 2 et 3 ans</option>
						<option value="+3 ans"> plus de 3 ans</option>
						</optgroup>
						<option value="pasinscrit">Non Inscrit</option>
						</select></td>
					</tr><tr>
					
					<td>
						<label for="listepriorites">Priorités :</label></td>
					<td>
						<select name="listepriorites" id="listepriorites" 
						class="largeur centre"  >
						<option value="vide" selected="selected"></option>
						<option value="RTH">RTH</option>
						<option value="RSA">RSA</option>
						<option value="AAH">AAH</option>
						<option value="ARE">ARE</option>
						<option value="ASS">ASS</option>
						<option value="EXDET">EXDET</option>
						<option value="AIDE-SOCIALE">AIDE-SOCIALE</option>
						<option value="SALARIE">SALARIE</option>
					<!-- 	<option value="INTERIMAIRE">INTERIMAIRE</option>
						<option value="INDEPENDANT">INDEPENDANT</option>-->
						<option value="ETUDIANT">ETUDIANT</option>
						<option value="RETRAITE">RETRAITE</option>
						<option value="CIVIS">CIVIS</option>
									
						</select>
					</td></tr>
					
					<tr>
					<td>
					<label for="origine" >Origine Géographique</label></td>
					<td>
					<select name="origine" id="origine" class="largeur" >
					<option value="vide" selected="selected" ></option>
					<option value="Tarn et Garonne">Tarn et Garonne</option>
					<option value="Lot et Garonne">Lot et Garonne</option>
					<option value="VALENCE D'AGEN">VALENCE D'AGEN</option>
					<option value="CC2R">CC2R</option>
					</select></td>
					</tr>
					<tr><td></td></tr>
					
					
			
			</table>
			<br>
			<hr>
			<br>
			<div id="droite">
				<input type="button" value="Rechercher" class="boutonvert" onclick="valider(form1);" /><input
					type="reset" class="boutonrouge" value="Effacer" />
			</div>
			</form>
			
			
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
		src="/valence/javascript/scripts/jour.js"></script>
	<script type="text/javascript"
		src="/valence/javascript/scripts/instructions.js"></script>
		</div>
</body>
</html>