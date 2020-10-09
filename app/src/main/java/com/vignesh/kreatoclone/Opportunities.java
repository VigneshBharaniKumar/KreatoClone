package com.vignesh.kreatoclone;

public class Opportunities {

    private String objectID;

    private String opportunityID;

    private String accountName;
    private String contactName;
    private String itemName;
    private long targetAmount;
    private long contactNo;
    private String emailId;
    private String billingAddress;
    private String billingCity;
    private String billingState;
    private String billingCountry;
    private String opportunityOwner;
    private String description;

    public Opportunities(String objectID) {
        this.objectID = objectID;
    }

    public Opportunities (String accountName, String contactName, String itemName, long targetAmount, long contactNo, String emailId, String billingAddress, String billingCity, String billingState, String billingCountry, String opportunityOwner, String description) {
        this.accountName = accountName;
        this.contactName = contactName;
        this.itemName = itemName;
        this.targetAmount = targetAmount;
        this.contactNo = contactNo;
        this.emailId = emailId;
        this.billingAddress = billingAddress;
        this.billingCity = billingCity;
        this.billingState = billingState;
        this.billingCountry = billingCountry;
        this.opportunityOwner = opportunityOwner;
        this.description = description;
    }

    /*Getters*/

    public String getObjectID() {
        return objectID;
    }

    public String getOpportunityID() {
        return opportunityID;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getContactName() {
        return contactName;
    }

    public String getItemName() {
        return itemName;
    }

    public long getTargetAmount() {
        return targetAmount;
    }

    public long getContactNo() {
        return contactNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public String getBillingState() {
        return billingState;
    }

    public String getBillingCountry() {
        return billingCountry;
    }

    public String getOpportunityOwner() {
        return opportunityOwner;
    }

    public String getDescription() {
        return description;
    }

    /*Setters*/

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    public void setOpportunityID(String opportunityID) {
        this.opportunityID = opportunityID;
    }
}
