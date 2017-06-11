package dao.imp.employeur;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.employeurs.Employeur;
import beans.employeurs.Offre;
import beans.parametres.accueil.Rome;
import beans.parametres.accueil.RomeDAO;
import beans.parametres.capemploi.UtilisateurDAO;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.interfaces.DAO;

public class OffreDAO implements DAO<Offre> {

	final private String query_insert = "insert into "
			+ DAOConstants.t_offre
			+ "( id_employeur,id_contact,datesaisie_offre,id_salarie,idrome,nbrpersonne_offre,"
			+ "datedeb_offre,datefin_offre,duree_offre,contrat_autre,contrat_ai,contrat_cae,"
			+ "contrat_avenir,contrat_cdd,contrat_cdi,contrat_alternance,detail_offre,"
			+ "pourvue_offre,observ_offre,jour,heures,dureestats_offre,"
			+ "annule_offre,autrerecrut_offre)"
			+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	final private String query_update = "update "
			+ DAOConstants.t_offre
			+ " set id_employeur=?,id_contact=?,datesaisie_offre=?,id_salarie=?,idrome=?,"
			+ "nbrpersonne_offre=?,datedeb_offre=?,datefin_offre=?,duree_offre=?,"
			+ "contrat_autre=?,contrat_ai=?,contrat_cae=?,"
			+ "contrat_avenir=?,contrat_cdd=?,contrat_cdi=?,contrat_alternance=?,"
			+ "detail_offre=?,pourvue_offre=?,observ_offre=?,"
			+ "jour=?,heures=?,dureestats_offre=?,"
			+ "annule_offre=?,autrerecrut_offre=?" + " where id_offre=";

	private Connection connect = null;

	public OffreDAO() {
		connect = DBConnexion.getConnexion();

	}

