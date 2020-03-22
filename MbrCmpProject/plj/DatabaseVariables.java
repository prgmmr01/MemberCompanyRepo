package com.MbrCmpProject.plj;

public class DatabaseVariables {

	/*Encapsulation and mobility...*/
	//Contact and Group Information...
	private int dbNAIC,dbGrpNAIC,dbNbrGrp,dbNumberInTheGroup;
	private int dbRehabDate,dbLiquidDate,dbMergeDate,dbWthDrewDate;
	private int dbZip,dbGroupZipCode,dbGroupLabel;
	private String dbTaxId,dbDeCd,dbPaCd,dbWvCd,dbPart,dbCmpName,dbAttn;
	private String dbAddress1, dbAddress2,dbCity,dbState,dbGroupAttn;
	private String dbGrpCd,dbWthStateDe,dbWthStatePa,dbWthStateWv;
	private String dbStatus,dbGroupStatus,dbLegend,dbParticipate,dbCmpComment;
	private String dbGroupName,dbGroupAddressOne,dbGroupAddressTwo,dbGroupCity,dbGroupState;
	private String dbGroupStateDE,dbGroupStatePA,dbGroupStateWV,dbGroupPart,dbGroupComments;
	private String dbGroupCode,rsvGrpCode,dbPhone,dbFax,dbEmail,dbOnlineRpt,dbFinancials;
	//Premium Factor Information...
	private String dbStateCode,reportSequence,proc_stmt;
	private int dbPremiumYear,dbPremiumSequence;
	private float dbPremiumAmount,dbPremiumPercentage;

	private int promptRunYear;
	private double promptDETotals,promptPATotals,promptWVTotals;
	private int promptDEPercent,promptPAPercent,promptWVPercent;
	private int promptDeChkNbr,promptPaChkNbr,promptWvChkNbr;
	private String promptRunUpdate,Proc,promptState;
	private boolean goodToGo;
	private int rptNumber,rptTotal,j;
	private int rangeYearEnd,rangeYearDE,rangeYearPA,rangeYearWV;
	private String financialDate,pymtRcvDate,assessDate,AorE;
	private int assessYr1, assessYr2, closeYr1, closeYr2;
	private String excelNAIC;
	
