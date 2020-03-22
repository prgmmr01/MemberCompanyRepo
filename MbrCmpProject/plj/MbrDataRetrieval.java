package com.MbrCmpProject.plj;

/*
Author:		Peter Jarvis
Date:		06012018
Purpose:	retrieve Member Company individual record and load it into database 
			variables to be displayed in the MbrCompanyDisplay class... 
*/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class MbrDataRetrieval {

	ResultSet rstSet = null;
	PreparedStatement preStmt = null;
	Connection connection = null;
	public DatabaseVariables dbVar = new DatabaseVariables();
	private int qryNAIC, qryTaxId;

	public void dataRetrieval(int swtNumber, int intTaxId, int intNAIC, String sqlQuery, DatabaseVariables dbVar) {
		// Connect to database...
		connection = sqlConnection.dbConnector();
		// Query based on options select from MbrCompanyMain menu...
		try {
			preStmt = connection.prepareStatement(sqlQuery);
			if (swtNumber == 2) {
				qryTaxId = intTaxId;
				preStmt.setInt(1, qryTaxId);
			} else {
				qryNAIC = intNAIC;
				preStmt.setInt(1, qryNAIC);
			}
			rstSet = preStmt.executeQuery();
			if (!rstSet.isBeforeFirst()) {
				JOptionPane.showMessageDialog(null, "Member Does Not Exist");
				dbVar.setGoodToGo(false);
			} else {
				while (rstSet.next()) {
					dbVar.setGoodToGo(true);
					dbVar.setDbNAIC(rstSet.getInt("NAIC"));
					dbVar.setDbTaxId(rstSet.getString("TaxId"));
					dbVar.setDbCmpName(rstSet.getString("CompanyName"));
					dbVar.setDbAddress1(rstSet.getString("AddressOne"));
					dbVar.setDbAddress2(rstSet.getString("AddressTwo"));
					dbVar.setDbCity(rstSet.getString("City"));
					dbVar.setDbState(rstSet.getString("State"));
					dbVar.setDbZip(rstSet.getInt("ZipCode"));
					dbVar.setDbAttn(rstSet.getString("Recipient"));
					dbVar.setDbStatus(rstSet.getString("StatusType"));
					dbVar.setDbLegend(rstSet.getString("Legend"));
					dbVar.setDbDeCd(rstSet.getString("DECd"));
					dbVar.setDbPaCd(rstSet.getString("PACd"));
					dbVar.setDbWvCd(rstSet.getString("WVCd"));
					dbVar.setDbParticipate(rstSet.getString("Participate"));
					int intValue = rstSet.getInt("NAICGroup");
			        if (intValue==0) 
			        	{dbVar.setDbGrpNAIC(intValue);}
			        else 
			        	{dbVar.setDbGrpNAIC(rstSet.getInt("NAICGroup"));}
					dbVar.setDbGroupCode(rstSet.getString("GroupCode"));
			        dbVar.setDbGroupName(rstSet.getString("GroupName"));
					dbVar.setDbGroupAddressOne(rstSet.getString("GroupAddressOne"));
					dbVar.setDbGroupAddressTwo(rstSet.getString("GroupAddressTwo"));
					dbVar.setDbGroupCity(rstSet.getString("GroupCity"));
					dbVar.setDbGroupState(rstSet.getString("GroupState"));
					dbVar.setDbGroupZipCode(rstSet.getInt("GroupZipCode"));
					dbVar.setDbPhone(rstSet.getString("Phone"));
					dbVar.setDbEmail(rstSet.getString("Email"));
					dbVar.setDbFax(rstSet.getString("Fax_Number"));
					dbVar.setDbOnlineRpt(rstSet.getString("OnlineRpt"));
					dbVar.setDbFinancials(rstSet.getString("Financials"));
			        if (rstSet.wasNull()) 
			        	{dbVar.setDbGroupZipCode(0);}
			        else;
			        {dbVar.setDbGroupZipCode(rstSet.getInt("GroupZipCode"));}
				}
			}
		} catch (Exception expAdd) {
			JOptionPane.showMessageDialog(null, expAdd);
			// Final steps to close out Queries...
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
			if (preStmt != null) {
				try {
					preStmt.close();
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

}
