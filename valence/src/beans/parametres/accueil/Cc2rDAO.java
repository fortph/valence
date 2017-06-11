package beans.parametres.accueil;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.DAOConstants;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.interfaces.DAO;

public class Cc2rDAO implements DAO<Cc2r>{
	

	final private String query_insert = "insert into "
			+ DAOConstants.t_cc2r
			+ "( libelle)"
			+ " values (?)";

	final private String query_delete = "delete from "
			+ DAOConstants.t_cc2r
			+ " where id_cc2r=?";
	
	private Connection connect = null;

	public Cc2rDAO() {
		connect = DBConnexion.getConnexion();

	}


	@Override
	public Cc2r findByID(int id) throws DAOException {
		String query = "select * from " + DAOConstants.t_cc2r
				+ " where  id_cc2r=" + id;
		Cc2r ville  = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				ville = new Cc2r( id, res.getString(2));

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

		return ville;

	}

	@Override
	public List<Cc2r> findAll() throws DAOException {
		String query = "select * from " + DAOConstants.t_cc2r;
		List<Cc2r> liste=new ArrayList<Cc2r>();
		Cc2r communaute = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			while (res.next()) {
				communaute = new Cc2r(res.getInt(1), res.getString(2));
				liste.add(communaute);
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
	public List<Cc2r> findByCriteria(Cc2r obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(Cc2r obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			///connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);
			
			pst.setString(1, obj.getLibelle());
			pst.executeUpdate();
			ResultSet rs = pst.getGeneratedKeys();
			if (rs != null && rs.first())
				cle = rs.getInt(1);
			//connect.commit();

		} catch (SQLException e) {
				e.printStackTrace();
			
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
	public Cc2r update(Cc2r obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(Cc2r obj) throws DAOException {
		int num = obj.getId_cc2r();
		return delete(num);
	}

	@Override
	public int delete(long id) throws DAOException {
		PreparedStatement st = null;
		int retour = 0;
		
			try {
				st = connect.prepareStatement(query_delete);
				st.setInt(1, (int) id);
				retour=st.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			 finally {
					try {
						st.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		

		return retour;
	}
	
	
	
	
	
	/** affiche toutes les villes de le communaute des communes
	 * 
	 * @return la liste des villes de la cc2r
	 * @throws DAOException
	 */
	
	public List<String> afficherVilles() throws DAOException{
		List<String> liste=null;
		Statement stat=null;
		ResultSet rs=null;
		String req="select libelle from "+DAOConstants.t_cc2r+" order by libelle asc";;
		
		try{
			stat=connect.createStatement();
			rs=stat.executeQuery(req);
			liste=new ArrayList<String>();
			while(rs.next())
				liste.add(rs.getString("libelle"));
		
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

	