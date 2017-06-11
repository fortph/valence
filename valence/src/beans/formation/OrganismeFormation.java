package beans.formation;



public class OrganismeFormation {
	private int id_org;
	private String org;
	private String adr_org;
	private String cp_org;
	private String ville_org;
	private String tel_org;
	private boolean actif;
	
	
	public int getId_org() {
		return id_org;
	}
	public void setId_org(int id_org) {
		this.id_org = id_org;
	}
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public String getAdr_org() {
		return adr_org;
	}
	public void setAdr_org(String adr_org) {
		this.adr_org = adr_org;
	}
	public String getCp_org() {
		return cp_org;
	}
	public void setCp_org(String cp_org) {
		this.cp_org = cp_org;
	}
	public String getVille_org() {
		return ville_org;
	}
	public void setVille_org(String ville_org) {
		this.ville_org = ville_org;
	}
	public String getTel_org() {
		return tel_org;
	}
	public void setTel_org(String tel_org) {
		this.tel_org = tel_org;
	}
	public boolean isActif() {
		return actif;
	}
	public void setActif(boolean actif) {
		this.actif = actif;
	}
	public OrganismeFormation(int id_org, String org, String adr_org,
			String cp_org, String ville_org, String tel_org, boolean actif) {
		super();
		this.id_org = id_org;
		this.org = org;
		this.adr_org = adr_org;
		this.cp_org = cp_org;
		this.ville_org = ville_org;
		this.tel_org = tel_org;
		this.actif = actif;
	}
	public OrganismeFormation(String org, String adr_org, String cp_org,
			String ville_org, String tel_org, boolean actif) {
		super();
		this.org = org;
		this.adr_org = adr_org;
		this.cp_org = cp_org;
		this.ville_org = ville_org;
		this.tel_org = tel_org;
		this.actif = actif;
	}
	public OrganismeFormation() {
		super();
	}
	
	

	

}
