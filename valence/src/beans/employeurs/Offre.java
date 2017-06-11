package beans.employeurs;

import java.util.Date;

import beans.parametres.accueil.Rome;
import beans.parametres.capemploi.Utilisateur;

public class Offre {
	
	private int id_offre;
	private Employeur employeur;
	private Contact contact;
	private Date dateSaisie;
	private Utilisateur salarie;
	private Rome rome;
	private int nbpersonnes;
	private Date datedeb_offre;
	private Date datefin_offre;
	private String duree_offre;
	private boolean contrat_autre;
	private boolean contrat_ai;
	private boolean contrat_cae;
	private boolean contrat_avenir;
	private boolean contrat_cdd;
	private boolean contrat_cdi;
	private boolean contrat_alternance;
	private String detail;
	private boolean pourvue;
	private String observation;
	private String jour;
	private String heures;
	private String dureestats;
	private boolean annule_offre;
	private boolean autrerecrute;
	public int getId_offre() {
		return id_offre;
	}
	public void setId_offre(int id_offre) {
		this.id_offre = id_offre;
	}
	public Employeur getEmployeur() {
		return employeur;
	}
	public void setEmployeur(Employeur employeur) {
		this.employeur = employeur;
	}
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public Date getDateSaisie() {
		return dateSaisie;
	}
	public void setDateSaisie(Date dateSaisie) {
		this.dateSaisie = dateSaisie;
	}
	public Utilisateur getSalarie() {
		return salarie;
	}
	public void setSalarie(Utilisateur salarie) {
		this.salarie = salarie;
	}
	public Rome getRome() {
		return rome;
	}
	public void setRome(Rome rome) {
		this.rome = rome;
	}
	public int getNbpersonnes() {
		return nbpersonnes;
	}
	public void setNbpersonnes(int nbpersonnes) {
		this.nbpersonnes = nbpersonnes;
	}
	public Date getDatedeb_offre() {
		return datedeb_offre;
	}
	public void setDatedeb_offre(Date datedeb_offre) {
		this.datedeb_offre = datedeb_offre;
	}
	public Date getDatefin_offre() {
		return datefin_offre;
	}
	public void setDatefin_offre(Date datefin_offre) {
		this.datefin_offre = datefin_offre;
	}
	public String getDuree_offre() {
		return duree_offre;
	}
	public void setDuree_offre(String duree_offre) {
		this.duree_offre = duree_offre;
	}
	public boolean isContrat_autre() {
		return contrat_autre;
	}
	public void setContrat_autre(boolean contrat_autre) {
		this.contrat_autre = contrat_autre;
	}
	public boolean isContrat_ai() {
		return contrat_ai;
	}
	public void setContrat_ai(boolean contrat_ai) {
		this.contrat_ai = contrat_ai;
	}
	public boolean isContrat_cae() {
		return contrat_cae;
	}
	public void setContrat_cae(boolean contrat_cae) {
		this.contrat_cae = contrat_cae;
	}
	public boolean isContrat_avenir() {
		return contrat_avenir;
	}
	public void setContrat_avenir(boolean contrat_avenir) {
		this.contrat_avenir = contrat_avenir;
	}
	public boolean isContrat_cdd() {
		return contrat_cdd;
	}
	public void setContrat_cdd(boolean contrat_cdd) {
		this.contrat_cdd = contrat_cdd;
	}
	public boolean isContrat_cdi() {
		return contrat_cdi;
	}
	public void setContrat_cdi(boolean contrat_cdi) {
		this.contrat_cdi = contrat_cdi;
	}
	public boolean isContrat_alternance() {
		return contrat_alternance;
	}
	public void setContrat_alternance(boolean contrat_alternance) {
		this.contrat_alternance = contrat_alternance;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public boolean isPourvue() {
		return pourvue;
	}
	public void setPourvue(boolean pourvue) {
		this.pourvue = pourvue;
	}
	public String getObservation() {
		return observation;
	}
	public void setObservation(String observation) {
		this.observation = observation;
	}
	public String getJour() {
		return jour;
	}
	public void setJour(String jour) {
		this.jour = jour;
	}
	public String getHeures() {
		return heures;
	}
	public void setHeures(String heures) {
		this.heures = heures;
	}
	public String getDureestats() {
		return dureestats;
	}
	public void setDureestats(String dureestats) {
		this.dureestats = dureestats;
	}
	public boolean isAnnule_offre() {
		return annule_offre;
	}
	public void setAnnule_offre(boolean annule_offre) {
		this.annule_offre = annule_offre;
	}
	public boolean isAutrerecrute() {
		return autrerecrute;
	}
	public void setAutrerecrute(boolean autrerecrute) {
		this.autrerecrute = autrerecrute;
	}
	public Offre(int id_offre, Employeur employeur, Contact contact,
			Date dateSaisie, Utilisateur salarie, Rome rome, int nbpersonnes,
			Date datedeb_offre, Date datefin_offre, String duree_offre,
			boolean contrat_autre, boolean contrat_ai, boolean contrat_cae,
			boolean contrat_avenir, boolean contrat_cdd, boolean contrat_cdi,
			boolean contrat_alternance, String detail, boolean pourvue,
			String observation, String jour, String heures, String dureestats,
			boolean annule_offre, boolean autrerecrute) {
		super();
		this.id_offre = id_offre;
		this.employeur = employeur;
		this.contact = contact;
		this.dateSaisie = dateSaisie;
		this.salarie = salarie;
		this.rome = rome;
		this.nbpersonnes = nbpersonnes;
		this.datedeb_offre = datedeb_offre;
		this.datefin_offre = datefin_offre;
		this.duree_offre = duree_offre;
		this.contrat_autre = contrat_autre;
		this.contrat_ai = contrat_ai;
		this.contrat_cae = contrat_cae;
		this.contrat_avenir = contrat_avenir;
		this.contrat_cdd = contrat_cdd;
		this.contrat_cdi = contrat_cdi;
		this.contrat_alternance = contrat_alternance;
		this.detail = detail;
		this.pourvue = pourvue;
		this.observation = observation;
		this.jour = jour;
		this.heures = heures;
		this.dureestats = dureestats;
		this.annule_offre = annule_offre;
		this.autrerecrute = autrerecrute;
	}
	public Offre() {
		super();
	}
	public Offre(Employeur employeur, Contact contact, Date dateSaisie,
			Utilisateur salarie, Rome rome, int nbpersonnes,
			Date datedeb_offre, Date datefin_offre, String duree_offre,
			boolean contrat_autre, boolean contrat_ai, boolean contrat_cae,
			boolean contrat_avenir, boolean contrat_cdd, boolean contrat_cdi,
			boolean contrat_alternance, String detail, boolean pourvue,
			String observation, String jour, String heures, String dureestats,
			boolean annule_offre, boolean autrerecrute) {
		super();
		this.employeur = employeur;
		this.contact = contact;
		this.dateSaisie = dateSaisie;
		this.salarie = salarie;
		this.rome = rome;
		this.nbpersonnes = nbpersonnes;
		this.datedeb_offre = datedeb_offre;
		this.datefin_offre = datefin_offre;
		this.duree_offre = duree_offre;
		this.contrat_autre = contrat_autre;
		this.contrat_ai = contrat_ai;
		this.contrat_cae = contrat_cae;
		this.contrat_avenir = contrat_avenir;
		this.contrat_cdd = contrat_cdd;
		this.contrat_cdi = contrat_cdi;
		this.contrat_alternance = contrat_alternance;
		this.detail = detail;
		this.pourvue = pourvue;
		this.observation = observation;
		this.jour = jour;
		this.heures = heures;
		this.dureestats = dureestats;
		this.annule_offre = annule_offre;
		this.autrerecrute = autrerecrute;
	}
	
	public Offre(int id_offre, Employeur employeur, Rome rome,
			Date datedeb_offre, boolean contrat_autre, boolean contrat_ai,
			boolean contrat_cae, boolean contrat_avenir, boolean contrat_cdd,
			boolean contrat_cdi, boolean contrat_alternance, boolean pourvue,
			boolean annule_offre, boolean autrerecrute) {
		super();
		this.id_offre = id_offre;
		this.employeur = employeur;
		this.rome = rome;
		this.datedeb_offre = datedeb_offre;
		this.contrat_autre = contrat_autre;
		this.contrat_ai = contrat_ai;
		this.contrat_cae = contrat_cae;
		this.contrat_avenir = contrat_avenir;
		this.contrat_cdd = contrat_cdd;
		this.contrat_cdi = contrat_cdi;
		this.contrat_alternance = contrat_alternance;
		this.pourvue = pourvue;
		this.annule_offre = annule_offre;
		this.autrerecrute = autrerecrute;
	}
	public Offre(int id_offre, Rome rome, Date datedeb_offre,
			boolean contrat_autre, boolean contrat_ai, boolean contrat_cae,
			boolean contrat_avenir, boolean contrat_cdd, boolean contrat_cdi,
			boolean contrat_alternance, boolean pourvue, String dureestats,
			boolean annule_offre, boolean autrerecrute) {
		super();
		this.id_offre = id_offre;
		this.rome = rome;
		this.datedeb_offre = datedeb_offre;
		this.contrat_autre = contrat_autre;
		this.contrat_ai = contrat_ai;
		this.contrat_cae = contrat_cae;
		this.contrat_avenir = contrat_avenir;
		this.contrat_cdd = contrat_cdd;
		this.contrat_cdi = contrat_cdi;
		this.contrat_alternance = contrat_alternance;
		this.pourvue = pourvue;
		this.dureestats = dureestats;
		this.annule_offre = annule_offre;
		this.autrerecrute = autrerecrute;
	}
	public Offre(int id_offre, Rome rome) {
		super();
		this.id_offre = id_offre;
		this.rome = rome;
	}
	
	
}
	