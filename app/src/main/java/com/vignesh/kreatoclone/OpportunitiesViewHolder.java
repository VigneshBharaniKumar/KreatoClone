package com.vignesh.kreatoclone;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OpportunitiesViewHolder extends RecyclerView.ViewHolder {

    private TextView txtAccountName, txtItemName, txtTargetAmount;

    public OpportunitiesViewHolder(@NonNull View itemView) {
        super(itemView);

        txtAccountName = itemView.findViewById(R.id.txtRecyclerView_AccountName_Opportunities);
        txtItemName = itemView.findViewById(R.id.txtRecyclerView_ItemName_Opportunities);
        txtTargetAmount = itemView.findViewById(R.id.txtRecyclerView_TargetAmount_Opportunities);

    }

    public TextView getTxtAccountName() {
        return txtAccountName;
    }

    public TextView getTxtItemName() {
        return txtItemName;
    }

    public TextView getTxtTargetAmount() {
        return txtTargetAmount;
    }
}
