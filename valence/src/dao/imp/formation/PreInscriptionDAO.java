package dao.imp.formation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.formation.ListeFormations;
import beans.formation.PreInscription;
import beans.identite.Identite;
import beans.parametres.accueil.Cc2rDAO;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.imp.identite.IdentiteDAO;
import dao.interfaces.DAO;

public class PreInscriptionDAO implements DAO<PreInscription> {

	final private String query_insert = "insert into "
			+ DAOConstants.t_preinscription
			+ "(id_identite,date_pyramide,tempsacc_pyramide,"
			+ "animatrice_pyramide,commentaires,raisonab_formation,"
			+ "dateabandon_formation,prescripteur_formation,interloc_formation,id_pformation,"
			+ "inscrit,enregistre) " + " values (?,?,?,?,?,?,?,?,?,?,?,?)";

	final private String query_update = "update "
			+ DAOConstants.t_preinscription
			+ " set id_identite=?,date_pyramide=?,tempsacc_pyramide=?,"
			+ "animatrice_pyramide=?,commentaires=?,raisonab_formation=?,"
			+ "dateabandon_formation=?,prescripteur_formation=?,interloc_formation=?,"
			+ "id_pformation=?,inscrit=?,enregistre=? " + "where id_formation=";

	private Connection connect = null;

	public PreInscriptionDAO() {
		connect = DBConnexion.getConnexion();
	}

	@Override
	public PreInscription findByID(int id) throws DAOException {
		String query = "select `id_formation`, `id_identite`, `date_pyramide`,"
				+ " `tempsacc_pyramide`, `animatrice_pyramide`, `commentaires`, "
				+ "`raisonab_formation`, `dateabandon_formation`, `prescripteur_formation`,"
				+ " `interloc_formation`, `id_pformation`, `inscrit`, `enregistre` from "
				+ DAOConstants.t_preinscription + " where  id_formation=" + id;
		PreInscription pre = null;
		Statement st = null;
		ResultSet res = null;

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				pre = new PreInscription(id, new IdentiteDAO().findByID(res
						.getInt(2)), res.getDate(3), res.getString(4),
						new AnimatriceDAO().findByID(res.getShort(5)),
						res.getString(6), res.getString(7), res.getDate(8),
						res.getString(9), res.getString(10),
						new ListeFormationsDAO().findByID(res.getInt(11)),
						res.getBoolean(12), res.getBoolean(13));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return pre;
	}

