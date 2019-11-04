package model;

import java.time.LocalDateTime;

public class Deadline {
	private int id;
	private Case aCase;
	private String title;
	private LocalDateTime date;

	public Deadline(String title, LocalDateTime date){
		this.title = title;
		this.date = date;
	}

	public int getId() { return id; }
	public void setId(int id) { this.id = id;}
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
