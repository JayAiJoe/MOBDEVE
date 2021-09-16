package com.mobdeve.group11.assist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    //toast messages regarding sms success/failure
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle extrasBundle = intent.getExtras();
        String number=extrasBundle.getString("Number");
        String message=extrasBundle.getString("Message");
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, message, null, null);
            Toast.makeText(context, "SMS Sent to " + number,Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(context,"SMS failed, please try again later!",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
