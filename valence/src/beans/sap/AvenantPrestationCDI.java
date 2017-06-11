package beans.sap;

import java.util.Date;


public class AvenantPrestationCDI {
	private int id_prestationavenant;
	private int rangavenant;
	private PrestationCDI prestation;
	private TachesSAP tache;
	private int heuresminimois_pres;
	private float salairehor_av;
	private float panier_av; 
	private float deplacement_av;
	private float facsalairehor_pr;
	private String commentaire;
	private Date redaction_pr;
	private Date datedebut_pr;
	private Date datefin_pr;
	private Date daterenouvel;
	private int heurescontrat;
	
	
	

	

	public Date getDaterenouvel() {
		return daterenouvel;
	}

	public void setDaterenouvel(Date daterenouvel) {
		this.daterenouvel = daterenouvel;
	}

	public int getId_prestationavenant() {
		return id_prestationavenant;
	}

	public void setId_prestationavenant(int id_prestationavenant) {
		this.id_prestationavenant = id_prestationavenant;
	}

	public int getRangavenant() {
		return rangavenant;
	}

	public void setRangavenant(int rangavenant) {
		this.rangavenant = rangavenant;
	}

	public PrestationCDI getPrestation() {
		return prestation;
	}

	public void setPrestation(PrestationCDI prestation) {
		this.prestation = prestation;
	}

	public TachesSAP getTache() {
		return tache;
	}

	public void setTache(TachesSAP tache) {
		this.tache = tache;
	}

	public int getHeuresminimois_pres() {
		return heuresminimois_pres;
	}

	public void setHeuresminimois_pres(int heuresminimois_pres) {
		this.heuresminimois_pres = heuresminimois_pres;
	}

	public float getSalairehor_av() {
		return salairehor_av;
	}

	public void setSalairehor_av(float salairehor_av) {
		this.salairehor_av = salairehor_av;
	}
	
	public float getPanier_av() {
		return panier_av;
	}

	public void setPanier_av(float panier_av) {
		this.panier_av = panier_av;
	}

	public float getDeplacement_av() {
		return deplacement_av;
	}

	public void setDeplacement_av(float deplacement_av) {
		this.deplacement_av = deplacement_av;
	}

	public float getFacsalairehor_pr() {
		return facsalairehor_pr;
	}

