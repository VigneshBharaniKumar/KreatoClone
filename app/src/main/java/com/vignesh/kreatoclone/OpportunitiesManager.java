package com.vignesh.kreatoclone;

import android.app.Activity;
import android.content.Context;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class OpportunitiesManager {

    private Context mContext;

    private SweetAlertDialog alertDialog;

    private ParseObject object = new ParseObject("Opportunities");

    public static final String ACCOUNT_NAME_KEY = "accountName";
    public static final String CONTACT_NAME_KEY = "contactName";
    public static final String ITEM_NAME_KEY = "itemName";
    public static final String TARGET_AMOUNT_KEY = "targetAmount";
    public static final String CONTACT_NO_KEY = "contactNo";
    public static final String EMAIL_ID_KEY = "emailId";
    public static final String BILLING_ADDRESS_KEY = "billingAddress";
    public static final String BILLING_CITY_KEY = "billingCity";
    public static final String BILLING_STATE_KEY = "billingState";
    public static final String BILLING_COUNTRY_KEY = "billingCountry";
    public static final String OPPORTUNITY_OWNER_KEY = "opportunityOwner";
    public static final String DESCRIPTION_KEY = "description";

    public OpportunitiesManager(Context mContext) {
        this.mContext = mContext;
    }

    public void saveOpportunity (final Opportunities opportunity) {

        alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitleText("Updating...");
        alertDialog.setCancelable(false);
        alertDialog.show();

        object.put(ACCOUNT_NAME_KEY, opportunity.getAccountName());
        object.put(CONTACT_NAME_KEY, opportunity.getContactName());
        object.put(ITEM_NAME_KEY, opportunity.getItemName());
        object.put(TARGET_AMOUNT_KEY, opportunity.getTargetAmount());
        object.put(CONTACT_NO_KEY, opportunity.getContactNo());
        object.put(EMAIL_ID_KEY, opportunity.getEmailId());
        object.put(BILLING_ADDRESS_KEY, opportunity.getBillingAddress());
        object.put(BILLING_CITY_KEY, opportunity.getBillingCity());
        object.put(BILLING_STATE_KEY, opportunity.getBillingState());
        object.put(BILLING_COUNTRY_KEY, opportunity.getBillingCountry());
        object.put(OPPORTUNITY_OWNER_KEY, opportunity.getOpportunityOwner());
        object.put(DESCRIPTION_KEY, opportunity.getDescription());

        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {

                    alertDialog.dismissWithAnimation();

                    opportunity.setObjectID(object.getObjectId());

                    alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText(opportunity.getItemName()+ " Saved")
                            .setContentText(opportunity.getItemName()+" Info saved successfully...")
                            .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    alertDialog.dismissWithAnimation();
                                    ((Activity) mContext).finish();
                                }
                            });
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

    }

}
