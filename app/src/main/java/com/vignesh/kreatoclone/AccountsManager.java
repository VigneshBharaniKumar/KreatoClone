package com.vignesh.kreatoclone;

import android.app.Activity;
import android.content.Context;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AccountsManager {

    private Context mContext;

    private SweetAlertDialog alertDialog;

    private ParseObject object = new ParseObject("Accounts");

    public static final String NAME_KEY = "name";
    public static final String EMAIL_ID_KEY = "emailId";
    public static final String CONTACT_NO_KEY = "contactNo";
    public static final String CONTACT_NAME_KEY = "contactName";
    public static final String PRIMARY_ADDRESS_KEY = "primaryAddress";
    public static final String PRIMARY_CITY_KEY = "primaryCity";
    public static final String PRIMARY_STATE_KEY = "primaryState";
    public static final String PRIMARY_COUNTRY_KEY = "primaryCountry";
    public static final String ACCOUNT_OWNER_KEY = "accountOwner";
    public static final String ADDITIONAL_INFORMATION_KEY = "additionalInformation";

    public AccountsManager(Context mContext) {
        this.mContext = mContext;
    }

    public void saveAccount (final Accounts account) {

        alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitleText("Updating...");
        alertDialog.setCancelable(false);
        alertDialog.show();

        object.put(NAME_KEY, account.getName());
        object.put(EMAIL_ID_KEY, account.getEmailId());
        object.put(CONTACT_NO_KEY, account.getContactNo());
        object.put(CONTACT_NAME_KEY, account.getContactName());
        object.put(PRIMARY_ADDRESS_KEY, account.getPrimaryAddress());
        object.put(PRIMARY_CITY_KEY, account.getPrimaryCity());
        object.put(PRIMARY_STATE_KEY, account.getPrimaryState());
        object.put(PRIMARY_COUNTRY_KEY, account.getPrimaryCountry());
        object.put(ACCOUNT_OWNER_KEY, account.getAccountOwner());
        object.put(ADDITIONAL_INFORMATION_KEY, account.getAdditionalInformation());

        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {

                    alertDialog.dismissWithAnimation();

                    account.setObjectID(object.getObjectId());

                    alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText(account.getName()+ " Saved")
                            .setContentText(account.getName()+" Info saved successfully...")
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
