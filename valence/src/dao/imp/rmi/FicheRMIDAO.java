package dao.imp.rmi;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.identite.Identite;
import beans.parametres.capemploi.Utilisateur;
import beans.parametres.capemploi.UtilisateurDAO;
import beans.rmi.FicheRMI;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.imp.identite.IdentiteDAO;
import dao.interfaces.DAO;
import divers.FormaterDate;

public class FicheRMIDAO implements DAO<FicheRMI> {

	private Connection connect = null;
	final private String query_insert = "insert into "
			+ DAOConstants.t_rmicreation
			+ "(id_identite,prescripteur,referent_rmi,referentcorfi_rmi,datecrea_rmi,"
			+ "fonction,agence,mail) values (?,?,?,?,?,?,?,?)";

	final private String query_update = "update "
			+ DAOConstants.t_rmicreation
			+ " set id_identite=?,prescripteur=?,referent_rmi=?,referentcorfi_rmi=?,datecrea_rmi=?,"
			+ "fonction=? ,agence=?,mail=?" + " where id_rmi=";

	public FicheRMIDAO() {
		connect = DBConnexion.getConnexion();
	}

	@Override
	public FicheRMI findByID(int id) throws DAOException {
		String query = "select  `id_rmi`, `id_identite`, `prescripteur`,"
				+ " `referent_rmi`, `referentcorfi_rmi`, `datecrea_rmi`,"
				+ " `fonction`, `agence`, `mail` from " 
				+ DAOConstants.t_rmicreation
				+ " where id_rmi=" + id;
		FicheRMI pl = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.next()) {
				pl = new FicheRMI(id,
						new IdentiteDAO().findByID(res.getInt(2)),
						res.getString(3), res.getString(4),
						new UtilisateurDAO().findByID(res.getInt(5)),
						res.getDate(6), res.getString(7), res.getString(8),
						res.getString(9));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return pl;

	}

