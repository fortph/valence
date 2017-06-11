package dao.imp.identite;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.identite.Identite;
import beans.identite.ProfilDiplome;
import beans.identite.ProfilLocomotion;
import beans.identite.ProfilPermisPro;
import beans.identite.ProfilPriorite;
import beans.identite.ProfilRecherche;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.interfaces.DAO;
import divers.FormaterChaine;

public class IdentiteDAO implements DAO<Identite> {

	final private String query_insert = "insert into "
			+ DAOConstants.t_identite
			+ "( sex_identite, nom_identite,"
			+ " prenom_identite,  nomjf_identite, adr1_identite, adr2_identite,cp_identite, ville_identite,"
			+ "mobil_identite,tel1_identite,autrephone_identite,phonelocalisation_identite,mail_identite,"
			+ "datenais_identite,lieunais_identite,paysnais_identite,deptnais_identite,nationalite_identite,"
			+ " ncs_identite, cs_expiration_identite, nss_identite, sitfam_identite, enfants_identite,"
			+ " dateaccueil_identite, permib_identite, statutfiche_identite,poleEmploiInscription_identite,"
			+ "poleEmploiID_identite,niveauFormation_identite) "
			+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"
			+ ",?,?,?,?,?,?,?,?,?,?,?,?,?)";
	final private String query_update = "update "
			+ DAOConstants.t_identite
			+ " set sex_identite=?, nom_identite=?,"
			+ " prenom_identite=?,  nomjf_identite=?, adr1_identite=?, adr2_identite=?,cp_identite=?, ville_identite=?,mobil_identite=?,"
			+ "tel1_identite=?, autrephone_identite=?, phonelocalisation_identite=?, mail_identite=?,datenais_identite=?,"
			+ "lieunais_identite=?, paysnais_identite=?,deptnais_identite=?,nationalite_identite=?, ncs_identite=?, "
			+ "cs_expiration_identite=?, nss_identite=?, sitfam_identite=?, enfants_identite=?, dateAccueil_identite=?, "
			+ "permib_identite=?, statutfiche_identite=?,niveauFormation_identite=?,poleEmploiInscription_identite=?,"
			+ "poleEmploiID_identite=? where id_identite=";
	final private String query_delete = "delete from "
			+ DAOConstants.t_identite + " where id_identite=";

	private Connection connect = null;

	public IdentiteDAO() {
	}

