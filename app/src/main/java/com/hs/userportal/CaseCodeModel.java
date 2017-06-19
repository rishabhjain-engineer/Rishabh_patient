package com.hs.userportal;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rishabh on 15/6/17.
 */

public class CaseCodeModel {

    private String caseCode;
    private String locationName;  // Name of lab , where patient visited for sample
    private String referrerName;  // who refer to this particular Lab .
    private String dateandTime ;
    private String patientName ;
    private String caseID ;
    private String testLocationID ;
    private List<TestNames> listOfTestNamesInCaseCode = new ArrayList<>();   // one CaseCode can have multiple TEST done .
    private TestNames testNameObject;
    private int totalPaidAmount ;
    private int totalActualAmount ;
    private int initialAmount ;
    private int discountAmount ;




    public CaseCodeModel(String caseCode) {
        this.caseCode = caseCode;
        testNameObject = new TestNames();
        listOfTestNamesInCaseCode.add(testNameObject);
    }

    public int getTotalPaidAmount() {
        return totalPaidAmount;
    }

    public void setTotalPaidAmount(int totalPaidAmount) {
        this.totalPaidAmount = totalPaidAmount;
    }

    public int getTotalActualAmount() {
        return totalActualAmount;
    }

    public void setTotalActualAmount(int totalActualAmount) {
        this.totalActualAmount = totalActualAmount;
    }

    public int getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(int initialAmount) {
        this.initialAmount = initialAmount;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
    }


    public String getCaseCode() {
        return caseCode;
    }


    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getReferrerName() {
        return referrerName;
    }

    public void setReferrerName(String referrerName) {

        if("null".equalsIgnoreCase(referrerName)) {
            this.referrerName = "SELF" ;
        }else{
             this.referrerName = referrerName;
        }
    }

    public TestNames getTestNamesObject() {
        return testNameObject;
    }


    public void createNewTestNameObject(){
        testNameObject = new TestNames();
        listOfTestNamesInCaseCode.add(testNameObject);
    }

    public List<TestNames> getListOfTestNamesInCaseCode () {
        return listOfTestNamesInCaseCode;
    }
    public String getCaseID() {
        return caseID;
    }

    public void setCaseID(String caseID) {
        this.caseID = caseID;
    }

    public String gettestLocationID() {
        return testLocationID;
    }

    public void settestLocationID(String locationID) {
        this.testLocationID = locationID;
    }

    public String getDateandTime() {
        return dateandTime;
    }

    public void setDateandTime(String dateandTime) {
        this.dateandTime = dateandTime;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
}
