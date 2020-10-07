package com.vignesh.kreatoclone;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LeadsViewHolder extends RecyclerView.ViewHolder {

    private TextView txtLeadName, txtCompanyName;

    public LeadsViewHolder(@NonNull View itemView) {
        super(itemView);

        txtLeadName = itemView.findViewById(R.id.txtRecyclerView_Name_Leads);
        txtCompanyName = itemView.findViewById(R.id.txtRecyclerView_CompanyName_Leads);

    }

    public TextView getTxtLeadName() {
        return txtLeadName;
    }

    public TextView getTxtCompanyName() {
        return txtCompanyName;
    }
}
