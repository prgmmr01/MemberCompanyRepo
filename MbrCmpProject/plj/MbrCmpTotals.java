package com.MbrCmpProject.plj;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class MbrCmpTotals extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtDeTotal;
	private JTextField txtPaTotal;
	private JTextField txtWvTotal;
	private int qryNAIC, qrySequence = 10;
	private String qryStateCode;
	public double dePremiumAmount, paPremiumAmount, wvPremiumAmount;

	// Database components...
	DatabaseVariables tlDbVar = new DatabaseVariables();
	DefaultTableModel totalModel = new DefaultTableModel();
	ResultSet rstSet = null;
	PreparedStatement preStmt = null;
	Connection connection = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MbrCmpTotals frame = new MbrCmpTotals();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MbrCmpTotals() {	}

	/**
	 * Create the frame.
	 * 
	 * @param cmpNAIC
	 */
	public MbrCmpTotals(JTextField cmpNAIC) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 675);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblInsurancePlacementFacility = new JLabel("Insurance Placement Facility of Pennsylvania");
		lblInsurancePlacementFacility.setFont(new Font("Times New Roman", Font.BOLD, 28));
		lblInsurancePlacementFacility.setBounds(142, 10, 562, 42);
		contentPane.add(lblInsurancePlacementFacility);

		JLabel lblMemberCompanies = new JLabel("Member Company File Maintenance");
		lblMemberCompanies.setFont(new Font("Times New Roman", Font.BOLD, 24));
		lblMemberCompanies.setBounds(229, 68, 387, 29);
		contentPane.add(lblMemberCompanies);

		JLabel lblTotalTitle = new JLabel("ENTER CURRENT MEMBER COMPANY TOTALS");
		lblTotalTitle.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblTotalTitle.setBounds(200, 205, 460, 20);
		contentPane.add(lblTotalTitle);

		JLabel lblDelaware = new JLabel("Delaware");
		lblDelaware.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblDelaware.setBounds(130, 301, 86, 20);
		contentPane.add(lblDelaware);

		JLabel lblPennsylvania = new JLabel("Pennsylvania");
		lblPennsylvania.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblPennsylvania.setBounds(372, 301, 118, 20);
		contentPane.add(lblPennsylvania);

		JLabel lblWestVirginia = new JLabel("West Virginia");
		lblWestVirginia.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblWestVirginia.setBounds(607, 301, 118, 20);
		contentPane.add(lblWestVirginia);

		// Process Totals...
		qryNAIC = Integer.parseInt(cmpNAIC.getText());
		qryStateCode = "D";
		SumTotals(tlDbVar, qryNAIC, qryStateCode, qrySequence);

		// Place Totals
		txtDeTotal = new JTextField();
		txtDeTotal.setBounds(89, 335, 175, 20);
		txtDeTotal.setText(Double.toString(dePremiumAmount));
		contentPane.add(txtDeTotal);
		txtDeTotal.setColumns(10);

		txtPaTotal = new JTextField();
		txtPaTotal.setColumns(10);
		txtPaTotal.setBounds(335, 335, 175, 20);
		txtPaTotal.setText(Double.toString(paPremiumAmount));
		contentPane.add(txtPaTotal);

		txtWvTotal = new JTextField();
		txtWvTotal.setColumns(10);
		txtWvTotal.setBounds(580, 335, 175, 20);
		txtWvTotal.setText(Double.toString(wvPremiumAmount));
		contentPane.add(txtWvTotal);

		JButton btnTotalEnter = new JButton("Enter");
		btnTotalEnter.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnTotalEnter.setBounds(90, 545, 115, 30);
		contentPane.add(btnTotalEnter);

		JButton btnTotalReturn = new JButton("Cancel");
		btnTotalReturn.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnTotalReturn.setBounds(630, 545, 115, 30);
		btnTotalReturn.addActionListener(new ActionListener() { // Open Enter Key Listener...
			// Process Main Menu Selection...
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		}); // Close Enter Key Listener...
		contentPane.add(btnTotalReturn);

	}

	// Retrieve database values...
	public void SumTotals(DatabaseVariables tlDbVar, int qryNAIC, String qryStateCode, int qrySequence) {

		// Connect to database...
		connection = sqlConnection.dbConnector();

		// Query based on options select from MbrCompanyMain menu...
		try {
			String sqlQuery = "Select * From MemberPremiums " + "Where NAIC=?";
			preStmt = connection.prepareStatement(sqlQuery);
			preStmt.setInt(1, qryNAIC);
			rstSet = preStmt.executeQuery();
			//Retrieve the column names...
			ResultSetMetaData rstMeta = rstSet.getMetaData();
			//Get the number of columns in the table...
			int columnCount = rstMeta.getColumnCount();
			//Declare object array to store various data types...
			Object row[] = new Object[columnCount];
			// read result set...
			while (rstSet.next()) {
				//JOptionPane.showMessageDialog(null, "Have a result set with "+columnCount+" Columns");
				//populate data into arrays...
				for (int i = 0; i < columnCount; i++) {
					row[i] = rstSet.getString(i + 1);
				}
				int intRow = Integer.parseInt(row[1].toString());
				if (intRow <= qrySequence) {
					//qryStateCode = row[3].toString();
					switch (row[3].toString()) {
					case "D":
						tlDbVar.setDbPremiumAmount(rstSet.getInt("ParticipationAmount"));
						dePremiumAmount += tlDbVar.getDbPremiumAmount();
						break;
					case "P":
						tlDbVar.setDbPremiumAmount(rstSet.getInt("ParticipationAmount"));
						paPremiumAmount += tlDbVar.getDbPremiumAmount();
						//JOptionPane.showMessageDialog(null, intRow + " Amount is " + paPremiumAmount);
						break;
					case "W":
						tlDbVar.setDbPremiumAmount(rstSet.getInt("ParticipationAmount"));
						wvPremiumAmount += tlDbVar.getDbPremiumAmount();
						break;
					}
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
