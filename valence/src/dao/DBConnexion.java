package dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public final class DBConnexion
{
	
	// Objet Connection
	private static Connection connexion;

	// Le constructeur d�clar� private
	private DBConnexion (){}

	public static boolean isValidConnection(){
		
		// SI handle vaut null ALORS connexion fermée SINON poursuivre traitement
		if (connexion==null)
			return false;
			
		try{		
			// 10 secondes : temps d'attente
			if (connexion.isValid(10))
				return true;
			else
				return false;	
		}
		catch(SQLException ex){
			return false;
		}
		finally{
		}
	}
	
	
	/**fermeture connexion base de donnees */
/*	public static void cloreConnexion ()
	{
		System.out.println("\nFERMETURE DE LA CONNEXION - cloreConnexion()\n"); 
		try
		{
			if (connexion != null && !connexion.isClosed())
				connexion.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Classe non trouvée " + ex.getMessage ());
		}
	}
*/
	@SuppressWarnings("finally")
	public static boolean cloreConnexion ()
	{
		//System.out.println("\nFERMETURE DE LA CONNEXION - cloreConnexion()\n"); 
		
		// Booléen retourné par la méthode
		boolean bClosed = false;
		
		try
		{
			// SI connexion ouverte
			if (isValidConnection())
			{
				// Fermer la connexion
				connexion.close();
				// Positionner le handle à NULL
				connexion = null;
				// Connexion fermée
				bClosed = true;
			}
		}
		catch (SQLException ex) {
			System.out.println("Fermeture connexion impossible " + ex.getMessage());
		}
		finally{
			return bClosed;
		}
	}
	
	/**
	etablir connexion a base de donnees
	 */
	/*public static Connection getConnexion ()
	{
		System.out.println("OUVERTURE D'UNE CONNEXION"); 
		try
		{
			if (connexion == null || connexion.isClosed())
			{
				Class.forName (DAOConstants.driver);
				if (DAOConstants.login != null)
					connexion = DriverManager.getConnection (DAOConstants.chaineConnexion, DAOConstants.login, DAOConstants.password);
				else
					connexion = DriverManager.getConnection (DAOConstants.chaineConnexion);
			}
			return connexion;
		}
		catch (ClassNotFoundException ex)
		{
			System.out.println("Classe non trouvée ");
			return connexion;
		}
		catch (SQLException ex)
		{
			System.out.println("Ouverture connexion impossible.");
			return connexion;
		}	
	}*/
	public static Connection getConnexion ()
	{
		//System.out.println("OUVERTURE D'UNE CONNEXION - getConnexion ()"); 
		try {
			// SI pas de connexion ouverte
			if (!isValidConnection())
			{		
				//System.out.println("Ouverture d'une connexion.");
		    	Context initContext = new InitialContext();
		  	   	DataSource ds = (DataSource)initContext.lookup("java:comp/env/jdbc/valence");	  	

		  	  	// SI datasource ok
		  	  	if (ds != null) { 	  		
		  	  		// Obtenir une connexion
		  	  		connexion = ds.getConnection(); 
		  	  	}			
			}
			return connexion;
		}
		catch (NamingException ex) {
			System.out.println("Classe non trouvée.");
			return connexion;
		}
		catch (SQLException ex) {
			System.out.println("Ouverture connexion impossible.");
			return connexion;
		}
	}
	
	
	
}
