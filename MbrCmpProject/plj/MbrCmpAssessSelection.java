package com.MbrCmpProject.plj;

/*
Author:		Peter Jarvis
Date:		06012018
Purpose:	retrieve Member Company individual record and load it into database 
			variables to be displayed in the MbrCompanyDisplay class... 
*/
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import com.microsoft.sqlserver.jdbc.SQLServerPreparedStatement;

public class MbrCmpAssessSelection extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNetPremium;
	private JTextField txtAorE, txtState;
	int yVal = 235, xVal = 35, counter=0;
	int rangeDate, maxRowCnt=0;
	char no = 'N';
	private int minAssess = Integer.MAX_VALUE;
	private int minClose = Integer.MAX_VALUE;

	JLabel[] lblEquityYear = new JLabel[10];
	JFormattedTextField[] txtEquityAmount = new JFormattedTextField[10];
	JTextField[] txtCloseout = new JTextField[10];
	String[] rangeYear = new String[10];
	int[] assessRngYear = new int[10];
	int[] closeRngYear = new int[10];

	ResultSet rstSet = null;
	PreparedStatement preStmt = null;
	Connection connection = null;

	DecimalFormat decimalFormat = new DecimalFormat("###,###,##0.00;-###,###,##0.00");
	DecimalFormatSymbols symbols = new DecimalFormatSymbols();
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
	EquityAssetType[] aryEquityAssetType = new EquityAssetType[10];

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MbrCmpAssessSelection frame = new MbrCmpAssessSelection(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MbrCmpAssessSelection(DatabaseVariables dbEqVar) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 785, 775);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblInsurancePlacementFacility = new JLabel("Insurance Placement Facility of Pennsylvania");
		lblInsurancePlacementFacility.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblInsurancePlacementFacility.setBounds(125, 10, 562, 42);
		getContentPane().add(lblInsurancePlacementFacility);

		JLabel lblMemberCompanies = new JLabel("Member Company File Maintenance");
		lblMemberCompanies.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblMemberCompanies.setBounds(200, 60, 385, 30);
		getContentPane().add(lblMemberCompanies);

		JSeparator sep1 = new JSeparator();
		sep1.setForeground(Color.BLACK);
		sep1.setBounds(20, 90, 710, 2);
		getContentPane().add(sep1);

		JLabel lblAorE = new JLabel("Enter (A) for Assessment or (E) for Equity:");
		lblAorE.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblAorE.setBounds(110, 110, 425, 25);
		getContentPane().add(lblAorE);

		JLabel lblState = new JLabel("State:");
		lblState.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblState.setBounds(110, 155, 65, 25);
		getContentPane().add(lblState);

		JLabel lblNetTotalPremium = new JLabel("Net Total Premium:");
		lblNetTotalPremium.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblNetTotalPremium.setBounds(365, 155, 200, 25);
		getContentPane().add(lblNetTotalPremium);

		JLabel lblEquityYearHdr = new JLabel("Equity Year");
		lblEquityYearHdr.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblEquityYearHdr.setBounds(135, 200, 115, 25);
		getContentPane().add(lblEquityYearHdr);

		JLabel lblEquityAmount = new JLabel("      Amount");
		lblEquityAmount.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblEquityAmount.setBounds(315, 200, 150, 25);
		getContentPane().add(lblEquityAmount);

		JLabel lblCloseout = new JLabel("Closeout");
		lblCloseout.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblCloseout.setBounds(515, 200, 90, 25);
		getContentPane().add(lblCloseout);

		JSeparator lblsep1 = new JSeparator();
		lblsep1.setForeground(Color.BLACK);
		lblsep1.setBounds(135, 225, 115, 2);
		getContentPane().add(lblsep1);

		JSeparator lblsep2 = new JSeparator();
		lblsep2.setForeground(Color.BLACK);
		lblsep2.setBounds(315, 225, 145, 2);
		getContentPane().add(lblsep2);

		JSeparator lblsep3 = new JSeparator();
		lblsep3.setBackground(Color.BLACK);
		lblsep3.setBounds(515, 225, 90, 2);
		getContentPane().add(lblsep3);

		LoadDates();

		for (int i = 0; i < 10; i++) {
			lblEquityYear[i] = new JLabel("Label" + (i + 1));
			lblEquityYear[i].setHorizontalAlignment(SwingConstants.RIGHT);
			lblEquityYear[i].setFont(new Font("Tahoma", Font.PLAIN, 22));
			lblEquityYear[i].setBounds(135, yVal, 85, 25);
			lblEquityYear[i].setText(rangeYear[i]);
			contentPane.add(lblEquityYear[i]);
			yVal += 35;
		}
		yVal = 235;
		symbols.setDecimalSeparator('.');
		decimalFormat.setNegativePrefix("-");
		decimalFormat.setNegativeSuffix("");
		for (int i = 0; i < 10; i++) {
			dbEqVar.setJ(i);
			txtEquityAmount[i] = new JFormattedTextField(decimalFormat);
			txtEquityAmount[i].setHorizontalAlignment(SwingConstants.RIGHT);
			txtEquityAmount[i].setFont(new Font("Tahoma", Font.PLAIN, 22));
			txtEquityAmount[i].setBounds(325, yVal, 125, 25);
			txtEquityAmount[i].setColumns(10);
			txtEquityAmount[i].setHorizontalAlignment(JFormattedTextField.RIGHT);
			txtEquityAmount[i].addKeyListener(new KeyAdapter() {
				final static String badchars = "`~!@#$%^&*()_=\\|\"':;?/><, ";

				@Override
				public void keyTyped(KeyEvent e) {
					char c = e.getKeyChar();
					// check for letters and bad characters...
					if ((Character.isLetter(c) || (c == KeyEvent.VK_BACK_SPACE)) || (badchars.indexOf(c) > -1)) {
						e.consume();
					}
				}

				@Override
				public void keyReleased(KeyEvent e) {
					int keyCode = e.getKeyCode();
					int pos;
					JFormattedTextField src = (JFormattedTextField) e.getSource();
					dbEqVar.setJ(src.getY());
					GetRow(dbEqVar);
					if (keyCode == KeyEvent.VK_ADD || keyCode == KeyEvent.VK_PLUS) {
						String str = txtEquityAmount[dbEqVar.getJ()].getText();
						pos = str.indexOf("+");
						if (pos > 1) {
							e.consume();
							txtEquityAmount[dbEqVar.getJ()].setText(str.substring(0, pos));
							txtCloseout[dbEqVar.getJ()].requestFocus();
						}
					} else if (keyCode == KeyEvent.VK_SUBTRACT || keyCode == KeyEvent.VK_MINUS) {
						String str = txtEquityAmount[dbEqVar.getJ()].getText();
						pos = str.indexOf("-");
						if (pos > 1) {
							e.consume();
							txtEquityAmount[dbEqVar.getJ()].setText("-" + str.substring(0, pos));
							txtCloseout[dbEqVar.getJ()].requestFocus();
						}
					}
				}
			});
			txtEquityAmount[i].setFocusLostBehavior(JFormattedTextField.PERSIST);
			txtEquityAmount[i].addFocusListener(new FocusAdapter() {
				public void focusGained(FocusEvent e) {
					txtEquityAmount[dbEqVar.getJ()].setText(txtEquityAmount[dbEqVar.getJ()].getText().trim());
				}
			});
			contentPane.add(txtEquityAmount[i]);
			yVal += 35;
		}
		yVal = 235;
		for (int i = 0; i < 10; i++) {
			txtCloseout[i] = new JTextField();
			txtCloseout[i].setHorizontalAlignment(SwingConstants.CENTER);
			txtCloseout[i].setFont(new Font("Tahoma", Font.PLAIN, 22));
			txtCloseout[i].setBounds(550, yVal, 25, 25);
			txtCloseout[i].setColumns(10);
			txtCloseout[i].setColumns(5);
			// Default to Upper Case...
			txtCloseout[i].addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					char keyChar = e.getKeyChar();
					if (Character.isLowerCase(keyChar)) {
						e.setKeyChar(Character.toUpperCase(keyChar));
					}
				}
			});
			contentPane.add(txtCloseout[i]);
			yVal += 35;
		}

		txtAorE = new JTextField("A");
		txtAorE.setEditable(false);
		txtAorE.setHorizontalAlignment(SwingConstants.CENTER);
		txtAorE.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (Character.isLowerCase(keyChar)) {
					e.setKeyChar(Character.toUpperCase(keyChar));
				}
			}
		});
		txtAorE.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtAorE.setBounds(550, 110, 25, 25);
		contentPane.add(txtAorE);
		txtAorE.setColumns(10);

		txtState = new JTextField();
		// Default to Upper Case...
		txtState.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (Character.isLowerCase(keyChar)) {
					e.setKeyChar(Character.toUpperCase(keyChar));
				}
			}
		});
		txtState.setHorizontalAlignment(SwingConstants.CENTER);
		txtState.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (!txtState.getText().equals("D") && !txtState.getText().equals("P")
						&& !txtState.getText().equals("W")) {
					JOptionPane.showMessageDialog(null,
							"Enter a Valid State: " + "D=Delaware " + "P=Pennsylvania " + "W=West Virginia");
					dbEqVar.setGoodToGo(false);
				} else {
					dbEqVar.setDbStateCode(txtState.getText());
					GetAmountTotals(dbEqVar);
				}
			}
		});
		txtState.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtState.setBounds(175, 155, 25, 25);
		contentPane.add(txtState);
		txtState.setColumns(10);

		lblNetPremium = new JLabel();
		lblNetPremium.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblNetPremium.setBounds(580, 155, 175, 25);
		contentPane.add(lblNetPremium);

		JRootPane rootPane = contentPane.getRootPane();
		// Start Enter Button...
		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbEqVar.setGoodToGo(true);
				dbEqVar.setAorE(txtAorE.getText());
				VerifyEquityAsset(dbEqVar);
				// Retrieve Equity/Assess Letter Dates...
				if (dbEqVar.getGoodToGo()) {
					// Get date information...
					StoreDateData(dbEqVar);
				}
				if (dbEqVar.getGoodToGo()) {
					String sqlQuery = null;
					String sqlQuery2 = null;
					String title = null;
					switch (txtState.getText()) {
					case "D":
						sqlQuery = "Select * From dbo.AssessDataDE";
						sqlQuery2 = "Select * From dbo.AssessTotalDE";
						title = "DE_Assessment";
						break;
					case "P":
						sqlQuery = "Select * From dbo.AssessDataPA";
						sqlQuery2 = "Select * From dbo.AssessTotalPA";
						title = "PA_Assessment";
						break;
					case "W":
						sqlQuery = "Select * From dbo.AssessDataWV";
						sqlQuery2 = "Select * From dbo.AssessTotalWV";
						title = "WV_Assessment";
						break;
					}
					// POI - Generate Excel Assess Spreadsheet...
					ExcelGeneratorClass excelGenerator = new ExcelGeneratorClass(sqlQuery, sqlQuery2, title);
					excelGenerator.dispose();
					sqlQuery = null;
					sqlQuery2 = null;
					title = null;
					switch (txtState.getText()) {
					case "D":
						sqlQuery = "Select * From dbo.AssessCheckDE";
						title = "DE_Assess_Check";
						break;
					case "P":
						sqlQuery = "Select * From dbo.AssessCheckPA";
						title = "PA_Assess_Check";
						break;
					case "W":
						sqlQuery = "Select * From dbo.AssessCheckWV";
						title = "WV_Assess_Check";
						break;
					}
					// POI - Generate Excel Assess Check Spreadsheet...
					ExcelGeneratorClass excelCheck = new ExcelGeneratorClass(sqlQuery, null, title);
					excelCheck.dispose();
				}
			}

		});
		btnEnter.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnEnter.setBounds(40, 650, 115, 30);
		contentPane.add(btnEnter);
		rootPane.setDefaultButton(btnEnter);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			// Return to Report Selection...
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnCancel.setBounds(600, 650, 115, 30);
		getContentPane().add(btnCancel);

	}

	public void LoadDates() {
		String strDate = new SimpleDateFormat("YYYY").format(Calendar.getInstance().getTime());
		rangeDate = Integer.parseInt(strDate);
		// initialize the array...
		for (int i = 0; i < 10; i++) {
			rangeYear[i] = String.valueOf(rangeDate);
			rangeDate -= 1;
		}
	}

	public void VerifyEquityAsset(DatabaseVariables dbEqVar) {
		if (!txtAorE.getText().equals("A")) {
			JOptionPane.showMessageDialog(null, "Enter A=Assessment");
			dbEqVar.setGoodToGo(false);
		}
		if (!txtState.getText().equals("D") && !txtState.getText().equals("P") && !txtState.getText().equals("W")) {
			JOptionPane.showMessageDialog(null,
					"Enter a Valid State: " + "D=Delaware " + "P=Pennsylvania " + "W=West Virginia");
			dbEqVar.setGoodToGo(false);
		}
	}

	// Retrieve database values...
	public void GetAmountTotals(DatabaseVariables dbEqVar) {
		dbEqVar.setPromptState(txtState.getText());
		dbEqVar.setPromptRunYear(Calendar.getInstance().get(Calendar.YEAR));
		GetNetTotals netTotals = new GetNetTotals(dbEqVar); // call net total class...
		netTotals.getClass();
		netTotals.dispose();
		switch (txtState.getText()) {
		case "D":
			lblNetPremium.setText(decimalFormat.format(dbEqVar.getPromptDETotals()));
			break;
		case "P":
			lblNetPremium.setText(decimalFormat.format(dbEqVar.getPromptPATotals()));
			break;
		case "W":
			lblNetPremium.setText(decimalFormat.format(dbEqVar.getPromptWVTotals()));
			break;
		}

	}

	// store sequence numbers
	public void GetRow(DatabaseVariables rowVar) {
		switch (rowVar.getJ()) {
		case 235:
			rowVar.setJ(0);
			break;
		case 270:
			rowVar.setJ(1);
			break;
		case 305:
			rowVar.setJ(2);
			break;
		case 340:
			rowVar.setJ(3);
			break;
		case 375:
			rowVar.setJ(4);
			break;
		case 410:
			rowVar.setJ(5);
			break;
		case 445:
			rowVar.setJ(6);
			break;
		case 480:
			rowVar.setJ(7);
			break;
		case 515:
			rowVar.setJ(8);
			break;
		case 550:
			rowVar.setJ(9);
			break;
		}
	}

	public void StoreDateData(DatabaseVariables dbEqVar) {
		
		/*for(JFormattedTextField el:txtEquityAmount) {
			if(!el.getText().equals("")) {
				++counter;
			}
		}*/
		counter=0;
		
		// Define and populate SQL database table array in place of array for SQL...
		for (int i = 0; i < aryEquityAssetType.length; i++) {
			aryEquityAssetType[i] = new EquityAssetType();
			if (!txtEquityAmount[i].getText().isEmpty()) {
				try {
					aryEquityAssetType[i].setYear(Integer.parseInt(lblEquityYear[i].getText()));
					aryEquityAssetType[i].setAmount(decimalFormat.parse(txtEquityAmount[i].getText()).floatValue());
					aryEquityAssetType[i].setClose(txtCloseout[i].getText());
					aryEquityAssetType[i].setSlot(++counter);
					dbEqVar.setGoodToGo(true);
					if (txtCloseout[i].getText().contains("Y")) {
						closeRngYear[i] = Integer.parseInt(lblEquityYear[i].getText());
					} else {
						assessRngYear[i] = Integer.parseInt(lblEquityYear[i].getText());
					}
				} catch (ParseException e) {
					JOptionPane.showMessageDialog(null, e);
				}
				// get maximum assessment table rows
				if (maxRowCnt < i) {
					maxRowCnt = i;
				}
			}
		}

		for (int i = 0; i < closeRngYear.length; i++) {
			if (closeRngYear[i] != 0 && closeRngYear[i] < minClose)
				minClose = closeRngYear[i];
		}
		for (int i = 0; i < assessRngYear.length; i++) {
			if (assessRngYear[i] != 0 && assessRngYear[i] < minAssess)
				minAssess = assessRngYear[i];
		}
		if (dbEqVar.getGoodToGo()) {
			// Define and populate SQL database table array in place of array for SQL...
			try {
				// Connect to database...
				connection = sqlConnection.dbConnector();

				// Getting Started with the SQL Server JDBC...
				SQLServerDataTable sqlTable = new SQLServerDataTable();
				// setup user-defined table column headers for user-defined table variable...
				// sqlTable.clear();
				sqlTable.addColumnMetadata("tblYear", java.sql.Types.INTEGER);
				sqlTable.addColumnMetadata("tblAmount", java.sql.Types.FLOAT);
				sqlTable.addColumnMetadata("tblClose", java.sql.Types.CHAR);
				sqlTable.addColumnMetadata("tblSlot", java.sql.Types.INTEGER);
				// insert values into user-defined table...
				for (int i = 0; i < aryEquityAssetType.length; i++) {
					if (aryEquityAssetType[i].getAmount() != 0) {
						// loop through each row and add to user-defined table variable data...
						sqlTable.addRow(aryEquityAssetType[i].getYear(), aryEquityAssetType[i].getAmount(),
								aryEquityAssetType[i].getClose(), aryEquityAssetType[i].getSlot());
					}
				}

				SQLServerPreparedStatement preStmt = (SQLServerPreparedStatement) connection
						.prepareStatement("Exec Member_Company_Assess_Data ?,?");
				preStmt.setString(1, dbEqVar.getDbStateCode());
				preStmt.setStructured(2, "dbo.tblTypeEquity", sqlTable); // user-defined table
				preStmt.execute();
				dbEqVar.setGoodToGo(true);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e);
				dbEqVar.setGoodToGo(false);
				// Final steps to close out queries...
			} finally {
				// Close the Prepared Statement...
				if (preStmt != null) {
					try {
						preStmt.close();
					} catch (Exception expClose) {
						JOptionPane.showMessageDialog(null, expClose);
						dbEqVar.setGoodToGo(false);
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
			// Define and populate SQL database table array in place of array for SQL...
			try {
				// Connect to database...
				connection = sqlConnection.dbConnector();

				// Getting Started with the SQL Server JDBC...
				SQLServerDataTable sqlTable = new SQLServerDataTable();
				// setup user-defined table column headers for user-defined table variable...
				// sqlTable.clear();
				sqlTable.addColumnMetadata("tblYear", java.sql.Types.INTEGER);
				sqlTable.addColumnMetadata("tblAmount", java.sql.Types.FLOAT);
				sqlTable.addColumnMetadata("tblClose", java.sql.Types.CHAR);
				sqlTable.addColumnMetadata("tblSlot", java.sql.Types.INTEGER);
				// insert values into user-defined table...
				for (int i = 0; i < aryEquityAssetType.length; i++) {
					if (aryEquityAssetType[i].getAmount() != 0) {
						// loop through each row and add to user-defined table variable data...
						sqlTable.addRow(aryEquityAssetType[i].getYear(), aryEquityAssetType[i].getAmount(),
								aryEquityAssetType[i].getClose(), aryEquityAssetType[i].getSlot());
					}
				}

				SQLServerPreparedStatement preStmt = (SQLServerPreparedStatement) connection
						.prepareStatement("Exec Member_Company_Assess_Totals ?");
				preStmt.setString(1, dbEqVar.getDbStateCode());
				preStmt.execute();
				dbEqVar.setGoodToGo(true);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e);
				dbEqVar.setGoodToGo(false);
				// Final steps to close out queries...
			} finally {
				// Close the Prepared Statement...
				if (preStmt != null) {
					try {
						preStmt.close();
					} catch (Exception expClose) {
						JOptionPane.showMessageDialog(null, expClose);
						dbEqVar.setGoodToGo(false);
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
}
