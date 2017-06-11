package beans.sap;

import java.util.Date;

import beans.employeurs.Employeur;
import beans.identite.Identite;

public class PrestationCDI {

	private int id_prestationcontrat;
	private Employeur employeur;
	private Identite identite;
	private TachesSAP tache;
	private int heuresminimois_pr;
	private float salairehor_pr; 
	private float panier_pr; 
	private float deplacement_pr;
	private float facsalairehor_pr;
	private String commentaire_pr;
	private Date redaction_pr;
	private Date datedebut_pr;
	private Date datefin_pr;
	private String agrementsap_pr; // a conserver en cas de changement
	private String directeurcapemploi_pr; // a conserver en cas de changement
	private Date daterenouvel;
	private int heurescontrat;
	
	
	
	public Date getDaterenouvel() {
		return daterenouvel;
	}

	public void setDaterenouvel(Date daterenouvel) {
		this.daterenouvel = daterenouvel;
	}

	public int getId_prestationcontrat() {
		return id_prestationcontrat;
	}

	public void setId_prestationcontrat(int id_prestationcontrat) {
		this.id_prestationcontrat = id_prestationcontrat;
	}

	public Employeur getEmployeur() {
		return employeur;
	}

	public void setEmployeur(Employeur employeur) {
		this.employeur = employeur;
	}

	public Identite getIdentite() {
		return identite;
	}

	public void setIdentite(Identite identite) {
		this.identite = identite;
	}

	public TachesSAP getTache() {
		return tache;
	}

	public void setTache(TachesSAP tache) {
		this.tache = tache;
	}

	public int getHeuresminimois_pr() {
		return heuresminimois_pr;
	}

	public void setHeuresminimois_pr(int heuresminimois_pr) {
		this.heuresminimois_pr = heuresminimois_pr;
	}

	public float getSalairehor_pr() {
		return salairehor_pr;
	}

	public void setSalairehor_pr(float salairehor_pr) {
		this.salairehor_pr = salairehor_pr;
	}
	
	public float getPanier_pr() {
		return panier_pr;
	}

	public void setPanier_pr(float panier_pr) {
		this.panier_pr = panier_pr;
	}

	public float getDeplacement_pr() {
		return deplacement_pr;
	}

	public void setDeplacement_pr(float deplacement_pr) {
		this.deplacement_pr = deplacement_pr;
	}

	public float getFacsalairehor_pr() {
		return facsalairehor_pr;
	}

	public void setFacsalairehor_pr(float facsalairehor_pr) {
		this.facsalairehor_pr = facsalairehor_pr;
	}

	public String getCommentaire_pr() {
		return commentaire_pr;
	}

	public void setCommentaire_pr(String commentaire_pr) {
		this.commentaire_pr = commentaire_pr;
	}

	public Date getRedaction_pr() {
		return redaction_pr;
	}

	public void setRedaction_pr(Date redaction_pr) {
		this.redaction_pr = redaction_pr;
	}

	public Date getDatedebut_pr() {
		return datedebut_pr;
	}

	public void setDatedebut_pr(Date datedebut_pr) {
		this.datedebut_pr = datedebut_pr;
	}

	public Date getDatefin_pr() {
		return datefin_pr;
	}

	public void setDatefin_pr(Date datefin_pr) {
		this.datefin_pr = datefin_pr;
	}

	public String getAgrementsap_pr() {
		return agrementsap_pr;
	}

	public void setAgrementsap_pr(String agrementsap_pr) {
		this.agrementsap_pr = agrementsap_pr;
	}

	public String getDirecteurcapemploi_pr() {
		return directeurcapemploi_pr;
	}

	public void setDirecteurcapemploi_pr(String directeurcapemploi_pr) {
		this.directeurcapemploi_pr = directeurcapemploi_pr;
	}
	
	public int getHeurescontrat() {
		return heurescontrat;
	}

	public void setHeurescontrat(int heurescontrat) {
		this.heurescontrat = heurescontrat;
	}

	public PrestationCDI(int id_prestationcontrat, Employeur employeur,
			Identite identite, TachesSAP tache, int heuresminimois_pr,
			float salairehor_pr, float facsalairehor_pr,float panier_pr,
			float deplacement_pr,String commentaire_pr,
			Date redaction_pr, Date datedebut_pr, Date datefin_pr,
			String agrementsap_pr, String directeurcapemploi_pr,int heurescontrat) {
		super();
		this.id_prestationcontrat = id_prestationcontrat;
		this.employeur = employeur;
		this.identite = identite;
		this.tache = tache;
		this.heuresminimois_pr = heuresminimois_pr;
		this.salairehor_pr = salairehor_pr;
		this.panier_pr=panier_pr;
		this.deplacement_pr=deplacement_pr;
		this.facsalairehor_pr = facsalairehor_pr;
		this.commentaire_pr = commentaire_pr;
		this.redaction_pr = redaction_pr;
		this.datedebut_pr = datedebut_pr;
		this.datefin_pr = datefin_pr;
		this.agrementsap_pr = agrementsap_pr;
		this.directeurcapemploi_pr = directeurcapemploi_pr;
		this.heurescontrat=heurescontrat;
	}

