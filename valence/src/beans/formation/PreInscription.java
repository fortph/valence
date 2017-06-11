package beans.formation;

import java.util.Date;

import beans.identite.Identite;

public class PreInscription {
	private int id_formation;
	private Identite identite;
	private Date date_pyramide;
	private String tempsacc_pyramide;
	private Animatrice animatrice;
	private String commentaires;
	private String rais_abandon;	
	private Date dateAbandon;
	private String prescripteur;
	private String interlocuteur;
	private ListeFormations liste;
	private boolean inscrit;
	private boolean enregistre;
	

	public String getRais_abandon() {
		return rais_abandon;
	}

	public void setRais_abandon(String rais_abandon) {
		this.rais_abandon = rais_abandon;
	}

	public Date getDateAbandon() {
		return dateAbandon;
	}

	public void setDateAbandon(Date dateAbandon) {
		this.dateAbandon = dateAbandon;
	}

	public String getPrescripteur() {
		return prescripteur;
	}

	public void setPrescripteur(String prescripteur) {
		this.prescripteur = prescripteur;
	}

	public String getInterlocuteur() {
		return interlocuteur;
	}

	public void setInterlocuteur(String interlocuteur) {
		this.interlocuteur = interlocuteur;
	}

	public boolean isInscrit() {
		return inscrit;
	}

	public void setInscrit(boolean inscrit) {
		this.inscrit = inscrit;
	}

	public ListeFormations getListe() {
		return liste;
	}

	public void setListe(ListeFormations liste) {
		this.liste = liste;
	}

	public String getCommentaires() {
		return commentaires;
	}

	public void setCommentaires(String commentaires) {
		this.commentaires = commentaires;
	}

	public boolean isEnregistre() {
		return enregistre;
	}

	public void setEnregistre(boolean enregistre) {
		this.enregistre = enregistre;
	}

	

	public int getId_formation() {
		return id_formation;
	}

	public void setId_formation(int id_formation) {
		this.id_formation = id_formation;
	}

	public Identite getIdentite() {
		return identite;
	}

	public void setIdentite(Identite identite) {
		this.identite = identite;
	}

	public Date getDate_pyramide() {
		return date_pyramide;
	}

	public void setDate_pyramide(Date date_pyramide) {
		this.date_pyramide = date_pyramide;
	}

	
	public String getTempsacc_pyramide() {
		return tempsacc_pyramide;
	}

	public void setTempsacc_pyramide(String tempsacc_pyramide) {
		this.tempsacc_pyramide = tempsacc_pyramide;
	}

	public Animatrice getAnimatrice() {
		return animatrice;
	}

	public void setAnimatrice(Animatrice animatrice) {
		this.animatrice = animatrice;
	}

	public PreInscription(Identite identite, Date date_pyramide,
			String tempsacc_pyramide, Animatrice animatrice,
			String commentaires, String rais_abandon, Date dateAbandon,
			String prescripteur, String interlocuteur, ListeFormations liste,
			boolean inscrit, boolean enregistre) {
		super();
		this.identite = identite;
		this.date_pyramide = date_pyramide;
		this.tempsacc_pyramide = tempsacc_pyramide;
		this.animatrice = animatrice;
		this.commentaires = commentaires;
		this.rais_abandon = rais_abandon;
		this.dateAbandon = dateAbandon;
		this.prescripteur = prescripteur;
		this.interlocuteur = interlocuteur;
		this.liste = liste;
		this.inscrit = inscrit;
		this.enregistre = enregistre;
	}

	

	public PreInscription(Identite identite, Date date_pyramide,
			String tempsacc_pyramide, Animatrice animatrice,
			String commentaires, String prescripteur, ListeFormations liste,
			boolean enregistre) {
		super();
		this.identite = identite;
		this.date_pyramide = date_pyramide;
		this.tempsacc_pyramide = tempsacc_pyramide;
		this.animatrice = animatrice;
		this.commentaires = commentaires;
		this.prescripteur = prescripteur;
		this.liste = liste;
		this.enregistre = enregistre;
	}

	public PreInscription(int id_formation, Identite identite,
			Date date_pyramide, String tempsacc_pyramide,
			Animatrice animatrice, String commentaires, String rais_abandon,
			Date dateAbandon, String prescripteur, String interlocuteur,
			ListeFormations liste, boolean inscrit, boolean enregistre) {
		super();
		this.id_formation = id_formation;
		this.identite = identite;
		this.date_pyramide = date_pyramide;
		this.tempsacc_pyramide = tempsacc_pyramide;
		this.animatrice = animatrice;
		this.commentaires = commentaires;
		this.rais_abandon = rais_abandon;
		this.dateAbandon = dateAbandon;
		this.prescripteur = prescripteur;
		this.interlocuteur = interlocuteur;
		this.liste = liste;
		this.inscrit = inscrit;
		this.enregistre = enregistre;
	}
	
	public PreInscription() {
		super();
	}

	public PreInscription(int id_formation, Date date_pyramide,
			String tempsacc_pyramide, Animatrice animatrice,
			String commentaires, String rais_abandon, Date dateAbandon,
			String prescripteur, String interlocuteur, ListeFormations liste,
			boolean inscrit, boolean enregistre) {
		super();
		this.id_formation = id_formation;
		this.date_pyramide = date_pyramide;
		this.tempsacc_pyramide = tempsacc_pyramide;
		this.animatrice = animatrice;
		this.commentaires = commentaires;
		this.rais_abandon = rais_abandon;
		this.dateAbandon = dateAbandon;
		this.prescripteur = prescripteur;
		this.interlocuteur = interlocuteur;
		this.liste = liste;
		this.inscrit = inscrit;
		this.enregistre = enregistre;
	}

	public PreInscription(int id_formation, Identite identite,
			String commentaires, String interlocuteur, ListeFormations liste,
			boolean inscrit) {
		super();
		this.id_formation = id_formation;
		this.identite = identite;
		this.commentaires = commentaires;
		this.interlocuteur = interlocuteur;
		this.liste = liste;
		this.inscrit = inscrit;
	}

	public PreInscription(Date date_pyramide, Animatrice animatrice,
			String commentaires) {
		super();
		this.date_pyramide = date_pyramide;
		this.animatrice = animatrice;
		this.commentaires = commentaires;
	}

	public PreInscription(Date dateAbandon,String rais_abandon) {
		super();
		this.rais_abandon = rais_abandon;
		this.dateAbandon = dateAbandon;
	}

	public PreInscription(Date dateAbandon,String rais_abandon, 
			ListeFormations liste) {
		super();
		this.dateAbandon = dateAbandon;
		this.rais_abandon = rais_abandon;		
		this.liste = liste;
	}

	

	


}