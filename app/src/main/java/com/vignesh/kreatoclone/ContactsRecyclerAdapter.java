package com.vignesh.kreatoclone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactsRecyclerAdapter extends RecyclerView.Adapter<ContactsViewHolder> {

    private ArrayList<Contacts> contacts;
    private onClickContactInterface onClickContactInterface;

    public interface onClickContactInterface {

        void onClickContact(String selectedContact);

    }

    public ContactsRecyclerAdapter(ArrayList<Contacts> contacts, ContactsRecyclerAdapter.onClickContactInterface onClickContactInterface) {
        this.contacts = contacts;
        this.onClickContactInterface = onClickContactInterface;
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_holder_contacts, parent, false);
        ContactsViewHolder contactsViewHolder = new ContactsViewHolder(view);

        return contactsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, final int position) {

        holder.getTxtContactName().setText(contacts.get(position).getName());
        holder.getTxtCompanyName().setText(contacts.get(position).getCompanyName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickContactInterface.onClickContact(contacts.get(position).getObjectID());
            }
        });

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