	public PrestationCDI() {
		super();
	}

	public PrestationCDI(Employeur employeur, Identite identite,
			TachesSAP tache, int heuresminimois_pr, float salairehor_pr,
			float panier_pr,float deplacement_pr,
			float facsalairehor_pr, String commentaire_pr, Date redaction_pr,
			Date datedebut_pr, Date datefin_pr, String agrementsap_pr,
			String directeurcapemploi_pr,int heurescontrat) {
		super();
		this.employeur = employeur;
		this.identite = identite;
		this.tache = tache;
		this.heuresminimois_pr = heuresminimois_pr;
		this.salairehor_pr = salairehor_pr;
		this.panier_pr=panier_pr;
		this.deplacement_pr=deplacement_pr;
		this.facsalairehor_pr = facsalairehor_pr;
		this.commentaire_pr = commentaire_pr;
		this.redaction_pr = redaction_pr;
		this.datedebut_pr = datedebut_pr;
		this.datefin_pr = datefin_pr;
		this.agrementsap_pr = agrementsap_pr;
		this.directeurcapemploi_pr = directeurcapemploi_pr;
		this.heurescontrat=heurescontrat;
	}

	public PrestationCDI(int id_prestationcontrat, Employeur employeur,
			TachesSAP tache, int heuresminimois_pr, Date datedebut_pr,
			Date datefin_pr) {
		super();
		this.id_prestationcontrat = id_prestationcontrat;
		this.employeur = employeur;
		this.tache = tache;
		this.heuresminimois_pr = heuresminimois_pr;
		this.datedebut_pr = datedebut_pr;
		this.datefin_pr = datefin_pr;
	}
	
	public PrestationCDI(int id_prestationcontrat, Employeur employeur,
			TachesSAP tache, int heuresminimois_pr, Date datedebut_pr,
			Date datefin_pr,int heurescontrat) {
		super();
		this.id_prestationcontrat = id_prestationcontrat;
		this.employeur = employeur;
		this.tache = tache;
		this.heuresminimois_pr = heuresminimois_pr;
		this.datedebut_pr = datedebut_pr;
		this.datefin_pr = datefin_pr;
		this.heurescontrat=heurescontrat;
	}


	public PrestationCDI(int id_prestationcontrat, Identite identite,
			TachesSAP tache, int heuresminimois_pr, Date datedebut_pr,
			Date datefin_pr, Date redaction_pr) {
		super();
		this.id_prestationcontrat = id_prestationcontrat;
		this.identite = identite;
		this.tache = tache;
		this.heuresminimois_pr = heuresminimois_pr;
		this.datedebut_pr = datedebut_pr;
		this.datefin_pr = datefin_pr;
		this.redaction_pr = redaction_pr;
	}

	public PrestationCDI(int id_prestationcontrat, Identite identite,
			TachesSAP tache, int heuresminimois_pr, Date datedebut_pr,
			Date datefin_pr, Date redaction_pr,int heurescontrat) {
		super();
		this.id_prestationcontrat = id_prestationcontrat;
		this.identite = identite;
		this.tache = tache;
		this.heuresminimois_pr = heuresminimois_pr;
		this.datedebut_pr = datedebut_pr;
		this.datefin_pr = datefin_pr;
		this.redaction_pr = redaction_pr;
		this.heurescontrat=heurescontrat;
	}

	public PrestationCDI(int id_prestationcontrat, TachesSAP tache,
			int heuresminimois_pr, Date datedebut_pr, Date datefin_pr) {
		super();
		this.id_prestationcontrat = id_prestationcontrat;
		this.tache = tache;
		this.heuresminimois_pr = heuresminimois_pr;
		this.datedebut_pr = datedebut_pr;
		this.datefin_pr = datefin_pr;

	}

	public PrestationCDI(int id_prestationcontrat,TachesSAP tache,
			Employeur employeur,
			 int heuresminimois_pr,
			Date datedebut_pr, Date datefin_pr,Date redaction_pr) {
		super();
		this.id_prestationcontrat = id_prestationcontrat;
		this.employeur = employeur;
		this.tache = tache;
		this.heuresminimois_pr = heuresminimois_pr;
		this.redaction_pr = redaction_pr;
		this.datedebut_pr = datedebut_pr;
		this.datefin_pr = datefin_pr;
	}

