package beans.parametres.accueil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.DAOConstants;
import dao.DAOUtil;
import dao.DBConnexion;
import dao.exception.DAOException;
import dao.interfaces.DAO;

public class NiveauFormationDAO implements DAO<NiveauFormation> {
	
	private Connection connect = null;
	public NiveauFormationDAO() {
		connect = DBConnexion.getConnexion();

	}

	@Override
	public NiveauFormation findByID(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NiveauFormation> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NiveauFormation> findByCriteria(NiveauFormation obj)
			throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(NiveauFormation obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public NiveauFormation update(NiveauFormation obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(NiveauFormation obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * affiche la liste de tous les niveaux de formation prevus dans le programme
	 * @return
	 * @throws DAOException
	 */
	public List<String> afficherNiveaux() throws DAOException{
		//affecte directe le 8/11/2016 au lieu de List<String> listeNiveaux=null;
		List<String> listeNiveaux=new ArrayList<String>();
		Statement stat=null;
		ResultSet rs=null;
		String req=null;
		try{
			connect=dao.DBConnexion.getConnexion();
		req="select libelle from "+DAOConstants.t_niveauFormation;
		stat=connect.createStatement();
		rs=stat.executeQuery(req);
		//listeNiveaux=new ArrayList<String>();
		while(rs.next())
			listeNiveaux.add(rs.getString("libelle"));
		
	} catch (SQLException ex) {
		System.out.print("Problème " + ex);
	} finally {
		// Fermeture du resultset
		DAOUtil.closeResultSet(rs);

		// Fermeture du statement
		DAOUtil.closeStatement(stat);
	}
	return listeNiveaux;
}
}
