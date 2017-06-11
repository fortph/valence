<%@ page
	import="dao.imp.identite.*, beans.identite.*,dao.imp.ai.*,beans.ai.*,java.util.*,
	java.text.*,dao.imp.rmi.*,beans.rmi.*,dao.imp.suivi.*,beans.suivi.*,beans.parametres.accueil.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/valence/css/rmi/fichecli.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<title>fiche liaison</title>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	String idpersonne = request.getParameter("personne");
	String contrat = request.getParameter("contrat");
	
	//ReferentPoleEmploiDAO ref=new ReferentPoleEmploiDAO();
	//ReferentPoleEmploi referent=ref.afficheReferent();
	
	//on recupere les coordonnees du conseil general 82
	OrganismeDAO orgdao=new OrganismeDAO();
	Organisme organisme=orgdao.findByID(3);

	IdentiteDAO idao = new IdentiteDAO();
	Identite identite = idao.findByID(Integer.parseInt(idpersonne));
	String mr = idao.afficheCivilite(identite);
	 String inscritPE="NON";
	 if(identite.getPoleEmploiInscripription_IDE()!=null)
		 inscritPE="OUI";

	FicheRMIDAO ficdao = new FicheRMIDAO();
	FicheRMI fiche = ficdao.recupereDerniereFicheRMI(identite);
	
	String pres=fiche.getPrescripteur();
	//System.out.println("pres ="+pres);
	int numpres=1;
	if(!pres.equals("Pole Emploi"))
		numpres=2;
	//System.out.println("numero="+numpres);
	
	ReferentPoleEmploiDAO pedao=new ReferentPoleEmploiDAO();
	ReferentPoleEmploi refepe=pedao.findByID(numpres);

	ContratRMIDAO contdao = new ContratRMIDAO();
	ContratRMI contratencours = contdao.findByID(Integer
	.parseInt(contrat));
	
	List<SuiviPersonne>  listesuivi=contdao.recupereDateSUiviDansFourchetteContrat(identite, contratencours);

	Date jour = new Date();
%>

<script type="text/javascript"
			src="/valence/javascript/scripts/attention.js"></script>

<script type="text/javascript">
	function valider(formulaire) {

		//liste des champs qui doivent etre renseignés ou dont le 
		//contenu est a verifier

		var datecreation = document.getElementById("datecreation").value;
	

		

		if (datecreation == "") {
			document.form1.datecreation.focus();
			attentionComplet(" La date de création de la fiche doit être remplie!!!!");
			return false;

		}
			
		
		formulaire.submit();
		return true;
	}
</script>




</head>
<body>

