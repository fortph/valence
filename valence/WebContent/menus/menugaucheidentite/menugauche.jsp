<%@ page
	import="dao.imp.identite.*,dao.mutuelle.*,beans.rth.*,dao.imp.rth.*, beans.identite.*,java.util.*,java.lang.*,
	divers.*,dao.imp.ai.*,beans.ai.*,dao.imp.rmi.*,dao.imp.sap.*,beans.sap.*,beans.formation.*,
	dao.imp.formation.*,java.io.*,jcifs.smb.*,java.nio.file.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String numero = request.getParameter("numero");
	IdentiteDAO iddao = new IdentiteDAO();
	int idpersonne = Integer.parseInt(numero);
	Identite identitep = iddao.findByID(idpersonne);
	final String nompers = identitep.getNom_IDE();
	final String prenompers = identitep.getPrenom_IDE();

	/*   on verifie si la personne a deja fait des formations pyramides*/
	PreInscriptionDAO preinsdao = new PreInscriptionDAO();
	//int formationpyrrealisee=preinsdao.nombrePreinscription(identitep);
	int formationpyrrealisee = preinsdao.nombreEnregistrement(identitep);
	int formpyrencours = preinsdao.nombrePreinscriptionEnCours(identitep);
	String urlimgpyramide = "/valence/images/bleu/corfi_b.png";

	if (formationpyrrealisee > 0) {
		urlimgpyramide = "/valence/images/bleu/corfi_br.png";
		if (formpyrencours > 0)
			urlimgpyramide = "/valence/images/bleu/corfi_v.png";

	}

	/*   on verifie si la personne a deja passé des visites médicales          ****/
	VisiteMedicaleDAO vmdao = new VisiteMedicaleDAO();
	String urlvm = "/valence/jsp/ai/fiches/fichesuite.jsp";
	int vmoui = vmdao.visitePassee(identitep);
	int vmcours = vmdao.visiteEnCoursValidite(identitep);
	String urlimagevisite = "/valence/images/bleu/croix_v.jpeg";
	if (vmoui > 0) {
		urlimagevisite = "/valence/images/bleu/croix_b.jpeg";
		if (vmcours > 0)
			urlimagevisite = "/valence/images/bleu/croix_br.jpeg";
	}

	/*********** on verifie si la personne est deja enregistrée en RMI   *****************************/
	FicheRMIDAO rmidao = new FicheRMIDAO();
	int creationrmi = rmidao.verifierCreationFiche(identitep);
	String urlrmi = "/valence/jsp/rmi_rsa/fiches/fichermi.jsp";

	ContratRMIDAO contdao = new ContratRMIDAO();
	int rmiencours = contdao.contratRMIEnCours(identitep);

	/* url de l'image affiche dans fiches ->rmi */
	String urlimagermi = "/valence/images/bleu/rmi_b.png";
	if (creationrmi >= 1) {
		urlrmi = "/valence/jsp/rmi_rsa/fiches/fichermisuite.jsp";
		urlimagermi = "/valence/images/bleu/rmi_br.png";
		if (rmiencours > 0)
			urlimagermi = "/valence/images/bleu/rmi_v.png";
	}

	/*****************on verifie si la personne est deja enregistrée en AI  **************************/
	CreationDAO credao = new CreationDAO();
	int creation = credao.verifierCreationFiche(identitep);

	//on verifie si la personne a des contrats en cours
	ContratDAO contratdao = new ContratDAO();
	int nbre = contratdao.contratEnCours(identitep);

	String urlai = "/valence/jsp/ai/fiches/fiche.jsp";

	/* url de l'image affiche dans fiches ->ai */
	String urlimageai = "/valence/images/bleu/ai_b.png";
	if (creation == 1) {
		urlai = "/valence/jsp/ai/fiches/fichesuite.jsp";
		urlimageai = "/valence/images/bleu/ai_br.png";
		if (nbre > 0)
			urlimageai = "/valence/images/bleu/ai_v.png";
	}

	/*****************on verifie si la personne à adhéré à une mutuelle  **************************/
	
	MutuelleDAO mutdao = new MutuelleDAO();
	int creationMutuelle=mutdao.verifierCreationFiche(identitep);	
	//on verifie si la personne a des contrats mutuelle en cours
	int nbremutuelle = mutdao.contratEnCoursMutuelle(identitep);
	String urlmutuelle = "/valence/jsp/mutuelle/nouveau.jsp";
	
	
	String urlimagemutuelle = "/valence/images/bleu/mutuel1.png";
	if(creationMutuelle>=1){
		urlmutuelle = "/valence/jsp/mutuelle/historiqueparpersonne.jsp";
		urlimagemutuelle = "/valence/images/bleu/mutuel3.png";
		if(nbremutuelle >0)
			urlimagemutuelle = "/valence/images/bleu/mutuel2.png";
	}

	/*** verifie si la personne a une rth encore valide    ********************/
	String urlrth = "/valence/jsp/rth/creationfiche.jsp";
	String urlimagerth = "/valence/images/bleu/rth_b.png";

	FicheDAORTH fichedao = new FicheDAORTH();
	int fichecree = fichedao.ficheRthExiste(identitep);
	if (fichecree > 0) {
		urlrth = "/valence/jsp/rth/affichagefiche.jsp";
	}

	ProfilPrioriteDAO profdao = new ProfilPrioriteDAO();
	int rthinscrit = profdao.rthValide(identitep);
	int rthencours = profdao.rthEnCoursDeValidite(identitep);

	if (rthinscrit > 0) {
		urlimagerth = "/valence/images/bleu/rth_br.png";
		//urlrth = "/valence/jsp/rth/affichagefiche.jsp";
	}
	if (rthencours > 0) {
		urlimagerth = "/valence/images/bleu/rth_v.png";
		//urlrth = "/valence/jsp/rth/affichagefiche.jsp";
	}

	/*********************************************************************/
	/*                              partie sap                           */
	/*********************************************************************/
	String urlsap = "/valence/jsp/sap/contrat/nouveau.jsp";
	String urlimagesap = "/valence/images/bleu/sap_b.png";

	ContratCDIDAO cdidao = new ContratCDIDAO();
	int cdicreation = cdidao.contratCdiCree(identitep);
	int cdiencours = cdidao.contratCdiCreeEnCours(identitep);
	if (cdiencours > 0) {
		urlimagesap = "/valence/images/bleu/sap_v.png";
		urlsap = "/valence/jsp/sap/fichesap/fiche.jsp";
	}
	if (cdicreation > 0 && cdiencours == 0) {
		urlimagesap = "/valence/images/bleu/sap_br.png";
		urlsap = "/valence/jsp/sap/fichesap/fiche.jsp";
	}
	/*********************************************************************/
	int total = iddao.totalListe();
	int age = identitep.calculerAgeReel();
	String url = "";

	//test de l'age 
	if (age <= 25)
		url = "/valence/images/bleu/j-26.png";
	else if (age <= 45)
		url = "/valence/images/bleu/26-45.png";
	else if (age <= 50)
		url = "/valence/images/bleu/45-50.png";
	else
		url = "/valence/images/bleu//+50.png";

	AfficherPriorites prio = new AfficherPriorites();
	List<String> listepriorites = prio.listingPrioritesPersonne(identitep);

	/*****************     CV                        *********************/
	/*	String urlimagecv="/valence/images/bleu/cv.jpeg";
		//String serveuradr="smb://192.168.21.5/mefi/Documents/CV";
		String serveuradr="smb://192.168.1.44/partage/adrien/";
		
		File fserveur=new File(serveuradr);
		Path pserveur=fserveur.toPath();
		
		File flocal=new File("/home/phil/Bureau");
		Path plocal=flocal.toPath();
		
		String nom="adrien";
		String passe="1234";
		//String nom="sylvain";
		//String passe="sylvain";
		
		String delim=System.getProperty("file.separator");
		
		NtlmPasswordAuthentication auth=new NtlmPasswordAuthentication(nom+":"+passe);
		SmbFile samba=new SmbFile(serveuradr,auth);
		
		SmbFile [] listecv=samba.listFiles(new SmbFileFilter(){
			public boolean accept(SmbFile f){
		
		String nomfichier=f.getName().toLowerCase();
		nomfichier=nomfichier.replace(" ", "");
		String nom= nompers+prenompers;
		nom=nom.toLowerCase();
		if(nomfichier.contains(nom))
		return true;	
		else
		return false;
			}		
		});
		
		for(int i=0;i<listecv.length;i++){
			System.out.println("url ="+listecv[i].getName());
			Path pfichier=new File("smb:///"+delim+"adrien@192.168.1.44/partage/adrien"+delim+listecv[i].getName()).toPath();
			Path pfichierlocal=new File(plocal+delim+listecv[i].getName()).toPath();
			System.out.println("url fichier serveur ="+pfichier);
			Files.createSymbolicLink(pfichierlocal, pfichier);
		}
		
		*/