	@Override
	public List<FicheRMI> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FicheRMI> findByCriteria(FicheRMI obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(FicheRMI obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, obj.getIdentite().getId_IDE());
			pst.setString(2, obj.getPrescripteur());
			pst.setString(3, obj.getResponsable());
			pst.setInt(4, obj.getReferent().getId_salarie());
			pst.setDate(5, (Date) obj.getCreation_rmi());
			pst.setString(6, obj.getFonction());
			pst.setString(7, obj.getAgence());
			pst.setString(8, obj.getMail());

			pst.executeUpdate();
			ResultSet rs = pst.getGeneratedKeys();
			if (rs != null && rs.first())
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
	public FicheRMI update(FicheRMI obj) throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getId_rmi();

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.setInt(1, obj.getIdentite().getId_IDE());
			pst.setString(2, obj.getPrescripteur());
			pst.setString(3, obj.getResponsable());
			pst.setInt(4, obj.getReferent().getId_salarie());
			pst.setDate(5, (Date) obj.getCreation_rmi());
			pst.setString(6, obj.getFonction());
			pst.setString(7, obj.getAgence());
			pst.setString(8, obj.getMail());
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
	public int delete(FicheRMI obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int verifierCreationFiche(Identite identite) throws DAOException {
		int retour = 0;
		String req = "select  count(*) as nbre from "
				+ DAOConstants.t_rmicreation + "  where id_identite="
				+ identite.getId_IDE();

		Statement stat = null;
		ResultSet res = null;
		try {

			stat = connect.createStatement();
			res = stat.executeQuery(req);
			if (res.first())
				retour = res.getInt(1);

		} catch (SQLException ex) {
			System.out.print("Problème " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return retour;

	}

	public FicheRMI recupereDerniereFicheRMI(Identite identite)
			throws DAOException {
		String query = "select max(id_rmi) from " + DAOConstants.t_rmicreation
				+ " where id_identite=" + identite.getId_IDE();
		FicheRMI pl = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				pl = this.findByID(res.getInt(1));
			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return pl;

	}

	/** affiche les contrats rmi se terminant dans la periode indiquee */
	public List<List<String>> contratRMIParDates(Date a, Date b, Utilisateur ref)
			throws DAOException {
		List<List<String>> liste = new ArrayList<List<String>>();

		Statement st = null;
		ResultSet res = null;
		String req = "select " + DAOConstants.t_rmicreation
				+ ".id_identite, prescripteur,referentcorfi_rmi, "
				+ "datedeb_rmicontrat,datefin_rmicontrat from "
				+ DAOConstants.t_rmicreation + " inner join "
				+ DAOConstants.t_rmicontrat + " on "
				+ DAOConstants.t_rmicreation + ".id_identite="
				+ DAOConstants.t_rmicontrat
				+ ".id_identite 	where (datefin_rmicontrat >='" + a
				+ "' and datefin_rmicontrat<='" + b + "') ";

		if (ref != null)
			req += " and referentcorfi_rmi=" + ref.getId_salarie();

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {
				List<String> affiche = new ArrayList<String>();
				affiche.add(res.getString(1));
				affiche.add(res.getString(2));
				affiche.add(res.getString(3));
				affiche.add(new FormaterDate().formateDate(res.getDate(4))
						.toString());
				affiche.add(new FormaterDate().formateDate(res.getDate(5))
						.toString());

				liste.add(affiche);

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
	
	

	/** affiche les contrats rmi qui commencent sur la periode indiquee */
	public List<List<String>> contratRMICommenceParDates(Date a, Date b, Utilisateur ref)
			throws DAOException {
		List<List<String>> liste = new ArrayList<List<String>>();

		Statement st = null;
		ResultSet res = null;
		String req = "select " + DAOConstants.t_rmicreation
				+ ".id_identite, prescripteur,referentcorfi_rmi, "
				+ "datedeb_rmicontrat,datefin_rmicontrat from "
				+ DAOConstants.t_rmicreation + " inner join "
				+ DAOConstants.t_rmicontrat + " on "
				+ DAOConstants.t_rmicreation + ".id_identite="
				+ DAOConstants.t_rmicontrat
				+ ".id_identite 	where (datedeb_rmicontrat >='" + a
				+ "' and datedeb_rmicontrat<='" + b + "') ";

		if (ref != null)
			req += " and referentcorfi_rmi=" + ref.getId_salarie();

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {
				List<String> affiche = new ArrayList<String>();
				affiche.add(res.getString(1));
				affiche.add(res.getString(2));
				affiche.add(res.getString(3));
				affiche.add(new FormaterDate().formateDate(res.getDate(4))
						.toString());
				affiche.add(new FormaterDate().formateDate(res.getDate(5))
						.toString());

				liste.add(affiche);

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


	/** affiche les contrats rmi se terminant dans la periode indiquee */
	public List<List<String>> contratRMIEnCoursParDates(Date a, Date b)
			throws DAOException {
		List<List<String>> liste = new ArrayList<List<String>>();

		Statement st = null;
		ResultSet res = null;
		String req = "select "
				+ DAOConstants.t_rmicreation
				+ ".id_identite, prescripteur,referentcorfi_rmi, "
				+ "datedeb_rmicontrat,datefin_rmicontrat from "
				+ DAOConstants.t_rmicreation
				+ " inner join "
				+ DAOConstants.t_rmicontrat
				+ " on "
				+ DAOConstants.t_rmicreation
				+ ".id_identite="
				+ DAOConstants.t_rmicontrat
				+ ".id_identite 	where (datedeb_rmicontrat is not null and datedeb_rmicontrat between '"
				+ a
				+ "' and '"
				+ b
				+ "') or (datefin_rmicontrat is not null and datefin_rmicontrat between '"
				+ a
				+ "' and '"
				+ b
				+ "') or (datedeb_rmicontrat is not null and datefin_rmicontrat is not null"
				+ " and (datedeb_rmicontrat < '" + a
				+ "' and datefin_rmicontrat > '" + b + "')) ";

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {
				List<String> affiche = new ArrayList<String>();
				affiche.add(res.getString(1));
				affiche.add(res.getString(2));
				affiche.add(res.getString(3));
				affiche.add(new FormaterDate().formateDate(res.getDate(4))
							.toString());
				affiche.add(new FormaterDate().formateDate(res.getDate(5))
							.toString());
				
				liste.add(affiche);

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
	 * affiche le nombre de personnes differentes dont les contrats se
	 * terminent dans la periode indiquee
	 */
	public int totalPersonnesParDates(Date a, Date b, Utilisateur ref)
			throws DAOException {
		int total = 0;
		Statement st = null;

		ResultSet res = null;
		String req = "select count(distinct(" + DAOConstants.t_rmicontrat
				+ ".id_identite)) as totid from (" + DAOConstants.t_rmicontrat
				+ " left join " + DAOConstants.t_rmicreation + " on "
				+ DAOConstants.t_rmicreation + ".id_identite="
				+ DAOConstants.t_rmicontrat
				+ ".id_identite)  where datefin_rmicontrat >='" + a
				+ "' and datefin_rmicontrat<='" + b + "' ";

		if (ref != null)
			req += " and referentcorfi_rmi=" + ref.getId_salarie();

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			if (res.first()) {

				total = res.getInt(1);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return total;

	}
	
	
	/**
	 * affiche le nombre de personnes doifferentes dont les contrats commencentse
	 * sur la periode indiquee
	 */
	public int totalPersonnesParDatesDebut(Date a, Date b, Utilisateur ref)
			throws DAOException {
		int total = 0;
		Statement st = null;

		ResultSet res = null;
		String req = "select count(distinct(" + DAOConstants.t_rmicontrat
				+ ".id_identite)) as totid from (" + DAOConstants.t_rmicontrat
				+ " left join " + DAOConstants.t_rmicreation + " on "
				+ DAOConstants.t_rmicreation + ".id_identite="
				+ DAOConstants.t_rmicontrat
				+ ".id_identite)  where datedeb_rmicontrat >='" + a
				+ "' and datedeb_rmicontrat<='" + b + "' ";

		if (ref != null)
			req += " and referentcorfi_rmi=" + ref.getId_salarie();

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			if (res.first()) {

				total = res.getInt(1);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return total;

	}

	/**
	 * affiche le nombre de personnes differentes dont les contrats sont en
	 * cours dans la periode indiquee
	 */
	public int totalPersonnesParDatesContratsEnCours(Date a, Date b)
			throws DAOException {
		int total = 0;
		Statement st = null;

		ResultSet res = null;
		String req = "select count(distinct(" + DAOConstants.t_rmicontrat
				+ ".id_identite)) as totid from (" + DAOConstants.t_rmicontrat
				+ " left join " + DAOConstants.t_rmicreation + " on "
				+ DAOConstants.t_rmicreation + ".id_identite="
				+ DAOConstants.t_rmicontrat
				+ ".id_identite)  	where (datedeb_rmicontrat between '" + a
				+ "' and '" + b + "') or (datefin_rmicontrat between '" + a
				+ "' and '" + b + "') or (datedeb_rmicontrat < '" + a
				+ "' and datefin_rmicontrat > '" + b + "') ";

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			if (res.first()) {

				total = res.getInt(1);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return total;

	}

	/** affiche le total des suivis rmi se terminant dans la periode indiquee */
	public int totalSuivisParPersonneDates(Date a, Date b, Identite identite)
			throws DAOException {
		int total = 0;
		Statement st = null;
		ResultSet res = null;
		String req = "select count(idsuivi)as tot from " + DAOConstants.t_suivi
				+ " where date_suivi >='" + a + "' and date_suivi <='" + b
				+ "' and id_identite=" + identite.getId_IDE();

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			if (res.first()) {
				total = res.getInt(1);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return total;

	}

}
