package beans.mutuelle;
import java.util.Date;

import beans.identite.Identite;

public class Mutuelle {
	private int id_mutuelle;
	private Identite identite;
	private Date dateProposition;
	private boolean acceptation;
	private Date dateecheance;
	private String causeRefus;
	private Date dateEcheanceMultiEmp;
	public Mutuelle(int id_mutuelle, Identite identite, Date dateProposition, boolean acceptation, Date dateecheance,
			String causeRefus, Date dateEcheanceMultiEmp) {
		super();
		this.id_mutuelle = id_mutuelle;
		this.identite = identite;
		this.dateProposition = dateProposition;
		this.acceptation = acceptation;
		this.dateecheance = dateecheance;
		this.causeRefus = causeRefus;
		this.dateEcheanceMultiEmp = dateEcheanceMultiEmp;
	}
	
	
	public Mutuelle(int id_mutuelle, Date dateProposition, boolean acceptation, Date dateecheance, String causeRefus,
			Date dateEcheanceMultiEmp) {
		super();
		this.id_mutuelle = id_mutuelle;
		this.dateProposition = dateProposition;
		this.acceptation = acceptation;
		this.dateecheance = dateecheance;
		this.causeRefus = causeRefus;
		this.dateEcheanceMultiEmp = dateEcheanceMultiEmp;
	}


	public Mutuelle(Identite identite, Date dateProposition, boolean acceptation, Date dateecheance, String causeRefus,
			Date dateEcheanceMultiEmp) {
		super();
		this.identite = identite;
		this.dateProposition = dateProposition;
		this.acceptation = acceptation;
		this.dateecheance = dateecheance;
		this.causeRefus = causeRefus;
		this.dateEcheanceMultiEmp = dateEcheanceMultiEmp;
	}
	
	public Mutuelle(Identite identite, Date dateProposition, boolean acceptation, Date dateecheance) {
		super();
		this.identite = identite;
		this.dateProposition = dateProposition;
		this.acceptation = acceptation;
		this.dateecheance = dateecheance;
	}
	public Mutuelle() {
		super();
	}
	public int getId_mutuelle() {
		return id_mutuelle;
	}
	public void setId_mutuelle(int id_mutuelle) {
		this.id_mutuelle = id_mutuelle;
	}
	public Identite getIdentite() {
		return identite;
	}
	public void setIdentite(Identite identite) {
		this.identite = identite;
	}
	public Date getDateProposition() {
		return dateProposition;
	}
	public void setDateProposition(Date dateProposition) {
		this.dateProposition = dateProposition;
	}
	public boolean isAcceptation() {
		return acceptation;
	}
	public void setAcceptation(boolean acceptation) {
		this.acceptation = acceptation;
	}
	public Date getDateecheance() {
		return dateecheance;
	}
	public void setDateecheance(Date dateecheance) {
		this.dateecheance = dateecheance;
	}
	public String getCauseRefus() {
		return causeRefus;
	}
	public void setCauseRefus(String causeRefus) {
		this.causeRefus = causeRefus;
	}
	public Date getDateEcheanceMultiEmp() {
		return dateEcheanceMultiEmp;
	}
	public void setDateEcheanceMultiEmp(Date dateEcheanceMultiEmp) {
		this.dateEcheanceMultiEmp = dateEcheanceMultiEmp;
	}
	
	
}