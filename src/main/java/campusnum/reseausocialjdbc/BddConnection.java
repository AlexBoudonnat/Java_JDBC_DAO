package campusnum.reseausocialjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//CTRL + SHIFT + O pour générer les imports
public class BddConnection {
	// URL de connexion
	private static String url = "jdbc:mysql://localhost:3306/rsjavajdbc";
	// Nom du user
	private static String user = "root";
	// Mot de passe de l'utilisateur
	private static String passwd = "";
	// Objet Connection
	private static Connection connect;

	// Méthode qui va nous retourner notre instance et la créer si elle n'existe pas
	public static Connection getInstance() throws ClassNotFoundException {
		if (connect == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				System.out.println("Driver OK");
				connect = DriverManager.getConnection(url, user, passwd);
				System.out.println("INSTANCIATION DE LA CONNEXION SQL ! ");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("CONNEXION SQL EXISTANTE ! ");
		}
		return connect;
	}
}
