package com.mobdeve.group11.assist;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private final ArrayList<LocalDate> days;

    private final View clDayCell;
    private final TextView tvDay;
    private final CalendarAdapter.OnItemListener onItemListener;

    public CalendarViewHolder(@NonNull @NotNull View itemView, CalendarAdapter.OnItemListener onItemListener, ArrayList<LocalDate> days) {
        super(itemView);
        this.clDayCell = itemView.findViewById(R.id.cell_day_container);
        tvDay = itemView.findViewById(R.id.cell_day_text);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
        this.days = days;
    }

    public TextView getTvDay() {
        return tvDay;
    }

    public View getCell() { return clDayCell;}

    @Override
    public void onClick(View v) {
        onItemListener.onItemClick(getAdapterPosition(),days.get(getAdapterPosition()));
    }

}