	public PrestationCDI(int id_prestationcontrat, Employeur employeur,
			Identite identite, TachesSAP tache, int heuresminimois_pr,
			 Date datedebut_pr, Date datefin_pr,Date redaction_pr) {
		super();
		this.id_prestationcontrat = id_prestationcontrat;
		this.employeur = employeur;
		this.identite = identite;
		this.tache = tache;
		this.heuresminimois_pr = heuresminimois_pr;		
		this.datedebut_pr = datedebut_pr;
		this.datefin_pr = datefin_pr;
		this.redaction_pr = redaction_pr;
	}

	public PrestationCDI(int id_prestationcontrat, Employeur employeur,
			Identite identite, TachesSAP tache, int heuresminimois_pr,
			float salairehor_pr,float panier_pr,
			float deplacement_pr, float facsalairehor_pr, String commentaire_pr,
			Date redaction_pr, Date datedebut_pr, Date datefin_pr,
			String agrementsap_pr, String directeurcapemploi_pr,
			Date daterenouvel,int heurescontrat) {
		super();
		this.id_prestationcontrat = id_prestationcontrat;
		this.employeur = employeur;
		this.identite = identite;
		this.tache = tache;
		this.heuresminimois_pr = heuresminimois_pr;
		this.salairehor_pr = salairehor_pr;
		this.panier_pr=panier_pr;
		this.deplacement_pr=deplacement_pr;
		this.facsalairehor_pr = facsalairehor_pr;
		this.commentaire_pr = commentaire_pr;
		this.redaction_pr = redaction_pr;
		this.datedebut_pr = datedebut_pr;
		this.datefin_pr = datefin_pr;
		this.agrementsap_pr = agrementsap_pr;
		this.directeurcapemploi_pr = directeurcapemploi_pr;
		this.daterenouvel = daterenouvel;
		this.heurescontrat=heurescontrat;
	}

	public PrestationCDI(Employeur employeur, Identite identite,
			TachesSAP tache, int heuresminimois_pr, float salairehor_pr,
			float panier_pr,
			float deplacement_pr,
			float facsalairehor_pr, String commentaire_pr, Date redaction_pr,
			Date datedebut_pr, Date datefin_pr, String agrementsap_pr,
			String directeurcapemploi_pr, Date daterenouvel) {
		super();
		this.employeur = employeur;
		this.identite = identite;
		this.tache = tache;
		this.heuresminimois_pr = heuresminimois_pr;
		this.salairehor_pr = salairehor_pr;
		this.panier_pr=panier_pr;
		this.deplacement_pr=deplacement_pr;
		this.facsalairehor_pr = facsalairehor_pr;
		this.commentaire_pr = commentaire_pr;
		this.redaction_pr = redaction_pr;
		this.datedebut_pr = datedebut_pr;
		this.datefin_pr = datefin_pr;
		this.agrementsap_pr = agrementsap_pr;
		this.directeurcapemploi_pr = directeurcapemploi_pr;
		this.daterenouvel = daterenouvel;
	}

	public PrestationCDI(int id_prestationcontrat,Identite identite, Employeur employeur,
			 TachesSAP tache, int heuresminimois_pr,
			float salairehor_pr, float facsalairehor_pr,float panier_pr, float deplacement_pr,
			 String commentaire_pr, Date datefin_pr) {
		super();
		this.id_prestationcontrat = id_prestationcontrat;
		this.employeur = employeur;
		this.identite = identite;
		this.tache = tache;
		this.heuresminimois_pr = heuresminimois_pr;
		this.salairehor_pr = salairehor_pr;
		this.panier_pr = panier_pr;
		this.deplacement_pr = deplacement_pr;
		this.facsalairehor_pr = facsalairehor_pr;
		this.commentaire_pr = commentaire_pr;
		this.datefin_pr = datefin_pr;
	}

	public PrestationCDI(int id_prestationcontrat,Identite identite, Employeur employeur,
			 TachesSAP tache, int heuresminimois_pr,
			float salairehor_pr, float facsalairehor_pr,float panier_pr, float deplacement_pr,
			 String commentaire_pr, Date datedebut_pr,
			int heurescontrat) {
		super();
		this.id_prestationcontrat = id_prestationcontrat;
		this.employeur = employeur;
		this.identite = identite;
		this.tache = tache;
		this.heuresminimois_pr = heuresminimois_pr;
		this.salairehor_pr = salairehor_pr;
		this.panier_pr = panier_pr;
		this.deplacement_pr = deplacement_pr;
		this.facsalairehor_pr = facsalairehor_pr;
		this.commentaire_pr = commentaire_pr;
		this.datedebut_pr = datedebut_pr;
		this.heurescontrat = heurescontrat;
	}

	
	
}
