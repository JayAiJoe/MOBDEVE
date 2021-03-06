package com.mobdeve.group11.assist;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {

    private final ArrayList<LocalDate> days;
    private final OnItemListener onItemListener;
    private HashMap<Integer,CalendarViewHolder> holderList;


    public CalendarAdapter(ArrayList<LocalDate> days, OnItemListener onItemListener){
        this.days = days;
        this.onItemListener = onItemListener;
        this.holderList = new HashMap<>();
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calender_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        if(days.size() > 15)
            layoutParams.height = (int)(parent.getHeight()*0.166666);
        else
            layoutParams.height = (int) parent.getHeight();
        return new CalendarViewHolder(view, onItemListener, days);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull CalendarViewHolder holder, int position) {
        LocalDate date = days.get(position);
        if(date == null)
            holder.getTvDay().setText("");
        else{
            holder.setDate(date);
            holder.getTvDay().setText(String.valueOf(date.getDayOfMonth()));
            if(date.equals(CalendarUtils.selectedDate))
                holder.getCell().setBackgroundColor(Color.LTGRAY);
            else
                holder.getCell().setBackgroundColor(Color.TRANSPARENT);
        }

        if(!holderList.containsKey(position)){
            holderList.put(position,holder);
        }

    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public interface OnItemListener{
        void onItemClick(int position, LocalDate date);
    }

    //get view holder based on position
    public CalendarViewHolder getViewByPosition(int position) {
        return holderList.get(position);
    }

    //highlight the currently selected cell
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showSelected(){
        for(int i=0; i<days.size(); i++) {
            if(days.get(i) !=  null){
                if (days.get(i).equals(CalendarUtils.selectedDate))
                    holderList.get(i).getCell().setBackgroundColor(Color.LTGRAY);
                else
                    holderList.get(i).getCell().setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }



}
