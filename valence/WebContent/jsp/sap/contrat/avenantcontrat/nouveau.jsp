<%@ page
	import="dao.imp.identite.*,beans.parametres.accueil.*, beans.identite.*,java.text.*,dao.imp.sap.*,beans.sap.*,java.util.*,beans.smic.*"%>
	
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
String idcontrat=request.getParameter("numcontrat");

ContratCDIDAO contdao=new ContratCDIDAO();
ContratCDI cdicont=contdao.findByID(Integer.parseInt(idcontrat));

IdentiteDAO agdao=new IdentiteDAO();
Identite une=agdao.findByID(cdicont.getIdentite().getId_IDE());

OrganismeDAO ordao=new OrganismeDAO();
Organisme capemploi=ordao.findByID(1);

SmicDAO smicdao=new SmicDAO();
float smic=smicdao.smicHoraire();

Date jour=new Date();
String aff=sdf.format(jour);

%>
<link rel="stylesheet" href="/valence/css/sap/contrat.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
	
<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>
	


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>nouveau contrat sap</title>
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
			attentionComplet("Le nombre d'heures doit être correctement renseigné !!!!");
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
			attentionComplet(" Le salaire horaire doit être correctement renseigné !!!!");
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

</head>
<body>




	<div id="body">
		<p><%@ include file='/menus/menusap/menusimplesap.jsp'%></p>
		<br>
		<div id="creation">CREATION AVENANT AU CONTRAT SAP N° C<%=idcontrat %></div>
		<br>
		
		<form name="form1" method="post" action="/valence/controleur" >		
			<input type="hidden" name="action" value="sapavenantnouveau" />
			<input type="hidden" name="numero" value="<%=une.getId_IDE() %>" />
			<input type="hidden" name="contrat" value="<%=idcontrat %>" />
		
		<center><label for="dateinscription">DATE ENREGISTREMENT:</label> <input
					type="text" id="dateinscription" name="dateinscription" value="<%=aff%>"/></center>
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
		
		
		<br>
		<h3>EMPLOYEUR</h3>
		<br>
		
		<table class="table2">
		<tr>
		<td><%=capemploi.getRs() %>-<%=capemploi.getStructure() %>
		
		</td>
		<td>N° Agrément simple : <%=capemploi.getAgrementsap() %></td>
		
		</tr></table>
		<br>
		<table class="table1">
		<tr>
		<td class="un">DATE EFFET:</td><td><input type="text" id="datedebut" class="de" name="datedebut" ></td>
		
		</tr>
		</table>
		<table class="table1">
		<tr>
		<td  class="un">Descriptif Emploi :</td><td ><input type="text" name="emploi" class="de" id="emploi" value="<%=cdicont.getTache() %>" />
		</td>
		</tr>
		<tr>
		<td class="un">Nbre Heures Hebdomadaire:</td><td ><input type="text" autocomplete="off" id="heures" name="heures" class="de" value="<%=cdicont.getHeuresminimois() %>"></td>
		</tr>
		
		<tr>
		<td class="un">URSSAF N°:</td><td ><input type="text" id="urssaf" name="urssaf" class="de" value="<%=cdicont.getUrssaf() %>" ></td>
		
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
		<td class="quatre"><input type="text" name="salaire" autocomplete="off" id="salaire" value="<%=cdicont.getSalairehoraire() %>" /></td>
		
		</tr>
		<tr>
		<td>PANIERS:</td>
		<td class="quatre"><input type="text" id="panier" autocomplete="off" name="panier" value="<%=cdicont.getPanier()%>"></td>
	
		</tr>
		<tr>
		<td >DEPLACEMENTS:</td>
		<td class="quatre"><input type="text" id="dep" autocomplete="off" name="dep" value="<%=cdicont.getDeplacement()%>"></td>
		
		</tr>
		<tr>
		<td >DIVERS:</td>
		<td class="quatre"><input type="text" id="comm" name="comm" value="<%=cdicont.getCommentaire()%>"></td>
		
		</tr>
		</table>
		<br>
		<br>
			<input type="button" id="droite" value="Enregistrer"
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