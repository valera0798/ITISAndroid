package ru.itis.android.lesson_18_09_17.activities.contactlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.itis.android.lesson_18_09_17.R;
import ru.itis.android.lesson_18_09_17.models.Contact;

/**
 * Created by Users on 18.09.2017.
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactViewHolder> {
    private List<Contact> contacts;
    private OnItemClickListener onItemClickListener;

    public ContactListAdapter(@NonNull List<Contact> contacts, OnItemClickListener onItemClickListener) {
        this.contacts = contacts;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_contact, parent, false);
        return new ContactViewHolder(item, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.bind(contact);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
