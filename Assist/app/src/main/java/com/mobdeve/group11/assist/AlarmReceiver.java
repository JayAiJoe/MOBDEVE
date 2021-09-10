package com.mobdeve.group11.assist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {
        String sms;
        String phoneNo;
        Bundle extrasBundle = intent.getExtras();
        phoneNo=extrasBundle.getString("phoneNo");
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, sms, null, null);
            Toast.makeText(context, "SMS Sent to " + phoneNo,Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(context,"SMS faild, please try again later!",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
}
