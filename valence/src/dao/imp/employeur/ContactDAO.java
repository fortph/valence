package dao.imp.employeur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.employeurs.Contact;
import beans.employeurs.Employeur;
import beans.employeurs.Service;

import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.interfaces.DAO;

public class ContactDAO implements DAO<Contact> {
	final private String query_insert = "insert into "
			+ DAOConstants.t_employeurcontact
			+ "( id_employeur,id_employeurservice,civ_contact,nom_contact,prenom_contact,tel_contact,fax_contact,portable_contact,mail_contact,rang_contact,actif)"
			+ " values (?,?,?,?,?,?,?,?,?,?,?)";

	final private String query_update = "update "
			+ DAOConstants.t_employeurcontact
			+ " set id_employeur=?,id_employeurservice=?,civ_contact=?,nom_contact=?,prenom_contact=?,"
			+ "tel_contact=?,fax_contact=?,portable_contact=?,mail_contact=?,"
			+ "rang_contact=?,actif=? " + " where id_contact=";

	private Connection connect = null;

	public ContactDAO() {
		connect = DBConnexion.getConnexion();

	}

	@Override
	public Contact findByID(int id) throws DAOException {
		String query = "select `id_contact`, `id_employeur`, `id_employeurservice`,"
				+ " `civ_contact`, `nom_contact`, `prenom_contact`, `tel_contact`, "
				+ "`fax_contact`, `portable_contact`, `mail_contact`,"
				+ " `rang_contact`, `actif` from " + DAOConstants.t_employeurcontact
				+ " where  id_contact=" + id;

		Contact contact = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				contact = new Contact(id, new EmployeurDAO().findByID(res
						.getInt(2)), new ServiceDAO().findByID(res.getInt(3)),
						res.getString(4), res.getString(5), res.getString(6),
						res.getString(7), res.getString(8), res.getString(9),
						res.getString(10), res.getString(11),
						res.getBoolean(12));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(res);

			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return contact;

	}

