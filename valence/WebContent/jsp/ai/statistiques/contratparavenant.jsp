<%@ page
	import="dao.imp.identite.*, beans.identite.*,dao.imp.ai.*,beans.ai.*,
	java.util.*,beans.smic.*,beans.employeurs.*,dao.imp.employeur.*,
	beans.parametres.accueil.*,divers.*,dao.exception.*,java.text.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<link rel="stylesheet" href="/valence/css/ai/stat/contratseul.css">
<link rel="stylesheet" type="text/css"
	href="/valence/javascript/css/ui-lightness/minified/jquery-ui.min.css" />
<link rel="stylesheet" href="/valence/javascript/css/TableTools.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables.css">
<link rel="stylesheet"
	href="/valence/javascript/css/jquery.dataTables_themeroller.css">
<%
	SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
	List<Identite> liste = (List<Identite>)request.getAttribute("listestat");
	
	String ddeb="",dfin="";
	java.util.Date debut=null,fin=null;
	
	if(request.getParameter("debut")!=null){
	 ddeb=request.getParameter("debut");
	 debut=new FormaterDate().changeFormatChaineDate(ddeb);
	}
	if(request.getParameter("fin")!=null){
	 dfin=request.getParameter("fin");
	 fin=new FormaterDate().changeFormatChaineDate(dfin);	
	}
	
	
	int total=0;	
	int h=0,f=0;
	int anpe=0;
	int totalfr=0,totalfrhomme=0,totalfrfemme=0,totalnonrenseigne=0;
	int totalet=0, totalethomme=0,totaletfemme=0;
	int totmoins25=0,totmoins50=0,totplus50=0,totindefini=0;
	int hommemoins25=0,hommemoins50=0,hommeplus50=0,hommeindefini=0;	
	int femmemoins25=0,femmemoins50=0,femmeplus50=0,femmeindefini=0;
	int petotalinscrit=0,petotalnoninscrit=0,petotalmoins1=0,petotalmoins2=0,petotalmoins3=0,petotalplus3=0;
	int petotalhommeinscrit=0,petotalhommenoninscrit=0,pehommemoins1=0,pehommemoins2=0,pehommemoins3=0,pehommeplus3=0;
	int petotalfemmeinscrit=0,petotalfemmenoninscrit=0,pefemmemoins1=0,pefemmemoins2=0,pefemmemoins3=0,pefemmeplus3=0;
	int totalniv0=0,totalniv1=0,totalniv2=0,totalniv3=0,totalniv4=0,totalniv5=0,
			totalniv5b=0,totalniv6=0,totalnivill=0;
	int hniveau0=0,hniveau1=0,hniveau2=0,hniveau3=0,hniveau4=0,hniveau5=0,hniveau5b=0,hniveau6=0,hniveauill=0;
	int fniveau0=0,fniveau1=0,fniveau2=0,fniveau3=0,fniveau4=0,fniveau5=0,fniveau5b=0,fniveau6=0,fniveauill=0;
	int totalvalence=0,totalcc2r=0,totalcanton=0,total82=0,totalregion=0, totalvillenonrenseigne=0,totalcastel=0;
	int hvalence=0, hcc2r=0,hcanton=0,hdepartement=0, hregion=0,hcastel=0;
	int fvalence=0, fcc2r=0,fcanton=0,fdepartement=0, fregion=0,fcastel=0;
	int totalaide=0,totalrmi=0,totalass=0,totalrth=0,totalprot=0,totaldiff=0;
	int haide=0,hrmi=0,hass=0,hrth=0,hprot=0,hdiff=0;
	int faide=0,frmi=0,fass=0,frth=0,fprot=0,fdiff=0;
	
	//villes de la communaute
	List<String> villescc2r=new Cc2rDAO().afficherVilles();
	
	//villes du canton
	String []canton={"VALENCE D'AGEN","POMMEVIC","GOUDOURVILLE","ESPALAIS","PERVILLE","GASQUES",
			"GOLFECH","MONTJOI","CASTELSAGRAT","ST VINCENT LESPINASSE","SAINT CLAIR","ST CLAIR"};
	
	List<String> villescanton=new ArrayList<String>(Arrays.asList(canton));
	
	ProfilPriorite profil=new ProfilPriorite();
	ProfilPrioriteDAO profildao=new ProfilPrioriteDAO();
	if(liste!=null) {
		//on recupere le nombre de resultats
		total=liste.size();
	
	for(int i=0;i<liste.size();i++)
		{int age=liste.get(i).calculerAge(fin);
		 //test de l'age
		 if( age>=0 && age <26 )	totmoins25++;
				else if(age >=26 && age <50) totmoins50++;
				else if(age >=50) totplus50++;
				else totindefini++;
		
		
		//on recupere la date inscription pole emploi
		boolean inscrit=false;
		java.util.Date datpe=liste.get(i).getPoleEmploiInscripription_IDE();
		java.util.Date datacc=liste.get(i).getDateAccueil_IDE();
		if(datpe!=null &&
				liste.get(i).getPoleEmploi_ID_IDE()!=null){
					inscrit=true;
					petotalinscrit++;
				 }
			
			else petotalnoninscrit++;
			
			
			if(inscrit){
				anpe=liste.get(i).calculerTempsPE();
				if(anpe<1) petotalmoins1++;
				else if(anpe>=1 && anpe<2) petotalmoins2++;
				else if(anpe>=2 && anpe <3) petotalmoins3++;
				else petotalplus3++;
				
			}
		
		//on recupere la liste des profilpriorites attaches a chaque personne
		profil.setId_identite(liste.get(i).getId_IDE());
		List<ProfilPriorite> listeprofil=null;
		
		try{
			listeprofil=profildao.findByCriteria(profil);
		}catch(DAOException e){
			e.printStackTrace();
		}
		
		
		//test priorites
		for(int jj=0;jj<listeprofil.size();jj++){
		if(listeprofil.get(jj).getLibelle().equals("AIDE-SOCIALE"))
			totalaide++;
		else if(listeprofil.get(jj).getLibelle().contains("RSA") ||listeprofil.get(jj).getLibelle().equals("RMI"))
			totalrmi++;
		else if(listeprofil.get(jj).getLibelle().equals("ASS"))
			totalass++;
		else if(listeprofil.get(jj).getLibelle().equals("RTH"))
			totalrth++;
		else if(listeprofil.get(jj).getLibelle().equals("EXDET"))
			totalprot++;
		else if(listeprofil.get(jj).getLibelle().equals("PGD") ||listeprofil.get(jj).getLibelle().contains("CIVIS"))
			totaldiff++;
		}
		
		//test nationalites
		if(liste.get(i).getNationalite_IDE().startsWith("Fran"))
			totalfr++;
		else if(liste.get(i).getNationalite_IDE().equals(""))
			totalnonrenseigne++;
		else totalet++;
		
		
		//test des niveaux de formation
		if(liste.get(i).getNiveauFormation_IDE()!=null){
		if(liste.get(i).getNiveauFormation_IDE().equals("ILLETRISME"))
			totalnivill++;
		else if(liste.get(i).getNiveauFormation_IDE().startsWith("N0"))
			totalniv0++;
		else if(liste.get(i).getNiveauFormation_IDE().startsWith("N1"))
			totalniv1++;
		else if(liste.get(i).getNiveauFormation_IDE().startsWith("N2"))
			totalniv2++;
		else if(liste.get(i).getNiveauFormation_IDE().startsWith("N3"))
			totalniv3++;
		else if(liste.get(i).getNiveauFormation_IDE().startsWith("N4"))
			totalniv4++;
		else if(liste.get(i).getNiveauFormation_IDE().startsWith("N5 -"))
			totalniv5++;
		else if(liste.get(i).getNiveauFormation_IDE().startsWith("N5b"))
			totalniv5b++;
		else if(liste.get(i).getNiveauFormation_IDE().startsWith("N6"))
			totalniv6++;
		}
		
		//test de l'origine geographique
		if(liste.get(i).getVille_IDE().trim().equalsIgnoreCase("VALENCE D'AGEN"))
			totalvalence++;
		
		if(liste.get(i).getVille_IDE().trim().equalsIgnoreCase("CASTELSARRASIN"))
			totalcastel++;
		
		 if(liste.get(i).getCp_IDE().startsWith("82"))
			total82++;
		
		if(liste.get(i).getCp_IDE().startsWith("82") ||
				liste.get(i).getCp_IDE().startsWith("09") ||
				liste.get(i).getCp_IDE().startsWith("46") ||
				liste.get(i).getCp_IDE().startsWith("12") ||
				liste.get(i).getCp_IDE().startsWith("81") ||
				liste.get(i).getCp_IDE().startsWith("32") ||
				liste.get(i).getCp_IDE().startsWith("65") ||
				liste.get(i).getCp_IDE().startsWith("31") )
			totalregion++;
		
		 if(liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(0)) ||
				liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(1)) ||
				liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(2)) ||
				liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(3)) ||
				liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(4)) ||
				liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(5)) ||
				liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(6)) ||
				liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(7)) ||
				liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(8)) ||
				liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(9)) ||
				liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(10)) ||
				liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(11)) )
			totalcanton++;
		 
		 for(int x=0;x<villescc2r.size();x++){		
			 if(liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescc2r.get(x)) )
				totalcc2r++;
				
			
			}
			
		
		/*****************statistiques hommes***********************************/
		if(liste.get(i).getSexe_IDE().equals("MASCULIN")){
			h++;
				//test de l'age
				if(age >0 && age <26 )	hommemoins25++;
				else if(age >=26 && age <50) hommemoins50++;
				else if(age >=50) hommeplus50++;
				else hommeindefini++;
				//test pole emploi
							
				if(inscrit){
					petotalhommeinscrit++;
					anpe=liste.get(i).calculerTempsPE();
					if(anpe<1) pehommemoins1++;
					else if(anpe>=1 && anpe<2) pehommemoins2++;
					else if(anpe>=2 && anpe <3) pehommemoins3++;
					else pehommeplus3++;
					
				}
				else petotalhommenoninscrit++;
				
				
				//test priorites
				for(int jj=0;jj<listeprofil.size();jj++){
				if(listeprofil.get(jj).getLibelle().equals("AIDE-SOCIALE"))
					haide++;
				else if(listeprofil.get(jj).getLibelle().contains("RSA") ||listeprofil.get(jj).getLibelle().equals("RMI"))
					hrmi++;
				else if(listeprofil.get(jj).getLibelle().equals("ASS"))
					hass++;
				else if(listeprofil.get(jj).getLibelle().equals("RTH"))
					hrth++;
				else if(listeprofil.get(jj).getLibelle().equals("EXDET"))
					hprot++;
				else if(listeprofil.get(jj).getLibelle().equals("PGD") ||listeprofil.get(jj).getLibelle().contains("CIVIS"))
					hdiff++;
				}
				
				
				//test nationalites
				if(liste.get(i).getNationalite_IDE().startsWith("Fran"))
					totalfrhomme++;
				else if(!liste.get(i).getNationalite_IDE().equals(""))
					totalethomme++;
				
				
				//test des niveaux de formation
				if(liste.get(i).getNiveauFormation_IDE()!=null){
				if(liste.get(i).getNiveauFormation_IDE().equals("ILLETRISME"))
					hniveauill++;
				else if(liste.get(i).getNiveauFormation_IDE().startsWith("N0"))
					hniveau0++;
				else if(liste.get(i).getNiveauFormation_IDE().startsWith("N1"))
					hniveau1++;
				else if(liste.get(i).getNiveauFormation_IDE().startsWith("N2"))
					hniveau2++;
				else if(liste.get(i).getNiveauFormation_IDE().startsWith("N3"))
					hniveau3++;
				else if(liste.get(i).getNiveauFormation_IDE().startsWith("N4"))
					hniveau4++;
				else if(liste.get(i).getNiveauFormation_IDE().startsWith("N5 -"))
					hniveau5++;
				else if(liste.get(i).getNiveauFormation_IDE().startsWith("N5b"))
					hniveau5b++;
				else if(liste.get(i).getNiveauFormation_IDE().startsWith("N6"))
					hniveau6++;
				}
				
				//test de l'origine geographique
				if(liste.get(i).getVille_IDE().equalsIgnoreCase("VALENCE D'AGEN"))
					hvalence++;
				
				if(liste.get(i).getVille_IDE().equalsIgnoreCase("CASTELSARRASIN"))
					hcastel++;
				
			 if(liste.get(i).getCp_IDE().startsWith("82"))
					hdepartement++;
					
				 if(liste.get(i).getCp_IDE().startsWith("82") ||
						liste.get(i).getCp_IDE().startsWith("09") ||
						liste.get(i).getCp_IDE().startsWith("46") ||
						liste.get(i).getCp_IDE().startsWith("12") ||
						liste.get(i).getCp_IDE().startsWith("81") ||
						liste.get(i).getCp_IDE().startsWith("32") ||
						liste.get(i).getCp_IDE().startsWith("65") ||
						liste.get(i).getCp_IDE().startsWith("31") )
					hregion++;
				
				if		(liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(0)) ||
						liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(1)) ||
						liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(2)) ||
						liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(3)) ||
						liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(4)) ||
						liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(5)) ||
						liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(6)) ||
						liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(7)) ||
						liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(8)) ||
						liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(9)) ||
						liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(10)) ||
						liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(11)) )
					hcanton++;
				
				 for(int x=0;x<villescc2r.size();x++){		
					 if(liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescc2r.get(x)))
						hcc2r++;
						
					
					}
				
					}		
				
	/***************************statistiques femmes	***********************************/
	
	if(liste.get(i).getSexe_IDE().equals("FEMININ")){
		f++;
		//test de l'age
		if(age >0 && age <26 )	femmemoins25++;
		else if(age >=26 && age <50) femmemoins50++;
		else if(age >=50) femmeplus50++;
		else femmeindefini++;
		//test pole emploi
		
		
		
		if(inscrit){
			petotalfemmeinscrit++;
			anpe=liste.get(i).calculerTempsPE();
			if(anpe<1) pefemmemoins1++;
			else if(anpe>=1 && anpe<2) pefemmemoins2++;
			else if(anpe>=2 && anpe <3) pefemmemoins3++;
			else pefemmeplus3++;
			
		}
		else petotalfemmenoninscrit++;
		
		
		
		//test priorites
		for(int jj=0;jj<listeprofil.size();jj++){
		if(listeprofil.get(jj).getLibelle().equals("AIDE-SOCIALE"))
			faide++;
		else if(listeprofil.get(jj).getLibelle().contains("RSA") ||listeprofil.get(jj).getLibelle().equals("RMI"))
			frmi++;
		else if(listeprofil.get(jj).getLibelle().equals("ASS"))
			fass++;
		else if(listeprofil.get(jj).getLibelle().equals("RTH"))
			frth++;
		else if(listeprofil.get(jj).getLibelle().equals("EXDET"))
			fprot++;
		else if(listeprofil.get(jj).getLibelle().equals("PGD") ||listeprofil.get(jj).getLibelle().contains("CIVIS"))
			fdiff++;
		}
		
		//test nationalites
		if(liste.get(i).getNationalite_IDE().startsWith("Fran"))
			totalfrfemme++;
		else if(!liste.get(i).getNationalite_IDE().equals(""))
			totaletfemme++;
		
		
		
		//test des niveaux de formation
		if(liste.get(i).getNiveauFormation_IDE()!=null){
		if(liste.get(i).getNiveauFormation_IDE().equals("ILLETRISME"))
			fniveauill++;
		else if(liste.get(i).getNiveauFormation_IDE().startsWith("N0"))
			fniveau0++;
		else if(liste.get(i).getNiveauFormation_IDE().startsWith("N1"))
			fniveau1++;
		else if(liste.get(i).getNiveauFormation_IDE().startsWith("N2"))
			fniveau2++;
		else if(liste.get(i).getNiveauFormation_IDE().startsWith("N3"))
			fniveau3++;
		else if(liste.get(i).getNiveauFormation_IDE().startsWith("N4"))
			fniveau4++;
		else if(liste.get(i).getNiveauFormation_IDE().startsWith("N5 -"))
			fniveau5++;
		else if(liste.get(i).getNiveauFormation_IDE().startsWith("N5b"))
			fniveau5b++;
		else if(liste.get(i).getNiveauFormation_IDE().startsWith("N6"))
			fniveau6++;
		}
		
		//test de l'origine geographique
				if(liste.get(i).getVille_IDE().equalsIgnoreCase("VALENCE D'AGEN"))
					fvalence++;
		
		
				if(liste.get(i).getVille_IDE().equalsIgnoreCase("CASTELSARRASIN"))
					fcastel++;
				 if(liste.get(i).getCp_IDE().startsWith("82"))
					fdepartement++;
				 if(liste.get(i).getCp_IDE().startsWith("82") ||
						liste.get(i).getCp_IDE().startsWith("09") ||
						liste.get(i).getCp_IDE().startsWith("46") ||
						liste.get(i).getCp_IDE().startsWith("12") ||
						liste.get(i).getCp_IDE().startsWith("81") ||
						liste.get(i).getCp_IDE().startsWith("32") ||
						liste.get(i).getCp_IDE().startsWith("65") ||
						liste.get(i).getCp_IDE().startsWith("31") )
					fregion++;
				
				 if(liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(0)) ||
							liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(1)) ||
							liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(2)) ||
							liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(3)) ||
							liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(4)) ||
							liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(5)) ||
							liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(6)) ||
							liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(7)) ||
							liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(8)) ||
							liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(9)) ||
							liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(10)) ||
							liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescanton.get(11)) )
					fcanton++;
				
				 for(int x=0;x<villescc2r.size();x++){		
					 if(liste.get(i).getVille_IDE().trim().equalsIgnoreCase(villescc2r.get(x)))
							 
						fcc2r++;
						
					
					}
					
		
	
	
		}
					}
	}
	
	
	
