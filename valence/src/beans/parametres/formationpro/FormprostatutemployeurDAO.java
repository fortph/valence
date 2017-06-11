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

public class FormprostatutemployeurDAO implements DAO<Formprostatutemployeur> {
	private Connection connect = null;

	public FormprostatutemployeurDAO() {
		connect = DBConnexion.getConnexion();

	}

	
	
	final private String query_insert = "insert into "
			+ DAOConstants.t_formprostatutemployeur
			+ " (statut) "
			+ " values (?)";
	@Override
	public Formprostatutemployeur findByID(int id) throws DAOException {
		String query = "select * from " + DAOConstants.t_formprostatutemployeur	
				+ " where id_statut=" + id;
		Formprostatutemployeur form = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
					form = new Formprostatutemployeur(id, res.getString(2));

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

		return form;
	}
	
	
	
	public Formprostatutemployeur findByName(String nom) throws DAOException {
		String query = "select * from " + DAOConstants.t_formprostatutemployeur
				+ " where statut like '"+nom +"'";
		Formprostatutemployeur lf = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				
				lf = new Formprostatutemployeur(res.getInt(1),nom);
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
	public List<Formprostatutemployeur> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Formprostatutemployeur> findByCriteria(
			Formprostatutemployeur obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(Formprostatutemployeur obj) throws DAOException {
		PreparedStatement pst = null;
		int cle = 0;
		try {
			connect.setAutoCommit(false);
			pst = connect.prepareStatement(query_insert,
					Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, obj.getStatut());
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
	public Formprostatutemployeur update(Formprostatutemployeur obj)
			throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(Formprostatutemployeur obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public List<String> afficherFormProStatEmployeur() throws DAOException{
		List<String> liste=null;
		Statement stat=null;
		ResultSet rs=null;
		String req="select statut from "+DAOConstants.t_formprostatutemployeur +"  order by statut asc";
		
		try{
			
		stat=connect.createStatement();
		rs=stat.executeQuery(req);
		liste=new ArrayList<String>();
		while(rs.next())
			liste.add(rs.getString("statut"));
		
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

}
