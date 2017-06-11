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
	List<Integer> maliste = (List<Integer>)request.getAttribute("liste");
	List<Integer> malisteav = (List<Integer>)request.getAttribute("listeav");
	List<PrestationCDI>liste=null;
	List<AvenantPrestationCDI> listeavenants=null;
	PrestationCDIDAO predao=new PrestationCDIDAO();
	AvenantPrestationCDIDAO avdao=new AvenantPrestationCDIDAO();
	//recuperation des prestations
	if(maliste!=null){
		liste=new ArrayList<PrestationCDI>();
	for(int i=0;i<maliste.size();i++){
	PrestationCDI pres=predao.findByID(maliste.get(i));
	liste.add(pres);
	}
	}
	//récuperation des avenants aux prestations
	if(malisteav!=null){
		listeavenants=new ArrayList<AvenantPrestationCDI>();
	for(int i=0;i<malisteav.size();i++){
	AvenantPrestationCDI prestav=avdao.findByID(malisteav.get(i));
	listeavenants.add(prestav);
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
		<div id="creation">RECHERCHE PRESTATIONS SAP PAR DATES DE FIN</div>
		<br>

		<p class="centre3">Veuillez sélectionner les dates de début et de fin de recherche </p>
		<br>
		<form name="form1" method="post" action="/valence/controleur" >
		<input type="hidden" name="action" value="sapprestationspardates" />
		
		
			<table class="table5">
				
				<tr>
					<td> Du : <input type="text" class="centre" name="datedebutrech" id="datedebutrech" value="<% if(debut!=null) out.println(debut); %>" />
					</td>
					
					<td> au : <input type="text" class="centre" name="datefinrech" id="datefinrech" value="<% if(fin!=null) out.println(fin);  %>" />
					
					</td>
					 </tr>
					
			</table>
			<br>
			 <input type="button" value="Envoyer"  class="boutonvert" id="droite" onclick="valider(form1);" />
		</form>
		<br><%
		if(liste!=null || listeavenants!=null){
		
		%>

		<table id="sapsalaries" >
			<thead>
				<tr>
					<th>Contrat</th>
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
				if(liste.size()>0){
				for(int k=0;k<liste.size();k++ ){
					PrestationCDI prestation=predao.findByID(liste.get(k).getId_prestationcontrat());
					Identite une=idao.findByID(prestation.getIdentite().getId_IDE());
					Employeur emp=empdao.findByID(prestation.getEmployeur().getId_employeur());
					TachesSAP tache=tacdao.findByID(prestation.getTache().getId_tache());
					
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
							if(prestation.getDatedebut_pr()!=null) out.println(sdf.format(prestation.getDatedebut_pr()));
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
					for(int k=0;k<listeavenants.size();k++ ){
						int id_contrat=	listeavenants.get(k).getPrestation().getId_prestationcontrat();
						
						//System.out.println("id prestation ="+ id_contrat);
						PrestationCDI prestation=predao.findByID(id_contrat);
						Identite une=idao.findByID(prestation.getIdentite().getId_IDE());
						Employeur emp=empdao.findByID(prestation.getEmployeur().getId_employeur());
						TachesSAP tache=tacdao.findByID(listeavenants.get(k).getTache().getId_tache());
						
									
									
									
				%>
					<tr>
					<td><a
						href="/valence/jsp/employeurs/avenantprestation/affichageprestation.jsp?prestation=<%=listeavenants.get(k).getId_prestationavenant()%>">
						P<%=prestation.getId_prestationcontrat()%>-A<%=listeavenants.get(k).getRangavenant() %>
					</a></td>
					<td><a
						href="/valence/jsp/employeurs/affichage.jsp?numero=<%=emp.getId_employeur()%>"><%=emp.getRs_employeur()%>
					</a></td>
					<td>
						<%
							if(listeavenants.get(k).getRedaction_pr()!=null) out.println(sdf.format(listeavenants.get(k).getRedaction_pr()));
						%>
					</td>
					<td>
						<%
							if(listeavenants.get(k).getDatedebut_pr()!=null) out.println(sdf.format(listeavenants.get(k).getDatedebut_pr()));
						%>
					</td>
					<td>
						<%
							if(listeavenants.get(k).getDatefin_pr()!=null) out.println(sdf.format(listeavenants.get(k).getDatefin_pr()));
						%>
					</td>
					<td><center><%=listeavenants.get(k).getHeuresminimois_pres()%></center></td>
					<td><a
						href="/valence/jsp/accueil/afficheInscrit.jsp?numero=<% if(listeavenants.get(k)!=null)
							out.println(une.getId_IDE()); %>">
							<% if(listeavenants.get(k)!=null)
								out.println(une.getNom_IDE()+" "+
								une.getPrenom_IDE()); %>
					</a></td>
					<td><% if(listeavenants.get(k).getDaterenouvel()!=null) out.println(sdf.format(listeavenants.get(k).getDaterenouvel())); %>
					<td><%=tache.getLibelle() %></td>
				</tr>

				<%
					}
				%>
				
			</tbody>

		</table>
		<%
			}
		%>
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
				<option value="99999">Tout</option>
			</select>
		</div>
<% }
else {
%>
<p>Aucun resultat trouvé sur la période indiquée</p>

<%
}
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