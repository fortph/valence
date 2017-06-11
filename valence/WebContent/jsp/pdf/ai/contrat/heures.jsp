<%@ page
	import="dao.imp.identite.*,beans.parametres.accueil.*, beans.identite.*,dao.imp.ai.*,beans.ai.*,
	java.util.*,beans.employeurs.*,dao.imp.employeur.*,divers.*,com.itextpdf.text.*,
	java.io.*,com.itextpdf.text.pdf.*,java.net.*,java.text.*,beans.smic.*"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%
	String numerocontrat = request.getParameter("contrat");
	String an = request.getParameter("an");
	String mois = request.getParameter("mois");
	
	
	/*on recupere le mois correspondant */
	int dmois = 0;
	if (mois.equals("janvier"))
		dmois = 1;
	else if (mois.equals("février"))
		dmois = 2;
	else if (mois.equals("mars"))
		dmois = 3;
	else if (mois.equals("avril"))
		dmois = 4;
	if (mois.equals("mai"))
		dmois = 5;
	else if (mois.equals("juin"))
		dmois = 6;
	else if (mois.equals("juillet"))
		dmois = 7;
	else if (mois.equals("août"))
		dmois = 8;
	if (mois.equals("septembre"))
		dmois = 9;
	else if (mois.equals("octobre"))
		dmois = 10;
	else if (mois.equals("novembre"))
		dmois = 11;
	else if (mois.equals("décembre"))
		dmois = 12;
	
	
	Calendar cal = Calendar.getInstance();
	cal.set(Calendar.YEAR, Integer.parseInt(an));
	cal.set(Calendar.MONTH, dmois - 1);
	cal.set(Calendar.DAY_OF_MONTH, 1);
	Date debutmois = cal.getTime();
	System.out.println("premier jour = " + cal.getTime());
	//position du jour de la semaine: lundi=1, mardi=1....
	Calendar moisencours = Calendar.getInstance();
	//on affecte les mois et annees recuperees a une date
	moisencours.setTime(debutmois);
	int nojour = moisencours.get(Calendar.DAY_OF_WEEK);
	nojour -= 1;
	if (nojour == 0)
		nojour = 7;

	int debutcal = 1;
	int nombrejourmois = moisencours
			.getActualMaximum(Calendar.DAY_OF_MONTH);

	//System.out.println("premier jour= " + nojour);
	//System.out.println("dernier jour= " + nombrejourmois);

	//on recupere le nom du jour correspondant au jour de la semaine
	//String nojour=joursSemaine[premierjourmois];
	String[] jrs = new String[43];
	//affichage du calendrier
	for (int semaine = 0; semaine < 43; semaine++) {
		if (semaine < 7) {
			if (nojour <= semaine) {
				jrs[semaine] = Integer.valueOf(debutcal).toString();
				debutcal++;
			}

			else
				jrs[semaine] = " ";

		} else {
			if (debutcal <= nombrejourmois)
				jrs[semaine] = Integer.valueOf(debutcal).toString();
			else
				jrs[semaine] = " ";
			debutcal++;
		}
	}
	debutcal = 1;
	//System.out.println("ma date tableau= " + nojour);
	ContratDAO contdao = new ContratDAO();

	Contrat contrat = contdao.findByID(Integer.parseInt(numerocontrat));
	
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
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		Document document = new Document(PageSize.A4, 20, 20, 00, 20);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		//precise le nombre de decimales des nombres reels
		DecimalFormat decimalformat=new DecimalFormat("0.00");
		
		String nb = "Nb Heures";
		String dat = "Date";
		String tot = "Tot";

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
		Font mini = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);
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
		//ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		try {
			//le flux sera stocké en mémoire et non pas dans un fichier
			PdfWriter.getInstance(document, buffer);
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

			PdfPTable contrattravail = new PdfPTable(1);
			contrattravail.setWidthPercentage(100);//largeur du tableau
			//nombre de colonnes et taille de chacune en proportion
			float[] largeurcontrat = { 1 };
			contrattravail.setWidths(largeurcontrat);

			PdfPCell cel9 = new PdfPCell(new Phrase("RELEVE HORAIRE - "
					+ mois + " " + an + "\n", policecontrat));
			/* on declare plusieurs bordures en precisant le total de chacun d'elles
			avec Rectangle.BOTTOM=2,Rectangle.TOP=1,Rectangle.RIGHT=8,Rectangle.LEFT=4
			 */
			cel9.setBorder(15);
			cel9.setBackgroundColor(new BaseColor(210, 210, 210));
			cel9.setHorizontalAlignment(Element.ALIGN_CENTER);
			cel9.setFixedHeight(20);

			contrattravail.addCell(cel9);

			document.add(contrattravail);

			Paragraph dispo = new Paragraph(
					new Phrase(
							"(conclu conformément à la loi 87.39 du 27 janvier  et du décrêt n° 87 du 30 avril 1987)",
							normal));
			dispo.setAlignment(Element.ALIGN_CENTER);
			document.add(dispo);
			document.add(new Phrase("\n", minixx));

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
			cel01.setBackgroundColor(new BaseColor(168, 196, 204));
			cel01.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cel02 = new PdfPCell(new Phrase("", normal));
			cel02.setBorder(Rectangle.NO_BORDER);
			PdfPCell cel03 = new PdfPCell(new Phrase("UTILISATEUR", normal));
			cel03.setColspan(2);
			cel03.setBackgroundColor(new BaseColor(168, 196, 204));
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
			PdfPCell cel041 = new PdfPCell(new Phrase(" ", normal));
			// cel041.setColspan(1);
			PdfPCell cel042 = new PdfPCell(new Phrase(" ", normal));
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
			PdfPCell cel061 = new PdfPCell(new Phrase("  ", normal));
			// cel061.setColspan(1);
			cel061.setBorder(6);
			PdfPCell cel062 = new PdfPCell(new Phrase(" ", normal));
			// cel062.setColspan(1);
			cel062.setBorder(10);
			table2.addCell(cel061);
			table2.addCell(cel062);

			PdfPCell cel063 = new PdfPCell(new Phrase("", normal));
			// cel063.setColspan(1);
			cel063.setBorder(Rectangle.NO_BORDER);
			table2.addCell(cel063);

			PdfPCell cel064 = new PdfPCell(new Phrase(" Responsable : "
					+ employeur.getCivresp() + " "
					+ employeur.getPrenomresponsable() + " "
					+ employeur.getNomresponsable(), normal));
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

			document.add(new Paragraph("\n"));

			PdfPTable mission = new PdfPTable(1);
			mission.setWidthPercentage(100);//largeur du tableau
			//nombre de colonnes et taille de chacune en proportion
			float[] largeurmission = { 1 };
			mission.setWidths(largeurmission);

			PdfPCell mis1 = new PdfPCell(new Phrase(
					"Définition de la mission\n", policecontrat));
			/* on declare plusieurs bordures en precisant le total de chacun d'elles
			avec Rectangle.BOTTOM=2,Rectangle.TOP=1,Rectangle.RIGHT=8,Rectangle.LEFT=4
			 */
			mis1.setBorder(15);
			mis1.setBackgroundColor(new BaseColor(210, 210, 210));
			mis1.setHorizontalAlignment(Element.ALIGN_CENTER);
			mis1.setFixedHeight(20);

			mission.addCell(mis1);

			document.add(mission);

			//creation d'un tableau
			PdfPTable qualite = new PdfPTable(3);
			qualite.setWidthPercentage(100);//largeur du tableau
			//nombre de colonnes et taille de chacune en proportion
			float[] largeurqualite = { 9, 1, 9 };
			qualite.setWidths(largeurqualite);
			//creation d'une cellule du tableau
			PdfPCell qualite1 = new PdfPCell(new Phrase(
					" Lieu d\'exécution :  " + contrat.getLieu(),
					normalgras2));
			qualite1.setColspan(2);
			qualite1.setBorder(Rectangle.NO_BORDER);
			qualite1.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell qual3 = new PdfPCell(new Phrase(
					" Date de mise à disposition : "
							+ sdf.format(contrat.getDebutcontrat()), normalgras2));

			qual3.setBorder(Rectangle.NO_BORDER);
			qualite.addCell(qualite1);

			qualite.addCell(qual3);
			PdfPCell qualite4 = new PdfPCell(
					new Phrase(" Poste occupé :  "
							+ contrat.getRome().getIntitule(), normalgras2));
			qualite4.setColspan(2);
			qualite4.setBorder(Rectangle.NO_BORDER);
			qualite4.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell qual5 = new PdfPCell(new Phrase(" Total du mois : ",
					normalgras2));

			qual5.setBorder(Rectangle.NO_BORDER);
			qualite.addCell(qualite4);

			qualite.addCell(qual5);
			document.add(qualite);

			document.add(new Phrase("\n", minixx));
			document.add(new Phrase("\n", minixx));

			/************ creation d'un tableau duree contrat  *************************************/
			PdfPTable dateddebfin = new PdfPTable(13);
			dateddebfin.setWidthPercentage(100);//largeur du tableau
			//nombre de colonnes et taille de chacune en proportion
			float[] largeurqualitedates = { 1, 1, 2, 1, 2, 1, 2, 1, 2, 1,
					2, 1, 2 };
			dateddebfin.setWidths(largeurqualitedates);
			dateddebfin.getDefaultCell().setBorder(14);
			dateddebfin.getDefaultCell().setFixedHeight(20);
			dateddebfin.getDefaultCell().setHorizontalAlignment(
					Element.ALIGN_CENTER);
			dateddebfin.getDefaultCell().setVerticalAlignment(
					Element.ALIGN_CENTER);
			PdfPCell datedeb = new PdfPCell(new Phrase(" ", normal));
			datedeb.setBorder(Rectangle.NO_BORDER);

			dateddebfin.addCell(datedeb);

			PdfPCell datedeb1 = new PdfPCell(new Phrase(dat, normal));
			dateddebfin.addCell(datedeb1);

			PdfPCell datedeb2 = new PdfPCell(new Phrase(nb, normal));
			//datedeb2.setBorder(15);
			dateddebfin.addCell(datedeb2);

			PdfPCell datedeb3 = new PdfPCell(new Phrase(dat, normal));
			dateddebfin.addCell(datedeb3);

			PdfPCell datedeb4 = new PdfPCell(new Phrase(nb, normal));
			dateddebfin.addCell(datedeb4);

			PdfPCell datedeb5 = new PdfPCell(new Phrase(dat, normal));
			dateddebfin.addCell(datedeb5);

			PdfPCell datedeb6 = new PdfPCell(new Phrase(nb, normal));
			dateddebfin.addCell(datedeb6);

			PdfPCell datedeb7 = new PdfPCell(new Phrase(dat, normal));
			dateddebfin.addCell(datedeb7);

			PdfPCell datedeb8 = new PdfPCell(new Phrase(nb, normal));
			dateddebfin.addCell(datedeb8);

			PdfPCell datedeb9 = new PdfPCell(new Phrase(dat, normal));
			dateddebfin.addCell(datedeb9);

			PdfPCell datedeb10 = new PdfPCell(new Phrase(nb, normal));
			dateddebfin.addCell(datedeb10);

			PdfPCell datedeb11 = new PdfPCell(new Phrase(dat, normal));
			dateddebfin.addCell(datedeb11);

			PdfPCell datedeb12 = new PdfPCell(new Phrase(nb, normal));
			dateddebfin.addCell(datedeb12);

			
			//lundi

			PdfPCell datelundi = new PdfPCell(new Phrase("Lun", normal));
			//datelundi.setBorder(15);
			datelundi.setPadding(5);
			datelundi.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datelundi);

			PdfPCell datelundi1 = new PdfPCell(new Phrase(jrs[1],
					normal));
			//datelundi1.setBorder(15);
			datelundi1.setHorizontalAlignment(Element.ALIGN_CENTER);
			//datelundi1.setVerticalAlignment(Element.ALIGN_BOTTOM);
			dateddebfin.addCell(datelundi1);

			PdfPCell datelundi2 = new PdfPCell(new Phrase("", normal));
			//datelundi2.setBorder(15);
			dateddebfin.addCell(datelundi2);

			PdfPCell datelundi3 = new PdfPCell(new Phrase(jrs[8],
					normal));
			//	datelundi3.setBorder(15);
			datelundi3.setHorizontalAlignment(Element.ALIGN_CENTER);
			//datelundi3.setVerticalAlignment(Element.ALIGN_BOTTOM);
			dateddebfin.addCell(datelundi3);

			PdfPCell datelundi4 = new PdfPCell(new Phrase("", normal));
			dateddebfin.addCell(datelundi4);

			PdfPCell datelundi5 = new PdfPCell(new Phrase(jrs[15],
					normal));
			//	datelundi5.setBorder(15);
				datelundi5.setHorizontalAlignment(Element.ALIGN_CENTER);
			//	datelundi5.setVerticalAlignment(Element.ALIGN_BOTTOM);
			dateddebfin.addCell(datelundi5);

			PdfPCell datelundi6 = new PdfPCell(new Phrase("", normal));
			dateddebfin.addCell(datelundi6);

			PdfPCell datelundi7 = new PdfPCell(new Phrase(jrs[22],
					normal));
			//datelundi7.setBorder(15);
			datelundi7.setHorizontalAlignment(Element.ALIGN_CENTER);
			//	datelundi7.setVerticalAlignment(Element.ALIGN_BOTTOM);
			dateddebfin.addCell(datelundi7);

			PdfPCell datelundi8 = new PdfPCell(new Phrase("", normal));
			dateddebfin.addCell(datelundi8);

			PdfPCell datelundi9 = new PdfPCell(new Phrase(jrs[29],
					normal));
			//datelundi9.setBorder(15);
			datelundi9.setHorizontalAlignment(Element.ALIGN_CENTER);
			//datelundi9.setVerticalAlignment(Element.ALIGN_BOTTOM);
			dateddebfin.addCell(datelundi9);

			PdfPCell datelundi10 = new PdfPCell(new Phrase("", normal));
			dateddebfin.addCell(datelundi10);

			PdfPCell datelundi11 = new PdfPCell(new Phrase(jrs[36],
					normal));
			//datelundi11.setBorder(15);
			datelundi11.setHorizontalAlignment(Element.ALIGN_CENTER);
			//datelundi11.setVerticalAlignment(Element.ALIGN_BOTTOM);
			dateddebfin.addCell(datelundi11);

			PdfPCell datelundi12 = new PdfPCell(new Phrase("", normal));
			dateddebfin.addCell(datelundi12);

			//mardi

			PdfPCell datemardi = new PdfPCell(new Phrase("Mar", normal));
			datemardi.setPadding(5);
			datemardi.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datemardi);

			PdfPCell datemardi1 = new PdfPCell(new Phrase(jrs[2],
					normal));
			datemardi1.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datemardi1);

			PdfPCell datemardi2 = new PdfPCell(new Phrase("", normal));
			datemardi2.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datemardi2);

			PdfPCell datemardi3 = new PdfPCell(new Phrase(jrs[9],
					normal));
			datemardi3.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datemardi3);

			PdfPCell datemardi4 = new PdfPCell(new Phrase("", normal));
			datemardi4.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datemardi4);

			PdfPCell datemardi5 = new PdfPCell(new Phrase(jrs[16],
					normal));
			datemardi5.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datemardi5);

			PdfPCell datemardi6 = new PdfPCell(new Phrase("", normal));
			datemardi6.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datemardi6);

			PdfPCell datemardi7 = new PdfPCell(new Phrase(jrs[23],
					normal));
			datemardi7.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datemardi7);

			PdfPCell datemardi8 = new PdfPCell(new Phrase("", normal));
			datemardi8.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datemardi8);

			PdfPCell datemardi9 = new PdfPCell(new Phrase(jrs[30],
					normal));
			datemardi9.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datemardi9);

			PdfPCell datemardi10 = new PdfPCell(new Phrase("", normal));
			datemardi10.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datemardi10);

			PdfPCell datemardi11 = new PdfPCell(new Phrase(jrs[37],
					normal));
			datemardi11.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datemardi11);

			PdfPCell datemardi12 = new PdfPCell(new Phrase("", normal));
			datemardi12.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datemardi12);

			//mercredi

			PdfPCell datemer = new PdfPCell(new Phrase("Mer", normal));
			datemer.setPadding(5);
			datemer.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datemer);

			PdfPCell datemer1 = new PdfPCell(new Phrase(jrs[3], normal));
			datemer1.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datemer1);

			PdfPCell datemer2 = new PdfPCell(new Phrase("", normal));
			datemer2.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datemer2);

			PdfPCell datemer3 = new PdfPCell(
					new Phrase(jrs[10], normal));
			datemer3.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datemer3);

			PdfPCell datemer4 = new PdfPCell(new Phrase("", normal));
			datemer4.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datemer4);

			PdfPCell datemer5 = new PdfPCell(
					new Phrase(jrs[17], normal));
			datemer5.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datemer5);

			PdfPCell datemer6 = new PdfPCell(new Phrase("", normal));
			datemer6.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datemer6);

			PdfPCell datemer7 = new PdfPCell(
					new Phrase(jrs[24], normal));
			datemer7.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datemer7);

			PdfPCell datemer8 = new PdfPCell(new Phrase("", normal));
			datemer8.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datemer8);

			PdfPCell datemer9 = new PdfPCell(
					new Phrase(jrs[31], normal));
			datemer9.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datemer9);

			PdfPCell datemer10 = new PdfPCell(new Phrase("", normal));
			datemer10.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datemer10);

			PdfPCell datemer11 = new PdfPCell(new Phrase(jrs[38],
					normal));
			datemer11.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datemer11);

			PdfPCell datemer12 = new PdfPCell(new Phrase("", normal));
			datemer12.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datemer12);

			//jeudi

			PdfPCell datejeu = new PdfPCell(new Phrase("Jeu", normal));
			datejeu.setPadding(5);
			datejeu.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datejeu);

			PdfPCell datejeu1 = new PdfPCell(new Phrase(jrs[4], normal));
			datejeu1.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datejeu1);

			PdfPCell datejeu2 = new PdfPCell(new Phrase("", normal));
			datejeu2.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datejeu2);

			PdfPCell datejeu3 = new PdfPCell(
					new Phrase(jrs[11], normal));
			datejeu3.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datejeu3);

			PdfPCell datejeu4 = new PdfPCell(new Phrase("", normal));
			datejeu4.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datejeu4);

			PdfPCell datejeu5 = new PdfPCell(
					new Phrase(jrs[18], normal));
			datejeu5.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datejeu5);

			PdfPCell datejeu6 = new PdfPCell(new Phrase("", normal));
			datejeu6.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datejeu6);

			PdfPCell datejeu7 = new PdfPCell(
					new Phrase(jrs[25], normal));
			datejeu7.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datejeu7);

			PdfPCell datejeu8 = new PdfPCell(new Phrase("", normal));
			datejeu8.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datejeu8);

			PdfPCell datejeu9 = new PdfPCell(
					new Phrase(jrs[32], normal));
			datejeu9.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datejeu9);

			PdfPCell datejeu10 = new PdfPCell(new Phrase("", normal));
			datejeu10.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datejeu10);

			PdfPCell datejeu11 = new PdfPCell(new Phrase(jrs[39],
					normal));
			datejeu11.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datejeu11);

			PdfPCell datejeu12 = new PdfPCell(new Phrase("", normal));
			datejeu12.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datejeu12);

			//vendredi

			PdfPCell dateven = new PdfPCell(new Phrase("Ven", normal));
			dateven.setPadding(5);
			dateven.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(dateven);

			PdfPCell dateven1 = new PdfPCell(new Phrase(jrs[5], normal));
			dateven1.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(dateven1);

			PdfPCell dateven2 = new PdfPCell(new Phrase("", normal));
			dateven2.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(dateven2);

			PdfPCell dateven3 = new PdfPCell(
					new Phrase(jrs[12], normal));
			dateven3.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(dateven3);

			PdfPCell dateven4 = new PdfPCell(new Phrase("", normal));
			dateven4.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(dateven4);

			PdfPCell dateven5 = new PdfPCell(
					new Phrase(jrs[19], normal));
			dateven5.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(dateven5);

			PdfPCell dateven6 = new PdfPCell(new Phrase("", normal));
			dateven6.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(dateven6);

			PdfPCell dateven7 = new PdfPCell(
					new Phrase(jrs[26], normal));
			dateven7.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(dateven7);

			PdfPCell dateven8 = new PdfPCell(new Phrase("", normal));
			dateven8.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datejeu8);

			PdfPCell dateven9 = new PdfPCell(
					new Phrase(jrs[33], normal));
			dateven9.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(dateven9);

			PdfPCell dateven10 = new PdfPCell(new Phrase("", normal));
			dateven10.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(dateven10);

			PdfPCell dateven11 = new PdfPCell(new Phrase(jrs[40],
					normal));
			dateven11.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(dateven11);

			PdfPCell dateven12 = new PdfPCell(new Phrase("", normal));
			dateven12.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(dateven12);

			//samdedi

			PdfPCell datesam = new PdfPCell(new Phrase("Sam", normal));
			datesam.setPadding(5);
			datesam.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datesam);

			PdfPCell datesam1 = new PdfPCell(new Phrase(jrs[6], normal));
			datesam1.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datesam1);

			PdfPCell datesam2 = new PdfPCell(new Phrase("", normal));
			datesam2.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datesam2);

			PdfPCell datesam3 = new PdfPCell(
					new Phrase(jrs[13], normal));
			datesam3.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datesam3);

			PdfPCell datesam4 = new PdfPCell(new Phrase("", normal));
			datesam4.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datesam4);

			PdfPCell datesam5 = new PdfPCell(
					new Phrase(jrs[20], normal));
			datesam5.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datesam5);

			PdfPCell datesam6 = new PdfPCell(new Phrase("", normal));
			datesam6.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datesam6);

			PdfPCell datesam7 = new PdfPCell(
					new Phrase(jrs[27], normal));
			datesam7.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datesam7);

			PdfPCell datesam8 = new PdfPCell(new Phrase("", normal));
			datesam8.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datesam8);

			PdfPCell datesam9 = new PdfPCell(
					new Phrase(jrs[34], normal));
			datesam9.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datesam9);

			PdfPCell datesam10 = new PdfPCell(new Phrase("", normal));
			datesam10.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datesam10);

			PdfPCell datesam11 = new PdfPCell(new Phrase(jrs[41],
					normal));
			datesam11.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datesam11);

			PdfPCell datesam12 = new PdfPCell(new Phrase("", normal));
			datesam12.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datesam12);
			
			
			//dimanche

			PdfPCell datede = new PdfPCell(new Phrase("Dim", normal));
			//datede.setBorder(15);
			datede.setHorizontalAlignment(Element.ALIGN_CENTER);
			datede.setPadding(5);
			dateddebfin.addCell(datede);

			PdfPCell datede1 = new PdfPCell(new Phrase(jrs[7], normal));
			datede1.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datede1);

			PdfPCell datede2 = new PdfPCell(new Phrase("", normal));
			datede2.setHorizontalAlignment(Element.ALIGN_CENTER);
			//datede2.setBorder(15);
			dateddebfin.addCell(datede2);

			PdfPCell datede3 = new PdfPCell(new Phrase(jrs[14], normal));
			datede3.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datede3);

			PdfPCell datede4 = new PdfPCell(new Phrase("", normal));
			datede4.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datede4);

			PdfPCell datede5 = new PdfPCell(new Phrase(jrs[21], normal));
			datede5.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datede5);

			PdfPCell datede6 = new PdfPCell(new Phrase("", normal));
			datede6.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datede6);

			PdfPCell datede7 = new PdfPCell(new Phrase(jrs[28], normal));
			datede7.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datede7);

			PdfPCell datede8 = new PdfPCell(new Phrase("", normal));
			datede8.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datede8);

			PdfPCell datede9 = new PdfPCell(new Phrase(jrs[35], normal));
			datede9.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datede9);

			PdfPCell datede10 = new PdfPCell(new Phrase("", normal));
			datede10.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datede10);

			PdfPCell datede11 = new PdfPCell(
					new Phrase(jrs[42], normal));
			datede11.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datede11);

			PdfPCell datede12 = new PdfPCell(new Phrase("", normal));
			datede12.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datede12);


			//total

			PdfPCell datetotal = new PdfPCell(new Phrase(" ", normal));
			dateddebfin.addCell(datetotal);

			PdfPCell datetotal1 = new PdfPCell(new Phrase(tot, normalgras));
			//datedeb1.setBorder(15);
			datetotal1.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datetotal1);

			PdfPCell datetotal2 = new PdfPCell(new Phrase("", normalgras));
			//datedeb2.setBorder(15);
			dateddebfin.addCell(datetotal2);

			PdfPCell datetotal3 = new PdfPCell(new Phrase(tot, normalgras));
			//datedeb3.setBorder(15);
			datetotal3.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datetotal3);

			PdfPCell datetotal4 = new PdfPCell(new Phrase("", normalgras));
			dateddebfin.addCell(datetotal4);

			PdfPCell datetotal5 = new PdfPCell(new Phrase(tot, normalgras));
			//datedeb5.setBorder(15);
			datetotal5.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datetotal5);

			PdfPCell datetotal6 = new PdfPCell(new Phrase("", normalgras));
			dateddebfin.addCell(datetotal6);

			PdfPCell datetotal7 = new PdfPCell(new Phrase(tot, normalgras));
			datetotal7.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datetotal7);

			PdfPCell datetotal8 = new PdfPCell(new Phrase("", normalgras));
			dateddebfin.addCell(datetotal8);

			PdfPCell datetotal9 = new PdfPCell(new Phrase(tot, normalgras));
			datetotal9.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datetotal9);

			PdfPCell datetotal10 = new PdfPCell(new Phrase("", normalgras));
			dateddebfin.addCell(datetotal10);

			PdfPCell datetotal11 = new PdfPCell(new Phrase(tot, normalgras));
			datetotal11.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateddebfin.addCell(datetotal11);

			PdfPCell datetotal12 = new PdfPCell(new Phrase("", normalgras));
			dateddebfin.addCell(datetotal12);

			document.add(dateddebfin);

			Paragraph duree = new Paragraph(
					new Phrase(
							"Ce contrat est à durée déterminée pour la durée de la mission",
							mini));
			duree.setAlignment(Element.ALIGN_CENTER);
			document.add(duree);

			Paragraph rature = new Paragraph(
					new Phrase(
							"TOUTE FEUILLE HORAIRE RATUREE ENTRAINERA LE NON-PAIEMENT DE LA MISSION",
							normalgras));
			rature.setAlignment(Element.ALIGN_CENTER);

			document.add(rature);
			document.add(new Phrase());

			/******************** deuxieme double tableau *************************/
			PdfPTable remuneration = new PdfPTable(9);
			remuneration.setWidthPercentage(100);//largeur du tableau
			//nombre de colonnes et taille de chacune en proportion
			float[] largeurrem = { 4, 4, 3, 2, 2, 2, 2, 2, 2 };
			remuneration.setWidths(largeurrem);
			//creation d'une cellule du tableau
			PdfPCell rem01 = new PdfPCell(new Phrase("REMUNERATION ",
					normal));
			rem01.setColspan(2);
			rem01.setBorder(0);
			rem01.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell rem02 = new PdfPCell(new Phrase("", normal));
			rem02.setBorder(15);
			PdfPCell rem03 = new PdfPCell(new Phrase("Hs Nor", normal));
			rem03.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell rem04 = new PdfPCell(new Phrase("Hs 10%", normal));
			rem04.setBorder(15);
			rem04.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell rem05 = new PdfPCell(new Phrase("Hs 25%", normal));
			rem05.setBorder(15);
			rem05.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell rem06 = new PdfPCell(new Phrase("Hs 50%", normal));
			rem05.setBorder(15);
			rem05.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell rem07 = new PdfPCell(new Phrase("Autre", normal));
			rem07.setBorder(15);
			rem07.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell rem08 = new PdfPCell(new Phrase("Total", normal));
			rem08.setBorder(15);
			rem08.setHorizontalAlignment(Element.ALIGN_CENTER);

			remuneration.addCell(rem01);
			remuneration.addCell(rem02);
			remuneration.addCell(rem03);
			remuneration.addCell(rem04);
			remuneration.addCell(rem05);
			remuneration.addCell(rem06);
			remuneration.addCell(rem07);
			remuneration.addCell(rem08);

			PdfPCell rem011 = new PdfPCell(new Phrase(
					"Salaire de base brut :", normal));
			rem011.setBorder(0);
			rem01.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell rem012 = new PdfPCell(new Phrase(
					decimalformat.format(contrat.getSalairehoraire()) + " €", normalgras));
			rem012.setBorder(0);
			PdfPCell rem013 = new PdfPCell(new Phrase("Semaine 1", normal));
			rem02.setBorder(15);
			PdfPCell rem014 = new PdfPCell(new Phrase("", normal));
			PdfPCell rem015 = new PdfPCell(new Phrase("", normal));
			rem04.setBorder(15);
			PdfPCell rem016 = new PdfPCell(new Phrase("", normal));
			rem05.setBorder(15);

			PdfPCell rem017 = new PdfPCell(new Phrase("", normal));
			rem05.setBorder(15);
			PdfPCell rem018 = new PdfPCell(new Phrase("", normal));
			rem07.setBorder(15);

			PdfPCell rem019 = new PdfPCell(new Phrase("", normal));
			rem08.setBorder(15);

			remuneration.addCell(rem011);
			remuneration.addCell(rem012);
			remuneration.addCell(rem013);
			remuneration.addCell(rem014);
			remuneration.addCell(rem015);
			remuneration.addCell(rem016);
			remuneration.addCell(rem017);
			remuneration.addCell(rem018);
			remuneration.addCell(rem019);

			PdfPCell rem021 = new PdfPCell(new Phrase("Congés Payés :",
					normal));
			rem021.setBorder(0);
			rem021.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell rem022 = new PdfPCell(new Phrase("10% du salaire",
					normalgras));
			rem022.setBorder(0);
			PdfPCell rem023 = new PdfPCell(new Phrase("Semaine 2", normal));
			rem023.setBorder(15);
			PdfPCell rem024 = new PdfPCell(new Phrase("", normal));
			PdfPCell rem025 = new PdfPCell(new Phrase("", normal));
			rem025.setBorder(15);
			PdfPCell rem026 = new PdfPCell(new Phrase("", normal));
			rem026.setBorder(15);

			PdfPCell rem027 = new PdfPCell(new Phrase("", normal));
			rem027.setBorder(15);
			PdfPCell rem028 = new PdfPCell(new Phrase("", normal));
			rem028.setBorder(15);

			PdfPCell rem029 = new PdfPCell(new Phrase("", normal));
			rem029.setBorder(15);

			remuneration.addCell(rem021);
			remuneration.addCell(rem022);
			remuneration.addCell(rem023);
			remuneration.addCell(rem024);
			remuneration.addCell(rem025);
			remuneration.addCell(rem026);
			remuneration.addCell(rem027);
			remuneration.addCell(rem028);
			remuneration.addCell(rem029);

			PdfPCell rem031 = new PdfPCell(new Phrase("Panier :", normal));
			rem031.setBorder(0);
			rem031.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell rem032 = new PdfPCell(new Phrase(decimalformat.format(contrat.getPanier())
					+ " €", normalgras));
			rem032.setBorder(0);
			PdfPCell rem033 = new PdfPCell(new Phrase("Semaine 3", normal));
			rem033.setBorder(15);
			PdfPCell rem034 = new PdfPCell(new Phrase("", normal));
			PdfPCell rem035 = new PdfPCell(new Phrase("", normal));
			rem035.setBorder(15);
			PdfPCell rem036 = new PdfPCell(new Phrase("", normal));
			rem036.setBorder(15);

			PdfPCell rem037 = new PdfPCell(new Phrase("", normal));
			rem037.setBorder(15);
			PdfPCell rem038 = new PdfPCell(new Phrase("", normal));
			rem038.setBorder(15);

			PdfPCell rem039 = new PdfPCell(new Phrase("", normal));
			rem039.setBorder(15);

			remuneration.addCell(rem031);
			remuneration.addCell(rem032);
			remuneration.addCell(rem033);
			remuneration.addCell(rem034);
			remuneration.addCell(rem035);
			remuneration.addCell(rem036);
			remuneration.addCell(rem037);
			remuneration.addCell(rem038);
			remuneration.addCell(rem039);

			PdfPCell rem041 = new PdfPCell(new Phrase("Déplacements :",
					normal));
			rem041.setBorder(0);
			rem041.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell rem042 = new PdfPCell(new Phrase(
					decimalformat.format(contrat.getDeplacement()) + " €", normalgras));
			rem042.setBorder(0);
			PdfPCell rem043 = new PdfPCell(new Phrase("Semaine 4", normal));
			rem043.setBorder(15);
			PdfPCell rem044 = new PdfPCell(new Phrase("", normal));
			PdfPCell rem045 = new PdfPCell(new Phrase("", normal));
			rem045.setBorder(15);
			PdfPCell rem046 = new PdfPCell(new Phrase("", normal));
			rem046.setBorder(15);

			PdfPCell rem047 = new PdfPCell(new Phrase("", normal));
			rem047.setBorder(15);
			PdfPCell rem048 = new PdfPCell(new Phrase("", normal));
			rem048.setBorder(15);

			PdfPCell rem049 = new PdfPCell(new Phrase("", normal));
			rem049.setBorder(15);

			remuneration.addCell(rem041);
			remuneration.addCell(rem042);
			remuneration.addCell(rem043);
			remuneration.addCell(rem044);
			remuneration.addCell(rem045);
			remuneration.addCell(rem046);
			remuneration.addCell(rem047);
			remuneration.addCell(rem048);
			remuneration.addCell(rem049);

			PdfPCell rem051 = new PdfPCell(new Phrase("Facturation :",
					normal));
			rem051.setBorder(0);
			rem051.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell rem052 = new PdfPCell(new Phrase(
					decimalformat.format(contrat.getFacturation()) + " €", normalgras));
			rem052.setBorder(0);
			PdfPCell rem053 = new PdfPCell(new Phrase("Semaine 5", normal));
			rem053.setBorder(15);
			PdfPCell rem054 = new PdfPCell(new Phrase("", normal));
			PdfPCell rem055 = new PdfPCell(new Phrase("", normal));
			rem055.setBorder(15);
			PdfPCell rem056 = new PdfPCell(new Phrase("", normal));
			rem056.setBorder(15);

			PdfPCell rem057 = new PdfPCell(new Phrase("", normal));
			rem057.setBorder(15);
			PdfPCell rem058 = new PdfPCell(new Phrase("", normal));
			rem058.setBorder(15);

			PdfPCell rem059 = new PdfPCell(new Phrase("", normal));
			rem059.setBorder(15);

			remuneration.addCell(rem051);
			remuneration.addCell(rem052);
			remuneration.addCell(rem053);
			remuneration.addCell(rem054);
			remuneration.addCell(rem055);
			remuneration.addCell(rem056);
			remuneration.addCell(rem057);
			remuneration.addCell(rem058);
			remuneration.addCell(rem059);

			PdfPCell rem061 = new PdfPCell(new Phrase("Divers fac. :",
					normal));
			rem061.setBorder(0);
			rem061.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell rem062 = new PdfPCell(new Phrase(
					decimalformat.format(contrat.getDivers())
					+ " €", normalgras));
			rem062.setBorder(0);
			PdfPCell rem063 = new PdfPCell(new Phrase("Semaine 6", normal));
			rem063.setBorder(15);
			PdfPCell rem064 = new PdfPCell(new Phrase("", normal));
			PdfPCell rem065 = new PdfPCell(new Phrase("", normal));
			rem065.setBorder(15);
			PdfPCell rem066 = new PdfPCell(new Phrase("", normal));
			rem066.setBorder(15);

			PdfPCell rem067 = new PdfPCell(new Phrase("", normal));
			rem067.setBorder(15);
			PdfPCell rem068 = new PdfPCell(new Phrase("", normal));
			rem068.setBorder(15);

			PdfPCell rem069 = new PdfPCell(new Phrase("", normal));
			rem069.setBorder(15);

			remuneration.addCell(rem061);
			remuneration.addCell(rem062);
			remuneration.addCell(rem063);
			remuneration.addCell(rem064);
			remuneration.addCell(rem065);
			remuneration.addCell(rem066);
			remuneration.addCell(rem067);
			remuneration.addCell(rem068);
			remuneration.addCell(rem069);

			PdfPCell rem071 = new PdfPCell(new Phrase("Commentaires :",
					normal));
			rem071.setBorder(0);
			rem071.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell rem072 = new PdfPCell(new Phrase(
					contrat.getCommentaire(), normalgras));
			rem072.setBorder(0);
			PdfPCell rem073 = new PdfPCell(new Phrase("Total", normal));
			rem073.setBorder(15);
			PdfPCell rem074 = new PdfPCell(new Phrase("", normal));
			PdfPCell rem075 = new PdfPCell(new Phrase("", normal));
			rem075.setBorder(15);
			PdfPCell rem076 = new PdfPCell(new Phrase("", normal));
			rem076.setBorder(15);

			PdfPCell rem077 = new PdfPCell(new Phrase("", normal));
			rem077.setBorder(15);
			PdfPCell rem078 = new PdfPCell(new Phrase("", normal));
			rem078.setBorder(15);

			PdfPCell rem079 = new PdfPCell(new Phrase("", normal));
			rem079.setBorder(15);

			remuneration.addCell(rem071);
			remuneration.addCell(rem072);
			remuneration.addCell(rem073);
			remuneration.addCell(rem074);
			remuneration.addCell(rem075);
			remuneration.addCell(rem076);
			remuneration.addCell(rem077);
			remuneration.addCell(rem078);
			remuneration.addCell(rem079);

			document.add(remuneration);

			Paragraph finmission = new Paragraph(
					new Phrase(
							"Enfin de mission, après avoir noté les heures effectuées, le contrat doit être signé par l\'utilisateur et le salarié.",
							normal));
			finmission.setAlignment(Element.ALIGN_CENTER);
			document.add(finmission);
			
			Paragraph exemplaire = new Paragraph(
					new Phrase(
							"L\'exemplaire sera remis à CAP EMPLOI avant le 6 du mois suivant.",
							normal));
			exemplaire.setAlignment(Element.ALIGN_CENTER);
			document.add(exemplaire);

			
			
			Paragraph faita = new Paragraph(
					new Phrase(
							"Fait à Valence d'Agen, le :",
							normal));
			faita.setAlignment(Element.ALIGN_LEFT);
			
			document.add(faita);
			document.add(new Phrase("\n", minixx));
			
			PdfPTable signature = new PdfPTable(3);
			signature.setWidthPercentage(100);//largeur du tableau
			//nombre de colonnes et taille de chacune en proportion
			float[] largeursig = { 1,1,1 };
			signature.setWidths(largeursig);
						
			PdfPCell sig01=new PdfPCell(new Phrase("L'UTILISATEUR",normal));
			sig01.setHorizontalAlignment(Element.ALIGN_CENTER);
			sig01.setBorder(0);
			signature.addCell(sig01);
			PdfPCell sig02=new PdfPCell(new Phrase("LE SALARIE TEMPORAIRE",normal));
			sig02.setHorizontalAlignment(Element.ALIGN_CENTER);
			sig02.setBorder(0);
			signature.addCell(sig02);
			PdfPCell sig03=new PdfPCell(new Phrase("CAP EMPLOI",normal));
			sig03.setHorizontalAlignment(Element.ALIGN_CENTER);
			sig03.setBorder(0);
			signature.addCell(sig03);
			PdfPCell sig04=new PdfPCell(new Phrase(employeur.getRs_employeur(),normalgras));
			sig04.setHorizontalAlignment(Element.ALIGN_CENTER);
			sig04.setBorder(0);
			signature.addCell(sig04);
			PdfPCell sig05=new PdfPCell(new Phrase(identite.getNom_IDE()+" "+identite.getPrenom_IDE(),normalgras));
			sig05.setHorizontalAlignment(Element.ALIGN_CENTER);
			sig05.setBorder(0);
			signature.addCell(sig05);
			PdfPCell sig06=new PdfPCell(new Phrase("",normalgras));
			sig06.setBorder(0);
			
			signature.addCell(sig06);
			
			document.add(signature);
			
			


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
	%>