	@Override
	public List<Contact> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Contact> findServiceEmployeur(int id_serv, int id_emp)
			throws DAOException {
		String req = " select `id_contact`, `id_employeur`, `id_employeurservice`, "
				+ "`civ_contact`, `nom_contact`, `prenom_contact`, `tel_contact`,"
				+ " `fax_contact`, `portable_contact`, `mail_contact`, `rang_contact`,"
				+ " `actif` from " + DAOConstants.t_employeurcontact
				+ " where id_employeur=" + id_emp + " and id_employeurservice="
				+ id_serv + " order by nom_contact asc";

		List<Contact> liste = new ArrayList<Contact>();
		Contact contact = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {

				EmployeurDAO empdao = new EmployeurDAO();
				Employeur emp = empdao.findByID(id_emp);
				ServiceDAO serdao = new ServiceDAO();
				Service serv = serdao.findByID(id_serv);

				contact = new Contact(res.getInt(1), emp, serv,
						res.getString(4), res.getString(5), res.getString(6),
						res.getString(7), res.getString(8), res.getString(9),
						res.getString(10), res.getString(11),
						res.getBoolean(12));
				liste.add(contact);
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
	public List<Contact> findByCriteria(Contact obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(Contact obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);

			pst.setInt(1, obj.getEmployeur().getId_employeur());
			pst.setInt(2, obj.getService().getId_employeurservice());
			pst.setString(3, obj.getCiv_contact());
			pst.setString(4, obj.getNom_contact());
			pst.setString(5, obj.getPrenom_contact());
			pst.setString(6, obj.getTel_contact());
			pst.setString(7, obj.getFax_contact());
			pst.setString(8, obj.getPortable_contact());
			pst.setString(9, obj.getMail_contact());
			pst.setString(10, obj.getRang_contact());
			pst.setBoolean(11, obj.isActif());
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
	public Contact update(Contact obj) throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getId_contact();

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);

			pst.setInt(1, obj.getEmployeur().getId_employeur());
			pst.setInt(2, obj.getService().getId_employeurservice());
			pst.setString(3, obj.getCiv_contact());
			pst.setString(4, obj.getNom_contact());
			pst.setString(5, obj.getPrenom_contact());
			pst.setString(6, obj.getTel_contact());
			pst.setString(7, obj.getFax_contact());
			pst.setString(8, obj.getPortable_contact());
			pst.setString(9, obj.getMail_contact());
			pst.setString(10, obj.getRang_contact());
			pst.setBoolean(11, obj.isActif());
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
	public int delete(Contact obj) throws DAOException {
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		String req = " update " + DAOConstants.t_employeurcontact
				+ " set actif=0 where id_contact=" + id;
		Statement stat = null;
		int nb = 0;
		try {
			stat = connect.createStatement();
			int x = stat.executeUpdate(req);
			if (x == 0)
				nb = 1;
			else
				nb = 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		finally{
			
			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return nb;
	}
	/** affiche les noms et prénoms de tous les contacts de l'employeur
	 * 
	 * @param id_emp
	 * @return
	 * @throws DAOException
	 */
	public List<String> afficherNomContacts(int id_emp) throws DAOException {
		List<String> liste = null;
		Statement stat = null;
		ResultSet rs = null;
		String req = "select nom_contact,prenom_contact from "
				+ DAOConstants.t_employeurcontact + " where id_employeur="
				+ id_emp + " and actif=1 order by nom_contact asc";

		try {
			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			liste = new ArrayList<String>();
			while (rs.next()) {
				String nom = rs.getString(1);
				String prenom = rs.getString(2);
				String tout = nom + " " + prenom;
				liste.add(tout);

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

	public List<Contact> afficherListeContacts(String id_emp)
			throws DAOException {
		List<Contact> liste = new ArrayList<Contact>();
		EmployeurDAO empdao = new EmployeurDAO();
		ServiceDAO serdao = new ServiceDAO();
		Contact contact = null;
		Statement stat = null;
		ResultSet rs = null;
		String req = "select `id_contact`, `id_employeur`, `id_employeurservice`,"
				+ " `civ_contact`, `nom_contact`, `prenom_contact`, `tel_contact`, "
				+ "`fax_contact`, `portable_contact`, `mail_contact`, `rang_contact`, "
				+ "`actif` from " + DAOConstants.t_employeurcontact
				+ " where id_employeur=" + id_emp
				+ " and actif=1 order by nom_contact asc";

		try {
			stat = connect.createStatement();
			rs = stat.executeQuery(req);

			while (rs.next()) {
				contact = new Contact(rs.getInt(1), empdao.findByID(rs
						.getInt(2)), serdao.findByID(rs.getInt(3)),
						rs.getString(4), rs.getString(5), rs.getString(6),
						rs.getString(7), rs.getString(8), rs.getString(9),
						rs.getString(10), rs.getString(11), rs.getBoolean(12));
				liste.add(contact);

			}

		} catch (SQLException ex) {
			System.out.print("Problème" + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return liste;

	}

	public Contact afficherPremierContact(String id_emp, String nomcontact)
			throws DAOException {
		EmployeurDAO empdao = new EmployeurDAO();
		ServiceDAO serdao = new ServiceDAO();
		Contact contact = null;
		Statement stat = null;
		ResultSet rs = null;
		String req = "select `id_contact`, `id_employeur`, `id_employeurservice`,"
				+ " `civ_contact`, `nom_contact`, `prenom_contact`, "
				+ "`tel_contact`, `fax_contact`, `portable_contact`, "
				+ "`mail_contact`, `rang_contact`, `actif` from " + DAOConstants.t_employeurcontact
				+ " where id_employeur=" + id_emp + " and (trim(nom_contact) like '"
				+nomcontact+"' or concat(trim(nom_contact),' ',trim(prenom_contact)) like '"
				+ nomcontact + "' or concat(trim(nom_contact),'  ',trim(prenom_contact)) like '"
				+ nomcontact + "') and actif=1 limit 1";

		try {
			stat = connect.createStatement();
			rs = stat.executeQuery(req);

			if (rs.first()) {
				contact = new Contact(rs.getInt(1), empdao.findByID(rs
						.getInt(2)), serdao.findByID(rs.getInt(3)),
						rs.getString(4), rs.getString(5), rs.getString(6),
						rs.getString(7), rs.getString(8), rs.getString(9),
						rs.getString(10), rs.getString(11), rs.getBoolean(12));
				

			}

		} catch (SQLException ex) {
			System.out.print("Problème" + ex);
		} finally {
			// Fermeture du resultset
			DAOUtil.closeResultSet(rs);

			// Fermeture du statement
			DAOUtil.closeStatement(stat);
		}
		return contact;

	}

}
