package com.kpgn.optimizecontacts.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kpgn.optimizecontacts.R;
import com.kpgn.optimizecontacts.application.Constants;
import com.kpgn.optimizecontacts.entity.Contact;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Contact> contactList;

    public ContactAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (Constants.Type.TYPE_ITEM == viewType) {
            return new ContactViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_contact, parent, false));
        } else {
            return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_header, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (Constants.Type.TYPE_ITEM == holder.getItemViewType()) {
            ((ContactViewHolder) holder).bindView(context, contactList.get(position));
        } else {
            ((HeaderViewHolder) holder).bindView(contactList.get(position).getDisplayName());
        }
    }

    @Override
    public int getItemCount() {
        if (contactList != null) {
            return contactList.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return contactList.get(position).getType();
    }
}