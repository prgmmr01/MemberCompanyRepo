package com.MbrCmpProject.plj;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class WordLabelsGenerator extends JFrame {

	private static final long serialVersionUID = 1L;

	ResultSet rstSet = null;
	PreparedStatement preStmt = null;
	Connection connection = null;
	DatabaseVariables dbVar = new DatabaseVariables();
	int i = 0;
	JSONArray mdList;
	JSONObject jsObj;
	ArrayList<String> columns = new ArrayList<String>();
	ArrayList<Integer> types = new ArrayList<Integer>();
	int sqlType;
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
	Date sqlDate;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WordLabelsGenerator frame = new WordLabelsGenerator();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public WordLabelsGenerator() {
	}

	public WordLabelsGenerator(String sqlQuery, String title) {

		// String wordPath = NewExcelFileLocation(title);
		String wordPath = "K:\\Member Company\\Member Company Mail Merge Documents\\MergedDocuments";
		// String wordPath = "C:\\Jarvis\\Member Company Conversion\\Member Company Mail
		// Merge Documents\\MergedDocuments";

		// Connect to database...
		connection = sqlConnection.dbConnector();

		// Blank Document
		XWPFDocument wordDocument = new XWPFDocument();
		XWPFTable wordTable = null;

		try {
			preStmt = connection.prepareStatement(sqlQuery);
			rstSet = preStmt.executeQuery();
			if (!rstSet.isBeforeFirst()) {
				JOptionPane.showMessageDialog(null, "No Labels were Processed");
				dbVar.setGoodToGo(false);
			} else {
				dbVar.setGoodToGo(true);

				wordTable = wordDocument.createTable();
				// first row...
				XWPFTableRow tableFirtstRow = wordTable.getRow(0);
				tableFirtstRow.getCell(0).setText("NAIC");
				tableFirtstRow.addNewTableCell().setText("Company Name");
				tableFirtstRow.addNewTableCell().setText("Check Amount");
				tableFirtstRow.addNewTableCell().setText("Check Date");

				// load data values...
				while (rstSet.next()) {
					/*
					 * i++; String strValue = rstSet.getString("CompanyName"); int intValue =
					 * rstSet.getInt("NAIC"); double dblValue = rstSet.getDouble("CheckAmount");
					 * String strDate = rstSet.getString("CheckDate");
					 * 
					 * XWPFTableRow tableSecondRow = wordTable.createRow();
					 * tableSecondRow.getCell(0).setText(String.valueOf(intValue));
					 * tableSecondRow.getCell(1).setText(strValue);
					 * tableSecondRow.getCell(2).setText(String.valueOf(dblValue));
					 * tableSecondRow.getCell(3).setText(strDate);
					 */

				}

				FileOutputStream out = new FileOutputStream(new File(wordPath + "\\" + title + " Word Labels.docx"));
				wordDocument.write(out);
				out.close();
				JOptionPane.showMessageDialog(null, title + " generated successfully");

			}
		} catch (Exception expAdd) {
			JOptionPane.showMessageDialog(null, expAdd);
			// Final steps to close out Queries...
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e);
				}
		}

	}

	public static String NewLabelFileLocation(String title) {
		// setup open dialog to assign the directory location to save the Excel file...
		File f = null;
		String labelPathRtn = null;
		JFileChooser NewLabelChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		NewLabelChooser.setDialogTitle("Member Company Labels " + title);
		NewLabelChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		NewLabelChooser.setAcceptAllFileFilterUsed(false);
		int returnValue = NewLabelChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			f = NewLabelChooser.getSelectedFile();
			labelPathRtn = f.getAbsolutePath();
		}
		return labelPathRtn;
	}
}
