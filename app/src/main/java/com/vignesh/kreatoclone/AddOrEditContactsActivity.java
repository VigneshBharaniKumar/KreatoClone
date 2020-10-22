package com.vignesh.kreatoclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputLayout;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddOrEditContactsActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;

    private SweetAlertDialog alertDialog;

    private ContactsManager mContactsManager;
    private Contacts contact;

    private String selectedContactObjectID = null;

    private static final String NAME_KEY = "name";
    private static final String EMAIL_ID_KEY = "emailId";
    private static final String CONTACT_NO_KEY = "contactNo";
    private static final String COMPANY_NAME_KEY = "companyName";
    private static final String PRIMARY_ADDRESS_KEY = "primaryAddress";
    private static final String PRIMARY_CITY_KEY = "primaryCity";
    private static final String PRIMARY_STATE_KEY = "primaryState";
    private static final String PRIMARY_COUNTRY_KEY = "primaryCountry";
    private static final String CONTACT_OWNER_KEY = "contactOwner";
    private static final String ADDITIONAL_INFORMATION_KEY = "additionalInformation";

    private TextInputLayout name;
    private TextInputLayout emailId;
    private TextInputLayout contactNo;
    private TextInputLayout companyName;
    private TextInputLayout primaryAddress;
    private TextInputLayout primaryCity;
    private TextInputLayout primaryState;
    private TextInputLayout primaryCountry;
    private TextInputLayout contactOwner;
    private TextInputLayout additionalInformation;

    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_contacts);

        toolbar = findViewById(R.id.toolbar);
        btnUpdate = findViewById(R.id.btnUpdate_addOrEdit_contacts);

        name = findViewById(R.id.edtName_contact_add);
        emailId = findViewById(R.id.edtEmailId_contact_add);
        contactNo = findViewById(R.id.edtContactNo_contact_add);
        companyName = findViewById(R.id.edtCompanyName_contact_add);
        primaryAddress = findViewById(R.id.edtPrimaryAddress_contact_add);
        primaryCity = findViewById(R.id.edtPrimaryCity_contact_add);
        primaryState = findViewById(R.id.edtPrimaryState_contact_add);
        primaryCountry = findViewById(R.id.edtPrimaryCountry_contact_add);
        contactOwner = findViewById(R.id.edtContactOwner_contact_add);
        additionalInformation = findViewById(R.id.edtAdditionalInformation_contact_add);

        selectedContactObjectID = getIntent().getStringExtra("selectedContactObjectID");

        mContactsManager = new ContactsManager(this);

        setTitle("Add new Contact");

        setSupportActionBar(toolbar);

        if (selectedContactObjectID != null) {

            putDataToUI(selectedContactObjectID);

        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedContactObjectID == null) {
                    addNewContact();
                } else {
                    updateExistingContact(selectedContactObjectID);
                }
            }
        });

    }

    private void addNewContact() {

        mContactsManager.saveContact(getDataFromUI());

    }

    private void putDataToUI(String objectID) {

        alertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitleText("Updating...");
        alertDialog.setCancelable(false);
        alertDialog.show();

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Contacts");
        query.getInBackground(objectID, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {

                    alertDialog.dismissWithAnimation();

                    Contacts selectedContact= new Contacts(

                            object.get(NAME_KEY).toString(),
                            object.get(EMAIL_ID_KEY).toString(),
                            Long.parseLong(object.get(CONTACT_NO_KEY).toString()),
                            object.get(COMPANY_NAME_KEY).toString(),
                            object.get(PRIMARY_ADDRESS_KEY).toString(),
                            object.get(PRIMARY_CITY_KEY).toString(),
                            object.get(PRIMARY_STATE_KEY).toString(),
                            object.get(PRIMARY_COUNTRY_KEY).toString(),
                            object.get(CONTACT_OWNER_KEY).toString(),
                            object.get(ADDITIONAL_INFORMATION_KEY).toString()


                    );

                    name.getEditText().setText(selectedContact.getName());
                    emailId.getEditText().setText(selectedContact.getEmailId());
                    contactNo.getEditText().setText(selectedContact.getContactNo() + "");
                    companyName.getEditText().setText(selectedContact.getCompanyName());
                    primaryAddress.getEditText().setText(selectedContact.getPrimaryAddress());
                    primaryCity.getEditText().setText(selectedContact.getPrimaryCity());
                    primaryState.getEditText().setText(selectedContact.getPrimaryState());
                    primaryCountry.getEditText().setText(selectedContact.getPrimaryCountry());
                    contactOwner.getEditText().setText(selectedContact.getContactOwner());
                    additionalInformation.getEditText().setText(selectedContact.getAdditionalInformation());

                } else {

                    alertDialog.dismissWithAnimation();

                    alertDialog = new SweetAlertDialog(AddOrEditContactsActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText("Error: " + e);
                    alertDialog.show();

                }
            }
        });

    }

    private Contacts getDataFromUI() {

        String name = this.name.getEditText().getText().toString();
        String emailId = this.emailId.getEditText().getText().toString();
        long contactNo = Long.parseLong(this.contactNo.getEditText().getText().toString().trim());
        String companyName = this.companyName.getEditText().getText().toString();
        String primaryAddress = this.primaryAddress.getEditText().getText().toString();
        String primaryCity = this.primaryCity.getEditText().getText().toString();
        String primaryState = this.primaryState.getEditText().getText().toString();
        String primaryCountry = this.primaryCountry.getEditText().getText().toString();
        String contactOwner = this.contactOwner.getEditText().getText().toString();
        String additionalInformation = this.additionalInformation.getEditText().getText().toString();

        contact = new Contacts(name, emailId, contactNo, companyName, primaryAddress, primaryCity, primaryState,
                primaryCountry, contactOwner, additionalInformation);

        return contact;

    }

    private void updateExistingContact (String selectedContactObjectID) {

        alertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitleText("Updating...");
        alertDialog.setCancelable(false);
        alertDialog.show();

        final Contacts contact = getDataFromUI();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Contacts");
        query.getInBackground(selectedContactObjectID, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {

                if (e == null) {

                    alertDialog.dismissWithAnimation();

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

                    object.saveInBackground();

                    alertDialog = new SweetAlertDialog(AddOrEditContactsActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Updated Successfully")
                            .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    finish();
                                }
                            });
                    alertDialog.setCancelable(false);
                    alertDialog.show();

                } else {

                    alertDialog.dismissWithAnimation();

                    alertDialog = new SweetAlertDialog(AddOrEditContactsActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText("Error: " + e);
                    alertDialog.show();

                }
            }
        });

    }

}
