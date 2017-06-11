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


<title>modifier fiche liaison</title>
<%
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	String idpersonne = request.getParameter("personne");
	String contrat = request.getParameter("contrat");
		
	//on recupere les coordonnees du conseil general 82
	OrganismeDAO orgdao=new OrganismeDAO();
	Organisme organisme=orgdao.findByID(3);

	IdentiteDAO idao = new IdentiteDAO();
	Identite identite = idao.findByID(Integer.parseInt(idpersonne));
	String mr = idao.afficheCivilite(identite);
	
	FicheRMIDAO ficdao = new FicheRMIDAO();
	FicheRMI fiche = ficdao.recupereDerniereFicheRMI(identite);

	ContratRMIDAO contdao = new ContratRMIDAO();
	ContratRMI contratencours = contdao.findByID(Integer
	.parseInt(contrat));
	
	FicheLiaisonRMIDAO fldao=new FicheLiaisonRMIDAO();
	FicheLiaisonRMI ficheliaison=fldao.recupereFicheCorrespondContrat(contratencours);
	
	String prescripteur=fiche.getPrescripteur();
	
	List<SuiviPersonne>  listesuivi=contdao.recupereDateSUiviDansFourchetteContrat(identite, contratencours);
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


	<%@ include file='/menus/menurmi/menufichermi.jsp'%>


	
	<div id="body">
		<br>
		<div id="creation">MISE A JOUR DE LA FICHE LIAISON PDI/RSA</div>
		<br>
		<p><%=mr%>
			<%=identite.getPrenom_IDE()%>
			<%=identite.getNom_IDE()%></p>
		<br>


		<form method="post" name="form1" action="/valence/controleur">
			<input type="hidden" name="action" value="modificationfichecli" /> <input
				type="hidden" name="contrat" value="<%=contrat%>" />
				 <input	type="hidden" name="prescripteur" value="<%=fiche.getPrescripteur() %>" />


			<table class="table2">
				<tr>
					<th>Date Bilan</th>
					<th>Nom du référent</th>
					<th>N° contrat</th>
					<th>Début de contrat</th>
					<th>Fin de contrat</th>
				</tr>
				<tr>

					<td class="blanccentre"><input type="text" name="datecreation" id="datecreation" 
						value="<%
							if(ficheliaison.getDatecreation()!=null) out.println(sdf.format(ficheliaison.getDatecreation()));
						%>" />
					</td>

					<td class="blanccentre"><%=fiche.getReferent().getPrenom()%> <%=fiche.getReferent().getNom()%></td>
					<td class="blanccentre"><%=contrat%></td>
					<td class="blanccentre">
						<%
							if(contratencours.getDatedeb_rmicontrat()!=null) out.println(sdf.format(contratencours.getDatedeb_rmicontrat()));
						%>
					</td>
					<td class="blanccentre">
						<%
							if(contratencours.getDatefin_rmicontrat()!=null) out.println(sdf.format(contratencours.getDatefin_rmicontrat()));
						%>
					
				</tr>

			</table>
			<br>
			<hr>
			<br>

			<h3>INSERTION SOCIO-PROFESSIONNELLE BILAN INDIVIDUEL</h3>
			<br> <span class="gauchenormal">Prescripteur : <label
				class="grasgauche"><%=prescripteur%></label></span> <br> <br>
			<p class="grasgauche">Coordonnées du Prescripteur</p>
			<table class="table2">

				<tr>

					<td class="un">Nom :</td>
					<td class="grasgauche blanc"><%=fiche.getResponsable()%></td>

				</tr>
				<tr>
					<td>Fonction :</td>
					<td class="grasgauche blanc"><%=fiche.getFonction()%></td>

				</tr>
				<%
					if(prescripteur!=null){
											if(prescripteur.equals("Pole Emploi")){
				%>
				<tr>
					<td>Agence :</td>
					<td class="grasgauche blanc"><%=fiche.getAgence()%></td>
				</tr>
				<tr>
					<td>Nom du référent :</td>
					<td class="grasgauche blanc"><%=ficheliaison.getNomreferentpe()%>
						<%=ficheliaison.getPrenomreferentpe()%></td>
					
					<td><a
						href="/valence/jsp/rmi_rsa/fiches/modifierReferentPoleEmploi.jsp?contrat=<%=contrat%>&personne=<%=idpersonne%>&modif=oui"><img
							class="icone" src="/valence/images/bleu/mod.png" /></a></td>
				<tr>
					<%
						}
												else {
					%>
					<td>N° Tél :</td>
					<td class="grasgauche blanc"><%=organisme.getTel()%></td>
				</tr>
				<tr>
					<td>Adresse :</td>
					<td class="grasgauche blanc"><%=organisme.getAdr1()%>, <%=organisme.getCp()%>
						<%=organisme.getVille()%></td>
				</tr>
				<tr>
					<td>Mail :</td>
					<td class="grasgauche blanc"><%=fiche.getMail()%></td>
				</tr>
				<%
					}
				%>

			</table>
			<%
				}
			%>
			<br>
			<hr>
			<br>
			<h3>OBSERVATIONS DU PRESCRIPTEUR</h3>
			<br>
			<table class="table4">

				<tr>
					<td><textarea rows="5" name="obsprescripteur" id="obsprescripteur"><%=ficheliaison.getObsprescripteur() %></textarea></td>
				</tr>
			</table>


			<br>
			<hr>
			<br>
			<h3>PRESENCE ET OBJECTIFS FIXES AU PREMIER MOIS PRISE EN CHARGE</h3>
			<br>

			<table class="table4">

				<tr>
					<td><textarea rows="5" name="presence" id="presence"><%=ficheliaison.getPresence()%></textarea></td>
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
					<td><textarea rows="5" name="acquis" id="acquis"><%=ficheliaison.getAcquis()%></textarea></td>
					<td><textarea rows="5" name="nonacquis" id="nonacquis"><%=ficheliaison.getNomacquis()%></textarea>
					</td>
					<td><textarea rows="5" name="encours" id="encours"><%=ficheliaison.getEncours()%></textarea>
					</td>
				</tr>
			</table>


			<br>
			<hr>
			<br>
			<h3>CONCLUSION ET PERSPECTIVES</h3>
			<br>
			<table class="table4">

				<tr>
					<td><textarea rows="5" name="conclusion" id="conclusion"><%=ficheliaison.getConclusion()%></textarea></td>
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
		<script type="text/javascript"
			src="/valence/javascript/jquery.mask.min.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/scripts/formatageMask.js"></script>




	<script type="text/javascript">
		$(document).ready(
				function() {
					$("input:radio[name='prescripteur']").on('load change',
							myfunction);
					function myfunction() {
						if ($(this).is(":checked")
								&& $(this).val() == "Conseil Général") {

							$(".agencepe").hide();
							$(".agencecg82").show();

						} else {
							$(".agencepe").show();
							$(".agencecg82").hide();
						}

					}
					;

					myfunction();

				});
		
	</script>
	
		</div>
	</body>
</html>