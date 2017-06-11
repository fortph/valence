package beans.ai;

import java.util.Date;

import beans.employeurs.Employeur;
import beans.employeurs.Service;
import beans.identite.Identite;
import beans.parametres.accueil.Rome;

public class Contrat {
	private int idaicontrat;
	private Identite identite;
	private Employeur employeur;
	private Service service;
	private Rome rome;
	private String tache;
	private String lieu;
	private Date debutcontrat;
	private Date fincontrat;
	private String heuresminihebdo;
	private float salairehoraire;
	private float facturation;
	private float panier;
	private float facturepanier;
	private float deplacement;
	private float facturedeplace;
	private float divers;
	private float facturedivers;
	private String commentaire;
	private String facturecommentaire;
	private Date redaction;
	private int numerocontrat;
	private String urssaf;
	private String fintache;
	private boolean annule;
	
	
	public int getIdaicontrat() {
		return idaicontrat;
	}
	public void setIdaicontrat(int idaicontrat) {
		this.idaicontrat = idaicontrat;
	}
	
	public Identite getIdentite() {
		return identite;
	}
	public void setIdentite(Identite identite) {
		this.identite = identite;
	}
	public Employeur getEmployeur() {
		return employeur;
	}
	public void setEmployeur(Employeur employeur) {
		this.employeur = employeur;
	}
	public Service getService() {
		return service;
	}
	public void setService(Service service) {
		this.service = service;
	}
	public Rome getRome() {
		return rome;
	}
	public void setRome(Rome rome) {
		this.rome = rome;
	}
	public String getTache() {
		return tache;
	}
	public void setTache(String tache) {
		this.tache = tache;
	}
	public String getLieu() {
		return lieu;
	}
	public void setLieu(String lieu) {
		this.lieu = lieu;
	}
	public Date getDebutcontrat() {
		return debutcontrat;
	}
	public void setDebutcontrat(Date debutcontrat) {
		this.debutcontrat = debutcontrat;
	}
	public Date getFincontrat() {
		return fincontrat;
	}
	public void setFincontrat(Date fincontrat) {
		this.fincontrat = fincontrat;
	}
	
	public String getHeuresminihebdo() {
		return heuresminihebdo;
	}
	public void setHeuresminihebdo(String heuresminihebdo) {
		this.heuresminihebdo = heuresminihebdo;
	}
	public float getSalairehoraire() {
		return salairehoraire;
	}
	public void setSalairehoraire(float salairehoraire) {
		this.salairehoraire = salairehoraire;
	}
	public float getFacturation() {
		return facturation;
	}
	public void setFacturation(float facturation) {
		this.facturation = facturation;
	}
	public float getPanier() {
		return panier;
	}
	public void setPanier(float panier) {
		this.panier = panier;
	}
	public float getFacturepanier() {
		return facturepanier;
	}
	public void setFacturepanier(float facturepanier) {
		this.facturepanier = facturepanier;
	}
	public float getDeplacement() {
		return deplacement;
	}
	public void setDeplacement(float deplacement) {
		this.deplacement = deplacement;
	}
	public float getFacturedeplace() {
		return facturedeplace;
	}
	public void setFacturedeplace(float facturedeplace) {
		this.facturedeplace = facturedeplace;
	}
	public float getDivers() {
		return divers;
	}
	public void setDivers(float divers) {
		this.divers = divers;
	}
	public float getFacturedivers() {
		return facturedivers;
	}
	public void setFacturedivers(float facturedivers) {
		this.facturedivers = facturedivers;
	}
	public String getCommentaire() {
		return commentaire;
	}
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	public String getFacturecommentaire() {
		return facturecommentaire;
	}
	public void setFacturecommentaire(String facturecommentaire) {
		this.facturecommentaire = facturecommentaire;
	}
	public Date getRedaction() {
		return redaction;
	}
	public void setRedaction(Date redaction) {
		this.redaction = redaction;
	}
	public int getNumerocontrat() {
		return numerocontrat;
	}
	public void setNumerocontrat(int numerocontrat) {
		this.numerocontrat = numerocontrat;
	}
	public String getUrssaf() {
		return urssaf;
	}
	public void setUrssaf(String urssaf) {
		this.urssaf = urssaf;
	}
	
