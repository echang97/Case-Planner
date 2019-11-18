package controller;

import model.*;
import view.CaseApplication;
import java.time.LocalDateTime;

import java.awt.*;
import java.time.temporal.ChronoUnit;


public class NotificationSender {

	public void sendNotification(Notification n){
		Appointment a = n.getAppointment();
		String name;
		String message = " Days Until ";
		Case c;
		long daysUntil;
		if(a != null){
			c = a.getCase();
			name = a.getTitle();
			daysUntil = LocalDateTime.now().until(a.getDate(), ChronoUnit.DAYS);
			message =  daysUntil + message + a.getDate();
		}else{
			Deadline d = n.getDeadline();
			c = d.getCase();
			name = n.getDeadline().getTitle();
			daysUntil = LocalDateTime.now().until(d.getDate(), ChronoUnit.DAYS);
			message =  daysUntil + message + d.getDate();
		}

		CaseApplication.getTrayIcon()
				.displayMessage(name + " for " + c.getTitle(), message, TrayIcon.MessageType.INFO);
	}

}
