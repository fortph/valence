package beans.parametres.accueil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.DAOConstants;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.interfaces.DAO;

public class LocomotionDAO implements DAO<Locomotion> {
	
	private Connection connect = null;
	public LocomotionDAO() {
		connect = DBConnexion.getConnexion();

	}

	@Override
	public Locomotion findByID(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Locomotion> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Locomotion> findByCriteria(Locomotion obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(Locomotion obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Locomotion update(Locomotion obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(Locomotion obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}
	/**  affiche la liste de tous les moyens de locomotion prévus dans le programme	
	 * 
	 * @return
	 * @throws DAOException
	 */
		
		
	public List<String> afficherMoyensLocomotion() throws DAOException {
		List<String> liste = null;
		Statement stat = null;
		ResultSet rs = null;
		String req = "select libelle from " + DAOConstants.t_paramlocomotion;

		try {

			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			liste = new ArrayList<String>();
			while (rs.next())
				liste.add(rs.getString("libelle"));

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
