package model;

import java.time.LocalDateTime;

public class Deadline {
	private int deadline_id;
	private Case aCase;
	private String title;
	private LocalDateTime date;
	private String dateString;
	private String status;

	public Deadline(int deadline_id, Case aCase, String title, String date, String status){
		this.deadline_id = deadline_id;
		this.aCase = aCase;
		this.title = title;
		this.date = LocalDateTime.parse(date);
		this.status = status;
		this.dateString = date.substring(0,date.indexOf("T"));
	}

	public Deadline(String title, LocalDateTime date) {
		this.title = title;
		this.date = date;
		this.status = "Incomplete";
	}

	public int getDeadline_id() { return deadline_id; }
	public void setDeadline_id(int deadline_id) { this.deadline_id = deadline_id;}
	public Case getCase() { return aCase; }
	public void setCase(Case aCase) { this.aCase = aCase; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public LocalDateTime getDate() { return date; }
	public void setDate(LocalDateTime date) {
		this.date = date;
		this.dateString = date.toString().substring(0,date.toString().indexOf("T"));
	}
	public String getStatus() {return status;}
	public void setStatus(String status) { this.status = status; }
	public String getDateString() { return dateString; }
}
