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

public class FormproniveauDAO implements DAO<Formproniveau> {
	
	private Connection connect = null;

	public FormproniveauDAO() {
		connect = DBConnexion.getConnexion();

	}

	@Override
	public Formproniveau findByID(int id) throws DAOException {
		String query = "select * from " + DAOConstants.t_formproniveau
				+ " where id_niveau=" + id;
		Formproniveau niv = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				niv = new Formproniveau(id, res.getString(2));

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

	
	public Formproniveau findByName(String nom) throws DAOException {
		String query = "select * from " + DAOConstants.t_formproniveau
				+ " where niveau like '"+nom+"'" ;
		Formproniveau lf = null;
		Statement st = null;
		ResultSet res = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = st.executeQuery(query);
			if (res.first()) {
				
				lf = new Formproniveau(res.getInt(1),nom);
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
	public List<Formproniveau> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Formproniveau> findByCriteria(Formproniveau obj)
			throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(Formproniveau obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Formproniveau update(Formproniveau obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(Formproniveau obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}
	public List<String> afficherFormProNiveau() throws DAOException{
		List<String> liste=null;
		Statement stat=null;
		ResultSet rs=null;
		String req="select niveau from "+DAOConstants.t_formproniveau +"  order by niveau asc";
		
		try{
			
		stat=connect.createStatement();
		rs=stat.executeQuery(req);
		liste=new ArrayList<String>();
		while(rs.next())
			liste.add(rs.getString("niveau"));
		
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