	public void setFacsalairehor_pr(float facsalairehor_pr) {
		this.facsalairehor_pr = facsalairehor_pr;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
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
	
	

	public int getHeurescontrat() {
		return heurescontrat;
	}

	public void setHeurescontrat(int heurescontrat) {
		this.heurescontrat = heurescontrat;
	}

	public AvenantPrestationCDI() {
		super();
	}

	public AvenantPrestationCDI(PrestationCDI prestation, TachesSAP tache,
			 int heuresminimois_pres, Date datedebut_pr,
			Date datefin_pr, Date redaction_pr) {
		super();
		this.prestation = prestation;
		this.tache = tache;
		this.heuresminimois_pres = heuresminimois_pres;
		this.redaction_pr = redaction_pr;
		this.datedebut_pr = datedebut_pr;
		this.datefin_pr = datefin_pr;
	}



	public AvenantPrestationCDI(int id_prestationavenant, int rangavenant,
			PrestationCDI prestation,TachesSAP tache,
			int heuresminimois_pres, float salairehor_av,float panier_av,float deplacement_av,
			float facsalairehor_pr, String commentaire, Date redaction_pr,
			Date datedebut_pr, Date datefin_pr) {
		super();
		this.id_prestationavenant = id_prestationavenant;
		this.rangavenant = rangavenant;
		this.prestation = prestation;
		this.tache = tache;
		this.heuresminimois_pres = heuresminimois_pres;
		this.salairehor_av = salairehor_av;
		this.panier_av=panier_av;
		this.deplacement_av=deplacement_av;
		this.facsalairehor_pr = facsalairehor_pr;
		this.commentaire = commentaire;
		this.redaction_pr = redaction_pr;
		this.datedebut_pr = datedebut_pr;
		this.datefin_pr = datefin_pr;
	}

	public AvenantPrestationCDI(int rangavenant, PrestationCDI prestation,
			 TachesSAP tache, int heuresminimois_pres,
			float salairehor_av,float panier_av,float deplacement_av,
			float facsalairehor_pr, String commentaire,
			Date redaction_pr, Date datedebut_pr, Date datefin_pr) {
		super();
		this.rangavenant = rangavenant;
		this.prestation = prestation;
		this.tache = tache;
		this.heuresminimois_pres = heuresminimois_pres;
		this.salairehor_av = salairehor_av;
		this.panier_av=panier_av;
		this.deplacement_av=deplacement_av;
		this.facsalairehor_pr = facsalairehor_pr;
		this.commentaire = commentaire;
		this.redaction_pr = redaction_pr;
		this.datedebut_pr = datedebut_pr;
		this.datefin_pr = datefin_pr;
	}

	public AvenantPrestationCDI(int id_prestationavenant, TachesSAP tache,
			 int heuresminimois_pres, Date redaction_pr,
			Date datedebut_pr, Date datefin_pr) {
		super();
		this.id_prestationavenant = id_prestationavenant;
		this.tache = tache;
		this.heuresminimois_pres = heuresminimois_pres;
		this.redaction_pr = redaction_pr;
		this.datedebut_pr = datedebut_pr;
		this.datefin_pr = datefin_pr;
	}

	public AvenantPrestationCDI(int id_prestationavenant,
			PrestationCDI prestation, TachesSAP tache, int heuresminimois_pres,
			 Date datedebut_pr, Date datefin_pr,Date redaction_pr) {
		super();
		this.id_prestationavenant = id_prestationavenant;
		this.prestation = prestation;
		this.tache = tache;
		this.heuresminimois_pres = heuresminimois_pres;
		this.redaction_pr = redaction_pr;
		this.datedebut_pr = datedebut_pr;
		this.datefin_pr = datefin_pr;
	}

	public AvenantPrestationCDI(int id_prestationavenant, int rangavenant,
			TachesSAP tache,int heuresminimois_pres,
			Date redaction_pr, Date datedebut_pr, Date datefin_pr) {
		super();
		this.id_prestationavenant = id_prestationavenant;
		this.rangavenant = rangavenant;
		this.tache = tache;
		this.heuresminimois_pres = heuresminimois_pres;
		this.redaction_pr = redaction_pr;
		this.datedebut_pr = datedebut_pr;
		this.datefin_pr = datefin_pr;
	}

	public AvenantPrestationCDI(int id_prestationavenant, int rangavenant,
			PrestationCDI prestation, TachesSAP tache, int heuresminimois_pres,
			Date datedebut_pr, Date datefin_pr,Date redaction_pr) {
		super();
		this.id_prestationavenant = id_prestationavenant;
		this.rangavenant = rangavenant;
		this.prestation = prestation;
		this.tache = tache;
		this.heuresminimois_pres = heuresminimois_pres;
		this.redaction_pr = redaction_pr;
		this.datedebut_pr = datedebut_pr;
		this.datefin_pr = datefin_pr;
	}

	public AvenantPrestationCDI(PrestationCDI prestation, 
			TachesSAP tache, int heuresminimois_pres, float salairehor_av,
			float panier_av, float deplacement_av,
			float facsalairehor_pr, String commentaire, Date redaction_pr,
			Date datedebut_pr, Date datefin_pr) {
		super();
		this.prestation = prestation;
		this.tache = tache;
		this.heuresminimois_pres = heuresminimois_pres;
		this.salairehor_av = salairehor_av;
		this.panier_av=panier_av;
		this.deplacement_av=deplacement_av;
		this.facsalairehor_pr = facsalairehor_pr;
		this.commentaire = commentaire;
		this.redaction_pr = redaction_pr;
		this.datedebut_pr = datedebut_pr;
		this.datefin_pr = datefin_pr;
	}

	public AvenantPrestationCDI(int id_prestationavenant, int rangavenant,
			PrestationCDI prestation, TachesSAP tache,
			int heuresminimois_pres, float salairehor_av,float panier_av,float deplacement_av,
			float facsalairehor_pr, String commentaire, Date redaction_pr,
			Date datedebut_pr, Date datefin_pr, Date daterenouvel,int heurescontrat) {
		super();
		this.id_prestationavenant = id_prestationavenant;
		this.rangavenant = rangavenant;
		this.prestation = prestation;
		this.tache = tache;
		this.heuresminimois_pres = heuresminimois_pres;
		this.salairehor_av = salairehor_av;
		this.panier_av=panier_av;
		this.deplacement_av=deplacement_av;
		this.facsalairehor_pr = facsalairehor_pr;
		this.commentaire = commentaire;
		this.redaction_pr = redaction_pr;
		this.datedebut_pr = datedebut_pr;
		this.datefin_pr = datefin_pr;
		this.daterenouvel = daterenouvel;
		this.heurescontrat=heurescontrat;
	}
	
	public AvenantPrestationCDI(int id_prestationavenant, int rangavenant,
			TachesSAP tache,int heuresminimois_pres,  Date redaction_pr,
			Date datedebut_pr, Date datefin_pr, Date daterenouvel) {
		super();
		this.id_prestationavenant = id_prestationavenant;
		this.rangavenant = rangavenant;
		this.tache = tache;
		this.heuresminimois_pres = heuresminimois_pres;
		this.redaction_pr = redaction_pr;
		this.datedebut_pr = datedebut_pr;
		this.datefin_pr = datefin_pr;
		this.daterenouvel = daterenouvel;
	}

	public AvenantPrestationCDI(int rangavenant, PrestationCDI prestation,
			TachesSAP tache, int heuresminimois_pres,
			float salairehor_av,float panier_av,float deplacement_av,
			float facsalairehor_pr, String commentaire,
			Date redaction_pr, Date datedebut_pr, Date datefin_pr,
			Date daterenouvel,int heurescontrat) {
		super();
		this.rangavenant = rangavenant;
		this.prestation = prestation;
		this.tache = tache;
		this.heuresminimois_pres = heuresminimois_pres;
		this.salairehor_av = salairehor_av;
		this.panier_av=panier_av;
		this.deplacement_av=deplacement_av;
		this.facsalairehor_pr = facsalairehor_pr;
		this.commentaire = commentaire;
		this.redaction_pr = redaction_pr;
		this.datedebut_pr = datedebut_pr;
		this.datefin_pr = datefin_pr;
		this.daterenouvel = daterenouvel;
		this.heurescontrat=heurescontrat;
	}

	public AvenantPrestationCDI(int id_prestationavenant, TachesSAP tache,
			int heuresminimois_pres, float salairehor_av, float facsalairehor_pr, 
			float panier_av,
			float deplacement_av, String commentaire,
			Date datefin_pr) {
		super();
		this.id_prestationavenant = id_prestationavenant;
		this.tache = tache;
		this.heuresminimois_pres = heuresminimois_pres;
		this.salairehor_av = salairehor_av;
		this.panier_av = panier_av;
		this.deplacement_av = deplacement_av;
		this.facsalairehor_pr = facsalairehor_pr;
		this.commentaire = commentaire;
		this.datefin_pr = datefin_pr;
	}

	public AvenantPrestationCDI(PrestationCDI prestation, TachesSAP tache,
			int heuresminimois_pres, float salairehor_av, float panier_av,
			float deplacement_av, float facsalairehor_pr, String commentaire,
			Date redaction_pr, Date datedebut_pr, Date datefin_pr,
			int heurescontrat) {
		super();
		this.prestation = prestation;
		this.tache = tache;
		this.heuresminimois_pres = heuresminimois_pres;
		this.salairehor_av = salairehor_av;
		this.panier_av = panier_av;
		this.deplacement_av = deplacement_av;
		this.facsalairehor_pr = facsalairehor_pr;
		this.commentaire = commentaire;
		this.redaction_pr = redaction_pr;
		this.datedebut_pr = datedebut_pr;
		this.datefin_pr = datefin_pr;
		this.heurescontrat = heurescontrat;
	}

	public AvenantPrestationCDI(int id_prestationavenant,
			PrestationCDI prestation, TachesSAP tache, int heuresminimois_pres,
			float salairehor_av, float panier_av, float deplacement_av,
			float facsalairehor_pr, String commentaire, Date datedebut_pr,
			int heurescontrat) {
		super();
		this.id_prestationavenant = id_prestationavenant;
		this.prestation = prestation;
		this.tache = tache;
		this.heuresminimois_pres = heuresminimois_pres;
		this.salairehor_av = salairehor_av;
		this.panier_av = panier_av;
		this.deplacement_av = deplacement_av;
		this.facsalairehor_pr = facsalairehor_pr;
		this.commentaire = commentaire;
		this.datedebut_pr = datedebut_pr;
		this.heurescontrat = heurescontrat;
	}
	
	
	

}
