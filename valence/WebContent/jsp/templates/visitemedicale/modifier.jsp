<%@ page
	import="dao.imp.identite.*, beans.identite.*,dao.imp.ai.*,beans.ai.*,java.util.*,java.text.*"%>
	
	
	
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
String id=request.getParameter("numvisite");
VisiteMedicaleDAO agdao=new VisiteMedicaleDAO();
VisiteMedicale visite=agdao.findByID(Integer.parseInt(id));

%>
<link rel="stylesheet" href="/valence/css/ai/fiches/fichesuite.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>modification visite medicale</title>


<script type="text/javascript"
	src="/valence/javascript/scripts/attention.js"></script>


<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier
		
		var dateconvoc = document.getElementById("dateconvoc").value;
		
		if (dateconvoc == "") {
			document.form1.dateconvoc.focus();
			// Afficher un message d'erreur
			attentionComplet(" La date de convocation doit être renseignée !!!!");
			return false;
		}
		
		formulaire.submit();
		return true;
	}
</script>

</head>
<body>




	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div id="creation">MODIFICATION VISITE MEDICALE</div>
		<br>
		<%
		if(visite!=null){
		%>
		<form name="form1" method="post" action="/valence/controleur" >		
			<input type="hidden" name="action" value="<%=destination %>" />
			<input type="hidden" name="idvisite" value="<%=visite.getId_suivi() %>" />
		<table class="table1" >
			<tr>
			<td>CONVOCATION:</td><td><input  type="text" class="centre" id="dateconvoc" name="dateconvoc" value="<% if(visite.getConvocation()!=null) out.println(sdf.format(visite.getConvocation())); %>" /></td>
			<td>VISITE:</td><td><input  type="text"   class="centre"id="datevisite" name="datevisite" value="<% if(visite.getVisite()!=null) out.println(sdf.format(visite.getVisite())); %>"/></td>
			 <td>ECHEANCE:</td><td><input  type="text"  class="centre" id="dateecheance" name="dateecheance" value="<% if(visite.getEcheance()!=null) out.println(sdf.format(visite.getEcheance())); %>" disabled="disabled" /></td>
			</tr>
			
			</table>
			<br>
			<input type="button" id="droite" value="Enregistrer"
				onclick="valider(form1);" />
			</form>
		
		<%
		}
		%>
		
			
			
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
			
		
		</div>
		
</body>
</html>