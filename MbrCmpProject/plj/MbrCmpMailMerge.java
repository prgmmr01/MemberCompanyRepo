package com.MbrCmpProject.plj;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Year;

import javax.swing.JOptionPane;

public class MbrCmpMailMerge {

	ResultSet rstSet = null;
	PreparedStatement preStmt = null;
	Connection connection = null;

	public void recipientsData(String sqlQuery) {
		// Connect to database...
		connection = sqlConnection.dbConnector();
		// Query based on options select from Report menu...
		sqlQuery = "Select * From MemberPremiums Where ParticipationYear=?";
		int currentYear = Year.now().getValue();
		try {
			preStmt = connection.prepareStatement(sqlQuery);
			preStmt.setInt(1, currentYear);
			rstSet = preStmt.executeQuery();
			while (rstSet.next()) {

			}
		} catch (Exception expAdd) {
			JOptionPane.showMessageDialog(null, expAdd.getMessage());
			// Final steps to close out Queries...
		} finally {
			// Close the Result Set...
			if (rstSet != null) {
				try {
					rstSet.close();
				} catch (Exception expClose) {
					JOptionPane.showMessageDialog(null, expClose);
				}
			}
			// Close the Prepared Statement...
			if (preStmt != null) {
				try {
					preStmt.close();
				} catch (Exception expClose) {
					JOptionPane.showMessageDialog(null, expClose);
				}
			}
			// Close the Connection...
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception expClose) {
					JOptionPane.showMessageDialog(null, expClose);
				}
			}

		}
	}

}
