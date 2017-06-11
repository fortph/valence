package beans.employeurs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import beans.parametres.employeur.Activite;
import beans.parametres.employeur.Statut;
import beans.parametres.employeur.Structure;
import beans.employeurs.Service;

public class Employeur {
	private int id_employeur;
	private String civemp;
	private String rs_employeur;
	private Statut rsstatut_employeur;
	private String adr1;
	private String adr2;
	private String cp;
	private String ville;
	private String tel1;
	private String tel2;
	private String fax;
	private String mail;
	private Structure structure;
	private String observation;
	private Activite activite;
	private Date datecreation;
	private String civresp;
	private String nomresponsable;
	private String prenomresponsable;
	private String rangresponsable;
	private boolean actif;
	private Date datemodif;
	private String siret;
	private String ape;
	private String rm;
	private List<Service> liste = new ArrayList<Service>();

	public List<Service> getListe() {
		return liste;
	}

	public void addService(Service ser) {
		if (liste == null)
			liste.add(ser);
		else {
			if (!liste.contains(ser))
				liste.add(ser);
		}

	}

	public void delService(Service ser) {
		if (liste.contains(ser))
			liste.remove(ser);
	}

	public int getId_employeur() {
		return id_employeur;
	}

	public void setId_employeur(int id_employeur) {
		this.id_employeur = id_employeur;
	}

	public String getCivemp() {
		return civemp;
	}

	public void setCivemp(String civemp) {
		this.civemp = civemp;
	}

	public String getRs_employeur() {
		return rs_employeur;
	}

	public void setRs_employeur(String rs_employeur) {
		this.rs_employeur = rs_employeur;
	}

	public Statut getRsstatut_employeur() {
		return rsstatut_employeur;
	}

	public void setRsstatut_employeur(Statut rsstatut_employeur) {
		this.rsstatut_employeur = rsstatut_employeur;
	}

	public String getAdr1() {
		return adr1;
	}

	public void setAdr1(String adr1) {
		this.adr1 = adr1;
	}

	public String getAdr2() {
		return adr2;
	}

	public void setAdr2(String adr2) {
		this.adr2 = adr2;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getTel1() {
		return tel1;
	}

	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}

	public String getTel2() {
		return tel2;
	}

	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Structure getStructure() {
		return structure;
	}

	public void setStructure(Structure structure) {
		this.structure = structure;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Activite getActivite() {
		return activite;
	}

	public void setActivite(Activite activite) {
		this.activite = activite;
	}

	public Date getDatecreation() {
		return datecreation;
	}

	public void setDatecreation(Date datecreation) {
		this.datecreation = datecreation;
	}

	public String getCivresp() {
		return civresp;
	}

	public void setCivresp(String civresp) {
		this.civresp = civresp;
	}

	public String getNomresponsable() {
		return nomresponsable;
	}

	public void setNomresponsable(String nomresponsable) {
		this.nomresponsable = nomresponsable;
	}

	public String getPrenomresponsable() {
		return prenomresponsable;
	}

	public void setPrenomresponsable(String prenomresponsable) {
		this.prenomresponsable = prenomresponsable;
	}

	public String getRangresponsable() {
		return rangresponsable;
	}

	public void setRangresponsable(String rangresponsable) {
		this.rangresponsable = rangresponsable;
	}

	public boolean isActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}

	public Date getDatemodif() {
		return datemodif;
	}

	public void setDatemodif(Date datemodif) {
		this.datemodif = datemodif;
	}

	public String getSiret() {
		return siret;
	}

	public void setSiret(String siret) {
		this.siret = siret;
	}

	public String getApe() {
		return ape;
	}

	public void setApe(String ape) {
		this.ape = ape;
	}

	public String getRm() {
		return rm;
	}

	public void setRm(String rm) {
		this.rm = rm;
	}

	public Employeur() {
		super();
	}

	public Employeur(int id_employeur, String civemp, String rs_employeur,
			Statut rsstatut_employeur, String adr1, String adr2, String cp,
			String ville, String tel1, String tel2, String fax, String mail,
			Structure structure, String observation, Activite activite,
			Date datecreation, String civresp, String nomresponsable,
			String prenomresponsable, String rangresponsable, boolean actif,
			Date datemodif, String siret, String ape, String rm,
			List<Service> liste) {
		super();
		this.id_employeur = id_employeur;
		this.civemp = civemp;
		this.rs_employeur = rs_employeur;
		this.rsstatut_employeur = rsstatut_employeur;
		this.adr1 = adr1;
		this.adr2 = adr2;
		this.cp = cp;
		this.ville = ville;
		this.tel1 = tel1;
		this.tel2 = tel2;
		this.fax = fax;
		this.mail = mail;
		this.structure = structure;
		this.observation = observation;
		this.activite = activite;
		this.datecreation = datecreation;
		this.civresp = civresp;
		this.nomresponsable = nomresponsable;
		this.prenomresponsable = prenomresponsable;
		this.rangresponsable = rangresponsable;
		this.actif = actif;
		this.datemodif = datemodif;
		this.siret = siret;
		this.ape = ape;
		this.rm = rm;
		this.liste = liste;
	}

