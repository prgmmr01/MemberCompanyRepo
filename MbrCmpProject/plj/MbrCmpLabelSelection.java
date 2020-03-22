package com.MbrCmpProject.plj;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.microsoft.sqlserver.jdbc.SQLServerPreparedStatement;

public class MbrCmpLabelSelection extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String sqlQuery,title;
	private boolean good;
	private JCheckBox chkDE,chkPA,chkWV;
	JPanel pnlStateGroup;

	ResultSet rstSet = null;
	PreparedStatement preStmt = null;
	Connection connection = null;
	String stCodeDE,stCodePA,stCodeWV;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MbrCmpLabelSelection frame = new MbrCmpLabelSelection();
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
	public MbrCmpLabelSelection() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMemberCompanyLabels = new JLabel("Member Company Labels");
		lblMemberCompanyLabels.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblMemberCompanyLabels.setBounds(90, 25, 231, 20);
		contentPane.add(lblMemberCompanyLabels);

		JLabel lblEnterState = new JLabel("Select a State");
		lblEnterState.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEnterState.setBounds(156, 75, 94, 20);
		contentPane.add(lblEnterState);

		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				good=false;
				getLabels();
			}
		});
		btnEnter.setBounds(22, 211, 89, 23);
		contentPane.add(btnEnter);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(318, 211, 89, 23);
		contentPane.add(btnCancel);
		
		pnlStateGroup = new JPanel();
		FlowLayout fl_pnlStateGroup = (FlowLayout) pnlStateGroup.getLayout();
		fl_pnlStateGroup.setAlignment(FlowLayout.LEFT);
		pnlStateGroup.setBounds(140, 106, 124, 99);
		contentPane.add(pnlStateGroup);
		
		chkDE = new JCheckBox("Delaware", false);
		chkDE.setActionCommand("DE");
		pnlStateGroup.add(chkDE);
		
		chkPA = new JCheckBox("Pennsylvania", false);
		chkPA.setActionCommand("PA");
		pnlStateGroup.add(chkPA);
		
		chkWV = new JCheckBox("West Virginia", false);
		chkWV.setActionCommand("WV");
		pnlStateGroup.add(chkWV);

	}

	protected void getLabels() {
		stCodeDE=null;
		stCodePA=null;
		stCodeWV=null;

		if (chkDE.isSelected()) {
			//sqlQuery = "Select Distinct * From LabelDataDE Order By LabelGroupCode,LabelNAIC";
			title = "DE_Labels";
			stCodeDE="D";		
			good=true;
		}
		if (chkPA.isSelected()) {
			//sqlQuery = "Select Distinct * From LabelDataPA Order By LabelGroupCode,LabelNAIC";
			title = "PA_Labels";
			stCodePA="P";
			good=true;
		}
		if (chkWV.isSelected()) {
			//sqlQuery = "Select Distinct * From LabelDataWV Order By LabelGroupCode,LabelNAIC";
			title = "WV_Labels";
			stCodeWV="W";
			good=true;
		}

		if (good) {
			generateLabels("Exec Member_Company_Label_Source ?,?,?",stCodeDE,stCodePA,stCodeWV);

			//WordLabelsGenerator labelGenerator = new WordLabelsGenerator(sqlQuery,title);
		};
	chkDE.setContentAreaFilled(false);
	chkPA.setContentAreaFilled(false);
	chkWV.setContentAreaFilled(false);
	}
	private void generateLabels(String sqlScript, String CodeDE, String CodePA, String CodeWV) {
		// Connect to database...
		connection = sqlConnection.dbConnector();

		// Getting Started with the SQL Server JDBC...
		SQLServerPreparedStatement preStmt;
		try {
			preStmt = (SQLServerPreparedStatement) connection
					.prepareStatement(sqlScript);
			preStmt.setString(1, CodeDE);
			preStmt.setString(2, CodePA);
			preStmt.setString(3, CodeWV);
			preStmt.execute();
			OpenMailMerge();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
 public void OpenMailMerge() {
	 String label=null;
			label = "K:\\Member Company\\Member Company Mail Merge Documents\\MergeTemplates\\MbrCmp5163Labels.docx";
	 
		try {
			Desktop.getDesktop().open(new File(label));
			// Close frames until Main Window
			Window opnWdw[] = getWindows();
			for (int w = 1; w < opnWdw.length - 1; w++) {
				opnWdw[w].dispose();
				opnWdw[w].setVisible(false);
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1);
		}
 }
}
