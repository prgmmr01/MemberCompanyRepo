package com.MbrCmpProject.plj;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import com.microsoft.sqlserver.jdbc.SQLServerPreparedStatement;

public class MbrCmpLetterDates2 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFormattedTextField txtFinancialDate;
	private JFormattedTextField txtPymtRcvDate;
	private JFormattedTextField txtAssessDate;
	MaskFormatter maskFormatDate;
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
	ResultSet rstSet = null;
	PreparedStatement preStmt = null;
	Connection connection = null;
	Date SqlFinancialDate, SqlPaymentDate, SqlAssessDate,lookSee;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MbrCmpLetterDates2 frame = new MbrCmpLetterDates2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MbrCmpLetterDates2() {}

	public MbrCmpLetterDates2(DatabaseVariables dbVar, EquityAssetType[] aryEquityAssetType) {
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 625, 535);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblInsurancePlacementFacility = new JLabel("Insurance Placement Facility of Pennsylvania");
		lblInsurancePlacementFacility.setFont(new Font("Times New Roman", Font.BOLD, 28));
		lblInsurancePlacementFacility.setBounds(25, 10, 560, 40);
		contentPane.add(lblInsurancePlacementFacility);

		JLabel lblMemberCompanies = new JLabel("Member Company File Maintenance");
		lblMemberCompanies.setFont(new Font("Times New Roman", Font.BOLD, 24));
		lblMemberCompanies.setBounds(125, 50, 385, 30);
		contentPane.add(lblMemberCompanies);

		JLabel lblFinancialdate = new JLabel("Enter the Financial Statement Date");
		lblFinancialdate.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblFinancialdate.setBounds(152, 182, 275, 20);
		contentPane.add(lblFinancialdate);

		JLabel lblPymtrcvdate = new JLabel("Enter the Payment Received by Date");
		lblPymtrcvdate.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblPymtrcvdate.setBounds(152, 252, 290, 20);
		contentPane.add(lblPymtrcvdate);

		JLabel lblAssessdate = new JLabel("Enter Assessment Letter Date");
		lblAssessdate.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblAssessdate.setBounds(177, 327, 235, 20);
		contentPane.add(lblAssessdate);

		JLabel lblInfo = new JLabel("Dates must be entered in MM/DD/YYYY format");
		lblInfo.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		lblInfo.setBounds(106, 131, 385, 20);
		contentPane.add(lblInfo);

		try {
			maskFormatDate = new MaskFormatter("##/##/####");
			maskFormatDate.setAllowsInvalid(false);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		txtFinancialDate = new JFormattedTextField(maskFormatDate);
		txtFinancialDate.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		txtFinancialDate.addKeyListener(new KeyAdapter() {
			final static String badchars = "`~!@#$%^&*()-+_=\\|\"':;?/><, ";

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
				if (keyCode == KeyEvent.VK_ADD || keyCode == KeyEvent.VK_PLUS || keyCode == KeyEvent.VK_SUBTRACT
						|| keyCode == KeyEvent.VK_MINUS) {
					String str = txtFinancialDate.getText();
					pos = str.indexOf("+");
					if (pos > 1) {
						e.consume();
						txtFinancialDate.setText(str.substring(0, pos));
					}
				}
			}
		});
		txtFinancialDate.setBounds(217, 212, 125, 25);
		txtFinancialDate.setColumns(10);
		contentPane.add(txtFinancialDate);

		txtPymtRcvDate = new JFormattedTextField(maskFormatDate);
		txtPymtRcvDate.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		txtPymtRcvDate.addKeyListener(new KeyAdapter() {
			final static String badchars = "`~!@#$%^&*()-+_=\\|\"':;?/><, ";

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
				if (keyCode == KeyEvent.VK_ADD || keyCode == KeyEvent.VK_PLUS || keyCode == KeyEvent.VK_SUBTRACT
						|| keyCode == KeyEvent.VK_MINUS) {
					String str = txtPymtRcvDate.getText();
					pos = str.indexOf("+");
					if (pos > 1) {
						e.consume();
						txtPymtRcvDate.setText(str.substring(0, pos));
					}
				}
			}
		});
		txtPymtRcvDate.setColumns(10);
		txtPymtRcvDate.setBounds(217, 287, 125, 25);
		contentPane.add(txtPymtRcvDate);

		txtAssessDate = new JFormattedTextField(maskFormatDate);
		txtAssessDate.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		txtAssessDate.addKeyListener(new KeyAdapter() {
			final static String badchars = "`~!@#$%^&*()-+_=\\|\"':;?/><, ";

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
				if (keyCode == KeyEvent.VK_ADD || keyCode == KeyEvent.VK_PLUS || keyCode == KeyEvent.VK_SUBTRACT
						|| keyCode == KeyEvent.VK_MINUS) {
					String str = txtAssessDate.getText();
					pos = str.indexOf("+");
					if (pos > 1) {
						e.consume();
						txtAssessDate.setText(str.substring(0, pos));
					}
				}
			}
		});
		txtAssessDate.setColumns(10);
		txtAssessDate.setBounds(217, 362, 125, 25);
		contentPane.add(txtAssessDate);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			// Return to Main Menu Selection...
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnCancel.setBounds(450, 425, 115, 30);
		contentPane.add(btnCancel);

		// Perform action based on selected button...
		JRootPane rootPane = contentPane.getRootPane();
		JButton btnEnter1 = new JButton("Enter");
		btnEnter1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// capture dates...
				if ((txtFinancialDate.getText().isEmpty()) || (txtPymtRcvDate.getText().isEmpty())
						|| (txtAssessDate.getText().isEmpty())) {
					JOptionPane.showMessageDialog(null, "Enter all three dates in MM/DD/YYYY format");
				} else {
					dbVar.setFinancialDate(txtFinancialDate.getText());
					dbVar.setPymtRcvDate(txtPymtRcvDate.getText());
					dbVar.setAssessDate(txtAssessDate.getText());
					dbVar.setGoodToGo(true);
					LoadTable(dbVar, aryEquityAssetType);
					if (dbVar.getGoodToGo()) {
						try {
							Desktop.getDesktop().open(new File(
									"K:\\Member Company\\Member Company Excel Reports\\Member_Company_Assess_List.xlsm"));
							//"C:\\Jarvis\\Member Company Conversion\\Member Company Excel Reports\\Member_Company_Assess_List.xlsm"));
							//Close frames until Main Window
							Window opnWdw[] = getWindows();
							for (int w=1;w<opnWdw.length-1;w++) {
								opnWdw[w].dispose();
								opnWdw[w].setVisible(false);
							}
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, e1);
						}
					}
				}
			}
		});
		btnEnter1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnEnter1.setBounds(35, 425, 115, 30);
		contentPane.add(btnEnter1);
		rootPane.setDefaultButton(btnEnter1);

	}

	public void LoadTable(DatabaseVariables dbEqVar, EquityAssetType[] aryEquityAssetType) {
		dbEqVar.setGoodToGo(false);
		// convert string dates to date data type...
		try {
			SqlFinancialDate = simpleDateFormat.parse(dbEqVar.getFinancialDate());
			SqlPaymentDate = simpleDateFormat.parse(dbEqVar.getPymtRcvDate());
			SqlAssessDate = simpleDateFormat.parse(dbEqVar.getAssessDate());
			dbEqVar.setGoodToGo(true);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1);
		}
		// Define and populate SQL database table array in place of array for SQL...
		try {
			// Connect to database...
			connection = sqlConnection.dbConnector();

			// Getting Started with the SQL Server JDBC...
			SQLServerDataTable sqlTable = new SQLServerDataTable();
			// setup user-defined table column headers for user-defined table variable...
			//sqlTable.clear();
			sqlTable.addColumnMetadata("tblYear", java.sql.Types.INTEGER);
			sqlTable.addColumnMetadata("tblAmount", java.sql.Types.FLOAT);
			sqlTable.addColumnMetadata("tblClose", java.sql.Types.CHAR);
			// insert values into user-defined table...
			for (int i = 0; i < aryEquityAssetType.length; i++) {
				if (aryEquityAssetType[i].getAmount() != 0) {
					// loop through each row and add to user-defined table variable data...
					sqlTable.addRow(aryEquityAssetType[i].getYear(), aryEquityAssetType[i].getAmount(),
							aryEquityAssetType[i].getClose());
				}
			}

			SQLServerPreparedStatement preStmt = (SQLServerPreparedStatement) connection
					.prepareStatement("Exec Member_Company_Assess_Data ?,?,?,?,?");
			preStmt.setString(1, dbEqVar.getDbStateCode());
			preStmt.setString(2, dbEqVar.getFinancialDate());
			preStmt.setString(3, dbEqVar.getPymtRcvDate());
			preStmt.setString(4, dbEqVar.getAssessDate());
			preStmt.setStructured(5, "dbo.tblTypeEquity", sqlTable); //user-defined table
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
