package dao.imp.suivi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import beans.identite.Identite;
import beans.parametres.capemploi.UtilisateurDAO;
import beans.suivi.SuiviPersonne;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.imp.identite.IdentiteDAO;
import dao.interfaces.DAO;
import divers.FormaterDate;

public class SuiviPersonneDAO implements DAO<SuiviPersonne> {

	final private String query_insert = "insert into " + DAOConstants.t_suivi
			+ "( id_identite, date_suivi,idreferent,commentaire_suivi)"
			+ " values (?,?,?,?)";
	final private String query_update = "update "
			+ DAOConstants.t_suivi
			+ " set id_identite=?, date_suivi=?,idreferent=?,commentaire_suivi=?"
			+ " where idsuivi=";

	private Connection connect = null;

	public SuiviPersonneDAO() {
		connect = DBConnexion.getConnexion();

	}

	@Override
	public SuiviPersonne findByID(int id) throws DAOException {
		String query = "select * from " + DAOConstants.t_suivi
				+ " where  idsuivi=" + id;
		SuiviPersonne suivi = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				suivi = new SuiviPersonne((int) id,
						new IdentiteDAO().findByID(res.getInt(2)),
						res.getDate(3), new UtilisateurDAO().findByID(res
								.getInt(4)), res.getString(5));

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
	public List<SuiviPersonne> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SuiviPersonne> findByCriteria(SuiviPersonne obj)
			throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(SuiviPersonne obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);

			pst.setInt(1, obj.getIdentite().getId_IDE());
			pst.setDate(2, (java.sql.Date) obj.getDatesuivi());
			pst.setInt(3, obj.getReferent().getId_salarie());
			pst.setString(4, obj.getCommentaire());

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
			DAOUtil.closePrepareStatement(pst);
		}
		return cle;

	}

	@Override
	public SuiviPersonne update(SuiviPersonne obj) throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getId_suivi();

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);

			pst.setInt(1, obj.getIdentite().getId_IDE());
			pst.setDate(2, (java.sql.Date) obj.getDatesuivi());
			pst.setInt(3, obj.getReferent().getId_salarie());
			pst.setString(4, obj.getCommentaire());
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
			DAOUtil.closePrepareStatement(pst);
		}
		return obj;
	}

	@Override
	public int delete(SuiviPersonne obj) throws DAOException {
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
		String query = "select max(date_suivi) from " + DAOConstants.t_suivi
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
	
	
	public Date dateDernierSuivi(Identite id) throws DAOException {
		Date suivi = null;
		String query = "select max(date_suivi) from " + DAOConstants.t_suivi
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
		
		return suivi;

	}


	/** affiche les 3 derniers suivi d'une personne */
	public List<SuiviPersonne> afficherSuiviPersonne(Identite id)
			throws DAOException {
		List<SuiviPersonne> liste = null;
		SuiviPersonne suivi = null;
		Statement stat = null;
		ResultSet rs = null;
		String req = "select date_suivi,idreferent from "
				+ DAOConstants.t_suivi + " where id_identite=" + id.getId_IDE()
				+ " order by date_suivi desc  limit 0,3";

		try {

			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			liste = new ArrayList<SuiviPersonne>();
			while (rs.next()) {
				suivi = new SuiviPersonne(rs.getDate(1),
						new UtilisateurDAO().findByID(rs.getInt(2)));
				liste.add(suivi);
			}

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

	/** affiche tous les accompagnements d'une personne */

	public List<SuiviPersonne> afficheTousLesSuivis(Identite id)
			throws DAOException {
		List<SuiviPersonne> liste = null;
		SuiviPersonne suivi = null;
		Statement stat = null;
		ResultSet rs = null;

		String req = "select * from " + DAOConstants.t_suivi
				+ " where id_identite=" + id.getId_IDE()
				+ " order by date_suivi desc ";

		try {

			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			liste = new ArrayList<SuiviPersonne>();
			while (rs.next()) {
				suivi = new SuiviPersonne(rs.getInt(1), id, rs.getDate(3),
						new UtilisateurDAO().findByID(rs.getInt(4)),
						rs.getString(5));
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
     /**total des suivis d'une personne entre 2 dates*/
	public int totalSuivisParPeriode(Identite identite, Date a, Date b)
			throws DAOException {
		int total = 0;
		Statement stat = null;
		ResultSet rs = null;

		String req = "select count(id_suivi) as tot from "
				+ DAOConstants.t_suivi + " where id_identite="
				+ identite.getId_IDE() + " and date_suivi between '" + a
				+ "' and '" + b + "' ";

		try {

			stat = connect.createStatement();
			rs = stat.executeQuery(req);

			if (rs.first()) {
				total = rs.getInt(1);
			}

		} catch (SQLException ex) {
			System.out.print("Problème " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return total;
	}
	/**
	 * retourne la liste des suivis du referent mentionné, dont les dates de 
	 * suivis sont superieure a la date indiquée
	 * @param referent
	 * @param mini
	 * @return
	 * @throws DAOException
	 */
	public List<List<String>> rechercheParSuivi(int referent,String mini)throws DAOException {
		Statement stat = null;
		ResultSet rs = null;
		SimpleDateFormat  sdf=new SimpleDateFormat("dd-MM-yyyy");
		Date convertir=null;
		if(!mini.equals(""))
			convertir=new FormaterDate().changeFormatChaineDate(mini);
		else
			convertir=new FormaterDate().changeFormatChaineDate("01-01-1900");
		
		
		List<String> liste=null;
		List<List<String>> retour=new ArrayList<List<String>>();
		String req = "select distinct("
				+DAOConstants.t_suivi
				+".id_identite), MAX(date_suivi) as date,idsuivi,nom_identite,prenom_identite,"
				+ "mobil_identite,tel1_identite,ville_identite from "
				+DAOConstants.t_suivi
				+" inner join "
				+DAOConstants.t_identite
				+" on "
				+DAOConstants.t_suivi
				+".id_identite="
				+DAOConstants.t_identite
				+".id_identite "
				+" where date_suivi >='"
				+convertir
				+"' and idreferent="
				+referent
				+" group by "
				+DAOConstants.t_suivi
				+".id_identite ";//order by date desc";

		try {

			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			
			while(rs.next()) {
				liste=new ArrayList<String>();
				liste.add(rs.getString(1));
				liste.add(sdf.format(rs.getDate(2)));
				liste.add(rs.getString(3));
				liste.add(rs.getString(4));
				liste.add(rs.getString(5));
				liste.add(rs.getString(6));
				liste.add(rs.getString(7));
				liste.add(rs.getString(8));
				retour.add(liste);
			}

		} catch (SQLException ex) {
			System.out.print("Problème " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
	
		return retour;

}
}
