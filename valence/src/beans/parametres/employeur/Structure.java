package beans.parametres.employeur;

public class Structure {
	private int id_structure;
	private String structure;
	private boolean actif;
	public int getId_structure() {
		return id_structure;
	}
	public void setId_structure(int id_structure) {
		this.id_structure = id_structure;
	}
	public String getStructure() {
		return structure;
	}
	public void setStructure(String structure) {
		this.structure = structure;
	}
	public boolean isActif() {
		return actif;
	}
	public void setActif(boolean actif) {
		this.actif = actif;
	}
	public Structure(int id_structure, String structure, boolean actif) {
		super();
		this.id_structure = id_structure;
		this.structure = structure;
		this.actif = actif;
	}
	public Structure() {
		super();
	}
	public Structure(String structure, boolean actif) {
		super();
		this.structure = structure;
		this.actif = actif;
	}
	

}
