package com.vignesh.kreatoclone;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
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

public class AddOrEditAccountsActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;

    private SweetAlertDialog alertDialog;

    private AccountsManager mAccountsManager;
    private Accounts account;

    private String selectedAccountObjectID = null;

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

    private TextInputLayout name;
    private TextInputLayout emailId;
    private TextInputLayout contactNo;
    private TextInputLayout contactName;
    private TextInputLayout primaryAddress;
    private TextInputLayout primaryCity;
    private TextInputLayout primaryState;
    private TextInputLayout primaryCountry;
    private TextInputLayout accountOwner;
    private TextInputLayout additionalInformation;

    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_accounts);

        toolbar = findViewById(R.id.toolbar);
        btnUpdate = findViewById(R.id.btnUpdate_addOrEdit_accounts);

        name = findViewById(R.id.edtName_account_add);
        emailId = findViewById(R.id.edtEmailId_account_add);
        contactNo = findViewById(R.id.edtContactNo_account_add);
        contactName = findViewById(R.id.edtContactName_account_add);
        primaryAddress = findViewById(R.id.edtPrimaryAddress_account_add);
        primaryCity = findViewById(R.id.edtPrimaryCity_account_add);
        primaryState = findViewById(R.id.edtPrimaryState_account_add);
        primaryCountry = findViewById(R.id.edtPrimaryCountry_account_add);
        accountOwner = findViewById(R.id.edtAccountOwner_account_add);
        additionalInformation = findViewById(R.id.edtAdditionalInformation_account_add);

        selectedAccountObjectID = getIntent().getStringExtra("selectedAccountObjectID");

        mAccountsManager = new AccountsManager(this);

        setTitle("Add new Account");

        setSupportActionBar(toolbar);

        if (selectedAccountObjectID != null) {
            putDataToUI(selectedAccountObjectID);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedAccountObjectID == null) {
                    addNewAccount();
                } else {
                    updateExistingAccount(selectedAccountObjectID);
                }
            }
        });

    }

    private void addNewAccount() {

        mAccountsManager.saveAccount(getDataFromUI());

    }

    private void putDataToUI(String objectID) {

        alertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitleText("Updating...");
        alertDialog.setCancelable(false);
        alertDialog.show();

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Accounts");
        query.getInBackground(objectID, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {

                    alertDialog.dismissWithAnimation();

                    Accounts selectedAccount = new Accounts(

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

                    name.getEditText().setText(selectedAccount.getName());
                    emailId.getEditText().setText(selectedAccount.getEmailId());
                    contactNo.getEditText().setText(selectedAccount.getContactNo() + "");
                    contactName.getEditText().setText(selectedAccount.getContactName());
                    primaryAddress.getEditText().setText(selectedAccount.getPrimaryAddress());
                    primaryCity.getEditText().setText(selectedAccount.getPrimaryCity());
                    primaryState.getEditText().setText(selectedAccount.getPrimaryState());
                    primaryCountry.getEditText().setText(selectedAccount.getPrimaryCountry());
                    accountOwner.getEditText().setText(selectedAccount.getAccountOwner());
                    additionalInformation.getEditText().setText(selectedAccount.getAdditionalInformation());

                } else {

                    alertDialog.dismissWithAnimation();

                    alertDialog = new SweetAlertDialog(AddOrEditAccountsActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText("Error: " + e);
                    alertDialog.show();

                }
            }
        });

    }

    private Accounts getDataFromUI() {

        String name = this.name.getEditText().getText().toString();
        String emailId = this.emailId.getEditText().getText().toString();
        long contactNo = Long.parseLong(this.contactNo.getEditText().getText().toString().trim());
        String contactName = this.contactName.getEditText().getText().toString();
        String primaryAddress = this.primaryAddress.getEditText().getText().toString();
        String primaryCity = this.primaryCity.getEditText().getText().toString();
        String primaryState = this.primaryState.getEditText().getText().toString();
        String primaryCountry = this.primaryCountry.getEditText().getText().toString();
        String accountOwner = this.accountOwner.getEditText().getText().toString();
        String additionalInformation = this.additionalInformation.getEditText().getText().toString();

        account = new Accounts(name, emailId, contactNo, contactName, primaryAddress, primaryCity, primaryState,
                primaryCountry, accountOwner, additionalInformation);

        return account;

    }

    private void updateExistingAccount (String selectedContactObjectID) {

        alertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitleText("Updating...");
        alertDialog.setCancelable(false);
        alertDialog.show();

        final Accounts account = getDataFromUI();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Accounts");
        query.getInBackground(selectedContactObjectID, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {

                if (e == null) {

                    alertDialog.dismissWithAnimation();

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

                    object.saveInBackground();

                    alertDialog = new SweetAlertDialog(AddOrEditAccountsActivity.this, SweetAlertDialog.SUCCESS_TYPE)
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

                    alertDialog = new SweetAlertDialog(AddOrEditAccountsActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText("Error: " + e);
                    alertDialog.show();

                }
            }
        });

    }

}
