package com.vignesh.kreatoclone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputLayout;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddOrEditLeadsActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private MaterialToolbar toolbar;
    private TextView toolbarTitle;

    private SweetAlertDialog alertDialog;

    private LeadsManager mLeadManager;
    private Leads lead;

    private String selectedLeadObjectID = null;

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

    private TextInputLayout name;
    private TextInputLayout emailId;
    private TextInputLayout contactNo;
    private TextInputLayout companyName;
    private TextInputLayout primaryAddress;
    private TextInputLayout primaryCity;
    private TextInputLayout primaryState;
    private TextInputLayout primaryCountry;
    private TextInputLayout leadOwner;
    private TextInputLayout coOwner;
    private TextInputLayout additionalInformation;

    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_leads);

        linearLayout = findViewById(R.id.linearLayoutAddOrEdit);
        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);

        btnUpdate = findViewById(R.id.btnUpdate_addOrEdit_leads);

        name = findViewById(R.id.edtName_lead_add);
        emailId = findViewById(R.id.edtEmailId_lead_add);
        contactNo = findViewById(R.id.edtContactNo_lead_add);
        companyName = findViewById(R.id.edtCompanyName_lead_add);
        primaryAddress = findViewById(R.id.edtPrimaryAddress_lead_add);
        primaryCity = findViewById(R.id.edtPrimaryCity_lead_add);
        primaryState = findViewById(R.id.edtPrimaryState_lead_add);
        primaryCountry = findViewById(R.id.edtPrimaryCountry_lead_add);
        leadOwner = findViewById(R.id.edtLeadOwner_lead_add);
        coOwner = findViewById(R.id.edtCoOwner_lead_add);
        additionalInformation = findViewById(R.id.edtAdditionalInformation_lead_add);

        selectedLeadObjectID = getIntent().getStringExtra("selectedLeadObjectID");

        mLeadManager = new LeadsManager(this);

        setTitle("Add New Lead");

        setSupportActionBar(toolbar);

        if (selectedLeadObjectID != null) {

            putDataToUI(selectedLeadObjectID);

        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedLeadObjectID == null) {
                    addNewLead();
                    finish();
                } else if (selectedLeadObjectID != null) {
                    updateExistingLead(selectedLeadObjectID);
                    //finish();
                }
            }
        });

        /*Dynamic Field Creation*/
/*        for (Field field : lead.getClass().getDeclaredFields()) {

            View view = LayoutInflater.from(this).inflate(R.layout.temp_normal_input_text_layout, null);

            TextInputLayout textInputLayout = view.findViewById(R.id.temp_normal_text_input_layout);
            TextInputEditText textInputEditText = view.findViewById(R.id.temp_normal_text_input_editText);

            textInputLayout.setHint(field.getName());

            linearLayout.addView(view);

        }


        View view = LayoutInflater.from(this).inflate(R.layout.temp_normal_input_text_layout, null);

        TextInputLayout textInputLayout = view.findViewById(R.id.temp_normal_text_input_layout);
        TextInputEditText textInputEditText = view.findViewById(R.id.temp_normal_text_input_editText);

        textInputLayout.setHint("This is an Example...");

        linearLayout.addView(view);*/

    }

    private void putDataToUI(String objectID) {

        alertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitleText("Updating...");
        alertDialog.setCancelable(false);
        alertDialog.show();

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Leads");
        query.getInBackground(objectID, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {

                    alertDialog.dismissWithAnimation();

                    Leads selectedLead = new Leads(

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


                    );

                    name.getEditText().setText(selectedLead.getName());
                    emailId.getEditText().setText(selectedLead.getEmailId());
                    contactNo.getEditText().setText(selectedLead.getContactNo() + "");
                    companyName.getEditText().setText(selectedLead.getCompanyName());
                    primaryAddress.getEditText().setText(selectedLead.getPrimaryAddress());
                    primaryCity.getEditText().setText(selectedLead.getPrimaryCity());
                    primaryState.getEditText().setText(selectedLead.getPrimaryState());
                    primaryCountry.getEditText().setText(selectedLead.getPrimaryCountry());
                    leadOwner.getEditText().setText(selectedLead.getLeadOwner());
                    coOwner.getEditText().setText(selectedLead.getCoOwner());
                    additionalInformation.getEditText().setText(selectedLead.getAdditionalInformation());

                } else {

                    alertDialog.dismissWithAnimation();

                    alertDialog = new SweetAlertDialog(AddOrEditLeadsActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText("Error: " + e);
                    alertDialog.show();

                }
            }
        });

    }

    private Leads getDataFromUI() {

        String name = this.name.getEditText().getText().toString();
        String emailId = this.emailId.getEditText().getText().toString();
        long contactNo = Long.parseLong(this.contactNo.getEditText().getText().toString().trim());
        String companyName = this.companyName.getEditText().getText().toString();
        String primaryAddress = this.primaryAddress.getEditText().getText().toString();
        String primaryCity = this.primaryCity.getEditText().getText().toString();
        String primaryState = this.primaryState.getEditText().getText().toString();
        String primaryCountry = this.primaryCountry.getEditText().getText().toString();
        String leadOwner = this.leadOwner.getEditText().getText().toString();
        String coOwner = this.coOwner.getEditText().getText().toString();
        String additionalInformation = this.additionalInformation.getEditText().getText().toString();

        lead = new Leads(name, emailId, contactNo, companyName, primaryAddress, primaryCity, primaryState,
                primaryCountry, leadOwner, coOwner, additionalInformation);

        return lead;

    }

    private void addNewLead() {

        mLeadManager.saveLead(getDataFromUI());

    }

    private void updateExistingLead(String selectedLeadObjectID) {

        alertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitleText("Updating...");
        alertDialog.setCancelable(false);
        alertDialog.show();

        final Leads lead = getDataFromUI();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Leads");
        query.getInBackground(selectedLeadObjectID, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {

                if (e == null) {

                    alertDialog.dismissWithAnimation();

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

                    object.saveInBackground();

                    alertDialog = new SweetAlertDialog(AddOrEditLeadsActivity.this, SweetAlertDialog.SUCCESS_TYPE)
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

                    alertDialog = new SweetAlertDialog(AddOrEditLeadsActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText("Error: " + e);
                    alertDialog.show();

                }
            }
        });

    }

}
