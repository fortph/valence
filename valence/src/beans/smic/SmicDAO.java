package beans.smic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import dao.DAOConstants;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.interfaces.DAO;

public class SmicDAO implements DAO<Smic> {
	final private String query_insert = "INSERT INTO " + DAOConstants.t_smic
			+ " (txsmic,date_smic,anciensmic) VALUES(?,?,?)";

	final private String query_update = "update " + DAOConstants.t_smic
			+ " set txsmic=?,date_smic=?,anciensmic=? where id_smic=?";

	private Connection connect = null;

	public SmicDAO() {
		connect = DBConnexion.getConnexion();
	}

	@Override
	public Smic findByID(int id) throws DAOException {
		String query = "select * from " + DAOConstants.t_smic
				+ " where  id_smic=" + id;
		Smic smic = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				smic = new Smic((int) id, res.getFloat(2), res.getDate(3),
						res.getFloat(4));

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

		return smic;

	}

	@Override
	public List<Smic> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Smic> findByCriteria(Smic obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(Smic obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);

			pst.setFloat(1, obj.getTsxmic());
			pst.setDate(2, (Date) obj.getDate_smic());
			pst.setFloat(3, obj.getAnciensmic());

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
	public Smic update(Smic obj) throws DAOException {
		PreparedStatement pst = null;

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_update);
			pst.setFloat(1, obj.getTsxmic());
			pst.setDate(2, (Date) obj.getDate_smic());
			pst.setFloat(3, obj.getAnciensmic());
			pst.setInt(4, obj.getId_smic());
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
	public int delete(Smic obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}
/**on recupere la date de mise en application du smic	
 * 
 * @return
 * @throws DAOException
 */
	public Date dateVigueurSmic() throws DAOException {
		Date retour = null;
		String query = "select date_smic from " + DAOConstants.t_smic;

		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				retour = res.getDate(1);

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

		return retour;

	}
/**retourne le montant du smic en fonction de la date du jour	
 * 
 * @return
 * @throws DAOException
 */
	public float smicHoraire() throws DAOException {
		float cout = 0.00f;

		Date jour = new Date(System.currentTimeMillis());
		Date vigueursmic = this.dateVigueurSmic();
		String req = "select anciensmic from " + DAOConstants.t_smic;
		String req1 = "select txsmic from " + DAOConstants.t_smic;
		String requete = "";
		if (jour.compareTo(vigueursmic) >= 0)
			requete = req1;
		else
			requete = req;

		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			res = st.executeQuery(requete);
			if (res.first()) {
				cout = res.getFloat(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
		return cout;

	}
	/*
	public float moisSmic(int m, String num) throws DAOException {
		float cout = 0f;
		ContratDAO cdao = new ContratDAO();
		Contrat contrat = cdao.findByID(Integer.parseInt(num));
		String req = "select *, month(date_smic) as date from  "
				+ DAOConstants.t_smic;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			if (res.first()) {
				if (m < res.getInt("date")) {
					cout = contrat.getFacturation() <= res
							.getFloat("ancienfacturation") ? res
							.getFloat("ancienfacturation") : contrat
							.getFacturation();
				} else {
					cout = contrat.getFacturation() <= res
							.getFloat("facturation") ? res
							.getFloat("facturation") : contrat.getFacturation();
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
		return cout;

	}

	public float moisSmicAvenant(int m, String num) throws DAOException {
		float cout = 0f;
		AvenantDAO cdao = new AvenantDAO();
		Avenant contrat = cdao.findByID(Integer.parseInt(num));
		String req = "select *, month(date_smic) as date from  "
				+ DAOConstants.t_smic;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			if (res.first()) {
				if (m < res.getInt("date")) {
					cout = contrat.getFacturation() <= res
							.getFloat("ancienfacturation") ? res
							.getFloat("ancienfacturation") : contrat
							.getFacturation();
				} else {
					cout = contrat.getFacturation() <= res
							.getFloat("facturation") ? res
							.getFloat("facturation") : contrat.getFacturation();
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
		return cout;

	}
	*/
}
