package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.commons.logging.*;

import dao.exception.DAOException;


/**
 * Classe utilitaire
 * @author philippe
 *
 */
public final class DAOUtil {

	static final private Log log = LogFactory.getLog(DAOUtil.class);
		
	// Constructeur en private -> pas d'instanciation
	private  DAOUtil (){		
	}
	
	/**
	 * Fermeture d'un Statement
	 * @param stmt
	 * @throws DAOException
	 */
	public static void closeStatement(final Statement stmt) throws DAOException
	{
		// SI statement existe
		if (stmt != null){
			 try{
				 // Fermeture du statement
				stmt.close();
			 }
			 catch (SQLException ex) {
				log.error(stmt, ex);
				 throw new DAOException("closeStatement <exception> :", ex);
			 }
		}
	}
	
	public static void closePrepareStatement(final PreparedStatement stmt) throws DAOException
	{
		// SI statement existe
		if (stmt != null){
			 try{
				 // Fermeture du statement
				stmt.close();
			 }
			 catch (SQLException ex) {
				log.error(stmt, ex);
				 throw new DAOException("closeStatement <exception> :", ex);
			 }
		}
	}

	/**
	 * Fermeture d'un ResultSet
	 * @param rs : resultset  fermer
	 * @throws DAOException
	 */
	public static void closeResultSet(final ResultSet rs) throws DAOException
	{
		// SI ResultSet existe
		if (rs != null){
			 try{
				 // Fermeture du ResultSet
				 rs.close();
			 }
			 catch (SQLException ex){
				log.error(rs, ex);
				 throw new DAOException("closeResultSet <exception> :", ex);
			 }
		}
	}
}
