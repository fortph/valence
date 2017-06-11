package dao.imp.ai;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.ai.Avenant;
import beans.ai.Contrat;
import beans.employeurs.Employeur;
import beans.identite.Identite;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.interfaces.DAO;

public class AvenantDAO implements DAO<Avenant> {

	final private String query_insert = "INSERT INTO " + DAOConstants.t_aiavenant
			+ " (idaicontrat,naiav,n_aiavenant,datedeb_aiavenant,datefin_aiavenant,"
			+ "remuneration_aiavenant,facturation_aiavenant, panier_aiavenant,panierfac_aiavenant,"
			+ "deplacement_aiavenant,deplacementfac_aiavenant,diver_aiavenant,diverfac_aiavenant,"
			+ "divert_aiavenant,diverfact_aiavenant, dateredac_aiavenant, hebdomin_aiavenant,"
			+ "tache_aiavenant,lieu_aiavenant " + ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	final private String query_update = "update " + DAOConstants.t_aiavenant
			+ " set idaicontrat=?,naiav=?,n_aiavenant=?,datedeb_aiavenant=?,datefin_aiavenant=?,"
			+ "remuneration_aiavenant=?,facturation_aiavenant=?, panier_aiavenant=?,panierfac_aiavenant=?,"
			+ "deplacement_aiavenant=?,deplacementfac_aiavenant=?,diver_aiavenant=?,diverfac_aiavenant=?,"
			+ "divert_aiavenant=?,diverfact_aiavenant=?, dateredac_aiavenant=?, hebdomin_aiavenant=?,"
			+ "tache_aiavenant=?,lieu_aiavenant=?  " + " where idaiavenant=";

	private Connection connect = null;

	public AvenantDAO() {
		connect = DBConnexion.getConnexion();
	}

	@Override
	public Avenant findByID(int id) throws DAOException {
		String query = "select * from " + DAOConstants.t_aiavenant + " where  idaiavenant=" + id;
		Avenant contrat = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				contrat = new Avenant((int) id, new ContratDAO().findByID(res.getInt(2)), res.getInt(3),
						res.getString(4), res.getDate(5), res.getDate(6), res.getFloat(7), res.getFloat(8),
						res.getFloat(9), res.getFloat(10), res.getFloat(11), res.getFloat(12), res.getFloat(13),
						res.getFloat(14), res.getString(15), res.getString(16), res.getDate(17), res.getString(18),
						res.getString(19), res.getString(20));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return contrat;
	}

