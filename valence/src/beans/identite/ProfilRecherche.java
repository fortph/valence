package beans.identite;

import beans.parametres.accueil.Rome;



public class ProfilRecherche {
	private int id_recherche;
	private Identite identite;
	private Rome rome;
	
	public int getId_recherche() {
		return id_recherche;
	}
	public void setId_recherche(int id_recherche) {
		this.id_recherche = id_recherche;
	}
	public Identite getIdentite() {
		return identite;
	}
	public void setIdentite(Identite identite) {
		this.identite = identite;
	}
	public Rome getRome() {
		return rome;
	}
	public void setRome(Rome rome) {
		this.rome = rome;
	}
	public ProfilRecherche(int id_recherche, Identite identite, Rome rome) {
		super();
		this.id_recherche = id_recherche;
		this.identite = identite;
		this.rome = rome;
	}
	public ProfilRecherche() {
		super();
	}
	public ProfilRecherche(Identite identite, Rome rome) {
		super();
		this.identite = identite;
		this.rome = rome;
	}
	
	
	
}