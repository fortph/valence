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
import beans.sap.AvenantCDI;
import beans.sap.ContratCDI;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.interfaces.DAO;

public class AvenantCDIDAO implements DAO<AvenantCDI> {
	
	private Connection connect = null;
	
	final private String query_insert = "insert into "
			+ DAOConstants.t_sapavenant
			+ "(rangavenent,id_contratcdi,tache_av,heuresminimois_av,salairehor_av,"
			+ "panier_av,deplacement_av,commentaire_av,redaction_av,urssaf_av,"
			+ "dateeffet_av)"
			+ " values (?,?,?,?,?,?,?,?,?,?,?)";

	final private String query_update = "update "
			+ DAOConstants.t_sapavenant
			+ " set rangavenent=?,id_contratcdi=?,tache_av=?,heuresminimois_av=?,salairehor_av=?,"
			+ "panier_av=?,deplacement_av=?,commentaire_av=?,redaction_av=?,urssaf_av=?,"
			+ "dateeffet_av=? "
			+ " where id_avenantcdi=";

	public AvenantCDIDAO() {
		connect = DBConnexion.getConnexion();
	}

	@Override
	public AvenantCDI findByID(int id) throws DAOException {
		String query = "select `id_avenantcdi`, `rangavenent`, `id_contratcdi`,"
				+ " `tache_av`, `heuresminimois_av`, `salairehor_av`, `panier_av`,"
				+ " `deplacement_av`, `commentaire_av`, `redaction_av`, "
				+ "`urssaf_av`, `dateeffet_av` from " 
				+ DAOConstants.t_sapavenant
				+ " where id_avenantcdi=" + id;
		AvenantCDI lf = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				
				lf = new AvenantCDI(id, res.getInt(2),new ContratCDIDAO().findByID(res.getInt(3)),
						res.getString(4),res.getInt(5),
						res.getFloat(6), res.getFloat(7),
						res.getFloat(8),res.getString(9),
						res.getDate(10),res.getString(11),
						res.getDate(12));
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
	public List<AvenantCDI> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AvenantCDI> findByCriteria(AvenantCDI obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(AvenantCDI obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1,obj.getRangavenent());
			pst.setInt(2,obj.getContrat().getId_contratcdi());
			pst.setString(3, obj.getTache());
			pst.setInt(4, obj.getHeuresminimois());
			pst.setFloat(5, obj.getSalairehoraire());
			pst.setFloat(6, obj.getPanier());
			pst.setFloat(7, obj.getDeplacement());
			pst.setString(8, obj.getCommentaire());
			pst.setDate(9, (Date) obj.getRedaction());
			pst.setString(10, obj.getUrssaf());
			pst.setDate(11, (Date) obj.getDateeffet());
			
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
	public AvenantCDI update(AvenantCDI obj) throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getId_avenant();

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.setInt(1,obj.getRangavenent());
			pst.setInt(2,obj.getContrat().getId_contratcdi());
			pst.setString(3, obj.getTache());
			pst.setInt(4, obj.getHeuresminimois());
			pst.setFloat(5, obj.getSalairehoraire());
			pst.setFloat(6, obj.getPanier());
			pst.setFloat(7, obj.getDeplacement());
			pst.setString(8, obj.getCommentaire());
			pst.setDate(9, (Date) obj.getRedaction());
			pst.setString(10, obj.getUrssaf());
			pst.setDate(11, (Date) obj.getDateeffet());
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
	public int delete(AvenantCDI obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/** liste tous les avenants d'un contrat sap*/
	public List<AvenantCDI> listeAvenantContratCDI(ContratCDI cont) throws DAOException {
		List<AvenantCDI> liste = new ArrayList<AvenantCDI>();
		Statement st = null;
		ResultSet rs = null;
		AvenantCDI avenant = null;
		int idaicontrat = cont.getId_contratcdi();
		String sql = "SELECT id_avenantcdi,rangavenent,heuresminimois_av,dateeffet_av,"
				+ "tache_av,redaction_av FROM "
				+ DAOConstants.t_sapavenant
				+ " where id_contratcdi="
				+ idaicontrat;

		try {
			st = connect.createStatement();

			// Ex�cution de la requ�te et r�cup�ration du r�sultat dans un
			// resultset
			rs = st.executeQuery(sql);

			// TANT QUE la fin du resultset n'est pas atteinte
			while (rs.next()) {
				avenant = new AvenantCDI(rs.getInt(1), rs.getInt(2),rs.getInt(3),
						rs.getDate(4),rs.getString(5),rs.getDate(6));
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
	
	
	
	
	
	/** retourne le numero du prochain avenant pour le contrat donne */
	public int recupereNumeroAvenantEContrat(ContratCDI cont) throws DAOException {
		int num = 0;
		String query = "select max(rangavenent) from " + DAOConstants.t_sapavenant
				+ " where  id_contratcdi=" + cont.getId_contratcdi();
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
	
	/**rajout du 7/08
	 * renvoie l'avenant avec la date embauche la plus recente pour une personne 
	 * en contrat chez l'employeur indiqué
	 * @param identite
	 * @param empl
	 * @return
	 * @throws DAOException
	 */
	public AvenantCDI dernierAvenantSAPPourEmployeur(Identite identite,Employeur empl)
			throws DAOException {
		AvenantCDI retour = null;
		String query = "select id_avenantcdi from "
				+ DAOConstants.t_sapavenant
				+" inner join "
				+DAOConstants.t_sapcontrat
				+" on "
				+DAOConstants.t_sapavenant
				+".id_contratcdi="
				+ DAOConstants.t_sapcontrat 
				+".id_contratcdi "
				+" inner join "
				+DAOConstants.t_sapprestation
				+" on "
				+ DAOConstants.t_sapcontrat 
				+".id_identite="
				+DAOConstants.t_sapprestation
				+".id_identite "
				+" inner join "
				+DAOConstants.t_employeur
				+" on "
				+DAOConstants.t_sapprestation
				+".id_employeur="
				+DAOConstants.t_employeur
				+".id_employeur "
				+ " where "
				+ DAOConstants.t_sapcontrat 
				+".id_identite="
				+ identite.getId_IDE()
				+" and "
				+DAOConstants.t_sapprestation
				+".id_employeur="+empl.getId_employeur()
				+" order by "
				+ DAOConstants.t_sapavenant
				+".dateeeffet_av desc limit 0,1";
				

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
	 *  recupere le dernier avenant etabli pour le contrat indiqué
	 * @param contrat
	 * @return
	 * @throws DAOException
	 */
	public AvenantCDI dernierAvenantContratCDI(ContratCDI contrat)
			throws DAOException {
		AvenantCDI retour = null;
		String query = "select max(id_avenantcdi) as maxi from "
				+ DAOConstants.t_sapavenant
				+ " where id_contratcdi="
				+contrat.getId_contratcdi();

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
	 *  recupere le dernier avenant etabli pour le contrat indiqué
	 * @param contrat
	 * @return
	 * @throws DAOException
	 */
	public AvenantCDI dernierAvenantContratCDIJavascript(int id)
			throws DAOException {
		AvenantCDI retour = null;
		String query = "select max(id_avenantcdi) as maxi from "
				+ DAOConstants.t_sapavenant
				+ " where id_contratcdi="
				+id;

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
