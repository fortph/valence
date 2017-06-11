package beans.parametres.accueil;

public class PermisPro {
	private int id;
	private String libelle;
	private String designation;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public PermisPro(int id, String libelle, String designation) {
		super();
		this.id = id;
		this.libelle = libelle;
		this.designation = designation;
	}
	public PermisPro() {
		super();
	}
	public PermisPro(String libelle, String designation) {
		super();
		this.libelle = libelle;
		this.designation = designation;
	}
	
	
	

}
