package beans.identite;

import java.util.Date;

public class ProfilPriorite {
	private int id_priorite;
	private String libelle;
	private int id_identite;
	//cette date est la date d'expiration pour rth ou la 
	//date d'inscription pour les RSA cloche et chapeau
	private Date expire;

	

	public ProfilPriorite() {
		super();
	}

	public ProfilPriorite(String libelle, int id_identite, Date expire) {
		super();
		this.libelle = libelle;
		this.id_identite = id_identite;
		this.expire = expire;
	}

	public ProfilPriorite(int id_priorite, String libelle, int id_identite,
			Date expire) {
		super();
		this.id_priorite = id_priorite;
		this.libelle = libelle;
		this.id_identite = id_identite;
		this.expire = expire;
	}

	public ProfilPriorite(String libelle, int id_identite) {
		super();
		this.libelle = libelle;
		this.id_identite = id_identite;
	}

	public Date getExpire() {
		return expire;
	}

	public void setExpire(Date expire) {
		this.expire = expire;
	}

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

	public int getId_identite() {
		return id_identite;
	}

	public void setId_identite(int id_identite) {
		this.id_identite = id_identite;
	}
}
