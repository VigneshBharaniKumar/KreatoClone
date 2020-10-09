package com.vignesh.kreatoclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class OpportunitiesOverviewActivity extends AppCompatActivity {

    private String selectedOpportunityObjectID;

    private Opportunities selectedOpportunity;

    private TextView tvOpportunityID, tvItemName, tvTargetAmount, tvAccountName, tvContactName, tvContactNo, tvEmailId
            ,tvBillingAddress, tvBillingCity, tvBillingState, tvBillingCountry, tvOpportunityOwner, tvDescription;

    private OpportunitiesManager mOpportunitiesManager;

    private SweetAlertDialog alertDialog;

    private MaterialToolbar toolbar;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opportunities_overview);

        selectedOpportunityObjectID = getIntent().getStringExtra("selectedOpportunityObjectID");

        tvOpportunityID = findViewById(R.id.tvOpportunityID_opportunity_overview);
        tvItemName = findViewById(R.id.tvItemName_opportunity_overview);
        tvTargetAmount = findViewById(R.id.tvTargetAmount_opportunity_overview);
        tvAccountName = findViewById(R.id.tvAccountName_opportunity_overview);
        tvContactName = findViewById(R.id.tvContactName_opportunity_overview);
        tvContactNo = findViewById(R.id.tvContactNo_opportunity_overview);
        tvEmailId = findViewById(R.id.tvEmailId_opportunity_overview);
        tvBillingAddress = findViewById(R.id.tvBillingAddress_opportunity_overview);
        tvBillingCity = findViewById(R.id.tvBillingCity_opportunity_overview);
        tvBillingState = findViewById(R.id.tvBillingState_opportunity_overview);
        tvBillingCountry = findViewById(R.id.tvBillingCountry_opportunity_overview);
        tvOpportunityOwner = findViewById(R.id.tvOpportunityOwner_opportunity_overview);
        tvDescription = findViewById(R.id.tvDescription_opportunity_overview);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.btnEdit_recordsOverview_menu:
                        Intent intent = new Intent(OpportunitiesOverviewActivity.this, AddOrEditOpportunityActivity.class);
                        intent.putExtra("selectedOpportunityObjectID", selectedOpportunityObjectID);
                        startActivity(intent);
                        break;

                    case R.id.btnDelete_recordsOverview_menu:
                        alertDialog = new SweetAlertDialog(OpportunitiesOverviewActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Are you sure want to Delete?")
                                .setConfirmButton("Delete", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        deleteAccount(selectedOpportunityObjectID);
                                    }
                                })
                                .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        alertDialog.dismissWithAnimation();
                                    }
                                });
                        alertDialog.setCancelable(false);
                        alertDialog.show();
                        break;

                }

                return true;
            }
        });

    }

    private void getDataFromServer() {

        alertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitleText("Updating...");
        alertDialog.setCancelable(false);
        alertDialog.show();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Opportunities");
        query.getInBackground(selectedOpportunityObjectID, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {

                    alertDialog.dismissWithAnimation();

                    selectedOpportunity = new Opportunities(

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
                    setDataToTV(selectedOpportunity);

                } else {

                    alertDialog.dismissWithAnimation();

                    alertDialog = new SweetAlertDialog(OpportunitiesOverviewActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText("Error: " + e);
                    alertDialog.show();

                }

            }
        });


    }

    private void setDataToTV(Opportunities selectedOpportunity) {

        setTitle("Sales - " + selectedOpportunity.getItemName());

        tvOpportunityID.setText(selectedOpportunity.getObjectID());
        tvItemName.setText(selectedOpportunity.getItemName());
        tvTargetAmount.setText(selectedOpportunity.getTargetAmount()+"");
        tvAccountName.setText(selectedOpportunity.getAccountName());
        tvContactName.setText(selectedOpportunity.getContactName());
        tvContactNo.setText(selectedOpportunity.getContactNo()+"");
        tvEmailId.setText(selectedOpportunity.getEmailId());
        tvBillingAddress.setText(selectedOpportunity.getBillingAddress());
        tvBillingCity.setText(selectedOpportunity.getBillingCity());
        tvBillingState.setText(selectedOpportunity.getBillingState());
        tvBillingCountry.setText(selectedOpportunity.getBillingCountry());
        tvOpportunityOwner.setText(selectedOpportunity.getOpportunityOwner());
        tvDescription.setText(selectedOpportunity.getDescription());

    }

    private void deleteAccount(String selectedOpportunityObjectID) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Opportunities");
        query.getInBackground(selectedOpportunityObjectID, new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject object, ParseException e) {
                if (e == null){

                    object.deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {

                                alertDialog = new SweetAlertDialog(OpportunitiesOverviewActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText(object.get(ITEM_NAME_KEY) + " deleted successfully")
                                        .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                finish();
                                            }
                                        });
                                alertDialog.setCancelable(false);
                                alertDialog.show();

                            } else {

                                alertDialog = new SweetAlertDialog(OpportunitiesOverviewActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setContentText("Error : " + e);
                                alertDialog.show();

                            }
                        }
                    });

                } else {
                    alertDialog = new SweetAlertDialog(OpportunitiesOverviewActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setContentText("Error : " + e);
                    alertDialog.show();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_records_overview, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataFromServer();
    }

}
