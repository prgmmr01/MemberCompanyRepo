package com.MbrCmpProject.plj;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class MbrCmpComments extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int cmpNAIC;
	private JPanel contentPane;
	private JTextArea commentArea;
	private JScrollPane commentScroll;
	private String strComments;
	ResultSet rstSet=null;
	PreparedStatement preStmt=null;
	Connection connection=null;	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MbrCmpComments frame = new MbrCmpComments(cmpNAIC);
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
	public MbrCmpComments(int cmpNAIC) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 740);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblInsurancePlacementFacility = new JLabel("Insurance Placement Facility of Pennsylvania");
		lblInsurancePlacementFacility.setFont(new Font("Times New Roman", Font.BOLD, 28));
		lblInsurancePlacementFacility.setBounds(142, 10, 562, 42);
		contentPane.add(lblInsurancePlacementFacility);

		JLabel lblMemberCompanies = new JLabel("Member Company File Maintenance");
		lblMemberCompanies.setFont(new Font("Times New Roman", Font.BOLD, 24));
		lblMemberCompanies.setBounds(229, 68, 387, 29);
		contentPane.add(lblMemberCompanies);

		// Start Enter Button...
		JRootPane rootPane = contentPane.getRootPane();
		JButton btnCmtEnter = new JButton("Enter");
		btnCmtEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ProcessComments();
				dispose();
			}
		});
		btnCmtEnter.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnCmtEnter.setBounds(70, 550, 115, 30);
		contentPane.add(btnCmtEnter);
		rootPane.setDefaultButton(btnCmtEnter);
		// End Enter Button...

		JButton btnCmtCancel = new JButton("Cancel");
		btnCmtCancel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnCmtCancel.setBounds(640, 550, 115, 30);
		btnCmtCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		contentPane.add(btnCmtCancel);

		commentScroll = new JScrollPane(commentArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		commentScroll.setBounds(35, 155, 750, 360);
		contentPane.add(commentScroll, BorderLayout.CENTER);
		LoadComments(cmpNAIC);
		commentArea = new JTextArea(this.getStrComments());
		commentArea.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		commentArea.setLineWrap(true);
		commentArea.setBounds(10, 10, 30, 30);
		commentArea.setColumns(512);
		commentScroll.setViewportView(commentArea);

	}
	protected void ProcessComments() {
		// Connect to database...
		connection = sqlConnection.dbConnector();

		String sqlQuery = "Update MemberComments Set "
				+ "Comments=?";
		try {
			preStmt = connection.prepareStatement(sqlQuery);
			preStmt.setString(1, commentArea.getText()); // Comments
			preStmt.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e);
				}
		}

	}

	// Retrieve database values...
	public void LoadComments(int qryNAIC) {

		// Connect to database...
		connection=sqlConnection.dbConnector();

		// Query based on options select from MbrCompanyMain menu...
		try
		{
			String sqlQuery = "Select * From MemberComments "
					+ "Where NAIC=?";
				preStmt=connection.prepareStatement(sqlQuery);
				preStmt.setInt(1,qryNAIC);
				rstSet = preStmt.executeQuery();
		        while (rstSet.next()) {
		        	this.setStrComments(rstSet.getString("Comments"));
		        }
		} catch(Exception expAdd) {JOptionPane.showMessageDialog(null, expAdd); 
		// Final steps to close out Queries...
		} finally {
			//Close the Result Set...
			if (rstSet!=null) {
				try { rstSet.close(); } catch(Exception expClose) {
					JOptionPane.showMessageDialog(null, expClose); }
				} 
			//Close the Prepared Statement...
			if (preStmt!=null) {
				try { preStmt.close(); } catch(Exception expClose) {
					JOptionPane.showMessageDialog(null, expClose); }
				}
			//Close the Connection...
			if (connection!=null) {
			try { connection.close(); } catch(Exception expClose) {
					JOptionPane.showMessageDialog(null, expClose); }
				}
			
		}
	}
	public String getStrComments() {
		return strComments;
	}

	public void setStrComments(String strComments) {
		this.strComments = strComments;
	}

}
