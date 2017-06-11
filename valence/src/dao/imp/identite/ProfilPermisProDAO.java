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
import beans.identite.ProfilPermisPro;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.interfaces.DAO;

public class ProfilPermisProDAO implements DAO<ProfilPermisPro> {
	final private String query_insert = "INSERT INTO " + DAOConstants.t_permis
			+ " (libelle_permis,id_identite,expire) VALUES(?,?,?)";
	
	final private String query_delete = "DELETE FROM " + DAOConstants.t_permis
			+ " WHERE id_permis= ";

	private Connection connect = null;

	public ProfilPermisProDAO() {
		connect = DBConnexion.getConnexion();
	}

	@Override
	public ProfilPermisPro findByID(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProfilPermisPro> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProfilPermisPro> findByCriteria(ProfilPermisPro obj)
			throws DAOException {
		List<ProfilPermisPro> liste = new ArrayList<ProfilPermisPro>();
		Statement st = null;
		ResultSet rs = null;
		ProfilPermisPro perm = null;
		String req = "select  `id_permis`, `libelle_permis`,"
				+ " `id_identite`, `expire` from " + DAOConstants.t_permis
				+ " where 1=1 " ;
		if(obj.getId_identite()!=null)
			req+=" and id_identite ="+obj.getId_identite();
		if(obj.getLibelle()!=null)
			req+=" and libelle_permis='"+obj.getLibelle()+"'";
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery(req);
			while(rs.next()) {
				perm = new ProfilPermisPro(rs.getInt(1), rs.getString(2),
						new IdentiteDAO().findByID(rs.getInt(3)),rs.getDate(4));
				liste.add(perm);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}
		return liste;
	}

	@Override
	public int create(ProfilPermisPro obj) throws DAOException {
		PreparedStatement pst = null;
		int cle=0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, obj.getLibelle());
			pst.setInt(2, obj.getId_identite().getId_IDE());
			pst.setDate(3,  (Date) obj.getExpiration());
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
	public ProfilPermisPro update(ProfilPermisPro obj) throws DAOException {
		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
		String query = query_delete + obj.getId_identite();
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.executeUpdate();
			pst1=connect.prepareStatement(query_insert);
			pst1.setString(1, obj.getLibelle());
			pst1.setInt(2, obj.getId_identite().getId_IDE());
			pst1.setDate(3,  (Date) obj.getExpiration());
			pst1.executeUpdate();
			connect.commit();


		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			DAOUtil.closePrepareStatement(pst1);
			DAOUtil.closePrepareStatement(pst);
		}

		return obj;
	}

	@Override
	public int delete(ProfilPermisPro obj) throws DAOException {
		int num = obj.getId_permis();
		return delete(num);
	}

	@Override
	public int delete(long id) throws DAOException {
		String query = query_delete + id;
		int retour = 0;
		Statement st = null;
		try {
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

	public List<String> afficherPermis() throws DAOException {
		List<String> liste = null;
		Statement stat = null;
		ResultSet rs = null;
		String req = null;
		try {
			req = "select libelle from " + DAOConstants.t_permis;
			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			liste = new ArrayList<String>();
			while (rs.next())
				liste.add(rs.getString("libelle"));

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
	
	public List<ProfilPermisPro> listingPermis(Identite identite)
			throws DAOException {
		List<ProfilPermisPro> liste = new ArrayList<ProfilPermisPro>();
		Statement st = null;
		ResultSet rs = null;
		ProfilPermisPro perm = null;
		String req = "select  `id_permis`, `libelle_permis`,"
				+ " `id_identite`, `expire` from " + DAOConstants.t_permis
				+ " where id_identite= "+identite.getId_IDE() ;
		
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery(req);
			while(rs.next()) {
				perm = new ProfilPermisPro(rs.getInt(1), rs.getString(2),
						new IdentiteDAO().findByID(rs.getInt(3)),rs.getDate(4));
				liste.add(perm);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}
		return liste;
	}

}
