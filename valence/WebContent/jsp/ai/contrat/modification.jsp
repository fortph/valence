<%@ page
	import="dao.imp.identite.*, beans.identite.*,dao.imp.ai.*,beans.ai.*,
	java.util.*,beans.smic.*,beans.employeurs.*,dao.imp.employeur.*,
	beans.parametres.accueil.*,java.text.*"%>
	
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
String idcontrat=request.getParameter("contrat");
ContratDAO contdao=new ContratDAO();
Contrat contrat=contdao.findByID(Integer.parseInt(idcontrat));
IdentiteDAO iddao=new IdentiteDAO();
Identite identite=iddao.findByID(contrat.getIdentite().getId_IDE());
EmployeurDAO empdao=new EmployeurDAO();
Employeur employeur=empdao.findByID(contrat.getEmployeur().getId_employeur());

Date deb=contrat.getDebutcontrat();
Date fin=contrat.getFincontrat();



%>
<link rel="stylesheet" href="/valence/css/ai/contrat/contrat.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
	
	
<script type="text/javascript" src="/valence/dwr/engine.js"></script>
<script type="text/javascript" src="/valence/dwr/util.js"></script>


<script type="text/javascript"
	src="/valence/dwr/interface/Utilisateur.js"></script>
<script type="text/javascript" src="/valence/dwr/interface/Employeur.js"></script>
<script type="text/javascript" src="/valence/dwr/interface/Rome.js"></script>
<script type="text/javascript" src="/valence/dwr/interface/Service.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>modification contrat ai</title>


<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>
			
			
<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		
		var employeurs = document.getElementById("employeurs").value;
		var datedebut = document.getElementById("datedebut").value;
		var datefin = document.getElementById("datefin").value;
		var tache = document.getElementById("tache").value;
		var lieu = document.getElementById("lieu").value;
		var urssaf = document.getElementById("urssaf").value;
		var emploipropose = document.getElementById("emploipropose").value;
		var facsalhor = document.getElementById("facsalhor").value;
		

		
		
		var panier=document.getElementById("panier").value;
		var facpanier=document.getElementById("facpanier").value;
		var dep=document.getElementById("dep").value;
		var facdep=document.getElementById("facdep").value;
		var divers=document.getElementById("divers").value;
		var facdivers=document.getElementById("facdivers").value;
		
		if (employeurs == "") {
			document.form1.employeurs.focus();
			// Afficher un message d'erreur
			attentionComplet(" L'employeur doit être renseigné !!!!");
			return false;
		}
		
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
		if (emploipropose == "") {
			document.form1.emploipropose.focus();
			// Afficher un message d'erreur
			attentionComplet(" L'emploi proposé doit être renseigné !!!!");
			return false;
		}
		
		if (tache == "") {
			document.form1.tache.focus();
			// Afficher un message d'erreur
			attentionComplet(" La description des taches doit être renseignée !!!!");
			return false;
		}
		
		if (lieu == "") {
			document.form1.lieu.focus();
			// Afficher un message d'erreur
			attentionComplet("Le lieu de travail doit être précisé !!!!");
			return false;
		}
		
		
		if (urssaf == "") {
			document.form1.urssaf.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le numéro d'URSSAF doit être renseigné !!!!");
			return false;
		}
		if (facsalhor == "" || isNaN(facsalhor)) {
			document.form1.facsalhor.focus();
			// Afficher un message d'erreur
			attentionComplet(" La refacturation horaire doit être  correctement renseignée !!!!");
			return false;
		}
	
		if(panier !="" && isNaN(panier)){
			document.form1.panier.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le montant du panier doit être  correctement renseignée !!!!");
			return false;
			
		}
		
		if(facpanier!="" && isNaN(facpanier)){
			document.form1.facpanier.focus();
			// Afficher un message d'erreur
			attentionComplet(" La refacturation du panier doit être  correctement renseignée !!!!");
			return false;
		}
		if(dep!="" && isNaN(dep)){
			document.form1.dep.focus();
			// Afficher un message d'erreur
			attentionComplet(" L'indemninté kilométrique doit être  correctement renseignée !!!!");
			return false;
		}
		if(facdep!="" && isNaN(facdep)){
			document.form1.facdep.focus();
			// Afficher un message d'erreur
			attentionComplet(" La refacturation de l'indemninté kilométrique doit être  correctement renseignée !!!!");
			return false;
		}
		if(divers!="" && isNaN(divers)){
			document.form1.divers.focus();
			// Afficher un message d'erreur
			attentionComplet(" Les frais divers doivent être  correctement renseignés !!!!");
			return false;
		}
		if(facdivers!="" && isNaN(facdivers)){
			document.form1.facdivers.focus();
			// Afficher un message d'erreur
			attentionComplet(" La refacturation des frais divers doit être  correctement renseignée !!!!");
			return false;
		}
		
		
		
		
		
		
		formulaire.submit();
		return true;
	}
</script>

