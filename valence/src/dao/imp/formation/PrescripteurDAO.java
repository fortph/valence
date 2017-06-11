package dao.imp.formation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.formation.Prescripteur;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.interfaces.DAO;

public class PrescripteurDAO implements DAO<Prescripteur> {

	final private String query_insert = "insert into "
			+ DAOConstants.t_prescripteur + "(prescripteur) " + " values (?)";

	final private String query_update = "update " + DAOConstants.t_prescripteur
			+ " set prescripteur=? " + "where id_prescripteur=";
	
	
	private Connection connect = null;

	public PrescripteurDAO() {
		connect = DBConnexion.getConnexion();
	}

	@Override
	public Prescripteur findByID(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Prescripteur> findAll() throws DAOException {
		List<Prescripteur> liste = new ArrayList<Prescripteur>();
		Statement stat = null;
		ResultSet rs = null;
		Prescripteur presc = null;
		String req = "select `id_prescripteur`, `prescripteur`"
				+ " from " 
				+ DAOConstants.t_prescripteur
				+" order by prescripteur asc";
		
		try {
			connect.setAutoCommit(false);
			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			while (rs.next()) {
				presc = new Prescripteur(rs.getShort(1), rs.getString(2));
				liste.add(presc);
			}
			connect.commit();

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

	@Override
	public List<Prescripteur> findByCriteria(Prescripteur obj)
			throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(Prescripteur obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);
			// pst.setLong(1, obj.getId_IDE());
			pst.setString(1, obj.getPrescripteur());
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
	public Prescripteur update(Prescripteur obj) throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getId_prescripteur();
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.setString(1, obj.getPrescripteur());
			pst.executeUpdate();
			connect.commit();

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
		DAOUtil.closePrepareStatement(pst);
		}
		return obj;
	}

	@Override
	public int delete(Prescripteur obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<String> afficherPrescripteurs() throws DAOException {
		List<String> liste = null;
		Statement stat = null;
		ResultSet rs = null;
		String req = "select prescripteur from " + DAOConstants.t_prescripteur
				+ " order by prescripteur asc";
		

		try {

			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			liste = new ArrayList<String>();
			while (rs.next())
				liste.add(rs.getString("prescripteur"));

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

}
