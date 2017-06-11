package beans.suivi;
/* créé le 10/9/2015*/

import java.util.Date;

import beans.formation.Animatrice;
import beans.identite.Identite;
import beans.parametres.accueil.TypeFormations;

public class SuiviFormation {
	private int idsuivi;
	private Identite identite;
	private Date dateSuivi;
	private Animatrice referent;
	private String formation;
	private Date dateDebutFormation;
	private Date dateFinFormation;
	private String of;
	private TypeFormations typeFormations;
	private String commentaires;

	

	public SuiviFormation(Identite identite, Date dateSuivi, Animatrice referent, String formation,
			Date dateDebutFormation, Date dateFinFormation, String of, TypeFormations typeFormations,
			String commentaires) {
		super();
		this.identite = identite;
		this.dateSuivi = dateSuivi;
		this.referent = referent;
		this.formation = formation;
		this.dateDebutFormation = dateDebutFormation;
		this.dateFinFormation = dateFinFormation;
		this.of = of;
		this.typeFormations = typeFormations;
		this.commentaires = commentaires;
	}

	public SuiviFormation(int idsuivi, Identite identite, Date dateSuivi, Animatrice referent, String formation,
			Date dateDebutFormation, Date dateFinFormation, String of, TypeFormations typeFormations,
			String commentaires) {
		super();
		this.idsuivi = idsuivi;
		this.identite = identite;
		this.dateSuivi = dateSuivi;
		this.referent = referent;
		this.formation = formation;
		this.dateDebutFormation = dateDebutFormation;
		this.dateFinFormation = dateFinFormation;
		this.of = of;
		this.typeFormations = typeFormations;
		this.commentaires = commentaires;
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

	public Animatrice getReferent() {
		return referent;
	}

	public void setReferent(Animatrice referent) {
		this.referent = referent;
	}

	public String getFormation() {
		return formation;
	}

	public void setFormation(String formation) {
		this.formation = formation;
	}

	public Date getDateDebutFormation() {
		return dateDebutFormation;
	}

	public void setDateDebutFormation(Date dateDebutFormation) {
		this.dateDebutFormation = dateDebutFormation;
	}

	public Date getDateFinFormation() {
		return dateFinFormation;
	}

	public void setDateFinFormation(Date dateFinFormation) {
		this.dateFinFormation = dateFinFormation;
	}

	public String getOf() {
		return of;
	}

	public void setOf(String of) {
		this.of = of;
	}

	public TypeFormations getTypeFormations() {
		return typeFormations;
	}

	public void setTypeFormations(TypeFormations typeFormations) {
		this.typeFormations = typeFormations;
	}

	public String getCommentaires() {
		return commentaires;
	}

	public void setCommentaires(String commentaires) {
		this.commentaires = commentaires;
	}

	public int getIdsuivi() {
		return idsuivi;
	}
}

