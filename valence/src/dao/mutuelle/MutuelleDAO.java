package dao.mutuelle;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.identite.Identite;
import beans.mutuelle.Mutuelle;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.imp.identite.IdentiteDAO;
import dao.interfaces.DAO;

public class MutuelleDAO implements DAO<Mutuelle> {

	final private String query_insert = "insert into " + DAOConstants.t_mutuelle
			+ "( id_identite, dateproposition, acceptation,dateecheance,causerefus,dateecheancerefus)"
			+ " values (?,?,?,?,?,?)";

	final private String query_update = "update " + DAOConstants.t_mutuelle
			+ " set id_identite=?, dateproposition=?, acceptation=?,dateecheance=?,causerefus=?,dateecheancerefus=? "
			+ " where id_mutuelle=";

	private Connection connect = null;

	public MutuelleDAO() {
		connect = DBConnexion.getConnexion();

	}

	@Override
	public Mutuelle findByID(int id) throws DAOException {
		String query = "select id_identite,dateproposition,acceptation,dateecheance,causeRefus,dateecheancerefus from "
				+ DAOConstants.t_mutuelle + " where  id_mutuelle=" + id;
		Mutuelle org = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				org = new Mutuelle(id, new IdentiteDAO().findByID(res.getInt(1)), res.getDate(2), res.getBoolean(3),
						res.getDate(4), res.getString(5), res.getDate(6));

			}

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

