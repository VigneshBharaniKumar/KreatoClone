package com.vignesh.kreatoclone;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContactsViewHolder extends RecyclerView.ViewHolder {

    private TextView txtContactName, txtCompanyName;

    public ContactsViewHolder(@NonNull View itemView) {
        super(itemView);

        txtContactName = itemView.findViewById(R.id.txtRecyclerView_Name_Contacts);
        txtCompanyName = itemView.findViewById(R.id.txtRecyclerView_CompanyName_Contacts);

    }

    public TextView getTxtContactName() {
        return txtContactName;
    }

    public TextView getTxtCompanyName() {
        return txtCompanyName;
    }
}
