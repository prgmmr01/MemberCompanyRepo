package com.MbrCmpProject.plj;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.microsoft.sqlserver.jdbc.SQLServerPreparedStatement;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MbrCmpLabelPrinting extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtState;
	private String sqlQuery, title;
	ResultSet rstSet = null;
	PreparedStatement preStmt = null;
	Connection connection = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MbrCmpLabelPrinting dialog = new MbrCmpLabelPrinting();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public MbrCmpLabelPrinting() {}

	public MbrCmpLabelPrinting(String sqlScript) {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblMemberCompanyLabels = new JLabel("Member Company Labels");
			lblMemberCompanyLabels.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblMemberCompanyLabels.setBounds(90, 25, 231, 20);
			contentPanel.add(lblMemberCompanyLabels);
		}
		{
			JLabel lblEnterState = new JLabel("Enter State");
			lblEnterState.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblEnterState.setBounds(165, 75, 85, 20);
			contentPanel.add(lblEnterState);
		}

		txtState = new JTextField();
		txtState.setBounds(190, 100, 25, 20);
		contentPanel.add(txtState);
		txtState.setColumns(10);

		JLabel lblInvalidState = new JLabel("Invalid State");
		lblInvalidState.setHorizontalAlignment(SwingConstants.CENTER);
		lblInvalidState.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblInvalidState.setBounds(165, 145, 85, 14);
		lblInvalidState.setVisible(false);
		contentPanel.add(lblInvalidState);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (txtState.getText()) {
				case "D":
					sqlQuery = "Select Distinct * From LabelDataDE";
					title = "DE_Labels";
					break;
				case "P":
					sqlQuery = "Select Distinct * From LabelDataPA";
					title = "PA_Labels";
					break;
				case "W":
					sqlQuery = "Select Distinct * From LabelDataWV";
					title = "WV_Labels";
					break;
				default:
					lblInvalidState.setVisible(true);
					sqlQuery=null;
				}
				if (sqlQuery != null) {
					
					generateLabels(sqlScript,txtState.getText());

					WordLabelsGenerator LabelGenerator = new WordLabelsGenerator(sqlQuery,title);
					LabelGenerator.dispose();
				}
			}

		});

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}

		});

	}
	private void generateLabels(String sqlScript, String stCode) {
		// Connect to database...
		connection = sqlConnection.dbConnector();

		// Getting Started with the SQL Server JDBC...
		SQLServerPreparedStatement preStmt;
		try {
			preStmt = (SQLServerPreparedStatement) connection
					.prepareStatement(sqlScript);
			//preStmt.setString(1, stCode);
			preStmt.execute();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
}
