package com.vignesh.kreatoclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

public class RecordsListActivity extends AppCompatActivity {

    private LeadsFragment leadsFragment;

    private int selectedModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records_list);

        Bundle bundle = getIntent().getExtras();

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

            case R.id.btnOpprtunities_home:
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