	@Override
	public Offre findByID(int id) throws DAOException {
		String query = "select  `id_offre`, `id_employeur`, `id_contact`,"
				+ " `datesaisie_offre`, `id_salarie`, `idrome`, "
				+ "`nbrpersonne_offre`, `datedeb_offre`, `datefin_offre`,"
				+ " `duree_offre`, `contrat_autre`, `contrat_ai`,"
				+ " `contrat_cae`, `contrat_avenir`, `contrat_cdd`,"
				+ " `contrat_cdi`, `contrat_alternance`, `detail_offre`,"
				+ " `pourvue_offre`, `observ_offre`, `jour`, `heures`,"
				+ " `dureestats_offre`, `annule_offre`, "
				+ "`autrerecrut_offre` from " + DAOConstants.t_offre
				+ " where  id_offre=" + id;
		Offre offre = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				offre = new Offre((int) id, new EmployeurDAO().findByID(res
						.getInt(2)), new ContactDAO().findByID(res.getInt(3)),
						res.getDate(4), new UtilisateurDAO().findByID(res
								.getInt(5)), new RomeDAO().findByID(res
								.getInt(6)), res.getInt(7), res.getDate(8),
						res.getDate(9), res.getString(10), res.getBoolean(11),
						res.getBoolean(12), res.getBoolean(13),
						res.getBoolean(14), res.getBoolean(15),
						res.getBoolean(16), res.getBoolean(17),
						res.getString(18), res.getBoolean(19),
						res.getString(20), res.getString(21),
						res.getString(22), res.getString(23),
						res.getBoolean(24), res.getBoolean(25));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return offre;

	}

	
	@Override
	public List<Offre> findAll() throws DAOException {
		String query = "select id_offre,id_employeur,idrome,datedeb_offre,contrat_autre,"
				+ "contrat_ai,contrat_cae,contrat_avenir,contrat_cdd,contrat_cdi,contrat_alternance,pourvue_offre,annule_offre,autrerecrut_offre  from "
				+ DAOConstants.t_offre + " order by datedeb_offre desc";
		Statement st = null;
		List<Offre> liste = new ArrayList<Offre>();
		ResultSet res = null;

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			while (res.next()) {

				EmployeurDAO empdao = new EmployeurDAO();
				RomeDAO rdao = new RomeDAO();
				Employeur emp = empdao.findByID(res.getInt(2));
				Rome rome = rdao.findByID(res.getInt(3));
				Offre offre = new Offre(res.getInt(1), emp, rome,
						res.getDate(4), res.getBoolean(5), res.getBoolean(6),
						res.getBoolean(7), res.getBoolean(8),
						res.getBoolean(9), res.getBoolean(10),
						res.getBoolean(11), res.getBoolean(12),
						res.getBoolean(13), res.getBoolean(14));
				liste.add(offre);

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

	/** liste les offres non pourvues, non annulees et pas affectées à d'autres recrutement	
	 * 
	 * @return
	 * @throws DAOException
	 */
	public List<Offre> offresNonPourvues() throws DAOException {
		String query = "select id_offre,id_employeur,idrome,datedeb_offre,contrat_autre,"
				+ "contrat_ai,contrat_cae,contrat_avenir,contrat_cdd,contrat_cdi,contrat_alternance,pourvue_offre,annule_offre,autrerecrut_offre  from "
				+ DAOConstants.t_offre
				+ " where pourvue_offre=0 and annule_offre=0 and autrerecrut_offre=0 order by datedeb_offre desc";
		Statement st = null;
		List<Offre> liste = new ArrayList<Offre>();
		ResultSet res = null;

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			while (res.next()) {

				EmployeurDAO empdao = new EmployeurDAO();
				RomeDAO rdao = new RomeDAO();
				Employeur emp = empdao.findByID(res.getInt(2));
				Rome rome = rdao.findByID(res.getInt(3));
				Offre offre = new Offre(res.getInt(1), emp, rome,
						res.getDate(4), res.getBoolean(5), res.getBoolean(6),
						res.getBoolean(7), res.getBoolean(8),
						res.getBoolean(9), res.getBoolean(10),
						res.getBoolean(11), res.getBoolean(12),
						res.getBoolean(13), res.getBoolean(14));
				liste.add(offre);

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

	/** liste les offres pourvues 	
	 * 
	 * @return
	 * @throws DAOException
	 */
	public List<Offre> offresPourvues() throws DAOException {
		String query = "select id_offre,id_employeur,idrome,datedeb_offre,contrat_autre,"
				+ "contrat_ai,contrat_cae,contrat_avenir,contrat_cdd,contrat_cdi,contrat_alternance,pourvue_offre,annule_offre,autrerecrut_offre  from "
				+ DAOConstants.t_offre
				+ " where pourvue_offre=1 order by datedeb_offre desc";
		Statement st = null;
		List<Offre> liste = new ArrayList<Offre>();
		ResultSet res = null;

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			while (res.next()) {

				EmployeurDAO empdao = new EmployeurDAO();
				RomeDAO rdao = new RomeDAO();
				Employeur emp = empdao.findByID(res.getInt(2));
				Rome rome = rdao.findByID(res.getInt(3));
				Offre offre = new Offre(res.getInt(1), emp, rome,
						res.getDate(4), res.getBoolean(5), res.getBoolean(6),
						res.getBoolean(7), res.getBoolean(8),
						res.getBoolean(9), res.getBoolean(10),
						res.getBoolean(11), res.getBoolean(12),
						res.getBoolean(13), res.getBoolean(14));
				liste.add(offre);

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

	public List<Offre> offresAnnulees() throws DAOException {
		String query = "select id_offre,id_employeur,idrome,datedeb_offre,contrat_autre,"
				+ "contrat_ai,contrat_cae,contrat_avenir,contrat_cdd,contrat_cdi,contrat_alternance,pourvue_offre,annule_offre,autrerecrut_offre  from "
				+ DAOConstants.t_offre
				+ " where  annule_offre=1  order by datedeb_offre desc";
		Statement st = null;
		List<Offre> liste = new ArrayList<Offre>();
		ResultSet res = null;

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			while (res.next()) {

				EmployeurDAO empdao = new EmployeurDAO();
				RomeDAO rdao = new RomeDAO();
				Employeur emp = empdao.findByID(res.getInt(2));
				Rome rome = rdao.findByID(res.getInt(3));
				Offre offre = new Offre(res.getInt(1), emp, rome,
						res.getDate(4), res.getBoolean(5), res.getBoolean(6),
						res.getBoolean(7), res.getBoolean(8),
						res.getBoolean(9), res.getBoolean(10),
						res.getBoolean(11), res.getBoolean(12),
						res.getBoolean(13), res.getBoolean(14));
				liste.add(offre);

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

	public List<Offre> offresAutreRecrutement() throws DAOException {
		String query = "select id_offre,id_employeur,idrome,datedeb_offre,contrat_autre,"
				+ "contrat_ai,contrat_cae,contrat_avenir,contrat_cdd,contrat_cdi,contrat_alternance,pourvue_offre,annule_offre,autrerecrut_offre  from "
				+ DAOConstants.t_offre
				+ " where  autrerecrut_offre=1 order by datedeb_offre desc";
		Statement st = null;
		List<Offre> liste = new ArrayList<Offre>();
		ResultSet res = null;

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			while (res.next()) {

				EmployeurDAO empdao = new EmployeurDAO();
				RomeDAO rdao = new RomeDAO();
				Employeur emp = empdao.findByID(res.getInt(2));
				Rome rome = rdao.findByID(res.getInt(3));
				Offre offre = new Offre(res.getInt(1), emp, rome,
						res.getDate(4), res.getBoolean(5), res.getBoolean(6),
						res.getBoolean(7), res.getBoolean(8),
						res.getBoolean(9), res.getBoolean(10),
						res.getBoolean(11), res.getBoolean(12),
						res.getBoolean(13), res.getBoolean(14));
				liste.add(offre);

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

	public List<Offre> statistiquesOffres(java.util.Date deb, java.util.Date fin)
			throws DAOException {
		String query = "select id_offre,idrome  from "
				+ DAOConstants.t_offre + " where  datedeb_offre between '"
				+ deb + "' and '" + fin + "'";
		Statement st = null;
		List<Offre> liste = new ArrayList<Offre>();
		ResultSet res = null;
		RomeDAO rdao = new RomeDAO();
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			while (res.next()) {

				Rome rome = rdao.findByID(res.getInt(2));
				Offre offre = new Offre(res.getInt(1), rome);
				liste.add(offre);

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
	public List<Offre> findByCriteria(Offre obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	public int totalOffres() {
		String query = "select count(*)  from " + DAOConstants.t_offre
				+ " where 1=1 ";
		Statement st = null;
		int cpt = 0;
		ResultSet res = null;

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				cpt = res.getInt(1);
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
		return cpt;
	}

	public int totalOffresAnnulees() {
		String query = "select count(*)  from " + DAOConstants.t_offre
				+ " where annule_offre=1 ";
		Statement st = null;
		int cpt = 0;
		ResultSet res = null;

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				cpt = res.getInt(1);
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
		return cpt;
	}

	public int totalOffresPourvues() {
		String query = "select count(*)  from " + DAOConstants.t_offre
				+ " where pourvue_offre=1 ";
		Statement st = null;
		int cpt = 0;
		ResultSet res = null;

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				cpt = res.getInt(1);
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
		return cpt;
	}

	public int totalOffresAutreRecrute() {
		String query = "select count(*)  from " + DAOConstants.t_offre
				+ " where autrerecrut_offre=1 ";
		Statement st = null;
		int cpt = 0;
		ResultSet res = null;

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				cpt = res.getInt(1);
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
		return cpt;
	}

	public int totalOffresNonPourvues() {
		String query = "select count(*)  from "
				+ DAOConstants.t_offre
				+ "  where pourvue_offre=0 and annule_offre=0 and autrerecrut_offre=0 ";
		Statement st = null;
		int cpt = 0;
		ResultSet res = null;

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				cpt = res.getInt(1);
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
		return cpt;
	}

	@Override
	public int create(Offre obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, obj.getEmployeur().getId_employeur());
			pst.setInt(2, obj.getContact().getId_contact());
			pst.setDate(3, (Date) obj.getDateSaisie());
			pst.setInt(4, obj.getSalarie().getId_salarie());
			pst.setInt(5, obj.getRome().getIdrome());
			pst.setInt(6, obj.getNbpersonnes());
			pst.setDate(7, (Date) obj.getDatedeb_offre());
			pst.setDate(8, (Date) obj.getDatefin_offre());
			pst.setString(9, obj.getDuree_offre());
			pst.setBoolean(10, obj.isContrat_autre());
			pst.setBoolean(11, obj.isContrat_ai());
			pst.setBoolean(12, obj.isContrat_cae());
			pst.setBoolean(13, obj.isContrat_avenir());
			pst.setBoolean(14, obj.isContrat_cdd());
			pst.setBoolean(15, obj.isContrat_cdi());
			pst.setBoolean(16, obj.isContrat_alternance());
			pst.setString(17, obj.getDetail());
			pst.setBoolean(18, obj.isPourvue());
			pst.setString(19, obj.getObservation());
			pst.setString(20, obj.getJour());
			pst.setString(21, obj.getHeures());
			pst.setString(22, obj.getDureestats());
			pst.setBoolean(23, obj.isAnnule_offre());
			pst.setBoolean(24, obj.isAutrerecrute());

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
	public Offre update(Offre obj) throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getId_offre();

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.setInt(1, obj.getEmployeur().getId_employeur());
			pst.setInt(2, obj.getContact().getId_contact());
			pst.setDate(3, (Date) obj.getDateSaisie());
			pst.setInt(4, obj.getSalarie().getId_salarie());
			pst.setInt(5, obj.getRome().getIdrome());
			pst.setInt(6, obj.getNbpersonnes());
			pst.setDate(7, (Date) obj.getDatedeb_offre());
			pst.setDate(8, (Date) obj.getDatefin_offre());
			pst.setString(9, obj.getDuree_offre());
			pst.setBoolean(10, obj.isContrat_autre());
			pst.setBoolean(11, obj.isContrat_ai());
			pst.setBoolean(12, obj.isContrat_cae());
			pst.setBoolean(13, obj.isContrat_avenir());
			pst.setBoolean(14, obj.isContrat_cdd());
			pst.setBoolean(15, obj.isContrat_cdi());
			pst.setBoolean(16, obj.isContrat_alternance());
			pst.setString(17, obj.getDetail());
			pst.setBoolean(18, obj.isPourvue());
			pst.setString(19, obj.getObservation());
			pst.setString(20, obj.getJour());
			pst.setString(21, obj.getHeures());
			pst.setString(22, obj.getDureestats());
			pst.setBoolean(23, obj.isAnnule_offre());
			pst.setBoolean(24, obj.isAutrerecrute());
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
	public int delete(Offre obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int numeroOffre() throws DAOException {
		String query = "select max(id_offre) from " + DAOConstants.t_offre;
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

		return num+1;
	}

	public int totalOffresPourvues(java.util.Date deb, java.util.Date fin)
			throws DAOException {
		String query = "select count(id_offre) from " + DAOConstants.t_offre
				+ " where datedeb_offre between '" + deb + "' and '" + fin
				+ "' and pourvue_offre=1";
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

	public int totalOffresNonPourvues(java.util.Date deb, java.util.Date fin)
			throws DAOException {
		String query = "select count(id_offre) from " + DAOConstants.t_offre
				+ " where datedeb_offre between '" + deb + "' and '" + fin
				+ "' and pourvue_offre=0 and annule_offre=0 and autrerecrut_offre=0";
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

	public int totalOffresai(java.util.Date deb, java.util.Date fin)
			throws DAOException {
		String query = "select count(id_offre) from " + DAOConstants.t_offre
				+ " where datedeb_offre between '" + deb + "' and '" + fin
				+ "' and contrat_ai=1";
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
	
	
	public int totalOffresAiCdi(java.util.Date deb, java.util.Date fin)
			throws DAOException {
		String query = "select count(id_offre) from " + DAOConstants.t_offre
				+ " where datedeb_offre between '" + deb + "' and '" + fin
				+ "' and contrat_ai=1 and contrat_cdi=1";
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
	
	
	public int totalOffresAiCdd(java.util.Date deb, java.util.Date fin)
			throws DAOException {
		String query = "select count(id_offre) from " + DAOConstants.t_offre
				+ " where datedeb_offre between '" + deb + "' and '" + fin
				+ "' and contrat_ai=1 and contrat_cdd=1";
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

	public int totalOffresAutre(java.util.Date deb, java.util.Date fin)
			throws DAOException {
		String query = "select count(id_offre) from " + DAOConstants.t_offre
				+ " where datedeb_offre between '" + deb + "' and '" + fin
				+ "' and contrat_autre=1";
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

	public int totalOffresCae(java.util.Date deb, java.util.Date fin)
			throws DAOException {
		String query = "select count(id_offre) from " + DAOConstants.t_offre
				+ " where datedeb_offre between '" + deb + "' and '" + fin
				+ "' and contrat_cae=1";
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

	public int totalOffresAvenir(java.util.Date deb, java.util.Date fin)
			throws DAOException {
		String query = "select count(id_offre) from " + DAOConstants.t_offre
				+ " where datedeb_offre between '" + deb + "' and '" + fin
				+ "' and contrat_avenir=1";
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

	public int totalOffresCdd(java.util.Date deb, java.util.Date fin)
			throws DAOException {
		String query = "select count(id_offre) from " + DAOConstants.t_offre
				+ " where datedeb_offre between '" + deb + "' and '" + fin
				+ "' and contrat_cdd=1";
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

	public int totalOffresCdi(java.util.Date deb, java.util.Date fin)
			throws DAOException {
		String query = "select count(id_offre) from " + DAOConstants.t_offre
				+ " where datedeb_offre between '" + deb + "' and '" + fin
				+ "' and contrat_cdi=1";
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
		}finally{
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return num;
	}

	public int totalOffresAlternance(java.util.Date deb, java.util.Date fin)
			throws DAOException {
		String query = "select count(id_offre) from " + DAOConstants.t_offre
				+ " where datedeb_offre between '" + deb + "' and '" + fin
				+ "' and contrat_alternance=1";
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

	public int totalOffresAnnule(java.util.Date deb, java.util.Date fin)
			throws DAOException {
		String query = "select count(id_offre) from " + DAOConstants.t_offre
				+ " where datedeb_offre between '" + deb + "' and '" + fin
				+ "' and annule_offre=1";
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

	public int totalOffresAutreRecrute(java.util.Date deb, java.util.Date fin)
			throws DAOException {
		String query = "select count(id_offre) from " + DAOConstants.t_offre
				+ " where datedeb_offre between '" + deb + "' and '" + fin
				+ "' and autrerecrut_offre=1";
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

	public int totalOffresUneSemaine(java.util.Date deb, java.util.Date fin)
			throws DAOException {
		String query = "select count(id_offre) from " + DAOConstants.t_offre
				+ " where datedeb_offre between '" + deb + "' and '" + fin
				+ "' and dureestats_offre='< 1 semaine'";
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

	public int totalOffresUnMois(java.util.Date deb, java.util.Date fin)
			throws DAOException {
		String query = "select count(id_offre) from " + DAOConstants.t_offre
				+ " where datedeb_offre between '" + deb + "' and '" + fin
				+ "' and dureestats_offre='< 1 mois'";
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

	public int totalOffresUnSemestre(java.util.Date deb, java.util.Date fin)
			throws DAOException {
		String query = "select count(id_offre) from " + DAOConstants.t_offre
				+ " where datedeb_offre between '" + deb + "' and '" + fin
				+ "' and dureestats_offre='de 1 à 6 mois'";
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

	public int totalOffresMoinsUnAn(java.util.Date deb, java.util.Date fin)
			throws DAOException {
		String query = "select count(id_offre) from " + DAOConstants.t_offre
				+ " where datedeb_offre between '" + deb + "' and '" + fin
				+ "' and dureestats_offre='de 6 mois à 1 an'";
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

	public int totalOffresPlusUnAn(java.util.Date deb, java.util.Date fin)
			throws DAOException {
		String query = "select count(id_offre) from " + DAOConstants.t_offre
				+ " where datedeb_offre between '" + deb + "' and '" + fin
				+ "' and dureestats_offre='> 1 an'";
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
		}finally{
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return num;
	}
	
	
	public int totalOffresFourchette(java.util.Date deb, java.util.Date fin)
			throws DAOException {
		String query = "select count(id_offre) from " + DAOConstants.t_offre
				+ " where datedeb_offre between '" + deb + "' and '" + fin+"'";
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
		}finally{
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return num;
	}
	
	
	public int totalOffresParMetierRome(java.util.Date deb, java.util.Date fin,int code)
			throws DAOException {
		String query = "select count(idrome) as rome from " + DAOConstants.t_offre
				+ " where idrome="+code+"  and datedeb_offre between '" + deb + "' and '" + fin+"' ";
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
		}finally{
		// Fermeture du resultset
		DAOUtil.closeResultSet(res);

		// Fermeture du statement
		DAOUtil.closeStatement(st);
		}

		return num;
	}
	
	public List<Offre> offresParEmployeur(Employeur employeur)throws DAOException{
		String query = "select id_offre from " + DAOConstants.t_offre
				+ " where id_employeur="+employeur.getId_employeur();
		Statement st = null;
		ResultSet res = null;
		List<Offre> liste=new ArrayList<Offre>();
		Offre offre=null;
		

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			while(res.next()) {
				offre=this.findByID(res.getInt(1));
				liste.add(offre);
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return liste;
		
	}
	
	
}