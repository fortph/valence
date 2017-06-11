<%@ page
	import="dao.imp.identite.*,divers.*, java.text.*,beans.identite.*,dao.imp.ai.*,beans.ai.*,java.util.*,beans.smic.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	String id = request.getParameter("numero");
	IdentiteDAO agdao = new IdentiteDAO();
	Identite une = agdao.findByID(Integer.parseInt(id));
	
	Date date = new Date();
	Date fin = new FormaterDate().plusUnAn(date);
%>
<link rel="stylesheet" href="/valence/css/mutuelle/mutuelle.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />




<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>proposition mutuelle</title>



<script type="text/javascript"
	src="/valence/javascript/scripts/attention.js"></script>


<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier

		var dateaccept = document.getElementById("dateaccept").value;
		var dateecheance = document.getElementById("dateecheance").value;
		var accepte=$("input:radio[name='acceptation']:checked").val();
		var refus=$("input:radio[name='refus']:checked").length;
		if (dateaccept == "") {
			document.form1.dateaccept.focus();
			// Afficher un message d'erreur
			attentionComplet("La date d'acceptation doit être renseignée !!!!");
			return false;
		}
	
			if(accepte=="non"){					
					if(refus == 0){
							attentionComplet("Une option doit être choisie spécifiant la cause du refus !!!!");
							return false;
							
						}
					}
				
		
		
		formulaire.submit();
		return true;
	}
</script>

</head>
<body onload="cache(this);">




	<div id="body">
		<p><%@ include file='/menus/mutuelle/menusimple2.jsp'%></p>
		<br>
		<div id="creation">FICHE MUTUELLE</div>
		<br>
		<h3>IDENTITE</h3>
		<table class="table1">
			<tr>
				<td><%=une.getNom_IDE()%> <%=une.getPrenom_IDE()%></td>
				<td><%=une.getCp_IDE()%> <%=une.getVille_IDE()%></td>
			</tr>
			<tr>
				<td>Date accueil :</td>
				<td>
					<%
						if (une.getDateAccueil_IDE() != null)
							out.println(sdf.format(une.getDateAccueil_IDE()));
					%>
				</td>
			</tr>

		</table>
		<br>

		<form name="form1" method="post" action="/valence/controleur">
			<input type="hidden" name="action" value="mutuellenouveau" />
			<input type="hidden" name="numero" value="<%=une.getId_IDE()%>" />

			<h3>PROPOSITION ADHESION</h3>

			<br>
			<table class="table1">


				<tr>
					<td class="un">Date</td>
					<td class="de"><input type="text" name="dateaccept" class="centrer"
						id="dateaccept" value="<%=sdf.format(date)%>" /></td>
					<td class="un">Date Echéance</td>
					<td class="de"><input type="text" name="dateecheance" class="centrer"
						id="dateecheance" value="<%=sdf.format(fin)%>" /></td>
				</tr>
				<tr><td><br></td></tr>
				<tr>
					<td class="un">Acceptation </td>
					<td class="de"><input type="radio" name="acceptation"
						value="oui" checked="checked" onclick="cache(this);" /><label>oui</label></td>
					</tr>
					
					<tr>
					<td> </td><td><input type="radio" name="acceptation"
						onclick="cache(this);" value="non" /><label>non</label></td>
				</tr>
			</table>
			
			<br />
			<p id="decalage" >Sélectionner la cause du refus:</p>
			<table id="refus">

				<tr>
					<td><input type="radio" name="refus" value="CMU-ACS"
						onclick="cache(this);" checked="checked" /><label>CMU-ACS</label></td>
				</tr>
				<tr>
					<td><input type="radio" name="refus" onclick="cache(this);"
						value="Couverture Conjoint" /><label>Couverture Conjoint</label></td>
				</tr>
				<tr>
					<td><input type="radio" name="refus" onclick="cache(this);"
						value="Multi Employeur" /><label>Multi-Employeurs</label></td>
					
				</tr>
				<tr>
					<td><input type="radio" name="refus" onclick="cache(this);"
						value="CDD < 1 an" /><label>CDD &lsaquo; 1 an</label></td>
					
				</tr>
				<tr>
					<td><br></td>
				</tr>
				<tr><td><label>Date fin couverture:</label><input
						class="finmultiemp centrer" id="datefin" type="text" name="finmulti" /></td>
				<tr>
				</tr>
			</table>
			<br> <br> <input type="button" id="droite"
				value="Enregistrer" onclick="valider(form1);" />
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
			src="/valence/javascript/scripts/cachePartiePage.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/scripts/journouveaux.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/jquery.mask.min.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/scripts/formatageMask.js"></script>



	</div>
</body>
</html>