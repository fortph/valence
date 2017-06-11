<%@ page
	import="beans.suivi.*,dao.imp.suivi.*,java.util.*,divers.*,beans.employeurs.*,
	dao.imp.employeur.*,beans.identite.*,dao.imp.identite.*,java.text.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="/valence/css/positionnement/affichageoffreempSans.css">
	<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />

<title>nouveau positionnement</title>


<script type='text/javascript' src='/valence/dwr/engine.js'></script>
  <script type='text/javascript' src='/valence/dwr/interface/Utilisateur.js'></script>
 <script type='text/javascript' src='/valence/dwr/util.js'></script>

<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>

<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		
		var utilisateurs= document.getElementById("utilisateurs").value;
		var personne=document.getElementById("nomarechercher").value;
		
		if (personne == "") {
			document.form1.nomarechercher.focus();
			// Afficher un message d'erreur
			attentionComplet("Une personne doit être renseignée !!!!");
			return false;

		}
		if (utilisateurs == "Aucun" || utilisateurs==0) {
			document.form1.utilisateurs.focus();
			// Afficher un message d'erreur
			attentionComplet("Le référent doit être renseigné !!!!");
			return false;

		}
		
				
		formulaire.submit();
		return true;
	}
</script>

<%
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
Date jour=new Date();
String maintenant=sdf.format(jour);
String numoffre=request.getParameter("numoffre");
int numero=Integer.parseInt(numoffre);
PositionnerPersonneDAO posdao=new PositionnerPersonneDAO();
List <PositionnerPersonne> liste=posdao.personnesPositionneesParOffre(numero);

IdentiteDAO iddao=new IdentiteDAO();
//Identite identite=iddao.findByID(Integer.parseInt(personne));
%>


</head>
<body>

	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div id="creation">NOUVEAU POSITIONNEMENT A UNE OFFRE EMPLOYEUR</div>
		<br>
		<hr>
		<h3>DEJA POSITIONNES</h3>
		<br>
		<table id="table1">
			<tr class="col1">
				<td>NOM PRENOM</td>
				<td>TEL</td>
				<td>CONTACT</td>
				<td>REFERENT</td>
				<td>REPONSE</td>
			</tr>
			<%
				if(liste.size()>0){
						for(int i=0;i<liste.size();i++){
			%>
			<tr>
				<td><%=liste.get(i).getIdentite().getNom_IDE()%> <%=liste.get(i).getIdentite().getPrenom_IDE()%></td>
				<td><%=liste.get(i).getIdentite().getFixe_IDE()%></td>
				<td><% if(liste.get(i).getDatecontact()!=null) out.println(sdf.format(liste.get(i).getDatecontact())); %></td>
				<td><%=liste.get(i).getSalarie().getPrenom()%> <%=liste.get(i).getSalarie().getNom()%></td>
				<td><%=liste.get(i).getReponse()%></td>
			</tr>
			<%
				}
					}
			%>
		</table>
		<br>
		<hr>
		<br>


		<h3>POSITIONNER</h3>
		<br>
		<form method="post" name="form1" action="/valence/controleur">
		<input type="hidden" name="action" value="validationdirecteoffrehorsselection" />
		<input type="hidden" name="numoffre" value="<%=numoffre %>" />
		
			<table id="table1">
				<tr class="col1">
					<td class="large1">NOM PRENOM</td>
					<td class="large2">CONTACT</td>
					<td class="large2">REFERENT</td>
					<td class="large3">REPONSE</td>
				</tr>
				<tr>
					<td><input type="text" autocomplete="off" class="large4" name="nomarechercher" id="nomarechercher"/></td>
					<td><input 	type="text" id="dateinscription" class="large4" name="dateinscription" value="<%=maintenant %>"/></td>
					<td><select name="utilisateurs" id="utilisateurs" class="large4"
						onfocus="afficheCapUtilisateurs();" >
						<option>Aucun</option></select></td>
					<td><input type="text" name="reponse" id="reponse" class="large4"/></td>
				</tr>
			</table>
			<br>
			<input type="button" id="droite" value="Ajouter" onclick="valider(form1);" />
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
			src="/valence/javascript/jquery.ui.autocomplete.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/scripts/dwrfonctionsemployeurs.js"></script>
			<script type="text/javascript"
			src="/valence/javascript/scripts/journouveaux.js"></script>
		<script type="text/javascript"
		src="/valence/javascript/scripts/autocompleteoffreemp.js" ></script>
		<br>

	</div>

</body>
</html>