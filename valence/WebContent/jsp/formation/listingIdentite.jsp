<%@page
	import="dao.imp.identite.*,divers.*,dao.imp.formation.*,java.text.SimpleDateFormat, beans.identite.*,beans.formation.*,java.sql.*, java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/valence/css/formation/affichageliste.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />

<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables_themeroller.css">
<title>Recherche</title>
<%
List<Identite> liste = (List<Identite>) request.getAttribute("resultset");
PreInscriptionDAO predao=new PreInscriptionDAO();
List<PreInscription> pre=null;


//PreInscription pre1=new PreInscription();	
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>
<div id="body">
<p><%@ include file='/menus/menugeneral/menu.html'%></p>
	
	<div id="creation" >PYRAMIDE : ENREGISTREMENT/INSCRIPTION</div>
	<%
			if(liste.size()>0){
				
		%>

		<br>
		<h4><%=liste.size()%>
		
		
			réponses ont été trouvées
		</h4>
		<br>
		 <input type="button" value="Créer"
			onclick="self.location.href='jsp/accueil/inscription.jsp'" />


		<!-- si des resultats ont ete trouves-->
		<table id="accueilpourcent" class="display">
			<thead>
				<tr>
					<th>NOM-PRENOM</th>
					<th>VILLE</th>
					<th>PORTABLE</th>
					<th>FIXE</th>
					<th>Modifier</th>
					<th></th>
					<th></th>
					</tr>
			</thead>
			<tbody>
		<%
			for (int i = 0; i < liste.size(); i++) {
					Identite tamp = liste.get(i);
					
					%>

		<tr>
			<td><a
				href="jsp/accueil/afficheInscrit.jsp?numero=<%=tamp.getId_IDE()%>"><%=tamp.getNom_IDE()%>
					<%=tamp.getPrenom_IDE()%></a></td>
			<td><%=tamp.getVille_IDE()%>
			<td><%=tamp.getMobile_IDE()%></td>
			<td><%=tamp.getFixe_IDE()%></td>
			<!-- lien modifier -->
			
			<td><center><a
				href="/valence/jsp/accueil/modifierInscrit.jsp?numero=<%=tamp.getId_IDE()%>"><img
					src="images/bleu/mod.png" /></a></center></td>
			<td>
			<%
			pre=predao.findByIdentite(tamp);
			
			//on récupère la date du jour
			FormaterDate madate=new FormaterDate();			
			String jj=madate.getSortie();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date=sdf.parse(jj);
			//System.out.println("date du jour="+date);
			
			java.util.Date fin=null;
			boolean vrai=false;
			boolean vrai1=false;
			for(int j=0;j<pre.size();j++){
				
				//si la date de fin de formation est enregistree
				//et si la personne est enregistree
				//et si la date de fin de formation est superieure a la date du jour
				 if(pre.get(j).getListe().getDatefin_form()!=null && pre.get(j).isEnregistre()==true  && date.compareTo(pre.get(j).getListe().getDatefin_form())<=0)
						vrai=true;
				 if( pre.get(j).getListe().getDatefin_form()!=null && pre.get(j).isInscrit()==true && date.compareTo(pre.get(j).getListe().getDatefin_form())<=0 &&
						 pre.get(j).getDateAbandon()==null	 )
						vrai1=true;}
			%>
			<button id="enregistre" style="<%  
					if(vrai==true){
						out.println("background-color:green;color:white");} %>"
					onclick="self.location.href='/valence/jsp/formation/preinscription.jsp?numero=<%=tamp.getId_IDE() %>'" >Enregistrement</button></td>
		<td><button id="inscrit" style="<%
				if(vrai1==true){
					out.println("background-color:red;color:white");} %>"
					onclick="self.location.href='/valence/jsp/formation/inscription.jsp?numero=<%=tamp.getId_IDE()%>'">Inscription</button></td>
					</tr>
		<%
					}
				%>
			</tbody>
		</table>
<br>
		<%
			}
				
			
			else {
		%>
	<p>Aucune réponse ne correspond à la recherche</p>
	<input type="button" value="Créer"
		onclick="self.location.href='/valence/jsp/inscription.jsp'" />
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
			src="/valence/javascript/jquery.dataTables.min.js"></script>

		<script type="text/javascript"
			src="/valence/javascript/scripts/tableaux.js"></script>
		<BR>
	</DIV>
	
</body>
</html>