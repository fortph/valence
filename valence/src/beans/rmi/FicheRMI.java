package beans.rmi;

import java.util.Date;

import beans.identite.Identite;
import beans.parametres.capemploi.Utilisateur;

public class FicheRMI {
	private int id_rmi;
	private Identite identite;
	private String prescripteur;
	private String responsable;
	private Utilisateur referent;
	private Date creation_rmi;	
	private String fonction;
	private String agence;
	private String mail;
	

	public int getId_rmi() {
		return id_rmi;
	}


	public void setId_rmi(int id_rmi) {
		this.id_rmi = id_rmi;
	}


	public Identite getIdentite() {
		return identite;
	}


	public void setIdentite(Identite identite) {
		this.identite = identite;
	}


	public String getPrescripteur() {
		return prescripteur;
	}


	public void setPrescripteur(String prescripteur) {
		this.prescripteur = prescripteur;
	}


	public String getResponsable() {
		return responsable;
	}


	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}


	public Utilisateur getReferent() {
		return referent;
	}


	public void setReferent(Utilisateur referent) {
		this.referent = referent;
	}


	public Date getCreation_rmi() {
		return creation_rmi;
	}


	public void setCreation_rmi(Date creation_rmi) {
		this.creation_rmi = creation_rmi;
	}


	public String getFonction() {
		return fonction;
	}


	public void setFonction(String fonction) {
		this.fonction = fonction;
	}


	public String getAgence() {
		return agence;
	}


	public void setAgence(String agence) {
		this.agence = agence;
	}


	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}


	public FicheRMI(Identite identite, String prescripteur, Utilisateur referent) {
		super();
		this.identite = identite;
		this.prescripteur = prescripteur;
		this.referent = referent;
	}


	public FicheRMI(int id_rmi, Identite identite, String prescripteur,
			String responsable, Utilisateur referent, Date creation_rmi,
			String fonction, String agence, String mail) {
		super();
		this.id_rmi = id_rmi;
		this.identite = identite;
		this.prescripteur = prescripteur;
		this.responsable = responsable;
		this.referent = referent;
		this.creation_rmi = creation_rmi;
		this.fonction = fonction;
		this.agence = agence;
		this.mail = mail;
	}


	public FicheRMI() {
		super();
	}


	public FicheRMI(Identite identite, String prescripteur, String responsable,
			Utilisateur referent, Date creation_rmi, String fonction,
			String agence, String mail) {
		super();
		this.identite = identite;
		this.prescripteur = prescripteur;
		this.responsable = responsable;
		this.referent = referent;
		this.creation_rmi = creation_rmi;
		this.fonction = fonction;
		this.agence = agence;
		this.mail = mail;
	}
	
	
	
}
	