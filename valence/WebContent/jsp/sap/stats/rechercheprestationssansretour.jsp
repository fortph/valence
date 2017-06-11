<%@ page
	import="dao.imp.identite.*, beans.identite.*,dao.imp.sap.*,beans.sap.*,
	java.util.*,java.text.*,dao.imp.employeur.*,beans.employeurs.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		
		var datedebutrech = document.getElementById("datedebutrech").value;
		var datefinrech = document.getElementById("datefinrech").value;
		
		
		
		
		if (datedebutrech == "") {
			document.form1.datedebutrech.focus();
			// Afficher un message d'erreur
			attentionComplet("La date de début doit être renseignée !!!!");
			return false;
		}
		
		if (datefinrech == "") {
			document.form1.datefinrech.focus();
			// Afficher un message d'erreur
			attentionComplet(" La date de fin doit être renseignée !!!!");
			return false;
		}
		
		
		
		formulaire.submit();
		return true;
	}
</script>

<script type="text/javascript"
	src="/valence/javascript/scripts/attention.js"></script>
<link rel="stylesheet" href="/valence/css/ai/stat/contratseul.css">
<link rel="stylesheet" href="/valence/javascript/css/theme.default.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.tablesorter.pager.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<title>recherche prestations sap </title>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	String debut=request.getParameter("debut");
	String fin=request.getParameter("fin");
	List<Integer> maliste = (List<Integer>)request.getAttribute("listepr");
	List<Integer> malisteav = (List<Integer>)request.getAttribute("listeav");
	
	List<PrestationCDI>liste=null;
	List<AvenantPrestationCDI>listeavenants=null;
	
	PrestationCDIDAO predao=new PrestationCDIDAO();
	AvenantPrestationCDIDAO avdao=new AvenantPrestationCDIDAO();
	
	if(maliste!=null){
		liste=new ArrayList<PrestationCDI>();
	for(int i=0;i<maliste.size();i++){
	PrestationCDI pres=predao.findByID(maliste.get(i));
	liste.add(pres);
	}
	
	if(malisteav!=null){
		listeavenants=new ArrayList<AvenantPrestationCDI>();
		for(int j=0;j<malisteav.size();j++){
			AvenantPrestationCDI aven=avdao.findByID(malisteav.get(j));
			listeavenants.add(aven);
		}
	}
	}
	
	IdentiteDAO idao=new IdentiteDAO();
	TachesSAPDAO tacdao=new TachesSAPDAO();
	
	EmployeurDAO empdao=new EmployeurDAO();
	//recupere le contrat en cours de chaque personne
	
	
%>