</head>
<body>





	<div id="body">
		<p><%@ include file='/menus/menusai/menuaffichecontratai.jsp'%></p>
		<br>
		<div id="creation">MODIFICATION CONTRAT AI</div>
		<br>
		<p>Modification d'un contrat au nom de <%=identite.getPrenom_IDE()%> <%=identite.getNom_IDE() %></p>
		<p>Date de Naissance : <% if(identite.getDatenais_IDE()!=null) out.println(sdf.format(identite.getDatenais_IDE())); %></p>
		<p>Demeurant à <%=identite.getVille_IDE() %> <%=identite.getCp_IDE() %>, <%=identite.getAdr1_IDE() %></p>
		<p>N° de Sécurité Sociale : <%=identite.getNss_IDE() %></p>
		<p>Contrat N° : C<%=idcontrat %>
		<br>
		<br>
		<hr>
		<br>
	<form name="form1" method="post" action="/valence/controleur" >		
			<input type="hidden" name="action" value="aimodifiercontrat" />
			<input type="hidden" name="numerocontrat" value="<%=id_contrat %>" />
			<input type="hidden" name="idemployeur" value="<%=employeur.getId_employeur() %>" />
			<h3>EMPLOYEUR</h3>
		
		<br>
		<table class="table1">
		<tr>
		<td  class="un">EMPLOYEUR</td><td  class="de"><select name="employeurs" id="employeurs" onfocus="recupererEmployeur();"  >
		<option><%=employeur.getRs_employeur() %>-<%=contrat.getEmployeur().getId_employeur() %></option></select></td>
		</tr>
		<tr><td  class="un">SERVICE</td><td class="de"><select name="services" id="services" onfocus="recupererServices();" >
		<option><%=contrat.getService().getService() %></option>
		</select></td></tr></table>
		<br>
		<table class="table2">
		<tr>
		<td class="un">DEBUT:</td><td class="de"><input type="text" id="datedebut" name="datedebut" value="<% if(deb!=null) out.println(sdf.format(deb)); %>"></td>
		<td class="un">FIN:</td><td class="de"><input type="text" id="datefin" name="datefin" value="<% if(fin!=null) out.println(sdf.format(fin)); %>" ></td>
		</tr>
		</table>
		<table class="table1">
		<tr>
		<td  class="un">METIER</td><td  class="de"><select name="emploipropose" id="emploipropose" onfocus="recupererEmploiPropose();"  >
		<option><%=contrat.getRome().getIntitule() %></option>
		</select></td>
		</tr>
		</table>
		<br>
		<p>Tâches:</p>
		<p class="de"><input type="text" name="tache" id="tache" value="<%=contrat.getTache()%>"/></p>
		<br>
		<p>Lieu d'exécution de la tâche:</p>
		<p class="de"><input type="text" name="lieu" id="lieu" value="<%=contrat.getLieu()%>" /></p>
		<br>
		<table class="table2">
		<tr>
		<td class="trois">URSSAF N°:</td><td class="de"><input type="text" id="urssaf" name="urssaf" value="<%=contrat.getUrssaf() %>" ></td>
		<td class="trois">DUREE HEBDO MINI:</td><td class="de"><input type="text" id="dureehebdo" name="dureehebdo" value="<%=contrat.getHeuresminihebdo() %>" ></td>
		</tr>
		</table>
		<br>
		<table class="table3" >
		<tr>
		<th></th>
		<th>PAIEMENT</th>
		<th>FACTURATION</th>
		</tr>
		<tr>
		<td>SALAIRE HOR BRUT:</td>
		<td class="quatre" ><input type="text" id="salhor" name="salhor" value="<%=contrat.getSalairehoraire()%>"></td>
		
		<td class="quatre" ><input type="text" id="facsalhor" name="facsalhor" value="<%=contrat.getFacturation() %>"></td>
		</tr>
		<tr>
		<td>PANIERS:</td>
		<td class="quatre" ><input type="text" id="panier" name="panier" value="<%=contrat.getPanier() %>"></td>
	
		<td class="quatre" ><input type="text" id="facpanier" name="facpanier" value="<%=contrat.getFacturepanier()%>"></td>
		</tr>
		<tr>
		<td >DEPLACEMENTS:</td>
		<td class="quatre" ><input type="text" id="dep" name="dep" value="<%=contrat.getPanier()%>"></td>
		
		<td class="quatre" ><input type="text" id="facdep" name="facdep"  value="<%=contrat.getFacturepanier()%>"></td>
		</tr>
		<tr>
		<td >DIVERS:</td>
		<td class="quatre" ><input type="text" id="divers" name="divers" value="<%=contrat.getDivers()%>"></td>
		
		<td class="quatre" ><input type="text" id="facdivers" name="facdivers" value="<%=contrat.getFacturedivers()%>"></td>
		</tr>
		<tr>
		<td >COMMENTAIRES:</td>
		<td class="quatre" ><input type="text" id="comm" name="comm" value="<%=contrat.getCommentaire()%>"></td>
		
		<td class="quatre" ><input type="text" id="faccomm" name="faccomm" value="<%=contrat.getFacturecommentaire()%>"></td>
		</tr>
		</table>
		<br>
		<table class="table1">
		<tr>
		<td  class="trois">DATE DE REDACTION</td>
		<td  class="de"><input type="text" name="dateredaction" id="dateredaction" value="<% if(contrat.getRedaction()!=null) out.println(sdf.format(contrat.getRedaction()));%>"/></td></tr></table>
		
		<br>
			<input type="button" id="droite" value="Enregistrer" class="boutonvert"
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
		<script type="text/javascript" 
			src="/valence/javascript/scripts/dwrfonctionsemployeurs.js"></script>
		<script type="text/javascript" src="/valence/javascript/jquery.mask.min.js"></script>
	<script type="text/javascript" src="/valence/javascript/scripts/formatageMask.js"></script>
	
	
		
		</div>
		
</body>
</html>