package beans.formationpro;

import java.util.Date;

import beans.identite.Identite;
import beans.parametres.formationpro.Formprofinancement;
import beans.parametres.formationpro.Formproniveau;
import beans.parametres.formationpro.Formprostatutemployeur;
import beans.parametres.formationpro.Formprotheme;

public class FormationProInscription {
	
	private int id_inscription;
	private Formprotheme theme;
	private Formproniveau niveau;
	private int nbheures;
	private Formprostatutemployeur statut;
	private Date debutformation;
	private Formprofinancement financement;
	private FormationProEmployeur employeur;
	private Identite identite;
	private float montant;
	private Date enregistrement;
	public FormationProInscription(int id_inscription, Formprotheme theme,
			Formproniveau niveau, int nbheures, Formprostatutemployeur statut,
			Date debutformation, Formprofinancement financement,
			FormationProEmployeur employeur, Identite identite, float montant,
			Date enregistrement) {
		super();
		this.id_inscription = id_inscription;
		this.theme = theme;
		this.niveau = niveau;
		this.nbheures = nbheures;
		this.statut = statut;
		this.debutformation = debutformation;
		this.financement = financement;
		this.employeur = employeur;
		this.identite = identite;
		this.montant = montant;
		this.enregistrement = enregistrement;
	}
	public FormationProInscription(Formprotheme theme, Formproniveau niveau,
			int nbheures, Formprostatutemployeur statut, Date debutformation,
			Formprofinancement financement, FormationProEmployeur employeur,
			Identite identite, float montant, Date enregistrement) {
		super();
		this.theme = theme;
		this.niveau = niveau;
		this.nbheures = nbheures;
		this.statut = statut;
		this.debutformation = debutformation;
		this.financement = financement;
		this.employeur = employeur;
		this.identite = identite;
		this.montant = montant;
		this.enregistrement = enregistrement;
	}
	
	
	
	
	public FormationProInscription(int id_inscription, Formprotheme theme,
			Formproniveau niveau, int nbheures, Formprostatutemployeur statut,
			Date debutformation, Formprofinancement financement,
			FormationProEmployeur employeur, float montant, Date enregistrement) {
		super();
		this.id_inscription = id_inscription;
		this.theme = theme;
		this.niveau = niveau;
		this.nbheures = nbheures;
		this.statut = statut;
		this.debutformation = debutformation;
		this.financement = financement;
		this.employeur = employeur;
		this.montant = montant;
		this.enregistrement = enregistrement;
	}
	public FormationProInscription() {
		super();
	}
	public int getId_inscription() {
		return id_inscription;
	}
	public void setId_inscription(int id_inscription) {
		this.id_inscription = id_inscription;
	}
	public Formprotheme getTheme() {
		return theme;
	}
	public void setTheme(Formprotheme theme) {
		this.theme = theme;
	}
	public Formproniveau getNiveau() {
		return niveau;
	}
	public void setNiveau(Formproniveau niveau) {
		this.niveau = niveau;
	}
	public int getNbheures() {
		return nbheures;
	}
	public void setNbheures(int nbheures) {
		this.nbheures = nbheures;
	}
	public Formprostatutemployeur getStatut() {
		return statut;
	}
	public void setStatut(Formprostatutemployeur statut) {
		this.statut = statut;
	}
	public Date getDebutformation() {
		return debutformation;
	}
	public void setDebutformation(Date debutformation) {
		this.debutformation = debutformation;
	}
	public Formprofinancement getFinancement() {
		return financement;
	}
	public void setFinancement(Formprofinancement financement) {
		this.financement = financement;
	}
	public FormationProEmployeur getEmployeur() {
		return employeur;
	}
	public void setEmployeur(FormationProEmployeur employeur) {
		this.employeur = employeur;
	}
	public Identite getIdentite() {
		return identite;
	}
	public void setIdentite(Identite identite) {
		this.identite = identite;
	}
	public float getMontant() {
		return montant;
	}
	public void setMontant(float montant) {
		this.montant = montant;
	}
	public Date getEnregistrement() {
		return enregistrement;
	}
	public void setEnregistrement(Date enregistrement) {
		this.enregistrement = enregistrement;
	}
	
	
	
	
	
	
}
	