<%@ page
	import="dao.imp.identite.*, beans.identite.*,dao.imp.ai.*,beans.ai.*,
	java.util.*,beans.smic.*,beans.employeurs.*,dao.imp.employeur.*,
	beans.parametres.accueil.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%
	String contrat=request.getParameter("contrat");
	Calendar cal = Calendar.getInstance();
	cal.setTime(new Date());
	
	int mois = cal.get(Calendar.MONTH);
	mois=mois+1;
	
	int an = cal.get(Calendar.YEAR);
	int anplus=an+1;
	int anmoins=an-1;
	
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
	
	String moisencours=moisannee.get(mois);
	
%>
<link rel="stylesheet" href="/valence/css/ai/contrat/affichage.css">



<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Contrat: mois et annee</title>

</head>
<body>




	<div id="body">
		<p><%@ include file='/menus/menusai/menuaffichecontratai.jsp'%></p>
		<br>
		<div id="creation"> CHOIX DU MOIS ET DE L'ANNEE</div>
		<br>
		<br>
		<p class="centre3">Veuillez sélectionner le mois et l'année de la fiche horaire.</p>
		<br>
		<form method="post" action="/valence/jsp/pdf/ai/contrat/heures.jsp">
		<input type="hidden" name="contrat" value="<%=contrat %>" />
			<table class="table4">
				<tr>
					<th>MOIS</th>
					
					<th>ANNEE</th>
				</tr>
				<tr>
					<td><select name="mois" id="mois">
					<% for (int i=1;i<13;i++){
						%>
					
					
					<option <%   if(moisannee.get(i)==moisencours) out.println("selected='selected'");
					%> >
					
				
					<%
					out.println(moisannee.get(i));
					}
					%>
					</select></td>
					
					<td><select name="an" id="an" >
						<option ><%=anmoins%></option>
						<option selected="selected"><%=an %></option>
					<option><%=anplus%></option>
					</select></td></tr>
					
			</table>
			<br>
			<input type="submit" value="Valider"  id="droite"/>
		</form>


	</div>

</body>
</html>