package com.vignesh.kreatoclone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OpportunitiesRecyclerAdapter extends RecyclerView.Adapter<OpportunitiesViewHolder> {

    private ArrayList<Opportunities> opportunities;
    private OnClickOpportunityInterface onClickOpportunityInterface;

    public interface OnClickOpportunityInterface {

        void onClickOpportunity(String selectedOpportunity);

    }

    public OpportunitiesRecyclerAdapter(ArrayList<Opportunities> opportunities, OnClickOpportunityInterface onClickOpportunityInterface) {
        this.opportunities = opportunities;
        this.onClickOpportunityInterface = onClickOpportunityInterface;
    }

    @NonNull
    @Override
    public OpportunitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_holder_opportunities, parent, false);
        OpportunitiesViewHolder opportunitiesViewHolder = new OpportunitiesViewHolder(view);

        return opportunitiesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OpportunitiesViewHolder holder, final int position) {

        holder.getTxtAccountName().setText(opportunities.get(position).getAccountName());
        holder.getTxtItemName().setText(opportunities.get(position).getItemName());
        holder.getTxtTargetAmount().setText(opportunities.get(position).getTargetAmount()+"");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOpportunityInterface.onClickOpportunity(opportunities.get(position).getObjectID());
            }
        });

    }

    @Override
    public int getItemCount() {
        return opportunities.size();
    }
}
