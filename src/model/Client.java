package model;

public class Client {

	private int client_id;
	private String name;
	private String email;
	private String phone;

	public Client(int client_id, String name, String phone, String email){
		this.client_id = client_id;
		this.name = name;
		this.phone = phone;
		this.email = email;
	}

	public Client(){
	}
	
	public int getClient_id() {
		return client_id;
	}
	public void setClient_id(int client_id){
		this.client_id = client_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
