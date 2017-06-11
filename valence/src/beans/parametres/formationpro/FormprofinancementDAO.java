package beans.parametres.formationpro;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.DAOConstants;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.interfaces.DAO;

public class FormprofinancementDAO implements DAO<Formprofinancement> {
	
	private Connection connect = null;

	public FormprofinancementDAO() {
		connect = DBConnexion.getConnexion();

	}

	@Override
	public Formprofinancement findByID(int id) throws DAOException {
		String query = "select * from " + DAOConstants.t_formprofinance
				+ " where id_finance=" + id;
		Formprofinancement niv = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				niv = new Formprofinancement(id, res.getString(2));

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

		return niv;
	}
	
	
	public Formprofinancement findByName(String nom) throws DAOException {
		String query = "select * from " + DAOConstants.t_formprofinance
				+ " where financement like '"+nom+"'" ;
		Formprofinancement lf = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				
				lf = new Formprofinancement(res.getInt(1),nom);
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
	public List<Formprofinancement> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Formprofinancement> findByCriteria(Formprofinancement obj)
			throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(Formprofinancement obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Formprofinancement update(Formprofinancement obj)
			throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(Formprofinancement obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}
	public List<String> afficherFormProFinance() throws DAOException{
		List<String> liste=null;
		Statement stat=null;
		ResultSet rs=null;
		String req="select financement from "+DAOConstants.t_formprofinance +"  order by financement asc";
		
		try{
			
		stat=connect.createStatement();
		rs=stat.executeQuery(req);
		liste=new ArrayList<String>();
		while(rs.next())
			liste.add(rs.getString("financement"));
		
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
