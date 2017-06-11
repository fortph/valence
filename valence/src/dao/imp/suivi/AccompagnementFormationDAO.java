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

import beans.formation.Animatrice;
import beans.identite.Identite;
import beans.suivi.AccompagnementFormation;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.imp.formation.AnimatriceDAO;
import dao.imp.identite.IdentiteDAO;
import dao.interfaces.DAO;
import divers.FormaterDate;

public class AccompagnementFormationDAO implements DAO<AccompagnementFormation> {

	final private String query_insert = "insert into " + DAOConstants.t_suiviAccompagneformation
			+ "( id_identite, dateredaction,animatrice_pyramide,demande,commentaire)" + " values (?,?,?,?,?)";
	final private String query_update = "update " + DAOConstants.t_suiviAccompagneformation
			+ " set id_identite=?, dateredaction=?,animatrice_pyramide=?,demande=?,commentaire=? where id_accomp=";

	private Connection connect = null;

	public AccompagnementFormationDAO() {
		connect = DBConnexion.getConnexion();

	}

	@Override
	public AccompagnementFormation findByID(int id) throws DAOException {
		String query = "select id_accomp,id_identite, dateredaction,animatrice_pyramide," + "demande,commentaire from "
				+ DAOConstants.t_suiviAccompagneformation + " where  id_accomp=" + id;
		AccompagnementFormation suivi = null;
		Statement st = null;
		ResultSet res = null;
		IdentiteDAO idao=new IdentiteDAO();
		Identite identite=null;
		AnimatriceDAO anidao=new AnimatriceDAO();
		Animatrice anim=null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				identite=idao.findByID(res.getInt(2));
				anim=anidao.findByID(res.getInt(4));
				suivi = new AccompagnementFormation( id, identite, res.getDate(3),
						anim, res.getString(5), res.getString(6));

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
	public List<AccompagnementFormation> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AccompagnementFormation> findByCriteria(AccompagnementFormation obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(AccompagnementFormation obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert, Statement.RETURN_GENERATED_KEYS);

			pst.setInt(1, obj.getIdentite().getId_IDE());
			pst.setDate(2, (java.sql.Date) obj.getDateredaction());
			pst.setShort(3, obj.getReferent().getId_animatrice());
			pst.setString(4, obj.getDemande());
			pst.setString(5, obj.getCommentaire());

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
	public AccompagnementFormation update(AccompagnementFormation obj) throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getId_accomp();

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);

			pst.setInt(1, obj.getIdentite().getId_IDE());
			pst.setDate(2, (java.sql.Date) obj.getDateredaction());
			pst.setShort(3, obj.getReferent().getId_animatrice());
			pst.setString(4, obj.getDemande());
			pst.setString(5, obj.getCommentaire());
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
	public int delete(AccompagnementFormation obj) throws DAOException {
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
		String query = "select max(dateredaction) from " + DAOConstants.t_suiviAccompagneformation
				+ " where id_identite=" + id.getId_IDE();
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(/*ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY*/);
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
		String query = "select max(dateredaction) from " + DAOConstants.t_suiviAccompagneformation
				+ " where id_identite=" + id.getId_IDE();
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
	public List<AccompagnementFormation> afficherAccompagenementFormation(Identite id) throws DAOException {
		List<AccompagnementFormation> liste = null;
		AccompagnementFormation suivi = null;
		Statement stat = null;
		ResultSet rs = null;
		AnimatriceDAO animdao=new AnimatriceDAO() ;
		Animatrice anim=null;
		String req = "select dateredaction,animatrice_pyramide from " + DAOConstants.t_suiviAccompagneformation
				+ " where id_identite=" + id.getId_IDE() + " order by dateredaction desc  limit 0,3";

		try {

			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			liste = new ArrayList<AccompagnementFormation>();
			while (rs.next()) {
				anim=animdao.findByID(rs.getInt(2));
				suivi = new AccompagnementFormation(rs.getDate(1), anim);
				liste.add(suivi);
			}

		} catch (SQLException ex) {
			System.out.print("Problème " + ex);
		} finally {
			
			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return liste;

	}

	/** affiche tous les accompagnements d'une personne */

	public List<AccompagnementFormation> afficheTousLesSuivis(Identite id) throws DAOException {
		List<AccompagnementFormation> liste = null;
		AccompagnementFormation suivi = null;
		Statement stat = null;
		ResultSet rs = null;
		AnimatriceDAO animdao=new AnimatriceDAO() ;
		Animatrice anim=null;
		String req = "select id_accomp,id_identite, dateredaction,animatrice_pyramide,demande,commentaire from "
				+ DAOConstants.t_suiviAccompagneformation + " where id_identite=" + id.getId_IDE()
				+ " order by dateredaction desc ";

		try {

			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			liste = new ArrayList<AccompagnementFormation>();
			while (rs.next()) {
				anim=animdao.findByID(rs.getInt(4));
				suivi = new AccompagnementFormation(rs.getInt(1), id, rs.getDate(3),
					anim, rs.getString(5), rs.getString(6));
				liste.add(suivi);
			}

		} catch (SQLException ex) {
			System.out.print("Problème " + ex);
		} finally {
						// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return liste;

	}

	/** total des suivis d'une personne entre 2 dates */
	public int totalSuivisParPeriode(Identite identite, Date a, Date b) throws DAOException {
		int total = 0;
		Statement stat = null;
		ResultSet rs = null;

		String req = "select count(id_accomp) as tot from " + DAOConstants.t_suiviAccompagneformation
				+ " where id_identite=" + identite.getId_IDE() + " and dateredaction between '" + a + "' and '" + b
				+ "' ";

		try {

			stat = connect.createStatement();
			rs = stat.executeQuery(req);

			if (rs.first()) {
				total = rs.getInt(1);
			}

		} catch (SQLException ex) {
			System.out.print("Problème " + ex);
		} finally {
		
			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return total;
	}

	/**
	 * retourne la liste des suivis du referent mentionné, dont les dates de
	 * suivis sont superieure a la date indiquée
	 * 
	 * @param referent
	 * @param mini
	 * @return
	 * @throws DAOException
	 */
	public List<List<String>> rechercheParSuivi(Animatrice referent, String mini) throws DAOException {
		Statement stat = null;
		ResultSet rs = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date convertir = null;
		if (!mini.equals(""))
			convertir = new FormaterDate().changeFormatChaineDate(mini);
		else
			convertir = new FormaterDate().changeFormatChaineDate("01-01-1900");

		List<String> liste = null;
		List<List<String>> retour = new ArrayList<List<String>>();
		String req = "select distinct(" + DAOConstants.t_suiviAccompagneformation
				+ ".id_identite), MAX(dateredaction) as date,id_accomp,nom_identite,prenom_identite,"
				+ "mobil_identite,tel1_identite,ville_identite from " + DAOConstants.t_suiviAccompagneformation
				+ " inner join " + DAOConstants.t_identite + " on " + DAOConstants.t_suiviAccompagneformation
				+ ".id_identite=" + DAOConstants.t_identite + ".id_identite " + " where dateredaction >='" + convertir
				+ "' and animatrice_pyramide=" + referent.getId_animatrice() + " group by " + DAOConstants.t_suiviAccompagneformation
				+ ".id_identite ";// order by date desc";

		try {

			stat = connect.createStatement();
			rs = stat.executeQuery(req);

			while (rs.next()) {
				liste = new ArrayList<String>();
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
			
			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}

		return retour;

	}
}
