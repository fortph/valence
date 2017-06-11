<%@page
	import="dao.imp.identite.*, beans.identite.*,java.util.*,java.text.*,divers.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/valence/css/stat/listingAccueil.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<link rel="stylesheet" href="/valence/javascript/css/TableTools.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables_themeroller.css">
<%
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
	List<Identite> liste = (ArrayList<Identite>) request.getAttribute("listestat");
	//System.out.println("liste=" + liste);
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Recherche</title>
</head>
<body>
	<%
		if (!liste.equals(null)) {
		//System.out.println("liste=" + liste);

		int longue = liste.size();
		java.util.Date debut=(java.util.Date)request.getAttribute("debut");
		java.util.Date fin=(java.util.Date)request.getAttribute("fin");
		String sexe = request.getAttribute("sexe").toString();
		String age = request.getAttribute("age").toString();
		String niveau = request.getAttribute("niveauformation")
		.toString();
		String pole = request.getAttribute("pole").toString();
		String prio = request.getAttribute("listepriorites").toString();
		String origine = request.getAttribute("origine").toString();
	%>
	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
				<!-- si des resultats ont ete trouves-->
		<div id="creation">AFFICHAGE RESULTATS CORRESPONDANT AUX CRITERES CI-DESSOUS</div>
			<br>
		<label class="marge">Date accueil entre le <span
			class="affiche"><%=sdf.format(debut)%></span> et le <span
			class="affiche"><%=sdf.format(fin)%></span></label> <br>
		<%
			if (!age.equals("vide")) {
		%>
		<label class="marge">Age : <span class="affiche"><%=age%></span></label>
		<br>
		<%
			}
				if (!sexe.equals("vide")) {
		%>
		<label class="marge">Sexe : <span class="affiche"><%=sexe%></span></label>
		<br>
		<%
			}
				if (!niveau.equals("vide")) {
		%>
		<label class="marge">Niveau de formation : <span
			class="affiche"><%=niveau%></span></label> <br>
		<%
			}
				if (!pole.equals("vide")) {
		%>
		<label class="marge">Inscription à Pole Emploi : <span
			class="affiche"><%=pole%></span></label> <br>
		<%
			}
				if (!prio.equals("vide")) {
		%>
		<label class="marge">Priorité : <span class="affiche"><%=prio%></span></label>
		<br>
		<%
			}
				if (!origine.equals("vide")) {
		%>
		<label class="marge">Origine : <span class="affiche"><%=origine%></span></label>
		<br>
		<%
			}
		%>

		<br> <label><%=liste.size()%> réponses ont été trouvées</label>
		<hr>
		<table align="center" id="tablestataccueil" 
			class="display">
			<thead>
				<tr>
					<th align="center" width="300" class="etiquette">NOM-PRENOM</th>
					<th align="center" width="200" class="etiquette">VILLE</th>
					<th align="center" width="100" class="etiquette">TELEPHONE</th>
					<th align="center" width="100" class="etiquette">PORTABLE</th>

				</tr>
			</thead>
			<tbody>
				<%
					for (int i = 0; i < liste.size(); i++) {
											IdentiteDAO identiteDAO = new IdentiteDAO();
											int numero = new Integer(liste.get(i).getId_IDE());

											Identite un = identiteDAO.findByID(numero);
				%>

				<tr>
					<td><a href="/valence/jsp/accueil/afficheInscrit.jsp?numero=<%=un.getId_IDE()%>"><%=un.getNom_IDE()%> <%=un.getPrenom_IDE()%></a></td>
					<td><%=un.getVille_IDE()%></td>
					<td><%=un.getFixe_IDE()%></td>
					<td><%=un.getMobile_IDE()%></td>
				</tr>

				<%
					}
				%>
			</tbody>
		</table>
		<%
			}

				else {
		%>
		<p>Aucune réponse ne correspond à la recherche</p>
		<input type="button" value="Créer"
			onclick="self.location.href='/valence/jsp/inscription.jsp'" />

		<%
			}
		%>

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
		src="/valence/javascript/jquery.dataTables.min.js"></script>

	<script type="text/javascript"
		src="/valence/javascript/ZeroClipboard.js"></script>
	<script type="text/javascript"
		src="/valence/javascript/TableTools.min.js"></script>

	<script type="text/javascript"
		src="/valence/javascript/scripts/tableaux.js"></script>
	
</div>
</body>
</html>