package campusnum.reseausocialjdbc;

import java.sql.Connection;

public abstract class DAO<T> {
	protected Connection connect = null;

	public DAO(Connection conn) {
		this.connect = conn;
	}
	   
	  /**
	   * M�thode de cr�ation
	   * @param obj
	   */
	  public abstract T create(T obj);

	  /**
	  * M�thode pour effacer
	  * @param obj
	  */
	  public abstract void delete(int id);

	  /**
	  * M�thode de mise � jour
	  * @param obj
	  */
	  public abstract T update(T obj);

	  /**
	  * M�thode de recherche des informations
	  * @param id
	  * @return T
	  */
	  public abstract T find(int id);

}
