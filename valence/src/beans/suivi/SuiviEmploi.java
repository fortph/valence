package beans.suivi;

import java.util.Date;

import beans.employeurs.Employeur;
import beans.identite.Identite;
import beans.parametres.accueil.Rome;
import beans.parametres.capemploi.Utilisateur;

public class SuiviEmploi {
	private int idsuiviemploi;
	private Identite identite;
	private Date dateSuivi;
	private Utilisateur referent;
	private Employeur employeur;
	private Rome rome;
	private Date dateDebutSuivi;
	private Date dateFinSuivi;
	private String commentaires;
	public int getIdsuiviemploi() {
		return idsuiviemploi;
	}
	public void setIdsuiviemploi(int idsuiviemploi) {
		this.idsuiviemploi = idsuiviemploi;
	}
	public Identite getIdentite() {
		return identite;
	}
	public void setIdentite(Identite identite) {
		this.identite = identite;
	}
	public Date getDateSuivi() {
		return dateSuivi;
	}
	public void setDateSuivi(Date dateSuivi) {
		this.dateSuivi = dateSuivi;
	}
	public Utilisateur getReferent() {
		return referent;
	}
	public void setReferent(Utilisateur referent) {
		this.referent = referent;
	}
	public Employeur getEmployeur() {
		return employeur;
	}
	public void setEmployeur(Employeur employeur) {
		this.employeur = employeur;
	}
	public Rome getRome() {
		return rome;
	}
	public void setRome(Rome rome) {
		this.rome = rome;
	}
	public Date getDateDebutSuivi() {
		return dateDebutSuivi;
	}
	public void setDateDebutSuivi(Date dateDebutSuivi) {
		this.dateDebutSuivi = dateDebutSuivi;
	}
	public Date getDateFinSuivi() {
		return dateFinSuivi;
	}
	public void setDateFinSuivi(Date dateFinSuivi) {
		this.dateFinSuivi = dateFinSuivi;
	}
	
	public String getCommentaires() {
		return commentaires;
	}
	public void setCommentaires(String commentaires) {
		this.commentaires = commentaires;
	}
	public SuiviEmploi() {
		super();
	}
	public SuiviEmploi(int idsuiviemploi, Identite identite, Date dateSuivi,
			Utilisateur referent, Employeur employeur, Rome rome,
			Date dateDebutSuivi, Date dateFinSuivi,
			String commentaires) {
		super();
		this.idsuiviemploi = idsuiviemploi;
		this.identite = identite;
		this.dateSuivi = dateSuivi;
		this.referent = referent;
		this.employeur = employeur;
		this.rome = rome;
		this.dateDebutSuivi = dateDebutSuivi;
		this.dateFinSuivi = dateFinSuivi;		
		this.commentaires = commentaires;
	}
	public SuiviEmploi(Identite identite, Date dateSuivi, Utilisateur referent,
			Employeur employeur, Rome rome, Date dateDebutSuivi,
			Date dateFinSuivi, String commentaires) {
		super();
		this.identite = identite;
		this.dateSuivi = dateSuivi;
		this.referent = referent;
		this.employeur = employeur;
		this.rome = rome;
		this.dateDebutSuivi = dateDebutSuivi;
		this.dateFinSuivi = dateFinSuivi;		
		this.commentaires = commentaires;
	}
	
	
	
	
	
	
	

}
