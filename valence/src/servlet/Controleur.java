package servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import beans.ai.Agrement;
import beans.ai.Avenant;
import beans.ai.Contrat;
import beans.ai.Creation;
import beans.ai.Extranet;
import beans.ai.VisiteMedicale;
import beans.employeurs.Contact;
import beans.employeurs.Employeur;
import beans.employeurs.Offre;
import beans.employeurs.PositionnerPersonne;
import beans.employeurs.Service;
import beans.employeurs.Suivi;
import beans.formation.Animatrice;
import beans.formation.ListeFormations;
import beans.formation.NiveauQualificationFormation;
import beans.formation.OrganismeFormation;
import beans.formation.PreInscription;
import beans.formation.Prescripteur;
import beans.formationpro.FormationProEmployeur;
import beans.formationpro.FormationProInscription;
import beans.identite.Identite;
import beans.identite.ProfilDiplome;
import beans.identite.ProfilLocomotion;
import beans.identite.ProfilPermisPro;
import beans.identite.ProfilPriorite;
import beans.identite.ProfilRecherche;
import beans.mutuelle.Mutuelle;
import beans.parametres.accueil.Cc2r;
import beans.parametres.accueil.Cc2rDAO;
import beans.parametres.accueil.CodesPostaux;
import beans.parametres.accueil.CodesPostauxDAO;
import beans.parametres.accueil.Organisme;
import beans.parametres.accueil.OrganismeDAO;
import beans.parametres.accueil.ReferentPoleEmploi;
import beans.parametres.accueil.ReferentPoleEmploiDAO;
import beans.parametres.accueil.Rome;
import beans.parametres.accueil.RomeDAO;
import beans.parametres.accueil.TypeFormations;
import beans.parametres.accueil.TypeFormationsDAO;
import beans.parametres.capemploi.Utilisateur;
import beans.parametres.capemploi.UtilisateurDAO;
import beans.parametres.employeur.Activite;
import beans.parametres.employeur.ActiviteDAO;
import beans.parametres.employeur.Statut;
import beans.parametres.employeur.StatutDAO;
import beans.parametres.employeur.Structure;
import beans.parametres.employeur.StructureDAO;
import beans.parametres.formation.Theme;
import beans.parametres.formation.ThemeFormationDAO;
import beans.parametres.formationpro.Formprofinancement;
import beans.parametres.formationpro.FormprofinancementDAO;
import beans.parametres.formationpro.Formproniveau;
import beans.parametres.formationpro.FormproniveauDAO;
import beans.parametres.formationpro.Formprostatutemployeur;
import beans.parametres.formationpro.FormprostatutemployeurDAO;
import beans.parametres.formationpro.Formprotheme;
import beans.parametres.formationpro.FormprothemeDAO;
import beans.rmi.ContratRMI;
import beans.rmi.ConvocRMI;
import beans.rmi.FicheLiaisonRMI;
import beans.rmi.FicheRMI;
import beans.rth.FicheRTH;
import beans.sap.AvenantCDI;
import beans.sap.AvenantPrestationCDI;
import beans.sap.ContratCDI;
import beans.sap.PrestationCDI;
import beans.sap.TachesSAP;
import beans.smic.Smic;
import beans.smic.SmicDAO;
import beans.suivi.AccompagnementFormation;
import beans.suivi.PropositionsFormation;
import beans.suivi.SuiviEmploi;
import beans.suivi.SuiviFormation;
import beans.suivi.SuiviPersonne;
import dao.exception.DAOException;
import dao.imp.ai.AgrementDAO;
import dao.imp.ai.AvenantDAO;
import dao.imp.ai.ContratDAO;
import dao.imp.ai.CreationDAO;
import dao.imp.ai.ExtranetDAO;
import dao.imp.ai.VisiteMedicaleDAO;
import dao.imp.employeur.ContactDAO;
import dao.imp.employeur.EmployeurDAO;
import dao.imp.employeur.OffreDAO;
import dao.imp.employeur.PositionnerPersonneDAO;
import dao.imp.employeur.ServiceDAO;
import dao.imp.employeur.SuiviDAO;
import dao.imp.formation.AnimatriceDAO;
import dao.imp.formation.ListeFormationsDAO;
import dao.imp.formation.NiveauQualificationFormationDAO;
import dao.imp.formation.OrganismeFormationDAO;
import dao.imp.formation.PreInscriptionDAO;
import dao.imp.formation.PrescripteurDAO;
import dao.imp.formationpro.FormationProEmployeurDAO;
import dao.imp.formationpro.FormationProInscriptionDAO;
import dao.imp.identite.IdentiteDAO;
import dao.imp.identite.ProfilDiplomeDAO;
import dao.imp.identite.ProfilLocomotionDAO;
import dao.imp.identite.ProfilPermisProDAO;
import dao.imp.identite.ProfilPrioriteDAO;
import dao.imp.identite.ProfilRechercheDAO;
import dao.imp.rmi.ContratRMIDAO;
import dao.imp.rmi.ConvocRMIDAO;
import dao.imp.rmi.FicheLiaisonRMIDAO;
import dao.imp.rmi.FicheRMIDAO;
import dao.imp.rth.FicheDAORTH;
import dao.imp.sap.AvenantCDIDAO;
import dao.imp.sap.AvenantPrestationCDIDAO;
import dao.imp.sap.ContratCDIDAO;
import dao.imp.sap.PrestationCDIDAO;
import dao.imp.sap.TachesSAPDAO;
import dao.imp.suivi.AccompagnementFormationDAO;
import dao.imp.suivi.PropositionsFormationDAO;
import dao.imp.suivi.SuiviEmploiDAO;
import dao.imp.suivi.SuiviFormationDAO;
import dao.imp.suivi.SuiviPersonneDAO;
import dao.mutuelle.MutuelleDAO;
import divers.Encode;
import divers.FormaterDate;
import divers.FormaterTexte;

/**
 * Servlet implementation class gestion
 */

public class Controleur extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public Controleur() {

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Récupération de l'action demandée
		String action = request.getParameter("action");
		// Par défaut page d'accueil
		String jspPage = "/valence/jsp/accueil/rechercherPersonne.jsp";

