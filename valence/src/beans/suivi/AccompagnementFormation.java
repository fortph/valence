package beans.suivi;
/*créé le 10/9/2015*/


import java.util.Date;

import beans.formation.Animatrice;
import beans.identite.Identite;

public class AccompagnementFormation {
	private int id_accomp;
	private Identite identite;
	private Date dateredaction;
	private Animatrice referent;
	private String demande;	
	private String commentaire;

	public Identite getIdentite() {
		return identite;
	}

	public void setIdentite(Identite identite) {
		this.identite = identite;
	}

	public Date getDateredaction() {
		return dateredaction;
	}

	public void setDateredaction(Date dateredaction) {
		this.dateredaction = dateredaction;
	}

	public Animatrice getReferent() {
		return referent;
	}

	public void setReferent(Animatrice referent) {
		this.referent = referent;
	}

	public String getDemande() {
		return demande;
	}

	public void setDemande(String demande) {
		this.demande = demande;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public int getId_accomp() {
		return id_accomp;
	}

	public AccompagnementFormation() {
		super();
	}

	public AccompagnementFormation(Identite identite, Date dateredaction, Animatrice referent, String demande,
			String commentaire) {
		super();
		this.identite = identite;
		this.dateredaction = dateredaction;
		this.referent = referent;
		this.demande = demande;
		this.commentaire = commentaire;
	}

	public AccompagnementFormation(int id_accomp, Identite identite, Date dateredaction, Animatrice referent,
			String demande, String commentaire) {
		super();
		this.id_accomp = id_accomp;
		this.identite = identite;
		this.dateredaction = dateredaction;
		this.referent = referent;
		this.demande = demande;
		this.commentaire = commentaire;
	}

	public AccompagnementFormation(Date dateredaction, Animatrice referent) {
		super();
		this.dateredaction = dateredaction;
		this.referent = referent;
	}
	

}
