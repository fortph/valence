package beans.parametres.accueil;

public class Civilite {
	private int id_civilite;
	private String civilite;
	public int getId_civilite() {
		return id_civilite;
	}
	public void setId_civilite(int id_civilite) {
		this.id_civilite = id_civilite;
	}
	public String getCivilite() {
		return civilite;
	}
	public void setCivilite(String civilite) {
		this.civilite = civilite;
	}
	public Civilite(int id_civilite, String civilite) {
		super();
		this.id_civilite = id_civilite;
		this.civilite = civilite;
	}
	public Civilite() {
		super();
	}
	
	

}
