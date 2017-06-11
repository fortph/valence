package divers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import beans.identite.Identite;
import beans.identite.ProfilPriorite;
import dao.exception.DAOException;
import dao.imp.identite.ProfilPrioriteDAO;

public class AfficherPriorites {

	public String listePrioritesPersonne(Identite id) {
		String maliste = "";
		Date date = new Date();

		ProfilPrioriteDAO prodao = new ProfilPrioriteDAO();
		List<ProfilPriorite> profil = null;
		try {
			profil = prodao.afficherPrioriteParIdentite(id);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (profil.size() > 0) {
			for (int i = 0; i < profil.size(); i++) {
				if(profil.get(i).getExpire()!=null){
				if (date.before(profil.get(i).getExpire()))
					maliste = maliste + profil.get(i).getLibelle() + "-";

			}
				else
					maliste = maliste + profil.get(i).getLibelle() + "-";
				}

		}
		//si ma liste n'est pas vide on supprime le dernier tiret
		if(maliste.length()>0){
		if (maliste.charAt((maliste.length()) - 1) == '-')
			maliste = maliste.substring(0, (maliste.length()) - 1);
		}
		
		return maliste;

	}
	public List<String> listingPrioritesPersonne(Identite id) {
		List<String> maliste = new ArrayList<String>();
		Date date = new Date();

		ProfilPrioriteDAO prodao = new ProfilPrioriteDAO();
		List<ProfilPriorite> profil = null;
		try {
			profil = prodao.afficherPrioriteParIdentite(id);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (profil.size() > 0) {
			for (int i = 0; i < profil.size(); i++) {
				if(profil.get(i).getExpire()!=null){
				if (date.before(profil.get(i).getExpire())){
						maliste.add(profil.get(i).getLibelle() );
					}

			}
				else{
					
					maliste.add(profil.get(i).getLibelle());
				}
				}
		}

		
		
		return maliste;

	}


}
