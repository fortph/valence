<%@ page
	import="dao.imp.identite.*,beans.parametres.accueil.*, beans.identite.*,dao.imp.sap.*,beans.sap.*,
	java.util.*,beans.employeurs.*,dao.imp.employeur.*,divers.*,com.itextpdf.text.*,
	java.io.*,com.itextpdf.text.pdf.*,com.itextpdf.text.pdf.FontSelector.*,java.net.*,java.text.*"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String numerocontrat = request.getParameter("numcontrat");
	ContratCDIDAO contdao = new ContratCDIDAO();

	ContratCDI contrat = contdao.findByID(Integer.parseInt(numerocontrat));

	/*String panier = "";
	if (contrat.getPanier() != 0.0f)
		panier = ", des paniers journaliers de " + contrat.getPanier() + " € ";
	String depl = "";
	if (contrat.getDeplacement() != 0.0f)
		depl = ", des indemnités kilométriques de " + contrat.getDeplacement() + " €/ km ";
	*/

	DecimalFormat df = new DecimalFormat("0.00");

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	IdentiteDAO idao = new IdentiteDAO();
	Identite identite = idao.findByID(contrat.getIdentite().getId_IDE());
	String civil = "M.";
	if (identite.getSexe_IDE().equals("MASCULIN"))
		civil = "Monsieur";
	else if (identite.getSexe_IDE().equals("FEMININ"))
		civil = "Madame";

	String nationalite = "";
	if (!identite.getNationalite_IDE().equals(""))
		nationalite = ", de nationalité " + identite.getNationalite_IDE();

	String secu = "";
	if (identite.getNss_IDE() != null)
		secu = ", immatriculé(e) à la sécurité sociale sous le n° " + identite.getNss_IDE();
	String nele = "";
	if (identite.getDatenais_IDE() != null) {
		nele = ", né(e) le " + sdf.format(identite.getDatenais_IDE());
		if (!identite.getLieunais_IDE().equals(""))
			nele += " à " + identite.getLieunais_IDE();
	}

	OrganismeDAO ordao = new OrganismeDAO();
	Organisme org = ordao.findByID(1);
