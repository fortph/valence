package dao.imp.rth;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import beans.identite.Identite;
import beans.rth.FicheRTH;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.imp.identite.IdentiteDAO;
import dao.interfaces.DAO;

public class FicheDAORTH implements DAO<FicheRTH> {
	
	private Connection connect = null;
	final private String query_insert = "insert into "
			+ DAOConstants.t_rth
			+ "(id_identite,categorie_rth,taux_rth,pension_rth,"
			+ "montantpension_rth,ci_rth,refadiad_rth,datecreation_rth ) values (?,?,?,?,?,?,?,?)";

	final private String query_update = "update "
			+ DAOConstants.t_rth
			+ " set id_identite=?,categorie_rth=?,taux_rth=?,pension_rth=?,"
			+ "montantpension_rth=?,ci_rth=?,refadiad_rth=?,datecreation_rth =? "
			+ " where id_rth=";

	public FicheDAORTH() {
		connect = DBConnexion.getConnexion();
	}

	@Override
	public FicheRTH findByID(int id) throws DAOException {
		String query = "select `id_rth`, `id_identite`, `categorie_rth`,"
				+ " `taux_rth`, `pension_rth`, `montantpension_rth`,"
				+ " `ci_rth`, `refadiad_rth`, `datecreation_rth` from " 
				+ DAOConstants.t_rth
				+ " where id_rth=" + id;
		FicheRTH pl = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.next()) {
				pl = new FicheRTH(id,
						new IdentiteDAO().findByID(res.getInt(2)),
						res.getString(3), res.getInt(4),res.getBoolean(5),
						 res.getString(6), res.getString(7),res.getString(8),res.getDate(9));
			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return pl;

	}
	/**recupere la derniere fiche rth de la personne*/
	public FicheRTH derniereFicheRth(Identite id) throws DAOException {
		String query = "select max(id_rth) as fic from " + DAOConstants.t_rth
				+ " where id_identite=" + id.getId_IDE();
		FicheRTH pl = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				pl = this.findByID(res.getInt(1));}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return pl;

	}

	@Override
	public List<FicheRTH> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FicheRTH> findByCriteria(FicheRTH obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(FicheRTH obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, obj.getIdentite().getId_IDE());
			pst.setString(2, obj.getCategorie_rth());
			pst.setInt(3, obj.getTaux_rth());
			pst.setBoolean(4, obj.isPension_rth());
			pst.setString(5, obj.getMontant());
			pst.setString(6, obj.getCi_rth());
			pst.setString(7, obj.getReferent_rth());
			pst.setDate(8, (Date) obj.getCreation_rth());
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
			DAOUtil.closePrepareStatement(pst);
		}
		return cle;
	}

	@Override
	public FicheRTH update(FicheRTH obj) throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getId_rth();

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.setInt(1, obj.getIdentite().getId_IDE());
			pst.setInt(1, obj.getIdentite().getId_IDE());
			pst.setString(2, obj.getCategorie_rth());
			pst.setInt(3, obj.getTaux_rth());
			pst.setBoolean(4, obj.isPension_rth());
			pst.setString(5, obj.getMontant());
			pst.setString(6, obj.getCi_rth());
			pst.setString(7, obj.getReferent_rth());
			pst.setDate(8, (Date) obj.getCreation_rth());
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
	public int delete(FicheRTH obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int ficheRthExiste(Identite une)throws DAOException {
		int retour=0;
		
		String query = "select count(id_rth) as maxi from " + DAOConstants.t_rth
				+ " where id_identite=" + une.getId_IDE();
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

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		
		return retour;
		
	}

}
