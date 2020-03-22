package com.MbrCmpProject.plj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.microsoft.sqlserver.jdbc.SQLServerPreparedStatement;

public class MbrCmpLblPrinting {

	ResultSet rstSet = null;
	PreparedStatement preStmt = null;
	Connection connection = null;
	String sqlQuery;

	public MbrCmpLblPrinting() {
	}

	public MbrCmpLblPrinting(DatabaseVariables dbRptVar, String sqlScript) {

		try {
			// Connect to database...
			connection = sqlConnection.dbConnector();

			// Getting Started with the SQL Server JDBC...
			SQLServerPreparedStatement preStmt = (SQLServerPreparedStatement) connection
					.prepareStatement(sqlScript);
			preStmt.setString(1, dbRptVar.getDbStateCode());
			preStmt.execute();
			dbRptVar.setGoodToGo(true);

			String title=null;
			switch (dbRptVar.getDbStateCode()) {
			case "D":
				sqlQuery = "Select Distinct * From CheckDataDE";
				title = "DE_Checks";
				break;
			case "P":
				sqlQuery = "Select Distinct * From CheckDataPA";
				title = "PA_Checks";
				break;
			case "W":
				sqlQuery = "Select Distinct * From CheckDataWV";
				title = "WV_Checks";
				break;
			}
			//POI - Generate Excel Assess Spreadsheet...
			WordGeneratorClass wordGenerator = new WordGeneratorClass(sqlQuery, title);
			wordGenerator.dispose();
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			dbRptVar.setGoodToGo(false);
			// Final steps to close out queries...
		} finally {
			// Close the Prepared Statement...
			if (preStmt != null) {
				try {
					preStmt.close();
				} catch (Exception expClose) {
					JOptionPane.showMessageDialog(null, expClose);
					dbRptVar.setGoodToGo(false);
				}
			}
			// Close the Connection...
			try {
				if ((connection != null) && (!connection.isClosed())) {
					connection.close();
				}
			} catch (Exception expClose) {
				JOptionPane.showMessageDialog(null, expClose);
			}
		}

	}
}
