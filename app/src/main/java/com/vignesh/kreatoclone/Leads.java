package com.vignesh.kreatoclone;

import java.io.Serializable;

public class Leads implements Serializable {

    private String name;
    private String emailId;
    private long contactNo;
    private String companyName;
    private String primaryAddress;
    private String primaryCity;
    private String primaryState;
    private String primaryCountry;
    private String leadOwner;
    private String coOwner;
    private String additionalInformation;

    private String objectID;

    public Leads(String objectID) {
        this.objectID = objectID;
    }

    public Leads(String name, String emailId, long contactNo, String companyName, String primaryAddress,
                 String primaryCity, String primaryState, String primaryCountry, String leadOwner,
                 String coOwner, String additionalInformation) {

        this.name = name;
        this.emailId = emailId;
        this.contactNo = contactNo;
        this.companyName = companyName;
        this.primaryAddress = primaryAddress;
        this.primaryCity = primaryCity;
        this.primaryState = primaryState;
        this.primaryCountry = primaryCountry;
        this.leadOwner = leadOwner;
        this.coOwner = coOwner;
        this.additionalInformation = additionalInformation;

    }

    /*Getters*/
    public String getName() {
        return name;
    }

    public String getEmailId() {
        return emailId;
    }

    public long getContactNo() {
        return contactNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getPrimaryAddress() {
        return primaryAddress;
    }

    public String getPrimaryCity() {
        return primaryCity;
    }

    public String getPrimaryState() {
        return primaryState;
    }

    public String getPrimaryCountry() {
        return primaryCountry;
    }

    public String getLeadOwner() {
        return leadOwner;
    }

    public String getCoOwner() {
        return coOwner;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    /*Setters*/
    /*public void setName(String name) {
        this.name = name;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setContactNo(long contactNo) {
        this.contactNo = contactNo;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setPrimaryAddress(String primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public void setPrimaryCity(String primaryCity) {
        this.primaryCity = primaryCity;
    }

    public void setPrimaryState(String primaryState) {
        this.primaryState = primaryState;
    }

    public void setPrimaryCountry(String primaryCountry) {
        this.primaryCountry = primaryCountry;
    }

    public void setLeadOwner(String leadOwner) {
        this.leadOwner = leadOwner;
    }

    public void setCoOwner(String coOwner) {
        this.coOwner = coOwner;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }*/

}



