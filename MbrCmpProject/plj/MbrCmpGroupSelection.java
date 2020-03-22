package com.MbrCmpProject.plj;

/*
Author:		Peter Jarvis
Date:		06012018
Purpose:	retrieve multiple Member Company records and load it into database 
			table to be displayed in the MbrCompanyDisplay class... 
*/
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class MbrCmpGroupSelection extends JFrame {
	int rowNumber;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable groupTable;
	MbrCmpGroupChg groupChg;
	public DatabaseVariables dbVar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MbrCmpGroupSelection frame = new MbrCmpGroupSelection();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MbrCmpGroupSelection() {
	}

	/**
	 * Create the frame.
	 */
	public MbrCmpGroupSelection(DefaultTableModel dbModel) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1263, 726);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(groupTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(33, 28, 1180, 555);
		contentPane.add(scrollPane);

		groupTable = new JTable();
		scrollPane.setViewportView(groupTable);

		groupTable.setModel(dbModel);
		groupTable.setRowHeight(20);
		groupTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		MouseHandlerClass squeak = new MouseHandlerClass();
		groupTable.addMouseListener(squeak);
		groupTable.addMouseMotionListener(squeak);

		JButton btnReturn = new JButton("Return");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnReturn.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnReturn.setBounds(1095, 615, 115, 30);
		contentPane.add(btnReturn);

	}

	private class MouseHandlerClass implements MouseListener, MouseMotionListener {
		
		public void mouseClicked(MouseEvent sqkEvt) {
			rowNumber = groupTable.getSelectedRow();
			getGroupCode(rowNumber);
		}

		public void mousePressed(MouseEvent sqkEvt) {
			//rowNumber = groupTable.getSelectedRow();
			//openApp=true;
		}

		public void mouseReleased(MouseEvent sqkEvt) {
			// getNAICNumber(rowNumber,columnNumber);
		}

		public void mouseEntered(MouseEvent sqkEvt) {
			// getNAICNumber(rowNumber,columnNumber);
		}

		public void mouseExited(MouseEvent sqkEvt) {
			// getNAICNumber(rowNumber,columnNumber);
		}

		public void mouseDragged(MouseEvent sqkEvt) {
			// getNAICNumber(rowNumber,columnNumber);
		}

		public void mouseMoved(MouseEvent sqkEvt) {
			// getNAICNumber(rowNumber,columnNumber);
		}

	}

	public void getGroupCode(int rowNumber) {
		dbVar = new DatabaseVariables();
		Object selValue = groupTable.getValueAt(rowNumber, 0);
		String strValue = selValue.toString();
		dbVar.setDbGrpNAIC(Integer.parseInt(strValue));
		dbVar.setDbGroupName(String.valueOf(groupTable.getValueAt(rowNumber, 1)));
		dbVar.setDbGroupCode(String.valueOf(groupTable.getValueAt(rowNumber, 2)));
		dbVar.setRsvGrpCode(String.valueOf(groupTable.getValueAt(rowNumber, 2)));
		dbVar.setDbGroupAttn(String.valueOf(groupTable.getValueAt(rowNumber, 4)));
		dbVar.setDbGroupAddressOne(String.valueOf(groupTable.getValueAt(rowNumber, 5)));
		dbVar.setDbGroupAddressTwo(String.valueOf(groupTable.getValueAt(rowNumber, 6)));
		dbVar.setDbGroupCity(String.valueOf(groupTable.getValueAt(rowNumber, 7)));
		dbVar.setDbGroupState(String.valueOf(groupTable.getValueAt(rowNumber, 8)));
		selValue = groupTable.getValueAt(rowNumber, 9);
		strValue = selValue.toString();
		dbVar.setDbGroupZipCode(Integer.parseInt(strValue));
		dbVar.setDbGroupStateDE(String.valueOf(groupTable.getValueAt(rowNumber, 10)));
		dbVar.setDbGroupStatePA(String.valueOf(groupTable.getValueAt(rowNumber, 11)));
		dbVar.setDbGroupStateWV(String.valueOf(groupTable.getValueAt(rowNumber, 12)));
		dbVar.setDbGroupPart(String.valueOf(groupTable.getValueAt(rowNumber, 13)));
		dbVar.setDbGroupStatus(String.valueOf(groupTable.getValueAt(rowNumber, 14)));
		dbVar.setDbGroupComments(String.valueOf(groupTable.getValueAt(rowNumber, 15)));

		groupChg = new MbrCmpGroupChg(dbVar);
		groupChg.setVisible(true);

	}

}
