<%@ page
	import="dao.imp.identite.*, beans.identite.*,dao.imp.ai.*,beans.ai.*,java.util.*,beans.smic.*"%>
	
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%

String id=request.getParameter("personne");
String id_fiche=request.getParameter("fiche");

IdentiteDAO idao = new IdentiteDAO();
Identite identite = idao.findByID(Integer.parseInt(id));

String mr = idao.afficheCivilite(identite);




%>
<link rel="stylesheet" href="/valence/css/rmi/fiche.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
	

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>nouveau contrat ai</title>


<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>
			
<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		
	
		var datedebut = document.getElementById("datedebut").value;
		var datefin = document.getElementById("datefin").value;
		
	
		
		if (datedebut == "") {
			document.form1.datedebut.focus();
			// Afficher un message d'erreur
			attentionComplet(" La date de début doit être renseignée !!!!");
			return false;
		}
		
		if (datefin == "") {
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
			<%@ include file='/menus/menurmi/menufichermi.jsp'%>

		<br>
		<div id="creation">CREATION CONTRAT INSERTION</div>
		<br>
		<br>
		<p><%=mr%>
			<%=identite.getPrenom_IDE()%>
			<%=identite.getNom_IDE()%></p>
		<br>
		
		<form name="form1" method="post" action="/valence/controleur" >		
			<input type="hidden" name="action" value="rmicontratnouveau" />
			<input type="hidden" name="numero" value="<%=id%>" />
			<input type="hidden" name="fiche" value="<%=id_fiche %>" />
		<h3>CONTRAT INSERTION</h3>
		<br>
		<table class="table2">
		<tr>
		<td>DATE DEBUT CONTRAT : </td>
		<td><input type="text" name= "datedebut" id="datedebut" /></td>
		<td>DATE FIN CONTRAT : </td>
		<td><input type="text" name= "datefin" id="datefin" /></td>
		</tr>
		
		</table>
		<br>
		
		<br>
			<input type="button" id="droite" value="Enregistrer"
				onclick="valider(form1);" />
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
			src="/valence/javascript/scripts/journouveaux.js"></script>
		
	
	
		
		</div>
		
</body>
</html>