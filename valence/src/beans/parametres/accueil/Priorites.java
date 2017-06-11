package beans.parametres.accueil;



public class Priorites {
	private int id_priorite;
	private String libelle;
	public int getId_priorite() {
		return id_priorite;
	}
	public void setId_priorite(int id_priorite) {
		this.id_priorite = id_priorite;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public Priorites(int id_priorite) {
		super();
		this.id_priorite = id_priorite;
	}
	public Priorites() {
		super();
	}
	public Priorites(int id_priorite, String libelle) {
		super();
		this.id_priorite = id_priorite;
		this.libelle = libelle;
	}
	
	


}
