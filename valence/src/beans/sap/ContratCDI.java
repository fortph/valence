package beans.sap;

import java.util.Date;

import beans.identite.Identite;
import beans.parametres.accueil.Organisme;

public class ContratCDI {

	private int id_contratcdi;
	private Organisme organisme;
	private Identite identite;
	private String tache;
	private Date embauche;	
	private int heuresminimois;
	private float salairehoraire;	
	private float panier;	
	private float deplacement;	
	private String commentaire;	
	private Date redaction;
	private String  urssaf;
	private String nompresident;
	private String agrement;
	private Date termecontrat;
	public int getId_contratcdi() {
		return id_contratcdi;
	}
	public void setId_contratcdi(int id_contratcdi) {
		this.id_contratcdi = id_contratcdi;
	}
	public Organisme getOrganisme() {
		return organisme;
	}
	public void setOrganisme(Organisme organisme) {
		this.organisme = organisme;
	}
	public Identite getIdentite() {
		return identite;
	}
	public void setIdentite(Identite identite) {
		this.identite = identite;
	}
	public String getTache() {
		return tache;
	}
	public void setTache(String tache) {
		this.tache = tache;
	}
	public Date getEmbauche() {
		return embauche;
	}
	public void setEmbauche(Date embauche) {
		this.embauche = embauche;
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
	public String getNompresident() {
		return nompresident;
	}
	public void setNompresident(String nompresident) {
		this.nompresident = nompresident;
	}
	public String getAgrement() {
		return agrement;
	}
	public void setAgrement(String agrement) {
		this.agrement = agrement;
	}
	public Date getTermecontrat() {
		return termecontrat;
	}
	public void setTermecontrat(Date termecontrat) {
		this.termecontrat = termecontrat;
	}
	public ContratCDI(int id_contratcdi, Organisme organisme,
			Identite identite, String tache, Date embauche, int heuresminimois,
			float salairehoraire, float panier, float deplacement,
			 String commentaire, Date redaction, String urssaf,
			String nompresident, String agrement, Date termecontrat) {
		super();
		this.id_contratcdi = id_contratcdi;
		this.organisme = organisme;
		this.identite = identite;
		this.tache = tache;
		this.embauche = embauche;
		this.heuresminimois = heuresminimois;
		this.salairehoraire = salairehoraire;
		this.panier = panier;
		this.deplacement = deplacement;
		this.commentaire = commentaire;
		this.redaction = redaction;
		this.urssaf = urssaf;
		this.nompresident = nompresident;
		this.agrement = agrement;
		this.termecontrat = termecontrat;
	}
	public ContratCDI(Organisme organisme, Identite identite, String tache,
			Date embauche, int heuresminimois, float salairehoraire,
			float panier, float deplacement,  String commentaire,
			Date redaction, String urssaf, String nompresident,
			String agrement, Date termecontrat) {
		super();
		this.organisme = organisme;
		this.identite = identite;
		this.tache = tache;
		this.embauche = embauche;
		this.heuresminimois = heuresminimois;
		this.salairehoraire = salairehoraire;
		this.panier = panier;
		this.deplacement = deplacement;
		this.commentaire = commentaire;
		this.redaction = redaction;
		this.urssaf = urssaf;
		this.nompresident = nompresident;
		this.agrement = agrement;
		this.termecontrat = termecontrat;
	}
	public ContratCDI() {
		super();
	}
	public ContratCDI(int id_contratcdi, Identite identite, Date embauche,
			int heuresminimois, Date termecontrat) {
		super();
		this.id_contratcdi = id_contratcdi;
		this.identite = identite;
		this.embauche = embauche;
		this.heuresminimois = heuresminimois;
		this.termecontrat = termecontrat;
	}
	
	
}
