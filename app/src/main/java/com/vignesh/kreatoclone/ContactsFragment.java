package com.vignesh.kreatoclone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ContactsFragment extends Fragment implements ContactsRecyclerAdapter.onClickContactInterface {

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

    private FloatingActionButton fabAddContact;

    private SweetAlertDialog alertDialog;

    private RecyclerView contactRecyclerView;
    private ContactsManager mContactsManager;

    private SwipeRefreshLayout pullToRefreshLayout;

    private TextView txtLoading;

    public ContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        fabAddContact = view.findViewById(R.id.fabAddContact);
        fabAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAddRecordActivity();
            }
        });

        contactRecyclerView = view.findViewById(R.id.recyclerView_contacts);
        txtLoading = view.findViewById(R.id.txtLoading);

        pullToRefreshLayout = view.findViewById(R.id.pullToRefreshLayout);
        pullToRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mContactsManager = new ContactsManager(context);

    }

    private void gotoAddRecordActivity() {

        Intent intent = new Intent(getContext(), AddOrEditContactsActivity.class);
        startActivity(intent);

    }

    private void refreshList() {

        pullToRefreshLayout.setRefreshing(true);

        final ArrayList<Contacts> contacts = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Contacts");
        query.whereNotEqualTo(NAME_KEY, "");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {

                    pullToRefreshLayout.setRefreshing(false);

                    for (ParseObject obj : objects) {

                        Contacts contact = new Contacts(

                                obj.get(NAME_KEY).toString(),
                                obj.get(EMAIL_ID_KEY).toString(),
                                Long.parseLong(obj.get(CONTACT_NO_KEY).toString()),
                                obj.get(COMPANY_NAME_KEY).toString(),
                                obj.get(PRIMARY_ADDRESS_KEY).toString(),
                                obj.get(PRIMARY_CITY_KEY).toString(),
                                obj.get(PRIMARY_STATE_KEY).toString(),
                                obj.get(PRIMARY_COUNTRY_KEY).toString(),
                                obj.get(CONTACT_OWNER_KEY).toString(),
                                obj.get(ADDITIONAL_INFORMATION_KEY).toString()

                        );

                        contact.setObjectID(obj.getObjectId());
                        contacts.add(contact);

                    }


                    contactRecyclerView.setAdapter(new ContactsRecyclerAdapter(contacts, ContactsFragment.this));
                    contactRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                } else {

                    pullToRefreshLayout.setRefreshing(false);

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
    public void onResume() {
        super.onResume();
        refreshList();
    }

    @Override
    public void onClickContact(String selectedContactObjectID) {

        Intent intent = new Intent(getContext(), ContactsOverviewActivity.class);
        intent.putExtra("selectedContactObjectID", selectedContactObjectID);
        startActivity(intent);

    }
}
