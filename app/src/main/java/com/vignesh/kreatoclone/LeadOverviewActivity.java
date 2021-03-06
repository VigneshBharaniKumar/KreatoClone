package com.vignesh.kreatoclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.MaterialToolbar;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LeadOverviewActivity extends AppCompatActivity {

    private String selectedLeadObjectID;

    private Leads selectedLead;

    private TextView tvName, tvEmailId, tvContactNo, tvCompanyName, tvPrimaryAddress,
            tvPrimaryCity, tvPrimaryState, tvPrimaryCountry, tvLeadOwner, tvCoOwner, tvAdditionalInfo;

    private LeadsManager mLeadManager;
    private ConvertManager mConvertManager;

    private SweetAlertDialog alertDialog;

    private MaterialToolbar toolbar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_overview);

        selectedLeadObjectID = getIntent().getStringExtra("selectedLeadObjectID");

        tvName = findViewById(R.id.tvLeadName_overview);
        tvEmailId = findViewById(R.id.tvEmailId_overview);
        tvContactNo = findViewById(R.id.tvContactNo_overview);
        tvCompanyName = findViewById(R.id.tvCompanyName_overview);
        tvPrimaryAddress = findViewById(R.id.tvPrimaryAddress_overview);
        tvPrimaryCity = findViewById(R.id.tvPrimaryCity_overview);
        tvPrimaryState = findViewById(R.id.tvPrimaryState_overview);
        tvPrimaryCountry = findViewById(R.id.tvPrimaryCountry_overview);
        tvLeadOwner = findViewById(R.id.tvLeadOwner_overview);
        tvCoOwner = findViewById(R.id.tvCoOwner_overview);
        tvAdditionalInfo = findViewById(R.id.tvAdditionalInfo_overview);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mLeadManager = new LeadsManager(this);
        mConvertManager = new ConvertManager(this);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.btnEdit_recordsOverview_menu:
                        Intent intent = new Intent(LeadOverviewActivity.this, AddOrEditLeadsActivity.class);
                        intent.putExtra("selectedLeadObjectID", selectedLeadObjectID);
                        startActivity(intent);
                        break;

                    case R.id.btnDelete_recordsOverview_menu:
                        alertDialog = new SweetAlertDialog(LeadOverviewActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Are you sure want to Delete?")
                                .setConfirmButton("Delete", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        alertDialog.dismissWithAnimation();
                                        mLeadManager.deleteLead(selectedLeadObjectID);
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

                    case R.id.btnConvert_recordsOverview_menu:
                        showConvertPopup();
                        break;

                }

                return true;
            }
        });

    }

    private void setDataToTV(String selectedLeadObjectID) {

        //selectedLead = mLeadManager.getLead(selectedLeadObjectID);

        alertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitleText("Updating...");
        alertDialog.setCancelable(false);
        alertDialog.show();

        try {
            ParseQuery<ParseObject> query = new ParseQuery<>("Leads");
            query.getInBackground(selectedLeadObjectID, new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
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

                    setTitle(selectedLead.getName() + "'s Overview");

                    tvName.setText(selectedLead.getName());
                    tvEmailId.setText(selectedLead.getEmailId());
                    tvContactNo.setText(selectedLead.getContactNo() + "");
                    tvCompanyName.setText(selectedLead.getCompanyName());
                    tvPrimaryAddress.setText(selectedLead.getPrimaryAddress());
                    tvPrimaryCity.setText(selectedLead.getPrimaryCity());
                    tvPrimaryState.setText(selectedLead.getPrimaryState());
                    tvPrimaryCountry.setText(selectedLead.getPrimaryCountry());
                    tvLeadOwner.setText(selectedLead.getLeadOwner());
                    tvCoOwner.setText(selectedLead.getCoOwner());
                    tvAdditionalInfo.setText(selectedLead.getAdditionalInformation());
                }
            });

        } catch (Exception e) {

            alertDialog.dismissWithAnimation();

            alertDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Failed")
                    .setContentText("Error: " + e);
            alertDialog.show();

        }

    }

    private void showConvertPopup() {

        View checkBoxView = View.inflate(this, R.layout.convert_checkbox_layout, null);
        final CheckBox contactCheckBox = checkBoxView.findViewById(R.id.cbConvert1);
        final CheckBox accountCheckBox = checkBoxView.findViewById(R.id.cbConvert2);
        contactCheckBox.setText("Convert to Contact");
        accountCheckBox.setText("Convert to Account");

        alertDialog = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE);
        alertDialog.setCustomView(checkBoxView)
                .setConfirmButton("Convert", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                        alertDialog.dismissWithAnimation();

                        if (accountCheckBox.isChecked()) {
                            mConvertManager.leadToAccount(selectedLeadObjectID);
                        }
                        if (contactCheckBox.isChecked()) {
                            mConvertManager.leadToContact(selectedLeadObjectID);
                        }

                    }
                });
        alertDialog.setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                alertDialog.dismissWithAnimation();
            }
        });
        alertDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_records_overview, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (selectedLeadObjectID != null) {
            setDataToTV(selectedLeadObjectID);
        }
    }
}
