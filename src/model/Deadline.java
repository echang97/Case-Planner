package model;

import java.time.LocalDateTime;

public class Deadline {
	private int deadline_id;
	private Case aCase;
	private String title;
	private LocalDateTime date;

	public Deadline(int deadline_id, Case aCase, String title, String date){
		this.deadline_id = deadline_id;
		this.aCase = aCase;
		this.title = title;
		this.date = LocalDateTime.parse(date);
	}

	public Deadline(String title, LocalDateTime date) {
		this.title = title;
		this.date = date;
	}

	public int getDeadline_id() { return deadline_id; }
	public void setDeadline_id(int deadline_id) { this.deadline_id = deadline_id;}
	public Case getCase() { return aCase; }
	public void setCase(Case aCase) { this.aCase = aCase; }
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
}
