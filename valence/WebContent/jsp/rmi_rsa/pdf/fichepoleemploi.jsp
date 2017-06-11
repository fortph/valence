<%@ page
	import="dao.imp.identite.*,beans.parametres.accueil.*, beans.identite.*,dao.imp.rmi.*,beans.rmi.*,
	java.util.*,beans.employeurs.*,dao.imp.employeur.*,dao.imp.suivi.*,beans.suivi.*,divers.*,com.itextpdf.text.*,
	java.io.*,com.itextpdf.text.pdf.*,java.net.*,java.text.*"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Pe</title>
<%
	String numerofiche = request.getParameter("fiche");

	FicheLiaisonRMIDAO fldao = new FicheLiaisonRMIDAO();
	FicheLiaisonRMI fiche = fldao.findByID(Integer
			.parseInt(numerofiche));

	ContratRMIDAO contdao = new ContratRMIDAO();
	ContratRMI contrat = contdao.findByID(fiche.getContrat()
			.getId_rmicontrat());

	IdentiteDAO idao = new IdentiteDAO();
	Identite identite = idao
			.findByID(contrat.getIdentite().getId_IDE());
	String sexe=identite.getSexe_IDE();
	sexe=("FEMININ").equals(sexe) ? "FEMME" : "HOMME";

	FicheRMIDAO ficrmidao = new FicheRMIDAO();
	FicheRMI fichermi = ficrmidao.recupereDerniereFicheRMI(identite);

	OrganismeDAO ordao = new OrganismeDAO();
	Organisme maisonemploi = ordao.findByID(6);
	Organisme cap2000 = ordao.findByID(4);//capemploi id=1

	
	java.util.List<SuiviPersonne> suivis = contdao.recupereDateSUiviDansFourchetteContrat(identite, contrat);
			

	ReferentPoleEmploiDAO refpedao = new ReferentPoleEmploiDAO();
	ReferentPoleEmploi referentpe = refpedao.afficheReferent();
