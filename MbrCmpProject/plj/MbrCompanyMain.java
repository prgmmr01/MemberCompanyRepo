package com.MbrCmpProject.plj;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

public class MbrCompanyMain {

	ResultSet rstSet = null;
	PreparedStatement preStmt = null;
	Connection connection = null;

	private JFrame frame;
	private JTextField txtNAIC;
	private JTextField txtTaxId;
	private JTextField txtGroupCode;
	private JTextField txtCompanyName;
	private int qryNAIC, qryTaxId;
	private String qryGrpCode, qryCompanyName, sqlQuery, strNAIC;
	private int swtNumber, MbrValue;
	public DatabaseVariables mainDbVar = new DatabaseVariables();
	public DefaultTableModel dbModel;
	public MbrDataRetrieval getData = new MbrDataRetrieval();
	// public JTable mainTable;

	// mbrValue getter and setter...
	public int getMbrValue() {
		return MbrValue;
	}

	public void setMbrValue(int mbrValue) {
		MbrValue = mbrValue;
	}

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MbrCompanyMain window = new MbrCompanyMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MbrCompanyMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame("Insurance Placement Facility of PA");
		// frame.getContentPane().setFont(new Font("Tahoma", Font.BOLD, 36));
		frame.setBounds(100, 100, 900, 520);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblMemberCompanies = new JLabel("Member Company");
		lblMemberCompanies.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblMemberCompanies.setBounds(340, 22, 192, 30);
		frame.getContentPane().add(lblMemberCompanies);

		JLabel lblNAIC = new JLabel("Enter NAIC Number:");
		lblNAIC.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNAIC.setBounds(133, 91, 192, 25);
		frame.getContentPane().add(lblNAIC);

		JLabel lblTaxId = new JLabel("Enter Tax Id Number:");
		lblTaxId.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTaxId.setBounds(122, 161, 203, 25);
		frame.getContentPane().add(lblTaxId);

		JLabel lblGroupCode = new JLabel("Search by Group Code:");
		lblGroupCode.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblGroupCode.setBounds(122, 231, 208, 25);
		frame.getContentPane().add(lblGroupCode);

		JLabel lblCompanyName = new JLabel("Search by Company Name:");
		lblCompanyName.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblCompanyName.setBounds(81, 296, 249, 25);
		frame.getContentPane().add(lblCompanyName);

		JLabel lblor1 = new JLabel("-or-");
		lblor1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblor1.setBounds(225, 126, 40, 25);
		frame.getContentPane().add(lblor1);

		JLabel lblor3 = new JLabel("-or-");
		lblor3.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblor3.setBounds(225, 196, 40, 25);
		frame.getContentPane().add(lblor3);

		JLabel lblor4 = new JLabel("-or-");
		lblor4.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblor4.setBounds(225, 261, 40, 25);
		frame.getContentPane().add(lblor4);

