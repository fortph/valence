package beans.formation;


public class Prescripteur {
	private short id_prescripteur;
	private String prescripteur;
	
	public short getId_prescripteur() {
		return id_prescripteur;
	}
	public void setId_prescripteur(short id_prescripteur) {
		this.id_prescripteur = id_prescripteur;
	}
	public String getPrescripteur() {
		return prescripteur;
	}
	public void setPrescripteur(String prescripteur) {
		this.prescripteur = prescripteur;
	}
	public Prescripteur(short id_prescripteur, String prescripteur) {
		super();
		this.id_prescripteur = id_prescripteur;
		this.prescripteur = prescripteur;
	}
	public Prescripteur(String prescripteur) {
		super();
		this.prescripteur = prescripteur;
	}
	public Prescripteur() {
		super();
	}
	
	

}
