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


public class OpportunitiesFragment extends Fragment implements OpportunitiesRecyclerAdapter.OnClickOpportunityInterface{

    private static final String ACCOUNT_NAME_KEY = "accountName";
    private static final String CONTACT_NAME_KEY = "contactName";
    private static final String ITEM_NAME_KEY = "itemName";
    private static final String TARGET_AMOUNT_KEY = "targetAmount";
    private static final String CONTACT_NO_KEY = "contactNo";
    private static final String EMAIL_ID_KEY = "emailId";
    private static final String BILLING_ADDRESS_KEY = "billingAddress";
    private static final String BILLING_CITY_KEY = "billingCity";
    private static final String BILLING_STATE_KEY = "billingState";
    private static final String BILLING_COUNTRY_KEY = "billingCountry";
    private static final String OPPORTUNITY_OWNER_KEY = "opportunityOwner";
    private static final String DESCRIPTION_KEY = "description";

    private FloatingActionButton fabAddAccount;

    private SweetAlertDialog alertDialog;

    private RecyclerView opportunityRecyclerView;
    private OpportunitiesManager mOpportunitiesManager;

    private TextView txtLoading;

    public OpportunitiesFragment() {
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
        View view = inflater.inflate(R.layout.fragment_opportunities, container, false);

        fabAddAccount = view.findViewById(R.id.fabAddOpportunities);
        fabAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAddRecordActivity();
            }
        });

        opportunityRecyclerView = view.findViewById(R.id.recyclerView_opportunities);
        txtLoading = view.findViewById(R.id.txtLoading);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mOpportunitiesManager = new OpportunitiesManager(context);

    }

    private void gotoAddRecordActivity() {


        Intent intent = new Intent(getContext(), AddOrEditOpportunityActivity.class);
        startActivity(intent);

    }

    @Override
    public void onResume() {
        super.onResume();

        alertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.setTitleText("Updating...");
        alertDialog.setCancelable(false);
        alertDialog.show();

        final ArrayList<Opportunities> opportunities = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Opportunities");
        query.whereNotEqualTo(ITEM_NAME_KEY, "");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {

                    alertDialog.dismissWithAnimation();

                    for (ParseObject obj : objects) {

                        Opportunities opportunity = new Opportunities(

                                obj.get(ACCOUNT_NAME_KEY).toString(),
                                obj.get(CONTACT_NAME_KEY).toString(),
                                obj.get(ITEM_NAME_KEY).toString(),
                                Long.parseLong(obj.get(TARGET_AMOUNT_KEY).toString()),
                                Long.parseLong(obj.get(CONTACT_NO_KEY).toString()),
                                obj.get(EMAIL_ID_KEY).toString(),
                                obj.get(BILLING_ADDRESS_KEY).toString(),
                                obj.get(BILLING_CITY_KEY).toString(),
                                obj.get(BILLING_STATE_KEY).toString(),
                                obj.get(BILLING_COUNTRY_KEY).toString(),
                                obj.get(OPPORTUNITY_OWNER_KEY).toString(),
                                obj.get(DESCRIPTION_KEY).toString()

                        );

                        opportunity.setObjectID(obj.getObjectId());
                        opportunities.add(opportunity);

                    }

                    opportunityRecyclerView.setAdapter(new OpportunitiesRecyclerAdapter(opportunities, OpportunitiesFragment.this));
                    opportunityRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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
    public void onClickOpportunity(String selectedOpportunityObjectID) {

        Intent intent = new Intent(getContext(), OpportunitiesOverviewActivity.class);
        intent.putExtra("selectedOpportunityObjectID", selectedOpportunityObjectID);
        startActivity(intent);

    }

}
