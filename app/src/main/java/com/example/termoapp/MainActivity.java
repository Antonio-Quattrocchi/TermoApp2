package com.example.termoapp;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.termoapp.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private double targetTemperature = 20.0;
    private boolean isConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // UI iniziale
        updateTemperatureUI();

        // Bottone di Connessione
        binding.btnConnect.setOnClickListener(v -> connettiAlTermostato());

        // Bottone +
        binding.btnPlus.setOnClickListener(v -> {
            if (isConnected) {
                targetTemperature += 0.5;
                updateTemperatureUI();
                inviaDatiAlTermostato(targetTemperature);
            } else {
                mostraAvvisoConnessione();
            }
        });

        // Bottone -
        binding.btnMinus.setOnClickListener(v -> {
            if (isConnected) {
                targetTemperature -= 0.5;
                updateTemperatureUI();
                inviaDatiAlTermostato(targetTemperature);
            } else {
                mostraAvvisoConnessione();
            }
        });
    }

    private void updateTemperatureUI() {
        binding.textTemperature.setText(String.format(Locale.getDefault(), "%.1f °C", targetTemperature));
    }

    private void mostraAvvisoConnessione() {
        Toast.makeText(this, "Connettiti prima al termostato!", Toast.LENGTH_SHORT).show();
    }

    private void connettiAlTermostato() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (wifiManager != null && !wifiManager.isWifiEnabled()) {
            Toast.makeText(this, "Attiva il Wi-Fi del telefono!", Toast.LENGTH_LONG).show();
            return;
        }

        // Simulazione connessione
        isConnected = true;
        binding.textStatus.setText("Stato: Connesso a Termostato_WiFi");
        binding.textStatus.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_dark));
        Toast.makeText(this, "Collegato con successo!", Toast.LENGTH_SHORT).show();
    }

    private void inviaDatiAlTermostato(double nuovaTemperatura) {
        // Feedback visivo dell'invio
        Toast.makeText(this, "Inviato: " + nuovaTemperatura + "°C", Toast.LENGTH_SHORT).show();
    }
}
