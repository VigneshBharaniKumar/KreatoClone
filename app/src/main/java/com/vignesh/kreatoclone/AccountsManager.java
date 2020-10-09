package com.vignesh.kreatoclone;

import android.content.Context;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AccountsManager {

    private Context mContext;

    private SweetAlertDialog alertDialog;

    private ParseObject object = new ParseObject("Accounts");

    private static final String NAME_KEY = "name";
    private static final String EMAIL_ID_KEY = "emailId";
    private static final String CONTACT_NO_KEY = "contactNo";
    private static final String CONTACT_NAME_KEY = "contactName";
    private static final String PRIMARY_ADDRESS_KEY = "primaryAddress";
    private static final String PRIMARY_CITY_KEY = "primaryCity";
    private static final String PRIMARY_STATE_KEY = "primaryState";
    private static final String PRIMARY_COUNTRY_KEY = "primaryCountry";
    private static final String ACCOUNT_OWNER_KEY = "accountOwner";
    private static final String ADDITIONAL_INFORMATION_KEY = "additionalInformation";

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

                    /*Todo
                    *  Alert Dialog is not displayed, getting run time error
                    *  please check on this*/
                    alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText(account.getName()+ " Saved")
                            .setContentText(account.getName()+" Info saved successfully...")
                            .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    alertDialog.dismissWithAnimation();
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
