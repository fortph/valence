package dao.imp.employeur;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.employeurs.Offre;
import beans.employeurs.PositionnerPersonne;
import beans.identite.Identite;
import beans.parametres.capemploi.Utilisateur;
import beans.parametres.capemploi.UtilisateurDAO;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.imp.identite.IdentiteDAO;
import dao.interfaces.DAO;

public class PositionnerPersonneDAO implements DAO<PositionnerPersonne> {

	final private String query_insert = "insert into "
			+ DAOConstants.t_employeuroffreprop
			+ "( id_offre,id_identite,datecontact_propo,id_salarie,reponse_propo,candidature_retenu)"
			+ " values (?,?,?,?,?,?)";

	final private String query_update = "update "
			+ DAOConstants.t_employeuroffreprop
			+ " set id_offre=?,id_identite=?,datecontact_propo=?,id_salarie=?,reponse_propo=?,"
			+ "candidature_retenu=? where id_propo=";

	final private String query_valide = "update "
			+ DAOConstants.t_employeuroffreprop
			+ " set candidature_retenu=? where id_offre=? and id_identite=?";

	private Connection connect = null;

	public PositionnerPersonneDAO() {
		connect = DBConnexion.getConnexion();

	}

	@Override
	public PositionnerPersonne findByID(int id) throws DAOException {
		String query = "select  `id_propo`, `id_offre`, `id_identite`,"
				+ " `datecontact_propo`, `id_salarie`, `reponse_propo`,"
				+ " `candidature_retenu` from " + DAOConstants.t_employeuroffreprop
				+ " where  id_propo=" + id;

		PositionnerPersonne contact = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				contact = new PositionnerPersonne(id,
						new OffreDAO().findByID(res.getInt(2)),
						new IdentiteDAO().findByID(res.getInt(3)),
						res.getDate(4), new UtilisateurDAO().findByID(res
								.getInt(5)), res.getString(6),
						res.getBoolean(7));

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

		return contact;

	}

