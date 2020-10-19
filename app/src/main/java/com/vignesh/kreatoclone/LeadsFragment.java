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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class LeadsFragment extends Fragment implements LeadsRecyclerAdapter.onClickLeadInterface {

    private FloatingActionButton fabAddLead;

    private RecyclerView leadsRecyclerView;
    private LeadsManager mLeadsManager;

    private SweetAlertDialog alertDialog;

    private TextView txtLoading;

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

    public LeadsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leads, container, false);

        fabAddLead = view.findViewById(R.id.fabAddLead);
        fabAddLead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAddRecordActivity();
            }
        });

        leadsRecyclerView = view.findViewById(R.id.recyclerView_leads);
        txtLoading = view.findViewById(R.id.txtLoading);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mLeadsManager = new LeadsManager(context);

    }

    private void gotoAddRecordActivity() {

        Intent intent = new Intent(getContext(), AddOrEditLeadsActivity.class);
        startActivity(intent);

    }

    @Override
    public void onResume() {
        super.onResume();

        alertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitleText("Updating...");
        alertDialog.setCancelable(false);
        alertDialog.show();

        final ArrayList<Leads> leads = new ArrayList<>();

        ParseQuery<ParseObject> query = new ParseQuery<>("Leads");
        query.whereNotEqualTo(NAME_KEY, "");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {
                    alertDialog.dismissWithAnimation();

                    for (ParseObject obj : objects) {

                        Leads lead = new Leads(

                                obj.get(NAME_KEY).toString(),
                                obj.get(EMAIL_ID_KEY).toString(),
                                Long.parseLong(obj.get(CONTACT_NO_KEY).toString()),
                                obj.get(COMPANY_NAME_KEY).toString(),
                                obj.get(PRIMARY_ADDRESS_KEY).toString(),
                                obj.get(PRIMARY_CITY_KEY).toString(),
                                obj.get(PRIMARY_STATE_KEY).toString(),
                                obj.get(PRIMARY_COUNTRY_KEY).toString(),
                                obj.get(LEAD_OWNER_KEY).toString(),
                                obj.get(CO_OWNER_KEY).toString(),
                                obj.get(ADDITIONAL_INFORMATION_KEY).toString()

                        );
                        lead.setObjectID(obj.getObjectId());
                        leads.add(lead);

                    }
                    leadsRecyclerView.setAdapter(new LeadsRecyclerAdapter(leads, LeadsFragment.this));
                    leadsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(getContext(), HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClickLead(String selectedLeadObjectID) {

        Intent intent = new Intent(getContext(), LeadOverviewActivity.class);
        intent.putExtra("selectedLeadObjectID", selectedLeadObjectID);
        startActivity(intent);

    }
}
