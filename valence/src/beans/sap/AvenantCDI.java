package beans.sap;

import java.util.Date;

public class AvenantCDI {
	private int id_avenant;
	private int rangavenent;
	private ContratCDI contrat;
	private String tache;
	private int heuresminimois;
	private float salairehoraire;	
	private float panier;	
	private float deplacement;	
	private String commentaire;	
	private Date redaction;
	private String  urssaf;
	private Date dateeffet;
	
	
	
	public int getId_avenant() {
		return id_avenant;
	}



	public void setId_avenant(int id_avenant) {
		this.id_avenant = id_avenant;
	}



	public int getRangavenent() {
		return rangavenent;
	}



	public void setRangavenent(int rangavenent) {
		this.rangavenent = rangavenent;
	}



	public ContratCDI getContrat() {
		return contrat;
	}



	public void setContrat(ContratCDI contrat) {
		this.contrat = contrat;
	}



	public String getTache() {
		return tache;
	}



	public void setTache(String tache) {
		this.tache = tache;
	}



	public int getHeuresminimois() {
		return heuresminimois;
	}



	public void setHeuresminimois(int heuresminimois) {
		this.heuresminimois = heuresminimois;
	}



	public float getSalairehoraire() {
		return salairehoraire;
	}



	public void setSalairehoraire(float salairehoraire) {
		this.salairehoraire = salairehoraire;
	}



	public float getPanier() {
		return panier;
	}



	public void setPanier(float panier) {
		this.panier = panier;
	}



	public float getDeplacement() {
		return deplacement;
	}



	public void setDeplacement(float deplacement) {
		this.deplacement = deplacement;
	}



	public String getCommentaire() {
		return commentaire;
	}



	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}



	public Date getRedaction() {
		return redaction;
	}



	public void setRedaction(Date redaction) {
		this.redaction = redaction;
	}



	public String getUrssaf() {
		return urssaf;
	}



	public void setUrssaf(String urssaf) {
		this.urssaf = urssaf;
	}



	public Date getDateeffet() {
		return dateeffet;
	}



	public void setDateeffet(Date dateeffet) {
		this.dateeffet = dateeffet;
	}



	public AvenantCDI(int rangavenent, ContratCDI contrat, String tache,
			int heuresminimois, float salairehoraire, float panier,
			float deplacement, String commentaire, Date redaction,
			String urssaf, Date dateeffet) {
		super();
		this.rangavenent = rangavenent;
		this.contrat = contrat;
		this.tache = tache;
		this.heuresminimois = heuresminimois;
		this.salairehoraire = salairehoraire;
		this.panier = panier;
		this.deplacement = deplacement;
		this.commentaire = commentaire;
		this.redaction = redaction;
		this.urssaf = urssaf;
		this.dateeffet = dateeffet;
	}



	public AvenantCDI() {
		super();
	}



	public AvenantCDI(int id_avenant, int rangavenent, ContratCDI contrat,
			String tache, int heuresminimois, float salairehoraire,
			float panier, float deplacement, String commentaire,
			Date redaction, String urssaf, Date dateeffet) {
		super();
		this.id_avenant = id_avenant;
		this.rangavenent = rangavenent;
		this.contrat = contrat;
		this.tache = tache;
		this.heuresminimois = heuresminimois;
		this.salairehoraire = salairehoraire;
		this.panier = panier;
		this.deplacement = deplacement;
		this.commentaire = commentaire;
		this.redaction = redaction;
		this.urssaf = urssaf;
		this.dateeffet = dateeffet;
	}



	public AvenantCDI(int id_avenant, int rangavenent, int heuresminimois,
			 Date dateeffet,String tache) {
		super();
		this.id_avenant = id_avenant;
		this.rangavenent = rangavenent;
		this.tache = tache;
		this.heuresminimois = heuresminimois;
		this.dateeffet = dateeffet;
	}



	public AvenantCDI(int id_avenant, int heuresminimois, Date dateeffet,String tache) {
		super();
		this.id_avenant = id_avenant;
		this.heuresminimois = heuresminimois;
		this.dateeffet = dateeffet;
		this.tache=tache;
	}



	public AvenantCDI(ContratCDI contrat, String tache, int heuresminimois,
			float salairehoraire, float panier, float deplacement,
			String commentaire, Date redaction, String urssaf, Date dateeffet) {
		super();
		this.contrat = contrat;
		this.tache = tache;
		this.heuresminimois = heuresminimois;
		this.salairehoraire = salairehoraire;
		this.panier = panier;
		this.deplacement = deplacement;
		this.commentaire = commentaire;
		this.redaction = redaction;
		this.urssaf = urssaf;
		this.dateeffet = dateeffet;
	}



	public AvenantCDI(int id_avenant, int rangavenent,int heuresminimois,
			Date dateeffet,String tache, Date redaction) {
		super();
		this.id_avenant = id_avenant;
		this.rangavenent = rangavenent;
		this.heuresminimois = heuresminimois;
		this.dateeffet = dateeffet;
		this.tache = tache;		
		this.redaction = redaction;
		
	}
	
	

}
