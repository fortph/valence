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

public class ActiviteDAO implements DAO<Activite> {
	final private String query_insert = "INSERT INTO "
			+ DAOConstants.t_employeuractivite
			+ " (activite,actif) VALUES(?,?)";

	final private String query_update = "update "
			+ DAOConstants.t_employeuractivite
			+ " set activite=?,actif=? where id_activite=?";
	

	private Connection connect = null;

	public ActiviteDAO() {
		connect = DBConnexion.getConnexion();
	}

	@Override
	public Activite findByID(int id) throws DAOException {
		String query = "select * from " + DAOConstants.t_employeuractivite
				+ " where  id_activite=" + id;
		Activite activite = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				activite = new Activite((int) id, res.getString(2),
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

		return activite;

	}
	/**
	 * recherche l'activité dont le nom est indiqué en parametre
	 * @param nom
	 * @return
	 * @throws DAOException
	 */
	public Activite findByName(String nom) throws DAOException {
		Activite activite=null;
		Statement st = null;
		ResultSet res = null;
		String req = "select * from "+DAOConstants.t_employeuractivite
				+" where activite='"+nom+"'";
				
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			if (res.first()) {
				activite = new Activite(res.getInt(1), res.getString(2),res.getBoolean(3));
				
				
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

		return activite;

	}
	
	@Override
	public List<Activite> findAll() throws DAOException {
		List<Activite> liste = new ArrayList<Activite>();
		Statement stat = null;
		ResultSet rs = null;
		Activite nivo = null;
		String req = "select * from " + DAOConstants.t_employeuractivite
				+" order by activite asc";
		try {
			connect.setAutoCommit(false);
			stat = connect.createStatement();
			rs = stat.executeQuery(req);

			while (rs.next()) {
				nivo = new Activite(rs.getInt(1),
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
	public List<Activite> findByCriteria(Activite obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(Activite obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, obj.getActivite());
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
	public Activite update(Activite obj) throws DAOException {
PreparedStatement pst = null;
		

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_update);
			pst.setString(1, obj.getActivite());
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
	public int delete(Activite obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}
	/**
	 * liste toutes les activités
	 * @return
	 * @throws DAOException
	 */
	public List<String> afficherActivites() throws DAOException{
		List<String> liste=null;
		Statement stat=null;
		ResultSet rs=null;
		String req="select activite from "+DAOConstants.t_employeuractivite+" order by activite asc";;
		
		try{
			stat=connect.createStatement();
			rs=stat.executeQuery(req);
			liste=new ArrayList<String>();
			while(rs.next())
				liste.add(rs.getString("activite"));
		
	} catch (SQLException ex) {
		System.out.print("Problème " + ex);
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
