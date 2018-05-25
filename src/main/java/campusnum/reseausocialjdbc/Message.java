package campusnum.reseausocialjdbc;

public class Message {
	
	int senderId, recipientId;
	String text;

	public Message() {
		// TODO Auto-generated constructor stub
	}

	public Message(int senderId, int recipientId, String text) {
		this.senderId = senderId;
		this.recipientId = recipientId;
		this.text = text;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public int getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(int recipientId) {
		this.recipientId = recipientId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