	public Employeur(String civemp, String rs_employeur,
			Statut rsstatut_employeur, String adr1, String adr2, String cp,
			String ville, String tel1, String tel2, String fax, String mail,
			Structure structure, String observation, Activite activite,
			Date datecreation, String civresp, String nomresponsable,
			String prenomresponsable, String rangresponsable, boolean actif,
			Date datemodif, String siret, String ape, String rm,
			List<Service> liste) {
		super();
		this.civemp = civemp;
		this.rs_employeur = rs_employeur;
		this.rsstatut_employeur = rsstatut_employeur;
		this.adr1 = adr1;
		this.adr2 = adr2;
		this.cp = cp;
		this.ville = ville;
		this.tel1 = tel1;
		this.tel2 = tel2;
		this.fax = fax;
		this.mail = mail;
		this.structure = structure;
		this.observation = observation;
		this.activite = activite;
		this.datecreation = datecreation;
		this.civresp = civresp;
		this.nomresponsable = nomresponsable;
		this.prenomresponsable = prenomresponsable;
		this.rangresponsable = rangresponsable;
		this.actif = actif;
		this.datemodif = datemodif;
		this.siret = siret;
		this.ape = ape;
		this.rm = rm;
		this.liste = liste;
	}

	public Employeur(String civemp, String rs_employeur,
			Statut rsstatut_employeur, String adr1, String adr2, String cp,
			String ville, String tel1, String tel2, String fax, String mail,
			Structure structure, String observation, Activite activite,
			Date datecreation, String civresp, String nomresponsable,
			String prenomresponsable, String rangresponsable, boolean actif,
			Date datemodif, String siret, String ape, String rm) {
		super();
		this.civemp = civemp;
		this.rs_employeur = rs_employeur;
		this.rsstatut_employeur = rsstatut_employeur;
		this.adr1 = adr1;
		this.adr2 = adr2;
		this.cp = cp;
		this.ville = ville;
		this.tel1 = tel1;
		this.tel2 = tel2;
		this.fax = fax;
		this.mail = mail;
		this.structure = structure;
		this.observation = observation;
		this.activite = activite;
		this.datecreation = datecreation;
		this.civresp = civresp;
		this.nomresponsable = nomresponsable;
		this.prenomresponsable = prenomresponsable;
		this.rangresponsable = rangresponsable;
		this.actif = actif;
		this.datemodif = datemodif;
		this.siret = siret;
		this.ape = ape;
		this.rm = rm;
	}

	public Employeur(int id_employeur, String civemp, String rs_employeur,
			Statut rsstatut_employeur, String adr1, String adr2, String cp,
			String ville, String tel1, String tel2, String fax, String mail,
			Structure structure, String observation, Activite activite,
			Date datecreation, String civresp, String nomresponsable,
			String prenomresponsable, String rangresponsable, boolean actif,
			Date datemodif, String siret, String ape, String rm) {
		super();
		this.id_employeur = id_employeur;
		this.civemp = civemp;
		this.rs_employeur = rs_employeur;
		this.rsstatut_employeur = rsstatut_employeur;
		this.adr1 = adr1;
		this.adr2 = adr2;
		this.cp = cp;
		this.ville = ville;
		this.tel1 = tel1;
		this.tel2 = tel2;
		this.fax = fax;
		this.mail = mail;
		this.structure = structure;
		this.observation = observation;
		this.activite = activite;
		this.datecreation = datecreation;
		this.civresp = civresp;
		this.nomresponsable = nomresponsable;
		this.prenomresponsable = prenomresponsable;
		this.rangresponsable = rangresponsable;
		this.actif = actif;
		this.datemodif = datemodif;
		this.siret = siret;
		this.ape = ape;
		this.rm = rm;
	}

	public Employeur(int id_employeur, String rs_employeur, String ville,
			String tel1, String fax, String nomresponsable,
			String prenomresponsable, Structure structure) {
		super();
		this.id_employeur = id_employeur;
		this.rs_employeur = rs_employeur;
		this.ville = ville;
		this.tel1 = tel1;
		this.fax = fax;
		this.structure = structure;
		this.nomresponsable = nomresponsable;
		this.prenomresponsable = prenomresponsable;
	}
	
	

	public Employeur(int id_employeur, String rs_employeur, String ville,
			String tel1, String fax, String nomresponsable,
			String prenomresponsable) {
		super();
		this.id_employeur = id_employeur;
		this.rs_employeur = rs_employeur;
		this.ville = ville;
		this.tel1 = tel1;
		this.fax = fax;
		this.nomresponsable = nomresponsable;
		this.prenomresponsable = prenomresponsable;
	}
	
	
	public Employeur( String rs_employeur,int id_employeur) {
		super();
		this.id_employeur = id_employeur;
		this.rs_employeur = rs_employeur;
	}

	public Employeur(int id_employeur, String rs_employeur, String tel1,
			String fax, Date datecreation) {
		super();
		this.id_employeur = id_employeur;
		this.rs_employeur = rs_employeur;
		this.tel1 = tel1;
		this.fax = fax;
		this.datecreation = datecreation;
	}
	
	
	

}
