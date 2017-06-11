<%@ page import="dao.imp.identite.*, beans.identite.*,java.util.Date,java.text.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
String id=request.getParameter("personne");
IdentiteDAO iddao=new IdentiteDAO();
Identite un=iddao.findByID(Integer.parseInt(id));

Date jour=new Date();
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
String crea=sdf.format(jour);

%>

<link rel="stylesheet" href="/valence/css/ai/fiches/fiche.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>creation fiche AI</title>

<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>

<script type="text/javascript">
/*verifie qu'au moins 1 bouton radio est coché*/
		function getRadioValue()		{   
			var val=null;
			for (var i = 0; i < document.form1.permanent.length; i++)
		    {   	if (document.form1.permanent[i].checked){
		    		val=document.form1.permanent[i].value;
		    			}
		    	
		}return val;
		}
		/*******************************************************************/
</script>


<script type="text/javascript"  >
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		
		
		//var datedebutai = document.getElementById("datedebutagrement").value;
		
		
		if (getRadioValue()==null) {
			document.form1.permanent[0].focus();
			// Afficher un message d'erreur
			attentionComplet(" Une option doit être cochée !!!!");
			return false;
		}
		
		
		
		/*if (datedebutai == "") {
			document.form1.datedebutagrement.focus();
			alert(" ERREUR !\n\nLa date de début doit être renseignée  !!!!");
			return false;
		}*/
		
		
		
		
	

		
		formulaire.submit();
		return true;
	}
</script>



</head>
<body>



	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div id="creation">CREATION FICHE AI</div>
		<br>
		
		<table class="table3">
		<tr>
		<td>NOM :</td><td class="affiche"><%=un.getNom_IDE() %></td>
		<td>PRENOM:</td>
		<td class="affiche"><%=un.getPrenom_IDE() %></td>
		</tr>
		<tr>
		<td>ADRESSE :</td>
		<td class="affiche"><%=un.getAdr1_IDE() %></td>
		<td>VILLE :</td>
		<td class="affiche"><%=un.getCp_IDE() %> <%=un.getVille_IDE() %></td>
		</tr>
		</table>
		<br>
		<hr>
		<h3>FICHE AI</h3>
		<form method="post" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="creationficheai" />
			<input type="hidden" name="personne" value="<%=id %>" />
			<br>
			<p class="centre">PERMANENT :</p>
			<div class="centre2">
			<input type="radio" name="permanent" value="oui" /> OUI&nbsp;&nbsp;
			<input type="radio" name="permanent" value="non" /> NON<br>
			</div>
			<br>
			<!-- <table class="table2" >
			<tr>
			<td class="grand" >Agrément Pole Emploi N°:</td>
			<td><input type="text" id="agrement" name="agrement" /></td>
			<td>DEBUT:</td><td><input  type="text"  id="datedebutagrement" name="datedebutagrement" /></td>
			</tr>
			
			</table>
			-->
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