	public String getFintache() {
		return fintache;
	}
	public void setFintache(String fintache) {
		this.fintache = fintache;
	}
	public boolean isAnnule() {
		return annule;
	}
	public void setAnnule(boolean annule) {
		this.annule = annule;
	}
	public Contrat(int idaicontrat, Identite identite,
			Employeur employeur, Service service, Rome rome, String tache,
			String lieu, Date debutcontrat, Date fincontrat,
			String heuresminihebdo, float salairehoraire, float facturation,
			float panier, float facturepanier, float deplacement,
			float facturedeplace, float divers, float facturedivers,
			String commentaire, String facturecommentaire, Date redaction,
			int numerocontrat, String urssaf, 
			String fintache, boolean annule) {
		super();
		this.idaicontrat = idaicontrat;
		this.identite = identite;
		this.employeur = employeur;
		this.service = service;
		this.rome = rome;
		this.tache = tache;
		this.lieu = lieu;
		this.debutcontrat = debutcontrat;
		this.fincontrat = fincontrat;
		this.heuresminihebdo = heuresminihebdo;
		this.salairehoraire = salairehoraire;
		this.facturation = facturation;
		this.panier = panier;
		this.facturepanier = facturepanier;
		this.deplacement = deplacement;
		this.facturedeplace = facturedeplace;
		this.divers = divers;
		this.facturedivers = facturedivers;
		this.commentaire = commentaire;
		this.facturecommentaire = facturecommentaire;
		this.redaction = redaction;
		this.numerocontrat = numerocontrat;
		this.urssaf = urssaf;
		this.fintache = fintache;
		this.annule = annule;
	}
	public Contrat() {
		super();
	}
	public Contrat( Identite identite, Employeur employeur,
			Service service, Rome rome, String tache, String lieu,
			Date debutcontrat, Date fincontrat, String heuresminihebdo,
			float salairehoraire, float facturation, float panier,
			float facturepanier, float deplacement, float facturedeplace,
			float divers, float facturedivers, String commentaire,
			String facturecommentaire, Date redaction, int numerocontrat,
			String urssaf, String fintache, boolean annule) {
		super();
		this.identite = identite;
		this.employeur = employeur;
		this.service = service;
		this.rome = rome;
		this.tache = tache;
		this.lieu = lieu;
		this.debutcontrat = debutcontrat;
		this.fincontrat = fincontrat;
		this.heuresminihebdo = heuresminihebdo;
		this.salairehoraire = salairehoraire;
		this.facturation = facturation;
		this.panier = panier;
		this.facturepanier = facturepanier;
		this.deplacement = deplacement;
		this.facturedeplace = facturedeplace;
		this.divers = divers;
		this.facturedivers = facturedivers;
		this.commentaire = commentaire;
		this.facturecommentaire = facturecommentaire;
		this.redaction = redaction;
		this.numerocontrat = numerocontrat;
		this.urssaf = urssaf;
		this.fintache = fintache;
		this.annule = annule;
	}
	public Contrat(int idaicontrat, int numerocontrat, Employeur employeur, 
			Date debutcontrat, Date fincontrat,Rome rome) {
		super();
		this.idaicontrat = idaicontrat;
		this.employeur = employeur;
		this.rome = rome;
		this.debutcontrat = debutcontrat;
		this.fincontrat = fincontrat;
		this.numerocontrat = numerocontrat;
	}
	public Contrat(Identite identite, Employeur employeur, Service service,
			Rome rome, String tache, String lieu, Date debutcontrat,
			Date fincontrat, String heuresminihebdo, float salairehoraire,
			float facturation, float panier, float facturepanier,
			float deplacement, float facturedeplace, float divers,
			float facturedivers, String commentaire, String facturecommentaire,
			Date redaction, String urssaf, String fintache,
			boolean annule) {
		super();
		this.identite = identite;
		this.employeur = employeur;
		this.service = service;
		this.rome = rome;
		this.tache = tache;
		this.lieu = lieu;
		this.debutcontrat = debutcontrat;
		this.fincontrat = fincontrat;
		this.heuresminihebdo = heuresminihebdo;
		this.salairehoraire = salairehoraire;
		this.facturation = facturation;
		this.panier = panier;
		this.facturepanier = facturepanier;
		this.deplacement = deplacement;
		this.facturedeplace = facturedeplace;
		this.divers = divers;
		this.facturedivers = facturedivers;
		this.commentaire = commentaire;
		this.facturecommentaire = facturecommentaire;
		this.redaction = redaction;
		this.urssaf = urssaf;
		this.fintache = fintache;
		this.annule = annule;
	}
	public Contrat(int idaicontrat, Identite identite, Employeur employeur,
			Rome rome, Date debutcontrat, Date fincontrat) {
		super();
		this.idaicontrat = idaicontrat;
		this.identite = identite;
		this.employeur = employeur;
		this.rome = rome;
		this.debutcontrat = debutcontrat;
		this.fincontrat = fincontrat;
	}
	public Contrat(int idaicontrat, Identite identite, Employeur employeur,
			Service service, Rome rome, Date fincontrat) {
		super();
		this.idaicontrat = idaicontrat;
		this.identite = identite;
		this.employeur = employeur;
		this.service = service;
		this.rome = rome;
		this.fincontrat = fincontrat;
	}
	public Contrat(int idaicontrat, Identite identite, Employeur employeur,
			Service service, Rome rome,Date debutcontrat, Date fincontrat, String tache, String lieu
			) {
		super();
		this.idaicontrat = idaicontrat;
		this.identite = identite;
		this.employeur = employeur;
		this.service = service;
		this.rome = rome;
		this.debutcontrat = debutcontrat;
		this.fincontrat = fincontrat;
		this.tache = tache;
		this.lieu = lieu;
		
	}
	public Contrat(int idaicontrat, Identite identite, Employeur employeur,
			Service service, Rome rome, String tache, String lieu,
			Date debutcontrat, Date fincontrat, float salairehoraire,
			float panier, float deplacement, float divers, String commentaire) {
		super();
		this.idaicontrat = idaicontrat;
		this.identite = identite;
		this.employeur = employeur;
		this.service = service;
		this.rome = rome;
		this.tache = tache;
		this.lieu = lieu;
		this.debutcontrat = debutcontrat;
		this.fincontrat = fincontrat;
		this.salairehoraire = salairehoraire;
		this.panier = panier;
		this.deplacement = deplacement;
		this.divers = divers;
		this.commentaire = commentaire;
	}
	
	
	public Contrat(int idaicontrat,int numerocontrat, Identite identite ,
			Date debutcontrat, Date fincontrat,Rome rome) {
		super();
		this.idaicontrat = idaicontrat;
		this.identite = identite;
		this.rome = rome;
		this.debutcontrat = debutcontrat;
		this.fincontrat = fincontrat;
		this.numerocontrat = numerocontrat;
	}
	
	
	
	
	
	

}
