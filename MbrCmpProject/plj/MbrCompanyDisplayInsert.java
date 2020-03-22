package com.MbrCmpProject.plj;

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
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DateFormatter;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

public class MbrCompanyDisplayInsert extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFormattedTextField cmpNAIC, cmpTaxId;
	private JTextField cmpDE, cmpPA, cmpWV;
	private JTextField cmpParticipation;
	private JTextField cmpCompanyName;
	private JTextField cmpAttention;
	private JTextField cmpAddress1;
	private JTextField cmpAddress2;
	private JTextField cmpCity;
	private JTextField cmpState;
	private JFormattedTextField cmpZipCode;
	private JTextField cmpGroupCode;
	private JTextField cmpWthStDE, cmpWthStPA, cmpWthStWV;
	private JFormattedTextField cmpRehabDate;
	private JFormattedTextField cmpMergeDate;
	private JFormattedTextField cmpLiquidDate;
	private JFormattedTextField cmpWthDate;
	private JTextField cmpLegend;
	private JFormattedTextField GroupNAIC;
	private JTextField cmpOnlineRpt;
	private JTextField cmpFinancials;
	private boolean good;

	private String cvtToString, stat;
	private MbrCmpComments comments;

	ResultSet rstSet = null;
	PreparedStatement preStmt = null;
	Connection connection = null;

	DatabaseVariables perInsert = new DatabaseVariables();
	DateFormat dateFmt = new SimpleDateFormat("MM/dd/yyyy");
	DateFormatter simpleDateFormat = new DateFormatter(dateFmt);
	int rehabDate, mergeDate, liquidDate, withDate;
	//DecimalFormat maskFormatAmount;
	//DecimalFormatSymbols symbols;
	MaskFormatter maskFormatDate;
	NumberFormatter maskFormatZip;
	NumberFormatter maskFormat6Digits;
	NumberFormatter maskFormatTax;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MbrCompanyDisplayInsert frame = new MbrCompanyDisplayInsert();
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
	// Main Constructor...
	public MbrCompanyDisplayInsert() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 940, 770);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setUpFormatting();
		JLabel lblInsurancePlacementFacility = new JLabel("Insurance Placement Facility of Pennsylvania");
		lblInsurancePlacementFacility.setFont(new Font("Times New Roman", Font.BOLD, 28));
		lblInsurancePlacementFacility.setBounds(175, 15, 555, 40);
		getContentPane().add(lblInsurancePlacementFacility);

		JLabel lblMemberCompany = new JLabel("Member Company");
		lblMemberCompany.setFont(new Font("Times New Roman", Font.BOLD, 26));
		lblMemberCompany.setBounds(360, 60, 215, 30);
		getContentPane().add(lblMemberCompany);

		JLabel lblDataFileMaintenance = new JLabel("Data File Entry");
		lblDataFileMaintenance.setFont(new Font("Times New Roman", Font.BOLD, 24));
		lblDataFileMaintenance.setBounds(375, 100, 165, 30);
		getContentPane().add(lblDataFileMaintenance);

		JLabel lblNaic_1 = new JLabel("NAIC#:");
		lblNaic_1.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		lblNaic_1.setBounds(33, 230, 80, 30);
		getContentPane().add(lblNaic_1);

		JLabel lblTaxId = new JLabel("Tax Id: ");
		lblTaxId.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		lblTaxId.setBounds(333, 230, 75, 30);
		getContentPane().add(lblTaxId);

		JLabel lblStateCodes = new JLabel("State Codes:");
		lblStateCodes.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		lblStateCodes.setBounds(588, 230, 120, 30);
		getContentPane().add(lblStateCodes);

		JLabel lblParticipation = new JLabel("Participation: ");
		lblParticipation.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		lblParticipation.setBounds(578, 190, 135, 30);
		getContentPane().add(lblParticipation);

		JLabel lblCompanyName = new JLabel("Company Name:");
		lblCompanyName.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		lblCompanyName.setBounds(33, 270, 170, 30);
		getContentPane().add(lblCompanyName);

		JLabel lblAttention = new JLabel("Attention:");
		lblAttention.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		lblAttention.setBounds(33, 310, 100, 30);
		getContentPane().add(lblAttention);

		JLabel lblAddress = new JLabel("Address 1:");
		lblAddress.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		lblAddress.setBounds(33, 350, 110, 30);
		getContentPane().add(lblAddress);

		JLabel lblAddress_1 = new JLabel("Address 2:");
		lblAddress_1.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		lblAddress_1.setBounds(33, 390, 110, 30);
		getContentPane().add(lblAddress_1);

		JLabel lblCity = new JLabel("City:");
		lblCity.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		lblCity.setBounds(33, 430, 50, 30);
		getContentPane().add(lblCity);

		JLabel lblState = new JLabel("State:");
		lblState.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		lblState.setBounds(558, 430, 60, 30);
		getContentPane().add(lblState);

		JLabel lblZipCode = new JLabel("Zip Code:");
		lblZipCode.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		lblZipCode.setBounds(688, 430, 100, 30);
		getContentPane().add(lblZipCode);

		JLabel lblGroupNaic = new JLabel("Group NAIC#:");
		lblGroupNaic.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		lblGroupNaic.setBounds(33, 590, 145, 30);
		getContentPane().add(lblGroupNaic);

		JLabel lblGroupCode = new JLabel("Group Code:");
		lblGroupCode.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		lblGroupCode.setBounds(363, 590, 125, 30);
		getContentPane().add(lblGroupCode);

		JLabel lblRehabDate = new JLabel("Rehab Date:");
		lblRehabDate.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		lblRehabDate.setBounds(33, 510, 130, 30);
		getContentPane().add(lblRehabDate);

		JLabel lblMergeDate = new JLabel("Merge Date:");
		lblMergeDate.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		lblMergeDate.setBounds(33, 550, 125, 30);
		getContentPane().add(lblMergeDate);

		JLabel lblLiquidDate = new JLabel("Liquid Date:");
		lblLiquidDate.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		lblLiquidDate.setBounds(363, 510, 125, 30);
		getContentPane().add(lblLiquidDate);

		JLabel lblWithdrewDate = new JLabel("Withdrew Date:");
		lblWithdrewDate.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		lblWithdrewDate.setBounds(363, 550, 155, 30);
		getContentPane().add(lblWithdrewDate);

		JLabel lblWithdrawState = new JLabel("Withdraw State");
		lblWithdrawState.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		lblWithdrawState.setBounds(713, 510, 155, 30);
		getContentPane().add(lblWithdrawState);

		JLabel cmpStatus = new JLabel("Active");
		cmpStatus.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		cmpStatus.setBounds(198, 190, 105, 30);
		contentPane.add(cmpStatus);
		stat = "A";

		JLabel lblLegend = new JLabel("Legend:");
		lblLegend.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		lblLegend.setBounds(328, 190, 80, 30);
		contentPane.add(lblLegend);

		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		lblStatus.setBounds(33, 190, 70, 30);
		contentPane.add(lblStatus);

		JLabel lblOnlineReports = new JLabel("Online Reports:");
		lblOnlineReports.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		lblOnlineReports.setBounds(514, 471, 155, 30);
		contentPane.add(lblOnlineReports);

		JLabel lblFinancials = new JLabel("Financials:");
		lblFinancials.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		lblFinancials.setBounds(736, 471, 105, 30);
		contentPane.add(lblFinancials);

		cmpNAIC = new JFormattedTextField(maskFormat6Digits);
		cmpNAIC.setHorizontalAlignment(SwingConstants.RIGHT);
		// **** Numeric Values Only  *******************************************************************************
		cmpNAIC.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		});
		// **********************************************************************************************************
		cmpNAIC.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		cmpNAIC.setBounds(198, 230, 75, 30);
		cmpNAIC.setText(cvtToString);
		contentPane.add(cmpNAIC);
		cmpNAIC.setColumns(10);
		cmpNAIC.setFocusLostBehavior(JFormattedTextField.PERSIST);

		cmpTaxId = new JFormattedTextField();
		cmpTaxId.setHorizontalAlignment(SwingConstants.RIGHT);
		// **** Numeric Values Only  *******************************************************************************
		cmpTaxId.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		});
		// **********************************************************************************************************
		cmpTaxId.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		cmpTaxId.setBounds(413, 230, 100, 30);
		cmpTaxId.setColumns(10);
		cmpTaxId.setFocusLostBehavior(JFormattedTextField.PERSIST);
		contentPane.add(cmpTaxId);

		cmpDE = new JTextField();
		cmpDE.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (!cmpDE.getText().equals("D") && !cmpDE.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Enter a Valid State  \r\n" + "D=Delaware \r\n" + " or leave blank");
				}
			}
		});
		cmpDE.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (Character.isLowerCase(keyChar)) {
					e.setKeyChar(Character.toUpperCase(keyChar));
				}
			}
		});
		cmpDE.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		cmpDE.setBounds(723, 230, 25, 30);
		cmpDE.setColumns(10);
		contentPane.add(cmpDE);

		cmpPA = new JTextField();
		cmpPA.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (!cmpPA.getText().equals("P") && !cmpPA.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Enter a Valid State  \r\n" + "P=Pennsylvania \r\n" + " or leave blank");
				}
			}
		});
		cmpPA.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (Character.isLowerCase(keyChar)) {
					e.setKeyChar(Character.toUpperCase(keyChar));
				}
			}
		});
		cmpPA.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		cmpPA.setColumns(10);
		cmpPA.setBounds(768, 230, 25, 30);
		contentPane.add(cmpPA);

		cmpWV = new JTextField();
		cmpWV.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (!cmpWV.getText().equals("W") && !cmpWV.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Enter a Valid State  \r\n" + "W=West Virginia \r\n" + " or leave blank");
				}
			}
		});
		cmpWV.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (Character.isLowerCase(keyChar)) {
					e.setKeyChar(Character.toUpperCase(keyChar));
				}
			}
		});
		cmpWV.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		cmpWV.setColumns(10);
		cmpWV.setBounds(813, 230, 25, 30);
		contentPane.add(cmpWV);

		cmpParticipation = new JTextField(1);
		cmpParticipation.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (Character.isLowerCase(keyChar)) {
					e.setKeyChar(Character.toUpperCase(keyChar));
				}
			}
		});
		cmpParticipation.addKeyListener(new KeyAdapter() {
			// enter numeric values only...
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				// check for letters and bad characters...
				if (!Character.isLetter(c)) {
					e.consume();
				} else {
					if ((c != 'Y') && (c != 'N')) {
						e.consume();
					}
				}
			}
		});
		cmpParticipation.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		cmpParticipation.setColumns(5);
		cmpParticipation.setBounds(723, 190, 20, 30);
		contentPane.add(cmpParticipation);

		cmpCompanyName = new JTextField();
		cmpCompanyName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		cmpCompanyName.setBounds(198, 270, 675, 30);
		contentPane.add(cmpCompanyName);
		cmpCompanyName.setColumns(10);

		cmpAttention = new JTextField();
		cmpAttention.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		cmpAttention.setColumns(10);
		cmpAttention.setBounds(198, 310, 675, 30);
		contentPane.add(cmpAttention);

		cmpAddress1 = new JTextField();
		cmpAddress1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		cmpAddress1.setColumns(10);
		cmpAddress1.setBounds(198, 350, 675, 30);
		contentPane.add(cmpAddress1);

		cmpAddress2 = new JTextField();
		cmpAddress2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		cmpAddress2.setColumns(10);
		cmpAddress2.setBounds(198, 390, 675, 30);
		contentPane.add(cmpAddress2);

		cmpCity = new JTextField();
		cmpCity.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (!cmpCity.getText().trim().matches("[a-z,A-Z]+")) {
					JOptionPane.showMessageDialog(null, "Enter a Valid City");
				}
			}
		});
		cmpCity.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		cmpCity.setBounds(198, 430, 345, 30);
		contentPane.add(cmpCity);
		cmpCity.setColumns(10);

		cmpState = new JTextField();
		cmpState.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (Character.isLowerCase(keyChar)) {
					e.setKeyChar(Character.toUpperCase(keyChar));
				}
			}
		});
		cmpState.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (cmpState.getText().length() != 2) {
					JOptionPane.showMessageDialog(null, "Enter State Abbreviation");
				}
			}
		});
		cmpState.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		cmpState.setBounds(618, 430, 50, 30);
		contentPane.add(cmpState);
		cmpState.setColumns(10);

		cmpZipCode = new JFormattedTextField(maskFormatZip);
		cmpZipCode.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		cmpZipCode.setBounds(793, 430, 80, 30);
		contentPane.add(cmpZipCode);
		cmpZipCode.setColumns(10);

		cmpWthStDE = new JTextField();
		cmpWthStDE.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (!cmpWthStDE.getText().equals("D") && !cmpWthStDE.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Enter a Valid State  \r\n" + "D=Delaware \r\n" + " or leave blank");
				}
			}
		});
		cmpWthStDE.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (Character.isLowerCase(keyChar)) {
					e.setKeyChar(Character.toUpperCase(keyChar));
				}
			}
		});
		cmpWthStDE.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		cmpWthStDE.setColumns(10);
		cmpWthStDE.setBounds(733, 550, 25, 30);
		contentPane.add(cmpWthStDE);

		cmpWthStPA = new JTextField();
		cmpWthStPA.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (!cmpWthStPA.getText().equals("P") && !cmpWthStPA.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Enter a Valid State  \r\n" + "P=Pennsylvania \r\n" + " or leave blank");
				}
			}
		});
		cmpWthStPA.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (Character.isLowerCase(keyChar)) {
					e.setKeyChar(Character.toUpperCase(keyChar));
				}
			}
		});
		cmpWthStPA.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		cmpWthStPA.setColumns(10);
		cmpWthStPA.setBounds(773, 550, 25, 30);
		contentPane.add(cmpWthStPA);

		cmpWthStWV = new JTextField();
		cmpWthStWV.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (!cmpWthStWV.getText().equals("W") && !cmpWthStWV.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Enter a Valid State  \r\n" + "W=West Virginia \r\n" + " or leave blank");
				}
			}
		});
		cmpWthStWV.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (Character.isLowerCase(keyChar)) {
					e.setKeyChar(Character.toUpperCase(keyChar));
				}
			}
		});
		cmpWthStWV.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		cmpWthStWV.setColumns(10);
		cmpWthStWV.setBounds(813, 550, 25, 30);
		contentPane.add(cmpWthStWV);

		GroupNAIC = new JFormattedTextField();
		GroupNAIC.setHorizontalAlignment(SwingConstants.RIGHT);
		// **** Numeric Values Only  *******************************************************************************
		GroupNAIC.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		});
		// **********************************************************************************************************
		GroupNAIC.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		GroupNAIC.setBounds(198, 590, 105, 30);
		contentPane.add(GroupNAIC);
		GroupNAIC.setColumns(10);
		GroupNAIC.setFocusLostBehavior(JFormattedTextField.PERSIST);
		GroupNAIC.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				GroupNAIC.setText(GroupNAIC.getText().trim());
			}

		});

		cmpRehabDate = new JFormattedTextField(maskFormatDate);
		cmpRehabDate.setHorizontalAlignment(SwingConstants.RIGHT);
		cmpRehabDate.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		cmpRehabDate.setColumns(10);
		cmpRehabDate.setBounds(198, 510, 100, 30);
		contentPane.add(cmpRehabDate);

		cmpMergeDate = new JFormattedTextField(maskFormatDate);
		cmpMergeDate.setHorizontalAlignment(SwingConstants.RIGHT);
		cmpMergeDate.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		cmpMergeDate.setColumns(10);
		cmpMergeDate.setBounds(198, 550, 100, 30);
		contentPane.add(cmpMergeDate);

		cmpLiquidDate = new JFormattedTextField(maskFormatDate);
		cmpLiquidDate.setHorizontalAlignment(SwingConstants.RIGHT);
		cmpLiquidDate.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		cmpLiquidDate.setColumns(10);
		cmpLiquidDate.setBounds(523, 510, 100, 30);
		contentPane.add(cmpLiquidDate);

		cmpWthDate = new JFormattedTextField(maskFormatDate);
		cmpWthDate.setHorizontalAlignment(SwingConstants.RIGHT);
		cmpWthDate.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		cmpWthDate.setColumns(10);
		cmpWthDate.setBounds(523, 550, 100, 30);
		contentPane.add(cmpWthDate);

		cmpLegend = new JTextField();
		cmpLegend.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		cmpLegend.setBounds(413, 190, 25, 30);
		contentPane.add(cmpLegend);
		cmpLegend.setColumns(10);

		cmpOnlineRpt = new JTextField();
		//force caps & restrict to Y or N...
		cmpOnlineRpt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (Character.isLowerCase(keyChar)) {
					e.setKeyChar(Character.toUpperCase(keyChar));
				}
			}
		});
		cmpOnlineRpt.addKeyListener(new KeyAdapter() {
			// enter numeric values only...
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				// check for letters and bad characters...
				if (!Character.isLetter(c)) {
					e.consume();
				} else {
					if ((c != 'Y') && (c != 'N')) {
						e.consume();
					}
				}
			}
		});
		cmpOnlineRpt.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		cmpOnlineRpt.setBounds(674, 471, 20, 30);
		contentPane.add(cmpOnlineRpt);
		cmpOnlineRpt.setColumns(10);

		cmpFinancials = new JTextField();
		//force caps & restrict to Y or N...
		cmpFinancials.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (Character.isLowerCase(keyChar)) {
					e.setKeyChar(Character.toUpperCase(keyChar));
				}
			}
		});
		cmpFinancials.addKeyListener(new KeyAdapter() {
			// enter numeric values only...
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				// check for letters and bad characters...
				if (!Character.isLetter(c)) {
					e.consume();
				} else {
					if ((c != 'Y') && (c != 'N')) {
						e.consume();
					}
				}
			}
		});
		cmpFinancials.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		cmpFinancials.setBounds(848, 471, 20, 30);
		contentPane.add(cmpFinancials);
		cmpFinancials.setColumns(10);

		cmpGroupCode = new JTextField();
		cmpGroupCode.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		cmpGroupCode.setColumns(10);
		cmpGroupCode.setBounds(523, 590, 135, 30);
		contentPane.add(cmpGroupCode);

		JRootPane rootPane = contentPane.getRootPane();
		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DateLoad();
				good = true;
				ValidateData();
				if (good) {
					InsertRecord();
					try {
						perInsert.setDbTaxId(cmpTaxId.getText());
						perInsert.setDbNAIC(Integer.parseInt(cmpNAIC.getText()));
						
						MbrCmpPercentageInsert percentageInsert = new MbrCmpPercentageInsert(perInsert);
						percentageInsert.dispose();
						percentageInsert.setVisible(true);
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, e2);
					}
				}
			}
		});
		btnEnter.setBounds(99, 649, 115, 30);
		contentPane.add(btnEnter);
		rootPane.setDefaultButton(btnEnter);

		JButton btnComment = new JButton("Comment");
		btnComment.setBounds(99, 689, 115, 29);
		btnComment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int intValue = Integer.parseInt(cmpNAIC.getText());
				comments = new MbrCmpComments(intValue);
				comments.setVisible(true);
			}
		});
		contentPane.add(btnComment);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(700, 649, 115, 30);
		contentPane.add(btnCancel);

	}

	public void DateLoad() {
		try {
			rehabDate = Integer.parseInt((cmpRehabDate.getText().replaceAll("\\D+", "")));
		} catch (Exception e) {
			rehabDate = java.sql.Types.INTEGER;
		}
		try {
			mergeDate = Integer.parseInt((cmpMergeDate.getText().replaceAll("\\D+", "")));
		} catch (Exception e) {
			mergeDate = java.sql.Types.INTEGER;
		}
		try {
			liquidDate = Integer.parseInt((cmpLiquidDate.getText().replaceAll("\\D+", "")));
		} catch (Exception e) {
			liquidDate = java.sql.Types.INTEGER;
		}
		try {
			withDate = Integer.parseInt((cmpWthDate.getText().replaceAll("\\D+", "")));
		} catch (Exception e) {
			withDate = java.sql.Types.INTEGER;
		}
	}

	public void ValidateData() {

		if (cmpNAIC.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Enter an Unique NAIC Number");
			cmpNAIC.setCaretPosition(0);
			good = false;
		}
		if (cmpTaxId.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Enter an Unique Tax Id Number");
			cmpTaxId.setCaretPosition(0);
			good = false;
		}
		if (cmpDE.getText().isEmpty() && cmpPA.getText().isEmpty() && cmpWV.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Valid State Code is Missing");
			cmpDE.setCaretPosition(0);
			good = false;
		}
		if (!cmpParticipation.getText().matches("[NY]+")) {
			JOptionPane.showMessageDialog(null, "Enter Y or N for Participation");
			cmpParticipation.setCaretPosition(0);
			good = false;
		}
		if (cmpCompanyName.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Enter an Company or Individual Name");
			cmpCompanyName.setCaretPosition(0);
			good = false;
		}
		if (cmpAddress1.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "An Address must be Entered");
			cmpAddress1.setCaretPosition(0);
			good = false;
		}
		if (cmpCity.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "An City must be Entered");
			cmpCity.setCaretPosition(0);
			good = false;
		}
		StateAbbrv(cmpState.getText());
		if (cmpState.getText().isEmpty() || cmpState.getText().equals(null)) {
			JOptionPane.showMessageDialog(null, "An Invalid State was Entered");
			cmpState.setCaretPosition(0);
			good = false;
		}
		if (cmpZipCode.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "An Zip Code must be Entered");
			cmpZipCode.setCaretPosition(0);
			good = false;
		}

	}

	public void StateAbbrv(String abbrvState) {
		// ...https://dzone.com/articles/java-hashmap-search-and-sort
		Map<String, String> states = new HashMap<String, String>();
		states.put("Alabama", "AL");
		states.put("Alaska", "AK");
		states.put("Arizona", "AZ");
		states.put("Arkansas", "AR");
		states.put("Armed Forces (AE)", "AE");
		states.put("Armed Forces Americas", "AA");
		states.put("Armed Forces Pacific", "AP");
		states.put("California", "CA");
		states.put("Colorado", "CO");
		states.put("Connecticut", "CT");
		states.put("Delaware", "DE");
		states.put("District Of Columbia", "DC");
		states.put("Florida", "FL");
		states.put("Georgia", "GA");
		states.put("Hawaii", "HI");
		states.put("Idaho", "ID");
		states.put("Illinois", "IL");
		states.put("Indiana", "IN");
		states.put("Iowa", "IA");
		states.put("Kansas", "KS");
		states.put("Kentucky", "KY");
		states.put("Louisiana", "LA");
		states.put("Maine", "ME");
		states.put("Maryland", "MD");
		states.put("Massachusetts", "MA");
		states.put("Michigan", "MI");
		states.put("Minnesota", "MN");
		states.put("Mississippi", "MS");
		states.put("Missouri", "MO");
		states.put("Montana", "MT");
		states.put("Nebraska", "NE");
		states.put("Nevada", "NV");
		states.put("New Hampshire", "NH");
		states.put("New Jersey", "NJ");
		states.put("New Mexico", "NM");
		states.put("New York", "NY");
		states.put("North Carolina", "NC");
		states.put("North Dakota", "ND");
		states.put("Ohio", "OH");
		states.put("Oklahoma", "OK");
		states.put("Oregon", "OR");
		states.put("Pennsylvania", "PA");
		states.put("Puerto Rico", "PR");
		states.put("Rhode Island", "RI");
		states.put("South Carolina", "SC");
		states.put("South Dakota", "SD");
		states.put("Tennessee", "TN");
		states.put("Texas", "TX");
		states.put("Utah", "UT");
		states.put("Vermont", "VT");
		states.put("Virgin Islands", "VI");
		states.put("Virginia", "VA");
		states.put("Washington", "WA");
		states.put("West Virginia", "WV");
		states.put("Wisconsin", "WI");
		states.put("Wyoming", "WY");

		if (!states.containsValue(abbrvState)) {
			cmpState.setText(null);
			abbrvState = null;
			return;
		}
	}

	// Load table
	public void InsertRecord() {

		// Connect to database...
		connection = sqlConnection.dbConnector();

		//String username = System.getProperty("user.name");

		String sqlQuery = "Insert Into MemberCompanyContacts (NAIC,StatusType,Legend,TaxId,CompanyName,Recipient"
				+ ",AddressOne,AddressTwo,City,State,ZipCode,DECd,PACd,WVCd"
				+ ",NAICGroup,GroupCode,Participate,WthDECd,WthPACd,WthWVCd"
				+ ",RehabDate,MergeDate,LiquidDate,WthDrwDate)"
				+ " Values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			preStmt = connection.prepareStatement(sqlQuery);
			preStmt.setInt(1, Integer.parseInt(cmpNAIC.getText())); // NAIC
			preStmt.setString(2, stat); // StatusType
			preStmt.setString(3, cmpLegend.getText()); // Legend
			preStmt.setInt(4, Integer.parseInt((cmpTaxId.getText()))); // TaxId
			preStmt.setString(5, cmpCompanyName.getText()); // CompanyName
			preStmt.setString(6, cmpAttention.getText()); // Recipient
			preStmt.setString(7, cmpAddress1.getText()); // AddressOne
			preStmt.setString(8, cmpAddress2.getText()); // AddressTwo
			preStmt.setString(9, cmpCity.getText()); // City
			preStmt.setString(10, cmpState.getText()); // State
			preStmt.setInt(11, Integer.parseInt((cmpZipCode.getText()))); // ZipCode
			preStmt.setString(12, cmpDE.getText()); // DECd
			preStmt.setString(13, cmpPA.getText()); // PACd
			preStmt.setString(14, cmpWV.getText()); // WVCd
			if (GroupNAIC.getText().equals(null) || GroupNAIC.getText().isEmpty()) {
				preStmt.setNull(15, java.sql.Types.INTEGER); // NAICGroup
			} else {
				preStmt.setInt(15, Integer.parseInt(GroupNAIC.getText())); // NAICGroup
			}
			preStmt.setString(16, cmpGroupCode.getText()); // GroupCode
			preStmt.setString(17, cmpParticipation.getText()); // Participation
			preStmt.setString(18, cmpWthStDE.getText()); // WthDECd
			preStmt.setString(19, cmpWthStPA.getText()); // WthPACd
			preStmt.setString(20, cmpWthStWV.getText()); // WthWVCd
			preStmt.setInt(21, rehabDate); // RehabDate
			preStmt.setInt(22, mergeDate); // MergeDate
			preStmt.setInt(23, liquidDate); // LiquidDate
			preStmt.setInt(24, withDate); // WthDrwDate
			preStmt.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			good=false;
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e);
				}
		}
	}

	public void setUpFormatting() {
		try {
			maskFormat6Digits = new NumberFormatter(new DecimalFormat("######"));
			maskFormat6Digits.setMaximum(999999);
			maskFormat6Digits.setAllowsInvalid(false);

			maskFormatTax = new NumberFormatter(new DecimalFormat("######"));
			maskFormatTax.setMinimum(0);
			maskFormatTax.setMaximum(999999999);
			maskFormatTax.setAllowsInvalid(false);

			maskFormatDate = new MaskFormatter("##/##/####");
			maskFormatDate.setPlaceholderCharacter(' ');

			maskFormatZip = new NumberFormatter(new DecimalFormat("#####"));
			maskFormatZip.setMaximum(99999);
			maskFormatZip.setAllowsInvalid(false);

		} catch (ParseException p) {
			JOptionPane.showMessageDialog(null, "setUpFormatting: " + p);
		}
	}
}
