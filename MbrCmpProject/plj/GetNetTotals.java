package com.MbrCmpProject.plj;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class GetNetTotals extends JFrame {

	private static final long serialVersionUID = 1L;
	ResultSet rstSet = null;
	PreparedStatement preStmt = null;
	Connection connection = null;
	Double dblNbr = 1000.00;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GetNetTotals frame = new GetNetTotals();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GetNetTotals() {}

	public GetNetTotals(DatabaseVariables totalVar) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		// Retrieve database values...
			// Connect to database...
			connection = sqlConnection.dbConnector();

			// Query based on options select from MbrCompanyMain menu...
			try {
				String sqlQuery = 
				"SELECT Sum(ParticipationAmount) sumAmount, Sum(ParticipationPercentage) sumPercent" + 
				"  From FP_Member_Companies..MemberPremiums" + 
				"  Where StateCode = ? and ParticipationYear = ? ";
				preStmt = connection.prepareStatement(sqlQuery);
				preStmt.setString(1, totalVar.getPromptState());
				preStmt.setInt(2, totalVar.getPromptRunYear());
				rstSet = preStmt.executeQuery();
				while (rstSet.next()) {
					Double sumAmt = (double) rstSet.getInt("sumAmount") * dblNbr;
					switch (totalVar.getPromptState()) {
					case "D":
						totalVar.setPromptDETotals(sumAmt);
						totalVar.setPromptDEPercent(rstSet.getInt("sumPercent"));
						break;
					case "P":
						totalVar.setPromptPATotals(sumAmt);
						totalVar.setPromptPAPercent(rstSet.getInt("sumPercent"));
						break;
					case "W":
						totalVar.setPromptWVTotals(sumAmt);
						totalVar.setPromptWVPercent(rstSet.getInt("sumPercent"));
						break;
					}
				}

			} catch (Exception expAdd) {
				JOptionPane.showMessageDialog(null, expAdd);
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
