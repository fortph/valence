package dao.imp.identite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.identite.Identite;
import beans.identite.ProfilRecherche;
import beans.parametres.accueil.Rome;
import beans.parametres.accueil.RomeDAO;
import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.interfaces.DAO;

public class ProfilRechercheDAO implements DAO<ProfilRecherche> {

	private Connection connect = null;

	final private String query_insert = "insert into "
			+ DAOConstants.t_emploiRecherche
			+ "(id_identite, idrome) values (?,?)";

	final private String query_delete = "delete from "
			+ DAOConstants.t_emploiRecherche + " where id_recherche=";

	public ProfilRechercheDAO() {
		connect = DBConnexion.getConnexion();
	}

	@Override
	public ProfilRecherche findByID(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProfilRecherche> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProfilRecherche> findByCriteria(ProfilRecherche obj)
			throws DAOException {
		ArrayList<ProfilRecherche> liste = new ArrayList<ProfilRecherche>();
		ProfilRecherche profil = null;
		Statement st = null;
		ResultSet res = null;
		String req = "select `id_recherche`, `id_identite`,"
				+ " `idrome` from " 
				+ DAOConstants.t_emploiRecherche
				+ " where 1=1 ";
		if (obj.getIdentite() != null) {
			if (obj.getIdentite().getId_IDE() != 0)
				req += " and id_identite =" + obj.getIdentite().getId_IDE();
		}
		if (obj.getRome()!=null){
			if(obj.getRome().getIdrome() != 0)
			req += " and idrome =" + obj.getRome().getIdrome();
	}
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {
				IdentiteDAO idao = new IdentiteDAO();
				Identite ident = idao.findByID(res.getInt(2));
				RomeDAO rda = new RomeDAO();
				Rome rom = rda.findByID(res.getInt(3));
				profil = new ProfilRecherche(res.getInt(1), ident, rom);
				liste.add(profil);

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
	
	
	
	public List<ProfilRecherche> listeEmploiParPersonne(Identite un)
			throws DAOException {
		ArrayList<ProfilRecherche> liste = new ArrayList<ProfilRecherche>();
		ProfilRecherche profil = null;
		Statement st = null;
		ResultSet res = null;
		String req =  "select `id_recherche`, `id_identite`,"
				+ " `idrome` from "  + DAOConstants.t_emploiRecherche
				+ " where id_identite="+un.getId_IDE();
		
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(req);
			while (res.next()) {
				RomeDAO rda = new RomeDAO();
				Rome rom = rda.findByID(res.getInt(3));
				profil = new ProfilRecherche(res.getInt(1), un, rom);
				liste.add(profil);

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
	public int create(ProfilRecherche obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, obj.getIdentite().getId_IDE());
			pst.setInt(2, obj.getRome().getIdrome());
			pst.executeUpdate();
			ResultSet rs = pst.getGeneratedKeys();
			if (rs != null && rs.next())
				cle = rs.getInt(1);
			connect.commit();

		} catch (SQLException e) {
			try {
				connect.rollback();
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
	public ProfilRecherche update(ProfilRecherche obj) throws DAOException {
		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
		String query = query_delete + obj.getIdentite().getId_IDE();
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query);
			pst.executeUpdate();
			pst1 = connect.prepareStatement(query_insert);
			pst1.setInt(1, obj.getIdentite().getId_IDE());
			pst1.setInt(2, obj.getRome().getIdrome());
			pst1.executeUpdate();
			connect.commit();
		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			DAOUtil.closePrepareStatement(pst1);
			DAOUtil.closePrepareStatement(pst);
		}
		return obj;
	}

	@Override
	public int delete(ProfilRecherche obj) throws DAOException {
		int num = obj.getId_recherche();
		return delete(num);
	}

	@Override
	public int delete(long id) throws DAOException {
		String query = query_delete + id;
		int retour = 0;
		Statement st = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			retour = st.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// Fermeture du statement
			DAOUtil.closeStatement(st);
		}

		return retour;
	}

}
