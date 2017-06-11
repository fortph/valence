
<%@ page
	import="beans.parametres.accueil.*,java.util.*,beans.identite.*,dao.imp.identite.*,
beans.suivi.*,dao.imp.suivi.*,divers.*,java.text.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
	NiveauFormationDAO nivdao=new NiveauFormationDAO();
List<String> listeniv=nivdao.afficherNiveaux();
PermisProDAO perdao=new PermisProDAO();
List<String> lispermispro=perdao.afficherPermisPro();
PrioritesDAO pridao=new PrioritesDAO();
List<String> listprio=pridao.afficherPriorites();
LocomotionDAO locodao=new LocomotionDAO();
List<String> listeloco=locodao.afficherMoyensLocomotion();


List<Identite> liste=(List<Identite>)request.getAttribute("liste");
ProfilPrioriteDAO prodao=new ProfilPrioriteDAO();
List<ProfilPriorite> profil=null;
SuiviPersonneDAO suidao=new SuiviPersonneDAO();
Date derniersuivi=null;


String rome=(String)request.getAttribute("rome");
String permib=(String)request.getAttribute("permib");
String ville=(String)request.getAttribute("ville");
String sexe=(String)request.getAttribute("sexe");
String prenom=(String)request.getAttribute("prenom");
String agemini=(String)request.getAttribute("agemini");
String agemaxi=(String)request.getAttribute("agemaxi");
String cp=(String)request.getAttribute("cp");
String sitfam=(String)request.getAttribute("sitfam");
String anpemini=(String)request.getAttribute("anpemini");
String anpemaxi=(String)request.getAttribute("anpemaxi");
String diplome=(String)request.getAttribute("diplome");
String[] niveau=(String[])request.getAttribute("niveau");
String[] permipro=(String[])request.getAttribute("permipro");
String[] priorites=(String[])request.getAttribute("priorites");
String[] locomotions=(String[])request.getAttribute("locomotions");
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>recherche avancee:souhait</title>



<link rel="stylesheet" href="/valence/css/ai/recherche/souhait.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.tablesorter.pager.css">
<link rel="stylesheet" href="/valence/javascript/css/theme.default.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<script type="text/javascript" src="/valence/dwr/engine.js"></script>
<script type="text/javascript" src="/valence/dwr/util.js"></script>



<script type="text/javascript" src="/valence/dwr/interface/Rome.js"></script>
<script type="text/javascript"
	src="/valence/dwr/interface/CodesPostaux.js"></script>
<script type="text/javascript"
	src="/valence/dwr/interface/NiveauFormation.js"></script>
<script type="text/javascript"
	src="/valence/dwr/interface/SituationFamiliale.js"></script>

</head>




