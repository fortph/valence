package dao.imp.sap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.sap.TachesSAP;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.interfaces.DAO;
import divers.FormaterChaine;

public class TachesSAPDAO implements DAO<TachesSAP> {
	
	private Connection connect = null;
	
	final private String query_insert = "insert into "
			+ DAOConstants.t_saptaches
			+ "(libelle)"
			+ " values (?)";

	final private String query_update = "update "
			+ DAOConstants.t_saptaches
			+ " set libelle=?  "
			+ "where id_tache=";

	public TachesSAPDAO() {
		connect = DBConnexion.getConnexion();
	}
	@Override
	public TachesSAP findByID(int id) throws DAOException {
		String query = "select  `id_tache`, `libelle` from " + DAOConstants.t_saptaches
				+ " where id_tache=" + id;
		TachesSAP lf = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				
				lf = new TachesSAP(id, res.getString(2));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return lf;
	}
	
	
	public TachesSAP findByName(String nom) throws DAOException {
		nom=new FormaterChaine().supprimerApostrophe(nom);
		String query = "select  `id_tache`, `libelle` from " + DAOConstants.t_saptaches
				+ " where libelle like '"+nom+"' ";
		
		TachesSAP lf = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				
				lf = new TachesSAP(res.getInt(1), res.getString(2));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return lf;
	}


	@Override
	public List<TachesSAP> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TachesSAP> findByCriteria(TachesSAP obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(TachesSAP obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);

			pst.setString(1,obj.getLibelle());
			
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
	public TachesSAP update(TachesSAP obj) throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getId_tache();

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
			DAOUtil.closePrepareStatement(pst);
		}
		return obj;
	}

	@Override
	public int delete(TachesSAP obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	/** affiche toutes les formations  */
	public List<String> afficherListeTaches() throws DAOException {
		List<String> liste = null;
		Statement stat = null;
		ResultSet rs = null;
		String req = null;

		try {

			req = "select  `id_tache`, `libelle` from "
					+ DAOConstants.t_saptaches
					+ " where 1=1";
			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			liste = new ArrayList<String>();
			while (rs.next()) {
				String t = rs.getString("libelle") ;
				liste.add(t);

			}
		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return liste;

	}

}
