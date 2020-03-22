package com.MbrCmpProject.plj;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.border.*;

import com.microsoft.sqlserver.jdbc.SQLServerPreparedStatement;

public class MbrCmpPrompt extends JFrame {

	Connection connection = null;
	ResultSet rstSet = null;
	PreparedStatement call_stmt = null;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtRunYear;
	private JFormattedTextField txtDeTotal, txtPaTotal, txtWvTotal;
	//private String Report = null;
	private String sqlQuery,title;

	DecimalFormat decimalFormat = new DecimalFormat("###,###,##0.00;-###,###,##0.00");
	DecimalFormatSymbols symbols = new DecimalFormatSymbols();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MbrCmpPrompt frame = new MbrCmpPrompt();
					frame.setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MbrCmpPrompt() {}

	/**
	 * Create the frame.
	 */
	public MbrCmpPrompt(DatabaseVariables dbPromptVar) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 870, 710);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblInsurancePlacementFacility = new JLabel("Insurance Placement Facility of Pennsylvania");
		lblInsurancePlacementFacility.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblInsurancePlacementFacility.setBounds(142, 10, 562, 42);
		getContentPane().add(lblInsurancePlacementFacility);

		JLabel lblMemberCompanies = new JLabel("Member Company File Maintenance");
		lblMemberCompanies.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblMemberCompanies.setBounds(212, 60, 403, 30);
		getContentPane().add(lblMemberCompanies);

		JRootPane rootPane = contentPane.getRootPane();
		// Start Enter Button...
		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// dbPromptVar = new DatabaseVariables();
				dbPromptVar.setGoodToGo(true);
				// 2nd Letter Member Company Participation Premium Listing...
				if (dbPromptVar.getRptNumber() > 0 && dbPromptVar.getRptNumber() < 7
						&& dbPromptVar.getRptNumber() != 9) {
					CheckReportErrors(dbPromptVar);
					if (dbPromptVar.getGoodToGo()) {
						dbPromptVar.setPromptRunYear(Integer.parseInt(txtRunYear.getText()));
						try {
							dbPromptVar.setPromptDETotals(decimalFormat.parse(txtDeTotal.getText()).doubleValue());
							dbPromptVar.setPromptPATotals(decimalFormat.parse(txtPaTotal.getText()).doubleValue());
							dbPromptVar.setPromptWVTotals(decimalFormat.parse(txtWvTotal.getText()).doubleValue());
						} catch (ParseException e1) {
							JOptionPane.showMessageDialog(null, e);;
						}
						Prompt4Parameters(dbPromptVar);
					}
				} 
			}
		});
		btnEnter.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnEnter.setBounds(40, 580, 115, 30);
		getContentPane().add(btnEnter);
		rootPane.setDefaultButton(btnEnter);

		JButton btnExit = new JButton("Cancel");
		btnExit.addActionListener(new ActionListener() {
			// Return to Report Selection...
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnExit.setBounds(685, 580, 115, 30);
		getContentPane().add(btnExit);

		JLabel lblEnterTheRun = new JLabel("Enter the Run Year");
		lblEnterTheRun.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblEnterTheRun.setBounds(310, 125, 200, 30);
		contentPane.add(lblEnterTheRun);

		txtRunYear = new JTextField();
		txtRunYear.setHorizontalAlignment(SwingConstants.CENTER);
		txtRunYear.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		});
		txtRunYear.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtRunYear.setBounds(365, 168, 75, 25);
		contentPane.add(txtRunYear);
		txtRunYear.setColumns(10);

		JSeparator sep1 = new JSeparator();
		sep1.setForeground(Color.BLACK);
		sep1.setBackground(Color.BLACK);
		sep1.setBounds(50, 255, 760, 2);
		contentPane.add(sep1);

		JLabel lblTotalPremium = new JLabel("Enter the Total Premium for Each State Being Ran");
		lblTotalPremium.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalPremium.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblTotalPremium.setBounds(50, 225, 760, 25);
		contentPane.add(lblTotalPremium);

		JLabel lblDelaware = new JLabel("Delaware");
		lblDelaware.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblDelaware.setBounds(75, 265, 115, 25);
		contentPane.add(lblDelaware);

		JLabel lblPennsylvania = new JLabel("Pennsylvania");
		lblPennsylvania.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblPennsylvania.setBounds(365, 265, 145, 25);
		contentPane.add(lblPennsylvania);

		JLabel lblWestVirginia = new JLabel("West Virginia");
		lblWestVirginia.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblWestVirginia.setBounds(640, 265, 145, 25);
		contentPane.add(lblWestVirginia);

		txtDeTotal = new JFormattedTextField();
		txtDeTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		txtDeTotal.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		});
		txtDeTotal.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtDeTotal.setBounds(50, 290, 175, 25);
		txtDeTotal.setText(decimalFormat.format(dbPromptVar.getPromptDETotals()));
		contentPane.add(txtDeTotal);
		txtDeTotal.setColumns(10);

		txtPaTotal = new JFormattedTextField();
		txtPaTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		txtPaTotal.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		});
		txtPaTotal.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtPaTotal.setBounds(325, 290, 200, 25);
		txtPaTotal.setText(decimalFormat.format(dbPromptVar.getPromptPATotals()));
		contentPane.add(txtPaTotal);
		txtPaTotal.setColumns(10);

		txtWvTotal = new JFormattedTextField();
		txtWvTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		txtWvTotal.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		});
		txtWvTotal.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtWvTotal.setBounds(615, 290, 175, 25);
		txtWvTotal.setText(decimalFormat.format(dbPromptVar.getPromptWVTotals()));
		contentPane.add(txtWvTotal);
		txtWvTotal.setColumns(10);

		JSeparator sep3 = new JSeparator();
		sep3.setForeground(Color.BLACK);
		sep3.setBackground(Color.BLACK);
		sep3.setBounds(50, 475, 760, 2);
		contentPane.add(sep3);

	}

	public void Prompt4Parameters(DatabaseVariables dbPromptVar) {
		if (dbPromptVar.getRptNumber() == 2) {
			// Call procedure to create SQL table...
			dbPromptVar.setProc("Exec dbo.Member_Company_Participation_Premium_Listing ?"); // Member_Company_Participation_Premium_Listing
			//Create tables...ParticiptionPremiumTotalDataDE,ParticiptionPremiumTotalDataPA,ParticiptionPremiumTotalDataWV
			GetData2(dbPromptVar.getProc(), dbPromptVar); 
			if (dbPromptVar.getGoodToGo()) { // Open Microsoft Word macro template...
				/* Delaware...Pennsylvania...West Virginia...
				   Open Microsoft Excel macro template...Uses SQL Table PremiumTotalData...
				   POI - Generate Excel Assess Spreadsheet... */
				sqlQuery = "Select * From FP_Member_Companies..ParticiptionListingDataDE";
				title = "Member Company Participation Premium Listing for Delaware";
				ExcelGeneratorClass excelGenerator1 = new ExcelGeneratorClass(sqlQuery, null,title);
				excelGenerator1.dispose();
				sqlQuery = "Select * From FP_Member_Companies..ParticiptionListingDataPA";
				title = "Member Company Participation Premium Listing for Pennsylvania";
				ExcelGeneratorClass excelGenerator2 = new ExcelGeneratorClass(sqlQuery, null,title);
				excelGenerator2.dispose();
				sqlQuery = "Select * From FP_Member_Companies..ParticiptionListingDataWV";
				title = "Member Company Participation Premium Listing for West Virginia";
				ExcelGeneratorClass excelGenerator3 = new ExcelGeneratorClass(sqlQuery, null,title);
				excelGenerator3.dispose();
			}
		}
	}

	public void GetData2(String procName, DatabaseVariables dbPromptVar) {
		// run query to create the data need for the report...
		// ***prepare data for the report...
		// DatabaseVariables dbPromptVar=new DatabaseVariables();
		try {
			// Connect to database...
			connection = sqlConnection.dbConnector();
			SQLServerPreparedStatement call_stmt = (SQLServerPreparedStatement) connection
					.prepareStatement(procName);
			if (dbPromptVar.getRptNumber() == 2) {
				call_stmt.setInt(1, dbPromptVar.getPromptRunYear());
			}
			call_stmt.execute();
			dbPromptVar.setGoodToGo(true);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			dbPromptVar.setGoodToGo(false);
		} finally {
			// Close the Result Set...
			if (rstSet != null) {
				try {
					rstSet.close();
				} catch (Exception expClose) {
					JOptionPane.showMessageDialog(null, expClose);
					dbPromptVar.setGoodToGo(false);
				}
			}
			// Close the Prepared Statement...
			if (call_stmt != null) {
				try {
					call_stmt.close();
				} catch (Exception expClose) {
					JOptionPane.showMessageDialog(null, expClose);
					dbPromptVar.setGoodToGo(false);
				}
			}
			// Close the Connection...
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception expClose) {
					JOptionPane.showMessageDialog(null, expClose);
					dbPromptVar.setGoodToGo(false);
				}
			}
		}
	}

	public void GetDocument(String macroName,DatabaseVariables dbPromptVar) {
		// ***run the report...
		try {
			// Open document...
			Desktop.getDesktop().open(new File(macroName));
		} catch (IOException io) {
			JOptionPane.showMessageDialog(null, io);
			dbPromptVar.setGoodToGo(false);
		}
	}

	public void CheckReportErrors(DatabaseVariables dbPromptVar) {
		if (txtRunYear.getText().isEmpty() || txtRunYear.getText().length() != 4) {
			JOptionPane.showMessageDialog(null, "Four Digit Run Year Must Be Entered");
			dbPromptVar.setGoodToGo(false);
		}

		if (txtDeTotal.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Enter the Total Delaware value");
			dbPromptVar.setGoodToGo(false);
		}
		if (txtPaTotal.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Enter the Total Pennsylvania value");
			dbPromptVar.setGoodToGo(false);
		}
		if (txtWvTotal.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Enter the Total West Virginia value");
			dbPromptVar.setGoodToGo(false);
		}
	}
}
