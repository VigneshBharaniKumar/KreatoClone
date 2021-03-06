package com.vignesh.kreatoclone;

public class Leads {

    private String objectID;

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

    /*Setters*/

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

}



