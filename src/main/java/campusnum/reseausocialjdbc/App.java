package campusnum.reseausocialjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.PreparedStatement;

import campusnum.reseausocialjdbc.MySQLAccess;

public class App {
	
	Scanner sc = new Scanner(System.in);
	String choice,  userId, prenom,  nom, pseudo, ville, age, friendId, query;
	boolean connected = false;
	int num = 1;
	
	// Creation des objets Connection Statement, PreparedStatement, ResultSet et ResultSetMetaData
	Connection conn = null;
	Statement state = null;
	PreparedStatement preState = null;
	ResultSet result = null;
	ResultSetMetaData resultMeta = null;
	
	public App() {
		
		try {
			state = BddConnection.getInstance().createStatement();
			
			userList();
			
			userConnect();
			
			if (result.first()) {
				System.out.println("Bonjour " + result.getString("FirstName") + " " + result.getString("LastName"));
				userId = result.getString("Id");
				result.close();

				getUserInfos();
				
				getUserMsg();

				getMenu();
				
			} else {
				System.out.println("Pseudo incorrect : Aurevoir !!!");
			}
				
			result.close();
			state.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void getMenu() throws SQLException {
		System.out.println("\nQue fait-on maintenant ?");
		System.out.println("1- Créer un nouvel utilisateur");
		System.out.println("2- Ajouter un ami");
		System.out.println("3- Voir la liste des amis");
		System.out.println("4- Deconnection");

		choice = sc.nextLine();

//		while (choice != "1" && choice != "2" && choice != "3") {
//			System.out.println("\nVeuillez taper une entrée valide.");
//			choice = sc.nextLine();
//		}

		switch (choice) {
		case "1":
			
			setNewUser();
			getMenu();
			
			break;
		case "2":
			
			setNewFriend();
			getMenu();

			break;
		case "3":
			
			getFriendsList();
			getMenu();

			break;
		case "4":
			
			System.out.println("Aurevoir et à bientôt :)");

			break;
		}
		
	}

	private void getFriendsList() throws SQLException {
		//On recherche tous les amis de l'utilisateur connecté
		result = state.executeQuery("SELECT * FROM friends INNER JOIN users ON Id = user2_id WHERE user1_id = " + userId);
		
		result.last();
		System.out.println("\nVous avez " + result.getRow() + " ami(s) :");
		result.beforeFirst();
		
		num = 1;
		while (result.next()) {
			System.out.println(num + "- " + result.getString("FirstName") + " " + result.getString("LastName"));
			num++;
		}
		result.close();
	}

	private void setNewFriend() throws SQLException {
		System.out.println("\nQui voulez-vous pour ami ?");
		
		userList();
		
		friendId = sc.nextLine();
		
		state.executeUpdate("INSERT INTO friends (user1_id, user2_id, date) VALUES('" + userId + "', '" + friendId + "', NOW())");
		state.executeUpdate("INSERT INTO friends (user1_id, user2_id, date) VALUES('" + friendId + "', '" + userId + "', NOW())");
		
	}

	private void setNewUser() throws SQLException {
		// On Déclare le PreparedStatement
		preState = conn.prepareStatement("INSERT INTO users (Date, FirstName, LastName, UserName, Town, Age) VALUES (?, ?, ?, ?, ?, ?)");

		// On recupere les infos à enregistrer dans la BDD
		System.out.println("\nQuel est votre prénom ?");
		prenom = sc.nextLine();
		System.out.println("Quel est votre nom ?");
		nom = sc.nextLine();
		System.out.println("Quel est votre pseudo ?");
		pseudo = sc.nextLine();
		System.out.println("Quelle est votre ville ?");
		ville = sc.nextLine();
		System.out.println("Quel est votre age ?");
		age = sc.nextLine();
						
		// On insert nos variables dans le PreparedStatement et on execute l'insersion
		preState.setTimestamp(1, getCurrentTimeStamp());
		preState.setString(2, prenom);
		preState.setString(3, nom);
		preState.setString(4, pseudo);
		preState.setString(5, ville);
		preState.setString(6, age);				
		preState.executeUpdate();	
	}

	private void getUserMsg() throws SQLException {
		// Récuperation de l'utilisateur connecté et des msg qu'il a écrit
		query = "SELECT * FROM messages INNER JOIN users ON Id = Recipient_Id WHERE Sender_id = " + userId;
		
		result = state.executeQuery(query);
		
		// Pour compter le nombre de rows on se met sur la derniere, on prend sont index et on revient sur la première
		result.last();
		System.out.println("\nVos messages : (" + result.getRow() + ")");
		result.beforeFirst();
		
		while (result.next()) {
			System.out.println("- " + result.getString("Text") + " (à " + result.getString("UserName") + ")");
		}
		result.close();		
	}

	private void getUserInfos() throws SQLException {
		//Affichage des infos perso
		query = "SELECT * FROM users WHERE Id = " + userId;
		result = state.executeQuery(query);
		System.out.println("\nVos infos personnelles :");
		while (result.next()) {
			for (int i = 1; i < resultMeta.getColumnCount(); i++) {
				System.out.println(resultMeta.getColumnName(i) + " : " + result.getString(i));
			}
		}
		result.close();		
	}

	private void userConnect() throws SQLException {
		// Connection
		System.out.println("\nCONNECTION\nQuel est votre pseudo ?\n");
		choice = sc.nextLine();
		query = "SELECT * FROM users WHERE UserName = '" + choice + "'";
		result = state.executeQuery(query);		
	}

	private void userList() throws SQLException {
		// L'objet ResultSet contient le resultat de la requete SQL
		query = "SELECT * FROM users";			
		result = state.executeQuery(query);
		
		// On recupère les Metadatas
		resultMeta = result.getMetaData();
		
		//Affichage de la liste de tous les profils
		System.out.println("Liste des Profils :");
		while (result.next()) {
			System.out.println(result.getString("Id") + "- " + result.getString("FirstName") + " " + result.getString("LastName"));
		}
		result.close();
	}

	private void connection() throws SQLException, ClassNotFoundException {
		// Chargement du driver MySQL
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("Driver OK");
		
		// Connexion à la BDD
		String url = "jdbc:mysql://localhost:3306/rsjavajdbc",
				user = "root",
				password = "";
		conn = DriverManager.getConnection(url, user, password);
		System.out.println("Connection OK\n");
		
		state = conn.createStatement();
	}

	public static void main(String[] args) throws Exception {
		
		App app = new App();
		
	}
	
	private static java.sql.Timestamp getCurrentTimeStamp() {

		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());

	}

}
