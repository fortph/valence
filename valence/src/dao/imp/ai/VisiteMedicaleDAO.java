package dao.imp.ai;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.ai.VisiteMedicale;
import beans.identite.Identite;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.imp.identite.IdentiteDAO;
import dao.interfaces.DAO;

public class VisiteMedicaleDAO implements DAO<VisiteMedicale> {
	

	final private String query_insert = "INSERT INTO " + DAOConstants.t_aivisitemedicale
			+ " (id_identite,dateconvocation, datevisite,dateecheance "
			+ ") VALUES(?,?,?,?)";
	
	final private String query_update = "update "
			+ DAOConstants.t_aivisitemedicale
			+ " set id_identite=?,dateconvocation=?,datevisite=?,dateecheance=? "			
			+ "where id_suivimedical=";
	final private String query_delete = "delete from  "
			+ DAOConstants.t_aivisitemedicale + " where id_suivimedical=?";
	
	private Connection connect = null;

	public VisiteMedicaleDAO() {
		connect = DBConnexion.getConnexion();
	}

	@Override
	public VisiteMedicale findByID(int id) throws DAOException {
		String query = "select * from " + DAOConstants.t_aivisitemedicale
				+ " where  id_suivimedical=" + id;

		VisiteMedicale visite = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				visite = new VisiteMedicale(id, new IdentiteDAO().findByID(res
						.getInt(2)), res.getDate(3), res.getDate(4),res.getDate(5));
			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return visite;
	}

	@Override
	public List<VisiteMedicale> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VisiteMedicale> findByCriteria(VisiteMedicale obj)
			throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(VisiteMedicale obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);

			pst.setInt(1, obj.getIdentite().getId_IDE());
			pst.setDate(2, (Date) obj.getConvocation());
			pst.setDate(3, (Date) obj.getVisite());
			pst.setDate(4, (Date) obj.getEcheance());
			
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
	public VisiteMedicale update(VisiteMedicale obj) throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getId_suivi();

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.setInt(1, obj.getIdentite().getId_IDE());
			pst.setDate(2, (Date) obj.getConvocation());
			pst.setDate(3, (Date) obj.getVisite());
			pst.setDate(4, (Date) obj.getEcheance());
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
	public int delete(VisiteMedicale obj) throws DAOException {
		PreparedStatement pst = null;
		int n = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_delete);
			pst.setInt(1, obj.getId_suivi());
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
	/**liste toutes les visites medicale de la personne indiquee	
	 * 
	 * @param identite
	 * @return
	 * @throws DAOException
	 */
	public List<VisiteMedicale> listeVisitesParPersonne(Identite identite) throws DAOException {
		List<VisiteMedicale> liste=new ArrayList<VisiteMedicale>();
		String req=" select id_suivimedical,dateconvocation,datevisite,dateecheance "
				+" from "+DAOConstants.t_aivisitemedicale+ " where id_identite="
				+identite.getId_IDE()+" order by id_suivimedical desc";
		VisiteMedicale visite = null;
		Statement st = null;
		ResultSet res = null;
		
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {

				visite = new VisiteMedicale(res.getInt(1),res.getDate(2), res.getDate(3), res.getDate(4));
				liste.add(visite);

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
	
	/**liste toutes les visites medicale  se terminant dans la periode indiquee	
	 * 
	 * @param a
	 * @param b
	 * @return
	 * @throws DAOException
	 */
	public List<VisiteMedicale> listeVisitesParDates(Date a,Date b) throws DAOException {
		List<VisiteMedicale> liste=new ArrayList<VisiteMedicale>();
		String req=" select id_suivimedical,"
				+DAOConstants.t_aivisitemedicale
				+".id_identite,dateecheance "
				+" from "+DAOConstants.t_aivisitemedicale
				+" inner join "
				+DAOConstants.t_identite
				+" on "
				+DAOConstants.t_identite
				+".id_identite="
				+DAOConstants.t_aivisitemedicale
				+".id_identite "
				+ " where dateecheance >='"+a+"' and dateecheance <='"+b+"' order by dateecheance asc";
				
		VisiteMedicale visite=null;
		IdentiteDAO idao=new IdentiteDAO();
		Identite identite=null;
		Statement st = null;
		ResultSet res = null;
		
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {
				identite=idao.findByID(res.getInt(2));
				visite=new VisiteMedicale(res.getInt(1),identite,res.getDate(3));
				liste.add(visite);

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
	
	
	/**verifie si la personne a deja passe une visite	
	 * 
	 * @param identite
	 * @return
	 * @throws DAOException
	 */
	public int visitePassee(Identite identite) throws DAOException {
	int oui=0;
		String req=" select count(id_suivimedical) as tot "
				+" from "+DAOConstants.t_aivisitemedicale
				+ " where id_identite="+identite.getId_IDE()
				+" and datevisite is not null ";
				
		Statement st = null;
		ResultSet res = null;
		
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			if (res.first()) {
				oui=res.getInt(1);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return oui;

	}
	
	
	/**verifie si la personne a deja passe une visite qui est encore valide	
	 * 
	 * @param identite
	 * @return
	 * @throws DAOException
	 */
	public int visiteEnCoursValidite(Identite identite) throws DAOException {
	int oui=0;
		String req=" select count(id_suivimedical) as tot "
				+" from "+DAOConstants.t_aivisitemedicale
				+ " where id_identite="+identite.getId_IDE()
				+" and (datevisite is not null and dateecheance > curdate()) ";
				
		Statement st = null;
		ResultSet res = null;
		
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			if (res.first()) {
				oui=res.getInt(1);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return oui;

	}



}
