package beans.employeurs;

import java.util.Date;

import beans.identite.Identite;
import beans.parametres.capemploi.Utilisateur;

public class PositionnerPersonne {
	private int id_propo;
	private Offre offre;
	private Identite identite;
	private Date datecontact;
	private Utilisateur salarie;
	private String reponse;
	private boolean retenu;
	
	
	public PositionnerPersonne(int id_propo, Offre offre, Identite identite,
			Date datecontact, Utilisateur salarie, String reponse,
			boolean retenu) {
		super();
		this.id_propo = id_propo;
		this.offre = offre;
		this.identite = identite;
		this.datecontact = datecontact;
		this.salarie = salarie;
		this.reponse = reponse;
		this.retenu = retenu;
	}
	public PositionnerPersonne(Offre offre, Identite identite,
			Date datecontact, Utilisateur salarie, String reponse,
			boolean retenu) {
		super();
		this.offre = offre;
		this.identite = identite;
		this.datecontact = datecontact;
		this.salarie = salarie;
		this.reponse = reponse;
		this.retenu = retenu;
	}
	public PositionnerPersonne() {
		super();
	}
	public int getId_propo() {
		return id_propo;
	}
	public void setId_propo(int id_propo) {
		this.id_propo = id_propo;
	}
	public Offre getOffre() {
		return offre;
	}
	public void setOffre(Offre offre) {
		this.offre = offre;
	}
	public Identite getIdentite() {
		return identite;
	}
	public void setIdentite(Identite identite) {
		this.identite = identite;
	}
	public Date getDatecontact() {
		return datecontact;
	}
	public void setDatecontact(Date datecontact) {
		this.datecontact = datecontact;
	}
	public Utilisateur getSalarie() {
		return salarie;
	}
	public void setSalarie(Utilisateur salarie) {
		this.salarie = salarie;
	}
	public String getReponse() {
		return reponse;
	}
	public void setReponse(String reponse) {
		this.reponse = reponse;
	}
	public boolean isRetenu() {
		return retenu;
	}
	public void setRetenu(boolean retenu) {
		this.retenu = retenu;
	}
	
	
	

}
