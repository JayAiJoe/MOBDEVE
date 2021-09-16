package com.mobdeve.group11.assist;

import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private final ArrayList<LocalDate> days;

    private final View clDayCell;
    private final TextView tvDay;
    private final TextView tvCount;
    private final CalendarAdapter.OnItemListener onItemListener;
    private LocalDate date;
    

    public CalendarViewHolder(@NonNull @NotNull View itemView, CalendarAdapter.OnItemListener onItemListener, ArrayList<LocalDate> days) {
        super(itemView);
        this.clDayCell = itemView.findViewById(R.id.cell_day_container);
        this.tvDay = itemView.findViewById(R.id.cell_day_text);
        this.tvCount = itemView.findViewById(R.id.tv_count);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
        this.days = days;
    }

    public TextView getTvDay() {
        return tvDay;
    }

    public View getCell() { return clDayCell;}

    public  void setDate(LocalDate d) { this.date = d;}

    public void hideCount() { this.tvCount.setVisibility(View.INVISIBLE);}



    public void setCount(int count) {
        if(count > 0)
        {
            this.tvCount.setText(String.valueOf(count));
            this.tvCount.setVisibility(View.VISIBLE);
        }
        else{
            this.tvCount.setVisibility(View.INVISIBLE);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        onItemListener.onItemClick(getAdapterPosition(),days.get(getAdapterPosition()));
        CalendarUtils.selectedDate = this.date;
    }

}
