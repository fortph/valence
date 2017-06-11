package dao.imp.employeur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.employeurs.Employeur;
import beans.employeurs.Service;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.interfaces.DAO;
import divers.FormaterChaine;

public class ServiceDAO implements DAO<Service> {

	final private String query_insert = "insert into "
			+ DAOConstants.t_employeurservice + "( id_employeur, service)"
			+ " values (?,?)";
	final private String query_update = "update "
			+ DAOConstants.t_employeurservice
			+ " set id_employeur=?, service=?" + " where id_employeurservice=";

	private Connection connect = null;

	public ServiceDAO() {
		connect = DBConnexion.getConnexion();

	}

	@Override
	public Service findByID(int id) throws DAOException {
		String query = "select  `id_employeurservice`, `id_employeur`,"
				+ " `service` from " + DAOConstants.t_employeurservice
				+ " where  id_employeurservice=" + id;
		Service service = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				service = new Service((int) id, new EmployeurDAO().findByID(res
						.getInt(2)), res.getString(3));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return service;

	}

	public Service findByName(String n) throws DAOException {
		FormaterChaine fc=new FormaterChaine();
		String nom=fc.supprimerApostrophe(n);
		String query = "select  `id_employeurservice`, `id_employeur`,"
				+ " `service` from " + DAOConstants.t_employeurservice
				+ " where service like '" + nom + "'";
		Service serv = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {

				serv = new Service(res.getInt(1),
						new EmployeurDAO().findByID(res.getInt(2)),
						res.getString(3));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return serv;
	}

	@Override
	public List<Service> findAll() throws DAOException {
		List<Service> liste = new ArrayList<Service>();
		Statement stat = null;
		ResultSet rs = null;
		String req = "select  `id_employeurservice`, `id_employeur`,"
				+ " `service` from " + DAOConstants.t_employeurservice
				+ "  order by service asc";

		Service services = null;
		try {

			stat = connect.createStatement();
			rs = stat.executeQuery(req);

			EmployeurDAO empdao = new EmployeurDAO();
			Employeur employeur = null;

			while (rs.next()) {
				employeur = empdao.findByID(rs.getInt(2));
				services = new Service(rs.getInt(1), employeur, rs.getString(3));
				liste.add(services);
			}

		} catch (SQLException ex) {
			System.out.print("Probl�me " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return liste;

	}

	/** liste les diff�rents services de l'employeur dont l'id est indiqué	
	 * 
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public List<Service> differentsServices(int id) throws DAOException {
		List<Service> liste = new ArrayList<Service>();
		Statement stat = null;
		ResultSet rs = null;
		String req = "select  `id_employeurservice`, `id_employeur`,"
				+ " `service` from " + DAOConstants.t_employeurservice
				+ " where id_employeur=" + id + "  order by service asc";

		Service services = null;
		try {

			stat = connect.createStatement();
			rs = stat.executeQuery(req);

			while (rs.next()) {
				services = new Service(rs.getInt(1),
						new EmployeurDAO().findByID(id), rs.getString(3));
				liste.add(services);
			}

		} catch (SQLException ex) {
			System.out.print("Probl�me " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return liste;

	}

	@Override
	public List<Service> findByCriteria(Service obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(Service obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);

			pst.setInt(1, obj.getId_employeur().getId_employeur());
			pst.setString(2, obj.getService());

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
	public Service update(Service obj) throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getId_employeurservice();

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);

			pst.setInt(1, obj.getId_employeur().getId_employeur());
			pst.setString(2, obj.getService());

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
	public int delete(Service obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}
	/** liste les diff�rents services de l'employeur dont l'id est indiqué	
	 * 
	 * @param id_emp
	 * @return
	 * @throws DAOException
	 */
	public List<String> afficherServices(int id_emp) throws DAOException {
		List<String> liste = new ArrayList<String>();
		Statement stat = null;
		ResultSet rs = null;
		String req = "select service from " + DAOConstants.t_employeurservice
				+ " where id_employeur=" + id_emp + "  order by service asc";

		try {
			stat = connect.createStatement();
			rs = stat.executeQuery(req);

			while (rs.next())
				liste.add(rs.getString("service"));

		} catch (SQLException ex) {
			System.out.print("Probl�me " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return liste;

	}
	
	
}