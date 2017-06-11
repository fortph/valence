package beans.parametres.accueil;

public class Rome {
	private int idrome;
	private String nrome;
	private String intitule;
	private short rang;
	private boolean actif;
	public int getIdrome() {
		return idrome;
	}
	public void setIdrome(int idrome) {
		this.idrome = idrome;
	}
	public String getNrome() {
		return nrome;
	}
	public void setNrome(String nrome) {
		this.nrome = nrome;
	}
	public String getIntitule() {
		return intitule;
	}
	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}
	public short getRang() {
		return rang;
	}
	public void setRang(short rang) {
		this.rang = rang;
	}
	public boolean isActif() {
		return actif;
	}
	public void setActif(boolean actif) {
		this.actif = actif;
	}
	public Rome() {
		super();
	}
	public Rome(int idrome, String nrome, String intitule, short rang,
			boolean actif) {
		super();
		this.idrome = idrome;
		this.nrome = nrome;
		this.intitule = intitule;
		this.rang = rang;
		this.actif = actif;
	}
	public Rome(String nrome, String intitule, short rang, boolean actif) {
		super();
		this.nrome = nrome;
		this.intitule = intitule;
		this.rang = rang;
		this.actif = actif;
	}
	public Rome(int idrome, String nrome, String intitule) {
		super();
		this.idrome = idrome;
		this.nrome = nrome;
		this.intitule = intitule;
	}
	public Rome(String intitule) {
		super();
		this.intitule = intitule;
	}
	
	
	
}
