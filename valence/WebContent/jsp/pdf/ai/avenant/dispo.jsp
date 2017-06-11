<%@ page
	import="dao.imp.identite.*,beans.parametres.accueil.*, beans.identite.*,dao.imp.ai.*,beans.ai.*,
	java.util.*,beans.employeurs.*,dao.imp.employeur.*,divers.*,com.itextpdf.text.*,
	java.io.*,com.itextpdf.text.pdf.*,java.net.*,java.text.*"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String numeroavenant = request.getParameter("numeroavenant");

	AvenantDAO avendao = new AvenantDAO();
	Avenant avenant = avendao.findByID(Integer.parseInt(numeroavenant));
	String periodessai = avendao.calculPeriodeEssai(avenant);
	ContratDAO contratdao = new ContratDAO();
	Contrat contrat = contratdao.findByID(avenant.getContrat()
			.getIdaicontrat());

	IdentiteDAO idao = new IdentiteDAO();
	Identite identite = idao
			.findByID(contrat.getIdentite().getId_IDE());
	EmployeurDAO empdao = new EmployeurDAO();
	Employeur employeur = empdao.findByID(contrat.getEmployeur()
			.getId_employeur());

	OrganismeDAO ordao = new OrganismeDAO();
	Organisme or = ordao.findByID(6);

	//l'employeur peut etre un particulier ou une entreprise 
	String civilite = employeur.getCivemp();
	String affichecivite = "";
	if (civilite != null)
		affichecivite = civilite;