%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Statistiques contrat avenant</title>

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
			attentionComplet("La date de début doit être renseignée !!!!");
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
		<div id="creation">STATISTIQUES GENERALES AI : contrat et avenant en cours</div>
		<br>

		<p class="centre3">Veuillez sélectionner les dates de début et de fin de recherche </p>
		<br>
		<form name="form1" method="post" action="/valence/controleur" >
		<input type="hidden" name="action" value="aistatistiquescontratavenant" />
		
		
			<table class="table5">
				
				<tr>
					<td> Du : <input type="text" name="datedebutrech" id="datedebutrech"  class="centre"/>
					</td>
					
					<td> au : <input type="text" name="datefinrech" id="datefinrech"  class="centre"/>
					
					</td>
					 </tr>
					
			</table>
			<br>
			 <input type="button" value="Envoyer"  class="boutonvert" id="droite" onclick="valider(form1);" />
		</form>
		<br>
		
		
		<% if(liste!=null){
			
			%>
		
		<h3>
	Personnes avec contrat  ou Avenant dont le début de contrat se situe dans la période du 
	<%=sdf.format(debut)%>
	au 
	<%=sdf.format(fin)%></h3>
	<br>
		
		<table id="accueilpourcentbis" class="display">
	<thead>
		<tr>
			<th>Critères</th>
			<th>Total</th>
			<th>%</th>
			<th>Homme</th>
			<th>%</th>
			<th>Femme</th>
			<th>%</th>
			
		</tr>
	</thead>
	<tbody>
	
		<tr id="colon1">
			<td >Total AI</td>
			<td><%=total%> </td>
			<td>100</td>
			<td><%=h%></td>
			<td> <%=FormaterDouble.formateDouble(h,total) %></td>
			<td><%=f%></td>
			<td> <%=FormaterDouble.formateDouble(f,total) %></td>
		</tr>
		<tr>
			<td>moins de 25 ans</td>
			<td><%=totmoins25 %> </td>
			<td> <%=FormaterDouble.formateDouble(totmoins25,total) %></td>
			<td><%=hommemoins25 %> </td>
			<td> <%=FormaterDouble.formateDouble(hommemoins25,totmoins25) %></td>
			<td><%=femmemoins25 %> </td>
			<td> <%=FormaterDouble.formateDouble(femmemoins25,totmoins25) %></td>
		</tr>
		<tr>
			<td>de 26 à 49 ans</td>
			<td><%=totmoins50 %></td>
			<td> <%=FormaterDouble.formateDouble(totmoins50,total) %></td>
			<td><%=hommemoins50 %> </td>
			<td> <%=FormaterDouble.formateDouble(hommemoins50,totmoins50) %></td>
			<td><%=femmemoins50 %></td>
			<td> <%=FormaterDouble.formateDouble(femmemoins50,totmoins50) %></td>
		</tr>
		<tr>
			<td>50 ans et plus</td>
			<td><%=totplus50 %></td>
			<td> <%=FormaterDouble.formateDouble(totplus50,total) %></td>
			<td><%=hommeplus50 %> </td>
			<td><%=FormaterDouble.formateDouble(hommeplus50,totplus50) %></td>
			<td><%=femmeplus50 %></td>
			<td> <%=FormaterDouble.formateDouble(femmeplus50,totplus50) %></td>
		</tr>
		<tr>
			<td>Date de naissance non enregistrée</td>
			<td><%=totindefini %> </td>
			<td> <%=FormaterDouble.formateDouble(totindefini,total) %></td>
			<td><%=hommeindefini %> </td>
			<td><%=FormaterDouble.formateDouble(hommeindefini,totindefini) %></td>
			<td><%=femmeindefini %> </td>
			<td> <%=FormaterDouble.formateDouble(femmeindefini,totindefini) %></td>
		</tr>
		<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
		
		<tr id="colon2">
			<td >Aide Sociale</td>
			<td><%=totalaide %> </td>
			<td> <%=FormaterDouble.formateDouble(totalaide,total) %></td>
			<td><%=haide %> </td>
			<td> <%=FormaterDouble.formateDouble(haide,h) %></td>
			<td><%=faide %> </td>
			<td><%=FormaterDouble.formateDouble(faide,f) %></td>
		</tr>
		<tr>
			<td>RMI/RSA</td>
			<td><%=totalrmi %> </td>
			<td><%=FormaterDouble.formateDouble(totalrmi,total) %></td>
			<td><%=hrmi %> </td>
			<td> <%=FormaterDouble.formateDouble(hrmi,totalrmi) %></td>
			<td><%=frmi %> </td>
			<td> <%=FormaterDouble.formateDouble(frmi,totalrmi) %></td>
		</tr>
		<tr>
			<td>ALLOC SOL SPE</td>
			<td><%=totalass %> </td>
			<td> <%=FormaterDouble.formateDouble(totalass,total) %></td>
			<td><%=hass %> </td>
			<td><%=FormaterDouble.formateDouble(hass,totalass)%></td>
			<td><%=fass %> </td>
			<td> <%=FormaterDouble.formateDouble(fass,totalass)%></td>
		</tr>
		<tr>
			<td>RTH</td>
			<td><%=totalrth %> </td>
			<td> <%=FormaterDouble.formateDouble(totalrth,total) %></td>
			<td><%=hrth %> </td>
			<td> <%=FormaterDouble.formateDouble(hrth,totalrth)%></td>
			<td><%=frth %> </td>
			<td> <%=FormaterDouble.formateDouble(frth,totalrth)%></td>
		</tr>
		<tr>
			<td>PROTECTION JURIDIQUE</td>
			<td><%=totalprot %> </td>
			<td> <%=FormaterDouble.formateDouble(totalprot,total) %></td>
			<td><%=hprot %> </td>
			<td> <%=FormaterDouble.formateDouble(hprot,totalprot)%></td>
			<td><%=fprot %></td>
			<td> <%=FormaterDouble.formateDouble(fprot,totalprot)%></td>
		</tr>
		<tr>
			<td>GRANDE DIFFICULTE</td>
			<td><%=totaldiff %> </td>
			<td> <%=FormaterDouble.formateDouble(totaldiff,total) %></td>
			<td><%=hdiff %> </td>
			<td> <%=FormaterDouble.formateDouble(hdiff,totaldiff)%></td>
			<td><%=fdiff %></td>
			<td> <%=FormaterDouble.formateDouble(fdiff,totaldiff)%></td>
		</tr>
		<tr><td> </td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
		<tr id="colon3">
			<td>Non Inscrit à Pole Emploi</td>
			<td><%=petotalnoninscrit %></td>
			<td> <%=FormaterDouble.formateDouble(petotalnoninscrit,total) %></td>
			<td><%=petotalhommenoninscrit %> </td>
			<td><%=FormaterDouble.formateDouble(petotalhommenoninscrit,petotalnoninscrit) %></td>
			<td><%=petotalfemmenoninscrit %> </td>
			<td> <%=FormaterDouble.formateDouble(petotalfemmenoninscrit,petotalnoninscrit) %></td>
		</tr>
		<tr>
			<td>Inscrit à Pole Emploi</td>
			<td><%=petotalinscrit %></td>
			<td> <%=FormaterDouble.formateDouble(petotalinscrit,total) %></td>
			<td><%=petotalhommeinscrit %> </td>
			<td><%=FormaterDouble.formateDouble(petotalhommeinscrit,petotalinscrit) %></td>
			<td><%=petotalfemmeinscrit %></td>
			<td> <%=FormaterDouble.formateDouble(petotalfemmeinscrit,petotalinscrit) %></td>
		</tr>
		
		<tr>
			<td>Inscrit à Pole Emploi depuis -1 an</td>
			<td><%=petotalmoins1 %> </td>
			<td><%=FormaterDouble.formateDouble(petotalmoins1,petotalinscrit) %></td>
			<td><%=pehommemoins1 %></td>
			<td><%=FormaterDouble.formateDouble(pehommemoins1,petotalmoins1) %></td>
			<td><%=pefemmemoins1 %> </td>
			<td> <%=FormaterDouble.formateDouble(pefemmemoins1,petotalmoins1) %></td>
		</tr>
		<tr>
			<td>Inscrit à Pole Emploi entre 1 et 2 ans</td>
			<td><%=petotalmoins2 %> </td>
			<td> <%=FormaterDouble.formateDouble(petotalmoins2,petotalinscrit) %></td>
			<td><%=pehommemoins2 %> </td>
			<td> <%=FormaterDouble.formateDouble(pehommemoins2,petotalmoins2) %></td>
			<td><%=pefemmemoins2 %> </td>
			<td><%=FormaterDouble.formateDouble(pefemmemoins2,petotalmoins2) %></td>
		</tr>
		<tr>
			<td>Inscrit à Pole Emploi  entre 2 et 3 ans</td>
			<td><%=petotalmoins3 %></td>
			<td> <%=FormaterDouble.formateDouble(petotalmoins3,petotalinscrit) %></td>
			<td><%=pehommemoins3 %></td>
			<td> <%=FormaterDouble.formateDouble(pehommemoins3,petotalmoins3) %></td>
			<td><%=pefemmemoins3 %></td>
			<td> <%=FormaterDouble.formateDouble(pefemmemoins3,petotalmoins3) %></td>
		</tr>
		<tr>
			<td>Inscrit à Pole Emploi depuis + 3 ans</td>
			<td><%=petotalplus3 %></td>
			<td> <%=FormaterDouble.formateDouble(petotalplus3,petotalinscrit) %></td>
			<td><%=pehommeplus3 %></td>
			<td> <%=FormaterDouble.formateDouble(pehommeplus3,petotalplus3) %></td>
			<td><%=pefemmeplus3 %> </td>
			<td> <%=FormaterDouble.formateDouble(pefemmeplus3,petotalplus3) %></td>
		</tr>
		<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
		
		<tr id="colon4">
			<td>Nationalité Française</td>
			<td><%=totalfr %> </td>
			<td> <%=FormaterDouble.formateDouble(totalfr,total) %></td>
			<td><%=totalfrhomme %></td>
			<td> <%=FormaterDouble.formateDouble(totalfrhomme,totalfr) %></td>
			<td><%=totalfrfemme %> </td>
			<td> <%=FormaterDouble.formateDouble(totalfrfemme,totalfr) %></td>
		</tr>
		<tr>
			<td>Nationalité Etrangère</td>
			<td><%=totalet %> </td>
			<td> <%=FormaterDouble.formateDouble(totalet,total) %></td>
			<td><%=totalethomme %> </td>
			<td><%=FormaterDouble.formateDouble(totalethomme,totalet) %></td>
			<td><%=totaletfemme %> </td>
			<td> <%=FormaterDouble.formateDouble(totaletfemme,totalet) %></td>
		</tr>
		<tr>
			<td>Nationalité non renseignée</td>
			<td ><%=totalnonrenseigne %> </td>
			<td> <%=FormaterDouble.formateDouble(totalnonrenseigne,total) %></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr><td> </td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
		<tr id="colon5">
			<td>Niveau 1</td>
			<td><%=totalniv1 %></td>
			<td> <%=FormaterDouble.formateDouble(totalniv1,total) %></td>
			<td><%=hniveau1 %> </td>
			<td><%=FormaterDouble.formateDouble(hniveau1,totalniv1) %></td>
			<td><%=fniveau1 %> </td>
			<td> <%=FormaterDouble.formateDouble(fniveau1,totalniv1) %></td>
		</tr>
		<tr>
			<td>Niveau 2</td>
			<td><%=totalniv2 %> </td>
			<td> <%=FormaterDouble.formateDouble(totalniv2,total) %></td>
			<td><%=hniveau2 %> </td>
			<td> <%=FormaterDouble.formateDouble(hniveau2,totalniv2) %></td>
			<td><%=fniveau2 %> </td>
			<td><%=FormaterDouble.formateDouble(fniveau2,totalniv2) %></td>
		</tr>
		<tr>
			<td>Niveau 3</td>
			<td><%=totalniv3 %> </td>
			<td> <%=FormaterDouble.formateDouble(totalniv3,total) %></td>
			<td><%=hniveau3 %> </td>
			<td> <%=FormaterDouble.formateDouble(hniveau3,totalniv3) %></td>
			<td><%=fniveau3 %></td>
			<td> <%=FormaterDouble.formateDouble(fniveau3,totalniv3) %></td>
		</tr>
		<tr>
			<td>Niveau 4</td>
			<td><%=totalniv4 %></td>
			<td> <%=FormaterDouble.formateDouble(totalniv4,total) %></td>
			<td><%=hniveau4 %> </td>
			<td> <%=FormaterDouble.formateDouble(hniveau4,totalniv4) %></td>
			<td><%=fniveau4 %></td>
			<td> <%=FormaterDouble.formateDouble(fniveau4,totalniv4) %></td>
		</tr>
		<tr>
			<td>Niveau 5</td>
			<td><%=totalniv5 %></td>
			<td> <%=FormaterDouble.formateDouble(totalniv5,total) %></td>
			<td><%=hniveau5 %></td>
			<td><%=FormaterDouble.formateDouble(hniveau5,totalniv5) %></td>
			<td><%=fniveau5 %></td>
			<td> <%=FormaterDouble.formateDouble(fniveau5,totalniv5) %></td>
		</tr>
		<tr>
			<td>Niveau 5B</td>
			<td><%=totalniv5b %></td>
			<td> <%=FormaterDouble.formateDouble(totalniv5b,total) %></td>
			<td><%=hniveau5b %> </td>
			<td> <%=FormaterDouble.formateDouble(hniveau5b,totalniv5b) %></td>
			<td><%=fniveau5b %> </td>
			<td> <%=FormaterDouble.formateDouble(fniveau5b,totalniv5b) %></td>
		</tr>
		<tr>
			<td>Niveau 6</td>
			<td><%=totalniv6 %> </td>
			<td><%=FormaterDouble.formateDouble(totalniv6,total) %></td>
			<td><%=hniveau6 %></td>
			<td> <%=FormaterDouble.formateDouble(hniveau6,totalniv6) %></td>
			<td><%=fniveau6 %> </td>
			<td> <%=FormaterDouble.formateDouble(fniveau6,totalniv6) %></td>
		</tr>
		<tr>
			<td>Niveau 0</td>
			<td><%=totalniv0 %></td>
			<td> <%=FormaterDouble.formateDouble(totalniv0,total) %></td>
			<td><%=hniveau0 %> </td>
			<td> <%=FormaterDouble.formateDouble(hniveau0,totalniv0) %></td>
			<td><%=fniveau0 %> </td>
			<td> <%=FormaterDouble.formateDouble(fniveau0,totalniv0) %></td>
		</tr>
		<tr><td></td><td> </td><td></td><td></td><td></td><td></td><td></td></tr>
		<tr id="colon6">
			<td>Origine Valence d'Agen</td>
			<td><%=totalvalence %> </td>
			<td> <%=FormaterDouble.formateDouble(totalvalence,total) %></td>
			<td><%=hvalence %> </td>
			<td><%=FormaterDouble.formateDouble(hvalence,totalvalence) %></td>
			<td><%=fvalence %></td>
			<td> <%=FormaterDouble.formateDouble(fvalence,totalvalence) %></td>
		</tr>
		<tr>
			<td>Origine CC2R</td>
			<td><%=totalcc2r %> </td>
			<td> <%=FormaterDouble.formateDouble(totalcc2r,total) %></td>
			<td><%=hcc2r %> </td>
			<td><%=FormaterDouble.formateDouble(hcc2r,totalcc2r) %></td>
			<td><%=fcc2r %></td>
			<td> <%=FormaterDouble.formateDouble(fcc2r,totalcc2r) %></td>
		</tr>
		<tr>
			<td>Origine Canton</td>
			<td><%=totalcanton %> </td>
			<td> <%=FormaterDouble.formateDouble(totalcanton,total) %></td>
			<td><%=hcanton %> </td>
			<td> <%=FormaterDouble.formateDouble(hcanton,totalcanton) %></td>
			<td><%=fcanton %></td>
			<td> <%=FormaterDouble.formateDouble(fcanton,totalcanton) %></td>
		</tr>
		
		<tr>
			<td>Origine Castelsarrasin</td>
			<td><%=totalcastel %> </td>
			<td> <%=FormaterDouble.formateDouble(totalcastel,total) %></td>
			<td><%=hcastel %> </td>
			<td> <%=FormaterDouble.formateDouble(hcastel,totalcastel) %></td>
			<td><%=fcastel %></td>
			<td> <%=FormaterDouble.formateDouble(fcastel,totalcastel) %></td>
		</tr>
		<tr>
			<td>Origine Département</td>
			<td><%=total82 %> </td>
			<td> <%=FormaterDouble.formateDouble(total82,total) %></td>
			<td><%=hdepartement %> </td>
			<td><%=FormaterDouble.formateDouble(hdepartement,total82) %></td>
			<td><%=fdepartement %> </td>
			<td> <%=FormaterDouble.formateDouble(fdepartement,total82) %></td>
		</tr>
		
		<tr>
			<td>Origine Région</td>
			<td><%=totalregion %> </td>
			<td> <%=FormaterDouble.formateDouble(totalregion,total) %></td>
			<td><%=hregion %> </td>
			<td> <%=FormaterDouble.formateDouble(hregion,totalregion) %> </td>
			<td><%=fregion %> </td>
			<td> <%=FormaterDouble.formateDouble(fregion,totalregion) %> </td>
		</tr>
		</tbody>
	
</table>
<br>
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
		src="/valence/javascript/jquery.dataTables.min.js"></script>

	<script type="text/javascript"
		src="/valence/javascript/ZeroClipboard.js"></script>
	<script type="text/javascript" src="/valence/javascript/TableTools.js"></script>

	<script type="text/javascript"
		src="/valence/javascript/scripts/tableaux.js"></script>
		<script type="text/javascript"
			src="/valence/javascript/scripts/jour.js"></script>

	</div>

</body>
</html>