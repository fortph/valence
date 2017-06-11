package dao.imp.formation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.formation.ListeFormations;
import beans.formation.OrganismeFormation;
import beans.identite.Identite;
import beans.parametres.formation.Theme;
import beans.parametres.formation.ThemeFormationDAO;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.interfaces.DAO;

public class ListeFormationsDAO implements DAO<ListeFormations> {
	private Connection connect = null;

	public ListeFormationsDAO() {
		connect = DBConnexion.getConnexion();
	}

	final private String query_insert = "insert into "
			+ DAOConstants.t_listeFormations
			+ "(formation,id_org,niveau_form,datedeb_form,datefin_form,heure_form,actif,etat_formation,session_form,id_themeformation)"
			+ " values (?,?,?,?,?,?,?,?,?,?)";

	final private String query_update = "update "
			+ DAOConstants.t_listeFormations
			+ " set formation=?, id_org=?,niveau_form=?,datedeb_form=?,datefin_form=?,heure_form=?,actif=?,etat_formation=?,session_form=?,id_themeformation=?  "
			+ "where id_pformation=";

	@Override
	public ListeFormations findByID(int id) throws DAOException {
		String query = "select `id_pformation`, `formation`, `id_org`,"
				+ " `niveau_form`, `datedeb_form`, `datefin_form`,"
				+ " `heure_form`, `actif`, `etat_formation`,"
				+ " `session_form`, `id_themeformation` from "
				+ DAOConstants.t_listeFormations
				+ " where id_pformation=" + id;
		ListeFormations lf = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				OrganismeFormationDAO ofdao = new OrganismeFormationDAO();
				OrganismeFormation of = ofdao.findByID(res.getInt(3));
				ThemeFormationDAO themdao=new ThemeFormationDAO();
				Theme theme=themdao.findByID(res.getInt(11));
				lf = new ListeFormations(id, res.getString(2), of,
						res.getString(4), res.getDate(5), res.getDate(6),
						res.getShort(7), res.getBoolean(8), res.getString(9),
						res.getString(10),theme);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return lf;
	}
	
	
	public ListeFormations findByName(String nom) throws DAOException {
		String query = "select `id_pformation`, `formation`, `id_org`,"
				+ " `niveau_form`, `datedeb_form`, `datefin_form`,"
				+ " `heure_form`, `actif`, `etat_formation`,"
				+ " `session_form`, `id_themeformation` from " + DAOConstants.t_listeFormations
				+ " where formation like '"+nom+"%'" ;
		ListeFormations lf = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				OrganismeFormationDAO ofdao = new OrganismeFormationDAO();
				OrganismeFormation of = ofdao.findByID(res.getInt(3));
				ThemeFormationDAO themdao=new ThemeFormationDAO();
				Theme theme=themdao.findByID(res.getInt(11));
				lf = new ListeFormations(res.getInt(1), res.getString(2), of,
						res.getString(4), res.getDate(5), res.getDate(6),
						res.getShort(7), res.getBoolean(8), res.getString(9),
						res.getString(10),theme);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return lf;
	}
	/**rechercher les formation qui ont pour id de formation et pour organisme de formation les parametres indiques	
	 * 
	 * @param idfor
	 * @param idor
	 * @return
	 * @throws DAOException
	 */
	public ListeFormations findByIDs(String idfor, String idor) throws DAOException {
		int idform=Integer.parseInt(idfor);
		int idorg=Integer.parseInt(idor);
		String query = "select `id_pformation`, `formation`, `id_org`,"
				+ " `niveau_form`, `datedeb_form`, `datefin_form`,"
				+ " `heure_form`, `actif`, `etat_formation`,"
				+ " `session_form`, `id_themeformation` from "
				+ DAOConstants.t_listeFormations
				+ " inner join " + DAOConstants.t_organismesformation + " on "
				+ DAOConstants.t_organismesformation + ".id_org="
				+ DAOConstants.t_listeFormations + ".id_org "
				+ "where id_pformation=" + idform + " and "
				+ DAOConstants.t_organismesformation + ".id_org=" + idorg;
		ListeFormations lf = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				OrganismeFormationDAO ofdao = new OrganismeFormationDAO();
				OrganismeFormation of = ofdao.findByID(idorg);
				ThemeFormationDAO themdao=new ThemeFormationDAO();
				Theme theme=themdao.findByID(res.getInt(11));
				lf = new ListeFormations(idform, res.getString(2), of,
						res.getString(4), res.getDate(5), res.getDate(6),
						res.getShort(7), res.getBoolean(8), res.getString(9),
						res.getString(10),theme);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return lf;
	}
	
	

