<%@ page
	import="dao.imp.identite.*,beans.rmi.*,dao.imp.rmi.*,beans.parametres.accueil.*, beans.identite.*,dao.imp.ai.*,beans.ai.*,
	java.util.*,beans.employeurs.*,dao.imp.employeur.*,divers.*,com.itextpdf.text.*,
	java.io.*,com.itextpdf.text.pdf.*,java.net.*,java.text.*,beans.parametres.capemploi.*"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%

String numeroconvocation = request.getParameter("convocation");

ConvocRMIDAO convdao = new ConvocRMIDAO();
ConvocRMI convoc = convdao.findByID(Integer
		.parseInt(numeroconvocation));

String hh="",mn="";
int pos=convoc.getHeureconvoc().lastIndexOf(":");
hh=convoc.getHeureconvoc().substring(0,pos);
mn=convoc.getHeureconvoc().substring(pos+1);

IdentiteDAO idao = new IdentiteDAO();
Identite identite = idao.findByID(convoc.getIdentite().getId_IDE());

UtilisateurDAO redao = new UtilisateurDAO();
Utilisateur referent = redao.findByID(convoc.getReferent()
		.getId_salarie());

OrganismeDAO orgdao = new OrganismeDAO();
Organisme organisme = orgdao.findByID(1);

String civilite = "";
if (identite.getSexe_IDE().equals("MASCULIN"))
	civilite = "Mr";
