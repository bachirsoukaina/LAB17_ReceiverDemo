package com.example.lab17_receiverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NetworkStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(intent.getAction())) {
            boolean airplaneOn = intent.getBooleanExtra("state", false);
            String msg = airplaneOn
                    ? "✈️ Mode avion ACTIVÉ"
                    : "📶 Mode avion DÉSACTIVÉ";
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }
}