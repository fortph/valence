package beans.parametres.accueil;

public class ReferentPoleEmploi {
	private int id_referent;
	private String nom;
	private String prenom;
	private String mail;
	private String mailrsa;
	private String maildt;
	public int getId_referent() {
		return id_referent;
	}
	public void setId_referent(int id_referent) {
		this.id_referent = id_referent;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getMailrsa() {
		return mailrsa;
	}
	public void setMailrsa(String mailrsa) {
		this.mailrsa = mailrsa;
	}
	public String getMaildt() {
		return maildt;
	}
	public void setMaildt(String maildt) {
		this.maildt = maildt;
	}
	public ReferentPoleEmploi(int id_referent, String nom, String prenom,
			String mail, String mailrsa, String maildt) {
		super();
		this.id_referent = id_referent;
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.mailrsa = mailrsa;
		this.maildt = maildt;
	}
	public ReferentPoleEmploi(String nom, String prenom, String mail,
			String mailrsa, String maildt) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.mailrsa = mailrsa;
		this.maildt = maildt;
	}
	public ReferentPoleEmploi() {
		super();
	}
	public ReferentPoleEmploi(int id_referent, String nom, String prenom,
			String mail) {
		super();
		this.id_referent = id_referent;
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
	}
	
	
}