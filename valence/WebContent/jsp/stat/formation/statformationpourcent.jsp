<%@page
	import="beans.parametres.accueil.*,dao.imp.formation.*,beans.formation.*,java.text.*,
	dao.imp.identite.*, beans.identite.*,divers.*,java.util.*,java.sql.*,dao.exception.*"%>
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
	List<ListeFormations> liste = (List<ListeFormations>)request.getAttribute("liste");
	java.util.Date debut=(java.util.Date)request.getAttribute("debut");
	java.util.Date fin=(java.util.Date)request.getAttribute("fin");
	
	PreInscriptionDAO predao=new PreInscriptionDAO();
	PreInscription preins=new PreInscription();
	
	IdentiteDAO iddao=new IdentiteDAO();
	Identite identite=null;
	
	
	int h=0,f=0,total=0;
	int anpe=0;
	
	
	int totmoins25=0,totmoins50=0,totplus50=0,totindefini=0;
	int hommemoins25=0,hommemoins50=0,hommeplus50=0,hommeindefini=0;	
	int femmemoins25=0,femmemoins50=0,femmeplus50=0,femmeindefini=0;
	int petotalinscrit=0,petotalnoninscrit=0,petotalmoins1=0,petotalplus1=0;
	int petotalhommeinscrit=0,petotalhommenoninscrit=0,pehommemoins1=0,pehommeplus1=0;
	int petotalfemmeinscrit=0,petotalfemmenoninscrit=0,pefemmemoins1=0,pefemmeplus1=0;
	int totalniv0=0,totalniv1=0,totalniv2=0,totalniv3=0,totalniv4=0,totalniv5=0,
	totalniv5b=0,totalniv6=0,totalnivill=0;
	int hniveau0=0,hniveau1=0,hniveau2=0,hniveau3=0,hniveau4=0,hniveau5=0,hniveau5b=0,hniveau6=0,hniveauill=0;
	int fniveau0=0,fniveau1=0,fniveau2=0,fniveau3=0,fniveau4=0,fniveau5=0,fniveau5b=0,fniveau6=0,fniveauill=0;
	int totalvalence=0,totalcc2r=0,total82=0,totalregion=0, totalvillenonrenseigne=0,total46=0,total47=0,total32=0;
	int hvalence=0, hcc2r=0,hdepartement=0, hregion=0,h46=0,h47=0,h32=0;
	int fvalence=0, fcc2r=0,fdepartement=0, fregion=0,f46=0,f47=0,f32=0;
		
	//villes de la communaute
	List<String> villescc2r=new Cc2rDAO().afficherVilles();
	
	
	//pour chaque formation trouvee sur la periode selectionnee
	for(int i=0;i<liste.size();i++)
		
		{
		/*on recupere la date de debut de formation*/
		java.util.Date debutformation=liste.get(i).getDatedeb_form();
		
	
		//System.out.println("formation "+liste.get(i).getFormation());	
		
		preins.setListe(liste.get(i));
		preins.setInscrit(true);
		//on recherche la liste des personnes inscrites a chaque formation
		List<PreInscription> listeinscrits=predao.findByCriteria(preins);
		
		
		
	/*pour chaque personne on recupere lses infos identite*/
	for(int j=0;j<listeinscrits.size();j++){
		total++;
		//System.out.println("total ="+total);
		identite=iddao.findByID(listeinscrits.get(j).getIdentite().getId_IDE());
		
		
		
		
		int age=identite.calculerAge(liste.get(i).getDatedeb_form());
		//test de l'age
		 if( age>=0 && age <26 )	totmoins25++;
				else if(age >=26 && age <50) totmoins50++;
				else if(age >=50) totplus50++;
				else totindefini++;	
		
		
		//on recupere la date inscription pole emploi
		boolean inscrit=false;
		java.util.Date datpe=identite.getPoleEmploiInscripription_IDE();
		
		if(datpe!=null &&
		identite.getPoleEmploi_ID_IDE()!=null){
	inscrit=true;
	petotalinscrit++;
		 }
	
		else petotalnoninscrit++;
	
		//si la personne est inscrite a pole emploi, depuis combien?
		if(inscrit){
		anpe=identite.calculerTempsPE(debutformation);
		if(anpe<1) petotalmoins1++;
		else petotalplus1++;
		
		}
		
		
		//test des niveaux de formation
		if(identite.getNiveauFormation_IDE()!=null){
		if(identite.getNiveauFormation_IDE().equals("ILLETRISME"))
	totalnivill++;
		else if(identite.getNiveauFormation_IDE().startsWith("N0"))
	totalniv0++;
		else if(identite.getNiveauFormation_IDE().startsWith("N1"))
	totalniv1++;
		else if(identite.getNiveauFormation_IDE().startsWith("N2"))
	totalniv2++;
		else if(identite.getNiveauFormation_IDE().startsWith("N3"))
	totalniv3++;
		else if(identite.getNiveauFormation_IDE().startsWith("N4"))
	totalniv4++;
		else if(identite.getNiveauFormation_IDE().startsWith("N5 -"))
	totalniv5++;
		else if(identite.getNiveauFormation_IDE().startsWith("N5b"))
	totalniv5b++;
		else if(identite.getNiveauFormation_IDE().startsWith("N6"))
	totalniv6++;
		}
		//test de l'origine geographique
		if(identite.getVille_IDE().trim().equalsIgnoreCase("VALENCE D'AGEN"))
	totalvalence++;
		
		 if(identite.getCp_IDE().startsWith("82"))
	total82++;
		 
		 else  if(identite.getCp_IDE().startsWith("46"))
		total46++;
		 
		 else  if(identite.getCp_IDE().startsWith("47"))
		total47++;
		 
		 else if(identite.getCp_IDE().startsWith("32"))
		total32++;
		
		if(identite.getCp_IDE().startsWith("82") ||
		identite.getCp_IDE().startsWith("09") ||
		identite.getCp_IDE().startsWith("46") ||
		identite.getCp_IDE().startsWith("12") ||
		identite.getCp_IDE().startsWith("81") ||
		identite.getCp_IDE().startsWith("32") ||
		identite.getCp_IDE().startsWith("65") ||
		identite.getCp_IDE().startsWith("31") )
	totalregion++;
		
		
		 for(int x=0;x<villescc2r.size();x++){	
	 //if((villescc2r.get(x)).equalsIgnoreCase(identite.getVille_IDE().trim()))
			 if(identite.getVille_IDE().equalsIgnoreCase(villescc2r.get(x)) ){
		totalcc2r++;
		
	break;		 
			 
			 }
	
		
		 }
		
	
		 
		 
		/*****************statistiques hommes***********************************/
		if(identite.getSexe_IDE().equals("MASCULIN")){
		h++;
		
		
		//test de l'age
		if(age >0 && age <26 )	hommemoins25++;
		else if(age >=26 && age <50) hommemoins50++;
		else if(age >=50) hommeplus50++;
		else hommeindefini++;
		//test pole emploi
		//test pole emploi
			
		if(inscrit){
	petotalhommeinscrit++;
	anpe=identite.calculerTempsPE(debutformation);
	if(anpe<1) pehommemoins1++;
	else pehommeplus1++;
	
		}
		else petotalhommenoninscrit++;
		
		
		//test des niveaux de formation
		if(identite.getNiveauFormation_IDE()!=null){
		if(identite.getNiveauFormation_IDE().equals("ILLETRISME"))
	hniveauill++;
		else if(identite.getNiveauFormation_IDE().startsWith("N0"))
	hniveau0++;
		else if(identite.getNiveauFormation_IDE().startsWith("N1"))
	hniveau1++;
		else if(identite.getNiveauFormation_IDE().startsWith("N2"))
	hniveau2++;
		else if(identite.getNiveauFormation_IDE().startsWith("N3"))
	hniveau3++;
		else if(identite.getNiveauFormation_IDE().startsWith("N4"))
	hniveau4++;
		else if(identite.getNiveauFormation_IDE().startsWith("N5 -"))
	hniveau5++;
		else if(identite.getNiveauFormation_IDE().startsWith("N5b"))
	hniveau5b++;
		else if(identite.getNiveauFormation_IDE().startsWith("N6"))
	hniveau6++;
		}
		//test de l'origine geographique
		if(identite.getVille_IDE().equalsIgnoreCase("VALENCE D'AGEN"))
	hvalence++;
	 if(identite.getCp_IDE().startsWith("82"))
	hdepartement++;
	 
	 else if(identite.getCp_IDE().startsWith("46"))
	h46++;
	 else if(identite.getCp_IDE().startsWith("47"))
	h47++;
	 
	 else  if(identite.getCp_IDE().startsWith("32"))
	h32++;
	
		 if(identite.getCp_IDE().startsWith("82") ||
		 identite.getCp_IDE().startsWith("09") ||
		 identite.getCp_IDE().startsWith("46") ||
		 identite.getCp_IDE().startsWith("12") ||
		 identite.getCp_IDE().startsWith("81") ||
		 identite.getCp_IDE().startsWith("32") ||
		 identite.getCp_IDE().startsWith("65") ||
		 identite.getCp_IDE().startsWith("31") )
	hregion++;
		
		/*if		(identite.getVille_IDE().trim().equalsIgnoreCase(villescanton.get(0)) ||
		identite.getVille_IDE().trim().equalsIgnoreCase(villescanton.get(1)) ||
		identite.getVille_IDE().trim().equalsIgnoreCase(villescanton.get(2)) ||
		identite.getVille_IDE().trim().equalsIgnoreCase(villescanton.get(3)) ||
		identite.getVille_IDE().trim().equalsIgnoreCase(villescanton.get(4)) ||
		identite.getVille_IDE().trim().equalsIgnoreCase(villescanton.get(5)) ||
		identite.getVille_IDE().trim().equalsIgnoreCase(villescanton.get(6)) ||
		identite.getVille_IDE().trim().equalsIgnoreCase(villescanton.get(7)) ||
		identite.getVille_IDE().trim().equalsIgnoreCase(villescanton.get(8)) ||
		identite.getVille_IDE().trim().equalsIgnoreCase(villescanton.get(9)) ||
		identite.getVille_IDE().trim().equalsIgnoreCase(villescanton.get(10)) ||
		identite.getVille_IDE().trim().equalsIgnoreCase(villescanton.get(11)) )
	hcanton++;
		*/
		
		 for(int x=0;x<villescc2r.size();x++){		
	 if(identite.getVille_IDE().trim().equalsIgnoreCase(villescc2r.get(x))){
		hcc2r++;
		break;
	}
		 }
		
		}
		
		
	/***************************statistiques femmes	***********************************/
	
	if(identite.getSexe_IDE().equals("FEMININ")){
		f++;
		
		//test de l'age
		if(age >0 && age <26 )	femmemoins25++;
		else if(age >=26 && age <50) femmemoins50++;
		else if(age >=50) femmeplus50++;
		else femmeindefini++;
		//test pole emploi
		
		
		
		if(inscrit){
		petotalfemmeinscrit++;
		anpe=identite.calculerTempsPE(debutformation);
		if(anpe<1) pefemmemoins1++;
	
		else pefemmeplus1++;
	
		}
		else petotalfemmenoninscrit++;
		
		
		
		
		//test des niveaux de formation
		if(identite.getNiveauFormation_IDE()!=null){
		if(identite.getNiveauFormation_IDE().equals("ILLETRISME"))
	fniveauill++;
		else if(identite.getNiveauFormation_IDE().startsWith("N0"))
	fniveau0++;
		else if(identite.getNiveauFormation_IDE().startsWith("N1"))
	fniveau1++;
		else if(identite.getNiveauFormation_IDE().startsWith("N2"))
	fniveau2++;
		else if(identite.getNiveauFormation_IDE().startsWith("N3"))
	fniveau3++;
		else if(identite.getNiveauFormation_IDE().startsWith("N4"))
	fniveau4++;
		else if(identite.getNiveauFormation_IDE().startsWith("N5 -"))
	fniveau5++;
		else if(identite.getNiveauFormation_IDE().startsWith("N5b"))
	fniveau5b++;
		else if(identite.getNiveauFormation_IDE().startsWith("N6"))
	fniveau6++;
		
		}
		//test de l'origine geographique
		if(identite.getVille_IDE().equalsIgnoreCase("VALENCE D'AGEN"))
	fvalence++;
		 if(identite.getCp_IDE().startsWith("82"))
	fdepartement++;
		 
		 else if(identite.getCp_IDE().startsWith("32"))
		f32++;
		 
		 else  if(identite.getCp_IDE().startsWith("46"))
		f46++;
		 
		 else  if(identite.getCp_IDE().startsWith("47"))
		f47++;
		 
		 if(identite.getCp_IDE().startsWith("82") ||
		 identite.getCp_IDE().startsWith("09") ||
		 identite.getCp_IDE().startsWith("46") ||
		 identite.getCp_IDE().startsWith("12") ||
		 identite.getCp_IDE().startsWith("81") ||
		 identite.getCp_IDE().startsWith("32") ||
		 identite.getCp_IDE().startsWith("65") ||
		 identite.getCp_IDE().startsWith("31") )
	fregion++;
		
		/* if(identite.getVille_IDE().trim().equalsIgnoreCase(villescanton.get(0)) ||
		 identite.getVille_IDE().trim().equalsIgnoreCase(villescanton.get(1)) ||
		 identite.getVille_IDE().trim().equalsIgnoreCase(villescanton.get(2)) ||
		 identite.getVille_IDE().trim().equalsIgnoreCase(villescanton.get(3)) ||
		 identite.getVille_IDE().trim().equalsIgnoreCase(villescanton.get(4)) ||
		 identite.getVille_IDE().trim().equalsIgnoreCase(villescanton.get(5)) ||
		 identite.getVille_IDE().trim().equalsIgnoreCase(villescanton.get(6)) ||
		 identite.getVille_IDE().trim().equalsIgnoreCase(villescanton.get(7)) ||
		 identite.getVille_IDE().trim().equalsIgnoreCase(villescanton.get(8)) ||
		 identite.getVille_IDE().trim().equalsIgnoreCase(villescanton.get(9)) ||
		 identite.getVille_IDE().trim().equalsIgnoreCase(villescanton.get(10)) ||
		 identite.getVille_IDE().trim().equalsIgnoreCase(villescanton.get(11)) )
	fcanton++;
		*/
		
		 for(int x=0;x<villescc2r.size();x++){		
	 if(identite.getVille_IDE().trim().equalsIgnoreCase(villescc2r.get(x))){
			
		fcc2r++;
		break;
	 }
	
	} 	
		
		//fin bloc if (feminin)
	
		
	}}
	
		}
	/******************************on deduit valence de la cc2r	*******************/
	totalcc2r=totalcc2r-totalvalence;		
	fcc2r-=fvalence;
	hcc2r-=hvalence;
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Statistiques</title>
</head>




