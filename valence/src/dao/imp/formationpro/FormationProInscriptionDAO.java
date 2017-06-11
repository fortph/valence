package dao.imp.formationpro;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.formationpro.FormationProEmployeur;
import beans.formationpro.FormationProInscription;
import beans.identite.Identite;
import beans.parametres.formationpro.Formprofinancement;
import beans.parametres.formationpro.FormprofinancementDAO;
import beans.parametres.formationpro.Formproniveau;
import beans.parametres.formationpro.FormproniveauDAO;
import beans.parametres.formationpro.Formprostatutemployeur;
import beans.parametres.formationpro.FormprostatutemployeurDAO;
import beans.parametres.formationpro.Formprotheme;
import beans.parametres.formationpro.FormprothemeDAO;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.imp.identite.IdentiteDAO;
import dao.interfaces.DAO;
import divers.FormaterDate;

public class FormationProInscriptionDAO implements DAO<FormationProInscription> {

	final private String query_insert = "insert into "
			+ DAOConstants.t_formproinscription
			+ "(id_theme,id_niveau,nbheures,id_statut,debutformation,id_finance,"
			+ "id_employeur,id_identite,montant,enregistrement)"
			+ " values (?,?,?,?,?,?,?,?,?,?)";

	final private String query_update = "update "
			+ DAOConstants.t_formproinscription
			+ " set id_theme=?, id_niveau=?,nbheures=?,id_statut=?,  "
			+ " debutformation=?,id_finance=?,id_employeur=?,id_identite=?,montant=?,enregistrement=? "
			+ "where id_inscription=";
	
	
	private Connection connect = null;

	public FormationProInscriptionDAO() {
		connect = DBConnexion.getConnexion();
	}

	@Override
	public FormationProInscription findByID(int id) throws DAOException {
		String query = "select `id_inscription`, `id_theme`, `id_niveau`,"
				+ " `nbheures`, `id_statut`, `debutformation`,"
				+ " `id_finance`, `id_employeur`, `id_identite`, "
				+ "`montant`, `enregistrement` from "
				+ DAOConstants.t_formproinscription
				+ " where id_inscription=" + id;
		FormationProInscription form = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				FormprothemeDAO themedao = new FormprothemeDAO();
				Formprotheme them = themedao.findByID(res.getInt(2));
				FormproniveauDAO nivdao = new FormproniveauDAO();
				Formproniveau niv = nivdao.findByID(res.getInt(3));
				FormprofinancementDAO formdao = new FormprofinancementDAO();
				Formprofinancement finance = formdao.findByID(res.getInt(7));
				IdentiteDAO idedao = new IdentiteDAO();
				Identite identite = idedao.findByID(res.getInt(9));
				FormationProEmployeurDAO empdao = new FormationProEmployeurDAO();
				FormationProEmployeur employ = empdao.findByID(res.getInt(8));
				FormprostatutemployeurDAO statdao = new FormprostatutemployeurDAO();
				Formprostatutemployeur statut = statdao.findByID(res.getInt(5));
				form = new FormationProInscription(id, them, niv,
						res.getInt(4), statut, res.getDate(6), finance, employ,
						identite, res.getFloat(10), res.getDate(11));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return form;
	}