%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

	<%
		response.setContentType("application/pdf");
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		Document document = new Document(PageSize.A4, 30, 30, 20, 30);

		//hauteur maxi page =842 points et largeur=595points
		Font titregras = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
		Font normal = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);
		Font normalgras = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
		Font normalgras2 = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD);
		Font policecontrat = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
		
		
		Font petit = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
		Font mini = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.NORMAL);
		Font minigras = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.BOLD);
		Font miniitalic = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.ITALIC);
		//	minigras.setStyle(Font.UNDERLINE);
		Font minixx = new Font(Font.FontFamily.TIMES_ROMAN, 2, Font.NORMAL);
		Font petitgras = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);

		Font titre1 = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL | Font.UNDERLINE);

		Font titre1gras = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
		Font titre1grassouligne = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD | Font.UNDERLINE);
		//ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		try {
			//le flux sera stocké en mémoire et non pas dans un fichier
			PdfWriter writer = PdfWriter.getInstance(document, buffer);
			writer.setPageEvent(new PdfPageEventHelper() {
				public void onEndPage(PdfWriter writer, Document document) {
					int pageNumber = writer.getPageNumber();
					String text = "Page " + pageNumber;
					Rectangle page = document.getPageSize();
					PdfPTable structure = new PdfPTable(1);
					PdfPCell cel1 = new PdfPCell(new Phrase(text));
					cel1.setBorder(Rectangle.NO_BORDER);
					cel1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					structure.addCell(cel1);
					structure.setHorizontalAlignment(Element.ALIGN_RIGHT);
					structure.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
					structure.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin(),
							writer.getDirectContent());
				}
			});

			document.open();

			/*logo de l'entreprise
			String urlimage = "http://localhost:8080/valence/images/bleu/mefi.jpg";
			Image corfi = new Jpeg(new URL(urlimage));
			corfi.setAbsolutePosition(5f, 770f); //position de l'image
			corfi.scalePercent(60f); //taille de l'image*/
			//nouveau paragraphe
			Paragraph titre = new Paragraph();
			titre.setAlignment(Element.ALIGN_CENTER);
			titre.setLeading(25);
			//titre.add("coucou");
			titre.add(new Phrase("CONTRAT DE TRAVAIL\nà temps partiel modulé,\nà DURÉE INDÉTERMINÉE\n\n"
					+ "dans le cadre de l’activité\n'Service à la Personne'\nAgrément simple n° "
					+ contrat.getAgrement(), titregras));
			document.add(titre);

			document.add(new Paragraph("\n"));

			document.add(new Paragraph("Entre les soussignés :", titre1));

			Paragraph signataires = new Paragraph();
			signataires.setAlignment(Element.ALIGN_JUSTIFIED);
			signataires.add(new Paragraph(
					"\nL’association " + org.getRs() + ", code APE n°" + org.getApe() + ", dont le siège est à  "
							+ org.getVille() + " - " + org.getAdr1() + ", représentée par Monsieur "
							+ org.getPresident() + ", agissant en qualité de Président de l’association,",
					policecontrat));
			document.add(signataires);

			Paragraph part1 = new Paragraph("d'une part", policecontrat);
			part1.setIndentationLeft(400);
			document.add(part1);

			Paragraph signataires2 = new Paragraph();
			signataires2.setAlignment(Element.ALIGN_JUSTIFIED);
			signataires2.add(new Paragraph(
					"\net " + civil + " " + identite.getPrenom_IDE() + " " + identite.getNom_IDE() + nele + secu
							+ ", demeurant à " + identite.getAdr1_IDE() + ",  à  " + identite.getVille_IDE(),
					policecontrat));
			document.add(signataires2);

			Paragraph part2 = new Paragraph("d'autre part", policecontrat);
			part2.setIndentationLeft(400);
			document.add(part2);

			Paragraph convenu = new Paragraph("\nIL A ETE CONVENU CE QUI SUIT :", titre1gras);

			document.add(convenu);

			Paragraph urssaf = new Paragraph("La déclaration préalable à l’embauche de  " + civil + " "
					+ identite.getPrenom_IDE() + " " + identite.getNom_IDE()
					+ " a été effectuée à l’URSSAF de Montauban, auprès de laquelle l’association " + org.getRs()
					+ " est immatriculée sous le n° " + org.getUrssaf() + "." + "\n" + civil + " "
					+ identite.getPrenom_IDE() + " " + identite.getNom_IDE()
					+ " pourra exercer auprès de cet organisme son droit d’accès et de rectification "
					+ "que lui confère la loi n° 78-17 du 6 janvier 1978.", policecontrat);
			urssaf.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(urssaf);

			Paragraph conditions = new Paragraph("\nCONDITIONS D'ENGAGEMENT :", titre1grassouligne);
			document.add(conditions);

			Paragraph conditions1 = new Paragraph();
			Phrase ph3 = new Phrase(civil + " " + identite.getPrenom_IDE() + " " + identite.getNom_IDE(),
					policecontrat);
			Phrase ph1 = new Phrase(" est engagée dans le cadre d’un Contrat à durée indéterminée à temps partiel,",
					titre1gras);
			Phrase ph2 = new Phrase(
					" et régi par les dispositions du Code du Travail, ainsi que par les dispositions particulières du présent contrat relatives à l’activité et à l’agrément Service à la Personne, et les conditions particulières ci-après.\n\n",
					policecontrat);
			Phrase ph4 = new Phrase(
					" s’engage, en outre, à se conformer aux dispositions du règlement intérieur, dont un exemplaire lui a été remis ce jour.",
					policecontrat);
			conditions1.add(ph3);
			conditions1.add(ph1);
			conditions1.add(ph2);
			conditions1.add(ph3);
			conditions1.add(ph4);

			conditions1.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(conditions1);

			Paragraph objet = new Paragraph("\nOBJET ET DUREE DU CONTRAT :", titre1grassouligne);
			document.add(objet);
			Paragraph objet1 = new Paragraph();
			Phrase ph5 = new Phrase(civil + " " + identite.getPrenom_IDE() + " " + identite.getNom_IDE(),
					policecontrat);
			Phrase ph6 = new Phrase(" est engagé(e) par l’association " + org.getRs() + " à compter du  "
					+ sdf.format(contrat.getEmbauche()) + " et dans le cadre", policecontrat);
			Phrase ph7 = new Phrase(" d’un contrat de travail à durée indéterminée, à temps partiel modulé.",
					titre1gras);
			objet1.add(ph5);
			objet1.add(ph6);
			objet1.add(ph7);
			objet1.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(objet1);

			Paragraph emploi = new Paragraph("\nEMPLOI ET QUALIFICATION :", titre1grassouligne);
			document.add(emploi);
			Paragraph emploi1 = new Paragraph();
			Phrase ph8 = new Phrase(civil + " " + identite.getPrenom_IDE() + " " + identite.getNom_IDE()
					+ " occupera un emploi d’ ", policecontrat);
			Phrase ph9 = new Phrase(contrat.getTache(), titre1gras);
			Phrase ph10 = new Phrase(", étant entendu qu’en fonction des nécessités d’organisation du travail,"
					+ " vous pourrez être affecté(e) aux divers postes correspondant à la nature "
					+ "de votre emploi. ", policecontrat);
			emploi1.add(ph8);
			emploi1.add(ph9);
			emploi1.add(ph10);
			emploi1.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(emploi1);

			Paragraph duree = new Paragraph("\nDUREE HEBDOMADAIRE DU TRAVAIL :", titre1grassouligne);
			document.add(duree);
			Paragraph duree1 = new Paragraph();
			Phrase ph11 = new Phrase(civil + " " + identite.getPrenom_IDE() + " " + identite.getNom_IDE()
					+ " est engagée dans le cadre ", policecontrat);
			Phrase ph12 = new Phrase(" d’un contrat de travail à temps partiel modulé", titre1gras);
			Phrase ph13 = new Phrase(" sur la base ", policecontrat);
			Phrase ph14 = new Phrase(
					"  d’un horaire hebdomadaire moyen de référence de " + contrat.getHeuresminimois() + " heures ",
					titre1gras);
			Phrase ph15 = new Phrase("  soit " + contrat.getHeuresminimois() * 47
					+ " heures annuelles (réparties sur 47 semaines hors congés).\n   ", policecontrat);
			duree1.add(ph11);
			duree1.add(ph12);
			duree1.add(ph13);
			duree1.add(ph14);
			duree1.add(ph15);
			document.add(duree1);

			Paragraph duree2 = new Paragraph();
			Phrase ph16 = new Phrase("La durée hebdomadaire de " + civil + " " + identite.getPrenom_IDE() + " "
					+ identite.getNom_IDE() + " pourra varier ", policecontrat);
			Phrase ph17 = new Phrase(" d’un dixième de la durée de référence pré-citée", titre1gras);
			Phrase ph18 = new Phrase(
					".  La planification indicative de l’horaire de travail sur l’année est jointe à l’annexe au contrat et dépendante des contrats de prestations de services conclus avec les particuliers utilisateurs. ",
					policecontrat);

			duree2.add(ph16);
			duree2.add(ph17);
			duree2.add(ph18);
			duree2.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(duree2);
			document.add(new Paragraph("\n"));

			Paragraph duree3 = new Paragraph();
			Phrase ph19 = new Phrase(
					" L’accomplissement d’heures complémentaires sous contrat à temps partiel modulé étant impossible, si l’horaire moyen hebdomadaire réellement effectué, dépasse sur 12 semaines consécutives ou pendant 12 semaines sur une période de 15 semaines, l’horaire moyen réellement accompli, plus",
					policecontrat);
			Phrase ph20 = new Phrase("  d’un dixième de la durée de référence pré-citée.", titre1gras);
			Phrase ph21 = new Phrase(
					" Cet horaire de référence sera réévaluée par l’intermédiaire d’un avenant au contrat de travail sur la base de la durée moyenne réellement effectuée, ",
					policecontrat);
			Phrase ph22 = new Phrase(" sous réserve d’un préavis de 7 jours et sauf opposition du salarié. ",
					titre1gras);

			duree3.add(ph19);
			duree3.add(ph20);
			duree3.add(ph21);
			duree3.add(ph22);
			duree3.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(duree3);
			document.add(new Paragraph("\n"));

			Paragraph duree4 = new Paragraph();
			Phrase ph23 = new Phrase(
					" Les horaires et leurs répartitions pourront être modifiés dans les cas suivants :",
					titre1gras);
			duree4.add(ph23);
			duree4.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(duree4);
			Paragraph duree5 = new Paragraph(
					"- Surcroît temporaire ou saisonnier d’activité\n- Absence et/ou remplacement d’un salarié absent",
					titre1gras);
			duree5.setIndentationLeft(50);
			document.add(duree5);

			/***********************REMUNERATION*********************************************/

			Paragraph salaire = new Paragraph("\nREMUNERATION :", titre1grassouligne);
			document.add(salaire);
			Paragraph salaire1 = new Paragraph();
			Phrase ph24 = new Phrase("En rémunération de ses services, " + civil + " " + identite.getPrenom_IDE()
					+ " " + identite.getNom_IDE() + " percevra un ", policecontrat);
			Phrase ph25 = new Phrase(
					"salaire minimal brut horaire de " + contrat.getSalairehoraire()
							+ " €  soit un salaire hebdomadaire brut de "
							+ df.format(contrat.getSalairehoraire() * contrat.getHeuresminimois()) + " € . ",
					titre1gras);
			Phrase ph26 = new Phrase("Il(Elle) percevra les mêmes primes et avantages que le salarié "
					+ " à temps plein de sa catégorie, calculés proportionnellement à son temps de travail,"
					+ " sauf dispositions conventionnelles plus favorables notamment avantages"
					+ " liés à l’ancienneté..", policecontrat);
			salaire1.add(ph24);
			salaire1.add(ph25);
			salaire1.add(new Paragraph("\n"));
			salaire1.add(ph26);
			salaire1.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(salaire1);

			Paragraph lieu = new Paragraph("\nLIEU DE TRAVAIL ET MOBILITE :", titre1grassouligne);
			document.add(lieu);
			Paragraph lieu1 = new Paragraph();
			Phrase ph27 = new Phrase(civil + " " + identite.getPrenom_IDE() + " " + identite.getNom_IDE(),
					policecontrat);
			Phrase ph28 = new Phrase(" exercera ses fonctions au domicile des particuliers et utilisera "
					+ " son véhicule personnel ", titre1gras);
			Phrase ph29 = new Phrase(
					"pour s’y rendre dans un périmètre d’intervention des communes appartenant à la Communauté de Communes des Deux Rives.",
					policecontrat);
			Phrase ph30 = new Phrase(
					"Par ailleurs, " + civil + " " + identite.getPrenom_IDE() + " " + identite.getNom_IDE()
							+ " s’engage à accepter tous changements de lieu de travail nécessités par l’intérêt de "
							+ org.getRs()
							+ ", en fonction des contrats de prestations de service conclus avec les particuliers.",
					policecontrat);
			lieu1.add(ph27);
			lieu1.add(ph28);
			lieu1.add(ph29);
			lieu1.add(new Paragraph("\n"));
			lieu1.add(ph30);
			lieu1.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(lieu1);

			Paragraph essai = new Paragraph("\nPERIODE D'ESSAI :", titre1grassouligne);
			document.add(essai);
			Paragraph essai1 = new Paragraph("L'engagement de " + civil + " " + identite.getPrenom_IDE() + " "
					+ identite.getNom_IDE() + " ne deviendra effectif qu’à l’expiration d’une période d’essai "
					+ "de 15 jours.\nToute suspension qui se produirait pendant la période"
					+ " d’essai prolongerait d’autant la durée de cette période, "
					+ "qui doit correspondre à un travail effectif.\nPendant cette période, "
					+ "chaque partie pourra mettre fin au contrat, à tout moment, "
					+ " sans indemnité d’aucune sorte.", policecontrat);
			essai1.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(essai1);
			document.newPage();

			/***********************CONGES PAYES*********************************************************/

			Paragraph conges = new Paragraph("\nCONGES PAYES :", titre1grassouligne);
			document.add(conges);
			Paragraph conges1 = new Paragraph();
			Phrase ph31 = new Phrase(civil + " " + identite.getPrenom_IDE() + " " + identite.getNom_IDE(),
					policecontrat);
			Phrase ph32 = new Phrase(" bénéficiera de droits à congés payés conformément aux "
					+ " dispositions légales, soit 10% versés mensuellement.", titre1gras);
			conges1.add(ph31);
			conges1.add(ph32);
			conges1.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(conges1);

			Paragraph cumul = new Paragraph("\nCUMUL D'ACTIVITES :", titre1grassouligne);
			document.add(cumul);
			Paragraph cumul1 = new Paragraph(
					"Le présent contrat à temps partiel est cumulable dans"
							+ " les conditions de droit commun avec une activité complémentaire, "
							+ "dans la limite de la durée maximale de travail, en application"
							+ " des articles L.212.1 et suivants du Code du Travail.\n Le salarié s’engage à communiquer à "
							+ org.getRs() + ", ses disponibilités et/ou les changements hebdomadaires.",
					policecontrat);
			cumul1.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(cumul1);

			Paragraph social = new Paragraph("\nPROTECTION SOCIALE :", titre1grassouligne);
			document.add(social);
			Paragraph social1 = new Paragraph();
			Phrase ph33=new Phrase("Dans le cadre du présent contrat, " + civil + " " + identite.getPrenom_IDE() + " "
							+ identite.getNom_IDE()
							+ ", bénéficiera de l’ensemble des régimes de retraite et de prévoyance existant dans l’association ou qui seraient mis en place ultérieurement.\n",
					policecontrat);
			Phrase ph34=new Phrase("Ces régimes sont les suivants :",policecontrat);
			social1.add(ph33);
			social1.add(ph34);
			social1.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(social1);
			
			Paragraph social2 = new Paragraph(
					"- Retraite complémentaire : KLESIA,\n- Prévoyance : CHORUM,\n- Complémentaire santé : HARMONIE MUTUELLE.",
					policecontrat);
			social2.setIndentationLeft(50);
			document.add(social2);
			
			Paragraph social3 = new Paragraph();
			Phrase ph35=new Phrase("A ce titre, "+ civil + " " + identite.getPrenom_IDE() + " "
							+ identite.getNom_IDE()
							+ " contribuera à hauteur de sa participation au financement de ces régimes, par le précompte sur son salaire des cotisations y afférentes.",
					policecontrat);
			
			social3.add(ph35);
			document.add(social3);		
			
			

			Paragraph condpart = new Paragraph("\nFORMALITES :", titre1grassouligne);
			document.add(condpart);
			Paragraph condpart1 = new Paragraph(
					"Le présent contrat est établi en deux exemplaires originaux, dont un pour chacune des parties.\n"
					+"Fait à "+org.getVille()+", le "+ sdf.format(contrat.getRedaction()),
					policecontrat);
			condpart1.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(condpart1);

			document.add(new Paragraph("\n"));

			//creation d'un tableau
			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(100);//largeur du tableau
			//nombre de colonnes et taille de chacune en proportion
			float[] largeur = { 1, 1 };
			table.setWidths(largeur);
			//creation d'une cellule du tableau
			PdfPCell cel1 = new PdfPCell(new Phrase(org.getRs(), policecontrat));
			cel1.setBorder(Rectangle.NO_BORDER);
			cel1.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cel2 = new PdfPCell(new Phrase("Le (La) salarié(e)", policecontrat));
			cel2.setBorder(Rectangle.NO_BORDER);
			cel2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cel1);
			table.addCell(cel2);
			PdfPCell cel3 = new PdfPCell(new Phrase("Mention manuscrite 'Lu et approuvé'", miniitalic));
			cel3.setBorder(Rectangle.NO_BORDER);
			cel3.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cel4 = new PdfPCell(new Phrase("Mention manuscrite 'Lu et approuvé'", miniitalic));
			cel4.setBorder(Rectangle.NO_BORDER);
			cel4.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cel5 = new PdfPCell(new Phrase("(signature)", miniitalic));
			cel5.setBorder(Rectangle.NO_BORDER);
			cel5.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cel6 = new PdfPCell(new Phrase("(signature)", miniitalic));
			cel6.setBorder(Rectangle.NO_BORDER);
			cel6.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cel3);
			table.addCell(cel4);
			table.addCell(cel5);
			table.addCell(cel6);

			document.add(table);

		} catch (DocumentException e) {
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