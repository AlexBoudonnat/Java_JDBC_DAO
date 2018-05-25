package campusnum.reseausocialjdbc;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
	
	private int id;
	private String firstName, lastName, userName, town, age;
	private ArrayList<Message> listMsg = new ArrayList<Message>();
	private ArrayList<User> friendsList = new ArrayList<User>();

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(int id, String firstName, String lastName, String userName, String town, String age) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.town = town;
		this.age = age;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
	public ArrayList<Message> getListMsg() {
		return listMsg;
	}
	
	public void setListMsg(ArrayList<Message> listMsg) {
		this.listMsg = listMsg;
	}
	
	public void addMsg(Message message) {
		this.listMsg.add(message);
	}
	
	public void removeMsg(Message message) {
		this.listMsg.remove(message);
	}
	
	public ArrayList<User> getFriendsList() {
		return friendsList;
	}
	
	public void setFriendsList(ArrayList<User> friendsList) {
		this.friendsList = friendsList;
	}
	
	public void addFriend(User friend) {
		this.friendsList.add(friend);
	}
	
	public void removeFriend(User friend) {
		this.friendsList.remove(friend);
	}

}
