package beans.employeurs;

public class Service {
	private int id_employeurservice;
	private Employeur id_employeur;
	private String service;
	public int getId_employeurservice() {
		return id_employeurservice;
	}
	public void setId_employeurservice(int id_employeurservice) {
		this.id_employeurservice = id_employeurservice;
	}
	public Employeur getId_employeur() {
		return id_employeur;
	}
	public void setId_employeur(Employeur id_employeur) {
		this.id_employeur = id_employeur;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public Service() {
		super();
	}
	public Service(int id_employeurservice, Employeur id_employeur,
			String service) {
		super();
		this.id_employeurservice = id_employeurservice;
		this.id_employeur = id_employeur;
		this.service = service;
	}
	public Service(Employeur id_employeur, String service) {
		super();
		this.id_employeur = id_employeur;
		this.service = service;
	}
	
	
}