</head>
<body>
	<div id="body">
			<%@ include file='/menus/menugeneral/menu.html'%>
		<br>
		<div id="creation">RECHERCHE PRESTATIONS SAP PAR DATES DE FIN SANS RETOUR COURRIER</div>
		<br>

		<p class="centre3">Veuillez sélectionner les dates de début et de fin de recherche </p>
		<br>
		<form name="form1" method="post" action="/valence/controleur" >
		<input type="hidden" name="action" value="sapprestationspardatessansretour" />
		
		
			<table class="table5">
				
				<tr>
					<td> Du : <input type="text" class="centre" name="datedebutrech" id="datedebutrech" value="<% if(debut!=null) out.println(debut); %>" />
					</td>
					
					<td> au : <input type="text" class="centre" name="datefinrech" id="datefinrech" value="<% if(fin!=null) out.println(fin);  %>" />
					
					</td>
					 </tr>
					
			</table>
			<br>
			 <input type="button" value="Envoyer"  class="boutonvert"  id="droite" onclick="valider(form1);" />
		</form>
		<br><%
		if(liste!=null){
		if(liste.size()>0){
		%>

		<table id="sapsalaries" >
			<thead>
				<tr>
					<th>N°</th>
					<th>Employeur</th>
					<th>Date Signature</th>
					<th>Début</th>
					<th>Fin</th>
					<th>Heures</th>
					<th>Salarié</th>
					<th>Retour</th>
					<th>Taches</th>
				</tr>
			</thead>
			<tbody>

				<%	//pour chaque personne on recupere tous ces contrats
				for(int k=0;k<liste.size();k++ ){
					PrestationCDI prestation=predao.findByID(liste.get(k).getId_prestationcontrat());
					Identite une=idao.findByID(prestation.getIdentite().getId_IDE());
					Employeur emp=empdao.findByID(prestation.getEmployeur().getId_employeur());
					TachesSAP tache=tacdao.findByID(prestation.getTache().getId_tache());
					if(prestation.getDaterenouvel()==null){
				%>
				
				<tr>
					<td><a
						href="/valence/jsp/employeurs/prestations/affichageprestation.jsp?prestation=<%=prestation.getId_prestationcontrat()%>">
							P<%=prestation.getId_prestationcontrat()%>
					</a></td>
					<td><a
						href="/valence/jsp/employeurs/affichage.jsp?numero=<%=emp.getId_employeur()%>"><%=emp.getRs_employeur()%>
					</a></td>
					<td>
						<%
							if(prestation.getRedaction_pr()!=null) out.println(sdf.format(prestation.getRedaction_pr()));
						%>
					</td>
					<td>
						<%
							if(prestation.getDatedebut_pr()!=null) out.println(sdf.format(prestation.getRedaction_pr()));
						%>
					</td>
					<td>
						<%
							if(prestation.getDatefin_pr()!=null) out.println(sdf.format(prestation.getDatefin_pr()));
						%>
					</td>
					<td><center><%=prestation.getHeuresminimois_pr()%></center></td>
					<td><a
						href="/valence/jsp/accueil/afficheInscrit.jsp?numero=<%=une.getId_IDE()%>">
							<%=une.getNom_IDE()%> <%=une.getPrenom_IDE()%>
					</a></td>
					<td><% if(prestation.getDaterenouvel()!=null) out.println(sdf.format(prestation.getDaterenouvel())); %>
					<td><%=tache.getLibelle()%></td>
				</tr>
				<%
					//pour chaque prestation on recupere les avenants
					}}
						if(listeavenants.size()>0){	
								for(int j=0;j<listeavenants.size();j++){
									if(listeavenants.get(j).getDaterenouvel()==null){
									
									
				%>
					<tr>
					<td><a
						href="/valence/jsp/employeurs/avenantprestation/affichageprestation.jsp?prestation=<%=listeavenants.get(j).getId_prestationavenant()%>">
						P<%=listeavenants.get(j).getPrestation().getId_prestationcontrat()%>-A<%=listeavenants.get(j).getRangavenant() %>
					</a></td>
					<td><a
						href="/valence/jsp/employeurs/affichage.jsp?numero=<%=listeavenants.get(j).getPrestation().getEmployeur().getId_employeur()%>"><%=listeavenants.get(j).getPrestation().getEmployeur().getRs_employeur()%>
					</a></td>
					<td>
						<%
							if(listeavenants.get(j).getRedaction_pr()!=null) out.println(sdf.format(listeavenants.get(j).getRedaction_pr()));
						%>
					</td>
					<td>
						<%
							if(listeavenants.get(j).getDatedebut_pr()!=null) out.println(sdf.format(listeavenants.get(j).getDatedebut_pr()));
						%>
					</td>
					<td>
						<%
							if(listeavenants.get(j).getDatefin_pr()!=null) out.println(sdf.format(listeavenants.get(j).getDatefin_pr()));
						%>
					</td>
					<td><center><%=listeavenants.get(j).getHeuresminimois_pres()%></center></td>
					<td><a
						href="/valence/jsp/accueil/afficheInscrit.jsp?numero=<% if(listeavenants.get(j)!=null)
							out.println(listeavenants.get(j).getPrestation().getIdentite().getId_IDE()); %>">
							<% if(listeavenants.get(j)!=null)
								out.println(listeavenants.get(j).getPrestation().getIdentite().getNom_IDE()+ " "+
										listeavenants.get(j).getPrestation().getIdentite().getPrenom_IDE()); %>
					</a></td>
					<td><% if(listeavenants.get(j).getDaterenouvel()!=null) out.println(sdf.format(listeavenants.get(j).getDaterenouvel())); %>
					<td><%=listeavenants.get(j).getTache().getLibelle() %></td>
				</tr>

				<%
					}}} 
				%>
				
			</tbody>

		</table>
		
		<br>
		<div id="pager" class="pager5">
			<img src="/valence/javascript/images/icons/first.png" class="first" />
			<img src="/valence/javascript/images/icons/prev.png" class="prev" />
			<span class="pagedisplay"></span>
			<!-- this can be any element, including an input -->
			<img src="/valence/javascript/images/icons/next.png" class="next" />
			<img src="/valence/javascript/images/icons/last.png" class="last" />
			<select class="pagesize">
				<!--  <option selected="selected" value="5">5</option>-->
				<option selected="selected" value="10">10</option>
				<option value="20">20</option>
				<option value="30">30</option>
				<option value="100">100</option>
			</select>
		</div>
<% }
else {
%>
<p>Aucun resultat trouvé sur la période indiquée</p>

<%
}}
%>
		<!-- **************************************************************************-->

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
			src="/valence/javascript/jquery.tablesorter.min.js" ></script>
			<script type="text/javascript"
			src="/valence/javascript/jquery.tablesorter.pager.min.js" ></script>
		<script type="text/javascript"
			src="/valence/javascript/scripts/tableautablesorter.js"></script>
			<script type="text/javascript"
			src="/valence/javascript/scripts/jour.js"></script>
			
	
	</div>
	</body>
	</html>