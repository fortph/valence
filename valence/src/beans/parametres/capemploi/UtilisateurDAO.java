package beans.parametres.capemploi;

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

public class UtilisateurDAO implements DAO<Utilisateur> {
	

	final private String query_insert = "insert into "
			+ DAOConstants.t_capemploiutilisateurs
			+ "( nom,prenom,login,passe,privilege,mail,actif)"
			+ " values (?,?,?,?,?,?,?)";

	final private String query_update = "update "
			+ DAOConstants.t_capemploiutilisateurs
			+ " set nom=?,prenom=?,login=?,passe=?,privilege=?,mail=?,actif=? "
			+ " where id_salarie=";

	
	
	
	private Connection connect = null;

	public UtilisateurDAO() {
		connect = DBConnexion.getConnexion();

	}

	@Override
	public Utilisateur findByID(int id) throws DAOException {
		String query = "select * from " + DAOConstants.t_capemploiutilisateurs
				+ " where  id_salarie=" + id;

		Utilisateur utilisateur = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				utilisateur = new Utilisateur(id, res.getString(2),
						res.getString(3), res.getString(4), res.getString(5),
						res.getString(6), res.getString(7),res.getBoolean(8));
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

		return utilisateur;
	}
	

	@Override
	public List<Utilisateur> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Utilisateur> findByCriteria(Utilisateur obj)
			throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(Utilisateur obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);
			
			pst.setString(1, obj.getNom());
			pst.setString(2, obj.getPrenom());		
			pst.setString(3, obj.getLogin());
			pst.setString(4, obj.getPasse());
			pst.setString(5, obj.getPrivilege());		
			pst.setString(6, obj.getMail());
			pst.setBoolean(7, obj.isActif());
			pst.executeUpdate();
			ResultSet rs = pst.getGeneratedKeys();
			if (rs != null && rs.first())
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
	public Utilisateur update(Utilisateur obj) throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getId_salarie();

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.setString(1, obj.getNom());
			pst.setString(2, obj.getPrenom());		
			pst.setString(3, obj.getLogin());
			pst.setString(4, obj.getPasse());
			pst.setString(5, obj.getPrivilege());		
			pst.setString(6, obj.getMail());
			pst.setBoolean(7, obj.isActif());
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
			try {
				pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return obj;
	}

	@Override
	public int delete(Utilisateur obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}
	/**
	 * affiche la liste de tous les utilisateurs actifs
	 * @return
	 * @throws DAOException
	 */
	public List<String> afficherUtilisateurs() throws DAOException {
		List<String> liste = null;
		Statement stat = null;
		ResultSet rs = null;
		String req = "select nom, prenom from "
				+ DAOConstants.t_capemploiutilisateurs + " where actif=1 order by nom asc";

		try {

			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			liste = new ArrayList<String>();
			while (rs.next()) {
				String nom = rs.getString(1);
				String prenom = rs.getString(2);
				String tout = nom + " " + prenom;
				liste.add(tout);
			}

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
	
	/** 
	 * affiche l'utilisateur dont le nom est indiqué 
	 * @param nom
	 * @return
	 * @throws DAOException
	 */
	public Utilisateur findByName(String nom) throws DAOException {
		Utilisateur utilisateur=null;
		Statement st = null;
		ResultSet res = null;
		String req = "select * from "
				+ DAOConstants.t_capemploiutilisateurs 
				+ " where concat (nom,' ',prenom)='" + nom
				+ "'";

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			if (res.first()) {

				utilisateur = new Utilisateur(res.getInt(1), res.getString(2),
						res.getString(3), res.getString(4), res.getString(5),
						res.getString(6), res.getString(7), res.getBoolean(8));
				

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

		return utilisateur;

	}
	
	
	
	/** 
	 * affiche l'utilisateur dont le login est indiqué
	 * @param nom : login de l'utilisateur
	 * @return
	 * @throws DAOException
	 */
	public Utilisateur rechercheParLogin(String nom) throws DAOException {
		Utilisateur utilisateur=null;
		Statement st = null;
		ResultSet res = null;
		String req = "select * from "
				+ DAOConstants.t_capemploiutilisateurs 
				+ " where login='" + nom
				+ "'";

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			if (res.first()) {

				utilisateur = new Utilisateur(res.getInt(1), res.getString(2),
						res.getString(3), res.getString(4), res.getString(5),
						res.getString(6), res.getString(7), res.getBoolean(8));
				

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

		return utilisateur;

	}

}