<body>
	<div id="body">
		<p><%@ include file='/menus/menugeneral/menu.html'%></p>
		<br>
		<div id="creation"><strong>
			STATISTIQUES</strong>
		</div>
		<h3>
			Personnes enregistrées en formation entre le
			<% if(debut!=null) out.println(sdf.format(debut));%>
			et le
			<% if (fin!=null) out.println(sdf.format(fin)); %></h3>
		<table id="accueilpourcent" class="display">
			<thead>
				<tr>
					<th>Critères</th>
					<th class="centre">Total</th>
					<th class="centre">%</th>
					<th class="centre">Homme</th>
					<th class="centre">%</th>
					<th class="centre">Femme</th>
					<th class="centre">%</th>

				</tr>
			</thead>
			<tbody>

				<tr id="colon1">
					<td>Nombre de stagiaires</td>
					<td class="centre"><%=total%></td>
					<td class="centre">100</td>
					<td class="centre"><%=h%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(h,total)%></td>
					<td class="centre"><%=f%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(f,total)%></td>
				</tr>
				<tr>
					<td>moins de 25 ans</td>
					<td class="centre"><%=totmoins25%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(totmoins25,total)%></td>
					<td class="centre"><%=hommemoins25%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(hommemoins25,totmoins25)%></td>
					<td class="centre"><%=femmemoins25%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(femmemoins25,totmoins25)%></td>
				</tr>
				<tr>
					<td>de 26 à 49 ans</td>
					<td class="centre"><%=totmoins50%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(totmoins50,total)%></td>
					<td class="centre"><%=hommemoins50%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(hommemoins50,totmoins50)%></td>
					<td class="centre"><%=femmemoins50%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(femmemoins50,totmoins50)%></td>
				</tr>
				<tr>
					<td>50 ans et plus</td>
					<td class="centre"><%=totplus50%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(totplus50,total)%></td>
					<td class="centre"><%=hommeplus50%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(hommeplus50,totplus50)%></td>
					<td class="centre"><%=femmeplus50%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(femmeplus50,totplus50)%></td>
				</tr>

				<tr id="colon3">
					<td>Non Inscrit à Pole Emploi</td>
					<td class="centre"><%=petotalnoninscrit%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(petotalnoninscrit,total)%></td>
					<td class="centre"><%=petotalhommenoninscrit%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(petotalhommenoninscrit,petotalnoninscrit)%></td>
					<td class="centre"><%=petotalfemmenoninscrit%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(petotalfemmenoninscrit,petotalnoninscrit)%></td>
				</tr>
				<tr>
					<td>Inscrit à Pole Emploi</td>
					<td class="centre"><%=petotalinscrit%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(petotalinscrit,total)%></td>
					<td class="centre"><%=petotalhommeinscrit%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(petotalhommeinscrit,petotalinscrit)%></td>
					<td class="centre"><%=petotalfemmeinscrit%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(petotalfemmeinscrit,petotalinscrit)%></td>
				</tr>

				<tr>
					<td>Inscrit à Pole Emploi depuis -1 an</td>
					<td class="centre"><%=petotalmoins1%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(petotalmoins1,petotalinscrit)%></td>
					<td class="centre"><%=pehommemoins1%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(pehommemoins1,petotalmoins1)%></td>
					<td class="centre"><%=pefemmemoins1%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(pefemmemoins1,petotalmoins1)%></td>
				</tr>

				<tr>
					<td>Inscrit à Pole Emploi depuis + 1 an</td>
					<td class="centre"><%=petotalplus1%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(petotalplus1,petotalinscrit)%></td>
					<td class="centre"><%=pehommeplus1%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(pehommeplus1,petotalplus1)%></td>
					<td class="centre"><%=pefemmeplus1%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(pefemmeplus1,petotalplus1)%></td>
				</tr>

				<tr id="colon5">
					<td>Niveau 1</td>
					<td class="centre"><%=totalniv1%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(totalniv1,total)%></td>
					<td class="centre"><%=hniveau1%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(hniveau1,totalniv1)%></td>
					<td class="centre"><%=fniveau1%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(fniveau1,totalniv1)%></td>
				</tr>
				<tr>
					<td>Niveau 2</td>
					<td class="centre"><%=totalniv2%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(totalniv2,total)%></td>
					<td class="centre"><%=hniveau2%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(hniveau2,totalniv2)%></td>
					<td class="centre"><%=fniveau2%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(fniveau2,totalniv2)%></td>
				</tr>
				<tr>
					<td>Niveau 3</td>
					<td class="centre"><%=totalniv3%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(totalniv3,total)%></td>
					<td class="centre"><%=hniveau3%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(hniveau3,totalniv3)%></td>
					<td class="centre"><%=fniveau3%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(fniveau3,totalniv3)%></td>
				</tr>
				<tr>
					<td>Niveau 4</td>
					<td class="centre"><%=totalniv4%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(totalniv4,total)%></td>
					<td class="centre"><%=hniveau4%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(hniveau4,totalniv4)%></td>
					<td class="centre"><%=fniveau4%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(fniveau4,totalniv4)%></td>
				</tr>
				<tr>
					<td>Niveau 5</td>
					<td class="centre"><%=totalniv5%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(totalniv5,total)%></td>
					<td class="centre"><%=hniveau5%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(hniveau5,totalniv5)%></td>
					<td class="centre"><%=fniveau5%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(fniveau5,totalniv5)%></td>
				</tr>
				<tr>
					<td>Niveau 5B</td>
					<td class="centre"><%=totalniv5b%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(totalniv5b,total)%></td>
					<td class="centre"><%=hniveau5b%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(hniveau5b,totalniv5b)%></td>
					<td class="centre"><%=fniveau5b%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(fniveau5b,totalniv5b)%></td>
				</tr>
				<tr>
					<td>Niveau 6</td>
					<td class="centre"><%=totalniv6%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(totalniv6,total)%></td>
					<td class="centre"><%=hniveau6%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(hniveau6,totalniv6)%></td>
					<td class="centre"><%=fniveau6%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(fniveau6,totalniv6)%></td>
				</tr>
				<tr>
					<td>Niveau 0</td>
					<td class="centre"><%=totalniv0%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(totalniv0,total)%></td>
					<td class="centre"><%=hniveau0%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(hniveau0,totalniv0)%></td>
					<td class="centre"><%=fniveau0%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(fniveau0,totalniv0)%></td>
				</tr>
				<tr id="colon6">
					<td>Origine Valence d'Agen</td>
					<td class="centre"><%=totalvalence%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(totalvalence,total)%></td>
					<td class="centre"><%=hvalence%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(hvalence,totalvalence)%></td>
					<td class="centre"><%=fvalence%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(fvalence,totalvalence)%></td>
				</tr>
				<tr>
					<td>Origine CC2R (hors Valence)</td>
					<td class="centre"><%=totalcc2r%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(totalcc2r,total)%></td>
					<td class="centre"><%=hcc2r%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(hcc2r,totalcc2r)%></td>
					<td class="centre"><%=fcc2r%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(fcc2r,totalcc2r)%></td>
				</tr>

				<tr>
					<td>Origine Tarn et Garonne</td>
					<td class="centre"><%=total82%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(total82,total)%></td>
					<td class="centre"><%=hdepartement%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(hdepartement,total82)%></td>
					<td class="centre"><%=fdepartement%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(fdepartement,total82)%></td>
				</tr>
				<tr>
					<td>Origine Région</td>
					<td class="centre"><%=totalregion%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(totalregion,total)%></td>
					<td class="centre"><%=hregion%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(hregion,totalregion)%></td>
					<td class="centre"><%=fregion%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(fregion,totalregion)%></td>
				</tr>

				<tr>
					<td>Origine Lot et Garonne</td>
					<td class="centre"><%=total47%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(total47,total)%></td>
					<td class="centre"><%=h47%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(h47,total47)%></td>
					<td class="centre"><%=f47%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(f47,total47)%></td>
				</tr>

				<tr>
					<td>Origine Lot</td>
					<td class="centre"><%=total46%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(total46,total)%></td>
					<td class="centre"><%=h46%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(h46,total46)%></td>
					<td class="centre"><%=f46%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(f46,total46)%></td>
				</tr>

				<tr>
					<td>Origine Gers</td>
					<td class="centre"><%=total32%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(total32,total)%></td>
					<td class="centre"><%=h32%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(h32,total32)%></td>
					<td class="centre"><%=f32%></td>
					<td class="centre"><%=FormaterDouble.formateDouble(f32,total32)%></td>
				</tr>
			</tbody>

		</table>



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
		<script type="text/javascript" src="/valence/javascript/TableTools.min.js"></script>

		<script type="text/javascript"
			src="/valence/javascript/scripts/tableaux.js"></script>
		<br>
	</div>
</body>
</html>