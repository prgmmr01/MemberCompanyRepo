package com.MbrCmpProject.plj;

import java.sql.*;

import javax.swing.*;

public class sqlConnection {
	Connection conn = null;

	public static Connection dbConnector() {
		try {
			// Establish the connection.
			/*String pwd="M$5278@pj#1", usr="MbrCmp";
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			String url="jdbc:sqlserver://FPTEST1:1433;DatabaseName=FP_Member_Companies;" + */
			String pwd="M$7267#@pj#1", usr="MbrCmp";
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			String url="jdbc:sqlserver://10.60.42.40:1433;DatabaseName=FP_Member_Companies;" + 
					"user="+usr+";password=" + pwd;
			Connection conn = DriverManager.getConnection(url);
			return conn;
		} catch (Exception exp) {
			JOptionPane.showMessageDialog(null, exp);
			return null;
		}

	}

}
	