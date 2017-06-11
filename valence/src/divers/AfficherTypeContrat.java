package divers;

import beans.employeurs.Offre;

public class AfficherTypeContrat {

	public String afficher(Offre offre) {
		String typecontrat="";
		boolean autre1 = false, ai1 = false, cae1 = false, avenir1 = false, cdd1 = false, cdi1 = false, alternance1 = false;
		String autre =null, ai = null, cae =null, avenir = null, cdd = null, cdi =null, alternance =null;
		autre1=offre.isContrat_autre();
		ai1=offre.isContrat_ai();
		cae1=offre.isContrat_cae();
		avenir1=offre.isContrat_avenir();
		cdd1=offre.isContrat_cdd();
		cdi1=offre.isContrat_cdi();
		alternance1=offre.isContrat_alternance();
		
		if (autre1==true)
			autre = "Autre";
		if (ai1 == true)
			ai = "AI";
		if (cae1 == true)
			cae = "CAE";
		if (avenir1 == true)
			avenir = "AVENIR";
		if (cdd1 == true)
			cdd = "CDD";
		if (cdi1 == true)
			cdi = "CDI";
		if (alternance1 == true)
			alternance ="Alternance" ;
		
		if (autre != null)
			typecontrat = autre + "-";
		if (ai != null)
			typecontrat += ai + "-";
		if (cae != null)
			typecontrat += cae + "-";
		if (avenir != null)
			typecontrat += avenir + "-";
		if (cdd != null)
			typecontrat += cdd + "-";
		if (cdi != null)
			typecontrat += cdi + "-";
		if (alternance != null)
			typecontrat += alternance;
		
		if (typecontrat.charAt((typecontrat.length()) - 1) == '-')
			typecontrat = typecontrat.substring(0,
					(typecontrat.length()) - 1);
		
		return typecontrat;

	}
}