if (identite.getSexe_IDE().equals("FEMININ"))
	civilite = "Mme";

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

		//hauteur maxi page =842 points et largeur=595points
				Font titregras = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);
				Font normal = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
				Font normalgras = new Font(Font.FontFamily.TIMES_ROMAN, 12,
						Font.BOLD);
				Font mini = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.NORMAL);
				//creation d'une police personnelle en couleur
				Font mapolice = FontFactory.getFont(FontFactory.COURIER, 20f,
						Font.BOLD, BaseColor.BLUE);
				

				ByteArrayOutputStream buffer = new ByteArrayOutputStream();

				document = new Document(PageSize.A4, 40, 40, 00, 20);

				try {
					//le flux sera stocké en mémoire et non pas dans un fichier
					ecriture = PdfWriter.getInstance(document, buffer);
					document.open();

					//logo de l'entreprise
				    String urlimage = "http://"+request.getServerName()+":"+request.getServerPort()+"/valence/images/bleu/mefi3.png";
				    Paragraph logo = new Paragraph();
					Image corfi = Image.getInstance(new URL(urlimage));
					corfi.setAbsolutePosition(-10f, 730f); //position de l'image
					corfi.scalePercent(45f); //taille de l'image
					logo.add(corfi);
					document.add(logo);
					document.add(new Paragraph("\n\n\n"));
					//nouveau paragraphe
					Paragraph titre = new Paragraph();
					titre.setAlignment(Element.ALIGN_CENTER);
					/*titre.add(new Paragraph(
							"Maison de l'Emploi,\n de la Formation \net de l'Internet des 2 Rives\n",
							mapolice));
					*/
					
					
					document.add(new Paragraph(" "));
					
					Paragraph titre1 = new Paragraph();										
					titre1.setIndentationLeft(320);					
					titre1.add(new Phrase("Valence d'Agen,  le "
							+ sdf.format(new Date()) + "\n\n", normal));
					document.add(titre1);
										
					Paragraph espace = new Paragraph("\n",normal);
					espace.setLeading(35);
					document.add(espace);
					//document.add(new Paragraph(" "));
					

					Paragraph titre2 = new Paragraph();
					titre2.setIndentationLeft(320);
					titre2.add(new Phrase(civilite + " " + identite.getPrenom_IDE()
							+ " " + identite.getNom_IDE() + "\n", normal));
					titre2.add(new Phrase(identite.getAdr1_IDE() + "\n", normal));
					titre2.add(new Phrase(identite.getAdr2_IDE() + "\n", normal));
					titre2.add(new Phrase(identite.getCp_IDE() + " "
							+ identite.getVille_IDE() + "\n", normal));
					document.add(titre2);
					document.add(new Paragraph("\n\n"));

					Paragraph objets = new Paragraph();
					objets.setLeading(10);
					Chunk objet = new Chunk("Objet", normalgras);
					
					Chunk objetsuite = new Chunk(" : convocation suivi RSA", normal);
					objets.add(objet);
					objets.add(objetsuite);
					document.add(objets);
					
					document.add(new Paragraph("\n"));
					
					Paragraph madame=new Paragraph(new Phrase("Madame, Monsieur,\n", normal));
					madame.setFirstLineIndent(40);
					document.add(madame);
					
					document.add(new Paragraph(" "));
					
					Paragraph intro = new Paragraph(new Phrase(
							"Vous êtes actuellement bénéficiaire du RSA et dans le cadre de"
							+" votre contrat d\'insertion, le service RSA vous a orienté"
							+" vers nos services pour un accompagnement vers l\'emploi,"
							+" aussi nous vous convoquons à un entretien avec :\n",
							normal));
					intro.setFirstLineIndent(40);
					intro.setAlignment(Element.ALIGN_JUSTIFIED);
					document.add(intro);
					
					document.add(new Phrase(" ",mini));
										
					Chunk refer = new Chunk(referent.getPrenom() + " "
							+ referent.getNom() + ", le "
							+ sdf.format(convoc.getDateconvoc_rmiconvoc()) + " à "
							+ hh+" h "+mn + "\n", normalgras);
					refer.setBackground(new BaseColor(174,244,243));
					Paragraph aa=new Paragraph(new Phrase(refer));
					aa.setAlignment(Element.ALIGN_CENTER);
					document.add(aa);
					document.add(new Phrase());
					
					
					document.add(new Phrase(" ",mini));
					
					Paragraph rdv = new Paragraph();
					rdv.setAlignment(Element.ALIGN_CENTER);
					rdv.setLeading(12);
					rdv.add(new Phrase(
							"Maison de l'Emploi, de la Formation et de l'Internet\n\n"+
							organisme.getAdr1() + " \n\n "
							+ organisme.getCp()+" "+organisme.getVille() + "\n", normalgras));
					document.add(rdv);
					
					document.add(new Phrase(" ",mini));
					document.add(new Paragraph("\n"));
					
					Paragraph rem = new Paragraph(new Phrase("Lors de cet entretien, il est impératif de vous"+
							" munir de ", normal));
					rem.setFirstLineIndent(40);
					rem.setAlignment(Element.ALIGN_JUSTIFIED);
					Chunk rem2 = new Chunk("votre pièce d\'identité", normalgras);
					rem2.setUnderline(0.1f,-2f);
					Chunk rem3 = new Chunk(" (carte d\'identité, ou passeport, ou titre de séjour), ainsi"
							+" que votre ",normal);
					Chunk rem4 = new Chunk("justificatif de bénéficiaire RSA",normalgras);
					rem4.setUnderline(0.1f,-2f);
					Chunk rem5 = new Chunk(" ( document issu de CAFPRO), le cas échéant, munissez vous"+
					" de votre ",normal);
					Chunk rem6 = new Chunk("numéro allocataire CAF",normalgras);
					rem6.setUnderline(0.1f,-2f);
					Chunk rem7 = new Chunk(" ainsi que de votre ",normal);
					Chunk rem8 = new Chunk("mot de passe.",normalgras);
					rem8.setUnderline(0.1f,-2f);
					rem.add(rem2);
					rem.add(rem3);
					rem.add(rem4);
					rem.add(rem5);
					rem.add(rem6);
					rem.add(rem7);
					rem.add(rem8);
					document.add(rem);
										
					Paragraph annul = new Paragraph(new Phrase(
							"Si cette date ne vous convenait pas, veuillez prendre contact avec notre service accueil afin de convenir d\'un autre rendez-vous au : "
									, normal));
					annul.setFirstLineIndent(40);
					annul.setAlignment(Element.ALIGN_JUSTIFIED);
					Chunk phone=new Chunk(organisme.getTel(),normalgras);
					annul.add(phone);
					document.add(annul);
					
					document.add(new Phrase("\n",normal));

					Paragraph conclusion =new Paragraph( new Phrase(
							"Nous vous prions d\'agréer, Madame, Monsieur, l\'expression de nos sincères salutations.\n"
								, normal));
					conclusion.setFirstLineIndent(40);
					conclusion.setAlignment(Element.ALIGN_JUSTIFIED);
					document.add(conclusion);
					document.add(new Phrase("\n\n",normal));
					
					Paragraph signe =new Paragraph( new Phrase("Le secrétariat"	, normalgras));
					signe.setAlignment(Element.ALIGN_RIGHT);
					document.add(signe);
					
					//logo de l'entreprise
				    String euro = "http://"+request.getServerName()+":"+request.getServerPort()+"/valence/images/bleu/logo_Europe.jpg";

					Image europe1 = Image.getInstance(new URL(euro));
					europe1.setAbsolutePosition(200f,10f); //position de l'image
					
					europe1.scalePercent(33f); //taille de l'image
					document.add(europe1);


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