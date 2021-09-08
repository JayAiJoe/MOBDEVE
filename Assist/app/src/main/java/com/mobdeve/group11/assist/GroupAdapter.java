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


import com.mobdeve.group11.assist.database.ContactGroup;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_L = 1, TYPE_M = 2, VIEW_REQUEST = 1;

    private List<ContactGroup> dataGroups = new ArrayList<>();
    private Activity activity;

    public GroupAdapter( Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position) {

        int viewType = 0;
        if (dataGroups.get(position).getType() == TYPE_L) {
            viewType = TYPE_L;
        } else if (dataGroups.get(position).getType() == TYPE_M) {
            viewType = TYPE_M;
        }

        return viewType;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch(viewType){
            case TYPE_L:
                View letterView = inflater.inflate(R.layout.recycler_list, parent, false);
                ViewHolderLetter GroupViewHolderLetter = new ViewHolderLetter(letterView);
                return GroupViewHolderLetter;
            case TYPE_M:
                View memberView = inflater.inflate(R.layout.recycler_list_temp, parent, false);
                ViewHolderMember GroupViewHolderMember = new ViewHolderMember(memberView);
                return GroupViewHolderMember;
            default:
                View view = inflater.inflate(R.layout.recycler_list_temp, parent, false);
                ViewHolderMember GroupViewHolder = new ViewHolderMember(view);
                return GroupViewHolder;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position){
        ContactGroup currentGroup = this.dataGroups.get(position);
        if (this.getItemViewType(position) == TYPE_L){
            ViewHolderLetter viewHolderLetter = (ViewHolderLetter) holder;
            viewHolderLetter.tvAlphabet.setText(currentGroup.getName().charAt(0)+" ");
        }
        else{
            ViewHolderMember viewHolderMember = (ViewHolderMember) holder;
            viewHolderMember.tvName.setText(currentGroup.getName());

            viewHolderMember.getContainer().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), GroupActivity.class);
                    intent.putExtra(GroupInfo.ID.name(),currentGroup.getId());
                    activity.startActivityForResult(intent, VIEW_REQUEST);
                }
            });
        }
    }

    @Override
    public int getItemCount(){return dataGroups.size();}

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

    public void setDataGroups(List<ContactGroup> lcg){
        this.dataGroups = lcg;
        notifyDataSetChanged();
    }

}
