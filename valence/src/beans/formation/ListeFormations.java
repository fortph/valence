package beans.formation;

import java.util.Date;

import beans.parametres.formation.Theme;



public class ListeFormations {
	private int id_pformation;
	private String formation;
	private OrganismeFormation organis;
	private String niveau;
	private Date datedeb_form;
	private Date datefin_form;
	private short heure_form;
	private boolean actif;
	private String etat_form;
	private String session_form;
	private Theme theme;
	
	public String getEtat_form() {
		return etat_form;
	}
	public void setEtat_form(String etat_form) {
		this.etat_form = etat_form;
	}
	public int getId_pformation() {
		return id_pformation;
	}
	public void setId_pformation(int id_pformation) {
		this.id_pformation = id_pformation;
	}
	public String getFormation() {
		return formation;
	}
	public void setFormation(String formation) {
		this.formation = formation;
	}
	public String getNiveau() {
		return niveau;
	}
	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}
	
	public Date getDatedeb_form() {
		return datedeb_form;
	}
	public void setDatedeb_form(Date datedeb_form) {
		this.datedeb_form = datedeb_form;
	}
	public Date getDatefin_form() {
		return datefin_form;
	}
	public void setDatefin_form(Date datefin_form) {
		this.datefin_form = datefin_form;
	}
	public short getHeure_form() {
		return heure_form;
	}
	public void setHeure_form(short heure_form) {
		this.heure_form = heure_form;
	}
	public boolean isActif() {
		return actif;
	}
	public void setActif(boolean actif) {
		this.actif = actif;
	}
	public String getSession_form() {
		return session_form;
	}
	public void setSession_form(String session_form) {
		this.session_form = session_form;
	}
	
	
	public OrganismeFormation getOrganis() {
		return organis;
	}
	public void setOrganis(OrganismeFormation organis) {
		this.organis = organis;
	}
		
	public Theme getTheme() {
		return theme;
	}
	public void setTheme(Theme theme) {
		this.theme = theme;
	}
	public ListeFormations() {
		super();
		
	}
	
	public ListeFormations(String formation, OrganismeFormation org,
			String niveau, Date datedeb_form, Date datefin_form,
			short heure_form, boolean actif,String etat_form, String session_form,Theme theme) {
		super();
		String an=datedeb_form.toString().substring(2, 4);
		String sem=null;
		String semestre=datedeb_form.toString().substring(5,7);
		int num=Integer.parseInt(semestre);
		if(num<=6)
			sem=".1.";
		else
			sem=".2.";
		
		this.formation = an+sem+formation;
		this.organis = org;
		this.niveau = niveau;
		this.datedeb_form = datedeb_form;
		this.datefin_form = datefin_form;
		this.heure_form = heure_form;
		this.actif = actif;
		this.etat_form=etat_form;
		this.session_form = session_form;
		this.theme=theme;
		
	}
	
	
	public ListeFormations(int id_pformation, String formation,
			OrganismeFormation org, String niveau, Date datedeb_form,
			Date datefin_form, short heure_form, boolean actif,String etat_form,
			String session_form,Theme theme) {
		super();
		this.id_pformation = id_pformation;
		this.formation = formation;
		this.organis = org;
		this.niveau = niveau;
		this.datedeb_form = datedeb_form;
		this.datefin_form = datefin_form;
		this.heure_form = heure_form;
		this.actif = actif;
		this.etat_form=etat_form;
		this.session_form = session_form;
		this.theme=theme;
	}
	
	
	public ListeFormations(int id_pformation, String formation,
			OrganismeFormation organis, String niveau, Date datedeb_form,
			Date datefin_form, short heure_form, boolean actif,
			String session_form, Theme theme) {
		super();
		this.id_pformation = id_pformation;
		this.formation = formation;
		this.organis = organis;
		this.niveau = niveau;
		this.datedeb_form = datedeb_form;
		this.datefin_form = datefin_form;
		this.heure_form = heure_form;
		this.actif = actif;
		this.session_form = session_form;
		this.theme=theme;
	}
	public ListeFormations(String formation, String niveau, Date datedeb_form,
			Date datefin_form) {
		super();
		this.formation = formation;
		this.niveau = niveau;
		this.datedeb_form = datedeb_form;
		this.datefin_form = datefin_form;
	}
	public ListeFormations(String formation, OrganismeFormation organis,
			String niveau, Date datedeb_form, Date datefin_form) {
		super();
		this.formation = formation;
		this.organis = organis;
		this.niveau = niveau;
		this.datedeb_form = datedeb_form;
		this.datefin_form = datefin_form;
	}
	
	
	

}
