package dao.imp.rmi;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import beans.parametres.capemploi.UtilisateurDAO;
import beans.rmi.ConvocRMI;
import beans.rmi.FicheRMI;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.imp.identite.IdentiteDAO;
import dao.interfaces.DAO;

public class ConvocRMIDAO implements DAO<ConvocRMI> {
	


	final private String query_insert = "insert into "
			+ DAOConstants.t_rmiconvocation
			+ "( id_rmi,id_identite, date_rmiconvoc,dateconvoc_rmiconvoc, heureconvoc_rmiconvoc,"
			+ "referent_rmiconvoc)"
			+ " values (?,?,?,?,?,?)";

	final private String query_update = "update "
			+ DAOConstants.t_rmiconvocation
			+ " set id_rmi=?,id_identite=?, date_rmiconvoc=?,dateconvoc_rmiconvoc=?,"
			+ " heureconvoc_rmiconvoc=?,referent_rmiconvoc=? "
			+ " where id_rmiconvoc=";

	private Connection connect = null;

	public ConvocRMIDAO() {
		connect = DBConnexion.getConnexion();

	}


	@Override
	public ConvocRMI findByID(int id) throws DAOException {
		String query = "select  `id_rmiconvoc`, `id_rmi`, `id_identite`,"
				+ " `date_rmiconvoc`, `dateconvoc_rmiconvoc`, "
				+ "`heureconvoc_rmiconvoc`, `referent_rmiconvoc` from "
				+ DAOConstants.t_rmiconvocation
				+ " where id_rmiconvoc=" + id;
		ConvocRMI pl = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.next()) {
				pl = new ConvocRMI(id, new FicheRMIDAO().findByID(res.getInt(2)),
						new IdentiteDAO().findByID(res.getInt(3)),res.getDate(4),res.getDate(5),
						res.getString(6),new UtilisateurDAO().findByID(res.getInt(7)));	}

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
	public List<ConvocRMI> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConvocRMI> findByCriteria(ConvocRMI obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(ConvocRMI obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, obj.getFichermi().getId_rmi());
			pst.setInt(2, obj.getIdentite().getId_IDE());
			pst.setDate(3, (Date) obj.getRmiconvoc());
			pst.setDate(4, (Date) obj.getDateconvoc_rmiconvoc());
			pst.setString(5, obj.getHeureconvoc());
			pst.setInt(6, obj.getReferent().getId_salarie());
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
	public ConvocRMI update(ConvocRMI obj) throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getId_convoc();

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.setInt(1, obj.getFichermi().getId_rmi());
			pst.setInt(2, obj.getIdentite().getId_IDE());
			pst.setDate(3, (Date) obj.getRmiconvoc());
			pst.setDate(4, (Date) obj.getDateconvoc_rmiconvoc());
			pst.setString(5,  obj.getHeureconvoc());
			pst.setInt(6, obj.getReferent().getId_salarie());
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
	public int delete(ConvocRMI obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}
	
/** recupere l'id de la derniere convocation concernant cette fiche*/
	public int recupereDerniereDateConvocation(FicheRMI fiche)throws DAOException{
		
		String query = "select max(id_rmiconvoc) as idrmiconvoc from "
		+ DAOConstants.t_rmiconvocation
				+ " where id_rmi=" + fiche.getId_rmi();
		int date = 0;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.next()) {
				date= res.getInt(1);	}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return date;
		
	}
}
