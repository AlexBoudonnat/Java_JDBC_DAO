package campusnum.reseausocialjdbc;

import java.sql.SQLException;

public class TestDAO {

	public TestDAO() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws ClassNotFoundException {
		// Testons les users
		DAO<User> userDAO = new UserDAO(BddConnection.getInstance());
		try {
			for (int i = 0; i < ((UserDAO) userDAO).getUserIdList().size(); i++) {
				User user = userDAO.find(((UserDAO) userDAO).getUserIdList().get(i));
				user = ((UserDAO) userDAO).findMessages(user);
				System.out.println(
						"\nUtilisateur n°" + user.getId() + " : " + user.getFirstName() + " " + user.getLastName());
				for (Message msg : user.getListMsg()) {
					System.out.println("   - " + msg.getText());
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// // Créer un nouvel Utilisateur
		// User newUser = new User(0, "Bod", "Leponge", "Bobo", "BikiniBottom", "25");
		// userDAO.create(newUser);

		// Afficher le nombre de ligne de la table Users
		try {
			System.out.println("\nNombre d'utilisateurs : " + ((UserDAO) userDAO).getUserIdList().size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 //Modifier un utilisateur
		 User updateUser = userDAO.find(8);
		 updateUser.setAge("51");
		 updateUser = userDAO.update(updateUser);
		 System.out.println(updateUser.getAge() + " ans");

		// // Supprimer un utilisateur
		// User deleteUser = userDAO.find(7);
		// userDAO.delete(deleteUser.getId());
		
		// Afficher les utilisateurs avec la liste de leurs amis
		try {
			for (int i = 0; i < ((UserDAO) userDAO).getUserIdList().size(); i++) {
				User user = userDAO.find(((UserDAO) userDAO).getUserIdList().get(i));
				user = ((UserDAO) userDAO).findFriends(user);
				System.out.println(
						"\nUtilisateur n°" + user.getId() + " : " + user.getFirstName() + " " + user.getLastName());
				for (User friend: user.getFriendsList()) {
					System.out.println("   - " + friend.getFirstName() + " " + friend.getLastName());
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
