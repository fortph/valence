package beans.parametres.accueil;

public class Sexe {
	private short id_sexe;
	private String libelle_sexe;

	public Sexe() {
		super();
	}

	public Sexe(short id_sexe, String libelle_sexe) {
		super();
		this.id_sexe = id_sexe;
		this.libelle_sexe = libelle_sexe;
	}

	public short getId_sexe() {
		return id_sexe;
	}

	public void setId_sexe(short id_sexe) {
		this.id_sexe = id_sexe;
	}

	public String getLibelle_sexe() {
		return libelle_sexe;
	}

	public void setLibelle_sexe(String libelle_sexe) {
		this.libelle_sexe = libelle_sexe;
	}

}
