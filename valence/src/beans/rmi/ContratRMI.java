package beans.rmi;

import java.util.Date;

import beans.identite.Identite;

public class ContratRMI {
	private int id_rmicontrat;
	private Identite identite;
	private Date datedeb_rmicontrat;
	private Date datefin_rmicontrat;
	
	public int getId_rmicontrat() {
		return id_rmicontrat;
	}
	public void setId_rmicontrat(int id_rmicontrat) {
		this.id_rmicontrat = id_rmicontrat;
	}
	public Identite getIdentite() {
		return identite;
	}
	public void setIdentite(Identite identite) {
		this.identite = identite;
	}
	public Date getDatedeb_rmicontrat() {
		return datedeb_rmicontrat;
	}
	public void setDatedeb_rmicontrat(Date datedeb_rmicontrat) {
		this.datedeb_rmicontrat = datedeb_rmicontrat;
	}
	public Date getDatefin_rmicontrat() {
		return datefin_rmicontrat;
	}
	public void setDatefin_rmicontrat(Date datefin_rmicontrat) {
		this.datefin_rmicontrat = datefin_rmicontrat;
	}
	public ContratRMI(int id_rmicontrat, Identite identite,
			Date datedeb_rmicontrat, Date datefin_rmicontrat) {
		super();
		this.id_rmicontrat = id_rmicontrat;
		this.identite = identite;
		this.datedeb_rmicontrat = datedeb_rmicontrat;
		this.datefin_rmicontrat = datefin_rmicontrat;
	}
	public ContratRMI() {
		super();
	}
	public ContratRMI(Identite identite, Date datedeb_rmicontrat,
			Date datefin_rmicontrat) {
		super();
		this.identite = identite;
		this.datedeb_rmicontrat = datedeb_rmicontrat;
		this.datefin_rmicontrat = datefin_rmicontrat;
	}
	
	
	

}
