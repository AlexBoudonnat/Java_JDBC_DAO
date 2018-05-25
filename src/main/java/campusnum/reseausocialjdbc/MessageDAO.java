package campusnum.reseausocialjdbc;

import java.sql.Connection;
import java.sql.ResultSet;

import campusnum.reseausocialjdbc.DAO;

public class MessageDAO extends DAO<Message> {

	public MessageDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Message create(Message obj) {
		return obj;
		// TODO Auto-generated method stub
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
	}

	@Override
	public Message update(Message obj) {
		return obj;
		// TODO Auto-generated method stub
	}

	@Override
	public Message find(int id) {
		Message message = new Message();

		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE, 
			        ResultSet.CONCUR_READ_ONLY
			      ).executeQuery("SELECT * FROM messages WHERE sender_id = " + id);
			
			if(result.first()) {
				User user = new User();
				while (result.next()) {
					message = new Message(id, result.getInt("recipient_id"), result.getString("text"));
					user.addMsg(message);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

}
