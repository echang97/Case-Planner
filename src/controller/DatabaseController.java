package controller;

import model.*;

import java.sql.*;
import java.time.LocalDateTime;

public class DatabaseController {

	public static Statement addCaseToDB(DatabaseConnection database, Case c) throws SQLException {
		Connection connection = database.getConnection();
		Statement statement = connection.createStatement();
		String addition = "INSERT INTO aCase(client_id, title, status, dateAdded) VALUES (";
		if (c.getClient() != null) {
			addition = addition + c.getClient().getClient_id() + ", '";
		}
		else {
			addition = addition + "null, '";
		}
		addition = addition + c.getTitle() + "', '" + c.getStatus() + "', '" + c.getDateAdded() + "')";
		System.out.println(addition);
		statement.executeUpdate(addition);
		return statement;
	}

	public static Statement addClientInfoToDB(DatabaseConnection database, Client c) throws SQLException {
		Connection connection = database.getConnection();
		Statement statement = connection.createStatement();
		String addition = "INSERT INTO client(name, phone, email) VALUES ('" + c.getName() + "', ";
		if (c.getPhone() != null) {
			addition = addition + "'" + c.getPhone() + "', ";
		}
		else {
			addition = addition + "NULL, ";
		}
		if (c.getEmail() != null) {
			addition = addition + "'" + c.getEmail() + "')";
		}
		else {
			addition = addition + "NULL)";
		}
		System.out.println(addition);
		statement.executeUpdate(addition);
		return statement;
	}

	public static Statement addAppointmentToDB(DatabaseConnection database, Appointment a) throws SQLException {
		Connection connection = database.getConnection();
		Statement statement = connection.createStatement();
		String addition = "INSERT INTO appointment(case_id, title, room, address, city, state, zip, date, status) VALUES (" +
				a.getCase().getCase_id() + ", '" + a.getTitle() + "', ";
		if (a.getRoom() != null) {
			addition = addition + "'" + a.getRoom() + "', ";
		}
		else {
			addition = addition + "NULL, ";
		}
		if (a.getAddress() != null) {
			addition = addition + "'" + a.getAddress() + "', ";
		}
		else {
			addition = addition + "NULL, ";
		}
		if (a.getCity() != null) {
			addition = addition + "'" + a.getCity() + "', ";
		}
		else {
			addition = addition + "NULL, ";
		}
		if (a.getState() != null) {
			addition = addition + "'" + a.getState() + "', ";
		}
		else {
			addition = addition + "NULL, ";
		}
		if (a.getZip() != null) {
			addition = addition + "'" + a.getZip() + "', ";
		}
		else {
			addition = addition + "NULL, ";
		}
		addition = addition + "'" + a.getDate().toString() + "', '" + a.getStatus() + "')";
		System.out.println(addition);
		statement.executeUpdate(addition);
		return statement;
	}

	public static Statement addDeadlineToDB(DatabaseConnection database, Deadline d) throws SQLException {
		Connection connection = database.getConnection();
		Statement statement = connection.createStatement();
		String addition = "INSERT INTO deadline(case_id, title, date, status) VALUES (" + d.getCase().getCase_id() + ", '" +
				d.getTitle() + "', '" + d.getDate() + "', '" + d.getStatus() + "')";
		System.out.println(addition);
		statement.executeUpdate(addition);
		return statement;
	}

	public static Statement delAppointmentInDB(DatabaseConnection database, Appointment a) throws SQLException {
		Connection connection = database.getConnection();
		Statement statement = connection.createStatement();
		String business = "DELETE FROM notification WHERE appointment_id = " + a.getAppointment_id();
		System.out.println(business);
		statement.executeUpdate(business);
		String deletion = "DELETE FROM appointment WHERE appointment_id = " + a.getAppointment_id();
		System.out.println(deletion);
		statement.executeUpdate(deletion);
		return statement;
	}

