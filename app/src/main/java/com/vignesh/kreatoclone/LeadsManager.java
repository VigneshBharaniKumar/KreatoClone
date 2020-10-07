package com.vignesh.kreatoclone;

import android.content.Context;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LeadsManager {

    private Context mContext;

    private SweetAlertDialog alertDialog;

    private Boolean status = false;

    public ArrayList<Leads> getLead;

    private ParseObject object = new ParseObject("Leads");

    private static final String NAME_KEY = "name";
    private static final String EMAIL_ID_KEY = "emailId";
    private static final String CONTACT_NO_KEY = "contactNo";
    private static final String COMPANY_NAME_KEY = "companyName";
    private static final String PRIMARY_ADDRESS_KEY = "primaryAddress";
    private static final String PRIMARY_CITY_KEY = "primaryCity";
    private static final String PRIMARY_STATE_KEY = "primaryState";
    private static final String PRIMARY_COUNTRY_KEY = "primaryCountry";
    private static final String LEAD_OWNER_KEY = "leadOwner";
    private static final String CO_OWNER_KEY = "coOwner";
    private static final String ADDITIONAL_INFORMATION_KEY = "additionalInformation";

    public LeadsManager(Context mContext) {
        this.mContext = mContext;
    }

    public boolean saveLead(final Leads lead) {

        alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitleText("Updating...");
        alertDialog.setCancelable(false);
        alertDialog.show();

        object.put(NAME_KEY, lead.getName());
        object.put(EMAIL_ID_KEY, lead.getEmailId());
        object.put(CONTACT_NO_KEY, lead.getContactNo());
        object.put(COMPANY_NAME_KEY, lead.getCompanyName());
        object.put(PRIMARY_ADDRESS_KEY, lead.getPrimaryAddress());
        object.put(PRIMARY_CITY_KEY, lead.getPrimaryCity());
        object.put(PRIMARY_STATE_KEY, lead.getPrimaryState());
        object.put(PRIMARY_COUNTRY_KEY, lead.getPrimaryCountry());
        object.put(LEAD_OWNER_KEY, lead.getLeadOwner());
        object.put(CO_OWNER_KEY, lead.getCoOwner());
        object.put(ADDITIONAL_INFORMATION_KEY, lead.getAdditionalInformation());

        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {

                    alertDialog.dismissWithAnimation();

                    lead.setObjectID(object.getObjectId());

                    alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Lead Saved")
                            .setContentText("Lead Info saved successfully...")
                            .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    alertDialog.dismissWithAnimation();
                                }
                            });
                    alertDialog.show();

                    status = true;

                } else {

                    alertDialog.dismissWithAnimation();

                    alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText("Error: " + e);
                    alertDialog.show();

                    status = false;

                }
            }
        });

        return status;

    }

    /*public Leads getSpecificLead(Leads lead) {

        alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitleText("Updating...");
        alertDialog.setCancelable(false);
        alertDialog.show();

        final Leads leadInfo;

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Leads");
        query.whereEqualTo("objectId", lead.getObjectID());
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {

                    alertDialog.dismissWithAnimation();

*//*                    leadInfo = new Leads(
                            object.get(NAME_KEY).toString(),
                            object.get(EMAIL_ID_KEY).toString(),
                            Long.parseLong(object.get(CONTACT_NO_KEY).toString()),
                            object.get(COMPANY_NAME_KEY).toString(),
                            object.get(PRIMARY_ADDRESS_KEY).toString(),
                            object.get(PRIMARY_CITY_KEY).toString(),
                            object.get(PRIMARY_STATE_KEY).toString(),
                            object.get(PRIMARY_COUNTRY_KEY).toString(),
                            object.get(LEAD_OWNER_KEY).toString(),
                            object.get(CO_OWNER_KEY).toString(),
                            object.get(ADDITIONAL_INFORMATION_KEY).toString()
                    );*//*

                } else {

                    alertDialog.dismissWithAnimation();

                    alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText("Error: " + e);
                    alertDialog.show();

                    status = false;

                }
            }
        });

    }*/

    /*public ArrayList<Leads> getLeads() {

        alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitleText("Updating...");
        alertDialog.setCancelable(false);
        alertDialog.show();

        getLead = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Leads");
        query.whereNotEqualTo(NAME_KEY, "");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> object, ParseException e) {
                if (e == null) {

                    ArrayList<Leads> temp = new ArrayList<>();

                    alertDialog.dismissWithAnimation();

                    for (ParseObject obj : object) {

                        Leads lead = new Leads(

                                obj.get(NAME_KEY).toString(),
                                obj.get(EMAIL_ID_KEY).toString(),
                                Long.parseLong(obj.get(CONTACT_NO_KEY).toString()),
                                obj.get(COMPANY_NAME_KEY).toString(),
                                obj.get(PRIMARY_ADDRESS_KEY).toString(),
                                obj.get(PRIMARY_CITY_KEY).toString(),
                                obj.get(PRIMARY_STATE_KEY).toString(),
                                obj.get(PRIMARY_COUNTRY_KEY).toString(),
                                obj.get(LEAD_OWNER_KEY).toString(),
                                obj.get(CO_OWNER_KEY).toString(),
                                obj.get(ADDITIONAL_INFORMATION_KEY).toString()

                        );

                        temp.add(lead);

                    }

                    getLead = temp;
                    Toast.makeText(mContext, getLead.size() + "", Toast.LENGTH_SHORT).show();

                } else {

                    alertDialog.dismissWithAnimation();

                    alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText("Error: " + e);
                    alertDialog.show();

                }
            }
        });

        return getLead;

    }*/

}
