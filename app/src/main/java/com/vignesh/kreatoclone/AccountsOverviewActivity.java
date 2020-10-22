package com.vignesh.kreatoclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AccountsOverviewActivity extends AppCompatActivity {

    private String selectedAccountObjectID;

    private Accounts selectedAccount;

    private TextView tvName, tvEmailId, tvContactNo, tvContactName, tvPrimaryAddress,
            tvPrimaryCity, tvPrimaryState, tvPrimaryCountry, tvAccountOwner, tvAdditionalInfo;

    private AccountsManager mAccountsManager;
    private ConvertManager mConvertManager;

    private SweetAlertDialog alertDialog;

    private MaterialToolbar toolbar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts_overview);

        selectedAccountObjectID = getIntent().getStringExtra("selectedAccountObjectID");

        tvName = findViewById(R.id.tvName_account_overview);
        tvEmailId = findViewById(R.id.tvEmailId_account_overview);
        tvContactNo = findViewById(R.id.tvContactNo_account_overview);
        tvContactName = findViewById(R.id.tvContactName_account_overview);
        tvPrimaryAddress = findViewById(R.id.tvPrimaryAddress_account_overview);
        tvPrimaryCity = findViewById(R.id.tvPrimaryCity_account_overview);
        tvPrimaryState = findViewById(R.id.tvPrimaryState_account_overview);
        tvPrimaryCountry = findViewById(R.id.tvPrimaryCountry_account_overview);
        tvAccountOwner = findViewById(R.id.tvAccountOwner_account_overview);
        tvAdditionalInfo = findViewById(R.id.tvAdditionalInfo_account_overview);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAccountsManager = new AccountsManager(this);
        mConvertManager = new ConvertManager(this);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.btnEdit_recordsOverview_menu:
                        Intent intent = new Intent(AccountsOverviewActivity.this, AddOrEditAccountsActivity.class);
                        intent.putExtra("selectedAccountObjectID", selectedAccountObjectID);
                        startActivity(intent);
                        break;

                    case R.id.btnDelete_recordsOverview_menu:
                        alertDialog = new SweetAlertDialog(AccountsOverviewActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Are you sure want to Delete?")
                                .setConfirmButton("Delete", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        deleteAccount(selectedAccountObjectID);
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

    private void getDataFromServer() {

        alertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitleText("Updating...");
        alertDialog.setCancelable(false);
        alertDialog.show();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Accounts");
        query.getInBackground(selectedAccountObjectID, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {

                    alertDialog.dismissWithAnimation();

                    selectedAccount = new Accounts(

                            object.get(NAME_KEY).toString(),
                            object.get(EMAIL_ID_KEY).toString(),
                            Long.parseLong(object.get(CONTACT_NO_KEY).toString()),
                            object.get(CONTACT_NAME_KEY).toString(),
                            object.get(PRIMARY_ADDRESS_KEY).toString(),
                            object.get(PRIMARY_CITY_KEY).toString(),
                            object.get(PRIMARY_STATE_KEY).toString(),
                            object.get(PRIMARY_COUNTRY_KEY).toString(),
                            object.get(ACCOUNT_OWNER_KEY).toString(),
                            object.get(ADDITIONAL_INFORMATION_KEY).toString()


                    );

                    setDataToTV(selectedAccount);

                } else {

                    alertDialog.dismissWithAnimation();

                    alertDialog = new SweetAlertDialog(AccountsOverviewActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText("Error: " + e);
                    alertDialog.show();

                }

            }
        });

    }

    private void setDataToTV(Accounts selectedAccount) {

        setTitle(selectedAccount.getName() + "'s Overview");

        tvName.setText(selectedAccount.getName());
        tvEmailId.setText(selectedAccount.getEmailId());
        tvContactNo.setText(selectedAccount.getContactNo() + "");
        tvContactName.setText(selectedAccount.getContactName());
        tvPrimaryAddress.setText(selectedAccount.getPrimaryAddress());
        tvPrimaryCity.setText(selectedAccount.getPrimaryCity());
        tvPrimaryState.setText(selectedAccount.getPrimaryState());
        tvPrimaryCountry.setText(selectedAccount.getPrimaryCountry());
        tvAccountOwner.setText(selectedAccount.getAccountOwner());
        tvAdditionalInfo.setText(selectedAccount.getAdditionalInformation());

    }

    private void deleteAccount(String selectedAccountObjectID) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Accounts");
        query.getInBackground(selectedAccountObjectID, new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject object, ParseException e) {
                if (e == null){

                    object.deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {

                                alertDialog = new SweetAlertDialog(AccountsOverviewActivity.this, SweetAlertDialog.SUCCESS_TYPE)
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

                                alertDialog = new SweetAlertDialog(AccountsOverviewActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setContentText("Error : " + e);
                                alertDialog.show();

                            }
                        }
                    });

                } else {
                    alertDialog = new SweetAlertDialog(AccountsOverviewActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setContentText("Error : " + e);
                    alertDialog.show();
                }
            }
        });

    }

    private void showConvertPopup() {

        View checkBoxView = View.inflate(this, R.layout.convert_checkbox_layout, null);
        final CheckBox opportunityCheckBox = checkBoxView.findViewById(R.id.cbConvert1);
        final CheckBox dummyCheckBox = checkBoxView.findViewById(R.id.cbConvert2);
        opportunityCheckBox.setText("Convert to Opportunity");
        dummyCheckBox.setVisibility(View.GONE);

        alertDialog = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE);
        alertDialog.setCustomView(checkBoxView)
                .setConfirmButton("Convert", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                        alertDialog.dismissWithAnimation();
                        if (opportunityCheckBox.isChecked()) {
                            mConvertManager.accountToOpportunity(selectedAccountObjectID);
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
        getDataFromServer();
    }

}
