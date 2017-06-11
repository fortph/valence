package dao.imp.ai;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.ai.Contrat;
import beans.employeurs.Employeur;
import beans.employeurs.Service;
import beans.identite.Identite;
import beans.parametres.accueil.Rome;
import beans.parametres.accueil.RomeDAO;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.imp.employeur.EmployeurDAO;
import dao.imp.employeur.ServiceDAO;
import dao.imp.identite.IdentiteDAO;
import dao.interfaces.DAO;

public class ContratDAO implements DAO<Contrat> {

	final private String query_insert = "insert into " + DAOConstants.t_aicontrat
			+ "( id_identite, id_employeur, id_employeurservice, idrome,"
			+ "tache_aicontrat,lieu_aicontrat,datedebcontrat_aicontrat, datefincontrat_aicontrat,"
			+ "hebdomin_aicontrat, salairehor_aicontrat,"
			+ " facturation_aicontrat, panier_aicontrat, panierfac_aicontrat,	deplacement_aicontrat,"
			+ " deplacementfac_aicontrat,"
			+ "	diver_aicontrat, diverfac_aicontrat, divert_aicontrat, diverfact_aicontrat, dateredac_aicontrat,"
			+ "	 ncontrat, urssaf_aicontrat, fintache_aicontrat, anul_aicontrat)"
			+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	final private String query_update = "update " + DAOConstants.t_aicontrat
			+ " set id_identite=?, id_employeur=?, id_employeurservice=?, idrome=?,"
			+ "tache_aicontrat=?,lieu_aicontrat=?,datedebcontrat_aicontrat=?, datefincontrat_aicontrat=?,"
			+ "	hebdomin_aicontrat=?, salairehor_aicontrat=?,"
			+ " facturation_aicontrat=?, panier_aicontrat=?, panierfac_aicontrat=?,	deplacement_aicontrat=?, "
			+ "deplacementfac_aicontrat=?,"
			+ "	diver_aicontrat=?, diverfac_aicontrat=?, divert_aicontrat=?, diverfact_aicontrat=?, dateredac_aicontrat=?,"
			+ "	 ncontrat=?, urssaf_aicontrat=?,fintache_aicontrat=?, anul_aicontrat=? " + " where idaicontrat=";

	private Connection connect = null;

	public ContratDAO() {
		connect = DBConnexion.getConnexion();

	}

	@Override
	public Contrat findByID(int id) throws DAOException {
		String query = "select * from " + DAOConstants.t_aicontrat + " where  idaicontrat=" + id;
		Contrat contrat = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				contrat = new Contrat((int) id, new IdentiteDAO().findByID(res.getInt(2)),
						new EmployeurDAO().findByID(res.getInt(3)), new ServiceDAO().findByID(res.getInt(4)),
						new RomeDAO().findByID(res.getInt(5)), res.getString(6), res.getString(7), res.getDate(8),
						res.getDate(9), res.getString(10), res.getFloat(11), res.getFloat(12), res.getFloat(13),
						res.getFloat(14), res.getFloat(15), res.getFloat(16), res.getFloat(17), res.getFloat(18),
						res.getString(19), res.getString(20), res.getDate(21), res.getInt(22), res.getString(23),
						res.getString(24), res.getBoolean(25));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return contrat;
	}

