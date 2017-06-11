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

public class OrigineDAO implements DAO<Origine> {
	
	private Connection connect = null;
	public OrigineDAO() {
		connect = DBConnexion.getConnexion();

	}

	@Override
	public Origine findByID(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Origine> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Origine> findByCriteria(Origine obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(Origine obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Origine update(Origine obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(Origine obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}
	public List<String> afficheOrigine() throws DAOException{
		List<String> listePays=null;
		Statement stat=null;
		ResultSet rs=null;
		String req="select pays from "+DAOConstants.t_origine;
		
		try{
		
		stat=connect.createStatement();
		rs=stat.executeQuery(req);
		listePays=new ArrayList<String>();
		while(rs.next())
			listePays.add(rs.getString("pays"));
		
	} catch (SQLException ex) {
		System.out.print("Problème " + ex);
	} finally {
		try {
			stat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	return listePays;
}
}