	public int getDbPremiumSequence() {
		return dbPremiumSequence;
	}
	public void setDbPremiumSequence(int dbPremiumSequence) {
		this.dbPremiumSequence = dbPremiumSequence;
	}
	public String getDbStateCode() {
		return dbStateCode;
	}
	public void setDbStateCode(String dbStateCode) {
		this.dbStateCode = dbStateCode;
	}
	public int getDbPremiumYear() {
		return dbPremiumYear;
	}
	public void setDbPremiumYear(int dbPremiumYear) {
		this.dbPremiumYear = dbPremiumYear;
	}
	public float getDbPremiumAmount() {
		return dbPremiumAmount;
	}
	public void setDbPremiumAmount(float dbPremiumAmount) {
		this.dbPremiumAmount = dbPremiumAmount;
	}
	public float getDbPremiumPercentage() {
		return dbPremiumPercentage;
	}
	public void setDbPremiumPercentage(float dbPremiumPercentage) {
		this.dbPremiumPercentage = dbPremiumPercentage;
	}
	/**
	 * @return the dbNumberInTheGroup
	 */
	public int getDbNumberInTheGroup() {
		return dbNumberInTheGroup;
	}
	/**
	 * @param dbNumberInTheGroup the dbNumberInTheGroup to set
	 */
	public void setDbNumberInTheGroup(int dbNumberInTheGroup) {
		this.dbNumberInTheGroup = dbNumberInTheGroup;
	}
	/**
	 * @return the dbGroupStateDE
	 */
	public String getDbGroupStateDE() {
		return dbGroupStateDE;
	}
	/**
	 * @param dbGroupStateDE the dbGroupStateDE to set
	 */
	public void setDbGroupStateDE(String dbGroupStateDE) {
		this.dbGroupStateDE = dbGroupStateDE;
	}
	/**
	 * @return the dbGroupStatePA
	 */
	public String getDbGroupStatePA() {
		return dbGroupStatePA;
	}
	/**
	 * @param dbGroupStatePA the dbGroupStatePA to set
	 */
	public void setDbGroupStatePA(String dbGroupStatePA) {
		this.dbGroupStatePA = dbGroupStatePA;
	}
	/**
	 * @return the dbGroupStateWV
	 */
	public String getDbGroupStateWV() {
		return dbGroupStateWV;
	}
	/**
	 * @param dbGroupStateWV the dbGroupStateWV to set
	 */
	public void setDbGroupStateWV(String dbGroupStateWV) {
		this.dbGroupStateWV = dbGroupStateWV;
	}
	/**
	 * @return the dbGroupPart
	 */
	public String getDbGroupPart() {
		return dbGroupPart;
	}
	/**
	 * @param dbGroupPart the dbGroupPart to set
	 */
	public void setDbGroupPart(String dbGroupPart) {
		this.dbGroupPart = dbGroupPart;
	}
	/**
	 * @return the dbGroupComments
	 */
	public String getDbGroupComments() {
		return dbGroupComments;
	}
	/**
	 * @param dbGroupComments the dbGroupComments to set
	 */
	public void setDbGroupComments(String dbGroupComments) {
		this.dbGroupComments = dbGroupComments;
	}
	/**
	 * @return the dbStatus
	 */
	public String getDbStatus() {
		return dbStatus;
	}
	/**
	 * @param dbStatus the dbStatus to set
	 */
	public void setDbStatus(String dbStatus) {
		this.dbStatus = dbStatus;
	}
	/**
	 * @return the dbLegend
	 */
	public String getDbLegend() {
		return dbLegend;
	}
	/**
	 * @param dbLegend the dbLegend to set
	 */
	public void setDbLegend(String dbLegend) {
		this.dbLegend = dbLegend;
	}
	public String setNAIC;
	//Encapsulation-Getters&Setters...
			//int number = Integer.parseInt(Value); //convert string into an int...
	
