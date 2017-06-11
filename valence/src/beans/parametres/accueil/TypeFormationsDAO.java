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

public class TypeFormationsDAO implements DAO<TypeFormations> {

	private Connection connect = null;

	public TypeFormationsDAO() {
		connect = DBConnexion.getConnexion();

	}

	@Override
	public TypeFormations findByID(int id) throws DAOException {
		String query = "select nomProposition from " 
				+ DAOConstants.t_parampropositionsformations
				+ " where id_proposition=" + id;
		TypeFormations lf = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				
				lf = new TypeFormations(id, res.getString(1));
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
	public List<TypeFormations> findAll() throws DAOException {
		String query = "select id_proposition,nomProposition from " 
				+ DAOConstants.t_parampropositionsformations
				+ " where 1=1";
		List<TypeFormations> liste = null;
		TypeFormations formation=null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			liste=new ArrayList<TypeFormations>();
			while (res.next()) {				
				formation = new TypeFormations(res.getInt(1), res.getString(2));
				liste.add(formation);
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

	@Override
	public List<TypeFormations> findByCriteria(TypeFormations obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(TypeFormations obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TypeFormations update(TypeFormations obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(TypeFormations obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<String> afficherListePropositionsFormations() throws DAOException {
		List<String> liste = null;
		Statement stat = null;
		ResultSet rs = null;
		String req = "select nomProposition from " + DAOConstants.t_parampropositionsformations + " where 1=1";

		try {

			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			liste = new ArrayList<String>();
			while (rs.next())
				liste.add(rs.getString("nomProposition"));

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
	
	public TypeFormations findByName(String nom) throws DAOException {
		TypeFormations type = null;
		Statement st = null;
		ResultSet res = null;
		String req = "select `id_proposition`, `nomProposition`"
		+ " from " + DAOConstants.t_parampropositionsformations
				+ "  where nomProposition like '" + nom + "'";
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			if (res.first()) {

				type = new TypeFormations(res.getInt(1), res.getString(2));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return type;

	}

}
