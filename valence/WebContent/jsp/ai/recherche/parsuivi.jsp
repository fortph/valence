
<%@ page
	import="beans.parametres.accueil.*,java.util.*,beans.identite.*,dao.imp.identite.*,
beans.suivi.*,dao.imp.suivi.*,divers.*,java.text.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>recherche par suivi</title>
<%
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
String nom=request.getParameter("utilisateur");
String madate=request.getParameter("date");
List<List<String>>liste=(List<List<String>>)request.getAttribute("liste");
AfficherPriorites prio=new AfficherPriorites();
IdentiteDAO idao=new IdentiteDAO();

%>

<link rel="stylesheet" href="/valence/css/ai/recherche/souhait.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.tablesorter.pager.css">
<link rel="stylesheet" href="/valence/javascript/css/theme.default.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<script type="text/javascript" src="/valence/dwr/engine.js"></script>
<script type="text/javascript" src="/valence/dwr/util.js"></script>
<script type="text/javascript"
	src="/valence/dwr/interface/Utilisateur.js"></script>
	
	
	<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>

<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier

		var utilisateurs = document.getElementById("utilisateurs").value;
				
		if(utilisateurs==0 || utilisateurs==""){
			document.form1.utilisateurs.focus();
			attentionComplet(" Un utilisateur doit être sélectionné !!!!");
			return false;
			
		}

		formulaire.submit();
		return true;
	}
</script>
	

</head>
<body>
<div id="body">
		<%@ include file='/menus/menugeneral/menu.html'%>
		<br>
		<div id="creation">RECHERCHE PAR SUIVI</div>
		<br>

		<form method="post" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="airechercheparsuivi" />
			
			<table class="table1">
				<tr>
					<td class="centre">REFERENT</td>
					<td class="centre">DATE MINI</td>
				</tr>
				<tr>
					<td><select
					name="utilisateurs" id="utilisateurs"
					onfocus="afficheCapUtilisateurs(); "><option><% if(nom!=null) out.println(nom) ;%>
									</select></td>
					<td ><input type="text" class="centre" name="datecreation"
						id="datecreation"  value="<% if(madate!=null)out.println(madate); %>" /></td>
						</tr>
						</table>
						<br> <input type="button" id="droite" value="Rechercher" class="boutonvert"
				onclick="valider(form1);" />
			
			</form>
			
			
			<br>
		<%
			if(liste!=null){
		%>

		<div id="resultat">
			<br>
			<h4>Résultat de la recherche :</h4>
			<br>
			<%
				if(liste.size()>0 ){
					
			%>

			<br>
			<table id="table7">
				<thead>
					<tr>
						<th>NOM</th>
						<th>COMMUNE</th>						
						<th>TELEPHONE</th>
						<th>PORTABLE</th>
						<th>PRIORITES</th>
						<th>DATE SUIVI</th>
						<th>N° SUIVI</th>
						</tr>
				</thead>
				<tbody>
					<%
						for(int i=0;i<liste.size();i++){
							Identite identite=idao.findByID(Integer.parseInt(liste.get(i).get(0)));
							String priorites=prio.listePrioritesPersonne(identite);
												
					%>
					<tr>
						<td><a
							href="/valence/jsp/accueil/afficheInscrit.jsp?numero=<%=liste.get(i).get(0)%>"><%=liste.get(i).get(3)%>
								<%=liste.get(i).get(4)%></a></td>
						
						<td><%=liste.get(i).get(7) %></td>
						<td><%=liste.get(i).get(6)%></td>
						<td><%=liste.get(i).get(5)%></td>
						<td><%=priorites %></td>
						<td><%=liste.get(i).get(1) %></td>
						<td><a href="/valence/jsp/suiviInscrits/accompagnement.jsp?numero=<%=liste.get(i).get(0)%>"><%=liste.get(i).get(2)%></a></td>
						</tr>
					<%
						}
					%>
				</tbody>

			</table>


			<!--************************Rajout des pager a tablesorter*********************** -->
			<div id="pager" class="pager6">
				<form>
					<img src="/valence/javascript/images/icons/first.png" class="first" />
					<img src="/valence/javascript/images/icons/prev.png" class="prev" />
					<span class="pagedisplay"></span>
					<!-- this can be any element, including an input -->
					<img src="/valence/javascript/images/icons/next.png" class="next" />
					<img src="/valence/javascript/images/icons/last.png" class="last" />
					<select class="pagesize">
						<option selected="selected" value="25">25</option>
						<option value="50">50</option>
						<option value="100">100</option>
						<option value="99999">Tout</option>
					</select>
				</form>
			</div>


			<%
				}
				else{
					out.println("Aucune personne ne correspond à cette recherche...");
												}
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
			src="/valence/javascript/scripts/journouveaux.js"></script>
		<script type="text/javascript" 
			src="/valence/javascript/scripts/dwrfonctionsemployeurs.js"></script>
			<script type="text/javascript"
			src="/valence/javascript/jquery.tablesorter.min.js"></script>

		<script type="text/javascript"
			src="/valence/javascript/jquery.tablesorter.pager.min.js"></script>

		<script type="text/javascript"
			src="/valence/javascript/scripts/tableautablesorter.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/scripts/instructions.js"></script>
			
		
	
	
		
			</div>
			</div>
</body>
</html>