		return org;
	}

	@Override
	public List<Mutuelle> findAll() throws DAOException {

		List<Mutuelle> liste = new ArrayList<Mutuelle>();
		Statement stat = null;
		ResultSet res = null;
		Mutuelle presc = null;
		String req = "select id_mutuelle,id_identite,dateproposition,acceptation,dateecheance,causerefus,dateecheancerefus from "
				+ DAOConstants.t_mutuelle + " order by id_mutuelle asc";

		try {
			connect.setAutoCommit(false);
			stat = connect.createStatement();
			res = stat.executeQuery(req);
			while (res.next()) {
				presc = new Mutuelle(res.getInt(1), new IdentiteDAO().findByID(res.getInt(2)), res.getDate(3),
						res.getBoolean(4), res.getDate(5), res.getString(6), res.getDate(7));
				liste.add(presc);
			}
			connect.commit();

		} catch (SQLException ex) {
			System.out.print("Probleme " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return liste;
	}

	public List<Mutuelle> findAllParPersonne(Identite une) throws DAOException {

		List<Mutuelle> liste = new ArrayList<Mutuelle>();
		Statement stat = null;
		ResultSet res = null;
		Mutuelle presc = null;
		String req = "select id_mutuelle,dateproposition,acceptation,dateecheance,causerefus,dateecheancerefus from "
				+ DAOConstants.t_mutuelle + " where id_identite=" + une.getId_IDE();

		try {
			connect.setAutoCommit(false);
			stat = connect.createStatement();
			res = stat.executeQuery(req);
			while (res.next()) {
				presc = new Mutuelle(res.getInt(1), res.getDate(2), res.getBoolean(3), res.getDate(4), res.getString(5),
						res.getDate(6));
				liste.add(presc);
			}
			connect.commit();

		} catch (SQLException ex) {
			System.out.print("Probleme " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return liste;
	}

	@Override
	public List<Mutuelle> findByCriteria(Mutuelle obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(Mutuelle obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert, Statement.RETURN_GENERATED_KEYS);

			pst.setInt(1, obj.getIdentite().getId_IDE());
			pst.setDate(2, (Date) obj.getDateProposition());
			pst.setBoolean(3, obj.isAcceptation());
			pst.setDate(4, (Date) obj.getDateecheance());
			pst.setString(5, obj.getCauseRefus());
			pst.setDate(6, (Date) obj.getDateEcheanceMultiEmp());

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
			try {
				pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return cle;
	}

	@Override
	public Mutuelle update(Mutuelle obj) throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getId_mutuelle();

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.setInt(1, obj.getIdentite().getId_IDE());
			pst.setDate(2, (Date) obj.getDateProposition());
			pst.setBoolean(3, obj.isAcceptation());
			pst.setDate(4, (Date) obj.getDateecheance());
			pst.setString(5, obj.getCauseRefus());
			pst.setDate(6, (Date) obj.getDateEcheanceMultiEmp());
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
			try {
				pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return obj;
	}

	@Override
	public int delete(Mutuelle obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * on vérifie qu'une fiche mutuelle existe pour cette personne
	 * 
	 * @param identite
	 * @return
	 * @throws DAOException
	 */
	public int verifierCreationFiche(Identite identite) throws DAOException {
		int retour = 0;
		String req = "select  count(*) as nbre from " + DAOConstants.t_mutuelle + "  where id_identite="
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
/**
 * renvoie le nombre de fiches mutuelle en cours de validité
 * @param identite
 * @return
 * @throws DAOException
 */
	public int contratEnCoursMutuelle(Identite identite) throws DAOException {
		int retour = 0;
		String requ = "select count(*) as nbre from " + DAOConstants.t_mutuelle + " where id_identite="
				+ identite.getId_IDE() + " and (dateecheance >= CURDATE() or dateecheancerefus >=CURDATE())";

		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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

	public List<Mutuelle> fichesAcompleter() throws DAOException {

		List<Mutuelle> liste = new ArrayList<Mutuelle>();
		IdentiteDAO idao=new IdentiteDAO();
		Statement stat = null;
		ResultSet res = null;
		Mutuelle presc = null;
		String req = "select id_mutuelle,id_identite,dateproposition,acceptation,dateecheance,causerefus,dateecheancerefus from "
				+ DAOConstants.t_mutuelle
				+ " where (dateecheance is null and dateecheancerefus is null) order by id_mutuelle asc";

		try {
			connect.setAutoCommit(false);
			stat = connect.createStatement();
			res = stat.executeQuery(req);
			while (res.next()) {
				presc = new Mutuelle(res.getInt(1), idao.findByID(res.getInt(2)),res.getDate(3), res.getBoolean(4), res.getDate(5), res.getString(6),
						res.getDate(7));
				liste.add(presc);
			}
			connect.commit();

		} catch (SQLException ex) {
			System.out.print("Probleme " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return liste;
	}
	/**
	 * récupération de la date de fin de couverture mutuelle
	 * @param identite
	 * @return
	 * @throws DAOException
	 */
	public Date couvertureMaxi(Identite identite) throws DAOException {

		Date max, max1, retour = null;
		Statement stat = null;
		ResultSet res = null;
		String req = "select id_mutuelle,dateecheance,dateecheancerefus from " + DAOConstants.t_mutuelle
				+ " where id_identite=" + identite.getId_IDE() + " order by id_mutuelle desc";

		try {
			connect.setAutoCommit(false);
			stat = connect.createStatement();
			res = stat.executeQuery(req);
			if (res.first()) {
				max = res.getDate(2);
				max1 = res.getDate(3);
				if (max == null)
					retour = max1;
				else if (max1 == null)
					retour = max;
				else if (max == null & max1 == null)
					retour = null;
				else if (max.getTime() > max1.getTime())
					retour = max;
				else
					retour = max1;

			}
			connect.commit();

		} catch (SQLException ex) {
			System.out.print("Probleme " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return retour;
	}
	
	/**
	 * liste toutes les propositions de mutuelle dont la date d'echeance est comprise entre les dates données
	 * @param deb
	 * @param fin
	 * @return
	 * @throws DAOException
	 */
	public List<Mutuelle> termineesDansFourchette(Date deb, Date fin) throws DAOException {

		List<Mutuelle> liste = new ArrayList<Mutuelle>();
		IdentiteDAO idao=new IdentiteDAO();
		Statement stat = null;
		ResultSet res = null;
		Mutuelle presc = null;
		String req = "select id_mutuelle,id_identite,dateproposition,acceptation,dateecheance,causerefus,dateecheancerefus from "
				+ DAOConstants.t_mutuelle
				+ " where dateecheance between '"+deb+"' and '"+fin+"'  order by id_mutuelle asc";

		try {
			connect.setAutoCommit(false);
			stat = connect.createStatement();
			res = stat.executeQuery(req);
			while (res.next()) {
				presc = new Mutuelle(res.getInt(1), idao.findByID(res.getInt(2)),res.getDate(3), res.getBoolean(4), res.getDate(5), res.getString(6),
						res.getDate(7));
				liste.add(presc);
			}
			connect.commit();

		} catch (SQLException ex) {
			System.out.print("Probleme " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return liste;
	}

}
