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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DateFormatter;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

import com.microsoft.sqlserver.jdbc.SQLServerPreparedStatement;

public class MbrCompanyDisplay extends JFrame {

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
	private JTextField cmpGroupName, cmpGroupCode;
	private JTextField cmpWthStDE, cmpWthStPA, cmpWthStWV;
	private JTextField cmpNumberInGroup;
	private JFormattedTextField cmpRehabDate;
	private JFormattedTextField cmpMergeDate;
	private JFormattedTextField cmpLiquidDate;
	private JFormattedTextField cmpWthDate;
	private JTextField cmpLegend;
	private JFormattedTextField GroupNAIC;
	private JTextField cmpOnlineRpt;
	private JTextField cmpFinancials;
	private boolean good;
	private JRadioButton rdbtnActive, rdbtnInactive, rdbtnSuspended;
	private ButtonGroup statusGroup;

	private String cvtToString, stat;
	private MbrCmpComments comments;
	private DatabaseVariables dbVar = new DatabaseVariables();
	ResultSet rstSet = null;
	PreparedStatement preStmt = null;
	Connection connection = null;

	DatabaseVariables perInsert = new DatabaseVariables();
	DateFormat dateFmt = new SimpleDateFormat("MM/dd/yyyy");
	DateFormatter simpleDateFormat = new DateFormatter(dateFmt);
	int rehabDate, mergeDate, liquidDate, withDate;
	// DecimalFormat maskFormatAmount;
	// DecimalFormatSymbols symbols;
	MaskFormatter maskFormatDate;
	NumberFormatter maskFormatZip;
	NumberFormatter maskFormat6Digits;
	NumberFormatter maskFormatTax;
	private JTextField cmpPhone;
	private JTextField cmpFax;
	private JTextField cmpEmail;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MbrCompanyDisplay frame = new MbrCompanyDisplay(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// public MbrCompanyDisplay() {}

	// Main Constructor...
	public MbrCompanyDisplay(DatabaseVariables dbVar) {
		super("Insurance Placement Facility of Pennsylvania");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 946, 855);
		contentPane = new JPanel();
		contentPane.setSize(945, 801);
		setContentPane(contentPane);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		JLabel lblMemberCompany = new JLabel("Member Company");
		lblMemberCompany.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblMemberCompany.setBounds(385, 20, 185, 25);
		contentPane.add(lblMemberCompany);

		JLabel lblDataFileMaintenance = new JLabel("Data File Entry");
		lblDataFileMaintenance.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblDataFileMaintenance.setBounds(410, 52, 145, 25);
		contentPane.add(lblDataFileMaintenance);

		JLabel lblNaic_1 = new JLabel("NAIC#:");
		lblNaic_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNaic_1.setBounds(33, 161, 80, 30);
		contentPane.add(lblNaic_1);

		JLabel lblTaxId = new JLabel("Tax Id: ");
		lblTaxId.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTaxId.setBounds(338, 161, 70, 30);
		contentPane.add(lblTaxId);

		JLabel lblStateCodes = new JLabel("State Codes:");
		lblStateCodes.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblStateCodes.setBounds(588, 161, 120, 30);
		contentPane.add(lblStateCodes);

		JLabel lblParticipation = new JLabel("Participation: ");
		lblParticipation.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblParticipation.setBounds(593, 105, 125, 30);
		contentPane.add(lblParticipation);

		JLabel lblCompanyName = new JLabel("Company Name:");
		lblCompanyName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCompanyName.setBounds(33, 201, 170, 30);
		contentPane.add(lblCompanyName);

		JLabel lblAttention = new JLabel("Attention:");
		lblAttention.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblAttention.setBounds(33, 241, 100, 30);
		contentPane.add(lblAttention);

		JLabel lblAddress = new JLabel("Address 1:");
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblAddress.setBounds(33, 281, 110, 30);
		contentPane.add(lblAddress);

		JLabel lblAddress_1 = new JLabel("Address 2:");
		lblAddress_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblAddress_1.setBounds(33, 321, 110, 30);
		contentPane.add(lblAddress_1);

		JLabel lblCity = new JLabel("City:");
		lblCity.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCity.setBounds(33, 361, 50, 30);
		contentPane.add(lblCity);

		JLabel lblState = new JLabel("State:");
		lblState.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblState.setBounds(558, 361, 60, 30);
		contentPane.add(lblState);

		JLabel lblZipCode = new JLabel("Zip Code:");
		lblZipCode.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblZipCode.setBounds(688, 361, 100, 30);
		contentPane.add(lblZipCode);

		JLabel lblGroupName = new JLabel("Group Name:");
		lblGroupName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblGroupName.setBounds(33, 641, 135, 30);
		contentPane.add(lblGroupName);

		JLabel lblGroupNaic = new JLabel("Group NAIC#:");
		lblGroupNaic.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblGroupNaic.setBounds(33, 601, 145, 30);
		contentPane.add(lblGroupNaic);

		JLabel lblGroupCode = new JLabel("Group Code:");
		lblGroupCode.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblGroupCode.setBounds(363, 601, 125, 30);
		contentPane.add(lblGroupCode);

		JLabel lblNumberInThe = new JLabel("Number in the Group:");
		lblNumberInThe.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNumberInThe.setBounds(33, 481, 215, 30);
		contentPane.add(lblNumberInThe);

		JLabel lblRehabDate = new JLabel("Rehab Date:");
		lblRehabDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblRehabDate.setBounds(33, 521, 130, 30);
		contentPane.add(lblRehabDate);

		JLabel lblMergeDate = new JLabel("Merge Date:");
		lblMergeDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblMergeDate.setBounds(33, 561, 125, 30);
		contentPane.add(lblMergeDate);

		JLabel lblLiquidDate = new JLabel("Liquid Date:");
		lblLiquidDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblLiquidDate.setBounds(363, 521, 125, 30);
		contentPane.add(lblLiquidDate);

		JLabel lblWithdrewDate = new JLabel("Withdrew Date:");
		lblWithdrewDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblWithdrewDate.setBounds(363, 561, 155, 30);
		contentPane.add(lblWithdrewDate);

		JLabel lblWithdrawState = new JLabel("Withdraw State");
		lblWithdrawState.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblWithdrawState.setBounds(713, 521, 155, 30);
		contentPane.add(lblWithdrawState);
		stat = "A";

		JLabel lblLegend = new JLabel("Legend:");
		lblLegend.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblLegend.setBounds(338, 105, 80, 30);
		contentPane.add(lblLegend);

		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblStatus.setBounds(33, 78, 60, 20);
		contentPane.add(lblStatus);

		JLabel lblOnlineReports = new JLabel("Online Reports:");
		lblOnlineReports.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblOnlineReports.setBounds(315, 481, 155, 30);
		contentPane.add(lblOnlineReports);

		JLabel lblFinancials = new JLabel("Financials:");
		lblFinancials.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblFinancials.setBounds(558, 481, 105, 30);
		contentPane.add(lblFinancials);

		JLabel lblPhone = new JLabel("Phone:");
		lblPhone.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPhone.setBounds(33, 401, 75, 20);
		contentPane.add(lblPhone);

		JLabel lblFax = new JLabel("Fax:");
		lblFax.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblFax.setBounds(405, 401, 50, 20);
		contentPane.add(lblFax);

		JLabel lblNewLabel_1 = new JLabel("Email:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(33, 441, 75, 20);
		contentPane.add(lblNewLabel_1);

		cvtToString = String.valueOf(dbVar.getDbNAIC());
		cmpNAIC = new JFormattedTextField(maskFormat6Digits);
		cmpNAIC.setEditable(false);
		cmpNAIC.setHorizontalAlignment(SwingConstants.RIGHT);
		cmpNAIC.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cmpNAIC.setBounds(198, 161, 75, 30);
		cmpNAIC.setText(cvtToString);
		contentPane.add(cmpNAIC);
		cmpNAIC.setColumns(10);
		cmpNAIC.setFocusLostBehavior(JFormattedTextField.PERSIST);

		cmpTaxId = new JFormattedTextField(maskFormatTax);
		cmpTaxId.setHorizontalAlignment(SwingConstants.RIGHT);
		cmpTaxId.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cmpTaxId.setBounds(420, 161, 100, 30);
		cmpTaxId.setText(dbVar.getDbTaxId());
		cmpTaxId.setColumns(10);
		cmpTaxId.setFocusLostBehavior(JFormattedTextField.PERSIST);
		contentPane.add(cmpTaxId);

		cmpDE = new JTextField();
		cmpDE.setText(dbVar.getDbDeCd());
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
		cmpDE.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cmpDE.setBounds(723, 161, 25, 30);
		cmpDE.setColumns(10);
		contentPane.add(cmpDE);

		cmpPA = new JTextField();
		cmpPA.setText(dbVar.getDbPaCd());
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
		cmpPA.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cmpPA.setColumns(10);
		cmpPA.setBounds(768, 161, 25, 30);
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
		cmpWV.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cmpWV.setColumns(10);
		cmpWV.setBounds(813, 161, 25, 30);
		contentPane.add(cmpWV);

		cmpParticipation = new JTextField(1);
		cmpParticipation.setText(dbVar.getDbParticipate());
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
		cmpParticipation.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cmpParticipation.setColumns(5);
		cmpParticipation.setBounds(728, 105, 20, 30);
		contentPane.add(cmpParticipation);

		cmpCompanyName = new JTextField();
		cmpCompanyName.setText(dbVar.getDbCmpName());
		cmpCompanyName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cmpCompanyName.setBounds(198, 201, 675, 30);
		contentPane.add(cmpCompanyName);
		cmpCompanyName.setColumns(10);

		cmpAttention = new JTextField();
		cmpAttention.setText(dbVar.getDbAttn());
		cmpAttention.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cmpAttention.setColumns(10);
		cmpAttention.setBounds(198, 241, 675, 30);
		contentPane.add(cmpAttention);

		cmpAddress1 = new JTextField();
		cmpAddress1.setText(dbVar.getDbAddress1());
		cmpAddress1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cmpAddress1.setColumns(10);
		cmpAddress1.setBounds(198, 281, 675, 30);
		contentPane.add(cmpAddress1);

		cmpAddress2 = new JTextField();
		cmpAddress2.setText(dbVar.getDbAddress2());
		cmpAddress2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cmpAddress2.setColumns(10);
		cmpAddress2.setBounds(198, 321, 675, 30);
		contentPane.add(cmpAddress2);

		cmpCity = new JTextField();
		cmpCity.setText(dbVar.getDbCity());
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
		cmpCity.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cmpCity.setBounds(198, 361, 345, 30);
		contentPane.add(cmpCity);
		cmpCity.setColumns(10);

		cmpState = new JTextField();
		cmpState.setText(dbVar.getDbState());
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
		cmpState.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cmpState.setBounds(618, 361, 50, 30);
		contentPane.add(cmpState);
		cmpState.setColumns(10);

		cmpZipCode = new JFormattedTextField(maskFormatZip);
		cmpZipCode.setText(String.valueOf(dbVar.getDbZip()));
		cmpZipCode.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cmpZipCode.setBounds(793, 361, 85, 30);
		contentPane.add(cmpZipCode);
		cmpZipCode.setColumns(10);

		cmpNumberInGroup = new JTextField();
		cmpNumberInGroup.setText(String.valueOf(dbVar.getDbNumberInTheGroup()));
		cmpNumberInGroup.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cmpNumberInGroup.setColumns(10);
		cmpNumberInGroup.setBounds(253, 481, 25, 30);
		contentPane.add(cmpNumberInGroup);

		cmpWthStDE = new JTextField();
		cmpWthStDE.setText(dbVar.getDbWthStateDe());
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
		cmpWthStDE.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cmpWthStDE.setColumns(10);
		cmpWthStDE.setBounds(733, 561, 25, 30);
		contentPane.add(cmpWthStDE);

		cmpWthStPA = new JTextField();
		cmpWthStPA.setText(dbVar.getDbWthStatePa());
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
		cmpWthStPA.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cmpWthStPA.setColumns(10);
		cmpWthStPA.setBounds(773, 561, 25, 30);
		contentPane.add(cmpWthStPA);

		cmpWthStWV = new JTextField();
		cmpWthStWV.setText(dbVar.getDbWthStateWv());
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
		cmpWthStWV.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cmpWthStWV.setColumns(10);
		cmpWthStWV.setBounds(813, 561, 25, 30);
		contentPane.add(cmpWthStWV);

		GroupNAIC = new JFormattedTextField(maskFormat6Digits);
		GroupNAIC.setText(String.valueOf(dbVar.getDbGrpNAIC()));
		GroupNAIC.setHorizontalAlignment(SwingConstants.RIGHT);
		GroupNAIC.addKeyListener(new KeyAdapter() {
			final static String badchars = "`~!@#$%^&*()+_=\\|\"':;?/><,.-+ ";

			// enter numeric values only...
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				// check for letters and bad characters...
				if ((Character.isLetter(c) || (c == KeyEvent.VK_BACK_SPACE)) || (badchars.indexOf(c) < -1)) {
					e.consume();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				int keyCode = e.getKeyCode();
				int pos;
				if (keyCode == KeyEvent.VK_ADD || keyCode == KeyEvent.VK_PLUS) {
					String str = GroupNAIC.getText();
					pos = str.indexOf("+");
					if (pos > 1) {
						e.consume();
						GroupNAIC.setText(str.substring(0, pos));
						cmpGroupName.requestFocus();
					}
				}
			}
		});
		GroupNAIC.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GroupNAIC.setBounds(198, 601, 105, 30);
		contentPane.add(GroupNAIC);
		GroupNAIC.setColumns(10);
		GroupNAIC.setFocusLostBehavior(JFormattedTextField.PERSIST);
		GroupNAIC.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				GroupNAIC.setText(GroupNAIC.getText().trim());
			}

		});

		cmpGroupName = new JTextField();
		cmpGroupName.setText(dbVar.getDbGroupName());
		cmpGroupName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cmpGroupName.setColumns(10);
		cmpGroupName.setBounds(198, 641, 675, 30);
		contentPane.add(cmpGroupName);

		cmpRehabDate = new JFormattedTextField(maskFormatDate);
		cmpRehabDate.setText(String.valueOf(dbVar.getDbRehabDate()));
		cmpRehabDate.setHorizontalAlignment(SwingConstants.RIGHT);
		cmpRehabDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cmpRehabDate.setColumns(10);
		cmpRehabDate.setBounds(198, 521, 100, 30);
		contentPane.add(cmpRehabDate);

		cmpMergeDate = new JFormattedTextField(maskFormatDate);
		cmpMergeDate.setText(String.valueOf(dbVar.getDbMergeDate()));
		cmpMergeDate.setHorizontalAlignment(SwingConstants.RIGHT);
		cmpMergeDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cmpMergeDate.setColumns(10);
		cmpMergeDate.setBounds(198, 561, 100, 30);
		contentPane.add(cmpMergeDate);

		cmpLiquidDate = new JFormattedTextField(maskFormatDate);
		cmpLiquidDate.setText(String.valueOf(dbVar.getDbLiquidDate()));
		cmpLiquidDate.setHorizontalAlignment(SwingConstants.RIGHT);
		cmpLiquidDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cmpLiquidDate.setColumns(10);
		cmpLiquidDate.setBounds(523, 521, 100, 30);
		contentPane.add(cmpLiquidDate);

		cmpWthDate = new JFormattedTextField(maskFormatDate);
		cmpWthDate.setText(String.valueOf(dbVar.getDbWthDrewDate()));
		cmpWthDate.setHorizontalAlignment(SwingConstants.RIGHT);
		cmpWthDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cmpWthDate.setColumns(10);
		cmpWthDate.setBounds(523, 561, 100, 30);
		contentPane.add(cmpWthDate);

		cmpLegend = new JTextField();
		cmpLegend.setText(dbVar.getDbLegend());
		cmpLegend.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cmpLegend.setBounds(430, 105, 25, 30);
		contentPane.add(cmpLegend);
		cmpLegend.setColumns(10);

		cmpOnlineRpt = new JTextField();
		cmpOnlineRpt.setText(dbVar.getDbOnlineRpt());
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
		cmpOnlineRpt.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cmpOnlineRpt.setBounds(475, 481, 20, 30);
		contentPane.add(cmpOnlineRpt);
		cmpOnlineRpt.setColumns(10);

		cmpFinancials = new JTextField();
		cmpFinancials.setText(dbVar.getDbFinancials());
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
		cmpFinancials.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cmpFinancials.setBounds(670, 481, 20, 30);
		contentPane.add(cmpFinancials);
		cmpFinancials.setColumns(10);

		cmpGroupCode = new JTextField();
		cmpGroupCode.setText(dbVar.getDbGroupCode());
		cmpGroupCode.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cmpGroupCode.setColumns(10);
		cmpGroupCode.setBounds(523, 601, 135, 30);
		contentPane.add(cmpGroupCode);

		rdbtnActive = new JRadioButton("Active");
		rdbtnActive.setBounds(87, 79, 109, 23);
		rdbtnInactive = new JRadioButton("Inactive");
		rdbtnInactive.setBounds(87, 105, 109, 23);
		rdbtnSuspended = new JRadioButton("Suspended");
		rdbtnSuspended.setBounds(87, 131, 109, 23);

		contentPane.add(rdbtnActive);
		contentPane.add(rdbtnInactive);
		contentPane.add(rdbtnSuspended);

		statusGroup = new ButtonGroup();
		statusGroup.add(rdbtnActive);
		statusGroup.add(rdbtnInactive);
		statusGroup.add(rdbtnSuspended);
		rdbtnActive.setActionCommand("Active");
		rdbtnInactive.setActionCommand("Inactive");
		rdbtnSuspended.setActionCommand("Suspended");

		switch (dbVar.getDbStatus()) {
		case "A":
			rdbtnActive.setSelected(true);
			break;
		case "I":
			rdbtnInactive.setSelected(true);
			break;
		case "S":
			rdbtnSuspended.setSelected(true);
			break;
		}

		cmpPhone = new JTextField();
		cmpPhone.setText(dbVar.getDbPhone());
		cmpPhone.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cmpPhone.setBounds(198, 401, 150, 30);
		contentPane.add(cmpPhone);
		cmpPhone.setColumns(10);

		cmpFax = new JTextField();
		cmpFax.setText(dbVar.getDbFax());
		cmpFax.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cmpFax.setBounds(450, 401, 150, 30);
		contentPane.add(cmpFax);
		cmpFax.setColumns(10);

		cmpEmail = new JTextField();
		cmpEmail.setText(dbVar.getDbEmail());
		cmpEmail.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cmpEmail.setBounds(198, 441, 400, 30);
		contentPane.add(cmpEmail);
		cmpEmail.setColumns(10);

		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DateLoad();
				good = true;
				ValidateData();
				if (good) {
					UpdateRecord();
					try {
						perInsert.setDbTaxId(cmpTaxId.getText());
						perInsert.setDbNAIC(Integer.parseInt(cmpNAIC.getText()));
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, e2);
					}
				}
			}
		});
		btnEnter.setBounds(99, 710, 115, 30);
		contentPane.add(btnEnter);

		JButton btnComment = new JButton("Comment");
		btnComment.setBounds(100, 750, 115, 29);
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
		btnCancel.setBounds(700, 710, 115, 30);
		contentPane.add(btnCancel);

		JButton btnParticipation = new JButton("Participation %");
		btnParticipation.setBounds(260, 710, 150, 30);
		contentPane.add(btnParticipation);

		JButton btnTotalNotPremium = new JButton("Total Not Premium");
		btnTotalNotPremium.setBounds(475, 710, 150, 30);
		contentPane.add(btnTotalNotPremium);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(700, 750, 115, 30);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnDelete.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						connection = sqlConnection.dbConnector();
						try {
							runMbrRmv(Integer.parseInt(cmpNAIC.getText()));
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, e + "Error out: DltMember");
						}
					}
				});
			}
		});
		contentPane.add(btnDelete);		

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
		if (!GroupNAIC.getText().equals("0")){
			if (cmpGroupName.getText().isEmpty() && !GroupNAIC.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "If Group NAIC is Entered, Group Name have to be Entered");
				cmpGroupName.setCaretPosition(0);
				good = false;
			}
			if (!cmpGroupName.getText().isEmpty() && GroupNAIC.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "If Group Name is Entered, Group Name have to be Entered");
				cmpGroupName.setCaretPosition(0);
				good = false;
			}
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
	public void UpdateRecord() {

		// Connect to database...
		connection = sqlConnection.dbConnector();

		String optStatus = statusGroup.getSelection().getActionCommand();
		switch (optStatus) {
		case "Active":
			dbVar.setDbStatus("A");
			break;
		case "Inactive":
			dbVar.setDbStatus("I");
			break;
		case "Suspended":
			dbVar.setDbStatus("S");
			break;
		}

		String userName = System.getProperty("user.name");

		String sqlQuery = "Update MemberCompanyContacts "
				+ "Set StatusType=?,Legend=?,TaxId=?,CompanyName=?,Recipient=?"
				+ ",AddressOne=?,AddressTwo=?,City=?,State=?,ZipCode=?,DECd=?,PACd=?,WVCd=?"
				+ ",NAICGroup=?,NumberInGroup=?,GroupCode=?,Participate=?,WthDECd=?,WthPACd=?,WthWVCd=?"
				+ ",RehabDate=?,MergeDate=?,LiquidDate=?,WthDrwDate=? Where NAIC=?";

		try {
			preStmt = connection.prepareStatement(sqlQuery);
			preStmt.setString(1, stat); // StatusType
			preStmt.setString(2, cmpLegend.getText()); // Legend
			preStmt.setInt(3, Integer.parseInt((cmpTaxId.getText()))); // TaxId
			preStmt.setString(4, cmpCompanyName.getText()); // CompanyName
			preStmt.setString(5, cmpAttention.getText()); // Recipient
			preStmt.setString(6, cmpAddress1.getText()); // AddressOne
			preStmt.setString(7, cmpAddress2.getText()); // AddressTwo
			preStmt.setString(8, cmpCity.getText()); // City
			preStmt.setString(9, cmpState.getText()); // State
			preStmt.setInt(10, Integer.parseInt((cmpZipCode.getText()))); // ZipCode
			preStmt.setString(11, cmpDE.getText()); // DECd
			preStmt.setString(12, cmpPA.getText()); // PACd
			preStmt.setString(13, cmpWV.getText()); // WVCd
			if (GroupNAIC.getText().equals(null) || GroupNAIC.getText().isEmpty()) {
				preStmt.setNull(14, java.sql.Types.INTEGER); // NAICGroup
			} else {
				preStmt.setInt(14, Integer.parseInt(GroupNAIC.getText())); // NAICGroup
			}
			preStmt.setInt(15, Integer.parseInt(cmpNumberInGroup.getText())); // NumberInGroup
			preStmt.setString(16, cmpGroupCode.getText()); // GroupCode
			preStmt.setString(17, cmpParticipation.getText()); // Participation
			preStmt.setString(18, cmpWthStDE.getText()); // WthDECd
			preStmt.setString(19, cmpWthStPA.getText()); // WthPACd
			preStmt.setString(20, cmpWthStWV.getText()); // WthWVCd
			preStmt.setInt(21, rehabDate); // RehabDate
			preStmt.setInt(22, mergeDate); // MergeDate
			preStmt.setInt(23, liquidDate); // LiquidDate
			preStmt.setInt(24, withDate); // WthDrwDate
			preStmt.setString(25, cmpNAIC.getText()); // UserId
			preStmt.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			good = false;
		} finally {
			if (connection != null)
				try {
					connection.close();
					dispose();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e + " " + userName);
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

	public void runMbrRmv(int qNAIC) throws SQLException {
		SQLServerPreparedStatement preStmt = (SQLServerPreparedStatement) connection
				.prepareStatement("Exec Member_Company_DltMember ?");
		preStmt.setString(1, cmpNAIC.getText());
		preStmt.execute();
		dispose();

	}
}
