package dao.imp.formation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.formation.Animatrice;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.interfaces.DAO;

public class AnimatriceDAO implements DAO<Animatrice> {

	final private String query_insert = "insert into "
			+ DAOConstants.t_animatrices + "(nom,mail,actif) "
			+ " values (?,?,?)";

	final private String query_update = "update " + DAOConstants.t_animatrices
			+ " set nom=?,mail=?,actif=? " + " where id_animatrice=?";

	private Connection connect = null;

	public AnimatriceDAO() {
		connect = DBConnexion.getConnexion();
	}

	@Override
	public Animatrice findByID(int id) throws DAOException {
		String query = "select  `nom`, `mail`, `actif` "
				+ " from " + DAOConstants.t_animatrices
				+ " where  id_animatrice=" + id;
		Animatrice anim = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				anim = new Animatrice((short) id, res.getString(1),
						res.getString(2), res.getBoolean(3));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
			//DBConnexion.cloreConnexion();
		}
	

		return anim;

	}

	@Override
	public List<Animatrice> findAll() throws DAOException {
		Statement stat = null;
		ResultSet rs = null;
		List<Animatrice> liste = new ArrayList<Animatrice>();
		String req = "select `id_animatrice`, `nom`, `mail`, `actif` "
				+ " from " + DAOConstants.t_animatrices;
		Animatrice anime = null;
		try {
			connect.setAutoCommit(false);
			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			while (rs.next()) {
				anime = new Animatrice(rs.getShort(1), rs.getString(2),
						rs.getString(3), rs.getBoolean(4));
				liste.add(anime);
			}
			connect.commit();

		} catch (SQLException ex) {
			try {
				connect.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.print("Probl�me " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return liste;

	}

	@Override
	public List<Animatrice> findByCriteria(Animatrice obj) throws DAOException {
		ArrayList<Animatrice> liste = new ArrayList<Animatrice>();
		Animatrice of = null;
		Statement st = null;
		ResultSet res = null;
		String req = "select `id_animatrice`, `nom`, `mail`, `actif` "
				+ " from " + DAOConstants.t_animatrices
				+ " where 1=1 ";
		if (obj.getNom() != null)
			req += "and nom =" + obj.getNom();

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {

				of = new Animatrice(res.getShort(1), res.getString(2),
						res.getString(3), res.getBoolean(4));
				liste.add(of);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return liste;
	}

	@Override
	/**rajouter des animateurs*/
	public int create(Animatrice obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);
			// pst.setLong(1, obj.getId_IDE());
			pst.setString(1, obj.getNom());
			pst.setString(2, obj.getMail());
			pst.setBoolean(3, obj.isActif());
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
			
			// Fermeture du statement
			DAOUtil.closeStatement(pst);
		}
		return cle;

	}

	@Override
	public Animatrice update(Animatrice obj) throws DAOException {
		PreparedStatement pst = null;

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_update);
			pst.setString(1, obj.getNom());
			pst.setString(2, obj.getMail());
			pst.setBoolean(3, obj.isActif());
			pst.setInt(4, obj.getId_animatrice());
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
							// Fermeture du statement
				DAOUtil.closeStatement(pst);
		}
		return obj;
	}

	@Override
	public int delete(Animatrice obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	/** affiche la liste des animatrices 
	 * 
	 * @return
	 * @throws DAOException
	 */
	public List<String> afficherAnimatrices() throws DAOException {
		List<String> liste = null;
		Statement stat = null;
		ResultSet rs = null;
		String req = null;

		try {

			req = "select nom from " + DAOConstants.t_animatrices
					+ " where actif=1 order by nom asc";
			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			liste = new ArrayList<String>();
			while (rs.next())
				liste.add(rs.getString("nom"));

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			DAOUtil.closeStatement(stat);
		}
		return liste;

	}

	/** affiche la liste de toutes les animatrices actif ou pas
	 * 
	 * @return
	 * @throws DAOException
	 */
	public List<String> afficherToutesAnimatrices() throws DAOException {
		List<String> liste = null;
		Statement stat = null;
		ResultSet rs = null;
		String req = null;

		try {

			req = "select nom from " + DAOConstants.t_animatrices
					+ " order by nom asc";
			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			liste = new ArrayList<String>();
			while (rs.next())
				liste.add(rs.getString("nom"));

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			
			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return liste;

	}

	/** recherche l'id correspondant au nom d'une animatrice 	
	 * 
	 * @param nom
	 * @return
	 */
	public short rechercherID(String nom) {
		short num = 0;
		String req = "select id_animatrice from " + DAOConstants.t_animatrices
				+ "  where nom='" + nom + "'";
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement();
			res = st.executeQuery(req);
			if (res.first())
				num = res.getShort(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				res.close();
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
						
		}
		return num;

	}

	/** recherche tous les champs de l'animatrice dont le nom est indiqué	
	 * 
	 * @param nom
	 * @return
	 * @throws DAOException
	 */
	public Animatrice findByName(String nom) throws DAOException {
		Animatrice anim = null;
		Statement st = null;
		ResultSet res = null;
		String req = "select `id_animatrice`, `nom`, `mail`, `actif` "
				+ " from " + DAOConstants.t_animatrices
				+ "  where nom like '" + nom + "'";
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			if (res.first()) {

				anim = new Animatrice(res.getShort(1), res.getString(2),
						res.getString(3), res.getBoolean(4));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return anim;

	}
}
