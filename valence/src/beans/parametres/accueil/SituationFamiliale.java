package beans.parametres.accueil;

public class SituationFamiliale {
	private short id_SF;
	private String libelle_SF;

	public SituationFamiliale(short id_SF, String libelle_SF) {
		super();
		this.id_SF = id_SF;
		this.libelle_SF = libelle_SF;
	}

	public SituationFamiliale() {
		super();
	}

	public short getId_SF() {
		return id_SF;
	}

	public void setId_SF(short id_SF) {
		this.id_SF = id_SF;
	}

	public String getLibelle_SF() {
		return libelle_SF;
	}

	public void setLibelle_SF(String libelle_SF) {
		this.libelle_SF = libelle_SF;
	}

}