	//NAIC...
	/**
	 * @return the dbNAIC
	 */
	public int getDbNAIC() {
		return dbNAIC;
	}
	/**
	 * @param dbNAIC the dbNAIC to set
	 */
	public void setDbNAIC(int dbNAIC) {
		if (dbNAIC>0 && dbNAIC<=999999) {
			this.dbNAIC = dbNAIC;
		}else {
			throw new IllegalArgumentException("Invalid NAIC Number");
		}
	}
	//Group NAIC...
	/**
	 * @return the dbGrpNAIC
	 */
	public int getDbGrpNAIC() {
		return dbGrpNAIC;
	}
	/**
	 * @param dbGrpNAIC the dbGrpNAIC to set
	 */
	public void setDbGrpNAIC(int dbGrpNAIC) {
		if (dbGrpNAIC>0 && dbGrpNAIC<999999) {
			this.dbGrpNAIC = dbGrpNAIC;
		}else {
			this.dbGrpNAIC=0;
			//throw new IllegalArgumentException("Invalid Group NAIC Number");
		}
	}
	//Tax Identification Number...
	/**
	 * @return the dbTaxId
	 */
	public String getDbTaxId() {
		return dbTaxId;
	}
	/**
	 * @param dbTaxId the dbTaxId to set
	 */
	public void setDbTaxId(String dbTaxId) {
			this.dbTaxId = dbTaxId;
	}
	/**
	 * @return the dbZip
	 */
	public int getDbZip() {
		return dbZip;
	}
	/**
	 * @param dbZip the dbZip to set
	 */
	public void setDbZip(int dbZip) {
		if (dbZip>0 && dbZip<99999) {
			this.dbZip = dbZip;
		}else {
			throw new IllegalArgumentException("Invalid Zip Number");
		}
	}
	/**
	 * @return the dbNbrGrp
	 */
	public int getDbNbrGrp() {
		return dbNbrGrp;
	}
	/**
	 * @param dbNbrGrp the dbNbrGrp to set
	 */
	public void setDbNbrGrp(int dbNbrGrp) {
		if (dbNbrGrp>=0 && dbNbrGrp<99999) {
			this.dbNbrGrp = dbNbrGrp;
		}else {
			throw new IllegalArgumentException("Invalid Group NAIC Number");
		}
	}
	/**
	 * @return the dbRehabDate
	 */
	public int getDbRehabDate() {
		return dbRehabDate;
	}
	/**
	 * @param dbRehabDate the dbRehabDate to set
	 */
	public void setDbRehabDate(int dbRehabDate) {
		this.dbRehabDate = dbRehabDate;
	}
	/**
	 * @return the dbLiquidDate
	 */
	public int getDbLiquidDate() {
		return dbLiquidDate;
	}
	/**
	 * @param dbLiquidDate the dbLiquidDate to set
	 */
	public void setDbLiquidDate(int dbLiquidDate) {
		this.dbLiquidDate = dbLiquidDate;
	}
	/**
	 * @return the dbMergeDate
	 */
	public int getDbMergeDate() {
		return dbMergeDate;
	}
	/**
	 * @param dbMergeDate the dbMergeDate to set
	 */
	public void setDbMergeDate(int dbMergeDate) {
		this.dbMergeDate = dbMergeDate;
	}
	/**
	 * @return the dbWthDrewDate
	 */
	public int getDbWthDrewDate() {
		return dbWthDrewDate;
	}
	/**
	 * @param dbWthDrewDate the dbWthDrewDate to set
	 */
	public void setDbWthDrewDate(int dbWthDrewDate) {
		this.dbWthDrewDate = dbWthDrewDate;
	}
	/**
	 * @return the dbDeCd
	 */
	public String getDbDeCd() {
		return dbDeCd;
	}
	/**
	 * @param dbDeCd the dbDeCd to set
	 */
	public void setDbDeCd(String dbDeCd) {
		this.dbDeCd = dbDeCd;
	}
	/**
	 * @return the dbPaCd
	 */
	public String getDbPaCd() {
		return dbPaCd;
	}
	/**
	 * @param dbPaCd the dbPaCd to set
	 */
	public void setDbPaCd(String dbPaCd) {
		this.dbPaCd = dbPaCd;
	}
	/**
	 * @return the dbWvCd
	 */
	public String getDbWvCd() {
		return dbWvCd;
	}
	/**
	 * @param dbWvCd the dbWvCd to set
	 */
	public void setDbWvCd(String dbWvCd) {
		this.dbWvCd = dbWvCd;
	}
	/**
	 * @return the dbPart
	 */
	public String getDbPart() {
		return dbPart;
	}
	/**
	 * @param dbPart the dbPart to set
	 */
	public void setDbPart(String dbPart) {
		this.dbPart = dbPart;
	}
	/**
	 * @return the dbCmpName
	 */
	public String getDbCmpName() {
		return dbCmpName;
	}
	/**
	 * @param dbCmpName the dbCmpName to set
	 */
	public void setDbCmpName(String dbCmpName) {
		this.dbCmpName = dbCmpName;
	}
	/**
	 * @return the dbAttn
	 */
	public String getDbAttn() {
		return dbAttn;
	}
	/**
	 * @param dbAttn the dbAttn to set
	 */
	public void setDbAttn(String dbAttn) {
		this.dbAttn = dbAttn;
	}
	/**
	 * @return the dbAddress1
	 */
	public String getDbAddress1() {
		return dbAddress1;
	}
	/**
	 * @param dbAddress1 the dbAddress1 to set
	 */
	public void setDbAddress1(String dbAddress1) {
		this.dbAddress1 = dbAddress1;
	}
	/**
	 * @return the dbAddess2
	 */
	public String getDbAddress2() {
		return dbAddress2;
	}
	/**
	 * @param dbAddess2 the dbAddess2 to set
	 */
	public void setDbAddress2(String dbAddress2) {
		this.dbAddress2 = dbAddress2;
	}
	/**
	 * @return the dbCity
	 */
	public String getDbCity() {
		return dbCity;
	}
	/**
	 * @param dbCity the dbCity to set
	 */
	public void setDbCity(String dbCity) {
		this.dbCity = dbCity;
	}
	/**
	 * @return the dbState
	 */
	public String getDbState() {
		return dbState;
	}
	/**
	 * @param dbState the dbState to set
	 */
	public void setDbState(String dbState) {
		this.dbState = dbState;
	}

