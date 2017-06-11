<%@ page
	import="dao.imp.identite.*, beans.identite.*,dao.imp.ai.*,beans.ai.*,
	java.util.*,beans.smic.*,java.text.*,dao.mutuelle.*,beans.mutuelle.*"%>
	
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
String idcontrat=request.getParameter("contrat");
ContratDAO agdao=new ContratDAO();
Contrat contrat=agdao.findByID(Integer.parseInt(idcontrat));
IdentiteDAO idao=new IdentiteDAO();
Identite une=idao.findByID(contrat.getIdentite().getId_IDE());
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");

AvenantDAO avndao=new AvenantDAO();
java.sql.Date debutavenant=avndao.dernierAvenantAI(contrat);
//on rajoute 1 jour a la date de fin du dernier avenant
java.util.Date debutav=new java.util.Date(debutavenant.getTime());
debutav=new FormaterDate().lendemain(debutav);
debutavenant= new java.sql.Date(debutav.getTime());
MutuelleDAO mudao = new MutuelleDAO();
Date max=mudao.couvertureMaxi(une);
Date jour=new Date();

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
<title>nouveau avenant ai</title>

<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>
			
<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		
		var datedebut = document.getElementById("datedebut").value;
		var datefin = document.getElementById("datefin").value;
		var tache = document.getElementById("tache").value;
		var lieu = document.getElementById("lieu").value;
		var facsalhor = document.getElementById("facsalhor").value;
		var salhor = document.getElementById("salhor").value;
		var panier = document.getElementById("panier").value;
		var facpanier = document.getElementById("facpanier").value;
		var dep = document.getElementById("dep").value;
		var facdep = document.getElementById("facdep").value;
		var divers = document.getElementById("divers").value;
		var facdivers = document.getElementById("facdivers").value;
		var dureehebdo=document.getElementById("dureehebdo").value;
		
		if (datedebut == "") {
			document.form1.datedebut.focus();
			// Afficher un message d'erreur
			attentionComplet(" La date de début doit être renseignée !!!!");
			return false;
		}
		
		if (datefin == "") {
			document.form1.datefin.focus();
			// Afficher un message d'erreur
			attentionComplet("La date de fin doit être renseignée !!!!");
			return false;
		}

		if (dureehebdo != "" && isNaN(dureehebdo)) {
			document.form1.dureehebdo.focus();
			// Afficher un message d'erreur
			attentionComplet(" La durée hebdomadaire doit être un nombre !!!!");
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
			attentionComplet(" Le lieu de travail doit être précisé !!!!");
			return false;
		}
		
		
		if (salhor == ""|| isNaN(salhor)) {
			document.form1.salhor.focus();
			// Afficher un message d'erreur
			attentionComplet("Le salaire horaire doit être correctement renseigné !!!!");
			return false;
		}
		if (facsalhor == "" || isNaN(facsalhor)) {
			document.form1.facsalhor.focus();
			// Afficher un message d'erreur
			attentionComplet("La refacturation horaire doit être renseigné !!!!");
			return false;
		}
		
		if (panier != "" && isNaN(panier)) {
			document.form1.panier.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le montant du panier doit être correctement renseigné !!!!");
			return false;
		}
		
		if (facpanier != "" && isNaN(facpanier)) {
			document.form1.facpanier.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le montant de la refacturation du panier doit être correctement renseigné !!!!");
			return false;
		}
		
		

		if (dep != "" && isNaN(dep)) {
			document.form1.dep.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le montant des déplacements doit être correctement renseigné !!!!");
			return false;
		}
		

		if (facdep != "" && isNaN(facdep)) {
			document.form1.facdep.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le montant de la refacturation des déplacements doit être correctement renseigné !!!!");
			return false;
		}
		
		if (divers != "" && isNaN(divers)) {
			document.form1.divers.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le montant des frais divers être correctement renseigné !!!!");
			return false;
		}
		
		if (facdivers != "" && isNaN(facdivers)) {
			document.form1.facdivers.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le montant de la refacturation des frais divers doit être correctement renseigné !!!!");
			return false;
		}
		
		formulaire.submit();
		return true;
	}
</script>

