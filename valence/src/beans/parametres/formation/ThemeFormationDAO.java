package beans.parametres.formation;





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
import divers.FormaterChaine;

public class ThemeFormationDAO implements DAO<Theme> {
	

	final private String query_insert = "insert into "
			+ DAOConstants.t_themes
			+ "(libelle)"
			+ " values (?)";

	final private String query_update = "update "
			+ DAOConstants.t_themes
			+ " set libelle=? "
			+ " where id_themeformation=";

	
	
	
	private Connection connect = null;

	public ThemeFormationDAO() {
		connect = DBConnexion.getConnexion();

	}

	@Override
	public Theme findByID(int id) throws DAOException {
		String query = "select * from " + DAOConstants.t_themes
				+ " where  id_themeformation=" + id;
		Theme org = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				org = new Theme(id, res.getString(2));

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

	
	
	public Theme findByName(String nom) throws DAOException {
		String formation=new FormaterChaine().supprimerApostrophe(nom);
		String query = "select * from " + DAOConstants.t_themes
				+ " where  libelle like '" +formation+"'";
		Theme org = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				org = new Theme(res.getInt(1), res.getString(2));

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
	public List<Theme> findAll() throws DAOException {
		List<Theme> liste = new ArrayList<Theme>();
		Statement stat = null;
		ResultSet rs = null;
		Theme presc = null;
		String req = "select * from " + DAOConstants.t_themes
				+" order by libelle asc";
		;
		try {
			connect.setAutoCommit(false);
			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			while (rs.next()) {
				presc = new Theme(rs.getInt(1), rs.getString(2));
				liste.add(presc);
			}
			connect.commit();

		} catch (SQLException ex) {
			System.out.print("Probleme " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return liste;
	}

	@Override
	public List<Theme> findByCriteria(Theme obj) throws DAOException {
		ArrayList<Theme> liste = new ArrayList<Theme>();
		Theme of = null;
		Statement st = null;
		ResultSet res = null;
		String req = "select * from " + DAOConstants.t_themes
				+ " where 1=1 ";
		if (obj.getLibelle() != null)
			req += "and org =" + obj.getLibelle();

		
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {

				of = new Theme(res.getInt(1), res.getString(2));
				liste.add(of);

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

		return liste;
	}

	@Override
	public int create(Theme obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);
			
			pst.setString(1, obj.getLibelle());
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
	public Theme update(Theme obj) throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getId_themeformation();

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.setString(1, obj.getLibelle());
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
	public int delete(Theme obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public List<String> afficherThemesFormation() throws DAOException{
		List<String> liste=null;
		Statement stat=null;
		ResultSet rs=null;
		String req="select libelle from "+DAOConstants.t_themes +" order by libelle asc";
		
		try{
			
		stat=connect.createStatement();
		rs=stat.executeQuery(req);
		liste=new ArrayList<String>();
		while(rs.next())
			liste.add(rs.getString("libelle"));
		
	} catch (SQLException ex) {
		System.out.print("Probleme " + ex);
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