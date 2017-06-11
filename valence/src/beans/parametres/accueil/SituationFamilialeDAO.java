package beans.parametres.accueil;

import java.sql.Connection;
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

public class SituationFamilialeDAO implements DAO<SituationFamiliale> {
	
	private Connection connect = null;
	public SituationFamilialeDAO() {
		connect = DBConnexion.getConnexion();

	}

	@Override
	public SituationFamiliale findByID(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SituationFamiliale> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SituationFamiliale> findByCriteria(SituationFamiliale obj)
			throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(SituationFamiliale obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SituationFamiliale update(SituationFamiliale obj)
			throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(SituationFamiliale obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<String> afficherSF() throws DAOException {
		List<String> liste = null;
		Statement stat = null;
		ResultSet rs = null;
		String req = "select libelle from " + DAOConstants.t_situationFamiliale;
		
		try {

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
}