</head>
<body>




	<div id="body">
		<p><%@ include file='/menus/menusai/menuficheai.jsp'%></p>
		<br>
		<div id="creation">CREATION D'UN AVENANT AU CONTRAT CDD AI N° C<%=idcontrat %></div>
		<br>
		<h3>IDENTITE</h3>
		<table class="table1">
		<tr>
		<td><%=une.getNom_IDE() %> <%=une.getPrenom_IDE() %></td><td><%=une.getCp_IDE() %> <%=une.getVille_IDE() %></td>
		</tr>
		<tr><td> <br/></td></tr>
			<tr>
			<td>Date d'échéance Mutuelle: <span id="finmutul"><% if (max!=null) out.println(sdf.format(max)); %></span></td>
			<% if(max!=null && max.before(jour)) out.println("<td class='surligne'>Penser à faire signer la proposition de mutuelle</td>");
			else if(max==null)  out.println("<td class='surligne'>Penser à faire signer la proposition de mutuelle</td>");
			%>
			</tr>
		
		</table>
		<br>
		
		<form name="form1" method="post" action="/valence/controleur" >		
			<input type="hidden" name="action" value="aicontratnouveauavenant" />
			<input type="hidden" name="numero" value="<%=une.getId_IDE() %>" />
			<input type="hidden" name="idcontrat" value="<%=idcontrat %>" />
		<h3>NOUVEL AVENANT</h3>
		
		<br>
		
		
		<table class="table2">
		<tr>
		<td class="un">DEBUT:</td><td class="de"><input type="text" id="datedebut" name="datedebut" value="<%=sdf.format(debutavenant) %>" /></td>
		<td class="un">FIN:</td><td class="de"><input type="text" id="datefin" name="datefin" ></td>
		<td class="un">Hebdo:</td><td class="de"><input type="text" id="dureehebdo" name="dureehebdo" value="<%=contrat.getHeuresminihebdo() %>"/></td>
		</tr>
		</table>
		
		<br>
		<p>Tâches:</p>
		<p class="de"><input type="text" name="tache" id="tache" value="<%=contrat.getTache() %>"/></p>
		<br>
		<p>Lieu d'exécution de la tâche:</p>
		<p class="de"><input type="text" name="lieu" id="lieu" value="<%=contrat.getLieu() %>"/></p>
		<br>
		<hr>
		<br>
		<h3>FACTURATION</h3>
		<table class="table3" >
		<tr>
		<th ></th>
		<th >PAIEMENT</th>
		<th  >FACTURATION</th>
		</tr>
		<tr>
		<td>SALAIRE HOR BRUT:</td>
		<td class="quatre"><input type="text" id="salhor" name="salhor" value="<%=contrat.getSalairehoraire() %>"/></td>
		
		<td class="quatre"><input type="text" id="facsalhor" name="facsalhor" value="<%=contrat.getFacturation()%>" /></td>
		</tr>
		<tr>
		<td>PANIERS:</td>
		<td class="quatre"><input type="text" id="panier" name="panier"  value="<%=contrat.getPanier() %>" /></td>
	
		<td class="quatre"><input type="text" id="facpanier" name="facpanier" value="<%=contrat.getFacturepanier() %>" /></td>
		</tr>
		<tr>
		<td >DEPLACEMENTS:</td>
		<td class="quatre"><input type="text" id="dep" name="dep" value="<%=contrat.getDeplacement() %>" /></td>
		
		<td class="quatre"><input type="text" id="facdep" name="facdep" value="<%=contrat.getFacturedeplace() %>" /></td>
		</tr>
		<tr>
		<td >DIVERS:</td>
		<td class="quatre"><input type="text" id="divers" name="divers" value="<%=contrat.getDivers() %>" /></td>
		
		<td class="quatre"><input type="text" id="facdivers" name="facdivers" value="<%=contrat.getFacturedivers() %>" /></td>
		</tr>
		<tr>
		<td >COMMENTAIRES:</td>
		<td class="quatre"><input type="text" id="comm" name="comm" value="<%=contrat.getCommentaire() %>"/></td>
		
		<td class="quatre"><input type="text" id="faccomm" name="faccomm" value="<%=contrat.getFacturecommentaire() %>" ></td>
		</tr>
		</table>
		<br>
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