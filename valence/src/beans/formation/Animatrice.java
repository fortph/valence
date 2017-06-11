package beans.formation;


public class Animatrice {
	private short id_animatrice;
	private String nom;
	private String mail;
	private boolean actif;
	public boolean isActif() {
		return actif;
	}
	public void setActif(boolean actif) {
		this.actif = actif;
	}
	public short getId_animatrice() {
		return id_animatrice;
	}
	public void setId_animatrice(short id_animatrice) {
		this.id_animatrice = id_animatrice;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	
	
	public Animatrice(short id_animatrice, String nom, String mail,
			boolean actif) {
		super();
		this.id_animatrice = id_animatrice;
		this.nom = nom;
		this.mail = mail;
		this.actif = actif;
	}
	public Animatrice(String nom, String mail, boolean actif) {
		super();
		this.nom = nom;
		this.mail = mail;
		this.actif = actif;
	}
	public Animatrice() {
		super();
	}
	
	
}
