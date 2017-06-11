package dao.imp.employeur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.employeurs.Employeur;
import beans.parametres.employeur.Activite;
import beans.parametres.employeur.ActiviteDAO;
import beans.parametres.employeur.Statut;
import beans.parametres.employeur.StatutDAO;
import beans.parametres.employeur.Structure;
import beans.parametres.employeur.StructureDAO;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.interfaces.DAO;

public class EmployeurDAO implements DAO<Employeur> {

	final private String query_insert = "insert into "
			+ DAOConstants.t_employeur
			+ "( civilemp_employeur,rs_employeur,rsstatut_employeur,adr1_employeur,"
			+ "adr2_employeur,"
			+ "cp_employeur,ville_employeur,tel1_employeur,tel2_employeur,"
			+ "fax_employeur,mail_employeur,structure_employeur,observ_employeur,"
			+ "id_activite,datecrea_employeur,civresp_employeur,nomresp_employeur,"
			+ "prenomresp_employeur,rangresp_employeur, actif,datemodif_employeur,"
			+ "siret_employeur,ape_employeur,rm_employeur)"
			+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	final private String query_update = "update "
			+ DAOConstants.t_employeur
			+ " set civilemp_employeur=?,rs_employeur=?,rsstatut_employeur=?,adr1_employeur=?,adr2_employeur=?,"
			+ "cp_employeur=?,ville_employeur=?,tel1_employeur=?,tel2_employeur=?,"
			+ "fax_employeur=?,mail_employeur=?,structure_employeur=?,observ_employeur=?,"
			+ "id_activite=?,datecrea_employeur=?,civresp_employeur=?,nomresp_employeur=?,"
			+ "prenomresp_employeur=?,rangresp_employeur=?, actif=?,datemodif_employeur=?,"
			+ "siret_employeur=?,ape_employeur=?,rm_employeur=?"
			+ " where id_employeur=";

	private Connection connect = null;

	public EmployeurDAO() {
		connect = DBConnexion.getConnexion();

	}

	@Override
	public Employeur findByID(int id) throws DAOException {
		String query = "select  `id_employeur`, `civilemp_employeur`, `rs_employeur`, "
				+ "`rsstatut_employeur`, `adr1_employeur`, `adr2_employeur`,"
				+ " `cp_employeur`, `ville_employeur`, `tel1_employeur`, `tel2_employeur`, "
				+ "`fax_employeur`, `mail_employeur`, `structure_employeur`,"
				+ " `observ_employeur`, `id_activite`, `datecrea_employeur`,"
				+ " `civresp_employeur`, `nomresp_employeur`, `prenomresp_employeur`,"
				+ " `rangresp_employeur`, `actif`, `datemodif_employeur`,"
				+ " `siret_employeur`, `ape_employeur`, `rm_employeur` from " + DAOConstants.t_employeur
				+ " where  id_employeur=" + id;
		Employeur employeur = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				employeur = new Employeur((int) id, res.getString(2),
						res.getString(3), new StatutDAO().findByID(res
								.getInt(4)), res.getString(5),
						res.getString(6), res.getString(7), res.getString(8),
						res.getString(9), res.getString(10), res.getString(11),
						res.getString(12), new StructureDAO().findByID(res
								.getInt(13)), res.getString(14),
						new ActiviteDAO().findByID(res.getInt(15)),
						res.getDate(16), res.getString(17), res.getString(18),
						res.getString(19), res.getString(20),
						res.getBoolean(21), res.getDate(22), res.getString(23),
						res.getString(24), res.getString(25));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return employeur;

	}