%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

	<%
		response.setContentType("application/pdf");
		Document document;
		PdfWriter ecriture;
		PdfContentByte cb;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		DecimalFormat decimalformat=new DecimalFormat("0.00");

		//hauteur maxi page =842 points et largeur=595points
		Font titregras = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);
		Font normal = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);
		Font normalgras = new Font(Font.FontFamily.TIMES_ROMAN, 10,
				Font.BOLD);
		Font normalgras2 = new Font(Font.FontFamily.TIMES_ROMAN, 9,
				Font.BOLD);
		Font policecontrat = new Font(Font.FontFamily.TIMES_ROMAN, 12,
				Font.BOLD);

		Font petit = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
		Font mini = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.NORMAL);
		Font minigras = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.BOLD);
		Font minigrasitalic = new Font(Font.FontFamily.TIMES_ROMAN, 8,
				Font.BOLDITALIC);
		//	minigras.setStyle(Font.UNDERLINE);
		Font minixx = new Font(Font.FontFamily.TIMES_ROMAN, 2, Font.NORMAL);
		Font petitgras = new Font(Font.FontFamily.TIMES_ROMAN, 10,
				Font.BOLD);
		//creation d'une police personnelle en couleur
		Font mapolice = FontFactory.getFont(FontFactory.COURIER, 20f,
				Font.BOLD, BaseColor.BLUE);
		Font normalgrasR = FontFactory.getFont(FontFactory.TIMES_ROMAN,
				10f, Font.BOLD, BaseColor.RED);

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		document = new Document(PageSize.A4, 20, 20, 00, 20);

		try {
			//le flux sera stocké en mémoire et non pas dans un fichier
			ecriture = PdfWriter.getInstance(document, buffer);
			document.open();

			//logo de l'entreprise
			String urlimage = "http://"+request.getServerName()+":"+request.getServerPort()+"/valence/images/bleu/mefi.jpg";

			Image corfi = new Jpeg(new URL(urlimage));
			corfi.setAbsolutePosition(5f, 770f); //position de l'image
			corfi.scalePercent(60f); //taille de l'image
			//nouveau paragraphe
			Paragraph titre = new Paragraph();
			titre.setAlignment(Element.ALIGN_CENTER);
			titre.add(new Paragraph(
					"Association Intermédiaire\n CAP EMPLOI\n", mapolice));

			//creation d'un tableau
			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(100);//largeur du tableau
			//nombre de colonnes et taille de chacune en proportion
			float[] largeur = { 1, 4 };
			table.setWidths(largeur);
			//creation d'une cellule du tableau
			PdfPCell cel1 = new PdfPCell(corfi);
			cel1.setBorder(Rectangle.NO_BORDER);
			cel1.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell cel2 = new PdfPCell(titre);
			cel2.setBorder(Rectangle.NO_BORDER);
			cel2.setPadding(10);
			cel2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cel2.setVerticalAlignment(Element.ALIGN_CENTER);
			table.addCell(cel1);
			table.addCell(cel2);
			document.add(table);

			Paragraph titre1 = new Paragraph();
			titre1.setAlignment(Element.ALIGN_CENTER);
			titre1.setLeading(12);

			titre1.add(new Phrase(or.getRs() + "\n", normalgras));
			titre1.add(new Phrase(or.getAdr1() + " " + or.getCp() + " "
					+ or.getVille() + "\n", normalgras2));
			titre1.add(new Phrase("Téléphone : " + or.getTel()
					+ " - Fax : " + or.getFax() + "\n", normalgras2));
			titre1.add(new Phrase("SIRET : " + or.getSiret() + " - APE : "
					+ or.getApe() + " - URSSAF 82 : " + or.getUrssaf()
					+ "\n", normalgras2));
			document.add(titre1);
			document.add(new Paragraph("\n"));

			Chunk sautligne = new Chunk("");

			PdfPTable table1 = new PdfPTable(6);
			table1.setWidthPercentage(100);//largeur du tableau
			//nombre de colonnes et taille de chacune en proportion
			float[] largeur1 = { 2, 3, 2, 3, 3, 3 };
			table1.setWidths(largeur1);
			//creation d'une cellule du tableau
			PdfPCell cel3 = new PdfPCell(new Phrase("Contrat N°",
					normalgras));
			cel3.setBorder(Rectangle.NO_BORDER);
			cel3.setHorizontalAlignment(Element.ALIGN_LEFT);
			cel3.setVerticalAlignment(Element.ALIGN_CENTER);
			PdfPCell cel4 = new PdfPCell(new Phrase("", normalgras));
			cel4.setHorizontalAlignment(Element.ALIGN_CENTER);
			cel4.setVerticalAlignment(Element.ALIGN_TOP);
			PdfPCell cel5 = new PdfPCell(new Phrase("Avenant N°",
					normalgras));
			cel5.setBorder(Rectangle.NO_BORDER);
			cel5.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cel5.setVerticalAlignment(Element.ALIGN_CENTER);
			PdfPCell cel6 = new PdfPCell(new Phrase(
					avenant.getN_aiavenant(), normalgrasR));
			cel6.setHorizontalAlignment(Element.ALIGN_CENTER);
			cel6.setVerticalAlignment(Element.ALIGN_TOP);
			PdfPCell cel7 = new PdfPCell(new Phrase("Date contrat initial",
					normalgras));
			cel7.setBorder(Rectangle.NO_BORDER);
			cel7.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cel7.setVerticalAlignment(Element.ALIGN_CENTER);
			PdfPCell cel8 = new PdfPCell(new Phrase(sdf.format(contrat
					.getRedaction()), normalgrasR));
			cel8.setHorizontalAlignment(Element.ALIGN_CENTER);
			cel8.setVerticalAlignment(Element.ALIGN_BOTTOM);
			table1.addCell(cel3);
			table1.addCell(cel4);
			table1.addCell(cel5);
			table1.addCell(cel6);
			table1.addCell(cel7);
			table1.addCell(cel8);
			document.add(table1);

			document.add(new Paragraph("\n"));

			PdfPTable contrattravail = new PdfPTable(1);
			contrattravail.setWidthPercentage(100);//largeur du tableau
			//nombre de colonnes et taille de chacune en proportion
			float[] largeurcontrat = { 1 };
			contrattravail.setWidths(largeurcontrat);

			PdfPCell cel9 = new PdfPCell(new Phrase(
					"CONTRAT DE MISE A DISPOSITION\n", policecontrat));
			/* on declare plusieurs bordures en precisant le total de chacun d'elles
			avec Rectangle.BOTTOM=2,Rectangle.TOP=1,Rectangle.RIGHT=8,Rectangle.LEFT=4
			 */
			cel9.setBorder(13);
			cel9.setBackgroundColor(new BaseColor(210, 210, 210));
			cel9.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cel10 = new PdfPCell(
					new Phrase(
							"Conclu par l'Association Intermédiaire - (Article L322-4.16 du Code du Travail)",
							petit));
			cel10.setBorder(14);

			cel10.setBackgroundColor(new BaseColor(210, 210, 210));
			cel10.setHorizontalAlignment(Element.ALIGN_CENTER);
			contrattravail.addCell(cel9);
			contrattravail.addCell(cel10);
			document.add(contrattravail);

			Paragraph dispo = new Paragraph(
					new Phrase(
							"Pour la mise à disposition du salarié auprès de l'utilisateur désigné ci-dessous.",
							mini));
			dispo.setAlignment(Element.ALIGN_CENTER);
			document.add(dispo);

			document.add(sautligne);

			/******************** premier double tableau *************************/
			PdfPTable table2 = new PdfPTable(5);
			table2.setWidthPercentage(100);//largeur du tableau
			//nombre de colonnes et taille de chacune en proportion
			float[] largeur2 = { 4, 5, 1, 4, 5 };
			table2.setWidths(largeur2);
			//creation d'une cellule du tableau
			PdfPCell cel01 = new PdfPCell(new Phrase(
					"SALARIE(E) EN MISE A DISPOSITION", normal));
			cel01.setColspan(2);
			cel01.setBackgroundColor(new BaseColor(172, 235, 172));
			cel01.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cel02 = new PdfPCell(new Phrase("", normal));
			cel02.setBorder(Rectangle.NO_BORDER);
			PdfPCell cel03 = new PdfPCell(new Phrase("UTILISATEUR", normal));
			cel03.setColspan(2);
			cel03.setBackgroundColor(new BaseColor(172, 235, 172));
			cel03.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cel01);
			table2.addCell(cel02);
			table2.addCell(cel03);

			//ligne0
			PdfPCell cel011 = new PdfPCell(new Phrase(" Nom et Prénom :",
					normal));
			cel011.setBorder(4);
			//cel011.setColspan(1);
			PdfPCell cel012 = new PdfPCell(new Phrase(" "
					+ identite.getNom_IDE() + " "
					+ identite.getPrenom_IDE(), normalgras));

			cel012.setBorder(8);
			//cel012.setColspan(1);
			table2.addCell(cel011);
			table2.addCell(cel012);

			PdfPCell cel013 = new PdfPCell(new Phrase("", normal));
			cel013.setBorder(Rectangle.NO_BORDER);
			//cel013.setColspan(1);
			table2.addCell(cel013);

			PdfPCell cel014 = new PdfPCell(new Phrase(affichecivite + " "
					+ employeur.getRs_employeur(), normalgras));
			cel014.setColspan(2);
			cel014.setBorder(12);
			cel014.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cel014);

			//ligne1
			PdfPCell cel021 = new PdfPCell(new Phrase(" Adresse :", normal));
			// cel021.setColspan(1);
			PdfPCell cel022 = new PdfPCell(new Phrase( identite.getAdr1_IDE() , normal));
			// cel022.setColspan(1);
			cel021.setBorder(4);
			cel022.setBorder(8);
			table2.addCell(cel021);
			table2.addCell(cel022);

			PdfPCell cel023 = new PdfPCell(new Phrase("", normal));
			// cel023.setColspan(1);
			cel023.setBorder(Rectangle.NO_BORDER);
			table2.addCell(cel023);

			PdfPCell cel024 = new PdfPCell(new Phrase(contrat.getService()
					.getService(), normalgras));
			cel024.setColspan(2);
			cel024.setBorder(12);
			cel024.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cel024);
			
			

			//ligne1
			PdfPCell cel0211 = new PdfPCell(new Phrase(" ", normal));
			cel0211.setBorder(4);
			// cel021.setColspan(1);
			PdfPCell cel0221 = new PdfPCell(new Phrase( identite.getAdr2_IDE(), normal));
			// cel022.setColspan(1);
			cel0221.setBorder(4);
			cel0221.setBorder(8);
			table2.addCell(cel0211);
			table2.addCell(cel0221);

			PdfPCell cel0231 = new PdfPCell(new Phrase("", normal));
			// cel023.setColspan(1);
			cel0231.setBorder(Rectangle.NO_BORDER);
			table2.addCell(cel0231);

			PdfPCell cel0241 = new PdfPCell(new Phrase("", normalgras));
			cel0241.setColspan(2);
			cel0241.setBorder(12);
			cel0241.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cel0241);


			//ligne3
			PdfPCell cel031 = new PdfPCell(new Phrase(" ", normal));
			// 	cel031.setColspan(1);
			PdfPCell cel032 = new PdfPCell(new Phrase(
					" " + identite.getCp_IDE() + "  "
							+ identite.getVille_IDE(), normal));
			// cel032.setColspan(1);
			cel031.setBorder(4);
			cel032.setBorder(8);
			table2.addCell(cel031);
			table2.addCell(cel032);

			PdfPCell cel033 = new PdfPCell(new Phrase("", normal));
			// cel033.setColspan(1);
			cel033.setBorder(Rectangle.NO_BORDER);
			table2.addCell(cel033);

			PdfPCell cel034 = new PdfPCell(new Phrase(employeur.getAdr1(),
					normal));
			cel034.setColspan(2);
			cel034.setBorder(12);
			cel034.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cel034);

			//ligne4
			PdfPCell cel041 = new PdfPCell(new Phrase(
					" Date de naissance : ", normal));
			// cel041.setColspan(1);
			PdfPCell cel042 = new PdfPCell(new Phrase(" "
					+ sdf.format(identite.getDatenais_IDE()), normal));
			// cel042.setColspan(1);
			cel041.setBorder(4);
			cel042.setBorder(8);
			table2.addCell(cel041);
			table2.addCell(cel042);

			PdfPCell cel043 = new PdfPCell(new Phrase("", normal));
			// cel043.setColspan(1);
			cel043.setBorder(Rectangle.NO_BORDER);
			table2.addCell(cel043);

			PdfPCell cel044 = new PdfPCell(new Phrase(employeur.getCp()
					+ " " + employeur.getVille(), normal));
			cel044.setColspan(2);
			cel044.setBorder(12);
			cel044.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.addCell(cel044);

			//ligne5
			PdfPCell cel051 = new PdfPCell(new Phrase(
					" N° de sécurité sociale: ", normal));
			// cel051.setColspan(1);
			cel051.setBorder(4);
			PdfPCell cel052 = new PdfPCell(new Phrase(" "
					+ identite.getNss_IDE(), normal));
			// cel052.setColspan(1);
			cel052.setBorder(8);
			table2.addCell(cel051);
			table2.addCell(cel052);

			PdfPCell cel053 = new PdfPCell(new Phrase("", normal));
			// cel053.setColspan(1);
			cel053.setBorder(Rectangle.NO_BORDER);
			table2.addCell(cel053);

			PdfPCell cel054 = new PdfPCell(new Phrase(" Téléphone : "
					+ employeur.getTel1(), normal));
			cel054.setColspan(2);
			cel054.setBorder(12);
			cel054.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cel054);
			/*PdfPCell cel055 = new PdfPCell(new Phrase(employeur.getTel1(),
					normal));
			// cel055.setColspan(1);
			cel055.setBorder(8);
			cel055.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cel055);
			 */
			//ligne6
			PdfPCell cel061 = new PdfPCell(new Phrase(
					" Déclaration URSSAF 82 : ", normal));
			// cel061.setColspan(1);
			cel061.setBorder(6);
			PdfPCell cel062 = new PdfPCell(new Phrase(" "
					+ contrat.getUrssaf(), normal));
			// cel062.setColspan(1);
			cel062.setBorder(10);
			table2.addCell(cel061);
			table2.addCell(cel062);

			PdfPCell cel063 = new PdfPCell(new Phrase("", normal));
			// cel063.setColspan(1);
			cel063.setBorder(Rectangle.NO_BORDER);
			table2.addCell(cel063);

			PdfPCell cel064 = new PdfPCell(new Phrase(" Responsable : "+employeur.getCivresp() + " "
					+ employeur.getPrenomresponsable() + " "
					+ employeur.getNomresponsable(),
					normal));
			 cel064.setColspan(2);
			cel064.setBorder(14);
			cel064.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cel064);
			/* PdfPCell cel065 = new PdfPCell(new Phrase(
					employeur.getCivresp() + " "
							+ employeur.getPrenomresponsable() + " "
							+ employeur.getNomresponsable(), normal));
			// cel065.setColspan(1);
			cel065.setBorder(10);
			cel065.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cel065);
*/
			document.add(table2);
			/***********************fin du premier tableau **********/
			document.add(new Phrase("\n", minixx));
			//creation d'un tableau
			PdfPTable qualite = new PdfPTable(3);
			qualite.setWidthPercentage(100);//largeur du tableau
			//nombre de colonnes et taille de chacune en proportion
			float[] largeurqualite = { 9, 1, 9 };
			qualite.setWidths(largeurqualite);
			//creation d'une cellule du tableau
			PdfPCell qualite1 = new PdfPCell(new Phrase(" EN QUALITE DE : "
					+ contrat.getRome().getIntitule(), normal));
			qualite1.setColspan(2);
			qualite1.setBorder(Rectangle.NO_BORDER);
			qualite1.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell qual3 = new PdfPCell(new Phrase(" LIEU D'EXECUTION : "
					+ avenant.getLieu(), normal));

			qual3.setBorder(Rectangle.NO_BORDER);
			qualite.addCell(qualite1);

			qualite.addCell(qual3);

			document.add(qualite);
			document.add(new Phrase("\n", mini));
			Phrase tache = new Phrase(" TACHES A EFFECTUER : "
					+ avenant.getTache(), normal);
			document.add(tache);
			document.add(sautligne);

			/************ creation d'un tableau duree contrat  *************************************/
			PdfPTable dateddebfin = new PdfPTable(6);
			dateddebfin.setWidthPercentage(100);//largeur du tableau
			//nombre de colonnes et taille de chacune en proportion
			float[] largeurqualitedates = { 4, 3, 2, 1, 3, 6 };
			dateddebfin.setWidths(largeurqualitedates);

			PdfPCell titreduree = new PdfPCell(new Phrase(
					" DUREE DETERMINEE DU CONTRAT : ", normal));
			titreduree.setColspan(6);
			titreduree.setBorder(Rectangle.NO_BORDER);

			PdfPCell datedeb = new PdfPCell(new Phrase(" Date de début : ",
					normal));
			datedeb.setBorder(Rectangle.NO_BORDER);
			datedeb.setHorizontalAlignment(Element.ALIGN_LEFT);
			datedeb.setVerticalAlignment(Element.ALIGN_BOTTOM);
			PdfPCell datedebj = new PdfPCell(new Phrase(sdf.format(avenant
					.getDatedeb()), normalgras));
			datedebj.setColspan(2);
			datedebj.setBorder(Rectangle.NO_BORDER);
			datedebj.setVerticalAlignment(Element.ALIGN_TOP);
			datedebj.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell datemil = new PdfPCell(new Phrase("", normal));
			datemil.setBorder(Rectangle.NO_BORDER);

			PdfPCell datefin = new PdfPCell(new Phrase(" Date de fin : ",
					normal));
			datefin.setBorder(Rectangle.NO_BORDER);
			datefin.setHorizontalAlignment(Element.ALIGN_LEFT);
			datefin.setVerticalAlignment(Element.ALIGN_BOTTOM);

			PdfPCell datefinj = new PdfPCell(new Phrase(sdf.format(avenant
					.getDatefin()), normalgras));
			datefinj.setBorder(Rectangle.NO_BORDER);
			datefinj.setVerticalAlignment(Element.ALIGN_TOP);
			datefinj.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell finduree = new PdfPCell(new Phrase(
					" Et pour une durée minimale hebdomadaire de : ",
					normal));
			finduree.setColspan(2);
			finduree.setBorder(Rectangle.NO_BORDER);
			finduree.setVerticalAlignment(Element.ALIGN_BOTTOM);

			PdfPCell findureej = new PdfPCell(new Phrase(
					avenant.getDureehebdomadaire() + " heure(s)",
					normalgras));
			findureej.setColspan(4);
			findureej.setVerticalAlignment(Element.ALIGN_TOP);
			findureej.setBorder(Rectangle.NO_BORDER);
			// findureej.setHorizontalAlignment(Element.ALIGN_CENTER);

			dateddebfin.addCell(titreduree);
			dateddebfin.addCell(datedeb);
			dateddebfin.addCell(datedebj);
			dateddebfin.addCell(datemil);
			dateddebfin.addCell(datefin);
			dateddebfin.addCell(datefinj);
			dateddebfin.addCell(finduree);
			dateddebfin.addCell(findureej);
			document.add(dateddebfin);

			document.add(new Phrase("\n"));

			

			Phrase essai = new Phrase(" PERIODE D'ESSAI : " + periodessai,
					normal);
			document.add(essai);
			Phrase nb = new Phrase(
					"\n NB : 1 jour franc par semaine avec 2 semaines maxi pour CDD de moins de 6 mois et 1 mois pour CDD supérieur à 6 mois.\n Pour toute intervention inférieure à une heure, il sera facturé une heure.. ",
					normal);
			document.add(nb);
			

			/******************** deuxieme double tableau *************************/
			PdfPTable remuneration = new PdfPTable(5);
			remuneration.setWidthPercentage(100);//largeur du tableau
			//nombre de colonnes et taille de chacune en proportion
			float[] largeurrem = { 4, 5, 1, 4, 5 };
			remuneration.setWidths(largeurrem);
			//creation d'une cellule du tableau
			PdfPCell rem01 = new PdfPCell(new Phrase(
					"REMUNERATION DU SALARIE", normal));
			rem01.setColspan(2);
			rem01.setBackgroundColor(new BaseColor(172, 235, 172));
			rem01.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell rem02 = new PdfPCell(new Phrase("", normal));
			rem02.setBorder(Rectangle.NO_BORDER);
			PdfPCell rem03 = new PdfPCell(new Phrase(
					"FACTURATION (non soumis à TVA)", normal));
			rem03.setColspan(2);
			rem03.setBackgroundColor(new BaseColor(172, 235, 172));
			rem03.setHorizontalAlignment(Element.ALIGN_CENTER);
			remuneration.addCell(rem01);
			remuneration.addCell(rem02);
			remuneration.addCell(rem03);

			//ligne0
			PdfPCell rem011 = new PdfPCell(new Phrase(
					" Salaire horaire brut :", normal));
			rem011.setBorder(4);
			PdfPCell rem012 = new PdfPCell(new Phrase(
					decimalformat.format(avenant.getSalairehoraire()) + " €", normalgras));

			rem012.setBorder(8);
			remuneration.addCell(rem011);
			remuneration.addCell(rem012);

			PdfPCell rem013 = new PdfPCell(new Phrase("", normal));
			rem013.setBorder(Rectangle.NO_BORDER);
			remuneration.addCell(rem013);

			PdfPCell rem014 = new PdfPCell(new Phrase(
					" Facturation horaire :", normal));
			rem014.setBorder(4);
			rem014.setHorizontalAlignment(Element.ALIGN_LEFT);
			remuneration.addCell(rem014);

			PdfPCell rem015 = new PdfPCell(new Phrase(
					decimalformat.format(avenant.getFacturation()) + " € ", normalgras));
			rem015.setBorder(8);
			remuneration.addCell(rem015);

			//ligne1
			PdfPCell rem021 = new PdfPCell(new Phrase(" Congés Payés :",
					normal));
			PdfPCell rem022 = new PdfPCell(new Phrase(
					"10% du salaire brut ", normalgras));
			rem021.setBorder(4);
			rem022.setBorder(8);
			remuneration.addCell(rem021);
			remuneration.addCell(rem022);

			PdfPCell rem023 = new PdfPCell(new Phrase("", normal));
			rem023.setBorder(Rectangle.NO_BORDER);
			remuneration.addCell(rem023);

			PdfPCell rem024 = new PdfPCell(new Phrase(" ", normal));
			rem024.setBorder(4);
			rem024.setHorizontalAlignment(Element.ALIGN_LEFT);
			remuneration.addCell(rem024);

			PdfPCell rem025 = new PdfPCell(new Phrase(" ", normalgras));
			rem025.setBorder(8);
			remuneration.addCell(rem025);

			//ligne3
			PdfPCell rem031 = new PdfPCell(
					new Phrase(" Paniers : ", normal));
			// rem031.setColspan(1);
			PdfPCell rem032 = new PdfPCell(new Phrase(
					decimalformat.format(avenant.getPanier())
					+ " €", normalgras));
			rem031.setBorder(4);
			rem032.setBorder(8);
			remuneration.addCell(rem031);
			remuneration.addCell(rem032);

			PdfPCell rem033 = new PdfPCell(new Phrase("", normal));
			// rem033.setColspan(1);
			rem033.setBorder(Rectangle.NO_BORDER);
			remuneration.addCell(rem033);

			PdfPCell rem034 = new PdfPCell(
					new Phrase(" Paniers : ", normal));
			rem034.setBorder(4);
			rem034.setHorizontalAlignment(Element.ALIGN_LEFT);
			remuneration.addCell(rem034);

			PdfPCell rem035 = new PdfPCell(new Phrase(
					decimalformat.format(avenant.getFacturepanier()) + " €", normalgras));
			rem035.setBorder(8);
			remuneration.addCell(rem035);

			//ligne4
			PdfPCell rem041 = new PdfPCell(new Phrase(
					" Frais de déplacement : ", normal));
			PdfPCell rem042 = new PdfPCell(new Phrase(
					decimalformat.format(avenant.getDeplacement()) + " €", normalgras));
			rem041.setBorder(4);
			rem042.setBorder(8);
			remuneration.addCell(rem041);
			remuneration.addCell(rem042);

			PdfPCell rem043 = new PdfPCell(new Phrase("", normal));
			rem043.setBorder(Rectangle.NO_BORDER);
			remuneration.addCell(rem043);

			PdfPCell rem044 = new PdfPCell(new Phrase(
					" Frais de déplacement : ", normal));
			rem044.setBorder(4);
			rem044.setHorizontalAlignment(Element.ALIGN_LEFT);
			remuneration.addCell(rem044);

			PdfPCell rem045 = new PdfPCell(new Phrase(
					decimalformat.format(avenant.getFacturedeplace()) + " €", normalgras));
			rem045.setBorder(8);
			remuneration.addCell(rem045);

			//ligne5
			PdfPCell rem051 = new PdfPCell(new Phrase(" Autres : ", normal));
			rem051.setBorder(6);
			PdfPCell rem052 = new PdfPCell(new Phrase(
					decimalformat.format(avenant.getDivers())
					+ " €", normalgras));
			rem052.setBorder(10);
			remuneration.addCell(rem051);
			remuneration.addCell(rem052);

			PdfPCell rem053 = new PdfPCell(new Phrase("", normal));
			rem053.setBorder(Rectangle.NO_BORDER);
			remuneration.addCell(rem053);

			PdfPCell rem054 = new PdfPCell(new Phrase(" Autres : ", normal));
			rem054.setBorder(6);
			rem054.setHorizontalAlignment(Element.ALIGN_LEFT);
			remuneration.addCell(rem054);

			PdfPCell rem055 = new PdfPCell(new Phrase(
					decimalformat.format(avenant.getFacturedivers()) + " €", normalgras));
			rem055.setBorder(10);
			remuneration.addCell(rem055);

			document.add(remuneration);
			/*******************************************************************/

			Phrase retraite = new Phrase(
					"Caisse de Retraite Complémentaire : KLESIA",
					normal);
			document.add(retraite);
			document.add(new Phrase("\n"));
			Phrase important = new Phrase(" IMPORTANT : ", minigras);
			document.add(important);
			Paragraph notes = new Paragraph();
			notes.setLeading(10);
			Chunk notes1 = new Chunk(
					"  L\'objet de ce contrat est la mise à disposition d\'une personne pour l\'exécution des tâches mentionnées ci-dessus.\n",
					minigras);
			Chunk notes11 = new Chunk(
					"  Cette personne est mise à disposition",
					minigrasitalic);
			Chunk notes12 = new Chunk(", c\'est à dire", minigras);
			Chunk notes13 = new Chunk(
					" qu\'elle exécute le travail sous vos ordres et sous votre entier contrôle.",
					minigrasitalic);
			Chunk notes14 = new Chunk(" Vous êtes donc ", minigras);

			Chunk notes15 = new Chunk(
					" responsable des conditions\n  d\'exécution des travaux ",
					minigrasitalic);
			Chunk notes16 = new Chunk(
					" conformément à l\'article L124-4-6 du Code du Travail ",
					minigras);
			Chunk notes17 = new Chunk(
					" ainsi que des conséquences vis-à-vis des tiers et des accidents ",
					minigrasitalic);
			Chunk notes18 = new Chunk(
					"qui pourraient survenir du fait\n  de l\'exécution de ces travaux, ",
					minigras);

			Chunk notes19 = new Chunk(
					"vous devez vérifier que votre assurance couvre bien ces risques.\n ",
					minigrasitalic);
			Chunk notes20 = new Chunk(
					" Aucune modification de la tâche indiquée au contrat ne peut être effectuée sans l\'accord préalable et exprès de l\'association.\n",
					minigras);

			Chunk notes21 = new Chunk(
					"  Les modifications portées au dos  de votre exemplaire font intégralement partie du contrat que vous signez.\n ",
					minigrasitalic);
			Chunk notes22 = new Chunk(
					" Ce contrat doit être signé avant le début de la mission et nous être retourné dans les 48 heures. Toute facture est payable au comptant, y compris lorsque le travail devra faire l\'objet de\n  plusieurs factures successives.\n",
					minigras);
			Chunk notes23 = new Chunk(
					"  L\'utilisateur s\'engage à ne pas traiter directement avec le travailleur, en recourant notamment au travail clandestin.\n",
					minigras);

			notes.add(notes1);
			notes.add(notes11);
			notes.add(notes12);
			notes.add(notes13);
			notes.add(notes14);
			notes.add(notes15);
			notes.add(notes16);
			notes.add(notes17);
			notes.add(notes18);
			notes.add(notes19);
			notes.add(notes20);
			notes.add(notes21);
			notes.add(notes22);
			notes.add(notes23);
			document.add(notes);

			Paragraph faita = new Paragraph();
			faita.setLeading(20);
			faita.setIndentationLeft(20);
			Chunk fait1 = new Chunk("Fait à Valence d'Agen, le ", normal);
			Chunk fait2 = new Chunk(sdf.format(avenant.getRedaction())
					+ ".", normalgras);
			faita.add(fait1);
			faita.add(fait2);
			document.add(faita);

			Paragraph signe = new Paragraph();
			signe.setLeading(20);
			signe.setIndentationLeft(20);
			Chunk signe1 = new Chunk(
					"Signatures des deux parties : (Déclarent avoir pris connaissance des conditions du contrat) - (Cachet et signature) ",
					normal);
			signe.add(signe1);
			document.add(signe);

			

			PdfPTable signatures = new PdfPTable(2);
			signatures.setWidthPercentage(100);//largeur du tableau
			//nombre de colonnes et taille de chacune en proportion
			float[] largeursig = { 1, 1 };
			signatures.setWidths(largeursig);

			PdfPCell sig03 = new PdfPCell(new Phrase("L'Utilisateur :",
					normalgras));

			sig03.setPaddingLeft(20);
			sig03.setPaddingTop(7);
			sig03.setBorder(Rectangle.NO_BORDER);
			sig03.setHorizontalAlignment(Element.ALIGN_CENTER);
			signatures.addCell(sig03);

			PdfPCell sig04 = new PdfPCell(new Phrase(
					"L'Association CAP EMPLOI :", normalgras));
			sig04.setPaddingLeft(20);
			sig04.setPaddingTop(7);
			sig04.setBorder(Rectangle.NO_BORDER);
			sig04.setHorizontalAlignment(Element.ALIGN_CENTER);
			signatures.addCell(sig04);
			document.add(signatures);

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
		DataOutput sortie = new DataOutputStream(response.getOutputStream());

		byte[] bytes = buffer.toByteArray();
		response.setContentLength(bytes.length);
		for (int i = 0; i < bytes.length; i++) {
			sortie.writeByte(bytes[i]);
		}
		try {
			buffer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	%>