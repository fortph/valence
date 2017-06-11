package beans.parametres.accueil;


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

public class SexeDAO implements DAO<Sexe> {
	
	private Connection connect = null;
	public SexeDAO() {
		connect = DBConnexion.getConnexion();

	}

	@Override
	public Sexe findByID(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Sexe> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Sexe> findByCriteria(Sexe obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(Sexe obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Sexe update(Sexe obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(Sexe obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}
	public List<String> afficherSexe() throws DAOException{
		List<String> liste=null;
		Statement stat=null;
		ResultSet rs=null;
		String req="select libelle from "+DAOConstants.t_sexe;;
		try{
		
		stat=connect.createStatement();
		rs=stat.executeQuery(req);
		liste=new ArrayList<String>();
		while(rs.next())
			liste.add(rs.getString("libelle"));
		
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
