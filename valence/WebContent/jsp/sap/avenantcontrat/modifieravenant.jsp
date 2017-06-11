<%@ page
	import="dao.imp.identite.*,beans.parametres.accueil.*, beans.identite.*,
	java.text.*,dao.imp.ai.*,beans.ai.*,java.util.*,beans.smic.*,dao.imp.sap.*, beans.sap.*"%>
	
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
String contrats=request.getParameter("numcontrat");



OrganismeDAO ordao=new OrganismeDAO();
Organisme capemploi=ordao.findByID(1);

AvenantCDIDAO csapdao=new AvenantCDIDAO();
AvenantCDI sap=csapdao.findByID(Integer.parseInt(contrats));

int ididentite=sap.getContrat().getIdentite().getId_IDE();


IdentiteDAO agdao=new IdentiteDAO();
Identite une=agdao.findByID(ididentite);


%>
<link rel="stylesheet" href="/valence/css/sap/contrat.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
	
	
<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>modifier avenant sap</title>
<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		
		
		var datedebut = document.getElementById("datedebut").value;
		var heures = document.getElementById("heures").value;
		var emploi = document.getElementById("emploi").value;		
		//var urssaf = document.getElementById("urssaf").value;		
		var salaire = document.getElementById("salaire").value;
		var panier= document.getElementById("panier").value;
		var dep= document.getElementById("dep").value;
		
		if (datedebut == "") {
			document.form1.datedebut.focus();
			// Afficher un message d'erreur
			attentionComplet(" La date de début doit être renseignée !!!!");
			return false;
		}
		
		if(emploi==""){
			document.form1.emploi.focus();
			// Afficher un message d'erreur
			attentionComplet(" L'emploi doit être indiqué !!!!");
			return false;
			
			
		}
		
		
		if (heures == "" ||isNaN(heures) ) {
			document.form1.heures.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le nombre d'heures doit être correctement renseigné !!!!");
			return false;
		}
		
		/*
		
		if (urssaf == "") {
			document.form1.urssaf.focus();
			// Afficher un message d'erreur
			alert(" ERREUR !\n\nLe numéro d'URSSAF doit être renseigné !!!!");
			return false;
		}
		
		*/
		if (salaire == "" || isNaN(salaire)) {
			document.form1.salaire.focus();
			// Afficher un message d'erreur
			attentionComplet("Le salaire horaire doit être correctement renseigné !!!!");
			return false;
		}
		 if(panier !="" && isNaN(panier)){
			 document.form1.panier.focus();
			 attentionComplet(" Le montant du panier doit être correctement renseigné !!!!");
				return false;
		 }
		 
		 if(dep !="" && isNaN(dep)){
			 document.form1.dep.focus();
			 attentionComplet(" Le montant des déplacements doit être correctement renseigné !!!!");
				return false;
		 }		
		
		formulaire.submit();
		return true;
	}
</script>
	


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>modifier avenant sap</title>

		
</head>
<body>




	<div id="body">
		<p><%@ include file='/menus/menusap/menusimpleavenantsap.jsp'%></p>
		<br>
		<div id="creation">MODIFIER AVENANT SAP N° A<%=sap.getRangavenent() %>-C<%=sap.getContrat().getId_contratcdi() %></div>
		<br>
		<h3>IDENTITE</h3>
		<table class="table1">
		<tr>
		<td><%=une.getNom_IDE() %> <%=une.getPrenom_IDE() %></td><td><%=une.getCp_IDE() %> <%=une.getVille_IDE() %></td>
		</tr>
		<tr><td>Date accueil :</td><td><% if(une.getDateAccueil_IDE()!=null) out.println(sdf.format(une.getDateAccueil_IDE())); %></td></tr>
		</table>
		<br>
		<hr>
		
		<form name="form1" method="post" action="/valence/controleur" >		
			<input type="hidden" name="action" value="sapmodifieravenant" />
			<input type="hidden" name="contrat" value="<%=sap.getId_avenant() %>" />
		<input type="hidden" name="personne" value="<%=ididentite %>" />
		
		<br>
		<h3>EMPLOYEUR</h3>
		<br>
		
		<table class="table2">
		<tr>
		<td><%=capemploi.getRs() %>-<%=capemploi.getStructure() %>
		
		</td>
		<td>N° Agrément simple : <%=sap.getContrat().getAgrement() %></td>
		
		</tr></table>
		<br>
		<table class="table1">
		<tr>
		<td class="un">DATE REDACTION AVENANT:</td><td><input type="text" id="dateredaction" class="de" name="dateredaction" value="<%=sdf.format(sap.getRedaction())%>"/></td>
		</tr>
		<tr>
		<td class="un">DATE EFFET :</td><td><input type="text" id="datedebut" class="de" name="datedebut" value="<% if(sap.getDateeffet()!=null) out.println(sdf.format(sap.getDateeffet())); %>"></td>
		
		</tr>
		
		<tr>
		<td  class="un">Descriptif Emploi :</td><td ><input type="text" name="emploi" class="de" id="emploi" value="<%=sap.getTache()%>" />
		</td>
		</tr>
		<tr>
		<td class="un">Nbre Heures Hebdomadaire:</td><td ><input type="text" autocomplete="off" id="heures" name="heures" class="de" value="<%=sap.getHeuresminimois()%>"></td>
		</tr>
		
		<tr>
		<td class="un">URSSAF N°:</td><td ><input type="text" id="urssaf" name="urssaf" class="de" value="<%=sap.getUrssaf()%>"></td>
		
		</table>
		<br>
		
		
		<br>
		<table class="table3" >
		<tr>
		<th ></th>
		<th >PAIEMENT</th>
		</tr>
		<tr>
		<td>SALAIRE HOR BRUT:</td>
		<td class="quatre"><input type="text" name="salaire" id="salaire"autocomplete="off"  value="<%=sap.getSalairehoraire()%>" /></td>
		
		</tr>
		<tr>
		<td>PANIERS:</td>
		<td class="quatre"><input type="text" id="panier" name="panier" autocomplete="off" value="<%=sap.getPanier()%>" ></td>
	
		</tr>
		<tr>
		<td >DEPLACEMENTS:</td>
		<td class="quatre"><input type="text" id="dep" name="dep" autocomplete="off" value="<%=sap.getDeplacement()%>"></td>
		
		</tr>
		
		<tr>
		<td >DIVERS:</td>
		<td class="quatre"><input type="text" id="comm" name="comm"  value="<%=sap.getCommentaire()%>"></td>
		
		</tr>
		</table>
		<br>
		<br>
		
		
		<hr>
		<br>
			<input type="button" id="droite" value="Modifier"
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
		
		<script type="text/javascript" src="/valence/javascript/jquery.mask.min.js"></script>
	<script type="text/javascript" src="/valence/javascript/scripts/formatageMask.js"></script>
	
		</div>
</body>
</html>