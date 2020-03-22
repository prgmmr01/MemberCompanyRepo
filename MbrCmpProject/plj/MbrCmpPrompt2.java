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

public class MbrCmpPrompt2 extends JFrame {

	Connection connection = null;
	ResultSet rstSet = null;
	PreparedStatement call_stmt = null;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtRunYear, txtRunUpdate, txtDeChkNbr, txtPaChkNbr,
			txtWvChkNbr;
	private JFormattedTextField txtDeTotal, txtPaTotal, txtWvTotal;
	private String Report = null;

	DecimalFormat decimalFormat = new DecimalFormat("###,###,##0.00;-###,###,##0.00");
	DecimalFormatSymbols symbols = new DecimalFormatSymbols();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MbrCmpPrompt2 frame = new MbrCmpPrompt2();
					frame.setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MbrCmpPrompt2() {}

	/**
	 * Create the frame.
	 */
	public MbrCmpPrompt2(DatabaseVariables dbPromptVar) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 870, 710);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblInsurancePlacementFacility = new JLabel("Insurance Placement Facility of Pennsylvania");
		lblInsurancePlacementFacility.setFont(new Font("Times New Roman", Font.BOLD, 28));
		lblInsurancePlacementFacility.setBounds(142, 10, 562, 42);
		getContentPane().add(lblInsurancePlacementFacility);

		JLabel lblMemberCompanies = new JLabel("Member Company File Maintenance");
		lblMemberCompanies.setFont(new Font("Times New Roman", Font.BOLD, 24));
		lblMemberCompanies.setBounds(230, 60, 385, 30);
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
							e1.printStackTrace();
						}
						dbPromptVar.setPromptRunUpdate(txtRunUpdate.getText());
						Prompt4Parameters(dbPromptVar);
					}
				} else if ((dbPromptVar.getRptNumber() == 7) || (dbPromptVar.getRptNumber() == 8)) {
					CheckCheckErrors(dbPromptVar);
					if (dbPromptVar.getGoodToGo()) {
						dbPromptVar.setPromptDeChkNbr(Integer.parseInt(txtDeChkNbr.getText()));
						dbPromptVar.setPromptPaChkNbr(Integer.parseInt(txtPaChkNbr.getText()));
						dbPromptVar.setPromptWvChkNbr(Integer.parseInt(txtWvChkNbr.getText()));
					}
				}

			}
		});
		btnEnter.setFont(new Font("Times New Roman", Font.BOLD, 16));
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
		btnExit.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnExit.setBounds(685, 580, 115, 30);
		getContentPane().add(btnExit);

		JLabel lblEnterTheRun = new JLabel("Enter the Run Year");
		lblEnterTheRun.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblEnterTheRun.setBounds(325, 125, 185, 30);
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
		txtRunYear.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		txtRunYear.setBounds(375, 165, 75, 25);
		contentPane.add(txtRunYear);
		txtRunYear.setColumns(10);

		JSeparator sep1 = new JSeparator();
		sep1.setForeground(Color.BLACK);
		sep1.setBackground(Color.BLACK);
		sep1.setBounds(50, 255, 760, 2);
		contentPane.add(sep1);

		JLabel lblTotalPremium = new JLabel("Enter the Total Premium for Each State Being Ran");
		lblTotalPremium.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalPremium.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblTotalPremium.setBounds(50, 225, 760, 25);
		contentPane.add(lblTotalPremium);

		JLabel lblDelaware = new JLabel("Delaware");
		lblDelaware.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblDelaware.setBounds(75, 265, 95, 25);
		contentPane.add(lblDelaware);

		JLabel lblPennsylvania = new JLabel("Pennsylvania");
		lblPennsylvania.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblPennsylvania.setBounds(365, 265, 125, 25);
		contentPane.add(lblPennsylvania);

		JLabel lblWestVirginia = new JLabel("West Virginia");
		lblWestVirginia.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblWestVirginia.setBounds(650, 265, 135, 25);
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
		txtDeTotal.setFont(new Font("Times New Roman", Font.PLAIN, 22));
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
		txtPaTotal.setFont(new Font("Times New Roman", Font.PLAIN, 22));
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
		txtWvTotal.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		txtWvTotal.setBounds(615, 290, 175, 25);
		txtWvTotal.setText(decimalFormat.format(dbPromptVar.getPromptWVTotals()));
		contentPane.add(txtWvTotal);
		txtWvTotal.setColumns(10);

		JSeparator sep2 = new JSeparator();
		sep2.setForeground(Color.BLACK);
		sep2.setBackground(Color.BLACK);
		sep2.setBounds(355, 375, 125, 2);
		contentPane.add(sep2);

		JLabel lblRunUpdate = new JLabel("Run Update");
		lblRunUpdate.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblRunUpdate.setBounds(365, 350, 115, 25);
		contentPane.add(lblRunUpdate);

		txtRunUpdate = new JTextField();
		txtRunUpdate.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		txtRunUpdate.setBounds(405, 385, 25, 25);
		// Default to Upper Case...
		txtRunUpdate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (Character.isLowerCase(keyChar)) {
					e.setKeyChar(Character.toUpperCase(keyChar));
				}
			}
		});
		txtRunUpdate.setText("N");
		contentPane.add(txtRunUpdate);
		txtRunUpdate.setColumns(1);

		JLabel lblYN = new JLabel("(Y/N)");
		lblYN.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		lblYN.setBounds(440, 385, 60, 25);
		contentPane.add(lblYN);

		JSeparator sep3 = new JSeparator();
		sep3.setForeground(Color.BLACK);
		sep3.setBackground(Color.BLACK);
		sep3.setBounds(50, 475, 760, 2);
		contentPane.add(sep3);

		JLabel lblChkNbr = new JLabel("Enter Starting Check Number");
		lblChkNbr.setHorizontalAlignment(SwingConstants.CENTER);
		lblChkNbr.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblChkNbr.setBounds(50, 445, 760, 25);
		contentPane.add(lblChkNbr);

		JLabel lblDeChkNbr = new JLabel("DE:");
		lblDeChkNbr.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblDeChkNbr.setBounds(50, 485, 45, 25);
		contentPane.add(lblDeChkNbr);

		JLabel lblPaChkNbr = new JLabel("PA:");
		lblPaChkNbr.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblPaChkNbr.setBounds(325, 485, 45, 25);
		contentPane.add(lblPaChkNbr);

		JLabel lblWvChkNbr = new JLabel("WV:");
		lblWvChkNbr.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblWvChkNbr.setBounds(590, 485, 45, 25);
		contentPane.add(lblWvChkNbr);

		txtDeChkNbr = new JTextField();
		txtDeChkNbr.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		});
		txtDeChkNbr.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		txtDeChkNbr.setColumns(10);
		txtDeChkNbr.setBounds(95, 485, 160, 25);
		contentPane.add(txtDeChkNbr);

		txtPaChkNbr = new JTextField();
		txtPaChkNbr.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		});
		txtPaChkNbr.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		txtPaChkNbr.setColumns(10);
		txtPaChkNbr.setBounds(370, 485, 160, 25);
		contentPane.add(txtPaChkNbr);

		txtWvChkNbr = new JTextField();
		txtWvChkNbr.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		});
		txtWvChkNbr.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		txtWvChkNbr.setColumns(10);
		txtWvChkNbr.setBounds(640, 485, 160, 25);
		contentPane.add(txtWvChkNbr);

		JLabel lblChecksOnly = new JLabel("(*** Required for Checks Only ***)");
		lblChecksOnly.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		lblChecksOnly.setBounds(260, 525, 325, 25);
		contentPane.add(lblChecksOnly);

	}

	public void Prompt4Parameters(DatabaseVariables dbPromptVar) {
		if (dbPromptVar.getRptNumber() == 2) {
			// Call procedure to create SQL table...
			dbPromptVar.setProc("Exec dbo.Member_Company_Participation_Premium_Listing ?"); // Member_Company_Participation_Premium_Listing
			//Create tables...ParticiptionPremiumTotalDataDE,ParticiptionPremiumTotalDataPA,ParticiptionPremiumTotalDataWV
			GetData2(dbPromptVar.getProc(), dbPromptVar); 
			if (dbPromptVar.getGoodToGo()) { // Open Microsoft Word macro template...
				// Delaware...Pennsylvania...West Virginia...
				Report = "K:\\Member Company\\Member Company Excel Reports\\Member_Company_Participation_Premium_Listing.xlsm";
				//Report = "C:\\Jarvis\\Member Company Conversion\\Member Company Excel Reports\\Member_Company_Participation_Premium_Listing.xlsm";
				GetDocument(Report,dbPromptVar);
				if (dbPromptVar.getGoodToGo()) 
				{
					//Close frames until Main Window
					Window opnWdw[] = getWindows();
					for (int w=1;w<opnWdw.length-1;w++) {
						opnWdw[w].dispose();
						opnWdw[w].setVisible(false);
					}	
				}
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
		if (txtRunUpdate.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Run Update be must Y for Yes or N for No");
			dbPromptVar.setGoodToGo(false);
		}
	}

	public void CheckCheckErrors(DatabaseVariables dbPromptVar) {
		if (txtDeChkNbr.getText().isEmpty() && txtPaChkNbr.getText().isEmpty() && txtWvChkNbr.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "One or More Check Number Must Be Entered");
			dbPromptVar.setGoodToGo(false);
		}

	}
}
