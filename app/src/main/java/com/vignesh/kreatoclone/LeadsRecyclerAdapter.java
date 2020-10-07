package com.vignesh.kreatoclone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LeadsRecyclerAdapter extends RecyclerView.Adapter<LeadsViewHolder> {

    private ArrayList<Leads> leads;
    private onClickLeadInterface onClickLeadInterface;

    public interface onClickLeadInterface {

        void onClickLead (String selectedLead);

    }

    public LeadsRecyclerAdapter(ArrayList<Leads> leads, LeadsRecyclerAdapter.onClickLeadInterface onClickLeadInterface) {
        this.leads = leads;
        this.onClickLeadInterface = onClickLeadInterface;
    }

    @NonNull
    @Override
    public LeadsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_holder_leads, parent, false);
        LeadsViewHolder leadsViewHolder = new LeadsViewHolder(view);

        return leadsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LeadsViewHolder holder, final int position) {

        holder.getTxtLeadName().setText(leads.get(position).getName());
        holder.getTxtCompanyName().setText(leads.get(position).getCompanyName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLeadInterface.onClickLead(leads.get(position).getObjectID());

            }
        });

    }

    @Override
    public int getItemCount() {
        return leads.size();
    }
}
