<%@ page
	import="dao.imp.identite.*, beans.identite.*,dao.imp.ai.*,beans.ai.*,
	java.util.*,beans.smic.*,beans.employeurs.*,dao.imp.employeur.*,
	beans.parametres.accueil.*,divers.*,dao.exception.*,java.text.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<link rel="stylesheet" href="/valence/css/ai/stat/contratmois.css">

<%
	String debut=request.getParameter("debut");
String fin=request.getParameter("fin");

List<String> listecontrats=(List<String>)request.getAttribute("contrats");
List<String> listeavenants=(List<String>)request.getAttribute("avenants");

HashMap<Integer,String> moisannee=new HashMap <Integer,String>();
moisannee.put(1,"Janvier");
moisannee.put(2,"Février");
moisannee.put(3,"Mars");
moisannee.put(4,"Avril");
moisannee.put(5,"Mai");
moisannee.put(6,"Juin");
moisannee.put(7,"Juillet");
moisannee.put(8,"Août");
moisannee.put(9,"Septembre");
moisannee.put(10,"Octobre");
moisannee.put(11,"Novembre");
moisannee.put(12,"Décembre");
%>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Contrats par année</title>


<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>
			

<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier

		var datedebutrech = document.getElementById("datedebutrech").value;
		var datefinrech = document.getElementById("datefinrech").value;

		if (datedebutrech == "") {
			document.form1.datedebutrech.focus();
			// Afficher un message d'erreur
			attentionComplet(" La date de début doit être renseignée !!!!");
			return false;
		}

		if (datefinrech == "") {
			document.form1.datefinrech.focus();
			// Afficher un message d'erreur
			attentionComplet(" La date de fin doit être renseignée !!!!");
			return false;
		}

		formulaire.submit();
		return true;
	}
</script>

</head>
<body>

	<div id="body">
		<%@ include file='/menus/menugeneral/menu.html'%>
		<br>
		<div id="creation">CONTRATS PAR ANNEE</div>
		<br>

		<p class="centre3">Veuillez sélectionner les dates de début et de
			fin de recherche</p>
		<br>
		<form name="form1" method="post" action="/valence/controleur">
			<input type="hidden" name="action" value="aistatistiquescontratmois" />


			<table class="table5">

				<tr>
					<td>De (année) : <input type="text" name="datedebutrech"
						id="datedebutrech"  class="centre"/>
					</td>

					<td>a (année) : <input type="text" name="datefinrech"
						id="datefinrech"  class="centre"/>

					</td>
				</tr>

			</table>
			<br> <input type="button" value="Envoyer" id="droite" class="boutonvert"
				onclick="valider(form1);" />
		</form>
		<br>

		<%
			if (debut != null) {
				int ideb = Integer.parseInt(debut);
				int ifin = Integer.parseInt(fin);
					if (listecontrats.size() > 0) {
				
				%>
				
				<h3>NOMBRE MENSUEL DE CONTRATS PAR ANNEE</h3>
				<%
				
				
				
				int j = 0;
					for (int numannee = ideb; numannee <= ifin; numannee++) {
						int total = 0;
		%>

		<table class="table">

			<tr>
				<th><%=numannee%></th>
				<th>Quantité</th>
			</tr>

			<%
				for (int i = j; i < j + 12 && i < listecontrats.size(); i++) {
			%>

			<%
				int posmois = listecontrats.get(i).indexOf(",");
								String mm = listecontrats.get(i).substring(0,
										posmois);

								int tot = Integer.parseInt(mm);//numero du mois pour conversion en lettre

								int posnbre = listecontrats.get(i).lastIndexOf(",");
								String nb = listecontrats.get(i).substring(
										posnbre + 1);

								String annee = listecontrats.get(i).substring(
										posmois + 1, posnbre);
								//System.out.println(annee);
								total += Integer.parseInt(nb);
			%>
			<tr>
				<td><%=moisannee.get(tot)%></td>
				<td class="centre"><%=nb%></td>
			</tr>
			<%
				}
			%>
			<tr>
				<td>Total <%=numannee%></td>
				<td class="centre"><%=total%></td>
			</tr>
			<%
				j = j + 12;
						}
					
			%>
		</table>
		<br>
		<%
			}
					
					if (listeavenants.size() > 0) {
						
						%>
						
						<h3>NOMBRE MENSUEL D'AVENANTS PAR ANNEE</h3>
						<%
						
						
						
						int j = 0;
							for (int numannee = ideb; numannee <= ifin; numannee++) {
								int total = 0;
				%>

				<table class="table">

					<tr>
						<th><%=numannee%></th>
						<th>Quantité</th>
					</tr>

					<%
						for (int i = j; i < j + 12 && i < listeavenants.size(); i++) {
					%>

					<%
						int posmois = listeavenants.get(i).indexOf(",");
										String mm = listeavenants.get(i).substring(0,
												posmois);

										int tot = Integer.parseInt(mm);//numero du mois pour conversion en lettre

										int posnbre = listeavenants.get(i).lastIndexOf(",");
										String nb = listeavenants.get(i).substring(
												posnbre + 1);

										String annee = listeavenants.get(i).substring(
												posmois + 1, posnbre);
										//System.out.println(annee);
										total += Integer.parseInt(nb);
					%>
					<tr>
						<td><%=moisannee.get(tot)%></td>
						<td class="centre"><%=nb%></td>
					</tr>
					<%
						}
					%>
					<tr>
						<td>Total <%=numannee%></td>
						<td class="centre"><%=total%></td>
					</tr>
					<%
						j = j + 12;
								}
							
					%>
				</table>
				<%
					}
									
					
					
					
					
					
			}
		%>


		<script type="text/javascript"
			src="/valence/javascript/jquery.mask.min.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/scripts/formatageMask.js"></script>




	</div>

</body>
</html>