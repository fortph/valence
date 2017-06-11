package beans.ai;

import java.util.Date;

import beans.identite.Identite;

public class Extranet {
	private int id_extranet;
	private Identite identite;
	private Date debut;
	private Date fin;
	public int getId_extranet() {
		return id_extranet;
	}
	public void setId_extranet(int id_extranet) {
		this.id_extranet = id_extranet;
	}
	public Identite getIdentite() {
		return identite;
	}
	public void setIdentite(Identite identite) {
		this.identite = identite;
	}
	public Date getDebut() {
		return debut;
	}
	public void setDebut(Date debut) {
		this.debut = debut;
	}
	public Date getFin() {
		return fin;
	}
	public void setFin(Date fin) {
		this.fin = fin;
	}
	public Extranet(int id_extranet, Identite identite, Date debut, Date fin) {
		super();
		this.id_extranet = id_extranet;
		this.identite = identite;
		this.debut = debut;
		this.fin = fin;
	}
	public Extranet() {
		super();
	}
	
	
	public Extranet(int id_extranet, Identite identite, Date debut) {
		super();
		this.id_extranet = id_extranet;
		this.identite = identite;
		this.debut = debut;
	}
	public Extranet(Identite identite, Date debut, Date fin) {
		super();
		this.identite = identite;
		this.debut = debut;
		this.fin = fin;
	}
	public Extranet(int id_extranet, Date debut, Date fin) {
		super();
		this.id_extranet = id_extranet;
		this.debut = debut;
		this.fin = fin;
	}
	

}
