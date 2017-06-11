package beans.parametres.formationpro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.DAOConstants;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.interfaces.DAO;

public class FormprothemeDAO implements DAO<Formprotheme> {
	
	private Connection connect = null;

	public FormprothemeDAO() {
		connect = DBConnexion.getConnexion();

	}


	final private String query_insert = "insert into "
			+ DAOConstants.t_formprotheme + " (theme) " + " values (?)";

	@Override
	public Formprotheme findByID(int id) throws DAOException {
		String query = "select * from " + DAOConstants.t_formprotheme
				+ " where id_theme=" + id;
		Formprotheme theme = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				theme = new Formprotheme(id, res.getString(2));

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

		return theme;
	}

	public Formprotheme findByName(String nom) throws DAOException {
		String query = "select * from " + DAOConstants.t_formprotheme
				+ " where theme like '" + nom + "'";
		Formprotheme lf = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {

				lf = new Formprotheme(res.getInt(1), nom);
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

		return lf;
	}

	@Override
	public List<Formprotheme> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Formprotheme> findByCriteria(Formprotheme obj)
			throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(Formprotheme obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, obj.getTheme());
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
	public Formprotheme update(Formprotheme obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(Formprotheme obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<String> afficherFormProTheme() throws DAOException {
		List<String> liste = null;
		Statement stat = null;
		ResultSet rs = null;
		String req = "select theme from " + DAOConstants.t_formprotheme
				+ "  order by theme asc";

		try {

			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			liste = new ArrayList<String>();
			while (rs.next())
				liste.add(rs.getString("theme"));

		} catch (SQLException ex) {
			System.out.print("Probl�me " + ex);
		} finally {
			try {
				stat.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return liste;
	}

	/**
	 * affiche le nombre de themes de formations differents dans la liste donnee
	 * en parametre
	 */
	public int nombreTheme(List<Formprotheme> obj) throws DAOException {
		Statement stat = null;
		ResultSet rs = null;
		int total = 0;
		List<Integer> listetheme = new ArrayList<Integer>();
		for (int i = 0; i < obj.size(); i++)
			listetheme.add(obj.get(i).getId_theme());
		int deb = listetheme.toString().indexOf("[");
		int fin = listetheme.toString().lastIndexOf("]");
		String nouvliste = listetheme.toString().substring(deb + 1, fin);
		System.out.println(listetheme);
		String req = "select count(distinct id_theme) from "
				+ DAOConstants.t_formprotheme + " where id_theme in("
				+ nouvliste + ")";
		try {

			stat = connect.createStatement();
			rs = stat.executeQuery(req);
			if (rs.first())
				total = rs.getInt(1);

		} catch (SQLException ex) {
			System.out.print("Probl�me " + ex);
		} finally {
			try {
				stat.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return total;
	}

}
