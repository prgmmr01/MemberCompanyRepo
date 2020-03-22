package com.MbrCmpProject.plj;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MbrCmpDateRanges extends JFrame {

	Connection connection = null;
	CallableStatement call_stmt = null;
	ResultSet rstSet = null;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtTimeEnd, txtTimeDE, txtTimePA, txtTimeWV;
	private String rangeTime, rangeDate, sqlQuery,sqlQuery2,title;
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MbrCmpDateRanges frame = new MbrCmpDateRanges();
					frame.setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public MbrCmpDateRanges() {}

	/**
	 * Create the frame.
	 */
	public MbrCmpDateRanges(DatabaseVariables dbRangeVar) {
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
		lblMemberCompanies.setBounds(220, 60, 395, 30);
		getContentPane().add(lblMemberCompanies);

		JRootPane rootPane = contentPane.getRootPane();
		// Start Enter Button...
		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CheckRangeErrors(dbRangeVar);
				if (dbRangeVar.getGoodToGo()) {
					RangeParameters(dbRangeVar);
				}
			}
		});

		btnEnter.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnEnter.setBounds(40, 580, 115, 30);
		getContentPane().add(btnEnter);
		rootPane.setDefaultButton(btnEnter);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			// Return to Report Selection...
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnCancel.setBounds(685, 580, 115, 30);
		getContentPane().add(btnCancel);

		JLabel lblEndYear = new JLabel("End Year & Time:");
		lblEndYear.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblEndYear.setBounds(190, 225, 180, 25);
		contentPane.add(lblEndYear);

		JLabel lblStartYear = new JLabel("Start Year");
		lblStartYear.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblStartYear.setBounds(370, 290, 108, 25);
		contentPane.add(lblStartYear);

		JSeparator sep1 = new JSeparator();
		sep1.setForeground(Color.BLACK);
		sep1.setBackground(Color.BLACK);
		sep1.setBounds(370, 315, 100, 2);
		contentPane.add(sep1);

		JLabel lblDelaware = new JLabel("Delaware:");
		lblDelaware.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblDelaware.setBounds(185, 335, 110, 25);
		contentPane.add(lblDelaware);

		JLabel lblPennsylvania = new JLabel("Pennsylvania:");
		lblPennsylvania.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblPennsylvania.setBounds(185, 390, 145, 25);
		contentPane.add(lblPennsylvania);

		JLabel lblWestVirginia = new JLabel("West Virginia:");
		lblWestVirginia.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblWestVirginia.setBounds(185, 445, 150, 25);
		contentPane.add(lblWestVirginia);

		txtTimeDE = new JTextField();
		txtTimeDE.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTimeDE.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtTimeDE.setBounds(380, 335, 75, 25);
		contentPane.add(txtTimeDE);
		txtTimeDE.setColumns(10);

		txtTimePA = new JTextField();
		txtTimePA.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTimePA.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtTimePA.setColumns(10);
		txtTimePA.setBounds(380, 390, 75, 25);
		contentPane.add(txtTimePA);

		txtTimeWV = new JTextField();
		txtTimeWV.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTimeWV.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtTimeWV.setColumns(10);
		txtTimeWV.setBounds(380, 445, 75, 25);
		contentPane.add(txtTimeWV);

		txtTimeEnd = new JTextField();
		txtTimeEnd.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTimeEnd.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtTimeEnd.setColumns(10);
		txtTimeEnd.setBounds(380, 225, 75, 25);
		rangeDate = new SimpleDateFormat("YYYY").format(Calendar.getInstance().getTime());
		txtTimeEnd.setText(rangeDate);
		contentPane.add(txtTimeEnd);

		JLabel lblRangeTime = new JLabel("");
		lblRangeTime.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblRangeTime.setBounds(475, 225, 100, 25);
		rangeTime = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
		lblRangeTime.setText(rangeTime);
		contentPane.add(lblRangeTime);

	}

	public void CheckRangeErrors(DatabaseVariables dbRangeVar) {
		if (txtTimeEnd.getText().isEmpty() || txtTimeDE.getText().isEmpty() || txtTimePA.getText().isEmpty()
				|| txtTimeWV.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "All Start Years Must be Entered");
			dbRangeVar.setGoodToGo(false);
		} else {
			if (dbRangeVar.getRptNumber() == 3) {
				if (dbRangeVar.getGoodToGo()) {
					dbRangeVar.setRangeYearEnd(Integer.parseInt(txtTimeEnd.getText()));
					dbRangeVar.setRangeYearDE(Integer.parseInt(txtTimeDE.getText()));
					dbRangeVar.setRangeYearPA(Integer.parseInt(txtTimePA.getText()));
					dbRangeVar.setRangeYearWV(Integer.parseInt(txtTimeWV.getText()));
				}
			}

		}

	}

	public void RangeParameters(DatabaseVariables dbRangeVar) {
		if (dbRangeVar.getRptNumber() == 3) {
			// Call procedure to create SQL table...
			dbRangeVar.setProc("{ call Member_Company_Participation_Premium10Yr_Listing_Data_v2(?,?,?,?) }");
			GetData2(dbRangeVar); //Create tables...Table Particiption10YrListingDataDE
											//,Table Particiption10YrListingDataPA
											//,Table Particiption10YrListingDataWV
			//Print reports...
			//POI - Generate Excel Assess Spreadsheet...
			
			//Delaware...
			sqlQuery = "Select NAIC,CompanyName,Amount,Factors,Replace(Str(TheYear),\'9999\',\' \') TheYear From FP_Member_Companies..MbrTenYearDE";
			sqlQuery2 = "Select Count(NAIC) NAIC,\'Totals-->\' CompanyName,Sum(Amount) Amount,Sum(Factors) Factor,TheYear From FP_Member_Companies..MbrTenYearDE"
			+ " Where TheYear != 9999 Group By TheYear Order By TheYear";
			title = "Ten Year Listing DE";
			ExcelGeneratorClass excelGeneratorDE = new ExcelGeneratorClass(sqlQuery, sqlQuery2,title);
			excelGeneratorDE.dispose();
			//Delaware...
			sqlQuery = "Select NAIC,CompanyName,Amount,Factors,Replace(Str(TheYear),\'9999\',\' \') TheYear From FP_Member_Companies..MbrTenYearPA";
			sqlQuery2 = "Select Count(NAIC) NAIC,\'Totals-->\' CompanyName,Sum(Amount) Amount,Sum(Factors) Factor,TheYear From FP_Member_Companies..MbrTenYearPA"
			+ " Where TheYear != 9999 Group By TheYear Order By TheYear";
			title = "Ten Year Listing PA";
			ExcelGeneratorClass excelGeneratorPA = new ExcelGeneratorClass(sqlQuery, sqlQuery2,title);
			excelGeneratorPA.dispose();
			//Delaware...
			sqlQuery = "Select NAIC,CompanyName,Amount,Factors,Replace(Str(TheYear),\'9999\',\' \') TheYear From FP_Member_Companies..MbrTenYearWV";
			sqlQuery2 = "Select Count(NAIC) NAIC,\'Totals-->\' CompanyName,Sum(Amount) Amount,Sum(Factors) Factor,TheYear From FP_Member_Companies..MbrTenYearWV"
			+ " Where TheYear != 9999 Group By TheYear Order By TheYear";
			title = "Ten Year Listing WV";
			ExcelGeneratorClass excelGeneratorWV = new ExcelGeneratorClass(sqlQuery, sqlQuery2,title);
			excelGeneratorWV.dispose();
		}
	}

	public void GetData2(DatabaseVariables dbRangeVar) {
		// run query to create the data need for the report...
		// ***prepare data for the report...
		try {
			// Connect to database...
			connection = sqlConnection.dbConnector();
			call_stmt = connection.prepareCall(dbRangeVar.getProc());
			call_stmt.setInt(1, Integer.parseInt(txtTimeDE.getText()));
			call_stmt.setInt(2, Integer.parseInt(txtTimePA.getText()));
			call_stmt.setInt(3, Integer.parseInt(txtTimeWV.getText()));
			call_stmt.setInt(4, Integer.parseInt(txtTimeEnd.getText()));
			call_stmt.execute();
			dbRangeVar.setGoodToGo(true);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
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
			if (call_stmt != null) {
				try {
					call_stmt.close();
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

	public void GetDocument(String macroName) {
		// ***run the report...
		try {
			// Open document...
			Desktop.getDesktop().open(new File(macroName));
		} catch (IOException io) {
			JOptionPane.showMessageDialog(null, io);
		}
	}

}