	@Override
	public List<Avenant> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Avenant> findByCriteria(Avenant obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * recupere date de fin du dernier avenant d'un prestation SAP
	 * 
	 * @param cont
	 * @return
	 * @throws DAOException
	 */
	public Date dernierAvenantAI(Contrat cont) throws DAOException {
		Date dernier = null;
		Statement st = null;
		ResultSet rs = null;
		int nbavenants = this.existeAvenantAI(cont);

		String sql = "SELECT  datefin_aiavenant FROM " + DAOConstants.t_aiavenant + " where idaiavenant=("
				+ " select max(idaiavenant) from " + DAOConstants.t_aiavenant + " where idaicontrat="
				+ cont.getNumerocontrat() + ")";

		String sql2 = "SELECT  datefincontrat_aicontrat FROM " + DAOConstants.t_aicontrat + " where  idaicontrat="
				+ cont.getNumerocontrat();
		// s'il y a des avenants a la prestation on recupere la date du dernier
		// avenant
		String req = "";
		if (nbavenants > 0)
			req = sql;
		// sinon on recupere la date de fin de la prestation
		else
			req = sql2;

		try {
			st = connect.createStatement();
			rs = st.executeQuery(req);

			// TANT QUE la fin du resultset n'est pas atteinte
			if (rs.first()) {
				dernier = rs.getDate(1);
			}

		} catch (SQLException ex) {
			System.out.print("Probléme " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}
		return dernier;
	}

	/**
	 * verifie s'il existe des avenants pour le contrat AI
	 * 
	 * @param cont
	 * @return
	 * @throws DAOException
	 */
	public int existeAvenantAI(Contrat cont) throws DAOException {

		Statement st = null;
		ResultSet rs = null;
		int nombre = 0;
		String sql = "SELECT count(idaiavenant) FROM " + DAOConstants.t_aiavenant + " where idaicontrat="
				+ cont.getNumerocontrat();

		try {
			st = connect.createStatement();
			rs = st.executeQuery(sql);

			if (rs.first()) {
				nombre = rs.getInt(1);
			}
		} catch (SQLException ex) {
			System.out.print("Probléme " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}
		return nombre;
	}

	@Override
	public int create(Avenant obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, obj.getContrat().getIdaicontrat());
			pst.setInt(2, obj.getNaiav());
			pst.setString(3, obj.getN_aiavenant());
			pst.setDate(4, (java.sql.Date) obj.getDatedeb());
			pst.setDate(5, (java.sql.Date) obj.getDatefin());
			pst.setFloat(6, obj.getSalairehoraire());
			pst.setFloat(7, obj.getFacturation());
			pst.setFloat(8, obj.getPanier());
			pst.setFloat(9, obj.getFacturepanier());
			pst.setFloat(10, obj.getDeplacement());
			pst.setFloat(11, obj.getFacturedeplace());
			pst.setFloat(12, obj.getDivers());
			pst.setFloat(13, obj.getFacturedivers());
			pst.setString(14, obj.getCommentaire());
			pst.setString(15, obj.getFacturecommentaire());
			pst.setDate(16, (java.sql.Date) obj.getRedaction());
			pst.setString(17, obj.getDureehebdomadaire());
			pst.setString(18, obj.getTache());
			pst.setString(19, obj.getLieu());

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
	public Avenant update(Avenant obj) throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getIdavenant();

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.setInt(1, obj.getContrat().getIdaicontrat());
			pst.setInt(2, obj.getNaiav());
			pst.setString(3, obj.getN_aiavenant());
			pst.setDate(4, (java.sql.Date) obj.getDatedeb());
			pst.setDate(5, (java.sql.Date) obj.getDatefin());
			pst.setFloat(6, obj.getSalairehoraire());
			pst.setFloat(7, obj.getFacturation());
			pst.setFloat(8, obj.getPanier());
			pst.setFloat(9, obj.getFacturepanier());
			pst.setFloat(10, obj.getDeplacement());
			pst.setFloat(11, obj.getFacturedeplace());
			pst.setFloat(12, obj.getDivers());
			pst.setFloat(13, obj.getFacturedivers());
			pst.setString(14, obj.getCommentaire());
			pst.setString(15, obj.getFacturecommentaire());
			pst.setDate(16, (java.sql.Date) obj.getRedaction());
			pst.setString(17, obj.getDureehebdomadaire());
			pst.setString(18, obj.getTache());
			pst.setString(19, obj.getLieu());

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

	@Override
	public int delete(Avenant obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * retourne le numero du prochain avenant pour le contrat donne
	 * 
	 * @param cont
	 * @return
	 * @throws DAOException
	 */
	public int recupereNumeroAvenantEContrat(Contrat cont) throws DAOException {
		int num = 0;
		String query = "select max(naiav) from " + DAOConstants.t_aiavenant + " where  idaicontrat="
				+ cont.getIdaicontrat();
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				num = res.getInt(1);
			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return num + 1;

	}

	/**
	 * renvoie la liste de tous les avenants du contrat donné en parametre
	 * 
	 * @param cont
	 * @return
	 * @throws DAOException
	 */
	public List<Avenant> listeAvenantContrat(Contrat cont) throws DAOException {
		List<Avenant> liste = new ArrayList<Avenant>();
		Statement st = null;
		ResultSet rs = null;
		Avenant avenant = null;
		int idaicontrat = cont.getIdaicontrat();
		String sql = "SELECT idaiavenant,n_aiavenant,datedeb_aiavenant,datefin_aiavenant,"
				+ "facturation_aiavenant,dateredac_aiavenant FROM " + DAOConstants.t_aiavenant + " where idaicontrat="
				+ idaicontrat;

		try {
			st = connect.createStatement();

			// Ex�cution de la requ�te et r�cup�ration du r�sultat dans un
			// resultset
			rs = st.executeQuery(sql);

			// TANT QUE la fin du resultset n'est pas atteinte
			while (rs.next()) {
				avenant = new Avenant(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getDate(4), rs.getFloat(5),
						rs.getDate(6));
				liste.add(avenant);
			}
		} catch (SQLException ex) {
			System.out.print("Probléme " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}
		return liste;
	}

	/**
	 * calcule la periode d'essai en fonction des dates de debut et de fin de
	 * contrat
	 * 
	 * @param cont
	 * @return
	 * @throws DAOException
	 */
	public String calculPeriodeEssai(Avenant cont) throws DAOException {
		int duree = 0;
		String retour = "";
		String req = "SELECT DATEDIFF(datefin_aiavenant, datedeb_aiavenant) AS duree FROM " + DAOConstants.t_aiavenant
				+ "  WHERE idaiavenant =" + cont.getIdavenant();
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			if (res.first()) {
				duree = res.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DAOUtil.closeResultSet(res);
			DAOUtil.closeStatement(st);

		}
		if (duree < 180) {
			double essai = duree / 7.0;
			if (essai > 15)
				retour = " 15 jours";
			else
				retour = (int) Math.floor(essai) + " jour(s)";
		} else
			retour = "1 mois";

		return retour;
	}

	/**
	 * liste des avenants se terminant entre les dates de debut et de fin
	 * 
	 * @param a
	 *            date de debut
	 * @param b
	 *            date de fin
	 * @return
	 * @throws DAOException
	 */
	public List<Avenant> listeAvenantParDates(Date a, Date b) throws DAOException {
		List<Avenant> liste = new ArrayList<Avenant>();
		Statement st = null;
		ResultSet rs = null;
		Avenant avenant = null;
		ContratDAO contdao = new ContratDAO();
		Contrat contrat = null;

		String sql = "SELECT idaiavenant, idaicontrat, datedeb_aiavenant,datefin_aiavenant " + " FROM "
				+ DAOConstants.t_aiavenant + " where datefin_aiavenant>='" + a + "' and datefin_aiavenant<='" + b
				+ "' order by datefin_aiavenant desc";

		try {
			st = connect.createStatement();

			// Ex�cution de la requ�te et r�cup�ration du r�sultat dans un
			// resultset
			rs = st.executeQuery(sql);

			// TANT QUE la fin du resultset n'est pas atteinte
			while (rs.next()) {
				contrat = contdao.findByID(rs.getInt(2));
				avenant = new Avenant(rs.getInt(1), contrat, rs.getDate(3), rs.getDate(4));
				liste.add(avenant);
			}
		} catch (SQLException ex) {
			System.out.print("Probléme " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}
		return liste;
	}

	/**
	 * listing des personnes qui ont un avenant valide sur la période sélectionnée
	 * @param a
	 * @param b
	 * @return
	 * @throws DAOException
	 */
	public List<Avenant> listeAvenantSurPeriode(Date a, Date b) throws DAOException {
		List<Avenant> liste = new ArrayList<Avenant>();
		Statement st = null;
		ResultSet rs = null;
		Avenant avenant = null;
		ContratDAO contdao = new ContratDAO();
		Contrat contrat = null;

		String sql = "SELECT idaiavenant, idaicontrat, datedeb_aiavenant,datefin_aiavenant " + " FROM "
				+ DAOConstants.t_aiavenant + " where (datedeb_aiavenant between '" + a + "' and '"+b+"' or datefin_aiavenant between '" + a
				+ "' and '"+b+"') or (datedeb_aiavenant < '"+a+"' and datefin_aiavenant >'"+b+"') order by datefin_aiavenant desc";

		try {
			st = connect.createStatement();

			// Ex�cution de la requ�te et r�cup�ration du r�sultat dans un
			// resultset
			rs = st.executeQuery(sql);

			// TANT QUE la fin du resultset n'est pas atteinte
			while (rs.next()) {
				contrat = contdao.findByID(rs.getInt(2));
				avenant = new Avenant(rs.getInt(1), contrat, rs.getDate(3), rs.getDate(4));
				liste.add(avenant);
			}
		} catch (SQLException ex) {
			System.out.print("Probléme " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}
		return liste;
	}

	
	
	
	
	/** liste des avenants se terminant entre les dates de debut et de fin */
	public List<Avenant> listeAvenantJusqua() throws DAOException {
		List<Avenant> liste = new ArrayList<Avenant>();
		Statement st = null;
		ResultSet rs = null;
		Avenant avenant = null;
		ContratDAO contdao = new ContratDAO();
		Contrat contrat = null;

		String sql = "SELECT idaiavenant, idaicontrat, datedeb_aiavenant,datefin_aiavenant " + " FROM "
				+ DAOConstants.t_aiavenant + " where 1=1  order by datefin_aiavenant desc";

		try {
			st = connect.createStatement();

			// Ex�cution de la requ�te et r�cup�ration du r�sultat dans un
			// resultset
			rs = st.executeQuery(sql);

			// TANT QUE la fin du resultset n'est pas atteinte
			while (rs.next()) {
				contrat = contdao.findByID(rs.getInt(2));
				avenant = new Avenant(rs.getInt(1), contrat, rs.getDate(3), rs.getDate(4));
				liste.add(avenant);
			}
		} catch (SQLException ex) {
			System.out.print("Probléme " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}
		return liste;
	}

	/*
	 * public List<Avenant> dmoAvenant(int mois, int an) throws DAOException {
	 * List<Avenant> liste = new ArrayList<Avenant>(); Statement st = null;
	 * ResultSet rs = null; Avenant avenant = null; ContratDAO contdao = new
	 * ContratDAO(); Contrat contrat = null;
	 * 
	 * String sql = "SELECT distinct idaiavenant," + DAOConstants.t_aiavenant +
	 * ".idaicontrat, datedeb_aiavenant,datefin_aiavenant " + " FROM " +
	 * DAOConstants.t_aiavenant + " inner join " + DAOConstants.t_aicontrat +
	 * " on " + DAOConstants.t_aiavenant + ".idaicontrat=" +
	 * DAOConstants.t_aicontrat + ".idaicontrat " + "  left outer join " +
	 * DAOConstants.t_aiextranet + " on " + DAOConstants.t_aicontrat +
	 * ".id_identite=" + DAOConstants.t_aiextranet + ".id_identite " +
	 * " where ((datedebut IS NULL or (month( datefin ) <=" + mois +
	 * " AND year( datefin ) <=" + an + ")) "
	 * 
	 * + " and ((month(datedeb_aiavenant)=" + mois +
	 * " and year (datedeb_aiavenant)=" + an +
	 * ") or (month (datefin_aiavenant)=" + mois +
	 * " and year(datefin_aiavenant)=" + an + ")))";
	 * 
	 * try { st = connect.createStatement();
	 * 
	 * // Ex�cution de la requ�te et r�cup�ration du r�sultat dans un //
	 * resultset rs = st.executeQuery(sql);
	 * 
	 * // TANT QUE la fin du resultset n'est pas atteinte while (rs.next()) {
	 * contrat = contdao.findByID(rs.getInt(2)); avenant = new
	 * Avenant(rs.getInt(1), contrat, rs.getDate(3), rs.getDate(4));
	 * liste.add(avenant); } } catch (SQLException ex) {
	 * System.out.print("Probléme " + ex); } finally { try { st.close(); } catch
	 * (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } } return liste; }
	 * 
	 * 
	 */
	public List<Avenant> dmoAvenant(int mois, int an) throws DAOException {
		// int moisCalendar=mois-1;
		List<Avenant> liste = new ArrayList<Avenant>();
		Statement st = null;
		ResultSet rs = null;
		// int mm= (mois==12 ) ? 1 : (mois+1) ;
		// int yy= (mois==12) ? (an+1) : an ;
		// String dateDMOPlusUnJour=yy+"-"+((mm<10)? "0"+mm : mm )+"-01";
		// Calendar jour=Calendar.getInstance();
		// jour.set(Calendar.MONTH,moisCalendar);
		// jour.set(Calendar.YEAR, an);
		// int jourMois=jour.getActualMaximum(Calendar.DAY_OF_MONTH);
		// String dernierjourMoisDMO=an+"-"+mois+"-"+jourMois;
		// String premierJourMoisDMO=an+"-"+mois+"-01";
		// System.out.println(dernierjourMoisDMO);
		Avenant avenant = null;
		ContratDAO contdao = new ContratDAO();
		Contrat contrat = null;

		String sql = "SELECT distinct idaiavenant," + DAOConstants.t_aiavenant
				+ ".idaicontrat, datedeb_aiavenant,datefin_aiavenant " + " FROM " + DAOConstants.t_aiavenant
				+ " inner join " + DAOConstants.t_aicontrat + " on " + DAOConstants.t_aiavenant + ".idaicontrat="
				+ DAOConstants.t_aicontrat + ".idaicontrat " + "  left outer join aiextranetVue "
				// + DAOConstants.t_aiextranet
				+ " on " + DAOConstants.t_aicontrat + ".id_identite=aiextranetVue"
				// + DAOConstants.t_aiextranet
				+ ".id_identite " + " where ((datedebut IS NULL or  datefin < datefin_aiavenant)"

				+ " and ((month(datedeb_aiavenant)=" + mois + " and year (datedeb_aiavenant)=" + an + ")))";

		try {
			st = connect.createStatement();

			// Ex�cution de la requ�te et r�cup�ration du r�sultat dans un
			// resultset
			rs = st.executeQuery(sql);

			// TANT QUE la fin du resultset n'est pas atteinte
			while (rs.next()) {
				contrat = contdao.findByID(rs.getInt(2));
				avenant = new Avenant(rs.getInt(1), contrat, rs.getDate(3), rs.getDate(4));
				liste.add(avenant);
			}
		} catch (SQLException ex) {
			System.out.print("Probléme " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}
		return liste;
	}

	/**
	 * liste des effectifs permanents
	 * 
	 * @param d
	 * @return
	 * @throws DAOException
	 */
	public List<Avenant> listingPermanentsAvenant(Date d) throws DAOException {
		ArrayList<Avenant> liste = new ArrayList<Avenant>();
		ContratDAO codao = new ContratDAO();
		Contrat contrat = null;
		Avenant avenant = null;
		Statement st = null;
		ResultSet res = null;
		String req = " SELECT DISTINCT (idaiavenant) AS c, " + DAOConstants.t_aiavenant
				+ ".idaicontrat,`datefin_aiavenant` , `remuneration_aiavenant` , `panier_aiavenant` ,"
				+ " `deplacement_aiavenant` , `diver_aiavenant` , `divert_aiavenant`,nom_identite" + " FROM "
				+ DAOConstants.t_aiavenant + " INNER JOIN " + DAOConstants.t_aicontrat + " ON "
				+ DAOConstants.t_aicontrat + ".idaicontrat =" + DAOConstants.t_aiavenant + ".idaicontrat"
				+ " INNER JOIN " + DAOConstants.t_aicreation + " ON " + DAOConstants.t_aicreation + ".id_identite ="
				+ DAOConstants.t_aicontrat + ".id_identite " + " INNER JOIN " + DAOConstants.t_identite + " ON "
				+ DAOConstants.t_aicreation + ".id_identite =" + DAOConstants.t_identite + ".id_identite "
				+ " WHERE `datefin_aiavenant` > '" + d
				+ "' AND permanent_aifiche =1 GROUP BY idaiavenant order by nom_identite asc";

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {

				contrat = codao.findByID(res.getInt(2));
				avenant = new Avenant(res.getInt(1), contrat, res.getDate(3), res.getFloat(4), res.getFloat(5),
						res.getFloat(6), res.getFloat(7), res.getString(8));
				liste.add(avenant);

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

	public List<String> nombreAvenantsParMois(String anneedeb, String anneefin) throws DAOException {
		List<String> liste = new ArrayList<String>();

		String requ = "SELECT month( datedeb_aiavenant ) AS mois, year( datedeb_aiavenant )"
				+ " AS an, count( idaiavenant ) AS nbre 	FROM " + DAOConstants.t_aiavenant
				+ " WHERE year( datedeb_aiavenant )	BETWEEN '" + anneedeb + "' 	AND '" + anneefin
				+ "'		GROUP BY year( datedeb_aiavenant ) , month( datedeb_aiavenant )";

		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(requ);
			while (res.next()) {
				int mm = res.getInt(1);
				int yy = res.getInt(2);
				int nb = res.getInt(3);
				String retour = mm + "," + yy + "," + nb;
				liste.add(retour);

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

		return liste;

	}

	/**
	 * liste des avenants se situant entre les dates de debut et de fin
	 * 
	 * @param a
	 * @param b
	 * @return
	 * @throws DAOException
	 */
	public List<Avenant> listeAvenantDansFourchetteDates(Date a, Date b) throws DAOException {
		List<Avenant> liste = new ArrayList<Avenant>();
		Statement st = null;
		ResultSet rs = null;
		Avenant avenant = null;
		ContratDAO contdao = new ContratDAO();
		Contrat contrat = null;

		String sql = "SELECT idaiavenant, idaicontrat, datedeb_aiavenant,datefin_aiavenant " + " FROM "
				+ DAOConstants.t_aiavenant + " where datedeb_aiavenant>='" + a + "' and datefin_aiavenant<='" + b + "'";

		try {
			st = connect.createStatement();

			// Ex�cution de la requ�te et r�cup�ration du r�sultat dans un
			// resultset
			rs = st.executeQuery(sql);

			// TANT QUE la fin du resultset n'est pas atteinte
			while (rs.next()) {
				contrat = contdao.findByID(rs.getInt(2));
				avenant = new Avenant(rs.getInt(1), contrat, rs.getDate(3), rs.getDate(4));
				liste.add(avenant);
			}
		} catch (SQLException ex) {
			System.out.print("Probléme " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}
		return liste;
	}

	/**
	 * recupere le dernier avenant AI etabli pour le contrat indiqué
	 * 
	 * @param contrat
	 * @return
	 * @throws DAOException
	 */
	public Avenant dernierAvenantContrat(Contrat contrat) throws DAOException {
		Avenant retour = null;
		String query = "select max(idaiavenant) as maxi from " + DAOConstants.t_aiavenant + " where idaicontrat="
				+ contrat.getIdaicontrat();

		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				retour = this.findByID(res.getInt(1));
			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return retour;

	}

	/**
	 * recupere l' avenant ayant la date de debut la plus grande
	 * 
	 * @param identite
	 * @return
	 * @throws DAOException
	 */
	public Avenant dernierAvenantPersonne(Identite identite, Employeur emp) throws DAOException {
		Avenant retour = null;
		String query = "select idaiavenant  from " + DAOConstants.t_aiavenant + " inner join "
				+ DAOConstants.t_aicontrat + " on " + DAOConstants.t_aiavenant + ".idaicontrat="
				+ DAOConstants.t_aicontrat + ".idaicontrat " + " where id_identite=" + identite.getId_IDE()
				+ " and id_employeur=" + emp.getId_employeur() + " order by " + DAOConstants.t_aiavenant
				+ ".idaiavenant desc," + DAOConstants.t_aiavenant + ".datedeb_aiavenant desc limit 0,1";

		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				retour = this.findByID(res.getInt(1));
			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return retour;

	}

	/**
	 * liste des avenants se terminant entre les dates de debut et de fin
	 * 
	 * @param a
	 *            date de debut
	 * @param b
	 *            date de fin
	 * @return
	 * @throws DAOException
	 */
	public List<Avenant> listeAvenantMutuelleParDates(Date a, Date b) throws DAOException {
		List<Avenant> liste = new ArrayList<Avenant>();
		Statement st = null;
		ResultSet rs = null;
		Avenant avenant = null;
		ContratDAO contdao = new ContratDAO();
		Contrat contrat = null;

		String sql = "SELECT idaiavenant, " + DAOConstants.t_aiavenant
				+ ".idaicontrat, datedeb_aiavenant,datefin_aiavenant,dateecheance,dateecheancerefus " + " FROM "
				+ DAOConstants.t_aiavenant + " inner join " + DAOConstants.t_aicontrat + " on "
				+ DAOConstants.t_aiavenant + ".idaicontrat=" + DAOConstants.t_aicontrat + ".idaicontrat "
				+ " inner join " + DAOConstants.t_mutuelle + " on " + DAOConstants.t_aicontrat + ".id_identite="
				+ DAOConstants.t_mutuelle + ".id_identite " + " where (datefin_aiavenant>='" + a
				+ "' and datefin_aiavenant<='" + b + "') and (dateecheance <='" + b + "' or dateecheance is null)"
				+ " and (dateecheancerefus <='" + b + "' or dateecheancerefus is null)  "
				+" group by idaiavenant, " + DAOConstants.t_aiavenant
				+ ".idaicontrat, datedeb_aiavenant,datefin_aiavenant order by datefin_aiavenant desc";

		try {
			st = connect.createStatement();

			// Ex�cution de la requ�te et r�cup�ration du r�sultat dans un
			// resultset
			rs = st.executeQuery(sql);

			// TANT QUE la fin du resultset n'est pas atteinte
			while (rs.next()) {
				contrat = contdao.findByID(rs.getInt(2));
				avenant = new Avenant(rs.getInt(1), contrat, rs.getDate(3), rs.getDate(4));
				liste.add(avenant);
			}
		} catch (SQLException ex) {
			System.out.print("Probléme " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}
		return liste;
	}

}
