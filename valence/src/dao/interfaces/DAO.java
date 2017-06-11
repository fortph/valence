package dao.interfaces;

import java.sql.Connection;
import java.util.List;

import dao.DBConnexion;
import dao.exception.DAOException;


public interface DAO<T> {
	
	// Récupérer une connexion à la base
	public Connection connect = DBConnexion.getConnexion();
	
	/**
	 * permet de réciperer un objet par son id
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public abstract T findByID(int id)throws DAOException;
	
	/** Permet de récupérer tous les objets
	 * 
	 * @return
	 * @throws DAOException
	 */
	 public abstract List<T> findAll()throws DAOException;
	 
	
	/** Permet de récupérer une liste d'objets via des critères
	 * 
	 * @param obj
	 * @return
	 * @throws DAOException
	 */
	
	public abstract List<T> findByCriteria(T obj) throws DAOException;
	
	/** Permet de créer une entrée dans la base de données	
	 * 
	 * @param obj
	 * @return
	 * @throws DAOException
	 */
	
	
	public abstract int create(T obj) throws DAOException;

	/** Permet de mettre à jour les données d'une entrée dans la base
	 * 
	 * @param obj
	 * @return
	 * @throws DAOException
	 */
	
	public abstract T update(T obj) throws DAOException;
	
	/** Permet la suppression d'une entrée de la base à partir de l'objet métier
	 * 
	 * @param obj
	 * @return
	 * @throws DAOException
	 */
		
		
	public abstract int delete(T obj) throws DAOException;
	
	/**Permet la suppression d'une entrée de la base à partir de  l'identifiant de l'objet métier
	 * 
	 * @param id
	 * @return
	 * @throws DAOException
	 */
		
		
	public abstract int delete(long id) throws DAOException;

	}