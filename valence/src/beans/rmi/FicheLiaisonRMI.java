package beans.rmi;

import java.util.Date;

public class FicheLiaisonRMI {
	private int id_ficheliaison;
	private ContratRMI contrat;
	private Date datecreation;

	private String nomreferentpe;
	private String prenomreferentpe;
	private String mailreferentpe;
	private String obsprescripteur;
	private String presence;
	private String acquis;
	private String nomacquis;
	private String encours;
	private String conclusion;
	
	public int getId_ficheliaison() {
		return id_ficheliaison;
	}
	public void setId_ficheliaison(int id_ficheliaison) {
		this.id_ficheliaison = id_ficheliaison;
	}
	public ContratRMI getContrat() {
		return contrat;
	}
	public void setContrat(ContratRMI contrat) {
		this.contrat = contrat;
	}
	public Date getDatecreation() {
		return datecreation;
	}
	public void setDatecreation(Date datecreation) {
		this.datecreation = datecreation;
	}
	public String getNomreferentpe() {
		return nomreferentpe;
	}
	public void setNomreferentpe(String nomreferentpe) {
		this.nomreferentpe = nomreferentpe;
	}
	public String getPrenomreferentpe() {
		return prenomreferentpe;
	}
	public void setPrenomreferentpe(String prenomreferentpe) {
		this.prenomreferentpe = prenomreferentpe;
	}
	public String getMailreferentpe() {
		return mailreferentpe;
	}
	public void setMailreferentpe(String mailreferentpe) {
		this.mailreferentpe = mailreferentpe;
	}
	
	public String getObsprescripteur() {
		return obsprescripteur;
	}
	public void setObsprescripteur(String obsprescripteur) {
		this.obsprescripteur = obsprescripteur;
	}
	public String getPresence() {
		return presence;
	}
	public void setPresence(String presence) {
		this.presence = presence;
	}
	public String getAcquis() {
		return acquis;
	}
	public void setAcquis(String acquis) {
		this.acquis = acquis;
	}
	public String getNomacquis() {
		return nomacquis;
	}
	public void setNomacquis(String nomacquis) {
		this.nomacquis = nomacquis;
	}
	public String getEncours() {
		return encours;
	}
	public void setEncours(String encours) {
		this.encours = encours;
	}
	public String getConclusion() {
		return conclusion;
	}
	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}
	public FicheLiaisonRMI(int id_ficheliaison, ContratRMI contrat,
			Date datecreation, String nomreferentpe, String prenomreferentpe,
			String mailreferentpe, String obsprescripteur,String presence, String acquis,
			String nomacquis, String encours, String conclusion) {
		super();
		this.id_ficheliaison = id_ficheliaison;
		this.contrat = contrat;
		this.datecreation = datecreation;
		this.nomreferentpe = nomreferentpe;
		this.prenomreferentpe = prenomreferentpe;
		this.mailreferentpe = mailreferentpe;
		this.obsprescripteur=obsprescripteur;
		this.presence = presence;
		this.acquis = acquis;
		this.nomacquis = nomacquis;
		this.encours = encours;
		this.conclusion = conclusion;
	}
	
	public FicheLiaisonRMI() {
		super();
		
	}
	public FicheLiaisonRMI(ContratRMI contrat, Date datecreation,
			String nomreferentpe, String prenomreferentpe,
			String mailreferentpe,String obsprescripteur, String presence, String acquis,
			String nomacquis, String encours, String conclusion) {
		super();
		this.contrat = contrat;
		this.datecreation = datecreation;
		this.nomreferentpe = nomreferentpe;
		this.prenomreferentpe = prenomreferentpe;
		this.mailreferentpe = mailreferentpe;
		this.obsprescripteur=obsprescripteur;
		this.presence = presence;
		this.acquis = acquis;
		this.nomacquis = nomacquis;
		this.encours = encours;
		this.conclusion = conclusion;
	}
	
	
	
}