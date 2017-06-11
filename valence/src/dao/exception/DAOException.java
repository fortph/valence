package dao.exception;

/**
 * Une exception levée par un DAO et liée à la persistance.
 */
public class DAOException extends Exception {
  /**
	 * 
	 */
	private static final long serialVersionUID = -6111973666438757776L;
/**
   * Code de l'erreur.
   * -1 s'il y a une exception chainée ;
   * 1 pour connexion pas ouverte ;
   * 2 pour connexion déjà ouverte ;
   * 4 si pas de transaction en cours ;
   * ...
   */
  private int codeErreur;

  public DAOException() {
  }

  public DAOException(String message) {
    this(message, 0);
  }

  /**
   * Crée une nouvelle exception avec un message et une cause donnée.
   * @param message le message qui explique le probléme.
   * @param cause une exception qui est la cause du problème. Le type de cette
   * exception doit etre caché à l'utilisateur du DAO et ne pas apparaitre
   * dans l'interface de la classe DaoException. Cette cause peut etre
   * connue de l'utilisateur par l'appel de la méthode getCause() héritée
   * de Exception.
   */
  public DAOException(String message, Throwable cause) {
    super(message, cause);
    this.codeErreur = -1;
  }

  public DAOException(Throwable cause) {
    this(null, cause);
  }
  
  public DAOException(String message, int codeErreur) {
    super(message);
    this.codeErreur = codeErreur;
  }
  
  public int getCode() {
    return codeErreur;
  }
}
