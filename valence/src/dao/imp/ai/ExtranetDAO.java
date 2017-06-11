package dao.imp.ai;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.ai.Extranet;
import beans.identite.Identite;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.imp.identite.IdentiteDAO;
import dao.interfaces.DAO;

public class ExtranetDAO implements DAO<Extranet> {

	final private String query_insert = "INSERT INTO "
			+ DAOConstants.t_aiextranet + " (id_identite,datedebut, datefin "
			+ ") VALUES(?,?,?)";

	final private String query_update = "update "
			+ DAOConstants.t_aiextranet
			+ " set id_identite=?,datedebut=?,datefin=? "
			+ "where id_extranet=";
	final private String query_delete = "delete from  "
			+ DAOConstants.t_aiextranet + " where id_extranet=?";

	private Connection connect = null;

	public ExtranetDAO() {
		connect = DBConnexion.getConnexion();
	}

	@Override
	public Extranet findByID(int id) throws DAOException {
		String query = "select * from " + DAOConstants.t_aiextranet
				+ " where  id_extranet=" + id;

		Extranet extranet = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				extranet = new Extranet(id, new IdentiteDAO().findByID(res
						.getInt(2)), res.getDate(3), res.getDate(4));
			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return extranet;

	}

	@Override
	public List<Extranet> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Extranet> findByCriteria(Extranet obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(Extranet obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);

			pst.setInt(1, obj.getIdentite().getId_IDE());
			pst.setDate(2, (Date) obj.getDebut());
			pst.setDate(3, (Date) obj.getFin());

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
	public Extranet update(Extranet obj) throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getId_extranet();

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.setInt(1, obj.getIdentite().getId_IDE());
			pst.setDate(2, (Date) obj.getDebut());
			pst.setDate(3, (Date) obj.getFin());

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
	public int delete(Extranet obj) throws DAOException {
		PreparedStatement pst = null;
		int n = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_delete);
			pst.setInt(1, obj.getId_extranet());
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
			DAOUtil.closePrepareStatement(pst);
		}
		return n;
	}
	
	/**liste tous les Extranet de la personne indiquee	
	 * 
	 * @param identite
	 * @return
	 * @throws DAOException
	 */
	public List<Extranet> listeExtranetParPersonne(Identite identite) throws DAOException {
		List<Extranet> liste=new ArrayList<Extranet>();
		String req=" select id_extranet,datedebut,datefin "
				+" from "+DAOConstants.t_aiextranet +" where id_identite="
				+identite.getId_IDE()+" order by id_extranet desc";
		Extranet extranet = null;
		Statement st = null;
		ResultSet res = null;
		
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {

				extranet = new Extranet(res.getInt(1),res.getDate(2), res.getDate(3));
				liste.add(extranet);

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
	/**
	 * extraction de toutes les personnes rentées dans l'extranet mais sans date de fin
	 * @return
	 * @throws DAOException
	 */
	public List<Extranet> listeExtranetSansDateFin() throws DAOException {
		List<Extranet> liste=new ArrayList<Extranet>();
		String req=" select id_extranet,id_identite, datedebut "
				+" from "+DAOConstants.t_aiextranet +" where datefin is null order by id_extranet desc";
		Extranet extranet = null;
		Statement st = null;
		ResultSet res = null;
		
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {

				extranet = new Extranet(res.getInt(1),new IdentiteDAO().findByID(res
						.getInt(2)),res.getDate(3));
				liste.add(extranet);

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