	@Override
	public List<PositionnerPersonne> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	/**liste toutes les personnes positionnees sur l'offre dont l'id est indiqué
	 * 
	 * @param num
	 * @return
	 * @throws DAOException
	 */
	 public List<PositionnerPersonne> personnesPositionneesParOffre(int num)
			throws DAOException {
		List<PositionnerPersonne> liste = new ArrayList<PositionnerPersonne>();
		PositionnerPersonne positionnee = null;
		Statement stat = null;
		ResultSet res = null;
		String req =  "select  `id_propo`, `id_offre`, `id_identite`,"
				+ " `datecontact_propo`, `id_salarie`, `reponse_propo`,"
				+ " `candidature_retenu` from "
				+ DAOConstants.t_employeuroffreprop
				+ " where id_offre=" + num+" order by datecontact_propo asc";

		try {

			stat = connect.createStatement();
			res = stat.executeQuery(req);

			OffreDAO ofdao = new OffreDAO();
			Offre offre = null;
			IdentiteDAO iddao = new IdentiteDAO();
			Identite identite = null;
			UtilisateurDAO utildao = new UtilisateurDAO();
			Utilisateur utilisateur = null;

			while (res.next()) {
				offre = ofdao.findByID(res.getInt(2));
				identite = iddao.findByID(res.getInt(3));
				utilisateur = utildao.findByID(res.getInt(5));

				positionnee = new PositionnerPersonne(res.getInt(1), offre,
						identite, res.getDate(4), utilisateur,
						res.getString(6), res.getBoolean(7));
				liste.add(positionnee);
			}

		} catch (SQLException ex) {
			System.out.print("Problème " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return liste;

	}
	
	/**nombre de personnes positionnees sur une offre	
	 * 
	 * @param num
	 * @return
	 * @throws DAOException
	 */
	public int nombrePersonnesPositionneesParOffre(int num)
			throws DAOException {
		int nombre=0;
		Statement stat = null;
		ResultSet res = null;
		String req = "select count(*) from " + DAOConstants.t_employeuroffreprop
				+ " where id_offre=" + num;

		try {

			stat = connect.createStatement();
			res = stat.executeQuery(req);

			
			if (res.first()) {
				nombre=res.getInt(1);
				
			}

		} catch (SQLException ex) {
			System.out.print("Problème " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return nombre;

	}


	
	public int totalOffresPositionneesRetenues(java.util.Date deb, java.util.Date fin)
			throws DAOException {
		String query = "select count(id_identite) from " + DAOConstants.t_offre
				+ " inner join "+
				DAOConstants.t_employeuroffreprop
				+ " on "+
				 DAOConstants.t_offre+
				 ".id_offre="+
				 DAOConstants.t_employeuroffreprop+
				 ".id_offre where datedeb_offre between '" + deb + "' and '" + fin+
				 "' and candidature_retenu=1";
		Statement st = null;
		ResultSet res = null;
		int num = 0;

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				num = res.getInt(1);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}
		return num;
	}
	
	
	public int totalOffresPositionnees(java.util.Date deb, java.util.Date fin)
			throws DAOException {
		String query = "select count(id_identite) from " + DAOConstants.t_offre
				+ " inner join "+
				DAOConstants.t_employeuroffreprop
				+ " on "+
				 DAOConstants.t_offre+
				 ".id_offre="+
				 DAOConstants.t_employeuroffreprop+
				 ".id_offre where datedeb_offre between '" + deb + "' and '" + fin+"'";
		Statement st = null;
		ResultSet res = null;
		int num = 0;

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				num = res.getInt(1);
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}
		return num;
	}
	
	
	@Override
	public List<PositionnerPersonne> findByCriteria(PositionnerPersonne obj)
			throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(PositionnerPersonne obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);

			pst.setInt(1, obj.getOffre().getId_offre());
			pst.setInt(2, obj.getIdentite().getId_IDE());
			pst.setDate(3, (Date) obj.getDatecontact());
			pst.setInt(4, obj.getSalarie().getId_salarie());
			pst.setString(5, obj.getReponse());
			pst.setBoolean(6, obj.isRetenu());
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
	public PositionnerPersonne update(PositionnerPersonne obj)
			throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getId_propo();

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);

			pst.setInt(1, obj.getOffre().getId_offre());
			pst.setInt(2, obj.getIdentite().getId_IDE());
			pst.setDate(3, (Date) obj.getDatecontact());
			pst.setInt(4, obj.getSalarie().getId_salarie());
			pst.setString(5, obj.getReponse());
			pst.setBoolean(6, obj.isRetenu());
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
	public int delete(PositionnerPersonne obj) throws DAOException {
		return 0;
	}

	/** suppression du positionnement d'une personne sur une offre 	
	 * 
	 * @param id
	 * @param pers
	 * @return
	 * @throws DAOException
	 */
	public int supprimerPersonneOffre(int id, int pers) throws DAOException {
		String query_delete = "delete from "
				+ DAOConstants.t_employeuroffreprop
				+ " where id_offre="+id+" and id_identite="+pers;
		Statement st = null;
		int val = 0;
		try {

			st = connect.createStatement();
			val=st.executeUpdate(query_delete);
			
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			
			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return val;

	}
	
	/** suppression du positionnement d'une personne sur une offre 	
	 * 
	 * @param id
	 * @param pers
	 * @param url
	 * @return
	 * @throws DAOException
	 */
	public int validerPersonneOffre(int id, int pers,String url) throws DAOException {
		PreparedStatement pst = null;
		int valide=0;
		if (url.equals("true"))
			valide=0;
		else if(url.equals("false"))
			valide=1;
			
		int nb = 0;
		try {

			pst = connect.prepareStatement(query_valide);
			pst.setInt(1, valide);
			pst.setInt(2, id);
			pst.setInt(3, pers);
			nb=pst.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			DAOUtil.closePrepareStatement(pst);
		}

		return nb;

	}


	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
