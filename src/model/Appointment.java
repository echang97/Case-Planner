package model;

import java.time.LocalDateTime;

public class Appointment {
	private int appointment_id;
	private Case aCase;
	private String title;
	private String room;
	private String address;
	private String city;
	private String state;
	private int zip;
	private LocalDateTime date;

	public int getAppointment_id() { return appointment_id; }
	public void setAppointment_id(int appointment_id) { this.appointment_id = appointment_id;}
	public Case getCase() { return aCase; }
	public void setCase(Case aCase) { this.aCase = aCase; }
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getZip() {
		return zip;
	}
	public void setZip(int zip) {
		this.zip = zip;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
}
