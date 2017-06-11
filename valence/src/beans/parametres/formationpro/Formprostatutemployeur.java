package beans.parametres.formationpro;

public class Formprostatutemployeur {
	private int id_statut;
	private String statut;
	public int getId_statut() {
		return id_statut;
	}
	public void setId_statut(int id_statut) {
		this.id_statut = id_statut;
	}
	public String getStatut() {
		return statut;
	}
	public void setStatut(String statut) {
		this.statut = statut;
	}
	public Formprostatutemployeur() {
		super();
	}
	public Formprostatutemployeur(int id_statut, String statut) {
		super();
		this.id_statut = id_statut;
		this.statut = statut;
	}
	public Formprostatutemployeur(String statut) {
		super();
		this.statut = statut;
	}
	

}