	/**
	 * @return the dbGrpCd
	 */
	public String getDbGrpCd() {
		return dbGrpCd;
	}
	/**
	 * @param dbGrpCd the dbGrpCd to set
	 */
	public void setDbGrpCd(String dbGrpCd) {
		this.dbGrpCd = dbGrpCd;
	}
	/**
	 * @return the dbWthStateDe
	 */
	public String getDbWthStateDe() {
		return dbWthStateDe;
	}
	/**
	 * @param dbWthStateDe the dbWthStateDe to set
	 */
	public void setDbWthStateDe(String dbWthStateDe) {
		this.dbWthStateDe = dbWthStateDe;
	}
	/**
	 * @return the dbWthStatePa
	 */
	public String getDbWthStatePa() {
		return dbWthStatePa;
	}
	/**
	 * @param dbWthStatePa the dbWthStatePa to set
	 */
	public void setDbWthStatePa(String dbWthStatePa) {
		this.dbWthStatePa = dbWthStatePa;
	}
	/**
	 * @return the dbWthStateWv
	 */
	public String getDbWthStateWv() {
		return dbWthStateWv;
	}
	/**
	 * @param dbWthStateWv the dbWthStateWv to set
	 */
	public void setDbWthStateWv(String dbWthStateWv) {
		this.dbWthStateWv = dbWthStateWv;
	}
	/**
	 * @return the dbGroupName
	 */
	public String getDbGroupName() {
		return dbGroupName;
	}
	/**
	 * @param dbGroupName the dbGroupName to set
	 */
	public void setDbGroupName(String dbGroupName) {
		this.dbGroupName = dbGroupName;
	}
	/**
	 * @return the dbGroupAddressOne
	 */
	public String getDbGroupAddressOne() {
		return dbGroupAddressOne;
	}
	/**
	 * @param dbGroupAddressOne the dbGroupAddressOne to set
	 */
	public void setDbGroupAddressOne(String dbGroupAddressOne) {
		this.dbGroupAddressOne = dbGroupAddressOne;
	}
	/**
	 * @return the dbGroupAddressTwo
	 */
	public String getDbGroupAddressTwo() {
		return dbGroupAddressTwo;
	}
	/**
	 * @param dbGroupAddressTwo the dbGroupAddressTwo to set
	 */
	public void setDbGroupAddressTwo(String dbGroupAddressTwo) {
		this.dbGroupAddressTwo = dbGroupAddressTwo;
	}
	/**
	 * @return the dbGroupCity
	 */
	public String getDbGroupCity() {
		return dbGroupCity;
	}
	/**
	 * @param dbGroupCity the dbGroupCity to set
	 */
	public void setDbGroupCity(String dbGroupCity) {
		this.dbGroupCity = dbGroupCity;
	}
	/**
	 * @return the dbGroupState
	 */
	public String getDbGroupState() {
		return dbGroupState;
	}
	/**
	 * @param dbGroupState the dbGroupState to set
	 */
	public void setDbGroupState(String dbGroupState) {
		this.dbGroupState = dbGroupState;
	}
	/**
	 * @return the dbGroupZipCode
	 */
	public int getDbGroupZipCode() {
		return dbGroupZipCode;
	}
	/**
	 * @param dbGroupZipCode the dbGroupZipCode to set
	 */
	public void setDbGroupZipCode(int dbGroupZipCode) {
		if (dbGroupZipCode>=0 && dbGroupZipCode<99999) {
			this.dbGroupZipCode = dbGroupZipCode;
		}else {
			this.dbGroupZipCode=0;
			//throw new IllegalArgumentException("Invalid Group Zip Number");
		}
	}
	
