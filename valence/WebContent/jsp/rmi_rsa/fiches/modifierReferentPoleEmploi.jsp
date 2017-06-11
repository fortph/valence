<%@ page
	import="beans.parametres.accueil.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
String refpe=request.getParameter("ref");
String pe=request.getParameter("pe");
String pers=request.getParameter("pers");
ReferentPoleEmploiDAO ref=new ReferentPoleEmploiDAO();
ReferentPoleEmploi referent=ref.findByID(Integer.parseInt(refpe));
//String numcontrat=request.getParameter("contrat");
//String numpersonne=request.getParameter("personne");
//String modif=request.getParameter("modif");
%>

	
<link rel="stylesheet" href="/valence/css/rmi/referentpe.css">


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>modification referent</title>


<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>

<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier

		var nom = document.getElementById("nom").value;
		var prenom=document.getElementById("prenom").value;
		var mail=document.getElementById("mail").value;
		var mailrsa=document.getElementById("mailrsa").value;
		var maildt=document.getElementById("maildt").value;
		
		if (nom == "") {
			document.form1.nom.focus();
			// Afficher un message d'erreur
			attentionComplet("Le nom doit être saisi !!!!");
			return false;
		}
		
		if (prenom == "") {
			document.form1.prenom.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le prénom doit être saisi !!!!");
			return false;
		}
		//test adresse mail 
		var expr = new RegExp("^[a-z0-9._-]+@[a-z0-9.-]{2,}[.][a-z]{2,4}$");
		if (mail != "" & !expr.test(mail)) { // Placer le focus dans la zone courriel
			document.form1.mail.focus();
			// Afficher un message d'erreur
			attentionComplet("Le courriel doit avoir un format correct : xxxxx@xxxxx.xx  !!!!");
			// Fin de la fonction -> Pas de submit
			return false;

		}
		
		if (mailrsa != "" & !expr.test(mailrsa)) { // Placer le focus dans la zone courriel
			document.form1.mailrsa.focus();
			// Afficher un message d'erreur
			attentionComplet("Le courriel doit avoir un format correct : xxxxx@xxxxx.xx  !!!!");
			// Fin de la fonction -> Pas de submit
			return false;

		}
		if (maildt != "" & !expr.test(maildt)) { // Placer le focus dans la zone courriel
			document.form1.maildt.focus();
			// Afficher un message d'erreur
			attentionComplet("Le courriel doit avoir un format correct : xxxxx@xxxxx.xx  !!!!");
			// Fin de la fonction -> Pas de submit
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
		<div id="creation">MODIFICATION REFERENT <%=pe %></div>
		<br>

		
		<form method="post" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="modificationreferentpe" /> 
			<input type="hidden" name="personne" value="<%=pers %>" /> 
			<input type="hidden" name="ref" value="<%=refpe %>" /> 
			<!-- <input type="hidden" name="modif" value="" /> 
			-->
			<table class="table2">
				<tr>
					<td>Nom :</td><td><input type="text" name="nom" id="nom" value="<%=referent.getNom() %>" /></td>
					<td>Prénom :</td><td><input type="text" name="prenom" id="prenom" value="<%=referent.getPrenom() %>" /></td>
					</tr>

				<tr>
					<td >Mail :</td><td colspan="3"><input type="text" name="mail" id="mail" value="<%=referent.getMail() %>" /></td>
					</tr>
					
				</table>
				<br>
				<p>Mail différents services (sert aux imprimés Pole-Emploi et CG-82 )</p>
				
			<table class="table1">
				<tr>
					<td class="un">Mail Service RSA-Insertion :</td><td ><input type="text" name="mailrsa" id="mailrsa" value="<%=referent.getMailrsa() %>" /></td>
				
					</tr>

				<tr>
					<td class="un">Mail Pôle Emploi- DT : </td><td><input type="text" name="maildt" id="maildt" value="<%=referent.getMaildt() %>" /></td>
					</tr>
					
				</table>
					
				
			<br> <input type="button" id="droite" value="Enregistrer"
				onclick="valider(form1);" />
				</form>
				
				
				
		

	</div>


</body>
</html>