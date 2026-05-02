package com.example.lab17_receiverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlertReceiver extends BroadcastReceiver {

    public static final String ACTION_INTERNAL_ALERT = "com.example.lab17_receiverdemo.INTERNAL_ALERT";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_INTERNAL_ALERT.equals(intent.getAction())) {
            String payload = intent.getStringExtra("payload");
            Toast.makeText(context, "📣 Alerte reçue : " + payload, Toast.LENGTH_LONG).show();
        }
    }
}