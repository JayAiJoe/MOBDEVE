package com.mobdeve.group11.assist;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.group11.assist.database.ContactGroup;
import com.mobdeve.group11.assist.database.Event;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_REQUEST = 1;

    private List<Event> dataEvents = new ArrayList<>();
    private Activity activity;

    public EventAdapter( Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.event_list_item, parent, false);
        EventAdapter.ViewHolderMember eventViewHolder = new EventAdapter.ViewHolderMember(view);
        return eventViewHolder;
    }

    public class ViewHolderMember extends RecyclerView.ViewHolder{
        private TextView tvTitle, tvTime;
        private ConstraintLayout clContainer;

        public ViewHolderMember (@NonNull View itemView){
            super(itemView);
            this.tvTitle = itemView.findViewById(R.id.tv_list_event_title);
            this.tvTime = itemView.findViewById(R.id.tv_list_event_time);
            this.clContainer = itemView.findViewById(R.id.cl_list_container);
        }

        public ConstraintLayout getContainer(){return this.clContainer;}
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        Event currentEvent = this.dataEvents.get(position);

        EventAdapter.ViewHolderMember viewHolderMember = (EventAdapter.ViewHolderMember) holder;
        viewHolderMember.tvTitle.setText(currentEvent.getTitle());
        viewHolderMember.tvTime.setText(currentEvent.getTimeStart().toString() + " - " + currentEvent.getTimeEnd().toString());

        viewHolderMember.getContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ViewEventActivity.class);
                intent.putExtra(EventInfo.ID.name(),currentEvent.getId());
                activity.startActivityForResult(intent, VIEW_REQUEST);
                }
        });
    }

    public void setDataEvents(List<Event> e) {
        this.dataEvents = e;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return dataEvents.size();
    }
}