%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

	<%
		response.setContentType("application/pdf");
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		Document document = new Document(PageSize.A4, 20, 20, 00, 20);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		//hauteur maxi page =842 points et largeur=595points

		Font normal = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
		Font normalitalic = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.ITALIC);
		Font normalgras = new Font(Font.FontFamily.TIMES_ROMAN, 12,
				Font.BOLD);

		Font gros = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL);
		Font petit = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
		Font mini = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.NORMAL);
		Font grosgras = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
		Font grosgrasrouge = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14f,Font.BOLD, BaseColor.RED);
		Font minigrasitalic = new Font(Font.FontFamily.TIMES_ROMAN, 8,
				Font.BOLDITALIC);
		//	minigras.setStyle(Font.UNDERLINE);
		Font minixx = new Font(Font.FontFamily.TIMES_ROMAN, 2, Font.NORMAL);
		Font petitgras = new Font(Font.FontFamily.TIMES_ROMAN, 10,
				Font.BOLD);
		//creation d'une police personnelle en couleur
		Font mapolice = FontFactory.getFont(FontFactory.COURIER, 12f,
				Font.BOLD, BaseColor.RED);
		//ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		try {
			//le flux sera stocké en mémoire et non pas dans un fichier
			PdfWriter.getInstance(document, buffer);
			document.open();

			//logo de poleemploi
			String urlimagepe = "http://"+request.getServerName()+":"+request.getServerPort()+"/valence/images/bleu/pe.jpeg";

			Image pe = new Jpeg(new URL(urlimagepe));
			pe.setAbsolutePosition(20f, 770f); //position de l'image
			pe.scalePercent(60f); //taille de l'image

			//logo du cg82
			String urlimagecg = "http://"+request.getServerName()+":"+request.getServerPort()+"/valence/images/bleu/cg82.jpeg";

			Image cg = new Jpeg(new URL(urlimagecg));
			cg.setAbsolutePosition(100f, 770f); //position de l'image
			//cg.scalePercent(80f); //taille de l'image

			//logo europe
			String urlimageeur = "http://"+request.getServerName()+":"+request.getServerPort()+"/valence/images/bleu/europe.jpeg";

			Image eur = new Jpeg(new URL(urlimageeur));
			eur.setAbsolutePosition(410f, 770f); //position de l'image
			//eur.scalePercent(80f); //taille de l'image

			//logo fse
			String urlimagefse = "http://"+request.getServerName()+":"+request.getServerPort()+"/valence/images/bleu/fse.jpeg";

			Image fse = new Jpeg(new URL(urlimagefse));
			fse.setAbsolutePosition(510f, 770f); //position de l'image
			//fse.scalePercent(60f); //taille de l'image

			Paragraph images = new Paragraph();
			images.add(cg);
			images.add(pe);
			images.add(eur);
			images.add(fse);

			document.add(images);
			document.add(new Paragraph("\n\n\n\n\n"));

			//nouveau paragraphe
			PdfPTable titre = new PdfPTable(1);
			titre.setWidthPercentage(100);
			PdfPCell nomtitre = new PdfPCell(new Phrase(
					"FICHE DE LIAISON P.D.I/ RSA\n", normalgras));
			nomtitre.setHorizontalAlignment(Element.ALIGN_CENTER);
			nomtitre.setPadding(5);
			titre.addCell(nomtitre);
			document.add(titre);

			Paragraph renvoi = new Paragraph();
			Phrase ph1 = new Phrase(
					"A renvoyer aux prescripteurs et copie :\n", normal);
			Phrase ph2 = new Phrase("Service RSA-Insertion :", normal);
			Phrase ph3 = new Phrase(referentpe.getMailrsa() + "\n",
					mapolice);
			Phrase ph4 = new Phrase("Pôle Emploi- DT : ", normal);
			Phrase ph5 = new Phrase(referentpe.getMaildt() + "\n", normal);
			renvoi.setAlignment(Element.ALIGN_CENTER);
			renvoi.add(ph1);
			renvoi.add(ph2);
			renvoi.add(ph3);
			renvoi.add(ph4);
			renvoi.add(ph5);
			document.add(renvoi);

			document.add(new Paragraph("\n\n"));

			Phrase ph6 = new Phrase(
					"Objectifs, Résultats et Perspectives à l'attention du prescripteur\n",
					grosgrasrouge);
			document.add(ph6);

			document.add(new Paragraph("\n"));

			Paragraph date = new Paragraph();
			//Phrase ph7 = new Phrase("Orientation pour la période du ", normalgras);
			Phrase ph7 = new Phrase("Orientation pour la période du "
					+ sdf.format(contrat.getDatedeb_rmicontrat()) + " au "
					+ sdf.format(contrat.getDatefin_rmicontrat()),normalgras);
			Chunk ph8 = new Chunk("\n( à renseigner par l'opérateur )",normalitalic);
			ph8.setBackground(new BaseColor(183,179,179));
			date.add(ph7);
			date.add(ph8);
			document.add(date);

			document.add(new Paragraph("\n"));

			PdfPTable intro = new PdfPTable(2);
			intro.setWidthPercentage(100);
			float[] largeur = { 1, 1 };
			intro.setWidths(largeur);
			PdfPCell cel1 = new PdfPCell(new Phrase("Prescripteur : "
					+ fichermi.getPrescripteur(), normal));
			cel1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cel1.setBorder(13);

			PdfPCell cel2 = new PdfPCell(new Phrase("Dates du contrat d'Engagement :\nObservations :",normal));
			cel2.setRowspan(7);
			PdfPCell cel3 = new PdfPCell(new Phrase("Nom : "
					+ fichermi.getResponsable(), normal));
			cel3.setBorder(12);
			PdfPCell cel4 = new PdfPCell(new Phrase("Fonction : "
					+ fichermi.getFonction() , normal));
			cel4.setBorder(12);
			PdfPCell cel5 = new PdfPCell(new Phrase("Agence ou Pôle : "
					+ fichermi.getAgence(), normal));
			cel5.setBorder(12);
			PdfPCell cel6 = new PdfPCell(new Phrase("Nom du référent : "
					+ fichermi.getReferent().getNom()+ " "+fichermi.getReferent().getPrenom(), normal));
			cel6.setBorder(12);
			PdfPCell cel7 = new PdfPCell(new Phrase("Mail du référent : "
					+ fichermi.getReferent().getMail(), normal));
			cel7.setBorder(12);
			PdfPCell cel8 = new PdfPCell(
					new Phrase(
							"* joindre le relevé de conclusion d'entretien",
							normal));
			cel8.setBorder(14);
			intro.addCell(cel1);
			intro.addCell(cel2);
			intro.addCell(cel3);
			intro.addCell(cel4);
			intro.addCell(cel5);
			intro.addCell(cel6);
			intro.addCell(cel7);
			intro.addCell(cel8);
			document.add(intro);

			document.add(new Paragraph(
					new Phrase(
							"Intitulé de l'action : Aide à l'émergence du projet prépro accompagnement individuel",
							normalgras)));
			document.add(new Paragraph("\n"));

			PdfPTable operateur = new PdfPTable(2);
			operateur.setWidthPercentage(100);
			float[] largeur1 = { 1, 1 };
			operateur.setWidths(largeur1);
			PdfPCell op1 = new PdfPCell(new Phrase("Opérateur : "
					+ cap2000.getRs(), normal));
			op1.setHorizontalAlignment(Element.ALIGN_CENTER);
			op1.setBorder(13);

			PdfPCell op2 = new PdfPCell();
			op2.setRowspan(7);
			PdfPCell op3 = new PdfPCell(new Phrase("Nom : "
					+ fichermi.getReferent().getPrenom() + " "
					+ fichermi.getReferent().getNom(), normal));
			op3.setBorder(12);
			//PdfPCell op4 = new PdfPCell(new Phrase("Fonction : Conseiller en insertion professionnelle"
			//		, normal));
			//op4.setBorder(12);
			PdfPCell op5 = new PdfPCell(new Phrase("Tél : "
					+ cap2000.getTel(), normal));
			op5.setBorder(12);
			PdfPCell op6 = new PdfPCell(new Phrase("Territoire d'intervention: Valence d'Agen",normal));
			op6.setBorder(12);
			PdfPCell op7 = new PdfPCell(new Phrase("Mail : "
					+ fichermi.getReferent().getMail(), normal));
			op7.setBorder(12);
			PdfPCell op8 = new PdfPCell(new Phrase("\n", normal));
			op8.setBorder(14);
			operateur.addCell(op1);
			operateur.addCell(op2);
			operateur.addCell(op3);
			//operateur.addCell(op4);
			operateur.addCell(op5);
			operateur.addCell(op6);
			operateur.addCell(op7);
			operateur.addCell(op8);
			document.add(operateur);

			document.add(new Paragraph("\n"));

			PdfPTable benef = new PdfPTable(2);
			benef.setWidthPercentage(100);
			float[] largeur2 = { 1, 1 };
			benef.setWidths(largeur2);
			Paragraph tout = new Paragraph();
			Chunk ch1 = new Chunk("Bénéficiaire", normal);
			Chunk ch2 = new Chunk(" à convoquer par courrier", normalgras);
			tout.add(ch1);
			tout.add(ch2);
			PdfPCell be1 = new PdfPCell(tout);
			be1.setHorizontalAlignment(Element.ALIGN_CENTER);
			be1.setBorder(13);

			PdfPCell be2 = new PdfPCell();
			be2.setRowspan(9);
			PdfPCell be3 = new PdfPCell(new Phrase("Nom : "
					+ identite.getNom_IDE(), normal));
			be3.setBorder(12);
			PdfPCell be4 = new PdfPCell(new Phrase("Prénom : "
					+ identite.getPrenom_IDE(), normal));
			be4.setBorder(12);
			PdfPCell be5 = new PdfPCell(new Phrase("N° Tél : "
					+ identite.getFixe_IDE(), normal));
			be5.setBorder(12);
			PdfPCell be6 = new PdfPCell(new Phrase("Adresse : "
					+ identite.getAdr1_IDE(), normal));
			be6.setBorder(12);
			PdfPCell be7 = new PdfPCell(new Phrase("Adresse : "
					+ identite.getCp_IDE() + " " + identite.getVille_IDE(),
					normal));
			be7.setBorder(12);
			PdfPCell be8 = new PdfPCell(new Phrase("Mail : "
					+ identite.getMail_IDE(), normal));
			be8.setBorder(12);
			PdfPCell be9 = new PdfPCell(new Phrase("Identifiant : "
					+ identite.getPoleEmploi_ID_IDE(), normal));
			be9.setBorder(12);
			PdfPCell be10 = new PdfPCell(new Phrase("\n", normal));
			be10.setBorder(14);
			benef.addCell(be1);
			benef.addCell(be2);
			benef.addCell(be3);
			benef.addCell(be4);
			benef.addCell(be5);
			benef.addCell(be6);
			benef.addCell(be7);
			benef.addCell(be8);
			benef.addCell(be9);
			benef.addCell(be10);
			document.add(benef);

			document.add(new Paragraph(
					new Phrase(
							"I - Présence et Objectifs fixés au premier mois de la prise en charge :",
							normalgras)));
			document.add(new Paragraph(
					new Phrase(
							"A transmettre dans les 15 jours de la prise en charge :",
							normal)));

			document.add(new Paragraph("\n"));

			PdfPTable pres = new PdfPTable(1);
			pres.setWidthPercentage(100);
			PdfPCell pres1 = new PdfPCell(new Phrase(fiche.getPresence(),
					normal));
			pres1.setHorizontalAlignment(Element.ALIGN_LEFT);
			pres1.setPadding(5);
			pres1.setBorder(13);
			PdfPCell pres2 = new PdfPCell(new Phrase("\n", normal));
			pres2.setBorder(14);
			pres.addCell(pres1);
			pres.addCell(pres2);
			document.add(pres);

			document.add(new Paragraph("\n"));

			document.add(new Paragraph(new Phrase(
					"II - Dates des rencontres :", normalgras)));

			document.add(new Paragraph("\n"));
			PdfPTable drenc = new PdfPTable(1);
			drenc.setWidthPercentage(100);
			PdfPCell drenc1 = new PdfPCell();
			drenc1.setHorizontalAlignment(Element.ALIGN_LEFT);
			drenc1.setPadding(5);

			if (suivis != null) {
				Paragraph suivi = new Paragraph();
				Phrase lessuivis = new Phrase();
				for (int i = 0; i < suivis.size(); i++) {
					lessuivis.add(sdf.format(suivis.get(i).getDatesuivi())
							+ ", ");
				}
				drenc1.addElement(lessuivis);
				drenc.addCell(drenc1);
			}
			document.add(drenc);

			document.add(new Paragraph("\n"));

			document.add(new Paragraph(new Phrase(
					"III - Résultats obtenus : ", normalgras)));
			document.add(new Paragraph(
					new Phrase(
							"A transmettre le 15 du mois de fin de prise en charge :",
							normal)));
			document.add(new Paragraph("\n"));

			PdfPTable competences = new PdfPTable(3);
			competences.setWidthPercentage(100);
			float[] largeur3 = { 1, 1, 1 };
			competences.setWidths(largeur3);

			PdfPCell com1 = new PdfPCell(new Phrase("Compétences acquises",
					normal));
			com1.setHorizontalAlignment(Element.ALIGN_CENTER);
			com1.setBorder(15);
			com1.setPadding(10);

			PdfPCell com2 = new PdfPCell(new Phrase(
					"Compétences en cours d'acquisition", normal));
			com2.setHorizontalAlignment(Element.ALIGN_CENTER);
			com2.setBorder(15);
			com2.setPadding(10);
			PdfPCell com3 = new PdfPCell(new Phrase(
					"Compétences non acquises ", normal));
			com3.setHorizontalAlignment(Element.ALIGN_CENTER);
			com3.setBorder(15);
			com3.setPadding(10);

			PdfPCell com4 = new PdfPCell(new Phrase(fiche.getAcquis(),
					normal));
			com4.setBorder(12);
			PdfPCell com5 = new PdfPCell(new Phrase(fiche.getEncours(),
					normal));
			com5.setBorder(12);
			PdfPCell com6 = new PdfPCell(new Phrase(fiche.getNomacquis(),
					normal));
			com6.setBorder(12);

			PdfPCell com7 = new PdfPCell(new Phrase("\n", normal));
			com7.setBorder(14);
			PdfPCell com8 = new PdfPCell(new Phrase("\n", normal));
			com8.setBorder(14);
			PdfPCell com9 = new PdfPCell(new Phrase("\n", normal));
			com9.setBorder(14);

			competences.addCell(com1);
			competences.addCell(com2);
			competences.addCell(com3);
			competences.addCell(com4);
			competences.addCell(com6);
			competences.addCell(com5);
			competences.addCell(com7);
			competences.addCell(com8);
			competences.addCell(com9);

			document.add(competences);

			document.add(new Paragraph("\n"));

			document.add(new Paragraph(new Phrase(
					"IV - Conclusions de l'opérateur et perspectives :  ",
					normalgras)));
			document.add(new Paragraph("\n"));
			PdfPTable conclure = new PdfPTable(1);
			conclure.setWidthPercentage(100);
			PdfPCell conc1 = new PdfPCell(new Phrase(fiche.getConclusion(),
					normal));
			conc1.setHorizontalAlignment(Element.ALIGN_LEFT);
			conc1.setBorder(13);
			conc1.setPadding(5);
			PdfPCell conc2 = new PdfPCell(new Phrase("\n", normal));
			conc2.setBorder(14);
			conclure.addCell(conc1);
			conclure.addCell(conc2);
			document.add(conclure);

			document.add(new Paragraph("\n"));

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		document.close();
		/*affichage de la page*/

		DataOutput output = new DataOutputStream(response.getOutputStream());
		byte[] bytes = buffer.toByteArray();
		response.setContentLength(bytes.length);
		for (int i = 0; i < bytes.length; i++) {
			output.writeByte(bytes[i]);
		}
		try {
			buffer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.getOutputStream().close();
	%>