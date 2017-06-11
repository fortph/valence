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
import beans.sap.AvenantPrestationCDI;
import beans.sap.PrestationCDI;
import beans.sap.TachesSAP;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.interfaces.DAO;

public class AvenantPrestationCDIDAO implements DAO<AvenantPrestationCDI> {

	private Connection connect = null;

	final private String query_insert = "insert into "
			+ DAOConstants.t_sapprestationaven
			+ "(rangavenant,id_prestationcontrat,id_tache,heuresminimois_pres,salairehor_av,"
			+ "panier_av,deplacement_av,facsalairehor_av,commentaire_pr,"
			+ "redaction_pr,datedebut_pr,datefin_pr,daterenouvel_av, heurescontratsalarie)"
			+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	final private String query_update = "update "
			+ DAOConstants.t_sapprestationaven
			+ " set rangavenant=?,id_prestationcontrat=?,id_tache=?,heuresminimois_pres=?,salairehor_av=?,"
			+ "panier_av=?,deplacement_av=?,facsalairehor_av=?,commentaire_pr=?,"
			+ "redaction_pr=?,datedebut_pr=?,datefin_pr=?,daterenouvel_av=?,heurescontratsalarie=? "
			+ " where id_prestationavenant=";

	public AvenantPrestationCDIDAO() {
		connect = DBConnexion.getConnexion();
	}

