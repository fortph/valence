package dao.imp.ai;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import beans.ai.Creation;
import beans.identite.Identite;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.imp.identite.IdentiteDAO;
import dao.interfaces.DAO;

public class CreationDAO implements DAO<Creation> {
	
	
	final private String query_insert = "INSERT INTO " + DAOConstants.t_aicreation
			+ " (id_identite,permanent_aifiche,ndue_aifiche,datedue_aifiche"
			+ ") VALUES(?,?,?,?)";
	
	final private String query_update = "update "
			+ DAOConstants.t_aicreation
			+ " set id_identite=?,permanent_aifiche=?,ndue_aifiche=?,datedue_aifiche=? "
			+ "where idaifiche=";
	 
	final private String query_verif = "select count(*) FROM " + DAOConstants.t_aicreation
			+ " WHERE id_identite= ";
	
	final private String query_id = "select idaifiche FROM " + DAOConstants.t_aicreation
			+ " WHERE id_identite= ";

	private Connection connect = null;

	public CreationDAO() {
		connect = DBConnexion.getConnexion();
	}

	@Override
	public Creation findByID(int id) throws DAOException {
		String query = "select * from " + DAOConstants.t_aicreation
				+ " where idaifiche=" + id;
		Creation fiche = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.next()) {
				fiche = new Creation(id,new IdentiteDAO().findByID(res.getInt(2)),
						res.getBoolean(3),res.getString(4),res.getDate(5));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return fiche;
	}

	@Override
	public List<Creation> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Creation> findByCriteria(Creation obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(Creation obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);

			pst.setInt(1, obj.getIdentite().getId_IDE());
			pst.setBoolean(2, obj.isPermanent());
			pst.setString(3, obj.getNdue());
			pst.setDate(4, (Date) obj.getDatendue());
			
			
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
	public Creation update(Creation obj) throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getId_aifiche();

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.setInt(1, obj.getIdentite().getId_IDE());
			pst.setBoolean(2, obj.isPermanent());
			pst.setString(3, obj.getNdue());
			pst.setDate(4, (Date) obj.getDatendue());
			
			
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
	public int delete(Creation obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}
	/** vérifie si la personne a deja eu un contrat ai (retour =1)  et si oui si le contrat est en cours (retour 2) 	
	 * 
	 * @param id
	 * @return
	 * @throws DAOException 
	 */
	public  int verifierContrat(Identite id) throws DAOException{
		int retour=0;
		PreparedStatement pst = null;
		String query = query_verif + id.getId_IDE();
		
		try {
			pst = connect.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			if(rs.first()){
				retour=rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DAOUtil.closePrepareStatement(pst);
		}
		
		
		return retour;
	}
	
	
	/** recupere l'id de la fiche ai de la personne	
	 * 
	 * @param id
	 * @return
	 * @throws DAOException 
	 */
	public  int recupererID(Identite id) throws DAOException{
		int retour=0;
		PreparedStatement pst = null;
		String query = query_id + id.getId_IDE();
		
		try {
			pst = connect.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			if(rs.first()){
				retour=rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DAOUtil.closePrepareStatement(pst);
		}
		
		
		return retour;
	}
	
	public int verifierCreationFiche (Identite identite)throws DAOException {
		int retour=0;
		String req="select  count(*) as nbre from "
				+DAOConstants.t_aicreation
				+"  where id_identite="
				+identite.getId_IDE();
				
		Statement stat = null;
		ResultSet res = null;
		try {

			stat = connect.createStatement();
			res = stat.executeQuery(req);
			if(res.first())
					retour=res.getInt(1);
			
		} catch (SQLException ex) {
			System.out.print("Problème " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return retour;
		
	}
	/** verifier si la personne est permanente ou pas	
	 * 
	 * @param identite
	 * @return
	 * @throws DAOException
	 */
	public boolean verifierPermanentFiche (Identite identite)throws DAOException {
		boolean retour=false;
		String req="select  permanent_aifiche from "
				+DAOConstants.t_aicreation
				+" where id_identite="
				+identite.getId_IDE();
		Statement stat = null;
		ResultSet res = null;
		try {

			stat = connect.createStatement();
			res = stat.executeQuery(req);
			if(res.first())
					retour=res.getBoolean(1);
			
		} catch (SQLException ex) {
			System.out.print("Problème " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return retour;
		
	}
	
	
/*	public List<Creation> listeAgrementParPersonne(Identite identite) throws DAOException {
		List<Creation> liste=new ArrayList<Creation>();
		Statement stat = null;
		ResultSet res = null;
		String req = "select * from " + DAOConstants.t_aicreation
				+ " whereid_identite="+identite.getId_IDE()+" order by datedebagre_aifiche desc";

		Creation creation = null;
		try {

			stat = connect.createStatement();
			res = stat.executeQuery(req);
			IdentiteDAO iddao=new IdentiteDAO();

			while (res.next()) {
				statut = statdao.findByID(res.getInt(4));
				structure = structdao.findByID(res.getInt(13));
				activite = actdao.findByID(res.getInt(15));

				employeur = new Employeur(res.getInt(1), res.getString(2),
						res.getString(3), statut, res.getString(5),
						res.getString(6), res.getString(7), res.getString(8),
						res.getString(9), res.getString(10), res.getString(11),
						res.getString(12), structure, res.getString(14),
						activite, res.getDate(16), res.getString(17),
						res.getString(18), res.getString(19),
						res.getString(20), res.getBoolean(21), res.getDate(22),
						res.getString(23), res.getString(24), res.getString(25));
				liste.add(employeur);
			}

		} catch (SQLException ex) {
			System.out.print("Probl�me " + ex);
		} finally {
			try {
				stat.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return liste;
	}*/

}
