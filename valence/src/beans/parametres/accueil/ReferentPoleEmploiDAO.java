package beans.parametres.accueil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.DAOConstants;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.interfaces.DAO;

public class ReferentPoleEmploiDAO implements DAO<ReferentPoleEmploi> {
	

	final private String query_insert = "INSERT INTO "
			+ DAOConstants.t_referentpe
			+ " (nom,prenom,mail ,mailrsa,maildt) VALUES(?,?,?,?,?)";

	final private String query_update = "update "
			+ DAOConstants.t_referentpe
			+ " set nom=?,prenom=?,mail=?,mailrsa=?,maildt=? "
			+ " where id_referent=?";
	

	private Connection connect = null;

	public ReferentPoleEmploiDAO() {
		connect = DBConnexion.getConnexion();
	}

	@Override
	public ReferentPoleEmploi findByID(int id) throws DAOException {
		String query = "select * from " + DAOConstants.t_referentpe
				+ " where  id_referent=" + id;
		ReferentPoleEmploi ref = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				ref = new ReferentPoleEmploi((int) id, res.getString(2),
						res.getString(3), res.getString(4),res.getString(5),res.getString(6));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			try {
				res.close();
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return ref;
	}
	/**
	 * affiche le referent pole emploi en cours
	 * @return
	 * @throws DAOException
	 */
	
	public ReferentPoleEmploi afficheReferent() throws DAOException {
		String query = "select * from " + DAOConstants.t_referentpe
				+ " where 1=1";
		ReferentPoleEmploi ref = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				ref = new ReferentPoleEmploi(res.getInt(1), res.getString(2),
						res.getString(3), res.getString(4),res.getString(5),res.getString(6));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			try {
				res.close();
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return ref;
	}
	
	

	@Override
	public List<ReferentPoleEmploi> findAll() throws DAOException {
		return null;
	}
	
	
	public List<String> displayAll() throws DAOException {
		String query = "select nom,prenom from " + DAOConstants.t_referentpe
				+ " where 1=1";
		String ref;
		List<String> liste=new ArrayList<String>();
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
		while (res.next()) {
				ref =  res.getString(1)+" "+res.getString(2);
				liste.add(ref);
			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			try {
				res.close();
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return liste;
	}


	@Override
	public List<ReferentPoleEmploi> findByCriteria(ReferentPoleEmploi obj)
			throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(ReferentPoleEmploi obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, obj.getNom());
			pst.setString(2, obj.getPrenom());
			pst.setString(3,  obj.getMail());
			pst.setString(4,  obj.getMailrsa());
			pst.setString(5,  obj.getMaildt());

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
			try {
				pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return cle;

	}

	@Override
	public ReferentPoleEmploi update(ReferentPoleEmploi obj)throws DAOException {
	PreparedStatement pst = null;
	

	try {
		connect.setAutoCommit(false);
		pst = connect.prepareStatement(query_update);

		pst.setString(1, obj.getNom());
		pst.setString(2, obj.getPrenom());
		pst.setString(3,  obj.getMail());
		pst.setString(4,  obj.getMailrsa());
		pst.setString(5,  obj.getMaildt());
		pst.setInt(6, obj.getId_referent());
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
		try {
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	return obj;
	}

	@Override
	public int delete(ReferentPoleEmploi obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

}
