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
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MbrCmpGroupChg extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String groupValue;
	private JTextField txtGrpWV;
	private JTextField txtGrpPA;
	private JTextField txtGrpDE;
	private JTextField txtGrpPart;
	private JTextField txtGrpNAIC;
	private JTextField txtGrpCode;
	private JTextField txtGrpName;
	private JTextField txtGrpAttn;
	private JTextField txtGrpAddress1;
	private JTextField txtGrpAddress2;
	private JTextField txtGrpCity;
	private JTextField txtGrpState;
	private JTextField txtGrpZ‬;
	private JTextField txtGrpStatus;
	private JButton btnEnter;
	private JButton btnDelete;
	private JButton btnCancel;
	private JTextField txtReserveGroup;

	String cntChgVar;

	ResultSet rstSet = null;
	PreparedStatement preStmt = null;
	Connection connection = null;

	public String getGroupValue() {
		return groupValue;
	}

	public void setGroupValue(String groupValue) {
		this.groupValue = groupValue;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MbrCmpGroupChg frame = new MbrCmpGroupChg(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MbrCmpGroupChg(DatabaseVariables dbVar) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 430);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblGroupMaintenace = new JLabel("Group Maintenace");
		lblGroupMaintenace.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblGroupMaintenace.setHorizontalAlignment(SwingConstants.CENTER);
		lblGroupMaintenace.setBounds(165, 10, 125, 20);
		contentPane.add(lblGroupMaintenace);

		JLabel lblGroupNaic = new JLabel("Group NAIC:");
		lblGroupNaic.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGroupNaic.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblGroupNaic.setBounds(25, 40, 120, 20);
		contentPane.add(lblGroupNaic);

		JLabel lblGroupCode = new JLabel("Group Code:");
		lblGroupCode.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGroupCode.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblGroupCode.setBounds(25, 65, 120, 20);
		contentPane.add(lblGroupCode);

		JLabel lblGroupName = new JLabel("Group Name:");
		lblGroupName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGroupName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblGroupName.setBounds(25, 90, 120, 20);
		contentPane.add(lblGroupName);

		JLabel lblGroupAttn = new JLabel("Group Attn:");
		lblGroupAttn.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGroupAttn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblGroupAttn.setBounds(25, 115, 120, 20);
		contentPane.add(lblGroupAttn);

		JLabel lblGroupAddress1 = new JLabel("Group Address1:");
		lblGroupAddress1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGroupAddress1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblGroupAddress1.setBounds(25, 140, 120, 20);
		contentPane.add(lblGroupAddress1);

		JLabel lblGroupAddress2 = new JLabel("Group Address2:");
		lblGroupAddress2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGroupAddress2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblGroupAddress2.setBounds(25, 165, 120, 20);
		contentPane.add(lblGroupAddress2);

		JLabel lblGroupCity = new JLabel("Group City:");
		lblGroupCity.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGroupCity.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblGroupCity.setBounds(25, 190, 120, 20);
		contentPane.add(lblGroupCity);

		JLabel lblGroupState = new JLabel("Group State:");
		lblGroupState.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGroupState.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblGroupState.setBounds(25, 215, 120, 20);
		contentPane.add(lblGroupState);

		JLabel lblGroupZip = new JLabel("Group Zip:");
		lblGroupZip.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGroupZip.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblGroupZip.setBounds(25, 240, 120, 20);
		contentPane.add(lblGroupZip);

		JLabel lblGroupParticipation = new JLabel("Participation:");
		lblGroupParticipation.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGroupParticipation.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblGroupParticipation.setBounds(25, 265, 120, 20);
		contentPane.add(lblGroupParticipation);

		JLabel lblGroupDE = new JLabel("DE:");
		lblGroupDE.setHorizontalAlignment(SwingConstants.CENTER);
		lblGroupDE.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblGroupDE.setBounds(190, 265, 40, 20);
		contentPane.add(lblGroupDE);

		JLabel lblGroupPA = new JLabel("PA:");
		lblGroupPA.setHorizontalAlignment(SwingConstants.CENTER);
		lblGroupPA.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblGroupPA.setBounds(260, 265, 40, 20);
		contentPane.add(lblGroupPA);
		JLabel lblGroupWV = new JLabel("WV:");
		lblGroupWV.setHorizontalAlignment(SwingConstants.CENTER);
		lblGroupWV.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblGroupWV.setBounds(335, 265, 40, 20);
		contentPane.add(lblGroupWV);

		JLabel lblGroupStatus = new JLabel("Group Status:");
		lblGroupStatus.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGroupStatus.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblGroupStatus.setBounds(25, 290, 120, 20);
		contentPane.add(lblGroupStatus);

		txtGrpNAIC = new JTextField();
		txtGrpNAIC.setText(String.valueOf(dbVar.getDbGrpNAIC()));
		// **** Numeric Values Only  *******************************************************************************
		txtGrpNAIC.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		});
		// **********************************************************************************************************
		txtGrpNAIC.setBounds(150, 40, 86, 20);
		contentPane.add(txtGrpNAIC);
		txtGrpNAIC.setColumns(10);

		txtGrpCode = new JTextField();
		txtGrpCode.setText(dbVar.getDbGroupCode());
		// Default to Upper Case...
		txtGrpCode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (Character.isLowerCase(keyChar)) {
					e.setKeyChar(Character.toUpperCase(keyChar));
				}
			}
		});
		txtGrpCode.setBounds(150, 65, 86, 20);
		contentPane.add(txtGrpCode);
		txtGrpCode.setColumns(10);

		txtReserveGroup = new JTextField();
		txtReserveGroup.setText(dbVar.getRsvGrpCode());
		txtReserveGroup.setEnabled(false);
		txtReserveGroup.setEditable(false);
		txtReserveGroup.setBounds(310, 65, 85, 20);
		contentPane.add(txtReserveGroup);
		txtReserveGroup.setColumns(10);

		txtGrpName = new JTextField();
		txtGrpName.setText(dbVar.getDbGroupName());
		txtGrpName.setBounds(150, 90, 250, 20);
		contentPane.add(txtGrpName);
		txtGrpName.setColumns(10);

		txtGrpAttn = new JTextField();
		txtGrpAttn.setText(dbVar.getDbGroupAttn());
		txtGrpAttn.setBounds(150, 115, 250, 20);
		contentPane.add(txtGrpAttn);
		txtGrpAttn.setColumns(10);

		txtGrpAddress1 = new JTextField();
		txtGrpAddress1.setText(dbVar.getDbGroupAddressOne());
		txtGrpAddress1.setBounds(150, 140, 250, 20);
		contentPane.add(txtGrpAddress1);
		txtGrpAddress1.setColumns(10);

		txtGrpAddress2 = new JTextField();
		txtGrpAddress2.setText(dbVar.getDbGroupAddressTwo());
		txtGrpAddress2.setBounds(150, 165, 250, 20);
		contentPane.add(txtGrpAddress2);
		txtGrpAddress2.setColumns(10);

		txtGrpCity = new JTextField();
		txtGrpCity.setText(dbVar.getDbGroupCity());
		txtGrpCity.setBounds(150, 190, 86, 20);
		contentPane.add(txtGrpCity);
		txtGrpCity.setColumns(10);

		txtGrpState = new JTextField();
		txtGrpState.setText(dbVar.getDbGroupState());
		//*********************************************
		// Default to Upper Case...
		txtGrpState.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (Character.isLowerCase(keyChar)) {
					e.setKeyChar(Character.toUpperCase(keyChar));
				}
			}
		});
		txtGrpState.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				StateAbbrv(txtGrpState.getText());
				if (txtGrpState.getText().length() != 2) {
				JOptionPane.showMessageDialog(null, "Invalid State");					}
			}
          });		//*********************************************
		txtGrpState.setBounds(150, 215, 60, 20);
		contentPane.add(txtGrpState);
		txtGrpState.setColumns(10);

		txtGrpZ‬ = new JTextField();
		txtGrpZ‬.setText(String.valueOf(dbVar.getDbGroupZipCode()));
		// **** Numeric Values Only  ******************************
		txtGrpZ‬.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		});
		// ********************************************************
		txtGrpZ‬.setBounds(150, 240, 86, 20);
		contentPane.add(txtGrpZ‬);
		txtGrpZ‬.setColumns(10);

		txtGrpDE = new JTextField();
		txtGrpDE.setText(dbVar.getDbGroupStateDE());
		// Default to Upper Case...
		txtGrpDE.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (Character.isLowerCase(keyChar)) {
					e.setKeyChar(Character.toUpperCase(keyChar));
				}
			}
		});
		txtGrpDE.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (!txtGrpDE.getText().equals("D")) {
					JOptionPane.showMessageDialog(null,
							"Enter a Valid State: D=Delaware ");
				} 
			}
		});
		txtGrpDE.setColumns(10);
		txtGrpDE.setBounds(225, 267, 40, 20);
		contentPane.add(txtGrpDE);
		txtGrpPA = new JTextField();
		txtGrpPA.setText(dbVar.getDbGroupStatePA());
		// Default to Upper Case...
		txtGrpPA.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (Character.isLowerCase(keyChar)) {
					e.setKeyChar(Character.toUpperCase(keyChar));
				}
			}
		});
		txtGrpPA.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (!txtGrpPA.getText().equals("P")) {
					JOptionPane.showMessageDialog(null,
							"Enter a Valid State: P=Pennsylvania ");
				} 
			}
		});
		txtGrpPA.setColumns(10);
		txtGrpPA.setBounds(295, 267, 40, 20);
		contentPane.add(txtGrpPA);

		txtGrpWV = new JTextField();
		txtGrpWV.setText(dbVar.getDbGroupStateWV());
		// Default to Upper Case...
		txtGrpWV.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (Character.isLowerCase(keyChar)) {
					e.setKeyChar(Character.toUpperCase(keyChar));
				}
			}
		});
		txtGrpWV.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (!txtGrpWV.getText().equals("W")) {
					JOptionPane.showMessageDialog(null,
							"Enter a Valid State: W=West Virginia ");
				} 
			}
		});
		txtGrpWV.setBounds(375, 267, 40, 20);
		contentPane.add(txtGrpWV);
		txtGrpWV.setColumns(10);

		txtGrpPart = new JTextField();
		txtGrpPart.setText(dbVar.getDbGroupPart());
		txtGrpPart.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (!txtGrpPart.getText().equals("Yes") && !txtGrpPart.getText().equals("No")) {
					JOptionPane.showMessageDialog(null,
							"Valid Entry. Enter: Yes or No ");
				} 
			}
		});
		txtGrpPart.setColumns(10);
		txtGrpPart.setBounds(150, 267, 40, 20);
		contentPane.add(txtGrpPart);

		txtGrpStatus = new JTextField();
		txtGrpStatus.setText(dbVar.getDbGroupStatus());
		txtGrpStatus.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (!txtGrpStatus.getText().equals("A") && !txtGrpStatus.getText().equals("I") && !txtGrpStatus.getText().equals("S")) {
					JOptionPane.showMessageDialog(null,
							"Valid Entry. A=Active, I=Inactive or S=Suspend");
				} 
			}
		});
		txtGrpStatus.setBounds(150, 290, 40, 20);
		contentPane.add(txtGrpStatus);
		txtGrpStatus.setColumns(10);

		btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtReserveGroup.getText().isEmpty()) {
					int msgValue = JOptionPane.showConfirmDialog(null,
							"You're about to create a new Member Group. \r\n You will have to attach the Member Company",
							"Group Management", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
					if (msgValue == JOptionPane.OK_OPTION) {
						InsertGroup();
					}
				} else {
					int msgValue = JOptionPane.showConfirmDialog(null,
							"Any changes will affect the Assigned Member Company", "Group Management",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
					if (msgValue == JOptionPane.OK_OPTION) {
						ProcessGroup();
						if (txtGrpCode.getText() != txtReserveGroup.getText()) {
							setCntChgVar(txtGrpCode.getText());
							ContactGroupChg();
						}
					}
				}
				dispose();
			}
		});
		btnEnter.setBounds(25, 340, 89, 23);
		contentPane.add(btnEnter);

		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int msgValue = JOptionPane.showConfirmDialog(null,
						"Delete will permanently remove a group and \r\n detach any Member Company", "Group Management",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
				if (msgValue == JOptionPane.OK_OPTION) {
					setCntChgVar(null);
					ContactGroupDlt();
				}
			}
		});
		btnDelete.setBounds(326, 340, 89, 23);
		contentPane.add(btnDelete);

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		btnCancel.setBounds(176, 340, 89, 23);
		contentPane.add(btnCancel);
	}

	public void ProcessGroup() {

		// Connect to database...
		connection = sqlConnection.dbConnector();

		String userName = System.getProperty("user.name");

		String sqlQuery = "Update MemberCompanyGroups Set "
				+ "NAICGroup=?,GroupCode=?,GroupName=?,Attention=?,GroupAddressOne=?"
				+ ",GroupAddressTwo=?,GroupCity=?,GroupState=?,GroupZipCode=?,DE=?,PA=?,WV=?,Participation=?"
				+ ",Status=? Where GroupCode=?";

		try {
			preStmt = connection.prepareStatement(sqlQuery);
			preStmt.setInt(1, Integer.parseInt((txtGrpNAIC.getText())));
			preStmt.setString(2, txtGrpCode.getText());
			preStmt.setString(3, txtGrpName.getText());
			preStmt.setString(4, txtGrpAttn.getText());
			preStmt.setString(5, txtGrpAddress1.getText());
			preStmt.setString(6, txtGrpAddress2.getText());
			preStmt.setString(7, txtGrpCity.getText());
			preStmt.setString(8, txtGrpState.getText());
			preStmt.setInt(9, Integer.parseInt((txtGrpZ‬.getText())));
			preStmt.setString(10, txtGrpDE.getText());
			preStmt.setString(11, txtGrpPA.getText());
			preStmt.setString(12, txtGrpWV.getText());
			preStmt.setString(13, txtGrpPart.getText());
			preStmt.setString(14, txtGrpStatus.getText());
			preStmt.setString(15, txtReserveGroup.getText());
			preStmt.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " Change Groups");
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

	public void ContactGroupChg() {

		// Connect to database...
		connection = sqlConnection.dbConnector();

		String userName = System.getProperty("user.name");

		String sqlQuery = "Update MemberCompanyContacts Set GroupCode=? Where GroupCode=?";
		try {
			preStmt = connection.prepareStatement(sqlQuery);
			preStmt.setString(1, txtGrpCode.getText());
			preStmt.setString(2, txtReserveGroup.getText());
			preStmt.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " Change Company");
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

	public void ContactGroupDlt() {

		// Connect to database...
		connection = sqlConnection.dbConnector();

		String userName = System.getProperty("user.name");

		String sqlQuery = "Delete From MemberCompanyGroups Where GroupCode=?";
		try {
			preStmt = connection.prepareStatement(sqlQuery);
			preStmt.setString(1, txtReserveGroup.getText());
			preStmt.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " Delete Groups");
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

	public void InsertGroup() {

		// Connect to database...
		connection = sqlConnection.dbConnector();

		String userName = System.getProperty("user.name");

		String sqlQuery = "Insert Into MemberCompanyGroups "
				+ "(NAICGroup,GroupCode,GroupName,Attention,GroupAddressOne"
				+ ",GroupAddressTwo,GroupCity,GroupState,GroupZipCode,DE,PA,WV,Participation,Status) "
				+ "Values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			preStmt = connection.prepareStatement(sqlQuery);
			preStmt.setInt(1, Integer.parseInt((txtGrpNAIC.getText())));
			preStmt.setString(2, txtGrpCode.getText());
			preStmt.setString(3, txtGrpName.getText());
			preStmt.setString(4, txtGrpAttn.getText());
			preStmt.setString(5, txtGrpAddress1.getText());
			preStmt.setString(6, txtGrpAddress2.getText());
			preStmt.setString(7, txtGrpCity.getText());
			preStmt.setString(8, txtGrpState.getText());
			preStmt.setInt(9, Integer.parseInt((txtGrpZ‬.getText())));
			preStmt.setString(10, txtGrpDE.getText());
			preStmt.setString(11, txtGrpPA.getText());
			preStmt.setString(12, txtGrpWV.getText());
			preStmt.setString(13, txtGrpPart.getText());
			preStmt.setString(14, txtGrpStatus.getText());
			preStmt.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			if (connection != null)
				try {
					connection.close();
					dispose();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e + " Insert Group" + userName);
				}
		}

	}

	public String getCntChgVar() {
		return cntChgVar;
	}

	public void setCntChgVar(String cntChgVar) {
		this.cntChgVar = cntChgVar;
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
			txtGrpState.setText(null);
			abbrvState = null;
			return;
		}
	}

}
