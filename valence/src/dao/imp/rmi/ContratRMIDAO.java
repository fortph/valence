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
import beans.parametres.capemploi.UtilisateurDAO;
import beans.rmi.ContratRMI;
import beans.suivi.SuiviPersonne;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.imp.identite.IdentiteDAO;
import dao.interfaces.DAO;

public class ContratRMIDAO implements DAO<ContratRMI> {

	final private String query_insert = "insert into "
			+ DAOConstants.t_rmicontrat
			+ "( id_identite, datedeb_rmicontrat, datefin_rmicontrat)"
			+ " values (?,?,?)";

	final private String query_update = "update " + DAOConstants.t_rmicontrat
			+ " set id_identite=?, datedeb_rmicontrat=?, datefin_rmicontrat=? "
			+ " where id_rmicontrat=";

	private Connection connect = null;

	public ContratRMIDAO() {
		connect = DBConnexion.getConnexion();

	}

	@Override
	public ContratRMI findByID(int id) throws DAOException {
		String query = "select `id_rmicontrat`, `id_identite`, "
				+ "`datedeb_rmicontrat`, `datefin_rmicontrat` "
				+ "from " + DAOConstants.t_rmicontrat
				+ " where id_rmicontrat=" + id;
		ContratRMI pl = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.next()) {
				pl = new ContratRMI(id, new IdentiteDAO().findByID(res
						.getInt(2)), res.getDate(3), res.getDate(4));
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
	public List<ContratRMI> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ContratRMI> findByCriteria(ContratRMI obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(ContratRMI obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, obj.getIdentite().getId_IDE());
			pst.setDate(2, (Date) obj.getDatedeb_rmicontrat());
			pst.setDate(3, (Date) obj.getDatefin_rmicontrat());
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
	public ContratRMI update(ContratRMI obj) throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getId_rmicontrat();

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.setInt(1, obj.getIdentite().getId_IDE());
			pst.setDate(2, (Date) obj.getDatedeb_rmicontrat());
			pst.setDate(3, (Date) obj.getDatefin_rmicontrat());

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
	public int delete(ContratRMI obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int contratRMIEnCours(Identite identite) throws DAOException {
		int retour = 0;
		String requ = "select count(*) as nbre from "
				+ DAOConstants.t_rmicontrat + " where id_identite="
				+ identite.getId_IDE()
				+ " and (datefin_rmicontrat >= CURDATE() )";

		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(requ);
			if (res.first()) {
				retour = res.getInt(1);
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

		return retour;

	}

	/** liste de tous les contrats rmi de la personne indiquee */
	public List<ContratRMI> contratRMIParPersonne(Identite identite)
			throws DAOException {
		ArrayList<ContratRMI> liste = new ArrayList<ContratRMI>();
		ContratRMI contrat = null;
		Statement st = null;
		ResultSet res = null;
		String req = "select id_rmicontrat,datedeb_rmicontrat, datefin_rmicontrat "
				+ "  from "
				+ DAOConstants.t_rmicontrat
				+ " where id_identite="
				+ identite.getId_IDE() + "  order by id_rmicontrat asc ";

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {

				contrat = new ContratRMI(res.getInt(1), identite,
						res.getDate(2), res.getDate(3));
				liste.add(contrat);

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
	 * recupere le contat rmi avec l'id le plus elevé (le dernier ) d'une
	 * personne
	 */
	public ContratRMI recupereDernierContratRMI(Identite identite)
			throws DAOException {
		ContratRMI contrat = null;
		Statement st = null;
		ResultSet res = null;
		String req = "select max(id_rmicontrat) " + "  from "
				+ DAOConstants.t_rmicontrat + " where id_identite="
				+ identite.getId_IDE();

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			if (res.first()) {
				contrat = this.findByID(res.getInt(1));

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

	public List<SuiviPersonne> recupereDateSUivi(Identite identite,
			ContratRMI contratrmi) throws DAOException {
		List<SuiviPersonne> listesuivi = new ArrayList<SuiviPersonne>();
		SuiviPersonne suivi = null;
		Statement st = null;
		ResultSet res = null;
		String req = "select  `idsuivi`, "
				+DAOConstants.t_suivi
				+ ".id_identite, `date_suivi`,"
				+ " `idreferent`, `commentaire_suivi`  "
				+ "  from " + DAOConstants.t_suivi
				+ " inner join " + DAOConstants.t_rmicontrat + " on "
				+ DAOConstants.t_rmicontrat + ".id_identite="
				+ DAOConstants.t_suivi + ".id_identite where "
				+ DAOConstants.t_rmicontrat + ".id_identite="
				+ identite.getId_IDE() + " and date_suivi between '"
				+ contratrmi.getDatedeb_rmicontrat() + "'  and '"
				+ contratrmi.getDatefin_rmicontrat() 
				+ "' group by idsuivi";

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {
				suivi = new SuiviPersonne(res.getInt(1),
						new IdentiteDAO().findByID(res.getInt(2)),
						res.getDate(3), new UtilisateurDAO().findByID(res
								.getInt(4)), res.getString(5));
				listesuivi.add(suivi);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}
		return listesuivi;

	}

	public List<SuiviPersonne> recupereDateSUiviDansFourchetteContrat(
			Identite identite, ContratRMI contratrmi) throws DAOException {
		List<SuiviPersonne> listesuivi = new ArrayList<SuiviPersonne>();
		SuiviPersonne suivi = null;
		Statement st = null;
		ResultSet res = null;
		String req = "select  `idsuivi`, "
				+DAOConstants.t_suivi
				+ ".id_identite, `date_suivi`,"
				+ " `idreferent`, `commentaire_suivi`  "
				+ "from " + DAOConstants.t_suivi
				+ " inner join " + DAOConstants.t_rmicontrat + " on "
				+ DAOConstants.t_rmicontrat + ".id_identite="
				+ DAOConstants.t_suivi + ".id_identite where "
				+ DAOConstants.t_rmicontrat + ".id_identite="
				+ identite.getId_IDE() + " and date_suivi>=' "
				+ contratrmi.getDatedeb_rmicontrat() + "' and date_suivi <='"
				+ contratrmi.getDatefin_rmicontrat() + "' group by idsuivi";

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {
				suivi = new SuiviPersonne(res.getInt(1),
						new IdentiteDAO().findByID(res.getInt(2)),
						res.getDate(3), new UtilisateurDAO().findByID(res
								.getInt(4)), res.getString(5));
				listesuivi.add(suivi);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}
		return listesuivi;

	}

	/** affiche les contrats rmi se terminant dans la periode indiquee */
	public List<ContratRMI> contratRMIParDates(Date a, Date b, Identite identite)
			throws DAOException {
		List<ContratRMI> liste = new ArrayList<ContratRMI>();
		Statement st = null;
		ContratRMI contrat = null;
		ResultSet res = null;
		String req = "select " + DAOConstants.t_rmicontrat
				+ ".id_identite, datedeb_rmicontrat,datefin_rmicontrat from"
				+ DAOConstants.t_rmicontrat

				+ "  where id_identite=" + identite.getId_IDE()
				+ " and datefin_rmicontrat >='" + a
				+ "' and datefin_rmicontrat<='" + b + "' ";

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {
				contrat = new ContratRMI();
				liste.add(contrat);

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
