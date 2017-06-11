package dao.imp.identite;

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
import beans.identite.*;

public class ProfilLocomotionDAO implements DAO<ProfilLocomotion> {

	final private String query_insert = "INSERT INTO "
			+ DAOConstants.t_locomotion
			+ " (libelle_locomotion,id_identite) VALUES(?,?)";
	
	final private String query_delete = "DELETE FROM "
			+ DAOConstants.t_locomotion + " WHERE id_locomotion= ";

	private Connection connect = null;

	public ProfilLocomotionDAO() {
		connect = DBConnexion.getConnexion();
	}

	@Override
	public ProfilLocomotion findByID(int id) throws DAOException {
		String query = "select  `id_locomotion`, `libelle_locomotion`,"
				+ " `id_identite` from " + DAOConstants.t_locomotion
				+ " where id_locomotion=" + id;
		ProfilLocomotion pl = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.next()) {
				pl = new ProfilLocomotion(id, res.getString(2),
						res.getInt(3));

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
	public List<ProfilLocomotion> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProfilLocomotion> findByCriteria(ProfilLocomotion obj)
			throws DAOException {
		List<ProfilLocomotion> liste = new ArrayList<ProfilLocomotion>();
		Statement st = null;
		ResultSet rs = null;
		ProfilLocomotion prof = null;
		String req= "select  `id_locomotion`, `libelle_locomotion`,"
				+ " `id_identite` from "+DAOConstants.t_locomotion+
				" where 1=1";
		if(obj.getId_identite()!=0)
			req+=" and id_identite ="+obj.getId_identite();
		
		if (obj.getLocomotion() != null)
			req += " AND libelle_locomotion LIKE '"
					+ obj.getLocomotion().toUpperCase() + "'";
		try {
			st=connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs=st.executeQuery(req);
			while(rs.next()){
				prof=new ProfilLocomotion(rs.getInt(1),rs.getString(2),rs.getInt(3));
				liste.add(prof);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}
		return liste;
	}

	@Override
	public int create(ProfilLocomotion obj) throws DAOException {
		PreparedStatement pst = null;
		int cle=0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, obj.getLocomotion());
			pst.setInt(2,obj.getId_identite());
			pst.executeUpdate();
			ResultSet rs = pst.getGeneratedKeys();
			if (rs != null && rs.next()) 
			cle = rs.getInt(1); 
			connect.commit();

		} catch (SQLException e) {
			try {
				connect.rollback();
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
	public ProfilLocomotion update(ProfilLocomotion obj) throws DAOException {
		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
		String query = query_delete + obj.getId_identite();
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.executeUpdate();
			pst1=connect.prepareStatement(query_insert);
			pst1.setString(1, obj.getLocomotion());
			pst1.setInt(2,obj.getId_identite());
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
	public int delete(ProfilLocomotion obj) throws DAOException {
		int num=obj.getId_locomotion();
		return delete(num);
	}

	@Override
	public int delete(long id) throws DAOException {
		String query = query_delete + id;
		int retour = 0;
		Statement st=null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			retour = st.executeUpdate(query);
			} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			
			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return retour;
	
	}

	public List<String> afficherLocomotion() throws DAOException {
		List<String> listeNiveaux = null;
		Statement stat = null;
		ResultSet rs = null;
		String req = null;
		try {

			req = "select libelle from " + DAOConstants.t_locomotion;
			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			listeNiveaux = new ArrayList<String>();
			while (rs.next())
				listeNiveaux.add(rs.getString("libelle_locomotion"));

		} catch (SQLException ex) {
			System.out.print("Problème " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return listeNiveaux;
	}

}
