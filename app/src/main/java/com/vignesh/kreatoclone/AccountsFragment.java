package com.vignesh.kreatoclone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AccountsFragment extends Fragment implements AccountsRecyclerAdapter.onClickAccountInterface{

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

    private FloatingActionButton fabAddAccount;

    private SweetAlertDialog alertDialog;

    private RecyclerView accountRecyclerView;
    private AccountsManager mAccountsManager;

    private TextView txtLoading;

    public AccountsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accounts, container, false);

        fabAddAccount = view.findViewById(R.id.fabAddAccount);
        fabAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAddRecordActivity();
            }
        });

        accountRecyclerView = view.findViewById(R.id.recyclerView_accounts);
        txtLoading = view.findViewById(R.id.txtLoading);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mAccountsManager = new AccountsManager(context);

    }

    private void gotoAddRecordActivity() {

        Intent intent = new Intent(getContext(), AddOrEditAccountsActivity.class);
        startActivity(intent);

    }

    @Override
    public void onResume() {
        super.onResume();

        alertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitleText("Updating...");
        alertDialog.setCancelable(false);
        alertDialog.show();

        final ArrayList<Accounts> accounts = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Accounts");
        query.whereNotEqualTo(NAME_KEY, "");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {

                    alertDialog.dismissWithAnimation();

                    for (ParseObject obj : objects) {

                        Accounts account = new Accounts(

                                obj.get(NAME_KEY).toString(),
                                obj.get(EMAIL_ID_KEY).toString(),
                                Long.parseLong(obj.get(CONTACT_NO_KEY).toString()),
                                obj.get(CONTACT_NAME_KEY).toString(),
                                obj.get(PRIMARY_ADDRESS_KEY).toString(),
                                obj.get(PRIMARY_CITY_KEY).toString(),
                                obj.get(PRIMARY_STATE_KEY).toString(),
                                obj.get(PRIMARY_COUNTRY_KEY).toString(),
                                obj.get(ACCOUNT_OWNER_KEY).toString(),
                                obj.get(ADDITIONAL_INFORMATION_KEY).toString()

                        );

                        account.setObjectID(obj.getObjectId());
                        accounts.add(account);

                    }


                    accountRecyclerView.setAdapter(new AccountsRecyclerAdapter(accounts, AccountsFragment.this));
                    accountRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                } else {

                    alertDialog.dismissWithAnimation();

                    alertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Failed")
                            .setContentText("Error: " + e);
                    alertDialog.show();

                }
            }
        });

        txtLoading.animate().alpha(0).setDuration(1000);

    }

    @Override
    public void onClickAccount(String selectedAccountObjectID) {

        Intent intent = new Intent(getContext(), AccountsOverviewActivity.class);
        intent.putExtra("selectedAccountObjectID", selectedAccountObjectID);
        startActivity(intent);

    }

}
