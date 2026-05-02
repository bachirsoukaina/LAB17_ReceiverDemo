package com.example.lab17_receiverdemo;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private NetworkStateReceiver networkReceiver;
    private AlertReceiver alertReceiver;
    private boolean networkListening = false;

    private TextView labelNetworkState;
    private Button btnNetworkToggle;
    private Button btnFireAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        labelNetworkState = findViewById(R.id.labelNetworkState);
        btnNetworkToggle  = findViewById(R.id.btnNetworkToggle);
        btnFireAlert      = findViewById(R.id.btnFireAlert);

        networkReceiver = new NetworkStateReceiver();
        alertReceiver   = new AlertReceiver();

        // AlertReceiver = broadcast interne, RECEIVER_NOT_EXPORTED ok
        IntentFilter alertFilter = new IntentFilter(AlertReceiver.ACTION_INTERNAL_ALERT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(alertReceiver, alertFilter, RECEIVER_NOT_EXPORTED);
        } else {
            registerReceiver(alertReceiver, alertFilter);
        }

        btnNetworkToggle.setOnClickListener(v -> handleNetworkToggle());
        btnFireAlert.setOnClickListener(v -> dispatchInternalAlert());
    }

    private void handleNetworkToggle() {
        if (!networkListening) {
            IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
            // RECEIVER_EXPORTED obligatoire pour recevoir les broadcasts SYSTEME sur Android 14+
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                registerReceiver(networkReceiver, filter, RECEIVER_EXPORTED);
            } else {
                registerReceiver(networkReceiver, filter);
            }
            networkListening = true;
            labelNetworkState.setText("Ecoute mode avion : ACTIVE");
            btnNetworkToggle.setText("Arreter ecoute");
        } else {
            unregisterReceiver(networkReceiver);
            networkListening = false;
            labelNetworkState.setText("Ecoute mode avion : INACTIVE");
            btnNetworkToggle.setText("Demarrer ecoute");
        }
    }

    private void dispatchInternalAlert() {
        Intent intent = new Intent(AlertReceiver.ACTION_INTERNAL_ALERT);
        intent.putExtra("payload", "Signal envoye depuis MainActivity");
        sendBroadcast(intent);
        Toast.makeText(this, "Broadcast envoye", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        if (networkListening) {
            unregisterReceiver(networkReceiver);
        }
        unregisterReceiver(alertReceiver);
        super.onDestroy();
    }
}