	@Override
	public Identite findByID(int id) throws DAOException {
		String query = "select  `id_identite`, `sex_identite`, `nom_identite`,"
				+ " `prenom_identite`, `nomjf_identite`, `adr1_identite`, "
				+ "`adr2_identite`, `cp_identite`, `ville_identite`, `mobil_identite`,"
				+ " `tel1_identite`, `autrephone_identite`,"
				+ " `phonelocalisation_identite`, `mail_identite`, `datenais_identite`,"
				+ " `lieunais_identite`, `paysnais_identite`, `deptnais_identite`, "
				+ "`nationalite_identite`, `ncs_identite`, `cs_expiration_identite`,"
				+ " `nss_identite`, `sitfam_identite`, `enfants_identite`,"
				+ " `dateaccueil_identite`, `permib_identite`, `statutfiche_identite`,"
				+ " `poleEmploiInscription_identite`, `poleEmploiID_identite`, "
				+ "`niveauFormation_identite` from "
				+ DAOConstants.t_identite
				+ " where  id_identite=" + id;
		Identite identite = null;
		Statement st = null;
		ResultSet res = null;
		try {
			connect = DBConnexion.getConnexion();
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				identite = new Identite((int) id, res.getString(2),
						res.getString(3), res.getString(4), res.getString(5),
						res.getString(6), res.getString(7), res.getString(8),
						res.getString(9), res.getString(10), res.getString(11),
						res.getString(12), res.getString(13),
						res.getString(14), res.getDate(15), res.getString(16),
						res.getString(17), res.getString(18),
						res.getString(19), res.getString(20), res.getDate(21),
						res.getString(22), res.getString(23), res.getShort(24),
						res.getDate(25), res.getBoolean(26),
						res.getBoolean(27), res.getDate(28), res.getString(29),
						res.getString(30));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return identite;

	}

	@Override
	public List<Identite> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Identite> findByCriteria(Identite obj) throws DAOException {
		ArrayList<Identite> liste = new ArrayList<Identite>();
		Identite identite = null;
		Statement st = null;
		ResultSet res = null;
		
		String req = "select  `id_identite`, `sex_identite`, `nom_identite`,"
				+ " `prenom_identite`, `nomjf_identite`, `adr1_identite`, "
				+ "`adr2_identite`, `cp_identite`, `ville_identite`, `mobil_identite`,"
				+ " `tel1_identite`, `autrephone_identite`,"
				+ " `phonelocalisation_identite`, `mail_identite`, `datenais_identite`,"
				+ " `lieunais_identite`, `paysnais_identite`, `deptnais_identite`, "
				+ "`nationalite_identite`, `ncs_identite`, `cs_expiration_identite`,"
				+ " `nss_identite`, `sitfam_identite`, `enfants_identite`,"
				+ " `dateaccueil_identite`, `permib_identite`, `statutfiche_identite`,"
				+ " `poleEmploiInscription_identite`, `poleEmploiID_identite`, "
				+ "`niveauFormation_identite` from "
				+ DAOConstants.t_identite
				+ " where 1=1 and  statutfiche_identite=1 ";
		if (obj.getId_IDE() != 0)
			req += "and id_identite =" + obj.getId_IDE();

		if (obj.getNom_IDE() != null)
			req += " AND UPPER(nom_identite) LIKE '"
					+ obj.getNom_IDE().toUpperCase() + "'";
		if (obj.getSexe_IDE() != null)
			req += " AND UPPER(sexe_identite) LIKE '"
					+ obj.getSexe_IDE().toUpperCase() + "'";
		if (obj.getPoleEmploi_ID_IDE() != null)
			req += " AND UPPER(poleEmploiInscripription_identite) LIKE '"
					+ obj.getPoleEmploiInscripription_IDE() + "'";

		try {
			connect = DBConnexion.getConnexion();
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {

				identite = new Identite(res.getInt(1), res.getString(2),
						res.getString(3), res.getString(4), res.getString(5),
						res.getString(6), res.getString(7), res.getString(8),
						res.getString(9), res.getString(10), res.getString(11),
						res.getString(12), res.getString(13),
						res.getString(14), res.getDate(15), res.getString(16),
						res.getString(17), res.getString(18),
						res.getString(19), res.getString(20), res.getDate(21),
						res.getString(22), res.getString(23), res.getShort(24),
						res.getDate(25), res.getBoolean(26),
						res.getBoolean(27), res.getDate(28), res.getString(29),
						res.getString(30));
				liste.add(identite);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return liste;

	}

	public List<Identite> findByName(String nom) throws DAOException {
		ArrayList<Identite> liste = new ArrayList<Identite>();
		Identite identite = null;
		Statement st = null;
		ResultSet res = null;
		String req = "select id_identite,nom_identite,prenom_identite,ville_identite,"
				+ "mobil_identite, tel1_identite,datenais_identite,"
				+ "lieunais_identite,nss_identite from "
				+ DAOConstants.t_identite
				+ "  where nom_identite like '"
				+ nom
				+ "%' order by nom_identite asc ";
		
		try {
			connect = DBConnexion.getConnexion();
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {

				identite = new Identite(res.getInt(1), res.getString(2),
						res.getString(3), res.getString(4), res.getString(5),
						res.getString(6), res.getDate(7), res.getString(8),
						res.getString(9));
				liste.add(identite);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return liste;

	}

	public List<Identite> findByName2(String nom) throws DAOException {
		ArrayList<Identite> liste = new ArrayList<Identite>();
		String formatenom=new FormaterChaine().supprimerApostrophe(nom);
		Identite identite = null;
		Statement st = null;
		ResultSet res = null;
		String req = "select id_identite,nom_identite,prenom_identite,ville_identite,"
				+ "mobil_identite, tel1_identite,datenais_identite,"
				+ "lieunais_identite,nss_identite from "
				+ DAOConstants.t_identite
				+ "  where concat(nom_identite, ' ',prenom_identite) like '"
				+ formatenom + "' order by nom_identite asc ";
		
		try {
			connect = DBConnexion.getConnexion();
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {

				identite = new Identite(res.getInt(1), res.getString(2),
						res.getString(3), res.getString(4), res.getString(5),
						res.getString(6), res.getDate(7), res.getString(8),
						res.getString(9));
				liste.add(identite);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return liste;

	}

	@Override
	public int create(Identite obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect = DBConnexion.getConnexion();
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);
			// pst.setLong(1, obj.getId_IDE());
			pst.setString(1, obj.getSexe_IDE());
			pst.setString(2, obj.getNom_IDE().toUpperCase());
			pst.setString(3, obj.getPrenom_IDE());
			pst.setString(4, obj.getNomjf_IDE());
			pst.setString(5, obj.getAdr1_IDE());
			pst.setString(6, obj.getAdr2_IDE());
			pst.setString(7, obj.getCp_IDE());
			pst.setString(8, obj.getVille_IDE());
			pst.setString(9, obj.getMobile_IDE());
			pst.setString(10, obj.getFixe_IDE());
			pst.setString(11, obj.getPhone3_IDE());
			pst.setString(12, obj.getPhone3Localisation_IDE());
			pst.setString(13, obj.getMail_IDE());
			pst.setDate(14, (java.sql.Date) obj.getDatenais_IDE());
			pst.setString(15, obj.getLieunais_IDE());
			pst.setString(16, obj.getPaysnais_IDE());
			pst.setString(17, obj.getDeptnais_IDE());
			pst.setString(18, obj.getNationalite_IDE());
			pst.setString(19, obj.getNcs_IDE());
			pst.setDate(20, (java.sql.Date) obj.getCs_expiration_IDE());
			pst.setString(21, obj.getNss_IDE());
			pst.setString(22, obj.getSitfam_IDE());
			pst.setInt(23, obj.getEnfants_IDE());
			pst.setDate(24, (java.sql.Date) obj.getDateAccueil_IDE());
			pst.setBoolean(25, obj.isPermib_IDE());
			pst.setBoolean(26, obj.isStatutfiche_IDE());
			pst.setDate(27,
					(java.sql.Date) obj.getPoleEmploiInscripription_IDE());
			pst.setString(28, obj.getPoleEmploi_ID_IDE());
			pst.setString(29, obj.getNiveauFormation_IDE());
			pst.executeUpdate();
			ResultSet rs = pst.getGeneratedKeys();
			if (rs != null && rs.next())
				cle = rs.getInt(1);
			connect.commit();

		} catch (SQLException e) {
			try {
				connect.rollback();
				e.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			
			// Fermeture du statement
			DAOUtil.closePrepareStatement(pst);
		}
		return cle;

	}

	@Override
	public Identite update(Identite obj) throws DAOException {
		PreparedStatement pst = null;

		ProfilDiplome prodip = new ProfilDiplome();
		prodip.setId_identite(obj.getId_IDE());
		ProfilDiplomeDAO prodipdao = new ProfilDiplomeDAO();
		List<ProfilDiplome> listedip = prodipdao.findByCriteria(prodip);

		ProfilLocomotion proloc = new ProfilLocomotion();
		proloc.setId_identite(obj.getId_IDE());
		ProfilLocomotionDAO prolocdao = new ProfilLocomotionDAO();
		List<ProfilLocomotion> listeloc = prolocdao.findByCriteria(proloc);

		ProfilPermisProDAO properdao = new ProfilPermisProDAO();
		List<ProfilPermisPro> listeperm = properdao.listingPermis(obj);

		ProfilPriorite propri = new ProfilPriorite();
		propri.setId_identite(obj.getId_IDE());
		ProfilPrioriteDAO propridao = new ProfilPrioriteDAO();
		List<ProfilPriorite> listeprio = propridao.findByCriteria(propri);

		ProfilRecherche prorec = new ProfilRecherche();
		prorec.setIdentite(obj);
		ProfilRechercheDAO prorecdao = new ProfilRechercheDAO();
		List<ProfilRecherche> listerech = prorecdao.findByCriteria(prorec);

		String query = query_update + obj.getId_IDE();
		try {
			connect = DBConnexion.getConnexion();
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.setString(1, obj.getSexe_IDE());
			pst.setString(2, obj.getNom_IDE());
			pst.setString(3, obj.getPrenom_IDE());
			pst.setString(4, obj.getNomjf_IDE());
			pst.setString(5, obj.getAdr1_IDE());
			pst.setString(6, obj.getAdr2_IDE());
			pst.setString(7, obj.getCp_IDE());
			pst.setString(8, obj.getVille_IDE());
			pst.setString(9, obj.getMobile_IDE());
			pst.setString(10, obj.getFixe_IDE());
			pst.setString(11, obj.getPhone3_IDE());
			pst.setString(12, obj.getPhone3Localisation_IDE());
			pst.setString(13, obj.getMail_IDE());
			pst.setDate(14, (java.sql.Date) obj.getDatenais_IDE());
			pst.setString(15, obj.getLieunais_IDE());
			pst.setString(16, obj.getPaysnais_IDE());
			pst.setString(17, obj.getDeptnais_IDE());
			pst.setString(18, obj.getNationalite_IDE());
			pst.setString(19, obj.getNcs_IDE());
			pst.setDate(20, (java.sql.Date) obj.getCs_expiration_IDE());
			pst.setString(21, obj.getNss_IDE());
			pst.setString(22, obj.getSitfam_IDE());
			pst.setInt(23, obj.getEnfants_IDE());
			pst.setDate(24, (java.sql.Date) obj.getDateAccueil_IDE());
			pst.setBoolean(25, obj.isPermib_IDE());
			pst.setBoolean(26, obj.isStatutfiche_IDE());
			pst.setString(27, obj.getNiveauFormation_IDE());
			pst.setDate(28,
					(java.sql.Date) obj.getPoleEmploiInscripription_IDE());

			pst.setString(29, obj.getPoleEmploi_ID_IDE());

			// mise a jour des tables liees a identite
			for (int i = 0; i < listedip.size(); i++) {
				prodipdao.delete(listedip.get(i).getId_diplome());
			}

			for (int i = 0; i < listeloc.size(); i++) {
				prolocdao.delete(listeloc.get(i).getId_locomotion());
			}

			for (int i = 0; i < listeperm.size(); i++) {
				properdao.delete(listeperm.get(i).getId_permis());
			}

			for (int i = 0; i < listeprio.size(); i++) {
				propridao.delete(listeprio.get(i).getId_priorite());
			}

			for (int i = 0; i < listerech.size(); i++) {
				System.out.println("id recherche ="
						+ listerech.get(i).getId_recherche());
				prorecdao.delete(listerech.get(i).getId_recherche());
			}

			pst.executeUpdate();
			connect.commit();

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			
			// Fermeture du statement
			DAOUtil.closePrepareStatement(pst);
		}

		return obj;

	}

	@Override
	public int delete(Identite obj) throws DAOException {
		long num = obj.getId_IDE();
		return delete(num);
	}

	@Override
	public int delete(long id) throws DAOException {
		String query = query_delete + id;
		int retour = 0;
		Statement st = null;
		try {
			connect = DBConnexion.getConnexion();
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			retour = st.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return retour;
	}

	public List<Identite> afficherStatistiques(Date debut, Date fin,
			String sexe, String age, String formation, String pe, String prio,
			String origine) throws DAOException {
		Statement stat = null;
		ResultSet rs = null;
		String req = "select " + DAOConstants.t_identite + ".id_identite,"
				+ DAOConstants.t_identite + ".nom_identite,"
				+ DAOConstants.t_identite + ".prenom_identite,"
				+ DAOConstants.t_identite + ".ville_identite,"
				+ DAOConstants.t_identite + ".dateaccueil_identite,"
				+ DAOConstants.t_identite + ".cp_identite from "
				+ DAOConstants.t_identite;

		if (prio.equals("vide")) {
			req += " where  dateaccueil_identite between '" + debut + "' and '"
					+ fin + "'";

		} else {
			req += " inner join " + DAOConstants.t_priorite + " on "
					+ DAOConstants.t_priorite + ".id_identite="
					+ DAOConstants.t_identite
					+ ".id_identite where libelle_priorite like '" + prio
					+ "%' and dateaccueil_identite between '" + debut
					+ "' and '" + fin + "'";

		}
		if (!sexe.equals("vide")) {
			req += " and sex_identite='" + sexe + "'";
		}

		if (!age.equals("vide")) {
			if (age.equals("-25"))
				req += " and datediff(CURDATE(),datenais_identite ) <9496";
			if (age.equals("26 a 49"))
				req += " and datediff(curdate(),datenais_identite) >9496 and "
						+ "datediff(curdate(),datenais_identite)<18262";
			if (age.equals("+50"))
				req += " and datediff(curdate(),datenais_identite) > 18262";
		}
		if (!formation.equals("vide")) {
			req += " and niveauFormation_identite='" + formation + "'";
		}
		if (!pe.equals("vide")) {
			if (pe.equals("pasinscrit")) {
				req += " and 	poleEmploiInscription_identite=NULL";
			}
			if (pe.equals("-1 an")) {
				req += " and datediff(curdate(),poleEmploiInscription_identite) <365";
			}
			if (pe.equals("-2 ans")) {
				req += " and datediff(curdate(),poleEmploiInscription_identite) >365"
						+ " and datediff(curdate(),poleEmploiInscription_identite) <730";
			}
			if (pe.equals("-3 ans")) {
				req += " and datediff(curdate(),poleEmploiInscription_identite) >730"
						+ " and datediff(curdate(),poleEmploiInscription_identite) <1095";
			}
			if (pe.equals("+3 ans")) {
				req += " and datediff(curdate(),poleEmploiInscription_identite) >1095";
			}

		}
		if (!origine.equals("vide")) {
			if (origine.equals("Tarn et Garonne"))
				req += " and cp_identite like '82%'";
			else if (origine.equals("Lot et Garonne"))
				req += " and cp_identite like '47%'";
			else if (origine.equals("VALENCE D'AGEN"))
				req += " and trim(ville_identite)='VALENCE D\\'AGEN'";
			else
				req += " and upper(ville_identite) in (select libelle from "
						+ DAOConstants.t_cc2r + ")";
		}

		Identite identite = null;
		List<Identite> liste = new ArrayList<Identite>();
		try {
			connect = DBConnexion.getConnexion();
			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			while (rs.next()) {
				int num = rs.getInt(1);
				String nom = rs.getString(2);
				String pren = rs.getString(3);
				String vil = rs.getString(4);
				String cp = rs.getString(5);

				identite = new Identite(num, nom, pren, cp, vil);
				liste.add(identite);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}

		return liste;

	}

	public List<Identite> afficherStatistiquesPourCent(Date debut, Date fin) throws DAOException {
		Statement stat = null;
		ResultSet rs = null;
		String req = "select " + DAOConstants.t_identite + ".id_identite,"
				+ DAOConstants.t_identite + ".sex_identite,"
				+ DAOConstants.t_identite + ".nom_identite,"
				+ DAOConstants.t_identite + ".prenom_identite,"
				+ DAOConstants.t_identite + ".cp_identite,"
				+ DAOConstants.t_identite + ".ville_identite,"
				+ DAOConstants.t_identite + ".datenais_identite,"
				+ DAOConstants.t_identite + ".nationalite_identite,"
				+ DAOConstants.t_identite + ".dateaccueil_identite,"
				+ DAOConstants.t_identite + ".poleEmploiInscription_identite,"
				+ DAOConstants.t_identite + ".poleEmploiID_identite,"
				+ DAOConstants.t_identite + ".niveauFormation_identite from "
				+ DAOConstants.t_identite
				+ " where  dateaccueil_identite between '" + debut + "' and '"
				+ fin + "'";

		Identite identite = null;
		List<Identite> liste = new ArrayList<Identite>();
		try {
			connect = DBConnexion.getConnexion();
			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			while (rs.next()) {
				int num = rs.getInt(1);
				String sexe = rs.getString(2);
				String nom = rs.getString(3);
				String pren = rs.getString(4);
				String cp = rs.getString(5);
				String vil = rs.getString(6);
				Date naiss = rs.getDate(7);
				String nation = rs.getString(8);
				Date accueil = rs.getDate(9);
				Date poleEmploi = rs.getDate(10);
				String numerope = rs.getString(11);
				String niveau = rs.getString(12);

				identite = new Identite(num, sexe, nom, pren, cp, vil, naiss,
						nation, accueil, poleEmploi, numerope, niveau);
				liste.add(identite);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}

		return liste;

	}

	/** permet d'afficher les noms des personnes commencant par les lettres donnees en parametre
	 * 
	 * @param lettre les premieres lettres d'un nom
	 * @return une liste de noms commencant par les lettres données en parametre
	 * @throws DAOException 
	 */
	 
	public List<String> afficherNoms(String lettre) throws DAOException {
		String nomcomplet = null;
		Statement stat = null;
		ResultSet rs = null;
		String req = "select nom_identite, prenom_identite from "
				+ DAOConstants.t_identite + " where nom_identite like upper('"
				+ lettre + "%') order by nom_identite asc";
		List<String> liste = new ArrayList<String>();
		// List<String> listenoms = new ArrayList<String>();
		try {
			connect = DBConnexion.getConnexion();
			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			while (rs.next()) {
				String nom = rs.getString(1);
				String pren = rs.getString(2);
				nomcomplet = nom + " " + pren;
				liste.add(nomcomplet);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}

		return liste;
	}

	/** liste des personnes qui sont a la recherche de l'emploi donné en parametre
	 * 
	 * @param rome code rome
	 * @return liste de personnes recherchant un poste dur ler code mletier saisi
	 * @throws DAOException
	 */
	public List<Identite> rechercheParEmploiRome(int rome) throws DAOException {
		ArrayList<Identite> liste = new ArrayList<Identite>();
		Identite identite = null;
		Statement st = null;
		ResultSet res = null;
		String req = "select " + DAOConstants.t_identite
				+ ".id_identite,nom_identite,prenom_identite,ville_identite,"
				+ "mobil_identite, tel1_identite  from "
				+ DAOConstants.t_identite + " inner join "
				+ DAOConstants.t_emploiRecherche + " on "
				+ DAOConstants.t_identite + ".id_identite="
				+ DAOConstants.t_emploiRecherche + ".id_identite "
				+ " where idrome=" + rome + " and statutfiche_identite=1";

		try {
			connect = DBConnexion.getConnexion();
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {

				identite = new Identite(res.getInt(1), res.getString(2),
						res.getString(3), res.getString(4), res.getString(5),
						res.getString(6));
				liste.add(identite);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return liste;

	}

	public List<Identite> afficherListeAIParSouhait(String requete)
			throws DAOException {
		Identite ident = null;
		Statement stat = null;
		ResultSet rs = null;
		String req = "select distinct "
				+ DAOConstants.t_identite
				+ ".id_identite,nom_identite, prenom_identite,ville_identite,"
				+ "mobil_identite,tel1_identite, poleEmploiInscription_identite from ((((("
				+ DAOConstants.t_identite + " left join "
				+ DAOConstants.t_emploiRecherche + " on "
				+ DAOConstants.t_identite + ".id_identite="
				+ DAOConstants.t_emploiRecherche + ".id_identite )"
				+ " left join " + DAOConstants.t_priorite + " on "
				+ DAOConstants.t_identite + ".id_identite="
				+ DAOConstants.t_priorite + ".id_identite )"
				+" left join "+ DAOConstants.t_diplome+ " on "
				+ DAOConstants.t_identite + ".id_identite="
				+ DAOConstants.t_diplome+ ".id_identite )"
				 + " left join " + DAOConstants.t_locomotion + " on "
				+ DAOConstants.t_identite + ".id_identite="
				+ DAOConstants.t_locomotion + ".id_identite )" + " left join "
				+ DAOConstants.t_permis + " on " + DAOConstants.t_identite
				+ ".id_identite=" + DAOConstants.t_permis
				+ ".id_identite)  where  1=1 and " 
				+ DAOConstants.t_identite
				+".statutfiche_identite= 1 "
				+ requete;
		// System.out.println(req);
		List<Identite> liste = new ArrayList<Identite>();
		// List<String> listenoms = new ArrayList<String>();
		try {
			connect = DBConnexion.getConnexion();
			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			while (rs.next()) {
				int id = rs.getInt(1);
				String nom = rs.getString(2);
				String pren = rs.getString(3);
				String ville = rs.getString(4);
				String mob = rs.getString(5);
				String tel = rs.getString(6);
				Date pe = rs.getDate(7);
				ident = new Identite(id, nom, pren, ville, mob, tel, pe);
				liste.add(ident);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}

		return liste;
	}
	/**
	 * total du nombre de personnes enregistrees en base
	 * @return
	 * @throws DAOException
	 */
	public int totalListe() throws DAOException {
		Statement st = null;
		ResultSet res = null;
		String req = "select count(*) from " + DAOConstants.t_identite;
		int total = 0;

		try {
			connect = DBConnexion.getConnexion();
			st = connect.createStatement();
			res = st.executeQuery(req);
			if (res.first()) {
				total = res.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return total;

	}

	/** statistiques contrats ai passes entre les dates de debut et de fin 
	 * 
	 * @param debut
	 * @param fin
	 * @return
	 * @throws DAOException 
	 */

	public List<Identite> afficherStatistiquesAI(Date debut, Date fin) throws DAOException {
		Statement stat = null;
		ResultSet rs = null;
		String req = "select distinct " + DAOConstants.t_identite
				+ ".id_identite ," + DAOConstants.t_identite + ".sex_identite,"
				+ DAOConstants.t_identite + ".nom_identite,"
				+ DAOConstants.t_identite + ".prenom_identite,"
				+ DAOConstants.t_identite + ".cp_identite,"
				+ DAOConstants.t_identite + ".ville_identite,"
				+ DAOConstants.t_identite + ".datenais_identite,"
				+ DAOConstants.t_identite + ".nationalite_identite,"
				+ DAOConstants.t_identite + ".dateaccueil_identite,"
				+ DAOConstants.t_identite + ".poleEmploiInscription_identite,"
				+ DAOConstants.t_identite + ".poleEmploiID_identite,"
				+ DAOConstants.t_identite + ".niveauFormation_identite "
				+ " from " + DAOConstants.t_identite + " inner join "
				+ DAOConstants.t_aicontrat + " on " + DAOConstants.t_identite
				+ ".id_identite=" + DAOConstants.t_aicontrat + ".id_identite "
				+ " where  datedebcontrat_aicontrat between '" + debut
				+ "' and '" + fin + "' ";

		Identite identite = null;
		List<Identite> liste = new ArrayList<Identite>();
		try {
			connect = DBConnexion.getConnexion();
			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			while (rs.next()) {
				int num = rs.getInt(1);
				String sexe = rs.getString(2);
				String nom = rs.getString(3);
				String pren = rs.getString(4);
				String cp = rs.getString(5);
				String vil = rs.getString(6);
				Date naiss = rs.getDate(7);
				String nation = rs.getString(8);
				Date accueil = rs.getDate(9);
				Date poleEmploi = rs.getDate(10);
				String numerope = rs.getString(11);
				String niveau = rs.getString(12);

				identite = new Identite(num, sexe, nom, pren, cp, vil, naiss,
						nation, accueil, poleEmploi, numerope, niveau);
				liste.add(identite);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}

		return liste;

	}

	/**statistiques contrats et avenants ai passes entre les dates de debut et de fin
	 * 
	 * @param debut
	 * @param fin
	 * @return
	 * @throws DAOException 
	 */

	public List<Identite> afficherStatistiquesAIcontratAvenant(Date debut,
			Date fin) throws DAOException {
		Statement stat = null;
		ResultSet rs = null;
		String req = "select  " + DAOConstants.t_identite + ".id_identite,"
				+ DAOConstants.t_identite + ".sex_identite,"
				+ DAOConstants.t_identite + ".nom_identite,"
				+ DAOConstants.t_identite + ".prenom_identite,"
				+ DAOConstants.t_identite + ".cp_identite,"
				+ DAOConstants.t_identite + ".ville_identite,"
				+ DAOConstants.t_identite + ".datenais_identite,"
				+ DAOConstants.t_identite + ".nationalite_identite,"
				+ DAOConstants.t_identite + ".dateaccueil_identite,"
				+ DAOConstants.t_identite + ".poleEmploiInscription_identite,"
				+ DAOConstants.t_identite + ".poleEmploiID_identite,"
				+ DAOConstants.t_identite + ".niveauFormation_identite,"
				+ DAOConstants.t_aicontrat + ".idaicontrat,idaiavenant from "
				+ DAOConstants.t_identite + " inner join "
				+ DAOConstants.t_aicontrat + " on " + DAOConstants.t_identite
				+ ".id_identite=" + DAOConstants.t_aicontrat + ".id_identite "
				+ " left outer join " + DAOConstants.t_aiavenant + " on "
				+ DAOConstants.t_aiavenant + ".idaicontrat="
				+ DAOConstants.t_aicontrat + ".idaicontrat "
				+ " where  (datedebcontrat_aicontrat >= '" + debut
				+ "'  and datefincontrat_aicontrat <='" + fin + "' )" + " or "
				+ " (datedeb_aiavenant  >= '" + debut
				+ "'  and datefin_aiavenant <='" + fin + "' )" + " group by "
				+ DAOConstants.t_identite + ".id_identite";

		Identite identite = null;
		List<Identite> liste = new ArrayList<Identite>();
		try {
			connect = DBConnexion.getConnexion();
			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			while (rs.next()) {
				int num = rs.getInt(1);
				String sexe = rs.getString(2);
				String nom = rs.getString(3);
				String pren = rs.getString(4);
				String cp = rs.getString(5);
				String vil = rs.getString(6);
				Date naiss = rs.getDate(7);
				String nation = rs.getString(8);
				Date accueil = rs.getDate(9);
				Date poleEmploi = rs.getDate(10);
				String numerope = rs.getString(11);
				String niveau = rs.getString(12);
				int n1 = rs.getInt(13);
				int n2 = rs.getInt(14);

				identite = new Identite(num, sexe, nom, pren, cp, vil, naiss,
						nation, accueil, poleEmploi, numerope, niveau);
				liste.add(identite);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}

		return liste;

	}
	/**
	 * affiche la civilité d'une personne en fonction du sexe saisi
	 * @param identite
	 * @return
	 */
	public String afficheCivilite(Identite identite) {
		
		String mr="";
		if(identite.getSexe_IDE().equals("FEMININ"))
			mr="Mme";
		else if(identite.getSexe_IDE().equals("MASCULIN"))
			mr="M";
		else if(identite.getSexe_IDE().equals("FEMININ") && identite.getSitfam_IDE().equals("Célibataire"))
			mr="Mlle";
		return mr;
		
	}
	
	/**
	 * affiche la liste des personnes ayant des contrats SAP en cours
	 * @param nom
	 * @return
	 * @throws DAOException
	 */
	public List<Identite> rechercheNomAvecContratSAP(String nom) throws DAOException {
		ArrayList<Identite> liste = new ArrayList<Identite>();
		Identite identite = null;
		Statement st = null;
		ResultSet res = null;
		String req = "select distinct "
				+DAOConstants.t_identite
				+".id_identite,nom_identite,prenom_identite,ville_identite,"
				+ "mobil_identite, tel1_identite,datenais_identite,"
				+ "lieunais_identite,nss_identite from "
				+ DAOConstants.t_identite
				+" inner join "
				+ DAOConstants.t_sapcontrat
				+" on "
				+ DAOConstants.t_identite
				+".id_identite="
				+ DAOConstants.t_sapcontrat
				+".id_identite "
				+ "  where nom_identite like '"
				+ nom
				+ "%' and (termecontrat is null or termecontrat > curdate()) order by nom_identite asc ";
		

		try {
			connect = DBConnexion.getConnexion();
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {

				identite = new Identite(res.getInt(1), res.getString(2),
						res.getString(3), res.getString(4), res.getString(5),
						res.getString(6), res.getDate(7), res.getString(8),
						res.getString(9));
				liste.add(identite);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return liste;

	}


}