	@Override
	public List<FormationProInscription> findAll() throws DAOException {
		List<FormationProInscription> liste = new ArrayList<FormationProInscription>();
		Statement stat = null;
		ResultSet rs = null;
		String req = "select `id_inscription`, `id_theme`, `id_niveau`,"
				+ " `nbheures`, `id_statut`, `debutformation`,"
				+ " `id_finance`, `id_employeur`, `id_identite`, "
				+ "`montant`, `enregistrement` from " + DAOConstants.t_formproinscription
				+ " order by nomformation asc";

		FormationProInscription formation = null;
		try {

			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			FormprothemeDAO themedao = new FormprothemeDAO();
			Formprotheme them = null;
			FormproniveauDAO nivdao = new FormproniveauDAO();
			Formproniveau niv = null;
			FormprofinancementDAO findao = new FormprofinancementDAO();
			Formprofinancement finance = null;
			IdentiteDAO idedao = new IdentiteDAO();
			Identite identite = null;
			FormationProEmployeurDAO empdao = new FormationProEmployeurDAO();
			FormationProEmployeur employ = null;
			FormprostatutemployeurDAO statdao = new FormprostatutemployeurDAO();
			Formprostatutemployeur statut = null;
			while (rs.next()) {
				niv = nivdao.findByID(rs.getInt(3));
				them = themedao.findByID(rs.getInt(2));
				finance = findao.findByID(rs.getInt(7));
				identite = idedao.findByID(rs.getInt(9));
				employ = empdao.findByID(8);
				statut = statdao.findByID(5);
				formation = new FormationProInscription(rs.getInt(1), them,
						niv, rs.getInt(4), statut, rs.getDate(6), finance,
						employ, identite, rs.getFloat(10), rs.getDate(11));
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

	/** liste les formations suivies par la personne dont l'id est indiqué	
	 * 
	 * @param identite
	 * @return
	 * @throws DAOException
	 */
	public List<FormationProInscription> findByIdentite(Identite identite)
			throws DAOException {
		String query = "select `id_inscription`, `id_theme`, `id_niveau`,"
				+ " `nbheures`, `id_statut`, `debutformation`,"
				+ " `id_finance`, `id_employeur`, `id_identite`, "
				+ "`montant`, `enregistrement` from "
				+ DAOConstants.t_formproinscription
				+ " where 1=1 and  id_identite=" + identite.getId_IDE();
		ArrayList<FormationProInscription> liste = new ArrayList<FormationProInscription>();
		FormationProInscription ins = null;
		Statement st = null;
		ResultSet res = null;

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			while (res.next()) {

				ins = new FormationProInscription(
						res.getInt(1),
						new FormprothemeDAO().findByID(res.getInt(2)),
						new FormproniveauDAO().findByID(res.getInt(3)),
						res.getInt(4),
						new FormprostatutemployeurDAO().findByID(res.getInt(5)),
						res.getDate(6), new FormprofinancementDAO()
								.findByID(res.getInt(7)),
						new FormationProEmployeurDAO().findByID(res.getInt(8)),
						identite, res.getFloat(10), res.getDate(11));
				liste.add(ins);

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
	public List<FormationProInscription> findByCriteria(
			FormationProInscription obj) throws DAOException {
		ArrayList<FormationProInscription> liste = new ArrayList<FormationProInscription>();
		FormationProInscription formation = null;
		Statement st = null;
		ResultSet res = null;

		FormprothemeDAO themedao = new FormprothemeDAO();
		FormproniveauDAO nivdao = new FormproniveauDAO();
		FormprofinancementDAO findao = new FormprofinancementDAO();
		IdentiteDAO idedao = new IdentiteDAO();
		FormationProEmployeurDAO empdao = new FormationProEmployeurDAO();
		FormprostatutemployeurDAO statdao = new FormprostatutemployeurDAO();

		String req = "select `id_inscription`, `id_theme`, `id_niveau`,"
				+ " `nbheures`, `id_statut`, `debutformation`,"
				+ " `id_finance`, `id_employeur`, `id_identite`, "
				+ "`montant`, `enregistrement` from "
				+ DAOConstants.t_formproinscription
				+ " where 1=1 ";
		if (obj.getTheme() != null && obj.getTheme().getTheme() != null)
			req += " and id_theme=" + obj.getTheme().getId_theme();
		if (obj.getEmployeur() != null
				&& obj.getEmployeur().getEmployeur() != null)
			req += " and id_employeur=" + obj.getEmployeur().getId_employeur();
		if (obj.getNbheures() != 0)
			req += " and nbheures=" + obj.getNbheures();
		if (obj.getNiveau() != null && obj.getNiveau().getNiveau() != null)
			req += " and id_niveau=" + obj.getNiveau().getId_niveau();
		if (obj.getDebutformation() != null) {

			req += " and debutformation >='" + obj.getDebutformation() + "' "
					+ " and debutformation < adddate('"
					+ new FormaterDate().formateDate(obj.getDebutformation())
					+ "',interval 1 YEAR)";
		}

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {

				formation = new FormationProInscription(res.getInt(1),
						themedao.findByID(res.getInt(2)), nivdao.findByID(res
								.getInt(3)), res.getInt(4),
						statdao.findByID(res.getInt(5)), res.getDate(6),
						findao.findByID(res.getInt(7)), empdao.findByID(res
								.getInt(8)), idedao.findByID(res.getInt(9)),
						res.getFloat(10), res.getDate(11));
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
	public int create(FormationProInscription obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);

			pst.setInt(1, obj.getTheme().getId_theme());
			pst.setInt(2, obj.getNiveau().getId_niveau());
			pst.setInt(3, obj.getNbheures());
			pst.setInt(4, obj.getStatut().getId_statut());
			pst.setDate(5, (Date) obj.getDebutformation());
			pst.setInt(6, obj.getFinancement().getId_finance());
			if (obj.getStatut().getStatut().equals("Perso"))
				obj.setEmployeur(new FormationProEmployeurDAO()
						.findByName("Perso"));
			pst.setInt(7, obj.getEmployeur().getId_employeur());
			pst.setInt(8, obj.getIdentite().getId_IDE());
			pst.setFloat(9, obj.getMontant());
			pst.setDate(10, (Date) obj.getEnregistrement());

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
	public FormationProInscription update(FormationProInscription obj)
			throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getId_inscription();

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.setInt(1, obj.getTheme().getId_theme());
			pst.setInt(2, obj.getNiveau().getId_niveau());
			pst.setInt(3, obj.getNbheures());
			pst.setInt(4, obj.getStatut().getId_statut());
			pst.setDate(5, (Date) obj.getDebutformation());
			pst.setInt(6, obj.getFinancement().getId_finance());
			pst.setInt(7, obj.getEmployeur().getId_employeur());
			pst.setInt(8, obj.getIdentite().getId_IDE());
			pst.setFloat(9, obj.getMontant());
			pst.setDate(10, (Date) obj.getEnregistrement());
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
	public int delete(FormationProInscription obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}
	/** permet de recuperer les heures formateurs	
	 * 
	 * @param liste
	 * @return
	 * @throws DAOException
	 */
	public int nombreHeuresFormateur(List<FormationProInscription> liste) throws DAOException {
		int total=0;
		boolean vrai=true;
		for(int i=0;i<(liste.size())-1;i++){
			
			FormationProInscription fp=new FormationProInscriptionDAO().findByID(liste.get(i).getId_inscription());
			
			for(int j=i+1;j<(liste.size());j++){
			/* on considere un groupe par  une formation qui a:
			 * le meme theme
			 * le meme niveau
			 * le meme nombre d'heures
			 
			 * la meme date de debut	
			 */
			if(fp.getTheme().getId_theme()==liste.get(j).getTheme().getId_theme() &&
					fp.getNiveau().getId_niveau()==liste.get(j).getNiveau().getId_niveau() &&
					fp.getNbheures()==liste.get(j).getNbheures() &&
					fp.getDebutformation()==liste.get(j).getDebutformation()
					)
			{vrai=false;
					
			}if(vrai){
				total+=liste.get(j).getNbheures();
				liste.remove(j);
				j--;}
			}
			
		
	}
	

		return total;
	}
}
