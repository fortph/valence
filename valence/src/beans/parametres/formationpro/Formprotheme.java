package beans.parametres.formationpro;

public class Formprotheme {
	
	private int id_theme;
	private String theme;
	public int getId_theme() {
		return id_theme;
	}
	public void setId_theme(int id_theme) {
		this.id_theme = id_theme;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public Formprotheme(int id_theme, String theme) {
		super();
		this.id_theme = id_theme;
		this.theme = theme;
	}
	public Formprotheme() {
		super();
	}
	public Formprotheme(String theme) {
		super();
		this.theme = theme;
	}
	

}
