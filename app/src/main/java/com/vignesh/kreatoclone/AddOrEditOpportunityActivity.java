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

public class AddOrEditOpportunityActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;

    private SweetAlertDialog alertDialog;

    private OpportunitiesManager mOpportunitiesManager;
    private Opportunities opportunity;

    private String selectedOpportunityObjectID = null;

    private static final String ACCOUNT_NAME_KEY = "accountName";
    private static final String CONTACT_NAME_KEY = "contactName";
    private static final String ITEM_NAME_KEY = "itemName";
    private static final String TARGET_AMOUNT_KEY = "targetAmount";
    private static final String CONTACT_NO_KEY = "contactNo";
    private static final String EMAIL_ID_KEY = "emailId";
    private static final String BILLING_ADDRESS_KEY = "billingAddress";
    private static final String BILLING_CITY_KEY = "billingCity";
    private static final String BILLING_STATE_KEY = "billingState";
    private static final String BILLING_COUNTRY_KEY = "billingCountry";
    private static final String OPPORTUNITY_OWNER_KEY = "opportunityOwner";
    private static final String DESCRIPTION_KEY = "description";

    private TextInputLayout itemName;
    private TextInputLayout targetAmount;
    private TextInputLayout accountName;
    private TextInputLayout contactName;
    private TextInputLayout contactNo;
    private TextInputLayout emailId;
    private TextInputLayout billingAddress;
    private TextInputLayout billingCity;
    private TextInputLayout billingState;
    private TextInputLayout billingCountry;
    private TextInputLayout opportunityOwner;
    private TextInputLayout description;

    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_opportunity);

        toolbar = findViewById(R.id.toolbar);
        btnUpdate = findViewById(R.id.btnUpdate_addOrEdit_opportunity);

        itemName = findViewById(R.id.edtItemName_opportunity_add);
        targetAmount = findViewById(R.id.edtTargetAmount_opportunity_add);
        accountName = findViewById(R.id.edtAccountName_opportunity_add);
        contactName = findViewById(R.id.edtContactName_opportunity_add);
        contactNo = findViewById(R.id.edtContactNo_opportunity_add);
        emailId = findViewById(R.id.edtEmailId_opportunity_add);
        billingAddress = findViewById(R.id.edtBillingAddress_opportunity_add);
        billingCity = findViewById(R.id.edtBillingCity_opportunity_add);
        billingState = findViewById(R.id.edtBillingState_opportunity_add);
        billingCountry = findViewById(R.id.edtBillingCountry_account_add);
        opportunityOwner = findViewById(R.id.edtOpportunityOwner_opportunity_add);
        description = findViewById(R.id.edtDescription_opportunity_add);

        selectedOpportunityObjectID = getIntent().getStringExtra("selectedOpportunityObjectID");

        mOpportunitiesManager = new OpportunitiesManager(this);

        setTitle("Add new Opportunity");

        setSupportActionBar(toolbar);

        if (selectedOpportunityObjectID != null) {
            putDataToUI(selectedOpportunityObjectID);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedOpportunityObjectID == null) {
                    addNewOpportunity();
                } else {
                    updateExistingOpportunity(selectedOpportunityObjectID);
                }
            }
        });

    }

    private void addNewOpportunity() {

        mOpportunitiesManager.saveOpportunity(getDataFromUI());

    }

    private void putDataToUI(final String objectID) {

        alertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitleText("Updating...");
        alertDialog.setCancelable(false);
        alertDialog.show();

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Opportunities");
        query.getInBackground(objectID, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {

                if (e == null) {

                    alertDialog.dismissWithAnimation();

                    Opportunities selectedOpportunity = new Opportunities(

                            object.get(ACCOUNT_NAME_KEY).toString(),
                            object.get(CONTACT_NAME_KEY).toString(),
                            object.get(ITEM_NAME_KEY).toString(),
                            Long.parseLong(object.get(TARGET_AMOUNT_KEY).toString()),
                            Long.parseLong(object.get(CONTACT_NO_KEY).toString()),
                            object.get(EMAIL_ID_KEY).toString(),
                            object.get(BILLING_ADDRESS_KEY).toString(),
                            object.get(BILLING_CITY_KEY).toString(),
                            object.get(BILLING_STATE_KEY).toString(),
                            object.get(BILLING_COUNTRY_KEY).toString(),
                            object.get(OPPORTUNITY_OWNER_KEY).toString(),
                            object.get(DESCRIPTION_KEY).toString()

                    );

                    selectedOpportunity.setObjectID(object.getObjectId());

                    itemName.getEditText().setText(selectedOpportunity.getItemName());
                    targetAmount.getEditText().setText(selectedOpportunity.getTargetAmount() + "");
                    accountName.getEditText().setText(selectedOpportunity.getAccountName());
                    contactName.getEditText().setText(selectedOpportunity.getContactName());
                    contactNo.getEditText().setText(selectedOpportunity.getContactNo() + "");
                    emailId.getEditText().setText(selectedOpportunity.getEmailId());
                    billingAddress.getEditText().setText(selectedOpportunity.getBillingAddress());
                    billingCity.getEditText().setText(selectedOpportunity.getBillingCity());
                    billingState.getEditText().setText(selectedOpportunity.getBillingState());
                    billingCountry.getEditText().setText(selectedOpportunity.getBillingCountry());
                    opportunityOwner.getEditText().setText(selectedOpportunity.getOpportunityOwner());
                    description.getEditText().setText(selectedOpportunity.getDescription());

                } else {

                    alertDialog.dismissWithAnimation();

                    alertDialog = new SweetAlertDialog(AddOrEditOpportunityActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText("Error: " + e);
                    alertDialog.show();

                }
            }

        });

    }

    private Opportunities getDataFromUI() {

        opportunity = new Opportunities(

                accountName.getEditText().getText().toString(),
                contactName.getEditText().getText().toString(),
                itemName.getEditText().getText().toString(),
                Long.parseLong(targetAmount.getEditText().getText().toString()),
                Long.parseLong(contactNo.getEditText().getText().toString()),
                emailId.getEditText().getText().toString(),
                billingAddress.getEditText().getText().toString(),
                billingCity.getEditText().getText().toString(),
                billingState.getEditText().getText().toString(),
                billingCountry.getEditText().getText().toString(),
                opportunityOwner.getEditText().getText().toString(),
                description.getEditText().getText().toString()

        );

        return opportunity;

    }

    private void updateExistingOpportunity (String selectedOpportunityObjectID) {

        alertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitleText("Updating...");
        alertDialog.setCancelable(false);
        alertDialog.show();

        final Opportunities opportunity = getDataFromUI();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Opportunities");
        query.getInBackground(selectedOpportunityObjectID, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {

                if (e == null) {

                    alertDialog.dismissWithAnimation();

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

                    object.saveInBackground();

                    alertDialog = new SweetAlertDialog(AddOrEditOpportunityActivity.this, SweetAlertDialog.SUCCESS_TYPE)
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

                    alertDialog = new SweetAlertDialog(AddOrEditOpportunityActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText("Error: " + e);
                    alertDialog.show();

                }
            }
        });

    }

}
