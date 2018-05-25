package campusnum.reseausocialjdbc;

import java.sql.Connection;

public abstract class DAO<T> {
	protected Connection connect = null;

	public DAO(Connection conn) {
		this.connect = conn;
	}
	   
	  /**
	   * Méthode de création
	   * @param obj
	   */
	  public abstract T create(T obj);

	  /**
	  * Méthode pour effacer
	  * @param obj
	  */
	  public abstract void delete(int id);

	  /**
	  * Méthode de mise à jour
	  * @param obj
	  */
	  public abstract T update(T obj);

	  /**
	  * Méthode de recherche des informations
	  * @param id
	  * @return T
	  */
	  public abstract T find(int id);

}
