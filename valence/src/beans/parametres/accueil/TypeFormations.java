package beans.parametres.accueil;

public class TypeFormations {
	private int id_proposition;
	private String nomProposition;
	public TypeFormations(int id_proposition, String nomProposition) {
		super();
		this.id_proposition = id_proposition;
		this.nomProposition = nomProposition;
	}
	public TypeFormations(String nomProposition) {
		super();
		this.nomProposition = nomProposition;
	}
	public TypeFormations() {
		super();
	}
	public String getNomProposition() {
		return nomProposition;
	}
	public void setNomProposition(String nomProposition) {
		this.nomProposition = nomProposition;
	}
	public int getId_proposition() {
		return id_proposition;
	}
}