		txtNAIC = new JTextField();
		txtNAIC.setFont(new Font("Tahoma", Font.BOLD, 18));
		// code: change JTextField to accept only numeric values...
		txtNAIC.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		});
		txtNAIC.setBounds(335, 89, 75, 25);
		frame.getContentPane().add(txtNAIC);
		txtNAIC.setColumns(10);

		txtTaxId = new JTextField();
		txtTaxId.setFont(new Font("Tahoma", Font.BOLD, 18));
		// code: change JTextField to accept only numeric values...
		txtTaxId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		});
		txtTaxId.setBounds(340, 161, 125, 25);
		frame.getContentPane().add(txtTaxId);
		txtTaxId.setColumns(10);

		txtGroupCode = new JTextField();
		txtGroupCode.setFont(new Font("Tahoma", Font.BOLD, 18));
		txtGroupCode.setBounds(340, 231, 125, 25);
		frame.getContentPane().add(txtGroupCode);
		txtGroupCode.setColumns(10);

		txtCompanyName = new JTextField();
		txtCompanyName.setFont(new Font("Tahoma", Font.BOLD, 18));
		txtCompanyName.setBounds(340, 296, 390, 25);
		frame.getContentPane().add(txtCompanyName);
		txtCompanyName.setColumns(10);

		// Start Enter Button...
		JRootPane rootPane = frame.getRootPane();
		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() { // Open Enter Key Listener...
			// Process Main Menu Selection...
			public void actionPerformed(ActionEvent arg0) {
				ProcessSelection();
				txtNAIC.setText("");
				txtTaxId.setText("");
				txtGroupCode.setText("");
				txtCompanyName.setText("");
			}
		}); // Close Enter Key Listener...
		btnEnter.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnEnter.setBounds(50, 375, 115, 30);
		frame.getContentPane().add(btnEnter);
		rootPane.setDefaultButton(btnEnter);
		// End Enter Button...

		// create an instance of the report selection...
		JButton btnRunReports = new JButton("Run Reports");
		btnRunReports.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MbrCmpReports callReportProc = new MbrCmpReports();
				callReportProc.dispose();
				callReportProc.setVisible(true);
			}
		});
		btnRunReports.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnRunReports.setBounds(272, 375, 120, 30);
		frame.getContentPane().add(btnRunReports);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnExit.setBounds(704, 375, 115, 30);
		frame.getContentPane().add(btnExit);

		JSeparator separator1 = new JSeparator();
		separator1.setBounds(10, 350, 864, 2);
		frame.getContentPane().add(separator1);

		JButton btnMngGrp = new JButton("Manage Group");
		btnMngGrp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				swtNumber = 0;
				if (txtGroupCode.getText().isEmpty()) {
					int cnfValue = JOptionPane.showConfirmDialog(null,
							"Group Code Must Be Enter to Manage Group \r\n or Click Ok to add a new Group",
							"Group Management", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
					if (cnfValue == JOptionPane.OK_OPTION) {
						mainDbVar.equals(null);
						MbrCmpGroupChg groupChg = new MbrCmpGroupChg(mainDbVar);
						groupChg.setVisible(true);
					}
				} else {
					swtNumber = 3;
					sqlQuery = "Select * From MemberCompanyGroups " + "Where GroupCode like ?";
					ProcessQueries();
					MbrCmpGroupSelection GrppTable = new MbrCmpGroupSelection(dbModel);
					GrppTable.setVisible(true);
				}
				txtNAIC.setText("");
				txtTaxId.setText("");
				txtGroupCode.setText("");
				txtCompanyName.setText("");
			}
		});
		btnMngGrp.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnMngGrp.setBounds(480, 375, 125, 30);
		frame.getContentPane().add(btnMngGrp);
		frame.getContentPane().add(btnMngGrp);

		JButton btnUploadNAIC = new JButton("NAIC Upload");
		btnUploadNAIC.setForeground(Color.RED);
		btnUploadNAIC.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnUploadNAIC.setBounds(382, 445, 100, 25);
		btnUploadNAIC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent fKey) {
					try {
						ExcelRetrieverClass grabExcel = new ExcelRetrieverClass();
						grabExcel.dispose();
						grabExcel.setVisible(true);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e);
					}
			}
		});
		frame.getContentPane().add(btnUploadNAIC);

	}

	// Process Selection...
	public void ProcessSelection() {
		if (!txtNAIC.getText().isEmpty()) {
			// Setup Query string for NAIC search...
			qryNAIC = Integer.parseInt(txtNAIC.getText());
			swtNumber = 1;
			sqlQuery = "Select * From MemberCompanyContacts c " + "Left Join MemberCompanyGroups g "
					+ "On c.NAICGroup = g.NAICGroup " + "Left Join MemberCompanyContactExt e " + "On c.NAIC = e.NAIC "
					+ "Where c.NAIC=?";
			getData.dataRetrieval(swtNumber, qryTaxId, qryNAIC, sqlQuery, mainDbVar);
			if (mainDbVar.getGoodToGo()) {
				MbrCompanyDisplay CmpDemo = new MbrCompanyDisplay(mainDbVar);
				CmpDemo.dispose();
				CmpDemo.setVisible(true);
			} else {
				txtNAIC.setText("");
				txtTaxId.setText("");
				txtGroupCode.setText("");
				txtCompanyName.setText("");
			}
		} else if (!txtTaxId.getText().isEmpty()) {
			// Setup Query string for NAIC search...
			qryTaxId = Integer.parseInt(txtTaxId.getText());
			swtNumber = 2;
			sqlQuery = "Select * From MemberCompanyContacts c " + "Left Join MemberCompanyGroups g "
					+ "On c.NAICGroup = g.NAICGroup " + "Left Join MemberCompanyContactExt e " + "On c.NAIC = e.NAIC "
					+ "Where c.TaxId=?";
			getData.dataRetrieval(swtNumber, qryTaxId, qryNAIC, sqlQuery, mainDbVar);
			if (mainDbVar.getGoodToGo()) {
				MbrCompanyDisplay CmpDemo = new MbrCompanyDisplay(mainDbVar);
				CmpDemo.dispose();
				CmpDemo.setVisible(true);
			} else {
				txtNAIC.setText("");
				txtTaxId.setText("");
				txtGroupCode.setText("");
				txtCompanyName.setText("");
			}
		} else if (!txtGroupCode.getText().isEmpty()) {
			// Setup Query string for NAIC search...
			swtNumber = 3;
			sqlQuery = "Select * From MemberCompanyContacts c " + "Left Outer Join MemberCompanyGroups g "
					+ "On c.NAICGroup = g.NAICGroup " + "Left Join MemberCompanyContactExt e " + "On c.NAIC = e.NAIC "
					+ "Where c.GroupCode like ?";
			ProcessQueries();
			// frame.dispose();
			MbrCompanySelection CmpTable = new MbrCompanySelection(dbModel);
			CmpTable.setVisible(true);
		} else if (!txtCompanyName.getText().isEmpty()) {
			// Setup Query string for NAIC search...
			swtNumber = 4;
			sqlQuery = "Select * From MemberCompanyContacts c " + "Left Outer Join MemberCompanyGroups g "
					+ "On c.NAICGroup = g.NAICGroup " + "Left Join MemberCompanyContactExt e " + "On c.NAIC = e.NAIC "
					+ "Where c.CompanyName like ?";
			ProcessQueries();
			// frame.dispose();
			MbrCompanySelection CmpTable = new MbrCompanySelection(dbModel);
			CmpTable.setVisible(true);
		}
	}

	// Process Queries...
	public void ProcessQueries() {
		dbModel = new DefaultTableModel();
		dbModel.setRowCount(0);
		// Connect to database...
		connection = sqlConnection.dbConnector();
		// Query based on options select from MbrCompanyMain menu...
		switch (swtNumber) {
		case 3:
			try {
				qryGrpCode = txtGroupCode.getText();
				preStmt = connection.prepareStatement(sqlQuery);
				preStmt.setString(1, "%" + qryGrpCode.trim() + "%");
				rstSet = preStmt.executeQuery();
				ResultSetMetaData rstMeta = rstSet.getMetaData();
				int columnCount = rstMeta.getColumnCount();
				// retrieve column names...
				String[] cols = new String[columnCount];
				for (int i = 0; i < columnCount; i++) {
					cols[i] = rstMeta.getColumnName(i + 1);
					dbModel.addColumn(cols[i]);
				}
				// read result set...
				Object row[] = new Object[columnCount];
				while (rstSet.next()) {
					for (int i = 0; i < columnCount; i++) {
						row[i] = rstSet.getString(i + 1);
					}
					dbModel.addRow(row);
				}
				break; // 3rd break
			} catch (Exception expAdd) {
				JOptionPane.showMessageDialog(null, expAdd);
				// Final steps to close out queries...
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
		case 4:
			try {
				qryCompanyName = txtCompanyName.getText();
				preStmt = connection.prepareStatement(sqlQuery);
				preStmt.setString(1, "%" + qryCompanyName + "%");
				rstSet = preStmt.executeQuery();
				ResultSetMetaData rstMeta = rstSet.getMetaData();
				int columnCount = rstMeta.getColumnCount();
				// retrieve column names...
				String[] cols = new String[columnCount];
				for (int i = 0; i < columnCount; i++) {
					cols[i] = rstMeta.getColumnName(i + 1);
					dbModel.addColumn(cols[i]);
				}
				// read result set...
				Object row[] = new Object[columnCount];
				while (rstSet.next()) {
					for (int i = 0; i < columnCount; i++) {
						row[i] = rstSet.getString(i + 1);
					}
					dbModel.addRow(row);
				}
				break; // 4th break
			} catch (Exception expAdd) {
				JOptionPane.showMessageDialog(null, expAdd);
				// Final steps to close out queries...
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

	public static String NewExcelFileLocation() {
		// setup open dialog to assign the directory location to save the Excel file...
		File f = null;
		String excelPathRtn = null;
		JFileChooser NewExcelFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		NewExcelFileChooser.setDialogTitle("Upload NAIC Excel File ");
		NewExcelFileChooser.setAcceptAllFileFilterUsed(false);
		// NewExcelFileChooser.setCurrentDirectory(new File("K:\\Accounting Dept\\Member
		// Company"));
		NewExcelFileChooser.setCurrentDirectory(new File("C:\\Users\\pjarvis\\Desktop"));
		int returnValue = NewExcelFileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			f = NewExcelFileChooser.getSelectedFile();
			excelPathRtn = f.getAbsolutePath();
		}
		return excelPathRtn;
	}

	/*
	 * /* Final steps to close out Queries...
	 */
	public void setMbrValue(Object selValue) {
		// Setup query for NAIC search...
		strNAIC = selValue.toString();
		qryNAIC = Integer.parseInt(strNAIC);
		swtNumber = 1;
		sqlQuery = "Select * From MemberCompanyContacts c " + "Left Join MemberCompanyGroups g "
				+ "On c.NAICGroup = g.NAICGroup " + "Left Join MemberCompanyContactExt e " + "On c.NAIC = e.NAIC "
				+ "Where c.NAIC=?";
		getData.dataRetrieval(swtNumber, qryTaxId, qryNAIC, sqlQuery, mainDbVar);
		frame.dispose();
		MbrCompanyDisplay CmpDemo = new MbrCompanyDisplay(mainDbVar);
		CmpDemo.dispose();
		CmpDemo.setVisible(true);
	}
}
