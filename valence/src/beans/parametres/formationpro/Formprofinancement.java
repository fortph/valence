package beans.parametres.formationpro;

public class Formprofinancement {
	private int id_finance;
	private String finance;
	public int getId_finance() {
		return id_finance;
	}
	public void setId_finance(int id_finance) {
		this.id_finance = id_finance;
	}
	public String getFinance() {
		return finance;
	}
	public void setFinance(String finance) {
		this.finance = finance;
	}
	public Formprofinancement() {
		super();
	}
	public Formprofinancement(int id_finance, String finance) {
		super();
		this.id_finance = id_finance;
		this.finance = finance;
	}
	public Formprofinancement(String finance) {
		super();
		this.finance = finance;
	}
	

}
