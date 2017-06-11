package beans.parametres.accueil;

public class Organisme {
	private int id;
	private String rs;
	private String adr1;
	private String adr2;
	private String cp;
	private String ville;
	private String tel;
	private String fax;
	private String mail;
	private String siret;
	private String agrement;
	private String president;
	private String titre;
	private String structure;
	private String ape;
	private String legal;
	private String urssaf;
	private String agrementsap;
	private String directeur;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRs() {
		return rs;
	}
	public void setRs(String rs) {
		this.rs = rs;
	}
	public String getAdr1() {
		return adr1;
	}
	public void setAdr1(String adr1) {
		this.adr1 = adr1;
	}
	public String getAdr2() {
		return adr2;
	}
	public void setAdr2(String adr2) {
		this.adr2 = adr2;
	}
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
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getSiret() {
		return siret;
	}
	public void setSiret(String siret) {
		this.siret = siret;
	}
	public String getAgrement() {
		return agrement;
	}
	public void setAgrement(String agrement) {
		this.agrement = agrement;
	}
	public String getPresident() {
		return president;
	}
	public void setPresident(String president) {
		this.president = president;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getStructure() {
		return structure;
	}
	public void setStructure(String structure) {
		this.structure = structure;
	}
	public String getApe() {
		return ape;
	}
	public void setApe(String ape) {
		this.ape = ape;
	}
	public String getLegal() {
		return legal;
	}
	public void setLegal(String legal) {
		this.legal = legal;
	}
	public String getUrssaf() {
		return urssaf;
	}
	public void setUrssaf(String urssaf) {
		this.urssaf = urssaf;
	}
	public String getAgrementsap() {
		return agrementsap;
	}
	public void setAgrementsap(String agrementsap) {
		this.agrementsap = agrementsap;
	}
	public String getDirecteur() {
		return directeur;
	}
	public void setDirecteur(String directeur) {
		this.directeur = directeur;
	}
	public Organisme(int id, String rs, String adr1, String adr2, String cp,
			String ville, String tel, String fax, String mail, String siret,
			String agrement, String president, String titre, String structure,
			String ape, String legal, String urssaf, String agrementsap,
			String directeur) {
		super();
		this.id = id;
		this.rs = rs;
		this.adr1 = adr1;
		this.adr2 = adr2;
		this.cp = cp;
		this.ville = ville;
		this.tel = tel;
		this.fax = fax;
		this.mail = mail;
		this.siret = siret;
		this.agrement = agrement;
		this.president = president;
		this.titre = titre;
		this.structure = structure;
		this.ape = ape;
		this.legal = legal;
		this.urssaf = urssaf;
		this.agrementsap = agrementsap;
		this.directeur = directeur;
	}
	public Organisme(String rs, String adr1, String adr2, String cp,
			String ville, String tel, String fax, String mail, String siret,
			String agrement, String president, String titre, String structure,
			String ape, String legal, String urssaf, String agrementsap,
			String directeur) {
		super();
		this.rs = rs;
		this.adr1 = adr1;
		this.adr2 = adr2;
		this.cp = cp;
		this.ville = ville;
		this.tel = tel;
		this.fax = fax;
		this.mail = mail;
		this.siret = siret;
		this.agrement = agrement;
		this.president = president;
		this.titre = titre;
		this.structure = structure;
		this.ape = ape;
		this.legal = legal;
		this.urssaf = urssaf;
		this.agrementsap = agrementsap;
		this.directeur = directeur;
	}
	public Organisme() {
		super();
	}
	
	
	

}
