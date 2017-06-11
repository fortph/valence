package beans.rmi;

import java.util.Date;

import beans.identite.Identite;
import beans.parametres.capemploi.Utilisateur;

public class ConvocRMI {
	private int id_convoc;
	private FicheRMI fichermi;
	private Identite identite;
	private Date rmiconvoc;
	private Date dateconvoc_rmiconvoc;
	private String heureconvoc;
	private Utilisateur referent;
	public ConvocRMI(FicheRMI fichermi, Identite identite, Date rmiconvoc,
			Date dateconvoc_rmiconvoc, String heureconvoc, Utilisateur referent) {
		super();
		this.fichermi = fichermi;
		this.identite = identite;
		this.rmiconvoc = rmiconvoc;
		this.dateconvoc_rmiconvoc = dateconvoc_rmiconvoc;
		this.heureconvoc = heureconvoc;
		this.referent = referent;
	}
	public ConvocRMI() {
		super();
	}
	public ConvocRMI(int id_convoc, FicheRMI fichermi, Identite identite,
			Date rmiconvoc, Date dateconvoc_rmiconvoc, String heureconvoc,
			Utilisateur referent) {
		super();
		this.id_convoc = id_convoc;
		this.fichermi = fichermi;
		this.identite = identite;
		this.rmiconvoc = rmiconvoc;
		this.dateconvoc_rmiconvoc = dateconvoc_rmiconvoc;
		this.heureconvoc = heureconvoc;
		this.referent = referent;
	}
	public int getId_convoc() {
		return id_convoc;
	}
	public void setId_convoc(int id_convoc) {
		this.id_convoc = id_convoc;
	}
	public FicheRMI getFichermi() {
		return fichermi;
	}
	public void setFichermi(FicheRMI fichermi) {
		this.fichermi = fichermi;
	}
	public Identite getIdentite() {
		return identite;
	}
	public void setIdentite(Identite identite) {
		this.identite = identite;
	}
	public Date getRmiconvoc() {
		return rmiconvoc;
	}
	public void setRmiconvoc(Date rmiconvoc) {
		this.rmiconvoc = rmiconvoc;
	}
	public Date getDateconvoc_rmiconvoc() {
		return dateconvoc_rmiconvoc;
	}
	public void setDateconvoc_rmiconvoc(Date dateconvoc_rmiconvoc) {
		this.dateconvoc_rmiconvoc = dateconvoc_rmiconvoc;
	}
	public String getHeureconvoc() {
		return heureconvoc;
	}
	public void setHeureconvoc(String heureconvoc) {
		this.heureconvoc = heureconvoc;
	}
	public Utilisateur getReferent() {
		return referent;
	}
	public void setReferent(Utilisateur referent) {
		this.referent = referent;
	}
	
	

}
