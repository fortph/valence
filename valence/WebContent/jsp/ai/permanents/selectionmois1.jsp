<%@ page
	import="java.util.*,java.text.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%
	String selectionmois=request.getParameter("mois");
String selectionan=request.getParameter("an");
String selectionmoislettre=request.getParameter("moislettre");

int selectionannee=0;
if(selectionan!=null)
selectionannee=Integer.parseInt(selectionan);



Calendar cal = Calendar.getInstance();
cal.setTime(new Date());
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");

int an = cal.get(Calendar.YEAR);
int anmoins=an+1;
	HashMap<Integer,String> moisannee=new HashMap <Integer,String>();
	
	moisannee.put(1,"janvier");
	moisannee.put(2,"février");
	moisannee.put(3,"mars");
	moisannee.put(4,"avril");
	moisannee.put(5,"mai");
	moisannee.put(6,"juin");
	moisannee.put(7,"juillet");
	moisannee.put(8,"août");
	moisannee.put(9,"septembre");
	moisannee.put(10,"octobre");
	moisannee.put(11,"novembre");
	moisannee.put(12,"décembre");
	
	
%>
<link rel="stylesheet" href="/valence/css/ai/dmo/declaration.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<link rel="stylesheet" href="/valence/javascript/css/TableTools.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables_themeroller.css">

<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>
			
			
<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier

		var mois = document.getElementById("mois").value;
		var an = document.getElementById("an").value;

		if (mois == "" || mois == "Selection") {
			document.form1.mois.focus();
			// Afficher un message d'erreur
			attentionComplet(" Le mois doit être renseigné !!!!");
			return false;
		}

		if (an == "" || an == "Selection") {
			document.form1.an.focus();
			// Afficher un message d'erreur
			attentionComplet(" L'année doit être renseignée !!!!");
			return false;
		}

		formulaire.submit();
		return true;
	}
</script>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AI Permanents</title>

</head>
<body>




	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div id="creation">SELECTION PERIODE RELEVES HORAIRES</div>
		<br> <br>
		<p class="centre3">Veuillez sélectionner le mois et l'année
			désirée</p>
		<br>
		<form name="form1" method="post" action="/valence/controleur">
			<input type="hidden" name="action" value="relevehoraire" />
			<table class="table1">
				<tr>
					<th>MOIS</th>
					<th>ANNEE</th>
					<th></th>
				</tr>
				<tr>
					<td><select name="mois" id="mois">
							<option>Selection</option>
							<%
								for (int i = 1; i < 13; i++) {
							%>


							<option
								<%if (moisannee.get(i).equals(selectionmoislettre))
					out.println("selected='selected'");%>>


								<%
									out.println(moisannee.get(i));
									}
								%>
							</option>
					</select></td>
					<td><select name="an" id="an"><option>Selection</option>
							<option
								<%if (selectionannee == an)
				out.println("selected='selected'");%>><%=an%></option>
							<option
								<%if (selectionannee == anmoins)
				out.println("selected='selected'");%>><%=anmoins%></option></select>
					<td><input type="button" value="Envoyer"
						onclick="valider(form1);" /></td>
				</tr>
			</table>


		</form>
		
		
	</div>
	<br>
	
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
		
</body>
</html>