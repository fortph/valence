<%@ page
	import="beans.suivi.*,dao.imp.suivi.*,java.util.*,divers.*,beans.employeurs.*,dao.imp.employeur.*,
	beans.parametres.capemploi.*,beans.parametres.accueil.*,java.util.Date.*,java.text.*"%>
	
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/valence/css/offres/statistiques.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
	<link rel="stylesheet" href="/valence/javascript/css/TableTools.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables_themeroller.css">
	
	
	
	
	<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>
			
			
	
	<script type="text/javascript">
	
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier

		var datedebutrech = document.getElementById("datedebutrech").value;
		var datefinrech = document.getElementById("datefinrech").value;

		if (datedebutrech == "") {
			document.form1.datedebutrech.focus();
			// Afficher un message d'erreur
			attentionComplet("La date de début de recherche  doit être renseignée !!!!");
			return false;

		}
		
		if (datefinrech == "") {
			document.form1.datefinrech.focus();
			// Afficher un message d'erreur
			attentionComplet("La date de fin de recherche  doit être renseignée !!! !!!!");
			return false;

		}

		formulaire.submit();
		return true;
	}
</script>

<%
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
String datevide="0000-00-00 00:00:00";
java.util.Date vide=sdf.parse(datevide);
java.util.Date debut=null;
debut=(java.util.Date)request.getAttribute("debut");
java.util.Date fin=null;
fin=(java.util.Date)request.getAttribute("fin");
EmployeurDAO empdao=new EmployeurDAO();
List<Employeur> liste=null;
String titre="LISTE DES EMPLOYEURS ACTIFS";


if(debut!=null){
	titre="LISTE DES EMPLOYEURS ACTIFS DU "+sdf.format(debut)+" AU "+sdf.format(fin);
}

%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Liste employeurs actifs</title>
</head>
<body>
	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div id="creation"><%=titre %></div>

		<br>
		<form method="post" name="form1" action="/valence/controleur">
			<span><input type="hidden" name="action" value="listeemployeursactifs" />
		<label class="col1">du :</label><input type="text" id="datedebutrech" name="datedebutrech" />
		<label class="col1"> au :</label><input type="text" id="datefinrech" name="datefinrech" />
		<input type="button" id="droite" class="boutonvert" value="Envoyer"
				onclick="valider(form1);" />
		</span>
		</form>	
		<br>
		<br>
		
		<%
		if(debut!=null){
			
			liste=empdao.afficherEmployeursActifsDansFourchette(debut, fin);
			
			
	
		
		%>
		<table id="listeemployeursactifspardates" class="display">
		
		<thead>
		<tr>
		<th>Raison Sociale</th>
		<th>Création</th>		
		<th>Tél.</th>
		<th>Fax</th>
		</tr>
		</thead>
		
		<tbody>
		<%
		if(liste!=null && liste.size()>0){
		for(int i=0;i<liste.size();i++){
		%>
		<tr id="ligne">
		<td><a href="/valence/jsp/employeurs/affichage.jsp?numero=<%=liste.get(i).getId_employeur() %>" ><%=liste.get(i).getRs_employeur() %></a></td>
		<td class="madate"><% if(liste.get(i).getDatecreation()!=null) out.println(sdf.format(liste.get(i).getDatecreation())); else out.println(sdf.format(vide)); %></td>
		<td><%=liste.get(i).getTel1() %></td>
		<td><%=liste.get(i).getFax() %>
		</tr>
		<%
		}}
		%>
		</tbody>
		
		</table>
			
		<%
		
		}
		%>	
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
			<script type="text/javascript"
			src="/valence/javascript/jquery.dataTables.min.js"></script>		
		<script type="text/javascript" src="/valence/javascript/scripts/datatabletripardates.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/scripts/tableaux.js"></script>
		<script type="text/javascript" src="/valence/javascript/scripts/journouveaux.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/ZeroClipboard.js"></script>
		<script type="text/javascript" src="/valence/javascript/TableTools.js"></script>
		
		
		
		</div>
	

</body>
</html>