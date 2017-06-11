package beans.rth;

import java.util.Date;

import beans.identite.Identite;

public class FicheRTH {
	private int id_rth;
	private Identite identite;
	private String categorie_rth;
	private int taux_rth;
	private boolean pension_rth;
	private String montant;
	private String ci_rth;
	private String referent_rth;
	private Date creation_rth;
	public int getId_rth() {
		return id_rth;
	}
	public void setId_rth(int id_rth) {
		this.id_rth = id_rth;
	}
	public Identite getIdentite() {
		return identite;
	}
	public void setIdentite(Identite identite) {
		this.identite = identite;
	}
	public String getCategorie_rth() {
		return categorie_rth;
	}
	public void setCategorie_rth(String categorie_rth) {
		this.categorie_rth = categorie_rth;
	}
	public int getTaux_rth() {
		return taux_rth;
	}
	public void setTaux_rth(int taux_rth) {
		this.taux_rth = taux_rth;
	}
	public boolean isPension_rth() {
		return pension_rth;
	}
	public void setPension_rth(boolean pension_rth) {
		this.pension_rth = pension_rth;
	}
	public String getMontant() {
		return montant;
	}
	public void setMontant(String montant) {
		this.montant = montant;
	}
	public String getCi_rth() {
		return ci_rth;
	}
	public void setCi_rth(String ci_rth) {
		this.ci_rth = ci_rth;
	}
	public String getReferent_rth() {
		return referent_rth;
	}
	public void setReferent_rth(String referent_rth) {
		this.referent_rth = referent_rth;
	}
	public Date getCreation_rth() {
		return creation_rth;
	}
	public void setCreation_rth(Date creation_rth) {
		this.creation_rth = creation_rth;
	}
	public FicheRTH(int id_rth, Identite identite, String categorie_rth,
			int taux_rth, boolean pension_rth, String montant, String ci_rth,
			String referent_rth, Date creation_rth) {
		super();
		this.id_rth = id_rth;
		this.identite = identite;
		this.categorie_rth = categorie_rth;
		this.taux_rth = taux_rth;
		this.pension_rth = pension_rth;
		this.montant = montant;
		this.ci_rth = ci_rth;
		this.referent_rth = referent_rth;
		this.creation_rth = creation_rth;
	}
	public FicheRTH() {
		super();
	}
	public FicheRTH(Identite identite, String categorie_rth, int taux_rth,
			boolean pension_rth, String montant, String ci_rth,
			String referent_rth, Date creation_rth) {
		super();
		this.identite = identite;
		this.categorie_rth = categorie_rth;
		this.taux_rth = taux_rth;
		this.pension_rth = pension_rth;
		this.montant = montant;
		this.ci_rth = ci_rth;
		this.referent_rth = referent_rth;
		this.creation_rth = creation_rth;
	}
	
	
}