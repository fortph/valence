package beans.parametres.formationpro;

public class Formproniveau {
	private int id_niveau;
	private String niveau;
	public Formproniveau() {
		super();
	}
	public Formproniveau(int id_niveau, String niveau) {
		super();
		this.id_niveau = id_niveau;
		this.niveau = niveau;
	}
	public Formproniveau(String niveau) {
		super();
		this.niveau = niveau;
	}
	public int getId_niveau() {
		return id_niveau;
	}
	public void setId_niveau(int id_niveau) {
		this.id_niveau = id_niveau;
	}
	public String getNiveau() {
		return niveau;
	}
	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}
	

}
