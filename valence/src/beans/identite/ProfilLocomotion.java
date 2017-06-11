package beans.identite;


public class ProfilLocomotion {
	
	private int id_locomotion;
	private String locomotion;
	private int id_identite;
	public int getId_locomotion() {
		return id_locomotion;
	}
	public void setId_locomotion(int id_locomotion) {
		this.id_locomotion = id_locomotion;
	}
	public String getLocomotion() {
		return locomotion;
	}
	public void setLocomotion(String locomotion) {
		this.locomotion = locomotion;
	}
	public int getId_identite() {
		return id_identite;
	}
	public void setId_identite(int id_identite) {
		this.id_identite = id_identite;
	}
	public ProfilLocomotion(int id_locomotion, String locomotion,
			int id_identite) {
		super();
		this.id_locomotion = id_locomotion;
		this.locomotion = locomotion;
		this.id_identite = id_identite;
	}
	public ProfilLocomotion(String locomotion, int id_identite) {
		super();
		this.locomotion = locomotion;
		this.id_identite = id_identite;
	}
	public ProfilLocomotion() {
		super();
	}
}
	
	