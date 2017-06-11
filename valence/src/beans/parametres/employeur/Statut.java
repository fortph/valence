package beans.parametres.employeur;

public class Statut {
	private int id_statemployeur;
	private String staturrs;
	private boolean actif;
	public int getId_statemployeur() {
		return id_statemployeur;
	}
	public void setId_statemployeur(int id_statemployeur) {
		this.id_statemployeur = id_statemployeur;
	}
	public String getStaturrs() {
		return staturrs;
	}
	public void setStaturrs(String staturrs) {
		this.staturrs = staturrs;
	}
	public boolean isActif() {
		return actif;
	}
	public void setActif(boolean actif) {
		this.actif = actif;
	}
	public Statut(int id_statemployeur, String staturrs, boolean actif) {
		super();
		this.id_statemployeur = id_statemployeur;
		this.staturrs = staturrs;
		this.actif = actif;
	}
	public Statut() {
		super();
	}
	public Statut(String staturrs, boolean actif) {
		super();
		this.staturrs = staturrs;
		this.actif = actif;
	}
	

}