	@Override
	public List<PreInscription> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PreInscription> findByCriteria(PreInscription obj)
			throws DAOException {
		ArrayList<PreInscription> liste = new ArrayList<PreInscription>();
		PreInscription identite = null;
		Statement st = null;
		ResultSet res = null;
		String req = "select `id_formation`, `id_identite`, `date_pyramide`,"
				+ " `tempsacc_pyramide`, `animatrice_pyramide`, `commentaires`, "
				+ "`raisonab_formation`, `dateabandon_formation`, `prescripteur_formation`,"
				+ " `interloc_formation`, `id_pformation`, `inscrit`, `enregistre` from "
				+ DAOConstants.t_preinscription + " where 1=1 ";
		if (obj.getListe() != null && obj.getListe().getId_pformation() > 0)
			req += " and id_pformation =" + obj.getListe().getId_pformation();
		if (obj.getIdentite() != null && obj.getIdentite().getId_IDE() > 0)
			req += " and id_identite =" + obj.getIdentite().getId_IDE();
		if (obj.isEnregistre() == true)
			req += " and enregistre=1";
		if (obj.getDateAbandon() == null)
			req += " and dateabandon_formation is null";
		else
			req += " and dateabandon_formation is not null";

		if (obj.isInscrit() == true)
			req += " and inscrit=1";
		else
			req += " and inscrit=0";
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {

				identite = new PreInscription(res.getInt(1),
						new IdentiteDAO().findByID(res.getInt(2)),
						res.getDate(3), res.getString(4),
						new AnimatriceDAO().findByID(res.getShort(5)),
						res.getString(6), res.getString(7), res.getDate(8),
						res.getString(9), res.getString(10),
						new ListeFormationsDAO().findByID(res.getInt(11)),
						res.getBoolean(12), res.getBoolean(13));
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

	public List<PreInscription> findByCriteria2(PreInscription obj)
			throws DAOException {
		ArrayList<PreInscription> liste = new ArrayList<PreInscription>();
		PreInscription identite = null;
		Statement st = null;
		ResultSet res = null;
		String req = "select `id_formation`, `id_identite`, `date_pyramide`,"
				+ " `tempsacc_pyramide`, `animatrice_pyramide`, `commentaires`, "
				+ "`raisonab_formation`, `dateabandon_formation`, `prescripteur_formation`,"
				+ " `interloc_formation`, `id_pformation`, `inscrit`, `enregistre` from "
				+ DAOConstants.t_preinscription + " where 1=1 ";
		if (obj.getListe() != null && obj.getListe().getId_pformation() > 0)
			req += " and id_pformation =" + obj.getListe().getId_pformation();

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {

				identite = new PreInscription(res.getInt(1),
						new IdentiteDAO().findByID(res.getInt(2)),
						res.getDate(3), res.getString(4),
						new AnimatriceDAO().findByID(res.getShort(5)),
						res.getString(6), res.getString(7), res.getDate(8),
						res.getString(9), res.getString(10),
						new ListeFormationsDAO().findByID(res.getInt(11)),
						res.getBoolean(12), res.getBoolean(13));
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

	/**
	 * affiche la liste des preinscriptions correspondantes a une personne
	 * 
	 * @param identite
	 * @return
	 * @throws DAOException
	 */
	public List<PreInscription> findByIdentite(Identite identite)
			throws DAOException {
		String query = "select `id_formation`, `id_identite`, `date_pyramide`,"
				+ " `tempsacc_pyramide`, `animatrice_pyramide`, `commentaires`, "
				+ "`raisonab_formation`, `dateabandon_formation`, `prescripteur_formation`,"
				+ " `interloc_formation`, `id_pformation`, `inscrit`, `enregistre` from "
				+ DAOConstants.t_preinscription
				+ " where 1=1 and  id_identite=" + identite.getId_IDE();
		ArrayList<PreInscription> liste = new ArrayList<PreInscription>();
		PreInscription pre = null;
		Statement st = null;
		ResultSet res = null;

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			while (res.next()) {

				pre = new PreInscription(res.getInt(1), identite,
						res.getDate(3), res.getString(4),
						new AnimatriceDAO().findByID(res.getShort(5)),
						res.getString(6), res.getString(7), res.getDate(8),
						res.getString(9), res.getString(10),
						new ListeFormationsDAO().findByID(res.getInt(11)),
						res.getBoolean(12), res.getBoolean(13));
				liste.add(pre);

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
	public int create(PreInscription obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);
			// pst.setLong(1, obj.getId_IDE());
			pst.setInt(1, obj.getIdentite().getId_IDE());
			pst.setDate(2, (Date) obj.getDate_pyramide());
			pst.setString(3, obj.getTempsacc_pyramide());
			pst.setInt(4, obj.getAnimatrice().getId_animatrice());
			pst.setString(5, obj.getCommentaires());
			pst.setString(6, obj.getRais_abandon());
			pst.setDate(7, (Date) obj.getDateAbandon());
			pst.setString(8, obj.getPrescripteur());
			pst.setString(9, obj.getInterlocuteur());
			pst.setInt(10, obj.getListe().getId_pformation());
			pst.setBoolean(11, obj.isInscrit());
			pst.setBoolean(12, obj.isEnregistre());
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
			DAOUtil.closePrepareStatement(pst);
		}
		return cle;

	}

	@Override
	public int delete(PreInscription obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PreInscription update(PreInscription obj) throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getId_formation();

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.setInt(1, obj.getIdentite().getId_IDE());
			pst.setDate(2, (Date) obj.getDate_pyramide());
			pst.setString(3, obj.getTempsacc_pyramide());
			pst.setShort(4, obj.getAnimatrice().getId_animatrice());
			pst.setString(5, obj.getCommentaires());
			pst.setString(6, obj.getRais_abandon());
			pst.setDate(7, (Date) obj.getDateAbandon());
			pst.setString(8, obj.getPrescripteur());
			pst.setString(9, obj.getInterlocuteur());
			pst.setInt(10, obj.getListe().getId_pformation());
			pst.setBoolean(11, obj.isInscrit());
			pst.setBoolean(12, obj.isEnregistre());
			pst.executeUpdate();
			connect.commit();

		} catch (SQLException e) {
			try {
				connect.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();

		} finally {
			DAOUtil.closePrepareStatement(pst);
		}
		return obj;
	}
	
	
	/*public List<List<String>> afficherStatistiquesformations(String req, ListeFormations formation) {
		List<List<String>> liste = new ArrayList<List<String>>();
		List<String> une=new ArrayList<String>();
		Statement st = null;
		ResultSet res = null;
		String lancer="select "+DAOConstants.t_identite+".id_identite, nom_identite, prenom_identite, cp_identite, ville_identite, inscrit, enregistre from "
				+DAOConstants.t_identite+" inner join "+
				DAOConstants.t_preinscription
				+" on "
				+DAOConstants.t_identite+".id_identite="
				+DAOConstants.t_preinscription+".id_identite "
				+ "inner join "
				+DAOConstants.t_listeFormations
				+" on "
				+DAOConstants.t_preinscription
				+".id_pformation="
				+DAOConstants.t_listeFormations
				+".id_pformation "
				+" where 1=1 and "
				+DAOConstants.t_listeFormations
				+".id_pformation="
				+formation.getId_pformation()
				+req;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(lancer);
			while (res.next()) {
				une.add(res.getString(1));
				une.add(res.getString(2));
				une.add(res.getString(3));
				une.add(res.getString(4));
				une.add(res.getString(5));
				une.add(res.getString(6));
				une.add(res.getString(7));
			liste.add(une);}
		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			try {
				res.close();
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return liste;
	
	}

*/
	public Identite afficherStatistiquesformation(PreInscription pre,
			String sexe, String age, ListeFormations formation, String niveau,
			String pole, String origine) {

		// villes de la communaute
		List<String> villescc2r = null;
		try {
			villescc2r = new Cc2rDAO().afficherVilles();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Identite une = null;
		try {
			une = new IdentiteDAO().findByID(pre.getIdentite().getId_IDE());
		} catch (DAOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (!sexe.equals("vide")) {
			if (!une.getSexe_IDE().equals(sexe)) {
				une = null;
				return null;
			}

		}

		if (!age.equals("vide")) {
			if (age.equalsIgnoreCase("moins de 25 ans")) {
				int agecalcul = une.calculerAge(pre.getListe()
						.getDatedeb_form());
				if (agecalcul > 25 || agecalcul == -1) {
					une = null;
					return null;
				}
			}
		}
		if (age.equalsIgnoreCase("de 26 à 49 ans")) {

			if (une.calculerAge(pre.getListe().getDatedeb_form()) > 49
					|| une.calculerAge(pre.getListe().getDatedeb_form()) < 26
					|| une.calculerAge(pre.getListe().getDatedeb_form()) == -1) {
				une = null;
				return null;
			}
		}

		if (age.equalsIgnoreCase("50 ans et plus")) {
			if (une.calculerAge(pre.getListe().getDatedeb_form()) < 50) {
				une = null;
				return null;
			}
		}

		if (!niveau.equals("vide")) {
			if (une.getNiveauFormation_IDE()!=null && !une.getNiveauFormation_IDE().equals(niveau)) {
				une = null;
				return null;
			}
		}
		/* comparaison de la date inscription pole emploi et de la date de debut de formation*/
		if (!pole.equals("vide")) {
			if (pole.equals("Non inscrit")) {
				if (!une.getPoleEmploi_ID_IDE().equals("")
						|| une.getPoleEmploiInscripription_IDE() != null) {
					une = null;
					return null;
				}
			} else if (pole.equals("moins de 1an")) {
				if (une.getPoleEmploiInscripription_IDE() != null) {
					if (une.calculerTempsPE(pre.getListe().getDatedeb_form()) >= 1
							|| une.calculerTempsPE(pre.getListe()
									.getDatedeb_form()) == -1) {
						une = null;
						return null;
					}

				}
				else{
				une = null;
				return null;
				}

			} else if (pole.equals("plus de 1 an")) {
				if (une.getPoleEmploiInscripription_IDE() != null) {
					if (une.calculerTempsPE(pre.getListe().getDatedeb_form()) < 1) {
						une = null;
						return null;
					}
				}
				else{
				une = null;
				return null;
				}
			}

		}

		if (!origine.equals("vide")) {
			if (origine.equalsIgnoreCase("Tarn et Garonne")) {
				if (!une.getCp_IDE().startsWith("82")) {
					une = null;
					return null;
				}
			} else if (origine.equalsIgnoreCase("Valence d'Agen")) {
				if (!une.getVille_IDE().equalsIgnoreCase("Valence d'agen")) {
					une = null;
					return null;
				}
			} else if (origine.equalsIgnoreCase("47")) {
				if (!une.getCp_IDE().startsWith("47")) {
					une = null;
					return null;
				}
			} else if (origine.equalsIgnoreCase("CC2R")) {
				boolean vrai = false;
				for (int x = 0; x < villescc2r.size(); x++) {
					if (une.getVille_IDE().equalsIgnoreCase(villescc2r.get(x))) {
						vrai = true;
						break;
					}
				}
				if (!vrai) {
					une = null;
					return null;
				}

			} else if (origine.equalsIgnoreCase("Region")) {
				System.out.println("code postzl=" + une.getCp_IDE());
				if (!une.getCp_IDE().startsWith("82")
						&& !une.getCp_IDE().startsWith("09")
						&& !une.getCp_IDE().startsWith("46")
						&& !une.getCp_IDE().startsWith("12")
						&& !une.getCp_IDE().startsWith("81")
						&& !une.getCp_IDE().startsWith("32")
						&& !une.getCp_IDE().startsWith("65")
						&& !une.getCp_IDE().startsWith("31"))
					une = null;

			}

		}
		return une;
	}

	/**
	 * retourne le nombre de visites de la personne pour laquelle aucune
	 * formation n'aété trouvée
	 * 
	 * @param un
	 * @return
	 * @throws DAOException
	 */
	public List<PreInscription> pyramideAccueil(Identite un)
			throws DAOException {
		String req = " select date_pyramide, animatrice_pyramide,commentaires"
				+ " from " + DAOConstants.t_preinscription
				+ " where id_identite=" + un.getId_IDE()
				+ " and enregistre=0 order by date_pyramide desc";
		ArrayList<PreInscription> liste = new ArrayList<PreInscription>();
		PreInscription preins = null;
		Statement st = null;
		ResultSet res = null;

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {
				preins = new PreInscription(res.getDate(1),
						new AnimatriceDAO().findByID(res.getShort(2)),
						res.getString(3));
				liste.add(preins);
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

	/**
	 * retourne les formations abandonnées par une personne
	 * 
	 * @param un
	 * @return
	 * @throws DAOException
	 */

	public List<PreInscription> formationabandon(Identite un)
			throws DAOException {
		String req = " select dateabandon_formation,raisonab_formation,id_pformation"
				+ " from "
				+ DAOConstants.t_preinscription
				+ " where id_identite="
				+ un.getId_IDE()
				+ " and enregistre=1 and inscrit=1 and dateabandon_formation is not null ";

		ArrayList<PreInscription> liste = new ArrayList<PreInscription>();
		PreInscription preins = null;
		Statement st = null;
		ResultSet res = null;

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {
				preins = new PreInscription(res.getDate(1), res.getString(2),
						new ListeFormationsDAO().findByID(res.getInt(3)));
				liste.add(preins);
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

	/**
	 * affiche la preinscriptions correspondantes a une personne et une
	 * formation
	 * 
	 * @param identite
	 * @param forma
	 * @return
	 * @throws DAOException
	 */
	public PreInscription recuperePreinscription(Identite identite,
			ListeFormations forma) throws DAOException {
		String query = "select `id_formation`, `id_identite`, `date_pyramide`,"
				+ " `tempsacc_pyramide`, `animatrice_pyramide`, `commentaires`, "
				+ "`raisonab_formation`, `dateabandon_formation`, `prescripteur_formation`,"
				+ " `interloc_formation`, `id_pformation`, `inscrit`, `enregistre` from "
				+ DAOConstants.t_preinscription + " where   id_identite="
				+ identite.getId_IDE() + " and id_pformation="
				+ forma.getId_pformation();
		PreInscription pre = null;
		Statement st = null;
		ResultSet res = null;

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {

				pre = new PreInscription(res.getInt(1), identite,
						res.getDate(3), res.getString(4),
						new AnimatriceDAO().findByID(res.getShort(5)),
						res.getString(6), res.getString(7), res.getDate(8),
						res.getString(9), res.getString(10), forma,
						res.getBoolean(12), res.getBoolean(13));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return pre;

	}

	/**
	 * affiche la liste des preinscriptions correspondantes a une personne
	 * 
	 * @param identite
	 * @return
	 * @throws DAOException
	 */
	public List<PreInscription> recupereListePreinscription(Identite identite)
			throws DAOException {
		String query = "select `id_formation`, `id_identite`, `date_pyramide`,"
				+ " `tempsacc_pyramide`, `animatrice_pyramide`, `commentaires`, "
				+ "`raisonab_formation`, `dateabandon_formation`, `prescripteur_formation`,"
				+ " `interloc_formation`, `id_pformation`, `inscrit`, `enregistre` from "
				+ DAOConstants.t_preinscription + " where   id_identite="
				+ identite.getId_IDE();

		List<PreInscription> liste = new ArrayList<PreInscription>();
		PreInscription pre = null;
		Statement st = null;
		ResultSet res = null;

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			while (res.next()) {

				pre = new PreInscription(res.getInt(1), identite,
						res.getDate(3), res.getString(4),
						new AnimatriceDAO().findByID(res.getShort(5)),
						res.getString(6), res.getString(7), res.getDate(8),
						res.getString(9), res.getString(10),
						new ListeFormationsDAO().findByID(res.getInt(11)),
						res.getBoolean(12), res.getBoolean(13));
				liste.add(pre);

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

	/**
	 * affiche le nombre de formations suivies par une personne
	 * 
	 * @param identite
	 * @return
	 * @throws DAOException
	 */
	public int nombrePreinscription(Identite identite) throws DAOException {
		String query = "select count(id_formation) as total from "
				+ DAOConstants.t_preinscription + " where   id_identite="
				+ identite.getId_IDE() + " and inscrit=1";

		int nombre = 0;
		Statement st = null;
		ResultSet res = null;

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {

				nombre = res.getInt(1);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return nombre;

	}

	/**
	 * permet de vérifier si la personne à deja été enregistrée sur une
	 * formation
	 * 
	 * @param identite
	 * @return
	 * @throws DAOException
	 */
	public int nombreEnregistrement(Identite identite) throws DAOException {
		String query = "select count(id_formation) as total from "
				+ DAOConstants.t_preinscription + " where   id_identite="
				+ identite.getId_IDE() + " and enregistre=1";

		int nombre = 0;
		Statement st = null;
		ResultSet res = null;

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {

				nombre = res.getInt(1);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return nombre;

	}

	/**
	 * verifie si la personne est en cours de formation
	 * 
	 * @param identite
	 * @return
	 * @throws DAOException
	 */
	public int nombrePreinscriptionEnCours(Identite identite)
			throws DAOException {
		String query = "select id_formation from "
				+ DAOConstants.t_preinscription + " inner join "
				+ DAOConstants.t_listeFormations + " on "
				+ DAOConstants.t_preinscription + ".id_pformation="
				+ DAOConstants.t_listeFormations + ".id_pformation"
				+ " where   id_identite=" + identite.getId_IDE()
				+ " and inscrit=1 and dateabandon_formation is null  and "
				+ " (curdate() >=datedeb_form  and curdate()<=datefin_form) ";

		int nombre = 0;
		Statement st = null;
		ResultSet res = null;

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {

				nombre = res.getInt(1);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return nombre;

	}

}
