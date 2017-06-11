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
	String datedebut = request.getParameter("datedebut");
	String datefin = request.getParameter("datefin");
	java.util.List<Agrement> liste = (java.util.List<Agrement>) request
			.getAttribute("liste");
%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

	<%
		response.setContentType("application/pdf");
		IdentiteDAO idao = new IdentiteDAO();
		Document document;
		PdfWriter ecriture;
		PdfContentByte cb;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		//hauteur maxi page =842 points et largeur=595points
		Font titregras = new Font(Font.FontFamily.COURIER, 10, Font.BOLD);
		Font normal = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);

		Font titre = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12f,
				Font.BOLD, BaseColor.BLACK);

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		document = new Document(PageSize.A4, 20, 20, 00, 20);

		try {
			//le flux sera stocké en mémoire et non pas dans un fichier
			ecriture = PdfWriter.getInstance(document, buffer);
			document.open();

			Paragraph montitre = new Paragraph();
			montitre.add(new Phrase("DEMANDE D'AGREMENT DU "
					+ sdf.format(java.sql.Date.valueOf(datedebut)) + " AU "
					+ sdf.format(java.sql.Date.valueOf(datefin)), titre));
			montitre.setAlignment(Element.ALIGN_CENTER);
			document.add(montitre);
			document.add(new Phrase("\n", normal));
			//creation d'un tableau
			PdfPTable table = new PdfPTable(4);
			table.setWidthPercentage(100);//largeur du tableau
			//nombre de colonnes et taille de chacune en proportion
			float[] largeur = { 3, 1, 1, 1 };
			table.setWidths(largeur);
			//creation d'une cellule du tableau
			PdfPCell cel1 = new PdfPCell(new Phrase("NOM", titregras));
			cel1.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cel2 = new PdfPCell(new Phrase("DATE", titregras));
			cel2.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cel3 = new PdfPCell(new Phrase("ECHEANCE", titregras));
			cel3.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cel4 = new PdfPCell(new Phrase("N° AGREMENT",
					titregras));
			cel4.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cel1);
			table.addCell(cel2);
			table.addCell(cel3);
			table.addCell(cel4);

			//PdfPCell nom1, nom2, nom3,nom4;
			for (int i = 0; i < liste.size(); i++) {
				
				String deb="",fin="";
				if(liste.get(i).getDatefin()!=null)
					fin=sdf.format(liste.get(i).getDatefin());
				
				if(liste.get(i).getDatedeb()!=null)
					deb=sdf.format(liste.get(i).getDatedeb());
				
				PdfPCell nom1 = new PdfPCell(new Phrase(liste.get(i)
						.getIdentite().getNom_IDE()
						+ " " + liste.get(i).getIdentite().getPrenom_IDE(),
						normal));
				nom1.setHorizontalAlignment(Element.ALIGN_LEFT);
				nom1.setPadding(5);
				PdfPCell nom2 = new PdfPCell(new Phrase(deb, normal));
				nom2.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				PdfPCell nom3 = new PdfPCell(new Phrase(fin, normal));
				nom3.setHorizontalAlignment(Element.ALIGN_CENTER);
				PdfPCell nom4 = new PdfPCell(new Phrase(liste.get(i)
						.getNumagrement(), normal));
				nom4.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(nom1);
				table.addCell(nom2);
				table.addCell(nom3);
				table.addCell(nom4);
			}

			document.add(table);

		} catch (DocumentException e) {
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
		response.getOutputStream().close();
	%>