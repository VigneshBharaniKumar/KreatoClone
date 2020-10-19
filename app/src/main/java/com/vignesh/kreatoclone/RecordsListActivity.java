package com.vignesh.kreatoclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;

import java.security.PrivateKey;

public class RecordsListActivity extends AppCompatActivity{

    private int selectedModule;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records_list);

        Bundle bundle = getIntent().getExtras();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (bundle != null) {
            selectedModule = (int) bundle.get("Module");
        }

        switch (selectedModule) {

            case R.id.btnLeads_home:
                displayFragment(new LeadsFragment());
                setTitle("Leads");
                break;

            case R.id.btnContacts_home:
                displayFragment(new ContactsFragment());
                setTitle("Contacts");
                break;

            case R.id.btnAccounts_home:
                displayFragment(new AccountsFragment());
                setTitle("Accounts");
                break;

            case R.id.btnOpportunities_home:
                displayFragment(new OpportunitiesFragment());
                setTitle("Opportunities");
                break;

            default:
                break;

        }


    }

    public void displayFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, fragment).addToBackStack(null).commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
