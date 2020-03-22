package com.MbrCmpProject.plj;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class MbrCmpPercentage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable deFactors;			//Delaware Table...
	private JTable paFactors;			//Pennsylvania Table...
	private JTable wvFactors;			//West Virginia Table...
	private JScrollPane deScrollPane;	//scroll through display...
	private JScrollPane paScrollPane;	//scroll through display...
	private JScrollPane wvScrollPane;	//scroll through display...
	private String qryStateCode;		//State code variable...
	
	//Database components...
	DefaultTableModel deTableModel=new DefaultTableModel();
	DefaultTableModel paTableModel=new DefaultTableModel();
	DefaultTableModel wvTableModel=new DefaultTableModel();
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
					MbrCmpPercentage frame = new MbrCmpPercentage(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MbrCmpPercentage(DatabaseVariables pctVar) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1285, 725);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		

		//Delaware Factors...
		deScrollPane = new JScrollPane(deFactors, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		deScrollPane.setBounds(33, 28, 1177, 125);
		contentPane.add(deScrollPane);
		qryStateCode="D";
		LoadFactors(deTableModel,pctVar.getDbNAIC(),qryStateCode);
		deFactors = new JTable();
		deFactors.setModel(deTableModel);
		deScrollPane.setViewportView(deFactors);
		deFactors.setRowHeight(24);
		deFactors.setEnabled(false);
		deFactors.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int	selRow = deFactors.rowAtPoint(e.getPoint());
				pctVar.setDbPremiumSequence(Integer.parseInt(deFactors.getValueAt(selRow, 1).toString()));
				pctVar.setDbStateCode(deFactors.getValueAt(selRow, 3).toString());
				pctVar.setDbPremiumYear(Integer.parseInt(deFactors.getValueAt(selRow, 4).toString()));
				pctVar.setDbPremiumAmount(Float.parseFloat(deFactors.getValueAt(selRow, 5).toString()));
				pctVar.setDbPremiumPercentage(Float.parseFloat(deFactors.getValueAt(selRow, 6).toString()));
				MbrCmpJDialogFct deJDialog = new MbrCmpJDialogFct(MbrCmpPercentage.this,pctVar);
				deJDialog.setVisible(true);
			}
		});

		//Pennsylvania Factors...
		paScrollPane = new JScrollPane(paFactors, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		paScrollPane.setBounds(33, 228, 1177, 125);
		contentPane.add(paScrollPane);
  		qryStateCode="P";
		LoadFactors(paTableModel,pctVar.getDbNAIC(),qryStateCode);
		paFactors = new JTable();
		paFactors.setModel(paTableModel);
		paScrollPane.setViewportView(paFactors);
		paFactors.setRowHeight(24);
		paFactors.setEnabled(false);
		paFactors.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int	selRow = paFactors.rowAtPoint(e.getPoint());
				pctVar.setDbPremiumSequence(Integer.parseInt(paFactors.getValueAt(selRow, 1).toString()));
				pctVar.setDbStateCode(paFactors.getValueAt(selRow, 3).toString());
				pctVar.setDbPremiumYear(Integer.parseInt(paFactors.getValueAt(selRow, 4).toString()));
				pctVar.setDbPremiumAmount(Float.parseFloat(paFactors.getValueAt(selRow, 5).toString()));
				pctVar.setDbPremiumPercentage(Float.parseFloat(paFactors.getValueAt(selRow, 6).toString()));
				MbrCmpJDialogFct deJDialog = new MbrCmpJDialogFct(MbrCmpPercentage.this,pctVar);
				deJDialog.setVisible(true);
			}
		});

		//West Virginia Factors...
		wvScrollPane = new JScrollPane(wvFactors, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		wvScrollPane.setBounds(33, 428, 1177, 125);
		contentPane.add(wvScrollPane);
		qryStateCode="W";
		LoadFactors(wvTableModel,pctVar.getDbNAIC(),qryStateCode);
		wvFactors = new JTable();
		wvFactors.setModel(wvTableModel);
		wvScrollPane.setViewportView(wvFactors);
		wvFactors.setRowHeight(24);
		wvFactors.setEnabled(false);
		wvFactors.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int	selRow = wvFactors.rowAtPoint(e.getPoint());
				pctVar.setDbPremiumSequence(Integer.parseInt(wvFactors.getValueAt(selRow, 1).toString()));
				pctVar.setDbStateCode(wvFactors.getValueAt(selRow, 3).toString());
				pctVar.setDbPremiumYear(Integer.parseInt(wvFactors.getValueAt(selRow, 4).toString()));
				pctVar.setDbPremiumAmount(Float.parseFloat(wvFactors.getValueAt(selRow, 5).toString()));
				pctVar.setDbPremiumPercentage(Float.parseFloat(wvFactors.getValueAt(selRow, 6).toString()));
				MbrCmpJDialogFct deJDialog = new MbrCmpJDialogFct(MbrCmpPercentage.this,pctVar);
				deJDialog.setVisible(true);
			}
		});
		
		JButton btnReturn = new JButton("Return");
		btnReturn.addActionListener(new ActionListener() { // Open Enter Key Listener...
			// Process Main Menu Selection...
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		}); // Close Enter Key Listener...
		btnReturn.setBounds(1095, 613, 115, 29);
		contentPane.add(btnReturn);
		
	}

	// Retrieve database values...
	public void LoadFactors(DefaultTableModel factorModel, int qryNAIC, String qryStateCode) {

		// Connect to database...
		connection=sqlConnection.dbConnector();

		// Query based on options select from MbrCompanyMain menu...
		try
		{
			String sqlQuery = "Select * From MemberPremiums "
					+ "Where NAIC=? and StateCode=?";
				preStmt=connection.prepareStatement(sqlQuery);
				preStmt.setInt(1,qryNAIC);
				preStmt.setString(2,qryStateCode);
				rstSet = preStmt.executeQuery();
				//Setup column...
				ResultSetMetaData rstMeta = rstSet.getMetaData();
			    int columnCount = rstMeta.getColumnCount();
			    //retrieve column names...
			    String[] cols = new String[columnCount];
	            for(int i=0;i<columnCount;i++){
	                cols[i]=rstMeta.getColumnName(i+1);
	                factorModel.addColumn(cols[i]);
	            }
	            //read result set...load tale with result set
				Object row[]=new Object[columnCount];
		        while (rstSet.next()) {
		            for(int i=0;i<columnCount;i++){
	                    row[i]=rstSet.getString(i+1);
	                }
		            factorModel.addRow(row);
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

}