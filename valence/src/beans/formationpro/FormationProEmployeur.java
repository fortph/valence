package beans.formationpro;

import beans.parametres.formationpro.Formprostatutemployeur;

public class FormationProEmployeur {
	private int id_employeur;
	private String employeur;
	private String adresse;
	private String cp;
	private String ville;
	private String tel1;
	private String tel2;
	private Formprostatutemployeur statut;
	private boolean actif;
	
	
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
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
	public int getId_employeur() {
		return id_employeur;
	}
	public void setId_employeur(int id_employeur) {
		this.id_employeur = id_employeur;
	}
	public String getEmployeur() {
		return employeur;
	}
	public void setEmployeur(String employeur) {
		this.employeur = employeur;
	}
	public Formprostatutemployeur getStatut() {
		return statut;
	}
	public void setStatut(Formprostatutemployeur statut) {
		this.statut = statut;
	}
	public boolean isActif() {
		return actif;
	}
	public void setActif(boolean actif) {
		this.actif = actif;
	}
	public FormationProEmployeur() {
		super();
	}
	public FormationProEmployeur(String employeur,
			Formprostatutemployeur statut, boolean actif) {
		super();
		this.employeur = employeur;
		this.statut = statut;
		this.actif = actif;
	}
	public FormationProEmployeur(int id_employeur, String employeur,
			Formprostatutemployeur statut, boolean actif) {
		super();
		this.id_employeur = id_employeur;
		this.employeur = employeur;
		this.statut = statut;
		this.actif = actif;
	}
	public FormationProEmployeur(int id_employeur, String employeur,
			String adresse, String cp, String ville, String tel1, String tel2,
			Formprostatutemployeur statut, boolean actif) {
		super();
		this.id_employeur = id_employeur;
		this.employeur = employeur;
		this.adresse = adresse;
		this.cp = cp;
		this.ville = ville;
		this.tel1 = tel1;
		this.tel2 = tel2;
		this.statut = statut;
		this.actif = actif;
	}
	public FormationProEmployeur(String employeur, String adresse, String cp,
			String ville, String tel1, String tel2,
			Formprostatutemployeur statut, boolean actif) {
		super();
		this.employeur = employeur;
		this.adresse = adresse;
		this.cp = cp;
		this.ville = ville;
		this.tel1 = tel1;
		this.tel2 = tel2;
		this.statut = statut;
		this.actif = actif;
	}
	
	
	

}
