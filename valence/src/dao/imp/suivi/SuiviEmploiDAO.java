package dao.imp.suivi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import beans.employeurs.Employeur;
import beans.identite.Identite;
import beans.parametres.accueil.Rome;
import beans.parametres.accueil.RomeDAO;
import beans.parametres.capemploi.Utilisateur;
import beans.parametres.capemploi.UtilisateurDAO;
import beans.suivi.SuiviEmploi;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.imp.employeur.EmployeurDAO;
import dao.imp.identite.IdentiteDAO;
import dao.interfaces.DAO;
import divers.FormaterDate;

public class SuiviEmploiDAO implements DAO<SuiviEmploi> {

	final private String query_insert = "insert into "
			+ DAOConstants.t_suiviemploi
			+ "(id_identite, date_suiviemploi,idreferent,"
			+ " id_employeur, idrome, datedeb_suiviemploi,datefin_suiviemploi,"
			+ " commentaire_suiviemploi)" + " values (?,?,?,?,?,?,?,?)";
	final private String query_update = "update "
			+ DAOConstants.t_suiviemploi
			+ " set id_identite=?, date_suiviemploi=?,idreferent=?,id_employeur=?,"
			+ "idrome=?,datedeb_suiviemploi=?, datefin_suiviemploi=?,"
			+ "commentaire_suiviemploi=?" + " where idsuiviemploi=";

	private Connection connect = null;

	public SuiviEmploiDAO() {
		connect = DBConnexion.getConnexion();

	}

	@Override
	public SuiviEmploi findByID(int id) throws DAOException {
		String query = "select * from " + DAOConstants.t_suiviemploi
				+ " where  idsuiviemploi=" + id;
		SuiviEmploi suivi = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				suivi = new SuiviEmploi((int) id,
						new IdentiteDAO().findByID(res.getInt(2)),
						res.getDate(3), new UtilisateurDAO().findByID(res
								.getInt(4)), new EmployeurDAO().findByID(res
								.getInt(5)), new RomeDAO().findByID(res
								.getInt(6)), res.getDate(7), res.getDate(8),
						 res.getString(9));

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
	public List<SuiviEmploi> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SuiviEmploi> findByCriteria(SuiviEmploi obj)
			throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(SuiviEmploi obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);

			pst.setInt(1, obj.getIdentite().getId_IDE());
			pst.setDate(2, (java.sql.Date) obj.getDateSuivi());
			pst.setInt(3, obj.getReferent().getId_salarie());
			pst.setInt(4, obj.getEmployeur().getId_employeur());
			pst.setInt(5, obj.getRome().getIdrome());
			pst.setDate(6, (java.sql.Date) obj.getDateDebutSuivi());
			pst.setDate(7, (java.sql.Date) obj.getDateFinSuivi());
			
			pst.setString(8, obj.getCommentaires());

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
			// Fermeture du resultset
						DAOUtil.closePrepareStatement(pst);

								}
		return cle;

	}

	@Override
	public SuiviEmploi update(SuiviEmploi obj) throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getIdsuiviemploi();

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);

			pst.setInt(1, obj.getIdentite().getId_IDE());
			pst.setDate(2, (java.sql.Date) obj.getDateSuivi());
			pst.setInt(3, obj.getReferent().getId_salarie());
			pst.setInt(4, obj.getEmployeur().getId_employeur());
			pst.setInt(5,obj.getRome().getIdrome());
			pst.setDate(6, (java.sql.Date) obj.getDateDebutSuivi());
			pst.setDate(7, (java.sql.Date) obj.getDateFinSuivi());			
			pst.setString(8, obj.getCommentaires());
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
	public int delete(SuiviEmploi obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String trouverDateDernierSuivi(Identite id) throws DAOException {
		Date suivi = null;
		String retour = null;
		String query = "select max(date_suiviemploi) from " + DAOConstants.t_suiviemploi
				+ " where id_identite=" + id.getId_IDE();
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				suivi = res.getDate(1);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}
		if (suivi != null)
			retour = new FormaterDate().formateDate(suivi);

		return retour;

	}

	/** affiche les 3 derniers suivi d'une personne */
	public List<SuiviEmploi> afficherTroisSuiviEmploi(Identite id)
			throws DAOException {
		List<SuiviEmploi> liste = null;
		SuiviEmploi suivi = null;
		Statement stat = null;
		ResultSet rs = null;
		
		RomeDAO rodao=new RomeDAO();
		Rome rome=null;
		EmployeurDAO empdao=new EmployeurDAO();
		Employeur employeur=null;
		UtilisateurDAO utildao=new UtilisateurDAO();
		Utilisateur utilisateur=null;
		
		
		String req = "select idsuiviemploi,id_identite, date_suiviemploi,idreferent,"
			+ " id_employeur, idrome, datedeb_suiviemploi,datefin_suiviemploi,"
			+ " commentaire_suiviemploi from "
				+ DAOConstants.t_suiviemploi + " where id_identite=" + id.getId_IDE()
				+ " order by date_suiviemploi desc  limit 0,3";

		try {

			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			liste = new ArrayList<SuiviEmploi>();
			while (rs.next()) {
				utilisateur=utildao.findByID(rs.getInt(4));
				employeur=empdao.findByID(rs.getInt(5));
				rome=rodao.findByID(rs.getInt(6));
				suivi = new SuiviEmploi(rs.getInt(1),
						id,rs.getDate(3),utilisateur,employeur,rome,
						rs.getDate(7),rs.getDate(8),rs.getString(9));
						
				liste.add(suivi);
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

	/** affiche tous les accompagnements d'une personne */

	public List<SuiviEmploi> afficheTousLesSuivis(Identite id)
			throws DAOException {
		List<SuiviEmploi> liste = null;
		SuiviEmploi suivi = null;
		Statement stat = null;
		ResultSet rs = null;
		RomeDAO rodao=new RomeDAO();
		Rome rome=null;
		EmployeurDAO empdao=new EmployeurDAO();
		Employeur employeur=null;
		UtilisateurDAO utildao=new UtilisateurDAO();
		Utilisateur utilisateur=null;
		
		String req = "select idsuiviemploi,id_identite, date_suiviemploi,idreferent,"
			+ " id_employeur, idrome, datedeb_suiviemploi,datefin_suiviemploi,"
			+ " commentaire_suiviemploi from "
				+ DAOConstants.t_suiviemploi + " where id_identite=" + id.getId_IDE()
				+ " order by date_suiviemploi desc ";

		try {

			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			liste = new ArrayList<SuiviEmploi>();
			while (rs.next()) {
				utilisateur=utildao.findByID(rs.getInt(4));
				employeur=empdao.findByID(rs.getInt(5));
				rome=rodao.findByID(rs.getInt(6));
				suivi = new SuiviEmploi(rs.getInt(1),
						id,rs.getDate(3),utilisateur,employeur,rome,
						rs.getDate(7),rs.getDate(8),rs.getString(9));
						
				liste.add(suivi);
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

}
