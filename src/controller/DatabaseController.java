package controller;

import model.*;

import java.sql.*;

public class DatabaseController {

	public static Statement addCaseToDB(DatabaseConnection database, Case c) throws SQLException {
		Connection connection = database.getConnection();
		Statement statement = connection.createStatement();
		//
		String addition = "INSERT INTO aCase(client_id, title, status, dateAdded) VALUES (";
		if (c.getClient() != null) {
			addition = addition + c.getClient().getClient_id() + ", '";
		} else {
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
		String addition = "INSERT INTO appointment(case_id, title, room, address, city, state, zip, date) VALUES (" +
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
		addition = addition + "'" + a.getDate().toString() + "')";
		System.out.println(addition);
		statement.executeUpdate(addition);
		return statement;
	}

	public static Statement addDeadlineToDB(DatabaseConnection database, Deadline d) throws SQLException {
		Connection connection = database.getConnection();
		Statement statement = connection.createStatement();
		String addition = "INSERT INTO deadline(case_id, title, date) VALUES (" + d.getCase().getCase_id() + ", '" +
				d.getTitle() + "', '" + d.getDate() + "')";
		System.out.println(addition);
		statement.executeUpdate(addition);
		return statement;
	}

	public static Statement delAppointmentInDB(DatabaseConnection database, Appointment a) throws SQLException {
		Connection connection = database.getConnection();
		Statement statement = connection.createStatement();
		String deletion = "DELETE FROM appointment WHERE appointment_id = " + a.getAppointment_id();
		System.out.println(deletion);
		statement.executeUpdate(deletion);
		return statement;
	}

	public static Statement delDeadlineInDB(DatabaseConnection database, Deadline d) throws SQLException {
		Connection connection = database.getConnection();
		Statement statement = connection.createStatement();
		String deletion = "DELETE FROM deadline WHERE deadline_id = " + d.getDeadline_id();
		System.out.println(deletion);
		statement.executeUpdate(deletion);
		return statement;
	}

	public static Statement editCaseInDB(DatabaseConnection database, Case c) throws SQLException {
		Connection connection = database.getConnection();
		Statement statement = connection.createStatement();
		String edit = "UPDATE aCase SET title = '" + c.getTitle() + "' client_id = " + c.getClient().getClient_id() +
				" WHERE case_id = " + c.getCase_id();
		System.out.println(edit);
		statement.executeUpdate(edit);
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

	public static void checkNotificationsInDB(DatabaseConnection database) throws SQLException {
		Connection connection = database.getConnection();
		Statement statement = connection.createStatement();
		//TODO Implement
	}

	public static void checkDeletedCasesInDB(DatabaseConnection database) throws SQLException {
		Connection connection = database.getConnection();
		Statement statement = connection.createStatement();
		//TODO Implement
	}

	public static void sendNotification(DatabaseConnection database, Notification n) throws SQLException {
		Connection connection = database.getConnection();
		Statement statement = connection.createStatement();
		//TODO Implement
	}

}
