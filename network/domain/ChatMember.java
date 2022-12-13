package network.domain;

public class ChatMember {
	private int chatmember_idx;
	private String id;
	private String pass;
	private String name;

	public int getChatmember_idx() {
		return chatmember_idx;
	}

	public void setChatmember_idx(int chatmember_idx) {
		this.chatmember_idx = chatmember_idx;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
