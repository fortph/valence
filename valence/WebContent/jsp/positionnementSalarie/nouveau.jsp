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
	href="/valence/css/positionnement/affichageoffreemp.css">
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

		//liste des champs qui doivent etre renseign�s ou dont le 
		//contenu est a verifier
		
		var utilisateurs= document.getElementById("utilisateurs").value;
		
		if (utilisateurs == "Aucun") {
			document.form1.utilisateurs.focus();
			// Afficher un message d'erreur
			attentionComplet("Le r�f�rent doit �tre renseign� !!!!");
			return false;

		}
		
		
		formulaire.submit();
		return true;
	}
</script>

<%
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
String numoffre=request.getParameter("numoffre");
String personne=request.getParameter("personne");
int numero=Integer.parseInt(numoffre);
PositionnerPersonneDAO posdao=new PositionnerPersonneDAO();
List <PositionnerPersonne> liste=posdao.personnesPositionneesParOffre(numero);

IdentiteDAO iddao=new IdentiteDAO();
Identite identite=iddao.findByID(Integer.parseInt(personne));
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
		<input type="hidden" name="action" value="validationdirecteoffre" />
		<input type="hidden" name="numoffre" value="<%=numoffre %>" />
		<input type="hidden" name="personne" value="<%=personne %>" />
			<table id="table1">
				<tr class="col1">
					<td>NOM PRENOM</td>
					<td class="large2">REFERENT</td>
					<td class="large3" >REPONSE</td>
				</tr>
				<tr>
					<td><%=identite.getNom_IDE()%> <%=identite.getPrenom_IDE()%></td>

					<td class="large4"><select name="utilisateurs" id="utilisateurs"
						onfocus="afficheCapUtilisateurs();" >
						<option>Aucun</option></select></td>
					<td class="large5"><input type="text" name="reponse" id="reponse" /></td>
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
			src="/valence/javascript/scripts/dwrfonctionsemployeurs.js"></script>

		<br>

	</div>

</body>
</html>