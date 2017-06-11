package beans.parametres.accueil;


public class CodesPostaux {
	private int id;
	private String cp;
	private String ville;
	
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public CodesPostaux() {
		super();
	}
	public CodesPostaux(int id, String cp, String ville) {
		super();
		this.id = id;
		this.cp = cp;
		this.ville = ville;
	}
	public CodesPostaux(int id, String ville) {
		super();
		this.id = id;
		this.ville = ville;
	}
	
	
	
}
	
