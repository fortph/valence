package beans.suivi;
/* créé le 10/09/2015*/

public class PropositionsFormation {

	private int id_proposition;
	private AccompagnementFormation id_accomp;
	private String nomProposition;
	private String commentaire;
	
	
	
	public PropositionsFormation() {
		super();
	}
	
	

	public PropositionsFormation(AccompagnementFormation id_accomp, String nomProposition) {
		super();
		this.id_accomp = id_accomp;
		this.nomProposition = nomProposition;
		
	}



	public PropositionsFormation(int id_proposition, AccompagnementFormation id_accomp, String nomProposition) {
		super();
		this.id_proposition = id_proposition;
		this.id_accomp = id_accomp;
		this.nomProposition = nomProposition;
		
	}

	public PropositionsFormation(int id_proposition, AccompagnementFormation id_accomp, String nomProposition, String commentaire) {
		super();
		this.id_proposition = id_proposition;
		this.id_accomp = id_accomp;
		this.nomProposition = nomProposition;
		this.commentaire = commentaire;
	}

	
	
	public PropositionsFormation(AccompagnementFormation id_accomp, String nomProposition, String commentaire) {
		super();
		this.id_accomp = id_accomp;
		this.nomProposition = nomProposition;
		this.commentaire = commentaire;
	}



	public String getCommentaire() {
		return commentaire;
	}



	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}



	


	public String getNomProposition() {
		return nomProposition;
	}



	public void setNomProposition(String nomProposition) {
		this.nomProposition = nomProposition;
	}



	public int getId_proposition() {
		return id_proposition;
	}



	public AccompagnementFormation getId_accomp() {
		return id_accomp;
	}



	public void setId_accomp(AccompagnementFormation id_accomp) {
		this.id_accomp = id_accomp;
	}



	

	
	

}
