package beans.parametres.formation;

public class Theme {
	private int id_themeformation;
	private String libelle;
	public int getId_themeformation() {
		return id_themeformation;
	}
	public void setId_themeformation(int id_themeformation) {
		this.id_themeformation = id_themeformation;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public Theme(int id_themeformation, String libelle) {
		super();
		this.id_themeformation = id_themeformation;
		this.libelle = libelle;
	}
	public Theme(String libelle) {
		super();
		this.libelle = libelle;
	}
	public Theme() {
		super();
	}
	

}
