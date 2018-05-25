package campusnum.reseausocialjdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO extends DAO<User> {

	public UserDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public User create(User user) {
		try {

			PreparedStatement ps = this.connect.prepareStatement(
					"INSERT INTO users (date, firstname, lastname, username, town, age) VALUES (NOW(), ?, ?, ?, ?, ?)");

			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getUserName());
			ps.setString(4, user.getTown());
			ps.setString(5, user.getAge());

			ps.executeUpdate();
			ps.close();

			System.out.println("User with following details was saved in DB: " + user.toString());
			DAO<User> userDAO;
			try {
				userDAO = new UserDAO(BddConnection.getInstance());
				// TODO recuperer l'utilisateur fraichement créé
//				user = ((userDAO) userDAO).find(user.getFirstName(), user.getLastName());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return user;
	}

	@Override
	public void delete(int id) {
		try {

			PreparedStatement ps = this.connect.prepareStatement("DELETE FROM users WHERE id = ?");

			ps.setInt(1, id);

			ps.executeUpdate();
			ps.close();

			System.out.println("User with id: " + id + " was sucesfully deleted from DB.");

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public User update(User user) {
		try {

			PreparedStatement ps = this.connect.prepareStatement(
					"UPDATE users SET firstname=?, lastname=?, username=?, town=?, age=? WHERE id = " + user.getId());

			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getUserName());
			ps.setString(4, user.getTown());
			ps.setString(5, user.getAge());

			ps.executeUpdate();
			ps.close();

			System.out.println(
					"User with id " + user.getId() + " was updated in DB with following details: " + user.toString());
			DAO<User> userDAO;
			try {
				userDAO = new UserDAO(BddConnection.getInstance());
				user = userDAO.find(user.getId());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return user;		
	}

	@Override
	public User find(int id) {
		User user = new User();

		try {
			ResultSet result = this.connect
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT * FROM Users WHERE id = " + id);
			if (result.first()) {
				user = new User(result.getInt("id"), result.getString("FirstName"), result.getString("LastName"),
						result.getString("UserName"), result.getString("Town"), result.getString("Age"));

//				// Recuperer les messages de cet utilisateur.
//				Message msg = new Message();
//				result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
//						.executeQuery("SELECT * FROM messages WHERE sender_id = " + id);
//				while (result.next()) {
//					msg = new Message(id, result.getInt("recipient_id"), result.getString("text"));
//					user.addMsg(msg);
//				}

//				// Recuperer les amis de cet utilisateur
//				User friend = new User();
//				result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
//						.executeQuery("SELECT * FROM friends INNER JOIN users ON Id = user2_id WHERE user1_id = " + id);
//				while (result.next()) {
//					friend = new User(result.getInt("id"), result.getString("firstname"), result.getString("lastname"),
//							result.getString("username"), result.getString("town"), result.getString("age"));
//					user.addFriend(friend);
//				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}
	
	public User find(String firstName, String lastName) {
		User user = new User();

		try {
			ResultSet result = this.connect
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT * FROM Users WHERE firstname = '" + firstName + "' AND lastname = '" + lastName + "'");
			if (result.first()) {
				user = new User(result.getInt("id"), result.getString("FirstName"), result.getString("LastName"),
						result.getString("UserName"), result.getString("Town"), result.getString("Age"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	public User findMessages(User user) {
		Message msg = new Message();
		ResultSet result;
		try {
			result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT * FROM messages WHERE sender_id = " + user.getId());
			while (result.next()) {
				msg = new Message(result.getInt("sender_id"), result.getInt("recipient_id"), result.getString("text"));
				user.addMsg(msg);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	public User findFriends(User user) {
		User friend = new User();
		ResultSet result;
		try {
			result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT * FROM friends INNER JOIN users ON Id = user2_id WHERE user1_id = " + user.getId());
			while (result.next()) {
				friend = new User(result.getInt("id"), result.getString("firstname"), result.getString("lastname"),
						result.getString("username"), result.getString("town"), result.getString("age"));
				user.addFriend(friend);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	public ArrayList<Integer> getUserIdList() throws SQLException {
		ArrayList<Integer> userIdList = new ArrayList<Integer>();
		ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
				.executeQuery("SELECT Id FROM Users");

		while (result.next()) {
			userIdList.add(result.getInt("id"));
		}
		return userIdList;
	}

}
