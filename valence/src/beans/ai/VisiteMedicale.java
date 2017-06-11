package beans.ai;

import java.util.Date;

import beans.identite.Identite;

public class VisiteMedicale {
	private int id_suivi;
	private Identite identite;
	private Date convocation;
	private Date visite;
	private Date echeance;
	public int getId_suivi() {
		return id_suivi;
	}
	public void setId_suivi(int id_suivi) {
		this.id_suivi = id_suivi;
	}
	public Identite getIdentite() {
		return identite;
	}
	public void setIdentite(Identite identite) {
		this.identite = identite;
	}
	public Date getConvocation() {
		return convocation;
	}
	public void setConvocation(Date convocation) {
		this.convocation = convocation;
	}
	public Date getVisite() {
		return visite;
	}
	public void setVisite(Date visite) {
		this.visite = visite;
	}
	public Date getEcheance() {
		return echeance;
	}
	public void setEcheance(Date echeance) {
		this.echeance = echeance;
	}
	public VisiteMedicale(int id_suivi, Identite identite, Date convocation,
			Date visite, Date echeance) {
		super();
		this.id_suivi = id_suivi;
		this.identite = identite;
		this.convocation = convocation;
		this.visite = visite;
		this.echeance = echeance;
	}
	public VisiteMedicale() {
		super();
	}
	public VisiteMedicale(Identite identite, Date convocation, Date visite,
			Date echeance) {
		super();
		this.identite = identite;
		this.convocation = convocation;
		this.visite = visite;
		this.echeance = echeance;
	}
	public VisiteMedicale(int id_suivi, Date convocation, Date visite,
			Date echeance) {
		super();
		this.id_suivi = id_suivi;
		this.convocation = convocation;
		this.visite = visite;
		this.echeance = echeance;
	}
	public VisiteMedicale(int id_suivi, Identite identite, Date echeance) {
		super();
		this.id_suivi = id_suivi;
		this.identite = identite;
		this.echeance = echeance;
	}
	
	

}
