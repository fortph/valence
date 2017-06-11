package dao.imp.rmi;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import beans.rmi.ContratRMI;
import beans.rmi.FicheLiaisonRMI;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.interfaces.DAO;

public class FicheLiaisonRMIDAO implements DAO<FicheLiaisonRMI> {

	private Connection connect = null;
	final private String query_insert = "insert into "
			+ DAOConstants.t_ficheliaison
			+ "(id_rmicontrat,datecreation,"
			+ "nomreferentpe,prenomreferentpe,mailreferentpe,obsprescripteur,presence,"
			+ "acquis,nomacquis,encours,conclusion) "
			+ " values (?,?,?,?,?,?,?,?,?,?,?)";

	final private String query_update = "update "
			+ DAOConstants.t_ficheliaison
			+ " set id_rmicontrat=?,datecreation=?,nomreferentpe=?,"
			+ "prenomreferentpe=?,mailreferentpe=?,obsprescripteur=?,presence=?,acquis=?,"
			+ "nomacquis=?,encours=?,conclusion=? "
			+ " where id_ficheliaison=";

	public FicheLiaisonRMIDAO() {
		connect = DBConnexion.getConnexion();
	}

	@Override
	public FicheLiaisonRMI findByID(int id) throws DAOException {
		String query = "select  `id_ficheliaison`, `id_rmicontrat`, `datecreation`,"
				+ " `nomreferentpe`, `prenomreferentpe`, `mailreferentpe`,"
				+ " `obsprescripteur`,`presence`, `acquis`, `nomacquis`, `encours`,"
				+ " `conclusion` from " 
				+ DAOConstants.t_ficheliaison
				+ " where id_ficheliaison=" + id;
		FicheLiaisonRMI pl = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				pl = new FicheLiaisonRMI(id, new ContratRMIDAO().findByID(res
						.getInt(2)), res.getDate(3), res.getString(4),
						res.getString(5), res.getString(6), res.getString(7),
						res.getString(8), res.getString(9), res.getString(10),
						res.getString(11),res.getString(12));

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

	@Override
	public List<FicheLiaisonRMI> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FicheLiaisonRMI> findByCriteria(FicheLiaisonRMI obj)
			throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(FicheLiaisonRMI obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, obj.getContrat().getId_rmicontrat());
			pst.setDate(2, (Date) obj.getDatecreation());
			pst.setString(3, obj.getNomreferentpe());
			pst.setString(4, obj.getPrenomreferentpe());
			pst.setString(5, obj.getMailreferentpe());
			pst.setString(6, obj.getObsprescripteur());
			pst.setString(7, obj.getPresence());
			pst.setString(8, obj.getAcquis());
			pst.setString(9, obj.getNomacquis());
			pst.setString(10, obj.getEncours());
			pst.setString(11, obj.getConclusion());

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
	public FicheLiaisonRMI update(FicheLiaisonRMI obj) throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getId_ficheliaison();

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.setInt(1, obj.getContrat().getId_rmicontrat());
			pst.setDate(2, (Date) obj.getDatecreation());
			pst.setString(3, obj.getNomreferentpe());
			pst.setString(4, obj.getPrenomreferentpe());
			pst.setString(5, obj.getMailreferentpe());
			pst.setString(6, obj.getObsprescripteur());
			pst.setString(7, obj.getPresence());
			pst.setString(8, obj.getAcquis());
			pst.setString(9, obj.getNomacquis());
			pst.setString(10, obj.getEncours());
			pst.setString(11, obj.getConclusion());
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
	public int delete(FicheLiaisonRMI obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**verifie si la fiche de liaison est deja créee*/
	public boolean ficheLiaisonCree(ContratRMI obj)throws DAOException {
		boolean retour=false;
		
		String query = "select id_ficheliaison from " + DAOConstants.t_ficheliaison
				+ " where id_rmicontrat=" + obj.getId_rmicontrat();
		
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				retour=true;
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
	
	
	public FicheLiaisonRMI recupereFicheCorrespondContrat(ContratRMI cont) throws DAOException {
		int id_contrat=cont.getId_rmicontrat();
		String query = "select  `id_ficheliaison`, `id_rmicontrat`, `datecreation`,"
				+ " `nomreferentpe`, `prenomreferentpe`, `mailreferentpe`,"
				+ " `obsprescripteur`,`presence`, `acquis`, `nomacquis`, `encours`,"
				+ " `conclusion` from "  + DAOConstants.t_ficheliaison
				+ " where id_rmicontrat=" + id_contrat;
		FicheLiaisonRMI fl = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				fl = new FicheLiaisonRMI(res.getInt(1),cont, res.getDate(3), res.getString(4),
						res.getString(5), res.getString(6), res.getString(7),
						res.getString(8), res.getString(9), res.getString(10),
						res.getString(11),res.getString(12));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return fl;

	}

}
