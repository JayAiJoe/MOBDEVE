package com.mobdeve.group11.assist;

import java.time.LocalDate;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleOwner;

import com.mobdeve.group11.assist.database.AssistViewModel;

public class EventCountRunnable implements Runnable{

    int index;
    Handler handler;

    public EventCountRunnable(int index, Handler handler){
        this.index = index;
        this.handler = handler;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void run() {

            Bundle bundle = new Bundle();
            bundle.putInt("INDEX", this.index);

            Message message = Message.obtain();
            message.setData(bundle);

            this.handler.sendMessage(message);
    }
}
