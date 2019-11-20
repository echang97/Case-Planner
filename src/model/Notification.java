package model;

import java.time.LocalDateTime;

public class Notification {
	private int notification_id;
	private Deadline deadline;
	private Appointment appointment;
	private String message;
	private String location;
	private LocalDateTime sendDate;
	private boolean sent;

	public Notification(){

	}

	public Notification(LocalDateTime sendDate){
		this.sendDate = sendDate;
	}

	public int getNotification_id() { return notification_id; }
	public void setNotification_id(int notification_id) { this.notification_id = notification_id;}
	public Deadline getDeadline() { return deadline; }
	public void setDeadline(Deadline deadline) { this.deadline = deadline; }
	public Appointment getAppointment() { return appointment; }
	public void setAppointment(Appointment appointment) { this.appointment = appointment; }
	public String getMessage() { return message; }
	public void setMessage(String message) { this.message = message; }
	public String getLocation() { return location; }
	public void setLocation(String location) { this.location = location; }
	public LocalDateTime getSendDate() { return sendDate; }
	public void setSendDate(LocalDateTime sendDate) { this.sendDate = sendDate; }
	public boolean isSent() { return sent; }
	public void setSent(boolean sent) { this.sent = sent; }
}
