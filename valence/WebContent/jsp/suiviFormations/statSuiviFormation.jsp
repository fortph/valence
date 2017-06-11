
<%@ page
	import="beans.parametres.accueil.*,java.util.*,dao.imp.suivi.*,beans.suivi.*,java.text.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	SimpleDateFormat sdform=new SimpleDateFormat("dd-MM-yyyy");
	
	String datedebut = request.getParameter("datedebut");
	String datefin = request.getParameter("datefin");
	Map<String,Integer> retour=(Map<String,Integer>)request.getAttribute("retour");
	
	
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/valence/css/ai/contrat/pardates.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />

<title>Stats suivi formation</title>

<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier

		var datedebutrech = document.getElementById("datedebut").value;
		var datefinrech = document.getElementById("datefin").value;

		if (datedebutrech == "") {
			document.form1.datedebut.focus();
			// Afficher un message d'erreur
			attentionComplet(" La date de début doit être renseignée !!!!");
			return false;
		}

		if (datefinrech == "") {
			document.form1.datefin.focus();
			// Afficher un message d'erreur
			attentionComplet(" La date de fin doit être renseignée !!!!");
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
		<div id="creation">STATISTIQUES SUIVI FORMATION PAR DATES</div>
		<br>
		<form name="form1" method="post" action="/valence/controleur">
			<input type="hidden" name="action" value="statsuiviformation" />
			<table class="table4">
				<tr>
					<td>Du :</td>
					<td><input type="text" class="centre" id="datedebut" name="datedebut" value="<% if (datedebut!=null) out.println(datedebut); %>" /></td>
					<td>au :</td>
					<td><input type="text" class="centre" id="datefin" name="datefin" value="<% if(datefin!=null) out.println(datefin);%>"/>
					<td><input type="button" value="Rechercher" id="droite" class="boutonvert"
				onclick="valider(form1);" /></td>
				</tr>
			</table>

		</form>
		<br>
		<br>
		<!-- Affichage des résultats -->
		<%
		if(retour!=null){
		%>
		<table id="montab" >
			<thead>
				<tr>

					<th>Type de formation</th>
					<th>Quantité</th>
					
				</tr>
			</thead>
			<tbody>
				<%
					
								for (String un: retour.keySet( ) ){
				%>

				<tr>
					<td><% String cle=un; out.println(cle); %></td>
					<td><%=retour.get(cle) %></td>
				</tr>


				<%
					} 
					
					%>

				
			</tbody>
		</table>
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
			src="/valence/javascript/scripts/joursuivi.js"></script>
		
			<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>
			
		<br>

	</div>

</body>
</html>