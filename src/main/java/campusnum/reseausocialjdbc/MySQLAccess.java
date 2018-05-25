package campusnum.reseausocialjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLAccess {
	
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public void readDataBase() throws Exception {
		try {
			// Chargement du driver MySQL
			Class.forName("com.mysql.jdbc.Driver");
			
			// Connection à la BDD
			connect = DriverManager.getConnection("jdbc:mysql://localhost/rsjavajdbc?" + "user=root&password=");
			
			//Statements autorise les requetes SQL à la BDD
			statement = connect.createStatement();
			
			//ResultSet reccupere le resultat de la requete SQL
			resultSet = statement.executeQuery("select * from users");
			writeResultSet(resultSet);
			
			// PreparedStatements peut utiliser des variables et est plus efficace
			preparedStatement = connect.prepareStatement("insert into users values (default, NOW(), ?, ?, ?, ?, ?)");
			
			//Parametrage des differents champs
			preparedStatement.setString(3, "John");
			preparedStatement.setString(4, "Doe");
			preparedStatement.setString(5, "Dodo");
			preparedStatement.setString(6, "Annecy");
			preparedStatement.setString(7, "35");
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("SELECT FirstName, LastName, UserName, Town, Age from users");
			resultSet = preparedStatement.executeQuery();
			writeResultSet(resultSet);
			
			//Supprimer un utilisateur
			preparedStatement = connect.prepareStatement("delete from users where userName= ? ; ");
			preparedStatement.setString(4, "Dodo");
			preparedStatement.executeUpdate();
			
			resultSet = statement.executeQuery("select * from users");
			writeResultSet(resultSet);
			
		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}
	}
	
	private void writeMetaData(ResultSet resultSet) throws SQLException {
		// Reccuperer des metadatas depuis notre BDD
		// ResultSet reccupere le resultat de la requete SQL
		
		System.out.println("Les colonnes dans la table sont : ");
		
		System.out.println("Table : " + resultSet.getMetaData().getTableName(1));
		for (int i = 1; i < resultSet.getMetaData().getColumnCount(); i++) {
			System.out.println("Colonne " +i + " "+ resultSet.getMetaData().getColumnName(i));
		}
		
	}
	// Il faut fermer le resultSet
	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	private void writeResultSet(ResultSet resultSet2) throws SQLException {
		// ResultSet est par defaut avant la premiere donnée settée
		while (resultSet.next()) {
			// Il est possible de reccuperer les colonnes via UserName
			// il est aussi possible des les reccuperer via les numero de colonne qui commence à 1
			// e.g. resultSet.getString(2);
			String firstName = resultSet.getString("FirstName");
			String lastName = resultSet.getString("LastName");
			String userName = resultSet.getString("UserName");
			String town = resultSet.getString("Town");
			String age = resultSet.getString("Age");
			String date = resultSet.getString("Date");
			System.out.println("Prénom : " + firstName);
			System.out.println("Nom : " + lastName);
			System.out.println("Pseudo : " + userName);
			System.out.println("Ville : " + town);
			System.out.println("Age : " + age + " ans");
			System.out.println("Date : " + date);
		}
		
	}

}
