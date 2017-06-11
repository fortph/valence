<%@page
	import="dao.imp.identite.*, beans.identite.*, java.util.*,java.text.*,dao.imp.suivi.*,beans.suivi.*,
	dao.imp.formation.*,beans.formation.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet" href="/valence/css/suivi/suivi.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />


<script type='text/javascript' src='/valence/dwr/engine.js'></script>
<script type="text/javascript"
	src="/valence/dwr/interface/AnimatriceDAO.js"></script>
<script type='text/javascript' src='/valence/dwr/util.js'></script>


<script type="text/javascript"
	src="/valence/javascript/scripts/attention.js"></script>

<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier

		var utilisateurs = document.getElementById("animatriceformation").value;
		var dateredaction=document.getElementById("dateredaction").value;
		
		if (utilisateurs == "Aucun" || utilisateurs == 0) {
			document.form1.utilisateurs.focus();
			// Afficher un message d'erreur
			attentionComplet("Le référent doit être renseigné !!!!");
			return false;

		}

		formulaire.submit();
		return true;
	}
</script>


<%
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	String suivi = request.getParameter("suivi");
	int nsuivi=Integer.parseInt(suivi);
	AccompagnementFormationDAO suidao = new AccompagnementFormationDAO();	
	AccompagnementFormation suivipersonne = suidao.findByID(nsuivi);
	
	PropositionsFormationDAO prodao=new PropositionsFormationDAO();
	List<PropositionsFormation> listeprop=prodao.findByIDAccompagnement(suivipersonne);
	
	String prop1="",com1="",prop2="",com2="",prop3="",com3="",prop4="",com4="",prop5="",com5="";
	for(int i=0;i<listeprop.size();i++){
		if("Pyramide".equals(listeprop.get(i).getNomProposition())){
			prop1="checked";
			com1=listeprop.get(i).getCommentaire();
		}
		else if("Formation Pro".equals(listeprop.get(i).getNomProposition())){
			prop2="checked";
			com2=listeprop.get(i).getCommentaire();
		}
		else if("Cyber-Base".equals(listeprop.get(i).getNomProposition())){
			prop3="checked";
			com3=listeprop.get(i).getCommentaire();
		}
		else if("Vodéclic".equals(listeprop.get(i).getNomProposition())){
			prop4="checked";
			com4=listeprop.get(i).getCommentaire();
		}
		else if("Autres".equals(listeprop.get(i).getNomProposition())){
			prop5="checked";
			com5=listeprop.get(i).getCommentaire();
		}
	}
	IdentiteDAO idao = new IdentiteDAO();
	Identite ident = idao.findByID(suivipersonne.getIdentite().getId_IDE());
	AnimatriceDAO anidao=new AnimatriceDAO();
	Animatrice animatrice=anidao.findByID(suivipersonne.getReferent().getId_animatrice());
%>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>modifier accompagnement</title>
</head>
<body>

	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div id="creation">
			MODIFIER ACCOMPAGNEMENT FORMATION pour
			<%=ident.getPrenom_IDE()%>
			<%=ident.getNom_IDE()%></div>
		<br> <br />
		<form method="post" name="form1" action="/valence/controleur">
			 <input type="hidden" name="action" value="modifsuiviaccompform" /> <input
				type="hidden" name="suivi" value="<%=suivi%>" />

			<div>
				<label class="col1">Date de rédaction :</label> <input
					class="taille" type="text" id="dateredaction" name="dateredaction" value="<%=sdf.format(suivipersonne.getDateredaction()) %>" />
				<label>Nom du référent :</label> <select class="taille"
					name="animatriceformation" id="animatriceformation" onfocus="recupererAnimatriceformation();"">
					<option><%=animatrice.getNom() %></option>
				</select>
			</div>
			<br /> <br />
			<hr>
			<br />
			<h3>FORMATION DEMANDEE</h3>
			<br />


			<textarea class="col3" rows="5" name="demande" ><%=suivipersonne.getDemande()%>
</textarea>
			<br /> <br />
			<hr>
			<br />
			<h3>PROPOSITIONS</h3>
			<br />
			<table class="table1">	
				
			
			<tr><td class="neuf"><input type="checkbox" name="choixform" value="Pyramide"
			<%=prop1 %>		
			></td>
			<td class="dix">Pyramide</td>
			<td class="onze" ><input type="text" name="choixform1" value="<%=com1%>"
			
			/></td></tr>
			
			<tr><td class="neuf"><input  type="checkbox" name="choixform" value="Formation Pro"
			<%=prop2 %>					></td>
			<td class="dix"> Formation Pro</td>
			<td><input  type="text" name="choixform2" value="<%=com2%>"
			
			/></td></tr>
			
			<tr><td class="neuf"><input  type="checkbox" name="choixform" value="Cyber-Base"
			<%=prop3 %>					
			></td><td class="dix"> Cyber-Base</td>
			<td><input class="onze"  type="text" name="choixform3" value="<%=com3%>"
			
			 /></td></tr>
			 
			<tr><td class="neuf"><input  type="checkbox" name="choixform" value="Vodéclic"
			<%=prop4 %>					
			></td><td class="dix"> Vodéclic</td>
			<td><input class="onze"  type="text" name="choixform4" value="<%=com4%>"
		
			/></td></tr>
			
			<tr><td class="neuf"><input   type="checkbox" name="choixform" value="Autres"
			<%=prop5 %> 
			></td><td class="dix"> Autres</td>
			<td><input class="onze"  type="text" name="choixform5" value="<%=com5%>"
		
			/></td></tr>
			
</table>
			
			<br>
			<h4>Commentaires</h4>
			<textarea class="col3" rows="5" name="commentaires1" ><%=suivipersonne.getCommentaire()%></textarea>
			<br /> <br> <input type="button" id="droite" value="Enregistrer"
				onclick="valider(form1);" />

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
		src="/valence/javascript/scripts/instructions.js"></script>


	<script type="text/javascript"
		src="/valence/javascript/scripts/journouveaux.js"></script>

</body>
</html>