package dao.imp.ai;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.ai.Agrement;
import beans.identite.Identite;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.imp.identite.IdentiteDAO;
import dao.interfaces.DAO;

public class AgrementDAO implements DAO<Agrement> {

	final private String query_insert = "INSERT INTO "
			+ DAOConstants.t_aiagrement
			+ " (id_identite,nagrement_aifiche,datedebagre_aifiche,datefinagre_aifiche "
			+ ") VALUES(?,?,?,?)";

	final private String query_update = "update "
			+ DAOConstants.t_aiagrement
			+ " set id_identite=?,nagrement_aifiche=?,datedebagre_aifiche=?,datefinagre_aifiche=? "
			+ " where idagrement=?";
	final private String query_delete = "delete from  "
			+ DAOConstants.t_aiagrement + " where idagrement=?";

	private Connection connect = null;

	public AgrementDAO() {
		connect = DBConnexion.getConnexion();
	}

	@Override
	public List<Agrement> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Agrement> findByCriteria(Agrement obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(Agrement obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);

			pst.setInt(1, obj.getIdentite().getId_IDE());
			pst.setString(2, obj.getNumagrement());
			pst.setDate(3, (Date) obj.getDatedeb());
			pst.setDate(4, (Date) obj.getDatefin());

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
	public Agrement update(Agrement obj) throws DAOException {
		PreparedStatement pst = null;
		

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_update);
			pst.setInt(1, obj.getIdentite().getId_IDE());
			pst.setString(2, obj.getNumagrement());
			pst.setDate(3, (Date) obj.getDatedeb());
			pst.setDate(4, (Date) obj.getDatefin());
			pst.setInt(5, obj.getId_aifiche());
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
	public int delete(Agrement obj) throws DAOException {
		PreparedStatement pst = null;
		int n = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_delete);
			pst.setInt(1, obj.getId_aifiche());
			n = pst.executeUpdate();
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
		return n;
	}

	@Override
	public int delete(long id) throws DAOException {
		PreparedStatement pst = null;
		int n = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_delete);
			pst.setLong(1, id);
			n = pst.executeUpdate();
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
		return n;
	}

	/** liste tous les agrement recus pour la personne indiquee 	
	 * 
	 * @param identite
	 * @return
	 * @throws DAOException
	 */
	public List<Agrement> listeAgrementParPersonne(Identite identite)
			throws DAOException {
		List<Agrement> liste = new ArrayList<Agrement>();
		String req = " select idagrement,nagrement_aifiche,datedebagre_aifiche,datefinagre_aifiche"
				+ " from "
				+ DAOConstants.t_aiagrement
				+ " where id_identite="
				+ identite.getId_IDE() + " order by idagrement desc";
		Agrement agrement = null;
		Statement st = null;
		ResultSet res = null;

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {

				agrement = new Agrement(res.getInt(1), res.getString(2),
						res.getDate(3), res.getDate(4));
				liste.add(agrement);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return liste;

	}

	@Override
	public Agrement findByID(int id) throws DAOException {
		String query = "select * from " + DAOConstants.t_aiagrement
				+ " where  idagrement=" + id;

		Agrement agrement = null;
		Statement st = null;
		ResultSet res = null;

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				agrement = new Agrement(id, new IdentiteDAO().findByID(res
						.getInt(2)), res.getString(3), res.getDate(4),
						res.getDate(5));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return agrement;
	}
	
	
	/** liste tous les agrement par personne et date	
	 * 
	 * @param deb
	 * @param fin
	 * @return
	 * @throws DAOException
	 */
	public List<Agrement> listeAgrementParDates(Date deb, Date fin)
			throws DAOException {
		List<Agrement> liste = new ArrayList<Agrement>();
		String req = " select idagrement,"
				+ DAOConstants.t_aiagrement
				+".id_identite,  nagrement_aifiche,datedebagre_aifiche,datefinagre_aifiche"
				+ ", concat(nom_identite, prenom_identite) as nom from "
				+ DAOConstants.t_aiagrement
				+" inner join "
				+ DAOConstants.t_identite
				+ " on "
				+ DAOConstants.t_aiagrement
				+".id_identite="
					+ DAOConstants.t_identite
					+".id_identite "
				+ " where datedebagre_aifiche between '"+deb+"' and '"+fin+"' order by nom asc ";
			
		Agrement agrement = null;
		Statement st = null;
		ResultSet res = null;
		IdentiteDAO idao=new IdentiteDAO();
		Identite identite=null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {
				identite=idao.findByID(res.getInt(2));
				agrement = new Agrement(res.getInt(1),identite, res.getString(3),
						res.getDate(4), res.getDate(5));
				liste.add(agrement);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return liste;

	}


}
