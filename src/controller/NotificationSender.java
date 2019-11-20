package controller;

import model.*;
import view.CaseApplication;

import java.awt.*;

public class NotificationSender {

	public void sendNotification(Notification n){
		Appointment a = n.getAppointment();
		String name;
		Case c;
		if(a != null){
			c = a.getCase();
			name = a.getTitle();
		}else{
			Deadline d = n.getDeadline();
			c = d.getCase();
			name = d.getTitle();
		}

		CaseApplication.getTrayIcon()
				.displayMessage(name + " for " + c.getTitle(), n.getMessage(), TrayIcon.MessageType.INFO);
	}

}
