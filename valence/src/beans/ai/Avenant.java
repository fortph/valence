package beans.ai;

import java.util.Date;

public class Avenant {
	private int idavenant;
	private Contrat contrat;
	private int naiav;
	private String n_aiavenant;
	private Date datedeb;
	private Date datefin;
	private float salairehoraire;
	private float facturation;
	private float panier;
	private float facturepanier;
	private float deplacement;
	private float facturedeplace;
	private float divers;
	private float facturedivers;
	private String commentaire;
	private String facturecommentaire;
	private Date redaction;
	private String dureehebdomadaire;
	private String tache;
	private String lieu;
	
	
	public Avenant(int idavenant, String n_aiavenant, Date datedeb,
			Date datefin, float facturation, Date redaction) {
		super();
		this.idavenant = idavenant;
		this.n_aiavenant = n_aiavenant;
		this.datedeb = datedeb;
		this.datefin = datefin;
		this.facturation = facturation;
		this.redaction = redaction;
	}
	public Avenant(Contrat contrat, Date datedeb, Date datefin,
			float salairehoraire, float facturation, float panier,
			float facturepanier, float deplacement, float facturedeplace,
			float divers, float facturedivers, String commentaire,
			String facturecommentaire, Date redaction,
			String dureehebdomadaire, String tache, String lieu) {
		super();
		this.contrat = contrat;
		this.datedeb = datedeb;
		this.datefin = datefin;
		this.salairehoraire = salairehoraire;
		this.facturation = facturation;
		this.panier = panier;
		this.facturepanier = facturepanier;
		this.deplacement = deplacement;
		this.facturedeplace = facturedeplace;
		this.divers = divers;
		this.facturedivers = facturedivers;
		this.commentaire = commentaire;
		this.facturecommentaire = facturecommentaire;
		this.redaction = redaction;
		this.dureehebdomadaire = dureehebdomadaire;
		this.tache = tache;
		this.lieu = lieu;
	}
	public Avenant(Contrat contrat, int naiav, String n_aiavenant,
			Date datedeb, Date datefin, float salairehoraire,
			float facturation, float panier, float facturepanier,
			float deplacement, float facturedeplace, float divers,
			float facturedivers, String commentaire, String facturecommentaire,
			Date redaction, String dureehebdomadaire, String tache, String lieu) {
		super();
		this.contrat = contrat;
		this.naiav = naiav;
		this.n_aiavenant = n_aiavenant;
		this.datedeb = datedeb;
		this.datefin = datefin;
		this.salairehoraire = salairehoraire;
		this.facturation = facturation;
		this.panier = panier;
		this.facturepanier = facturepanier;
		this.deplacement = deplacement;
		this.facturedeplace = facturedeplace;
		this.divers = divers;
		this.facturedivers = facturedivers;
		this.commentaire = commentaire;
		this.facturecommentaire = facturecommentaire;
		this.redaction = redaction;
		this.dureehebdomadaire = dureehebdomadaire;
		this.tache = tache;
		this.lieu = lieu;
	}
	public Avenant() {
		super();
	}
	public Avenant(int idavenant, Contrat contrat, int naiav,
			String n_aiavenant, Date datedeb, Date datefin,
			float salairehoraire, float facturation, float panier,
			float facturepanier, float deplacement, float facturedeplace,
			float divers, float facturedivers, String commentaire,
			String facturecommentaire, Date redaction,
			String dureehebdomadaire, String tache, String lieu) {
		super();
		this.idavenant = idavenant;
		this.contrat = contrat;
		this.naiav = naiav;
		this.n_aiavenant = n_aiavenant;
		this.datedeb = datedeb;
		this.datefin = datefin;
		this.salairehoraire = salairehoraire;
		this.facturation = facturation;
		this.panier = panier;
		this.facturepanier = facturepanier;
		this.deplacement = deplacement;
		this.facturedeplace = facturedeplace;
		this.divers = divers;
		this.facturedivers = facturedivers;
		this.commentaire = commentaire;
		this.facturecommentaire = facturecommentaire;
		this.redaction = redaction;
		this.dureehebdomadaire = dureehebdomadaire;
		this.tache = tache;
		this.lieu = lieu;
	}
	public int getIdavenant() {
		return idavenant;
	}
	public void setIdavenant(int idavenant) {
		this.idavenant = idavenant;
	}
	public Contrat getContrat() {
		return contrat;
	}
	public void setContrat(Contrat contrat) {
		this.contrat = contrat;
	}
	public int getNaiav() {
		return naiav;
	}
	public void setNaiav(int naiav) {
		this.naiav = naiav;
	}
	public String getN_aiavenant() {
		return n_aiavenant;
	}
	public void setN_aiavenant(String n_aiavenant) {
		this.n_aiavenant = n_aiavenant;
	}
	public Date getDatedeb() {
		return datedeb;
	}
	public void setDatedeb(Date datedeb) {
		this.datedeb = datedeb;
	}
	public Date getDatefin() {
		return datefin;
	}
	public void setDatefin(Date datefin) {
		this.datefin = datefin;
	}
	public float getSalairehoraire() {
		return salairehoraire;
	}
	public void setSalairehoraire(float salairehoraire) {
		this.salairehoraire = salairehoraire;
	}
	public float getFacturation() {
		return facturation;
	}
	public void setFacturation(float facturation) {
		this.facturation = facturation;
	}
	public float getPanier() {
		return panier;
	}
	public void setPanier(float panier) {
		this.panier = panier;
	}
	public float getFacturepanier() {
		return facturepanier;
	}
	public void setFacturepanier(float facturepanier) {
		this.facturepanier = facturepanier;
	}
	public float getDeplacement() {
		return deplacement;
	}
	public void setDeplacement(float deplacement) {
		this.deplacement = deplacement;
	}
	public float getFacturedeplace() {
		return facturedeplace;
	}
	public void setFacturedeplace(float facturedeplace) {
		this.facturedeplace = facturedeplace;
	}
	public float getDivers() {
		return divers;
	}
	public void setDivers(float divers) {
		this.divers = divers;
	}
	public float getFacturedivers() {
		return facturedivers;
	}
	public void setFacturedivers(float facturedivers) {
		this.facturedivers = facturedivers;
	}
	public String getCommentaire() {
		return commentaire;
	}
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	public String getFacturecommentaire() {
		return facturecommentaire;
	}
	public void setFacturecommentaire(String facturecommentaire) {
		this.facturecommentaire = facturecommentaire;
	}
	public Date getRedaction() {
		return redaction;
	}
	public void setRedaction(Date redaction) {
		this.redaction = redaction;
	}
	public String getDureehebdomadaire() {
		return dureehebdomadaire;
	}
	public void setDureehebdomadaire(String dureehebdomadaire) {
		this.dureehebdomadaire = dureehebdomadaire;
	}
	public String getTache() {
		return tache;
	}
	public void setTache(String tache) {
		this.tache = tache;
	}
	public String getLieu() {
		return lieu;
	}
	public void setLieu(String lieu) {
		this.lieu = lieu;
	}
	public Avenant(int idavenant, Contrat contrat, Date datedeb, Date datefin) {
		super();
		this.idavenant = idavenant;
		this.contrat = contrat;
		this.datedeb = datedeb;
		this.datefin = datefin;
	}
	public Avenant(int idavenant, Contrat contrat, Date datefin,
			float salairehoraire, float panier, float deplacement,
			float divers, String commentaire) {
		super();
		this.idavenant = idavenant;
		this.contrat = contrat;
		this.datefin = datefin;
		this.salairehoraire = salairehoraire;
		this.panier = panier;
		this.deplacement = deplacement;
		this.divers = divers;
		this.commentaire = commentaire;
	}
	
	
	
	
	

}
