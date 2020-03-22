package com.MbrCmpProject.plj;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.border.EmptyBorder;

public class MbrCmpReports extends JFrame {

	ResultSet rstSet = null;
	Connection connection = null;
	CallableStatement proc_stmt = null;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String Proc, sqlQuery, title;
	private JRadioButton rdbtnReport1, rdbtnReport2, rdbtnReport3, // rdbtnReport4,
			rdbtnReport4, rdbtnReport5, rdbtnReport6, rdbtnReport7;
	ButtonGroup reportGroup;
	DatabaseVariables dbRptVar = new DatabaseVariables();
	MbrCmpEquitySelection mbrEquity;
	MbrCmpAssessSelection mbrAssess;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MbrCmpReports frame = new MbrCmpReports();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MbrCmpReports() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 920, 670);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblInsurancePlacementFacility = new JLabel("Insurance Placement Facility of Pennsylvania");
		lblInsurancePlacementFacility.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblInsurancePlacementFacility.setBounds(170, 10, 562, 40);
		contentPane.add(lblInsurancePlacementFacility);

		JLabel lblMemberCompanies = new JLabel("Member Company Report Process");
		lblMemberCompanies.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblMemberCompanies.setBounds(260, 50, 363, 30);
		contentPane.add(lblMemberCompanies);

		JButton btnReportCancel = new JButton("Cancel");
		btnReportCancel.addActionListener(new ActionListener() {
			// Return to Main Menu Selection...
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnReportCancel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnReportCancel.setBounds(725, 570, 115, 30);
		contentPane.add(btnReportCancel);

		// Define radio buttons and place them on the default panel...
		rdbtnReport1 = new JRadioButton("Member Company NAIC Premium Listing");
		rdbtnReport1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		rdbtnReport1.setBounds(100, 125, 450, 30);
		contentPane.add(rdbtnReport1);
		rdbtnReport2 = new JRadioButton("Member Company Participation Premium Listing");
		rdbtnReport2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		rdbtnReport2.setBounds(100, 165, 550, 30);
		contentPane.add(rdbtnReport2);
		rdbtnReport3 = new JRadioButton("Member Company Participation Premium 10 yrs Listing");
		rdbtnReport3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		rdbtnReport3.setBounds(100, 205, 600, 30);
		contentPane.add(rdbtnReport3);
		rdbtnReport4 = new JRadioButton("Member Company Equity Letters");
		rdbtnReport4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		rdbtnReport4.setBounds(100, 285, 375, 30);
		contentPane.add(rdbtnReport4);
		rdbtnReport5 = new JRadioButton("Member Company Assessment Letters");
		rdbtnReport5.setFont(new Font("Tahoma", Font.PLAIN, 20));
		rdbtnReport5.setBounds(100, 325, 625, 30);
		contentPane.add(rdbtnReport5);
		rdbtnReport6 = new JRadioButton("Member Company Check Printing - Download File");
		rdbtnReport6.setFont(new Font("Tahoma", Font.PLAIN, 20));
		rdbtnReport6.setBounds(100, 405, 625, 30);
		contentPane.add(rdbtnReport6);
		rdbtnReport7 = new JRadioButton("Member Company Labels - Download File");
		rdbtnReport7.setFont(new Font("Tahoma", Font.PLAIN, 20));
		rdbtnReport7.setBounds(100, 445, 600, 30);
		contentPane.add(rdbtnReport7);

		// Add radio buttons to group to control that only one button is selected...
		reportGroup = new ButtonGroup();
		reportGroup.add(rdbtnReport1);
		reportGroup.add(rdbtnReport2);
		reportGroup.add(rdbtnReport3);
		reportGroup.add(rdbtnReport4);
		reportGroup.add(rdbtnReport5);
		reportGroup.add(rdbtnReport6);
		reportGroup.add(rdbtnReport7);

		// Set action name to radio buttons to determine which button was selected...
		rdbtnReport1.setActionCommand("1st");
		rdbtnReport2.setActionCommand("2nd");
		rdbtnReport3.setActionCommand("3rd");
		rdbtnReport4.setActionCommand("4th");
		rdbtnReport5.setActionCommand("5th");
		rdbtnReport6.setActionCommand("6th");
		rdbtnReport7.setActionCommand("7th");

		// Perform action based on selected button...
		JRootPane rootPane = contentPane.getRootPane();
		JButton btnReportEnter = new JButton("Enter");
		btnReportEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radioSelection();
			}
		});
		btnReportEnter.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnReportEnter.setBounds(60, 570, 115, 30);
		contentPane.add(btnReportEnter);
		rootPane.setDefaultButton(btnReportEnter);
	}

	public void GetData(String procName) {
		// run query to create the data need for the report...
		// ***prepare data for the report...
		try {
			// Connect to database...
			connection = sqlConnection.dbConnector();
			proc_stmt = connection.prepareCall(procName);
			proc_stmt.execute();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public void radioSelection() {
		dbRptVar.setReportSequence(reportGroup.getSelection().getActionCommand());
		switch (dbRptVar.getReportSequence()) {
		case "1st": // Member Company NAIC Premium List...
			// Call procedure to create SQL table...
			Proc = "{ call Member_Company_NAIC_Premium_Data() }";
			GetData(Proc); // Create table...PremiumTotalData
			// Open Microsoft Excel macro template...Uses SQL Table PremiumTotalData...
			// POI - Generate Excel Assess Spreadsheet...
			sqlQuery = "Select * From FP_Member_Companies..PremiumTotalData ";
			title = "Member Company NAIC Premium Listing";
			ExcelGeneratorClass excelGenerator = new ExcelGeneratorClass(sqlQuery, null, title);
			excelGenerator.dispose();
			break;
		case "2nd": // Member Company Participation Premium List...
			// Call java class used to create SQL table...
			dbRptVar.setPromptRunYear(Calendar.getInstance().get(Calendar.YEAR));
			dbRptVar.setRptNumber(2);
			dbRptVar.setPromptState("D");
			GetNetTotals getTotal1 = new GetNetTotals(dbRptVar);
			getTotal1.dispose();
			dbRptVar.setPromptState("P");
			GetNetTotals getTotal2 = new GetNetTotals(dbRptVar);
			getTotal2.dispose();
			dbRptVar.setPromptState("W");
			GetNetTotals getTotal3 = new GetNetTotals(dbRptVar);
			getTotal3.dispose();
			// Produce report...
			MbrCmpPrompt prompt = new MbrCmpPrompt(dbRptVar);
			prompt.setFocusable(true);
			prompt.setVisible(true);
			break;
		case "3rd": // Member Company Participation Premium 10 Year List...
			dbRptVar.setRptNumber(3);
			dbRptVar.setGoodToGo(true);
			MbrCmpDateRanges dateRanges = new MbrCmpDateRanges(dbRptVar);
			dateRanges.dispose();
			dateRanges.setVisible(true);
			break;
		case "4th":
			// Member Company Equity Letter...
			dbRptVar.setRptNumber(4);
			mbrEquity = new MbrCmpEquitySelection(dbRptVar);
			mbrEquity.dispose();
			mbrEquity.setVisible(true);
			break;
		case "5th":
			// Member Company Assets Letter...
			dbRptVar.setRptNumber(5);
			mbrAssess = new MbrCmpAssessSelection(dbRptVar);
			mbrAssess.dispose();
			mbrAssess.setVisible(true);

			break;
		case "6th":
			// Member Company Check Disbursement...
			String strStateCode = JOptionPane.showInputDialog("Enter State Code: ");
			dbRptVar.setDbStateCode(strStateCode);
			String sqlScript = "Exec Member_Company_Check_Printing ?";
			MbrCmpCheckPrinting checkPrinting = new MbrCmpCheckPrinting(dbRptVar, sqlScript);
			break;
		case "7th":
			// Member Company Label Data...
			dbRptVar.setRptNumber(7);
			MbrCmpLabelSelection labelPrinting = new MbrCmpLabelSelection();
			labelPrinting.dispose();
			labelPrinting.setVisible(true);
			// labelPrinting.getDefaultCloseOperation();
			break;
		}
		reportGroup.clearSelection();
	}
}
