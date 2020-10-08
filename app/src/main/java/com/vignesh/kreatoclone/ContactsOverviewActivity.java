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

public class ContactsOverviewActivity extends AppCompatActivity {

    private String selectedContactObjectID;

    private Contacts selectedContact;

    private TextView tvName, tvEmailId, tvContactNo, tvCompanyName, tvPrimaryAddress,
            tvPrimaryCity, tvPrimaryState, tvPrimaryCountry, tvContactOwner, tvAdditionalInfo;

    private ContactsManager mContactsManager;

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
    private static final String CONTACT_OWNER_KEY = "contactOwner";
    private static final String ADDITIONAL_INFORMATION_KEY = "additionalInformation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_overview);

        selectedContactObjectID = getIntent().getStringExtra("selectedContactObjectID");

        tvName = findViewById(R.id.tvName_contact_overview);
        tvEmailId = findViewById(R.id.tvEmailId_contact_overview);
        tvContactNo = findViewById(R.id.tvContactNo_contact_overview);
        tvCompanyName = findViewById(R.id.tvCompanyName_contact_overview);
        tvPrimaryAddress = findViewById(R.id.tvPrimaryAddress_contact_overview);
        tvPrimaryCity = findViewById(R.id.tvPrimaryCity_contact_overview);
        tvPrimaryState = findViewById(R.id.tvPrimaryState_contact_overview);
        tvPrimaryCountry = findViewById(R.id.tvPrimaryCountry_contact_overview);
        tvContactOwner = findViewById(R.id.tvContactOwner_contact_overview);
        tvAdditionalInfo = findViewById(R.id.tvAdditionalInfo_contact_overview);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContactsManager = new ContactsManager(this);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.btnEdit_recordsOverview_menu:
                        Intent intent = new Intent(ContactsOverviewActivity.this, AddOrEditContactsActivity.class);
                        intent.putExtra("selectedContactObjectID", selectedContactObjectID);
                        startActivity(intent);
                        break;

                    case R.id.btnDelete_recordsOverview_menu:
                        alertDialog = new SweetAlertDialog(ContactsOverviewActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Are you sure want to Delete?")
                                .setConfirmButton("Delete", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        deleteContact(selectedContactObjectID);
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

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Contacts");
        query.getInBackground(selectedContactObjectID, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {

                    alertDialog.dismissWithAnimation();

                    selectedContact = new Contacts(

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

                    setDataToTV(selectedContact);

                } else {

                    alertDialog.dismissWithAnimation();

                    alertDialog = new SweetAlertDialog(ContactsOverviewActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText("Error: " + e);
                    alertDialog.show();

                }

            }
        });

    }

    private void setDataToTV(Contacts selectedContact) {

        setTitle(selectedContact.getName() + "'s Overview");

        tvName.setText(selectedContact.getName());
        tvEmailId.setText(selectedContact.getEmailId());
        tvContactNo.setText(selectedContact.getContactNo() + "");
        tvCompanyName.setText(selectedContact.getCompanyName());
        tvPrimaryAddress.setText(selectedContact.getPrimaryAddress());
        tvPrimaryCity.setText(selectedContact.getPrimaryCity());
        tvPrimaryState.setText(selectedContact.getPrimaryState());
        tvPrimaryCountry.setText(selectedContact.getPrimaryCountry());
        tvContactOwner.setText(selectedContact.getContactOwner());
        tvAdditionalInfo.setText(selectedContact.getAdditionalInformation());

    }

    private void deleteContact(String selectedLeadObjectID) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Contacts");
        query.getInBackground(selectedLeadObjectID, new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject object, ParseException e) {
                if (e == null){

                    object.deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {

                                alertDialog = new SweetAlertDialog(ContactsOverviewActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText(object.get(NAME_KEY) + " deleted successfully")
                                        .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                finish();
                                            }
                                        });
                                alertDialog.setCancelable(false);
                                alertDialog.show();

                            } else {

                                alertDialog = new SweetAlertDialog(ContactsOverviewActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setContentText("Error : " + e);
                                alertDialog.show();

                            }
                        }
                    });

                } else {
                    alertDialog = new SweetAlertDialog(ContactsOverviewActivity.this, SweetAlertDialog.ERROR_TYPE)
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