	@Override
	public List<Employeur> findAll() throws DAOException {
		List<Employeur> liste = new ArrayList<Employeur>();
		Statement stat = null;
		ResultSet res = null;
		String req = "select  `id_employeur`, `civilemp_employeur`, `rs_employeur`, "
				+ "`rsstatut_employeur`, `adr1_employeur`, `adr2_employeur`,"
				+ " `cp_employeur`, `ville_employeur`, `tel1_employeur`, `tel2_employeur`, "
				+ "`fax_employeur`, `mail_employeur`, `structure_employeur`,"
				+ " `observ_employeur`, `id_activite`, `datecrea_employeur`,"
				+ " `civresp_employeur`, `nomresp_employeur`, `prenomresp_employeur`,"
				+ " `rangresp_employeur`, `actif`, `datemodif_employeur`,"
				+ " `siret_employeur`, `ape_employeur`, `rm_employeur` from " + DAOConstants.t_employeur
				+ " where actif=1 and id_employeur!=1  order by rs_employeur asc";

		Employeur employeur = null;
		try {

			stat = connect.createStatement();
			res = stat.executeQuery(req);

			StatutDAO statdao = new StatutDAO();
			Statut statut = null;
			StructureDAO structdao = new StructureDAO();
			Structure structure = null;
			ActiviteDAO actdao = new ActiviteDAO();
			Activite activite = null;

			while (res.next()) {
				statut = statdao.findByID(res.getInt(4));
				structure = structdao.findByID(res.getInt(13));
				activite = actdao.findByID(res.getInt(15));

				employeur = new Employeur(res.getInt(1), res.getString(2),
						res.getString(3), statut, res.getString(5),
						res.getString(6), res.getString(7), res.getString(8),
						res.getString(9), res.getString(10), res.getString(11),
						res.getString(12), structure, res.getString(14),
						activite, res.getDate(16), res.getString(17),
						res.getString(18), res.getString(19),
						res.getString(20), res.getBoolean(21), res.getDate(22),
						res.getString(23), res.getString(24), res.getString(25));
				liste.add(employeur);
			}

		} catch (SQLException ex) {
			System.out.print("Probl�me " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return liste;

	}

	/** affiche liste de tous les employeurs dont le nom commence par les lettres saisies
	 * 
	 * @param nom
	 * @return
	 * @throws DAOException
	 */
	public List<Employeur> findByName(String nom) throws DAOException {
		ArrayList<Employeur> liste = new ArrayList<Employeur>();
		Employeur employeur = null;
		Statement st = null;
		ResultSet res = null;
		String req = "select "
				+ DAOConstants.t_employeur
				+ ".id_employeur,rs_employeur,ville_employeur,tel1_employeur,"
				+ "fax_employeur,nomresp_employeur,prenomresp_employeur,structure_employeur "
				+ " from " + DAOConstants.t_employeur + "  inner join "
				+ DAOConstants.t_employeurstructure + " on "
				+ DAOConstants.t_employeurstructure + ".id_structure="
				+ DAOConstants.t_employeur + ".structure_employeur "
				+ " where rs_employeur like '" + nom
				+ "%'   order by rs_employeur asc ";

		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {

				StructureDAO stdao = new StructureDAO();
				Structure structure = stdao.findByID(res.getInt(8));
				employeur = new Employeur(res.getInt(1), res.getString(2),
						res.getString(3), res.getString(4), res.getString(5),
						res.getString(6), res.getString(7), structure);
				liste.add(employeur);

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
	public List<Employeur> findByCriteria(Employeur obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(Employeur obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, obj.getCivemp());
			pst.setString(2, obj.getRs_employeur().toUpperCase().trim());
			pst.setInt(3, obj.getRsstatut_employeur().getId_statemployeur());
			pst.setString(4, obj.getAdr1());
			pst.setString(5, obj.getAdr2());
			pst.setString(6, obj.getCp());
			pst.setString(7, obj.getVille());
			pst.setString(8, obj.getTel1());
			pst.setString(9, obj.getTel2());
			pst.setString(10, obj.getFax());
			pst.setString(11, obj.getMail());
			pst.setInt(12, obj.getStructure().getId_structure());
			pst.setString(13, obj.getObservation());
			pst.setInt(14, obj.getActivite().getId_activite());
			pst.setDate(15, (java.sql.Date) obj.getDatecreation());
			pst.setString(16, obj.getCivresp());
			pst.setString(17, obj.getNomresponsable());
			pst.setString(18, obj.getPrenomresponsable());
			pst.setString(19, obj.getRangresponsable());
			pst.setBoolean(20, obj.isActif());
			pst.setDate(21, (java.sql.Date) obj.getDatemodif());
			pst.setString(22, obj.getSiret());
			pst.setString(23, obj.getApe());
			pst.setString(24, obj.getRm());

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
	public Employeur update(Employeur obj) throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getId_employeur();

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.setString(1, obj.getCivemp());
			pst.setString(2, obj.getRs_employeur().toUpperCase().trim());
			pst.setInt(3, obj.getRsstatut_employeur().getId_statemployeur());
			pst.setString(4, obj.getAdr1());
			pst.setString(5, obj.getAdr2());
			pst.setString(6, obj.getCp());
			pst.setString(7, obj.getVille());
			pst.setString(8, obj.getTel1());
			pst.setString(9, obj.getTel2());
			pst.setString(10, obj.getFax());
			pst.setString(11, obj.getMail());
			pst.setInt(12, obj.getStructure().getId_structure());
			pst.setString(13, obj.getObservation());
			pst.setInt(14, obj.getActivite().getId_activite());
			pst.setDate(15, (java.sql.Date) obj.getDatecreation());
			pst.setString(16, obj.getCivresp());
			pst.setString(17, obj.getNomresponsable());
			pst.setString(18, obj.getPrenomresponsable());
			pst.setString(19, obj.getRangresponsable());
			pst.setBoolean(20, obj.isActif());
			pst.setDate(21, (java.sql.Date) obj.getDatemodif());
			pst.setString(22, obj.getSiret());
			pst.setString(23, obj.getApe());
			pst.setString(24, obj.getRm());
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
	public int delete(Employeur obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}
	/**affiche la liste de tous les employeurs actifs
	 * 
	 * @return
	 * @throws DAOException
	 */
	public List<String> afficherEmployeurs() throws DAOException {
		List<String> liste = null;
		Statement stat = null;
		ResultSet rs = null;
		String req = "select rs_employeur,id_employeur from " + DAOConstants.t_employeur
				+ " where actif=1 and id_employeur!=1 order by rs_employeur asc";
		

		try {
			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			liste = new ArrayList<String>();
			while (rs.next()){
				String a=rs.getString(1);
				int b=rs.getInt(2);
				String tout=a+"-"+b;
				liste.add(tout);}

		} catch (SQLException ex) {
			System.out.print("Problème " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return liste;

	}

	public List<Employeur> afficherEmployeursParNom(String nom)
			throws DAOException {
		List<Employeur> liste = new ArrayList<Employeur>();
		Employeur employeur = null;
		Statement stat = null;
		ResultSet rs = null;
		String req = "select rs_employeur,id_employeur from "
				+ DAOConstants.t_employeur + " where rs_employeur like '" + nom
				+ "%' order by rs_employeur asc";

		try {
			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			while (rs.next()) {
				employeur = new Employeur(rs.getString(1), rs.getInt(2));
				liste.add(employeur);
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

	/** affiche la liste des employeurs correspondant à la structure 	
	 * 
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public List<Employeur> afficherEmployeursParStructure(int id)
			throws DAOException {
		List<Employeur> liste = new ArrayList<Employeur>();
		Employeur employeur = null;
		Statement stat = null;
		ResultSet rs = null;
		String req = "select id_employeur ,rs_employeur,ville_employeur,tel1_employeur,fax_employeur,nomresp_employeur,prenomresp_employeur  from "
				+ DAOConstants.t_employeur
				+ " where structure_employeur="
				+ id
				+ " and actif=1 order by rs_employeur asc";

		try {
			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			while (rs.next()) {
				employeur = new Employeur(rs.getInt(1), rs.getString(2),
						rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7));
				liste.add(employeur);
			}
		} catch (SQLException ex) {
			System.out.print("Problème " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return liste;

	}
	
	
	/** affiche la liste des employeurs correspondant à l'activité 	
	 * 
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public List<Employeur> afficherEmployeursParActivite(int id)
			throws DAOException {
		List<Employeur> liste = new ArrayList<Employeur>();
		Employeur employeur = null;
		Statement stat = null;
		ResultSet rs = null;
		String req = "select id_employeur ,rs_employeur,ville_employeur,tel1_employeur,fax_employeur,nomresp_employeur,prenomresp_employeur  from "
				+ DAOConstants.t_employeur
				+ " where id_activite="
				+ id
				+ " and actif=1 order by rs_employeur asc";

		try {
			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			while (rs.next()) {
				employeur = new Employeur(rs.getInt(1), rs.getString(2),
						rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7));
				liste.add(employeur);
			}
		} catch (SQLException ex) {
			System.out.print("Problème " + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return liste;

	}
	
	/** affiche la liste des employeurs actifs dans le fourchette de date indiquee 	
	 * 
	 * @param deb
	 * @param fin
	 * @return
	 * @throws DAOException
	 */
	public List<Employeur> afficherEmployeursActifsDansFourchette(java.util.Date deb,java.util.Date fin)
			throws DAOException {
		List<Employeur> liste = new ArrayList<Employeur>();
		Employeur employeur = null;
		Statement stat = null;
		ResultSet rs = null;
		String req = "select distinct "
				+ DAOConstants.t_employeur
				+".id_employeur ,rs_employeur,tel1_employeur,fax_employeur,datecrea_employeur  from "
				+ DAOConstants.t_employeur
				+" left outer join "
				+DAOConstants.t_offre
				+" on "
				+ DAOConstants.t_employeur
				+".id_employeur="
				+DAOConstants.t_offre
				+".id_employeur "
				
				+" left outer join "
				+DAOConstants.t_aicontrat
				+" on "
				+ DAOConstants.t_employeur
				+".id_employeur="
				+DAOConstants.t_aicontrat
				+".id_employeur "
				
				+" left outer join "
				+DAOConstants.t_aiavenant
				+" on "
				+ DAOConstants.t_aicontrat
				+".idaicontrat="
				+DAOConstants.t_aiavenant
				+".idaicontrat"
				
				+" left outer  join "
				+DAOConstants.t_sapprestation
				+" on "
				+ DAOConstants.t_aicontrat
				+".id_employeur="
				+DAOConstants.t_sapprestation
				+".id_employeur"
				
				+" left outer join "
				+DAOConstants.t_sapprestationaven
				+" on "
				+ DAOConstants.t_sapprestation
				+".id_prestationcontrat="
				+DAOConstants.t_sapprestationaven
				+".id_prestationcontrat"
				
				+ " where "
				+ DAOConstants.t_employeur
				+".id_employeur!=1 and datesaisie_offre between '"+deb+"' and '"+fin+"' or "
						+ "  datedebcontrat_aicontrat between '"+deb+"' and '"+fin+"' "
						+" or datefincontrat_aicontrat between '"+deb+"' and '"+fin+"' "
						+" or datedeb_aiavenant between '"+deb+"' and '"+fin+"' "
						+" or datefin_aiavenant between '"+deb+"' and '"+fin+"' "
						+ " or "
						+DAOConstants.t_sapprestation
						+".datedebut_pr between '"+deb+"' and '"+fin+"' "
						+" or "
						+DAOConstants.t_sapprestation
						+".datefin_pr between '"+deb+"' and '"+fin+"' "
						+" or "
						+DAOConstants.t_sapprestationaven
						+".datedebut_pr between '"+deb+"' and '"+fin+"' "
						+" or "
						+DAOConstants.t_sapprestationaven
						+".datefin_pr between '"+deb+"' and '"+fin+"' "
						
						
						
								+ "  order by rs_employeur asc";
				
		try {
			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			while (rs.next()) {
				employeur = new Employeur(rs.getInt(1), rs.getString(2),
						 rs.getString(3), rs.getString(4),rs.getDate(5)
						);
				liste.add(employeur);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		
		return liste;	
		}
		
		
	}