<div id="body">
	<%@ include file='/menus/menurmi/menusimple.jsp'%>


	
	
		<br>
		<div id="creation">CREATION DE LA FICHE LIAISON PDI/RSA</div>
		<br>
		<p><%=mr%>
			<%=identite.getPrenom_IDE()%>
			<%=identite.getNom_IDE()%></p>
		<br>


		<form method="post" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="creationfichecli" />
			<input type="hidden" name="contrat" value="<%=contrat%>" />
			<input type="hidden" name="prescripteur" value="<%=fiche.getPrescripteur() %>" />
			<table class="table1">
				<tr>
					<td >Date Bilan :</td>


					<td><input type="text" class="centre blanc" id="datecreation"
						name="datecreation" value="<%=sdf.format(jour)%>"></td>

				</tr>

			</table>
			<br>
			<hr>
			<br>

			<h3>INSERTION SOCIO-PROFESSIONNELLE BILAN INDIVIDUEL</h3>
			<br>

			
					
					<p class="grasgauche">PRESCRIPTEUR : <span id="idprescripteur"><%=fiche.getPrescripteur() %></span></p>
				
				<br>
				<p class="grasgauche">Coordonnées du Prescripteur</p>
			<table class="table2">
				
				<tr>

					<td class="un">Nom :</td>
					<td class="blanc"><%=fiche.getResponsable() %></td>
						<td class="colpetite"></td>
				</tr>
				<tr>
					<td>Fonction :</td>
					<td class="blanc"><%=fiche.getFonction() %></td>

				</tr>
				<tr>
					<td class="agencepe">Agence :</td>
					<td class="agencepe blanc"><%=fiche.getAgence() %> </td>
				</tr>
				<!--  <tr>
					<td class="agencepe">Nom du référent :</td>
					<td class="agencepe blanc"><%=refepe.getNom()%> <%=refepe.getPrenom()%></td>
					<td ><a
						href="/valence/jsp/rmi_rsa/fiches/modifierReferentPoleEmploi.jsp?contrat=<%=contrat%>&personne=<%=idpersonne%>"><img
							class="icone agencepe" src="/valence/images/bleu/mod.png" /></a></td>
				</tr>
				-->
				<tr>
					<td class="agencecg82">N° Tél :</td>
					<td class="agencecg82 blanc "><%=organisme.getTel()%></td>
				</tr>
				<tr>
					<td class="agencecg82">Adresse :</td>
					<td class="agencecg82 blanc"><%=organisme.getAdr1()%>, <%=organisme.getCp()%>
						<%=organisme.getVille()%></td>
				</tr>
				<tr>
					<td class="agencecg82">Mail :</td>
					<td class="agencecg82 blanc"> <%=fiche.getMail() %></td>
				</tr>

			</table>

			<br>
			<hr>
			<br>
			<h3>OBSERVATIONS DU PRESCRIPTEUR</h3>
			<br>
			<table class="table4">

				<tr>
					<td><textarea rows="5" name="obsprescripteur" id="obsprescripteur"></textarea></td>
				</tr>
			</table>

			<br>
			<hr>
			<br>
			<h3>PRESENCE ET OBJECTIFS FIXES AU PREMIER MOIS PRISE EN CHARGE</h3>
			<br>

			<table class="table4">

				<tr>
					<td><textarea rows="5" name="presence" id="presence"></textarea></td>
				</tr>
			</table>



			<br>
			<hr>
			<br>
			<h3>DATES DE RENCONTRES</h3>
			<br>
			<table class="table4">

				<tr>
					<td class="centre blanc">
						<%
							if(listesuivi!=null) for(int i=0;i<listesuivi.size();i++)
										out.println(sdf.format(listesuivi.get(i).getDatesuivi())+", ");
						%>
					</td>
				</tr>
			</table>
<br>
			<hr>
			<br>
			<h3>RESULTATS OBTENUS</h3>
			<br>
			<table class="table4">

				<tr>
					<th>Freins identifiés en début de parcours</th>
				<th>Freins levés au cours de l'acompagnement</th>
				<th>Freins restant à lever</th>
				</tr>
				<tr>
					<td><textarea rows="5" name="acquis" id="acquis"></textarea></td>
					<td><textarea rows="5" name="nonacquis" id="nonacquis"></textarea>
					</td>
					<td><textarea rows="5" name="encours" id="encours"></textarea>
					</td>
				</tr>
			</table>


			<br>
			<hr><br>
			<h3>CONCLUSION ET PERSPECTIVES</h3>
			<br>
			<table class="table4">

				<tr>
					<td><textarea rows="5" name="conclusion" id="conclusion"></textarea></td>
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
			src="/valence/javascript/scripts/dwrfonctionsemployeurs.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/scripts/journouveaux.js"></script>	


	<script type="text/javascript">
	$(document).ready(
			function() {
					var valeur=$("#idprescripteur").html();
					
							if( valeur=="Conseil Général 82"){
						$(".agencepe").hide();
						$(".agencecg82").show();

					} else {
						$(".agencecg82").hide();
						$(".agencepe").show();
						
					}

				

			});
	</script>
</div>

</body>
</html>