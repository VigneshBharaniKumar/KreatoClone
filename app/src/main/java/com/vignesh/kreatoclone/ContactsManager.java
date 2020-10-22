package com.vignesh.kreatoclone;

import android.app.Activity;
import android.content.Context;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ContactsManager {

    private Context mContext;

    private SweetAlertDialog alertDialog;

    private ParseObject object = new ParseObject("Contacts");

    public static final String NAME_KEY = "name";
    public static final String EMAIL_ID_KEY = "emailId";
    public static final String CONTACT_NO_KEY = "contactNo";
    public static final String COMPANY_NAME_KEY = "companyName";
    public static final String PRIMARY_ADDRESS_KEY = "primaryAddress";
    public static final String PRIMARY_CITY_KEY = "primaryCity";
    public static final String PRIMARY_STATE_KEY = "primaryState";
    public static final String PRIMARY_COUNTRY_KEY = "primaryCountry";
    public static final String CONTACT_OWNER_KEY = "contactOwner";
    public static final String ADDITIONAL_INFORMATION_KEY = "additionalInformation";

    public ContactsManager(Context mContext) {
        this.mContext = mContext;
    }

    public void saveContact (final Contacts contact) {

        alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitleText("Updating...");
        alertDialog.setCancelable(false);
        alertDialog.show();

        object.put(NAME_KEY, contact.getName());
        object.put(EMAIL_ID_KEY, contact.getEmailId());
        object.put(CONTACT_NO_KEY, contact.getContactNo());
        object.put(COMPANY_NAME_KEY, contact.getCompanyName());
        object.put(PRIMARY_ADDRESS_KEY, contact.getPrimaryAddress());
        object.put(PRIMARY_CITY_KEY, contact.getPrimaryCity());
        object.put(PRIMARY_STATE_KEY, contact.getPrimaryState());
        object.put(PRIMARY_COUNTRY_KEY, contact.getPrimaryCountry());
        object.put(CONTACT_OWNER_KEY, contact.getContactOwner());
        object.put(ADDITIONAL_INFORMATION_KEY, contact.getAdditionalInformation());

        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {

                    alertDialog.dismissWithAnimation();

                    contact.setObjectID(object.getObjectId());

                    alertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText(contact.getName()+ " Saved")
                            .setContentText(contact.getName()+" Info saved successfully...")
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
