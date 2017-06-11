package beans.formation;



public class NiveauQualificationFormation {
private byte id_niveau;
private String libelle_niv;

public byte getId_niveau() {
	return id_niveau;
}
public void setId_niveau(byte id_niveau) {
	this.id_niveau = id_niveau;
}
public String getLibelle_niv() {
	return libelle_niv;
}
public void setLibelle_niv(String libelle_niv) {
	this.libelle_niv = libelle_niv;
}
public NiveauQualificationFormation(String libelle_niv) {
	super();
	this.libelle_niv = libelle_niv;
}
public NiveauQualificationFormation(byte id_niveau, String libelle_niv) {
	super();
	this.id_niveau = id_niveau;
	this.libelle_niv = libelle_niv;
}
public NiveauQualificationFormation() {
	super();
}

}
