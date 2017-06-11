package beans.employeurs;


public class Contact {
	private int id_contact;
	private Employeur employeur;
	private Service service;
	private String civ_contact;
	private String nom_contact;
	private String prenom_contact;
	private String tel_contact;
	private String fax_contact;
	private String portable_contact;
	private String mail_contact;
	private String rang_contact;
	private boolean actif;
	public int getId_contact() {
		return id_contact;
	}
	public void setId_contact(int id_contact) {
		this.id_contact = id_contact;
	}
	public Employeur getEmployeur() {
		return employeur;
	}
	public void setEmployeur(Employeur employeur) {
		this.employeur = employeur;
	}
	public Service getService() {
		return service;
	}
	public void setService(Service service) {
		this.service = service;
	}
	public String getCiv_contact() {
		return civ_contact;
	}
	public void setCiv_contact(String civ_contact) {
		this.civ_contact = civ_contact;
	}
	public String getNom_contact() {
		return nom_contact;
	}
	public void setNom_contact(String nom_contact) {
		this.nom_contact = nom_contact;
	}
	public String getPrenom_contact() {
		return prenom_contact;
	}
	public void setPrenom_contact(String prenom_contact) {
		this.prenom_contact = prenom_contact;
	}
	public String getTel_contact() {
		return tel_contact;
	}
	public void setTel_contact(String tel_contact) {
		this.tel_contact = tel_contact;
	}
	public String getFax_contact() {
		return fax_contact;
	}
	public void setFax_contact(String fax_contact) {
		this.fax_contact = fax_contact;
	}
	public String getPortable_contact() {
		return portable_contact;
	}
	public void setPortable_contact(String portable_contact) {
		this.portable_contact = portable_contact;
	}
	public String getMail_contact() {
		return mail_contact;
	}
	public void setMail_contact(String mail_contact) {
		this.mail_contact = mail_contact;
	}
	public String getRang_contact() {
		return rang_contact;
	}
	public void setRang_contact(String rang_contact) {
		this.rang_contact = rang_contact;
	}
	public boolean isActif() {
		return actif;
	}
	public void setActif(boolean actif) {
		this.actif = actif;
	}
	public Contact() {
		super();
	}
	public Contact(int id_contact, Employeur employeur, Service service,
			String civ_contact, String nom_contact, String prenom_contact,
			String tel_contact, String fax_contact, String portable_contact,
			String mail_contact, String rang_contact, boolean actif) {
		super();
		this.id_contact = id_contact;
		this.employeur = employeur;
		this.service = service;
		this.civ_contact = civ_contact;
		this.nom_contact = nom_contact;
		this.prenom_contact = prenom_contact;
		this.tel_contact = tel_contact;
		this.fax_contact = fax_contact;
		this.portable_contact = portable_contact;
		this.mail_contact = mail_contact;
		this.rang_contact = rang_contact;
		this.actif = actif;
	}
	public Contact(Employeur employeur, Service service, String civ_contact,
			String nom_contact, String prenom_contact, String tel_contact,
			String fax_contact, String portable_contact, String mail_contact,
			String rang_contact, boolean actif) {
		super();
		this.employeur = employeur;
		this.service = service;
		this.civ_contact = civ_contact;
		this.nom_contact = nom_contact;
		this.prenom_contact = prenom_contact;
		this.tel_contact = tel_contact;
		this.fax_contact = fax_contact;
		this.portable_contact = portable_contact;
		this.mail_contact = mail_contact;
		this.rang_contact = rang_contact;
		this.actif = actif;
	}
	public Contact(Employeur employeur, String civ_contact, String nom_contact,
			String prenom_contact, String tel_contact, String fax_contact,
			String portable_contact, String mail_contact, String rang_contact,
			boolean actif) {
		super();
		this.employeur = employeur;
		this.civ_contact = civ_contact;
		this.nom_contact = nom_contact;
		this.prenom_contact = prenom_contact;
		this.tel_contact = tel_contact;
		this.fax_contact = fax_contact;
		this.portable_contact = portable_contact;
		this.mail_contact = mail_contact;
		this.rang_contact = rang_contact;
		this.actif = actif;
	}
	
	
	
	
}