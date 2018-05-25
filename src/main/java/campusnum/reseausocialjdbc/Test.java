package campusnum.reseausocialjdbc;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

//CTRL + SHIFT + O pour générer les imports
public class Test {
public static void main(String[] args) throws Exception {
  try {
    //Nous appelons quatre fois la méthode getInstance()
    PreparedStatement prepare = BddConnection.getInstance().prepareStatement("SELECT * FROM users WHERE UserName = ?");

    Statement state = BddConnection.getInstance().createStatement();

    BddConnection.getInstance().setAutoCommit(false);

    DatabaseMetaData meta = BddConnection.getInstance().getMetaData();
       
  } catch (SQLException e) {
    e.printStackTrace();
  }
}
}
