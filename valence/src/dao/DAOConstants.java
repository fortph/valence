package dao;

public final class DAOConstants {

	// D�claration des noms des tables : nom en minuscule
	// ****************************beans.identite***********************
	// *****************************************************************
	public static final String t_diplome = "profildiplome";
	public static final String t_emploiRecherche = "profilrecherche";
	public static final String t_identite = "identite";
	public static final String t_locomotion = "profilLocomotion";
	public static final String t_niveauFormation = "param_niveauFormation";
	public static final String t_permis = "profilPermisPro";
	public static final String t_priorite = "profilPriorite";
	public static final String t_rome = "param_rome";
	public static final String t_situationFamiliale = "param_situationFamiliale";
	public static final String t_codespostaux = "param_codespostaux";
	public static final String t_nationalite = "param_nationalite";
	public static final String t_origine = "param_origine";
	public static final String t_sexe = "param_sexe";
	public static final String t_cc2r = "param_cc2r";
	public static final String t_parampermispro = "param_permis";
	public static final String t_parampriorite = "param_priorite";
	public static final String t_paramlocomotion = "param_locomotion";
	public static final String t_experience = "profilexperience";
	public static final String t_civilite = "param_civilite";
	public static final String t_organisme = "param_organisme";
	public static final String t_parampropositionsformations="param_typeFormations";

	// ****************************beans.formation***********************
	// *****************************************************************/
	public static final String t_animatrices = "formationanimatrice";
	public static final String t_prescripteur = "formationprescripteur";
	public static final String t_organismesformation = "formationorganisation";
	public static final String t_listeFormations = "formationliste";
	public static final String t_qualificationFormation = "formationqualificationniveau";
	public static final String t_preinscription = "formationinscription";
	public static final String t_themes = "formationtheme";
	

	// ****************************beans.parametres.formationpro***********/
	// *****************************************************************/
	public static final String t_formproemployeur = "formproemployeur";
	public static final String t_formprofinance = "formprofinancement";
	public static final String t_formproinscription = "formproinscription";
	public static final String t_formproniveau = "formproniveau";
	public static final String t_formprostatutemployeur = "formprostatutemployeur";
	public static final String t_formprotheme = "formprotheme";

	// ****************************beans.parametres.employeurs**************/
	// *****************************************************************/
	public static final String t_employeurservice = "employeurservice";
	public static final String t_employeurstatut = "employeurstatut";
	public static final String t_employeurstructure = "employeurstructure";
	public static final String t_employeuractivite = "employeuractivitepro";
	public static final String t_employeurcontact = "employeurcontact";
	public static final String t_employeur = "employeur";
	public static final String t_capemploiutilisateurs = "capemploiutilisateurs";
	public static final String t_employeursuivi = "employeursuivi";
	public static final String t_offre = "employeuroffre";
	public static final String t_employeuroffreprop="employeuroffrepropo";
	
	/**************************suivi salaries *****************************/
	/**********************************************************************/
	public static final String t_suivi="suivi";
	public static final String t_suiviemploi="suiviemploi";
	public static final String t_propositionsFormation="propositionsFormation";
	public static final String t_suiviAccompagneformation="suiviAccomFormation";
	public static final String t_suiviformation="suiviFormation";

	/**************************     A I      *****************************/
	/**********************************************************************/
	public static final String t_aicreation="aifiche";
	public static final String t_aivisitemedicale="aisuivimedical";
	public static final String t_aicontrat="aicontrat";
	public static final String t_aiextranet="aiextranet";
	public static final String t_aiagrement="aiagrement";
	public static final String t_smic="param_smic";
	public static final String t_aiavenant="aiavenant";
	
	
	/***************************     RMI  **************************************/
	/***************************************************************************/
	public static final String t_rmicreation="rmifiche";
	public static final String t_rmicontrat="rmicontrat";
	public static final String t_rmiconvocation="rmiconvoc";
	public static final String t_referentpe="referentpoleemploi";
	public static final String t_ficheliaison="rmiliaison";
	
	/*******************************RTH**********************************/
	public static final String t_rth="rthfiche";
	
	/**************************    SAP   *******************************/
	public static final String t_saptaches="saptaches";
	public static final String t_sapcontrat="sapcontrat";
	public static final String t_sapavenant="sapavenantcontrat";
	public static final String t_sapprestation="sapprestationcontrat";
	public static final String t_sapprestationaven="sapprestationavenant";
	
	public static final String t_mutuelle="mutuelle";
	public static final String t_mutuellemultiemp="mutuelle_multiEmployeurs";
	
	/*
	// Declaration des informations n�cessaires � la connexion
	public static String driver = "com.mysql.jdbc.Driver";
	//public static String chaineConnexion = "jdbc:mysql://localhost/valence?useUnicode=TRUE&characterEncoding=Cp1252";
	public static String chaineConnexion = "jdbc:mysql://localhost/valence";

	public static String login = "root";
	public static String password = "";    PhPcAp   
	  */
	
	
	// Constructeur en private : pas d'instanciation de la classe car classe
	// utilitaire
	private DAOConstants() {

	}
}