package beans.ai;

import java.util.Date;

import beans.identite.Identite;

public class Agrement {
	private int id_aifiche;
	private Identite identite;
	private String numagrement;
	private Date datedeb;
	private Date datefin;
	public int getId_aifiche() {
		return id_aifiche;
	}
	public void setId_aifiche(int id_aifiche) {
		this.id_aifiche = id_aifiche;
	}
	public Identite getIdentite() {
		return identite;
	}
	public void setIdentite(Identite identite) {
		this.identite = identite;
	}
	public String getNumagrement() {
		return numagrement;
	}
	public void setNumagrement(String numagrement) {
		this.numagrement = numagrement;
	}
	public Date getDatedeb() {
		return datedeb;
	}
	public void setDatedeb(Date datedeb) {
		this.datedeb = datedeb;
	}
	public Date getDatefin() {
		return datefin;
	}
	public void setDatefin(Date datefin) {
		this.datefin = datefin;
	}
	public Agrement(int id_aifiche, Identite identite, String numagrement,
			Date datedeb, Date datefin) {
		super();
		this.id_aifiche = id_aifiche;
		this.identite = identite;
		this.numagrement = numagrement;
		this.datedeb = datedeb;
		this.datefin = datefin;
	}
	public Agrement() {
		super();
	}
	public Agrement(Identite identite, String numagrement, Date datedeb,
			Date datefin) {
		super();
		this.identite = identite;
		this.numagrement = numagrement;
		this.datedeb = datedeb;
		this.datefin = datefin;
	}
	public Agrement(int id_aifiche, String numagrement, Date datedeb,
			Date datefin) {
		super();
		this.id_aifiche = id_aifiche;
		this.numagrement = numagrement;
		this.datedeb = datedeb;
		this.datefin = datefin;
	}
	
	

}
