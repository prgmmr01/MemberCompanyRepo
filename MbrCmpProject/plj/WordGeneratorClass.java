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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

public class WordGeneratorClass extends JFrame {

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
					WordGeneratorClass frame = new WordGeneratorClass();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public WordGeneratorClass() {
	}

	public WordGeneratorClass(String sqlQuery, String title) {

		//String excelPath =  NewExcelFileLocation(title);
		String excelPath = "K:\\Member Company\\Member Company Mail Merge Documents\\MergedDocuments";
		//String excelPath = "C:\\Jarvis\\Member Company Conversion\\Member Company Mail Merge Documents\\MergedDocuments";

		// Connect to database...
		connection = sqlConnection.dbConnector();

		// Blank Document
		XWPFDocument wordDocument = new XWPFDocument();
		// create Paragraph
		XWPFParagraph wordParagraph = wordDocument.createParagraph();
		XWPFRun runParagraph = wordParagraph.createRun();
		XmlCursor cursor = null;
		XWPFTable wordTable = null;

		try {
			preStmt = connection.prepareStatement(sqlQuery);
			rstSet = preStmt.executeQuery();
			ResultSetMetaData rstMetaData = rstSet.getMetaData();
			for (int k = 1; k < rstMetaData.getColumnCount(); k++) {
				columns.add(rstMetaData.getColumnName(k));
				types.add(rstMetaData.getColumnType(k));
			}
			if (!rstSet.isBeforeFirst()) {
				JOptionPane.showMessageDialog(null, "No Checks were Processed");
				dbVar.setGoodToGo(false);
			} else {
				// Create JSon file of meta data...
				try (FileWriter jsFile = new FileWriter(excelPath + "jsMetaDataChecks.json")) {
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

				wordTable = wordDocument.createTable();
				// first row...
				XWPFTableRow tableFirtstRow = wordTable.getRow(0);
				tableFirtstRow.getCell(0).setText("NAIC");
				tableFirtstRow.addNewTableCell().setText("Company Name");
				tableFirtstRow.addNewTableCell().setText("Check Amount");
				tableFirtstRow.addNewTableCell().setText("Check Date");

//
				
		        CTSectPr sectPr = wordDocument.getDocument().getBody().addNewSectPr();
				XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(wordDocument, sectPr);
					
				//write header content
				CTP ctpHeader = CTP.Factory.newInstance();
			        CTR ctrHeader = ctpHeader.addNewR();
				CTText ctHeader = ctrHeader.addNewT();
				String headerText = "List of Checks";
				ctHeader.setStringValue(headerText);	
				XWPFParagraph headerParagraph = new XWPFParagraph(ctpHeader, wordDocument);
			    XWPFParagraph[] parsHeader = new XWPFParagraph[1];
			    parsHeader[0] = headerParagraph;
			    policy.createHeader(XWPFHeaderFooterPolicy.DEFAULT, parsHeader);
				
//				
				
				// load data values...
				while (rstSet.next()) {
					i++;
					String strValue = rstSet.getString("CompanyName");
					int intValue = rstSet.getInt("NAIC");
					double dblValue = rstSet.getDouble("CheckAmount");
					String strDate = rstSet.getString("CheckDate");

					XWPFTableRow tableSecondRow = wordTable.createRow();
					tableSecondRow.getCell(0).setText(String.valueOf(intValue));
					tableSecondRow.getCell(1).setText(strValue);
					tableSecondRow.getCell(2).setText(String.valueOf(dblValue));
					tableSecondRow.getCell(3).setText(strDate);

				}
		        
				//write footer content
				CTP ctpFooter = CTP.Factory.newInstance();
				CTR ctrFooter = ctpFooter.addNewR();
				CTText ctFooter = ctrFooter.addNewT();
				String footerText = "This is footer";
				ctFooter.setStringValue(footerText);	
				XWPFParagraph footerParagraph = new XWPFParagraph(ctpFooter, wordDocument);
			    XWPFParagraph[] parsFooter = new XWPFParagraph[1];
			    parsFooter[0] = footerParagraph;
				policy.createFooter(XWPFHeaderFooterPolicy.DEFAULT, parsFooter);				

				//runParagraph.addBreak();
				
				FileOutputStream out = new FileOutputStream(new File(excelPath + "\\" + title + " Word Checks.docx"));
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

	public static String NewExcelFileLocation(String title) {
		// setup open dialog to assign the directory location to save the Excel file...
		File f = null;
		String excelPathRtn = null;
		JFileChooser NewExcelFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		NewExcelFileChooser.setDialogTitle("Member Company Excel File " + title);
		NewExcelFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		NewExcelFileChooser.setAcceptAllFileFilterUsed(false);
		int returnValue = NewExcelFileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			f = NewExcelFileChooser.getSelectedFile();
			excelPathRtn = f.getAbsolutePath();
		}
		return excelPathRtn;
	}
}
