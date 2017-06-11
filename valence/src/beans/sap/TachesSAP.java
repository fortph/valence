package beans.sap;

public class TachesSAP {
	private int id_tache;
	private String libelle;
	public int getId_tache() {
		return id_tache;
	}
	public void setId_tache(int id_tache) {
		this.id_tache = id_tache;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public TachesSAP(int id_tache, String libelle) {
		super();
		this.id_tache = id_tache;
		this.libelle = libelle;
	}
	public TachesSAP() {
		super();
	}
	public TachesSAP(String libelle) {
		super();
		this.libelle = libelle;
	}
	

}
