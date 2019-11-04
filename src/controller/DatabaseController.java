package controller;

import model.*;

import java.sql.*;

public class DatabaseController {

	public static Statement addCaseToDB(DatabaseConnection database, Case c) throws SQLException {
		Connection connection = database.getConnection();
		Statement statement = connection.createStatement();
		String addition = "INSERT INTO aCase(Client_ID, Title, Status, DateAdded) VALUES (" + c.getClient().getClient_id() + ", '" +
				c.getTitle() + "', '" + c.getStatus() + "', '" + c.getDateAdded() + "')";
		System.out.println(addition);
		statement.executeUpdate(addition);
		return statement;
	}

	public static Statement addClientInfoToDB(DatabaseConnection database, Client c) throws SQLException {
		Connection connection = database.getConnection();
		Statement statement = connection.createStatement();
		String addition = "INSERT INTO Client(Name, Phone, Email) VALUES ('" + c.getName() + "', ";
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
		String addition = "INSERT INTO Appointment(Case_ID, Title, Room, Address, City, State, ZIP, Date) VALUES (" +
				a.getCase().getCase_id() + ", '" + a.getTitle() + "', '";
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
		if (a.getZip() != -1) {
			addition = addition + "" + a.getZip() + ", ";
		}
		else {
			addition = addition + "NULL, ";
		}
		addition = addition + "'" + a.getDate() + "')";
		System.out.println(addition);
		statement.executeUpdate(addition);
		return statement;
	}

	public static Statement addDeadlineToDB(DatabaseConnection database, Deadline d) throws SQLException {
		Connection connection = database.getConnection();
		Statement statement = connection.createStatement();
		String addition = "INSERT INTO Deadline(Case_ID, Title, Date) VALUES (" + d.getCase().getCase_id() + ", '" +
				d.getTitle() + "', '" + d.getDate() + "')";
		System.out.println(addition);
		statement.executeUpdate(addition);
		return statement;
	}

	public static Statement delAppointmentInDB(DatabaseConnection database, Appointment a) throws SQLException {
		Connection connection = database.getConnection();
		Statement statement = connection.createStatement();
		String deletion = "DELETE FROM Appointment WHERE Appointment_ID = " + a.getAppointment_id();
		System.out.println(deletion);
		statement.executeUpdate(deletion);
		return statement;
	}

	public static Statement delDeadlineInDB(DatabaseConnection database, Deadline d) throws SQLException {
		Connection connection = database.getConnection();
		Statement statement = connection.createStatement();
		String deletion = "DELETE FROM Deadline WHERE Appointment_ID = " + d.getDeadline_id();
		System.out.println(deletion);
		statement.executeUpdate(deletion);
		return statement;
	}

	public static Statement editCaseInDB(DatabaseConnection database, Case c) throws SQLException {
		Connection connection = database.getConnection();
		Statement statement = connection.createStatement();
		String edit = "UPDATE aCase SET Title = '" + c.getTitle() + "' Client_ID = " + c.getClient().getClient_id() +
				" WHERE Case_ID = " + c.getCase_id();
		System.out.println(edit);
		statement.executeUpdate(edit);
		return statement;
	}

	public static Statement archiveCaseInDB(DatabaseConnection database, Case c) throws SQLException {
		Connection connection = database.getConnection();
		Statement statement = connection.createStatement();
		String archival = "UPDATE aCase SET Status = 'archived', DateResolved = '" + c.getDateResolved() +
				"' WHERE Case_ID = " + c.getCase_id();
		System.out.println(archival);
		statement.executeUpdate(archival);
		return statement;
	}

	public static Statement removeCaseInDB(DatabaseConnection database, Case c) throws SQLException {
		Connection connection = database.getConnection();
		Statement statement = connection.createStatement();
		String removal = "UPDATE aCase SET Status = 'removed', DateResolved = '" + c.getDateRemoved() +
				"' WHERE Case_ID = " + c.getCase_id();
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
