package com.vignesh.kreatoclone;

public class Contacts {

    private String objectID;

    private String name;
    private String emailId;
    private long contactNo;
    private String companyName;
    private String primaryAddress;
    private String primaryCity;
    private String primaryState;
    private String primaryCountry;
    private String ContactOwner;
    private String additionalInformation;

    public Contacts(String objectID) {
        this.objectID = objectID;
    }

    public Contacts(String name, String emailId, long contactNo, String companyName,
                    String primaryAddress, String primaryCity, String primaryState,
                    String primaryCountry, String contactOwner, String additionalInformation) {
        this.name = name;
        this.emailId = emailId;
        this.contactNo = contactNo;
        this.companyName = companyName;
        this.primaryAddress = primaryAddress;
        this.primaryCity = primaryCity;
        this.primaryState = primaryState;
        this.primaryCountry = primaryCountry;
        ContactOwner = contactOwner;
        this.additionalInformation = additionalInformation;
    }

    /*Getters*/

    public String getObjectID() {
        return objectID;
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

    public String getContactOwner() {
        return ContactOwner;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    /*Setters*/

    public void setObjectID(String objectID) {
        this.objectID = objectID;
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

    public void setContactOwner(String contactOwner) {
        ContactOwner = contactOwner;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }
}
