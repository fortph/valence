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

public class NationaliteDAO implements DAO<Nationalite> {
	

	private Connection connect = null;
	public NationaliteDAO() {
		connect = DBConnexion.getConnexion();

	}

	@Override
	public Nationalite findByID(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Nationalite> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Nationalite> findByCriteria(Nationalite obj)
			throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(Nationalite obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Nationalite update(Nationalite obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(Nationalite obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}
	/**
	 * affiche la liste de toutes les nationalites
	 * @return
	 * @throws DAOException
	 */
	public List<String> afficherLangue() throws DAOException {
		List<String> listeNationalite = null;
		Statement stat = null;
		ResultSet rs = null;
		String req = "select nationalite from " + DAOConstants.t_nationalite;

		try {

			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			listeNationalite = new ArrayList<String>();
			while (rs.next())
				listeNationalite.add(rs.getString("nationalite"));

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
		return listeNationalite;

	}
}