%>
<!-- <link rel="stylesheet" href="/valence/css/inscription.css">-->
<link rel="stylesheet" href="/valence/css/menugauche/menugauche.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<div class="gauche">

		<img class="gaucheimage" src="/valence/images/bleu/logo_corfi2.jpg" /><br />
		<div class="gaucheprio">
			TOTAL BASE<br /> <font color=#0000FF size="2"><%=total%><br /></font>
			<img src="/valence/images/bleu/capemploi.png" width="50" height="18"><br />
		</div>
		<div class="gaucheprio">
			PRIORITE<br /> <img src="<%=url%>" width="50" height="18" border="0" /><br />
			<%
				if (listepriorites.size() > 0) {
			%>
			<div class="prio">
				<%
					for (int i = 0; i < listepriorites.size(); i++) {
							//on modifie certains  libellés, gain de place
							if (listepriorites.get(i).equals("AIDE-SOCIALE"))
								listepriorites.set(i, "Aide Soc");
							/*
							else if (listepriorites.get(i).equals("INTERIMAIRE"))
								listepriorites.set(i, "Intérim");
							else if (listepriorites.get(i).equals("INDEPENDANT"))
								listepriorites.set(i, "Indép");
							*/
							else if (listepriorites.get(i).equals("ETUDIANT"))
								listepriorites.set(i, "Etudiant");
							else if (listepriorites.get(i).equals("CIVIS-CLASSIQUE"))
								listepriorites.set(i, "Civis Clas");
							else if (listepriorites.get(i).equals("CIVIS-RENFORCE"))
								listepriorites.set(i, "Civis Renf");
							else if (listepriorites.get(i).equals("RSA-SOCLE"))
								listepriorites.set(i, "RSA Soc");
							else if (listepriorites.get(i).equals("RSA-CHAPEAU"))
								listepriorites.set(i, "RSA Chap");

							out.println(listepriorites.get(i) + "<br>");
						}
				%>
			</div>
			<%
				}
			%>
			<br />
		</div>
		<div class="gaucheprio">
			FICHES<br /> <a href="<%=urlai%>?personne=<%=numero%>"> <img
				src="<%=urlimageai%>" width="50" height="18" border="0" alt="AI"></a>
			<br /> <a href="<%=urlrmi%>?personne=<%=numero%>"> <img
				src="<%=urlimagermi%>" width="50" height="18" border="0" alt="RSA"></a>
			<br /> <a href="<%=urlrth%>?personne=<%=numero%>"> <img
				src="<%=urlimagerth%>" width="50" height="18" border="0" alt="RTH"></a>
			<br /> <a href="<%=urlsap%>?personne=<%=numero%>"> <img
				src="<%=urlimagesap%>" width="50" height="18" border="0" alt="SAP"></a>
			<br /> <img src="<%=urlimgpyramide%>" width="50" height="18"
				border="0" alt="CORFI"> <br />
				 			  <a
				href="<%=urlmutuelle%>?numero=<%=numero%>"> <img
				src="<%=urlimagemutuelle%>" width="50" height="18" border="0"
				alt="MUTUELLE"></a><br>
				
				
		</div>

		<div class="gaucheprio">
			VISITE MEDECIN<br /> <a href="<%=urlvm%>?personne=<%=numero%>"><img
				src="<%=urlimagevisite%>" width="50" height="18" border="0" alt="AI"></a>
		</div>
		<!-- 
		<div class="gaucheprio">
			<a href="#" onclick=""><img src="" width="50"
				height="50" border="0" alt="CV"></a>
		</div>
		-->
	</div>
</body>
</html>
