<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/valence/css/formation/nouvelorganisme.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>nouvel organisme</title>

<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>

<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		var org=document.getElementById("of").value;
		//var phone = document.getElementById("phone").value;
		var codep = document.getElementById("cp").value;
		
	if(org=="" || org.length==0){
		document.form4.of.focus();
		// Afficher un message d'erreur
		attentionComplet("Le nom de l'organisme est requis  !!!!");
		return false;
		
	}
		
		//test du code postal
		var ex1 = new RegExp(
				"^((0[1-9])|([1-8][0-9])|(9[0-8])|(2A)|(2B))[0-9]{3}$");
		if (!ex1.test(codep)) {
			document.form4.cp.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le code postal est incorrect  !!!!");
			return false;
		}

		//le téléphone doit etre de la forme XX.XX.XX.XX.XX
		// Vérifier avec une fonction
	/*	var exp = new RegExp(
				"^[0-9]{2}[.][0-9]{2}[.][0-9]{2}[.][0-9]{2}[.][0-9]{2}$");
		if (phone != "" && !exp.test(phone)) {// Placer le focus dans la zone telephone
			document.form4.phone.focus();
			// Afficher un message d'erreur
			alert(" E R R E U R !\n\nLe numéro de téléphone doit être de la forme XX.XX.XX.XX.XX  !!!!");
			// Fin de la fonction -> Pas de submit
			return false;

		}*/


		
		formulaire.submit();
		return true;
	}
</script>

</head>
<body>
<div id="body">
	<p><%@ include file='/menus/menugeneral/menu.html' %></p><br>
<div id="creation"> Création d'un nouvel Organisme de formation</div>
<br>
<form method="post" name="form4" action="/valence/controleur" >
<input type="hidden" name="action" value="creerorganismeformation" /> 
<table>
<tr><td class="petit">Nom de l'organisme :</td>
<td><input type="text" name="of" id="of" /></td></tr>
<tr><td class="petit">Adresse :</td>
<td><input type="text" name="adresse" id="adresse" /></td></tr>
<tr><td class="petit">Code Postal :</td>
<td><input type="text" name="cp" id="cp" /></td></tr>
<tr><td class="petit">Ville :</td>
<td><input type="text" name="ville" id="ville" /></td></tr>
<tr><td class="petit">Téléphone :</td>
<td><input type="text" name="autrephone" id="autrephone" /></td></tr>

</table>
<br>
<div id="fin">
				<input type="button" value="Ajouter" onclick="valider(form4);" /><input
					type="reset" value="Effacer" />
</div>
</form>
<br>
<br>
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
	<script type="text/javascript" src="/valence/javascript/jquery.mask.min.js"></script>
	<script type="text/javascript" src="/valence/javascript/scripts/formatageMask.js"></script>
</div>	
</body>
</html>