	@Override
	public List<Contrat> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contrat> findByCriteria(Contrat obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(Contrat obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, obj.getIdentite().getId_IDE());
			pst.setInt(2, obj.getEmployeur().getId_employeur());
			pst.setInt(3, obj.getService().getId_employeurservice());
			pst.setInt(4, obj.getRome().getIdrome());
			pst.setString(5, obj.getTache());
			pst.setString(6, obj.getLieu());
			pst.setDate(7, (java.sql.Date) obj.getDebutcontrat());
			pst.setDate(8, (java.sql.Date) obj.getFincontrat());
			pst.setString(9, obj.getHeuresminihebdo());
			pst.setFloat(10, obj.getSalairehoraire());
			pst.setFloat(11, obj.getFacturation());
			pst.setFloat(12, obj.getPanier());
			pst.setFloat(13, obj.getFacturepanier());
			pst.setFloat(14, obj.getDeplacement());
			pst.setFloat(15, obj.getFacturedeplace());
			pst.setFloat(16, obj.getDivers());
			pst.setFloat(17, obj.getFacturedivers());
			pst.setString(18, obj.getCommentaire());
			pst.setString(19, obj.getFacturecommentaire());
			pst.setDate(20, (java.sql.Date) obj.getRedaction());
			pst.setInt(21, obj.getNumerocontrat());
			pst.setString(22, obj.getUrssaf());
			pst.setString(23, obj.getFintache());
			pst.setBoolean(24, obj.isAnnule());

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
	public Contrat update(Contrat obj) throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getIdaicontrat();

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.setInt(1, obj.getIdentite().getId_IDE());
			pst.setInt(2, obj.getEmployeur().getId_employeur());
			pst.setInt(3, obj.getService().getId_employeurservice());
			pst.setInt(4, obj.getRome().getIdrome());
			pst.setString(5, obj.getTache());
			pst.setString(6, obj.getLieu());
			pst.setDate(7, (java.sql.Date) obj.getDebutcontrat());
			pst.setDate(8, (java.sql.Date) obj.getFincontrat());
			pst.setString(9, obj.getHeuresminihebdo());
			pst.setFloat(10, obj.getSalairehoraire());
			pst.setFloat(11, obj.getFacturation());
			pst.setFloat(12, obj.getPanier());
			pst.setFloat(13, obj.getFacturepanier());
			pst.setFloat(14, obj.getDeplacement());
			pst.setFloat(15, obj.getFacturedeplace());
			pst.setFloat(16, obj.getDivers());
			pst.setFloat(17, obj.getFacturedivers());
			pst.setString(18, obj.getCommentaire());
			pst.setString(19, obj.getFacturecommentaire());
			pst.setDate(20, (java.sql.Date) obj.getRedaction());
			pst.setInt(21, obj.getNumerocontrat());
			pst.setString(22, obj.getUrssaf());
			pst.setString(23, obj.getFintache());
			pst.setBoolean(24, obj.isAnnule());

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
	public int delete(Contrat obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Contrat> contratAiParPersonne(Identite identite) throws DAOException {
		ArrayList<Contrat> liste = new ArrayList<Contrat>();
		Employeur employeur = null;
		Contrat contrat = null;
		Statement st = null;
		ResultSet res = null;
		String req = "select idaicontrat,ncontrat,id_employeur,datedebcontrat_aicontrat, datefincontrat_aicontrat,"
				+ "idrome  from " + DAOConstants.t_aicontrat + " where id_identite=" + identite.getId_IDE()
				+ "  order by ncontrat desc ";

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {

				RomeDAO rodao = new RomeDAO();
				Rome rome = rodao.findByID(res.getInt(6));
				EmployeurDAO empdao = new EmployeurDAO();
				employeur = empdao.findByID(res.getInt(3));

				contrat = new Contrat(res.getInt(1), res.getInt(2), employeur, res.getDate(4), res.getDate(5), rome);
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

	public int contratEnCours(Identite identite) throws DAOException {
		int retour = 0;
		String requ = "select count(*) as nbre from " + DAOConstants.t_aicontrat + " left join "
				+ DAOConstants.t_aiavenant + " on  " + DAOConstants.t_aicontrat + ".idaicontrat="
				+ DAOConstants.t_aiavenant + ".idaicontrat where id_identite=" + identite.getId_IDE()
				+ " and (datefin_aiavenant >= CURDATE() or datefincontrat_aicontrat >= CURDATE() )";

		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(requ);
			if (res.first()) {
				retour = res.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return retour;

	}

	public String calculPeriodeEssai(Contrat cont) throws DAOException {
		int duree = 0;
		String retour = "";
		String req = "SELECT DATEDIFF(datefincontrat_aicontrat, datedebcontrat_aicontrat) AS duree FROM "
				+ DAOConstants.t_aicontrat + "  WHERE idaicontrat =" + cont.getIdaicontrat();
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			if (res.first()) {
				duree = res.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DAOUtil.closeResultSet(res);
			DAOUtil.closeStatement(st);
			// DBConnexion.cloreConnexion();
		}
		if (duree < 180) {
			double essai = duree / 7.0;
			if (essai > 15)
				retour = " 15 jours";
			else
				retour = (int) Math.floor(essai) + " jour(s)";
		} else
			retour = "1 mois";

		return retour;
	}

	/**
	 * liste des contrats se terminant entre les dates indiquees
	 * 
	 * @param a
	 *            date debut
	 * @param b
	 *            date fin
	 * @return
	 * @throws DAOException
	 */
	public List<Contrat> contratAvenantParDateFin(Date a, Date b) throws DAOException {
		ArrayList<Contrat> liste = new ArrayList<Contrat>();
		Employeur employeur = null;
		Contrat contrat = null;
		Statement st = null;
		ResultSet res = null;
		String req = "select idaicontrat,id_employeur,id_identite,datedebcontrat_aicontrat, datefincontrat_aicontrat,"
				+ "idrome  from " + DAOConstants.t_aicontrat + " where datefincontrat_aicontrat>='" + a
				+ "' and  datefincontrat_aicontrat<='" + b + "'  order by datefincontrat_aicontrat desc ";

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {

				RomeDAO rodao = new RomeDAO();
				Rome rome = rodao.findByID(res.getInt(6));
				EmployeurDAO empdao = new EmployeurDAO();
				employeur = empdao.findByID(res.getInt(2));
				IdentiteDAO idao = new IdentiteDAO();
				Identite identite = idao.findByID(res.getInt(3));

				contrat = new Contrat(res.getInt(1), identite, employeur, rome, res.getDate(4), res.getDate(5));
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
	
	/**
	 * listing des personnes ayant un contrat valide sur la période sélectionnée
	 * @param a
	 * @param b
	 * @return
	 * @throws DAOException
	 */
	
	public List<Contrat> contratAIsurPeriode(Date a, Date b) throws DAOException {
		ArrayList<Contrat> liste = new ArrayList<Contrat>();
		Employeur employeur = null;
		Contrat contrat = null;
		Statement st = null;
		ResultSet res = null;
		String req = "select idaicontrat,id_employeur,id_identite,datedebcontrat_aicontrat, datefincontrat_aicontrat,"
				+ "idrome  from " + DAOConstants.t_aicontrat + " where (datedebcontrat_aicontrat between '" + a
				+ "' and  '" + b + "' or datefincontrat_aicontrat between '" + a
				+ "' and  '" + b + "') or (datedebcontrat_aicontrat < '"+
				a+"' and datefincontrat_aicontrat >'"+b+"' ) order by datefincontrat_aicontrat desc ";

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {

				RomeDAO rodao = new RomeDAO();
				Rome rome = rodao.findByID(res.getInt(6));
				EmployeurDAO empdao = new EmployeurDAO();
				employeur = empdao.findByID(res.getInt(2));
				IdentiteDAO idao = new IdentiteDAO();
				Identite identite = idao.findByID(res.getInt(3));

				contrat = new Contrat(res.getInt(1), identite, employeur, rome, res.getDate(4), res.getDate(5));
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

	/**
	 * liste des contrats se terminant entre les dates indiquees
	 * 
	 * @return
	 * @throws DAOException
	 */
	public List<Contrat> contratParDateJusqua() throws DAOException {
		ArrayList<Contrat> liste = new ArrayList<Contrat>();
		Employeur employeur = null;
		Contrat contrat = null;
		Statement st = null;
		ResultSet res = null;
		String req = "select idaicontrat,id_employeur,id_identite,datedebcontrat_aicontrat, datefincontrat_aicontrat,"
				+ "idrome  from " + DAOConstants.t_aicontrat + " where 1=1  "
				+ "  order by datefincontrat_aicontrat desc ";

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {

				RomeDAO rodao = new RomeDAO();
				Rome rome = rodao.findByID(res.getInt(6));
				EmployeurDAO empdao = new EmployeurDAO();
				employeur = empdao.findByID(res.getInt(2));
				IdentiteDAO idao = new IdentiteDAO();
				Identite identite = idao.findByID(res.getInt(3));

				contrat = new Contrat(res.getInt(1), identite, employeur, rome, res.getDate(4), res.getDate(5));
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

	/**
	 * liste des contrats se terminant entre les dates indiquees
	 * 
	 * @param mois
	 * @param an
	 * @return
	 * @throws DAOException
	 */
	/*
	 * public List<Contrat> dmoContrat(int mois, int an) throws DAOException {
	 * ArrayList<Contrat> liste = new ArrayList<Contrat>(); Employeur employeur
	 * = null; Contrat contrat = null; Statement st = null; ResultSet res =
	 * null; String req = " SELECT distinct idaicontrat," +
	 * DAOConstants.t_aicontrat
	 * +".id_identite,id_employeur,idrome, datedebcontrat_aicontrat,datefincontrat_aicontrat "
	 * + " FROM " + DAOConstants.t_aicontrat +"  left outer join "
	 * +DAOConstants.t_aiextranet +" on " + DAOConstants.t_aicontrat
	 * +".id_identite=" +DAOConstants.t_aiextranet +".id_identite " +
	 * " where ((datedebut IS NULL or (month( datefin ) <="
	 * +mois+" AND year( datefin ) <="+an+")) "
	 * 
	 * +" and ((month(datedebcontrat_aicontrat)="
	 * +mois+" and year (datedebcontrat_aicontrat)="+an
	 * +") or (month (datefincontrat_aicontrat)="
	 * +mois+" and year(datefincontrat_aicontrat)="+an +")))";
	 * 
	 * try { st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
	 * ResultSet.CONCUR_READ_ONLY); res = st.executeQuery(req); while
	 * (res.next()) { RomeDAO rodao = new RomeDAO(); Rome rome =
	 * rodao.findByID(res.getInt(4)); EmployeurDAO empdao = new EmployeurDAO();
	 * employeur = empdao.findByID(res.getInt(3)); IdentiteDAO idao=new
	 * IdentiteDAO(); Identite identite=idao.findByID(res.getInt(2));
	 * 
	 * 
	 * contrat = new Contrat(res.getInt(1),identite,employeur, rome,
	 * res.getDate(5), res.getDate(6)); liste.add(contrat);
	 * 
	 * }
	 * 
	 * } catch (SQLException e) {
	 * 
	 * e.printStackTrace();
	 * 
	 * } finally { try { res.close(); st.close(); } catch (SQLException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); } }
	 * 
	 * return liste;
	 * 
	 * }
	 */

	public List<Contrat> dmoContrat(int mois, int an) throws DAOException {
		// int moisCalendar=mois-1;
		ArrayList<Contrat> liste = new ArrayList<Contrat>();
		Employeur employeur = null;
		Contrat contrat = null;
		Statement st = null;
		ResultSet res = null;
		/*
		 * on fait un tri sur la table aiextranet pour ne garder que la
		 * datedebut la plus récente pour chaque personne que l'on supprime à
		 * chaque appel pour l'avoir à jour.
		 */
		String supTable = "drop table if exists aiextranetVue";
		String creationTable = "create table aiextranetVue SELECT tout . * " + "FROM aiextranet tout INNER JOIN"
				+ " (SELECT `id_extranet` , `id_identite` , max( `datedebut` ) AS maxi,"
				+ " `datefin` FROM aiextranet GROUP BY `id_identite`) groupe  "
				+ "ON tout.`id_identite` = groupe.`id_identite` " + "AND tout.`datedebut` = groupe.maxi";

		// Calendar jour=Calendar.getInstance();
		// jour.set(Calendar.MONTH,moisCalendar);
		// jour.set(Calendar.YEAR, an);
		// int jourMois=jour.getActualMaximum(Calendar.DAY_OF_MONTH);
		// String dernierjourMoisDMO=an+"-"+mois+"-"+jourMois;
		// String premierJourMoisDMO=an+"-"+mois+"-01";
		// System.out.println(dernierjourMoisDMO);
		// String dateDMOPlusUnJour=yy+"-"+((mm<10)? "0"+mm : mm )+"-01";
		String req = " SELECT distinct  idaicontrat," + DAOConstants.t_aicontrat
				+ ".id_identite,id_employeur,idrome, datedebcontrat_aicontrat,datefincontrat_aicontrat " + " FROM "
				+ DAOConstants.t_aicontrat + "  left outer join aiextranetVue "
				// +DAOConstants.t_aiextranet
				+ " on " + DAOConstants.t_aicontrat + ".id_identite=aiextranetVue"
				// +DAOConstants.t_aiextranet
				+ ".id_identite " + " where ((datedebut IS NULL or  datefin < datefincontrat_aicontrat) "
				+ " and ((month(datedebcontrat_aicontrat)=" + mois + " and year (datedebcontrat_aicontrat)=" + an
				+ ")))";
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			st.execute(supTable);
			st.execute(creationTable);
			res = st.executeQuery(req);
			while (res.next()) {
				RomeDAO rodao = new RomeDAO();
				Rome rome = rodao.findByID(res.getInt(4));
				EmployeurDAO empdao = new EmployeurDAO();
				employeur = empdao.findByID(res.getInt(3));
				IdentiteDAO idao = new IdentiteDAO();
				Identite identite = idao.findByID(res.getInt(2));

				contrat = new Contrat(res.getInt(1), identite, employeur, rome, res.getDate(5), res.getDate(6));
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

	/**
	 * liste des effectifs permanents
	 * 
	 * @param d
	 * @return
	 * @throws DAOException
	 */
	public List<Contrat> listingPermanentsContrat(Date d) throws DAOException {
		ArrayList<Contrat> liste = new ArrayList<Contrat>();

		Service service = null;
		Employeur employeur = null;
		Contrat contrat = null;
		Statement st = null;
		ResultSet res = null;
		String req = " SELECT distinct(idaicontrat) as c," + DAOConstants.t_aicontrat
				+ ".id_identite,id_employeur,id_employeurservice,idrome,tache_aicontrat, lieu_aicontrat,"
				+ "datedebcontrat_aicontrat, " + "datefincontrat_aicontrat,salairehor_aicontrat,"
				+ "  panier_aicontrat,	deplacement_aicontrat," + " diver_aicontrat,divert_aicontrat,nom_identite FROM "
				+ DAOConstants.t_aicontrat + " inner join " + DAOConstants.t_aicreation + " on "
				+ DAOConstants.t_aicontrat + ".id_identite=" + DAOConstants.t_aicreation + ".id_identite"
				+ " inner join " + DAOConstants.t_identite + " on " + DAOConstants.t_aicontrat + ".id_identite="
				+ DAOConstants.t_identite + ".id_identite"

				+ " where datefincontrat_aicontrat >'" + d
				+ "' and permanent_aifiche=1 group by idaicontrat order by nom_identite asc";

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {
				ServiceDAO serdao = new ServiceDAO();
				service = serdao.findByID(res.getInt(4));
				RomeDAO rodao = new RomeDAO();
				Rome rome = rodao.findByID(res.getInt(5));
				EmployeurDAO empdao = new EmployeurDAO();
				employeur = empdao.findByID(res.getInt(3));
				IdentiteDAO idao = new IdentiteDAO();
				Identite identite = idao.findByID(res.getInt(2));

				contrat = new Contrat(res.getInt(1), identite, employeur, service, rome, res.getString(6),
						res.getString(7), res.getDate(8), res.getDate(9), res.getFloat(10), res.getFloat(11),
						res.getFloat(12), res.getFloat(13), res.getString(14));
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

	public List<String> nombreContratsParMois(String anneedeb, String anneefin) throws DAOException {
		List<String> liste = new ArrayList<String>();

		String requ = "SELECT month( datedebcontrat_aicontrat ) AS mois, year( datedebcontrat_aicontrat )"
				+ " AS an, count( idaicontrat ) AS nbre 	FROM " + DAOConstants.t_aicontrat
				+ " WHERE year( datedebcontrat_aicontrat )	BETWEEN '" + anneedeb + "' 	AND '" + anneefin
				+ "'		GROUP BY year( datedebcontrat_aicontrat ) , month( datedebcontrat_aicontrat )";

		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(requ);
			while (res.next()) {
				int mm = res.getInt(1);
				int yy = res.getInt(2);
				int nb = res.getInt(3);
				String retour = mm + "," + yy + "," + nb;
				liste.add(retour);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
	 * liste tous les contrats ai de l'employeur donné en parametre
	 * 
	 * @param emp
	 * @return
	 * @throws DAOException
	 */
	public List<Contrat> contratAiParEmployeur(Employeur emp) throws DAOException {
		ArrayList<Contrat> liste = new ArrayList<Contrat>();
		Identite identite = null;
		Contrat contrat = null;
		Statement st = null;
		ResultSet res = null;
		String req = "select idaicontrat,ncontrat,id_identite,datedebcontrat_aicontrat, datefincontrat_aicontrat,"
				+ "idrome  from " + DAOConstants.t_aicontrat + " where id_employeur=" + emp.getId_employeur()
				+ "  order by ncontrat desc ";

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {

				RomeDAO rodao = new RomeDAO();
				Rome rome = rodao.findByID(res.getInt(6));
				IdentiteDAO iddao = new IdentiteDAO();
				identite = iddao.findByID(res.getInt(3));

				contrat = new Contrat(res.getInt(1), res.getInt(2), identite, res.getDate(4), res.getDate(5), rome);
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

	/**
	 * liste des contrats situes entre les dates indiquees
	 * 
	 * @param a
	 *            date debut
	 * @param b
	 *            date fin
	 * @return
	 * @throws DAOException
	 */
	public List<Contrat> contratDansFourchetteDate(Date a, Date b) throws DAOException {
		ArrayList<Contrat> liste = new ArrayList<Contrat>();
		Employeur employeur = null;
		Contrat contrat = null;
		Statement st = null;
		ResultSet res = null;
		String req = "select idaicontrat,id_employeur,id_identite,datedebcontrat_aicontrat, datefincontrat_aicontrat,"
				+ "idrome  from " + DAOConstants.t_aicontrat + " where datedebcontrat_aicontrat>='" + a
				+ "' and  datefincontrat_aicontrat<='" + b + "' ";

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {

				RomeDAO rodao = new RomeDAO();
				Rome rome = rodao.findByID(res.getInt(6));
				EmployeurDAO empdao = new EmployeurDAO();
				employeur = empdao.findByID(res.getInt(2));
				IdentiteDAO idao = new IdentiteDAO();
				Identite identite = idao.findByID(res.getInt(3));

				contrat = new Contrat(res.getInt(1), identite, employeur, rome, res.getDate(4), res.getDate(5));
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

	/**
	 * recupere le dernier contrat AI etabli pour la personne chaz l'employeur
	 * indiqué
	 * 
	 * @param identite
	 * @return
	 * @throws DAOException
	 */
	public Contrat dernierContratAIEnCours(Identite identite, Employeur empl) throws DAOException {
		Contrat retour = null;
		String query = "select max(idaicontrat) from " + DAOConstants.t_aicontrat + " where id_identite="
				+ identite.getId_IDE() + " and id_employeur=" + empl.getId_employeur();

		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
	 * liste des contrats se terminant entre les dates indiquees
	 * 
	 * @param a
	 *            date debut
	 * @param b
	 *            date fin
	 * @return
	 * @throws DAOException
	 */
	public List<Contrat> contratAIMutuelleParDateFin(Date a, Date b) throws DAOException {
		ArrayList<Contrat> liste = new ArrayList<Contrat>();
		Employeur employeur = null;
		Contrat contrat = null;
		Statement st = null;
		ResultSet res = null;
		String req = "select  idaicontrat,id_employeur," + DAOConstants.t_aicontrat
				+ ".id_identite,datedebcontrat_aicontrat, datefincontrat_aicontrat,"
				+ "idrome,dateecheance,dateecheancerefus  from " + DAOConstants.t_aicontrat + " inner join "
				+ DAOConstants.t_mutuelle + " on " + DAOConstants.t_aicontrat + ".id_identite="
				+ DAOConstants.t_mutuelle + ".id_identite"

				+ " where (datefincontrat_aicontrat>='" + a + "' and  datefincontrat_aicontrat<='" + b
				+ "') and (dateecheance <='" + b + "' or dateecheance is null)" + " and (dateecheancerefus <='" + b
				+ "' or dateecheancerefus is null)  group by "
				+ "idaicontrat,id_employeur," + DAOConstants.t_aicontrat
				+ ".id_identite,datedebcontrat_aicontrat,idrome  order by datefincontrat_aicontrat desc";

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {

				RomeDAO rodao = new RomeDAO();
				Rome rome = rodao.findByID(res.getInt(6));
				EmployeurDAO empdao = new EmployeurDAO();
				employeur = empdao.findByID(res.getInt(2));
				IdentiteDAO idao = new IdentiteDAO();
				Identite identite = idao.findByID(res.getInt(3));

				contrat = new Contrat(res.getInt(1), identite, employeur, rome, res.getDate(4), res.getDate(5));
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

}
