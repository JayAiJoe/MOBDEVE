package com.mobdeve.group11.assist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_LETTER = 1, TYPE_MEMBER = 2;

    private ArrayList<Contact> dataContacts;
    private Activity activity;

    public ContactAdapter(ArrayList<Contact> dataContacts, Activity activity) {
        this.dataContacts = dataContacts;
        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0;
        if (dataContacts.get(position).getType() == TYPE_LETTER) {
            viewType = TYPE_LETTER;
        } else if (dataContacts.get(position).getType() == TYPE_MEMBER) {
            viewType = TYPE_MEMBER;
        }

        return viewType;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch(viewType){
            case TYPE_LETTER:
                View letterView = inflater.inflate(R.layout.recycler_list, parent, false);
                ViewHolderLetter contactViewHolderLetter = new ViewHolderLetter(letterView);
                return contactViewHolderLetter;
            case TYPE_MEMBER:
                View memberView = inflater.inflate(R.layout.recycler_list_temp, parent, false);
                ViewHolderMember contactViewHolderMember = new ViewHolderMember(memberView);
                return contactViewHolderMember;
            default:
                View view = inflater.inflate(R.layout.recycler_list_temp, parent, false);
                ViewHolderMember contactViewHolder = new ViewHolderMember(view);
                return contactViewHolder;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position){
        Contact currentContact = this.dataContacts.get(position);
        if (this.getItemViewType(position) == TYPE_LETTER){
            ViewHolderLetter viewHolderLetter = (ViewHolderLetter) holder;
            viewHolderLetter.tvAlphabet.setText(currentContact.getLName().charAt(0)+" ");
        }
        else{
            ViewHolderMember viewHolderMember = (ViewHolderMember) holder;
            viewHolderMember.tvName.setText(currentContact.getFName()+" "+currentContact.getLName());

            viewHolderMember.getContainer().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ContactActivity.class);
                    //photo
                    intent.putExtra(ContactInfo.FIRST_NAME.name(),currentContact.getFName());
                    intent.putExtra(ContactInfo.LAST_NAME.name(),currentContact.getLName());
                    intent.putExtra(ContactInfo.PHONE_NUMBER.name(),currentContact.getPNumber());
                    intent.putExtra(ContactInfo.GUARDIAN.name(),currentContact.getGuardian());
                    //groups

                    activity.startActivityForResult(intent, 1);
                }
            });
        }
    }

    @Override
    public int getItemCount(){return this.dataContacts.size();}

    public class ViewHolderLetter extends RecyclerView.ViewHolder{
        private TextView tvAlphabet;

        public ViewHolderLetter (@NonNull View itemView){
            super(itemView);
            this.tvAlphabet = itemView.findViewById(R.id.tv_list_letter);
        }
    }

    public class ViewHolderMember extends RecyclerView.ViewHolder{
        private TextView tvName;
        private ConstraintLayout clContainer;

        public ViewHolderMember (@NonNull View itemView){
            super(itemView);
            this.tvName = itemView.findViewById(R.id.tv_list_name);
            this.clContainer = itemView.findViewById(R.id.cl_list_container);
        }

        public ConstraintLayout getContainer(){return this.clContainer;}
    }

}