	@Override
	public List<ListeFormations> findAll() throws DAOException {
		List<ListeFormations> liste = new ArrayList<ListeFormations>();
		Statement stat = null;
		ResultSet rs = null;
		String req = "select `id_pformation`, `formation`, `id_org`,"
				+ " `niveau_form`, `datedeb_form`, `datefin_form`,"
				+ " `heure_form`, `actif`, `etat_formation`,"
				+ " `session_form`, `id_themeformation` from "
				+ DAOConstants.t_listeFormations
				+ " where actif=1 and formation !='AUCUNE' order by formation asc";
		
		ListeFormations formation = null;
		try {

			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			OrganismeFormationDAO orgdao = new OrganismeFormationDAO();
			OrganismeFormation org = null;
			ThemeFormationDAO themdao=new ThemeFormationDAO();
			Theme theme=null;
			while (rs.next()) {
				org = orgdao.findByID(rs.getInt(3));
				theme=themdao.findByID(rs.getInt(11));
				formation = new ListeFormations(rs.getInt(1), rs.getString(2),
						org, rs.getString(4), rs.getDate(5), rs.getDate(6),
						rs.getShort(7), rs.getBoolean(8), rs.getString(9),
						rs.getString(10),theme);
				liste.add(formation);
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
	public List<ListeFormations> findByCriteria(ListeFormations obj)
			throws DAOException {
		ArrayList<ListeFormations> liste = new ArrayList<ListeFormations>();
		ListeFormations formation = null;
		Statement st = null;
		ResultSet res = null;
		String req = "select `id_pformation`, `formation`, `id_org`,"
				+ " `niveau_form`, `datedeb_form`, `datefin_form`,"
				+ " `heure_form`, `actif`, `etat_formation`,"
				+ " `session_form`, `id_themeformation` from "
				+ DAOConstants.t_listeFormations
				+ " where 1=1 ";
		
		if(obj.getId_pformation()!=0)
			req += " and id_pformation="+obj.getId_pformation();
		if (obj.getDatedeb_form()!=null)
			req += " and datedeb_form >='" + obj.getDatedeb_form()+"'  ";
		
		if(obj.getDatefin_form()!=null)
			req += "    and datedeb_form <='" + obj.getDatefin_form()+"'";
		else{
			java.util.Date t=obj.getDatedeb_form();
			long temps=t.getTime();
			//Date test=new Date(temps);
			temps=temps+31536000000l;
			
			req+=" and datefin_form <'" +new Date(temps)+"'  ";
		}
		
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {

				formation = new ListeFormations(res.getInt(1),
						res.getString(2),new OrganismeFormationDAO().findByID(res.getInt(3)),
						res.getString(4), res.getDate(5),
						 res.getDate(6),
						res.getShort(7), res.getBoolean(8),
						res.getString(9),res.getString(10),new ThemeFormationDAO().findByID(res.getInt(11)));
				liste.add(formation);

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
	public int create(ListeFormations obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, obj.getFormation());
			pst.setInt(2, obj.getOrganis().getId_org());
			pst.setString(3, obj.getNiveau());
			pst.setDate(4, (Date) obj.getDatedeb_form());
			pst.setDate(5, (Date) obj.getDatefin_form());
			pst.setShort(6, obj.getHeure_form());
			pst.setBoolean(7, obj.isActif());
			pst.setString(8, obj.getEtat_form());
			pst.setString(9, obj.getSession_form());
			pst.setInt(10, obj.getTheme().getId_themeformation());
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
	public ListeFormations update(ListeFormations obj) throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getId_pformation();

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.setString(1, obj.getFormation());
			pst.setInt(2, obj.getOrganis().getId_org());
			pst.setString(3, obj.getNiveau());
			pst.setDate(4, (Date) obj.getDatedeb_form());
			pst.setDate(5, (Date) obj.getDatefin_form());
			pst.setShort(6, obj.getHeure_form());
			pst.setBoolean(7, obj.isActif());
			pst.setString(8, obj.getEtat_form());
			pst.setString(9, obj.getSession_form());
			pst.setInt(10, obj.getTheme().getId_themeformation());
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
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}
/** affiche toutes les formations  en cours et future	
 * 
 * @return
 * @throws DAOException
 */
	public List<String> afficherListeFormations() throws DAOException {
		List<String> liste = null;
		Statement stat = null;
		ResultSet rs = null;
		String req = null;

		try {

			req = "select formation,id_pformation from "
					+ DAOConstants.t_listeFormations
					+ " where actif=1  and etat_formation in('EN COURS','FUTURE')  order by formation asc";
			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			liste = new ArrayList<String>();
			while (rs.next()) {
				String t = rs.getString("formation") + "("
						+ rs.getInt("id_pformation") + ")";
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
	/**affiche toutes les formations actives	
	 * 
	 * @return
	 * @throws DAOException
	 */
	public List<String> afficherListeToutesFormations() throws DAOException {
		List<String> liste = null;
		Statement stat = null;
		ResultSet rs = null;
		String req = null;

		try {

			req = "select formation,id_pformation from "
					+ DAOConstants.t_listeFormations
					+ " where actif=1  and formation !='AUCUNE'  order by formation asc";
			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			liste = new ArrayList<String>();
			while (rs.next()) {
				String t = rs.getString("formation") + "("
						+ rs.getInt("id_pformation") + ")";
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
	/**affiche l'organisme de formation dont le numero est indiqué en parametre	
	 * 
	 * @param no
	 * @return
	 * @throws DAOException
	 */
	public List<String> afficherOrganismeConcerne(String no)
			throws DAOException {
		if(no.endsWith(")"))
				no=no.substring(0, no.length()-1);
		int nom=Integer.parseInt(no);
		List<String> liste = null;
		Statement stat = null;
		ResultSet rs = null;
		String req = null;

		try {

			req = "select org," + DAOConstants.t_organismesformation
					+ ".id_org from " + DAOConstants.t_organismesformation
					+ " inner join " + DAOConstants.t_listeFormations + " on "
					+ DAOConstants.t_listeFormations + ".id_org="
					+ DAOConstants.t_organismesformation + ".id_org "
					+ "where id_pformation="+ nom ;
			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			liste = new ArrayList<String>();
			while (rs.next()) {
				String t = rs.getString("org") + "(" + rs.getInt("id_org")
						+ ")";
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

	@Override
	public int delete(ListeFormations obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}
	
/** retourne la liste des formations sur laquelle la personne est enregistrée	
 * 
 * @param un
 * @return
 * @throws DAOException
 */
	public List<ListeFormations> pyramideEnregistre(Identite un) throws DAOException {
		String req = " select formation,"+DAOConstants.t_organismesformation+".id_org,niveau_form, datedeb_form,datefin_form"
				+ " from " + DAOConstants.t_preinscription
				+" inner join "+DAOConstants.t_listeFormations
				+" on "+ DAOConstants.t_listeFormations+".id_pformation="
				+DAOConstants.t_preinscription+".id_pformation"
				+" inner join "+DAOConstants.t_organismesformation
				+" on "+ DAOConstants.t_listeFormations+".id_org="
				+DAOConstants.t_organismesformation+".id_org"
				+ " where id_identite=" + un.getId_IDE()
				+ " and enregistre=1 and inscrit=0"+
				" order by datefin_form desc";
		ArrayList<ListeFormations> liste = new ArrayList<ListeFormations>();
		ListeFormations form = null;
		Statement st = null;
		ResultSet res = null;

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {
				form = new ListeFormations(res.getString(1),
						
						new OrganismeFormationDAO().findByID(res.getInt(2)),
						res.getString(3),
						res.getDate(4),res.getDate(5)
						);
				liste.add(form);
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
	
	
	/** retourne la liste des formations sur laquelle la personne est inscrite	
	 * 
	 * @param un
	 * @return
	 * @throws DAOException
	 */
	public List<ListeFormations> pyramideInscrit(Identite un) throws DAOException {
		String req = " select formation,"+DAOConstants.t_organismesformation+".id_org,niveau_form, datedeb_form,datefin_form"
				+ " from " + DAOConstants.t_preinscription
				+" inner join "+DAOConstants.t_listeFormations
				+" on "+ DAOConstants.t_listeFormations+".id_pformation="
				+DAOConstants.t_preinscription+".id_pformation"
				+" inner join "+DAOConstants.t_organismesformation
				+" on "+ DAOConstants.t_listeFormations+".id_org="
				+DAOConstants.t_organismesformation+".id_org"
				+ " where id_identite=" + un.getId_IDE()
				+ " and enregistre=1 and inscrit=1 and dateabandon_formation is null "+
				" order by datefin_form desc";
		ArrayList<ListeFormations> liste = new ArrayList<ListeFormations>();
		ListeFormations form = null;
		Statement st = null;
		ResultSet res = null;

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {
				form = new ListeFormations(res.getString(1),
						new OrganismeFormationDAO().findByID(res.getInt(2)),
						res.getString(3),
						res.getDate(4),res.getDate(5)
						);
				liste.add(form);
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
	
	
	/** retourne la liste des formations abandonnées par  la personne	
	 * 
	 * @param form
	 * @return
	 * @throws DAOException
	 */
	public List<ListeFormations> pyramideAbandon(ListeFormations form) throws DAOException {
		String req = " select formation,"+DAOConstants.t_organismesformation+".id_org,niveau_form, datedeb_form,datefin_form"
				+ " from " + DAOConstants.t_listeFormations
				+" inner join "+DAOConstants.t_organismesformation
				+" on "+ DAOConstants.t_listeFormations+".id_org="
				+DAOConstants.t_organismesformation+".id_org"
				+ " where id_pformation="+form.getId_pformation();
				
		ArrayList<ListeFormations> liste = new ArrayList<ListeFormations>();
		ListeFormations listeform = null;
		Statement st = null;
		ResultSet res = null;

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			if (res.first()) {
				listeform = new ListeFormations(res.getString(1),
						new OrganismeFormationDAO().findByID(res.getInt(2)),
						res.getString(3),
						res.getDate(4),res.getDate(5)
					);
				liste.add(listeform);
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
