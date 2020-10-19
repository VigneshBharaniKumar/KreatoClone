package com.vignesh.kreatoclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.MaterialToolbar;
import com.parse.ParseUser;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialToolbar toolbar;

    private ActionMenuItemView btnLogout_menu;

    private Button btnLeads, btnContacts, btnAccounts, btnOpportunities;

    private SweetAlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        btnLogout_menu = findViewById(R.id.btnLogout_home_menu);
        btnLeads = findViewById(R.id.btnLeads_home);
        btnContacts = findViewById(R.id.btnContacts_home);
        btnAccounts = findViewById(R.id.btnAccounts_home);
        btnOpportunities = findViewById(R.id.btnOpportunities_home);

        setSupportActionBar(toolbar);

        setTitle("Welcome Home, " + ParseUser.getCurrentUser().getUsername());

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.btnLogout_home_menu:
                        logOut();
                        break;

                }

                return true;
            }
        });

        btnLeads.setOnClickListener(this);
        btnContacts.setOnClickListener(this);
        btnAccounts.setOnClickListener(this);
        btnOpportunities.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnLeads_home:
                Intent intentLead = new Intent(HomeActivity.this, RecordsListActivity.class);
                intentLead.putExtra("Module", R.id.btnLeads_home);
                startActivity(intentLead);
                break;

            case R.id.btnContacts_home:
                Intent intentContact = new Intent(HomeActivity.this, RecordsListActivity.class);
                intentContact.putExtra("Module", R.id.btnContacts_home);
                startActivity(intentContact);
                break;

            case R.id.btnAccounts_home:
                Intent intentAccount = new Intent(HomeActivity.this, RecordsListActivity.class);
                intentAccount.putExtra("Module", R.id.btnAccounts_home);
                startActivity(intentAccount);
                break;

            case R.id.btnOpportunities_home:
                Intent intentOpportunity = new Intent(HomeActivity.this, RecordsListActivity.class);
                intentOpportunity.putExtra("Module", R.id.btnOpportunities_home);
                startActivity(intentOpportunity);
                break;

        }

    }

    private void logOut() {

        alertDialog = new SweetAlertDialog(HomeActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are You Sure?")
                .setConfirmButton("Log Out", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        ParseUser.logOut();
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        alertDialog.dismissWithAnimation();
                    }
                })
                .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        alertDialog.dismiss();
                    }
                });
        alertDialog.show();

    }

    @Override
    public void onBackPressed() {
        logOut();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }
}

