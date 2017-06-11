package beans.ai;

import java.util.Date;

import beans.identite.Identite;

public class Creation {
	private int id_aifiche;
	private Identite identite;
	private boolean permanent;
	private String ndue;
	private Date datendue;

	
	public Creation(int id_aifiche, Identite identite, boolean permanent,
			String ndue, Date datendue) {
		super();
		this.id_aifiche = id_aifiche;
		this.identite = identite;
		this.permanent = permanent;
		this.ndue = ndue;
		this.datendue = datendue;
	}
	public Creation() {
		super();
	}
	public Creation(Identite identite, boolean permanent, String ndue,
			Date datendue) {
		super();
		this.identite = identite;
		this.permanent = permanent;
		this.ndue = ndue;
		this.datendue = datendue;
		
	}
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
	public boolean isPermanent() {
		return permanent;
	}
	public void setPermanent(boolean permanent) {
		this.permanent = permanent;
	}
	public String getNdue() {
		return ndue;
	}
	public void setNdue(String ndue) {
		this.ndue = ndue;
	}
	public Date getDatendue() {
		return datendue;
	}
	public void setDatendue(Date datendue) {
		this.datendue = datendue;
	}
}
	