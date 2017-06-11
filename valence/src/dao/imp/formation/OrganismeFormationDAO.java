package dao.imp.formation;




import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.formation.OrganismeFormation;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.interfaces.DAO;

public class OrganismeFormationDAO implements DAO<OrganismeFormation> {
	
	final private String query_insert = "insert into "
			+ DAOConstants.t_organismesformation
			+ "(org,adr_org,cp_org,ville_org,tel_org,actif) "
			+ " values (?,?,?,?,?,?)";
	
	final private String query_update = "update "
			+ DAOConstants.t_organismesformation
			+ " set org=?, adr_org=?,cp_org=?,ville_org=?,tel_org=?,actif=? " +
			"where id_org=";
	
	private Connection connect = null;

	public OrganismeFormationDAO() {
		connect = DBConnexion.getConnexion();
	}
	@Override
	public OrganismeFormation findByID(int id) throws DAOException {
		String query = "select `id_org`, `org`, `adr_org`, `cp_org`,"
				+ " `ville_org`, `tel_org`, `actif` from " 
				+ DAOConstants.t_organismesformation
				+ " where  id_org=" + id;
		OrganismeFormation org = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				org = new OrganismeFormation(id, res.getString(2),
						res.getString(3), res.getString(4), res.getString(5),
						res.getString(6), res.getBoolean(7));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return org;
	}

	@Override
	public List<OrganismeFormation> findAll() throws DAOException {
		List<OrganismeFormation> liste=new ArrayList<OrganismeFormation>();
		Statement stat=null;
		ResultSet rs=null;
		String req="select `id_org`, `org`, `adr_org`, `cp_org`,"
				+ " `ville_org`, `tel_org`, `actif` from " 
				+DAOConstants.t_organismesformation+" where org !='AUCUNE' order by org asc";;
		OrganismeFormation orga=null;
		try{
			stat=connect.createStatement();
			rs=stat.executeQuery(req);
				while(rs.next()){
					orga=new OrganismeFormation(rs.getInt(1),rs.getString(2),
							rs.getString(3),rs.getString(4),rs.getString(5),
							rs.getString(6),rs.getBoolean(7));
							liste.add(orga);
				}
		
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
	public List<OrganismeFormation> findByCriteria(OrganismeFormation obj)
			throws DAOException {
		ArrayList<OrganismeFormation> liste = new ArrayList<OrganismeFormation>();
		OrganismeFormation of = null;
		Statement st = null;
		ResultSet res = null;
		String req = "select `id_org`, `org`, `adr_org`, `cp_org`,"
				+ " `ville_org`, `tel_org`, `actif` from " 
				+ DAOConstants.t_organismesformation
				+ " where 1=1 ";
		if (obj.getOrg() != null)
			req += "and org =" + obj.getOrg();

		
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {

				of = new OrganismeFormation(res.getInt(1), res.getString(2),
						res.getString(3), res.getString(4), res.getString(5),
						res.getString(6),
						res.getBoolean(7));
				liste.add(of);

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
	public int create(OrganismeFormation obj) throws DAOException {
			PreparedStatement pst = null;
			int cle = 0;
			try {
				connect.setAutoCommit(false);
				pst = connect.prepareStatement(query_insert,
						Statement.RETURN_GENERATED_KEYS);
				
				pst.setString(1, obj.getOrg());
				pst.setString(2, obj.getAdr_org());
				pst.setString(3,obj.getCp_org());
				pst.setString(4,obj.getVille_org());
				pst.setString(5, obj.getTel_org());
				pst.setBoolean(6, obj.isActif());
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
	public OrganismeFormation update(OrganismeFormation obj)
			throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getId_org();
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.setString(1, obj.getOrg());
			pst.setString(2,obj.getAdr_org());
			pst.setString(3,obj.getCp_org());
			pst.setString(4,obj.getVille_org());
			pst.setString(5,obj.getTel_org());
			pst.setBoolean(6, obj.isActif());
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
	public int delete(OrganismeFormation obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}
	/** affiche la liste des organismes de formation actifs	
	 * 
	 * @return
	 * @throws DAOException
	 */
	public List<String> afficherOrganismesFormation() throws DAOException{
		List<String> liste=null;
		Statement stat=null;
		ResultSet rs=null;
		String req="select org,id_org from "+DAOConstants.t_organismesformation+"  where actif=1 order by org asc";;
		try{
			
			stat=connect.createStatement();
			rs=stat.executeQuery(req);
			liste=new ArrayList<String>();
			while(rs.next()){
				String t=rs.getString("org")+"("+rs.getInt("id_org")+")";
			 liste.add(t);
			 
			}
			
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
	
	/**affiche la liste des formations proposées par un organisme de formation	
	 * 
	 * @return
	 * @throws DAOException
	 */
	public List<String> afficherListeFormationsProposees() throws DAOException{
		List<String> liste=null;
		Statement stat=null;
		ResultSet rs=null;
		String req="select formation,id_pformation from "+DAOConstants.t_listeFormations+" " +
				" inner join "+DAOConstants.t_organismesformation+" on "+
				DAOConstants.t_organismesformation+".id_org="+
				DAOConstants.t_listeFormations+".id_org where actif=1 order by formation asc";;
		try{
			
			stat=connect.createStatement();
			rs=stat.executeQuery(req);
			liste=new ArrayList<String>();
			while(rs.next()){
				String t=rs.getString("formation")+"("+rs.getInt("id_pformation")+")";
			 liste.add(t);
			 
			}
			
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
