package model;

import java.time.LocalDateTime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Case {

	private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
	private ObservableList<Deadline> deadlines = FXCollections.observableArrayList();
	private Client client;

	private int id;
	private String title;
	private String status;

	private LocalDateTime dateAdded;
	private LocalDateTime dateResolved;
	private LocalDateTime dateRemoved;

	public ObservableList<Appointment> getAppointments() {
		return appointments;
	}

	public void addAppointment(Appointment a){
		appointments.add(a);
	}

	public ObservableList<Deadline> getDeadlines() {
		return deadlines;
	}

	public void setDeadlines(ObservableList<Deadline> deadlines) {
		this.deadlines = deadlines;
	}

	public void setAppointments(ObservableList<Appointment> appointments) {
		this.appointments = appointments;
	}

	public void addDeadline(Deadline d){
		deadlines.add(d);
	}

	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDateTime getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(LocalDateTime dateAdded) {
		this.dateAdded = dateAdded;
	}
	public LocalDateTime getDateResolved() {
		return dateResolved;
	}
	public void setDateResolved(LocalDateTime dateResolved) {
		this.dateResolved = dateResolved;
	}
	public LocalDateTime getDateRemoved() {
		return dateRemoved;
	}
	public void setDateRemoved(LocalDateTime dateRemoved) {
		this.dateRemoved = dateRemoved;
	}


}
