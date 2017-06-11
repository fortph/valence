package dao.imp.suivi;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.formation.Animatrice;
import beans.identite.Identite;
import beans.parametres.accueil.TypeFormations;
import beans.parametres.accueil.TypeFormationsDAO;
import beans.suivi.SuiviFormation;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.imp.formation.AnimatriceDAO;
import dao.imp.identite.IdentiteDAO;
import dao.interfaces.DAO;
import divers.FormaterDate;

public class SuiviFormationDAO implements DAO<SuiviFormation> {

	final private String query_insert = "insert into " + DAOConstants.t_suiviformation
			+ "(id_identite, datesuivi,animatrice_pyramide,formation,datedebut, datefin,of,typeformation, commentaire)"
			+ " values (?,?,?,?,?,?,?,?,?)";
	final private String query_update = "update " + DAOConstants.t_suiviformation
			+ " set id_identite=?, datesuivi=?,animatrice_pyramide=?,formation=?,datedebut=?,"
			+ " datefin=?,of=?,typeformation=?, commentaire=?" + " where id_suivi=";

	private Connection connect = null;

	public SuiviFormationDAO() {
		connect = DBConnexion.getConnexion();

	}

	@Override
	public SuiviFormation findByID(int id) throws DAOException {
		String query = "select id_identite, datesuivi,animatrice_pyramide,formation,datedebut,"
				+ " datefin,of,typeformation, commentaire from " + DAOConstants.t_suiviformation + " where  id_suivi="
				+ id;
		SuiviFormation suivi = null;
		Statement st = null;
		ResultSet res = null;
		IdentiteDAO idao = new IdentiteDAO();
		AnimatriceDAO anidao = new AnimatriceDAO();
		TypeFormationsDAO tydao = new TypeFormationsDAO();
		//OrganismeFormationDAO ordao = new OrganismeFormationDAO();
		//OrganismeFormation org = null;
		Identite ident = null;
		Animatrice anim = null;
		TypeFormations type = null;

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				ident = idao.findByID(res.getInt(1));
				//org = ordao.findByID(res.getInt(7));
				anim = anidao.findByID(res.getInt(3));
				type = tydao.findByID(res.getInt(8));
				suivi = new SuiviFormation(id, ident, res.getDate(2), anim, res.getString(4), res.getDate(5),
						res.getDate(6), res.getString(7), type, res.getString(9));

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
	public List<SuiviFormation> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SuiviFormation> findByCriteria(SuiviFormation obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(SuiviFormation obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert, Statement.RETURN_GENERATED_KEYS);

			pst.setInt(1, obj.getIdentite().getId_IDE());
			pst.setDate(2, (java.sql.Date) obj.getDateSuivi());
			pst.setInt(3, obj.getReferent().getId_animatrice());
			pst.setString(4, obj.getFormation());
			pst.setDate(5, (java.sql.Date) obj.getDateDebutFormation());
			pst.setDate(6, (java.sql.Date) obj.getDateFinFormation());
			pst.setString(7, obj.getOf());
			pst.setInt(8, obj.getTypeFormations().getId_proposition());
			pst.setString(9, obj.getCommentaires());

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
	public SuiviFormation update(SuiviFormation obj) throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getIdsuivi();

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);

			pst.setInt(1, obj.getIdentite().getId_IDE());
			pst.setDate(2, (java.sql.Date) obj.getDateSuivi());
			pst.setInt(3, obj.getReferent().getId_animatrice());
			pst.setString(4, obj.getFormation());
			pst.setDate(5, (java.sql.Date) obj.getDateDebutFormation());
			pst.setDate(6, (java.sql.Date) obj.getDateFinFormation());
			pst.setString(7, obj.getOf());
			pst.setInt(8, obj.getTypeFormations().getId_proposition());
			pst.setString(9, obj.getCommentaires());

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
	public int delete(SuiviFormation obj) throws DAOException {
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
		String query = "select max(datesuivi) from " + DAOConstants.t_suiviformation + " where id_identite="
				+ id.getId_IDE();
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
		if (suivi != null)
			retour = new FormaterDate().formateDate(suivi);

		return retour;

	}

	/** affiche les 3 derniers suivi d'une personne */
	public List<SuiviFormation> afficherTroisSuiviForm(Identite id) throws DAOException {
		List<SuiviFormation> liste = null;
		SuiviFormation suivi = null;
		Statement stat = null;
		ResultSet rs = null;
		//IdentiteDAO idao = new IdentiteDAO();
		AnimatriceDAO anidao = new AnimatriceDAO();
		TypeFormationsDAO tydao = new TypeFormationsDAO();
		//OrganismeFormationDAO ordao = new OrganismeFormationDAO();
		//OrganismeFormation org = null;
		//Identite ident = null;
		Animatrice anim = null;
		TypeFormations type = null;

		String req = "select id_suivi,id_identite, datesuivi,animatrice_pyramide,formation,datedebut,"
				+ " datefin,of,typeformation, commentaire from " + DAOConstants.t_suiviformation + " where id_identite="
				+ id.getId_IDE() + " order by datesuivi desc  limit 0,3";

		try {

			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			liste = new ArrayList<SuiviFormation>();
			while (rs.next()) {
				//ident = idao.findByID(rs.getInt(2));
				//org = ordao.findByID(rs.getInt(8));
				anim = anidao.findByID(rs.getInt(4));
				type = tydao.findByID(rs.getInt(9));
				suivi = new SuiviFormation(rs.getInt(1), id, rs.getDate(3), anim, rs.getString(5), rs.getDate(6),
						rs.getDate(7), rs.getString(8), type, rs.getString(10));

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

	public List<SuiviFormation> afficheTousLesSuivisForm(Identite id) throws DAOException {
		List<SuiviFormation> liste = null;
		SuiviFormation suivi = null;
		Statement stat = null;
		ResultSet rs = null;
		//IdentiteDAO idao = new IdentiteDAO();
		AnimatriceDAO anidao = new AnimatriceDAO();
		TypeFormationsDAO tydao = new TypeFormationsDAO();
		//OrganismeFormationDAO ordao = new OrganismeFormationDAO();
		//OrganismeFormation org = null;
		//Identite ident = null;
		Animatrice anim = null;
		TypeFormations type = null;

		String req = "select id_suivi,id_identite, datesuivi,animatrice_pyramide,formation,datedebut,"
				+ " datefin,of,typeformation, commentaire from " + DAOConstants.t_suiviformation + " where id_identite="
				+ id.getId_IDE() + " order by datesuivi desc";

		try {

			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			liste = new ArrayList<SuiviFormation>();
			while (rs.next()) {
				//ident = idao.findByID(rs.getInt(2));
				//org = ordao.findByID(rs.getInt(8));
				anim = anidao.findByID(rs.getInt(4));
				type = tydao.findByID(rs.getInt(9));
				suivi = new SuiviFormation(rs.getInt(1), id, rs.getDate(3), anim, rs.getString(5), rs.getDate(6),
						rs.getDate(7), rs.getString(8), type, rs.getString(10));

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
	
	
	/** 
	 * affiche le nombre de participants par type de formation et par date
	 * @param debut
	 * @param fin
	 * @param formation
	 * @return
	 * @throws DAOException
	 */	

	public int afficheStatSurPeriode(Date debut, Date fin, int formation) throws DAOException {
		int nbre=0;
		Statement stat = null;
		ResultSet rs = null;
		
		String req = "select count(distinct id_suivi)  from " + DAOConstants.t_suiviformation + 
				" where typeformation="+formation+" and ((datedebut between '"+debut+ "' and '"+fin+ "') or "
						+ "(datefin between '"+debut+ "' and '"+fin+"') or (datedebut <= '"+debut+ "' and datefin >='"+fin+"')) ";

		try {

			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			
			if(rs.first()) {
				nbre=rs.getInt(1);
			}

		} catch (SQLException ex) {
			System.out.print("Problème " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return nbre;

	}


}