	public static Statement delDeadlineInDB(DatabaseConnection database, Deadline d) throws SQLException {
		Connection connection = database.getConnection();
		Statement statement = connection.createStatement();
		String business = "DELETE FROM notification WHERE deadline_id = " + d.getDeadline_id();
		System.out.println(business);
		statement.executeUpdate(business);
		String deletion = "DELETE FROM deadline WHERE deadline_id = " + d.getDeadline_id();
		System.out.println(deletion);
		statement.executeUpdate(deletion);
		return statement;
	}
	
	public static Statement delClientInfoInDB(DatabaseConnection database, Client c) throws SQLException {
		Connection connection = database.getConnection();
		Statement statement = connection.createStatement();
		String business = "UPDATE aCase SET client_id = null WHERE client_id = " + c.getClient_id();
		System.out.println(business);
		statement.executeUpdate(business);
		String deletion = "DELETE FROM client WHERE client_id = " + c.getClient_id();
		System.out.println(deletion);
		statement.executeUpdate(deletion);
		return statement;
	}

	public static Statement editCaseInDB(DatabaseConnection database, Case c) throws SQLException {
		Connection connection = database.getConnection();
		Statement statement = connection.createStatement();
		String edit = "UPDATE aCase SET title = '" + c.getTitle();
		if (c.getClient_id() > 0) {
			edit = edit + "', client_id = " + c.getClient().getClient_id();
		}
		else {
			edit = edit + "', client_id = NULL";
		}
		edit = edit + " WHERE case_id = " + c.getCase_id();
		System.out.println(edit);
		statement.executeUpdate(edit);
		return statement;
	}
	
	public static Statement editClientInfoInDB(DatabaseConnection database, Client c) throws SQLException {
		Connection connection = database.getConnection();
		Statement statement = connection.createStatement();
		String editClient = "UPDATE client SET name = '" + c.getName() + "', phone = '" + c.getPhone() +
				"', email = '" + c.getEmail() + "' WHERE client_id = " + c.getClient_id();
		System.out.println(editClient);
		statement.executeUpdate(editClient);
		return statement;
	}
	
	public static Statement editDeadlineInDB(DatabaseConnection database, Deadline d) throws SQLException {
		Connection connection = database.getConnection();
		Statement statement = connection.createStatement();
		String editDeadline = "UPDATE deadline SET title = '" + d.getTitle() + "', date = '" + d.getDate() +
				"', status = '" + d.getStatus() + "' WHERE deadline_id = " + d.getDeadline_id();
		System.out.println(editDeadline);
		statement.executeUpdate(editDeadline);
		return statement;
	}
	
	public static Statement editAppointmentInDB(DatabaseConnection database, Appointment a) throws SQLException {
		Connection connection = database.getConnection();
		Statement statement = connection.createStatement();
		String editAppointment = "UPDATE appointment SET title = '" + a.getTitle() + "', room = '" + a.getRoom() +
				"', address = '" + a.getAddress() + "', city = '" + a.getCity() + "', state = '" + a.getState() +
				"', zip = '" + a.getZip() + "', date = '" + a.getDate() + "', status = '" + a.getStatus() +
				"' WHERE appointment_id = " + a.getAppointment_id();
		System.out.println(editAppointment);
		statement.executeUpdate(editAppointment);
		return statement;
	}

	public static Statement archiveCaseInDB(DatabaseConnection database, Case c) throws SQLException {
		Connection connection = database.getConnection();
		Statement statement = connection.createStatement();
		String archival = "UPDATE aCase SET status = 'resolved', dateResolved = '" + c.getDateResolved() +
				"' WHERE case_id = " + c.getCase_id();
		System.out.println(archival);
		statement.executeUpdate(archival);
		return statement;
	}

	public static Statement removeCaseInDB(DatabaseConnection database, Case c) throws SQLException {
		Connection connection = database.getConnection();
		Statement statement = connection.createStatement();
		String removal = "UPDATE aCase SET status = 'removed', dateRemoved = '" + c.getDateRemoved() +
				"' WHERE case_ID = " + c.getCase_id();
		System.out.println(removal);
		statement.executeUpdate(removal);
		return statement;
	}

