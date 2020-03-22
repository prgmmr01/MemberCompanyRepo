package com.MbrCmpProject.plj;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

public class MbrCmpPercentageInsert extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFormattedTextField[] prmYear = new JFormattedTextField[3];
	private JFormattedTextField[] prmAmt = new JFormattedTextField[3];
	private JFormattedTextField[] prmPct; // = new JFormattedTextField[3];
	int yVal;
	boolean good;

	PercentType[] percentType = new PercentType[3];

	ResultSet rstSet = null;
	PreparedStatement preStmt = null;
	Connection connection = null;

	NumberFormat percentFormat;
	NumberFormat amountFormat;
	MaskFormatter YYYY;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MbrCmpPercentageInsert frame = new MbrCmpPercentageInsert();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MbrCmpPercentageInsert() {
	}

	/**
	 * Create the frame.
	 */
	public MbrCmpPercentageInsert(DatabaseVariables perInsert) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 714, 470);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		amountFormat = NumberFormat.getCurrencyInstance();
		amountFormat.setMinimumFractionDigits(2);
		amountFormat.setGroupingUsed(false);
		percentFormat = NumberFormat.getPercentInstance();
		percentFormat.setMinimumFractionDigits(3);
		percentFormat.setGroupingUsed(false);
		try {
			YYYY = new MaskFormatter("####");
		} catch (ParseException p) {
			JOptionPane.showMessageDialog(null, "Formatting: " + p);
		}

		JLabel lblInsurancePlacementFacility = new JLabel("Insurance Placement Facility of Pennsylvania");
		lblInsurancePlacementFacility.setFont(new Font("Times New Roman", Font.BOLD, 24));
		lblInsurancePlacementFacility.setBounds(105, 16, 475, 25);
		getContentPane().add(lblInsurancePlacementFacility);

		JLabel lblDataFileMaintenance = new JLabel("Data File Entry");
		lblDataFileMaintenance.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblDataFileMaintenance.setBounds(287, 44, 135, 25);
		getContentPane().add(lblDataFileMaintenance);

		JLabel lblDELine = new JLabel("Delaware Premium:");
		lblDELine.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblDELine.setBounds(30, 151, 165, 25);
		contentPane.add(lblDELine);

		JLabel lblPALine = new JLabel("Pennsylvania Premium:");
		lblPALine.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblPALine.setBounds(30, 211, 185, 25);
		contentPane.add(lblPALine);

		JLabel lblNewLabel = new JLabel("West Virginai Premium:");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel.setBounds(30, 271, 195, 25);
		contentPane.add(lblNewLabel);

		JLabel lblGrpTitle = new JLabel("********************** Premium **********************");
		lblGrpTitle.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblGrpTitle.setBounds(114, 74, 510, 25);
		contentPane.add(lblGrpTitle);

		JLabel lblYear = new JLabel("Year (YYYY)");
		lblYear.setBounds(225, 115, 100, 20);
		contentPane.add(lblYear);

		JLabel lblAmount = new JLabel("Amount");
		lblAmount.setBounds(375, 115, 69, 20);
		contentPane.add(lblAmount);

		JLabel lblFactor = new JLabel("Factor");
		lblFactor.setBounds(525, 115, 55, 20);
		contentPane.add(lblFactor);

		yVal = 150;

		for (int i = 0; i < 3; i++) {
			prmYear[i] = new JFormattedTextField(YYYY);
			// **** Numeric Values Only
			// *******************************************************************************
			prmYear[i].addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					char c = e.getKeyChar();
					if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
						e.consume();
					}
				}
			});
			// **********************************************************************************************************
			prmYear[i].setHorizontalAlignment(SwingConstants.RIGHT);
			prmYear[i].setFont(new Font("Times New Roman", Font.PLAIN, 20));
			prmYear[i].setBounds(240, yVal, 75, 25);
			prmYear[i].setColumns(10);
			contentPane.add(prmYear[i]);
			yVal += 60;
		}
		yVal = 150;
		prmPct = new JFormattedTextField[3];
		for (int i = 0; i < 3; i++) {
			prmAmt[i] = new JFormattedTextField(amountFormat);
			// **** Numeric Values Only
			// *******************************************************************************
			prmAmt[i].addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					char c = e.getKeyChar();
					if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
						e.consume();
					}
				}
			});
			// **********************************************************************************************************
			prmAmt[i].setHorizontalAlignment(SwingConstants.RIGHT);
			prmAmt[i].setFont(new Font("Times New Roman", Font.PLAIN, 20));
			prmAmt[i].setBounds(350, yVal, 100, 25);
			prmAmt[i].setColumns(10);
			contentPane.add(prmAmt[i]);
			yVal += 60;
		}

		yVal = 150;

		for (int i = 0; i < 3; i++) {
			prmPct[i] = new JFormattedTextField(percentFormat);
			// **** Numeric Values Only
			// *******************************************************************************
			prmPct[i].addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					char c = e.getKeyChar();
					if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
						e.consume();
					}
				}
			});
			// **********************************************************************************************************
			prmPct[i].setHorizontalAlignment(SwingConstants.RIGHT);
			prmPct[i].setFont(new Font("Times New Roman", Font.PLAIN, 20));
			prmPct[i].setBounds(500, yVal, 100, 25);
			prmPct[i].setColumns(10);
			contentPane.add(prmPct[i]);
			yVal += 60;
		}

		// Start Enter Button...
		JRootPane rootPane = contentPane.getRootPane();
		JButton btnEnter = new JButton("Enter");
		btnEnter.setBounds(30, 350, 115, 29);
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < 3; i++) {
					switch (i) {
					case 0:
						perInsert.setDbStateCode("D");
						break;
					case 1:
						perInsert.setDbStateCode("P");
						break;
					case 2:
						perInsert.setDbStateCode("W");
						break;
					}
					if (!prmYear[i].getText().isEmpty() && !prmAmt[i].getText().isEmpty()
							&& !prmPct[i].getText().isEmpty()) {
						InsertPercent(perInsert, i);
					}
					Window opnWdw[] = getWindows();
					for (int w = 1; w < opnWdw.length - 1; w++) {
						opnWdw[w].dispose();
						opnWdw[w].setVisible(false);
					}
				}
			}
		});
		contentPane.add(btnEnter);
		rootPane.setDefaultButton(btnEnter);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(540, 350, 115, 29);
		contentPane.add(btnCancel);

	}

	public void InsertPercent(DatabaseVariables perInsert, int j) {

		// Connect to database...
		connection = sqlConnection.dbConnector();

		String sqlQuery = "Insert Into MemberPremiums " + "(NAIC,PremiumSequence,TaxId,StateCode"
				+ ",PremiumYear,PremiumAmount,PremiumPercentage)" + " Values (?,?,?,?,?,?,?)";
		try {
			preStmt = connection.prepareStatement(sqlQuery);
			preStmt.setInt(1, perInsert.getDbNAIC()); // NAIC
			preStmt.setInt(2, 1); // StatusType
			preStmt.setInt(3, Integer.parseInt(perInsert.getDbTaxId())); // TaxId
			preStmt.setString(4, perInsert.getDbStateCode()); // CompanyName
			preStmt.setInt(5, Integer.parseInt(prmYear[j].getText())); // Recipient
			preStmt.setFloat(6, Float.parseFloat(prmAmt[j].getText())); // Recipient
			preStmt.setFloat(7, Float.parseFloat(prmPct[j].getText())); // Recipient
			preStmt.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			good = false;
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e);
					good = false;
				}
		}
	}
}