		if ((action == null) || (action.length() < 1)) {
			action = "default";

		}
		if ("default".equals(action)) {
			// Page JSP � retourner
			jspPage = "/valence/jsp/accueil/rechercherPersonne.jsp";
		}
		// action correspondant a une nouvelle inscription en base
		else if ("savepersonnebase".equals(action)) {
			// on recupere les infos saisies dans le formulaire en convertissant
			// certaines au bon format
			IdentiteDAO personne = new IdentiteDAO();
			int num = 0;
			FormaterDate jour = new FormaterDate();
			java.sql.Date accueil = null, inscripPE = null, dateNaiss = null, expireCS = null;

			String nom = request.getParameter("nom");
			String prenom = request.getParameter("prenom");
			prenom = prenom.trim();
			String premierelettre = prenom.substring(0, 1);
			premierelettre = premierelettre.toUpperCase();
			// System.out.println(premierelettre);
			String resteprenom = prenom.substring(1);
			// System.out.println(resteprenom);
			String prenomfinal = premierelettre + resteprenom;
			// System.out.println(prenomfinal);

			String nais = request.getParameter("naissance");
			if (!nais.equals("") && !nais.equals(null))
				dateNaiss = new FormaterDate().changeFormatChaineDate(nais);

			String cs = request.getParameter("dateexpiration");
			if (!cs.equals("") && !cs.equals(null))
				expireCS = new FormaterDate().changeFormatChaineDate(cs);

			String insPE = request.getParameter("dateinscriptionanpe");
			if (!insPE.equals("") && !insPE.equals(null))
				inscripPE = new FormaterDate().changeFormatChaineDate(insPE);

			/*
			 * si la date d'inscription n'est pas saisie on met la date du jour
			 */
			String ins = request.getParameter("dateinscription");
			if (ins.equals("") || ins.equals(null)) {
				String a = jour.getSortie();
				accueil = new FormaterDate().changeFormatChaineDate(a);
			} else
				accueil = new FormaterDate().changeFormatChaineDate(ins);

			String permb = request.getParameter("permisb");
			// System.out.println("perrmis b=" + permb);

			String adresse1 = request.getParameter("adresse1");
			if (adresse1.length() > 50)
				adresse1 = adresse1.substring(0, 49);

			String adresse2 = request.getParameter("adresse2");
			if (adresse2.length() > 50)
				adresse2 = adresse2.substring(0, 49);

			String chichi = request.getParameter("sexe");
			String sf = request.getParameter("situationfamiliale");
			if (sf != null && sf.equals("Aucun"))
				sf = null;

			boolean pb;
			if (permb.equals("oui"))
				pb = true;
			else
				pb = false;

			Short enf = new Short(request.getParameter("enfants"));
			// on cree notre personne
			Identite une = new Identite(chichi, nom, prenomfinal, request.getParameter("nomjf"), adresse1, adresse2,
					request.getParameter("cp"), request.getParameter("ville"), request.getParameter("mobile"),
					request.getParameter("fixe"), request.getParameter("autrephone"),
					request.getParameter("phonelocalisation"), request.getParameter("mail"), dateNaiss,
					request.getParameter("lieunaissance"), request.getParameter("paysnaissance"),
					request.getParameter("departement"), request.getParameter("nationalite"),
					request.getParameter("cartesejour"), expireCS, request.getParameter("securitesociale"), sf, enf,
					accueil, pb, true, inscripPE, request.getParameter("numeroidentifiant"),
					request.getParameter("niveauformation"));

			try {
				num = personne.create(une);
				// System.out.println(num);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			/***************************************************************************/
			/* partie diplomes */
			/***************************************************************************/
			ProfilDiplomeDAO pdao = new ProfilDiplomeDAO();
			String dip1 = request.getParameter("nomdiplome1");
			int numpd1 = 0;
			if (!dip1.equals("") && !dip1.equals(null)) {
				ProfilDiplome pd = new ProfilDiplome(num, dip1, request.getParameter("diplomeobtenu1"),
						request.getParameter("anneeobtention1"));
				try {
					numpd1 = pdao.create(pd);
					// System.out.println(numpd1);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String dip2 = request.getParameter("nomdiplome2");
			int numpd2 = 0;
			if (!dip2.equals("") && !dip2.equals(null)) {
				ProfilDiplome pd = new ProfilDiplome(num, dip2, request.getParameter("diplomeobtenu2"),
						request.getParameter("anneeobtention2"));
				try {
					numpd2 = pdao.create(pd);
					// System.out.println(numpd2);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String dip3 = request.getParameter("nomdiplome3");
			int numpd3 = 0;
			if (!dip3.equals("") && !dip3.equals(null)) {
				ProfilDiplome pd = new ProfilDiplome(num, dip3, request.getParameter("diplomeobtenu3"),
						request.getParameter("anneeobtention3"));
				try {
					numpd3 = pdao.create(pd);
					// System.out.println(numpd3);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String dip4 = request.getParameter("nomdiplome4");
			int numpd4 = 0;
			if (!dip4.equals("") && !dip4.equals(null)) {
				ProfilDiplome pd = new ProfilDiplome(num, dip4, request.getParameter("diplomeobtenu4"),
						request.getParameter("anneeobtention4"));
				try {
					numpd4 = pdao.create(pd);
					// System.out.println(numpd4);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String dip5 = request.getParameter("nomdiplome5");
			int numpd5 = 0;
			if (!dip5.equals("") && !dip5.equals(null)) {
				ProfilDiplome pd = new ProfilDiplome(num, dip5, request.getParameter("diplomeobtenu5"),
						request.getParameter("anneeobtention5"));
				try {
					numpd5 = pdao.create(pd);
					// System.out.println(numpd5);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			/***************************************************************************/
			/* partie locomotion */
			/***************************************************************************/
			ProfilLocomotionDAO pl = new ProfilLocomotionDAO();
			String[] locomotions = request.getParameterValues("loco");
			// String voiture=request.getParameter("voiture");
			// String velo=request.getParameter("velo");
			if (locomotions != null) {
				for (int i = 0; i < locomotions.length; i++) {
					ProfilLocomotion pro1 = new ProfilLocomotion(locomotions[i], num);
					try {
						int cle = pl.create(pro1);
						// System.out.println(cle);
					} catch (DAOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			/**************************************************************************/
			/* partie permis professionnel */
			/**************************************************************************/
			Identite tampon = null;
			try {
				tampon = personne.findByID(num);
			} catch (DAOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			ProfilPermisProDAO profilpermis = new ProfilPermisProDAO();

			// on recupere les dates saisies pour chaque permis
			java.sql.Date datc = null, datd = null, datec = null, datcar = null, datcac = null, datfim = null,
					datfco = null, datapt = null;
			String perc = request.getParameter("expirationC");
			if (!perc.equals("") && !perc.equals(null)) {
				datc = new FormaterDate().changeFormatChaineDate(perc);
				ProfilPermisPro ppp = new ProfilPermisPro("C", tampon, datc);
				try {
					int cle = profilpermis.create(ppp);
					// System.out.println(cle);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			String perd = request.getParameter("expirationD");
			if (!perd.equals("") && !perd.equals(null)) {
				datd = new FormaterDate().changeFormatChaineDate(perd);
				ProfilPermisPro ppp = new ProfilPermisPro("D", tampon, datd);
				try {
					int cle = profilpermis.create(ppp);
					// System.out.println(cle);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			String perec = request.getParameter("expirationEC");
			if (!perec.equals("") && !perec.equals(null)) {
				datec = new FormaterDate().changeFormatChaineDate(perec);
				ProfilPermisPro ppp = new ProfilPermisPro("E(C)", tampon, datec);
				try {
					int cle = profilpermis.create(ppp);
					// System.out.println(cle);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String percar = request.getParameter("expirationCariste");
			if (!percar.equals("") && !percar.equals(null)) {
				datcar = new FormaterDate().changeFormatChaineDate(percar);
				ProfilPermisPro ppp = new ProfilPermisPro("CARISTE", tampon, datcar);
				try {
					int cle = profilpermis.create(ppp);
					// System.out.println(cle);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String percac = request.getParameter("expirationCaces");
			if (!percac.equals("") && !percac.equals(null)) {
				datcac = new FormaterDate().changeFormatChaineDate(percac);
				ProfilPermisPro ppp = new ProfilPermisPro("CACES", tampon, datcac);
				try {
					int cle = profilpermis.create(ppp);
					// System.out.println(cle);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			String perfim = request.getParameter("expirationFimo");
			if (!perfim.equals("") && !perfim.equals(null)) {
				datfim = new FormaterDate().changeFormatChaineDate(perfim);
				ProfilPermisPro ppp = new ProfilPermisPro("FIMO", tampon, datfim);
				try {
					int cle = profilpermis.create(ppp);
					// System.out.println(cle);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String perfco = request.getParameter("expirationFcos");
			if (!perfco.equals("") && !perfco.equals(null)) {
				datfco = new FormaterDate().changeFormatChaineDate(perfco);
				ProfilPermisPro ppp = new ProfilPermisPro("FCOS", tampon, datfco);
				try {
					int cle = profilpermis.create(ppp);
					// System.out.println(cle);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String perapt = request.getParameter("expirationApth");
			if (!perapt.equals("") && !perapt.equals(null)) {
				datapt = new FormaterDate().changeFormatChaineDate(perapt);
				ProfilPermisPro ppp = new ProfilPermisPro("APTH", tampon, datapt);
				try {
					int cle = profilpermis.create(ppp);
					// System.out.println(cle);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			/**************************************************************************/
			/* partie priorite */
			/**************************************************************************/
			ProfilPrioriteDAO profilprio = new ProfilPrioriteDAO();
			String[] priorites = request.getParameterValues("prio");

			if (priorites != null) {
				// r�cup�ration des 3 dates possibles dans priorit�s
				String daterth = request.getParameter("expirerth");
				String datersasocle = request.getParameter("datesocle");
				String datersachapeau = request.getParameter("datechapeau");
				java.sql.Date madaterth = null;
				java.sql.Date madatersasocle = null;
				java.sql.Date madatersachapeau = null;

				if (!daterth.equals("") && !daterth.equals(null)) {
					madaterth = new FormaterDate().changeFormatChaineDate(daterth);
				}

				if (!datersasocle.equals("") && !datersasocle.equals(null)) {
					madatersasocle = new FormaterDate().changeFormatChaineDate(datersasocle);
				}

				if (!datersachapeau.equals("") && !datersachapeau.equals(null)) {
					madatersachapeau = new FormaterDate().changeFormatChaineDate(datersachapeau);
				}
				for (int i = 0; i < priorites.length; i++) {
					ProfilPriorite profp = null;
					if (priorites[i].equals("RTH") || priorites[i].equals("RSA-SOCLE")
							|| priorites[i].equals("RSA-CHAPEAU")) {
						if (priorites[i].equals("RTH")) {
							profp = new ProfilPriorite(priorites[i], num, madaterth);
						} else if (priorites[i].equals("RSA-SOCLE")) {
							profp = new ProfilPriorite(priorites[i], num, madatersasocle);
						} else {
							profp = new ProfilPriorite(priorites[i], num, madatersachapeau);
						}
					}

					else {
						profp = new ProfilPriorite(priorites[i], num);
					}
					try {
						int cle = profilprio.create(profp);
						// System.out.println(cle);
					} catch (DAOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			/**************************************************************************/
			/* partie recherche emploi */
			/**************************************************************************/
			ProfilRechercheDAO profilrech = new ProfilRechercheDAO();
			RomeDAO rda = new RomeDAO();
			Identite encours = null;
			try {
				encours = personne.findByID(num);
			} catch (DAOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			String empl1 = request.getParameter("emploirecherche1");

			if (!empl1.equals("") && !empl1.equals(null)) {
				empl1 = new FormaterTexte().supprimerTableMatiere(empl1);
				Rome rome1 = null;
				try {
					rome1 = rda.findByName(empl1);
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ProfilRecherche pr1 = new ProfilRecherche(encours, rome1);
				try {
					int cleem1 = profilrech.create(pr1);
					// System.out.println(cleem1);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			String empl2 = request.getParameter("emploirecherche2");

			if (!empl2.equals("") && !empl2.equals(null)) {
				empl2 = new FormaterTexte().supprimerTableMatiere(empl2);
				Rome rome2 = null;
				try {
					rome2 = rda.findByName(empl2);
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ProfilRecherche pr2 = new ProfilRecherche(encours, rome2);
				try {
					int cleem2 = profilrech.create(pr2);
					// System.out.println(cleem2);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String empl3 = request.getParameter("emploirecherche3");

			if (!empl3.equals("") && !empl3.equals(null)) {
				empl3 = new FormaterTexte().supprimerTableMatiere(empl3);
				Rome rome3 = null;
				try {
					rome3 = rda.findByName(empl3);
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ProfilRecherche pr3 = new ProfilRecherche(encours, rome3);
				try {
					int cleem3 = profilrech.create(pr3);
					// System.out.println(cleem3);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String empl4 = request.getParameter("emploirecherche4");

			if (!empl4.equals("") && !empl4.equals(null)) {
				empl4 = new FormaterTexte().supprimerTableMatiere(empl4);
				Rome rome4 = null;
				try {
					rome4 = rda.findByName(empl4);
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ProfilRecherche pr4 = new ProfilRecherche(encours, rome4);
				try {
					int cleem4 = profilrech.create(pr4);
					// System.out.println(cleem4);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String empl5 = request.getParameter("emploirecherche5");

			if (!empl5.equals("") && !empl5.equals(null)) {
				empl5 = new FormaterTexte().supprimerTableMatiere(empl5);
				Rome rome5 = null;
				try {
					rome5 = rda.findByName(empl5);
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ProfilRecherche pr5 = new ProfilRecherche(encours, rome5);
				try {
					int cleem5 = profilrech.create(pr5);
					// System.out.println(cleem5);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			jspPage = "/jsp/accueil/afficheInscrit.jsp?numero=" + num;
		}

		/********************************************************************************************/
		/***** recherche une personne dans la base *********/
		/********************************************************************************************/

		// action correspondant a une recherche par nom
		else if ("recherchernom".equals(action)) {
			String nom = request.getParameter("numero");

			IdentiteDAO personne = new IdentiteDAO();
			List<Identite> liste = new ArrayList<Identite>();
			try {
				liste = personne.findByName(nom);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("resultset", liste);

			jspPage = "/jsp/accueil/listing.jsp";
		}

		// action correspondant a une recherche par nom+ pr�nom
		else if ("recherchernomprenom".equals(action)) {
			String nom = request.getParameter("numero");
			/*
			 * // modifié le 23/08/2014 :inutile IdentiteDAO personne = new
			 * IdentiteDAO(); Identite une = null; try { une =
			 * personne.findByID(Integer.valueOf(nom)); } catch
			 * (NumberFormatException e1) { // TODO Auto-generated catch block
			 * e1.printStackTrace(); } catch (DAOException e1) { // TODO
			 * Auto-generated catch block e1.printStackTrace(); }
			 * 
			 * List<Identite> liste = new ArrayList<Identite>(); try { liste =
			 * personne.findByName2(une.getNom_IDE() + " " +
			 * une.getPrenom_IDE()); } catch (DAOException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); }
			 * request.setAttribute("resultset", liste);
			 * 
			 * jspPage = "/jsp/accueil/listing.jsp";
			 */

			jspPage = "/jsp/accueil/afficheInscrit.jsp?numero=" + nom;
		}

		/********************************************************************************************/
		/***** afficher une personne *********/
		/********************************************************************************************/
		else if ("afficherpersonne".equals(action)) {
			String num = request.getParameter("numero");
			jspPage = "/jsp/afficheInscrit.jsp?numero=" + num;
		}

		/********************************************************************************************/
		/***** modifier une personne *********/
		/********************************************************************************************/
		else if ("modifierpersonnebase".equals(action)) {
			String num = request.getParameter("numero");
			int nume = Integer.parseInt(num);
			Identite une = null;
			IdentiteDAO personne = new IdentiteDAO();

			java.sql.Date accueil = null, inscripPE = null, dateNaiss = null, expireCS = null;

			String nom = request.getParameter("nom");
			String prenom = request.getParameter("prenom");

			/*
			 * si la date d'inscription n'est pas saisie on met la date du jour
			 */
			String ins = request.getParameter("dateinscription");
			// System.out.println("date inscription=" + ins);
			if (!ins.equals("") && !ins.equals("null"))
				accueil = new FormaterDate().changeFormatChaineDate(ins);

			String nais = request.getParameter("naissance");
			if (!nais.equals("") && !nais.equals("null"))
				dateNaiss = new FormaterDate().changeFormatChaineDate(nais);

			String cs = request.getParameter("dateexpiration");
			// System.out.println("dateexpiration =" + cs);
			if (!cs.equals("") && !cs.equals("null"))
				expireCS = new FormaterDate().changeFormatChaineDate(cs);

			String insPE = request.getParameter("dateinscriptionanpe");
			if (!insPE.equals("") && !insPE.equals("null"))
				inscripPE = new FormaterDate().changeFormatChaineDate(insPE);

			String actif = request.getParameter("actif");
			boolean act;
			if (actif.equals("oui"))
				act = true;
			else
				act = false;

			String permb = request.getParameter("permisb");

			String adresse1 = request.getParameter("adresse1");

			String adresse2 = request.getParameter("adresse2");
			if (adresse1.length() > 50)
				adresse1 = adresse1.substring(0, 49);
			if (adresse2.length() > 50)
				adresse2 = adresse2.substring(0, 49);

			boolean pb;

			if (permb.equals("oui"))
				pb = true;
			else
				pb = false;

			Short enf = new Short(request.getParameter("enfants"));
			// on cree notre personne
			une = new Identite(nume, request.getParameter("sexe"), nom, prenom, request.getParameter("nomjf"), adresse1,
					adresse2, request.getParameter("cp"), request.getParameter("ville"), request.getParameter("mobile"),
					request.getParameter("fixe"), request.getParameter("autrephone"),
					request.getParameter("phonelocalisation"), request.getParameter("mail"), dateNaiss,
					request.getParameter("lieunaissance"), request.getParameter("paysnaissance"),
					request.getParameter("departement"), request.getParameter("nationalite"),
					request.getParameter("cartesejour"), expireCS, request.getParameter("securitesociale"),
					request.getParameter("situationfamiliale"), enf, accueil, pb, act, inscripPE,
					request.getParameter("numeroidentifiant"), request.getParameter("niveauformation"));

			try {
				une = personne.update(une);
				// System.out.println(num);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			/***************************************************************************/
			/* partie diplomes */
			/***************************************************************************/
			ProfilDiplomeDAO pdao = new ProfilDiplomeDAO();
			String dip1 = request.getParameter("nomdiplome1");
			int numpd1 = 0;
			if (!dip1.equals("") && !dip1.equals(null)) {
				ProfilDiplome pd = new ProfilDiplome(nume, dip1, request.getParameter("diplomeobtenu1"),
						request.getParameter("anneeobtention1"));
				try {
					numpd1 = pdao.create(pd);
					// System.out.println(numpd1);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String dip2 = request.getParameter("nomdiplome2");
			int numpd2 = 0;
			if (!dip2.equals("") && !dip2.equals(null)) {
				ProfilDiplome pd = new ProfilDiplome(nume, dip2, request.getParameter("diplomeobtenu2"),
						request.getParameter("anneeobtention2"));
				try {
					numpd2 = pdao.create(pd);
					// System.out.println(numpd2);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String dip3 = request.getParameter("nomdiplome3");
			int numpd3 = 0;
			if (!dip3.equals("") && !dip3.equals(null)) {
				ProfilDiplome pd = new ProfilDiplome(nume, dip3, request.getParameter("diplomeobtenu3"),
						request.getParameter("anneeobtention3"));
				try {
					numpd3 = pdao.create(pd);
					// System.out.println(numpd3);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String dip4 = request.getParameter("nomdiplome4");
			int numpd4 = 0;
			if (!dip4.equals("") && !dip4.equals(null)) {
				ProfilDiplome pd = new ProfilDiplome(nume, dip4, request.getParameter("diplomeobtenu4"),
						request.getParameter("anneeobtention4"));
				try {
					numpd4 = pdao.create(pd);
					// System.out.println(numpd4);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String dip5 = request.getParameter("nomdiplome5");
			int numpd5 = 0;
			if (!dip5.equals("") && !dip5.equals(null)) {
				ProfilDiplome pd = new ProfilDiplome(nume, dip5, request.getParameter("diplomeobtenu5"),
						request.getParameter("anneeobtention5"));
				try {
					numpd5 = pdao.create(pd);
					// System.out.println(numpd5);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			/***************************************************************************/
			/* partie locomotion */
			/***************************************************************************/
			ProfilLocomotionDAO pl = new ProfilLocomotionDAO();
			String[] locomotions = request.getParameterValues("loco");
			// String voiture=request.getParameter("voiture");
			// String velo=request.getParameter("velo");
			if (locomotions != null) {
				for (int i = 0; i < locomotions.length; i++) {
					ProfilLocomotion pro1 = new ProfilLocomotion(locomotions[i], nume);
					try {
						int cle = pl.create(pro1);
						// System.out.println(cle);
					} catch (DAOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			/**************************************************************************/
			/* partie permis professionnel */
			/**************************************************************************/
			ProfilPermisProDAO profilpermis = new ProfilPermisProDAO();

			// on recupere les dates saisies pour chaque permis
			java.sql.Date datc = null, datd = null, datec = null, datcar = null, datcac = null, datfim = null,
					datfco = null, datapt = null;
			String perc = request.getParameter("expirationC");
			if (!perc.equals("") && !perc.equals(null)) {
				datc = new FormaterDate().changeFormatChaineDate(perc);
				ProfilPermisPro ppp = new ProfilPermisPro("C", une, datc);
				try {
					int cle = profilpermis.create(ppp);
					// System.out.println(cle);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			String perd = request.getParameter("expirationD");
			if (!perd.equals("") && !perd.equals(null)) {
				datd = new FormaterDate().changeFormatChaineDate(perd);
				ProfilPermisPro ppp = new ProfilPermisPro("D", une, datd);
				try {
					int cle = profilpermis.create(ppp);
					// System.out.println(cle);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			String perec = request.getParameter("expirationEC");
			if (!perec.equals("") && !perec.equals(null)) {
				datec = new FormaterDate().changeFormatChaineDate(perec);
				ProfilPermisPro ppp = new ProfilPermisPro("E(C)", une, datec);
				try {
					int cle = profilpermis.create(ppp);
					// System.out.println(cle);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String percar = request.getParameter("expirationCariste");
			if (!percar.equals("") && !percar.equals(null)) {
				datcar = new FormaterDate().changeFormatChaineDate(percar);
				ProfilPermisPro ppp = new ProfilPermisPro("CARISTE", une, datcar);
				try {
					int cle = profilpermis.create(ppp);
					// System.out.println(cle);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String percac = request.getParameter("expirationCaces");
			if (!percac.equals("") && !percac.equals(null)) {
				datcac = new FormaterDate().changeFormatChaineDate(percac);
				ProfilPermisPro ppp = new ProfilPermisPro("CACES", une, datcac);
				try {
					int cle = profilpermis.create(ppp);
					// System.out.println(cle);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			String perfim = request.getParameter("expirationFimo");
			if (!perfim.equals("") && !perfim.equals(null)) {
				datfim = new FormaterDate().changeFormatChaineDate(perfim);
				ProfilPermisPro ppp = new ProfilPermisPro("FIMO", une, datfim);
				try {
					int cle = profilpermis.create(ppp);
					// System.out.println(cle);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String perfco = request.getParameter("expirationFcos");
			if (!perfco.equals("") && !perfco.equals(null)) {
				datfco = new FormaterDate().changeFormatChaineDate(perfco);
				ProfilPermisPro ppp = new ProfilPermisPro("FCOS", une, datfco);
				try {
					int cle = profilpermis.create(ppp);
					// System.out.println(cle);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String perapt = request.getParameter("expirationApth");
			if (!perapt.equals("") && !perapt.equals(null)) {
				datapt = new FormaterDate().changeFormatChaineDate(perapt);
				ProfilPermisPro ppp = new ProfilPermisPro("APTH", une, datapt);
				try {
					int cle = profilpermis.create(ppp);
					// System.out.println(cle);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			/**************************************************************************/
			/* partie priorite */
			/**************************************************************************/
			ProfilPrioriteDAO profilprio = new ProfilPrioriteDAO();
			String[] priorites = request.getParameterValues("prio");

			if (priorites != null) {
				// r�cup�ration des 3 dates possibles dans priorit�s
				String daterth = request.getParameter("expirerth");
				String datersasocle = request.getParameter("datesocle");
				String datersachapeau = request.getParameter("datechapeau");
				java.sql.Date madaterth = null;
				java.sql.Date madatersasocle = null;
				java.sql.Date madatersachapeau = null;

				if (!daterth.equals("") && !daterth.equals("null")) {
					madaterth = new FormaterDate().changeFormatChaineDate(daterth);
				}

				if (!datersasocle.equals("") && !datersasocle.equals("null")) {
					madatersasocle = new FormaterDate().changeFormatChaineDate(datersasocle);
				}

				if (!datersachapeau.equals("") && !datersachapeau.equals("null")) {
					madatersachapeau = new FormaterDate().changeFormatChaineDate(datersachapeau);
				}
				for (int i = 0; i < priorites.length; i++) {
					ProfilPriorite profp = null;
					if (priorites[i].equals("RTH") || priorites[i].equals("RSA-SOCLE")
							|| priorites[i].equals("RSA-CHAPEAU")) {
						if (priorites[i].equals("RTH")) {
							profp = new ProfilPriorite(priorites[i], nume, madaterth);
						} else if (priorites[i].equals("RSA-SOCLE")) {
							profp = new ProfilPriorite(priorites[i], nume, madatersasocle);
						} else {
							profp = new ProfilPriorite(priorites[i], nume, madatersachapeau);
						}
					}

					else {
						profp = new ProfilPriorite(priorites[i], nume);
					}
					try {
						int cle = profilprio.create(profp);
						// System.out.println(cle);
					} catch (DAOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			/**************************************************************************/
			/* partie recherche emploi */
			/**************************************************************************/
			ProfilRechercheDAO profilrech = new ProfilRechercheDAO();
			RomeDAO rda = new RomeDAO();
			String empl1 = request.getParameter("emploirecherche1");

			if (!empl1.equals("") && !empl1.equals(null)) {
				empl1 = new FormaterTexte().supprimerTableMatiere(empl1);
				Rome rome1 = null;
				try {
					rome1 = rda.findByName(empl1);
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ProfilRecherche pr1 = new ProfilRecherche(une, rome1);
				try {
					int cleem1 = profilrech.create(pr1);
					// System.out.println(cleem1);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			String empl2 = request.getParameter("emploirecherche2");

			if (!empl2.equals("") && !empl2.equals(null)) {
				empl2 = new FormaterTexte().supprimerTableMatiere(empl2);
				Rome rome2 = null;
				try {
					rome2 = rda.findByName(empl2);
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ProfilRecherche pr2 = new ProfilRecherche(une, rome2);
				try {
					int cleem2 = profilrech.create(pr2);
					// System.out.println(cleem2);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String empl3 = request.getParameter("emploirecherche3");

			if (!empl3.equals("") && !empl3.equals(null)) {
				empl3 = new FormaterTexte().supprimerTableMatiere(empl3);
				Rome rome3 = null;
				try {
					rome3 = rda.findByName(empl3);
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ProfilRecherche pr3 = new ProfilRecherche(une, rome3);
				try {
					int cleem3 = profilrech.create(pr3);
					// System.out.println(cleem3);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String empl4 = request.getParameter("emploirecherche4");

			if (!empl4.equals("") && !empl4.equals(null)) {
				empl4 = new FormaterTexte().supprimerTableMatiere(empl4);
				Rome rome4 = null;
				try {
					rome4 = rda.findByName(empl4);
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ProfilRecherche pr4 = new ProfilRecherche(une, rome4);
				try {
					int cleem4 = profilrech.create(pr4);
					// System.out.println(cleem4);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String empl5 = request.getParameter("emploirecherche5");

			if (!empl5.equals("") && !empl5.equals(null)) {
				empl5 = new FormaterTexte().supprimerTableMatiere(empl5);
				Rome rome5 = null;
				try {
					rome5 = rda.findByName(empl5);
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ProfilRecherche pr5 = new ProfilRecherche(une, rome5);
				try {
					int cleem5 = profilrech.create(pr5);
					// System.out.println(cleem5);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			String empl6 = request.getParameter("emploirecherche6");

			if (!empl6.equals("") && !empl6.equals(null)) {
				empl6 = new FormaterTexte().supprimerTableMatiere(empl6);
				Rome rome6 = null;
				try {
					rome6 = rda.findByName(empl6);
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ProfilRecherche pr6 = new ProfilRecherche(une, rome6);
				try {
					int cleem6 = profilrech.create(pr6);
					// System.out.println(cleem1);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			String empl7 = request.getParameter("emploirecherche7");

			if (!empl7.equals("") && !empl7.equals(null)) {
				empl7 = new FormaterTexte().supprimerTableMatiere(empl7);
				Rome rome7 = null;
				try {
					rome7 = rda.findByName(empl7);
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ProfilRecherche pr7 = new ProfilRecherche(une, rome7);
				try {
					int cleem7 = profilrech.create(pr7);
					// System.out.println(cleem1);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			String empl8 = request.getParameter("emploirecherche8");

			if (!empl8.equals("") && !empl8.equals(null)) {
				empl8 = new FormaterTexte().supprimerTableMatiere(empl8);
				Rome rome8 = null;
				try {
					rome8 = rda.findByName(empl8);
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ProfilRecherche pr8 = new ProfilRecherche(une, rome8);
				try {
					int cleem8 = profilrech.create(pr8);
					// System.out.println(cleem1);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			String empl9 = request.getParameter("emploirecherche9");

			if (!empl9.equals("") && !empl9.equals(null)) {
				empl9 = new FormaterTexte().supprimerTableMatiere(empl9);
				Rome rome9 = null;
				try {
					rome9 = rda.findByName(empl9);
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ProfilRecherche pr9 = new ProfilRecherche(une, rome9);
				try {
					int cleem9 = profilrech.create(pr9);
					// System.out.println(cleem1);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			String empl10 = request.getParameter("emploirecherche10");

			if (!empl10.equals("") && !empl10.equals(null)) {
				empl10 = new FormaterTexte().supprimerTableMatiere(empl10);
				Rome rome10 = null;
				try {
					rome10 = rda.findByName(empl10);
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ProfilRecherche pr10 = new ProfilRecherche(une, rome10);
				try {
					int cleem10 = profilrech.create(pr10);
					// System.out.println(cleem1);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			jspPage = "/jsp/accueil/afficheInscrit.jsp?numero=" + nume;

		}

		else if ("supprimerechercheemploi".equals(action)) {
			String nume = request.getParameter("id");
			String emploi = request.getParameter("emploi");
			ProfilRechercheDAO prodao = new ProfilRechercheDAO();
			int sup = 0;
			try {
				sup = prodao.delete(Long.parseLong(emploi));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/accueil/afficheInscrit.jsp?numero=" + nume;

		}

		/********************************************************************************************/
		/***** afficher statistiques accueil *********/
		/********************************************************************************************/

		// action afficher statistiques dans Identite
		else if ("statIdentite".equals(action)) {
			// r�cuperation des parametres du formulaire
			java.sql.Date debut = null, fin = null;
			String d = request.getParameter("datedebutrech");
			if (!d.equals(null) & !d.equals(""))
				debut = new FormaterDate().changeFormatChaineDate(d);
			request.setAttribute("debut", debut);

			// System.out.print("date debut =" + debut);
			String f = request.getParameter("datefinrech");
			if (!f.equals(null) & !f.equals(""))
				fin = new FormaterDate().changeFormatChaineDate(f);
			request.setAttribute("fin", fin);

			String sexe = request.getParameter("sexe");
			request.setAttribute("sexe", sexe);

			String age = request.getParameter("age");
			request.setAttribute("age", age);

			String formation = request.getParameter("niveauformation");
			request.setAttribute("niveauformation", formation);

			String pe = request.getParameter("pole");
			request.setAttribute("pole", pe);

			String prio = request.getParameter("listepriorites");
			request.setAttribute("listepriorites", prio);

			String origine = request.getParameter("origine");
			request.setAttribute("origine", origine);

			IdentiteDAO personne = new IdentiteDAO();
			List<Identite> liste = new ArrayList<Identite>();

			try {
				liste = personne.afficherStatistiques(debut, fin, sexe, age, formation, pe, prio, origine);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("listestat", liste);

			jspPage = "/jsp/stat/accueil/listingAccueil.jsp";

		}
		/************************************************************************************/
		/* statistiques en % */

		else if ("stataccueilpourcent".equals(action)) {
			// r�cuperation des parametres du formulaire
			java.sql.Date debut = null, fin = null;
			String d = request.getParameter("datedebutrech");
			if (!d.equals(null) & !d.equals(""))
				debut = new FormaterDate().changeFormatChaineDate(d);
			request.setAttribute("debut", debut);

			// System.out.print("date debut =" + debut);
			String f = request.getParameter("datefinrech");
			if (!f.equals(null) & !f.equals(""))
				fin = new FormaterDate().changeFormatChaineDate(f);
			request.setAttribute("fin", fin);

			IdentiteDAO identitedao = new IdentiteDAO();
			Identite personne = new Identite();
			personne.setDateAccueil_IDE(debut);
			List<Identite> listePersonnes = null;
			try {
				listePersonnes = identitedao.afficherStatistiquesPourCent(debut, fin);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			/*
			 * ProfilPrioriteDAO profildao = new ProfilPrioriteDAO();
			 * ProfilPriorite profil = new ProfilPriorite();
			 * List<ProfilPriorite> listeprofil = null; for (int i = 0; i <
			 * listePersonnes.size(); i++) try { listeprofil =
			 * (List<ProfilPriorite>) profildao
			 * .findByID(listePersonnes.get(i).getId_IDE()); } catch
			 * (DAOException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */

			request.setAttribute("listestat", listePersonnes);

			jspPage = "/jsp/stat/accueil/listingAccueilpourcent.jsp";

		}
		/* _________________________________________________________________________________________________ */
		/****************************************************************************************************/

		/****************************************************************************************************/
		/************ partie formation *************************/
		/***************************************************************************************************/
		/***************************************************************************************************/

		/* _________________________________________________________________________________________________ */

		else if ("modifierorganismeformation".equals(action)) {
			String numero = request.getParameter("numero");
			int num = Integer.parseInt(numero);
			boolean act = false;
			String nom = request.getParameter("nom");

			String adr = request.getParameter("adresse");
			String cp = request.getParameter("cp");
			String ville = request.getParameter("ville");
			String phone = request.getParameter("phone");
			String actif = request.getParameter("actif");
			if (actif.equals("OUI"))
				act = true;
			OrganismeFormation of = new OrganismeFormation(num, nom, adr, cp, ville, phone, act);
			OrganismeFormationDAO ofdao = new OrganismeFormationDAO();
			try {
				ofdao.update(of);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/formation/listeOrganismeFormation.jsp";
		}
		/****************************************************************************/

		else if ("modifierformation".equals(action)) {
			// r�cuperation des parametres du formulaire
			java.sql.Date debut = null, fin = null;
			String d = request.getParameter("debut");
			if (!d.equals(null) && !d.equals("null") && !d.equals(""))
				debut = new FormaterDate().changeFormatChaineDate(d);

			String f = request.getParameter("fin");
			if (!f.equals(null) && !f.equals("null") && !f.equals(""))
				fin = new FormaterDate().changeFormatChaineDate(f);

			String numero = request.getParameter("numero");
			int num = Integer.parseInt(numero);

			String etat = request.getParameter("etat");

			/*
			 * String idorg=request.getParameter("idorg"); int
			 * numorg=Integer.parseInt(idorg);
			 * System.out.println("id organisme ="+numorg);
			 */
			boolean act = false;
			String nom = request.getParameter("nom");

			String organisme = request.getParameter("nomorganismeformation");
			// System.out.println("organisme =" + organisme);
			int der = organisme.lastIndexOf(")");
			int avantder = organisme.lastIndexOf("(", der);
			String ch = organisme.substring(avantder + 1, der);
			// System.out.println("test code =" + ch);

			String niveau = request.getParameter("niveauqualification");
			String h = request.getParameter("heure");
			short heure = Short.parseShort(h);
			String actif = request.getParameter("actif");
			String nomtheme = request.getParameter("nomtheme");
			if (actif.equals("OUI"))
				act = true;

			OrganismeFormation of = new OrganismeFormation();
			OrganismeFormationDAO ofdao = new OrganismeFormationDAO();

			ThemeFormationDAO thdao = new ThemeFormationDAO();
			Theme autre = null;
			try {
				autre = thdao.findByName(nomtheme);
			} catch (DAOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			try {
				of = ofdao.findByID(new Integer(ch));
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			ListeFormationsDAO lfdao = new ListeFormationsDAO();
			ListeFormations lsf = null;
			try {
				lsf = lfdao.findByID(num);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			String nomdebut = lsf.getFormation().substring(0, 5);
			nom = nomdebut + nom;
			try {
				lfdao.update(new ListeFormations(num, nom, of, niveau, debut, fin, heure, act, etat,
						lsf.getSession_form(), autre));
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/formation/listeFormations.jsp";
		}

		/*****************************************************************************/
		/* action correspondant a une recherche par nom */

		else if ("formationrecherchernom".equals(action)) {
			String nom = request.getParameter("numero");

			IdentiteDAO personne = new IdentiteDAO();
			List<Identite> liste = new ArrayList<Identite>();
			try {
				liste = personne.findByName(nom);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("resultset", liste);

			jspPage = "/jsp/formation/listingIdentite.jsp";
		}

		/*****************************************************************************/
		/* action correspondant a une recherche par nom et prenom */

		else if ("formationrecherchernomprenom".equals(action)) {
			String nom = request.getParameter("numero");

			IdentiteDAO personne = new IdentiteDAO();
			Identite une = null;
			try {
				une = personne.findByID(Integer.valueOf(nom));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			List<Identite> liste = new ArrayList<Identite>();
			try {
				liste = personne.findByName2(une.getNom_IDE() + " " + une.getPrenom_IDE());
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("resultset", liste);

			jspPage = "/jsp/formation/listingIdentite.jsp";
		}

		/*****************************************************************************/
		/* creer une nouvelle formation */

		else if ("creerformation".equals(action)) {
			String numero = request.getParameter("numero");
			String nom = request.getParameter("intitule");
			String ses1 = null, ses2 = null, ses3 = null;
			// System.out.println("nom formation =" + nom);
			String niveau = request.getParameter("niveauqualification");
			String nomtheme = request.getParameter("nomtheme");
			String org = request.getParameter("nomorganismeformation");
			// on recupere l'id de l'organisme
			int der = org.lastIndexOf(")");
			int avantder = org.lastIndexOf("(", der);
			String numorganisme = org.substring(avantder + 1, der);

			String anime = request.getParameter("animatriceformation");
			String he = request.getParameter("heureformation");
			short heure = Short.parseShort(he);

			java.sql.Date debut = null, fin = null;
			String deb = request.getParameter("datedebutformation");
			if (!deb.equals("") && !deb.equals(null)) {
				debut = new FormaterDate().changeFormatChaineDate(deb);
				ses1 = (String) debut.toString().subSequence(0, 4);
			}

			String fi = request.getParameter("datefinformation");
			if (!fi.equals("") && !fi.equals(null)) {
				fin = new FormaterDate().changeFormatChaineDate(fi);
				ses2 = (String) fin.toString().subSequence(0, 4);
			}
			if (!ses1.equals(ses2))
				ses3 = ses1 + "-" + ses2;
			else
				ses3 = ses1;

			ThemeFormationDAO themdao = new ThemeFormationDAO();
			Theme theme = null;
			try {
				theme = themdao.findByName(nomtheme);
			} catch (DAOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			OrganismeFormation of = new OrganismeFormation();
			OrganismeFormationDAO ofdao = new OrganismeFormationDAO();

			try {
				of = ofdao.findByID(new Integer(numorganisme));

			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			ListeFormations formation = new ListeFormations(nom, of, niveau, debut, fin, heure, true, "FUTURE", ses3,
					theme);
			ListeFormationsDAO formationdao = new ListeFormationsDAO();
			try {
				formationdao.create(formation);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (numero.equals("null") || numero == null)
				jspPage = "/jsp/formation/listeFormations.jsp";
			else
				jspPage = "/jsp/formation/preinscription.jsp?numero=" + numero;

		}

		/*****************************************************************************/
		/* creer un nouvel organisme de formation */

		else if ("creerorganismeformation".equals(action)) {
			String nom = request.getParameter("of");
			String adresse = request.getParameter("adresse");
			String cp = request.getParameter("cp");
			String ville = request.getParameter("ville");
			String phone = request.getParameter("phone");
			OrganismeFormationDAO orgdao = new OrganismeFormationDAO();
			OrganismeFormation org = new OrganismeFormation(nom, adresse, cp, ville, phone, true);
			try {
				orgdao.create(org);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/formation/creernouvelleformation.jsp";

		}

		else if ("archiverorganismeformation".equals(action)) {
			String numero = request.getParameter("numero");
			int num = Integer.parseInt(numero);
			OrganismeFormationDAO orgdao = new OrganismeFormationDAO();
			OrganismeFormation org = null;
			try {
				org = orgdao.findByID(num);
				org.setActif(false);
				orgdao.update(org);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			jspPage = "/jsp/formation/listeOrganismeFormation.jsp";

		}
		/* on enregistre une preinscription */
		else if ("enregistrerpreinscriptionformation".equals(action)) {
			FormaterDate jour = new FormaterDate();
			java.sql.Date inscrip = null;
			int numInscription = 0;
			/*
			 * si la date d'inscription n'est pas saisie on met la date du jour
			 */
			/*
			 * String dateinscriptionformation = request
			 * .getParameter("dateinscriptionformation");
			 * 
			 * if (dateinscriptionformation.equals("") ||
			 * dateinscriptionformation.equals(null)) {
			 */
			String d = jour.getSortie();
			inscrip = new FormaterDate().changeFormatChaineDate(d);
			/*
			 * } else inscrip = java.sql.Date.valueOf(dateinscriptionformation);
			 */
			String id_identite = request.getParameter("numero");
			IdentiteDAO iddao = new IdentiteDAO();
			Identite identite = null;
			try {
				identite = iddao.findByID(Integer.parseInt(id_identite));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			String prescripteurformation = request.getParameter("prescripteurformation");
			String tempsaccueilformation = request.getParameter("tempsaccueilformation");
			String animatriceformation = request.getParameter("animatriceformation");
			AnimatriceDAO animdao = new AnimatriceDAO();
			Animatrice anim = null;

			try {
				anim = animdao.findByName(animatriceformation);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String nomformation = request.getParameter("nomformation");
			// System.out.println(nomformation);
			ListeFormationsDAO formdao = new ListeFormationsDAO();

			ListeFormations formation = null;
			String ch = null;

			// if (!nomformation.equals("AUCUNE")) {
			try {
				int der = nomformation.lastIndexOf(")");
				int avantder = nomformation.lastIndexOf("(", der);
				ch = nomformation.substring(avantder + 1, der);
				// System.out.println("test code =" + ch);
			} catch (StringIndexOutOfBoundsException e) {
				e.printStackTrace();
			}

			try {
				formation = formdao.findByID(Integer.parseInt(ch));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
				formation = null;
			}

			/*
			 * } else { try { formation = formdao.findByName("AUCUNE"); } catch
			 * (DAOException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); } }
			 */

			String explication = request.getParameter("explication");
			// System.out.println("les commentaires =" + explication);
			explication = explication.trim();
			boolean enregistre = false;
			if (!nomformation.startsWith("AUCUNE"))
				enregistre = true;

			PreInscriptionDAO preindao = new PreInscriptionDAO();
			List<PreInscription> listeprein = null;
			try {
				listeprein = preindao.recupereListePreinscription(identite);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			PreInscription preins = null;
			boolean dejainscrit = false;
			for (int i = 0; i < listeprein.size(); i++) {
				if (listeprein.get(i).getListe().getId_pformation() == formation.getId_pformation()
						&& listeprein.get(i).getDate_pyramide().equals(inscrip)) {
					dejainscrit = true;
					break;
				}
			}
			if (!dejainscrit) {
				preins = new PreInscription(identite, inscrip, tempsaccueilformation, anim, explication,
						prescripteurformation, formation, enregistre);
				try {
					numInscription = preindao.create(preins);
				} catch (DAOException e) {
					e.printStackTrace();
				}
			}
			if (!nomformation.startsWith("AUCUNE"))
				jspPage = "/jsp/formation/listeenregistresparformation.jsp?numeroformation=" + ch;

			else
				jspPage = "/jsp/accueil/afficheInscrit.jsp?numero=" + id_identite;
		}

		/* onmodifie une personne une preinscription enregistree */
		else if ("modifierenregistrementpersonne".equals(action)) {
			String numeroformation = request.getParameter("numero");
			String idperso = request.getParameter("identite");
			int idpersonne = Integer.parseInt(idperso);

			IdentiteDAO identdao = new IdentiteDAO();
			Identite identite = null;
			try {
				identite = identdao.findByID(idpersonne);
			} catch (DAOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			// on recupere la formation
			PreInscriptionDAO predao = new PreInscriptionDAO();
			PreInscription recup = null;
			try {
				recup = predao.findByID(Integer.parseInt(numeroformation));
			} catch (NumberFormatException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (DAOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			String prescripteurformation = request.getParameter("prescripteurformation");
			String tempsaccueilformation = request.getParameter("tempsaccueilformation");
			String animatriceformation = request.getParameter("animatriceformation");
			AnimatriceDAO animdao = new AnimatriceDAO();
			Animatrice anim = null;

			try {
				anim = animdao.findByName(animatriceformation);

			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String nomformation = request.getParameter("nomformation");
			// System.out.println(nomformation);
			ListeFormationsDAO formdao = new ListeFormationsDAO();
			ListeFormations formation = null;
			String ch = null;
			// if (!nomformation.equals("AUCUNE")) {
			try {
				int der = nomformation.lastIndexOf(")");
				int avantder = nomformation.lastIndexOf("(", der);
				ch = nomformation.substring(avantder + 1, der);
				// System.out.println("test code =" + ch);
			} catch (StringIndexOutOfBoundsException e) {
				e.printStackTrace();
			}

			try {
				formation = formdao.findByID(Integer.parseInt(ch));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
				formation = null;
			}
			/*
			 * } else { try { formation = formdao.findByName("AUCUNE"); } catch
			 * (DAOException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); } }
			 */

			String explication = request.getParameter("explication");
			// System.out.println("les commentaires =" + explication);
			explication = explication.trim();
			boolean enregistre = false;
			if (!nomformation.startsWith("AUCUNE"))
				enregistre = true;

			// System.out.println("numero formation=" + numeroformation);

			try {
				recup = predao.update(new PreInscription(Integer.parseInt(numeroformation), identite,
						recup.getDate_pyramide(), tempsaccueilformation, anim, explication, null, null,
						prescripteurformation, null, formation, false, enregistre));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (!nomformation.startsWith("AUCUNE"))
				jspPage = "/jsp/formation/listeenregistresparformation.jsp?numeroformation=" + ch
				// + "&nom="
				// + nomformation
				;

			else
				jspPage = "/jsp/accueil/rechercherPersonne.jsp";// /index.jsp;"

		}

		/* on affiche tous les inscrits a un e formation */
		else if ("listeinscritsparformation".equals(action)) {
			String nomformation = request.getParameter("nomformation");
			// System.out.println("nom formation " + nomformation);
			// String numero=request.getParameter(arg0)
			ListeFormationsDAO formdao = new ListeFormationsDAO();
			ListeFormations formation = null;
			String ch = null;
			try {
				int der = nomformation.lastIndexOf(")");
				int avantder = nomformation.lastIndexOf("(", der);
				ch = nomformation.substring(avantder + 1, der);
				// System.out.println("test code =" + ch);
			} catch (StringIndexOutOfBoundsException e) {
				e.printStackTrace();
			}

			try {
				formation = formdao.findByID(Integer.parseInt(ch));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
				formation = null;
			}

			jspPage = "/jsp/formation/listeinscritsparformation.jsp?numeroformation=" + ch;
		}

		/* on affiche tous les enregistr�s a une formation */
		else if ("listeenregistresparformation".equals(action)) {
			String nomformation = request.getParameter("nomformation");
			// System.out.println("nom formation " + nomformation);
			// String numero=request.getParameter(arg0)
			ListeFormationsDAO formdao = new ListeFormationsDAO();
			ListeFormations formation = null;
			String ch = null;
			try {
				int der = nomformation.lastIndexOf(")");
				int avantder = nomformation.lastIndexOf("(", der);
				ch = nomformation.substring(avantder + 1, der);
				// System.out.println("test code =" + ch);
			} catch (StringIndexOutOfBoundsException e) {
				e.printStackTrace();
			}

			try {
				formation = formdao.findByID(Integer.parseInt(ch));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
				formation = null;
			}

			jspPage = "/jsp/formation/listeenregistresparformation.jsp?numeroformation=" + ch;
		}
		/* on valide l'inscription d'une personne enregistree a une formation */

		else if ("validerinscriptionformation".equals(action)) {
			String personne = request.getParameter("numero");

			String nomformation = request.getParameter("nomformation");
			// System.out.println("nom formation =" + nomformation);
			String nomorganismeformationconcerne = request.getParameter("nomorganismeformationconcerne");
			String interlocuteur = request.getParameter("interlocuteur");
			IdentiteDAO persdao = new IdentiteDAO();
			Identite pers = null;
			try {
				pers = persdao.findByID(Integer.parseInt(personne));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			ListeFormationsDAO formdao = new ListeFormationsDAO();
			ListeFormations formation = null;
			String ch = null;
			try {
				int der = nomformation.lastIndexOf(")");
				int avantder = nomformation.lastIndexOf("(", der);
				ch = nomformation.substring(avantder + 1, der);
				// System.out.println("test code =" + ch);
			} catch (StringIndexOutOfBoundsException e) {
				e.printStackTrace();
			}

			try {
				formation = formdao.findByID(Integer.parseInt(ch));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
				formation = null;
			}

			String explication = request.getParameter("explication");
			// System.out.println("les commentaires =" + explication);
			explication = explication.trim();
			boolean inscrit = false;
			if (!nomformation.startsWith("AUCUNE"))
				inscrit = true;

			PreInscriptionDAO predao = new PreInscriptionDAO();
			/*
			 * on recupere l'id de l'inscription qui est unique sur les champs
			 * identite et formation
			 */
			PreInscription recup = null;
			try {
				recup = predao.recuperePreinscription(pers, formation);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			recup.setInterlocuteur(interlocuteur);
			recup.setCommentaires(recup.getCommentaires() + "\n" + explication);
			recup.setInscrit(true);

			/*
			 * PreInscriptionDAO predao =new PreInscription();
			 * recup.setIdentite(pers); recup.setListe(formation);
			 * List<PreInscription> encours = null; try { encours =
			 * predao.findByCriteria(recup); } catch (DAOException e1) { // TODO
			 * Auto-generated catch block e1.printStackTrace(); }
			 * 
			 * 
			 * PreInscription prei = new PreInscription(encours.get(0)
			 * .getId_formation(), pers, encours.get(0).getDate_pyramide(),
			 * encours.get(0) .getTempsacc_pyramide(), encours.get(0)
			 * .getAnimatrice(), encours.get(0).getCommentaires() + "\n" +
			 * explication, encours.get(0) .getRais_abandon(),
			 * encours.get(0).getDateAbandon(), encours.get(0)
			 * .getPrescripteur(), interlocuteur, formation, inscrit,
			 * encours.get(0).isEnregistre());
			 * 
			 * PreInscription finale = null;
			 * 
			 * try { finale = predao.update(prei); } catch (DAOException e) { //
			 * TODO Auto-generated catch block e.printStackTrace(); }
			 */
			try {
				recup = predao.update(recup);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/formation/listeinscritsparformation.jsp?numeroformation=" + ch;

		}

		/*
		 * on modifie l'inscription d'une personne enregistree a une formation
		 */

		else if ("modifierinscriptionformation".equals(action)) {
			String nu = request.getParameter("numero");
			int num = Integer.parseInt(nu);
			FormaterDate jour = new FormaterDate();
			java.sql.Date abandon = null;

			String raison = request.getParameter("raison");

			/* si la date d'inscription n'est pas saisie pas de modification */
			String dateabandonformation = request.getParameter("dateabandonformation");

			if (dateabandonformation != "")
				abandon = new FormaterDate().changeFormatChaineDate(dateabandonformation);

			PreInscriptionDAO preindao = new PreInscriptionDAO();
			PreInscription preins = null;
			try {
				preins = preindao.findByID(num);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			IdentiteDAO persdao = new IdentiteDAO();
			Identite pers = null;
			try {
				pers = persdao.findByID(preins.getIdentite().getId_IDE());
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			AnimatriceDAO animdao = new AnimatriceDAO();
			Animatrice anime = null;
			try {
				anime = animdao.findByID(preins.getAnimatrice().getId_animatrice());
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			ListeFormationsDAO lsdao = new ListeFormationsDAO();
			ListeFormations formation = null;
			try {
				formation = lsdao.findByID(preins.getListe().getId_pformation());
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			PreInscription tampon = new PreInscription(preins.getId_formation(), pers, preins.getDate_pyramide(),
					preins.getTempsacc_pyramide(), anime, preins.getCommentaires(), raison, abandon,
					preins.getPrescripteur(), preins.getInterlocuteur(), formation, preins.isInscrit(),
					preins.isEnregistre());
			// PreInscription finale=null;
			try {
				preins = preindao.update(tampon);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/formation/listeinscritsparformation.jsp?numeroformation="
					+ preins.getListe().getId_pformation();

		}

		// liste des formations par annees pyramide septembre a fin aout
		else if ("statformationpardate".equals(action)) {
			java.sql.Date debut = null, fin = null;
			String annee = request.getParameter("datedebutformationstat");
			String debutannee = annee + "-09-01";

			Integer an = new Integer(annee);
			int ansuivant = an + 1;
			String finannee = ansuivant + "-08-31";

			debut = new FormaterDate().changeFormatChaineDate(debutannee);
			// System.out.println("deb=" + debutannee);
			request.setAttribute("debut", debut);

			fin = new FormaterDate().changeFormatChaineDate(finannee);
			// System.out.println("fin=" + finannee);
			request.setAttribute("fin", fin);

			ListeFormationsDAO lsdao = new ListeFormationsDAO();
			ListeFormations liste = new ListeFormations();
			liste.setDatedeb_form(debut);
			liste.setDatefin_form(fin);
			List<ListeFormations> listeformations = null;
			try {
				listeformations = lsdao.findByCriteria(liste);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.setAttribute("liste", listeformations);

			jspPage = "/jsp/stat/formation/listedesformationspardates.jsp";
		}

		// liste des formations par annees civiles janvier a decembre
		else if ("statformationpardatecivile".equals(action)) {
			java.sql.Date debut = null, fin = null;
			String annee = request.getParameter("datedebutformationstat");
			// on demarre la recherche au 1/1
			String debutannee = annee + "-01-01";

			String finannee = annee + "-12-31";

			debut = new FormaterDate().changeFormatChaineDate(debutannee);
			// System.out.println("deb=" + debutannee);
			request.setAttribute("debut", debut);

			fin = new FormaterDate().changeFormatChaineDate(finannee);
			// System.out.println("fin=" + finannee);
			request.setAttribute("fin", fin);

			ListeFormationsDAO lsdao = new ListeFormationsDAO();
			ListeFormations liste = new ListeFormations();
			liste.setDatedeb_form(debut);
			liste.setDatefin_form(fin);
			List<ListeFormations> listeformations = null;
			try {
				listeformations = lsdao.findByCriteria(liste);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.setAttribute("liste", listeformations);

			jspPage = "/jsp/stat/formation/listedesformationspardates.jsp";
		}

		// statisques par annee pyramide en %
		else if ("statformationpourcent".equals(action)) {
			java.sql.Date debut = null, fin = null;
			String annee = request.getParameter("datedebutrech");
			String debutannee = annee + "-09-01";

			Integer an = new Integer(annee);
			int ansuivant = an + 1;
			String finannee = ansuivant + "-08-31";

			debut = new FormaterDate().changeFormatChaineDate(debutannee);
			// System.out.println("deb=" + debutannee);
			request.setAttribute("debut", debut);

			fin = new FormaterDate().changeFormatChaineDate(finannee);
			// System.out.println("fin=" + finannee);
			request.setAttribute("fin", fin);

			ListeFormationsDAO lsdao = new ListeFormationsDAO();
			ListeFormations liste = new ListeFormations();
			liste.setDatedeb_form(debut);
			liste.setDatefin_form(fin);
			List<ListeFormations> listeformations = null;
			try {
				listeformations = lsdao.findByCriteria(liste);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.setAttribute("liste", listeformations);

			jspPage = "/jsp/stat/formation/statformationpourcent.jsp";
		}

		// statisques par annee pyramide en %
		else if ("statformationpourcentcivil".equals(action)) {
			java.sql.Date debut = null, fin = null;
			String annee = request.getParameter("datedebutrech");
			// on demarre la recherche au 1/1
			String debutannee = annee + "-01-01";

			String finannee = annee + "-12-31";

			debut = new FormaterDate().changeFormatChaineDate(debutannee);
			// System.out.println("deb=" + debutannee);
			request.setAttribute("debut", debut);

			fin = new FormaterDate().changeFormatChaineDate(finannee);
			// System.out.println("fin=" + finannee);
			request.setAttribute("fin", fin);

			ListeFormationsDAO lsdao = new ListeFormationsDAO();
			ListeFormations liste = new ListeFormations();
			liste.setDatedeb_form(debut);
			// liste.setDatefin_form(fin);
			List<ListeFormations> listeformations = null;
			try {
				listeformations = lsdao.findByCriteria(liste);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.setAttribute("liste", listeformations);

			jspPage = "/jsp/stat/formation/statformationpourcent.jsp";
		}

		// autres statisques pyramide on recherche des element sur une formation
		else if ("statformationautre".equals(action)) {
			String formation = request.getParameter("nomformation");
			// System.out.println("formation =" + formation);
			if (formation != null) {
				String ch = null;
				if (!formation.equals("Toutes")) {

					try {
						int der = formation.lastIndexOf(")");
						int avantder = formation.lastIndexOf("(", der);
						ch = formation.substring(avantder + 1, der);
						// System.out.println("test code =" + ch);
					} catch (StringIndexOutOfBoundsException e) {
						e.printStackTrace();
					}
				}
				request.setAttribute("numeroformation", ch);
			}

			String sexe = request.getParameter("sexe");
			request.setAttribute("sexe", sexe);
			String age = request.getParameter("age");
			request.setAttribute("age", age);
			String niveau = request.getParameter("niveauformation");
			request.setAttribute("niveauformation", niveau);
			String pole = request.getParameter("pole");
			request.setAttribute("pole", pole);
			String origine = request.getParameter("origine");
			request.setAttribute("origine", origine);

			jspPage = "/jsp/stat/formation/autrestatformation.jsp";
		}
		/*
		 * else if ("statformationautreannee".equals(action)) { String requete =
		 * "";
		 * 
		 * Cc2rDAO cdao=new Cc2rDAO(); List<String> villes=null; try { villes =
		 * cdao.afficherVilles();
		 * 
		 * } catch (DAOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 * 
		 * 
		 * java.sql.Date debut = null, fin = null; String ddeb, dfin; String
		 * datedebutrech = request.getParameter("datedebutrech"); String annee =
		 * request.getParameter("annee"); if (annee.equals("civil")) { ddeb =
		 * datedebutrech + "-01-01"; dfin = datedebutrech + "-12-31"; } else {
		 * 
		 * Integer an = new Integer(datedebutrech); int ansuivant = an + 1; ddeb
		 * = datedebutrech + "-09-01"; dfin = ansuivant + "-08-31"; } debut =
		 * new FormaterDate().changeFormatChaineDate(ddeb);
		 * System.out.println("deb=" + debut); request.setAttribute("debut",
		 * debut);
		 * 
		 * fin = new FormaterDate().changeFormatChaineDate(dfin);
		 * System.out.println("fin=" + fin); request.setAttribute("fin", fin);
		 * 
		 * String sexe = request.getParameter("sexe"); if (!sexe.equals("Aucun")
		 * && !sexe.equals("")) { requete += " and sex_identite='" + sexe + "'";
		 * request.setAttribute("sexe", sexe); }
		 * 
		 * String age = request.getParameter("age"); if (!age.equals("vide")) {
		 * if (age.equalsIgnoreCase("moins de 25 ans")) {
		 * requete+=" and datediff(curdate(),datenais_identite) <= (365*25) "; }
		 * 
		 * else if (age.equalsIgnoreCase("de 26 à 49 ans")) { requete+
		 * =" and (datediff(curdate(),datenais_identite) > (365.25*25) and" +
		 * " datediff(curdate(),datenais_identite) <= (365.25*50)) ";
		 * 
		 * 
		 * }
		 * 
		 * 
		 * else if (age.equalsIgnoreCase("50 ans et plus")) {
		 * requete+=" and datediff(curdate(),datenais_identite) > (365.25*50) ";
		 * 
		 * } request.setAttribute("age", age); }
		 * 
		 * 
		 * String niveau = request.getParameter("niveauformation"); if
		 * (!niveau.equals("vide")) { requete = requete +
		 * " and niveauFormation_identite ='"+niveau+"' ";
		 * request.setAttribute("niveauformation", niveau); }
		 * 
		 * String pole = request.getParameter("pole"); if (!pole.equals("vide"))
		 * { if (pole.equals("Non inscrit")) {
		 * requete+=" and poleEmploiInscription_identite is null "; } else if
		 * (pole.equals("moins de 1an")) { requete+
		 * =" and datediff(curdate(),poleEmploiInscription_identite) <= 365 "; }
		 * 
		 * else if (pole.equals("plus de 1 an")) { requete+
		 * =" and datediff(curdate(),poleEmploiInscription_identite) > 365 ";
		 * 
		 * }
		 * 
		 * request.setAttribute("pole", pole);
		 * 
		 * }
		 * 
		 * 
		 * String origine = request.getParameter("origine"); if
		 * (!origine.equals("vide")) { if
		 * (origine.equalsIgnoreCase("Tarn et Garonne")) {
		 * requete+=" and cp_identite like '82%'"; } else if
		 * (origine.equalsIgnoreCase("Valence d'Agen")) {
		 * requete+=" and ville_identite like 'VALENCE D''AGEN'"; } else if
		 * (origine.equalsIgnoreCase("47")) {
		 * requete+=" and cp_identite like '47%'"; } else if
		 * (origine.equalsIgnoreCase("CC2R")) { StringBuilder liste=new
		 * StringBuilder();
		 * 
		 * for(int i=0;i<villes.size();i++){ String ville= villes.get(i);
		 * ville=new FormaterChaine().supprimerApostrophe(ville);
		 * if(i<(villes.size())-1)
		 * liste=liste.append("'").append(ville).append("',"); else
		 * liste=liste.append("'").append(ville).append("'");
		 * 
		 * }
		 * 
		 * requete+=" and ville_identite in ("+liste+") "; } else if
		 * (origine.equalsIgnoreCase("Region")) { requete+
		 * =" and cp_identite in('82%','09%','46%','12%','81%','32%','65%','31%')"
		 * ; }
		 * 
		 * 
		 * request.setAttribute("origine", origine); }
		 * request.setAttribute("req", requete);
		 * 
		 * jspPage = "/jsp/stat/formation/autrestatformationannee.jsp";
		 * 
		 * }
		 */
		else if ("statformationautreannee".equals(action)) {
			java.sql.Date debut = null, fin = null;
			String ddeb, dfin;
			String datedebutrech = request.getParameter("datedebutrech");
			String annee = request.getParameter("annee");
			if (annee.equals("civil")) {
				ddeb = datedebutrech + "-01-01";
				dfin = datedebutrech + "-12-31";
			} else {

				Integer an = new Integer(datedebutrech);
				int ansuivant = an + 1;
				ddeb = datedebutrech + "-09-01";
				dfin = ansuivant + "-08-31";
			}
			debut = new FormaterDate().changeFormatChaineDate(ddeb);
			System.out.println("deb=" + debut);
			request.setAttribute("debut", debut);

			fin = new FormaterDate().changeFormatChaineDate(dfin);
			System.out.println("fin=" + fin);
			request.setAttribute("fin", fin);

			String sexe = request.getParameter("sexe");
			request.setAttribute("sexe", sexe);
			String age = request.getParameter("age");
			request.setAttribute("age", age);
			String niveau = request.getParameter("niveauformation");
			request.setAttribute("niveauformation", niveau);
			String pole = request.getParameter("pole");
			request.setAttribute("pole", pole);
			String origine = request.getParameter("origine");
			request.setAttribute("origine", origine);

			jspPage = "/jsp/stat/formation/autrestatformationannee.jsp";
		}

		/* _________________________________________________________________________________________________ */
		/****************************************************************************************************/

		/****************************************************************************************************/
		/************
		 * partie formation professionnelle
		 *************************/
		/***************************************************************************************************/
		/***************************************************************************************************/

		/* _________________________________________________________________________________________________ */

		/* action correspondant a une recherche par nom */

		else if ("formationprorecherchernom".equals(action)) {
			String nom = request.getParameter("numero");

			IdentiteDAO personne = new IdentiteDAO();
			List<Identite> liste = new ArrayList<Identite>();
			try {
				liste = personne.findByName(nom);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("resultset", liste);

			jspPage = "/jsp/formationpro/listingIdentite.jsp";
		}

		/* action correspondant a une recherche par nom et prenom */

		else if ("formationprorecherchernomprenom".equals(action)) {
			String nom = request.getParameter("numero");

			IdentiteDAO personne = new IdentiteDAO();
			Identite une = null;
			try {
				une = personne.findByID(Integer.valueOf(nom));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			List<Identite> liste = new ArrayList<Identite>();
			try {
				liste = personne.findByName2(une.getNom_IDE() + " " + une.getPrenom_IDE());
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("resultset", liste);

			jspPage = "/jsp/formationpro/listingIdentite.jsp";
		}

		else if ("formpronouveauemployeur".equals(action)) {
			String nom = request.getParameter("nomemployeur");
			String adresse = request.getParameter("adresse");
			String cp = request.getParameter("cp");
			String ville = request.getParameter("ville");
			String tel1 = request.getParameter("fixe");
			String tel2 = request.getParameter("mobile");
			String status = request.getParameter("nomstatut");
			int num = 0;
			FormprostatutemployeurDAO statdao = new FormprostatutemployeurDAO();
			Formprostatutemployeur statut = null;
			try {
				statut = statdao.findByName(status);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			FormationProEmployeurDAO empdao = new FormationProEmployeurDAO();
			FormationProEmployeur nouveau = new FormationProEmployeur(nom, adresse, cp, ville, tel1, tel2, statut,
					true);
			try {
				num = empdao.create(nouveau);
			}

			catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			if (num == 0)
				jspPage = "/jsp/formationpro/listingTousEmployeurs.jsp";
			else
				jspPage = "/jsp/formationpro/listingEmployeurs.jsp";
		}

		else if ("modifieremployeurforpro".equals(action)) {
			String numero = request.getParameter("numero");
			String adresse = request.getParameter("adresse");
			String cp = request.getParameter("cp");
			String ville = request.getParameter("ville");
			String tel1 = request.getParameter("fixe");
			String tel2 = request.getParameter("mobile");
			String status = request.getParameter("nomstatut");
			String actif = request.getParameter("actif");
			boolean act;
			if (actif.equals("OUI"))
				act = true;
			else
				act = false;
			FormprostatutemployeurDAO statdao = new FormprostatutemployeurDAO();
			Formprostatutemployeur statut = null;
			try {
				statut = statdao.findByName(status);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// on recupere l'employeur a modifier
			FormationProEmployeurDAO empdao = new FormationProEmployeurDAO();
			FormationProEmployeur emp = null;
			try {
				emp = empdao.findByID(Integer.parseInt(numero));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			FormationProEmployeur modif = new FormationProEmployeur(emp.getId_employeur(), emp.getEmployeur(), adresse,
					cp, ville, tel1, tel2, statut, act);
			try {
				emp = empdao.update(modif);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				jspPage = "/jsp/formationpro/inscription.jsp";
			}

			jspPage = "/jsp/formationpro/listingEmployeurs.jsp";
		}

		// creeer une nouvelle formation

		else if ("enregistrenouvelleformationpro".equals(action)) {
			String idpersonne = request.getParameter("ident");
			String nom = request.getParameter("nom");
			FormprothemeDAO fordao = new FormprothemeDAO();
			Formprotheme theme = new Formprotheme(nom);
			try {
				fordao.create(theme);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (idpersonne.equals("null"))
				jspPage = "/jsp/accueil/rechercherPersonne.jsp";
			else
				jspPage = "/jsp/formationpro/inscription.jsp?numero=" + idpersonne;
		}

		else if ("enregistrerinscriptionformationpro".equals(action)) {

			String personne = request.getParameter("numero");
			String debut = request.getParameter("datedebutformation");
			String theme = request.getParameter("nomtheme");
			String niveau = request.getParameter("nomniveau");
			String heures = request.getParameter("nbheures");
			int nbheure = Integer.parseInt(heures);
			String finan = request.getParameter("nomfinanceur");
			String emplo = request.getParameter("listeemppro");
			String status = request.getParameter("nomstatut");
			String montant = request.getParameter("montant");
			float prix = Float.parseFloat(montant);

			FormaterDate jour = new FormaterDate();
			java.sql.Date inscription = null, debutforpro = null;

			String inscrip = jour.getSortie();
			// System.out.println("inscription :" + inscrip);
			inscription = new FormaterDate().changeFormatChaineDate(inscrip);
			// System.out.println("inscription :" + inscription);

			debutforpro = new FormaterDate().changeFormatChaineDate(debut);

			FormprothemeDAO themedao = new FormprothemeDAO();
			Formprotheme them = null;
			try {
				them = themedao.findByName(theme);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FormproniveauDAO nivdao = new FormproniveauDAO();
			Formproniveau niv = null;
			try {
				niv = nivdao.findByName(niveau);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FormprofinancementDAO findao = new FormprofinancementDAO();
			Formprofinancement finance = null;
			try {
				finance = findao.findByName(finan);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			IdentiteDAO idedao = new IdentiteDAO();
			Identite identite = null;
			try {
				identite = idedao.findByID(Integer.parseInt(personne));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FormationProEmployeurDAO empdao = new FormationProEmployeurDAO();
			FormationProEmployeur employ = null;
			try {
				employ = empdao.findByName(emplo);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FormprostatutemployeurDAO statdao = new FormprostatutemployeurDAO();
			Formprostatutemployeur statut = null;
			try {
				statut = statdao.findByName(status);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			FormationProInscriptionDAO fpdao = new FormationProInscriptionDAO();
			FormationProInscription nouvelinscript = new FormationProInscription(them, niv, nbheure, statut,
					debutforpro, finance, employ, identite, prix, inscription);
			try {
				fpdao.create(nouvelinscript);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("formation", them);

			jspPage = "/jsp/formationpro/listeInscritsparFormation.jsp";
		}

		else if ("modifierinscriptionformationpro".equals(action)) {
			String numero = request.getParameter("numero");
			String personne = request.getParameter("idpersonne");
			String enregistre = request.getParameter("dateenregistre");
			// String personne = request.getParameter("numero");
			String debut = request.getParameter("datedebutformation");
			String theme = request.getParameter("nomtheme");
			String niveau = request.getParameter("nomniveau");
			String heures = request.getParameter("nbheures");
			int nbheure = Integer.parseInt(heures);
			String finan = request.getParameter("nomfinanceur");
			String emplo = request.getParameter("listeemppro");
			String status = request.getParameter("nomstatut");
			String montant = request.getParameter("montant");
			float prix = Float.parseFloat(montant);

			java.sql.Date inscription = null, debutforpro = null;

			debutforpro = new FormaterDate().changeFormatChaineDate(debut);
			inscription = new FormaterDate().changeFormatChaineDate(enregistre);
			FormprothemeDAO themedao = new FormprothemeDAO();
			Formprotheme them = null;
			try {
				them = themedao.findByName(theme);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FormproniveauDAO nivdao = new FormproniveauDAO();
			Formproniveau niv = null;
			try {
				niv = nivdao.findByName(niveau);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FormprofinancementDAO findao = new FormprofinancementDAO();
			Formprofinancement finance = null;
			try {
				finance = findao.findByName(finan);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			IdentiteDAO idedao = new IdentiteDAO();
			Identite identite = null;
			try {
				identite = idedao.findByID(Integer.parseInt(personne));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FormationProEmployeurDAO empdao = new FormationProEmployeurDAO();
			FormationProEmployeur employ = null;
			try {
				employ = empdao.findByName(emplo);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FormprostatutemployeurDAO statdao = new FormprostatutemployeurDAO();
			Formprostatutemployeur statut = null;
			try {
				statut = statdao.findByName(status);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			FormationProInscriptionDAO isdao = new FormationProInscriptionDAO();
			FormationProInscription modif = new FormationProInscription(Integer.parseInt(numero), them, niv, nbheure,
					statut, debutforpro, finance, employ, identite, prix, inscription);
			try {
				modif = isdao.update(modif);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("formation", them);

			jspPage = "/jsp/formationpro/listeInscritsparFormation.jsp";
		}

		// liste des inscrits par formations par annees
		else if ("afficherinscritsformationpro".equals(action)) {
			java.sql.Date debut = null, fin = null;
			String annee = request.getParameter("datedebutrech");

			// si on recherche par anneee
			if (annee != null) {
				String choix = request.getParameter("annee");
				String debutannee = null;
				String finannee = null;
				if (choix.equals("civil")) {
					// on demarre la recherche au 1/1
					debutannee = annee + "-01-01";
					finannee = annee + "-12-31";

				} else {
					debutannee = annee + "-09-01";

					Integer an = new Integer(annee);
					int ansuivant = an + 1;
					finannee = ansuivant + "-08-31";

				}

				debut = new FormaterDate().changeFormatChaineDate(debutannee);
				// System.out.println("deb=" + debutannee);
				request.setAttribute("debut", debut);

				fin = new FormaterDate().changeFormatChaineDate(finannee);
				// System.out.println("fin=" + finannee);
				request.setAttribute("fin", fin);
			}

			String formation = request.getParameter("nomtheme");
			// System.out.println("formation =" + formation);

			String niveau = request.getParameter("nomniveau");
			// System.out.println("niveau =" + niveau);

			FormprothemeDAO themedao = new FormprothemeDAO();
			Formprotheme them = null;
			try {
				them = themedao.findByName(formation);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.setAttribute("theme", them);
			FormproniveauDAO nivdao = new FormproniveauDAO();
			Formproniveau niv = null;
			try {
				niv = nivdao.findByName(niveau);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			FormationProInscriptionDAO insdao = new FormationProInscriptionDAO();
			FormationProInscription form = new FormationProInscription();
			form.setTheme(them);
			if (niv != null)
				form.setNiveau(niv);
			form.setDebutformation(debut);
			List<FormationProInscription> liste = null;
			try {
				liste = insdao.findByCriteria(form);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("liste", liste);

			jspPage = "/jsp/stat/formationpro/listingStagiairesformation.jsp";
		}

		// rajouter un nouveau statut de stagiaire

		else if ("formpronouveaustatut".equals(action)) {
			String statut = request.getParameter("nomstatut");
			String idpersonne = request.getParameter("ident");
			FormprostatutemployeurDAO stdao = new FormprostatutemployeurDAO();
			Formprostatutemployeur stat = new Formprostatutemployeur(statut);
			try {
				stdao.create(stat);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// on transmet l'id a la page pour ne pas perdre le stagiaire
			request.setAttribute("numeroid", idpersonne);

			jspPage = "/jsp/formationpro/inscription.jsp";
		}

		// cr�ation d'un nouvel organisme de financement
		else if ("formpronouveaufinanceur".equals(action)) {
			String finance = request.getParameter("nomfinance");
			String idpersonne = request.getParameter("ident");
			FormprofinancementDAO fidao = new FormprofinancementDAO();
			Formprofinancement fin = new Formprofinancement(finance);
			try {
				fidao.create(fin);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// on transmet l'id a la page pour ne pas perdre le stagiaire
			request.setAttribute("numeroid", idpersonne);

			jspPage = "/jsp/formationpro/inscription.jsp";
		}

		// liste des stagiaires par employeur
		else if ("afficherinscritsformationproEmploy".equals(action)) {
			java.sql.Date debut = null, fin = null;
			String annee = request.getParameter("datedebutrech");

			// si on recherche par anneee
			if (annee != null) {
				String choix = request.getParameter("annee");
				String debutannee = null;
				String finannee = null;
				if (choix.equals("civil")) {
					// on demarre la recherche au 1/1
					debutannee = annee + "-01-01";
					finannee = annee + "-12-31";

				} else {
					debutannee = annee + "-09-01";

					Integer an = new Integer(annee);
					int ansuivant = an + 1;
					finannee = ansuivant + "-08-31";

				}

				debut = new FormaterDate().changeFormatChaineDate(debutannee);
				// System.out.println("deb=" + debutannee);
				request.setAttribute("debut", debut);

				fin = new FormaterDate().changeFormatChaineDate(finannee);
				// System.out.println("fin=" + finannee);
				request.setAttribute("fin", fin);
			}

			String employeur = request.getParameter("listeemppro");
			// System.out.println("employeur =" + employeur);

			FormationProEmployeurDAO fpdao = new FormationProEmployeurDAO();
			FormationProEmployeur employ = null;
			try {
				employ = fpdao.findByName(employeur);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			request.setAttribute("employeur", employ);

			FormationProInscriptionDAO insdao = new FormationProInscriptionDAO();
			FormationProInscription form = new FormationProInscription();
			form.setEmployeur(employ);
			form.setDebutformation(debut);
			List<FormationProInscription> liste = null;
			try {
				liste = insdao.findByCriteria(form);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("liste", liste);

			jspPage = "/jsp/stat/formationpro/listingStagiairesParEmployeur.jsp";
		}

		// statistiques par annee
		else if ("statformationproanneee".equals(action)) {
			java.sql.Date debut = null, fin = null;
			String annee = request.getParameter("datedebutrech");

			// si on recherche par anneee
			if (annee != null) {
				String choix = request.getParameter("annee");
				String debutannee = null;
				String finannee = null;
				if (choix.equals("civil")) {
					// on demarre la recherche au 1/1
					debutannee = annee + "-01-01";
					finannee = annee + "-12-31";

				}
				// recherche par exercice
				else {
					debutannee = annee + "-09-01";

					Integer an = new Integer(annee);
					int ansuivant = an + 1;
					finannee = ansuivant + "-08-31";

				}

				debut = new FormaterDate().changeFormatChaineDate(debutannee);
				// System.out.println("deb=" + debutannee);
				request.setAttribute("debut", debut);

				fin = new FormaterDate().changeFormatChaineDate(finannee);
				// System.out.println("fin=" + finannee);
				request.setAttribute("fin", fin);
			}

			FormationProInscriptionDAO insdao = new FormationProInscriptionDAO();
			FormationProInscription form = new FormationProInscription();
			form.setDebutformation(debut);
			List<FormationProInscription> liste = null;
			try {
				liste = insdao.findByCriteria(form);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("liste", liste);

			jspPage = "/jsp/stat/formationpro/statformationpro.jsp";
		}

		/**************************************************************************/
		/************* partie employeur ************/
		/**************************************************************************/

		// enregistrer un nouvel employeur
		else if ("saveemployeur".equals(action)) {
			// r�cuperation des parametres du formulaire
			String personne = request.getParameter("personne");
			String type = request.getParameter("type");
			String statut = request.getParameter("statut");
			String civiliteemp = request.getParameter("civiliteemp");
			String rs = request.getParameter("rs");
			String adresse1 = request.getParameter("adresse1");
			String adresse2 = request.getParameter("adresse2");
			String cp = request.getParameter("cp");
			String ville = request.getParameter("ville");
			String civiliteresp = request.getParameter("civiliteresp");
			String nomresp = request.getParameter("nomresp");
			String prenomresp = request.getParameter("prenomresp");
			String fixe = request.getParameter("fixe");
			String mobile = request.getParameter("mobile");
			String fax = request.getParameter("fax");
			String mail = request.getParameter("mail");
			String structure = request.getParameter("structure");
			String activite = request.getParameter("activite");
			String rangresp = request.getParameter("rangresp");
			String obs = request.getParameter("obs");
			String siret = request.getParameter("siret");
			String ape = request.getParameter("ape");
			String rm = request.getParameter("rm");
			String civilitecontact = request.getParameter("civilitecontact");
			String nomcontact = request.getParameter("nomcontact");
			String prenomcontact = request.getParameter("prenomcontact");
			String rangcontact = request.getParameter("rangcontact");
			String fixecontact = request.getParameter("fixecontact");
			String mobilecontact = request.getParameter("mobilecontact");
			String faxcontact = request.getParameter("faxcontact");
			String mailcontact = request.getParameter("mailcontact");
			String dateinscription = request.getParameter("dateinscription");

			// si la date d'enregistrement n'est pas saisie on met la date du
			// jour
			FormaterDate jour = new FormaterDate();
			java.sql.Date creation = null;
			if (dateinscription.equals("") || dateinscription.equals(null)) {
				String a = jour.getSortie();
				creation = new FormaterDate().changeFormatChaineDate(a);
			} else
				creation = new FormaterDate().changeFormatChaineDate(dateinscription);

			ActiviteDAO actdao = new ActiviteDAO();
			Activite acti = null;
			try {
				acti = actdao.findByName(activite);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			StatutDAO stdao = new StatutDAO();
			Statut status = null;
			// si l'employeur est un particulier on lui affecte le statut 17
			// (vide)
			if (statut == null)
				try {
					status = stdao.findByID(17);
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			else
				try {
					status = stdao.findByName(statut);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			StructureDAO structdao = new StructureDAO();
			Structure struct = null;
			try {
				struct = structdao.findByName(structure);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int id_emp = 0;
			EmployeurDAO empdao = new EmployeurDAO();
			Employeur employeur = new Employeur(civiliteemp, rs, status, adresse1, adresse2, cp, ville, fixe, mobile,
					fax, mail, struct, obs, acti, creation, civiliteresp, nomresp, prenomresp, rangresp, true, null,
					siret, ape, rm);
			// insertion en base
			try {
				id_emp = empdao.create(employeur);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			/****************** partie contact *******************/

			if (!nomcontact.equals("") && !nomcontact.equals(null)) {
				ContactDAO codao = new ContactDAO();
				ServiceDAO serdao = new ServiceDAO();
				Service service = null;
				try {
					service = serdao.findByID(28);
				} catch (DAOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				Employeur emp = null;
				try {
					emp = empdao.findByID(id_emp);
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Contact contact = new Contact(emp, service, civilitecontact, nomcontact, prenomcontact, fixecontact,
						faxcontact, mobilecontact, mailcontact, rangcontact, true);
				try {
					codao.create(contact);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			/* si le contact n'est pas renseigné on met le nom du responsable */
			else {
				ContactDAO codao = new ContactDAO();
				Employeur emp = null;
				ServiceDAO serdao = new ServiceDAO();
				Service service = null;
				/* on lui affecte un service bidon créé expres d'id 1 */
				try {
					service = serdao.findByID(1);
				} catch (DAOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					emp = empdao.findByID(id_emp);
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Contact contact = new Contact(emp, service, civiliteresp, nomresp, prenomresp, fixe, fax, mobile, mail,
						rangresp, true);
				try {
					codao.create(contact);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (personne != null) {
				if (type == "ai")
					jspPage = "/jsp/ai/contrat/nouveau.jsp?numero=" + personne;
				else
					jspPage = "/jsp/sap/prestation/nouvelle.jsp?personne=" + personne;
			} else
				jspPage = "/jsp/accueil/rechercherPersonne.jsp"; // "/index.jsp";

		}

		else if ("modifieremployeur".equals(action)) {
			String id_emp = request.getParameter("emp");
			String actif = request.getParameter("actif");
			boolean act;
			if (actif.equals("oui"))
				act = true;
			else
				act = false;
			java.sql.Date accueil = null, modif = null;
			String dateinscription = request.getParameter("dateinscription");
			accueil = new FormaterDate().changeFormatChaineDate(dateinscription);

			/* on met la date de modification */
			FormaterDate jour = new FormaterDate();
			String a = jour.getSortie();
			modif = new FormaterDate().changeFormatChaineDate(a);

			String statut = request.getParameter("statut");
			StatutDAO statutdao = new StatutDAO();
			Statut status = null;
			// si l'employeur est un particulier on lui affecte le statut 17
			// (vide)
			if (statut == null)
				try {
					status = statutdao.findByID(17);
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			else
				try {
					status = statutdao.findByName(statut);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			String civiliteemp = request.getParameter("civiliteemp");
			String rs = request.getParameter("rs");
			String adresse1 = request.getParameter("adresse1");
			String adresse2 = request.getParameter("adresse2");
			String cp = request.getParameter("cp");
			String ville = request.getParameter("ville");
			String civiliteresp = request.getParameter("civiliteresp");
			String nomresp = request.getParameter("nomresp");
			String prenomresp = request.getParameter("prenomresp");
			String fixe = request.getParameter("fixe");
			String mobile = request.getParameter("mobile");
			String fax = request.getParameter("fax");
			String mail = request.getParameter("mail");
			String structure = request.getParameter("structure");
			String activite = request.getParameter("activite");
			String rangresp = request.getParameter("rangresp");
			String obs = request.getParameter("obs");
			String siret = request.getParameter("siret");
			String ape = request.getParameter("ape");
			String rm = request.getParameter("rm");
			EmployeurDAO empdao = new EmployeurDAO();

			StructureDAO stdao = new StructureDAO();
			Structure struc = null;
			try {
				struc = stdao.findByName(structure);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ActiviteDAO acdao = new ActiviteDAO();
			Activite acti = null;
			try {
				acti = acdao.findByName(activite);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Employeur employeur = new Employeur(Integer.valueOf(id_emp), civiliteemp, rs, status, adresse1, adresse2,
					cp, ville, fixe, mobile, fax, mail, struc, obs, acti, accueil, civiliteresp, nomresp, prenomresp,
					rangresp, act, modif, siret, ape, rm);

			try {
				employeur = empdao.update(employeur);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jspPage = "/jsp/employeurs/affichage.jsp?numero=" + id_emp;

		}

		else if ("rechercheremployeurnom".equals(action)) {
			String nom = request.getParameter("numero");

			EmployeurDAO personne = new EmployeurDAO();
			List<Employeur> liste = new ArrayList<Employeur>();
			try {
				liste = personne.findByName(nom);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("resultset", liste);

			jspPage = "/jsp/employeurs/listing.jsp";
		}

		else if ("creerservice".equals(action)) {
			String service = request.getParameter("service");
			String emp = request.getParameter("idemployeur");
			EmployeurDAO empdao = new EmployeurDAO();
			Employeur employeur = null;
			try {
				employeur = empdao.findByID(Integer.valueOf(emp));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ServiceDAO serdao = new ServiceDAO();
			Service servic = new Service(employeur, service);
			try {
				serdao.create(servic);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jspPage = "/jsp/employeurs/affichage.jsp?numero=" + emp;

		}

		else if ("nouveaucontact".equals(action)) {
			String civil = request.getParameter("civilitecontact");
			String id_emp = request.getParameter("employeurs");
			String nom = request.getParameter("nom");
			String prenom = request.getParameter("prenom");
			String rang = request.getParameter("rang");
			String services = request.getParameter("services");
			String fixe = request.getParameter("fixe");
			String mobile = request.getParameter("mobile");
			String fax = request.getParameter("fax");
			String mail = request.getParameter("mail");

			EmployeurDAO empdao = new EmployeurDAO();
			Employeur employeur = null;
			try {
				employeur = empdao.findByID(Integer.valueOf(id_emp));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ServiceDAO serdao = new ServiceDAO();
			Service service = null;
			if (services == null)
				try {
					service = serdao.findByID(1);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			else
				try {
					service = serdao.findByName(services);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			ContactDAO contdao = new ContactDAO();
			Contact contact = new Contact(employeur, service, civil, nom, prenom, fixe, fax, mobile, mail, rang, true);
			try {
				contdao.create(contact);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/employeurs/affichage.jsp?numero=" + id_emp;

		}

		else if ("modifiercontact".equals(action)) {
			String id_cont = request.getParameter("id_contact");
			int id_contact = Integer.valueOf(id_cont);
			String id_employ = request.getParameter("employeurs");
			int id_emp = Integer.valueOf(id_employ);
			String civil = request.getParameter("civilitecontact");
			String nom = request.getParameter("nom");
			String prenom = request.getParameter("prenom");
			String rang = request.getParameter("rang");
			String services = request.getParameter("services");
			String fixe = request.getParameter("fixe");
			String mobile = request.getParameter("mobile");
			String fax = request.getParameter("fax");
			String mail = request.getParameter("mail");

			EmployeurDAO empdao = new EmployeurDAO();
			Employeur employeur = null;
			try {
				employeur = empdao.findByID(id_emp);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			ServiceDAO serdao = new ServiceDAO();
			Service service = null;
			if (services == null)
				try {
					service = serdao.findByID(1);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			else
				try {
					service = serdao.findByName(services);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			ContactDAO contdao = new ContactDAO();
			Contact contact = new Contact(id_contact, employeur, service, civil, nom, prenom, fixe, fax, mobile, mail,
					rang, true);

			try {
				contact = contdao.update(contact);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jspPage = "/jsp/employeurs/affichage.jsp?numero=" + id_emp;

		} else if ("supprimercontact".equals(action)) {
			String id_contact = request.getParameter("contact");
			int id = Integer.parseInt(id_contact);
			String emp = request.getParameter("emp");
			ContactDAO contdao = new ContactDAO();

			try {
				int sup = contdao.delete(id);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jspPage = "/jsp/employeurs/affichage.jsp?numero=" + emp;

		}

		else if ("savesuiviemployeur".equals(action)) {

			String id_emp = request.getParameter("id_emp");
			String date = request.getParameter("dateinscription");
			String nom = request.getParameter("utilisateurs");
			String commentaire = request.getParameter("commentaires");
			// si la date d'enregistrement n'est pas saisie on met la date du
			// jour
			FormaterDate jour = new FormaterDate();
			java.sql.Date creation = null;
			if (date.equals("") || date.equals(null)) {
				String a = jour.getSortie();
				creation = new FormaterDate().changeFormatChaineDate(a);
			} else
				creation = new FormaterDate().changeFormatChaineDate(date);

			EmployeurDAO empdao = new EmployeurDAO();
			Employeur employeur = null;
			try {
				employeur = empdao.findByID(Integer.valueOf(id_emp));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			UtilisateurDAO utdao = new UtilisateurDAO();
			Utilisateur utilisateur = null;
			try {
				utilisateur = utdao.findByName(nom);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SuiviDAO suidao = new SuiviDAO();
			Suivi suivi = new Suivi(utilisateur, employeur, commentaire, creation);
			try {
				int num = suidao.create(suivi);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/employeurs/affichage.jsp?numero=" + id_emp;

		}

		else if ("modifsuiviemployeur".equals(action)) {
			String id_suivi = request.getParameter("id_suivi");
			String date = request.getParameter("dateinscription");
			String nom = request.getParameter("utilisateurs");
			String commentaire = request.getParameter("commentaires");

			java.sql.Date creation = null;
			creation = new FormaterDate().changeFormatChaineDate(date);

			UtilisateurDAO utdao = new UtilisateurDAO();
			Utilisateur utilisateur = null;
			try {
				utilisateur = utdao.findByName(nom);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SuiviDAO suidao = new SuiviDAO();
			Suivi suivi = null;
			try {
				suivi = suidao.findByID(Integer.valueOf(id_suivi));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Suivi tampon = new Suivi(suivi.getId_suivi(), utilisateur, suivi.getEmployeur(), commentaire, creation);
			try {
				suivi = suidao.update(tampon);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/employeurs/affichage.jsp?numero=" + suivi.getEmployeur().getId_employeur();

		}

		else if ("rechercheEmployeurParStructure".equals(action)) {
			String structure = request.getParameter("structure");
			Structure struct = null;
			try {
				struct = new StructureDAO().findByName(structure);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// on récupère l'id de cette structure
			int num = struct.getId_structure();
			EmployeurDAO empdao = new EmployeurDAO();
			List<Employeur> liste = null;
			try {
				liste = empdao.afficherEmployeursParStructure(num);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("liste", liste);
			jspPage = "/jsp/employeurs/rechercherStructureEmp.jsp?nom=" + structure;

		}

		else if ("rechercheEmployeurParDomaine".equals(action)) {
			String activite = request.getParameter("activite");
			Activite activ = null;
			try {
				activ = new ActiviteDAO().findByName(activite);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// on récupère l'id de cette structure
			int num = activ.getId_activite();
			EmployeurDAO empdao = new EmployeurDAO();
			List<Employeur> liste = null;
			try {
				liste = empdao.afficherEmployeursParActivite(num);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("liste", liste);
			jspPage = "/jsp/employeurs/rechercherDomaineEmp.jsp?nom=" + activite;

		}

		else if ("nouvelleoffreemployeur".equals(action)) {
			String numoffre = request.getParameter("numoffre");
			String utilisateurs = request.getParameter("utilisateurs");
			String employeurs = request.getParameter("employeurs");
			String emploipropose = request.getParameter("emploipropose");
			String nbpersonne = request.getParameter("nbpersonne");
			String autre = request.getParameter("autre");
			String ai = request.getParameter("ai");
			String cae = request.getParameter("cae");
			String avenir = request.getParameter("avenir");
			String cdd = request.getParameter("cdd");
			String cdi = request.getParameter("cdi");
			String alternance = request.getParameter("alternance");
			String datedebutemploi = request.getParameter("datedebutemploi");
			String duree = request.getParameter("duree");
			String stats = request.getParameter("stats");
			String dateinscription = request.getParameter("dateinscription");
			String jourstravail = request.getParameter("jourstravail");
			String heurestravail = request.getParameter("heurestravail");
			String detailposte = request.getParameter("detailposte");
			String obs = request.getParameter("obs");
			String contacts = request.getParameter("contacts");

			java.sql.Date inscrip = null, dateemp = null;
			if (!dateinscription.equals("") && !dateinscription.equals("null"))
				inscrip = new FormaterDate().changeFormatChaineDate(dateinscription);
			if (!datedebutemploi.equals("") && !dateinscription.equals("null"))
				dateemp = new FormaterDate().changeFormatChaineDate(datedebutemploi);

			emploipropose = new FormaterTexte().supprimerTableMatiere(emploipropose);
			UtilisateurDAO utdao = new UtilisateurDAO();
			Utilisateur utilisateur = null;
			try {
				utilisateur = utdao.findByName(utilisateurs);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			EmployeurDAO empdao = new EmployeurDAO();
			int i = employeurs.length();
			int j = employeurs.lastIndexOf("-");
			int code = Integer.parseInt(employeurs.substring((j + 1), i));

			// System.out.println("employeur =" + code);
			Employeur emp = null;
			try {
				emp = empdao.findByID(code);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ContactDAO codao = new ContactDAO();
			Contact contact = null;
			// System.out.println("contact =" + contacts);
			if (!contacts.equals("Aucun") || !contacts.equals("")) {
				Integer codeemploy = Integer.valueOf(code);
				try {
					contact = codao.afficherPremierContact(codeemploy.toString(), contacts);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			RomeDAO rodao = new RomeDAO();
			Rome rom = null;
			try {
				rom = rodao.findByName(emploipropose);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			boolean autre1 = false, ai1 = false, cae1 = false, avenir1 = false, cdd1 = false, cdi1 = false,
					alternance1 = false;

			if (autre != null)
				autre1 = true;
			if (ai != null)
				ai1 = true;
			if (cae != null)
				cae1 = true;
			if (avenir != null)
				avenir1 = true;
			if (cdd != null)
				cdd1 = true;
			if (cdi != null)
				cdi1 = true;
			if (alternance != null)
				alternance1 = true;
			String stat = null;
			if (stats.equals("1semaine"))
				stat = "< 1  semaine";
			else if (stats.equals("1mois"))
				stat = "< 1 mois";
			else if (stats.equals("-6mois"))
				stat = "de 1 à 6 mois";
			else if (stats.equals("+6mois"))
				stat = "de 6 mois à 1 an";
			else
				stat = "> 1 an";

			OffreDAO odao = new OffreDAO();
			Offre offre = new Offre(emp, contact, inscrip, utilisateur, rom, Integer.parseInt(nbpersonne), dateemp,
					null, duree, autre1, ai1, cae1, avenir1, cdd1, cdi1, alternance1, detailposte, false, obs,
					jourstravail, heurestravail, stat, false, false);
			int idoffre = 0;
			try {
				idoffre = odao.create(offre);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/employeurs/offres/affichageoffre.jsp?numoffre=" + numoffre;

		}

		else if ("offreemployeurmodification".equals(action)) {
			String numoffre = request.getParameter("numoffre");
			String utilisateurs = request.getParameter("utilisateurs");
			String employeurs = request.getParameter("employeurs");
			String emploipropose = request.getParameter("emploipropose");
			String nbpersonne = request.getParameter("nbpersonne");
			String autre = request.getParameter("autre");
			String ai = request.getParameter("ai");
			String cae = request.getParameter("cae");
			String avenir = request.getParameter("avenir");
			String cdd = request.getParameter("cdd");
			String cdi = request.getParameter("cdi");
			String alternance = request.getParameter("alternance");
			String datedebutemploi = request.getParameter("datedebutemploi");
			String duree = request.getParameter("duree");
			String stats = request.getParameter("stats");
			String dateinscription = request.getParameter("dateinscription");
			String jourstravail = request.getParameter("jourstravail");
			String heurestravail = request.getParameter("heurestravail");
			String detailposte = request.getParameter("detailposte");
			String obs = request.getParameter("obs");
			String contacts = request.getParameter("contacts");

			java.sql.Date inscrip = null, dateemp = null;
			if (!dateinscription.equals("") && !dateinscription.equals(null))
				inscrip = new FormaterDate().changeFormatChaineDate(dateinscription);
			if (!datedebutemploi.equals("") && !dateinscription.equals(null))
				dateemp = new FormaterDate().changeFormatChaineDate(datedebutemploi);

			emploipropose = new FormaterTexte().supprimerTableMatiere(emploipropose);
			UtilisateurDAO utdao = new UtilisateurDAO();
			Utilisateur utilisateur = null;
			try {
				utilisateur = utdao.findByName(utilisateurs);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			EmployeurDAO empdao = new EmployeurDAO();
			int i = employeurs.length();
			int j = employeurs.lastIndexOf("-");
			int code = Integer.parseInt(employeurs.substring((j + 1), i));

			// System.out.println("employeur =" + code);
			Employeur emp = null;
			try {
				emp = empdao.findByID(code);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ContactDAO codao = new ContactDAO();
			Contact contact = null;
			// System.out.println("contact =" + contacts);
			if (!contacts.equals("Aucun")) {
				Integer codeemploy = Integer.valueOf(code);
				try {
					contact = codao.afficherPremierContact(codeemploy.toString(), contacts);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			RomeDAO rodao = new RomeDAO();
			Rome rom = null;
			try {
				rom = rodao.findByName(emploipropose);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			boolean autre1 = false, ai1 = false, cae1 = false, avenir1 = false, cdd1 = false, cdi1 = false,
					alternance1 = false;

			if (autre != null)
				autre1 = true;
			if (ai != null)
				ai1 = true;
			if (cae != null)
				cae1 = true;
			if (avenir != null)
				avenir1 = true;
			if (cdd != null)
				cdd1 = true;
			if (cdi != null)
				cdi1 = true;
			if (alternance != null)
				alternance1 = true;

			OffreDAO odao = new OffreDAO();
			Offre offre = null;
			try {
				offre = odao.findByID(Integer.parseInt(numoffre));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			offre.setDateSaisie(inscrip);
			offre.setSalarie(utilisateur);
			offre.setEmployeur(emp);
			offre.setContact(contact);
			offre.setRome(rom);
			offre.setNbpersonnes(Integer.parseInt(nbpersonne));
			if (autre1)
				offre.setContrat_autre(true);
			else
				offre.setContrat_autre(false);
			if (ai1)
				offre.setContrat_ai(true);
			else
				offre.setContrat_ai(false);
			if (cae1)
				offre.setContrat_cae(true);
			else
				offre.setContrat_cae(false);
			if (avenir1)
				offre.setContrat_avenir(true);
			else
				offre.setContrat_avenir(false);
			if (cdd1)
				offre.setContrat_cdd(true);
			else
				offre.setContrat_cdd(false);
			if (cdi1)
				offre.setContrat_cdi(true);
			else
				offre.setContrat_cdi(false);
			if (alternance1)
				offre.setContrat_alternance(true);
			else
				offre.setContrat_alternance(false);

			offre.setDatedeb_offre(dateemp);
			offre.setDuree_offre(duree);

			String stat = null;

			if (stats.equals("1semaine"))
				stat = "< 1  semaine";
			else if (stats.equals("1mois"))
				stat = "< 1 mois";
			else if (stats.equals("-6mois"))
				stat = "de 1 à 6 mois";
			else if (stats.equals("+6mois"))
				stat = "de 6 mois à 1 an";
			else
				stat = "> 1 an";

			offre.setDureestats(stat);
			offre.setJour(jourstravail);
			offre.setHeures(heurestravail);
			offre.setDetail(detailposte);
			offre.setObservation(obs);

			try {
				offre = odao.update(offre);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/employeurs/offres/affichageoffre.jsp?numoffre=" + numoffre;

		}

		else if ("modifoffrespourvues".equals(action)) {
			String numero = request.getParameter("numero");
			OffreDAO ofdao = new OffreDAO();
			Offre offre = null;
			try {
				offre = ofdao.findByID(Integer.parseInt(numero));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			offre.setPourvue(true);
			try {
				ofdao.update(offre);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jspPage = "/jsp/employeurs/offres/nonpourvues.jsp";

		}

		else if ("modifoffresannulees".equals(action)) {
			String numero = request.getParameter("numero");
			OffreDAO ofdao = new OffreDAO();
			Offre offre = null;
			try {
				offre = ofdao.findByID(Integer.parseInt(numero));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			offre.setAnnule_offre(true);
			offre.setPourvue(true);
			try {
				ofdao.update(offre);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jspPage = "/jsp/employeurs/offres/nonpourvues.jsp";

		}

		else if ("modifoffresautres".equals(action)) {
			String numero = request.getParameter("numero");
			OffreDAO ofdao = new OffreDAO();
			Offre offre = null;
			try {
				offre = ofdao.findByID(Integer.parseInt(numero));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			offre.setAutrerecrute(true);
			offre.setPourvue(true);
			try {
				ofdao.update(offre);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jspPage = "/jsp/employeurs/offres/nonpourvues.jsp";

		}

		else if ("offrepassepourvue".equals(action)) {
			String numero = request.getParameter("numero");
			String categorie = request.getParameter("categorie");
			String colonne = request.getParameter("colonne");
			OffreDAO ofdao = new OffreDAO();
			Offre offre = null;
			try {
				offre = ofdao.findByID(Integer.parseInt(numero));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (colonne.equals("un")) {
				if (offre.isPourvue())
					offre.setPourvue(false);
				else
					offre.setPourvue(true);
			}
			if (colonne.equals("deux")) {
				if (offre.isAnnule_offre()) {
					offre.setAnnule_offre(false);
					offre.setPourvue(false);
				} else {
					offre.setAnnule_offre(true);

				}

			}
			if (colonne.equals("trois")) {
				if (offre.isAutrerecrute()) {
					offre.setAutrerecrute(false);
					offre.setPourvue(false);
				} else {
					offre.setAutrerecrute(true);

				}

			}

			try {
				ofdao.update(offre);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/employeurs/offres/nonpourvues.jsp?categorie=" + categorie;

		}

		else if ("supprimerpositionnement".equals(action)) {
			String numoffre = request.getParameter("numoffre");
			String personne = request.getParameter("personne");
			PositionnerPersonneDAO podao = new PositionnerPersonneDAO();

			try {
				podao.supprimerPersonneOffre(Integer.parseInt(numoffre), Integer.parseInt(personne));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jspPage = "/jsp/employeurs/offres/affichageoffre.jsp?numoffre=" + numoffre;

		}

		else if ("offrepropositionretenue".equals(action)) {
			String numoffre = request.getParameter("numoffre");
			String personne = request.getParameter("personne");
			String url = request.getParameter("url");
			PositionnerPersonneDAO podao = new PositionnerPersonneDAO();

			try {
				podao.validerPersonneOffre(Integer.parseInt(numoffre), Integer.parseInt(personne), url);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jspPage = "/jsp/employeurs/offres/affichageoffre.jsp?numoffre=" + numoffre;

		}

		else if ("validationdirecteoffre".equals(action)) {
			String numoffre = request.getParameter("numoffre");
			String personne = request.getParameter("personne");
			String reponse = request.getParameter("reponse");
			String utilisateurs = request.getParameter("utilisateurs");

			// on met la date du jour
			FormaterDate jour = new FormaterDate();
			java.sql.Date creation = null;
			String a = jour.getSortie();
			creation = new FormaterDate().changeFormatChaineDate(a);

			OffreDAO ofdao = new OffreDAO();
			Offre offre = null;
			try {
				offre = ofdao.findByID(Integer.parseInt(numoffre));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			IdentiteDAO iddao = new IdentiteDAO();
			Identite identite = null;
			try {
				identite = iddao.findByID(Integer.parseInt(personne));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			UtilisateurDAO utidao = new UtilisateurDAO();
			Utilisateur referent = null;
			try {
				referent = utidao.findByName(utilisateurs);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			PositionnerPersonneDAO podao = new PositionnerPersonneDAO();
			PositionnerPersonne positione = new PositionnerPersonne(offre, identite, creation, referent, reponse,
					false);

			try {
				podao.create(positione);

			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jspPage = "/jsp/employeurs/offres/affichageoffre.jsp?numoffre=" + numoffre;

		}

		else if ("validationdirecteoffrehorsselection".equals(action)) {
			String numoffre = request.getParameter("numoffre");
			String reponse = request.getParameter("reponse");
			String utilisateurs = request.getParameter("utilisateurs");
			String personne = request.getParameter("nomarechercher");
			int n = personne.lastIndexOf("(");
			personne = personne.substring(n + 1, personne.length() - 1);
			String jour = request.getParameter("dateinscription");

			java.sql.Date creation = null;
			creation = new FormaterDate().changeFormatChaineDate(jour);

			OffreDAO ofdao = new OffreDAO();
			Offre offre = null;
			try {
				offre = ofdao.findByID(Integer.parseInt(numoffre));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			IdentiteDAO iddao = new IdentiteDAO();
			Identite identite = null;
			try {
				identite = iddao.findByID(Integer.parseInt(personne));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			UtilisateurDAO utidao = new UtilisateurDAO();
			Utilisateur referent = null;
			try {
				referent = utidao.findByName(utilisateurs);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			PositionnerPersonneDAO podao = new PositionnerPersonneDAO();
			PositionnerPersonne positione = new PositionnerPersonne(offre, identite, creation, referent, reponse,
					false);

			try {
				podao.create(positione);

			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jspPage = "/jsp/employeurs/offres/affichageoffre.jsp?numoffre=" + numoffre;

		}

		else if ("modifierpositionoffre".equals(action)) {
			String numoffre = request.getParameter("numoffrepropo");
			String reponse = request.getParameter("reponse");
			PositionnerPersonneDAO podao = new PositionnerPersonneDAO();
			PositionnerPersonne pos = null;
			try {
				pos = podao.findByID(Integer.parseInt(numoffre));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pos.setReponse(reponse);
			try {
				podao.update(pos);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jspPage = "/jsp/employeurs/offres/affichageoffre.jsp?numoffre=" + pos.getOffre().getId_offre();
		}

		// simple suivi d'une personne
		else if ("suivisimplesalarie".equals(action)) {
			String id = request.getParameter("personne");
			String commentaires = request.getParameter("commentaires");
			String utilisateurs = request.getParameter("utilisateurs");
			String dateredaction = request.getParameter("dateredaction");

			// si la date d'enregistrement n'est pas saisie on met la date du
			// jour
			FormaterDate jour = new FormaterDate();
			java.sql.Date creation = null;
			if (dateredaction.equals("") || dateredaction.equals(null)) {
				String a = jour.getSortie();
				creation = new FormaterDate().changeFormatChaineDate(a);
			} else
				creation = new FormaterDate().changeFormatChaineDate(dateredaction);

			IdentiteDAO iddao = new IdentiteDAO();
			Identite identite = null;
			try {
				identite = iddao.findByID(Integer.parseInt(id));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			UtilisateurDAO utidao = new UtilisateurDAO();
			Utilisateur util = null;
			try {
				util = utidao.findByName(utilisateurs);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SuiviPersonneDAO suidao = new SuiviPersonneDAO();
			SuiviPersonne suivi = new SuiviPersonne(identite, creation, util, commentaires);

			try {
				suidao.create(suivi);
			} catch (DAOException e) {
				// TODO Auto-generated catch block

				e.printStackTrace();

			}

			jspPage = "/jsp/accueil/afficheInscrit.jsp?numero=" + id;
		}

		// simpleemploi d'une personne
		else if ("suiviemploisalarie".equals(action)) {
			String id = request.getParameter("personne");
			String utilisateurs = request.getParameter("utilisateurs");
			String dateredaction = request.getParameter("dateredaction");
			String employeurs = request.getParameter("employeurs");
			String emploipropose = request.getParameter("emploipropose");
			String datedebut = request.getParameter("datedebut");
			String datefin = request.getParameter("datefin");
			String commentaires = request.getParameter("commentaires");

			// si la date d'enregistrement n'est pas saisie on met la date du
			// jour
			FormaterDate jour = new FormaterDate();
			java.sql.Date creation = null, debut = null, fin = null;

			if (dateredaction.equals("") || dateredaction.equals(null)) {
				String a = jour.getSortie();
				creation = new FormaterDate().changeFormatChaineDate(a);
			} else {

				creation = new FormaterDate().changeFormatChaineDate(dateredaction);
			}

			if (!datedebut.equals("") && !datedebut.equals(null))
				debut = new FormaterDate().changeFormatChaineDate(datedebut);

			if (!datefin.equals("") && !datefin.equals(null))
				fin = new FormaterDate().changeFormatChaineDate(datefin);

			IdentiteDAO iddao = new IdentiteDAO();
			Identite identite = null;
			try {
				identite = iddao.findByID(Integer.parseInt(id));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			UtilisateurDAO utidao = new UtilisateurDAO();
			Utilisateur util = null;
			try {
				util = utidao.findByName(utilisateurs);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			int position = employeurs.lastIndexOf("-");
			String idemployeur = employeurs.substring(position + 1);
			EmployeurDAO empdao = new EmployeurDAO();
			Employeur employ = null;
			try {
				employ = empdao.findByID(Integer.parseInt(idemployeur));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			RomeDAO rodao = new RomeDAO();
			Rome rome = null;
			if (!emploipropose.equals("SELECTION") && !emploipropose.equals("")) {

				try {
					rome = rodao.findByName(emploipropose);
				} catch (DAOException e) {
					e.printStackTrace();

				}
			}

			SuiviEmploiDAO suidao = new SuiviEmploiDAO();
			SuiviEmploi suivi = new SuiviEmploi(identite, creation, util, employ, rome, debut, fin, commentaires);

			try {
				suidao.create(suivi);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/accueil/afficheInscrit.jsp?numero=" + id;
		}

		else if ("modificationsuivipersonne".equals(action)) {
			String suivi = request.getParameter("suivi");
			String date = request.getParameter("dateredaction");
			String ref = request.getParameter("utilisateurs");
			String commentaire = request.getParameter("commentaires");

			java.sql.Date creation = null;
			creation = new FormaterDate().changeFormatChaineDate(date);

			SuiviPersonneDAO suidao = new SuiviPersonneDAO();
			SuiviPersonne suivipers = null;
			try {
				suivipers = suidao.findByID(Integer.parseInt(suivi));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			UtilisateurDAO utidao = new UtilisateurDAO();
			Utilisateur util = null;
			try {
				util = utidao.findByName(ref);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			suivipers = new SuiviPersonne(suivipers.getId_suivi(), suivipers.getIdentite(), creation, util,
					commentaire);
			try {
				suidao.update(suivipers);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/suiviInscrits/accompagnement.jsp?numero=" + suivipers.getIdentite().getId_IDE();

		}

		else if ("modificationsuiviemploipersonne".equals(action)) {

			String id = request.getParameter("suivi");
			String utilisateurs = request.getParameter("utilisateurs");
			String dateredaction = request.getParameter("dateredaction");
			String employeurs = request.getParameter("employeurs");
			String emploipropose = request.getParameter("emploipropose");
			String datedebut = request.getParameter("datedebut");
			String datefin = request.getParameter("datefin");
			String commentaires = request.getParameter("commentaires");

			// si la date d'enregistrement n'est pas saisie on met la date du
			// jour
			FormaterDate jour = new FormaterDate();
			java.sql.Date creation = null, debut = null, fin = null;

			if (dateredaction.equals("") || dateredaction.equals(null)) {
				String a = jour.getSortie();
				creation = new FormaterDate().changeFormatChaineDate(a);
			} else {

				creation = new FormaterDate().changeFormatChaineDate(dateredaction);
			}

			if (!datedebut.equals("") && !datedebut.equals(null))
				debut = new FormaterDate().changeFormatChaineDate(datedebut);

			if (!datefin.equals("") && !datefin.equals(null))
				fin = new FormaterDate().changeFormatChaineDate(datefin);

			/*
			 * IdentiteDAO iddao = new IdentiteDAO(); Identite identite = null;
			 * try { identite = iddao.findByID(Integer.parseInt(id)); } catch
			 * (NumberFormatException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); } catch (DAOException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); }
			 */

			UtilisateurDAO utidao = new UtilisateurDAO();
			Utilisateur util = null;
			try {
				util = utidao.findByName(utilisateurs);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			int position = employeurs.lastIndexOf("-");
			String idemployeur = employeurs.substring(position + 1);
			EmployeurDAO empdao = new EmployeurDAO();
			Employeur employ = null;
			try {
				employ = empdao.findByID(Integer.parseInt(idemployeur));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			RomeDAO rodao = new RomeDAO();
			Rome rome = null;
			if (!emploipropose.equals("SELECTION") && !emploipropose.equals("")) {

				try {
					rome = rodao.findByName(emploipropose);
				} catch (DAOException e) {
					e.printStackTrace();

				}
			}

			SuiviEmploiDAO suidao = new SuiviEmploiDAO();
			SuiviEmploi suivi = null;
			try {
				suivi = suidao.findByID(Integer.parseInt(id));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			suivi.setReferent(util);
			suivi.setRome(rome);
			suivi.setEmployeur(employ);
			suivi.setDateSuivi(creation);
			suivi.setDateDebutSuivi(debut);
			suivi.setDateFinSuivi(fin);
			suivi.setCommentaires(commentaires);

			try {
				suidao.update(suivi);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/suiviInscrits/accompagnementemploi.jsp?numero=" + suivi.getIdentite().getId_IDE();

		}

		else if ("statistiquesoffresemployeurs".equals(action)) {
			String datedebut = request.getParameter("datedebutrech");
			String datefin = request.getParameter("datefinrech");

			java.sql.Date deb = null, fin = null;
			deb = new FormaterDate().changeFormatChaineDate(datedebut);
			fin = new FormaterDate().changeFormatChaineDate(datefin);

			request.setAttribute("debut", deb);
			request.setAttribute("fin", fin);
			jspPage = "/jsp/employeurs/offres/statistiques.jsp";

		}

		else if ("listeemployeursactifs".equals(action)) {
			String datedebut = request.getParameter("datedebutrech");
			String datefin = request.getParameter("datefinrech");

			java.sql.Date deb = null, fin = null;
			deb = new FormaterDate().changeFormatChaineDate(datedebut);
			fin = new FormaterDate().changeFormatChaineDate(datefin);

			request.setAttribute("debut", deb);
			request.setAttribute("fin", fin);
			jspPage = "/jsp/employeurs/listeemployeursactifs.jsp";

		}
		/***********************
		 * partie ai
		 *******************************************/
		/*******************************************************************************/
		else if ("airechercheavancee".equals(action)) {
			String requete = "";
			String emploirecherche1 = request.getParameter("emploirome");
			// String emploisanstab=new
			// FormaterTexte().supprimerTableMatiere(emploirecherche1);
			// System.out.println("emploi ="+emploirecherche1);
			Rome rome = null;
			List<Rome> listecodesromes = null;
			RomeDAO rodao = new RomeDAO();

			if (!emploirecherche1.equals("SELECTION") && !emploirecherche1.equals("")) {

				try {
					rome = rodao.findByName(emploirecherche1);
					listecodesromes = rodao.rechercheParCodeNRome(rome.getNrome());

				} catch (DAOException e) {
					e.printStackTrace();

				}
				String afficheliste = "";
				for (int i = 0; i < listecodesromes.size(); i++) {
					if (i < listecodesromes.size() - 1)
						afficheliste += listecodesromes.get(i).getIdrome() + ",";
					else if (i == listecodesromes.size() - 1)
						afficheliste += listecodesromes.get(i).getIdrome();
				}
				requete += " and idrome in (" + afficheliste + ")";
				System.out.println("requete=" + requete);
				request.setAttribute("rome", rome.getIntitule());
			}

			String ville = request.getParameter("ville");
			// FormaterChaine fc=new FormaterChaine();
			// ville=fc.supprimerApostrophe(ville);
			if (!ville.equals("Aucune") && !ville.equals("")) {
				requete += " and ville_identite=\"" + ville + "\"";
				request.setAttribute("ville", ville);
			}
			String sexe = request.getParameter("sexe");
			if (!sexe.equals("Aucun") && !sexe.equals("")) {
				requete += " and sex_identite='" + sexe + "'";
				request.setAttribute("sexe", sexe);
			}
			String prenom = request.getParameter("prenom");
			if (!prenom.equals("")) {
				requete += " and prenom_identite='" + prenom + "'";
				request.setAttribute("prenom", prenom);
			}
			String agemini = request.getParameter("agemini");
			if (!agemini.equals("")) {
				requete += " and (YEAR(CURRENT_DATE)-YEAR(datenais_identite))-(RIGHT(CURRENT_DATE,5)<RIGHT(datenais_identite,5))>='"
						+ agemini + "'";
				request.setAttribute("agemini", agemini);
			}

			String agemaxi = request.getParameter("agemaxi");
			if (!agemaxi.equals("")) {
				requete += " and (YEAR(CURRENT_DATE)-YEAR(datenais_identite))-(RIGHT(CURRENT_DATE,5)<RIGHT(datenais_identite,5))<='"
						+ agemaxi + "'";
				request.setAttribute("agemaxi", agemaxi);
			}
			String cp = request.getParameter("cp");
			if (!cp.equals("")) {
				requete += " and cp_identite='" + cp + "'";
				request.setAttribute("cp", cp);
			}

			String situationfamiliale = request.getParameter("situationfamiliale");
			if (!situationfamiliale.equals("Aucun") && !situationfamiliale.equals("")) {
				requete += " and sitfam_identite='" + situationfamiliale + "'";
				request.setAttribute("sitfam", situationfamiliale);
			}
			String anpemini = request.getParameter("anpemini");
			if (!anpemini.equals("")) {
				requete += " and (YEAR(CURRENT_DATE)-YEAR(poleEmploiInscription_identite))-(RIGHT(CURRENT_DATE,5)<RIGHT(poleEmploiInscription_identite,5))>='"
						+ anpemini + "'";
				request.setAttribute("anpemini", anpemini);
			}
			String anpemaxi = request.getParameter("anpemaxi");
			if (!anpemaxi.equals("")) {
				requete += " and (YEAR(CURRENT_DATE)-YEAR(poleEmploiInscription_identite))-(RIGHT(CURRENT_DATE,5)<RIGHT(poleEmploiInscription_identite,5))<='"
						+ anpemaxi + "'";
				request.setAttribute("anpemaxi", anpemaxi);
			}
			String diplome = request.getParameter("diplome");
			if (!diplome.equals("")) {
				requete += " and diplome_diplome like '" + diplome + "%'";
				request.setAttribute("diplome", diplome);
			}

			String[] niveau = request.getParameterValues("niveau[]");
			if (niveau != null) {

				String ls = "";
				requete = requete + " and niveauFormation_identite in (";
				for (int i = 0; i < niveau.length; i++) {
					if (i < (niveau.length) - 1)
						ls += "'" + niveau[i] + "', ";
					else
						ls += "'" + niveau[i] + "')";
				}
				requete = requete + ls;
				request.setAttribute("niveau", niveau);
			}

			String[] priorites = request.getParameterValues("priorite[]");
			if (priorites != null) {
				String ls = "";
				requete = requete + " and libelle_priorite in (";
				for (int i = 0; i < priorites.length; i++) {
					if (i < (priorites.length) - 1)
						ls += "'" + priorites[i] + "', ";
					else
						ls += "'" + priorites[i] + "')";
				}
				requete = requete + ls;
				request.setAttribute("priorites", priorites);
			}

			String[] locomotions = request.getParameterValues("locomotion[]");

			if (locomotions != null) {
				String ls = "";
				requete = requete + " and libelle_locomotion in (";
				for (int i = 0; i < locomotions.length; i++) {
					if (i < (locomotions.length) - 1)
						ls += "'" + locomotions[i] + "', ";
					else
						ls += "'" + locomotions[i] + "')";

				}
				requete = requete + ls;
				request.setAttribute("locomotions", locomotions);
			}

			String[] permipro = request.getParameterValues("permis[]");
			if (permipro != null) {
				String ls = "";
				requete = requete + " and libelle_permis in (";
				for (int i = 0; i < permipro.length; i++) {
					if (i < (permipro.length) - 1)
						ls += "'" + permipro[i] + "', ";
					else
						ls += "'" + permipro[i] + "')";
				}
				requete = requete + ls;
				request.setAttribute("permipro", permipro);
			}

			String permib = request.getParameter("permib");
			if (permib != null) {
				requete += " and permib_identite=1 ";
				request.setAttribute("permib", permib);
			}

			IdentiteDAO iddao = new IdentiteDAO();
			List<Identite> liste = null;
			try {
				liste = iddao.afficherListeAIParSouhait(requete);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("liste", liste);

			jspPage = "/jsp/ai/recherche/rechercheavance.jsp";

		}

		else if ("airechercheparsuivi".equals(action)) {
			String utilisateurs = request.getParameter("utilisateurs");
			String debut = request.getParameter("datecreation");
			// java.sql.Date ddebut = null;
			// (!debut.equals("") && !debut.equals(null))
			// debut = new FormaterDate().formateDate(debut);

			UtilisateurDAO utildao = new UtilisateurDAO();
			Utilisateur referent = null;
			try {
				referent = utildao.findByName(utilisateurs);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SuiviPersonneDAO suidao = new SuiviPersonneDAO();
			List<List<String>> retour = null;
			try {
				retour = suidao.rechercheParSuivi(referent.getId_salarie(), debut);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("liste", retour);
			jspPage = "/jsp/ai/recherche/parsuivi.jsp?utilisateur=" + utilisateurs + "&date=" + debut;

		}

		else if ("creationficheai".equals(action)) {
			String personne = request.getParameter("personne");
			String permanent = request.getParameter("permanent");
			// String agrement=request.getParameter("agrement");
			// request.setAttribute("agrement", agrement);
			boolean oui = false;
			if (permanent.equals("oui"))
				oui = true;
			request.setAttribute("permanent", oui);

			IdentiteDAO idao = new IdentiteDAO();
			Identite une = null;
			try {
				une = idao.findByID(Integer.parseInt(personne));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			CreationDAO fiche = new CreationDAO();
			Creation nouvelle = new Creation(une, oui, null, null);
			try {
				fiche.create(nouvelle);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jspPage = "/jsp/ai/fiches/fichesuite.jsp?numero=" + personne;

		}

		else if ("aimodifierfichepermanent".equals(action)) {
			String id = request.getParameter("id");
			String personne = request.getParameter("personne");
			CreationDAO agdao = new CreationDAO();
			Creation creation = null;
			try {
				creation = agdao.findByID(Integer.parseInt(id));
				if (creation.isPermanent())
					creation.setPermanent(false);
				else
					creation.setPermanent(true);

				agdao.update(creation);

			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// on effectue la mise a jour du champ permanent

			jspPage = "/jsp/ai/fiches/fichesuite.jsp?numero=" + personne;
		}

		else if ("ainouveauagrement".equals(action)) {
			String personne = request.getParameter("personne");
			IdentiteDAO idao = new IdentiteDAO();
			Identite identite = null;
			try {
				identite = idao.findByID(Integer.parseInt(personne));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			String numero = request.getParameter("agrement");
			String datedebutagrement = request.getParameter("datedebutagrement");
			CreationDAO fiche = new CreationDAO();
			boolean oui = false;
			try {
				oui = fiche.verifierPermanentFiche(identite);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			request.setAttribute("permanent", oui);

			java.sql.Date deb = null, fin = null;
			java.util.Date tampon = null;
			deb = new FormaterDate().changeFormatChaineDate(datedebutagrement);
			request.setAttribute("debut", deb);
			// on rajoute 2 ans a la date de debut
			Calendar cal = Calendar.getInstance();
			cal.setTime(deb);
			cal.add(Calendar.YEAR, 2);
			tampon = cal.getTime();
			// conversion de java.util.date en java.sql.date
			fin = new java.sql.Date(tampon.getTime());
			request.setAttribute("fin", fin);
			Agrement agrement = null;
			try {
				agrement = new Agrement(new IdentiteDAO().findByID(Integer.parseInt(personne)), numero, deb, fin);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			AgrementDAO agdao = new AgrementDAO();
			try {
				agdao.create(agrement);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/ai/fiches/fichesuite.jsp?numero=" + personne;

		}

		else if ("aimodifieragrement".equals(action)) {
			String numagrement = request.getParameter("numagrement");
			String numero = request.getParameter("agrement");
			String idpers = request.getParameter("idpers");
			String datedebutagrement = request.getParameter("datedebutagrement");
			java.sql.Date deb = null, fin = null;
			deb = new FormaterDate().changeFormatChaineDate(datedebutagrement);
			java.util.Date tampon = null;

			// on rajoute 2 ans a la date de debut
			Calendar cal = Calendar.getInstance();
			cal.setTime(deb);
			cal.add(Calendar.YEAR, 2);
			tampon = cal.getTime();
			// conversion de java.util.date en java.sql.date
			fin = new java.sql.Date(tampon.getTime());

			AgrementDAO agdao = new AgrementDAO();
			Agrement agrement = null;
			try {
				agrement = agdao.findByID(Integer.parseInt(numagrement));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			IdentiteDAO iddao = new IdentiteDAO();
			Identite identite = null;
			try {
				identite = iddao.findByID(agrement.getIdentite().getId_IDE());
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Agrement nouveau = new Agrement(Integer.parseInt(numagrement), identite, numero, deb, fin);
			try {
				agrement = agdao.update(nouveau);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/ai/fiches/fichesuite.jsp?numero=" + agrement.getIdentite().getId_IDE() + "&personne="
					+ idpers;
		}

		else if ("aisupagrement".equals(action)) {
			String idagrement = request.getParameter("idagrement");
			String personne = request.getParameter("personne");
			AgrementDAO agdao = new AgrementDAO();

			try {
				agdao.delete(Long.parseLong(idagrement));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jspPage = "/jsp/ai/fiches/fichesuite.jsp?numero=" + personne;
		}

		else if ("ainouveauextranet".equals(action)) {
			String personne = request.getParameter("personne");
			IdentiteDAO idao = new IdentiteDAO();
			Identite identite = null;
			try {
				identite = idao.findByID(Integer.parseInt(personne));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			String datedebut = request.getParameter("datedebut");
			String datefin = request.getParameter("datefin");

			CreationDAO fiche = new CreationDAO();
			boolean oui = false;
			try {
				oui = fiche.verifierPermanentFiche(identite);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			request.setAttribute("permanent", oui);

			java.sql.Date deb = null, fin = null;
			deb = new FormaterDate().changeFormatChaineDate(datedebut);
			if (!datefin.equals("") && !datefin.equals(null))
				fin = new FormaterDate().changeFormatChaineDate(datefin);

			Extranet extra = null;
			try {
				extra = new Extranet(new IdentiteDAO().findByID(Integer.parseInt(personne)), deb, fin);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			ExtranetDAO agdao = new ExtranetDAO();
			try {
				agdao.create(extra);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/ai/fiches/fichesuite.jsp?numero=" + personne;

		}

		else if ("aimodifierextranet".equals(action)) {
			String idextranet = request.getParameter("idextranet");
			String idpers = request.getParameter("idpers");
			String datedebut = request.getParameter("datedebut");
			String datefin = request.getParameter("datefin");
			java.sql.Date deb = null, fin = null;
			deb = new FormaterDate().changeFormatChaineDate(datedebut);

			if (!datefin.equals("") && !datefin.equals(null))
				fin = new FormaterDate().changeFormatChaineDate(datefin);

			ExtranetDAO agdao = new ExtranetDAO();
			Extranet extra = null;
			try {
				extra = agdao.findByID(Integer.parseInt(idextranet));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			IdentiteDAO iddao = new IdentiteDAO();
			Identite identite = null;
			try {
				identite = iddao.findByID(extra.getIdentite().getId_IDE());
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Extranet nouveau = new Extranet(Integer.parseInt(idextranet), identite, deb, fin);
			try {
				extra = agdao.update(nouveau);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/ai/fiches/fichesuite.jsp?numero=" + extra.getIdentite().getId_IDE() + "&personne=" + idpers;
		}

		else if ("aisupextranet".equals(action)) {
			String idextranet = request.getParameter("idextranet");
			String personne = request.getParameter("personne");
			ExtranetDAO agdao = new ExtranetDAO();

			try {
				agdao.delete(Long.parseLong(idextranet));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jspPage = "/jsp/ai/fiches/fichesuite.jsp?numero=" + personne;
		}

		else if ("ainouvellevisite".equals(action)) {
			String personne = request.getParameter("personne");
			IdentiteDAO idao = new IdentiteDAO();
			Identite identite = null;
			try {
				identite = idao.findByID(Integer.parseInt(personne));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			String dateconvoc = request.getParameter("dateconvoc");
			String datevisite = request.getParameter("datevisite");

			CreationDAO fiche = new CreationDAO();
			boolean oui = false;
			try {
				oui = fiche.verifierPermanentFiche(identite);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			request.setAttribute("permanent", oui);

			java.sql.Date deb = null, vis = null, eche = null;
			java.util.Date tampon = null;

			if (!dateconvoc.equals("") && !dateconvoc.equals(null))
				deb = new FormaterDate().changeFormatChaineDate(dateconvoc);
			if (!datevisite.equals("") && !datevisite.equals(null)) {
				vis = new FormaterDate().changeFormatChaineDate(datevisite);
				// on rajoute 2 ans a la date de visite

				Calendar cal = Calendar.getInstance();
				cal.setTime(vis);
				cal.add(Calendar.YEAR, 2);
				tampon = cal.getTime();
				// conversion de java.util.date en java.sql.date

				eche = new java.sql.Date(tampon.getTime());
			}

			VisiteMedicale visitemed = null;
			try {
				visitemed = new VisiteMedicale(new IdentiteDAO().findByID(Integer.parseInt(personne)), deb, vis, eche);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			VisiteMedicaleDAO agdao = new VisiteMedicaleDAO();
			try {
				agdao.create(visitemed);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/ai/fiches/fichesuite.jsp?numero=" + personne;

		}

		else if ("aimodifiervisite".equals(action)) {
			String idvisite = request.getParameter("idvisite");

			String dateconvoc = request.getParameter("dateconvoc");
			String datevisite = request.getParameter("datevisite");

			java.sql.Date deb = null, vis = null, eche = null;
			if (!dateconvoc.equals("") && !dateconvoc.equals(null))
				deb = new FormaterDate().changeFormatChaineDate(dateconvoc);

			if (!datevisite.equals("") && !datevisite.equals(null))
				vis = new FormaterDate().changeFormatChaineDate(datevisite);

			java.util.Date tampon = null;

			// on rajoute 2 ans a la date de visite
			if (vis != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(vis);
				cal.add(Calendar.YEAR, 2);
				tampon = cal.getTime();
				// conversion de java.util.date en java.sql.date
				eche = new java.sql.Date(tampon.getTime());
			}

			VisiteMedicaleDAO agdao = new VisiteMedicaleDAO();
			VisiteMedicale visite = null;
			try {
				visite = agdao.findByID(Integer.parseInt(idvisite));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			IdentiteDAO iddao = new IdentiteDAO();
			Identite identite = null;
			try {
				identite = iddao.findByID(visite.getIdentite().getId_IDE());
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			VisiteMedicale nouveau = new VisiteMedicale(Integer.parseInt(idvisite), identite, deb, vis, eche);
			try {
				visite = agdao.update(nouveau);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/ai/fiches/fichesuite.jsp?personne=" + visite.getIdentite().getId_IDE();
		}

		else if ("aisupvisite".equals(action)) {
			String idvisite = request.getParameter("idvisite");
			String personne = request.getParameter("personne");
			VisiteMedicaleDAO agdao = new VisiteMedicaleDAO();

			try {
				agdao.delete(Long.parseLong(idvisite));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jspPage = "/jsp/ai/fiches/fichesuite.jsp?numero=" + personne;
		}

		else if ("aicontratnouveau".equals(action)) {

			FormaterDate jour = new FormaterDate();
			java.sql.Date redaction = null, debut = null, fin = null;

			/* on met la date du jour comme date redaction du contrat */
			String a = jour.getSortie();
			redaction = new FormaterDate().changeFormatChaineDate(a);

			String datedebut = request.getParameter("datedebut");
			if (!datedebut.equals("") && !datedebut.equals(null))
				debut = new FormaterDate().changeFormatChaineDate(datedebut);

			String datefin = request.getParameter("datefin");
			if (!datefin.equals("") && !datefin.equals(null))
				fin = new FormaterDate().changeFormatChaineDate(datefin);

			String numero = request.getParameter("numero");

			IdentiteDAO idao = new IdentiteDAO();
			Identite identite = null;
			try {
				identite = idao.findByID(Integer.parseInt(numero));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String employeurs = request.getParameter("employeurs");
			int position = employeurs.lastIndexOf("-");
			String idemployeur = employeurs.substring(position + 1);
			EmployeurDAO empdao = new EmployeurDAO();
			Employeur employeur = null;
			try {
				employeur = empdao.findByID(Integer.parseInt(idemployeur));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// System.out.println("id employeur ="+idemployeur);
			String services = request.getParameter("services");
			Service service = null;
			ServiceDAO serdao = new ServiceDAO();
			if (services != null) {
				try {
					service = serdao.findByName(services);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					service = serdao.findByID(1);
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			String emploipropose = request.getParameter("emploipropose");
			String rome = new FormaterTexte().supprimerTableMatiere(emploipropose);
			RomeDAO romdao = new RomeDAO();
			Rome metier = null;
			try {
				metier = romdao.findByName(rome);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String tache = request.getParameter("tache");
			String lieu = request.getParameter("lieu");
			String urssaf = request.getParameter("urssaf");

			String dureehebdo = request.getParameter("dureehebdo");
			/*
			 * String dureeh; if (!dureehebdo.equals("")) dureeh =
			 * Integer.parseInt(dureehebdo);
			 */
			String smich = request.getParameter("salhor");
			float smicHoraire = Float.parseFloat(smich);

			String facsalhor = request.getParameter("facsalhor");
			float facsh = Float.parseFloat(facsalhor);

			float pan = 0.00f;
			String panier = request.getParameter("panier");
			if (!panier.equals(""))
				pan = Float.parseFloat(panier);

			float facpan = 0.00f;
			String facpanier = request.getParameter("facpanier");
			if (!facpanier.equals(""))
				facpan = Float.parseFloat(facpanier);

			float deplace = 0.00f;
			String dep = request.getParameter("dep");
			if (!dep.equals(""))
				deplace = Float.parseFloat(dep);

			float facdeplace = 0.00f;
			String facdep = request.getParameter("facdep");
			if (!facdep.equals(""))
				facdeplace = Float.parseFloat(facdep);

			float dive = 0.00f;
			String divers = request.getParameter("divers");
			if (!divers.equals(""))
				dive = Float.parseFloat(divers);

			float facdiv = 0.00f;
			String facdivers = request.getParameter("facdivers");
			if (!facdivers.equals(""))
				facdiv = Float.parseFloat(facdivers);

			String comm = request.getParameter("comm");
			String faccomm = request.getParameter("faccomm");
			Contrat contrat = new Contrat(identite, employeur, service, metier, tache, lieu, debut, fin, dureehebdo,
					smicHoraire, facsh, pan, facpan, deplace, facdeplace, dive, facdiv, comm, faccomm, redaction,
					urssaf, "", false);
			// on recupere l'id du contrat
			ContratDAO condao = new ContratDAO();
			int idcontrat = 0;
			try {
				idcontrat = condao.create(contrat);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			contrat.setIdaicontrat(idcontrat);
			contrat.setNumerocontrat(idcontrat);
			try {
				contrat = condao.update(contrat);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/ai/contrat/affichage.jsp?contrat=" + idcontrat;
		}

		else if ("aimodifiercontrat".equals(action)) {

			java.sql.Date redaction = null, debut = null, fin = null;

			String dateredaction = request.getParameter("dateredaction");
			if (!dateredaction.equals("") && !dateredaction.equals(null))
				redaction = new FormaterDate().changeFormatChaineDate(dateredaction);

			String datedebut = request.getParameter("datedebut");
			if (!datedebut.equals("") && !datedebut.equals(null))
				debut = new FormaterDate().changeFormatChaineDate(datedebut);

			String datefin = request.getParameter("datefin");
			if (!datefin.equals("") && !datefin.equals(null))
				fin = new FormaterDate().changeFormatChaineDate(datefin);

			String numerocontrat = request.getParameter("numerocontrat");
			int idcontratrecup = Integer.parseInt(numerocontrat);
			ContratDAO contdao = new ContratDAO();
			Contrat contatrecupere = null;
			try {
				contatrecupere = contdao.findByID(idcontratrecup);
			} catch (NumberFormatException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (DAOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			IdentiteDAO idao = new IdentiteDAO();
			Identite identite = null;
			try {
				identite = idao.findByID(contatrecupere.getIdentite().getId_IDE());
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String idemployeur = request.getParameter("employeurs");
			int i = idemployeur.length();
			int j = idemployeur.lastIndexOf("-");
			int code = Integer.parseInt(idemployeur.substring((j + 1), i));

			EmployeurDAO empdao = new EmployeurDAO();
			Employeur employeur = null;
			try {
				employeur = empdao.findByID(code);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String services = request.getParameter("services");
			Service service = null;
			ServiceDAO serdao = new ServiceDAO();
			if (services != null) {
				try {
					service = serdao.findByName(services);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					service = serdao.findByID(1);
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			String emploipropose = request.getParameter("emploipropose");
			String rome = new FormaterTexte().supprimerTableMatiere(emploipropose);
			RomeDAO romdao = new RomeDAO();
			Rome metier = null;
			try {
				metier = romdao.findByName(rome);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String tache = request.getParameter("tache");
			String lieu = request.getParameter("lieu");
			String urssaf = request.getParameter("urssaf");

			String dureehebdo = request.getParameter("dureehebdo");
			/*
			 * int dureeh = 0; if (!dureehebdo.equals("")) dureeh =
			 * Integer.parseInt(dureehebdo);
			 */
			String salhor = request.getParameter("salhor");
			float smicHoraire = Float.parseFloat(salhor);

			String facsalhor = request.getParameter("facsalhor");
			float facsh = Float.parseFloat(facsalhor);

			float pan = 0.00f;
			String panier = request.getParameter("panier");
			if (!panier.equals(""))
				pan = Float.parseFloat(panier);

			float facpan = 0.00f;
			String facpanier = request.getParameter("facpanier");
			if (!facpanier.equals(""))
				facpan = Float.parseFloat(facpanier);

			float deplace = 0.00f;
			String dep = request.getParameter("dep");
			if (!dep.equals(""))
				deplace = Float.parseFloat(dep);

			float facdeplace = 0.00f;
			String facdep = request.getParameter("facdep");
			if (!facdep.equals(""))
				facdeplace = Float.parseFloat(facdep);

			float dive = 0.00f;
			String divers = request.getParameter("divers");
			if (!divers.equals(""))
				dive = Float.parseFloat(divers);

			float facdiv = 0.00f;
			String facdivers = request.getParameter("facdivers");
			if (!facdivers.equals(""))
				facdiv = Float.parseFloat(facdivers);

			String comm = request.getParameter("comm");
			String faccomm = request.getParameter("faccomm");
			Contrat contrat = new Contrat(identite, employeur, service, metier, tache, lieu, debut, fin, dureehebdo,
					smicHoraire, facsh, pan, facpan, deplace, facdeplace, dive, facdiv, comm, faccomm, redaction,
					urssaf, "", false);

			ContratDAO condao = new ContratDAO();

			contrat.setIdaicontrat(idcontratrecup);
			contrat.setNumerocontrat(idcontratrecup);
			try {
				contrat = condao.update(contrat);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/ai/contrat/affichage.jsp?contrat=" + idcontratrecup;
		}

		else if ("aicontratnouveauavenant".equals(action)) {

			FormaterDate jour = new FormaterDate();
			java.sql.Date redaction = null, debut = null, fin = null;

			/* on met la date du jour comme date redaction du contrat */
			String a = jour.getSortie();
			redaction = new FormaterDate().changeFormatChaineDate(a);

			String datedebut = request.getParameter("datedebut");
			if (!datedebut.equals("") && !datedebut.equals(null))
				debut = new FormaterDate().changeFormatChaineDate(datedebut);

			String datefin = request.getParameter("datefin");
			if (!datefin.equals("") && !datefin.equals(null))
				fin = new FormaterDate().changeFormatChaineDate(datefin);

			// String idpersonne=request.getParameter("numero");

			/*
			 * IdentiteDAO idao=new IdentiteDAO(); Identite identite=null; try {
			 * identite=idao.findByID(Integer.parseInt(idpersonne)); } catch
			 * (NumberFormatException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); } catch (DAOException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); }
			 */

			String idcontrat = request.getParameter("idcontrat");
			ContratDAO codao = new ContratDAO();
			Contrat contrat = null;
			try {
				contrat = codao.findByID(Integer.parseInt(idcontrat));
			} catch (NumberFormatException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (DAOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			/*
			 * EmployeurDAO empdao=new EmployeurDAO(); Employeur employeur=null;
			 * try {
			 * employeur=empdao.findByID(contrat.getEmployeur().getId_employeur
			 * ()); } catch (DAOException e2) { // TODO Auto-generated catch
			 * block e2.printStackTrace(); }
			 */

			String tache = request.getParameter("tache");
			String lieu = request.getParameter("lieu");

			String dureehebdo = request.getParameter("dureehebdo");

			String salhor = request.getParameter("salhor");
			float smicHoraire = Float.parseFloat(salhor);

			String facsalhor = request.getParameter("facsalhor");
			float facsh = Float.parseFloat(facsalhor);

			float pan = 0.00f;
			String panier = request.getParameter("panier");
			if (!panier.equals(""))
				pan = Float.parseFloat(panier);

			float facpan = 0.00f;
			String facpanier = request.getParameter("facpanier");
			if (!facpanier.equals(""))
				facpan = Float.parseFloat(facpanier);

			float deplace = 0.00f;
			String dep = request.getParameter("dep");
			if (!dep.equals(""))
				deplace = Float.parseFloat(dep);

			float facdeplace = 0.00f;
			String facdep = request.getParameter("facdep");
			if (!facdep.equals(""))
				facdeplace = Float.parseFloat(facdep);

			float dive = 0.00f;
			String divers = request.getParameter("divers");
			if (!divers.equals(""))
				dive = Float.parseFloat(divers);

			float facdiv = 0.00f;
			String facdivers = request.getParameter("facdivers");
			if (!facdivers.equals(""))
				facdiv = Float.parseFloat(facdivers);

			String comm = request.getParameter("comm");
			String faccomm = request.getParameter("faccomm");

			Avenant avenant = new Avenant(contrat, debut, fin, smicHoraire, facsh, pan, facpan, deplace, facdeplace,
					dive, facdiv, comm, faccomm, redaction, dureehebdo, tache, lieu);
			// on recupere l'id du contrat
			AvenantDAO avendao = new AvenantDAO();
			int idavenantContrat = 0;

			try {
				idavenantContrat = avendao.recupereNumeroAvenantEContrat(contrat);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int idavenant = 0;
			try {
				idavenant = avendao.create(avenant);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String numeroavenantcomplet = "A" + idavenantContrat + " C" + idcontrat;
			avenant.setIdavenant(idavenant);
			avenant.setNaiav(idavenantContrat);
			avenant.setN_aiavenant(numeroavenantcomplet);
			avenant.setDureehebdomadaire(dureehebdo);
			try {
				avenant = avendao.update(avenant);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/ai/contrat/affichage.jsp?contrat=" + idcontrat;
		}

		else if ("aimodifieravenantcontrat".equals(action)) {

			FormaterDate jour = new FormaterDate();
			java.sql.Date redaction = null, debut = null, fin = null;

			/*
			 * on met la date du jour comme date redaction de l'avenant si elle
			 * n'est pas renseignée
			 */
			String dateredaction = request.getParameter("dateredaction");
			if (!dateredaction.equals("") && !dateredaction.equals(null)) {
				redaction = new FormaterDate().changeFormatChaineDate(dateredaction);
			} else {
				String a = jour.getSortie();
				redaction = new FormaterDate().changeFormatChaineDate(a);
			}

			String datedebut = request.getParameter("datedebut");
			if (!datedebut.equals("") && !datedebut.equals(null))
				debut = new FormaterDate().changeFormatChaineDate(datedebut);

			String datefin = request.getParameter("datefin");
			if (!datefin.equals("") && !datefin.equals(null))
				fin = new FormaterDate().changeFormatChaineDate(datefin);

			String numeroavenant = request.getParameter("numeroavenant");
			AvenantDAO avdao = new AvenantDAO();
			Avenant avenant = null;
			try {
				avenant = avdao.findByID(Integer.parseInt(numeroavenant));
			} catch (NumberFormatException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (DAOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			String tache = request.getParameter("tache");
			String lieu = request.getParameter("lieu");

			String dureehebdo = request.getParameter("dureehebdo");

			String salhor = request.getParameter("salhor");
			float smicHoraire = Float.parseFloat(salhor);

			String facsalhor = request.getParameter("facsalhor");
			float facsh = Float.parseFloat(facsalhor);

			float pan = 0.00f;
			String panier = request.getParameter("panier");
			if (!panier.equals(""))
				pan = Float.parseFloat(panier);

			float facpan = 0.00f;
			String facpanier = request.getParameter("facpanier");
			if (!facpanier.equals(""))
				facpan = Float.parseFloat(facpanier);

			float deplace = 0.00f;
			String dep = request.getParameter("dep");
			if (!dep.equals(""))
				deplace = Float.parseFloat(dep);

			float facdeplace = 0.00f;
			String facdep = request.getParameter("facdep");
			if (!facdep.equals(""))
				facdeplace = Float.parseFloat(facdep);

			float dive = 0.00f;
			String divers = request.getParameter("divers");
			if (!divers.equals(""))
				dive = Float.parseFloat(divers);

			float facdiv = 0.00f;
			String facdivers = request.getParameter("facdivers");
			if (!facdivers.equals(""))
				facdiv = Float.parseFloat(facdivers);

			String comm = request.getParameter("comm");
			String faccomm = request.getParameter("faccomm");
			avenant.setRedaction(redaction);
			avenant.setDatedeb(debut);
			avenant.setDatefin(fin);
			avenant.setTache(tache);
			avenant.setLieu(lieu);
			avenant.setDureehebdomadaire(dureehebdo);
			avenant.setSalairehoraire(smicHoraire);
			avenant.setFacturation(facsh);
			avenant.setPanier(pan);
			avenant.setFacturepanier(facpan);
			avenant.setDeplacement(deplace);
			avenant.setFacturedeplace(facdeplace);
			avenant.setDivers(dive);
			avenant.setFacturedivers(facdiv);
			avenant.setCommentaire(comm);
			avenant.setFacturecommentaire(faccomm);

			try {
				avenant = avdao.update(avenant);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/ai/avenant/affichage.jsp?avenant=" + numeroavenant;
		}

		else if ("ailistecontratpardatefin".equals(action)) {
			String a = request.getParameter("a");
			String debut = null, fin = null;
			java.sql.Date ddeb = null, dfin = null;

			// recuperation des dates de debut et fin du mois suivant

			Calendar cal0 = Calendar.getInstance();
			java.util.Date date = cal0.getTime();
			// System.out.println("mois " + date);

			cal0.add(Calendar.MONTH, +1);
			java.util.Date date1 = cal0.getTime();
			// System.out.println("mois +1 " + date1);

			cal0.add(Calendar.MONTH, +1);
			java.util.Date date2 = cal0.getTime();
			// System.out.println("mois +2 " + date2);

			cal0.add(Calendar.MONTH, -3);
			java.util.Date datemoins1 = cal0.getTime();
			// System.out.println("mois -1 " + datemoins1);

			// Calendar calmoins2=Calendar.getInstance();
			cal0.add(Calendar.MONTH, -1);
			java.util.Date datemoins2 = cal0.getTime();
			// System.out.println("mois - 2 " + datemoins2);

			ContratDAO codao = new ContratDAO();
			AvenantDAO avdao = new AvenantDAO();
			List<Contrat> listecontrat = null;
			List<Avenant> listeAvenant = null;

			if (a == null) {
				debut = request.getParameter("datedebut");
				fin = request.getParameter("datefin");
				ddeb = new FormaterDate().changeFormatChaineDate(debut);
				dfin = new FormaterDate().changeFormatChaineDate(fin);
				try {
					listecontrat = codao.contratAvenantParDateFin(ddeb, dfin);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("contrats", listecontrat);
				try {
					listeAvenant = avdao.listeAvenantParDates(ddeb, dfin);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("avenants", listeAvenant);
			} else {
				if (a.equals("-2")) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(datemoins2);
					int njours = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

					cal.set(Calendar.DATE, 1);
					ddeb = new java.sql.Date(cal.getTime().getTime());
					cal.set(Calendar.DATE, njours);
					dfin = new java.sql.Date(cal.getTime().getTime());

				} else if (a.equals("-1")) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(datemoins1);
					int njours = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

					cal.set(Calendar.DATE, 1);
					ddeb = new java.sql.Date(cal.getTime().getTime());
					cal.set(Calendar.DATE, njours);
					dfin = new java.sql.Date(cal.getTime().getTime());

				}

				else if (a.equals("0")) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					int njours = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

					cal.set(Calendar.DATE, 1);
					ddeb = new java.sql.Date(cal.getTime().getTime());
					cal.set(Calendar.DATE, njours);
					dfin = new java.sql.Date(cal.getTime().getTime());

				}

				else if (a.equals("1")) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(date1);
					int njours = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

					cal.set(Calendar.DATE, 1);
					ddeb = new java.sql.Date(cal.getTime().getTime());
					cal.set(Calendar.DATE, njours);
					dfin = new java.sql.Date(cal.getTime().getTime());

				}

				else if (a.equals("2")) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(date2);
					int njours = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

					cal.set(Calendar.DATE, 1);
					ddeb = new java.sql.Date(cal.getTime().getTime());
					cal.set(Calendar.DATE, njours);
					dfin = new java.sql.Date(cal.getTime().getTime());

				}

				try {
					listecontrat = codao.contratAvenantParDateFin(ddeb, dfin);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("contrats", listecontrat);
				try {
					listeAvenant = avdao.listeAvenantParDates(ddeb, dfin);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("avenants", listeAvenant);

			}

			jspPage = "/jsp/ai/listes/pardates.jsp?datedebut=" + ddeb + "&datefin=" + dfin;

		}

		else if ("aisalariesencontratsur".equals(action)) {
			String a = request.getParameter("a");
			String debut = null, fin = null;
			java.sql.Date ddeb = null, dfin = null;

			// recuperation des dates de debut et fin du mois suivant

			Calendar cal0 = Calendar.getInstance();
			java.util.Date date = cal0.getTime();
			// System.out.println("mois " + date);

			cal0.add(Calendar.MONTH, +1);
			java.util.Date date1 = cal0.getTime();
			// System.out.println("mois +1 " + date1);

			cal0.add(Calendar.MONTH, +1);
			java.util.Date date2 = cal0.getTime();
			// System.out.println("mois +2 " + date2);

			cal0.add(Calendar.MONTH, -3);
			java.util.Date datemoins1 = cal0.getTime();
			// System.out.println("mois -1 " + datemoins1);

			// Calendar calmoins2=Calendar.getInstance();
			cal0.add(Calendar.MONTH, -1);
			java.util.Date datemoins2 = cal0.getTime();
			// System.out.println("mois - 2 " + datemoins2);

			ContratDAO codao = new ContratDAO();
			AvenantDAO avdao = new AvenantDAO();
			List<Contrat> listecontrat = null;
			List<Avenant> listeAvenant = null;

			if (a == null) {
				debut = request.getParameter("datedebut");
				fin = request.getParameter("datefin");
				ddeb = new FormaterDate().changeFormatChaineDate(debut);
				dfin = new FormaterDate().changeFormatChaineDate(fin);
				try {
					listecontrat = codao.contratAIsurPeriode(ddeb, dfin);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("contrats", listecontrat);
				try {
					listeAvenant = avdao.listeAvenantSurPeriode(ddeb, dfin);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("avenants", listeAvenant);
			} else {
				if (a.equals("-2")) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(datemoins2);
					int njours = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

					cal.set(Calendar.DATE, 1);
					ddeb = new java.sql.Date(cal.getTime().getTime());
					cal.set(Calendar.DATE, njours);
					dfin = new java.sql.Date(cal.getTime().getTime());

				} else if (a.equals("-1")) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(datemoins1);
					int njours = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

					cal.set(Calendar.DATE, 1);
					ddeb = new java.sql.Date(cal.getTime().getTime());
					cal.set(Calendar.DATE, njours);
					dfin = new java.sql.Date(cal.getTime().getTime());

				}

				else if (a.equals("0")) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					int njours = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

					cal.set(Calendar.DATE, 1);
					ddeb = new java.sql.Date(cal.getTime().getTime());
					cal.set(Calendar.DATE, njours);
					dfin = new java.sql.Date(cal.getTime().getTime());

				}

				else if (a.equals("1")) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(date1);
					int njours = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

					cal.set(Calendar.DATE, 1);
					ddeb = new java.sql.Date(cal.getTime().getTime());
					cal.set(Calendar.DATE, njours);
					dfin = new java.sql.Date(cal.getTime().getTime());

				}

				else if (a.equals("2")) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(date2);
					int njours = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

					cal.set(Calendar.DATE, 1);
					ddeb = new java.sql.Date(cal.getTime().getTime());
					cal.set(Calendar.DATE, njours);
					dfin = new java.sql.Date(cal.getTime().getTime());

				}

				try {
					listecontrat = codao.contratAIsurPeriode(ddeb, dfin);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("contrats", listecontrat);
				try {
					listeAvenant = avdao.listeAvenantSurPeriode(ddeb, dfin);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("avenants", listeAvenant);

			}

			jspPage = "/jsp/ai/listes/contratsPeriodes.jsp?datedebut=" + ddeb + "&datefin=" + dfin;

		}

		else if ("ailistecontratdansfourchette".equals(action)) {

			String debut = null, fin = null;
			debut = request.getParameter("datedebut");
			fin = request.getParameter("datefin");
			java.sql.Date ddeb = null, dfin = null;

			if (!debut.equals("") && !debut.equals(null))
				ddeb = new FormaterDate().changeFormatChaineDate(debut);

			if (!fin.equals("") && !fin.equals(null))
				dfin = new FormaterDate().changeFormatChaineDate(fin);

			ContratDAO codao = new ContratDAO();
			AvenantDAO avdao = new AvenantDAO();
			List<Contrat> listecontrat = null;
			List<Avenant> listeAvenant = null;

			try {
				listecontrat = codao.contratDansFourchetteDate(ddeb, dfin);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("contrats", listecontrat);
			try {
				listeAvenant = avdao.listeAvenantDansFourchetteDates(ddeb, dfin);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("avenants", listeAvenant);

			jspPage = "/jsp/ai/listes/parfourchettedates.jsp?datedebut=" + debut + "&datefin=" + fin;

		}

		else if ("listeavenantpardates".equals(action)) {
			String deb = request.getParameter("datedebutrech");
			String fin = request.getParameter("datefinrech");

			java.sql.Date ddeb = null, dfin = null;
			if (!deb.equals("") && !deb.equals(null))
				ddeb = new FormaterDate().changeFormatChaineDate(deb);
			if (!fin.equals("") && !fin.equals(null))
				dfin = new FormaterDate().changeFormatChaineDate(fin);
			AgrementDAO agdao = new AgrementDAO();
			List<Agrement> liste = Collections.emptyList();
			try {
				liste = agdao.listeAgrementParDates(ddeb, dfin);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("liste", liste);

			jspPage = "/jsp/pdf/ai/agrement/listing.jsp?datedebut=" + ddeb + "&datefin=" + dfin;

		}

		else if ("dmo".equals(action)) {
			String mois = request.getParameter("mois");
			String an = request.getParameter("an");
			FormaterDate fd = new FormaterDate();
			int idmois = fd.moisCorrespondant(mois);
			// System.out.println("mois = " + idmois);

			int annee = Integer.parseInt(an);
			// System.out.println("annee = " + annee);

			ContratDAO codao = new ContratDAO();
			AvenantDAO avdao = new AvenantDAO();
			List<Contrat> listecontrat = null;
			List<Avenant> listeAvenant = null;

			try {
				listecontrat = codao.dmoContrat(idmois, annee);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("listecontrats", listecontrat);

			try {
				listeAvenant = avdao.dmoAvenant(idmois, annee);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("listeavenants", listeAvenant);

			jspPage = "/jsp/ai/dmo/selectionmois.jsp?mois=" + idmois + "&an=" + an + "&moislettre=" + mois;

		}

		else if ("relevehoraire".equals(action)) {
			String mois = request.getParameter("mois");
			String an = request.getParameter("an");
			FormaterDate fd = new FormaterDate();
			int idmois = fd.moisCorrespondant(mois);
			// System.out.println("mois = " + idmois);

			int annee = Integer.parseInt(an);
			// System.out.println("annee = " + annee);
			jspPage = "/jsp/pdf/ai/permanents/listing.jsp?mois=" + idmois + "&an=" + annee;

		}

		else if ("ailistevisitespardatefin".equals(action)) {
			String a = request.getParameter("a");
			String debut = null, fin = null;
			java.sql.Date ddeb = null, dfin = null;

			// recuperation des dates de debut et fin du mois suivant

			Calendar cal0 = Calendar.getInstance();
			java.util.Date date = cal0.getTime();
			// System.out.println("mois " + date);

			cal0.add(Calendar.MONTH, +1);
			java.util.Date date1 = cal0.getTime();
			// System.out.println("mois +1 " + date1);

			cal0.add(Calendar.MONTH, +1);
			java.util.Date date2 = cal0.getTime();
			// System.out.println("mois +2 " + date2);

			VisiteMedicaleDAO codao = new VisiteMedicaleDAO();

			List<VisiteMedicale> liste = null;

			if (a == null) {
				debut = request.getParameter("datedebut");
				fin = request.getParameter("datefin");
				ddeb = new FormaterDate().changeFormatChaineDate(debut);
				dfin = new FormaterDate().changeFormatChaineDate(fin);
				try {
					liste = codao.listeVisitesParDates(ddeb, dfin);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("listevisites", liste);
			}

			else {

				if (a.equals("0")) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					int njours = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

					cal.set(Calendar.DATE, 1);
					ddeb = new java.sql.Date(cal.getTime().getTime());
					cal.set(Calendar.DATE, njours);
					dfin = new java.sql.Date(cal.getTime().getTime());

				}

				else if (a.equals("1")) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(date1);
					int njours = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

					cal.set(Calendar.DATE, 1);
					ddeb = new java.sql.Date(cal.getTime().getTime());
					cal.set(Calendar.DATE, njours);
					dfin = new java.sql.Date(cal.getTime().getTime());

				}

				else if (a.equals("2")) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(date2);
					int njours = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

					cal.set(Calendar.DATE, 1);
					ddeb = new java.sql.Date(cal.getTime().getTime());
					cal.set(Calendar.DATE, njours);
					dfin = new java.sql.Date(cal.getTime().getTime());
				}

				try {
					liste = codao.listeVisitesParDates(ddeb, dfin);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("listevisites", liste);

			}

			jspPage = "/jsp/ai/visitemedicale/listingpardates.jsp?datedebut=" + ddeb + "&datefin=" + dfin;
		}

		else if ("aistatistiquescontratseul".equals(action)) {
			String deb = request.getParameter("datedebutrech");
			String fin = request.getParameter("datefinrech");

			java.sql.Date ddeb = null, dfin = null;
			if (!deb.equals("") && !deb.equals(null))
				ddeb = new FormaterDate().changeFormatChaineDate(deb);

			if (!fin.equals("") && !fin.equals(null))
				dfin = new FormaterDate().changeFormatChaineDate(fin);

			// statistiques permanents
			IdentiteDAO identitedao = new IdentiteDAO();
			List<Identite> listePersonnes = null;
			try {
				listePersonnes = identitedao.afficherStatistiquesAI(ddeb, dfin);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.setAttribute("listestat", listePersonnes);

			jspPage = "/jsp/ai/statistiques/contratseul.jsp?debut=" + deb + "&fin=" + fin;

		} else if ("aistatistiquescontratmois".equals(action)) {
			String deb = request.getParameter("datedebutrech");
			String fin = request.getParameter("datefinrech");

			List<String> listecontrats = null;
			List<String> listeavenants = null;

			ContratDAO contdao = new ContratDAO();
			if (!deb.equals("") && !deb.equals(null)) {
				try {
					listecontrats = contdao.nombreContratsParMois(deb, fin);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			request.setAttribute("contrats", listecontrats);

			AvenantDAO avedao = new AvenantDAO();
			if (!fin.equals("") && !fin.equals(null)) {
				try {
					listeavenants = avedao.nombreAvenantsParMois(deb, fin);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			request.setAttribute("avenants", listeavenants);

			jspPage = "/jsp/ai/statistiques/contratmois.jsp?debut=" + deb + "&fin=" + fin;

		}

		else if ("aistatistiquescontratavenant".equals(action)) {
			String deb = request.getParameter("datedebutrech");
			String fin = request.getParameter("datefinrech");

			java.sql.Date ddeb = null, dfin = null;
			if (!deb.equals("") && !deb.equals(null))
				ddeb = new FormaterDate().changeFormatChaineDate(deb);

			if (!fin.equals("") && !fin.equals(null))
				dfin = new FormaterDate().changeFormatChaineDate(fin);

			// statistiques permanents
			IdentiteDAO identitedao = new IdentiteDAO();
			List<Identite> listePersonnes = null;
			try {
				listePersonnes = identitedao.afficherStatistiquesAIcontratAvenant(ddeb, dfin);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.setAttribute("listestat", listePersonnes);

			jspPage = "/jsp/ai/statistiques/contratparavenant.jsp?debut=" + deb + "&fin=" + fin;

		}

		/*********************************************************************************/
		/* RMI RSA */
		/*********************************************************************************/

		else if ("creationfichermi".equals(action)) {
			String personne = request.getParameter("personne");
			String deb = request.getParameter("datedebut");
			String fin = request.getParameter("datefin");
			String creation = request.getParameter("datecreation");

			java.sql.Date ddebut = null, dfin = null, dcreation = null;
			if (!deb.equals("") && !deb.equals(null))
				ddebut = new FormaterDate().changeFormatChaineDate(deb);

			if (!fin.equals("") && !fin.equals(null))
				dfin = new FormaterDate().changeFormatChaineDate(fin);

			if (!creation.equals("") && !creation.equals(null))
				dcreation = new FormaterDate().changeFormatChaineDate(creation);

			IdentiteDAO idao = new IdentiteDAO();
			Identite identite = null;
			try {
				identite = idao.findByID(Integer.parseInt(personne));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String utilisateurs = request.getParameter("utilisateurs");

			UtilisateurDAO utildao = new UtilisateurDAO();
			Utilisateur utilisateur = null;
			try {
				utilisateur = utildao.findByName(utilisateurs);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String instructeur = request.getParameter("instructeur");
			String conseiller = request.getParameter("conseiller");
			String fonction = request.getParameter("fonction");
			String agence = request.getParameter("agencepresripteur");
			String mail = request.getParameter("mailcg82");

			FicheRMIDAO ficdao = new FicheRMIDAO();
			FicheRMI fiche = new FicheRMI(identite, instructeur, conseiller, utilisateur, dcreation, fonction, agence,
					mail);
			int idfiche = 0;

			ContratRMIDAO contdao = new ContratRMIDAO();
			ContratRMI contratrmi = new ContratRMI(identite, ddebut, dfin);
			int idcontratrmi = 0;
			try {
				idcontratrmi = contdao.create(contratrmi);
				idfiche = ficdao.create(fiche);

			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/rmi_rsa/fiches/fichermisuite.jsp?personne=" + personne;
		}

		else if ("convocationrmi".equals(action)) {
			String personne = request.getParameter("personne");
			String creation = request.getParameter("datecreation");
			String utilisateurs = request.getParameter("utilisateurs");
			String heure = request.getParameter("heure");

			FormaterDate jour = new FormaterDate();
			String a = jour.getSortie();

			java.sql.Date dcreation = null, djour = null;
			djour = new FormaterDate().changeFormatChaineDate(a);

			if (!creation.equals("") && !creation.equals(null))
				dcreation = new FormaterDate().changeFormatChaineDate(creation);

			IdentiteDAO idao = new IdentiteDAO();
			Identite identite = null;
			try {
				identite = idao.findByID(Integer.parseInt(personne));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			UtilisateurDAO utildao = new UtilisateurDAO();
			Utilisateur referent = null;
			try {
				referent = utildao.findByName(utilisateurs);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			FicheRMIDAO ficdao = new FicheRMIDAO();
			FicheRMI fichermi = null;
			try {
				fichermi = ficdao.recupereDerniereFicheRMI(identite);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			ConvocRMIDAO condao = new ConvocRMIDAO();
			ConvocRMI convocation = new ConvocRMI(fichermi, identite, djour, dcreation, heure, referent);

			try {
				condao.create(convocation);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jspPage = "/jsp/rmi_rsa/fiches/fichermisuite.jsp?personne=" + personne;

		}

		else if ("modificationfichermi".equals(action)) {
			String idfiche = request.getParameter("fiche");

			FicheRMIDAO ficdao = new FicheRMIDAO();
			FicheRMI fiche = null;
			try {
				fiche = ficdao.findByID(Integer.parseInt(idfiche));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			String creation = request.getParameter("datecreation");

			java.sql.Date dcreation = null;

			if (!creation.equals("") && !creation.equals(null))
				dcreation = new FormaterDate().changeFormatChaineDate(creation);

			fiche.setCreation_rmi(dcreation);

			String service = request.getParameter("instructeur");
			fiche.setPrescripteur(service);

			String responsable = request.getParameter("responsable");
			fiche.setResponsable(responsable);

			String fonction = request.getParameter("fonction");
			fiche.setFonction(fonction);

			String agence = request.getParameter("agence");
			fiche.setAgence(agence);

			String mail = request.getParameter("mail");
			fiche.setMail(mail);

			String referent = request.getParameter("utilisateurs");
			UtilisateurDAO utildao = new UtilisateurDAO();
			Utilisateur utilisateur = null;
			try {
				utilisateur = utildao.findByName(referent);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			fiche.setReferent(utilisateur);

			try {
				ficdao.update(fiche);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/rmi_rsa/fiches/fichermisuite.jsp?personne=" + fiche.getIdentite().getId_IDE();

		}

		else if ("rmicontratnouveau".equals(action)) {
			// String idfiche=request.getParameter("fiche");
			String idpersonne = request.getParameter("numero");
			String datedebut = request.getParameter("datedebut");
			String datefin = request.getParameter("datefin");

			IdentiteDAO idao = new IdentiteDAO();
			Identite identite = null;
			try {
				identite = idao.findByID(Integer.parseInt(idpersonne));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			java.sql.Date ddeb = null, dfin = null;

			if (!datedebut.equals("") && !datedebut.equals(null))
				ddeb = new FormaterDate().changeFormatChaineDate(datedebut);

			if (!datefin.equals("") && !datefin.equals(null))
				dfin = new FormaterDate().changeFormatChaineDate(datefin);

			int idcontrat = 0;
			ContratRMIDAO contdao = new ContratRMIDAO();
			ContratRMI contrat = new ContratRMI(identite, ddeb, dfin);
			try {
				idcontrat = contdao.create(contrat);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/rmi_rsa/fiches/fichermisuite.jsp?personne=" + identite.getId_IDE();

		}

		else if ("rmicontratmodif".equals(action)) {
			// String idfiche=request.getParameter("fiche");
			String cont = request.getParameter("contrat");

			ContratRMIDAO contdao = new ContratRMIDAO();
			ContratRMI contrat = null;
			try {
				contrat = contdao.findByID(Integer.parseInt(cont));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			String datedebut = request.getParameter("datedebut");
			String datefin = request.getParameter("datefin");

			java.sql.Date ddeb = null, dfin = null;

			if (!datedebut.equals("") && !datedebut.equals(null))
				ddeb = new FormaterDate().changeFormatChaineDate(datedebut);

			if (!datefin.equals("") && !datefin.equals(null))
				dfin = new FormaterDate().changeFormatChaineDate(datefin);

			contrat.setDatedeb_rmicontrat(ddeb);
			contrat.setDatefin_rmicontrat(dfin);
			try {
				contrat = contdao.update(contrat);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/rmi_rsa/fiches/fichermisuite.jsp?personne=" + contrat.getIdentite().getId_IDE();

		}

		else if ("creationfichecli".equals(action)) {
			String cont = request.getParameter("contrat");
			String datecreation = request.getParameter("datecreation");

			java.sql.Date ddeb = null;
			if (!datecreation.equals("") && !datecreation.equals(null))
				ddeb = new FormaterDate().changeFormatChaineDate(datecreation);

			String prescripteur = request.getParameter("prescripteur");

			String nomreferentpe = "", prenomreferentpe = "", mailreferentpe = "";

			if (prescripteur.equals("Pole Emploi")) {
				ReferentPoleEmploiDAO refdao = new ReferentPoleEmploiDAO();
				ReferentPoleEmploi referent = null;
				try {
					referent = refdao.afficheReferent();
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				nomreferentpe = referent.getNom();
				prenomreferentpe = referent.getPrenom();
				mailreferentpe = referent.getMail();
			}

			String presence = request.getParameter("presence");
			String acquis = request.getParameter("acquis");
			String nomacquis = request.getParameter("nonacquis");
			String encours = request.getParameter("encours");
			String conclusion = request.getParameter("conclusion");
			String obsprescripteur = request.getParameter("obsprescripteur");
			ContratRMIDAO contdao = new ContratRMIDAO();
			ContratRMI contrat = null;
			try {
				contrat = contdao.findByID(Integer.parseInt(cont));
			} catch (NumberFormatException | DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			FicheLiaisonRMIDAO ficdao = new FicheLiaisonRMIDAO();
			FicheLiaisonRMI ficheliaison = new FicheLiaisonRMI(contrat, ddeb, nomreferentpe, prenomreferentpe,
					mailreferentpe, obsprescripteur, presence, acquis, nomacquis, encours, conclusion);
			try {
				ficdao.create(ficheliaison);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/rmi_rsa/fiches/fichermisuite.jsp?personne=" + contrat.getIdentite().getId_IDE();

		}

		else if ("modificationfichecli".equals(action)) {
			String cont = request.getParameter("contrat");
			String datecreation = request.getParameter("datecreation");

			java.sql.Date ddeb = null;
			if (!datecreation.equals("") && !datecreation.equals(null))
				ddeb = new FormaterDate().changeFormatChaineDate(datecreation);

			String prescripteur = request.getParameter("prescripteur");

			String nomreferentpe = "", prenomreferentpe = "", mailreferentpe = "";

			if (prescripteur.equals("Pole Emploi")) {
				ReferentPoleEmploiDAO refdao = new ReferentPoleEmploiDAO();
				ReferentPoleEmploi referent = null;
				try {
					referent = refdao.afficheReferent();
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				nomreferentpe = referent.getNom();
				prenomreferentpe = referent.getPrenom();
				mailreferentpe = referent.getMail();
			}

			String presence = request.getParameter("presence");
			String acquis = request.getParameter("acquis");
			String nomacquis = request.getParameter("nonacquis");
			String encours = request.getParameter("encours");
			String conclusion = request.getParameter("conclusion");
			String obsprescripteur = request.getParameter("obsprescripteur");

			ContratRMIDAO contdao = new ContratRMIDAO();
			ContratRMI contrat = null;
			try {
				contrat = contdao.findByID(Integer.parseInt(cont));
			} catch (NumberFormatException | DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			FicheLiaisonRMIDAO ficdao = new FicheLiaisonRMIDAO();
			FicheLiaisonRMI ficheliaison = null;
			try {
				ficheliaison = ficdao.recupereFicheCorrespondContrat(contrat);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ficheliaison.setNomreferentpe(nomreferentpe);
			ficheliaison.setObsprescripteur(obsprescripteur);
			ficheliaison.setPrenomreferentpe(prenomreferentpe);
			ficheliaison.setMailreferentpe(mailreferentpe);
			ficheliaison.setDatecreation(ddeb);
			ficheliaison.setPresence(presence);
			ficheliaison.setAcquis(acquis);
			ficheliaison.setNomacquis(nomacquis);
			ficheliaison.setEncours(encours);
			ficheliaison.setConclusion(conclusion);

			try {
				ficdao.update(ficheliaison);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/rmi_rsa/fiches/afficherfichecli.jsp?personne=" + contrat.getIdentite().getId_IDE()
					+ "&contrat=" + contrat.getId_rmicontrat();

		}

		else if ("modificationreferentpe".equals(action)) {
			String nom = request.getParameter("nom");
			String prenom = request.getParameter("prenom");
			String mail = request.getParameter("mail");
			String mailrsa = request.getParameter("mailrsa");
			String maildt = request.getParameter("maildt");
			// String numcontrat = request.getParameter("contrat");
			String numpersonne = request.getParameter("personne");
			String numero = request.getParameter("ref");
			ReferentPoleEmploiDAO ref = new ReferentPoleEmploiDAO();
			ReferentPoleEmploi referent = null;
			try {
				referent = ref.findByID(Integer.parseInt(numero));
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			referent.setNom(nom);
			referent.setPrenom(prenom);
			referent.setMail(mail);
			referent.setMailrsa(mailrsa);
			referent.setMaildt(maildt);
			try {
				referent = ref.update(referent);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/rmi_rsa/fiches/fichermisuite.jsp?personne=" + numpersonne;

		}

		else if ("rmilistecontratpardatefin".equals(action)) {
			String boutons = request.getParameter("a");
			String referent = request.getParameter("referent");

			UtilisateurDAO utidao = new UtilisateurDAO();
			Utilisateur ref = null;

			String debut = null, fin = null;
			java.sql.Date ddeb = null, dfin = null;

			// recuperation des dates de debut et fin du mois suivant
			Calendar cal0 = Calendar.getInstance();
			java.util.Date date = cal0.getTime();
			// System.out.println("mois en cours ="+date);

			// SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
			int jours = cal0.getActualMaximum(Calendar.DAY_OF_MONTH);
			int anneecours = cal0.get(Calendar.YEAR);
			int moiscours = cal0.get(Calendar.MONTH) + 1;
			String debutmois = "1-" + moiscours + "-" + anneecours;
			String finmois = jours + "-" + moiscours + "-" + anneecours;

			cal0.add(Calendar.MONTH, +1);
			java.util.Date date1 = cal0.getTime();
			cal0.add(Calendar.MONTH, +1);
			java.util.Date date2 = cal0.getTime();
			cal0.add(Calendar.MONTH, +1);
			java.util.Date date3 = cal0.getTime();
			cal0.add(Calendar.MONTH, +1);
			java.util.Date date4 = cal0.getTime();

			cal0.add(Calendar.MONTH, +1);
			java.util.Date date5 = cal0.getTime();

			cal0.add(Calendar.MONTH, +1);
			java.util.Date date6 = cal0.getTime();

			cal0.add(Calendar.MONTH, +1);
			java.util.Date date7 = cal0.getTime();

			cal0.add(Calendar.MONTH, +1);
			java.util.Date date8 = cal0.getTime();

			cal0.add(Calendar.MONTH, +1);
			java.util.Date date9 = cal0.getTime();

			cal0.add(Calendar.MONTH, +1);
			java.util.Date date10 = cal0.getTime();

			cal0.add(Calendar.MONTH, +1);
			java.util.Date date11 = cal0.getTime();

			cal0.add(Calendar.MONTH, +1);
			java.util.Date date12 = cal0.getTime();

			cal0.add(Calendar.MONTH, -13);
			java.util.Date datemoins1 = cal0.getTime();

			cal0.add(Calendar.MONTH, -1);
			java.util.Date datemoins2 = cal0.getTime();

			cal0.add(Calendar.MONTH, -1);
			java.util.Date datemoins3 = cal0.getTime();

			cal0.add(Calendar.MONTH, -1);
			java.util.Date datemoins4 = cal0.getTime();

			cal0.add(Calendar.MONTH, -1);
			java.util.Date datemoins5 = cal0.getTime();

			cal0.add(Calendar.MONTH, -1);
			java.util.Date datemoins6 = cal0.getTime();

			cal0.add(Calendar.MONTH, -1);
			java.util.Date datemoins7 = cal0.getTime();

			cal0.add(Calendar.MONTH, -1);
			java.util.Date datemoins8 = cal0.getTime();

			cal0.add(Calendar.MONTH, -1);
			java.util.Date datemoins9 = cal0.getTime();

			cal0.add(Calendar.MONTH, -1);
			java.util.Date datemoins10 = cal0.getTime();

			cal0.add(Calendar.MONTH, -1);
			java.util.Date datemoins11 = cal0.getTime();

			cal0.add(Calendar.MONTH, -1);
			java.util.Date datemoins12 = cal0.getTime();

			FicheRMIDAO fichedao = new FicheRMIDAO();

			List<List<String>> listefiche = null;
			int totalpersonnes = 0;

			if (boutons == null || boutons.equals("null")) {
				debut = request.getParameter("datedebut");
				fin = request.getParameter("datefin");
				if (!debut.equals("null"))
					ddeb = new FormaterDate().changeFormatChaineDate(debut);
				else
					ddeb = new FormaterDate().changeFormatChaineDate(debutmois);

				if (!fin.equals("null"))
					dfin = new FormaterDate().changeFormatChaineDate(fin);
				else
					dfin = new FormaterDate().changeFormatChaineDate(finmois);

				if (referent != null) {
					if (referent.equals("lac"))
						try {
							ref = utidao.findByName("Lacourt monique");
						} catch (DAOException e1) { // TODO Auto-generated catch
													// block
							e1.printStackTrace();
						}
					else if (referent.equals("phil"))
						try {
							ref = utidao.findByName("Jacquet philippe");
						} catch (DAOException e1) { // TODO Auto-generated catch
													// block
							e1.printStackTrace();
						}
					else if (referent.equals("nat"))
						try {
							ref = utidao.findByName("Marty Nathalie");
						} catch (DAOException e1) { // TODO Auto-generated catch
													// block
							e1.printStackTrace();
						}
					else
						ref = null;

				}
			}

			else {
				Calendar cal = Calendar.getInstance();
				if (boutons.equals("-12")) {
					cal.setTime(datemoins12);

				} else if (boutons.equals("-11")) {
					cal.setTime(datemoins11);

				} else if (boutons.equals("-10")) {

					cal.setTime(datemoins10);

				} else if (boutons.equals("-9")) {

					cal.setTime(datemoins9);

				} else if (boutons.equals("-8")) {

					cal.setTime(datemoins8);

				} else if (boutons.equals("-7")) {

					cal.setTime(datemoins7);

				} else if (boutons.equals("-6")) {

					cal.setTime(datemoins6);

				} else if (boutons.equals("-5")) {

					cal.setTime(datemoins5);

				} else if (boutons.equals("-4")) {

					cal.setTime(datemoins4);

				} else if (boutons.equals("-3")) {

					cal.setTime(datemoins3);

				} else if (boutons.equals("-2")) {

					cal.setTime(datemoins2);

				} else if (boutons.equals("-1")) {

					cal.setTime(datemoins1);

				}

				else if (boutons.equals("0")) {

					cal.setTime(date);

				}

				else if (boutons.equals("1")) {

					cal.setTime(date1);

				}

				else if (boutons.equals("2")) {

					cal.setTime(date2);

				} else if (boutons.equals("3")) {

					cal.setTime(date3);

				}

				else if (boutons.equals("4")) {

					cal.setTime(date4);

				}

				else if (boutons.equals("5")) {

					cal.setTime(date5);

				} else if (boutons.equals("6")) {

					cal.setTime(date6);

				}

				else if (boutons.equals("7")) {

					cal.setTime(date7);

				}

				else if (boutons.equals("8")) {

					cal.setTime(date8);

				} else if (boutons.equals("9")) {

					cal.setTime(date9);

				}

				else if (boutons.equals("10")) {

					cal.setTime(date10);

				}

				else if (boutons.equals("11")) {

					cal.setTime(date11);

				} else if (boutons.equals("12")) {

					cal.setTime(date12);

				}
				int njours = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
				cal.set(Calendar.DATE, 1);
				ddeb = new java.sql.Date(cal.getTime().getTime());
				cal.set(Calendar.DATE, njours);
				dfin = new java.sql.Date(cal.getTime().getTime());
				if (referent.equals("lac"))
					try {
						ref = utidao.findByName("Lacourt monique");
					} catch (DAOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				else if (referent.equals("phil"))
					try {
						ref = utidao.findByName("Jacquet philippe");
					} catch (DAOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				else if (referent.equals("nat"))
					try {
						ref = utidao.findByName("Marty Nathalie");
					} catch (DAOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				else
					ref = null;

			}

			try {
				listefiche = fichedao.contratRMIParDates(ddeb, dfin, ref);
				totalpersonnes = fichedao.totalPersonnesParDates(ddeb, dfin, ref);

			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("contrats", listefiche);
			request.setAttribute("totalpersonnes", totalpersonnes);

			jspPage = "/jsp/rmi_rsa/stats/fincontrat.jsp?datedebut=" + ddeb + "&datefin=" + dfin + "&ref=" + ref;
		}

		else if ("rmilistecontratpardatedebut".equals(action)) {
			String boutons = request.getParameter("a");
			String referent = request.getParameter("referent");

			UtilisateurDAO utidao = new UtilisateurDAO();
			Utilisateur ref = null;

			String debut = null, fin = null;
			java.sql.Date ddeb = null, dfin = null;

			// recuperation des dates de debut et fin du mois suivant
			Calendar cal0 = Calendar.getInstance();
			java.util.Date date = cal0.getTime();

			int jours = cal0.getActualMaximum(Calendar.DAY_OF_MONTH);
			int anneecours = cal0.get(Calendar.YEAR);
			int moiscours = cal0.get(Calendar.MONTH) + 1;
			String debutmois = "1-" + moiscours + "-" + anneecours;
			String finmois = jours + "-" + moiscours + "-" + anneecours;

			cal0.add(Calendar.MONTH, +1);
			java.util.Date date1 = cal0.getTime();
			cal0.add(Calendar.MONTH, +1);
			java.util.Date date2 = cal0.getTime();
			cal0.add(Calendar.MONTH, +1);
			java.util.Date date3 = cal0.getTime();
			cal0.add(Calendar.MONTH, +1);
			java.util.Date date4 = cal0.getTime();

			cal0.add(Calendar.MONTH, +1);
			java.util.Date date5 = cal0.getTime();

			cal0.add(Calendar.MONTH, +1);
			java.util.Date date6 = cal0.getTime();

			cal0.add(Calendar.MONTH, +1);
			java.util.Date date7 = cal0.getTime();

			cal0.add(Calendar.MONTH, +1);
			java.util.Date date8 = cal0.getTime();

			cal0.add(Calendar.MONTH, +1);
			java.util.Date date9 = cal0.getTime();

			cal0.add(Calendar.MONTH, +1);
			java.util.Date date10 = cal0.getTime();

			cal0.add(Calendar.MONTH, +1);
			java.util.Date date11 = cal0.getTime();

			cal0.add(Calendar.MONTH, +1);
			java.util.Date date12 = cal0.getTime();

			cal0.add(Calendar.MONTH, -13);
			java.util.Date datemoins1 = cal0.getTime();

			cal0.add(Calendar.MONTH, -1);
			java.util.Date datemoins2 = cal0.getTime();

			cal0.add(Calendar.MONTH, -1);
			java.util.Date datemoins3 = cal0.getTime();

			cal0.add(Calendar.MONTH, -1);
			java.util.Date datemoins4 = cal0.getTime();

			cal0.add(Calendar.MONTH, -1);
			java.util.Date datemoins5 = cal0.getTime();

			cal0.add(Calendar.MONTH, -1);
			java.util.Date datemoins6 = cal0.getTime();

			cal0.add(Calendar.MONTH, -1);
			java.util.Date datemoins7 = cal0.getTime();

			cal0.add(Calendar.MONTH, -1);
			java.util.Date datemoins8 = cal0.getTime();

			cal0.add(Calendar.MONTH, -1);
			java.util.Date datemoins9 = cal0.getTime();

			cal0.add(Calendar.MONTH, -1);
			java.util.Date datemoins10 = cal0.getTime();

			cal0.add(Calendar.MONTH, -1);
			java.util.Date datemoins11 = cal0.getTime();

			cal0.add(Calendar.MONTH, -1);
			java.util.Date datemoins12 = cal0.getTime();

			FicheRMIDAO fichedao = new FicheRMIDAO();

			List<List<String>> listefiche = null;
			int totalpersonnes = 0;

			if (boutons == null || boutons.equals("null")) {
				debut = request.getParameter("datedebut");
				fin = request.getParameter("datefin");

				if (!debut.equals("null"))
					ddeb = new FormaterDate().changeFormatChaineDate(debut);
				else
					ddeb = new FormaterDate().changeFormatChaineDate(debutmois);

				if (!fin.equals("null"))
					dfin = new FormaterDate().changeFormatChaineDate(fin);
				else
					dfin = new FormaterDate().changeFormatChaineDate(finmois);

				if (referent != null) {
					if (referent.equals("lac"))
						try {
							ref = utidao.findByName("Lacourt monique");
						} catch (DAOException e1) { // TODO Auto-generated catch
													// block
							e1.printStackTrace();
						}
					else if (referent.equals("phil"))
						try {
							ref = utidao.findByName("Jacquet philippe");
						} catch (DAOException e1) { // TODO Auto-generated catch
													// block
							e1.printStackTrace();
						}
					else if (referent.equals("nat"))
						try {
							ref = utidao.findByName("Marty Nathalie");
						} catch (DAOException e1) { // TODO Auto-generated catch
													// block
							e1.printStackTrace();
						}
					else
						ref = null;

				}
			}

			else {
				Calendar cal = Calendar.getInstance();
				if (boutons.equals("-12")) {
					cal.setTime(datemoins12);

				} else if (boutons.equals("-11")) {
					cal.setTime(datemoins11);

				} else if (boutons.equals("-10")) {

					cal.setTime(datemoins10);

				} else if (boutons.equals("-9")) {

					cal.setTime(datemoins9);

				} else if (boutons.equals("-8")) {

					cal.setTime(datemoins8);

				} else if (boutons.equals("-7")) {

					cal.setTime(datemoins7);

				} else if (boutons.equals("-6")) {

					cal.setTime(datemoins6);

				} else if (boutons.equals("-5")) {

					cal.setTime(datemoins5);

				} else if (boutons.equals("-4")) {

					cal.setTime(datemoins4);

				} else if (boutons.equals("-3")) {

					cal.setTime(datemoins3);

				} else if (boutons.equals("-2")) {

					cal.setTime(datemoins2);

				} else if (boutons.equals("-1")) {

					cal.setTime(datemoins1);

				}

				else if (boutons.equals("0")) {

					cal.setTime(date);

				}

				else if (boutons.equals("1")) {

					cal.setTime(date1);

				}

				else if (boutons.equals("2")) {

					cal.setTime(date2);

				} else if (boutons.equals("3")) {

					cal.setTime(date3);

				}

				else if (boutons.equals("4")) {

					cal.setTime(date4);

				}

				else if (boutons.equals("5")) {

					cal.setTime(date5);

				} else if (boutons.equals("6")) {

					cal.setTime(date6);

				}

				else if (boutons.equals("7")) {

					cal.setTime(date7);

				}

				else if (boutons.equals("8")) {

					cal.setTime(date8);

				} else if (boutons.equals("9")) {

					cal.setTime(date9);

				}

				else if (boutons.equals("10")) {

					cal.setTime(date10);

				}

				else if (boutons.equals("11")) {

					cal.setTime(date11);

				} else if (boutons.equals("12")) {

					cal.setTime(date12);

				}
				int njours = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
				cal.set(Calendar.DATE, 1);
				ddeb = new java.sql.Date(cal.getTime().getTime());
				cal.set(Calendar.DATE, njours);
				dfin = new java.sql.Date(cal.getTime().getTime());
				if (referent.equals("lac"))
					try {
						ref = utidao.findByName("Lacourt monique");
					} catch (DAOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				else if (referent.equals("phil"))
					try {
						ref = utidao.findByName("Jacquet philippe");
					} catch (DAOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				else if (referent.equals("nat"))
					try {
						ref = utidao.findByName("Marty Nathalie");
					} catch (DAOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				else
					ref = null;

			}

			try {
				listefiche = fichedao.contratRMICommenceParDates(ddeb, dfin, ref);
				totalpersonnes = fichedao.totalPersonnesParDatesDebut(ddeb, dfin, ref);

			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("contrats", listefiche);
			request.setAttribute("totalpersonnes", totalpersonnes);

			jspPage = "/jsp/rmi_rsa/stats/debutcontrat.jsp?datedebut=" + ddeb + "&datefin=" + dfin + "&ref=" + ref;
		}

		else if ("rmilistecontratpardateencours".equals(action)) {

			String debut = request.getParameter("datedebut");
			String fin = request.getParameter("datefin");
			java.sql.Date ddeb = null, dfin = null;

			if (!debut.equals("") && !debut.equals(null))
				ddeb = new FormaterDate().changeFormatChaineDate(debut);

			if (!fin.equals("") && !fin.equals(null))
				dfin = new FormaterDate().changeFormatChaineDate(fin);

			FicheRMIDAO fichedao = new FicheRMIDAO();

			List<List<String>> listefiche = null;
			int totalpersonnes = 0;

			try {
				listefiche = fichedao.contratRMIEnCoursParDates(ddeb, dfin);
				totalpersonnes = fichedao.totalPersonnesParDatesContratsEnCours(ddeb, dfin);

			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("contrats", listefiche);
			request.setAttribute("totalpersonnes", totalpersonnes);

			jspPage = "/jsp/rmi_rsa/stats/contratencours.jsp?datedebut=" + ddeb + "&datefin=" + dfin;

		}

		/****************************************************************/
		/******************* RTH **********************/

		else if ("creationficherth".equals(action)) {
			FormaterDate jour = new FormaterDate();
			java.sql.Date creation = null;
			String a = jour.getSortie();
			creation = new FormaterDate().changeFormatChaineDate(a);

			String personne = request.getParameter("personne");
			String categorie = request.getParameter("categorie");

			String taux = request.getParameter("taux");
			int tx = Integer.parseInt(taux);

			String pension = request.getParameter("pension");
			boolean pens = false;
			if (pension.equals("OUI"))
				pens = true;

			String montant = request.getParameter("montant");

			String referent = request.getParameter("referent");
			String ci = request.getParameter("ci");
			IdentiteDAO idao = new IdentiteDAO();
			Identite identite = null;
			try {
				identite = idao.findByID(Integer.parseInt(personne));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FicheDAORTH ficdao = new FicheDAORTH();
			FicheRTH fiche = new FicheRTH(identite, categorie, tx, pens, montant, ci, referent, creation);

			int num = 0;
			try {
				num = ficdao.create(fiche);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/rth/affichagefiche.jsp?personne=" + personne;
		}

		else if ("modificationficherth".equals(action)) {

			String personne = request.getParameter("personne");
			String fiche = request.getParameter("fiche");
			String categorie = request.getParameter("categorie");

			String taux = request.getParameter("taux");
			// int pos=taux.indexOf(".");
			// taux=taux.substring(0, pos);
			int tx = Integer.parseInt(taux);

			String pension = request.getParameter("pension");

			boolean pens = false;
			if (pension.equals("OUI"))
				pens = true;

			String montant = request.getParameter("montant");

			String referent = request.getParameter("referent");
			String ci = request.getParameter("ci");

			FicheDAORTH ficdao = new FicheDAORTH();
			FicheRTH ficherth = null;
			try {
				ficherth = ficdao.findByID(Integer.parseInt(fiche));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ficherth.setTaux_rth(tx);
			ficherth.setCategorie_rth(categorie);
			ficherth.setPension_rth(pens);
			ficherth.setMontant(montant);
			ficherth.setCi_rth(ci);
			ficherth.setReferent_rth(referent);

			try {
				ficdao.update(ficherth);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/rth/affichagefiche.jsp?personne=" + personne;
		}

		/***************** SAP ************************/

		else if ("sapcontratnouveau".equals(action)) {
			String crea = request.getParameter("dateinscription");
			String personne = request.getParameter("numero");
			String datedebut = request.getParameter("datedebut");
			String emploi = request.getParameter("emploi");
			String heure = request.getParameter("heures");
			int heures = Integer.parseInt(heure);
			String urssaf = request.getParameter("urssaf");
			String sal = request.getParameter("salaire");
			float salaire = Float.parseFloat(sal);
			float panier = 0.00f;
			String pan = request.getParameter("panier");
			if (!pan.equals(""))
				panier = Float.parseFloat(pan);
			String deplace = request.getParameter("dep");
			float dep = 0.00f;
			if (!deplace.equals(""))
				dep = Float.parseFloat(deplace);
			String divers = request.getParameter("divers");

			IdentiteDAO idao = new IdentiteDAO();
			Identite identite = null;
			try {
				identite = idao.findByID(Integer.parseInt(personne));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			java.sql.Date deb = null, creation = null;

			FormaterDate fdate = new FormaterDate();

			if (!crea.equals("") && !crea.equals(null))
				creation = fdate.changeFormatChaineDate(crea);
			if (!datedebut.equals("") && !datedebut.equals(null))
				deb = fdate.changeFormatChaineDate(datedebut);

			OrganismeDAO ordao = new OrganismeDAO();
			Organisme capemploi = null;
			try {
				capemploi = ordao.findByID(1);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ContratCDIDAO contratdao = new ContratCDIDAO();
			ContratCDI contrat = new ContratCDI(capemploi, identite, emploi, deb, heures, salaire, panier, dep, divers,
					creation, urssaf, capemploi.getPresident(), capemploi.getAgrementsap(), null);

			int numcontrat = 0;
			try {
				numcontrat = contratdao.create(contrat);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			ContratCDI retour = null;

			/*
			 * si le contrat a deja ete saisi dans la journee on affiche
			 * l'ancien
			 */
			if (numcontrat == 0) {
				try {
					retour = contratdao.contratEnregistreDansLaJournee(identite);
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				jspPage = "/jsp/sap/contrat/affichagecontrat.jsp?personne=" + personne + "&contrat="
						+ retour.getId_contratcdi();
			}

			else

				jspPage = "/jsp/sap/contrat/affichagecontrat.jsp?personne=" + personne + "&contrat=" + numcontrat;

		}

		else if ("sapmodifiercontrat".equals(action)) {
			FormaterDate fdate = new FormaterDate();
			String idcontrat = request.getParameter("contrat");

			ContratCDIDAO contratdao = new ContratCDIDAO();
			ContratCDI contrat = null;
			try {
				contrat = contratdao.findByID(Integer.parseInt(idcontrat));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String datedebut = request.getParameter("datedebut");
			String dateredaction = request.getParameter("dateredaction");
			String dateterme = request.getParameter("terme");
			java.sql.Date deb = null, creation = null, fin = null;

			if (!datedebut.equals("") && !datedebut.equals(null))
				deb = fdate.changeFormatChaineDate(datedebut);

			if (!dateredaction.equals("") && !dateredaction.equals(null))
				creation = fdate.changeFormatChaineDate(dateredaction);

			if (!dateterme.equals("") && !dateterme.equals(null))
				fin = fdate.changeFormatChaineDate(dateterme);

			contrat.setEmbauche(deb);
			contrat.setRedaction(creation);
			contrat.setTermecontrat(fin);

			String emploi = request.getParameter("emploi");
			contrat.setTache(emploi);
			String heure = request.getParameter("heures");
			int heures = Integer.parseInt(heure);
			contrat.setHeuresminimois(heures);
			String urssaf = request.getParameter("urssaf");
			contrat.setUrssaf(urssaf);
			String sal = request.getParameter("salaire");
			float salaire = Float.parseFloat(sal);
			contrat.setSalairehoraire(salaire);
			float panier = 0.00f;
			String pan = request.getParameter("panier");
			if (!pan.equals("")) {
				panier = Float.parseFloat(pan);
				contrat.setPanier(panier);
			} else {
				panier = 0.00f;
				contrat.setPanier(panier);

			}
			String deplace = request.getParameter("dep");
			float dep = 0.00f;
			if (!deplace.equals("")) {
				dep = Float.parseFloat(deplace);
				contrat.setDeplacement(dep);
			} else {
				dep = 0.00f;
				contrat.setDeplacement(dep);

			}
			String divers = request.getParameter("comm");

			contrat.setCommentaire(divers);

			try {
				contrat = contratdao.update(contrat);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/sap/contrat/affichagecontrat.jsp?personne=" + contrat.getIdentite().getId_IDE()
					+ "&contrat=" + idcontrat;

		}

		else if ("saplistevisitespardatefin".equals(action)) {
			String a = request.getParameter("a");
			String debut = null, fin = null;
			java.sql.Date ddeb = null, dfin = null;

			// recuperation des dates de debut et fin du mois suivant

			Calendar cal0 = Calendar.getInstance();
			java.util.Date date = cal0.getTime();
			// System.out.println("mois " + date);

			cal0.add(Calendar.MONTH, +1);
			java.util.Date date1 = cal0.getTime();
			// System.out.println("mois +1 " + date1);

			cal0.add(Calendar.MONTH, +1);
			java.util.Date date2 = cal0.getTime();
			// System.out.println("mois +2 " + date2);

			VisiteMedicaleDAO codao = new VisiteMedicaleDAO();

			List<VisiteMedicale> liste = null;

			if (a == null) {
				debut = request.getParameter("datedebut");
				fin = request.getParameter("datefin");
				ddeb = new FormaterDate().changeFormatChaineDate(debut);
				dfin = new FormaterDate().changeFormatChaineDate(fin);
				try {
					liste = codao.listeVisitesParDates(ddeb, dfin);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("listevisites", liste);
			}

			else {

				if (a.equals("0")) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					int njours = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

					cal.set(Calendar.DATE, 1);
					ddeb = new java.sql.Date(cal.getTime().getTime());
					cal.set(Calendar.DATE, njours);
					dfin = new java.sql.Date(cal.getTime().getTime());

				}

				else if (a.equals("1")) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(date1);
					int njours = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

					cal.set(Calendar.DATE, 1);
					ddeb = new java.sql.Date(cal.getTime().getTime());
					cal.set(Calendar.DATE, njours);
					dfin = new java.sql.Date(cal.getTime().getTime());

				}

				else if (a.equals("2")) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(date2);
					int njours = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

					cal.set(Calendar.DATE, 1);
					ddeb = new java.sql.Date(cal.getTime().getTime());
					cal.set(Calendar.DATE, njours);
					dfin = new java.sql.Date(cal.getTime().getTime());
				}

				try {
					liste = codao.listeVisitesParDates(ddeb, dfin);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("listevisites", liste);

			}

			jspPage = "/jsp/sap/visitemedicale/listingpardates.jsp?datedebut=" + ddeb + "&datefin=" + dfin;
		}

		else if ("sapmodifiervisite".equals(action)) {
			String idvisite = request.getParameter("idvisite");

			String dateconvoc = request.getParameter("dateconvoc");
			String datevisite = request.getParameter("datevisite");

			java.sql.Date deb = null, vis = null, eche = null;
			if (!dateconvoc.equals("") && !dateconvoc.equals(null))
				deb = new FormaterDate().changeFormatChaineDate(dateconvoc);

			if (!datevisite.equals("") && !datevisite.equals(null))
				vis = new FormaterDate().changeFormatChaineDate(datevisite);

			java.util.Date tampon = null;

			// on rajoute 2 ans a la date de visite
			Calendar cal = Calendar.getInstance();
			cal.setTime(vis);
			cal.add(Calendar.YEAR, 2);
			tampon = cal.getTime();
			// conversion de java.util.date en java.sql.date
			eche = new java.sql.Date(tampon.getTime());

			VisiteMedicaleDAO agdao = new VisiteMedicaleDAO();
			VisiteMedicale visite = null;
			try {
				visite = agdao.findByID(Integer.parseInt(idvisite));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			IdentiteDAO iddao = new IdentiteDAO();
			Identite identite = null;
			try {
				identite = iddao.findByID(visite.getIdentite().getId_IDE());
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			VisiteMedicale nouveau = new VisiteMedicale(Integer.parseInt(idvisite), identite, deb, vis, eche);
			try {
				visite = agdao.update(nouveau);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/sap/fichesap/fiche.jsp?personne=" + visite.getIdentite().getId_IDE();
		}

		else if ("sapnouvellevisite".equals(action)) {
			String personne = request.getParameter("personne");
			IdentiteDAO idao = new IdentiteDAO();
			Identite identite = null;
			try {
				identite = idao.findByID(Integer.parseInt(personne));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			String dateconvoc = request.getParameter("dateconvoc");
			String datevisite = request.getParameter("datevisite");

			CreationDAO fiche = new CreationDAO();
			boolean oui = false;
			try {
				oui = fiche.verifierPermanentFiche(identite);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			request.setAttribute("permanent", oui);

			java.sql.Date deb = null, vis = null, eche = null;
			java.util.Date tampon = null;

			if (!dateconvoc.equals("") && !dateconvoc.equals(null))
				deb = new FormaterDate().changeFormatChaineDate(dateconvoc);
			if (!datevisite.equals("") && !datevisite.equals(null)) {
				vis = new FormaterDate().changeFormatChaineDate(datevisite);
				// on rajoute 2 ans a la date de visite

				Calendar cal = Calendar.getInstance();
				cal.setTime(vis);
				cal.add(Calendar.YEAR, 2);
				tampon = cal.getTime();
				// conversion de java.util.date en java.sql.date

				eche = new java.sql.Date(tampon.getTime());
			}

			VisiteMedicale visitemed = null;
			try {
				visitemed = new VisiteMedicale(new IdentiteDAO().findByID(Integer.parseInt(personne)), deb, vis, eche);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			VisiteMedicaleDAO agdao = new VisiteMedicaleDAO();
			try {
				agdao.create(visitemed);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/sap/fichesap/fiche.jsp?personne=" + personne;

		}

		else if ("sapavenantnouveau".equals(action)) {
			String crea = request.getParameter("dateinscription");
			String personne = request.getParameter("numero");
			String cont = request.getParameter("contrat");
			String dateeffet = request.getParameter("datedebut");
			String emploi = request.getParameter("emploi");
			String heure = request.getParameter("heures");
			int heures = Integer.parseInt(heure);
			String urssaf = request.getParameter("urssaf");
			String sal = request.getParameter("salaire");
			float salaire = Float.parseFloat(sal);
			float panier = 0.00f;
			String pan = request.getParameter("panier");
			if (!pan.equals(""))
				panier = Float.parseFloat(pan);
			String deplace = request.getParameter("dep");
			float dep = 0.00f;
			if (!deplace.equals(""))
				dep = Float.parseFloat(deplace);

			String comm = request.getParameter("comm");

			java.sql.Date eff = null, creation = null;

			FormaterDate fdate = new FormaterDate();

			if (!crea.equals("") && !crea.equals(null))
				creation = fdate.changeFormatChaineDate(crea);
			if (!dateeffet.equals("") && !dateeffet.equals(null))
				eff = fdate.changeFormatChaineDate(dateeffet);

			ContratCDIDAO contratdao = new ContratCDIDAO();
			ContratCDI contrat = null;
			try {
				contrat = contratdao.findByID(Integer.parseInt(cont));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			AvenantCDIDAO avedao = new AvenantCDIDAO();
			AvenantCDI avenant = new AvenantCDI(contrat, emploi, heures, salaire, panier, dep, comm, creation, urssaf,
					eff);

			// on récupére le numero de l'avenant correspondant a ce contrat
			int idrangavenant = 0;
			try {
				idrangavenant = avedao.recupereNumeroAvenantEContrat(contrat);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			int numavenant = 0;

			try {
				numavenant = avedao.create(avenant);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			avenant.setId_avenant(numavenant);
			avenant.setRangavenent(idrangavenant);

			try {
				avenant = avedao.update(avenant);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/sap/avenantcontrat/affichageavenant.jsp?personne=" + personne + "&contrat=" + numavenant;

		}

		else if ("sapmodifieravenant".equals(action)) {
			FormaterDate fdate = new FormaterDate();
			String idcontrat = request.getParameter("contrat");
			String personne = request.getParameter("personne");

			AvenantCDIDAO contratdao = new AvenantCDIDAO();
			AvenantCDI contrat = null;
			try {
				contrat = contratdao.findByID(Integer.parseInt(idcontrat));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			String dateredaction = request.getParameter("dateredaction");
			String dateeffet = request.getParameter("datedebut");
			java.sql.Date eff = null, creation = null;
			if (!dateredaction.equals("") && !dateredaction.equals(null)) {
				creation = fdate.changeFormatChaineDate(dateredaction);
				contrat.setRedaction(creation);
			}

			if (!dateeffet.equals("") && !dateeffet.equals(null)) {
				eff = fdate.changeFormatChaineDate(dateeffet);
				contrat.setDateeffet(eff);
			}
			String emploi = request.getParameter("emploi");
			contrat.setTache(emploi);
			String heure = request.getParameter("heures");
			int heures = Integer.parseInt(heure);
			contrat.setHeuresminimois(heures);
			String urssaf = request.getParameter("urssaf");
			contrat.setUrssaf(urssaf);
			String sal = request.getParameter("salaire");
			float salaire = Float.parseFloat(sal);
			contrat.setSalairehoraire(salaire);
			float panier = 0.00f;
			String pan = request.getParameter("panier");
			if (!pan.equals("")) {
				panier = Float.parseFloat(pan);
				contrat.setPanier(panier);
			} else {
				panier = 0.00f;
				contrat.setPanier(panier);
			}
			String deplace = request.getParameter("dep");
			float dep = 0.00f;
			if (!deplace.equals("")) {
				dep = Float.parseFloat(deplace);
				contrat.setDeplacement(dep);
			} else {
				dep = 0.00f;
				contrat.setDeplacement(dep);
			}

			String comm = request.getParameter("comm");

			contrat.setCommentaire(comm);

			try {
				contratdao.update(contrat);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/sap/avenantcontrat/affichageavenant.jsp?personne=" + personne + "&contrat=" + idcontrat;

		}

		else if ("saprestationnouveau".equals(action)) {
			FormaterDate fdate = new FormaterDate();
			String personne = request.getParameter("emp");
			EmployeurDAO empdao = new EmployeurDAO();
			Employeur employeur = null;
			try {
				employeur = empdao.findByID(Integer.parseInt(personne));
			} catch (NumberFormatException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (DAOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			String salarie = request.getParameter("personne");
			int n = salarie.lastIndexOf("(");
			salarie = salarie.substring(n + 1, salarie.length() - 1);
			IdentiteDAO idao = new IdentiteDAO();
			Identite identite = null;
			try {
				identite = idao.findByID(Integer.parseInt(salarie));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ContratCDIDAO contdao = new ContratCDIDAO();
			ContratCDI contrat = null;
			try {
				contrat = contdao.dernierContratEnCours(identite);
			} catch (DAOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			String datedebut = request.getParameter("datedebut");
			String datefin = request.getParameter("datefin");
			String datecrea = request.getParameter("dateinscription");
			java.sql.Date deb = null, fin = null, creation = null;
			// String a = fdate.getSortie();
			// creation = fdate.changeFormatChaineDate(a);
			if (!datecrea.equals("") && !datecrea.equals(null))
				creation = fdate.changeFormatChaineDate(datecrea);
			if (!datedebut.equals("") && !datedebut.equals(null))
				deb = fdate.changeFormatChaineDate(datedebut);
			if (!datefin.equals("") && !datefin.equals(null))
				fin = fdate.changeFormatChaineDate(datefin);

			String tachepropose = request.getParameter("tachepropose");
			TachesSAPDAO tachedao = new TachesSAPDAO();
			TachesSAP tache = null;
			try {
				tache = tachedao.findByName(tachepropose);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String dureehebdo = request.getParameter("dureehebdo");
			int duree = 0;
			if (!dureehebdo.equals(""))
				duree = Integer.parseInt(dureehebdo);

			String fsalaire = request.getParameter("salhor");
			float salaire = 0.00f;
			if (!fsalaire.equals(""))
				salaire = Float.parseFloat(fsalaire);
			String facsalhor = request.getParameter("facsalhor");
			float facsalaire = 0.00f;
			if (!facsalhor.equals(""))
				facsalaire = Float.parseFloat(facsalhor);
			String fpanier = request.getParameter("panier");
			float panier = 0.00f;
			if (!fpanier.equals(""))
				panier = Float.parseFloat(fpanier);
			String fdeplace = request.getParameter("deplacement");
			float deplacement = 0.00f;
			if (!fdeplace.equals(""))
				deplacement = Float.parseFloat(fdeplace);

			String heurescontrat = request.getParameter("heuresmini");
			int heurecont = 0;
			if (!heurescontrat.equals(""))
				heurecont = Integer.parseInt(heurescontrat);
			System.out.println("heures contrat =" + heurecont);

			String comm = request.getParameter("comm");

			OrganismeDAO ordao = new OrganismeDAO();
			Organisme capemploi = null;
			try {
				capemploi = ordao.findByID(1);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			PrestationCDIDAO presdao = new PrestationCDIDAO();
			PrestationCDI prestation = new PrestationCDI(employeur, identite, tache, duree, salaire, panier,
					deplacement, facsalaire, comm, creation, deb, fin, capemploi.getAgrementsap(),
					capemploi.getDirecteur(), heurecont);
			int idprestation = 0;

			try {
				idprestation = presdao.create(prestation);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			PrestationCDI derniereCdi = null;

			try {
				derniereCdi = presdao.recupereDernierePrestaion(identite);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			/*
			 * si le contrat a deja ete saisi dans la journee on affiche
			 * l'ancien
			 */

			if (idprestation == 0)
				jspPage = "/jsp/employeurs/prestations/affichageprestation.jsp?prestation="
						+ derniereCdi.getId_prestationcontrat();

			else
				jspPage = "/jsp/employeurs/prestations/affichageprestation.jsp?prestation=" + idprestation;

		}

		else if ("saprestationmodifier".equals(action)) {
			FormaterDate fdate = new FormaterDate();

			PrestationCDIDAO prestdao = new PrestationCDIDAO();
			PrestationCDI prestation = null;
			String presta = request.getParameter("prestation");
			try {
				prestation = prestdao.findByID(Integer.parseInt(presta));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			String salarie = request.getParameter("personne");
			int posparenthese = salarie.lastIndexOf("(");
			String numsalarie = salarie.substring(posparenthese + 1, (salarie.length()) - 1);
			int numerosalarie = 0;
			try {
				numerosalarie = Integer.parseInt(numsalarie);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}

			try {
				prestation.setIdentite(new IdentiteDAO().findByID(numerosalarie));
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			String dateenreg = request.getParameter("dateinscription");
			String datedebut = request.getParameter("datedebut");
			String datefin = request.getParameter("datefin");
			String daterelance = request.getParameter("relance");
			java.sql.Date deb = null, fin = null, relance = null, enreg = null;

			if (!dateenreg.equals("") && !dateenreg.equals(null)) {
				enreg = fdate.changeFormatChaineDate(dateenreg);
				prestation.setRedaction_pr(enreg);
			}
			if (!datedebut.equals("") && !datedebut.equals(null)) {
				deb = fdate.changeFormatChaineDate(datedebut);
				prestation.setDatedebut_pr(deb);
			}
			if (!datefin.equals("") && !datefin.equals(null)) {
				fin = fdate.changeFormatChaineDate(datefin);
				prestation.setDatefin_pr(fin);
			}
			if (!daterelance.equals("") && !daterelance.equals(null)) {
				relance = fdate.changeFormatChaineDate(daterelance);
				prestation.setDaterenouvel(relance);
			}
			String tachepropose = request.getParameter("tachepropose");
			TachesSAPDAO tachedao = new TachesSAPDAO();
			TachesSAP tache = null;
			try {
				tache = tachedao.findByName(tachepropose);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			prestation.setTache(tache);

			String dureehebdo = request.getParameter("dureehebdo");
			int duree = 0;
			if (!dureehebdo.equals("")) {
				duree = Integer.parseInt(dureehebdo);
				prestation.setHeuresminimois_pr(duree);
			}
			String fsalhor = request.getParameter("salhor");
			float salhor = 0.00f;
			if (!fsalhor.equals("")) {
				salhor = Float.parseFloat(fsalhor);
				prestation.setSalairehor_pr(salhor);
			}
			String facsalhor = request.getParameter("facsalhor");
			float facsalaire = 0.00f;
			if (!facsalhor.equals("")) {
				facsalaire = Float.parseFloat(facsalhor);
				prestation.setFacsalairehor_pr(facsalaire);
			}
			String fpanier = request.getParameter("panier");
			float panier = 0.00f;
			if (!fpanier.equals("")) {
				panier = Float.parseFloat(fpanier);
				prestation.setPanier_pr(panier);
			}
			String fdeplace = request.getParameter("deplacement");
			float deplace = 0.00f;
			if (!fdeplace.equals("")) {
				deplace = Float.parseFloat(fdeplace);
				prestation.setDeplacement_pr(deplace);
			}

			String comm = request.getParameter("comm");
			prestation.setCommentaire_pr(comm);

			try {
				prestdao.update(prestation);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/employeurs/prestations/affichageprestation.jsp?personne=" + salarie + "&prestation="
					+ prestation.getId_prestationcontrat();

		}

		else if ("sapprestationspardates".equals(action)) {
			String deb = request.getParameter("datedebutrech");
			String fin = request.getParameter("datefinrech");

			java.sql.Date ddeb = null, dfin = null;
			if (!deb.equals("") && !deb.equals(null))
				ddeb = new FormaterDate().changeFormatChaineDate(deb);

			if (!fin.equals("") && !fin.equals(null))
				dfin = new FormaterDate().changeFormatChaineDate(fin);
			List<Integer> liste = null;
			try {
				liste = new PrestationCDIDAO().listePrestationsTermineDansFourchette(ddeb, dfin);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<Integer> listefinale = null;
			try {
				listefinale = new AvenantPrestationCDIDAO().listeAvenantsPrestationsTermineDansFourchette(ddeb, dfin);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.setAttribute("liste", liste);
			request.setAttribute("listeav", listefinale);
			jspPage = "/jsp/sap/stats/rechercheprestations.jsp?debut=" + deb + "&fin=" + fin;

		}

		else if ("sapprestationspardatessansretour".equals(action)) {
			String deb = request.getParameter("datedebutrech");
			String fin = request.getParameter("datefinrech");

			java.sql.Date ddeb = null, dfin = null;
			if (!deb.equals("") && !deb.equals(null))
				ddeb = new FormaterDate().changeFormatChaineDate(deb);

			if (!fin.equals("") && !fin.equals(null))
				dfin = new FormaterDate().changeFormatChaineDate(fin);
			List<Integer> liste = null;
			try {
				liste = new PrestationCDIDAO().listePrestationsTermineDansFourchetteSansRetour(ddeb, dfin);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<Integer> listefinale = null;
			try {
				listefinale = new AvenantPrestationCDIDAO().listePrestationCDIParPeriodeSansRetour(ddeb, dfin);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.setAttribute("listepr", liste);
			request.setAttribute("listeav", listefinale);
			jspPage = "/jsp/sap/stats/rechercheprestationssansretour.jsp?debut=" + deb + "&fin=" + fin;

		}

		else if ("sapavenantprestation".equals(action)) {
			FormaterDate fdate = new FormaterDate();
			String crea = request.getParameter("dateinscription");
			String idprestation = request.getParameter("prestation");
			PrestationCDIDAO prestationdao = new PrestationCDIDAO();
			PrestationCDI prestation = null;
			try {
				prestation = prestationdao.findByID(Integer.parseInt(idprestation));
			} catch (NumberFormatException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			} catch (DAOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}

			String datedebut = request.getParameter("datedebut");
			String datefin = request.getParameter("datefin");
			java.sql.Date deb = null, fin = null, creation = null;

			if (!crea.equals("") && !crea.equals(null))
				creation = fdate.changeFormatChaineDate(crea);
			if (!datedebut.equals("") && !datedebut.equals(null))
				deb = fdate.changeFormatChaineDate(datedebut);
			if (!datefin.equals("") && !datefin.equals(null))
				fin = fdate.changeFormatChaineDate(datefin);

			String tachepropose = request.getParameter("tachepropose");
			TachesSAPDAO tachedao = new TachesSAPDAO();
			TachesSAP tache = null;
			try {
				tache = tachedao.findByName(tachepropose);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String dureehebdo = request.getParameter("dureehebdo");
			int duree = 0;
			if (!dureehebdo.equals(""))
				duree = Integer.parseInt(dureehebdo);

			String fsalhor = request.getParameter("salhor");
			float salhor = 0.00f;
			if (!fsalhor.equals(""))
				salhor = Float.parseFloat(fsalhor);

			String fpanier = request.getParameter("panier");
			float panier = 0.00f;
			if (!fpanier.equals(""))
				panier = Float.parseFloat(fpanier);

			String fdepl = request.getParameter("deplacement");
			float deplacement = 0.00f;
			if (!fdepl.equals(""))
				deplacement = Float.parseFloat(fdepl);

			String facsalhor = request.getParameter("facsalhor");
			float facsalaire = 0.00f;
			if (!facsalhor.equals(""))
				facsalaire = Float.parseFloat(facsalhor);
			String comm = request.getParameter("comm");

			String heurescontrat = request.getParameter("heuresmini");
			int heurecont = 0;
			if (!heurescontrat.equals(""))
				heurecont = Integer.parseInt(heurescontrat);
			// System.out.println("heures contrat ="+heurecont);

			/*
			 * OrganismeDAO ordao = new OrganismeDAO(); Organisme capemploi =
			 * null; try { capemploi = ordao.findByID(1); } catch (DAOException
			 * e) { // TODO Auto-generated catch block e.printStackTrace(); }
			 */

			AvenantPrestationCDIDAO presdao = new AvenantPrestationCDIDAO();
			AvenantPrestationCDI avprestation = new AvenantPrestationCDI(prestation, tache, duree, salhor, panier,
					deplacement, facsalaire, comm, creation, deb, fin, heurecont);
			int num = 0;

			try {
				num = presdao.create(avprestation);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			int idrang = 0;
			try {
				idrang = presdao.recupereNumeroAvenantPrestation(prestation);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			avprestation.setId_prestationavenant(num);
			avprestation.setRangavenant(idrang);

			try {
				avprestation = presdao.update(avprestation);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/employeurs/avenantprestation/affichageprestation.jsp?prestation=" + num;

		}

		else if ("sapavenantprestationmodifier".equals(action)) {
			FormaterDate fdate = new FormaterDate();

			AvenantPrestationCDIDAO prestdao = new AvenantPrestationCDIDAO();
			AvenantPrestationCDI prestation = null;
			String presta = request.getParameter("prestation");
			try {
				prestation = prestdao.findByID(Integer.parseInt(presta));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			String crea = request.getParameter("dateinscription");
			String datedebut = request.getParameter("datedebut");
			String datefin = request.getParameter("datefin");
			String daterelance = request.getParameter("relance");
			java.sql.Date deb = null, fin = null, relance = null, creation = null;

			if (!crea.equals("") && !crea.equals(null)) {
				creation = fdate.changeFormatChaineDate(crea);
				prestation.setRedaction_pr(creation);
			}
			if (!datedebut.equals("") && !datedebut.equals(null)) {
				deb = fdate.changeFormatChaineDate(datedebut);
				prestation.setDatedebut_pr(deb);
			}
			if (!datefin.equals("") && !datefin.equals(null)) {
				fin = fdate.changeFormatChaineDate(datefin);
				prestation.setDatefin_pr(fin);
			}

			if (!daterelance.equals("") && !daterelance.equals(null)) {
				relance = fdate.changeFormatChaineDate(daterelance);
				prestation.setDaterenouvel(relance);
			}

			String tachepropose = request.getParameter("tachepropose");
			TachesSAPDAO tachedao = new TachesSAPDAO();
			TachesSAP tache = null;
			try {
				tache = tachedao.findByName(tachepropose);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			prestation.setTache(tache);

			String dureehebdo = request.getParameter("dureehebdo");
			int duree = 0;
			if (!dureehebdo.equals("")) {
				duree = Integer.parseInt(dureehebdo);
				prestation.setHeuresminimois_pres(duree);
			}

			String fsalhor = request.getParameter("salhor");
			float salhor = 0.00f;
			if (!fsalhor.equals("")) {
				salhor = Float.parseFloat(fsalhor);
				prestation.setSalairehor_av(salhor);
			}

			String fpanier = request.getParameter("panier");
			float panier = 0.00f;
			if (!fpanier.equals("")) {
				panier = Float.parseFloat(fpanier);
				prestation.setPanier_av(panier);
			}

			String fdepl = request.getParameter("deplacement");
			float deplacement = 0.00f;
			if (!fdepl.equals("")) {
				deplacement = Float.parseFloat(fdepl);
				prestation.setDeplacement_av(deplacement);
			}

			String facsalhor = request.getParameter("facsalhor");
			float facsalaire = 0.00f;
			if (!facsalhor.equals("")) {
				facsalaire = Float.parseFloat(facsalhor);
				prestation.setFacsalairehor_pr(facsalaire);
			}

			String comm = request.getParameter("comm");
			prestation.setCommentaire(comm);

			/*
			 * ContratCDIDAO contcdao = new ContratCDIDAO(); ContratCDI
			 * contratcdi = null; try { contratcdi =
			 * contcdao.dernierContratEnCours(identite); } catch (DAOException
			 * e1) { // TODO Auto-generated catch block e1.printStackTrace(); }
			 * // on affecte le salaire horaire du nouvel employé a la
			 * prestation String salhor=request.getParameter("salhor"); float
			 * salhoraire = Float.parseFloat(salhor);
			 * prestation.setSalairehor_av(salhoraire);
			 */

			try {
				prestdao.update(prestation);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/employeurs/avenantprestation/affichageprestation.jsp?prestation="
					+ prestation.getId_prestationavenant();

		}

		else if ("supextranet".equals(action)) {
			String id = request.getParameter("idextranet");
			String pe = request.getParameter("personne");
			ExtranetDAO exdao = new ExtranetDAO();
			Extranet extranet = null;
			try {
				exdao.delete(Integer.parseInt(id));

			} catch (NumberFormatException | DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jspPage = "/jsp/ai/fiches/fichesuite.jsp?personne=" + pe;

		}

		else if ("relevehoraireSAP".equals(action)) {
			String mois = request.getParameter("mois");
			String an = request.getParameter("an");
			FormaterDate fd = new FormaterDate();
			int idmois = fd.moisCorrespondant(mois);
			// System.out.println("mois = " + idmois);

			int annee = Integer.parseInt(an);
			// System.out.println("annee = " + annee);
			jspPage = "/jsp/sap/pdf/listing.jsp?mois=" + idmois + "&an=" + annee;

		}

		/****************************************************************************************/
		/* ADMINISTRATION */
		/****************************************************************************************/

		else if ("administration".equals(action)) {
			String log = request.getParameter("login");
			String passe = request.getParameter("passe");
			passe = new Encode().encode(passe);
			// String nouveau1 = request.getParameter("nouveau1");
			// String nouveau2 = request.getParameter("nouveau2");
			// String modifpasse = request.getParameter("modifpasse");
			UtilisateurDAO userdao = new UtilisateurDAO();
			Utilisateur login = null;
			try {
				login = userdao.rechercheParLogin(log);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (login != null) {
				if (login.getPasse().equals(passe) && login.getPrivilege().equals("admingen"))
					jspPage = "/jsp/administration/menuadmin.jsp";
				else
					jspPage = "/jsp/administration/passe.jsp?login=" + login.getLogin() + "&passe=erreur";
			} else
				jspPage = "/jsp/administration/passe.jsp?login=erreur";

		}

		else if ("adminstatutemployeur".equals(action)) {
			String stat = request.getParameter("statut");
			stat = stat.toUpperCase();
			StatutDAO statdao = new StatutDAO();
			Statut statut = new Statut(stat, true);
			int num = 0;
			try {
				num = statdao.create(statut);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jspPage = "/jsp/administration/employeur/nouveaustatut.jsp?statut=" + num;

		}

		else if ("adminactiviteemployeur".equals(action)) {
			String act = request.getParameter("activite");
			act = StringUtils.capitalize(act);
			ActiviteDAO acdao = new ActiviteDAO();
			Activite statut = new Activite(act, true);
			int num = 0;
			try {
				num = acdao.create(statut);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jspPage = "/jsp/administration/employeur/nouveauactivite.jsp?activite=" + num;

		}

		else if ("adminstructureemployeur".equals(action)) {
			String struct = request.getParameter("structure");
			struct = StringUtils.capitalize(struct);
			StructureDAO acdao = new StructureDAO();
			Structure statut = new Structure(struct, true);
			int num = 0;
			try {
				num = acdao.create(statut);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jspPage = "/jsp/administration/employeur/nouveaustructure.jsp?structure=" + num;

		}

		else if ("adminsmic".equals(action)) {
			FormaterDate fdate = new FormaterDate();
			SmicDAO smicdao = new SmicDAO();
			Smic ancien = null;
			try {
				ancien = smicdao.findByID(1);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String smic = request.getParameter("smic");
			String datevigueur = request.getParameter("vigueur");
			java.sql.Date deb = null;

			if (!datevigueur.equals("") && !datevigueur.equals(null)) {
				deb = fdate.changeFormatChaineDate(datevigueur);
				ancien.setDate_smic(deb);
			}
			// ancien.setId_smic(ancien.getId_smic());
			ancien.setAnciensmic(ancien.getTsxmic());
			if (!smic.equals(""))
				ancien.setTsxmic(Float.parseFloat(smic));

			try {
				ancien = smicdao.update(ancien);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/administration/smic/affichesmic.jsp";

		}

		else if ("administrationmodifpasse".equals(action)) {
			String nom = request.getParameter("nom");
			String passe = request.getParameter("passe");
			String nouveau1 = request.getParameter("nouveau1");
			String nouveau2 = request.getParameter("nouveau2");
			UtilisateurDAO userdao = new UtilisateurDAO();
			Utilisateur login = null;
			try {
				login = userdao.rechercheParLogin(nom);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (nouveau1.equals(nouveau2)) {
				login.setPasse(nouveau1);
				try {
					userdao.update(login);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				jspPage = "/jsp/administration/menuadmin.jsp?login=" + nom + "&passe=" + nouveau1 + "&modifpasse=ok";
			} else
				jspPage = "/jsp/administration/menuadmin.jsp?login=" + nom + "&passe=" + passe + "&modifpasse=non";

		}

		else if ("admincorfiajoututilisateur".equals(action)) {
			String nom = request.getParameter("nom");
			String mail = request.getParameter("mail");
			AnimatriceDAO animdao = new AnimatriceDAO();
			Animatrice animatrice = new Animatrice(nom, mail, true);
			int id = 0;
			try {
				id = animdao.create(animatrice);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jspPage = "/jsp/administration/corfi/afficheutilisateur.jsp?id=" + id;
		}

		else if ("admincorfimodifutilisateur".equals(action)) {
			String mail = request.getParameter("mail");
			String animatriceformation = request.getParameter("animatriceformation");
			String actif = request.getParameter("actif");
			boolean oui = true;
			if (actif.equals("non"))
				oui = false;
			AnimatriceDAO animdao = new AnimatriceDAO();
			Animatrice animatrice = null;
			try {
				animatrice = animdao.findByName(animatriceformation);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			animatrice.setMail(mail);
			animatrice.setActif(oui);

			try {
				animdao.update(animatrice);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/administration/corfi/afficheutilisateur.jsp?id=" + animatrice.getId_animatrice();

		}

		else if ("admincorfiajoutprecripteur".equals(action)) {
			String nom = request.getParameter("nom");
			nom = nom.toUpperCase();
			PrescripteurDAO presdao = new PrescripteurDAO();
			Prescripteur prescripteur = new Prescripteur(nom);
			int id = 0;
			try {
				id = presdao.create(prescripteur);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/administration/corfi/nouveauprescripteur.jsp?id=" + id + "&nom=" + nom;

		}

		else if ("admincorfiajouttheme".equals(action)) {
			String nom = request.getParameter("nom");
			nom = StringUtils.capitalize(nom);
			ThemeFormationDAO temdao = new ThemeFormationDAO();
			Theme theme = new Theme(nom);
			int id = 0;
			try {
				id = temdao.create(theme);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/administration/corfi/nouveautheme.jsp?id=" + id + "&nom=" + nom;

		}

		else if ("admincorfiajoutniveau".equals(action)) {
			String nom = request.getParameter("nom");
			NiveauQualificationFormationDAO temdao = new NiveauQualificationFormationDAO();
			NiveauQualificationFormation niveau = new NiveauQualificationFormation(nom);
			int id = 0;
			try {
				id = temdao.create(niveau);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/administration/corfi/nouveauniveau.jsp?id=" + id + "&nom=" + nom;

		}

		else if ("admincapajoututilisateur".equals(action)) {
			String nom = request.getParameter("nom");
			String prenom = request.getParameter("prenom");
			String mail = request.getParameter("mail");
			String login = request.getParameter("login");
			String privilege = request.getParameter("privilege");
			UtilisateurDAO temdao = new UtilisateurDAO();
			Utilisateur utilisateur = new Utilisateur(nom, prenom, login, privilege, mail, true);
			int id = 0;
			try {
				id = temdao.create(utilisateur);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (id != 0)
				jspPage = "/jsp/administration/capemploi/afficheutilisateur.jsp?id=" + id + "&login=" + login;
			else
				jspPage = "/jsp/administration/capemploi/nouvelutilisateur.jsp?id=" + id + "&login=" + login + "&nom="
						+ nom + "&prenom=" + prenom + "&mail=" + mail;

		}

		else if ("admincapmodifutilisateur".equals(action)) {
			String mail = request.getParameter("mail");
			String login = request.getParameter("login");
			String ancienlogin = request.getParameter("ancienlogin");
			String privilege = request.getParameter("privilege");
			String actif = request.getParameter("actif");
			boolean oui = true;
			if (actif.equals("non"))
				oui = false;
			UtilisateurDAO temdao = new UtilisateurDAO();
			Utilisateur utilisateur = null;
			try {
				utilisateur = temdao.findByID(Integer.parseInt(ancienlogin));
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			utilisateur.setLogin(login);
			utilisateur.setPrivilege(privilege);
			utilisateur.setMail(mail);
			utilisateur.setActif(oui);

			try {
				temdao.update(utilisateur);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/administration/capemploi/afficheutilisateur.jsp?id=" + utilisateur.getId_salarie()
					+ "&modif=1";

		}

		else if ("modifiercc2r".equals(action)) {
			String ajout = request.getParameter("ajouter");
			String supp = request.getParameter("supprimer");
			Cc2rDAO cdao = new Cc2rDAO();
			CodesPostauxDAO cpdao = new CodesPostauxDAO();
			CodesPostaux ajoutVille = null;
			// Cc2r supVille=null;
			if (ajout != null) {
				String[] liste = request.getParameterValues("list2");
				if (liste != null) {
					for (int i = 0; i < liste.length; i++) {
						try {
							ajoutVille = cpdao.findByID(Integer.parseInt(liste[i]));
							cdao.create(new Cc2r(ajoutVille.getVille()));

						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (DAOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}
			}

			if (supp != null) {
				String[] liste2 = request.getParameterValues("list1");
				if (liste2 != null) {
					for (int i = 0; i < liste2.length; i++) {
						try {
							// supVille=cdao.findByID(Integer.parseInt(liste2[i]));
							cdao.delete(Integer.parseInt(liste2[i]));
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (DAOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

			}
			jspPage = "/jsp/administration/cc2r/modification.jsp";

		}

		/* partie accompagnement et suivi formation ajout le 9/09/2015 */

		else if ("suiviaccompform".equals(action)) {
			String id = request.getParameter("personne");
			int num = Integer.parseInt(id);
			String commentaires = request.getParameter("demande");
			String utilisateurs = request.getParameter("animatriceformation");
			String dateredaction = request.getParameter("dateredaction");
			String commentaires1 = request.getParameter("commentaires1");

			// si la date d'enregistrement n'est pas saisie on met la date du
			// jour
			FormaterDate jour = new FormaterDate();
			java.sql.Date creation = null;
			if (dateredaction.equals("") || dateredaction.equals(null)) {
				String a = jour.getSortie();
				creation = new FormaterDate().changeFormatChaineDate(a);
			} else
				creation = new FormaterDate().changeFormatChaineDate(dateredaction);

			IdentiteDAO iddao = new IdentiteDAO();
			Identite identite = null;
			try {
				identite = iddao.findByID(num);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			AnimatriceDAO utidao = new AnimatriceDAO();
			Animatrice util = null;
			try {
				util = utidao.findByName(utilisateurs);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			AccompagnementFormationDAO acdao = new AccompagnementFormationDAO();
			AccompagnementFormation suivi = new AccompagnementFormation(identite, creation, util, commentaires,
					commentaires1);
			int numacc = 0;
			try {
				numacc = acdao.create(suivi);
				suivi = acdao.findByID(numacc);
			} catch (DAOException e) {
				// TODO Auto-generated catch block

				e.printStackTrace();

			}

			/***************************************************************************/
			/* partie propositions formations */
			/***************************************************************************/
			PropositionsFormationDAO pf = new PropositionsFormationDAO();

			String[] propositions = request.getParameterValues("choixform");
			String com1 = request.getParameter("choixform1");
			String com2 = request.getParameter("choixform2");
			String com3 = request.getParameter("choixform3");
			String com4 = request.getParameter("choixform4");
			String com5 = request.getParameter("choixform5");

			if (propositions != null) {
				for (int i = 0; i < propositions.length; i++) {
					PropositionsFormation pro1 = null;
					switch (propositions[i]) {
					case "Pyramide":
						pro1 = new PropositionsFormation(suivi, propositions[i], com1);
						break;
					case "Formation Pro":
						pro1 = new PropositionsFormation(suivi, propositions[i], com2);
						break;
					case "Cyber-Base":
						pro1 = new PropositionsFormation(suivi, propositions[i], com3);
						break;
					case "Vodéclic":
						pro1 = new PropositionsFormation(suivi, propositions[i], com4);
						break;
					case "Autres":
						pro1 = new PropositionsFormation(suivi, propositions[i], com5);
						break;

					}

					try {
						int cle = pf.create(pro1);
						// System.out.println(cle);
					} catch (DAOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			jspPage = "/jsp/accueil/afficheInscrit.jsp?numero=" + id;
		}

		else if ("modifsuiviaccompform".equals(action)) {

			String id = request.getParameter("suivi");
			int id_suivi = Integer.parseInt(id);
			String utilisateurs = request.getParameter("animatriceformation");
			String dateredaction = request.getParameter("dateredaction");
			String demande = request.getParameter("demande");
			String commentaires = request.getParameter("commentaires1");

			AnimatriceDAO utidao = new AnimatriceDAO();
			Animatrice util = null;
			try {
				util = utidao.findByName(utilisateurs);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// si la date d'enregistrement n'est pas saisie on met la date du
			// jour
			FormaterDate jour = new FormaterDate();
			java.sql.Date creation = null;

			if (dateredaction.equals("") || dateredaction.equals(null)) {
				String a = jour.getSortie();
				creation = new FormaterDate().changeFormatChaineDate(a);
			} else {

				creation = new FormaterDate().changeFormatChaineDate(dateredaction);
			}

			AccompagnementFormationDAO acdao = new AccompagnementFormationDAO();
			AccompagnementFormation suivi = null;
			try {
				suivi = acdao.findByID(id_suivi);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			suivi.setDateredaction(creation);
			suivi.setReferent(util);
			suivi.setDemande(demande);
			suivi.setCommentaire(commentaires);

			try {
				suivi = acdao.update(suivi);

			} catch (DAOException e) {
				// TODO Auto-generated catch block

				e.printStackTrace();

			}
			/***************************************************************************/
			/* partie propositions formations */
			/***************************************************************************/
			PropositionsFormationDAO pf = new PropositionsFormationDAO();
			try {
				pf.deleteByIdAccomp(suivi);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String[] propositions = request.getParameterValues("choixform");
			String com1 = request.getParameter("choixform1");
			String com2 = request.getParameter("choixform2");
			String com3 = request.getParameter("choixform3");
			String com4 = request.getParameter("choixform4");
			String com5 = request.getParameter("choixform5");

			if (propositions != null) {
				for (int i = 0; i < propositions.length; i++) {
					PropositionsFormation pro1 = null;
					switch (propositions[i]) {
					case "Pyramide":
						pro1 = new PropositionsFormation(suivi, propositions[i], com1);
						break;
					case "Formation Pro":
						pro1 = new PropositionsFormation(suivi, propositions[i], com2);
						break;
					case "Cyber-Base":
						pro1 = new PropositionsFormation(suivi, propositions[i], com3);
						break;
					case "Vodéclic":
						pro1 = new PropositionsFormation(suivi, propositions[i], com4);
						break;
					case "Autres":
						pro1 = new PropositionsFormation(suivi, propositions[i], com5);
						break;

					}

					try {
						int cle = pf.create(pro1);
						// System.out.println(cle);
					} catch (DAOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}

			jspPage = "/jsp/suiviFormations/accompagnement.jsp?numero=" + suivi.getIdentite().getId_IDE();

		}

		/* suivi des formations */

		else if ("suiviformation".equals(action)) {
			String id = request.getParameter("personne");
			String animatriceformation = request.getParameter("animatriceformation");
			String dateredaction = request.getParameter("dateredaction");
			String formation = request.getParameter("formation");
			String datedebut = request.getParameter("datedebut");
			String datefin = request.getParameter("datefin");
			String type = request.getParameter("type");
			String of = request.getParameter("of");
			String commentaires = request.getParameter("commentaires");

			// si la date d'enregistrement n'est pas saisie on met la date du
			// jour
			FormaterDate jour = new FormaterDate();
			java.sql.Date creation = null, debut = null, fin = null;

			if (dateredaction.equals("") || dateredaction.equals(null)) {
				String a = jour.getSortie();
				creation = new FormaterDate().changeFormatChaineDate(a);
			} else {

				creation = new FormaterDate().changeFormatChaineDate(dateredaction);
			}

			if (!datedebut.equals("") && !datedebut.equals(null))
				debut = new FormaterDate().changeFormatChaineDate(datedebut);

			if (!datefin.equals("") && !datefin.equals(null))
				fin = new FormaterDate().changeFormatChaineDate(datefin);

			IdentiteDAO iddao = new IdentiteDAO();
			Identite identite = null;
			try {
				identite = iddao.findByID(Integer.parseInt(id));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			AnimatriceDAO utidao = new AnimatriceDAO();
			Animatrice util = null;
			try {
				util = utidao.findByName(animatriceformation);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TypeFormationsDAO tydao = new TypeFormationsDAO();
			TypeFormations typeform = null;
			try {
				typeform = tydao.findByName(type);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			SuiviFormationDAO suidao = new SuiviFormationDAO();
			SuiviFormation suivi = new SuiviFormation(identite, creation, util, formation, debut, fin, of, typeform,
					commentaires);

			try {
				suidao.create(suivi);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/accueil/afficheInscrit.jsp?numero=" + id;
		}

		else if ("modifsuiviformation".equals(action)) {

			String id = request.getParameter("suivi");
			int numId = Integer.parseInt(id);
			SuiviFormationDAO suidao = new SuiviFormationDAO();
			SuiviFormation suivi = null;
			try {
				suivi = suidao.findByID(numId);
			} catch (DAOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}

			String animatriceformation = request.getParameter("animatriceformation");
			String dateredaction = request.getParameter("dateredaction");
			String formation = request.getParameter("formation");
			String datedebut = request.getParameter("datedebut");
			String datefin = request.getParameter("datefin");
			String type = request.getParameter("type");
			String of = request.getParameter("of");
			String commentaires = request.getParameter("commentaires");

			/*
			 * IdentiteDAO idao = new IdentiteDAO(); Identite ident =null; try {
			 * ident = idao.findByID(suivi.getIdentite().getId_IDE()); } catch
			 * (DAOException e2) { // TODO Auto-generated catch block
			 * e2.printStackTrace(); }
			 */
			AnimatriceDAO utidao = new AnimatriceDAO();
			Animatrice anim = null;
			try {
				anim = utidao.findByName(animatriceformation);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			TypeFormationsDAO tydao = new TypeFormationsDAO();
			TypeFormations typeform = null;
			try {
				typeform = tydao.findByName(type);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// si la date d'enregistrement n'est pas saisie on met la date du
			// jour
			FormaterDate jour = new FormaterDate();
			java.sql.Date creation = null, debut = null, fin = null;

			if (dateredaction.equals("") || dateredaction.equals(null)) {
				String a = jour.getSortie();
				creation = new FormaterDate().changeFormatChaineDate(a);
			} else {

				creation = new FormaterDate().changeFormatChaineDate(dateredaction);
			}

			if (!datedebut.equals("") && !datedebut.equals(null))
				debut = new FormaterDate().changeFormatChaineDate(datedebut);

			if (!datefin.equals("") && !datefin.equals(null))
				fin = new FormaterDate().changeFormatChaineDate(datefin);

			suivi.setDateSuivi(creation);
			suivi.setReferent(anim);
			suivi.setFormation(formation);
			suivi.setDateDebutFormation(debut);
			suivi.setDateFinFormation(fin);
			suivi.setOf(of);
			suivi.setTypeFormations(typeform);
			suivi.setCommentaires(commentaires);

			try {
				suidao.update(suivi);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/suiviFormations/suiviformation.jsp?numero=" + suivi.getIdentite().getId_IDE();

		}

		else if ("statsuiviformation".equals(action)) {

			String debut = null, fin = null;
			debut = request.getParameter("datedebut");
			fin = request.getParameter("datefin");
			java.sql.Date ddeb = null, dfin = null;

			if (!debut.equals("") && !debut.equals(null))
				ddeb = new FormaterDate().changeFormatChaineDate(debut);

			if (!fin.equals("") && !fin.equals(null))
				dfin = new FormaterDate().changeFormatChaineDate(fin);

			TypeFormationsDAO tydao = new TypeFormationsDAO();
			List<TypeFormations> listetypes = null;
			try {
				listetypes = tydao.findAll();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Map<String, Integer> retour = new HashMap<String, Integer>();
			SuiviFormationDAO suidao = new SuiviFormationDAO();
			for (int i = 0; i < listetypes.size(); i++) {
				try {
					retour.put(listetypes.get(i).getNomProposition(),
							suidao.afficheStatSurPeriode(ddeb, dfin, listetypes.get(i).getId_proposition()));
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			request.setAttribute("retour", retour);

			jspPage = "/jsp/suiviFormations/statSuiviFormation.jsp?datedebut=" + debut + "&datefin=" + fin;

		} else if ("mutuellenouveau".equals(action)) {
			String idpers = request.getParameter("numero");
			IdentiteDAO daopersonne = new IdentiteDAO();
			Identite personne = null;
			try {
				personne = daopersonne.findByID(Integer.parseInt(idpers));
			} catch (DAOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			String debut = null, fin = null, datefinmulti = null;
			debut = request.getParameter("dateaccept");
			fin = request.getParameter("dateecheance");
			java.sql.Date ddeb = null, dfin = null, dfinmulti = null;
			String refus = request.getParameter("refus");
			datefinmulti = request.getParameter("finmulti");

			if (!debut.equals("") && !debut.equals(null))
				ddeb = new FormaterDate().changeFormatChaineDate(debut);

			if (!fin.equals("") && !fin.equals(null))
				dfin = new FormaterDate().changeFormatChaineDate(fin);

			if (!datefinmulti.equals("") && !datefinmulti.equals(null))
				dfinmulti = new FormaterDate().changeFormatChaineDate(datefinmulti);

			/* sinon la date d'echéance est la date de proposition +1 an */
			else {
				java.util.Date proposition = new FormaterDate().plusUnAn(ddeb);
				dfinmulti = new java.sql.Date(proposition.getTime());
			}

			String acceptation = request.getParameter("acceptation");
			boolean accept;
			if (acceptation.equals("oui")) {
				accept = true;
				datefinmulti = "";
				refus = "";

			} else {
				accept = false;
				if (dfinmulti != null)
					dfin = dfinmulti;

			}

			MutuelleDAO mutdao = new MutuelleDAO();
			Mutuelle mutuelle = new Mutuelle(personne, ddeb, accept, dfin, refus, dfinmulti);
			int id = 0;
			try {
				id = mutdao.create(mutuelle);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (id != 0)
				jspPage = "/jsp/mutuelle/affichage.jsp?idmutuelle=" + id;
			else
				jspPage = "/jsp/accueil/afficheInscrit.jsp?numero=" + idpers;
		}

		else if ("mutuellemodifierfiche".equals(action)) {
			String mutuel = request.getParameter("id_mutuelle");
			MutuelleDAO mudao = new MutuelleDAO();
			Mutuelle mutuelle = null;
			try {
				mutuelle = mudao.findByID(Integer.parseInt(mutuel));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			IdentiteDAO daopersonne = new IdentiteDAO();
			Identite personne = null;
			personne = mutuelle.getIdentite();

			String debut = null, fin = null, finrefus = null;
			debut = request.getParameter("dateaccept");
			fin = request.getParameter("dateecheance");
			java.sql.Date ddeb = null, dfin = null, dfinrefus = null;
			String refus = request.getParameter("refus");
			finrefus = request.getParameter("finmulti");

			if (!debut.equals("") && !debut.equals(null))
				ddeb = new FormaterDate().changeFormatChaineDate(debut);

			if (!fin.equals("") && !fin.equals(null))
				dfin = new FormaterDate().changeFormatChaineDate(fin);

			/*
			 * si une date de fin couverture est donnée , elle sert aussi de
			 * date d'echeance
			 */
			if (!finrefus.equals("") && !finrefus.equals(null)) {
				dfinrefus = new FormaterDate().changeFormatChaineDate(finrefus);

			}
			/* sinon la date d'echéance est la date de proposition +1 an */
			else {
				java.util.Date proposition = new FormaterDate().plusUnAn(ddeb);
				dfinrefus = new java.sql.Date(proposition.getTime());
			}

			String acceptation = request.getParameter("acceptation");
			boolean accept;
			if (acceptation.equals("oui")) {
				accept = true;
				refus = "";
				dfinrefus = null;

			} else {
				accept = false;
				if (dfinrefus != null)
					dfin = dfinrefus;
				else
					dfin = null;
			}

			Mutuelle tampon = new Mutuelle(mutuelle.getId_mutuelle(), personne, ddeb, accept, dfin, refus, dfinrefus);

			try {
				mutuelle = mudao.update(tampon);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jspPage = "/jsp/mutuelle/affichage.jsp?idmutuelle=" + mutuelle.getId_mutuelle() + "&numero="
					+ personne.getId_IDE();
		}

		else if ("echeancemutuelle".equals(action)) {
			String a = request.getParameter("a");
			String debut = null, fin = null;
			java.sql.Date ddeb = null, dfin = null;

			// recuperation des dates de debut et fin du mois suivant

			Calendar cal0 = Calendar.getInstance();
			java.util.Date date = cal0.getTime();
			// System.out.println("mois " + date);

			cal0.add(Calendar.MONTH, +1);
			java.util.Date date1 = cal0.getTime();
			// System.out.println("mois +1 " + date1);

			cal0.add(Calendar.MONTH, +1);
			java.util.Date date2 = cal0.getTime();
			// System.out.println("mois +2 " + date2);

			cal0.add(Calendar.MONTH, -3);
			java.util.Date datemoins1 = cal0.getTime();
			// System.out.println("mois -1 " + datemoins1);

			// Calendar calmoins2=Calendar.getInstance();
			cal0.add(Calendar.MONTH, -1);
			java.util.Date datemoins2 = cal0.getTime();
			// System.out.println("mois - 2 " + datemoins2);

			ContratDAO codao = new ContratDAO();
			AvenantDAO avdao = new AvenantDAO();
			List<Contrat> listecontrat = null;
			List<Avenant> listeAvenant = null;

			if (a == null) {
				debut = request.getParameter("datedebut");
				fin = request.getParameter("datefin");
				ddeb = new FormaterDate().changeFormatChaineDate(debut);
				dfin = new FormaterDate().changeFormatChaineDate(fin);
				try {
					listecontrat = codao.contratAIMutuelleParDateFin(ddeb, dfin);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("contrats", listecontrat);
				try {
					listeAvenant = avdao.listeAvenantMutuelleParDates(ddeb, dfin);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("avenants", listeAvenant);
			} else {
				if (a.equals("-2")) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(datemoins2);
					int njours = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

					cal.set(Calendar.DATE, 1);
					ddeb = new java.sql.Date(cal.getTime().getTime());
					cal.set(Calendar.DATE, njours);
					dfin = new java.sql.Date(cal.getTime().getTime());

				} else if (a.equals("-1")) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(datemoins1);
					int njours = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

					cal.set(Calendar.DATE, 1);
					ddeb = new java.sql.Date(cal.getTime().getTime());
					cal.set(Calendar.DATE, njours);
					dfin = new java.sql.Date(cal.getTime().getTime());

				}

				else if (a.equals("0")) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					int njours = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

					cal.set(Calendar.DATE, 1);
					ddeb = new java.sql.Date(cal.getTime().getTime());
					cal.set(Calendar.DATE, njours);
					dfin = new java.sql.Date(cal.getTime().getTime());

				}

				else if (a.equals("1")) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(date1);
					int njours = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

					cal.set(Calendar.DATE, 1);
					ddeb = new java.sql.Date(cal.getTime().getTime());
					cal.set(Calendar.DATE, njours);
					dfin = new java.sql.Date(cal.getTime().getTime());

				}

				else if (a.equals("2")) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(date2);
					int njours = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

					cal.set(Calendar.DATE, 1);
					ddeb = new java.sql.Date(cal.getTime().getTime());
					cal.set(Calendar.DATE, njours);
					dfin = new java.sql.Date(cal.getTime().getTime());

				}

				try {
					listecontrat = codao.contratAIMutuelleParDateFin(ddeb, dfin);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("contrats", listecontrat);
				try {
					listeAvenant = avdao.listeAvenantMutuelleParDates(ddeb, dfin);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("avenants", listeAvenant);

			}

			jspPage = "/jsp/ai/recherche/echeancemutuelle.jsp?datedebut=" + ddeb + "&datefin=" + dfin;

		}

		else if ("echeancemutuelleignoredatescontrats".equals(action)) {
			String a = request.getParameter("a");
			String debut = null, fin = null, debutMutuelle = null, finDepuisDebut = null;
			java.sql.Date ddeb = null, dfin = null, dDebutMutuelle = null, dfinDepuisDebut = null;

			// recuperation des dates de debut et fin du mois suivant

			Calendar cal0 = Calendar.getInstance();
			java.util.Date date = cal0.getTime();
			// System.out.println("mois " + date);

			cal0.add(Calendar.MONTH, +1);
			java.util.Date date1 = cal0.getTime();
			// System.out.println("mois +1 " + date1);

			cal0.add(Calendar.MONTH, +1);
			java.util.Date date2 = cal0.getTime();
			// System.out.println("mois +2 " + date2);

			cal0.add(Calendar.MONTH, -3);
			java.util.Date datemoins1 = cal0.getTime();
			// System.out.println("mois -1 " + datemoins1);

			// Calendar calmoins2=Calendar.getInstance();
			cal0.add(Calendar.MONTH, -1);
			java.util.Date datemoins2 = cal0.getTime();
			// System.out.println("mois - 2 " + datemoins2);

			MutuelleDAO mudao = new MutuelleDAO();
			List<Mutuelle> liste = null;

			if (a == null) {
				debut = request.getParameter("datedebut");
				/* la date de début est la date 2017/01/01 */
				if (debut.equals("") || debut.equals(null)) {
					java.util.Date proposition = new FormaterDate().debutMutuelle();
					ddeb = new java.sql.Date(proposition.getTime());
					finDepuisDebut = request.getParameter("datefin1");
					if (!finDepuisDebut.equals("") && !finDepuisDebut.equals(null)) {
						dfinDepuisDebut = new FormaterDate().changeFormatChaineDate(finDepuisDebut);
					}
					try {
						liste = mudao.termineesDansFourchette(ddeb, dfinDepuisDebut);
					} catch (DAOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					request.setAttribute("liste", liste);
					jspPage = "/jsp/ai/recherche/echeancemutuelleIgnoreDateContrats.jsp?datedebut=" + ddeb + "&datefin="
							+ dfinDepuisDebut;

				}
				/*
				 * si une date de fin couverture est donnée , elle sert aussi de
				 * date d'echeance
				 */
				else {
					ddeb = new FormaterDate().changeFormatChaineDate(debut);
					fin = request.getParameter("datefin");
					dfin = new FormaterDate().changeFormatChaineDate(fin);

					try {
						liste = mudao.termineesDansFourchette(ddeb, dfin);
					} catch (DAOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					request.setAttribute("liste", liste);
					jspPage = "/jsp/ai/recherche/echeancemutuelleIgnoreDateContrats.jsp?datedebut=" + ddeb + "&datefin="
							+ dfin;

				}
			} else {
				if (a.equals("-2")) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(datemoins2);
					int njours = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

					cal.set(Calendar.DATE, 1);
					ddeb = new java.sql.Date(cal.getTime().getTime());
					cal.set(Calendar.DATE, njours);
					dfin = new java.sql.Date(cal.getTime().getTime());

				} else if (a.equals("-1")) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(datemoins1);
					int njours = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

					cal.set(Calendar.DATE, 1);
					ddeb = new java.sql.Date(cal.getTime().getTime());
					cal.set(Calendar.DATE, njours);
					dfin = new java.sql.Date(cal.getTime().getTime());

				}

				else if (a.equals("0")) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					int njours = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

					cal.set(Calendar.DATE, 1);
					ddeb = new java.sql.Date(cal.getTime().getTime());
					cal.set(Calendar.DATE, njours);
					dfin = new java.sql.Date(cal.getTime().getTime());

				}

				else if (a.equals("1")) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(date1);
					int njours = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

					cal.set(Calendar.DATE, 1);
					ddeb = new java.sql.Date(cal.getTime().getTime());
					cal.set(Calendar.DATE, njours);
					dfin = new java.sql.Date(cal.getTime().getTime());

				}

				else if (a.equals("2")) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(date2);
					int njours = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

					cal.set(Calendar.DATE, 1);
					ddeb = new java.sql.Date(cal.getTime().getTime());
					cal.set(Calendar.DATE, njours);
					dfin = new java.sql.Date(cal.getTime().getTime());

				}

				try {
					liste = mudao.termineesDansFourchette(ddeb, dfin);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("liste", liste);

			

			jspPage = "/jsp/ai/recherche/echeancemutuelleIgnoreDateContrats.jsp?datedebut=" + ddeb + "&datefin=" + dfin;

		}
		}

		/* ____________________________________________________________________________________________ */
		/**********************************************************************************************/
		/*******
		 * Envoyer la page correspondante � la demande
		 ********************/
		/**********************************************************************************************/
		getServletContext().getRequestDispatcher(jspPage).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
