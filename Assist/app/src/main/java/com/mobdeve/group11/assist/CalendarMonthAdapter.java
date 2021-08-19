package com.mobdeve.group11.assist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CalendarMonthAdapter extends RecyclerView.Adapter<CalendarMonthViewHolder> {

    private final ArrayList<String> daysOfMonth;
    private final OnItemListener onItemListener;

    public CalendarMonthAdapter(ArrayList<String> daysOfMonth, OnItemListener onItemListener){
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
    }


    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public CalendarMonthViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calender_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int)(parent.getHeight()*0.166666);
        return new CalendarMonthViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull CalendarMonthViewHolder holder, int position) {
        holder.getTvDay().setText(daysOfMonth.get(position));
    }

    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }

    public interface OnItemListener{
        void onItemClick(int position, String dayText);
    }
}