	public String getDbGroupCode() {
		return dbGroupCode;
	}
	public void setDbGroupCode(String dbGroupCode) {
		this.dbGroupCode = dbGroupCode;
	}
	public String getDbParticipate() {
		return dbParticipate;
	}
	public void setDbParticipate(String dbParticipate) {
		this.dbParticipate = dbParticipate;
	}
	public String getDbCmpComment() {
		return dbCmpComment;
	}
	public void setDbCmpComment(String dbCmpComment) {
		this.dbCmpComment = dbCmpComment;
	}
	public int getDbGroupLabel() {
		return dbGroupLabel;
	}
	public void setDbGroupLabel(int dbGroupLabel) {
		this.dbGroupLabel = dbGroupLabel;
	}
	public String getReportSequence() {
		return reportSequence;
	}

	public void setReportSequence(String reportSequence) {
		this.reportSequence = reportSequence;
	}
	public String getProc_stmt() {
		return proc_stmt;
	}
	public void setProc_stmt(String proc_stmt) {
		this.proc_stmt = proc_stmt;
	}
	
	//Member Company Prompt variables...
	public int getPromptRunYear() {
		return promptRunYear;
	}
	public void setPromptRunYear(int promptRunYear) {
		this.promptRunYear = promptRunYear;
	}
	public double getPromptDETotals() {
		return promptDETotals;
	}
	public void setPromptDETotals(double promptDETotals) {
		this.promptDETotals = promptDETotals;
	}
	public double getPromptPATotals() {
		return promptPATotals;
	}
	public void setPromptPATotals(double promptPATotals) {
		this.promptPATotals = promptPATotals;
	}
	public double getPromptWVTotals() {
		return promptWVTotals;
	}
	public void setPromptWVTotals(double promptWVTotals) {
		this.promptWVTotals = promptWVTotals;
	}
	public int getPromptDeChkNbr() {
		return promptDeChkNbr;
	}
	public void setPromptDeChkNbr(int promptDeChkNbr) {
		this.promptDeChkNbr = promptDeChkNbr;
	}
	public int getPromptPaChkNbr() {
		return promptPaChkNbr;
	}
	public void setPromptPaChkNbr(int promptPaChkNbr) {
		this.promptPaChkNbr = promptPaChkNbr;
	}
	public int getPromptWvChkNbr() {
		return promptWvChkNbr;
	}
	public void setPromptWvChkNbr(int promptWvChkNbr) {
		this.promptWvChkNbr = promptWvChkNbr;
	}
	public String getPromptRunUpdate() {
		return promptRunUpdate;
	}
	public void setPromptRunUpdate(String promptRunUpdate) {
		this.promptRunUpdate = promptRunUpdate;
	}
	public boolean getGoodToGo() {
		return goodToGo;
	}
	public void setGoodToGo(boolean goodToGo) {
		this.goodToGo = goodToGo;
	}
	public int getRptNumber() {
		return rptNumber;
	}
	public void setRptNumber(int rptNumber) {
		this.rptNumber = rptNumber;
	}
	public int getRptTotal() {
		return rptTotal;
	}
	public void setRptTotal(int rptTotal) {
		this.rptTotal = rptTotal;
	}
	public String getProc() {
		return Proc;
	}
	public void setProc(String proc) {
		Proc = proc;
	}
	public int getRangeYearEnd() {
		return rangeYearEnd;
	}
	public void setRangeYearEnd(int rangeYearEnd) {
		this.rangeYearEnd = rangeYearEnd;
	}
	public int getRangeYearDE() {
		return rangeYearDE;
	}
	public void setRangeYearDE(int rangeYearDE) {
		this.rangeYearDE = rangeYearDE;
	}
	public int getRangeYearPA() {
		return rangeYearPA;
	}
	public void setRangeYearPA(int rangeYearPA) {
		this.rangeYearPA = rangeYearPA;
	}
	public int getRangeYearWV() {
		return rangeYearWV;
	}
	public void setRangeYearWV(int rangeYearWV) {
		this.rangeYearWV = rangeYearWV;
	}
	public String getPromptState() {
		return promptState;
	}
	public void setPromptState(String promptState) {
		this.promptState = promptState;
	}
	public int getPromptDEPercent() {
		return promptDEPercent;
	}
	public void setPromptDEPercent(int promptDEPercent) {
		this.promptDEPercent = promptDEPercent;
	}
	public int getPromptPAPercent() {
		return promptPAPercent;
	}
	public void setPromptPAPercent(int promptPAPercent) {
		this.promptPAPercent = promptPAPercent;
	}
	public int getPromptWVPercent() {
		return promptWVPercent;
	}
	public void setPromptWVPercent(int promptWVPercent) {
		this.promptWVPercent = promptWVPercent;
	}
	public String getFinancialDate() {
		return financialDate;
	}
	public void setFinancialDate(String financialDate) {
		this.financialDate = financialDate;
	}
	public String getPymtRcvDate() {
		return pymtRcvDate;
	}
	public void setPymtRcvDate(String pymtRcvDate) {
		this.pymtRcvDate = pymtRcvDate;
	}
	public String getAssessDate() {
		return assessDate;
	}
	public void setAssessDate(String assessDate) {
		this.assessDate = assessDate;
	}
	public String getAorE() {
		return AorE;
	}
	public void setAorE(String aorE) {
		AorE = aorE;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}
	public int getAssessYr1() {
		return assessYr1;
	}
	public void setAssessYr1(int assessYr1) {
		this.assessYr1 = assessYr1;
	}
	public int getAssessYr2() {
		return assessYr2;
	}
	public void setAssessYr2(int assessYr2) {
		this.assessYr2 = assessYr2;
	}
	public int getCloseYr1() {
		return closeYr1;
	}
	public void setCloseYr1(int closeYr1) {
		this.closeYr1 = closeYr1;
	}
	public int getCloseYr2() {
		return closeYr2;
	}
	public void setCloseYr2(int closeYr2) {
		this.closeYr2 = closeYr2;
	}
	public String getDbPhone() {
		return dbPhone;
	}
	public void setDbPhone(String dbPhone) {
		this.dbPhone = dbPhone;
	}
	public String getDbEmail() {
		return dbEmail;
	}
	public void setDbEmail(String dbEmail) {
		this.dbEmail = dbEmail;
	}
	public String getDbFax() {
		return dbFax;
	}
	public void setDbFax(String dbFax) {
		this.dbFax = dbFax;
	}
	public String getDbOnlineRpt() {
		return dbOnlineRpt;
	}
	public void setDbOnlineRpt(String dbOnlineRpt) {
		this.dbOnlineRpt = dbOnlineRpt;
	}
	public String getDbFinancials() {
		return dbFinancials;
	}
	public void setDbFinancials(String dbFinancials) {
		this.dbFinancials = dbFinancials;
	}
	public String getDbGroupStatus() {
		return dbGroupStatus;
	}
	public void setDbGroupStatus(String dbGroupStatus) {
		this.dbGroupStatus = dbGroupStatus;
	}
	public String getDbGroupAttn() {
		return dbGroupAttn;
	}
	public void setDbGroupAttn(String dbGroupAttn) {
		this.dbGroupAttn = dbGroupAttn;
	}
	public String getRsvGrpCode() {
		return rsvGrpCode;
	}
	public void setRsvGrpCode(String rsvGrpCode) {
		this.rsvGrpCode = rsvGrpCode;
	}
	public String getExcelNAIC() {
		return excelNAIC;
	}
	public void setExcelNAIC(String excelNAIC) {
		this.excelNAIC = excelNAIC;
	}
	
}
