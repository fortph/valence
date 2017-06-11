package dao.imp.formationpro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.formationpro.FormationProEmployeur;
import beans.parametres.formationpro.Formprostatutemployeur;
import beans.parametres.formationpro.FormprostatutemployeurDAO;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.interfaces.DAO;

public class FormationProEmployeurDAO implements DAO<FormationProEmployeur> {

	final private String query_insert = "insert into "
			+ DAOConstants.t_formproemployeur
			+ "(employeur,adresse,cp,ville,tel1,tel2,id_statut,actif)"
			+ " values (?,?,?,?,?,?,?,?)";

	final private String query_update = "update "
			+ DAOConstants.t_formproemployeur
			+ " set employeur=?, adresse=?, cp=?,ville=?,tel1=?,tel2=?,id_statut=?,actif=?  "
			+ "where id_employeur=";
	
	
	private Connection connect = null;

	public FormationProEmployeurDAO() {
		connect = DBConnexion.getConnexion();
	}

	@Override
	public FormationProEmployeur findByID(int id) throws DAOException {
		String query = "select `id_employeur`, `employeur`, `adresse`,"
				+ " `cp`, `ville`, `tel1`, `tel2`, `id_statut`, "
				+ "`actif` from " 
				+ DAOConstants.t_formproemployeur
				+ " where id_employeur=" + id;
		FormationProEmployeur form = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				FormprostatutemployeurDAO statdao = new FormprostatutemployeurDAO();
				Formprostatutemployeur statut = statdao.findByID(res.getInt(8));
				form = new FormationProEmployeur(id, res.getString(2),
						res.getString(3), res.getString(4), res.getString(5),
						res.getString(6), res.getString(7), statut,
						res.getBoolean(9));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return form;
	}

	@Override
	public List<FormationProEmployeur> findAll() throws DAOException {
		List<FormationProEmployeur> liste = new ArrayList<FormationProEmployeur>();
		Statement stat = null;
		ResultSet res = null;
		String req = "select `id_employeur`, `employeur`, `adresse`,"
				+ " `cp`, `ville`, `tel1`, `tel2`, `id_statut`, "
				+ "`actif` from "  + DAOConstants.t_formproemployeur
				+ " order by employeur asc";

		FormationProEmployeur employeur = null;
		try {

			stat = connect.createStatement();
			res = stat.executeQuery(req);
			FormprostatutemployeurDAO statdao = new FormprostatutemployeurDAO();
			Formprostatutemployeur statut = null;

			while (res.next()) {
				statut = statdao.findByID(res.getInt(8));

				employeur = new FormationProEmployeur(res.getInt(1),
						res.getString(2), res.getString(3), res.getString(4),
						res.getString(5), res.getString(6), res.getString(7),
						statut, res.getBoolean(9));
				liste.add(employeur);
			}

		} catch (SQLException ex) {
			System.out.print("Probl�me " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return liste;

	}

	@Override
	public List<FormationProEmployeur> findByCriteria(FormationProEmployeur obj)
			throws DAOException {
		ArrayList<FormationProEmployeur> liste = new ArrayList<FormationProEmployeur>();
		FormationProEmployeur employeur = new FormationProEmployeur();
		Statement st = null;
		ResultSet res = null;
		String req = "select `id_employeur`, `employeur`, `adresse`,"
				+ " `cp`, `ville`, `tel1`, `tel2`, `id_statut`, "
				+ "`actif` from " 
				+ DAOConstants.t_formproemployeur
				+ " where 1=1 ";
		if (obj.isActif())
			req += " and actif=1 ";

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {

				employeur = new FormationProEmployeur(
						res.getInt(1),
						res.getString(2),
						res.getString(3),
						res.getString(4),
						res.getString(5),
						res.getString(6),
						res.getString(7),
						new FormprostatutemployeurDAO().findByID(res.getInt(8)),
						res.getBoolean(9));
				liste.add(employeur);

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
	public int create(FormationProEmployeur obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, obj.getEmployeur());
			pst.setString(2, obj.getAdresse());
			pst.setString(3, obj.getCp());
			pst.setString(4, obj.getVille());
			pst.setString(5, obj.getTel1());
			pst.setString(6, obj.getTel2());
			pst.setInt(7, obj.getStatut().getId_statut());
			pst.setBoolean(8, obj.isActif());
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
	public FormationProEmployeur update(FormationProEmployeur obj)
			throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getId_employeur();

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.setString(1, obj.getEmployeur());
			pst.setString(2, obj.getAdresse());
			pst.setString(3, obj.getCp());
			pst.setString(4, obj.getVille());
			pst.setString(5, obj.getTel1());
			pst.setString(6, obj.getTel2());
			pst.setInt(7, obj.getStatut().getId_statut());
			pst.setBoolean(8, obj.isActif());
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

	@Override
	public int delete(FormationProEmployeur obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<String> afficherEmployeursFormPro() throws DAOException {
		List<String> liste = null;
		Statement stat = null;
		ResultSet rs = null;
		String req = "select employeur from " + DAOConstants.t_formproemployeur
				+ "  order by employeur asc";

		try {

			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			liste = new ArrayList<String>();
			while (rs.next())
				liste.add(rs.getString("employeur"));

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

	public FormationProEmployeur findByName(String nom) throws DAOException {
		String query = "select `id_employeur`, `employeur`, `adresse`,"
				+ " `cp`, `ville`, `tel1`, `tel2`, `id_statut`, "
				+ "`actif` from " 
				+ DAOConstants.t_formproemployeur
				+ " where employeur like '" + nom + "'";
		FormationProEmployeur lf = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {

				lf = new FormationProEmployeur(
						res.getInt(1),
						nom,
						res.getString(3),
						res.getString(4),
						res.getString(5),
						res.getString(6),
						res.getString(7),
						new FormprostatutemployeurDAO().findByID(res.getInt(8)),
						res.getBoolean(9));
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

}
