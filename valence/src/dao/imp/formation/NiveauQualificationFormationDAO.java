package dao.imp.formation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.formation.NiveauQualificationFormation;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.interfaces.DAO;

public class NiveauQualificationFormationDAO implements
		DAO<NiveauQualificationFormation> {

	final private String query_insert = "insert into "
			+ DAOConstants.t_qualificationFormation + "(libelle_niv) "
			+ " values (?)";

	final private String query_update = "update "
			+ DAOConstants.t_qualificationFormation + " set libelle_niv=? "
			+ " where id_niveau=?";

	private Connection connect = null;

	public NiveauQualificationFormationDAO() {
		connect = DBConnexion.getConnexion();
	}

	@Override
	public NiveauQualificationFormation findByID(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NiveauQualificationFormation> findAll() throws DAOException {
		List<NiveauQualificationFormation> liste = new ArrayList<NiveauQualificationFormation>();
		Statement stat = null;
		ResultSet rs = null;
		NiveauQualificationFormation nivo = null;
		String req = "select `id_niveau`, `libelle_niv` from " + DAOConstants.t_qualificationFormation;
		try {
			connect.setAutoCommit(false);
			stat = connect.createStatement();
			rs = stat.executeQuery(req);

			while (rs.next()) {
				nivo = new NiveauQualificationFormation(rs.getByte(1),
						rs.getString(2));
				liste.add(nivo);
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
	public List<NiveauQualificationFormation> findByCriteria(
			NiveauQualificationFormation obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(NiveauQualificationFormation obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);
			// pst.setLong(1, obj.getId_IDE());
			pst.setString(1, obj.getLibelle_niv());

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
	public NiveauQualificationFormation update(NiveauQualificationFormation obj)
			throws DAOException {
		PreparedStatement pst = null;

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_update);
			pst.setString(1, obj.getLibelle_niv());
			pst.setInt(2, obj.getId_niveau());

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
	public int delete(NiveauQualificationFormation obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<String> afficherNiveauQualification() throws DAOException {
		List<String> liste = null;
		Statement stat = null;
		ResultSet rs = null;
		String req = "select libelle_niv from "
				+ DAOConstants.t_qualificationFormation;
		try {
			connect.setAutoCommit(false);
			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			liste = new ArrayList<String>();
			while (rs.next())
				liste.add(rs.getString("libelle_niv"));
			connect.commit();
		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return liste;
	}

}
