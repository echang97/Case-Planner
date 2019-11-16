package controller;

import model.Notification;
import view.CaseApplication;

import java.awt.*;

public class NotificationSender {

	public void sendNotification(Notification n){
		String type = "Deadline";
		if(n.getAppointment() != null){
			type = "Appointment";
		}
		String message = n.getMessage();

		CaseApplication.getTrayIcon()
				.displayMessage(type + " (Case Planner)", message, TrayIcon.MessageType.INFO);
	}

}
