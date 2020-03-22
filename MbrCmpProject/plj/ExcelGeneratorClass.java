package com.MbrCmpProject.plj;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ExcelGeneratorClass extends JFrame {

	private static final long serialVersionUID = 1L;
	private int totalBillsCount;
	private float totalBillsAmount;
	// private JFrame frame;
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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExcelGeneratorClass frame = new ExcelGeneratorClass(null,null,null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ExcelGeneratorClass(String sqlQuery, String sqlQuery2, String title) {

		
		//String excelPath =  NewExcelFileLocation(title);
		String excelPath = "K:\\Member Company\\Member Company Excel Reports\\";
		//String excelPath = "C:\\Jarvis\\Member Company Conversion\\Member Company Excel Reports";

		// Connect to database...
		connection = sqlConnection.dbConnector();

		// setup Excel workbook and sheet
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet spreadsheet = workbook.createSheet(title);
		Iterator<Row> rowIterator = spreadsheet.iterator();
		XSSFDataFormat format = workbook.createDataFormat();

		try {
			preStmt = connection.prepareStatement(sqlQuery);
			rstSet = preStmt.executeQuery();
			ResultSetMetaData rstMetaData = rstSet.getMetaData();
			for (int k = 1; k < rstMetaData.getColumnCount(); k++) {
				columns.add(rstMetaData.getColumnName(k));
				types.add(rstMetaData.getColumnType(k));
			}
			if (!rstSet.isBeforeFirst()) {
				JOptionPane.showMessageDialog(null, sqlQuery+" Member Does Not Exist");
				dbVar.setGoodToGo(false);
			} else {
				// Create JSon file of meta data...
				try (FileWriter jsFile = new FileWriter(excelPath + "jsMetaData.json")) {
					// FileWriter jsFile = new FileWriter("jsMetaData.json");
					for (int j = 1; j <= rstMetaData.getColumnCount(); j++) {
						jsObj = new JSONObject();
						mdList = new JSONArray();
						mdList.add(j);
						mdList.add(rstMetaData.getColumnName(j));
						mdList.add(rstMetaData.getColumnType(j));
						mdList.add(rstMetaData.getTableName(j));
						mdList.add(rstMetaData.getColumnDisplaySize(j));

						jsObj.put("metaData", mdList);
						jsFile.write(jsObj.toString());
						jsFile.flush();
					}
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e);
				}

				dbVar.setGoodToGo(true);
				XSSFRow row = spreadsheet.createRow(i);
				XSSFCell cell;
				XSSFCellStyle decStyle = workbook.createCellStyle();
				decStyle.setDataFormat(format.getFormat("#0.00###"));
				XSSFCellStyle dateStyle = workbook.createCellStyle();
				dateStyle.setDataFormat(format.getFormat("MM/dd/yyyy"));
				// load headers...
				for (int j = 1; j <= rstMetaData.getColumnCount(); j++) {
					cell = row.createCell(j - 1);
					cell.setCellValue(rstMetaData.getColumnName(j));
				}
				// load data values...
				while (rstSet.next()) {
					i++;
					row = spreadsheet.createRow(i);
					for (int j = 1; j <= rstMetaData.getColumnCount(); j++) {
						sqlType = rstMetaData.getColumnType(j);
						cell = row.createCell(j - 1);
						switch (sqlType) {
						case Types.FLOAT:
						case Types.DOUBLE:
						case Types.DECIMAL:
						case Types.NUMERIC:
							//cell.setCellStyle(decStyle);
							cell.setCellValue(rstSet.getDouble(j));
							break;
						case Types.CHAR:
						case Types.VARCHAR:
							cell.setCellValue(rstSet.getString(j));
							break;
						case Types.REAL:
						case Types.LONGVARBINARY:
						case Types.BIGINT:
						case Types.SMALLINT:
						case Types.TINYINT:
						case Types.INTEGER:
							cell.setCellValue(rstSet.getInt(j));
							break;
						case Types.DATE:
						case Types.TIMESTAMP:
							cell.setCellValue(rstSet.getDate(j));
							break;
						default:
							cell.setCellValue(rstSet.getObject(j).toString());
						}
					}
				}
				JOptionPane.showMessageDialog(null, title + " generated successfully");

				// this will save single sheet only
				if (sqlQuery2 == null) {
					
					FileOutputStream out = new FileOutputStream(new File(excelPath + title + " Excel database.xlsx"));
					workbook.write(out);
					out.close();

				}
			
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

		// Second Sheet...
		if (sqlQuery2 != null) {
			// Connect to database...
			i = 0;
			connection = sqlConnection.dbConnector();

			XSSFSheet spreadsheet2 = workbook.createSheet(title + "_Totals");
			Iterator<Row> rowIterator2 = spreadsheet.iterator();

			try {
				preStmt = connection.prepareStatement(sqlQuery2);
				rstSet = preStmt.executeQuery();
				ResultSetMetaData rstMetaData = rstSet.getMetaData();
				for (int k = 1; k < rstMetaData.getColumnCount(); k++) {
					columns.add(rstMetaData.getColumnName(k));
					types.add(rstMetaData.getColumnType(k));
				}
				if (!rstSet.isBeforeFirst()) {
					JOptionPane.showMessageDialog(null, "Member Does Not Exist");
					dbVar.setGoodToGo(false);
				} else {

					dbVar.setGoodToGo(true);
					XSSFRow row = spreadsheet2.createRow(i);
					XSSFCell cell;
					// load headers...
					for (int j = 1; j <= rstMetaData.getColumnCount(); j++) {
						cell = row.createCell(j - 1);
						cell.setCellValue(rstMetaData.getColumnName(j));
					}
					// load data values...
					while (rstSet.next()) {
						i++;
						row = spreadsheet2.createRow(i);
						for (int j = 1; j <= rstMetaData.getColumnCount(); j++) {
							sqlType = rstMetaData.getColumnType(j);
							cell = row.createCell(j - 1);
							switch (sqlType) {
							case Types.FLOAT:
							case Types.DOUBLE:
							case Types.DECIMAL:
								cell.setCellValue(rstSet.getDouble(j));
								// cell.setCellStyle(style);
								break;
							case Types.CHAR:
							case Types.VARCHAR:
								cell.setCellValue(rstSet.getString(j));
								break;
							case Types.REAL:
							case Types.NUMERIC:
							case Types.LONGVARBINARY:
							case Types.BIGINT:
							case Types.SMALLINT:
							case Types.TINYINT:
							case Types.INTEGER:
								cell.setCellValue(rstSet.getInt(j));
								break;
							case Types.DATE:
							case Types.TIMESTAMP:
								cell.setCellValue(rstSet.getDate(j));
								break;
							default:
								cell.setCellValue(rstSet.getObject(j).toString());
							}
						}
					}

					FileOutputStream out = new FileOutputStream(new File(excelPath + title + " Excel database.xlsx"));
					workbook.write(out);
					out.close();
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

	}
	
	public static String NewExcelFileLocation(String title) {
		// setup open dialog to assign the directory location to save the Excel file...
		File f = null;
		String excelPathRtn = null;
		JFileChooser NewExcelFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		NewExcelFileChooser.setDialogTitle("Member Company Excel File "+title);
		NewExcelFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		NewExcelFileChooser.setAcceptAllFileFilterUsed(false);
		NewExcelFileChooser.setCurrentDirectory(new File("K:\\Member Company\\Member Company Mail Merge Documents\\MergedDocuments"));
		//NewExcelFileChooser.setCurrentDirectory(new File("C:\\Jarvis\\Member Company Conversion\\Member Company Mail Merge Documents\\MergedDocuments"));
		int returnValue = NewExcelFileChooser.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			f = NewExcelFileChooser.getSelectedFile();
			excelPathRtn = f.getAbsolutePath();
			}
		return excelPathRtn;
	}
}
