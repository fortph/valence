package beans.parametres.accueil;



public class Cc2r {
	
	private int id_cc2r;
	private String libelle;
	public int getId_cc2r() {
		return id_cc2r;
	}
	public void setId_cc2r(int id_cc2r) {
		this.id_cc2r = id_cc2r;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public Cc2r() {
		super();
	}
	public Cc2r(int id_cc2r, String libelle) {
		super();
		this.id_cc2r = id_cc2r;
		this.libelle = libelle;
	}
	public Cc2r(String libelle) {
		super();
		this.libelle = libelle;
	}
	
	
	
	
}
