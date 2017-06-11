package beans.employeurs;



import java.sql.Date;

import beans.parametres.capemploi.Utilisateur;

public class Suivi {
	private int id_suivi;
	private Utilisateur utilisateur;
	private Employeur employeur;
	private String commentaires;
	private Date jour;
	public int getId_suivi() {
		return id_suivi;
	}
	public void setId_suivi(int id_suivi) {
		this.id_suivi = id_suivi;
	}
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	public Employeur getEmployeur() {
		return employeur;
	}
	public void setEmployeur(Employeur employeur) {
		this.employeur = employeur;
	}
	public String getCommentaires() {
		return commentaires;
	}
	public void setCommentaires(String commentaires) {
		this.commentaires = commentaires;
	}
	public Date getJour() {
		return jour;
	}
	public void setJour(Date jour) {
		this.jour = jour;
	}
	public Suivi(int id_suivi, Utilisateur utilisateur, Employeur employeur,
			String commentaires, Date jour) {
		super();
		this.id_suivi = id_suivi;
		this.utilisateur = utilisateur;
		this.employeur = employeur;
		this.commentaires = commentaires;
		this.jour = jour;
	}
	public Suivi() {
		super();
	}
	public Suivi(Utilisateur utilisateur, Employeur employeur,
			String commentaires, Date jour) {
		super();
		this.utilisateur = utilisateur;
		this.employeur = employeur;
		this.commentaires = commentaires;
		this.jour = jour;
	}

	
	

}
