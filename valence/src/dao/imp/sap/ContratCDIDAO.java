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
import beans.parametres.accueil.OrganismeDAO;
import beans.sap.ContratCDI;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.imp.identite.IdentiteDAO;
import dao.interfaces.DAO;

public class ContratCDIDAO implements DAO<ContratCDI> {

	private Connection connect = null;

	final private String query_insert = "insert into "
			+ DAOConstants.t_sapcontrat
			+ "(id_organisme,id_identite,tache,dateembauche,heuresminimois,"
			+ "salairehor,panier,deplacement,commentaire,redaction,urssaf,"
			+ "nompresident,agrementsap,termecontrat)"
			+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	final private String query_update = "update "
			+ DAOConstants.t_sapcontrat
			+ " set id_organisme=?,id_identite=?,tache=?,dateembauche=?,heuresminimois=?,"
			+ "salairehor=?,panier=?,deplacement=?,commentaire=?,redaction=?,"
			+ "urssaf=?,nompresident=?,agrementsap=?,termecontrat=? "
			+ "where id_contratcdi=";

	public ContratCDIDAO() {
		connect = DBConnexion.getConnexion();
	}

	@Override
	public ContratCDI findByID(int id) throws DAOException {
		String query = "select  `id_contratcdi`, `id_organisme`, `id_identite`,"
				+ " `tache`, `dateembauche`, `heuresminimois`, `salairehor`, `panier`, "
				+ "`deplacement`, `commentaire`, `redaction`, `urssaf`, `nompresident`,"
				+ " `agrementsap`, `termecontrat` from " + DAOConstants.t_sapcontrat
				+ " where id_contratcdi=" + id;
		ContratCDI lf = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {

				lf = new ContratCDI(id, new OrganismeDAO().findByID(res
						.getInt(2)), new IdentiteDAO().findByID(res.getInt(3)),
						res.getString(4), res.getDate(5), res.getInt(6),
						res.getFloat(7), res.getFloat(8), res.getFloat(9),
						res.getString(10), res.getDate(11), res.getString(12),
						res.getString(13), res.getString(14), res.getDate(15));
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
	
	public ContratCDI trouveParID(int id) throws DAOException {
		String query = "select  `id_contratcdi`, `id_organisme`, `id_identite`,"
				+ " `tache`, `dateembauche`, `heuresminimois`, `salairehor`, `panier`, "
				+ "`deplacement`, `commentaire`, `redaction`, `urssaf`, `nompresident`,"
				+ " `agrementsap`, `termecontrat` from " + DAOConstants.t_sapcontrat
				+ " where id_contratcdi=" + id;
		ContratCDI lf = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {

				lf = new ContratCDI(id, new OrganismeDAO().findByID(res
						.getInt(2)), new IdentiteDAO().findByID(res.getInt(3)),
						res.getString(4), res.getDate(5), res.getInt(6),
						res.getFloat(7), res.getFloat(8), res.getFloat(9),
						res.getString(10), res.getDate(11), res.getString(12),
						res.getString(13), res.getString(14), res.getDate(15));
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
	public List<ContratCDI> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ContratCDI> findByCriteria(ContratCDI obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(ContratCDI obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);

			pst.setInt(1, obj.getOrganisme().getId());
			pst.setInt(2, obj.getIdentite().getId_IDE());
			pst.setString(3, obj.getTache());
			pst.setDate(4, (Date) obj.getEmbauche());
			pst.setInt(5, obj.getHeuresminimois());
			pst.setFloat(6, obj.getSalairehoraire());
			pst.setFloat(7, obj.getPanier());
			pst.setFloat(8, obj.getDeplacement());
			pst.setString(9, obj.getCommentaire());
			pst.setDate(10, (Date) obj.getRedaction());
			pst.setString(11, obj.getUrssaf());
			pst.setString(12, obj.getNompresident());
			pst.setString(13, obj.getAgrement());
			pst.setDate(14, (Date) obj.getTermecontrat());

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
	public ContratCDI update(ContratCDI obj) throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getId_contratcdi();

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.setInt(1, obj.getOrganisme().getId());
			pst.setInt(2, obj.getIdentite().getId_IDE());
			pst.setString(3, obj.getTache());
			pst.setDate(4, (Date) obj.getEmbauche());
			pst.setInt(5, obj.getHeuresminimois());
			pst.setFloat(6, obj.getSalairehoraire());
			pst.setFloat(7, obj.getPanier());
			pst.setFloat(8, obj.getDeplacement());
			pst.setString(9, obj.getCommentaire());
			pst.setDate(10, (Date) obj.getRedaction());
			pst.setString(11, obj.getUrssaf());
			pst.setString(12, obj.getNompresident());
			pst.setString(13, obj.getAgrement());
			pst.setDate(14, (Date) obj.getTermecontrat());
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
	public int delete(ContratCDI obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	/** liste tous les contrats sap de la personne */
	public List<ContratCDI> contratSapParPersonne(Identite identite)
			throws DAOException {
		ArrayList<ContratCDI> liste = new ArrayList<ContratCDI>();
		ContratCDI contrat = null;
		Statement st = null;
		ResultSet res = null;
		String req = "select id_contratcdi " + "  from "
				+ DAOConstants.t_sapcontrat + " where id_identite="
				+ identite.getId_IDE() + "  order by id_contratcdi desc ";

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {
				contrat = this.findByID(res.getInt(1));
				liste.add(contrat);

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

	/** verifie si un contrat a deja ete etabli pour la personne */
	public int contratCdiCree(Identite identite) throws DAOException {
		int retour = 0;
		String query = "select count(id_contratcdi) as tot from "
				+ DAOConstants.t_sapcontrat + " where id_identite="
				+ identite.getId_IDE();

		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				retour = res.getInt(1);
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

	/** verifie si un contrat a deja ete etabli pour la personne */
	public int contratCdiCreeEnCours(Identite identite) throws DAOException {
		int retour = 0;
		String query = "select count(id_contratcdi) as tot from "
				+ DAOConstants.t_sapcontrat + " where id_identite="
				+ identite.getId_IDE() + " and termecontrat is null";

		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				retour = res.getInt(1);
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
	 * verifie si un contrat a deja ete etabli pour la personne 
	 * @param identite
	 * @return
	 * @throws DAOException
	 */
	public ContratCDI contratEnregistreDansLaJournee(Identite identite)
			throws DAOException {
		ContratCDI retour = null;
		String query = "select id_contratcdi from " + DAOConstants.t_sapcontrat
				+ " where id_identite=" + identite.getId_IDE()
				+ " and redaction=curdate()";

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
	 * recupere le dernier contrat CDI etabli pour la personne qui est en cours 
	 * @param identite
	 * @return
	 * @throws DAOException
	 */
	public ContratCDI dernierContratEnCours(Identite identite)
			throws DAOException {
		ContratCDI retour = null;
		String query = "select max(id_contratcdi) as maxi from "
				+ DAOConstants.t_sapcontrat + " where id_identite="
				+ identite.getId_IDE() + " and (termecontrat is null or termecontrat > curdate()) ";

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
	 * recupere le dernier contrat CDI etabli pour la personne 
	 * @param identite
	 * @return
	 * @throws DAOException
	 */
	public ContratCDI dernierContrat(int identite)
			throws DAOException {
		ContratCDI retour = null;
		String query = "select max(id_contratcdi) as maxi from "
				+ DAOConstants.t_sapcontrat + " where id_identite="
				+ identite;

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
 * recupere le salaire contractuel de la personne sur le dernier contrat valide
 * @param id
 * @return
 * @throws DAOException
 */
	public float recupereSalaireHoraireContratSAP(int id)
			throws DAOException {
		float retour = 0.00f;
		/*String query = "select salairehor  from " + DAOConstants.t_sapcontrat
				+ " where id_identite=" + id
				+ " and if ("
				+ "termecontrat is null,' termecontrat is null',"
				+ " ' 1=1 order by id_contratcdi desc') ";*/
		String query = "select salairehor  from " + DAOConstants.t_sapcontrat
				+ " where id_identite=" + id
				+ " and termecontrat is null order by id_contratcdi desc";
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				retour = res.getFloat(1);
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
	 * recupere le salaire contractuel de la personne sur le dernier contrat
	 * @param id
	 * @return
	 * @throws DAOException
	 */
		public float recupereDernierSalaireHoraireContratSAP(int id)
				throws DAOException {
			float retour = 0.00f;
			/*String query = "select salairehor  from " + DAOConstants.t_sapcontrat
					+ " where id_identite=" + id
					+ " and if ("
					+ "termecontrat is null,' termecontrat is null',"
					+ " ' 1=1 order by id_contratcdi desc') ";*/
			String query = "select salairehor  from " + DAOConstants.t_sapcontrat
					+ " where id_identite=" + id
					+ " order by id_contratcdi desc";
			Statement st = null;
			ResultSet res = null;
			try {
				st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				res = st.executeQuery(query);
				if (res.first()) {
					retour = res.getFloat(1);
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
		
	
	
	/** recupere les personnes ayant un contrat en cours */
	public List<Integer> sapEnCours()
			throws DAOException {
		List <Integer> liste=new ArrayList<Integer>();
		/*String query = "select distinct id_identite from "
				+ DAOConstants.t_sapcontrat + " where  termecontrat is null ";*/
		String query = "select distinct id_identite from "
		 + DAOConstants.t_sapcontrat + " where  id_contratcdi in( select id_contratcdi"
		 +" from "
		+ DAOConstants.t_sapcontrat + " where  (termecontrat is null or termecontrat > curdate()))";
		 
		int numero;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			while (res.next()) {
				numero = res.getInt(1);
				liste.add(numero);
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
	 * recupere la liste des personnes ayant un contrat sap
	 * @return
	 * @throws DAOException
	 */
	public List<Integer> sapContrats()
			throws DAOException {
		List <Integer> liste=new ArrayList<Integer>();
		
		String query = "select distinct id_identite from "
		 + DAOConstants.t_sapcontrat/* + " where  id_contratcdi in( select id_contratcdi"
		 +" from "
		+ DAOConstants.t_sapcontrat +")"*/;
		 
		int numero;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			while (res.next()) {
				numero = res.getInt(1);
				liste.add(numero);
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
	
	
	/** rajout du 07/08
	 * renvoie le contrat avec la date embauche la plus recente pour une personne 
	 * en contrat chez l'employeur indiqué
	 * @param identite
	 * @param empl
	 * @return
	 * @throws DAOException
	 */
	public ContratCDI dernierContratSAPPourEmployeur(Identite identite,Employeur empl)
			throws DAOException {
		ContratCDI retour = null;
		String query = "select id_contratcdi from "
				+ DAOConstants.t_sapcontrat 
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
				+ DAOConstants.t_sapcontrat 
				+".dateembauche desc limit 0,1";
				

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
