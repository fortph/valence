<%@ page
	import="dao.imp.identite.*,beans.parametres.accueil.*, beans.identite.*,dao.imp.sap.*,beans.sap.*,
	java.util.*,beans.employeurs.*,dao.imp.employeur.*,divers.*,com.itextpdf.text.*,
	java.io.*,com.itextpdf.text.pdf.*,java.net.*,java.text.*"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String numerocontrat = request.getParameter("numcontrat");
	String rajoutpaniers = "";
	AvenantPrestationCDIDAO contratdao = new AvenantPrestationCDIDAO();
	AvenantPrestationCDI prestation = contratdao.findByID(Integer.parseInt(numerocontrat));
	if (!prestation.getCommentaire().equals(""))
		rajoutpaniers = "DIVERS : " + prestation.getCommentaire();

	EmployeurDAO empdao = new EmployeurDAO();
	Employeur employeur = empdao.findByID(prestation.getPrestation().getEmployeur().getId_employeur());

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	IdentiteDAO idao = new IdentiteDAO();
	Identite identite = idao.findByID(prestation.getPrestation().getIdentite().getId_IDE());
	String civil = "M.", civilemp = "M.";
	if (identite.getSexe_IDE().equals("MASCULIN"))
		civil = "Monsieur";
	else if (identite.getSexe_IDE().equals("FEMININ"))
		civil = "Madame";

	if (employeur.getCivemp().startsWith("Mr"))
		civilemp = "Monsieur";
	else if (employeur.getCivemp().startsWith("Mm"))
		civilemp = "Madame";

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
		Font titre = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.NORMAL);
		Font titre1gras = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD | Font.UNDERLINE);
		Font titre1 = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.NORMAL);
		Font normal = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.NORMAL);
		Font normalgras = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
		Font normalgras2 = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD);
		Font policecontrat = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.ITALIC);

		Font moyen = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.UNDERLINE | Font.BOLD);
		Font moyen2 = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.UNDERLINE | Font.BOLD);

		Font petit = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.NORMAL);
		Font petitsouligne = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.NORMAL | Font.UNDERLINE);
		Font mini = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL | Font.ITALIC);
		Font petitgras = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);
		Font petititalic = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.ITALIC);
		Font miniitalic = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.ITALIC);
		//	minigras.setStyle(Font.UNDERLINE);
		Font minixx = new Font(Font.FontFamily.TIMES_ROMAN, 2, Font.NORMAL);

		Font titre1grassouligne = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD | Font.UNDERLINE);

		//creation d'une police personnelle en couleur
		Font mapolice = FontFactory.getFont(FontFactory.TIMES_ROMAN, 20f, Font.BOLD, BaseColor.BLUE);
		Font petitgrasrouge = FontFactory.getFont(FontFactory.TIMES_ROMAN, 13f, Font.BOLD, BaseColor.RED);
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

			Paragraph organisation = new Paragraph();
			organisation.setAlignment(Element.ALIGN_CENTER);
			organisation.setLeading(25);

			organisation.add(new Phrase(org.getRs() + " - " + org.getStructure(), mapolice));
			document.add(organisation);

			Paragraph service = new Paragraph(new Phrase("SERVICE A LA PERSONNE", titre1gras));
			service.setAlignment(Element.ALIGN_CENTER);
			document.add(service);

			Paragraph service1 = new Paragraph(new Phrase("AGREMENT SIMPLE  N°  " + org.getAgrementsap(), titre1));
			service1.setAlignment(Element.ALIGN_CENTER);
			document.add(service1);
			document.add(new Paragraph("\n"));
			//creation d'un tableau
			PdfPTable table = new PdfPTable(1);
			table.setWidthPercentage(100);//largeur du tableau

			PdfPCell cel1 = new PdfPCell(new Phrase("AVENANT A LA PRESTATION DE SERVICE N° P"
					+ prestation.getPrestation().getId_prestationcontrat() + "-A" + prestation.getRangavenant(),
					titre));
			cel1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cel1.setPadding(5);
			table.addCell(cel1);
			document.add(table);

			document.add(new Paragraph("\n"));

			Paragraph entre = new Paragraph(
					new Phrase("Entre : l'Association Intermédiaire " + org.getRs() + ",", normal));
			document.add(entre);
			Paragraph entre1 = new Paragraph(new Phrase(
					org.getAdr1() + " - " + org.getAdr2() + " - " + org.getCp() + " " + org.getVille(), normal));
			entre1.setFirstLineIndent(45);
			entre1.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(entre1);

			Paragraph entre2 = new Paragraph(new Phrase(
					"\nEt : le particulier utilisateur " + civilemp + " " + employeur.getRs_employeur(), normal));
			document.add(entre2);
			Paragraph entre3 = new Paragraph(new Phrase(employeur.getAdr1(), normal));
			entre3.setFirstLineIndent(200);
			document.add(entre3);
			Paragraph entre4 = new Paragraph(new Phrase(employeur.getCp() + " " + employeur.getVille(), normal));
			entre4.setFirstLineIndent(200);
			document.add(entre4);

			Paragraph objectif = new Paragraph(new Phrase(
					"\nLe contrat a pour objectif  de définir le type de prestation offerte, les modalités et la durée d’intervention ainsi que les conditions financières.",
					petit));
			objectif.setAlignment(Element.ALIGN_JUSTIFIED);
			objectif.setLeading(15);
			document.add(objectif);

			document.add(new Paragraph("\n"));

			Paragraph objet = new Paragraph("Objet de la prestation offerte :", moyen);
			document.add(objet);

			Paragraph objet1 = new Paragraph("-> " + prestation.getTache().getLibelle(), petit);
			objet1.setFirstLineIndent(50);
			document.add(objet1);

			document.add(new Paragraph("\n"));

			Paragraph cond = new Paragraph("Conditions d’exécution :", moyen);
			document.add(cond);
			Paragraph cond1 = new Paragraph("\n");
			Phrase cond11 = new Phrase("La prestation est conclue pour ", petit);
			Phrase cond12 = new Phrase(
					"une durée hebdomadaire moyenne de : " + prestation.getHeuresminimois_pres() + " h", petitgras);
			cond1.add(cond11);
			cond1.add(cond12);
			document.add(cond1);

			document.add(new Paragraph("\n"));

			Paragraph engage = new Paragraph(new Phrase(
					"Aux vus de la législation limitant les heures complémentaires des salariés à temps partiels, il est impératif de nous signaler toutes évolutions (augmentation ou diminution) de la durée hebdomadaire horaire de la prestation.",
					mini));
			engage.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(engage);
			document.add(new Paragraph("\n"));
			Paragraph salarie = new Paragraph("Le salarié(e) mis(e) à disposition sera : " + civil + " "
					+ identite.getPrenom_IDE() + " " + identite.getNom_IDE(), petitgras);
			document.add(salarie);

			Paragraph arret = new Paragraph(new Phrase(
					" \nEn cas d’arrêt de travail définitif de la part de la salariée mise à disposition, l’association "
							+ org.getRs()
							+ " s’engage à faire intervenir une nouvelle salariée à votre domicile dans les mêmes conditions que stipule le contrat.",
					petit));
			arret.setAlignment(Element.ALIGN_JUSTIFIED);
			arret.setLeading(15);
			document.add(arret);
			document.newPage();
			document.add(new Paragraph("\n"));

			Paragraph serv = new Paragraph("Organisation et fonctionnement du service : ", moyen);
			document.add(serv);

			document.add(new Paragraph("\n"));

			Paragraph serv1 = new Paragraph("Jour d’intervention : ", moyen2);
			document.add(serv1);
			document.add(new Paragraph("\n"));
			Paragraph serv2 = new Paragraph("Les interventions seront assurées :\n", petit);
			Paragraph serv3 = new Paragraph(" Du Lundi au Samedi de 7h à 20h excepté les jours fériés.\n", petit);
			serv3.setIndentationLeft(40);

			Paragraph serv4 = new Paragraph(
					"Tout retard de la part du salarié(e) et excédent 15 minutes devra être rattrapé.", petit);
			Paragraph serv5 = new Paragraph(
					"Toute modification de la durée hebdomadaire, du jour d'intervention,"
							+ " même ponctuelle, devra faire l'objet d'une information préalable à nos services (mail, courrier,...)",
					petitgras);
			document.add(serv2);
			document.add(serv3);
			document.add(serv4);
			document.add(new Paragraph("\n"));
			document.add(serv5);

			document.add(new Paragraph("\n"));
			Paragraph presence = new Paragraph("Feuille de présence : ", moyen2);
			document.add(presence);
			document.add(new Paragraph("\n"));
			Paragraph presence1 = new Paragraph(
					"A la fin de chaque mois, le salarié(e) présente au bénéficiaire sa fiche de relevé horaire afin qu'il"
							+ " puisse la signer en toute connaissance de cause. Le salarié(e) ne doit en aucun cas la signer à la place de l'utilisateur.",
					petit);
			Paragraph presence2 = new Paragraph("\n");
			Phrase presence4 = new Phrase(
					"Les feuilles de relevés horaires sont de la responsabilité conjointe du particulier et du salarié et devront être remises à l’association en fin de chaque mois et dans tous les cas",
					petit);
			Phrase presence5 = new Phrase(" avant le 02 ou le 09 du mois suivant.", petitgrasrouge);
			presence1.setAlignment(Element.ALIGN_JUSTIFIED);
			presence2.setAlignment(Element.ALIGN_JUSTIFIED);
			presence2.add(presence4);
			presence2.add(presence5);
			document.add(presence1);
			document.add(presence2);

			document.add(new Paragraph("\n"));
			Paragraph conges = new Paragraph("Congés annuels : ", moyen2);
			document.add(conges);
			Paragraph conges1 = new Paragraph();
			Phrase conges2 = new Phrase("Les congés annuels seront pris par le salarié(e) après validation "
					+ "par nos soins. Une information vous sera communiquée ", petit);
			Phrase conges3 = new Phrase("15 jours avant; ", petitgras);
			Phrase conges4 = new Phrase("il sera possible de vous assurer une continuité de la "
					+ "prestation pendant cette période avec un nouveau salarié(e) mis(e) à disposition "
					+ "(en remplacement).", petit);
			conges1.add(conges2);
			conges1.add(conges3);
			conges1.add(conges4);
			conges1.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(new Paragraph("\n"));
			Paragraph conges5 = new Paragraph();
			Phrase conges6 = new Phrase(
					"De plus, les congés annuels du salarié et les absences et/ou congés cumulés du bénéficiaire du service ne pourront excéder ",
					petit);
			Phrase conges7 = new Phrase("5 semaines annuellement ", petitgras);
			Phrase conges8 = new Phrase(
					" et non facturables. Une information devra impérativement être faite à l’association ", petit);
			Phrase conges9 = new Phrase(" au moins 2 semaines au préalable. ", petitgras);
			conges5.add(conges6);
			conges5.add(conges7);
			conges5.add(conges8);
			conges5.add(conges9);
			conges5.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(conges1);
			document.add(new Paragraph("\n"));
			document.add(conges5);
			Paragraph conges10 = new Paragraph(
					"Sans information de votre part, le minimum d'heures contractuel sera facturé.", petit);
			document.add(new Paragraph("\n"));
			document.add(conges10);
			document.newPage();
			document.add(new Paragraph("\n"));
			Paragraph obligation = new Paragraph("Obligations des utilisateurs : ", moyen2);
			document.add(obligation);
			document.add(new Paragraph("\n"));
			Paragraph obligation1 = new Paragraph("- L'utilisateur met à disposition de l'employé(e) de ménage :",
					petit);
			Paragraph obligation2 = new Paragraph();
			Phrase obligation3 = new Phrase(
					"Produits ménagers et matériels nécessaires à l'entretien du domicile :", petitgras);
			Phrase obligation4 = new Phrase(
					" produits pour les sols, la cuisine, les WC (dont produit désinfectant), aspirateur et/ou balai, chiffons et éponges, serpillière, gants...",
					petit);
			obligation2.add(obligation3);
			obligation2.add(obligation4);
			obligation2.setAlignment(Element.ALIGN_JUSTIFIED);
			obligation2.setIndentationLeft(40);
			document.add(obligation1);
			document.add(obligation2);
			Phrase obligation5 = new Phrase(
					"En aucun cas, l'aide à domicile ne doit utiliser du matériel et des produits lui appartenant.",
					petititalic);
			document.add(obligation5);
			document.add(new Paragraph("\n"));
			Paragraph obligation6 = new Paragraph(new Phrase(
					"- L’utilisateur s’engage à ne demander à l’employé(e) de ménage que les tâches qui ont été acceptées lors de la signature du contrat de prestations.",
					petit));
			obligation6.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(obligation6);

			document.add(new Paragraph("\n"));
			Paragraph execution = new Paragraph("Exécution du travail : ", moyen2);
			document.add(execution);
			document.add(new Paragraph("\n"));
			Paragraph execution1 = new Paragraph(new Phrase(
					"Dans l’exécution des tâches qui leur sont confiées, les salariés doivent se conformer aux directives qui leur sont données par l’utilisateur.",
					petit));
			execution1.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(execution1);
			document.add(new Paragraph("\n"));
			Paragraph execution2 = new Paragraph("Tâches ne pouvant être effectuées :  ", petitsouligne);
			document.add(execution2);
			Paragraph execution3 = new Paragraph(new Phrase(
					"- Déplacer du mobilier lourd (armoires…)\n- Travaux de peinture et de tapisserie\n"
							+ "- Lavage des vitres s’il y a danger pour la sécurité de l’employé(e) de ménage.\n"
							+ "- Toutes tâches nécessitant de monter plus haut qu’un escabeau (échelle…). ",
					petit));
			execution3.setIndentationLeft(40);
			execution3.setLeading(20);
			document.add(execution3);
			document.add(new Paragraph("\n"));
			Paragraph execution4 = new Paragraph(new Phrase(
					"Afin d’assurer un service de qualité, des contrôles pourront être effectués "
							+ " pour attester du respect des règles d’exécution de travail, des horaires de travail ainsi que la durée.",
					petititalic));
			execution4.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(execution4);

			document.add(new Paragraph("\n"));
			Paragraph facture = new Paragraph("Condition de facturation : ", moyen2);
			document.add(facture);
			document.add(new Paragraph("\n"));
			Paragraph facture1 = new Paragraph(new Phrase(
					"Dans le cas d’une suspension  de la prestation à l’initiative de l’utilisateur, pour quelconque raison, le minimum d’heures contractuel sera facturé.",
					petitgras));
			facture1.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(facture1);
			Paragraph facture2 = new Paragraph(new Phrase("Excepté lors :", petit));
			Paragraph facture3 = new Paragraph(
					new Phrase("- des 5 semaines de congés et absences annuelles autorisées.", petit));
			facture3.setIndentationLeft(40);
			document.add(facture2);
			document.add(facture3);
			document.add(new Paragraph("\n"));
			Paragraph facture4 = new Paragraph();
			Phrase facture5 = new Phrase("La prestation sera facturée mensuellement ", petitgras);
			Phrase facture6 = new Phrase(
					"sur la base du nombre d’heures réellement réalisées après avoir été validée par vos soins et par le salarié mis à disposition. ",
					petit);
			facture4.add(facture5);
			facture4.add(facture6);
			facture4.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(facture4);

			document.add(new Paragraph("\n"));
			document.newPage();
			Paragraph facturep = new Paragraph("Facturation de la prestation : ", moyen2);
			document.add(facturep);
			document.add(new Paragraph("\n"));
			Paragraph facturep1 = new Paragraph(
					"La facturation horaire sera effectuée mensuellement sur une base de : "
							+ prestation.getFacsalairehor_pr() + " € (pour un salaire de "
							+ prestation.getSalairehor_av()
							+ " € brut/h). Une réévaluation sera effectuée lors de chaque augmentation du tarif du SMIC. ",
					petitgras);
			facturep1.setAlignment(Element.ALIGN_JUSTIFIED);

			document.add(facturep1);
			if (!rajoutpaniers.equals(""))
				document.add(new Paragraph(new Phrase(rajoutpaniers)));
			Paragraph facturep2 = new Paragraph("Le tarif horaire comprend : ", petit);
			Paragraph facturep3 = new Paragraph(
					"- Salaire, charges, congés payés,\n- Frais administratif (contrat, bulletin de salaire, coût des visites médiales…)",
					petit);
			facturep3.setIndentationLeft(40);
			document.add(facturep2);
			document.add(facturep3);
			document.add(new Paragraph("\n"));
			Paragraph facturep4 = new Paragraph(
					"Le 1er mai, seul jour chômé et payé, vous sera facturé dans le cas où la prestation a lieu ce jour-là.\n"
							+ "Le paiement s’effectuera par chèque, dès réception de la facture.\n"
							+ "Toute facture impayée  entraînera de notre part la suspension de nos prestations après mise en demeure par lettre recommandée.\n"
							+ "A la fin de l’année une attestation fiscale vous sera délivrée.\n ",
					petit);
			facturep4.setLeading(25);
			document.add(facturep4);
			Paragraph facturep5 = new Paragraph(
					"Réduction ou crédit d’impôt sur le revenu correspondant à 50%  des sommes versées pour le paiement"
							+ " de services à la personne. Cette réduction ou crédit d’impôt est accordé à chaque foyer fiscal"
							+ " dans la limite d’un plafond de 12 000 euros par an (15 000€ pour une famille avec minimum 2 "
							+ "enfants ou 13 500 € avec un enfant unique et 20 000€ pour les personnes dépendantes).",
					petititalic);
			facturep5.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(facturep5);
			document.add(new Paragraph("\n"));
			Paragraph facturep6 = new Paragraph(
					"Activités ouvrant droit à avantage fiscal sous conditions de plafond "
							+ "de l’assiette des prestations :",
					petitsouligne);
			facturep6.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(facturep6);
			Paragraph facturep7 = new Paragraph("- Petits travaux de jardinage : 3000 €/an/foyer fiscal\n"
					+ "- Prestations de petit bricolage dites  ' hommes toutes mains ' " + " : "
					+ " 500 €/an/foyer fiscal", petit);
			//facturep7.setAlignment(Element.ALIGN_JUSTIFIED);
			facturep7.setIndentationLeft(40);
			document.add(facturep7);

			document.add(new Paragraph("\n"));
			Paragraph assurance = new Paragraph("Assurances : ", moyen2);
			document.add(assurance);
			document.add(new Paragraph("\n"));
			Paragraph assurance1 = new Paragraph(
					"L’association intermédiaire CAP EMPLOI a souscrit un contrat Responsabilité "
							+ "Civile dans le cadre de cette activité Service  à la Personne qui couvre tout "
							+ "dommage causé par le salarié à l’occasion de l’exécution de ses missions. ",
					petit);
			Paragraph assurance2 = new Paragraph(
					"Lors de tout accident survenant chez l’utilisateur tels que : objets brisés,"
							+ " appareils cassés…, il est demandé de faire sur place une déclaration très simple portant sur :  ",
					petit);
			assurance1.setAlignment(Element.ALIGN_JUSTIFIED);
			assurance2.setAlignment(Element.ALIGN_JUSTIFIED);
			Paragraph assurance3 = new Paragraph("- La nature\n- La date et l’ heure\n- Les circonstances", petit);
			assurance3.setIndentationLeft(50);
			document.add(assurance1);
			document.add(assurance2);
			document.add(assurance3);
			Paragraph assurance4 = new Paragraph("Cette déclaration devra être signée par l’utilisateur"
					+ " et le salarié et déposée le jour même à " + org.getRs() + ".", petit);
			assurance4.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(assurance4);
			document.add(new Paragraph("\n"));
			document.newPage();
			Paragraph duree = new Paragraph("Durée : ", moyen2);
			document.add(duree);
			document.add(new Paragraph("\n"));
			Paragraph duree1 = new Paragraph();
			Phrase duree2 = new Phrase("1. ", petit);
			Phrase duree2b = new Phrase(" Le présent contrat est conclu pour la période du ", petit);
			Phrase duree3 = new Phrase(sdf.format(prestation.getDatedebut_pr()) + " au "
					+ sdf.format(prestation.getDatefin_pr()) + ".", petitgras);
			duree1.add(duree2);
			duree1.add(duree2b);
			duree1.add(duree3);
			Paragraph duree4 = new Paragraph(
					"2. Pour tous les nouveaux utilisateurs de " + org.getRs()
							+ ", l’engagement ne deviendra effectif qu’à l’expiration d’une période d’essai de 15 jours.",
					petit);
			Paragraph duree5 = new Paragraph(
					"3.  A l’expiration de la période ci-dessus, le présent "
							+ "contrat se renouvellera, par tacite reconduction, pour la même durée que le contrat initial, "
							+ "sauf résiliation par l’une des parties selon notification adressée à l’autre"
							+ " partie par lettre recommandée avec demande d’avis de réception :"
							+ "\n  - Pour les contrats d'une durée d'un an et plus: 2 mois avant la date d'expiration du contrat"
							+ "\n  - Pour les contrats d'une durée de 6 à 12 mois: 1 mois avant la date d'expiration du contrat"
							+ "\n  - Pour les contrats d'une durée de moins de 6 mois: 15 jours avant la date d'expiration du contrat",
					petit);

			Paragraph duree6 = new Paragraph("Rupture de contrat :", moyen2);
			Paragraph duree7 = new Paragraph(
					"Le contrat peut être rompu avant la date de fin dans les cas suivants uniquement, sur présentation d'un justificatif :",
					petit);
			Paragraph duree8 = new Paragraph(
					"- départ en maison de retraite, \n- perte d’emploi,\n- décès, \n - déménagement", petit);
			duree8.setIndentationLeft(50);
			document.add(duree1);
			document.add(new Paragraph(""));
			document.add(duree4);
			document.add(new Paragraph(""));
			document.add(duree5);
			document.add(new Paragraph("\n"));
			document.add(new Paragraph(""));
			document.add(duree6);
			document.add(new Paragraph("\n"));
			document.add(duree7);
			document.add(duree8);

			document.add(new Paragraph("\n"));
			Paragraph litiges = new Paragraph("Litiges : ", moyen2);
			document.add(litiges);
			document.add(new Paragraph("\n"));
			Paragraph litiges1 = new Paragraph("Tout litige devra être signalé par écrit à l’association."
					+ " En cas d’impossibilité de trouver une solution amiable, le Tribunal du lieu de "
					+ "Juridiction dont dépend le siège de l’association sera seul compétent.", petit);
			litiges1.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(litiges1);
			document.add(new Paragraph("\n"));
			document.add(new Paragraph("Fait à Valence d'Agen en double exemplaire,\n", petit));
			document.add(new Paragraph("Le " + sdf.format(prestation.getRedaction_pr()), petit));
			document.add(new Paragraph("\n"));
			//creation d'un tableau
			PdfPTable tableau = new PdfPTable(2);
			tableau.setWidthPercentage(100);//largeur du tableau
			//nombre de colonnes et taille de chacune en proportion
			float[] largeur = { 1, 1 };
			tableau.setWidths(largeur);
			//creation d'une cellule du tableau
			PdfPCell cell1 = new PdfPCell(new Phrase("Le particulier Utilisateur", petit));
			cell1.setBorder(Rectangle.NO_BORDER);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell2 = new PdfPCell(new Phrase(org.getRs(), petit));
			cell2.setBorder(Rectangle.NO_BORDER);
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableau.addCell(cell1);
			tableau.addCell(cell2);
			PdfPCell cel3 = new PdfPCell(new Phrase(employeur.getRs_employeur(), petit));
			cel3.setBorder(Rectangle.NO_BORDER);
			cel3.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cel4 = new PdfPCell(new Phrase("Le Directeur", petit));
			cel4.setBorder(Rectangle.NO_BORDER);
			cel4.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cel5 = new PdfPCell(new Phrase("", petit));
			cel5.setBorder(Rectangle.NO_BORDER);
			cel5.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cel6 = new PdfPCell(new Phrase(org.getDirecteur(), petit));
			cel6.setBorder(Rectangle.NO_BORDER);
			cel6.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell cel7 = new PdfPCell(new Phrase("(signature)", policecontrat));
			PdfPCell cel8 = new PdfPCell(new Phrase("(signature)", policecontrat));
			cel7.setBorder(Rectangle.NO_BORDER);
			cel7.setHorizontalAlignment(Element.ALIGN_CENTER);
			cel8.setBorder(Rectangle.NO_BORDER);
			cel8.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableau.addCell(cel3);
			tableau.addCell(cel4);
			tableau.addCell(cel5);
			tableau.addCell(cel6);
			tableau.addCell(cel7);
			tableau.addCell(cel8);
			document.add(tableau);
			document.add(new Paragraph("\n"));

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