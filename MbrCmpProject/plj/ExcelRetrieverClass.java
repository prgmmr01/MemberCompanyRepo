package com.MbrCmpProject.plj;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ExcelRetrieverClass extends JFrame {

	private static final long serialVersionUID = 1L;
	// private JFrame frame;
	ResultSet rstSet = null;
	PreparedStatement preStmt = null;
	Connection connection = null;
	DatabaseVariables dbVar = new DatabaseVariables();
	int i = 0;
	private String excelFile;
	FileInputStream fileStream;
	JSONArray mdList;
	JSONObject jsObj;
	ArrayList<String> columns = new ArrayList<String>();
	ArrayList<Integer> types = new ArrayList<Integer>();
	CellType sqlType;
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExcelRetrieverClass frame = new ExcelRetrieverClass();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ExcelRetrieverClass() throws IOException {

		String excelPath = NewExcelFileLocation(excelFile);
		// String excelPath = "K:\\Member Company\\Member Company Excel Reports\\";
		// String excelPath = "C:\\Jarvis\\Member Company Conversion\\Member Company
		// Excel Reports";

		// Connect to database...
		//connection = sqlConnection.dbConnector();
		try {
			fileStream = new FileInputStream(new File(excelPath));
			// setup Excel workbook and sheet
			XSSFWorkbook workbook = new XSSFWorkbook(fileStream);
			XSSFSheet spreadsheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = spreadsheet.iterator();
			Iterator<Cell> cellIterator;
			XSSFDataFormat format = workbook.createDataFormat();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				cellIterator = row.iterator();

				while (cellIterator.hasNext()) {
					XSSFCell cell = (XSSFCell) cellIterator.next();
					sqlType = cell.getCellType();
					switch (sqlType) {
					case FORMULA:
						Object fml = cell.getCellFormula();
						System.out.println(fml);
						break;
					case STRING:
						String val = cell.getStringCellValue();
						System.out.println(val);
						break;
					case NUMERIC:
						double val2 = cell.getNumericCellValue();
						System.out.println(val2);
						break;
					case BOOLEAN:
						boolean boo = cell.getBooleanCellValue();
						System.out.println(boo);
						break;
					default:
						// cell.setCellValue(rstSet.getObject(j).toString());
					}
				}

			}
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null, e1);
		}

	}

	public static String NewExcelFileLocation(String title) {
		File f = null;
		String excelPathRtn = null;
		JFileChooser NewExcelFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		NewExcelFileChooser.setDialogTitle("Upload NAIC Excel File ");
		NewExcelFileChooser.setAcceptAllFileFilterUsed(false);
		//NewExcelFileChooser.setCurrentDirectory(new File("K:\\Accounting Dept\\Member Company"));
		NewExcelFileChooser.setCurrentDirectory(new File("C:\\Users\\pjarvis\\Desktop"));
		int returnValue = NewExcelFileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			f = NewExcelFileChooser.getSelectedFile();
			excelPathRtn = f.getAbsolutePath();
		}
		return excelPathRtn;
	}
}
