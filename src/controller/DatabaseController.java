package controller;

import model.*;

import java.sql.*;
import java.time.LocalDateTime;

public class DatabaseController {

	public static void addCaseToDB(DatabaseConnection database, Case c) throws SQLException {
		Connection connection = database.getConnection();
		PreparedStatement addition = connection.prepareStatement("INSERT INTO aCase(client_id, title, status, dateAdded)" +
				"VALUES (?, ?, ?, ?)");
		if (c.getClient() != null) {
			addition.setInt(1, c.getClient().getClient_id());
		}
		else {
			addition.setNull(1, Types.INTEGER);
		}
		addition.setString(2, c.getTitle());
		addition.setString(3, c.getStatus());
		addition.setString(4, c.getDateAdded().toString());
		addition.executeUpdate();
	}

	public static void addClientInfoToDB(DatabaseConnection database, Client c) throws SQLException {
		Connection connection = database.getConnection();
		PreparedStatement addition = connection.prepareStatement("INSERT INTO client(name, phone, email) VALUES (?, ?, ?)");
		addition.setString(1, c.getName());
		addition.setString(2, c.getPhone());
		addition.setString(3, c.getEmail());
		addition.executeUpdate();
	}

	public static void addAppointmentToDB(DatabaseConnection database, Appointment a) throws SQLException {
		Connection connection = database.getConnection();
		PreparedStatement addition = connection.prepareStatement("INSERT INTO appointment(case_id, title, room, " +
				"address, city, state, zip, date, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
		addition.setInt(1, a.getCase().getCase_id());
		addition.setString(2, a.getTitle());
		addition.setString(3, a.getRoom());
		addition.setString(4, a.getAddress());
		addition.setString(5, a.getCity());
		addition.setString(6, a.getState());
		addition.setString(7, a.getZip());
		addition.setString(8, a.getDate().toString());
		addition.setString(9, a.getStatus());
		addition.executeUpdate();
	}

	public static void addDeadlineToDB(DatabaseConnection database, Deadline d) throws SQLException {
		Connection connection = database.getConnection();
		PreparedStatement addition = connection.prepareStatement("INSERT INTO deadline(case_id, title, date, status) VALUES (?, ?, ?, ?)");
		addition.setInt(1, d.getCase().getCase_id());
		addition.setString(2, d.getTitle());
		addition.setString(3, d.getDate().toString());
		addition.setString(4, d.getStatus());
		addition.executeUpdate();
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

	public static void editCaseInDB(DatabaseConnection database, Case c) throws SQLException {
		Connection connection = database.getConnection();
		PreparedStatement edit = connection.prepareStatement("UPDATE aCase SET title = ?, " +
				"client_id = ? WHERE case_id = ?");
		edit.setString(1, c.getTitle());
		if (c.getClient_id() > 0) {
			edit.setInt(2, c.getClient().getClient_id());
		}
		else {
			edit.setNull(2, Types.INTEGER);
		}
		edit.setInt(3, c.getCase_id());
	}
	
	public static void editClientInfoInDB(DatabaseConnection database, Client c) throws SQLException {
		Connection connection = database.getConnection();
		PreparedStatement edit = connection.prepareStatement("UPDATE client SET name = ?, phone = ?, " +
				"email = ? WHERE client_id = ?");
		edit.setString(1, c.getName());
		edit.setString(2, c.getPhone());
		edit.setString(3, c.getEmail());
		edit.setInt(4, c.getClient_id());
		edit.executeUpdate();
	}
	
	public static void editDeadlineInDB(DatabaseConnection database, Deadline d) throws SQLException {
		Connection connection = database.getConnection();
		PreparedStatement edit = connection.prepareStatement("UPDATE deadline SET title = ?, date = ?, " +
				"status = ? WHERE deadline_id = ?");
		edit.setString(1, d.getTitle());
		edit.setString(2, d.getDate().toString());
		edit.setString(3, d.getStatus());
		edit.setInt(4, d.getDeadline_id());
		edit.executeUpdate();
	}
	
	public static void editAppointmentInDB(DatabaseConnection database, Appointment a) throws SQLException {
		Connection connection = database.getConnection();
		PreparedStatement edit = connection.prepareStatement("UPDATE appointment SET title = ?, room = ?, " +
				"address = ?, city = ?, state = ?, zip = ?, date = ?, status = ? WHERE appointment_id = ?");
		edit.setString(1, a.getTitle());
		edit.setString(2, a.getRoom());
		edit.setString(3, a.getAddress());
		edit.setString(4, a.getCity());
		edit.setString(5, a.getState());
		edit.setString(6, a.getZip());
		edit.setString(7, a.getDate().toString());
		edit.setString(8, a.getStatus());
		edit.setInt(9, a.getAppointment_id());
		edit.executeUpdate();
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
		String location = "', NULL, '";
		String addition = "INSERT INTO notification(deadline_id, appointment_id, message, location, sendDate, sent) VALUES " + "(";
		if (n.getDeadline() != null) {
			ResultSet deadlineSet = statement.executeQuery("SELECT * FROM deadline ORDER BY deadline_id DESC LIMIT 1");
			addition = addition  + deadlineSet.getInt("deadline_id") + ", 0";
		}
		else {
			ResultSet appointmentSet = statement.executeQuery("SELECT * FROM appointment ORDER BY appointment_id DESC LIMIT 1");
			addition = addition + "0, " + appointmentSet.getInt("appointment_id");
			location = "', " + n.getAppointment().getAddress() +", '";
		}
		addition = addition + ", '" + n.getMessage() + location + n.getSendDate() + "', FALSE)";
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
			notification.setMessage(resultSet.getString("message"));
			if(appointment_id > 0){
				String aQuery = "SELECT * FROM appointment WHERE appointment_id = " + appointment_id;
				System.out.println(aQuery);
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