<body>

	<div id="body">
		<%@ include file='/menus/menugeneral/menu.html'%>
		<br>
		<div id="creation">RECHERCHE AVANCEE</div>
		<br>

		<form method="post" action="/valence/controleur">
			<input type="hidden" name="action" value="airechercheavancee" />
			<table class="table1">
				<tr>
					<td class="centre">METIER</td>
					<td class="centre">VILLE</td>
					<td class="centre">SEXE</td>
				</tr>
				<tr>
					<td><select name="emploirome" id="emploirome"
						onfocus="recupererEmploiRechercheDefaut();">
							<%
								if (rome!=null){
							%>
							<option value="<%=rome%>" selected><%=rome%></option>
							<%
								}else{
							%>
							<option>SELECTION</option>
							<%
								}
							%>
					</select></td>

					<td><select name="ville" id="ville"
						onfocus="recupererVillesDept();">
							<%
								if (ville!=null){
							%>
							<option value="<%=ville%>" selected><%=ville%></option>
							<%
								}else{
							%>
							<option>Aucune</option>
							<%
								}
							%>
					</select></td>

					<td><select name="sexe">

							<option>Aucun</option>
							<option>FEMININ</option>
							<option>MASCULIN</option>
							<%
								if(sexe!=null){
							%>
							<option value='<%=sexe%>' selected><%=sexe%></option>
							<%
								}
							%>
					</select></td>
				</tr>
			</table>
			<br>
			<table class="table2">
				<tr>
					<td class="centre">PRENOM</td>
					<td class="centre">AGE mini</td>
					<td class="centre">AGE maxi</td>
					<td class="centre">C.P.</td>
					<td class="centre">SIT. FAM.</td>
				</tr>
				<tr>
					<td><input type="text" name="prenom"
						<%if( prenom!=null) out.println("value="+prenom);%> /></td>
					<td><input type="text" name="agemini"
						<%if(agemini!=null) out.println("value="+agemini);%> /></td>
					<td><input type="text" name="agemaxi"
						<%if(agemaxi!=null) out.println("value="+agemaxi);%> /></td>
					<td><input type="text" name="cp"
						<%if(cp!=null) out.println("value="+cp);%> /></td>
					<td><select name="situationfamiliale" id="situationfamiliale"
						onfocus="recupererSF();">
							<%
								if (sitfam!=null){
							%>
							<option value="<%=sitfam%>" selected><%=sitfam%></option>
							<%
								}else{
							%>
							<option>Aucun</option>
							<%
								}
							%>
					</select></td>
				</tr>
			</table>
			<br>


			<table class="table1">
				<tr>
					<td class="centre">ANPE MINI</td>
					<td class="centre">ANPE MAXI</td>
					<td class="centre">DIPLOME</td>
				</tr>
				<tr>
					<td><input type="text" name="anpemini"
						<%if(anpemini!=null) out.println("value="+anpemini);%> /></td>
					<td><input type="text" name="anpemaxi"
						<%if(anpemaxi!=null) out.println("value="+anpemaxi);%> /></td>
					<td><input type="text" name="diplome"
						<%if(diplome!=null) out.println("value="+diplome);%> /></td>
				</tr>
			</table>
			<br>
			<hr>
			<br>
			<h4>NIVEAU D'ETUDE</h4>
			<table class="table4">
				<tr>
					<%
						for(int i=0;i<4;i++){
					%>
					<td><input type="checkbox" name="niveau[]"
						value="<%=listeniv.get(i)%>"
						<%if(niveau!=null) {
							for(int j=0;j<niveau.length;j++){
							if(listeniv.get(i).equals(niveau[j]))
								out.println("checked");}}%>><%=listeniv.get(i).replace(" ","")%></td>
					<%
						}
					%>
				</tr>
			</table>
			<table class="table4">
				<tr>
					<%
						for(int i=4;i<8;i++){
					%>
					<td><input type="checkbox" name="niveau[]"
						value="<%=listeniv.get(i)%>"
						<%if(niveau!=null) {
							for(int j=0;j<niveau.length;j++){
							if(listeniv.get(i).equals(niveau[j]))
								out.println("checked");}}%>><%=listeniv.get(i).replace(" ","")%></td>
					<%
						}
					%>
				</tr>
			</table>

			<table class="table5">
				<tr>
					<%
						for(int i=8;i<listeniv.size();i++){
					%>
					<td><input type="checkbox" name="niveau[]"
						value="<%=listeniv.get(i)%>"
						<%if(niveau!=null) {
							for(int j=0;j<niveau.length;j++){
							if(listeniv.get(i).equals(niveau[j]))
								out.println("checked");}}%>><%=listeniv.get(i).replace(" ","")%></td>
					<%
						}
					%>
				</tr>
			</table>


			<br>
			<hr>
			<br>
			<h4>MOTIF DE PRIORITE</h4>
			<table class="table4">
				<tr>
					<%
						for(int i=0;i<4;i++){
					%>
					<td><input type="checkbox" name="priorite[]"
						value="<%=listprio.get(i)%>"
						<%if(priorites!=null) {
							for(int j=0;j<priorites.length;j++){
							if(listprio.get(i).equals(priorites[j]))
								out.println("checked");}}%>><%=listprio.get(i)%></td>
					<%
						}
					%>
				</tr>
				<tr>
					<%
						for(int i=4;i<8;i++){
					%>
					<td><input type="checkbox" name="priorite[]"
						value="<%=listprio.get(i)%>"
						<%if(priorites!=null) {
							for(int j=0;j<priorites.length;j++){
							if(listprio.get(i).equals(priorites[j]))
								out.println("checked");}}%>><%=listprio.get(i)%></td>
					<%
						}
					%>
				</tr>
				<tr>
					<%
						for(int i=8;i<12;i++){
					%>
					<td><input type="checkbox" name="priorite[]"
						value="<%=listprio.get(i)%>"
						<%if(priorites!=null) {
							for(int j=0;j<priorites.length;j++){
							if(listprio.get(i).equals(priorites[j]))
								out.println("checked");}}%>><%=listprio.get(i)%></td>
					<%
						}
					%>
				</tr>
				<tr>
					<%
						for(int i=12;i<listprio.size();i++){
					%>
					<td><input type="checkbox" name="priorite[]"
						value="<%=listprio.get(i)%>"
						<%if(priorites!=null) {
							for(int j=0;j<priorites.length;j++){
							if(listprio.get(i).equals(priorites[j]))
								out.println("checked");}}%>><%=listprio.get(i)%></td>
					<%
						}
					%>
				</tr>
			</table>
			<br>
			<hr>


			<br>
			<h4>LOCOMOTION</h4>

			<table class="table7">
				<tr>
					<%
						for(int i=0;i<listeloco.size();i++){
					%>
					<td><input type="checkbox" name="locomotion[]"
						value="<%=listeloco.get(i)%>"
						<%if(locomotions!=null) {
							for(int j=0;j<locomotions.length;j++){
							if(listeloco.get(i).equals(locomotions[j]))
								out.println("checked");}}%>><%=listeloco.get(i)%></td>
					<%
						}
					%>
				</tr>

			</table>
			<br>
			<hr>
			<br>
			<h4>PERMIS</h4>

			<table class="table8">
				<tr>
					<%
						for(int i=0;i<lispermispro.size();i++){
					%>
					<td><input type="checkbox" name="permis[]"
						value="<%=lispermispro.get(i)%>"
						<%if(permipro!=null) {
							for(int j=0;j<permipro.length;j++){
							if(lispermispro.get(i).equals(permipro[j]))
								out.println("checked");}}%>>
						<%=lispermispro.get(i)%></td>
					<%
						}
					%>
				</tr>
			</table>
			<div class="centre">
				<input type="checkbox" class="centre" name="permib" value="permib"
					<%if(permib!=null) {if(permib.equals("permib"))out.println(" checked ");
							}%>> Permis
				B
			</div>
			<br> <br> <br><span id="droite" ><input type="submit" class="boutonvert"
				value="Valider" /> </span><br> <br>
		</form>


		<br>
		<%
			if(liste!=null){
		%>

		<div id="resultat">
			<br>
			<h4>Résultat de la recherche sur :</h4>
			<br>
			<%
				if (rome!=null)
												 	out.println ("Métier : "+ rome
												 			+"<br>");
												if(ville!=null)
													out.println ("Ville : "+ ville+"<br>");
												if(permib!=null)
													out.println ("Personnes disposant du permis B <br>");
												if(sexe!=null)
													out.println ("Sexe : "+ sexe+"<br>");	
												if(prenom!=null)
													out.println ("Prénom : "+ prenom+"<br>");
												if(agemini!=null)
													out.println ("Age Minimum : "+ agemini+"<br>");
												if(agemaxi!=null)
													out.println ("Age maximum : "+ agemaxi+"<br>");
												if(cp!=null)
													out.println ("Code Postal : "+ cp+"<br>");
												if(sitfam!=null)
													out.println ("Situation Familiale : "+ sitfam+"<br>");
												if(anpemini!=null)
													out.println ("Inscription Pole Emploi minimale : "+ anpemini+"<br>");
												if(anpemaxi!=null)
													out.println ("Inscription Pole Emploi maximale : "+ anpemaxi+"<br>");
												if(diplome!=null)
													out.println ("Diplome contenant : "+ diplome+"<br>");
												
												if(niveau!=null){	
														out.println ("Niveau formation : ");
												for(int i=0;i<niveau.length;i++)
													out.println(niveau[i]+"  ");
												out.println("<br>");
												}
												
												if(permipro!=null)	{
													out.println ("Permis Professionnel : ");
													for(int i=0;i<permipro.length;i++)
														out.println(permipro[i]+" ");
													out.println("<br>");

												}	

												if(priorites!=null)	{
													out.println ("Priorités : ");
													for(int i=0;i<priorites.length;i++)
														out.println(priorites[i]+" ");
													out.println("<br>");

												}


												if(locomotions!=null)	{
													out.println ("Moyens de locomotion : ");
													for(int i=0;i<locomotions.length;i++)
														out.println(locomotions[i]+" ");
													out.println("<br>");

												}
			%>
			<br>
			<%
				if(liste!=null){

													if(liste.size()>0 ){
			%>

			<br>
			<table id="table6">
				<thead>
					<tr>
						<th>NOM</th>
						<th>COMMUNE</th>
						<th>TELEPHONE</th>
						<th>PORTABLE</th>
						<th>PRIORITE</th>
						<th>SUIVI</th>
						<th>date Pole Emploi</th>
					</tr>
				</thead>
				<tbody>
					<%
						for(int i=0;i<liste.size();i++){
							profil=prodao.afficherPrioriteParIdentite(liste.get(i));
							derniersuivi=suidao.dateDernierSuivi(liste.get(i));
						
					%>
					<tr>
						<td><a
							href="/valence/jsp/accueil/afficheInscrit.jsp?numero=<%=liste.get(i).getId_IDE()%>"><%=liste.get(i).getNom_IDE()%>
								<%=liste.get(i).getPrenom_IDE()%></a></td>
						<td><%=liste.get(i).getVille_IDE()%></td>
						<td><%=liste.get(i).getFixe_IDE()%></td>
						<td><%=liste.get(i).getMobile_IDE()%></td>
						<td>
							<%
								for(int j=0;j<profil.size();j++)out.println(profil.get(j).getLibelle()+" ");
							%>
						</td>
						<td><%if(derniersuivi!=null) out.println(sdf.format(derniersuivi));
								else out.println("01-01-2000");%></td>
						<td>
							<%
								if(liste.get(i).getPoleEmploiInscripription_IDE()!=null)
									out.println(sdf.format(liste.get(i).getPoleEmploiInscripription_IDE()));
								else
									out.println("00-00-0000");
							
							%>
						</td>
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
														}
			%>
		</div>

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
			src="/valence/javascript/jquery.tablesorter.min.js"></script>

		<script type="text/javascript"
			src="/valence/javascript/jquery.tablesorter.pager.min.js"></script>

		<script type="text/javascript"
			src="/valence/javascript/scripts/tableautablesorter.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/scripts/instructions.js"></script>

		<br>


	</div>

</body>
</html>