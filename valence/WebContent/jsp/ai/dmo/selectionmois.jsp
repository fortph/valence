<%@ page
	import="dao.imp.identite.*, beans.identite.*,dao.imp.ai.*,beans.ai.*,
	java.util.*,beans.smic.*,beans.employeurs.*,dao.imp.employeur.*,
	beans.parametres.accueil.*,java.text.*"%>

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
	
	List<Contrat> listecontrats=(List<Contrat>)request.getAttribute("listecontrats");
	List<Avenant> listeavenants=(List<Avenant>)request.getAttribute("listeavenants");
	IdentiteDAO idao=new IdentiteDAO();
	RomeDAO rodao=new RomeDAO();
	Identite identite=null;
	Rome rome=null;
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
<title>DMO</title>

</head>
<body>




	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div id="creation">Déclaration Mensuelle Obligatoire des
			Mouvements de Main d'Oeuvre AI</div>
		<br> <br>
		<p class="centre3">Veuillez sélectionner le mois et l'année
			désirée</p>
		<br>
		<form name="form1" method="post" action="/valence/controleur">
			<input type="hidden" name="action" value="dmo" />
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
					<td><input type="button" value="Envoyer" class="boutonvert"
						onclick="valider(form1);" /></td>
				</tr>
			</table>


		</form>
		<%
			if (listeavenants != null || listecontrats != null) {
		%>

		<br>
		<table id="dmolistecontratspardates" class="display">
		<thead>
			<tr>
				<th>Nom-Prénom</th>
				<th>C/A</th>
				<th>N</th>
				<th>Age</th>
				<th>S</th>
				<th>Niv</th>
				<th>Emploi</th>
				<th>Entrée</th>
				
				<th>Sortie</th>
				
				
				</tr>
				</thead>
				<tbody>
			



			<%
				if (listeavenants != null) {
						for (int i = 0; i < listeavenants.size(); i++) {
							identite = idao.findByID(listeavenants.get(i)
									.getContrat().getIdentite().getId_IDE());
							rome = rodao.findByID(listeavenants.get(i).getContrat()
									.getRome().getIdrome());
							String nationalite = "E";
							String sexe = "M";

							java.util.Date naiss = identite.getDatenais_IDE();
							//Calendar cal1 = Calendar.getInstance();
							int annee = 0;
							if (naiss != null) {
								annee =identite.calculerAgeReel();
							}

							if (identite.getNationalite_IDE().equals("Française"))
								nationalite = "F";
							else if (identite.getNationalite_IDE().equals(""))
								nationalite = "";
							if (identite.getSexe_IDE().equals(""))
								sexe = "";
							else if (identite.getSexe_IDE().equals("FEMININ"))
								sexe = "F";

							String affiche1 = "", affiche2 = "";
							String avene = "A";
							java.util.Date deb = listeavenants.get(i).getDatedeb();
							Calendar cal2 = Calendar.getInstance();

							cal2.setTime(deb);
							int moisdebutavenant = cal2.get(Calendar.MONTH);
							java.util.Date fin = listeavenants.get(i).getDatefin();
							Calendar cal3 = Calendar.getInstance();

							cal3.setTime(fin);
							int moisfinavenant = cal3.get(Calendar.MONTH);

							int moischoisi = Integer.parseInt(selectionmois);
							moischoisi -= 1;//les mois commencent a 0 sur Calendar -> on compense;
							int anchoisi = Integer.parseInt(selectionan);

							if (moisdebutavenant == moischoisi) {
								affiche1 = sdf.format(deb);
								
							}

							if (moisfinavenant == moischoisi) {
								affiche2 = sdf.format(fin);
								
							}
							
							String niveau=identite.getNiveauFormation_IDE();
			%>
			<tr>
				<td><a
					href="/valence/jsp/accueil/afficheInscrit.jsp?numero=<%=listeavenants.get(i).getContrat()
								.getIdentite().getId_IDE()%>"><%=listeavenants.get(i).getContrat()
								.getIdentite().getNom_IDE()%> <%=listeavenants.get(i).getContrat()
								.getIdentite().getPrenom_IDE()%></a></td>
				<td class="petit"><%=avene%></td>
				<td class="petit"><%=nationalite%></td>
				<td class="petit"><%=annee%></td>
				<td class="petit"><%=sexe%></td>
				<td><%=niveau %></td>
				<td><%=rome.getIntitule()%></td>
				<td><%=affiche1%></td>
				
				<td><%=affiche2%></td>
				
				<%
					}
						}
						if (listecontrats != null) {
							for (int i = 0; i < listecontrats.size(); i++) {
								identite = idao.findByID(listecontrats.get(i)
										.getIdentite().getId_IDE());
								rome = rodao.findByID(listecontrats.get(i).getRome()
										.getIdrome());
								String nationalite = "E";
								String sexe = "M";
								String cont = "C";

								java.util.Date naiss = identite.getDatenais_IDE();
								//Calendar cal1 = Calendar.getInstance();
								int annee = 0;
								if (naiss != null) {
									//cal1.setTime(naiss);
									annee = identite.calculerAgeReel();
								}
								
								if(identite.getNationalite_IDE()!=null){
								if (identite.getNationalite_IDE().equals("Française"))
									nationalite = "F";
								else if (identite.getNationalite_IDE().equals(""))
									nationalite = "";
								}
								
								if(identite.getSexe_IDE()!=null){
								if (identite.getSexe_IDE().equals(""))
									sexe = "";
								else if (identite.getSexe_IDE().equals("FEMININ"))
									sexe = "F";
								}
								String affiche1 = "", affiche2 = "";
								//String affiche1bis = "", affiche2bis = "";

								java.util.Date deb = listecontrats.get(i)
										.getDebutcontrat();
								Calendar cal2 = Calendar.getInstance();
								cal2.setTime(deb);
								int moisdebutcontrat = cal2.get(Calendar.MONTH);
								java.util.Date fin = listecontrats.get(i)
										.getFincontrat();
								Calendar cal3 = Calendar.getInstance();
								cal3.setTime(fin);
								int moisfincontrat = cal3.get(Calendar.MONTH);

								int moischoisi = Integer.parseInt(selectionmois);
								moischoisi -= 1;//les mois commencent a 0 sur Calendar -> on compense;
								int anchoisi = Integer.parseInt(selectionan);

								if (moisdebutcontrat == moischoisi) {
									affiche1 = sdf.format(deb);
									//affiche1bis = "RD";
								}

								if (moisfincontrat == moischoisi) {
									affiche2 = sdf.format(fin);
									//affiche2bis = "FD";
								}
								String niveau=identite.getNiveauFormation_IDE();
				%>
			
			<tr>
				<td><a
					href="/valence/jsp/accueil/afficheInscrit.jsp?numero=<%=listecontrats.get(i).getIdentite()
								.getId_IDE()%>"><%=listecontrats.get(i).getIdentite()
								.getNom_IDE()%> <%=listecontrats.get(i).getIdentite()
								.getPrenom_IDE()%></a></td>
				<td class="petit"><%=cont%></td>
				<td class="petit"><%=nationalite%></td>
				<td class="petit"><%=annee%></td>
				<td class="petit"><%=sexe%></td>
				<td><%=niveau %></td>
				<td><%=rome.getIntitule()%></td>
				<td><%=affiche1%></td>
				
				<td><%=affiche2%></td>
				
				<%
					}
				%>
		</tr>
		<%
			}
			}
		%>
		</tbody>	
		</table>
		
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
		<script type="text/javascript"
			src="/valence/javascript/jquery.dataTables.min.js"></script>

		<script type="text/javascript"
			src="/valence/javascript/ZeroClipboard.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/TableTools.min.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/scripts/datatabletripardates.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/scripts/tableaux.js"></script>
</body>
</html>