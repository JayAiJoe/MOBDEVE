package com.mobdeve.group11.assist;

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

public class TemplateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_LE = 1, TYPE_ME = 2;

    private ArrayList<Template> dataTemplates;

    public TemplateAdapter(ArrayList<Template> dataTemplates) { this.dataTemplates = dataTemplates;}

    @Override
    public int getItemViewType(int position) {
        int viewType = 0;
        if (dataTemplates.get(position).getType() == TYPE_LE) {
            viewType = TYPE_LE;
        } else if (dataTemplates.get(position).getType() == TYPE_ME) {
            viewType = TYPE_ME;
        }

        return viewType;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch(viewType){
            case TYPE_LE:
                View letterView = inflater.inflate(R.layout.recycler_list, parent, false);
                ViewHolderLetter TemplateViewHolderLetter = new ViewHolderLetter(letterView);
                return TemplateViewHolderLetter;
            case TYPE_ME:
                View memberView = inflater.inflate(R.layout.recycler_list_temp, parent, false);
                ViewHolderMember TemplateViewHolderMember = new ViewHolderMember(memberView);
                return TemplateViewHolderMember;
            default:
                View view = inflater.inflate(R.layout.recycler_list_temp, parent, false);
                ViewHolderMember TemplateViewHolder = new ViewHolderMember(view);
                return TemplateViewHolder;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position){
        Template currentTemplate = this.dataTemplates.get(position);
        if (this.getItemViewType(position) == TYPE_LE){
            ViewHolderLetter viewHolderLetter = (ViewHolderLetter) holder;
            viewHolderLetter.tvAlphabet.setText(currentTemplate.getTitle().charAt(0)+" ");
        }
        else{
            ViewHolderMember viewHolderMember = (ViewHolderMember) holder;
            viewHolderMember.tvName.setText(currentTemplate.getTitle());
        }
    }

    @Override
    public int getItemCount(){return this.dataTemplates.size();}

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
