package dao.imp.sap;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.employeurs.Employeur;
import beans.identite.Identite;
import beans.sap.PrestationCDI;
import beans.sap.TachesSAP;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.imp.employeur.EmployeurDAO;
import dao.imp.identite.IdentiteDAO;
import dao.interfaces.DAO;

public class PrestationCDIDAO implements DAO<PrestationCDI> {
	

	private Connection connect = null;

	final private String query_insert = "insert into "
			+ DAOConstants.t_sapprestation
			+ "(id_employeur,id_identite,id_tache,heuresminimois_pr,"
			+ "salairehor_pr,panier_pr,deplacement_pr,facsalairehor_pr,commentaire_pr,"
			+ "redaction_pr,datedebut_pr,datefin_pr,agrementsap_pr,directeurcapemploi_pr,"
			+ "daterenouvel_pr,heurescontratsalarie)"
			+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	final private String query_update = "update "
			+ DAOConstants.t_sapprestation
			+ " set id_employeur=?,id_identite=?,id_tache=?,heuresminimois_pr=?,"
			+ "salairehor_pr=?,panier_pr=?,deplacement_pr=?,"
			+ "facsalairehor_pr=?,commentaire_pr=?,"
			+ "redaction_pr=?,datedebut_pr=?,datefin_pr=?,agrementsap_pr=?,directeurcapemploi_pr=?,"
			+ "daterenouvel_pr=? ,heurescontratsalarie=? "
			+ " where id_prestationcontrat=";

	public PrestationCDIDAO() {
		connect = DBConnexion.getConnexion();
	}

