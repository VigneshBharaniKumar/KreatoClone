package com.vignesh.kreatoclone;

import android.app.Activity;
import android.content.Context;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ConvertManager {

    private Context mContext;

    private SweetAlertDialog alertDialog;

    public ConvertManager(Context mContext) {
        this.mContext = mContext;
    }

    public void leadToContact(String objectID) {

        alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitleText("Updating...");
        alertDialog.setCancelable(false);
        alertDialog.show();

        ParseQuery<ParseObject> query = new ParseQuery<>("Leads");
        query.whereEqualTo("objectId", objectID);
        query.getInBackground(objectID, new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject object, ParseException e) {

                if (e == null) {

                    Leads lead = new Leads(

                            object.get(LeadsManager.NAME_KEY).toString(),
                            object.get(LeadsManager.EMAIL_ID_KEY).toString(),
                            Long.parseLong(object.get(LeadsManager.CONTACT_NO_KEY).toString()),
                            object.get(LeadsManager.COMPANY_NAME_KEY).toString(),
                            object.get(LeadsManager.PRIMARY_ADDRESS_KEY).toString(),
                            object.get(LeadsManager.PRIMARY_CITY_KEY).toString(),
                            object.get(LeadsManager.PRIMARY_STATE_KEY).toString(),
                            object.get(LeadsManager.PRIMARY_COUNTRY_KEY).toString(),
                            object.get(LeadsManager.LEAD_OWNER_KEY).toString(),
                            object.get(LeadsManager.CO_OWNER_KEY).toString(),
                            object.get(LeadsManager.ADDITIONAL_INFORMATION_KEY).toString()

                    );

                    final Contacts contact = new Contacts(

                            lead.getName(),
                            lead.getEmailId(),
                            lead.getContactNo(),
                            lead.getCompanyName(),
                            lead.getPrimaryAddress(),
                            lead.getPrimaryCity(),
                            lead.getPrimaryState(),
                            lead.getPrimaryCountry(),
                            lead.getLeadOwner(),
                            lead.getAdditionalInformation()

                    );

                    final ParseObject objectContact = new ParseObject("Contacts");

                    objectContact.put(ContactsManager.NAME_KEY, contact.getName());
                    objectContact.put(ContactsManager.EMAIL_ID_KEY, contact.getEmailId());
                    objectContact.put(ContactsManager.CONTACT_NO_KEY, contact.getContactNo());
                    objectContact.put(ContactsManager.COMPANY_NAME_KEY, contact.getCompanyName());
                    objectContact.put(ContactsManager.PRIMARY_ADDRESS_KEY, contact.getPrimaryAddress());
                    objectContact.put(ContactsManager.PRIMARY_CITY_KEY, contact.getPrimaryCity());
                    objectContact.put(ContactsManager.PRIMARY_STATE_KEY, contact.getPrimaryState());
                    objectContact.put(ContactsManager.PRIMARY_COUNTRY_KEY, contact.getPrimaryCountry());
                    objectContact.put(ContactsManager.CONTACT_OWNER_KEY, contact.getContactOwner());
                    objectContact.put(ContactsManager.ADDITIONAL_INFORMATION_KEY, contact.getAdditionalInformation());

                    objectContact.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {

                                alertDialog.dismissWithAnimation();

                                contact.setObjectID(objectContact.getObjectId());

                                alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Converted")
                                        .setContentText("Converted successfully...")
                                        .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                alertDialog.dismissWithAnimation();
                                                object.deleteInBackground();
                                                ((Activity) mContext).finish();
                                            }
                                        });
                                alertDialog.setCancelable(false);
                                alertDialog.show();

                            } else {

                                alertDialog.dismissWithAnimation();

                                alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Failed")
                                        .setContentText("Error: " + e);
                                alertDialog.show();

                            }
                        }
                    });

                } else {

                    alertDialog.dismissWithAnimation();

                    alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText("Error: " + e);
                    alertDialog.show();

                }

            }
        });
    }

    public void leadToAccount (String objectID) {

        alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitleText("Updating...");
        alertDialog.setCancelable(false);
        alertDialog.show();

        ParseQuery<ParseObject> query = new ParseQuery<>("Leads");
        query.whereEqualTo("objectId", objectID);
        query.getInBackground(objectID, new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject object, ParseException e) {

                if (e == null) {

                    Leads lead = new Leads(

                            object.get(LeadsManager.NAME_KEY).toString(),
                            object.get(LeadsManager.EMAIL_ID_KEY).toString(),
                            Long.parseLong(object.get(LeadsManager.CONTACT_NO_KEY).toString()),
                            object.get(LeadsManager.COMPANY_NAME_KEY).toString(),
                            object.get(LeadsManager.PRIMARY_ADDRESS_KEY).toString(),
                            object.get(LeadsManager.PRIMARY_CITY_KEY).toString(),
                            object.get(LeadsManager.PRIMARY_STATE_KEY).toString(),
                            object.get(LeadsManager.PRIMARY_COUNTRY_KEY).toString(),
                            object.get(LeadsManager.LEAD_OWNER_KEY).toString(),
                            object.get(LeadsManager.CO_OWNER_KEY).toString(),
                            object.get(LeadsManager.ADDITIONAL_INFORMATION_KEY).toString()

                    );

                    final Accounts account = new Accounts(

                            lead.getCompanyName(),
                            lead.getEmailId(),
                            lead.getContactNo(),
                            lead.getName(),
                            lead.getPrimaryAddress(),
                            lead.getPrimaryCity(),
                            lead.getPrimaryState(),
                            lead.getPrimaryCountry(),
                            lead.getLeadOwner(),
                            lead.getAdditionalInformation()

                    );

                    final ParseObject objectAccount = new ParseObject("Accounts");

                    objectAccount.put(AccountsManager.NAME_KEY, account.getName());
                    objectAccount.put(AccountsManager.EMAIL_ID_KEY, account.getEmailId());
                    objectAccount.put(AccountsManager.CONTACT_NO_KEY, account.getContactNo());
                    objectAccount.put(AccountsManager.CONTACT_NAME_KEY, account.getContactName());
                    objectAccount.put(AccountsManager.PRIMARY_ADDRESS_KEY, account.getPrimaryAddress());
                    objectAccount.put(AccountsManager.PRIMARY_CITY_KEY, account.getPrimaryCity());
                    objectAccount.put(AccountsManager.PRIMARY_STATE_KEY, account.getPrimaryState());
                    objectAccount.put(AccountsManager.PRIMARY_COUNTRY_KEY, account.getPrimaryCountry());
                    objectAccount.put(AccountsManager.ACCOUNT_OWNER_KEY, account.getAccountOwner());
                    objectAccount.put(AccountsManager.ADDITIONAL_INFORMATION_KEY, account.getAdditionalInformation());

                    objectAccount.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {

                                alertDialog.dismissWithAnimation();

                                account.setObjectID(objectAccount.getObjectId());

                                alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Converted")
                                        .setContentText("Converted successfully...")
                                        .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                alertDialog.dismissWithAnimation();
                                                //((Activity) mContext).finish();
                                            }
                                        });
                                alertDialog.setCancelable(false);
                                alertDialog.show();

                            } else {

                                alertDialog.dismissWithAnimation();

                                alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Failed")
                                        .setContentText("Error: " + e);
                                alertDialog.show();

                            }
                        }
                    });

                } else {

                    alertDialog.dismissWithAnimation();

                    alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText("Error: " + e);
                    alertDialog.show();

                }

            }
        });

    }

    public void contactToAccount(String objectID) {

        alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitleText("Updating...");
        alertDialog.setCancelable(false);
        alertDialog.show();

        ParseQuery<ParseObject> query = new ParseQuery<>("Contacts");
        query.whereEqualTo("objectId", objectID);
        query.getInBackground(objectID, new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject object, ParseException e) {

                if (e == null) {

                    Contacts contact = new Contacts(

                            object.get(ContactsManager.NAME_KEY).toString(),
                            object.get(ContactsManager.EMAIL_ID_KEY).toString(),
                            Long.parseLong(object.get(ContactsManager.CONTACT_NO_KEY).toString()),
                            object.get(ContactsManager.COMPANY_NAME_KEY).toString(),
                            object.get(ContactsManager.PRIMARY_ADDRESS_KEY).toString(),
                            object.get(ContactsManager.PRIMARY_CITY_KEY).toString(),
                            object.get(ContactsManager.PRIMARY_STATE_KEY).toString(),
                            object.get(ContactsManager.PRIMARY_COUNTRY_KEY).toString(),
                            object.get(ContactsManager.CONTACT_OWNER_KEY).toString(),
                            object.get(ContactsManager.ADDITIONAL_INFORMATION_KEY).toString()

                    );

                    final Accounts account = new Accounts(

                            contact.getCompanyName(),
                            contact.getEmailId(),
                            contact.getContactNo(),
                            contact.getName(),
                            contact.getPrimaryAddress(),
                            contact.getPrimaryCity(),
                            contact.getPrimaryState(),
                            contact.getPrimaryCountry(),
                            contact.getContactOwner(),
                            contact.getAdditionalInformation()

                    );

                    final ParseObject objectAccount = new ParseObject("Accounts");

                    objectAccount.put(AccountsManager.NAME_KEY, account.getName());
                    objectAccount.put(AccountsManager.EMAIL_ID_KEY, account.getEmailId());
                    objectAccount.put(AccountsManager.CONTACT_NO_KEY, account.getContactNo());
                    objectAccount.put(AccountsManager.CONTACT_NAME_KEY, account.getContactName());
                    objectAccount.put(AccountsManager.PRIMARY_ADDRESS_KEY, account.getPrimaryAddress());
                    objectAccount.put(AccountsManager.PRIMARY_CITY_KEY, account.getPrimaryCity());
                    objectAccount.put(AccountsManager.PRIMARY_STATE_KEY, account.getPrimaryState());
                    objectAccount.put(AccountsManager.PRIMARY_COUNTRY_KEY, account.getPrimaryCountry());
                    objectAccount.put(AccountsManager.ACCOUNT_OWNER_KEY, account.getAccountOwner());
                    objectAccount.put(AccountsManager.ADDITIONAL_INFORMATION_KEY, account.getAdditionalInformation());

                    objectAccount.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {

                                alertDialog.dismissWithAnimation();

                                account.setObjectID(objectAccount.getObjectId());

                                alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Converted")
                                        .setContentText("Converted successfully...")
                                        .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                alertDialog.dismissWithAnimation();
                                                ((Activity) mContext).finish();
                                            }
                                        });
                                alertDialog.setCancelable(false);
                                alertDialog.show();

                            } else {

                                alertDialog.dismissWithAnimation();

                                alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Failed")
                                        .setContentText("Error: " + e);
                                alertDialog.show();

                            }
                        }
                    });

                } else {

                    alertDialog.dismissWithAnimation();

                    alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText("Error: " + e);
                    alertDialog.show();

                }

            }
        });

    }

    public void accountToOpportunity(String objectID) {

        alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitleText("Updating...");
        alertDialog.setCancelable(false);
        alertDialog.show();

        ParseQuery<ParseObject> query = new ParseQuery<>("Accounts");
        query.whereEqualTo("objectId", objectID);
        query.getInBackground(objectID, new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject object, ParseException e) {

                if (e == null) {

                    Accounts account = new Accounts(

                            object.get(AccountsManager.NAME_KEY).toString(),
                            object.get(AccountsManager.EMAIL_ID_KEY).toString(),
                            Long.parseLong(object.get(AccountsManager.CONTACT_NO_KEY).toString()),
                            object.get(AccountsManager.CONTACT_NAME_KEY).toString(),
                            object.get(AccountsManager.PRIMARY_ADDRESS_KEY).toString(),
                            object.get(AccountsManager.PRIMARY_CITY_KEY).toString(),
                            object.get(AccountsManager.PRIMARY_STATE_KEY).toString(),
                            object.get(AccountsManager.PRIMARY_COUNTRY_KEY).toString(),
                            object.get(AccountsManager.ACCOUNT_OWNER_KEY).toString(),
                            object.get(AccountsManager.ADDITIONAL_INFORMATION_KEY).toString()

                    );

                    final Opportunities opportunity = new Opportunities(

                            account.getName(),
                            account.getContactName(),
                            "itemName",
                            0,
                            account.getContactNo(),
                            account.getEmailId(),
                            account.getPrimaryAddress(),
                            account.getPrimaryCity(),
                            account.getPrimaryState(),
                            account.getPrimaryCountry(),
                            account.getAccountOwner(),
                            account.getAdditionalInformation()

                    );

                    final ParseObject objectOpportunity = new ParseObject("Opportunities");

                    objectOpportunity.put(OpportunitiesManager.ACCOUNT_NAME_KEY, opportunity.getAccountName());
                    objectOpportunity.put(OpportunitiesManager.CONTACT_NAME_KEY, opportunity.getContactName());
                    objectOpportunity.put(OpportunitiesManager.ITEM_NAME_KEY, opportunity.getItemName());
                    objectOpportunity.put(OpportunitiesManager.TARGET_AMOUNT_KEY, opportunity.getTargetAmount());
                    objectOpportunity.put(OpportunitiesManager.CONTACT_NO_KEY, opportunity.getContactNo());
                    objectOpportunity.put(OpportunitiesManager.EMAIL_ID_KEY, opportunity.getEmailId());
                    objectOpportunity.put(OpportunitiesManager.BILLING_ADDRESS_KEY, opportunity.getBillingAddress());
                    objectOpportunity.put(OpportunitiesManager.BILLING_CITY_KEY, opportunity.getBillingCity());
                    objectOpportunity.put(OpportunitiesManager.BILLING_STATE_KEY, opportunity.getBillingState());
                    objectOpportunity.put(OpportunitiesManager.BILLING_COUNTRY_KEY, opportunity.getBillingCountry());
                    objectOpportunity.put(OpportunitiesManager.OPPORTUNITY_OWNER_KEY, opportunity.getOpportunityOwner());
                    objectOpportunity.put(OpportunitiesManager.DESCRIPTION_KEY, opportunity.getDescription());

                    objectOpportunity.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {

                                alertDialog.dismissWithAnimation();

                                opportunity.setObjectID(objectOpportunity.getObjectId());

                                alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Converted")
                                        .setContentText("Converted successfully...")
                                        .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                alertDialog.dismissWithAnimation();
                                                ((Activity) mContext).finish();
                                            }
                                        });
                                alertDialog.setCancelable(false);
                                alertDialog.show();

                            } else {

                                alertDialog.dismissWithAnimation();

                                alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Failed")
                                        .setContentText("Error: " + e);
                                alertDialog.show();

                            }
                        }
                    });

                } else {

                    alertDialog.dismissWithAnimation();

                    alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText("Error: " + e);
                    alertDialog.show();

                }

            }
        });

    }

}


