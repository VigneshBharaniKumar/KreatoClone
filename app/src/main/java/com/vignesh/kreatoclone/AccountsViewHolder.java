package com.vignesh.kreatoclone;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AccountsViewHolder extends RecyclerView.ViewHolder {

    private TextView txtAccountName, txtContactName;

    public AccountsViewHolder(@NonNull View itemView) {
        super(itemView);

        txtAccountName = itemView.findViewById(R.id.txtRecyclerView_Name_Accounts);
        txtContactName = itemView.findViewById(R.id.txtRecyclerView_ContactName_Accounts);

    }

    public TextView getTxtAccountName() {
        return txtAccountName;
    }

    public TextView getTxtContactName() {
        return txtContactName;
    }
}