	@Override
	public PrestationCDI findByID(int id) throws DAOException {
		String query = "select `id_prestationcontrat`, `id_employeur`, `id_identite`,"
				+ " `id_tache`, `heuresminimois_pr`, `salairehor_pr`, `panier_pr`,"
				+ " `deplacement_pr`, `facsalairehor_pr`, `commentaire_pr`,"
				+ " `redaction_pr`, `datedebut_pr`, `datefin_pr`, `agrementsap_pr`,"
				+ " `directeurcapemploi_pr`, `daterenouvel_pr`,"
				+ " `heurescontratsalarie` from " + DAOConstants.t_sapprestation
				+ " where id_prestationcontrat=" + id;
		PrestationCDI lf = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {

				lf = new PrestationCDI(id, new EmployeurDAO().findByID(res
						.getInt(2)), new IdentiteDAO().findByID(res.getInt(3)),
						new TachesSAPDAO().findByID(res.getInt(4)),
						res.getInt(5), res.getFloat(6),
						res.getFloat(7), res.getFloat(8),res.getFloat(9), res.getString(10),
						res.getDate(11), res.getDate(12), res.getDate(13),
						res.getString(14), res.getString(15),res.getDate(16),res.getInt(17));
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
	
	public PrestationCDI recupereDernierePrestaion(Identite ident) throws DAOException {
		String query = "select max(id_prestationcontrat) as maxi from " + DAOConstants.t_sapprestation
				+ " where id_identite="+ident.getId_IDE();
		PrestationCDI lf = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {

				lf = this.findByID(res.getInt(1));
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
	public List<PrestationCDI> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PrestationCDI> findByCriteria(PrestationCDI obj)
			throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(PrestationCDI obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);

			pst.setInt(1, obj.getEmployeur().getId_employeur());
			pst.setInt(2, obj.getIdentite().getId_IDE());
			pst.setInt(3, obj.getTache().getId_tache());
			pst.setInt(4, obj.getHeuresminimois_pr());			
			pst.setFloat(5, obj.getSalairehor_pr());
			pst.setFloat(6, obj.getPanier_pr());
			pst.setFloat(7, obj.getDeplacement_pr());
			pst.setFloat(8, obj.getFacsalairehor_pr());			
			pst.setString(9, obj.getCommentaire_pr());			
			pst.setDate(10, (Date) obj.getRedaction_pr());
			pst.setDate(11, (Date) obj.getDatedebut_pr());
			pst.setDate(12, (Date) obj.getDatefin_pr());
			pst.setString(13, obj.getAgrementsap_pr());
			pst.setString(14, obj.getDirecteurcapemploi_pr());
			pst.setDate(15, (Date) obj.getDaterenouvel());
			pst.setInt(16,obj.getHeurescontrat());

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
	public PrestationCDI update(PrestationCDI obj) throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getId_prestationcontrat();

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.setInt(1, obj.getEmployeur().getId_employeur());
			pst.setInt(2, obj.getIdentite().getId_IDE());
			pst.setInt(3, obj.getTache().getId_tache());
			pst.setInt(4, obj.getHeuresminimois_pr());			
			pst.setFloat(5, obj.getSalairehor_pr());
			pst.setFloat(6, obj.getPanier_pr());
			pst.setFloat(7, obj.getDeplacement_pr());
			pst.setFloat(8, obj.getFacsalairehor_pr());			
			pst.setString(9, obj.getCommentaire_pr());			
			pst.setDate(10, (Date) obj.getRedaction_pr());
			pst.setDate(11, (Date) obj.getDatedebut_pr());
			pst.setDate(12, (Date) obj.getDatefin_pr());
			pst.setString(13, obj.getAgrementsap_pr());
			pst.setString(14, obj.getDirecteurcapemploi_pr());
			pst.setDate(15, (Date) obj.getDaterenouvel());
			pst.setInt(16,obj.getHeurescontrat());
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
	public int delete(PrestationCDI obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**liste des emplois occupes par une personne*/
	public List<PrestationCDI> listePrestationsPersonne(Identite identite)
			throws DAOException {
		List<PrestationCDI> liste = new ArrayList<PrestationCDI>();
		Statement st = null;
		ResultSet rs = null;
		PrestationCDI prest = null;
		EmployeurDAO empdao = new EmployeurDAO();
	
		TachesSAPDAO tacdao = new TachesSAPDAO();
		
		String sql = "SELECT id_prestationcontrat,id_employeur,id_tache,heuresminimois_pr,"
				+ "datedebut_pr,datefin_pr FROM "
				+ DAOConstants.t_sapprestation
				+ " where id_identite="
				+ identite.getId_IDE();

		try {
			st = connect.createStatement();

			// Ex�cution de la requ�te et r�cup�ration du r�sultat dans un
			// resultset
			rs = st.executeQuery(sql);

			// TANT QUE la fin du resultset n'est pas atteinte
			while (rs.next()) {
				prest = new PrestationCDI(rs.getInt(1), empdao.findByID(rs
						.getInt(2)), tacdao.findByID(rs.getInt(3)),
						rs.getInt(4), rs.getDate(5), rs.getDate(6));
				liste.add(prest);
			}
		} catch (SQLException ex) {
			System.out.print("Probléme " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}
		return liste;
	}
	
	public List<PrestationCDI> listePrestationsParEmployeur(Employeur employeur)
			throws DAOException {
		List<PrestationCDI> liste = new ArrayList<PrestationCDI>();
		Statement st = null;
		ResultSet rs = null;
		PrestationCDI prest = null;
		TachesSAPDAO tacdao = new TachesSAPDAO();
		
		String sql = "SELECT id_prestationcontrat,id_identite,id_tache,heuresminimois_pr,"
				+ "datedebut_pr,datefin_pr,redaction_pr FROM "
				+ DAOConstants.t_sapprestation
				+ " where id_employeur="
				+ employeur.getId_employeur();

		try {
			st = connect.createStatement();

			// Ex�cution de la requ�te et r�cup�ration du r�sultat dans un
			// resultset
			rs = st.executeQuery(sql);

			// TANT QUE la fin du resultset n'est pas atteinte
			while (rs.next()) {
				prest = new PrestationCDI(rs.getInt(1), new IdentiteDAO().findByID(rs.getInt(2)),
						tacdao.findByID(rs.getInt(3)),
						rs.getInt(4), rs.getDate(5), rs.getDate(6),rs.getDate(7));
				liste.add(prest);
			}
		} catch (SQLException ex) {
			System.out.print("Probléme " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}
		return liste;
	}
	
	/** liste les prestations employeurs encore valides */
	public List<PrestationCDI> listePrestationsParEmployeurEnCours()
			throws DAOException {
		List<PrestationCDI> liste = new ArrayList<PrestationCDI>();
		Statement st = null;
		ResultSet rs = null;
		PrestationCDI prest = null;
		TachesSAPDAO tacdao = new TachesSAPDAO();
		
		String sql = "SELECT "
				+ DAOConstants.t_sapprestation
				+".id_prestationcontrat,id_employeur,id_identite,"
				+ DAOConstants.t_sapprestation
				+ ".id_tache,"
				+ DAOConstants.t_sapprestation
				+ ".heuresminimois_pr,"
				+ DAOConstants.t_sapprestation
				+ ".datedebut_pr,"
				+ DAOConstants.t_sapprestation
				+ ".datefin_pr,"
				+ DAOConstants.t_sapprestation
				+ ".redaction_pr FROM "
				+ DAOConstants.t_sapprestation
				+" where "
				+ DAOConstants.t_sapprestation
				+".datefin_pr >= curdate()";

		try {
			st = connect.createStatement();

			// Ex�cution de la requ�te et r�cup�ration du r�sultat dans un
			// resultset
			rs = st.executeQuery(sql);

			// TANT QUE la fin du resultset n'est pas atteinte
			while (rs.next()) {
				prest = new PrestationCDI(rs.getInt(1),
						new EmployeurDAO().findByID(rs.getInt(2)) ,
						new IdentiteDAO().findByID(rs.getInt(3)),
						tacdao.findByID(rs.getInt(4)),
						rs.getInt(5), rs.getDate(6), rs.getDate(7),rs.getDate(8));
				liste.add(prest);
			}
		} catch (SQLException ex) {
			System.out.print("Probléme " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}
		return liste;
	}
	
	
	public List<PrestationCDI> listePrestationsParEmployeurEtPersonne(Employeur employeur,Identite identite)
			throws DAOException {
		List<PrestationCDI> liste = new ArrayList<PrestationCDI>();
		Statement st = null;
		ResultSet rs = null;
		PrestationCDI prest = null;
		TachesSAPDAO tacdao = new TachesSAPDAO();
		
		String sql = "SELECT id_prestationcontrat,id_tache,heuresminimois_pr,"
				+ "datedebut_pr,datefin_pr FROM "
				+ DAOConstants.t_sapprestation
				+ " where id_employeur="
				+ employeur.getId_employeur()
				+" and id_identite="
				+identite.getId_IDE();

		try {
			st = connect.createStatement();

			// Ex�cution de la requ�te et r�cup�ration du r�sultat dans un
			// resultset
			rs = st.executeQuery(sql);

			// TANT QUE la fin du resultset n'est pas atteinte
			while (rs.next()) {
				prest = new PrestationCDI(rs.getInt(1),
						tacdao.findByID(rs.getInt(2)),
						rs.getInt(3), rs.getDate(4), rs.getDate(5));
				liste.add(prest);
			}
		} catch (SQLException ex) {
			System.out.print("Probléme " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}
		return liste;
	}
	
	
	public List<PrestationCDI> listePrestationsParPersonne(Identite identite)
			throws DAOException {
		List<PrestationCDI> liste = new ArrayList<PrestationCDI>();
		Statement st = null;
		ResultSet rs = null;
		PrestationCDI prest = null;
		TachesSAPDAO tacdao = new TachesSAPDAO();
		EmployeurDAO empdao=new EmployeurDAO();
		String sql = "SELECT id_prestationcontrat,id_tache,id_employeur,heuresminimois_pr,"
				+ "datedebut_pr,datefin_pr,redaction_pr FROM "
				+ DAOConstants.t_sapprestation
				+ " where id_identite="
				+identite.getId_IDE();

		try {
			st = connect.createStatement();

			// Ex�cution de la requ�te et r�cup�ration du r�sultat dans un
			// resultset
			rs = st.executeQuery(sql);

			// TANT QUE la fin du resultset n'est pas atteinte
			while (rs.next()) {
				prest = new PrestationCDI(rs.getInt(1),
						tacdao.findByID(rs.getInt(2)),empdao.findByID(rs.getInt(3)),
						rs.getInt(4), rs.getDate(5), rs.getDate(6),rs.getDate(7));
				liste.add(prest);
			}
		} catch (SQLException ex) {
			System.out.print("Probléme " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}
		return liste;
	}
/** liste toutes les prestations et avenants pour lesquelles le contrat se termine dans la fourchette de dates indiquée*/
	
	public List<Integer> listePrestationsTermineDansFourchette(Date deb,Date fin)
			throws DAOException {
		List<Integer> liste = new ArrayList<Integer>();
		Statement st = null;
		ResultSet rs = null;
		/*
		 *PrestationCDI prest = null;
				TachesSAPDAO tacdao = new TachesSAPDAO();
		EmployeurDAO empdao=new EmployeurDAO();
		*/
		String sql = "SELECT id_prestationcontrat FROM "
				+ DAOConstants.t_sapprestation
				+" where "
				+ DAOConstants.t_sapprestation
				+".datefin_pr between '"+deb+"' and '"+fin+"'";
				

		try {
			st = connect.createStatement();

			// Ex�cution de la requ�te et r�cup�ration du r�sultat dans un
			// resultset
			rs = st.executeQuery(sql);

			// TANT QUE la fin du resultset n'est pas atteinte
			while (rs.next()) {
				
				liste.add(rs.getInt(1));
			}
		} catch (SQLException ex) {
			System.out.print("Probléme " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}
		return liste;
	}


/** liste toutes les prestations et avenants pour lesquelles le contrat se termine 
 * dans la fourchette de dates indiquée et pour lequel la date de retour est nulle*/
	
	public List<Integer> listePrestationsTermineDansFourchetteSansRetour(Date deb,Date fin)
			throws DAOException {
		List<Integer> liste = new ArrayList<Integer>();
		Statement st = null;
		ResultSet rs = null;
		/*
		 *PrestationCDI prest = null;
				TachesSAPDAO tacdao = new TachesSAPDAO();
		EmployeurDAO empdao=new EmployeurDAO();
		*/
		String sql = "SELECT id_prestationcontrat FROM "
				+ DAOConstants.t_sapprestation
				+" where "
				+ DAOConstants.t_sapprestation
				+".datefin_pr between '"+deb+"' and '"+fin+"'"
				+" and daterenouvel_pr is null";
				

		try {
			st = connect.createStatement();

			// Ex�cution de la requ�te et r�cup�ration du r�sultat dans un
			// resultset
			rs = st.executeQuery(sql);

			// TANT QUE la fin du resultset n'est pas atteinte
			while (rs.next()) {
				
				liste.add(rs.getInt(1));
			}
		} catch (SQLException ex) {
			System.out.print("Probléme " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}
		return liste;
	}

	/*h permanents*/
	/**
	 * listing salaries dont la prestation est en cours
	 * @param d
	 * @return
	 * @throws DAOException
	 */
	public List<PrestationCDI> listingContratCDIEnCours(Date d,Date deux)
			throws DAOException {
		ArrayList<PrestationCDI> liste = new ArrayList<PrestationCDI>();
		PrestationCDI prestation=null;		
		TachesSAP tache=null;
		Employeur employeur = null;
		Identite identite=null;
		Statement st = null;
		ResultSet res = null;
		String req = " SELECT distinct(id_prestationcontrat) as c,"
				+ DAOConstants.t_sapprestation
				+".id_identite,id_employeur,id_tache,heuresminimois_pr, "
				+ "salairehor_pr,facsalairehor_pr,panier_pr,deplacement_pr, "
				+ "commentaire_pr,datedebut_pr,heurescontratsalarie FROM " 
				+ DAOConstants.t_sapprestation
				+" inner join "
				+DAOConstants.t_identite
				+" on "
				+ DAOConstants.t_sapprestation
				+".id_identite="
				+DAOConstants.t_identite
				+".id_identite"
				+ " where (datefin_pr is null or datefin_pr >'"+d
				+"') and datedebut_pr <='"+deux
				+"' order by nom_identite asc";

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {
				TachesSAPDAO serdao=new TachesSAPDAO();
				tache=serdao.findByID(res.getInt(4));
				EmployeurDAO empdao = new EmployeurDAO();
				employeur = empdao.findByID(res.getInt(3));
				IdentiteDAO idao=new IdentiteDAO();
				 identite=idao.findByID(res.getInt(2));
				

				 prestation = new PrestationCDI(res.getInt(1),identite,employeur,tache,res.getInt(5),
						res.getFloat(6),res.getFloat(7),res.getFloat(8),res.getFloat(9),res.getString(10),
						res.getDate(11),res.getInt(12));
				liste.add(prestation);

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
	
	
	public PrestationCDI dernierContratCDIEnCours(Identite identite,Employeur empl)
			throws DAOException {
		PrestationCDI retour = null;
		String query = "select max(id_prestationcontrat) from "
				+ DAOConstants.t_sapprestation + " where id_identite="
				+ identite.getId_IDE()
				+" and id_employeur="+empl.getId_employeur();
				

		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				retour = this.findByID(res.getInt(1));
			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return retour;

	}
	


}
