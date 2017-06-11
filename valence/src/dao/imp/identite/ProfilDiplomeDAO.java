package dao.imp.identite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.identite.ProfilDiplome;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.interfaces.DAO;

public class ProfilDiplomeDAO implements DAO<ProfilDiplome> {
	final private String query_insert = "INSERT INTO " + DAOConstants.t_diplome
			+ " (id_identite,diplome_diplome,obtenu_diplome,annee_diplome) VALUES(?,?, ?, ?)";
	
		final private String query_delete = "DELETE FROM " + DAOConstants.t_diplome
			+ " WHERE id_diplome= ";

	private Connection connect = null;

	public ProfilDiplomeDAO() {
		connect = DBConnexion.getConnexion();
	}

	/** liste des diplomes appartenant � la personne dont l'id est indiqué	
	 * 
	 */
	 
	 	public ProfilDiplome findByID(int id) throws DAOException {
	
		return null;
	}

	@Override
	public List<ProfilDiplome> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	/**s�lection de tous les diplomes d'une personne	
	 * 
	 */
	public List<ProfilDiplome> findByCriteria(ProfilDiplome obj)
			throws DAOException {
		List<ProfilDiplome> liste = new ArrayList<ProfilDiplome>();
		Statement st = null;
		ResultSet rs = null;
		ProfilDiplome prof = null;
		String req="select `id_diplome`, `id_identite`, "
				+ "`diplome_diplome`, `obtenu_diplome`, `annee_diplome` "
				+ "from "
				+DAOConstants.t_diplome+
				" where 1=1 ";
		if (obj.getId_identite() !=0)
			req += " AND id_identite="+obj.getId_identite();
		if(obj.getNomDiplome()!=null)
			req+="and diplome_diplome="+obj.getNomDiplome();
		try {
			st=connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs=st.executeQuery(req);
			while(rs.next()){
				prof=new ProfilDiplome(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),
						rs.getString(5));
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
	public int create(ProfilDiplome obj) throws DAOException {
		PreparedStatement pst = null;
		int cle=0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, obj.getId_identite());
			pst.setString(2, obj.getNomDiplome());
			pst.setString(3,obj.getObtenu());
			pst.setString(4, obj.getAnnee());
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
	public ProfilDiplome update(ProfilDiplome obj) throws DAOException {
		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
		String query = query_delete + obj.getId_identite();
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.executeUpdate();
			pst1=connect.prepareStatement(query_insert);
			pst1.setInt(1, obj.getId_identite());
			pst1.setString(2, obj.getNomDiplome());
			pst1.setString(3,obj.getObtenu());
			pst1.setString(4, obj.getAnnee());
			pst1.executeUpdate();
			connect.commit();

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closePrepareStatement(pst);

			// Fermeture du statement
			DAOUtil.closePrepareStatement(pst1);
		}

		return obj;

	}
	

	@Override
	public int delete(ProfilDiplome obj) throws DAOException {
		int num=obj.getId_diplome();
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
	
	
	public List<String> recherchesurPartieDiplome(String dip)throws DAOException{
		String prof=null;
		List<String> liste=new ArrayList<String>();
		Statement st = null;
		ResultSet res = null;
		String req = "select diplome_diplome from " + DAOConstants.t_diplome
				+ "  where diplome_diplome like '%" + dip + "%'";
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while(res.next()) {

				prof=res.getString(1);
				liste.add(prof);

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


