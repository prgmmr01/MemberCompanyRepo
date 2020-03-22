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

public class MbrCompanySelection extends JFrame {
	int rowNumber;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable memberTable;
	MbrCompanyMain mainValue;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MbrCompanySelection frame = new MbrCompanySelection();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MbrCompanySelection() {}

	/**
	 * Create the frame.
	 */
	public MbrCompanySelection(DefaultTableModel dbModel) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1263, 726);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(memberTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(33, 28, 1180, 555);
		contentPane.add(scrollPane);

		memberTable = new JTable();
		scrollPane.setViewportView(memberTable);
	
		memberTable.setModel(dbModel);
		memberTable.setRowHeight(20);
		memberTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		MouseHandlerClass squeak = new MouseHandlerClass();
		memberTable.addMouseListener(squeak);
		memberTable.addMouseMotionListener(squeak);

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
			rowNumber = memberTable.getSelectedRow();
			getNAICNumber(rowNumber);
		}

		public void mousePressed(MouseEvent sqkEvt) {
			rowNumber = memberTable.getSelectedRow();
			getNAICNumber(rowNumber);
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

		public void getNAICNumber(int rowNumber) {
				Object selValue = (memberTable.getValueAt(rowNumber, 0));
				mainValue = new MbrCompanyMain();
				mainValue.setMbrValue(selValue);
		}
	}

}
