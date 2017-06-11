package beans.parametres.employeur;

public class Activite {
	private int id_activite;
	private String activite;
	private boolean actif;
	public int getId_activite() {
		return id_activite;
	}
	public void setId_activite(int id_activite) {
		this.id_activite = id_activite;
	}
	public String getActivite() {
		return activite;
	}
	public void setActivite(String activite) {
		this.activite = activite;
	}
	public boolean isActif() {
		return actif;
	}
	public void setActif(boolean actif) {
		this.actif = actif;
	}
	public Activite(int id_activite, String activite, boolean actif) {
		super();
		this.id_activite = id_activite;
		this.activite = activite;
		this.actif = actif;
	}
	public Activite() {
		super();
	}
	public Activite(String activite, boolean actif) {
		super();
		this.activite = activite;
		this.actif = actif;
	}
	
	
}
