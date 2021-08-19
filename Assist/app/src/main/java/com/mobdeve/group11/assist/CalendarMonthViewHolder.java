package com.mobdeve.group11.assist;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CalendarMonthViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView tvDay;
    private final CalendarMonthAdapter.OnItemListener onItemListener;

    public CalendarMonthViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView, CalendarMonthAdapter.OnItemListener onItemListener) {
        super(itemView);
        tvDay = itemView.findViewById(R.id.cell_day_text);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
    }

    public TextView getTvDay() {
        return tvDay;
    }

    @Override
    public void onClick(View v) {
        onItemListener.onItemClick(getAdapterPosition(),(String) tvDay.getText());
    }
}
