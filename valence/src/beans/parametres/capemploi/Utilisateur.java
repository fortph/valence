package beans.parametres.capemploi;

import divers.Encode;

public class Utilisateur {
	private int id_salarie;
	private String nom;
	private String prenom;
	private String login;
	private String passe;
	private String privilege;
	private String mail;
	private boolean actif;
	public int getId_salarie() {
		return id_salarie;
	}
	public void setId_salarie(int id_salarie) {
		this.id_salarie = id_salarie;
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
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPasse() {
		return passe;
	}
	public void setPasse(String passe) {
		this.passe = new Encode().encode(passe);
	}
	public String getPrivilege() {
		return privilege;
	}
	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public boolean isActif() {
		return actif;
	}
	public void setActif(boolean actif) {
		this.actif = actif;
	}
	public Utilisateur(String nom, String prenom, String login, String passe,
			String privilege, String mail, boolean actif) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.login = login;
		this.passe = passe;
		this.privilege = privilege;
		this.mail = mail;
		this.actif = actif;
	}
	public Utilisateur(int id_salarie, String nom, String prenom, String login,
			String passe, String privilege, String mail, boolean actif) {
		super();
		this.id_salarie = id_salarie;
		this.nom = nom;
		this.prenom = prenom;
		this.login = login;
		this.passe = passe;
		this.privilege = privilege;
		this.mail = mail;
		this.actif = actif;
	}
	public Utilisateur() {
		super();
	}
	public Utilisateur(String nom, String prenom, String login,
			String privilege, String mail, boolean actif) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.login = login;
		this.passe=new Encode().encode("1234");
		this.privilege = privilege;
		this.mail = mail;
		this.actif = actif;
	}
	
	
	
	/*private String encode(String s){
		byte[] cle=s.getBytes();
		byte[]hash=null;
		try {
			hash=MessageDigest.getInstance("MD5").digest(cle);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuilder hashString=new StringBuilder();
		 for (int i = 0; i < hash.length; i++)
	        {
	            String hex = Integer.toHexString(hash[i]);
	            if (hex.length() == 1)
	            {
	                hashString.append('0');
	                hashString.append(hex.charAt(hex.length() - 1));
	            }
	            else
	                hashString.append(hex.substring(hex.length() - 2));
	        }
		return hashString.toString();
	}*/
}