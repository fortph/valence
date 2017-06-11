package beans.identite;



public class ProfilDiplome {

	private int id_diplome;
	private int id_identite;
	private String nomDiplome;
	private String obtenu;
	private String annee;
	
	
	public ProfilDiplome() {
		super();
	}
	
	

	public ProfilDiplome(int id_identite, String nomDiplome, String obtenu,
			String annee) {
		super();
		this.id_identite = id_identite;
		this.nomDiplome = nomDiplome;
		this.obtenu = obtenu;
		this.annee = annee;
	}



	public ProfilDiplome(int id_diplome, int id_identite, String nomDiplome,
			String obtenu, String annee) {
		super();
		this.id_diplome = id_diplome;
		this.id_identite = id_identite;
		this.nomDiplome = nomDiplome;
		this.obtenu = obtenu;
		this.annee = annee;
	}



	public int getId_diplome() {
		return id_diplome;
	}
	
	public String getNomDiplome() {
		return nomDiplome;
	}
	public void setNomDiplome(String nomDiplome) {
		this.nomDiplome = nomDiplome;
	}
	
	public String getObtenu() {
		return obtenu;
	}

	public void setObtenu(String obtenu) {
		this.obtenu = obtenu;
	}

	public String getAnnee() {
		return annee;
	}
	public void setAnnee(String annee) {
		this.annee = annee;
	}
	
	public void setId_diplome(int id_diplome) {
		this.id_diplome = id_diplome;
	}

	public int getId_identite() {
		return id_identite;
	}

	public void setId_identite(int id_identite) {
		this.id_identite = id_identite;
	}
	

}
