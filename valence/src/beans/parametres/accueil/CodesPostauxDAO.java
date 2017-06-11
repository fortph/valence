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

public class CodesPostauxDAO implements DAO<CodesPostaux> {
	
	private Connection connect = null;

	public CodesPostauxDAO() {
		connect = DBConnexion.getConnexion();

	}

	@Override
	public CodesPostaux findByID(int id) throws DAOException {
		String query = "select ville from " + DAOConstants.t_codespostaux
				+ " where  id_ville=" + id;
		CodesPostaux ville  = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				ville = new CodesPostaux( id, res.getString(1));

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
	public List<CodesPostaux> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CodesPostaux> findByCriteria(CodesPostaux obj)
			throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(CodesPostaux obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CodesPostaux update(CodesPostaux obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(CodesPostaux obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}
	/** affiche toutes les villes dont le code postal commence par le parametre indique	
	 * 
	 * @param cp
	 * @return
	 * @throws DAOException
	 */
	 
	
	public List<String> afficherListe(String cp) throws DAOException {
		List<String> lvilles = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "SELECT ville FROM " + DAOConstants.t_codespostaux
				+ " where cp like'" + cp + "%'";
		
		try {
			st = connect.createStatement();

			// Ex�cution de la requ�te et r�cup�ration du r�sultat dans un
			// resultset
			rs = st.executeQuery(sql);

			// Cr�ation d'une liste de villes
			lvilles = new ArrayList<String>();

			// TANT QUE la fin du resultset n'est pas atteinte
			while (rs.next()) {
				lvilles.add(rs.getString("ville"));
			}
		} catch (SQLException ex) {
			System.out.print("Problème " + ex);
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return lvilles;
	}
	
	/** affiche toutes les villes du tarn et garonne 	
	 * 
	 * @return
	 * @throws DAOException
	 */
	public List<String> afficherVillesDepartement() throws DAOException {
		List<String> lvilles = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "SELECT ville FROM " + DAOConstants.t_codespostaux
				+ " where cp like'82%' order by ville asc";
		
		try {
			st = connect.createStatement();

			// Ex�cution de la requ�te et r�cup�ration du r�sultat dans un
			// resultset
			rs = st.executeQuery(sql);

			// Cr�ation d'une liste de villes
			lvilles = new ArrayList<String>();

			// TANT QUE la fin du resultset n'est pas atteinte
			while (rs.next()) {
				lvilles.add(rs.getString("ville"));
			}
		} catch (SQLException ex) {
			System.out.print("Probl�me " + ex);
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return lvilles;
	}
	
	/**affiche toutes les villes des departements voisins :32 et 47	
	 * 
	 * @return
	 * @throws DAOException
	 */
	public List<CodesPostaux> afficherVillesDepartementVoisins() throws DAOException {
		List<CodesPostaux> lvilles = new ArrayList<CodesPostaux>();
		Statement st = null;
		ResultSet rs = null;
		CodesPostaux commune=null;
		String sql = "SELECT * FROM " + DAOConstants.t_codespostaux
				+ " where cp like'82%' or cp like '47%' or cp like '32%' order by ville asc";
		
		try {
			st = connect.createStatement();

			// Ex�cution de la requ�te et r�cup�ration du r�sultat dans un
			// resultset
			rs = st.executeQuery(sql);
			// TANT QUE la fin du resultset n'est pas atteinte
			while (rs.next()) {
				commune=new CodesPostaux(rs.getInt(1),rs.getString(2),rs.getString(3));
				lvilles.add(commune);
			}
		} catch (SQLException ex) {
			System.out.print("Problème " + ex);
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return lvilles;
	}



}
