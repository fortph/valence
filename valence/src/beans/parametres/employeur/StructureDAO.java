package beans.parametres.employeur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.interfaces.DAO;

public class StructureDAO implements DAO<Structure> {

	final private String query_insert = "INSERT INTO "
			+ DAOConstants.t_employeurstructure
			+ " (structure,actif) VALUES(?,?)";

	final private String query_update = "update "
			+ DAOConstants.t_employeurstructure
			+ " set structure=?,actif=? where id_structure=?";

	private Connection connect = null;

	public StructureDAO() {
		connect = DBConnexion.getConnexion();
	}

	@Override
	public Structure findByID(int id) throws DAOException {
		String query = "select * from " + DAOConstants.t_employeurstructure
				+ " where  id_structure=" + id;
		Structure structure = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				structure = new Structure((int) id, res.getString(2),
						res.getBoolean(3));

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

		return structure;

	}
/**recherche une structure par son libelle
 * 
 * @param nom
 * @return
 * @throws DAOException
 */
	public Structure findByName(String nom) throws DAOException {
		Structure structure = null;
		Statement st = null;
		ResultSet res = null;
		String req = "select * from " + DAOConstants.t_employeurstructure
				+ " where structure='" + nom + "'";

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			if (res.first()) {
				structure = new Structure(res.getInt(1), res.getString(2),
						res.getBoolean(3));

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

		return structure;

	}

	@Override
	public List<Structure> findAll() throws DAOException {
		List<Structure> liste = new ArrayList<Structure>();
		Statement stat = null;
		ResultSet rs = null;
		Structure nivo = null;
		String req = "select * from " + DAOConstants.t_employeurstructure
				+" order by structure asc";
		try {
			connect.setAutoCommit(false);
			stat = connect.createStatement();
			rs = stat.executeQuery(req);

			while (rs.next()) {
				nivo = new Structure(rs.getInt(1),
						rs.getString(2),rs.getBoolean(3));
				liste.add(nivo);
			}
			connect.commit();

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
	public List<Structure> findByCriteria(Structure obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(Structure obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, obj.getStructure());
			pst.setBoolean(2, obj.isActif());

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
	public Structure update(Structure obj) throws DAOException {
		PreparedStatement pst = null;

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_update);
			pst.setString(1, obj.getStructure());
			pst.setBoolean(2, obj.isActif());

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
	public int delete(Structure obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<String> afficherStructure() throws DAOException {
		List<String> liste = null;
		Statement stat = null;
		ResultSet rs = null;
		String req = "select structure from "
				+ DAOConstants.t_employeurstructure + " order by structure asc";
	

		try {
			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			liste = new ArrayList<String>();
			while (rs.next())
				liste.add(rs.getString("structure"));

		} catch (SQLException ex) {
			System.out.print("Probl�me " + ex);
		} finally {
			try {
				stat.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return liste;

	}

}
