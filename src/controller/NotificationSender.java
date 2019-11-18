package controller;

import model.Appointment;
import model.Deadline;
import model.Notification;
import view.CaseApplication;
import java.time.LocalDateTime;

import java.awt.*;
import java.time.temporal.ChronoUnit;


public class NotificationSender {

	public void sendNotification(Notification n){
		Appointment a = n.getAppointment();
		String type = "Deadline";
		String name;
		String message = " Days Until ";
		long daysUntil;
		if(a != null){
			type = "Appointment";
			name = a.getTitle();
			daysUntil = LocalDateTime.now().until(a.getDate(), ChronoUnit.DAYS);
			message =  daysUntil + message + a.getDate();
		}else{
			Deadline d = n.getDeadline();
			name = n.getDeadline().getTitle();
			daysUntil = LocalDateTime.now().until(d.getDate(), ChronoUnit.DAYS);
			message =  daysUntil + message + d.getDate();
		}

		CaseApplication.getTrayIcon()
				.displayMessage(type + ": " + name, message, TrayIcon.MessageType.INFO);
	}

}
