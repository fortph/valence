package beans.suivi;

import java.util.Date;

import beans.identite.Identite;
import beans.parametres.capemploi.Utilisateur;

public class SuiviPersonne {
	private int id_suivi;
	private Identite identite;
	private Date datesuivi;
	private Utilisateur referent;
	private String commentaire;
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
	public Date getDatesuivi() {
		return datesuivi;
	}
	public void setDatesuivi(Date datesuivi) {
		this.datesuivi = datesuivi;
	}
	public Utilisateur getReferent() {
		return referent;
	}
	public void setReferent(Utilisateur referent) {
		this.referent = referent;
	}
	public String getCommentaire() {
		return commentaire;
	}
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	public SuiviPersonne() {
		super();
	}
	public SuiviPersonne(int id_suivi, Identite identite, Date datesuivi,
			Utilisateur referent, String commentaire) {
		super();
		this.id_suivi = id_suivi;
		this.identite = identite;
		this.datesuivi = datesuivi;
		this.referent = referent;
		this.commentaire = commentaire;
	}
	public SuiviPersonne(Identite identite, Date datesuivi, Utilisateur referent,
			String commentaire) {
		super();
		this.identite = identite;
		this.datesuivi = datesuivi;
		this.referent = referent;
		this.commentaire = commentaire;
	}
	public SuiviPersonne(Date datesuivi, Utilisateur referent) {
		super();
		this.datesuivi = datesuivi;
		this.referent = referent;
	}
	
	
	

}
