package dao.imp.identite;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.identite.Identite;
import beans.identite.ProfilPriorite;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.interfaces.DAO;

public class ProfilPrioriteDAO implements DAO<ProfilPriorite> {
	private Connection connect = null;
	final private String query_insert = "insert into "
			+ DAOConstants.t_priorite
			+ "(libelle_priorite,id_identite,expire_priorite) values (?,?,?)";

	final private String query_delete = "delete from "
			+ DAOConstants.t_priorite + " where id_priorite=";

	public ProfilPrioriteDAO() {
		connect = DBConnexion.getConnexion();
	}

	@Override
	public ProfilPriorite findByID(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProfilPriorite> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProfilPriorite> findByCriteria(ProfilPriorite obj)
			throws DAOException {
		ArrayList<ProfilPriorite> liste = new ArrayList<ProfilPriorite>();
		ProfilPriorite profil = null;
		Statement st = null;
		ResultSet res = null;
		String req = "SELECT `id_priorite`, `libelle_priorite`,"
				+ " `id_identite`, `expire_priorite` from " + DAOConstants.t_priorite + " where 1=1 ";
		if (obj.getId_identite() != 0)
			req += " and id_identite=" + obj.getId_identite();
		if (obj.getLibelle() != null)
			req += " and libelle_priorite=" + obj.getLibelle();
		if (obj.getExpire() != null)
			req += " and expire_priorite=" + obj.getExpire();

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {

				profil = new ProfilPriorite(res.getInt(1), res.getString(2),
						res.getInt(3), res.getDate(4));
				liste.add(profil);

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
	public int create(ProfilPriorite obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, obj.getLibelle());
			pst.setInt(2, obj.getId_identite());
			pst.setDate(3, (Date) obj.getExpire());
			pst.executeUpdate();
			ResultSet rs = pst.getGeneratedKeys();
			if (rs != null && rs.next())
				cle = rs.getInt(1);
			connect.commit();

		} catch (SQLException e) {
			e.printStackTrace();
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
	public ProfilPriorite update(ProfilPriorite obj) throws DAOException {
		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
		String query = query_delete + obj.getId_identite();
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.executeUpdate();
			pst1 = connect.prepareStatement(query_insert);
			pst1.setString(1, obj.getLibelle());
			pst1.setInt(2, obj.getId_identite());
			pst1.setDate(3, (Date) obj.getExpire());
			pst1.executeUpdate();
			connect.commit();
		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			DAOUtil.closePrepareStatement(pst);
		}
		return obj;
	}

	@Override
	public int delete(ProfilPriorite obj) throws DAOException {
		int num = obj.getId_priorite();
		return delete(num);
	}

	@Override
	public int delete(long id) throws DAOException {
		String query = query_delete + id;
		int retour = 0;
		Statement st = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			retour = st.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return retour;
	}

	public List<String> afficherPriorite() throws DAOException {
		List<String> liste = null;
		Statement stat = null;
		ResultSet rs = null;
		String req = null;
		try {
			req = "select libelle from " + DAOConstants.t_priorite;
			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			liste = new ArrayList<String>();
			while (rs.next())
				liste.add(rs.getString("libelle"));

		} catch (SQLException ex) {
			System.out.print("Problème " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return liste;
	}

	/** affiche toutes les priorites d'une personne 
	 * 
	 * @param iden une personne
	 * @return la liste de priorites
	 * @throws DAOException
	 */
	public List<ProfilPriorite> afficherPrioriteParIdentite(Identite iden)
			throws DAOException {
		List<ProfilPriorite> liste = null;
		ProfilPriorite pro = null;
		Statement stat = null;
		ResultSet rs = null;
		String req = null;
		try {
			req = "select libelle_priorite,expire_priorite from "
					+ DAOConstants.t_priorite + " where id_identite="
					+ iden.getId_IDE();
			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			liste = new ArrayList<ProfilPriorite>();
			while (rs.next()) {
				pro = new ProfilPriorite(rs.getString(1), iden.getId_IDE(),
						rs.getDate(2));
				liste.add(pro);
			}
		} catch (SQLException ex) {
			System.out.print("Problème " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return liste;
	}
	
	public ProfilPriorite finByName(String nom) throws DAOException {
		String query = "select SELECT `id_priorite`, `libelle_priorite`,"
				+ " `id_identite`, `expire_priorite` from "
				+ DAOConstants.t_priorite
				+ " where libelle_priorite like '" + nom + "'";
		ProfilPriorite pp = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				pp = new ProfilPriorite(res.getInt(1), res.getString(2),new IdentiteDAO().findByID(res.getInt(3)).getId_IDE(),res.getDate(4));
			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
		
				// Fermeture du resultset
				DAOUtil.closeResultSet(res);

				// Fermeture du statement
				DAOUtil.closeStatement(st);
		}

		return pp;
	}
	
	/** verifie si la personne dispose ou à disposé d'une RTH 
	 * 
	 * @param identite
	 * @return le nombre de priorites de la personne
	 * @throws DAOException
	 */
	
	public int rthValide(Identite identite)throws DAOException {
			int retour=0;
		
		String query = "select count(id_priorite) as tot from " + DAOConstants.t_priorite
				+ " where id_identite=" + identite.getId_IDE() 
				+" and libelle_priorite like 'RTH'";	
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				retour=res.getInt(1);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}
				
		return retour;
		
	}
	
	/** verifie si la personne dispose d'une RTH en cours de validite
	 * 
	 * @param identite
	 * @return
	 * @throws DAOException
	 */
	
	  public int rthEnCoursDeValidite(Identite identite)throws DAOException {
			int retour=0;
		
		String query = "select count(id_priorite) as tot from " + DAOConstants.t_priorite
				+ " where id_identite=" + identite.getId_IDE()
				+" and libelle_priorite like 'RTH' and expire_priorite >= curdate()";
		
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				retour=res.getInt(1);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}
				
		return retour;
		
	}
	

}
