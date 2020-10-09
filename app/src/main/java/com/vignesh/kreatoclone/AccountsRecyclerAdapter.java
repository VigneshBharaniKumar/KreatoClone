package com.vignesh.kreatoclone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AccountsRecyclerAdapter extends RecyclerView.Adapter<AccountsViewHolder> {

    private ArrayList<Accounts> accounts;
    private onClickAccountInterface onClickAccountInterface;

    public interface onClickAccountInterface {

        void onClickAccount(String selectedAccount);

    }

    public AccountsRecyclerAdapter(ArrayList<Accounts> accounts, AccountsRecyclerAdapter.onClickAccountInterface onClickAccountInterface) {
        this.accounts = accounts;
        this.onClickAccountInterface = onClickAccountInterface;
    }

    @NonNull
    @Override
    public AccountsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_holder_accounts, parent, false);
        AccountsViewHolder accountsViewHolder = new AccountsViewHolder(view);

        return accountsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AccountsViewHolder holder, final int position) {

        holder.getTxtAccountName().setText(accounts.get(position).getName());
        holder.getTxtContactName().setText(accounts.get(position).getContactName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAccountInterface.onClickAccount(accounts.get(position).getObjectID());
            }
        });

    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

}
