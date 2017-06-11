package dao.imp.employeur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.employeurs.Employeur;
import beans.employeurs.Suivi;
import beans.parametres.capemploi.Utilisateur;
import beans.parametres.capemploi.UtilisateurDAO;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.interfaces.DAO;

public class SuiviDAO implements DAO<Suivi> {

	final private String query_insert = "insert into "
			+ DAOConstants.t_employeursuivi
			+ "( id_utilisateur,id_employeur,commentaires,jour)"
			+ " values (?,?,?,?)";

	final private String query_update = "update "
			+ DAOConstants.t_employeursuivi
			+ " set id_utilisateur=?,id_employeur=?,commentaires=?,jour=?"
			+ " where id_suivi=";

	private Connection connect = null;

	public SuiviDAO() {
		connect = DBConnexion.getConnexion();

	}

	@Override
	public Suivi findByID(int id) throws DAOException {
		String query = "select  `id_suivi`, `id_utilisateur`, `id_employeur`,"
				+ " `commentaires`, `jour` from " 
				+ DAOConstants.t_employeursuivi
				+ " where  id_suivi=" + id;

		Suivi suivi = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				suivi = new Suivi(id, new UtilisateurDAO().findByID(res
						.getInt(2)),
						new EmployeurDAO().findByID(res.getInt(3)),
						res.getString(4), res.getDate(5));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return suivi;
	}

	@Override
	public List<Suivi> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Suivi> findByCriteria(Suivi obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(Suivi obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);

			pst.setInt(1, obj.getUtilisateur().getId_salarie());
			pst.setInt(2, obj.getEmployeur().getId_employeur());
			pst.setString(3, obj.getCommentaires());
			pst.setDate(4, obj.getJour());

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
			DAOUtil.closePrepareStatement(pst);
		}
		return cle;

	}

	@Override
	public Suivi update(Suivi obj) throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getId_suivi();

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);

			pst.setInt(1, obj.getUtilisateur().getId_salarie());
			pst.setInt(2, obj.getEmployeur().getId_employeur());
			pst.setString(3, obj.getCommentaires());
			pst.setDate(4, obj.getJour());
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
			DAOUtil.closePrepareStatement(pst);
		}
		return obj;
	}
	/**recherche la liste des suivi d'un employeur 	
	 * 
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public List<Suivi> listeSuiviParEmployeur(int id) throws DAOException{
		String query = "select  `id_suivi`, `id_utilisateur`, `id_employeur`,"
				+ " `commentaires`, `jour` from "  + DAOConstants.t_employeursuivi
				+ " where  id_employeur=" + id;

		List<Suivi>liste = new ArrayList<Suivi>();
		Suivi suivi=null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			while (res.next()) {
				UtilisateurDAO utdao=new UtilisateurDAO();
				Utilisateur utilisateur=utdao.findByID(res.getInt(2));
				EmployeurDAO empdao=new EmployeurDAO();
				Employeur employeur=empdao.findByID(res.getInt(3));
				suivi=new Suivi(res.getInt(1), utilisateur,employeur,res.getString(4),res.getDate(5));
				liste.add(suivi);
			}}
	 catch (SQLException ex) {
		System.out.print("Probléme " + ex);
	} finally {
		// Fermeture du resultset
		DAOUtil.closeResultSet(res);

		// Fermeture du statement
		DAOUtil.closeStatement(st);
	}
	return liste;
		
	}
	
	
	@Override
	public int delete(Suivi obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

}
