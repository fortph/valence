package divers;

public class NomUtilisateur {
private String nom;

public NomUtilisateur() {
	super();
}

public String getNom() {
	return nom;
}

public void setNom(String nom) {
	this.nom = nom;
}
/**affiche le nom d'utilisateur de la machine*/
public String afficherNom(){
	return System.getProperty("user.name");
}
}