	public static Statement resumeCaseInDB(Case c) throws SQLException{
		Connection connection = DatabaseConnection.getConnection();
		Statement statement = connection.createStatement();
		String resume = "UPDATE aCase SET status = 'ongoing' WHERE case_ID = " + c.getCase_id();
		System.out.println(resume);
		statement.executeUpdate(resume);
		return statement;
	}

	public static Statement addNotificationToDB(Notification n) throws SQLException{
		Connection connection = DatabaseConnection.getConnection();
		Statement statement = connection.createStatement();
		String addition = "INSERT INTO notification(deadline_id, appointment_id, message, location, sendDate, sent) VALUES " + "(";
		if (n.getDeadline() != null) {
			addition = addition  + n.getDeadline().getDeadline_id() + ", 0";
		}
		else {
			addition = addition + "0, " + n.getAppointment().getAppointment_id();
		}
		addition = addition + ", '" + n.getMessage() + "', NULL, '" + n.getSendDate() + "', FALSE)";
		System.out.println(addition);
		statement.executeUpdate(addition);
		return statement;
	}

	public static void checkNotificationsInDB(DatabaseConnection database) throws SQLException {
		Connection connection = database.getConnection();
		Statement statement = connection.createStatement();
		String check = "SELECT * FROM notification WHERE datetime(sendDate) <= datetime('now')";
		System.out.println(check);
		ResultSet resultSet = statement.executeQuery(check);

		while(resultSet.next()){
			Notification notification = new Notification();
			int appointment_id = resultSet.getInt("appointment_id");
			int deadline_id = resultSet.getInt("deadline_id");
			if(appointment_id > 0){
				String aQuery = "SELECT * FROM appointment WHERE appointment_id = " + appointment_id;
				ResultSet appointmentSet = statement.executeQuery(aQuery);
				Appointment appointment = new Appointment();
				appointment.setTitle(appointmentSet.getString("title"));
				int case_id = appointmentSet.getInt("case_id");
				String cQuery = "SELECT * FROM aCase WHERE case_id = " + case_id;
				ResultSet caseSet = statement.executeQuery(cQuery);
				Case c = new Case(caseSet.getInt("case_id"), caseSet.getInt("client_id"),
						caseSet.getString("title"), caseSet.getString("status"),
						caseSet.getString("dateAdded"), caseSet.getString("dateResolved"),
						caseSet.getString("dateRemoved"));
				appointment.setCase(c);
				notification.setAppointment(appointment);
			}else{
				String dQuery = "SELECT * FROM deadline WHERE deadline_id = " + deadline_id;
				ResultSet deadlineSet = statement.executeQuery(dQuery);
				System.out.println(dQuery);
				Deadline deadline = new Deadline(deadlineSet.getString("title"),
						LocalDateTime.parse(deadlineSet.getString("date")));
				int case_id = deadlineSet.getInt("case_id");
				String cQuery = "SELECT * FROM aCase WHERE case_id = " + case_id;
				ResultSet caseSet = statement.executeQuery(cQuery);
				Case c = new Case(caseSet.getInt("case_id"), caseSet.getInt("client_id"),
						caseSet.getString("title"), caseSet.getString("status"),
						caseSet.getString("dateAdded"), caseSet.getString("dateResolved"),
						caseSet.getString("dateRemoved"));
				deadline.setCase(c);
				notification.setDeadline(deadline);
			}
			sendNotification(database, notification);
		}
	}

	public static void autoDeleteCasesInDB(DatabaseConnection database) throws SQLException {
		Connection connection = database.getConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate("DELETE FROM aCase WHERE status = 'removed' AND datetime(dateRemoved) < datetime('now', '-30 days')");
	}

	public static void sendNotification(DatabaseConnection database, Notification n) throws SQLException {
		NotificationSender sender = new NotificationSender();
		sender.sendNotification(n);
	}

}
