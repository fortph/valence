package beans.parametres.accueil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import dao.DAOConstants;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.interfaces.DAO;

public class OrganismeDAO implements DAO<Organisme> {

	private Connection connect = null;
	
	
	final private String query_insert = "insert into "
			+ DAOConstants.t_organisme
			+ "(rs_organisme,adr1_organisme,adr2_organisme,cp_organisme,ville_organisme,tel_organisme,fax_organisme,mail_organisme,"
			+ "siret_organisme,agrement_organisme,presid_organisme,titre_presid_organisme,"
			+ "structure_organisme,ape_organisme,legal,urssaf,agrementSAP,directeur) "
			+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	final private String query_update = "update "
			+ DAOConstants.t_sapavenant
			+ " set rs_organisme=?,adr1_organisme=?,adr2_organisme=?,cp_organisme=?,ville_organisme=?,tel_organisme=?,fax_organisme=?,mail_organisme=?,"
			+ "siret_organisme=?,agrement_organisme=?,presid_organisme=?,titre_presid_organisme=?,"
			+ "structure_organisme=?,ape_organisme=?,legal=?,urssaf=?,agrementSAP=?,directeur =? "
			+ " where id_organisme=";

	public OrganismeDAO() {
		connect = DBConnexion.getConnexion();
	}

	@Override
	public Organisme findByID(int id) throws DAOException {
		String query = "select * from " + DAOConstants.t_organisme
				+ " where  id_organisme=" + id;
		Organisme contrat = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				contrat = new Organisme((int) id, res.getString(2),
						res.getString(3), res.getString(4), res.getString(5),
						res.getString(6), res.getString(7), res.getString(8),
						res.getString(9), res.getString(10), res.getString(11),
						res.getString(12), res.getString(13),
						res.getString(14), res.getString(15),
						res.getString(16), res.getString(17),res.getString(18),res.getString(19)
						);

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

		return contrat;
	}

	@Override
	public List<Organisme> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Organisme> findByCriteria(Organisme obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(Organisme obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);

			pst.setString(1,obj.getRs());
			pst.setString(1,obj.getAdr1());
			pst.setString(1,obj.getAdr2());
			pst.setString(1,obj.getCp());
			pst.setString(1,obj.getVille());
			pst.setString(1,obj.getTel());
			pst.setString(1,obj.getFax());
			pst.setString(1,obj.getMail());
			pst.setString(1,obj.getSiret());
			pst.setString(1,obj.getAgrement());
			pst.setString(1,obj.getPresident());
			pst.setString(1,obj.getTitre());
			pst.setString(1,obj.getStructure());
			pst.setString(1,obj.getApe());
			pst.setString(1,obj.getLegal());
			pst.setString(1,obj.getUrssaf());
			pst.setString(1,obj.getAgrementsap());
			pst.setString(1,obj.getDirecteur());
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
			try {
				pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return cle;
	
	}

	@Override
	public Organisme update(Organisme obj) throws DAOException {
		PreparedStatement pst = null;
		String query = query_update + obj.getId();

		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.setString(1,obj.getRs());
			pst.setString(1,obj.getAdr1());
			pst.setString(1,obj.getAdr2());
			pst.setString(1,obj.getCp());
			pst.setString(1,obj.getVille());
			pst.setString(1,obj.getTel());
			pst.setString(1,obj.getFax());
			pst.setString(1,obj.getMail());
			pst.setString(1,obj.getSiret());
			pst.setString(1,obj.getAgrement());
			pst.setString(1,obj.getPresident());
			pst.setString(1,obj.getTitre());
			pst.setString(1,obj.getStructure());
			pst.setString(1,obj.getApe());
			pst.setString(1,obj.getLegal());
			pst.setString(1,obj.getUrssaf());
			pst.setString(1,obj.getAgrementsap());
			pst.setString(1,obj.getDirecteur());
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
			try {
				pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return obj;
	}

	@Override
	public int delete(Organisme obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

}
