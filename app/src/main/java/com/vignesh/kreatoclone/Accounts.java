package com.vignesh.kreatoclone;

public class Accounts {

    private String objectID;
    private String contactID;

    private String name;
    private String emailId;
    private long contactNo;
    private String contactName;
    private String primaryAddress;
    private String primaryCity;
    private String primaryState;
    private String primaryCountry;
    private String accountOwner;
    private String additionalInformation;

    public Accounts(String objectID) {
        this.objectID = objectID;
    }

    public Accounts(String name, String emailId, long contactNo, String contactName, String primaryAddress, String primaryCity, String primaryState, String primaryCountry, String accountOwner, String additionalInformation) {
        this.name = name;
        this.emailId = emailId;
        this.contactNo = contactNo;
        this.contactName = contactName;
        this.primaryAddress = primaryAddress;
        this.primaryCity = primaryCity;
        this.primaryState = primaryState;
        this.primaryCountry = primaryCountry;
        this.accountOwner = accountOwner;
        this.additionalInformation = additionalInformation;
    }

    /*Getters*/

    public String getObjectID() {
        return objectID;
    }

    public String getContactID() {
        return contactID;
    }

    public String getName() {
        return name;
    }

    public String getEmailId() {
        return emailId;
    }

    public long getContactNo() {
        return contactNo;
    }

    public String getContactName() {
        return contactName;
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

    public String getAccountOwner() {
        return accountOwner;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    /*Setters*/

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    public void setContactID(String contactID) {
        this.contactID = contactID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setContactNo(long contactNo) {
        this.contactNo = contactNo;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
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

    public void setAccountOwner(String accountOwner) {
        this.accountOwner = accountOwner;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }
}
