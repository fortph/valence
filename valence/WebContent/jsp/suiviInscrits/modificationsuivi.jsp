<%@ page
	import="dao.imp.identite.*, beans.identite.*,java.util.*,java.lang.*,java.text.*,
dao.imp.formation.*,beans.formation.*,dao.imp.suivi.*,beans.suivi.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet" href="/valence/css/suivi/suivi.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />


<script type='text/javascript' src='/valence/dwr/engine.js'></script>
<script type='text/javascript'
	src='/valence/dwr/interface/Utilisateur.js'></script>
<script type='text/javascript' src='/valence/dwr/util.js'></script>

<script type="text/javascript"
	src="/valence/javascript/scripts/attention.js"></script>



<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier

		var utilisateurs = document.getElementById("utilisateurs").value;

		if (utilisateurs == "Aucun") {
			document.form1.utilisateurs.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le référent doit être renseigné !!!!");
			return false;

		}

		
		formulaire.submit();
		return true;
	}
</script>

<%
SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	String suivi = request.getParameter("suivi");
	SuiviPersonneDAO suidao = new SuiviPersonneDAO();
	SuiviPersonne suivipersonne = suidao.findByID(Integer
			.parseInt(suivi));
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>modification suivi</title>
</head>
<body>


	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div id="creation">MODIFICATION SUIVI de <%=suivipersonne.getIdentite().getPrenom_IDE() %> <%=suivipersonne.getIdentite().getNom_IDE() %></div>
		<br>
		<hr>

		<br>
		<form method="post" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="modificationsuivipersonne" />
			<input type="hidden" name="suivi" value="<%=suivi%>" />

			<div>
				<label class="col1">Date de rédaction :</label> <input
					class="taille" type="text" id="dateredaction" name="dateredaction"
					value="<% if(suivipersonne.getDatesuivi()!=null) out.println(sdf.format(suivipersonne.getDatesuivi())); %>" /> <label>Nom du
					référent :</label> <select class="taille" name="utilisateurs"
					id="utilisateurs" onfocus="afficheCapUtilisateurs();"><option><%=suivipersonne.getReferent().getNom()%> <%=suivipersonne.getReferent().getPrenom()%></option>
				</select>
			</div>
			<br>
			<p class="col1">COMMENTAIRES</p>
			<textarea class="col3" rows="5" name="commentaires"
				 ><%=suivipersonne.getCommentaire()%>
</textarea>
			<br> <br> <input type="button" id="droite"
				value="Enregistrer" onclick="valider(form1);" />
		</form>

		<br>
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
		src="/valence/javascript/scripts/dwrfonctionsemployeurs.js"></script>


	<script type="text/javascript"
		src="/valence/javascript/scripts/journouveaux.js"></script>

</body>
</html>