	@Override
	public AvenantPrestationCDI findByID(int id) throws DAOException {
		String query = "select  `id_prestationavenant`, `rangavenant`, "
				+ "`id_prestationcontrat`, `id_tache`,"
				+ " `heuresminimois_pres`, `salairehor_av`, `panier_av`,"
				+ " `deplacement_av`, `facsalairehor_av`, `commentaire_pr`, "
				+ "`redaction_pr`, `datedebut_pr`, `datefin_pr`,"
				+ " `daterenouvel_av`, `heurescontratsalarie` from " 
				+ DAOConstants.t_sapprestationaven
				+ " where id_prestationavenant=" + id;
		AvenantPrestationCDI lf = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {

				lf = new AvenantPrestationCDI(id, res.getInt(2),
						new PrestationCDIDAO().findByID(res.getInt(3)),
						new TachesSAPDAO().findByID(res.getInt(4)),
						res.getInt(5), res.getFloat(6),res.getFloat(7),res.getFloat(8), res.getFloat(9),
						res.getString(10), res.getDate(11), res.getDate(12),
						res.getDate(13),res.getDate(14),res.getInt(15));
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
	public List<AvenantPrestationCDI> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AvenantPrestationCDI> findByCriteria(AvenantPrestationCDI obj)
			throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(AvenantPrestationCDI obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, obj.getRangavenant());
			pst.setInt(2, obj.getPrestation().getId_prestationcontrat());
			pst.setInt(3, obj.getTache().getId_tache());
			pst.setInt(4, obj.getHeuresminimois_pres());
			pst.setFloat(5, obj.getSalairehor_av());
			pst.setFloat(6, obj.getPanier_av());
			pst.setFloat(7, obj.getDeplacement_av());
			pst.setFloat(8, obj.getFacsalairehor_pr());
			pst.setString(9, obj.getCommentaire());
			pst.setDate(10, (Date) obj.getRedaction_pr());
			pst.setDate(11, (Date) obj.getDatedebut_pr());
			pst.setDate(12, (Date) obj.getDatefin_pr());
			pst.setDate(13, (Date) obj.getDaterenouvel());
			pst.setInt(14, obj.getHeurescontrat());

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
	public AvenantPrestationCDI update(AvenantPrestationCDI obj)
			throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getId_prestationavenant();

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.setInt(1, obj.getRangavenant());
			pst.setInt(2, obj.getPrestation().getId_prestationcontrat());
			pst.setInt(3, obj.getTache().getId_tache());
			pst.setInt(4, obj.getHeuresminimois_pres());
			pst.setFloat(5, obj.getSalairehor_av());
			pst.setFloat(6, obj.getPanier_av());
			pst.setFloat(7, obj.getDeplacement_av());
			pst.setFloat(8, obj.getFacsalairehor_pr());
			pst.setString(9, obj.getCommentaire());
			pst.setDate(10, (Date) obj.getRedaction_pr());
			pst.setDate(11, (Date) obj.getDatedebut_pr());
			pst.setDate(12, (Date) obj.getDatefin_pr());
			pst.setDate(13, (Date) obj.getDaterenouvel());
			pst.setInt(14, obj.getHeurescontrat());
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
	public int delete(AvenantPrestationCDI obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	/** 
	 * liste tous les avenants d'un prestation SAP 
	 * @param cont
	 * @return
	 * @throws DAOException
	 */
	public List<AvenantPrestationCDI> listeAvenantPrestationCDI(
			PrestationCDI cont) throws DAOException {
		List<AvenantPrestationCDI> liste = new ArrayList<AvenantPrestationCDI>();
		Statement st = null;
		ResultSet rs = null;
		AvenantPrestationCDI avenant = null;
		int idaiprestation = cont.getId_prestationcontrat();
		String sql = "SELECT id_prestationavenant,rangavenant,id_tache,"
				+ "heuresminimois_pres,redaction_pr,datedebut_pr,datefin_pr,"
				+ "daterenouvel_av   FROM "
				+ DAOConstants.t_sapprestationaven
				+ " where id_prestationcontrat=" + idaiprestation;
		TachesSAPDAO tacdao = new TachesSAPDAO();
		
		try {
			st = connect.createStatement();
			rs = st.executeQuery(sql);

			// TANT QUE la fin du resultset n'est pas atteinte
			while (rs.next()) {
				avenant = new AvenantPrestationCDI(rs.getInt(1), rs.getInt(2),
						tacdao.findByID(rs.getInt(3)),  rs.getInt(4), rs.getDate(5),
						rs.getDate(6), rs.getDate(7),rs.getDate(8));
				liste.add(avenant);
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

	/** 
	 * recupere date de fin du dernier avenant d'un prestation SAP 
	 * @param cont
	 * @return
	 * @throws DAOException
	 */
	public Date dernierAvenantPrestationCDI(PrestationCDI cont)
			throws DAOException {
		Date dernier = null;
		Statement st = null;
		ResultSet rs = null;
		int nbavenants = this.existeAvenantPrestationCDI(cont);

		String sql = "SELECT  datefin_pr FROM "
				+ DAOConstants.t_sapprestationaven
				+ " where id_prestationavenant=("
				+ " select max(id_prestationavenant) from "
				+ DAOConstants.t_sapprestationaven
				+ " where id_prestationcontrat="
				+ cont.getId_prestationcontrat() + ")";

		String sql2 = "SELECT  datefin_pr FROM " + DAOConstants.t_sapprestation
				+ " where  id_prestationcontrat="
				+ cont.getId_prestationcontrat();
		// s'il y a des avenants a la prestation on recupere la date du dernier
		// avenant
		String req = "";
		if (nbavenants > 0)
			req = sql;
		// sinon on recupere la date de fin de la prestation
		else
			req = sql2;

		try {
			st = connect.createStatement();
			rs = st.executeQuery(req);

			// TANT QUE la fin du resultset n'est pas atteinte
			if (rs.first()) {
				dernier = rs.getDate(1);
			}

		} catch (SQLException ex) {
			System.out.print("Probléme " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}
		return dernier;
	}

	/** 
	 * verifie s'il existe des avenants pour la prestation SAP 
	 * @param cont
	 * @return
	 * @throws DAOException
	 */
	public int existeAvenantPrestationCDI(PrestationCDI cont)
			throws DAOException {

		Statement st = null;
		ResultSet rs = null;
		int nombre = 0;
		String sql = "SELECT count(id_prestationavenant) FROM "
				+ DAOConstants.t_sapprestationaven
				+ " where id_prestationcontrat="
				+ cont.getId_prestationcontrat();

		try {
			st = connect.createStatement();
			rs = st.executeQuery(sql);

			if (rs.first()) {
				nombre = rs.getInt(1);
			}
		} catch (SQLException ex) {
			System.out.print("Probléme " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}
		return nombre;
	}

	/** 
	 * liste tous les avenants d'un prestation SAP pour une personne 
	 * @param ident
	 * @return
	 * @throws DAOException
	 */
	public List<AvenantPrestationCDI> listeAvenantPrestationCDIParPersonne(
			Identite ident) throws DAOException {
		List<AvenantPrestationCDI> liste = new ArrayList<AvenantPrestationCDI>();
		Statement st = null;
		ResultSet rs = null;
		AvenantPrestationCDI avenant = null;

		String sql = "SELECT id_prestationavenant,rangavenant,"
				+ DAOConstants.t_sapprestationaven 
				+ ".id_prestationcontrat,"
				+ DAOConstants.t_sapprestationaven 
				+".id_tache,"
				+ "heuresminimois_pres,"
				+ DAOConstants.t_sapprestationaven 
				+".datedebut_pr,"
				+ DAOConstants.t_sapprestationaven 
				+".datefin_pr,"
				+ DAOConstants.t_sapprestationaven 
				+ ".redaction_pr FROM " 
				+ DAOConstants.t_sapprestationaven 
				+" inner join "
				+DAOConstants.t_sapprestation
				+ " on "
				+DAOConstants.t_sapprestationaven
				+".id_prestationcontrat="
				+DAOConstants.t_sapprestation
				+".id_prestationcontrat "
				+ " where " + DAOConstants.t_sapprestation
				+ ".id_identite=" + ident.getId_IDE();
		TachesSAPDAO tacdao = new TachesSAPDAO();
		PrestationCDIDAO prestdao = new PrestationCDIDAO();

		try {
			st = connect.createStatement();
			rs = st.executeQuery(sql);

			// TANT QUE la fin du resultset n'est pas atteinte
			while (rs.next()) {
				avenant = new AvenantPrestationCDI(rs.getInt(1), rs.getInt(2),
						prestdao.findByID(rs.getInt(3)), tacdao.findByID(rs
								.getInt(4)), rs.getInt(5), rs.getDate(6),
						rs.getDate(7), rs.getDate(8));
				liste.add(avenant);
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
	/*
	public List<AvenantPrestationCDI> listeAvenantPrestationCDIParPersonnePrestation(
			Identite ident,PrestationCDI prest) throws DAOException {
		List<AvenantPrestationCDI> liste = new ArrayList<AvenantPrestationCDI>();
		Statement st = null;
		ResultSet rs = null;
		AvenantPrestationCDI avenant = null;

		String sql = "SELECT id_prestationavenant,rangavenant,"
				+ DAOConstants.t_sapprestationaven + ".id_prestationcontrat,"
				+ DAOConstants.t_sapprestationaven + ".id_tache,"
				+ "heuresminimois_pres," + DAOConstants.t_sapprestationaven
				+ ".datedebut_pr," + DAOConstants.t_sapprestationaven
				+ ".datefin_pr," + DAOConstants.t_sapprestationaven
				+ ".redaction_pr FROM " + DAOConstants.t_sapprestationaven
				+ " inner join " + DAOConstants.t_sapprestation + " on "
				+ DAOConstants.t_sapprestationaven + ".id_prestationcontrat="
				+ DAOConstants.t_sapprestation + ".id_prestationcontrat "
				+ " where " + DAOConstants.t_sapprestation
				+ ".id_identite=" + ident.getId_IDE()
				+ " and " 							//rajouté le 6/08/2014
				+DAOConstants.t_sapprestation
				+"id_prestationcontrat="
				+prest.getId_prestationcontrat();
		TachesSAPDAO tacdao = new TachesSAPDAO();
		PrestationCDIDAO prestdao = new PrestationCDIDAO();

		try {
			st = connect.createStatement();
			rs = st.executeQuery(sql);

			// TANT QUE la fin du resultset n'est pas atteinte
			while (rs.next()) {
				avenant = new AvenantPrestationCDI(rs.getInt(1), rs.getInt(2),
						prestdao.findByID(rs.getInt(3)), tacdao.findByID(rs
								.getInt(4)), rs.getInt(5), rs.getDate(6),
						rs.getDate(7), rs.getDate(8));
				liste.add(avenant);
			}
		} catch (SQLException ex) {
			System.out.print("Probléme " + ex);
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return liste;
	}
*/
	/** 
	 * retourne le numero du prochain avenant pour la prestation donnée 
	 * @param cont
	 * @return
	 * @throws DAOException
	 */
	public int recupereNumeroAvenantPrestation(PrestationCDI cont)
			throws DAOException {
		int num = 0;
		String query = "select max(rangavenant) from "
				+ DAOConstants.t_sapprestationaven
				+ " where  id_prestationcontrat="
				+ cont.getId_prestationcontrat();
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				num = res.getInt(1);
			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return num + 1;

	}
	
	
/** 
 * liste tous les avenants pour lesquelles le contrat se termine dans la fourchette de dates indiquée
 * @param deb
 * @param fin
 * @return
 * @throws DAOException
 */
	
	public List<Integer> listeAvenantsPrestationsTermineDansFourchette(Date deb,Date fin)
			throws DAOException {
		List<Integer> liste = new ArrayList<Integer>();
		Statement st = null;
		ResultSet rs = null;
		/*
		 *PrestationCDI prest = null;
				TachesSAPDAO tacdao = new TachesSAPDAO();
		EmployeurDAO empdao=new EmployeurDAO();
		*/
		String sql = "SELECT id_prestationavenant FROM "
				+ DAOConstants.t_sapprestationaven
				+" where "
				+ DAOConstants.t_sapprestationaven
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

	/**
	 * liste toutes les prestations pour lesquelles aucun avenant n'est etabli
	 * sur la periode en cours
	 */
	public List<Integer> listePrestationCDIParPeriode
	(Date a, Date b,List<Integer> listeprest) throws DAOException {
		List<Integer> liste = new ArrayList<Integer>();
		for(int i=0;i<listeprest.size();i++){
			liste.add(listeprest.get(i));
		}
		Statement st = null;
		ResultSet rs = null;
		//PrestationCDI prestationencours=null;
		//on récupere les id des prestations se terminant sur la periode
		
		System.out.println("liste ="+liste);
		String  maliste=liste.toString().replace("[", " ");
		maliste=maliste.replace("]", " ");
		System.out.println(maliste);
		String sql = "SELECT  id_prestationcontrat from "
				+DAOConstants.t_sapprestationaven
				+" where id_prestationcontrat in ("+maliste+") "
				+" and datefin_pr >='"+b+ "' group by id_prestationcontrat";
		
		
		try {
			st = connect.createStatement();
			rs = st.executeQuery(sql);

			// TANT QUE la fin du resultset n'est pas atteinte
			while (rs.next()) {
				int pos=liste.indexOf(rs.getInt(1));
				//prestationencours=new PrestationCDIDAO().findByID(rs.getInt(1));
				liste.remove(pos);
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
	
	
	/**
	 * liste toutes les prestations pour lesquelles aucun avenant n'est etabli
	 * sur la periode en cours et dont la date de retour courrier est nulle
	 */
	public List<Integer> listePrestationCDIParPeriodeSansRetour
	(Date a, Date b) throws DAOException {
		ArrayList<Integer> liste=new ArrayList<Integer>();
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "SELECT  id_prestationavenant from "
				+DAOConstants.t_sapprestationaven
				+" where  datefin_pr between '"+a+ "' and '"+b+"'"
				+" and daterenouvel_av is null";
		
		
		try {
			st = connect.createStatement();
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
	
	
	/**heures permanents*/
	public List<AvenantPrestationCDI> listingAvenantContratCDIEnCours(PrestationCDI prest)
			throws DAOException {
		ArrayList<AvenantPrestationCDI> liste = new ArrayList<AvenantPrestationCDI>();
		AvenantPrestationCDI prestation=null;		
		TachesSAP tache=null;
		Statement st = null;
		ResultSet res = null;
		String req = " SELECT distinct(id_prestationavenant) as c,"
				+"id_tache,heuresminimois_pres, "
				+ "salairehor_av,facsalairehor_av,panier_av,deplacement_av, "
				+ "commentaire_pr,datedebut_pr FROM " + DAOConstants.t_aicontrat
				+ " where datefin_pr is null or datefin_pr >curdate()";

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {
				TachesSAPDAO serdao=new TachesSAPDAO();
				tache=serdao.findByID(res.getInt(4));
				

				 prestation = new AvenantPrestationCDI(res.getInt(1),tache,res.getInt(3),
						res.getFloat(4),res.getFloat(5),res.getFloat(6),res.getFloat(7),res.getString(8),
						res.getDate(9));
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


	/**
	 * liste de tous les avenants dont la date de fin est nulle ou superieure a la date indiquee
	 * @param d
	 * @return
	 * @throws DAOException
	 */
	public List<AvenantPrestationCDI> listingAvenantCDIenCours(Date d,Date deux) throws DAOException {
		ArrayList<AvenantPrestationCDI> liste = new ArrayList<AvenantPrestationCDI>();
		PrestationCDIDAO codao = new PrestationCDIDAO();
		PrestationCDI contrat = null;
		TachesSAPDAO serdao=new TachesSAPDAO();
		TachesSAP tache=null;
		AvenantPrestationCDI avenant = null;
		Statement st = null;
		ResultSet res = null;
		String req = " SELECT DISTINCT (id_prestationavenant) AS c, "
				+ DAOConstants.t_sapprestationaven
				+ ".id_prestationcontrat,"
				+ DAOConstants.t_sapprestationaven
				+ ".id_tache,heuresminimois_pres,salairehor_av,"
				+ "panier_av,deplacement_av,facsalairehor_av,"
				+ DAOConstants.t_sapprestationaven
				+ ".commentaire_pr,"
				+ DAOConstants.t_sapprestationaven
				+ ".datedebut_pr,"
				+ DAOConstants.t_sapprestationaven
				+ ".heurescontratsalarie ,nom_identite"
				+ " FROM " + DAOConstants.t_sapprestationaven
				+ " INNER JOIN "
				+ DAOConstants.t_sapprestation
				+ " ON "
				+DAOConstants.t_sapprestationaven
				+ ".id_prestationcontrat ="
				+DAOConstants.t_sapprestation+ ".id_prestationcontrat"
				+ " INNER JOIN " 
				+ DAOConstants.t_identite 
				+ " ON "
				+ DAOConstants.t_sapprestation 
				+ ".id_identite ="
				+ DAOConstants.t_identite
				+ ".id_identite "
				+ " where ("
				+DAOConstants.t_sapprestationaven
				+ ".datefin_pr is null or "
				+DAOConstants.t_sapprestationaven
				+ ".datefin_pr >'"+d
				+"') and "
				+DAOConstants.t_sapprestationaven
				+".datedebut_pr <='"+deux
				+ "'  order by nom_identite asc";
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {
				
				tache=serdao.findByID(res.getInt(3));
				contrat = codao.findByID(res.getInt(2));
				avenant = new AvenantPrestationCDI(res.getInt(1), contrat,tache,res.getInt(4),
						res.getFloat(5), res.getFloat(6), res.getFloat(7),
						res.getFloat(8), res.getString(9),res.getDate(10),res.getInt(11));
				liste.add(avenant);

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
	
	
	public AvenantPrestationCDI dernierAvenantContratCDI(PrestationCDI contrat)
			throws DAOException {
		AvenantPrestationCDI retour = null;
		String query = "select max(id_prestationavenant) as maxi from "
				+ DAOConstants.t_sapprestationaven + " where id_prestationcontrat="
				+contrat.getId_prestationcontrat();

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
	
	
	public AvenantPrestationCDI dernierAvenantCDIPersonne(Identite identite,Employeur emp)
			throws DAOException {
		AvenantPrestationCDI retour = null;
		String query = "select id_prestationavenant  from "
				+ DAOConstants.t_sapprestationaven
				+ " inner join "
				+DAOConstants.t_sapprestation
				+" on "
				+ DAOConstants.t_sapprestationaven
				+".id_prestationcontrat="
				+DAOConstants.t_sapprestation
				+".id_prestationcontrat "
				+" where id_identite="
				+identite.getId_IDE()
				+" and id_employeur="
				+emp.getId_employeur()
				+" order by "
				+ DAOConstants.t_sapprestationaven
				+".id_prestationavenant desc,"
				+ DAOConstants.t_sapprestationaven
				+".datedebut_pr desc limit 0,1";

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
	
	/** 
	 * liste les avenants de prestations employeurs encore valides 
	 * @return
	 * @throws DAOException
	 */
	public List<AvenantPrestationCDI> listeAvenantsPrestationsParEmployeurEnCours()
			throws DAOException {
		List<AvenantPrestationCDI> liste = new ArrayList<AvenantPrestationCDI>();
		Statement st = null;
		ResultSet rs = null;
		AvenantPrestationCDI prest = null;
		TachesSAPDAO tacdao = new TachesSAPDAO();
		
		
		String sql = "SELECT id_prestationavenant,rangavenant,"
				+ DAOConstants.t_sapprestationaven
				+ ".id_prestationcontrat,"
				+ DAOConstants.t_sapprestationaven
				+ ".id_tache,"
				+ DAOConstants.t_sapprestationaven
				+ ".heuresminimois_pres,"
				+ DAOConstants.t_sapprestationaven
				+ ".datedebut_pr,"
				+ DAOConstants.t_sapprestationaven
				+ ".datefin_pr,"
				+ DAOConstants.t_sapprestationaven
				+ ".redaction_pr FROM "
				+ DAOConstants.t_sapprestationaven
				+" inner join "
				+DAOConstants.t_sapprestation
				+" on "
				+ DAOConstants.t_sapprestationaven
				+".id_prestationcontrat="
				+DAOConstants.t_sapprestation
				+".id_prestationcontrat "
				+" where "
				+ DAOConstants.t_sapprestationaven
				+".datefin_pr >= curdate()";

		try {
			st = connect.createStatement();

			// Ex�cution de la requ�te et r�cup�ration du r�sultat dans un
			// resultset
			rs = st.executeQuery(sql);

			// TANT QUE la fin du resultset n'est pas atteinte
			while (rs.next()) {
				prest = new AvenantPrestationCDI(rs.getInt(1),rs.getInt(2),
						new PrestationCDIDAO().findByID(rs.getInt(3)),
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
	
	

}
