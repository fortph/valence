package beans.identite;


import java.util.Date;


public class ProfilPermisPro {

	private int id_permis;
	private String libelle;
	private Identite id_identite;
	private Date expiration;
	public int getId_permis() {
		return id_permis;
	}
	public void setId_permis(int id_permis) {
		this.id_permis = id_permis;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public Identite getId_identite() {
		return id_identite;
	}
	public void setId_identite(Identite id_identite) {
		this.id_identite = id_identite;
	}
	public Date getExpiration() {
		return expiration;
	}
	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
	public ProfilPermisPro(int id_permis, String libelle, Identite id_identite,
			Date expiration) {
		super();
		this.id_permis = id_permis;
		this.libelle = libelle;
		this.id_identite = id_identite;
		this.expiration = expiration;
	}
	public ProfilPermisPro() {
		super();
	}
	public ProfilPermisPro(String libelle, Identite id_identite, Date expiration) {
		super();
		this.libelle = libelle;
		this.id_identite = id_identite;
		this.expiration = expiration;
	}
}