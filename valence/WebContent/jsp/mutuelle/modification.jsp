<%@ page
	import="dao.imp.identite.*, beans.identite.*,java.text.*,dao.mutuelle.*,beans.mutuelle.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	String idmutuelle = request.getParameter("id_mutuelle");
	MutuelleDAO mutdao = new MutuelleDAO();
	Mutuelle mutuelle = mutdao.findByID(Integer.parseInt(idmutuelle));
	Identite une = mutuelle.getIdentite();
	boolean accept = mutuelle.isAcceptation();
	String causeref = mutuelle.getCauseRefus();

	//if (causeref.startsWith("CMU"))
%>
<link rel="stylesheet" href="/valence/css/mutuelle/mutuelle.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>modification mutuelle</title>


<script type="text/javascript"
	src="/valence/javascript/scripts/attention.js"></script>


<script type="text/javascript">
	function valider(formulaire) {
		var accepte=$("input:radio[name='acceptation']:checked").val();
		var refus=$("input:radio[name='refus']:checked").length;
		if(accepte=="non"){					
			if(refus == 0){
					attentionComplet("Une option doit être choisie spécifiant la cause du refus !!!!");
					return false;
					
				}
			}
		
		//liste des champs qui doivent etre renseignés ou dont le 

		formulaire.submit();
		return true;
	}
</script>

</head>
<body onload="cache(this);">





	<div id="body">
		<p><%@ include file='/menus/mutuelle/menusimple2.jsp'%></p>
		<br>
		<div id="creation">MODIFICATION FICHE MUTUELLE</div>

		<br>
		<hr>
		<br>
		<form name="form1" method="post" action="/valence/controleur">
			<input type="hidden" name="action" value="mutuellemodifierfiche" />
			<input type="hidden" name="id_mutuelle" value="<%=idmutuelle%>" />

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

			<h3>AFFICHAGE PROPOSITION</h3>

			<br>
			<table class="table1">


				<tr>
					<td class="un">Date de proposition</td>
					<td class="de"><input type="text" name="dateaccept"
						id="dateaccept" class="centrer"
						value="<%=sdf.format(mutuelle.getDateProposition())%>" /></td>
					<td class="un">Date Echéance</td>
					<td class="de"><input type="text" name="dateecheance"
						id="dateecheance" class="centrer"
						value="<%if (mutuelle.getDateecheance() != null)
				out.println(sdf.format(mutuelle.getDateecheance()));%>" /></td>
				</tr>
				<tr>
					<td><br></td>
				</tr>
				<tr>
					<td class="un">Acceptation</td>
					<td class="de"><input type="radio" name="acceptation"
						value="oui" <%if (accept)
				out.println("checked=checked");%>
						onclick="cache(this);" /><label>oui</label></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="radio" name="acceptation"
						onclick="cache(this);"
						<%if (!accept)
				out.println("checked=checked");%> value="non" /><label>non</label></td>
				</tr>
			</table>
			<br />
			<p id="decalage">Sélectionner la cause du refus:</p>
			<table id="refus">

				<tr>
					<td><input type="radio" name="refus" value="CMU-ACS"
						onclick="cache(this);"
						<%if (causeref.startsWith("CMU"))
				out.println("checked=checked");%> /><label>CMU-ACS</label></td>
				</tr>
				<tr>
					<td><input type="radio" name="refus" onclick="cache(this);"
						<%if (causeref.startsWith("Couv"))
				out.println("checked=checked");%>
						value="Couverture Conjoint" /><label>Couverture Conjoint</label></td>
				</tr>
				<tr>
					<td><input type="radio" name="refus" onclick="cache(this);"
						value="Multi Employeur"
						<%if (causeref.startsWith("Multi"))
				out.println("checked=checked");%> /><label>Multi-Employeurs</label></td>

				</tr>
				<tr>
					<td><input type="radio" name="refus" onclick="cache(this);"
						value="CDD < 1 an" <%if (causeref.startsWith("CDD"))
				out.println("checked=checked");%>/><label>CDD &lsaquo; 1 an</label></td>
					
				</tr>
				<tr>
					<td><br></td>
				</tr>
				<tr>
					<td><label>Date fin couverture:</label><input
						class="finmultiemp centrer" id="datefin" type="text"
						value="<%if (mutuelle.getDateEcheanceMultiEmp() != null)
				out.println(sdf.format(mutuelle.getDateEcheanceMultiEmp()));%>"
						name="finmulti" /></td>
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
			src="/valence/javascript/scripts/cachePartiePage2.js"></script>

	<script type="text/javascript"
			src="/valence/javascript/scripts/journouveaux.js"></script>

	</div>

</body>
</html>